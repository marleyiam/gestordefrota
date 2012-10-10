package com.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public class GenericDAO<T> {

	private final static String UNIT_NAME = "ejbPU";

	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager em;

	private Class<T> entityClass;

	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void save(T entity) {
		em.persist(entity);
	}

	public void delete(T entity) {
		T entityRemovida = em.merge(entity);

		em.remove(entityRemovida);
	}

	public T update(T entity) {
		return em.merge(entity);
	}

	public T find(int entityID) {
		return em.find(entityClass, entityID);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parametros) {
		T resultado = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			if (parametros != null && !parametros.isEmpty()) {
				populateQueryParameters(query, parametros);
			}

			resultado = (T) query.getSingleResult();

		} catch (Exception e) {
			System.out.println("Erro na execução da query: " + e.getMessage());
			e.printStackTrace();
		}

		return resultado;
	}

	private void populateQueryParameters(Query query,
			Map<String, Object> parametros) {

		for (Entry<String, Object> entry : parametros.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

}
