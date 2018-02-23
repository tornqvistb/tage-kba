package se.goteborg.retursidan.portlet.controller;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.util.DateHelper;

/**
 * Controller handling republish of ads
 *
 */
@Controller
@RequestMapping("VIEW")
public class RepublishAdController extends BaseController {
	private static Logger logger = Logger.getLogger(RepublishAdController.class.getName());

	@ModelAttribute("advertisement")
	public Advertisement getAdvertisement(@RequestParam(value="advertisementId", required=false) Integer advertisementId, PortletRequest request) {
		return modelService.getAdvertisement(advertisementId);
	}
	
	@RenderMapping(params="page=republishAd")
	public String republishAd() {
		return "republish_ad";
	}

	@ActionMapping("performRepublishing")
	public void performRepublishing(@ModelAttribute("advertisement") Advertisement advertisement, ActionRequest request, ActionResponse response) {
		logger.log(Level.FINER, "Republishing ad with id=" + advertisement.getId());
		advertisement.setStatus(Advertisement.Status.PUBLISHED);
		advertisement.setPublishDate(DateHelper.getCurrentDate());
		modelService.updateAd(advertisement);
	}
}
