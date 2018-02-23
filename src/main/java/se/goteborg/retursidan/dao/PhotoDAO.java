package se.goteborg.retursidan.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import se.goteborg.retursidan.model.entity.Photo;

@Repository
public class PhotoDAO extends BaseDAO<Photo> {
	
	
	@Override
	/**
	 * Delete an entity from the database
	 * @param entity the entity to delete
	 */
	public void delete(Photo photo) {
		Query query = getSessionFactory().getCurrentSession().createSQLQuery( "delete from photoset where photos_id=:id")
	            .setParameter("id", photo.getId()); 
		query.executeUpdate();
		query = getSessionFactory().getCurrentSession().createSQLQuery( "delete from photoset_request where photos_id=:id")
	            .setParameter("id", photo.getId()); 
		query.executeUpdate();
		getSessionFactory().getCurrentSession().delete(photo);
	}

	/**
	 * @see BaseDAO#findById(int)
	 */
	@Override
	public Photo findById(int id) {
		return (Photo)getSessionFactory().getCurrentSession().get(Photo.class, id);
	}

	/**
	 * @see BaseDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Photo> findAll() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Photo.class);
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}
}
