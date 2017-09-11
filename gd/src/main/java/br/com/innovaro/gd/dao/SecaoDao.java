package br.com.innovaro.gd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.innovaro.gd.model.Secao;

public class SecaoDao extends GenericDao<Secao,Long>{
	
	@SuppressWarnings("unchecked")
	public List<Secao> buscaSecaoPorModelo(Long modelo_id){
		EntityManager entityManager = JpaUtil.getEntityManager();
		Query query = entityManager.createQuery("FROM Secao WHERE idtemplate = '" + modelo_id + "'");
		return query.getResultList();
	}
	
}
