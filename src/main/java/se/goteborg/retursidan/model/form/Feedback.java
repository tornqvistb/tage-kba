package se.goteborg.retursidan.model.form;

import org.springframework.stereotype.Component;

import se.goteborg.retursidan.model.GeneralModelBean;
import se.goteborg.retursidan.model.entity.Person;

/**
 * Model bean representing a booking.
 *
 */
@Component
public class Feedback extends GeneralModelBean {
	private Person contact;
	private String message; 
	public Person getContact() {
		return contact;
	}
	public void setContact(Person contact) {
		this.contact = contact;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
