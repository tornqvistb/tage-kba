package se.goteborg.retursidan.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.goteborg.retursidan.dao.AdvertisementDAO;
import se.goteborg.retursidan.dao.RequestDAO;
import se.goteborg.retursidan.model.entity.Advertisement.Status;
import se.goteborg.retursidan.model.entity.Unit;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class StatisticsService {
	
	@Autowired
	private AdvertisementDAO advertisementDAO;

	@Autowired
	private RequestDAO requestDAO;

	public Integer getTotalNumberOfAds() {
		return advertisementDAO.count();
	}

	public Integer getTotalNumberOfRequests() {
		return requestDAO.count();
	}

	public Integer getTotalAdsForUnit(Unit unit) {
		return advertisementDAO.count(unit);
	}

	public Integer getTotalRequestsForUnit(Unit unit) {
		return requestDAO.count(unit);
	}

	public Integer getTotalNumberOfBookedAds() {
		return advertisementDAO.count(Status.BOOKED);
	}

	public Integer getBookedAdsForUnit(Unit unit) {
		return advertisementDAO.count(Status.BOOKED, unit);
	}

	public Integer getTotalNumberOfExpiredAds() {
		return advertisementDAO.count(Status.EXPIRED);
	}

}