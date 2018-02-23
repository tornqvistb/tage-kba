package se.goteborg.retursidan.portlet.controller.config;

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
import se.goteborg.retursidan.model.entity.Request;
import se.goteborg.retursidan.model.entity.Request.Status;
import se.goteborg.retursidan.portlet.controller.PhotoBaseController;

@Controller
@RequestMapping("EDIT")
public class AdminRequestsController extends PhotoBaseController {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@RenderMapping(params="page=adminRequests")
	public String adminAds(@RequestParam(value="pageIdx", required=false) Integer pageIdx, RenderRequest request, RenderResponse response, Model model) {
	    logger.finest("entering adminRequests");
		PagedList<Request> pagedList = modelService.getAllRequests((pageIdx != null) ? pageIdx : 1, getConfig(request).getPageSizeInt());
		model.addAttribute("requests", pagedList);
		return "config/admin_requests";
	}
	
	@ActionMapping("selectRequestsPage")
	public void selectAds(@RequestParam(value="pageIdx", required=false) String pageIdx, Model model, ActionRequest request, ActionResponse response) {
	    logger.finest("entering selectRequestsPage");
		response.setRenderParameter("page", "adminRequests");
		response.setRenderParameter("pageIdx", pageIdx);
	}
	
	@ActionMapping("removeRequest")
	public void removeAd(@RequestParam(value="requestId", required=true) Integer requestId, ActionResponse response) {
	    logger.finest("entering removeRequest");
	    Request request = modelService.getRequest(requestId);
		modelService.removeRequest(request);
		response.setRenderParameter("page", "adminRequests");
	}
	
	@ActionMapping("expireRequest")
	public void expireAd(@RequestParam(value="requestId", required=true) Integer requestId, ActionResponse response) {
	    logger.finest("entering expireRequest");
		Request request = modelService.getRequest(requestId);
		request.setStatus(Status.EXPIRED);
		modelService.updateRequest(request);
		response.setRenderParameter("page", "adminRequests");
	}
	@ActionMapping("republishRequest")
	public void republishAd(@RequestParam(value="requestId", required=true) Integer requestId, ActionResponse response) {
	    logger.finest("entering republishRequest");
		Request request = modelService.getRequest(requestId);
		request.setStatus(Status.PUBLISHED);
		modelService.updateRequest(request);
		response.setRenderParameter("page", "adminRequests");
	}		
	
}
