package se.goteborg.retursidan.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import se.goteborg.retursidan.model.entity.Visit;

/**
 * Data access object for the Visit entity objects 
 * A visit entity represents the total number of visits made by one specific user 
 */
@Repository
public class VisitDAO extends BaseDAO<Visit> {
	
	/**
	 * @see BaseDAO#findById(int)
	 */
	@Override
	public Visit findById(int id) {
		return (Visit)getSessionFactory().getCurrentSession().get(Visit.class, id);
	}

	/**
	 * @see BaseDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Visit> findAll() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Visit.class);
		criteria.addOrder(Order.asc("lastVisit"));
		return criteria.list();
	}

	/**
	 * Find a visit entity object for a specific user
	 * @param uid the user id to search for
	 * @return the found visit, or null if nothing was found
	 */
	public Visit findByUid(String uid) {
		return (Visit)getSessionFactory().getCurrentSession()
			.createCriteria(Visit.class)
			.add(Restrictions.eq("userId", uid)).uniqueResult();
	}
	
	/**
	 * Return the number of unique visits to the application
	 * @return the unique count
	 */
	public int getUniqueVisitorCount() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Visit.class);
		criteria.setProjection(Projections.countDistinct("userId"));
		return ((Number)criteria.uniqueResult()).intValue();
	}
}
