package br.dev.thiagoalopes.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.thiagoalopes.dscatalog.entities.Category;

@RestController
@RequestMapping("categories")
public class CategoryResource {

	public ResponseEntity<Category> findAll() {
		
		List<Category> categories = new ArrayList<>();
		
		for(int x=1; x <= 10; x++) {
			categories.add(new Category((Long) x , "Categoria " + x));
		}
		
	}
	
}
