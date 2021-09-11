package br.com.fiap.matchfruit.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "É obrigatório o usuário ter um nome.")
	private String name;
	
	@NotBlank(message = "É obrigatório o usuário ter um email.")
	@Email(message = "Precisa ser um email válido.")
	private String email;
	
	@NotBlank(message = "É obrigatório o usuário ter uma senha.")
	@Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
	private String pass;
	
	@ManyToMany
	@JoinTable(
			name = "tb_favorites", 
			joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "fruit_id"))
	private Set<Fruit> favorites;

}
