package se.goteborg.retursidan.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import se.goteborg.retursidan.model.entity.Category;

/**
 * Data access object for the Category entity objects
 * 
 */
@Repository
public class CategoryDAO extends BaseDAO<Category> {
	
	/**
	 * @see BaseDAO#findById(int)
	 */
	@Override
	public Category findById(int id) {
		return (Category)getSessionFactory().getCurrentSession().get(Category.class, id);
	}

	/**
	 * @see BaseDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Category> findAll() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Category.class);
		criteria.addOrder(Order.asc("title"));
		return criteria.list();
	}
	
	/**
	 * Find all categories that doesn't have any parent.
	 * @return a list of categories
	 */
	@SuppressWarnings("unchecked")
	public List<Category> findTopCategories() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Category.class);
		criteria.add(Restrictions.isNull("parent"));
		criteria.addOrder(Order.asc("title"));
		return criteria.list();
	}

	/**
	 * Find all categories that are child of the given parent category
	 * @param id the id of the parent category
	 * @return a list of categories
	 */
	@SuppressWarnings("unchecked")
	public List<Category> findAllSubCategories(int id) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Category.class);
		criteria.createCriteria("parent").add(Restrictions.eq("id", id));
		criteria.addOrder(Order.asc("title"));
		return criteria.list();
	}
}
