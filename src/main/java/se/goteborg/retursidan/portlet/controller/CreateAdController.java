package se.goteborg.retursidan.portlet.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.model.form.MailComposition;
import se.goteborg.retursidan.model.form.Texts;
import se.goteborg.retursidan.portlet.binding.PhotoListPropertyEditor;
import se.goteborg.retursidan.portlet.validation.AdValidator;
import se.goteborg.retursidan.service.MailService;
import se.goteborg.retursidan.service.UserService;
import se.goteborg.retursidan.util.CreateURLHelper;
import se.goteborg.retursidan.util.DateHelper;
import se.goteborg.retursidan.util.UnitHelper;

/**
 * Controller handling creating ads
 *
 */
@Controller
@RequestMapping("VIEW")
public class CreateAdController extends BaseController {
	private static Logger logger = Logger.getLogger(CreateAdController.class.getName());	
	
	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;
	
	@InitBinder("advertisement")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new AdValidator());
		binder.registerCustomEditor(List.class, "photos", new PhotoListPropertyEditor());	
	}

	@ModelAttribute("advertisement")
	public Advertisement createAdvertisement() {
		return new Advertisement();
	}
		
	@RenderMapping(params="page=createAdConfirm")
	public String render(Model model) {		
		return "create_ad_confirm";
	}

	
	@RenderMapping(params="page=createAd")
	public String createAd(Model model, PortletRequest request) {
		// work-around for Spring form bug/misbehavior, errors are not persisted in model 
		Advertisement advertisement;
		if(model.containsAttribute("advertisement")) {
			advertisement = (Advertisement)model.asMap().get("advertisement");
		} else {
			advertisement = new Advertisement();
		}
		advertisement.setContact(getCurrentUser(request));
		advertisement.setUnit(userService.getUsersUnit(request));
		
		List<Unit> units = modelService.getUnits();
		units.add(0, UnitHelper.getEmptyUnit());
		model.addAttribute("units", units);
	
		List<Category> topCategories = modelService.getTopCategories();
		model.addAttribute("topCategories", topCategories);

		if (advertisement.getTopCategory() != null && advertisement.getTopCategory().getId() > 0) {
			List<Category> subCategories = modelService.getSubCategories(advertisement.getTopCategory().getId());
			model.addAttribute("subCategories", subCategories);		
		}
			
		// Send mail
		return "create_ad";
	}

	private Date getExpireDate(String dateStr) {
		try {
			return DateHelper.getDateFromString(dateStr);
		} catch (ParseException e) {
			logger.log(Level.WARNING, "Could not parse date: " + dateStr);
			return null;
		}
	}
	
	@ActionMapping("saveAd")
	public void saveAd(@Valid @ModelAttribute("advertisement") Advertisement advertisement, BindingResult bindingResult, ActionRequest request, ActionResponse response, Model model) {
		advertisement.setCreatorUid(getUserId(request));
		if (!bindingResult.hasErrors()) {
			logger.log(Level.FINER, "Saving advertisement: " + advertisement);
			advertisement.setStatus(Advertisement.Status.PUBLISHED);
			advertisement.setPublishDate(DateHelper.getCurrentDate());
			advertisement.setOriginalCount(advertisement.getCount());
			if (Advertisement.ExpireType.FIXED_DATE.equals(advertisement.getExpireType())) {
				advertisement.setExpireDate(getExpireDate(advertisement.getExpireDateString()));
			}
			int id = modelService.saveAd(advertisement);
			logger.log(Level.FINE, "Advertisement saved with id = " + id);
			
			// Send mail to the person who created the ad
			advertisement = modelService.getAdvertisement(id);
			Texts texts = getTexts(request);
			Config config = getConfig(request);
			String adLink = CreateURLHelper.createHttpBaseURL(request) + getConfig(request).getPocURIBase() +  ADVERTISEMENT_URI + id;
			MailComposition composition = new MailComposition(null,
					texts.getMailSenderAddressNewAd(),
					new String[] {advertisement.getContact().getEmail()},	
					"",
					texts.getMailSubjectNewAd(),
					advertisement.getTitle(),
					"",	"",	"",
					advertisement.getContact().getName(),
					advertisement.getContact().getPhone(),
					advertisement.getContact().getEmail(),
					"",	"",	"",	"",	"",	"", "",
					adLink,
					texts.getMailBodyNewAd(),
					config.getRulesUrl(),
					config.getLogoUrl(),
					0);

			if (!advertisement.getPhotos().isEmpty()) {				
				composition.setPhoto(modelService.getPhoto(advertisement.getPhotos().get(0).getId()));
			}
			
			mailService.composeAndSendMail(composition);

			// reload the ad into the model to get all data populated
			model.addAttribute("advertisement", advertisement);
			response.setRenderParameter("page", "viewAd");
		} else {
			response.setRenderParameter("page", "createAd");			
		}
	}	
}
