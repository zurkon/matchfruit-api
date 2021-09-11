package br.com.fiap.matchfruit.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.matchfruit.model.Fruit;
import br.com.fiap.matchfruit.repository.FruitRepository;

@RestController
@RequestMapping("/api/fruit")
public class FruitController {
	
	@Autowired
	FruitRepository repository;
	
	@GetMapping
	public List<Fruit> index(@RequestParam(required = false) String name) {
		
		if(name == null) {
			return repository.findAll();
		}
		
		return repository.findByNameLikeIgnoreCase("%" + name + "%");
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Fruit> get(@PathVariable Long id) {
		Optional<Fruit> fruit = repository.findById(id);
		return ResponseEntity.of(fruit);
	}
	
	@PostMapping
	public ResponseEntity<Fruit> create(@Valid @RequestBody Fruit fruit, UriComponentsBuilder uriBuilder) {
		repository.save(fruit);
		URI uri = uriBuilder.path("/api/fruit/{id}").buildAndExpand(fruit.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Fruit> update(@Valid @RequestBody Fruit newFruit, @PathVariable Long id) {
		Optional<Fruit> optional = repository.findById(id);
		if(optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Fruit fruit = optional.get();
		fruit.setName(newFruit.getName());
		fruit.setInformations(newFruit.getInformations());
		fruit.setBenefits(newFruit.getBenefits());
		fruit.setSeason(newFruit.getSeason());
		
		repository.save(fruit);
		
		return ResponseEntity.ok(fruit);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Fruit> delete(@PathVariable Long id) {
		Optional<Fruit> optional = repository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
	
}
