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

import se.goteborg.retursidan.model.form.Texts;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.portlet.validation.TextsValidator;

@Controller
@RequestMapping("EDIT")
public class ChangeTextsController extends BaseController {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private TextsValidator textsValidator;
	
	@InitBinder("texts")  
	protected void initBinder(WebDataBinder binder) {  
		binder.setValidator(textsValidator);  
	}
	
	@RenderMapping(params="page=changeTexts")
	public String changeConfig() {
		return "config/change_texts";
	}

	@ActionMapping("saveTexts")
	public void saveConfig(@Valid @ModelAttribute("texts") Texts texts, BindingResult bindingResult, ActionRequest request, ActionResponse response) {
		if (!bindingResult.hasErrors()) {
			PortletPreferences prefs = request.getPreferences();
			try {
				prefs.setValue("confirmCreateAdText", texts.getConfirmCreateAdText());
				prefs.setValue("confirmBookingText", texts.getConfirmBookingText());
				prefs.setValue("confirmRemoveRequestText", texts.getConfirmRemoveRequestText());
				prefs.setValue("confirmRepublishText", texts.getConfirmRepublishText());
				prefs.setValue("bookingConfirmationText", texts.getBookingConfirmationText());
				prefs.setValue("mailSubject", texts.getMailSubject());
				prefs.setValue("mailSenderAddress", texts.getMailSenderAddress());
				prefs.setValue("mailBody", texts.getMailBody());
				prefs.setValue("startPageIngress", texts.getStartPageIngress());
				prefs.setValue("mailSubjectNewAd", texts.getMailSubjectNewAd());
				prefs.setValue("mailSenderAddressNewAd", texts.getMailSenderAddressNewAd());
				prefs.setValue("mailBodyNewAd", texts.getMailBodyNewAd());
				prefs.setValue("mailSubjectRequest", texts.getMailSubjectRequest());
				prefs.setValue("mailSenderAddressRequest", texts.getMailSenderAddressRequest());
				prefs.setValue("mailBodyRequest", texts.getMailBodyRequest());
				prefs.setValue("mailSubjectNewRequest", texts.getMailSubjectNewRequest());
				prefs.setValue("mailSenderAddressNewRequest", texts.getMailSenderAddressNewRequest());
				prefs.setValue("mailBodyNewRequest", texts.getMailBodyNewRequest());
				prefs.store();
			} catch (ReadOnlyException e) {
				logger.log(Level.SEVERE, "Could not set read-only portlet preferences!", e);
			} catch (ValidatorException e) {
				logger.log(Level.SEVERE, "Portlet preference did not validate!", e);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Could not save portlet preferences!", e);
			}
		} else {
			response.setRenderParameter("page", "changeTexts");
		}
	}

}
