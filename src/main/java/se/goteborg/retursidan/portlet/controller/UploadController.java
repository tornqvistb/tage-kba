package se.goteborg.retursidan.portlet.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.fileupload.FileUploadException;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import se.goteborg.retursidan.model.entity.Photo;
import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.service.upload.ImageUploadService;
import se.goteborg.retursidan.service.upload.UploadedImage;

/**
 * Controller responsible for handling file uploads of photos
 *
 */
@Controller
@RequestMapping("VIEW")
public class UploadController extends BaseController {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private ImageUploadService imageUploadService;

	@ResourceMapping("uploadPhoto")
	public void upload(ResourceRequest request, ResourceResponse response) {
		int photoMinWidth = getConfig(request).getImageWidthInt() / 2;
		int photoMinHeight = getConfig(request).getImageHeightInt() / 2;
		
		try {
			UploadedImage upload = imageUploadService.getUploadedImage(request);
			
			BufferedImage img = upload.getImage();
			String filename = upload.getFilename();
			
			if (upload.isValidUpload()) {
				if (!upload.isValidImage()) {
					logger.log(Level.FINE, "Uploaded file " + filename + " is not a valid image");
					writeResponse(response, "Felaktigt filformat, den uppladdade filen " + filename + " är inte en giltig bild", null);
				
				} else if (img.getWidth() < photoMinWidth || img.getHeight() < photoMinHeight) {
					logger.log(Level.FINE, "Invalid image uploaded, the image dimensions is below minimum threshold.");
					writeResponse(response, "Den uppladdade filen " + filename + " är för liten, minsta bredd är " + photoMinWidth + " pixlar och minsta höjd " + photoMinHeight + " pixlar, Bredd/höjd på denna bild: " + img.getWidth() + "/" + img.getHeight(), null);
				
				} else {
			    	// scale image
					img = Scalr.resize(img, Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_WIDTH, getConfig(request).getImageWidthInt(), getConfig(request).getImageHeightInt());
					logger.log(Level.FINE, "Photo scaled to " + img.getWidth() + "x" + img.getHeight());
					
					int id = storePhoto(img, getUserId(request), filename, getConfig(request));
			        writeResponse(response, null, id, img.getWidth(), img.getHeight());
				}
			} else {
				writeResponse(response, "Felaktig uppladdning", null);
			}
		} catch(IOException e) {
			logger.log(Level.WARNING, "Error occured when handling uploaded file data.", e);
		} catch (FileUploadException e) {
			logger.log(Level.WARNING, "Error occured uploaded multipart data.", e);
		} catch(SQLException e) {
			logger.log(Level.WARNING, "Error occured when storing uploaded file data.", e);
		}
	}
	
	@ResourceMapping("removePhoto") 
	public void remove(ResourceResponse response, @RequestParam int id) {
		try {
			Photo photo = modelService.getPhoto(id);
			modelService.removePhoto(photo);
			writeResponse(response, null, id);
		} catch(Exception e) {
			writeResponse(response, "Ett fel uppstod när fotot skulle tas bort: " + e.getMessage(), null);
		}
	}
	
    private int storePhoto(BufferedImage img, String uid, String filename, Config config) throws IOException, SQLException {     
    	// output the image to a byte array in PNG format
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(img, "png", bos);
		
    	Photo photo = new Photo();
    	photo.setCreatorUid(uid);
        photo.setImage(new SerialBlob(bos.toByteArray()));       
        photo.setThumbnail(new SerialBlob(createThumbnail(img, config)));
        photo.setTitle(filename);
        photo.setMimeType("image/png");
        photo.setWidth(img.getWidth());
        photo.setHeight(img.getHeight());
        modelService.addPhoto(photo);
        return photo.getId();
    }

    private byte[] createThumbnail(BufferedImage img, Config config) throws IOException {
		BufferedImage dest = Scalr.resize(img, Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_WIDTH, config.getThumbWidthInt(), config.getThumbHeightInt(), Scalr.OP_ANTIALIAS);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(dest, "png", bos);
		return bos.toByteArray();
    }
    
    private void writeResponse(ResourceResponse response, String failureReason, Integer photoId) {
    	writeResponse(response, failureReason, photoId, null, null);
    }
    
    private void writeResponse(ResourceResponse response, String failureReason, Integer photoId, Integer width, Integer height) {
    	try {
    		PrintWriter writer = response.getWriter();
    		if (failureReason == null) {
    			writer.print("{\"success\": true, \"id\": " + photoId + ((width != null && height != null) ? ", \"width\": " + width + ", \"height\": " + height : "") + "}");
    		} else {
    			writer.print("{\"error\": \"" + failureReason + "\"}");
    		}
    	} catch(IOException e) {
    		logger.log(Level.SEVERE, "Could not write to response output stream!", e);
    	}
    }
}
