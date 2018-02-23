package se.goteborg.retursidan.portlet.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.PortletContext;
import javax.portlet.ResourceResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.portlet.context.PortletContextAware;

import se.goteborg.retursidan.model.entity.Photo;

/**
 * Controller responsible for serving up photos loaded from the database.
 * 
 */
public class PhotoBaseController extends BaseController implements PortletContextAware {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private void outputImage(ResourceResponse response, Integer id, boolean thumbnail) {
		if (id != null) {
			Photo photo = null;
			try {
				photo = modelService.getPhoto(id);
				if (photo != null) {
					if (photo.getMimeType() != null) {
						response.setContentType(photo.getMimeType());
					}
					Blob image = (thumbnail) ? photo.getThumbnail() : photo.getImage();
					response.setContentLength((int)image.length());
					OutputStream os = response.getPortletOutputStream();					
					IOUtils.copyLarge(image.getBinaryStream(), os);
					os.close();
				} else {
					logger.log(Level.FINE, "Could not find " + (thumbnail ? "thumbnail" : "photo") + " with id = '" + id +"' in database.");
					outputDefault(response, thumbnail);
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "Could not write " + (thumbnail ? "thumbnail" : "photo") + " with id = '" + id + "' to output stream.", e);
				outputDefault(response, thumbnail);
			}
		} else {
			logger.log(Level.FINE, "No id specified for " + (thumbnail ? "thumbnail" : "photo") + " requested.");
			outputDefault(response, thumbnail);			
		}
	}

	private void outputDefault(ResourceResponse response, boolean thumbnail) {
		// photo could not be found; serve up a default photo
		logger.log(Level.FINE, "Requested " + (thumbnail ? "thumbnail" : "photo") + " could not be retrieved, so a default photo will be served up instead.");
		try {
			FileInputStream is = new FileInputStream(portletContext.getRealPath("/img/pic_missing" + ((thumbnail) ? "_thumb" : "") + ".png"));
			response.setContentType("image/png");
			OutputStream os = response.getPortletOutputStream();
			byte[] buf = new byte[1024];
			while(is.read(buf) != -1) {
				os.write(buf);
			}
			is.close();
			os.close();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while serving up default photo.", e);
		}		
	}
	
	@ResourceMapping("photo")
	public void servePhoto(ResourceResponse response, @RequestParam int id) {
		outputImage(response, id, false);
	}
	
	@ResourceMapping("thumbnail")
	public void serveThumbnail(ResourceResponse response, @RequestParam(required=false) Integer id) {
		outputImage(response, id, true);
	}
	private PortletContext portletContext = null;
	
	@Override
	public void setPortletContext(PortletContext portletContext) {
		this.portletContext = portletContext;
	}
}
