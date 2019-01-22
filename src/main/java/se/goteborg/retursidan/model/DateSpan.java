package se.goteborg.retursidan.model;

import java.util.Date;

/**
 * Class that holds a date span. Used for statistics.
 * 
 */
public class DateSpan {

	private Date fromDate;
	private Date toDate;
	
	public DateSpan(Date fromDate, Date toDate) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}	
	
}
