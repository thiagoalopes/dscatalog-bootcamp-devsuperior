package br.dev.thiagoalopes.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.thiagoalopes.dscatalog.dto.CategoryDTO;
import br.dev.thiagoalopes.dscatalog.services.CategoryService;

@RestController
@RequestMapping("categories")
public class CategoryResource {

	@Autowired
	private CategoryService CategoryService;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		return ResponseEntity.ok(this.CategoryService.findAll());
	}
	
}
