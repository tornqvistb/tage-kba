package se.goteborg.retursidan.portlet.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.PortletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Person;
import se.goteborg.retursidan.service.UserService;

/**
 * Controller handling viewing of ads
 *
 */


@Controller
@RequestMapping({"VIEW", "CONFIG"})
public class ViewAdController extends BaseController {
	private static Logger logger = Logger.getLogger(ViewAdController.class.getName());
	
	@ModelAttribute("advertisement")
	public Advertisement loadAdvertisement(@RequestParam(value="advertisementId", required=false) Integer advertisementId) {
		if (advertisementId != null) {
			logger.log(Level.FINEST, "loading advertisement id=" + advertisementId);
			Advertisement advertisement = modelService.getAdvertisement(advertisementId);
			logger.log(Level.FINEST, "advertisement=" + advertisement);			
			return advertisement;
		}
		return null;
	}

	@ModelAttribute("adCreator")
	public Person loadAdCreator(@RequestParam(value="advertisementId", required=false) Integer advertisementId, PortletRequest request) {
		if (advertisementId != null) {
			Advertisement advertisement = modelService.getAdvertisement(advertisementId);
			Person adCreator = userService.getUserByUserID(request, advertisement.getCreatorUid());
			return adCreator;
		}
		return null;
	}
	
	
	@RenderMapping(params="page=viewAd")
	public String viewAd(@RequestParam(value="previousPage", required=false) String previousPage, 
						@RequestParam(value="pageIdx", required=false) String pageIdx, Model model) {
		logger.log(Level.FINEST, "viewAd start");
		if (previousPage != null) {
			model.addAttribute("previousPage", previousPage);
		}
		if (pageIdx != null) {
			model.addAttribute("pageIdx", pageIdx);
		}
		return "view_ad";
	}
}
