package se.goteborg.retursidan.portlet.synpunkter.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.model.form.ConfigFeedback;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.portlet.validation.ConfigFeedbackValidator;

@Controller
@RequestMapping("EDIT")
public class ChangeConfigFeedbackController extends BaseController {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private ConfigFeedbackValidator configValidator;
	
	@InitBinder("configFeedback")  
	protected void initBinder(WebDataBinder binder) {  
		binder.setValidator(configValidator);  
	}
	
	@ModelAttribute("configFeedback")
	public ConfigFeedback getConfigFeedback(PortletRequest request) {
		PortletPreferences prefs = request.getPreferences();
		return new ConfigFeedback(prefs);
	}
	
	@RenderMapping
	public String render() {
	    logger.finest("entering default render");
		return "config/change_config_feedback";
	}

	@ActionMapping("saveConfig")
	public void saveConfig(@Valid @ModelAttribute("configFeedback") ConfigFeedback config, BindingResult bindingResult, ActionRequest request, ActionResponse response) {
		if (!bindingResult.hasErrors()) {
			PortletPreferences prefs = request.getPreferences();
						
			try {
				prefs.setValue("confirmationText", config.getConfirmationText());
				prefs.setValue("feedbackMailSender", config.getFeedbackMailSender());
				prefs.setValue("feedbackMailReceiver", config.getFeedbackMailReceiver());
				prefs.setValue("feedbackMailSubject", config.getFeedbackMailSubject());
				prefs.setValue("feedbackMailBody", config.getFeedbackMailBody());
				prefs.store();
			} catch (ReadOnlyException e) {
				logger.log(Level.SEVERE, "Could not set read-only portlet preferences!", e);
			} catch (ValidatorException e) {
				logger.log(Level.SEVERE, "Portlet preference did not validate!", e);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Could not save portlet preferences!", e);
			}
		}
	}

}
