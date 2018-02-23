package se.goteborg.retursidan.portlet.controller.config;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.service.StatisticsService;
import se.goteborg.retursidan.service.VisitorLoggingService;

@Controller
@RequestMapping("EDIT")
public class StatisticsController extends BaseController {
	private static Logger logger = Logger.getLogger(StatisticsController.class.getName());
	
	@Autowired
	private VisitorLoggingService visitorLogging;
	
	@Autowired
	private StatisticsService statisticsService;
	
	@RenderMapping(params="page=statistics")
	public String showStatistics(RenderRequest request, RenderResponse response, Model model) {
	    logger.finest("entering statistics");
		Integer uniqueVisitors = visitorLogging.getUniqueVisitors();
		model.addAttribute("uniqueVisitors", uniqueVisitors);
		
		List<Unit> units = modelService.getUnits();
		model.addAttribute("units", units);

		Integer totalNumberOfAds = statisticsService.getTotalNumberOfAds();
		model.addAttribute("totalNumberOfAds", totalNumberOfAds);
		List<Integer> unitAdCount = new ArrayList<Integer>();
		for (Unit unit : units) {
			Integer count = statisticsService.getTotalAdsForUnit(unit);
			unitAdCount.add(count);
		}
		model.addAttribute("unitAdCount", unitAdCount);
		
		Integer totalNumberOfRequests = statisticsService.getTotalNumberOfRequests();
		model.addAttribute("totalNumberOfRequests", totalNumberOfRequests);
		List<Integer> unitRequestCount = new ArrayList<Integer>();
		for (Unit unit : units) {
			Integer count = statisticsService.getTotalRequestsForUnit(unit);
			unitRequestCount.add(count);
		}
		model.addAttribute("unitRequestCount", unitRequestCount);

		Integer totalNumberOfBookedAds = statisticsService.getTotalNumberOfBookedAds();
		model.addAttribute("totalNumberOfBookedAds", totalNumberOfBookedAds);
		List<Integer> unitBookedCount = new ArrayList<Integer>();
		for (Unit unit : units) {
			Integer count = statisticsService.getBookedAdsForUnit(unit);
			unitBookedCount.add(count);
		}
		model.addAttribute("unitBookedCount", unitBookedCount);
		
		model.addAttribute("totalNumberOfExpiredAds", statisticsService.getTotalNumberOfExpiredAds());
		return "config/statistics";
	}
}
