package se.goteborg.retursidan.portlet.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.validation.Valid;

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

import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Request;
import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.portlet.binding.PhotoListPropertyEditor;
import se.goteborg.retursidan.portlet.validation.RequestValidator;

/**
 * Controller handling changing requests
 *
 */
@Controller
@RequestMapping("VIEW")
public class ChangeRequestController extends BaseController {
	private static Logger logger = Logger.getLogger(ChangeRequestController.class.getName());

	@InitBinder("request")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new RequestValidator());	
		binder.registerCustomEditor(List.class, "photos", new PhotoListPropertyEditor());
	}
	
	@RenderMapping(params="page=changeRequest")
	public String changeRequest(Model model) {
		// work-around for Spring form bug/misbehavior, errors are not persisted in model 
		Request request;
		if(model.containsAttribute("request")) {
			request = (Request)model.asMap().get("request");
		} else {
			request = new Request();
		}
		
		List<Unit> units = modelService.getUnits();
		model.addAttribute("units", units);
	
		List<Category> topCategories = modelService.getTopCategories();
		model.addAttribute("topCategories", topCategories);

		if (request.getTopCategory() != null && request.getTopCategory().getId() > 0) {
			List<Category> subCategories = modelService.getSubCategories(request.getTopCategory().getId());
			model.addAttribute("subCategories", subCategories);		
		}
			
		return "change_request";
	}

	@ActionMapping("loadRequest")
	public void loadRequest(@RequestParam(value="requestId") Integer requestId, Model model, ActionResponse response) {
		Request request = modelService.getRequest(requestId);
		request.setTopCategory(request.getCategory().getParent());
		model.addAttribute("request", request);
		response.setRenderParameter("page", "changeRequest");
	}
	
	@ActionMapping("updateRequest")
	public void updateRequest(@Valid @ModelAttribute("request") Request request, BindingResult bindingResult, ActionRequest portletRequest, ActionResponse portletResponse, Model model) {
		request.setCreatorUid(getUserId(portletRequest));
		if (!bindingResult.hasErrors()) {
			logger.log(Level.FINER, "Updating request: " + request);
			modelService.updateRequest(request);
			logger.log(Level.FINE, "Request updated: ");
			// reload the request into the model to get all data populated
			model.addAttribute("request", modelService.getRequest(request.getId()));
			portletResponse.setRenderParameter("page", "viewRequest");
		} else {
			portletResponse.setRenderParameter("page", "changeRequest");			
		}
	}		
}
