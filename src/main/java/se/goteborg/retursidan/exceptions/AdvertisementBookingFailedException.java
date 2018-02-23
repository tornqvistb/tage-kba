package se.goteborg.retursidan.exceptions;

/**
 * Exception thrown when a booking is made on an ad that has already been booked 
 *
 */
public class AdvertisementBookingFailedException extends AdvertisementException {
	
	private static final long serialVersionUID = 4945863902575358711L;
	
	public AdvertisementBookingFailedException(Integer advertisementId) {
		super("Advertisement booking failed", "error.advertisement.bookingfailed", advertisementId);
	}
}
