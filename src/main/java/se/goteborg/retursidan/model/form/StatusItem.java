package se.goteborg.retursidan.model.form;

import se.goteborg.retursidan.model.entity.Advertisement.Status;

public class StatusItem {

	public StatusItem(Status status, String label) {
		super();
		this.status = status;
		this.label = label;
	}
	private Status	status;
	private String  label;
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
