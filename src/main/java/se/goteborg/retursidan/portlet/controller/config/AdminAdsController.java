package se.goteborg.retursidan.portlet.controller.config;

import java.util.Date;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.model.PagedList;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Advertisement.Status;
import se.goteborg.retursidan.portlet.controller.PhotoBaseController;
import se.goteborg.retursidan.util.DateHelper;

@Controller
@RequestMapping("EDIT")
public class AdminAdsController extends PhotoBaseController {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@RenderMapping(params="page=adminAds")
	public String adminAds( @RequestParam(value="pageIdx", required=false) Integer pageIdx, RenderRequest request, RenderResponse response, Model model) {
	    logger.finest("params page = adminAds");
	    logger.finest("params pageIdx = " + pageIdx);
	    logger.finest("page size = " + getConfig(request).getPageSizeInt());
	    PagedList<Advertisement> pagedListTemp = modelService.getAllAdvertisements(null, (pageIdx != null) ? pageIdx : 1, getConfig(request).getPageSizeInt());
	    logger.finest("number of pages = " + pagedListTemp.getNumberOfPages());
	    if (pageIdx != null && pageIdx > pagedListTemp.getNumberOfPages()) {
	    	pageIdx = (int)pagedListTemp.getNumberOfPages();
	    	if(pageIdx == 0) pageIdx = 1;
	    }
		PagedList<Advertisement> pagedList = modelService.getAllAdvertisements(null, (pageIdx != null) ? pageIdx : 1, getConfig(request).getPageSizeInt());
		model.addAttribute("ads", pagedList);
		return "config/admin_ads";
	}	
		
	@ActionMapping("selectAdsPage")
	public void selectAds(@RequestParam(value="pageIdx", required=false) String pageIdx, Model model, ActionRequest request, ActionResponse response) {
	    logger.finest("params page = adminAds");
		response.setRenderParameter("page", "adminAds");
		response.setRenderParameter("pageIdx", pageIdx);
	}
	
	@ActionMapping("removeAd")
	public void removeAd(@RequestParam(value="advertisementId", required=true) Integer advertisementId, ActionResponse response) {
	    logger.finest("params page = removeAd");
		Advertisement ad = modelService.getAdvertisement(advertisementId);
		modelService.removeAdvertisement(ad);
		response.setRenderParameter("page", "adminAds");
	}
	
	@ActionMapping("expireAd")
	public void expireAd(@RequestParam(value="advertisementId", required=true) Integer advertisementId, ActionResponse response) {
	    logger.finest("params page = expireAd");
		Advertisement ad = modelService.getAdvertisement(advertisementId);
		ad.setStatus(Status.EXPIRED);
		modelService.updateAd(ad);
		response.setRenderParameter("page", "adminAds");
	}
	@ActionMapping("republishAd")
	public void republishAd(@RequestParam(value="pageIdx", required=false) Integer pageIdx, 
							@RequestParam(value="advertisementId", required=true) Integer advertisementId, ActionResponse response) {
	    logger.finest("params page = republishAd");
		Advertisement ad = modelService.getAdvertisement(advertisementId);
		ad.setStatus(Status.PUBLISHED);
		ad.setPublishDate(DateHelper.getCurrentDate());
		modelService.updateAd(ad);
		response.setRenderParameter("page", "adminAds");
		response.setRenderParameter("pageIdx", String.valueOf(pageIdx));
	}		
	
}
