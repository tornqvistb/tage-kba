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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import se.goteborg.retursidan.model.GeneralEntityBean;
import se.goteborg.retursidan.util.DateHelper;
import se.goteborg.retursidan.util.StringFormatter;

/**
 * Model bean representing an advertisement
 *
 */
@Entity
@Table(name="advertisement")
public class Advertisement extends GeneralEntityBean implements Serializable {
	private static final long serialVersionUID = 2549203246057963368L;

	/**
	 * Possible statuses of the advertisement 
	 */
	public enum Status {
	    PUBLISHED, BOOKED, EXPIRED, UNPUBLISHED, ALL
	}
	
	/**
	 * Possible display options of the advertisement 
	 */
	public enum DisplayOption {
		OWN_UNIT, ENTIRE_CITY
	}
	
	@Column(nullable=false)
	private Status status = Status.PUBLISHED;
	
	@DateTimeFormat(style="SS")
	@Column(nullable=false)
	private Date created = DateHelper.getCurrentDate();
	;
	
	@Column(nullable=false)
	private String creatorUid;

	@Column(nullable=false)
	private String title;
	
	@ManyToOne(optional=false)
	@Cascade({CascadeType.SAVE_UPDATE})
	private Person contact;

	@ManyToOne(optional=true)
	@Cascade({CascadeType.MERGE})
	private Person booker;
	
	@Transient
	private Category topCategory;
	
	@ManyToOne(optional=false)
	private Category category;
	
	@Column(nullable=false, length=1000)
	private String description;
	
	@ManyToOne(optional=false)
	private Unit unit;
	
	@Column(nullable=false)
	private String pickupAddress;
	
	@Column(length=1000)
	private String pickupConditions;
	
	@Column(nullable=false)
	private DisplayOption displayOption = DisplayOption.ENTIRE_CITY;
	
	@DateTimeFormat(style="SS")
	private Date publishDate = DateHelper.getCurrentDate();
	
	@Column(nullable=true)
	private Integer count;
	
	/**
	 * Fetch the photos lazy, we do not want to load the actual image data for each photo
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade=javax.persistence.CascadeType.REMOVE)
	@JoinTable(name = "photoset")
	@OrderBy("id ASC")
	private List<Photo> photos;
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}
	public String getFormattedCreatedDate() {
		return StringFormatter.formatShortDate(created);
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCreatorUid() {
		return creatorUid;
	}

	public void setCreatorUid(String creatorUid) {
		this.creatorUid = creatorUid;
	}

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
	public Person getBooker() {
		return booker;
	}

	public void setBooker(Person booker) {
		this.booker = booker;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Category getTopCategory() {
		return topCategory;
	}

	public void setTopCategory(Category topCategory) {
		this.topCategory = topCategory;
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

	public String getPickupAddress() {
		return pickupAddress;
	}

	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	public String getPickupConditions() {
		return pickupConditions;
	}

	public void setPickupConditions(String pickupConditions) {
		this.pickupConditions = pickupConditions;
	}

	public DisplayOption getDisplayOption() {
		return displayOption;
	}

	public void setDisplayOption(DisplayOption displayOption) {
		this.displayOption = displayOption;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	public boolean isPublished() {
		return Status.PUBLISHED.equals(status);
	}
	public boolean isBooked() {
		return Status.BOOKED.equals(status);
	}
	public boolean isExpired() {
		return Status.EXPIRED.equals(status);
	}

	public Date getPublishDate() {
		return publishDate;
	}
	public String getFormattedPublishDate() {
		return StringFormatter.formatShortDate(publishDate);
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	@Transient
	public String toString() {
		return "Advertisement [status=" + status + ", created=" + created + ", creatorUid=" + creatorUid + ", title="
				+ title + ", contact=" + contact + ", booker=" + booker + ", topCategory=" + topCategory + ", category="
				+ category + ", description=" + description + ", unit=" + unit + ", pickupAddress=" + pickupAddress
				+ ", pickupConditions=" + pickupConditions + ", displayOption=" + displayOption + ", publishDate="
				+ publishDate + ", photos=" + photos + "]";
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
