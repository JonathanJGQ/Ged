package br.com.innovaro.gd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.innovaro.gd.model.Secao;

public class SecaoDao extends GenericDao<Secao,Long>{
	
	@SuppressWarnings("unchecked")
	public List<Secao> buscaSecaoPorModelo(Long modelo_id){
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			Query query = entityManager.createQuery("FROM Secao WHERE idtemplate = '" + modelo_id + "' ORDER BY posicao");
			return query.getResultList();
		}
		catch(Exception e) {
			if(entityManager.getTransaction() != null) {
				if(entityManager.getTransaction().isActive()) {
					entityManager.getTransaction().rollback();
				}
			}
			return null;
		}
		finally{
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}
}
