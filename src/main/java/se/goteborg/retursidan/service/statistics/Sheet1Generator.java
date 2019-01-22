package se.goteborg.retursidan.service.statistics;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import se.goteborg.retursidan.model.MonthData;

public class Sheet1Generator extends BaseSheetGenerator {
	
    public Sheet1Generator(Workbook workbook, Map<String, Object> model) {
		super(workbook, model);
	}
	
    public void createSheet() {
    	Sheet sheet = workbook.createSheet("Annonser totalt + per mån. år");
        sheet.setDefaultColumnWidth((short) 30);
        Row pageHeaderRow = sheet.createRow(0);
        
        Cell pageHeaderCell = pageHeaderRow.createCell(0);	        
        pageHeaderCell.setCellStyle(headerStyle);
        pageHeaderCell.setCellValue("Totalt antal publicerade annonser per månad, per år och totalt");
        
        //Create year header
        Row subHeaderRow = sheet.createRow(2);
        	        
        Cell headerCell = subHeaderRow.createCell(0);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("År");
        
        headerCell = subHeaderRow.createCell(1);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Månad + Totalt");
        
        headerCell = subHeaderRow.createCell(2);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Antal publicerade annonser");
        
        List<MonthData> monthList = getMonthList();
                
        int rowNumber = 3;
        for (MonthData month : monthList) {
        	Row dataRow = sheet.createRow(rowNumber);
	        Cell cell = dataRow.createCell(0);
	        cell.setCellStyle(dataStyle);
	        cell.setCellValue(month.getYear());
	        
	        cell = dataRow.createCell(1);
	        cell.setCellValue(month.getMonth());
        	
	        int count = statisticsService.getNumberOfAdsByPeriod(month.getDateSpan());
	        cell = dataRow.createCell(2);
	        cell.setCellValue(String.valueOf(count));		        
	        
        	rowNumber++;
        	        	
       		if (month.spanOver2Years()) {
        		Row summaryRow = sheet.createRow(rowNumber);
        		
		        Cell cellSummary = summaryRow.createCell(1);
		        cellSummary.setCellStyle(dataStyle);
		        int year = getYearFromDate(month.getDateSpan().getFromDate());
		        cellSummary.setCellValue("Totalt Jan - Dec " + year);
				count = statisticsService.getNumberOfAdsByPeriod(month.getDateSpanForYear());
				cellSummary = summaryRow.createCell(2);
				cellSummary.setCellValue(String.valueOf(count));		        
		         
        		rowNumber++;
        	}
        }	        	    		    	
    }
}
