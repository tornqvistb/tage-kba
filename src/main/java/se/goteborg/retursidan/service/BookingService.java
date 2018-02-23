package se.goteborg.retursidan.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.goteborg.retursidan.dao.AdvertisementDAO;
import se.goteborg.retursidan.exceptions.AdvertisementAlreadyBookedException;
import se.goteborg.retursidan.exceptions.AdvertisementBookingFailedException;
import se.goteborg.retursidan.exceptions.AdvertisementExpiredException;
import se.goteborg.retursidan.exceptions.AdvertisementNotFoundException;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Person;
import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.model.form.MailComposition;
import se.goteborg.retursidan.model.form.Texts;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BookingService {
	Logger logger = Logger.getLogger(BookingService.class.getName());
	
	@Autowired
	private AdvertisementDAO advertisementDAO;

	@Autowired
	private MailService mailService;
		
	public void bookAdvertisement(Integer advertisementId, Person contact, Texts texts, Config config, String adLink) throws AdvertisementBookingFailedException {
		logger.log(Level.FINE, "Trying to book advertisement id=" + advertisementId + " for " + contact);
	
		Advertisement advertisement = advertisementDAO.findById(advertisementId);
		
		if (advertisement == null) {
			logger.log(Level.WARNING, "Could not find advertisement with id=" + advertisementId + " to book.");
			throw new AdvertisementNotFoundException(advertisementId);
		}
					
		// make sure only one thread can book at a time
		synchronized(this) {
			if (Advertisement.Status.BOOKED.equals(advertisement.getStatus())) {
				logger.log(Level.WARNING, "Advertisement with id=" + advertisementId + " is already booked.");
				throw new AdvertisementAlreadyBookedException(advertisementId);
			} else if (Advertisement.Status.EXPIRED.equals(advertisement.getStatus())) {
				logger.log(Level.WARNING, "Advertisement with id=" + advertisementId + " is expired and can not be booked.");
				throw new AdvertisementExpiredException(advertisementId);
			}
			advertisement.setBooker(contact);
			advertisement.setStatus(Advertisement.Status.BOOKED);
			advertisementDAO.merge(advertisement);
			
			MailComposition composition = new MailComposition(null,
					texts.getMailSenderAddress(),
					null,	"",
					texts.getMailSubject(),
					advertisement.getTitle(),
					contact.getName(),
					contact.getPhone(),
					contact.getEmail(),
					advertisement.getContact().getName(),
					advertisement.getContact().getPhone(),
					advertisement.getContact().getEmail(),
					"",	"",	"",	"",	"",	"", "",
					adLink,
					texts.getMailBody(),
					config.getRulesUrl(),
					config.getLogoUrl());

			if (!advertisement.getPhotos().isEmpty()) {
				composition.setPhoto(advertisement.getPhotos().get(0));
			}
			
			String bookerMail = contact.getEmail();
			String advertiserMail = advertisement.getContact().getEmail();
						
			composition.setReveiverAdress(new String[] {bookerMail});
			composition.setReplyToAdress(advertiserMail);
			mailService.composeAndSendMail(composition);
			composition.setReveiverAdress(new String[] {advertiserMail});
			composition.setReplyToAdress(bookerMail);
			mailService.composeAndSendMail(composition);			
						
		}
	}

}
