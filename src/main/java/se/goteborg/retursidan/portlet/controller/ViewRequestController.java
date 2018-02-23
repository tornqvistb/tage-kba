package se.goteborg.retursidan.portlet.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Person;
import se.goteborg.retursidan.model.entity.Request;

/**
 * Controller handling viewing requests
 *
 */
@Controller
@RequestMapping({"VIEW", "CONFIG"})
public class ViewRequestController extends BaseController {
	private static Logger logger = Logger.getLogger(ViewRequestController.class.getName());
	
	@ModelAttribute("request")
	public Request loadRequest(@RequestParam(value="requestId", required=false) Integer requestId, PortletRequest portletRequest) {
		return null;
	}

	@ModelAttribute("requestCreator")
	public Person loadAdCreator(@RequestParam(value="requestId", required=false) Integer requestId, PortletRequest portletRequest) {
		if (requestId != null) {
			Request request = modelService.getRequest(requestId);
			Person requestCreator = userService.getUserByUserID(portletRequest, request.getCreatorUid());
			return requestCreator;
		}
		return null;
	}
	
	@RenderMapping(params="externalPage=viewRequest")
	public String viewRequestFromOther(RenderRequest portletRequest, Model model) {
		// read public render parameter
		Integer externalRequestId = null;
		if (portletRequest.getParameter("externalRequestId") != null) {
			externalRequestId = new Integer(portletRequest.getParameter("externalRequestId"));
		}
		return viewRequest(externalRequestId, null, model);		
	}

	@RenderMapping(params="page=viewRequest")
	public String viewRequest(@RequestParam(value="requestId", required=false) Integer requestId, @RequestParam(value="previousPage", required=false) String previousPage, Model model) {
		if (requestId != null) {
			logger.log(Level.FINEST, "loading request id=" + requestId);
			Request request = modelService.getRequest(requestId);
			logger.log(Level.FINEST, "request=" + request);
			model.addAttribute("request", request);
		}
		if (previousPage != null) {
			model.addAttribute("previousPage", previousPage);
		}
		return "view_request";
	}
}