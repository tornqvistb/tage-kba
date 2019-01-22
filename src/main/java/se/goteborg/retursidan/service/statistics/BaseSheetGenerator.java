package se.goteborg.retursidan.service.statistics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import se.goteborg.retursidan.model.DateSpan;
import se.goteborg.retursidan.model.MonthData;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.service.StatisticsService;

public class BaseSheetGenerator {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    
	protected Workbook workbook;
	protected Map<String, Object> model;
	
	protected List<Unit> allUnits;
	protected List<Category> allCategories;
    protected StatisticsService statisticsService;
    protected CellStyle headerStyle;
    protected CellStyle dataStyle;
    protected String currentYear;
    
    
    @SuppressWarnings("unchecked")
    public BaseSheetGenerator(Workbook workbook, Map<String, Object> model) {
		super();
		this.workbook = workbook;
		this.model = model;
		this.allUnits = (List<Unit>) model.get("allUnits");
		this.allCategories = (List<Category>) model.get("subCategories");
		this.statisticsService = getStatisticsService();
		Font font = workbook.createFont();
		this.headerStyle = workbook.createCellStyle();
		font.setBold(true);
		this.headerStyle.setFont(font);
		this.dataStyle = workbook.createCellStyle();
		font.setBold(false);
		this.dataStyle.setFont(font);
		this.currentYear = (String) model.get("year");
	}

    protected int getYearFromDate(Date date) {
    	Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);	    	
    }
    
    protected List<MonthData> getMonthList() {
    	
    	List<MonthData> monthList = new ArrayList<>();

    	DateFormat formatter ; 
        formatter = new SimpleDateFormat("yyyy-MM-dd");

    	for (int month = 1; month <= 12; month++) {
    	    String monthDayPart = "-0" + month + "-01";
    	    if (month > 9) {
    	        monthDayPart = "-" + month + "-01";
    	    }
    	    Date startDate = null;
            try {
                startDate = formatter.parse(currentYear + monthDayPart);
            } catch (ParseException e) {
                logger.log(Level.WARNING, "Parseexception when creating monthlist");
            }
    	    DateSpan span = new DateSpan(startDate, DateUtils.addMonths(startDate, 1));
            MonthData monthData = new MonthData();
            monthData.setDateSpan(span);
            monthData.setYear(currentYear);
            DateFormat df = new SimpleDateFormat("MMM", new Locale("sv"));
            monthData.setMonth(df.format(startDate));
            monthList.add(monthData);
    	}
    	return monthList;
    }
    protected List<MonthData> getYearList() {
        
        List<MonthData> monthList = new ArrayList<>();
        try {
            DateFormat formatter ; 
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = formatter.parse(currentYear + "-01-01");        
            Date endDate = formatter.parse(currentYear + "-12-31");
            DateSpan span = new DateSpan(startDate, endDate);
            MonthData monthData = new MonthData();
            monthData.setYear(currentYear);
            monthData.setDateSpan(span);
            monthList.add(monthData);
        } catch (ParseException e) {
            logger.log(Level.WARNING, "Parseexception when creating yearlist");
        }
        return monthList;
    }
    private StatisticsService getStatisticsService() {
    	return (StatisticsService) model.get("statisticsService");
    }
}
