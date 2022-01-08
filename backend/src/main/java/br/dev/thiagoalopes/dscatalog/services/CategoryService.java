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
import br.dev.thiagoalopes.dscatalog.entities.Category;
import br.dev.thiagoalopes.dscatalog.repositories.CategoryRepository;
import br.dev.thiagoalopes.dscatalog.services.exceptions.DatabaseException;
import br.dev.thiagoalopes.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAll(PageRequest pageRequest) {
		return this.categoryRepository.findAll(pageRequest)
				.map(x -> new CategoryDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		return new CategoryDTO(this.categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found")));
	}
	
	@Transactional
	public CategoryDTO store(CategoryDTO categoryDTO) {
		
		return new CategoryDTO(this.categoryRepository
				.save(new Category(null, categoryDTO.getName())));
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
		
		Category category = null;
		
		try {
			
			category = this.categoryRepository.getById(id);
			category.setName(categoryDTO.getName());
			category = this.categoryRepository.save(category);
			
			return new CategoryDTO(category);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Resource not found", e);
		}
	}
	
	public void delete(Long id) {
		
		try {
			this.categoryRepository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Resource not found", e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("", e);
		}
	}
}
