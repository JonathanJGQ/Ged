package br.com.innovaro.gd.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Documento {
	
	@Id
	@GeneratedValue
	private Long id;
	private Long idTemplate;
	private String nome;
	private String autor;
	private String status;
	@Temporal(TemporalType.DATE)
	private Date vigencia_inicio;
	@Temporal(TemporalType.DATE)
	private Date vigencia_fim;
	private String versao;
	
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
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public Date getVigencia_inicio() {
		return vigencia_inicio;
	}
	public void setVigencia_inicio(Date vigencia_inicio) {
		this.vigencia_inicio = vigencia_inicio;
	}
	public Date getVigencia_fim() {
		return vigencia_fim;
	}
	public void setVigencia_fim(Date vigencia_fim) {
		this.vigencia_fim = vigencia_fim;
	}
}
