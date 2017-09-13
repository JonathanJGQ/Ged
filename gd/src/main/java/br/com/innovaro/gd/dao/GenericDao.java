package br.com.innovaro.gd.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.vaadin.ui.Notification;

import br.com.innovaro.gd.dao.JpaUtil;

public class GenericDao<T, ID extends Serializable> {
	
	private final Class<T> classPersistence;
	
	@SuppressWarnings("unchecked")
	public GenericDao() {
		this.classPersistence = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	
	
	public T save(T object) {
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			
			EntityTransaction t = entityManager.getTransaction();
			t.begin();
			entityManager.persist(object);
			entityManager.flush();
			t.commit();
			
//			entityManager.getTransaction().begin();
//			if(entityManager.getTransaction().isActive()) {
//				Notification.show("Tá ativo");
//			}
//			entityManager.persist(object);
//			entityManager.getTransaction().commit();
			return object;
			
		} catch (Exception e) {
//			if(entityManager.getTransaction() != null) {
//				if(entityManager.getTransaction().isActive()) {
//					entityManager.getTransaction().rollback();
//				}
//			}
			if(entityManager.isOpen()) {
				entityManager.getTransaction().rollback();
				Notification.show(e.getMessage());
				e.printStackTrace();
			}
			return null;
			
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}
	
	public T update(T object) {
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(object);
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
		return null;
	}
	
	public void delete(ID id) {
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			T object = entityManager.find(getClassPersistence(), id);
			entityManager.getTransaction().begin();
			entityManager.remove(object);
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
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(String order){
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			StringBuffer sql = new StringBuffer("SELECT obj FROM "+ getClassPersistence().getSimpleName() +" obj");
			if(order != null){
				sql.append(" ORDER BY "+ order);
			}
			Query query = entityManager.createQuery(sql.toString());
			return query.getResultList();
			
		} catch (Exception e) {
			if(entityManager.isOpen()) {
				entityManager.getTransaction().rollback();
			}
			return null;
			
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}
	
	public T findById(ID id){
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			return entityManager.find(getClassPersistence(), id);
			
		} catch (Exception e) {
			if(entityManager.isOpen()) {
				entityManager.getTransaction().rollback();
			}
			return null;
			
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}
	
	public Class<T> getClassPersistence() {
		return classPersistence;
	}
}
