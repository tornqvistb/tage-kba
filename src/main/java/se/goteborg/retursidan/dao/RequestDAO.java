package se.goteborg.retursidan.dao;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.le;
import static org.hibernate.criterion.Restrictions.ge;
import static org.hibernate.criterion.Projections.rowCount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import se.goteborg.retursidan.model.DateSpan;
import se.goteborg.retursidan.model.PagedList;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Photo;
import se.goteborg.retursidan.model.entity.Request;
import se.goteborg.retursidan.model.entity.Request.Status;
import se.goteborg.retursidan.model.entity.Unit;

/**
 *  Data access object for the Request entity objects
 * 
 */
@Repository
public class RequestDAO extends BaseDAO<Request> {
	
    private static final String CRITERIA_STATUS = "status";
    private static final String CRITERIA_CREATED = "created";
    private static final String CRITERIA_UNIT = "unit";
    private static final String CRITERIA_CATEGORY = "category";
    private static final String CRITERIA_PUBLISH_DATE = "publishDate";
	
	/**
	 * @see BaseDAO#findById(int)
	 */
	@Override
	public Request findById(int id) {
		Request request = (Request)getSessionFactory().getCurrentSession().get(Request.class, id);
		if (request != null) {
			request = replaceProxiedPhoto(request);
		}				
		return request;
	}

	/**
	 * @see BaseDAO#findAll()
	 */
	@Override
	public List<Request> findAll() {
		return findAll(null);
	}

	/**
	 * Find wanted requests as a paged list using the status as filter
	 * @param status The status to filter with, or null no status should be used to filter
	 * @param page The page number to retrieve
	 * @param pageSize The size of each page
	 * @return A paged list with the result
	 */
	@SuppressWarnings("unchecked")
	public PagedList<Request> find(Status status, int page, int pageSize) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Request.class);
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		long totalCount = (Long)criteria.list().get(0);
		
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("created"));
		
		criteria.setMaxResults(pageSize);
		criteria.setFetchSize(pageSize);
		criteria.setFirstResult((page - 1) * pageSize);
		
		return new PagedList<Request>(replaceProxiedPhotos(criteria.list()), page, pageSize, (int)totalCount);
	}
	
	@SuppressWarnings("unchecked")
	public List<Request> findByCreatorUid(Status status, String creatorUid) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Request.class);
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		if (creatorUid != null) {
			criteria.add(Restrictions.eq("creatorUid", creatorUid));
		}		
		criteria.addOrder(Order.desc("created"));

		return replaceProxiedPhotos(criteria.list());
	}

	/**
	 * Find all wanted request for the given status
	 * @param status the status to search for or null if all requests should be retrieved
	 * @return a list of found requests
	 */
	@SuppressWarnings("unchecked")
	public List<Request> findAll(Status status) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Request.class);
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		criteria.addOrder(Order.desc("created"));
		return replaceProxiedPhotos(criteria.list());
	}
	
    /**
     * Retrieve the number of requests found in the database
     * 
     * @return the number of requests found
     */
    public Integer count() {
        return count(null, null, null);
    }
    /**
     * Retrieve the number of requests found in the database with a certain unit
     * 
     * @param unit
     * @return the number of requests
     */
    public Integer count(Unit unit) {
        return count(unit, null, null);
    }
    /**
     * Retrieve the number of requests found in the database with a certain datespan
     * 
     * @param span
     * @return the number of requests
     */
    public Integer count(DateSpan span) {
        return count(null, span, null);
    }
    /**
     * Retrieve the number of requests found in the database with a certain unit and datespan
     * 
     * @param unit
     * @param span
     * @return the number of requests
     */
    public Integer count(Unit unit, DateSpan span) {
        return count(unit, span, null);
    }
    /**
     * Retrieve the number of requests found in the database with a certain datespan and category
     * 
     * @param span
     * @param category
     * @return the number of requests
     */
    public Integer count(DateSpan span, Category category) {
        return count(null, span, category);
    }

    /**
     * Retrieve the number of requests found in the database for the given unit
     * 
     * @param unit
     *            the unit to search for, or null if all requests should be counted
     * @return the number of requests found
     */
    public Integer count(Unit unit, DateSpan span, Category category) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Request.class);
        if (unit != null) {
            criteria.add(eq(CRITERIA_UNIT, unit));
        }
        if (span != null) {
            criteria.add(ge(CRITERIA_CREATED, span.getFromDate()));
            criteria.add(le(CRITERIA_CREATED,  span.getToDate()));
        }
        if (category != null) {
            criteria.add(eq(CRITERIA_CATEGORY, category));
        }

        return ((Number)criteria.setProjection(rowCount()).uniqueResult()).intValue();
    }

	/**
	 * Expire any request that is older than the provided amount of days
	 * @param days The maximum number of days 
	 * @return the number of ads removed
	 */
	public int expireOldRequests(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -days);
		Date maxDate = cal.getTime();
		Query query = getSessionFactory().getCurrentSession().createQuery("UPDATE Request SET status=:status WHERE created <= :maxDate AND status=:publishStatus");
		query.setParameter("status", Status.EXPIRED);
		query.setParameter("publishStatus", Status.PUBLISHED);
		query.setDate("maxDate", maxDate);
		return query.executeUpdate();
	}
	/**
	 * Replace all photos for all requests in a list
	 * @see RequestDAO#replaceProxiedPhoto(Request)
	 * @param list The list of requests
	 * @return The list with requests that has been updated with replaced photos
	 */
	private List<Request> replaceProxiedPhotos(List<Request> list) {
		for (Request request : list) {
			replaceProxiedPhoto(request);
		}
		return list;
	}
	
	/**
	 * Replace all photo objects attached to this request with new, non-proxied objects. This is needed in order to 
	 * avoid errors regarding missing hibernate session when the photo objects are accessed in the JSP pages. We do not need the 
	 * proxied objects because we are not interested of the image byte content but only the id's of the images, in order to construct
	 * photo serving resource URL's. 
	 * @param request The request for which all photos should be replaced
	 * @return The request with replaced photos
	 */
	private Request replaceProxiedPhoto(Request request) {
		List<Photo> photos = request.getPhotos();
		List<Photo> newPhotos = new ArrayList<Photo>();
		for (Photo photo : photos) {
			Photo newPhoto = new Photo();
			newPhoto.setId(photo.getId());
			newPhotos.add(newPhoto);
		}
		request.setPhotos(newPhotos);
		return request;
	}	

	/**
	 * Counts requests by category
	 * 
	 * @param category
	 * @return
	 */
	public Integer countByCategory(Category category) {
		String hql = "select count(*) from Request req"
            + " where req.category.id = :id";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql).setParameter("id", category.getId());
		return ((Number) query.uniqueResult()).intValue();
	}

	/**
     * Finds all requests that have expired and are to be unpublished.
     * 
     * @param days
     * @return list of requests that have expired.
     */
    @SuppressWarnings("unchecked")
    public List<Request> findRequestsToExpire(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        Date maxDate = cal.getTime();
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Request.class);
        criteria.add(eq("status", Status.PUBLISHED));
        criteria.add(le("publishDate", maxDate));
        return criteria.list();
    }
	
}
