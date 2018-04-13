package se.goteborg.retursidan.dao;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;
import static org.hibernate.criterion.Restrictions.or;
import static org.hibernate.criterion.Restrictions.le;

import static se.goteborg.retursidan.model.entity.Advertisement.DisplayOption.ENTIRE_CITY;
import static se.goteborg.retursidan.model.entity.Advertisement.Status.PUBLISHED;
import static java.util.Calendar.DAY_OF_YEAR;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import se.goteborg.retursidan.model.PagedList;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Advertisement.Status;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Photo;
import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.util.StringFormatter;

/**
 * Data access object for the Advertisement entity objects
 *
 */
@Repository
public class AdvertisementDAO extends BaseDAO<Advertisement> {
	
	/**
	 * @see BaseDAO#findById(int)
	 */
	@Override
	public Advertisement findById(int id) {
		Advertisement advertisement = (Advertisement)getSessionFactory().getCurrentSession().get(Advertisement.class, id);
		if (advertisement != null) {
			replaceProxiedPhoto(advertisement);
		}
		return advertisement;
	}

	/**
	 * @see BaseDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Advertisement> findAll() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Advertisement.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("created"));
		return replaceProxiedPhotos(criteria.list());
	}
	
	/**
	 * Retrieve a paged list of advertisements from the database, filtered using the method parameters
	 * @param creatorUid The userId of the author of the advertisement, or null for all authors
	 * @param status The advertisement status, or null for all statuses
	 * @param topCategory The top category for the advertisements, or null for all categories
	 * @param category The sub category for the advertisements, or null for all sub categories
	 * @param unit The unit of the advertisement, or null for all units
	 * @param usingDisplayOption If true and no unit is set, fetch only advertisements aimed for the entire city 	
	 * @param page The page number
	 * @param pageSize The size of each page
	 * @return a PagedList containing the results
	 */
	@SuppressWarnings("unchecked")
	public PagedList<Advertisement> find(String creatorUid, Status status, Category topCategory, Category category, Unit unit, String bookerUid, String searchString, boolean usingDisplayOption, int page, int pageSize) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Advertisement.class, "a");
		if (creatorUid != null) {
			criteria.add(Restrictions.eq("creatorUid", creatorUid));
		}
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		if (topCategory != null && topCategory.getId() != -1) {
			criteria.createCriteria("category").add(Restrictions.eq("parent", topCategory));
			if (category != null && category.getId() != -1) {
				criteria.add(Restrictions.eq("category", category));
			}
		}
		if (unit  != null && unit.getId() != -1) {
			criteria.add(Restrictions.eq("unit", unit));
		} else if (usingDisplayOption){
			criteria.add(Restrictions.eq("displayOption", Advertisement.DisplayOption.ENTIRE_CITY));
		}
		
		// Search string
		if (StringUtils.isNotEmpty(searchString)) {
			String[] searchParts = searchString.split(" ");
			criteria.createAlias("a.contact", "c");
			for (String part : searchParts) {
				part = part.trim();
				Criterion likeTitle = like("title", "%" + part + "%").ignoreCase();
				Criterion likeDescription = like("description", "%" + part + "%").ignoreCase();
				Criterion likeCreatorUid = like("creatorUid", "%" + part + "%").ignoreCase();				
				Criterion likeContactName = like("c.name", "%" + part + "%").ignoreCase();
				Criterion likeContactPhone = like("c.phone", "%" + part + "%").ignoreCase();
				Criterion likeContactEmail = like("c.email", "%" + part + "%").ignoreCase();
				Criterion likePickupAddress = like("pickupAddress", "%" + part + "%").ignoreCase();
				Criterion likePickupConditions = like("pickupConditions", "%" + part + "%").ignoreCase();
				Criterion equalsID;
				if (StringFormatter.isInteger(part)) {
					equalsID = eq("id", Integer.parseInt(part));
				} else {
					equalsID = eq("id", -1);
				}
							
				criteria.add(or(likeTitle, likeDescription, likeCreatorUid, likeContactName, 
						likeContactPhone, likeContactEmail, likePickupAddress, likePickupConditions, equalsID));
			}
		}
		
		// Booker UID
		if (bookerUid != null) {
			criteria.createAlias("a.booker", "b");
			criteria.add(Restrictions.eq("b.userId", bookerUid));
		}
		
		// get the row count for this query
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		long totalCount = (Long)criteria.list().get(0);
		
		// clear the projection and set the result transformer again
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		criteria.addOrder(Order.desc("publishDate"));
		criteria.setMaxResults(pageSize);
		criteria.setFetchSize(pageSize);
		criteria.setFirstResult((page - 1) * pageSize);
		List<Advertisement> list = criteria.list();
				
		return new PagedList<Advertisement>(replaceProxiedPhotos(list), page, pageSize, (int)totalCount);
	}
	
	/**
	 * Replace all photo objects attached to this advertisement with new, non-proxied objects. This is needed in order to 
	 * avoid errors regarding missing hibernate session when the photo objects are accessed in the JSP pages. We do not need the 
	 * proxied objects because we are not interested of the image byte content but only the id's of the images, in order to construct
	 * photo serving resource URL's. 
	 * @param advertisement The advertisement for which all photos should be replaced
	 * @return The advertisement with replaced photos
	 */
	private Advertisement replaceProxiedPhoto(Advertisement advertisement) {
		List<Photo> photos = advertisement.getPhotos();
		List<Photo> newPhotos = new ArrayList<Photo>();
		for (Photo photo : photos) {
			Photo newPhoto = new Photo();
			newPhoto.setId(photo.getId());
			newPhotos.add(newPhoto);
		}
		advertisement.setPhotos(newPhotos);
		return advertisement;
	}	
	
	/**
	 * Replace all photos for all advertisements in a list
	 * @see AdvertisementDAO#replaceProxiedPhoto(Advertisement)
	 * @param list The list of advertisements
	 * @return The list with advertisements that has been updated with replaced photos
	 */
	private List<Advertisement> replaceProxiedPhotos(List<Advertisement> list) {
		for (Advertisement advertisement : list) {
			replaceProxiedPhoto(advertisement);
		}
		return list;
	}
	
	/**
	 * Retrieve the count of all advertisements in the database
	 * @return the number of advertisements
	 */
	public Integer count() {
		return count(null, null);
	}

	/**
	 * Retrieve the count of all advertisements for the specified unit
	 * @param unit The unit to use as filter
	 * @return the number of advertisements
	 */
	public Integer count(Unit unit) {
		return count(null, unit);
	}

	/**
	 * Retrieve the count of all advertisements for the specified status
	 * @param status The status to use as a filter
	 * @return the number of advertisements
	 */
	public Integer count(Status status) {
		return count(status, null);
	}

	/**
	 * Count the number of advertisements in the database, filtered using the provided method parameters
	 * @param status The status to filter with, or null for all statuses
	 * @param unit The unit to filter with, or null for all units
	 * @return the number of advertisements
	 */
	public Integer count(Status status, Unit unit) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Advertisement.class);
		if (unit != null) {
			criteria.add(Restrictions.eq("unit", unit));
		}
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		return ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	/**
	 * Expire any ad that is older than the provided amount of days
	 * @param days The maximum number of days 
	 * @return the number of ads removed
	 */
	public int expireOldAds(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -days);
		Date maxDate = cal.getTime();
		Query query = getSessionFactory().getCurrentSession().createQuery("UPDATE Advertisement SET status=:status WHERE publishDate <= :maxDate AND status=:publishStatus");
		query.setParameter("status", Status.EXPIRED);
		query.setParameter("publishStatus", Status.PUBLISHED);
		query.setDate("maxDate", maxDate);
		return query.executeUpdate();
	}
	
	@Override
	/**
	 * Delete an entity from the database
	 * @param entity the entity to delete
	 */
	public void delete(Advertisement advertisement) {
		for (Photo photo : advertisement.getPhotos()) {
			Query query = getSessionFactory().getCurrentSession().createSQLQuery( "delete from photoset where photos_id=:id")
		            .setParameter("id", photo.getId()); 
			query.executeUpdate();
			query = getSessionFactory().getCurrentSession().createSQLQuery( "delete from photo where id=:id")
		            .setParameter("id", photo.getId()); 
			query.executeUpdate();
		}
		Query query = getSessionFactory().getCurrentSession().createSQLQuery( "delete from advertisement where id=:id")
	            .setParameter("id", advertisement.getId()); 
		query.executeUpdate();
	}

	 /**
	  * Counts ads by category
	  * 
	  * @param category
	  * @return
	  */
	public Integer countByCategory(Category category) {
		String hql = "select count(*) from Advertisement ad"
            + " where ad.category.id = :id";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql).setParameter("id", category.getId());
		return ((Number) query.uniqueResult()).intValue();
	}
	
	/**
	 * Finds all ads that have expired and are to be unpublished.
	 * 
	 * @param days
	 * @return list of ads that have expired.
	 */
	@SuppressWarnings("unchecked")
    public List<Advertisement> findAdsToExpire(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(DAY_OF_YEAR, -days);
        Date maxDate = cal.getTime();
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Advertisement.class);
        criteria.add(eq("displayOption", ENTIRE_CITY));
        criteria.add(eq("status", PUBLISHED));
        criteria.add(le("publishDate", maxDate));
        return criteria.list();
    }
}
