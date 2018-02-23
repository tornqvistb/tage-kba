package se.goteborg.retursidan.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import se.goteborg.retursidan.model.GeneralEntityBean;

/**
 * Model bean representing a category. A category can either have a parent (sub category) or not have
 * a parent (top category).
 *
 */
@Entity
public class Category extends GeneralEntityBean implements Serializable {
	private static final long serialVersionUID = -9815324993397017L;

	@Column
	private String title;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Category parent;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	
}
