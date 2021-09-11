package br.com.fiap.matchfruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.matchfruit.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findByNameLikeIgnoreCase(String name);
	
}
