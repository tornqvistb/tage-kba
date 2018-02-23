package se.goteborg.retursidan.exceptions;

/**
 * Exception thrown when a booking is made on an ad that has already been booked 
 *
 */
public class AdvertisementAlreadyBookedException extends AdvertisementException {
	private static final long serialVersionUID = 4945863902575358710L;
	
	public AdvertisementAlreadyBookedException(Integer advertisementId) {
		super("Advertisement already booked", "error.advertisement.alreadyBooked", advertisementId);
	}
}
