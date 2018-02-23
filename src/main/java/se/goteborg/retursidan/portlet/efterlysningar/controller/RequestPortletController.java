package se.goteborg.retursidan.portlet.efterlysningar.controller;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.util.PortalUtil;

import se.goteborg.retursidan.model.entity.Request.Status;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.service.ModelService;

/**
 * Controller for the Efterlysningar portlet
 *
 */
@Controller()
@RequestMapping("VIEW")
public class RequestPortletController extends BaseController{
	
	@Autowired
	private ModelService modelService;
	
	@RenderMapping
	public String render(RenderRequest request, RenderResponse response, Model model) {
		model.addAttribute("requests", modelService.getAllRequests(Status.PUBLISHED));		
		model.addAttribute("baseURI", getCurrentURL(request));
		return "list_requests";
	}
	
	@RenderMapping(params="page=listMyRequests")
	public String listMyRequests(@ModelAttribute("userId") String userId, RenderRequest request, RenderResponse response, Model model) {
		model.addAttribute("requests", modelService.getAllRequestsForCreatorUid(Status.PUBLISHED, userId));		
		model.addAttribute("baseURI", getCurrentURL(request));
		return "list_my_requests";		
	}

	@RenderMapping(params="page=listRequests")
	public String listRequests(RenderRequest request, RenderResponse response, Model model) {
		model.addAttribute("baseURI", getCurrentURL(request));
		return render(request, response, model);
	}

	private String getCurrentURL(RenderRequest request) {
		String url = PortalUtil.getCurrentURL(request);
		int queryPos = url.indexOf("?");
		if (queryPos > 0) {
			url = url.substring(0, queryPos);
		}
		return url;
	}
}
