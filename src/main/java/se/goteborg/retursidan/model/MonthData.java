package se.goteborg.retursidan.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MonthData {

	String month;
	String year;
	DateSpan dateSpan;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public DateSpan getDateSpan() {
		return dateSpan;
	}
	public void setDateSpan(DateSpan dateSpan) {
		this.dateSpan = dateSpan;
	}
	
	public boolean spanOver2Years() {
		return getYearFromDate(dateSpan.getFromDate()) != getYearFromDate(dateSpan.getToDate());		
	}
		
    private int getYearFromDate(Date date) {
    	Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);	    	
    }
    
	public DateSpan getDateSpanForYear() {
		DateSpan dateSpan = this.dateSpan;
		
		try {
			int year = getYearFromDate(dateSpan.getFromDate());
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			dateSpan = new DateSpan(
					(Date)formatter.parse((String.valueOf(year) + "-01-01")),
					(Date)formatter.parse((String.valueOf(year + 1) + "-01-01")));
		} catch (ParseException e) {
		}
    	
    	return dateSpan;
    }

	
}
