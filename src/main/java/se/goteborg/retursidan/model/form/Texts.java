package se.goteborg.retursidan.model.form;

import javax.portlet.PortletPreferences;

import org.springframework.stereotype.Component;

import se.goteborg.retursidan.model.GeneralModelBean;

/**
 * Model bean containing all texts configured for the application
 *
 */
@Component
public class Texts extends GeneralModelBean {
	private String confirmCreateAdText;
	private String confirmBookingText;
	private String confirmRepublishText;
	private String confirmRemoveRequestText;
	private String bookingConfirmationText;
	private String mailSenderAddress;
	private String mailSubject;
	private String mailBody;
	private String startPageIngress;
	private String mailSenderAddressNewAd;
	private String mailSubjectNewAd;
	private String mailBodyNewAd;
	private String mailSenderAddressRequest;
	private String mailSubjectRequest;
	private String mailBodyRequest;
	private String mailSenderAddressNewRequest;
	private String mailSubjectNewRequest;
	private String mailBodyNewRequest;	

	public Texts() {
	}

	public Texts(PortletPreferences prefs) {
		this.confirmCreateAdText = prefs.getValue("confirmCreateAdText", "");
		this.confirmBookingText = prefs.getValue("confirmBookingText", "");
		this.confirmRepublishText = prefs.getValue("confirmRepublishText", "");
		this.confirmRemoveRequestText = prefs.getValue("confirmRemoveRequestText", "");
		this.bookingConfirmationText = prefs.getValue("bookingConfirmationText", "");
		this.mailSenderAddress = prefs.getValue("mailSenderAddress", "tage@goteborg.se");
		this.mailSubject = prefs.getValue("mailSubject", "Annons bokad!");
		this.mailBody = prefs.getValue("mailBody", "");
		this.startPageIngress = prefs.getValue("startPageIngress", "");
		this.mailSenderAddressNewAd = prefs.getValue("mailSenderAddressNewAd", "tage@goteborg.se");
		this.mailSubjectNewAd = prefs.getValue("mailSubjectNewAd", "Annons bokad!");
		this.mailBodyNewAd = prefs.getValue("mailBodyNewAd", "");
		this.mailSenderAddressRequest = prefs.getValue("mailSenderAddressRequest", "tage@goteborg.se");
		this.mailSubjectRequest = prefs.getValue("mailSubjectRequest", "Annons bokad!");
		this.mailBodyRequest = prefs.getValue("mailBodyRequest", "");
		this.mailSenderAddressNewRequest = prefs.getValue("mailSenderAddressNewRequest", "tage@goteborg.se");
		this.mailSubjectNewRequest = prefs.getValue("mailSubjectNewRequest", "Efterlysning skapad!");
		this.mailBodyNewRequest = prefs.getValue("mailBodyNewRequest", "");
	}

	public String getConfirmCreateAdText() {
		return confirmCreateAdText;
	}

	public void setConfirmCreateAdText(String confirmCreateAdText) {
		this.confirmCreateAdText = confirmCreateAdText;
	}

	public String getConfirmBookingText() {
		return confirmBookingText;
	}

	public void setConfirmBookingText(String confirmBookingText) {
		this.confirmBookingText = confirmBookingText;
	}

	public String getConfirmRepublishText() {
		return confirmRepublishText;
	}

	public void setConfirmRepublishText(String confirmRepublishText) {
		this.confirmRepublishText = confirmRepublishText;
	}

	public String getConfirmRemoveRequestText() {
		return confirmRemoveRequestText;
	}

	public void setConfirmRemoveRequestText(String confirmRemoveRequestText) {
		this.confirmRemoveRequestText = confirmRemoveRequestText;
	}

	public String getBookingConfirmationText() {
		return bookingConfirmationText;
	}

	public void setBookingConfirmationText(String bookingConfirmationText) {
		this.bookingConfirmationText = bookingConfirmationText;
	}

	public String getMailSenderAddress() {
		return mailSenderAddress;
	}

	public void setMailSenderAddress(String mailSenderAddress) {
		this.mailSenderAddress = mailSenderAddress;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public String getStartPageIngress() {
		return startPageIngress;
	}

	public void setStartPageIngress(String startPageIngress) {
		this.startPageIngress = startPageIngress;
	}

	public String getMailSenderAddressNewAd() {
		return mailSenderAddressNewAd;
	}

	public void setMailSenderAddressNewAd(String mailSenderAddressNewAd) {
		this.mailSenderAddressNewAd = mailSenderAddressNewAd;
	}

	public String getMailSubjectNewAd() {
		return mailSubjectNewAd;
	}

	public void setMailSubjectNewAd(String mailSubjectNewAd) {
		this.mailSubjectNewAd = mailSubjectNewAd;
	}

	public String getMailBodyNewAd() {
		return mailBodyNewAd;
	}

	public void setMailBodyNewAd(String mailBodyNewAd) {
		this.mailBodyNewAd = mailBodyNewAd;
	}

	public String getMailSenderAddressRequest() {
		return mailSenderAddressRequest;
	}

	public void setMailSenderAddressRequest(String mailSenderAddressRequest) {
		this.mailSenderAddressRequest = mailSenderAddressRequest;
	}

	public String getMailSubjectRequest() {
		return mailSubjectRequest;
	}

	public void setMailSubjectRequest(String mailSubjectRequest) {
		this.mailSubjectRequest = mailSubjectRequest;
	}

	public String getMailBodyRequest() {
		return mailBodyRequest;
	}

	public void setMailBodyRequest(String mailBodyRequest) {
		this.mailBodyRequest = mailBodyRequest;
	}

	public String getMailSubjectNewRequest() {
		return mailSubjectNewRequest;
	}

	public void setMailSubjectNewRequest(String mailSubjectNewRequest) {
		this.mailSubjectNewRequest = mailSubjectNewRequest;
	}

	public String getMailBodyNewRequest() {
		return mailBodyNewRequest;
	}

	public void setMailBodyNewRequest(String mailBodyNewRequest) {
		this.mailBodyNewRequest = mailBodyNewRequest;
	}

	public String getMailSenderAddressNewRequest() {
		return mailSenderAddressNewRequest;
	}

	public void setMailSenderAddressNewRequest(String mailSenderAddressNewRequest) {
		this.mailSenderAddressNewRequest = mailSenderAddressNewRequest;
	}
}
