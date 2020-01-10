package se.goteborg.retursidan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Help class for returning current date. Compensates 1 hour time diff from server.
 * 
 * @author BJOTOR1216
 *
 */

public class DateHelper {
	
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(new Date());
	    cal.add(Calendar.HOUR_OF_DAY, 1);
		return cal.getTime();
	} 
	public static Date getDateFromString(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(dateStr);
	}
}
