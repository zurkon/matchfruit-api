package br.com.fiap.matchfruit.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import br.com.fiap.matchfruit.model.User;
import br.com.fiap.matchfruit.repository.FruitRepository;
import br.com.fiap.matchfruit.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private FruitRepository fruitRepo;
	
	@GetMapping
	public List<User> index(@RequestParam(required = false) String name) {
		if (name == null) {			
			return userRepo.findAll();
		}
		return userRepo.findByNameLikeIgnoreCase("%" + name + "%");
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> get(@PathVariable Long id) {
		Optional<User> user = userRepo.findById(id);
		return ResponseEntity.of(user);
	}
	
	@PostMapping
	public ResponseEntity<User> create(@Valid @RequestBody User user, UriComponentsBuilder uriBuilder) {
		userRepo.save(user);
		URI uri = uriBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("{id}")
	public ResponseEntity<User> update(@Valid @RequestBody User newUser, @PathVariable Long id) {
		Optional<User> optional = userRepo.findById(id);
		if(optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		User user = optional.get();
		user.setName(newUser.getName());
		user.setEmail(newUser.getEmail());
		user.setPass(newUser.getPass());
		if( newUser.getFavorites() != null ) {
			if ( !newUser.getFavorites().isEmpty() ) {				
				user.setFavorites(newUser.getFavorites());
			}
		}
		
		userRepo.save(user);
		
		return ResponseEntity.ok(user);
		
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<User> delete(@PathVariable Long id) {
		Optional<User> optional = userRepo.findById(id);
		if(optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		userRepo.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{userId}/fruit/{fruitId}")
	public ResponseEntity<User> addFruit(@PathVariable Long userId, @PathVariable Long fruitId) {
		Optional<User> optUser = userRepo.findById(userId);
		Optional<Fruit> optFruit = fruitRepo.findById(fruitId);
		
		if (optUser.isEmpty() || optFruit.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		User user = optUser.get();
		Fruit fruit = optFruit.get();
		Set<Fruit> favorites = user.getFavorites();
		favorites.add(fruit);
		user.setFavorites(favorites);
		
		userRepo.save(user);
		
		return ResponseEntity.ok(user);
	}
	
}
