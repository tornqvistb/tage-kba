package se.goteborg.retursidan.portlet.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.service.ModelService;

/**
 * Controller handling AJAX resource requests in the JSP pages, currently just
 * used for retrieving sub categories which is called when the user selects a
 * top category in a select box.
 *
 */
@Controller
@RequestMapping("VIEW")
public class AjaxDataController {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private ModelService modelService;
	
	@ResourceMapping("subCategories")
	public void getSubCategories(ResourceResponse response, @RequestParam("parent") int id) {
		try {
			List<Category> subCategories = modelService.getSubCategories(id);
			StringBuffer sb = new StringBuffer();
			for (Category category : subCategories) {
				sb.append("<option value=\"").append(category.getId()).append("\">").append(category.getTitle()).append("</option>");
			}
			response.getWriter().println(sb.toString());
		} catch (IOException e) {
			logger.log(Level.WARNING, "Could not write out sub categories to response stream", e);
		}
	}
}
