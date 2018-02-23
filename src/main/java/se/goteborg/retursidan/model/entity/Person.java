package se.goteborg.retursidan.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import se.goteborg.retursidan.model.GeneralEntityBean;

/**
 * Model bean representing a person. This entity is connected to a real portal user
 * via the userId field.
 *
 */
@Entity
public class Person extends GeneralEntityBean implements Serializable {
	private static final long serialVersionUID = 6652069014714650972L;

	@Column(nullable=true)
	private String userId;
	
	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private String phone;
	
	@Column(nullable=false)
	private String email;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
