package se.goteborg.retursidan.model.form;

import javax.portlet.PortletPreferences;

import org.springframework.stereotype.Component;

import se.goteborg.retursidan.model.GeneralModelBean;

/**
 * Model bean representing the configuration
 *
 */
@Component
public class Config extends GeneralModelBean {
	private boolean useInternalResources;
	private String pageSize;
	private String imageWidth;
	private String imageHeight;
	private String thumbWidth;
	private String thumbHeight;
	private String adExpireTime;
	private String requestExpireTime;
	private String pocURIBase;
	private String rulesUrl;
	private String logoUrl;
	private String mailSmtpHost;
	private String mailSmtpPort;
	private String useMailAuthentication;
	private String mailUser;
	private String mailPassword;

	public Config() {
	}

	public Config(PortletPreferences prefs) {
		//this.useInternalResources = Boolean.parseBoolean(prefs.getValue("useInternalResources", "false"));
		this.useInternalResources = true;
		this.pageSize = prefs.getValue("pageSize", "10");
		this.imageWidth = prefs.getValue("imageWidth", "600");
		this.imageHeight = prefs.getValue("imageHeight", "600");
		this.thumbWidth = prefs.getValue("thumbWidth", "150");
		this.thumbHeight = prefs.getValue("thumbHeight", "150");
		this.adExpireTime = prefs.getValue("adExpireTime", "30");
		this.requestExpireTime = prefs.getValue("requestExpireTime", "30");
		this.pocURIBase = prefs.getValue("pocURIBase", "/wps/mypoc?uri=gbglink:");
		this.rulesUrl = prefs.getValue("rulesUrl", "");
		this.logoUrl = prefs.getValue("logoUrl", "");
		this.mailSmtpHost = prefs.getValue("mailSmtpHost", "");
		this.mailSmtpPort = prefs.getValue("mailSmtpPort", "");
		this.useMailAuthentication = prefs.getValue("useMailAuthentication", "false");
		this.mailUser = prefs.getValue("mailUser", "");
		this.mailPassword = prefs.getValue("mailPassword", "");
	}
	
	public boolean getUseInternalResources() {
		return useInternalResources;
	}

	public String getPageSize() {
		return pageSize;
	}
	public int getPageSizeInt() {
		return (pageSize != null) ? Integer.parseInt(pageSize) : null;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getImageWidth() {
		return imageWidth;
	}
	public int getImageWidthInt() {
		return (imageWidth != null) ? Integer.parseInt(imageWidth) : null;
	}

	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getImageHeight() {
		return imageHeight;
	}
	public int getImageHeightInt() {
		return (imageHeight != null) ? Integer.parseInt(imageHeight) : null;
	}

	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getThumbWidth() {
		return thumbWidth;
	}
	public int getThumbWidthInt() {
		return (thumbWidth != null) ? Integer.parseInt(thumbWidth) : null;
	}
	

	public void setThumbWidth(String thumbWidth) {
		this.thumbWidth = thumbWidth;
	}

	public String getThumbHeight() {
		return thumbHeight;
	}
	public int getThumbHeightInt() {
		return (thumbHeight != null) ? Integer.parseInt(thumbHeight) : null;
	}

	public void setThumbHeight(String thumbHeight) {
		this.thumbHeight = thumbHeight;
	}

	public String getAdExpireTime() {
		return adExpireTime;
	}
	public int getAdExpireTimeInt() {
		return (adExpireTime != null) ? Integer.parseInt(adExpireTime) : null;
	}
	
	public void setAdExpireTime(String adExpireTime) {
		this.adExpireTime = adExpireTime;
	}

	public String getRequestExpireTime() {
		return requestExpireTime;
	}
	public int getRequestExpireTimeInt() {
		return (requestExpireTime != null) ? Integer.parseInt(requestExpireTime) : null;
	}
	
	public void setRequestExpireTime(String requestExpireTime) {
		this.requestExpireTime = requestExpireTime;
	}

	public String getRulesUrl() {
		return rulesUrl;
	}

	public void setRulesUrl(String rulesUrl) {
		this.rulesUrl = rulesUrl;
	}

	public String getPocURIBase() {
		return pocURIBase;
	}

	public void setPocURIBase(String pocURIBase) {
		this.pocURIBase = pocURIBase;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

	public String getMailSmtpPort() {
		return mailSmtpPort;
	}

	public void setMailSmtpPort(String mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}

	public String getUseMailAuthentication() {
		return useMailAuthentication;
	}

	public void setUseMailAuthentication(String useMailAuthentication) {
		this.useMailAuthentication = useMailAuthentication;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
}
