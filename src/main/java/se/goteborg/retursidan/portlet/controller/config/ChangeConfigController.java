package se.goteborg.retursidan.portlet.controller.config;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
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

import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.portlet.validation.ConfigValidator;

@Controller
@RequestMapping("EDIT")
public class ChangeConfigController extends BaseController {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private ConfigValidator configValidator;
	
	@InitBinder("config")  
	protected void initBinder(WebDataBinder binder) {  
		binder.setValidator(configValidator);  
	}
	
	@RenderMapping(params="page=changeConfig")
	public String changeConfig() {
		return "config/change_config";
	}

	@ActionMapping("saveConfig")
	public void saveConfig(@Valid @ModelAttribute("config") Config config, BindingResult bindingResult, ActionRequest request, ActionResponse response) {
		if (!bindingResult.hasErrors()) {
			PortletPreferences prefs = request.getPreferences();
			try {
				prefs.setValue("pageSize", config.getPageSize());
				prefs.setValue("imageWidth", config.getImageWidth());
				prefs.setValue("imageHeight", config.getImageHeight());
				prefs.setValue("thumbWidth", config.getThumbWidth());
				prefs.setValue("thumbHeight", config.getThumbHeight());
				prefs.setValue("adExpireTime", config.getAdExpireTime());
				prefs.setValue("requestExpireTime", config.getRequestExpireTime());
				prefs.setValue("pocURIBase", config.getPocURIBase());
				prefs.setValue("rulesUrl", config.getRulesUrl());
				prefs.setValue("logoUrl", config.getLogoUrl());
				prefs.setValue("mailSmtpHost", config.getMailSmtpHost());
				prefs.setValue("mailSmtpPort", config.getMailSmtpPort());
				prefs.setValue("useMailAuthentication", config.getUseMailAuthentication());
				prefs.setValue("mailUser", config.getMailUser());
				prefs.setValue("mailPassword", config.getMailPassword());				
				prefs.store();
			} catch (ReadOnlyException e) {
				logger.log(Level.SEVERE, "Could not set read-only portlet preferences!", e);
			} catch (ValidatorException e) {
				logger.log(Level.SEVERE, "Portlet preference did not validate!", e);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Could not save portlet preferences!", e);
			}
		} else {
			response.setRenderParameter("page", "changeConfig");
		}
	}

}
