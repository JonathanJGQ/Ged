package br.com.innovaro.gd.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.innovaro.gd.model.Modelo;
import br.com.innovaro.gd.model.Secao;

public class ModeloDao extends GenericDao<Modelo,Long>{
	
	@Override
	public void delete(Long id) {
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			Query query = entityManager.createQuery("DELETE FROM Secao WHERE idtemplate = :id");
			query.setParameter("id", id);
			
			entityManager.getTransaction().begin();
			query.executeUpdate();
			query = entityManager.createQuery("DELETE FROM Modelo WHERE id = '" + id + "'");
			query.executeUpdate();
			entityManager.getTransaction().commit();
		}
		catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
	}
}
