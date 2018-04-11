package se.goteborg.retursidan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.goteborg.retursidan.dao.AdvertisementDAO;
import se.goteborg.retursidan.dao.CategoryDAO;
import se.goteborg.retursidan.dao.PersonDAO;
import se.goteborg.retursidan.dao.PhotoDAO;
import se.goteborg.retursidan.dao.RequestDAO;
import se.goteborg.retursidan.dao.UnitDAO;
import se.goteborg.retursidan.model.PagedList;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Advertisement.Status;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Person;
import se.goteborg.retursidan.model.entity.Photo;
import se.goteborg.retursidan.model.entity.Request;
import se.goteborg.retursidan.model.entity.Unit;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ModelService {
	@Autowired
	private AdvertisementDAO advertisementDAO;		
	
	@Autowired
	private RequestDAO requestDAO;	
	
	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private UnitDAO unitDAO;	

	@Autowired
	private PersonDAO personDAO;		

	@Autowired
	private PhotoDAO photoDAO;

	
	public void addCategory(Category category) {
		if (category.getParent() != null && category.getParent().getId() == -1) {
			category.setParent(null);
		}
		categoryDAO.add(category);
	}
	public List<Category> getCategories() {
		return categoryDAO.findAll();
	}
	public List<Category> getTopCategories() {
		return categoryDAO.findTopCategories();
	}
	public List<Category> getSubCategories(int id) {
		return categoryDAO.findAllSubCategories(id);
	}

	public void addUnit(Unit unit) {
		unitDAO.add(unit);
	}
	public List<Unit> getUnits() {
		return unitDAO.findAll();
	}

	public void addPerson(Person person) {
		personDAO.add(person);
	}
	
	public void addPhoto(Photo photo) {
		photoDAO.add(photo);
	}
	public Photo getPhoto(int id) {
		return photoDAO.findById(id);
	}
		
	public void removePhoto(Photo photo) {
		photoDAO.delete(photo);
	}
	public List<Photo> getAllPhotos() {
		return photoDAO.findAll();
	}	
	
	public Category getCategory(int id) {
		return categoryDAO.findById(id);
	}
	
	
	public void addAdvertisement(Advertisement advertisement) {
		advertisementDAO.add(advertisement);
	}
	public Advertisement getAdvertisement(int id) {
		return advertisementDAO.findById(id);
	}
	public PagedList<Advertisement> getAllFilteredAdvertisements(Advertisement.Status status, Category topCategory, Category category, Unit unit, String searchString, int page, int pageSize) {
		return advertisementDAO.find(null, status, topCategory, category, unit, null, searchString, false, page, pageSize);
	}
	public PagedList<Advertisement> getAllAdvertisements(Advertisement.Status status, int page, int pageSize) {
		return advertisementDAO.find(null, status, null, null, null, null, null, true, page, pageSize);
	}
	public PagedList<Advertisement> getAllAdvertisementsForUid(String uid, int page, int pageSize) {
		return advertisementDAO.find(uid, null, null, null, null, null, null, false, page, pageSize);
	}
	public PagedList<Advertisement> getAllBookingsForUid(String uid, int page, int pageSize) {
		return advertisementDAO.find(null, null, null, null, null, uid, null, false, page, pageSize);
	}
	public PagedList<Advertisement> getAllFilteredAdvertisementsForUid(String uid, Category topCategory, Category category, Unit unit, Status status, int page, int pageSize) {
		return advertisementDAO.find(uid, status, topCategory, category, unit, null, null, false, page, pageSize);
	}
	public PagedList<Advertisement> getAllFilteredBookingsForUid(String uid, Category topCategory, Category category, int page, int pageSize) {
		return advertisementDAO.find(null, null, topCategory, category, null, uid, null, false, page, pageSize);
	}
	
	public void removeAdvertisement(Advertisement advertisement) {
		advertisementDAO.delete(advertisement);
	}
	
	public void addRequest(Request request) {
		requestDAO.add(request);
	}
	public Request getRequest(int id) {
		return requestDAO.findById(id);
	}
	
	public int saveRequest(Request request) {
		return requestDAO.add(request);
	}
	public PagedList<Request> getAllRequests(int page, int pageSize) {
		return requestDAO.find(null, page, pageSize);
	}

	
	public int saveAd(Advertisement ad) {
		return advertisementDAO.add(ad);
	}

	
	public void updateAd(Advertisement ad) {
		advertisementDAO.update(ad);
	}

	
	public void updateRequest(Request request) {
		requestDAO.update(request);
	}

	public Person getPerson(String uid) {
		return personDAO.findByUid(uid);
	}
	public void removeRequest(Request request) {
		requestDAO.delete(request);
	}
	public List<Request> getAllRequests(Request.Status status) {
		return requestDAO.findAll(status);
	}
	public List<Request> getAllRequestsForCreatorUid(Request.Status status, String userId) {
		return requestDAO.findByCreatorUid(status, userId);
	}
	public void removeUnit(Unit unit) {
		unitDAO.delete(unit);
	}
	public void removeCategory(Category category) {
		categoryDAO.delete(category);
	}
	public Integer countAdsByUnit(Unit unit) {
		return advertisementDAO.count(unit);
	}
	public Integer countRequestsByUnit(Unit unit) {
		return requestDAO.count(unit);
	}
	public Integer countAdsByCategory(Category category) {
		return advertisementDAO.countByCategory(category);
	}
	public Integer countRequestsByCategory(Category category) {
		return requestDAO.countByCategory(category);
	}


}
