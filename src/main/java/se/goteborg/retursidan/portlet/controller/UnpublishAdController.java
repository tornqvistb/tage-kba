package se.goteborg.retursidan.portlet.controller;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Advertisement.Status;

/**
 * Controller handling changing ads
 *
 */
@Controller
@RequestMapping("VIEW")
public class UnpublishAdController extends BaseController {
	private static Logger logger = Logger.getLogger(UnpublishAdController.class.getName());
	
	@ModelAttribute("advertisement")
	public Advertisement getAdvertisement(@RequestParam(value="advertisementId", required=false) Integer advertisementId, PortletRequest request) {
		return modelService.getAdvertisement(advertisementId);
	}		
	
	@RenderMapping(params="page=unpublishAdFinished")
	public String unpublishAdFinished() {
		return "unpublish_ad_finished";
	}
	
	@RenderMapping(params="page=unpublishAd")
	public String unpublishAd() {
		return "unpublish_ad";
	}
	
	@ActionMapping("performUnpublishing")
	public void unpublishAd(@RequestParam(value="advertisementId") Integer advertisementId, Model model, ActionResponse response) {
		Advertisement advertisement = modelService.getAdvertisement(advertisementId);
		advertisement.setStatus(Status.UNPUBLISHED);
		logger.log(Level.FINER, "Updating advertisement: " + advertisement);		
		modelService.updateAd(advertisement);
		logger.log(Level.FINE, "Advertisement updated");
		// reload the ad into the model to get all data populated
		model.addAttribute("advertisement", modelService.getAdvertisement(advertisement.getId()));
		response.setRenderParameter("page", "unpublishAdFinished");
	}		

}
