package br.com.fiap.matchfruit.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "tb_fruit")
public class Fruit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome é obrigatório. Digite um nome.")
	@Size(max = 60, message = "O nome não pode ter mais de 60 caracteres.")
	private String name;
	
	@NotBlank(message = "As informações são obrigatórias para a descrição da fruta.")
	@Size(max = 500, message = "As informações não pode ter mais de 500 caractéres.")
	private String informations;
	
	@NotBlank(message = "É obrigatório ter os benefícios da frutas para melhor descrição.")
	@Size(max = 500, message = "Os benefícios não pode ter mais de 500 caractéres.")
	private String benefits;

	@Enumerated(EnumType.STRING)
	private Seasons season;
	
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	@ManyToMany(mappedBy = "favorites")
	private List<User> usuarios;

}
