package br.dev.thiagoalopes.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.thiagoalopes.dscatalog.dto.CategoryDTO;
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
}
