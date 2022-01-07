package br.dev.thiagoalopes.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.thiagoalopes.dscatalog.dto.CategoryDTO;
import br.dev.thiagoalopes.dscatalog.entities.Category;
import br.dev.thiagoalopes.dscatalog.repositories.CategoryRepository;
import br.dev.thiagoalopes.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		return this.categoryRepository.findAll().stream()
				.map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		return new CategoryDTO(this.categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Entity not found")));
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
}
