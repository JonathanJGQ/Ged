package br.com.innovaro.gd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Conteudo {
	
	@Id
	@GeneratedValue	
	private Long id;
	private Long idSecao;
	private Long idDocumento;
	@Column(length=10485760, columnDefinition = "text")
	private String conteudo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdSecao() {
		return idSecao;
	}
	public void setIdSecao(Long idSecao) {
		this.idSecao = idSecao;
	}
	public Long getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
}
