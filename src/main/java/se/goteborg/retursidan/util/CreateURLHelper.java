package se.goteborg.retursidan.util;

import javax.portlet.PortletRequest;

public class CreateURLHelper {
	/**
	 * Create the first part of an URL based on the request URL (<protocol>://<server>[:<port>])
	 * @param request
	 * @return the base URL
	 */
	public static String createHttpBaseURL(PortletRequest request) {
		String url = request.getScheme() + "://" + request.getServerName() + ((request.getServerPort()!=80) ? ":"+request.getServerPort() : "");
		return url;
	}
}
