package se.goteborg.retursidan.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import se.goteborg.retursidan.model.GeneralEntityBean;
import se.goteborg.retursidan.util.DateHelper;

/**
 * Model bean representing a visit, which is all visits made by a
 * specific user.
 *
 */
@Entity
public class Visit extends GeneralEntityBean implements Serializable {
	private static final long serialVersionUID = 653537183934165621L;

	@Column(nullable=false)
	private String userId;

	@Column(nullable=false)
	private Integer visitCount;
	
	@Column(nullable=false)
	private Date lastVisit = DateHelper.getCurrentDate();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public Date getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}
}
