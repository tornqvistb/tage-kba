package se.goteborg.retursidan.model.form;

import javax.portlet.PortletPreferences;

import org.springframework.stereotype.Component;

import se.goteborg.retursidan.model.GeneralModelBean;

/**
 * Model bean containing all config for the feedback portlet
 *
 */

@Component
public class ConfigFeedback extends GeneralModelBean {
	private String confirmationText;
	private String feedbackMailSender;
	private String feedbackMailReceiver;
	private String feedbackMailSubject;
	private String feedbackMailBody;
	

	public ConfigFeedback() {
	}

	public ConfigFeedback(PortletPreferences prefs) {
		this.confirmationText = prefs.getValue("confirmationText", "");
		this.feedbackMailSender = prefs.getValue("feedbackMailSender", "");
		this.feedbackMailReceiver = prefs.getValue("feedbackMailReceiver", "");
		this.feedbackMailSubject = prefs.getValue("feedbackMailSubject", "");
		this.feedbackMailBody = prefs.getValue("feedbackMailBody", "");
	}

	public String getConfirmationText() {
		return confirmationText;
	}

	public void setConfirmationText(String confirmationText) {
		this.confirmationText = confirmationText;
	}

	public String getFeedbackMailSender() {
		return feedbackMailSender;
	}

	public void setFeedbackMailSender(String feedbackMailSender) {
		this.feedbackMailSender = feedbackMailSender;
	}

	public String getFeedbackMailReceiver() {
		return feedbackMailReceiver;
	}

	public void setFeedbackMailReceiver(String feedbackMailReceiver) {
		this.feedbackMailReceiver = feedbackMailReceiver;
	}

	public String getFeedbackMailSubject() {
		return feedbackMailSubject;
	}

	public void setFeedbackMailSubject(String feedbackMailSubject) {
		this.feedbackMailSubject = feedbackMailSubject;
	}

	public String getFeedbackMailBody() {
		return feedbackMailBody;
	}

	public void setFeedbackMailBody(String feedbackMailBody) {
		this.feedbackMailBody = feedbackMailBody;
	}

}
