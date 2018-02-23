package se.goteborg.retursidan.service.upload;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.ResourceRequest;

import org.apache.commons.fileupload.RequestContext;

public class ResourceRequestContext implements RequestContext {

	private ResourceRequest request;
	
	public ResourceRequestContext(ResourceRequest request) {
		this.request = request;
	}
	
	@Override
	public String getCharacterEncoding() {
		return request.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		return request.getContentType();
	}

	@Override
	public int getContentLength() {
		return request.getContentLength();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return request.getPortletInputStream();
	}
	
	public String toString() {
		return "ContentLength=" + getContentLength() + ", ContentType=" + getContentType();
	}
}
