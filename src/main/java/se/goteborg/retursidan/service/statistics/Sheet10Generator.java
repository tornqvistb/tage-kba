package se.goteborg.retursidan.service.statistics;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import se.goteborg.retursidan.model.MonthData;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Unit;

public class Sheet10Generator extends BaseSheetGenerator {
	
    public Sheet10Generator(Workbook workbook, Map<String, Object> model) {
		super(workbook, model); 
	}

    public void createSheet() {
    	
    	Sheet sheet = workbook.createSheet("Efterl. per kat. förv.bolag");
        sheet.setDefaultColumnWidth((short) 20);
        Row pageHeaderRow = sheet.createRow(0);
        
        Cell pageHeaderCell = pageHeaderRow.createCell(0);	        
        pageHeaderCell.setCellStyle(headerStyle);
        pageHeaderCell.setCellValue("Antal publicerade efterlysningar per kategori, per förvaltning/bolag, per år och totalt");
        
        //Create year header
        Row subHeaderRow = sheet.createRow(2);
        
        Cell headerCell = subHeaderRow.createCell(0);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Kategori");
        
        //Loop through the years
        List<MonthData> yearList = getYearList();
        
        int cellNumber = 1;
        for (MonthData year : yearList) {
            for (Unit unit : allUnits) {
            	Cell cell = subHeaderRow.createCell(cellNumber);
            	cell.setCellStyle(headerStyle);
            	cell.setCellValue(unit.getName());            	
            	cellNumber++;
            }
            Cell cell = subHeaderRow.createCell(cellNumber);
        	cell.setCellStyle(headerStyle);
        	cell.setCellValue("Totalt antal " + year.getYear());            	
        	cellNumber++;
        }                
        
        
        int rowNumber = 3;
        for (Category category : allCategories) {
        	Row row = sheet.createRow(rowNumber);
        	Cell cell = row.createCell(0);        	
        	cell.setCellValue(category.getParent().getTitle() + " / " + category.getTitle());
        	cellNumber = 1;
        	for (MonthData year : yearList) {
                for (Unit unit : allUnits) {
                	cell = row.createCell(cellNumber);
            		int count = statisticsService.getNumberOfRequestsForCategoryAndUnitByPeriod(year.getDateSpan(), category, unit);
            		cell.setCellValue(String.valueOf(count));
                	cellNumber++;
                }
                Cell cellTotal = row.createCell(cellNumber);
                cellTotal.setCellStyle(headerStyle);
                int count = statisticsService.getNumberOfRequestsForCategoryByPeriod(year.getDateSpan(), category);
                cellTotal.setCellValue(String.valueOf(count));            	
            	cellNumber++;
            }        
        	rowNumber++;
        }
        Row row = sheet.createRow(rowNumber);
		Cell cell = row.createCell(0);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Totalt");
        cellNumber = 1;
    	for (MonthData year : yearList) {
            for (Unit unit : allUnits) {
            	cell = row.createCell(cellNumber);
            	cell.setCellStyle(headerStyle);
        		int count = statisticsService.getNumberOfRequestsForUnitByPeriod(unit, year.getDateSpan());
        		cell.setCellValue(String.valueOf(count));
            	cellNumber++;
            }
            Cell cellTotal = row.createCell(cellNumber);
            cellTotal.setCellStyle(headerStyle);
            int count = statisticsService.getNumberOfRequestsByPeriod(year.getDateSpan());
            cellTotal.setCellValue(String.valueOf(count));            	
        	cellNumber++;
        }        
    }
}
