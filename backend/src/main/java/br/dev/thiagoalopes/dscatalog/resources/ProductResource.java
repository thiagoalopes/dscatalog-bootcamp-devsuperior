package br.dev.thiagoalopes.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.dev.thiagoalopes.dscatalog.dto.ProductDTO;
import br.dev.thiagoalopes.dscatalog.resources.exceptions.ResourceArgumentException;
import br.dev.thiagoalopes.dscatalog.services.ProductService;

@RestController
@RequestMapping("products")
public class ProductResource {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
			) {
		
		try {
			PageRequest pageRequest = PageRequest
					.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
			
			return ResponseEntity.ok(this.productService.findAll(pageRequest));
		} catch (IllegalArgumentException e) {
			throw new ResourceArgumentException("Wrong argument", e);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(this.productService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> store(@RequestBody ProductDTO ProductDTO) {
		
		ProductDTO = this.productService.store(ProductDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(ProductDTO.getId()).toUri();

		return ResponseEntity.created(uri)
				.body(ProductDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> store(@PathVariable Long id, @RequestBody ProductDTO ProductDTO) {
		return ResponseEntity.ok(this.productService.update(id, ProductDTO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> store(@PathVariable Long id) {
		this.productService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
