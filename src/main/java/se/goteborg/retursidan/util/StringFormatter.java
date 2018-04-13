package se.goteborg.retursidan.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringFormatter {

	public static String formatShortDate(Date created) {
		Calendar date = Calendar.getInstance();
	    date.setTime(created);
	    Calendar now = Calendar.getInstance();
	    DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
	    DateFormat weekdayTimeFormatter = new SimpleDateFormat("EEEE HH:mm");
	    DateFormat dateFormatter = new SimpleDateFormat("d MMMM HH:mm");
	    DateFormat yearDateFormatter = new SimpleDateFormat("yyyy-M-d HH:mm");
	    
	    if (date.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
	    	if (date.get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR)) {
	    		if (date.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
	    			return "Idag " + timeFormatter.format(created);
	    		} else if(date.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)-1) {
	    			return "Igår " + timeFormatter.format(created);
	    		} else {
	    			return weekdayTimeFormatter.format(created);
	    		}
	    	} else {
	    		return dateFormatter.format(created);
	    	}
	    } else {
	    	return yearDateFormatter.format(created);
	    }
	}

	public static String formatDateToDay(Date created) {
		Calendar date = Calendar.getInstance();
	    date.setTime(created);
	    
	    DateFormat yearDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	    
	    return yearDateFormatter.format(created);	    
	}
	
	public static boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}
}
