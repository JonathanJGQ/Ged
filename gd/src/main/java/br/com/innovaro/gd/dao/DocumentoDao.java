package br.com.innovaro.gd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import br.com.innovaro.gd.model.Documento;
import br.com.innovaro.gd.model.Secao;
import br.com.innovaro.gd.type.DocumentoStatusType;

public class DocumentoDao extends GenericDao<Documento,Long>{
	
	@SuppressWarnings("unchecked")
	public List<Documento> buscaDocumentosNaoAprovados(String order){
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			StringBuffer sql = new StringBuffer("SELECT obj FROM "+ getClassPersistence().getSimpleName() +" obj WHERE status <> 'Aprovado'");
			if(order != null){
				sql.append(" ORDER BY "+ order);
			}
			Query query = entityManager.createQuery(sql.toString());
			return query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			entityManager.close();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Documento> buscaDocumentosPorStatus(String type){
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			Query query = entityManager.createQuery("FROM Documento WHERE status = '" + type + "'");
			return query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			entityManager.close();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Object contarDocumentosPorStatus(String type){
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			Query query = entityManager.createQuery("SELECT COUNT(id) FROM Documento WHERE status = '" + type + "'");
			return query.getSingleResult();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			entityManager.close();
		}
		return null;
	}
	
	@Override
	public void delete(Long id) {
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			Query query = entityManager.createQuery("DELETE FROM Conteudo WHERE idDocumento = :id");
			query.setParameter("id", id);
			
			entityManager.getTransaction().begin();
			query.executeUpdate();
			query = entityManager.createQuery("DELETE FROM Documento WHERE id = :id");
			query.setParameter("id", id);
			query.executeUpdate();
			entityManager.getTransaction().commit();
			
		} catch (Exception e) {
			if(entityManager.isOpen()) {
				entityManager.getTransaction().rollback();
			}
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}
}
