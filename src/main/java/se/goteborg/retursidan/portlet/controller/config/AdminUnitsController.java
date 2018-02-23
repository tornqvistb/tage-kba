package se.goteborg.retursidan.portlet.controller.config;

import java.util.List;
import java.util.logging.Logger;

import javax.portlet.ActionResponse;
import javax.validation.Valid;

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

import se.goteborg.retursidan.dao.AdvertisementDAO;
import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.portlet.validation.UnitValidator;

@Controller
@RequestMapping("EDIT")
public class AdminUnitsController extends BaseController {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@ModelAttribute("newUnit")
	public Unit getNewUnit() {
		return new Unit();
	}

	@ModelAttribute("removeUnit")
	public Unit getRemoveUnit() {
		return new Unit();
	}

	@Autowired
	private UnitValidator unitValidator;
	@Autowired
	private AdvertisementDAO advertisementDAO;
	
	@InitBinder("newUnit")  
	protected void initBinder(WebDataBinder binder) {  
		binder.setValidator(unitValidator);  
	}

	@RenderMapping(params="page=adminUnits")
	public String changeUnits(Model model) {
		List<Unit> units = modelService.getUnits();
		model.addAttribute("units", units);
		return "config/admin_units";
	}
	
	@ActionMapping("saveUnit")
	public void saveUnit(@Valid @ModelAttribute("newUnit") Unit unit, BindingResult bindingResult, ActionResponse response, Model model) {
	    logger.finest("entering saveUnit");
		if (!bindingResult.hasErrors()) {
			modelService.addUnit(unit);
			
			// clear the newUnit model attribute so that the input box is empty, and set the removeUnit
			// to the recently created unit so that it is selected in the list.
			model.addAttribute("newUnit", new Unit());
			model.addAttribute("removeUnit", unit);
		}
		response.setRenderParameter("page", "adminUnits");
	}
	
	@ActionMapping("removeUnit")
	public void removeUnit(@ModelAttribute("removeUnit") Unit unit, ActionResponse response, Model model) {
	    logger.finest("entering removeUnit");
	    // Check if there are any request or ads for this unit
	    Integer count = modelService.countAdsByUnit(unit) + modelService.countRequestsByUnit(unit);
	    if (count == 0) {
	    	modelService.removeUnit(unit);
	    } else {
	    	model.addAttribute("errorMessage", "Enheten kan inte tas bort då det finns annonser/efterlysningar som är registrerade på denna. Ta bort dessa först.");
	    }
		response.setRenderParameter("page", "adminUnits");
	}

}
