package br.com.fiap.matchfruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.matchfruit.model.Fruit;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
	
	List<Fruit> findByNameLikeIgnoreCase(String name);
	
}
