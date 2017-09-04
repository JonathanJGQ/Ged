package br.com.innovaro.gd.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;

public class GenericDao<T, ID extends Serializable> {

	protected static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ged");
	protected EntityManager entityManager = entityManagerFactory.createEntityManager();
	protected EntityTransaction transaction = entityManager.getTransaction();
	
	private final Class<T> classPersistence;
	
	@SuppressWarnings("unchecked")
	public GenericDao() {
		this.classPersistence = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public T salva(T object) {
		transaction.begin();
		entityManager.persist(object);
		transaction.commit();
		entityManager.clear();
		return object;
	}
	
	public T atualiza(T object) {
		transaction.begin();
		entityManager.merge(object);
		transaction.commit();
		entityManager.clear();
		return null;
	}
	
	public void exclui(ID id) {
		T object = entityManager.find(getClassPersistence(), id);
		transaction.begin();
		entityManager.remove(object);
		transaction.commit();
		entityManager.clear();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscaTodos(String order){
		StringBuffer sql = new StringBuffer("SELECT obj FROM "+ getClassPersistence().getSimpleName() +" obj");
		
		if(order != null){
			sql.append(" ORDER BY "+ order);
		}
		
		Query q = entityManager.createQuery(sql.toString());
		entityManager.clear();
		return q.getResultList();
	}
	
	public T buscaPorId(ID id){
		return entityManager.find(getClassPersistence(), id);
	}
	
	protected Criteria criaCriteria(){
		Session session = (Session) entityManager.getDelegate();
		entityManager.clear();
		return session.createCriteria(getClassPersistence());
	}
	
	public Class<T> getClassPersistence() {
		return classPersistence;
	}
}
