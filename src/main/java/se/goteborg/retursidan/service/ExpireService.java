package se.goteborg.retursidan.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.goteborg.retursidan.dao.AdvertisementDAO;
import se.goteborg.retursidan.dao.RequestDAO;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ExpireService {
	private static Logger logger = Logger.getLogger(ExpireService.class.getName());
	
	@Autowired
	AdvertisementDAO advertisementDAO;
	
	@Autowired
	RequestDAO requestDAO;
	
	public void expireAds(int days) {
		logger.fine("Expiring ads older than " + days + " days.");
		int count = advertisementDAO.expireOldAds(days);
		logger.fine(count + " ads were expired.");
	}

	public void expireRequests(int days) {
		logger.fine("Expiring requests older than " + days + " days.");
		int count = requestDAO.expireOldRequests(days);
		logger.fine(count + " requests were expired.");
	}
}
