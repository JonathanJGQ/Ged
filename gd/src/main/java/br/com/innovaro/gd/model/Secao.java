package br.com.innovaro.gd.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Secao {
	
	@Id
	@GeneratedValue
	private Long id;
	private Long idTemplate;
	private String nome;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdTemplate() {
		return idTemplate;
	}
	public void setIdTemplate(Long idTemplate) {
		this.idTemplate = idTemplate;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
