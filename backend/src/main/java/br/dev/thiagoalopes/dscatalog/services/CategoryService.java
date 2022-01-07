package br.dev.thiagoalopes.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.thiagoalopes.dscatalog.entities.Category;
import br.dev.thiagoalopes.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<Category> findAll() {
		return this.categoryRepository.findAll();
	}
}
