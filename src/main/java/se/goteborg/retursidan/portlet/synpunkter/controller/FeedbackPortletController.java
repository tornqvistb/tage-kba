package se.goteborg.retursidan.portlet.synpunkter.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
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

import se.goteborg.retursidan.model.entity.Person;
import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.model.form.Feedback;
import se.goteborg.retursidan.model.form.MailComposition;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.portlet.validation.FeedbackValidator;
import se.goteborg.retursidan.service.MailService;

/**
 * Controller for the Efterlysningar portlet
 *
 */
@Controller()
@RequestMapping("VIEW")
public class FeedbackPortletController extends BaseController{
	
	private static Logger logger = Logger.getLogger(FeedbackPortletController.class.getName());
	
	@Autowired
	private MailService mailService;

	@Autowired
	private FeedbackValidator feedbackValidator;
		
	@InitBinder("feedback")  
	protected void initBinder(WebDataBinder binder) {  
		binder.setValidator(feedbackValidator);  
	}
	
	@ModelAttribute("feedback")
	public Feedback getFeedback(PortletRequest portletRequest) {
		Feedback feedback = new Feedback();

		Person contact = getCurrentUser(portletRequest);
	
		if (contact != null) {
			feedback.setContact(contact);
		}
		feedback.setMessage("");
		return feedback;
	}
	
	@RenderMapping
	public String render(RenderRequest request, RenderResponse response, Model model) {
		return "feedback_form";
	}
	
	@RenderMapping(params="page=feedbackForm")
	public String feedbackForm(RenderRequest request, RenderResponse response, Model model) {
		return render(request, response, model);
	}

	@RenderMapping(params="page=feedbackFinished")
	public String feedbackFinished() {
		return "feedback_finished";
	}

	
	@ActionMapping("sendMail")
	public void sendMail(@Valid @ModelAttribute("feedback") Feedback feedback, BindingResult bindingResult, Model model, ActionRequest actionRequest, ActionResponse actionResponse) {
		logger.log(Level.FINER, "Feedback: " + feedback);
		Config config = getConfig(actionRequest);
		if (!bindingResult.hasErrors()) {
			PortletPreferences prefs = actionRequest.getPreferences();
			String receiver = prefs.getValue("feedbackMailReceiver", "");
			String sender = prefs.getValue("feedbackMailSender", "");
			String subject = prefs.getValue("feedbackMailSubject", "");
			String mailTemplate = prefs.getValue("feedbackMailBody", "");
			
			MailComposition composition = new MailComposition(null,
					sender,
					new String[] {receiver, feedback.getContact().getEmail()},	
					feedback.getContact().getEmail(),
					subject,
					"",
					"", "", "",
					"", "", "",
					feedback.getContact().getName(),
					feedback.getContact().getPhone(),
					feedback.getContact().getEmail(),
					"", "", "",				
					feedback.getMessage(),
					"",
					mailTemplate,
					config.getRulesUrl(), config.getLogoUrl(),
					0);
			mailService.composeAndSendMail(composition);
			actionResponse.setRenderParameter("page", "feedbackFinished");
		} else {
			actionResponse.setRenderParameter("page", "feedbackForm");
		}
	}

}
