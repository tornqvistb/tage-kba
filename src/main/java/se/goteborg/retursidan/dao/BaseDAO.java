package se.goteborg.retursidan.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Base class for all DAO classes, contains and defines common functionality
 *
 * @param <T> The entity type
 */
@Repository
public abstract class BaseDAO<T> {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Get the autowired session factory defined in the spring configuration
	 * @return the session factory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Add an entity to the database
	 * @param entity The entity to add
	 * @return the generated identifier for the persisted entity
	 */
	public int add(T entity) {
		return (Integer)getSessionFactory().getCurrentSession().save(entity);	
	}
	
	/**
	 * Update an entity in the database
	 * @param entity the entity to update
	 */
	public void update(T entity) {
		getSessionFactory().getCurrentSession().update(entity);
	}

	/**
	 * Save or update an entity depending on whether it already exists in the database or not
	 * @param entity the entity to persist
	 */
	public void saveOrUpdate(T entity) {
		getSessionFactory().getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * Copy the state of the given entity onto the persistent entity with the same identifier.
	 * @param entity the entity to merge
	 */
	public void merge(T entity) {
		getSessionFactory().getCurrentSession().merge(entity);
	}
	
	/**
	 * Delete an entity from the database
	 * @param entity the entity to delete
	 */
	public void delete(T entity) {
		getSessionFactory().getCurrentSession().delete(entity);
	}
	
	/**
	 * Find a specific entity with the given id
	 * @param id the entity id to search for
	 * @return the found entity or null if no entity was found
	 */
	public abstract T findById(int id);
	
	/**
	 * Find all entities in the database
	 * @return a list of entities found
	 */
	public abstract List<T> findAll();
}