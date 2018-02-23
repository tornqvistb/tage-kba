package se.goteborg.retursidan.exceptions;

/**
 * Exception thrown when an booking is made on an advertisement that has expired
 *
 */
public class AdvertisementExpiredException extends AdvertisementException {
	private static final long serialVersionUID = 8397347460097144798L;
	
	public AdvertisementExpiredException(Integer advertisementId) {
		super("Advertisement has expired", "error.advertisement.expired", advertisementId);
	}	
}
