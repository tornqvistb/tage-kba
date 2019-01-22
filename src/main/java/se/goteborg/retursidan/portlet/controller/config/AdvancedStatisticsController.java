package se.goteborg.retursidan.portlet.controller.config;

import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.portlet.controller.BaseController;

@Controller
@Transactional
@RequestMapping("EDIT")
public class AdvancedStatisticsController extends BaseController {
	private static final int STATISTICS_START_YEAR = 2017;
	
	@RenderMapping(params="page=advancedStatistics")
    public String showAdvancedStatistics(RenderRequest request, RenderResponse response, Model model) {
	    List<String> years  = new ArrayList<>();
	    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	    for (int year = STATISTICS_START_YEAR; year <= currentYear; year++) {
	        years.add(valueOf(year));
	    }
        model.addAttribute("years", years);
        return "config/advancedStatistics";
    }
	
}
