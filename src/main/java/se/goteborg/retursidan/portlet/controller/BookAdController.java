package se.goteborg.retursidan.portlet.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.exceptions.AdvertisementException;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Person;
import se.goteborg.retursidan.model.form.Booking;
import se.goteborg.retursidan.portlet.validation.BookingValidator;
import se.goteborg.retursidan.service.BookingService;
import se.goteborg.retursidan.util.CreateURLHelper;

/**
 * Controller handling booking of ads
 *
 */
@Controller
@RequestMapping("VIEW")
public class BookAdController extends BaseController {
	private static Logger logger = Logger.getLogger(BookAdController.class.getName());

	@Autowired
	private BookingValidator bookingValidator;
	
	@Autowired
	private BookingService bookingService;
	
	@InitBinder("booking")  
	protected void initBinder(WebDataBinder binder) {  
		binder.setValidator(bookingValidator);  
	}
	
	@ModelAttribute("advertisement")
	public Advertisement getAdvertisement(@RequestParam(value="advertisementId", required=false) Integer advertisementId, PortletRequest request) {
		return modelService.getAdvertisement(advertisementId);
	}
	
	@ModelAttribute("booking")
	public Booking getBooking(@RequestParam(value="advertisementId", required=false) Integer advertisementId, PortletRequest request) {
		Booking booking = new Booking();
		Advertisement advertisement =  modelService.getAdvertisement(advertisementId);
		if (advertisement != null) {
			logger.log(Level.FINER, "getBooking has advertisment: " + advertisement.getId());
			booking.setAdvertisementId(advertisement.getId());
			booking.setAdvertisementTitle(advertisement.getTitle());
		} else {
			logger.log(Level.FINER, "getBooking did NOT find advertisment");
		}
		String uid = request.getUserPrincipal().getName();
		
		//Person contact = modelService.getPerson(uid);
		// Liferay: Get person from session instead
		Person contact = getCurrentUser(request);
	
		if (contact != null) {
			booking.setContact(contact);
		}
		return booking;
	}
	
	@RenderMapping(params="page=bookAd")
	public String bookAd(Model model) {

		return "book_ad";
	}

	@RenderMapping(params="page=bookAdFinished")
	public String bookAdFinished() {
		return "book_ad_finished";
	}

	@RenderMapping(params="page=bookAdError")
	public String bookAdError() {
		return "book_ad_error";
	}
		
	@ActionMapping("performBooking")
	public void performBooking(@Valid @ModelAttribute("booking") Booking booking, BindingResult bindingResult, Model model, ActionRequest request, ActionResponse response) {
		logger.log(Level.FINER, "Booking requested: " + booking);
		if (!bindingResult.hasErrors()) {
			booking.getContact().setUserId(getUserId(request));
			try {
				String adLink = CreateURLHelper.createHttpBaseURL(request) + getConfig(request).getPocURIBase() +  ADVERTISEMENT_URI + booking.getAdvertisementId();
				bookingService.bookAdvertisement(booking.getAdvertisementId(), booking.getContact(), getTexts(request), getConfig(request), adLink);
				response.setRenderParameter("page", "bookAdFinished");
			} catch(AdvertisementException e) {
				model.addAttribute("errorMessage", messageSource.getMessage(e.getMessageCode(), new Object[]{e.getAdvertisementId()}, request.getLocale()));
				response.setRenderParameter("page", "bookAdError");
			}
		} else {
			response.setRenderParameter("page", "bookAd");
		}
	}
}
