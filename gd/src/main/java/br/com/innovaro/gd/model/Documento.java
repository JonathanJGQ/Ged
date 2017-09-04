package br.com.innovaro.gd.model;

public class Documento {
	private long id;
	private long idTemplate;
	private String nome;
	private String descricao;
	private String autor;
	private String status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdTemplate() {
		return idTemplate;
	}
	public void setIdTemplate(long idTemplate) {
		this.idTemplate = idTemplate;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
