package se.goteborg.retursidan.service.upload;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.portlet.ResourceRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

@Service
public class ImageUploadService {
	private final String PARAM_UPLOAD_FILENAME = "qqfile";
	private Logger logger = Logger.getLogger(ImageUploadService.class.getName());

	public UploadedImage getUploadedImage(ResourceRequest request) throws IOException, FileUploadException {
		UploadedImage upload = new UploadedImage();

		PortletFileUpload pfu = new PortletFileUpload();
		ResourceRequestContext ctx = new ResourceRequestContext(request);
		InputStream is = null;
		String filename = null;
		
		// incoming file uploaded as multipart/form-data 
		if(PortletFileUpload.isMultipartContent(ctx)) {
        	logger.log(Level.FINE, "Multi-part upload initiated");
			FileItemIterator iter = pfu.getItemIterator(ctx);
			if(iter.hasNext()) {
				FileItemStream item = iter.next();
				is = item.openStream();
				filename = item.getName();
			}
		// incoming file uploaded as application/octet-stream
		} else if ("application/octet-stream".equals(request.getContentType())) {
        	logger.log(Level.FINE, "Octet-stream upload initiated");
			is = request.getPortletInputStream();
			filename = request.getParameter(PARAM_UPLOAD_FILENAME);
		} else {
        	logger.log(Level.WARNING, "Invalid file upload, request is neither multipart nor octet-stream");
        	upload.setValidUpload(false);
			return upload;
		}
		// read uploaded file into a BufferedImage object
		BufferedImage img = ImageIO.read(is);
		IOUtils.closeQuietly(is);

		upload.setValidUpload(true);
		upload.setImage(img);
		
		// IE sends the complete file path, just use the filename
		if (filename != null) {
			int pos = filename.lastIndexOf("\\");
			if (pos != -1) {
				filename = filename.substring(pos + 1);
			}
		}
		upload.setFilename(filename);
		return upload;
	}

}
