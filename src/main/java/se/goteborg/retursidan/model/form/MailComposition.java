package se.goteborg.retursidan.model.form;

import se.goteborg.retursidan.model.entity.Photo;

public class MailComposition {

	public MailComposition(Photo photo, String senderAdress, String[] reveiverAdress, String replyToAdress,
			String mailHeader, String title, String bookerName, String bookerPhone, String bookerMail,
			String advertiserName, String advertiserPhone, String advertiserMail, String respondentName,
			String respondentPhone, String respondentMail, String requesterName, String requesterPhone,
			String requesterMail, String message, String link, String configMailBody, String rulesUrl, String logoUrl, int bookedQuantity) {
		super();
		this.photo = photo;
		this.senderAdress = senderAdress;
		this.reveiverAdress = reveiverAdress;
		this.replyToAdress = replyToAdress;
		this.mailHeader = mailHeader;
		this.title = title;
		this.bookerName = bookerName;
		this.bookerPhone = bookerPhone;
		this.bookerMail = bookerMail;
		this.advertiserName = advertiserName;
		this.advertiserPhone = advertiserPhone;
		this.advertiserMail = advertiserMail;
		this.respondentName = respondentName;
		this.respondentPhone = respondentPhone;
		this.respondentMail = respondentMail;
		this.requesterName = requesterName;
		this.requesterPhone = requesterPhone;
		this.requesterMail = requesterMail;
		this.message = message;
		this.link = link;
		this.configMailBody = configMailBody;
		this.rulesUrl = rulesUrl;		
		this.logoUrl = logoUrl;
		this.bookedQuantity = bookedQuantity;
	}
	
	public MailComposition() {
        super();
    }
	
	private Photo photo;
	private String senderAdress;
	private String[] reveiverAdress;
	private String replyToAdress;
	private String mailHeader;
	private String title;
	private String bookerName;
	private String bookerPhone;
	private String bookerMail;
	private String advertiserName;
	private String advertiserPhone;
	private String advertiserMail;
	private String respondentName;
	private String respondentPhone;
	private String respondentMail;
	private String requesterName;
	private String requesterPhone;
	private String requesterMail;
	private String link;
	private String configMailBody;
	private String rulesUrl;
	private String logoUrl;
	private String message = "";
	private int id;
	private int publishedDays;
	private int bookedQuantity;
	
	public Photo getPhoto() {
		return photo;
	}
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	public String getMailHeader() {
		return mailHeader;
	}
	public void setMailHeader(String mailHeader) {
		this.mailHeader = mailHeader;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBookerName() {
		return bookerName;
	}
	public void setBookerName(String bookerName) {
		this.bookerName = bookerName;
	}
	public String getBookerPhone() {
		return bookerPhone;
	}
	public void setBookerPhone(String bookerPhone) {
		this.bookerPhone = bookerPhone;
	}
	public String getBookerMail() {
		return bookerMail;
	}
	public void setBookerMail(String bookerMail) {
		this.bookerMail = bookerMail;
	}
	public String getAdvertiserName() {
		return advertiserName;
	}
	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}
	public String getAdvertiserPhone() {
		return advertiserPhone;
	}
	public void setAdvertiserPhone(String advertiserPhone) {
		this.advertiserPhone = advertiserPhone;
	}
	public String getAdvertiserMail() {
		return advertiserMail;
	}
	public void setAdvertiserMail(String advertiserMail) {
		this.advertiserMail = advertiserMail;
	}
	public String getRespondentName() {
		return respondentName;
	}
	public void setRespondentName(String respondentName) {
		this.respondentName = respondentName;
	}
	public String getRespondentPhone() {
		return respondentPhone;
	}
	public void setRespondentPhone(String respondentPhone) {
		this.respondentPhone = respondentPhone;
	}
	public String getRespondentMail() {
		return respondentMail;
	}
	public void setRespondentMail(String respondentMail) {
		this.respondentMail = respondentMail;
	}
	public String getRequesterName() {
		return requesterName;
	}
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	public String getRequesterPhone() {
		return requesterPhone;
	}
	public void setRequesterPhone(String requesterPhone) {
		this.requesterPhone = requesterPhone;
	}
	public String getRequesterMail() {
		return requesterMail;
	}
	public void setRequesterMail(String requesterMail) {
		this.requesterMail = requesterMail;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getConfigMailBody() {
		return configMailBody;
	}
	public void setConfigMailBody(String configMailBody) {
		this.configMailBody = configMailBody;
	}
	public String getRulesUrl() {
		return rulesUrl;
	}
	public void setRulesUrl(String rulesUrl) {
		this.rulesUrl = rulesUrl;
	}
	public String getSenderAdress() {
		return senderAdress;
	}
	public void setSenderAdress(String senderAdress) {
		this.senderAdress = senderAdress;
	}
	public String[] getReveiverAdress() {
		return reveiverAdress;
	}
	public void setReveiverAdress(String[] reveiverAdress) {
		this.reveiverAdress = reveiverAdress;
	}
	public String getReplyToAdress() {
		return replyToAdress;
	}
	public void setReplyToAdress(String replyToAdress) {
		this.replyToAdress = replyToAdress;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}	
	public int getPublishedDays() {
        return publishedDays;
    }
    public void setPublishedDays(int publishedDays) {
        this.publishedDays = publishedDays;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookedQuantity() {
		return bookedQuantity;
	}

	public void setBookedQuantity(int bookedQuantity) {
		this.bookedQuantity = bookedQuantity;
	}
}
