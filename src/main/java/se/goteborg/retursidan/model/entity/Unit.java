package se.goteborg.retursidan.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import se.goteborg.retursidan.model.GeneralEntityBean;

/**
 * Model bean representing a unit
 *
 */
@Entity
public class Unit extends GeneralEntityBean implements Serializable {
	private static final long serialVersionUID = 7781919883548238711L;

	private String name;
	@Column(name="ADMINISTRATION_ID")
	private String administrationId;

	public Unit() {
	}
	public Unit(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdministrationId() {
		return administrationId;
	}
	public void setAdministrationId(String administrationId) {
		this.administrationId = administrationId;
	}
	
}
