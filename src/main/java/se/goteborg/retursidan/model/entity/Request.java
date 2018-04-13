package se.goteborg.retursidan.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import se.goteborg.retursidan.model.GeneralEntityBean;
import se.goteborg.retursidan.util.DateHelper;
import se.goteborg.retursidan.util.StringFormatter;

/**
 * Model bean representing a request (a wanted ad).
 *
 */
@Entity
public class Request extends GeneralEntityBean implements Serializable {
	private static final long serialVersionUID = 5734565963237558347L;

	public enum Status {
	    PUBLISHED, EXPIRED 
	}
	
	@Column(nullable=false)
	private Status status = Status.PUBLISHED;
	
	@Column(nullable=false)
	private String creatorUid;
	
	@Column(nullable=false)
	private String title;
	
	@OneToOne(optional=false)
	@Cascade({CascadeType.SAVE_UPDATE})
	private Person contact;
	
	@DateTimeFormat(style="SS")
	@Column(nullable=false)
	private Date created = DateHelper.getCurrentDate();
	
	@Transient
	private Category topCategory;
	
	@ManyToOne(optional=false)
	private Category category;
	
	@Column(nullable=false, length=500)
	private String description;
	
	@ManyToOne(optional=false)
	private Unit unit;
	
	@DateTimeFormat(style = "SS")
	@Column(nullable = false)
	private Date publishDate = new Date();
	/**
	 * Fetch the photos lazy, we do not want to load the actual image data for each photo
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade=javax.persistence.CascadeType.REMOVE)
	@JoinTable(name = "photoset_request")
	@OrderBy("id ASC")
	private List<Photo> photos;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Person getContact() {
		return contact;
	}
	public void setContact(Person contact) {
		this.contact = contact;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getCreatorUid() {
		return creatorUid;
	}
	public void setCreatorUid(String creatorUid) {
		this.creatorUid = creatorUid;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getFormattedCreatedDate() {
		return StringFormatter.formatShortDate(created);
	}
	public Category getTopCategory() {
		return topCategory;
	}
	public void setTopCategory(Category topCategory) {
		this.topCategory = topCategory;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public boolean isPublished() {
		return Status.PUBLISHED.equals(status);
	}
	public boolean isExpired() {
		return Status.EXPIRED.equals(status);
	}
	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
    public Date getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    @Transient
	public String getDescriptionDisplay() {
		if (description != null) {
			return description.replaceAll("\n", "<br />");
		} else {
			return "";
		} 
	}
}
