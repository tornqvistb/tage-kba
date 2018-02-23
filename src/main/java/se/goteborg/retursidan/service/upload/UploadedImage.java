package se.goteborg.retursidan.service.upload;

import java.awt.image.BufferedImage;

public class UploadedImage {
	private BufferedImage image;
	private String filename;
	private boolean validUpload;
	
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**
	 * If image is null, no registered ImageReader was found able to read the input stream, meaning that the uploaded file
	 * isn't a valid image.
	 */
	public boolean isValidImage() {
		return (validUpload && image != null);
	}
	public boolean isValidUpload() {
		return validUpload;
	}
	public void setValidUpload(boolean validUpload) {
		this.validUpload = validUpload;
	}
}
