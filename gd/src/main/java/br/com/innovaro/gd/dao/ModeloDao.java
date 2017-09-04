package br.com.innovaro.gd.dao;

import javax.persistence.Query;

import br.com.innovaro.gd.model.Modelo;
import br.com.innovaro.gd.model.Secao;

public class ModeloDao extends GenericDao<Modelo,Long>{
	
	@Override
	public void exclui(Long id) {
		try {
			Query query = entityManager.createQuery("DELETE FROM Secao WHERE idtemplate = :id");
			query.setParameter("id", id);
			
			transaction.begin();
			query.executeUpdate();
			query = entityManager.createQuery("DELETE FROM Modelo WHERE id = '" + id + "'");
			query.executeUpdate();
			transaction.commit();
		}
		catch (Exception e) {
			transaction.rollback();
		}
	}
}
