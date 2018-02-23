package se.goteborg.retursidan.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import se.goteborg.retursidan.model.entity.Person;

@Repository
public class PersonDAO extends BaseDAO<Person> {
	
	/**
	 * @see BaseDAO#findById(int)
	 */
	@Override
	public Person findById(int id) {
		return (Person)getSessionFactory().getCurrentSession().get(Person.class, id);
	}

	/**
	 * @see BaseDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Person> findAll() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Person.class);
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}

	/**
	 * Find a user given a user id
	 * @param uid the user id to search for
	 * @return a Person entity object representing the user, or null if no user was found
	 */
	public Person findByUid(String uid) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Person.class);
		criteria.add(Restrictions.eq("userId", uid));
		criteria.addOrder(Order.desc("id"));
		return (Person)criteria.uniqueResult();
	}
}
