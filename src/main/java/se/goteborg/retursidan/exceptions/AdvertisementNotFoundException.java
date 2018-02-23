package se.goteborg.retursidan.exceptions;

/**
 * Exception thrown when an advertisment is not found 
 *
 */
public class AdvertisementNotFoundException extends AdvertisementException {
	private static final long serialVersionUID = 6092891324065333896L;
	
	public AdvertisementNotFoundException(Integer advertisementId) {
		super("Advertisement not found", "error.advertisement.notFound", advertisementId);
	}	
}
