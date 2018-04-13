package se.goteborg.retursidan.service;

import static se.goteborg.retursidan.portlet.controller.BaseController.ADVERTISEMENT_URI;
import static se.goteborg.retursidan.portlet.controller.BaseController.REQUEST_URI;

import java.util.List;
import java.util.logging.Logger;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.goteborg.retursidan.dao.AdvertisementDAO;
import se.goteborg.retursidan.dao.RequestDAO;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Advertisement.Status;
import se.goteborg.retursidan.model.entity.Request;
import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.model.form.MailComposition;
import se.goteborg.retursidan.model.form.Texts;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.util.CreateURLHelper;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ExpireService {
	private static Logger logger = Logger.getLogger(ExpireService.class.getName());
	
	@Autowired
	AdvertisementDAO advertisementDAO;
	
	@Autowired
	RequestDAO requestDAO;
	
    @Autowired
    MailService mailService;

    @Autowired
    ModelService modelService;
	
    public void expireAds(int days, Texts texts, RenderRequest request) {
        logger.fine("Expiring ads older than " + days + " days.");
        List<Advertisement> adsToExpire = advertisementDAO.findAdsToExpire(days);
        for (Advertisement ad : adsToExpire) {
            ad.setStatus(Status.EXPIRED);
            advertisementDAO.saveOrUpdate(ad);
            MailComposition mail = new MailComposition();
        	String adLink = CreateURLHelper.createHttpBaseURL(request) + getConfig(request).getPocURIBase() +  ADVERTISEMENT_URI + ad.getId();
        	mail.setLink(adLink);
            mail.setAdvertiserName(ad.getContact().getName());
            mail.setAdvertiserMail(ad.getContact().getEmail());
            mail.setAdvertiserPhone(ad.getContact().getPhone());
            mail.setId(ad.getId());
            mail.setTitle(ad.getTitle());
            mail.setPublishedDays(days);
            mail.setMailHeader(texts.getMailSubjectExpiredAd());
            mail.setSenderAdress(texts.getMailSenderAddressExpiredAd());
            mail.setReveiverAdress(new String[] { ad.getContact().getEmail() });
            mail.setConfigMailBody(texts.getMailBodyExpiredAd());
            mail.setMessage("");
			if (!ad.getPhotos().isEmpty()) {				
				mail.setPhoto(modelService.getPhoto(ad.getPhotos().get(0).getId()));
			}

            mailService.composeAndSendMail(mail);
        }

        int count = adsToExpire.size();
        logger.fine(count + " ads were expired.");
    }

    public void expireRequests(int days, Texts texts, RenderRequest request) {
        logger.fine("Expiring ads older than " + days + " days.");
        List<Request> requestsToExpire = requestDAO.findRequestsToExpire(days);
        for (Request req : requestsToExpire) {
            req.setStatus(Request.Status.EXPIRED);
            requestDAO.saveOrUpdate(req);
            MailComposition mail = new MailComposition();
            String adLink = CreateURLHelper.createHttpBaseURL(request) + getConfig(request).getPocURIBase() +  REQUEST_URI + req.getId();
            mail.setLink(adLink);
            mail.setAdvertiserName(req.getContact().getName());
            mail.setAdvertiserMail(req.getContact().getEmail());
            mail.setAdvertiserPhone(req.getContact().getPhone());
            mail.setId(req.getId());
            mail.setTitle(req.getTitle());
            mail.setPublishedDays(days);
            mail.setMailHeader(texts.getMailSubjectExpiredRequest());
            mail.setSenderAdress(texts.getMailSenderAddressExpiredRequest());
            mail.setReveiverAdress(new String[] { req.getContact().getEmail() });
            mail.setConfigMailBody(texts.getMailBodyExpiredRequest());
            if (!req.getPhotos().isEmpty()) {				
				mail.setPhoto(modelService.getPhoto(req.getPhotos().get(0).getId()));
			}
            mailService.composeAndSendMail(mail);
        }

        int count = requestsToExpire.size();
        logger.fine(count + " requests were expired.");
    }

	private Config getConfig(PortletRequest request) {
		PortletPreferences prefs = request.getPreferences();
		return new Config(prefs);
	}

    
}
