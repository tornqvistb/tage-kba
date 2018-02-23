package se.goteborg.retursidan.exceptions;

/**
 * General advertisement exception 
 *
 */
public abstract class AdvertisementException extends RuntimeException {
	private static final long serialVersionUID = 5078622028197805638L;
	private Integer advertisementId;
	private String messageCode;
	
	public AdvertisementException(String defaultMessage, String messageCode, Integer advertisementId) {
		super(defaultMessage);
		this.messageCode = messageCode;
		this.advertisementId = advertisementId;
	}
	
	public Integer getAdvertisementId() {
		return advertisementId;
	}
	
	public String getMessageCode() {
		return messageCode;
	}
		
	@Override
	public String getMessage() {
		return super.getMessage() + ". Advertisement id = " + advertisementId;
	}
}
