package se.goteborg.retursidan.portlet.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import se.goteborg.retursidan.model.entity.Person;
import se.goteborg.retursidan.model.entity.Request;
import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.model.form.MailComposition;
import se.goteborg.retursidan.model.form.RequestMailContact;
import se.goteborg.retursidan.model.form.Texts;
import se.goteborg.retursidan.portlet.validation.RequestMailContactValidator;
import se.goteborg.retursidan.service.MailService;
import se.goteborg.retursidan.util.CreateURLHelper;

/**
 * Controller handling taking contact with users who have added requests.
 *
 */
@Controller
@RequestMapping("VIEW")
public class ContactRequesterController extends BaseController {
	private static Logger logger = Logger.getLogger(ContactRequesterController.class.getName());

	@Autowired
	private RequestMailContactValidator mailContactValidator;
	
	@Autowired
	private MailService mailService;
		
	@InitBinder("requestMailContact")  
	protected void initBinder(WebDataBinder binder) {  
		binder.setValidator(mailContactValidator);  
	}
	
	@ModelAttribute("request")
	public Request getRequest(@RequestParam(value="requestId", required=false) Integer requestId) {
		return modelService.getRequest(requestId);
	}
	
	@ModelAttribute("requestMailContact")
	public RequestMailContact getMailContact(@RequestParam(value="requestId", required=false) Integer requestId, PortletRequest portletRequest) {
		RequestMailContact mailContact = new RequestMailContact();
		Request request = modelService.getRequest(requestId);
		if (request != null) {
			logger.log(Level.FINER, "getMailContact has request: " + request.getId());
			mailContact.setRequestId(request.getId());
			mailContact.setRequestTitle(request.getTitle());
		} else {
			logger.log(Level.FINER, "getBooking did NOT find advertisment");
		}

		Person contact = getCurrentUser(portletRequest);
	
		if (contact != null) {
			mailContact.setContact(contact);
		}
		mailContact.setMessage("");
		return mailContact;
	}
	
	@RenderMapping(params="page=contactRequester")
	public String contactRequester(Model model) {
		return "contact_requester";
	}

	@RenderMapping(params="page=contactRequesterFinished")
	public String contactRequesterFinished() {
		return "contact_requester_finished";
	}
		
	@ActionMapping("sendMail")
	public void sendMail(@Valid @ModelAttribute("requestMailContact") RequestMailContact mailContact, BindingResult bindingResult, Model model, ActionRequest actionRequest, ActionResponse actionResponse) {
		logger.log(Level.FINER, "RequestMailContact: " + mailContact);
		if (!bindingResult.hasErrors()) {
			Request request = modelService.getRequest(mailContact.getRequestId());
			Texts texts = getTexts(actionRequest);
			Config config = getConfig(actionRequest);
			String adLink = CreateURLHelper.createHttpBaseURL(actionRequest) + getConfig(actionRequest).getPocURIBase() +  REQUEST_URI + request.getId();
			MailComposition composition = new MailComposition(null,
					texts.getMailSenderAddressRequest(),
					new String[] {request.getContact().getEmail(), mailContact.getContact().getEmail()},	
					mailContact.getContact().getEmail(),
					texts.getMailSubjectRequest(),
					request.getTitle(),
					"", "", "",
					"", "", "",
					mailContact.getContact().getName(),
					mailContact.getContact().getPhone(),
					mailContact.getContact().getEmail(),
					request.getContact().getName(),
					request.getContact().getPhone(),
					request.getContact().getEmail(),					
					mailContact.getMessage(),
					adLink,
					texts.getMailBodyRequest(),
					config.getRulesUrl(),
					config.getLogoUrl());
			
			if (!request.getPhotos().isEmpty()) {				
				composition.setPhoto(modelService.getPhoto(request.getPhotos().get(0).getId()));
			}
			
			mailService.composeAndSendMail(composition);
			actionResponse.setRenderParameter("page", "contactRequesterFinished");
		} else {
			actionResponse.setRenderParameter("page", "contactRequester");
		}
	}
}
