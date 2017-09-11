package br.com.innovaro.gd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.innovaro.gd.model.Documento;
import br.com.innovaro.gd.model.Secao;

public class DocumentoDao extends GenericDao<Documento,Long>{
	
	@SuppressWarnings("unchecked")
	public List<Documento> buscaDocumentosNaoAprovados(){
		EntityManager entityManager = JpaUtil.getEntityManager();
		Query query = entityManager.createQuery("FROM Documento WHERE status <> 'Aprovado'");
		return query.getResultList();
	}
}
