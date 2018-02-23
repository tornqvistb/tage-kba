package se.goteborg.retursidan.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base class for all entity beans
 *
 */
@MappedSuperclass
public abstract class GeneralEntityBean extends GeneralModelBean {
	@Id
	@GeneratedValue
	private int id = -1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
