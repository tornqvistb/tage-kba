package se.goteborg.retursidan.model.form;

import org.springframework.stereotype.Component;

import se.goteborg.retursidan.model.GeneralModelBean;
import se.goteborg.retursidan.model.entity.Person;

/**
 * Model bean representing a booking.
 *
 */
@Component
public class Booking extends GeneralModelBean {
	private Person contact;
	private Integer advertisementId;
	private String advertisementTitle;
	private boolean transportationConfirmed;
	private Integer adQuantity;
	private Integer bookedQuantity;
	
	public Integer getAdQuantity() {
		return adQuantity;
	}
	public void setAdQuantity(Integer adQuantity) {
		this.adQuantity = adQuantity;
	}
	public Integer getBookedQuantity() {
		return bookedQuantity;
	}
	public void setBookedQuantity(Integer bookedQuantity) {
		this.bookedQuantity = bookedQuantity;
	}
	public Person getContact() {
		return contact;
	}
	public void setContact(Person contact) {
		this.contact = contact;
	}
	public Integer getAdvertisementId() {
		return advertisementId;
	}
	public void setAdvertisementId(Integer advertisementId) {
		this.advertisementId = advertisementId;
	}
	public String getAdvertisementTitle() {
		return advertisementTitle;
	}
	public void setAdvertisementTitle(String advertisementTitle) {
		this.advertisementTitle = advertisementTitle;
	}
	public boolean isTransportationConfirmed() {
		return transportationConfirmed;
	}
	public void setTransportationConfirmed(boolean transportationConfirmed) {
		this.transportationConfirmed = transportationConfirmed;
	}	
}
