package br.dev.thiagoalopes.dscatalog.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.thiagoalopes.dscatalog.dto.CategoryDTO;
import br.dev.thiagoalopes.dscatalog.dto.ProductDTO;
import br.dev.thiagoalopes.dscatalog.entities.Category;
import br.dev.thiagoalopes.dscatalog.entities.Product;
import br.dev.thiagoalopes.dscatalog.repositories.CategoryRepository;
import br.dev.thiagoalopes.dscatalog.repositories.ProductRepository;
import br.dev.thiagoalopes.dscatalog.services.exceptions.DatabaseException;
import br.dev.thiagoalopes.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(PageRequest pageRequest) {
		return this.productRepository.findAll(pageRequest)
				.map(x -> new ProductDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product=  this.productRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
		
		return new ProductDTO(product, product.getCategories());	
	}
	
	@Transactional
	public ProductDTO store(ProductDTO productDTO) {
		
		Product product = new Product();
		this.copyDtoToEntity(productDTO, product);
		
		return new ProductDTO(this.productRepository
				.save(product));
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO productDTO) {
		
		Product product = null;
		
		try {
			
			product = this.productRepository.getById(id);
			
			this.copyDtoToEntity(productDTO, product);

			product = this.productRepository.save(product);
			
			return new ProductDTO(product);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Resource not found", e);
		}
	}
	
	public void delete(Long id) {
		
		try {
			this.productRepository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Resource not found", e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("", e);
		}
	}
	
	private void copyDtoToEntity(ProductDTO productDTO, Product product) {
		
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setImgUrl(productDTO.getImgUrl());
		product.setDate(productDTO.getDate());
		
		product.getCategories().clear();
		
		for (CategoryDTO categoryDTO : productDTO.getCategories()) {
			Category category = this.categoryRepository.getById(categoryDTO.getId());
			product.getCategories().add(category);
		}
	}
	
}
