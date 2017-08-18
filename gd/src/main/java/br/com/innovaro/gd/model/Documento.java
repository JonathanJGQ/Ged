package br.com.innovaro.gd.model;

public class Documento {
	private long id;
	private long idTemplate;
	private String titulo;
	private String descricao;
	private String autor;
	
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
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
}
