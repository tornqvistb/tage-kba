package se.goteborg.retursidan.portlet.controller.config;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.service.ExcelViewBuilder;
import se.goteborg.retursidan.service.StatisticsService;

@Controller
@Transactional
@RequestMapping("EDIT")
public class AdvancedStatisticsResourceController extends BaseController {
	
	@Autowired
	private StatisticsService statisticsService;
		
	@ResourceMapping("advancedStatistics")
	public ModelAndView showStatistics(ResourceRequest request, ResourceResponse response, Model model) {
		Map<String, Object> excelModel = new HashMap<>();
		excelModel.put("year", request.getParameter("year"));
		excelModel.put("bookname", "Statistik");
		excelModel.put("statisticsService", statisticsService);
		excelModel.put("allUnits", modelService.getUnits());
		excelModel.put("subCategories", modelService.getAllSubCategoriesSorted());
		
        response.setContentType( "application/vnd.ms-excel" );
        response.setProperty("Content-Disposition", "attachment; filename=statistik-" + request.getParameter("year") + ".xls"); 
        return new ModelAndView(new ExcelViewBuilder(), excelModel);				
	}
}
