package se.goteborg.retursidan.portlet.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.portlet.ModelAndView;

import se.goteborg.retursidan.model.entity.Person;
import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.model.form.Texts;
import se.goteborg.retursidan.service.ModelService;
import se.goteborg.retursidan.service.UserService;

/**
 * Base class for all controllers, implements common functionality
 *
 */
@Controller
public abstract class BaseController {
	private static Logger logger = Logger.getLogger(BaseController.class.getName());
	public static final String ADVERTISEMENT_URI = "?p_p_id=Retursidan_WAR_tageportlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_Retursidan_WAR_tageportlet_page=viewAd&_Retursidan_WAR_tageportlet_advertisementId=";
	public static final String REQUEST_URI = "?p_p_id=Retursidan_WAR_tageportlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_Retursidan_WAR_tageportlet_page=viewRequest&_Retursidan_WAR_tageportlet_requestId=";
	@Autowired
	protected ModelService modelService;
	
	@Autowired
	protected MessageSource messageSource;
	
	@Autowired
	protected UserService userService;
	
	@ModelAttribute("userId")
	public String getUserId(PortletRequest request) {
		return userService.getCurrentUserId(request);
	}

	@ModelAttribute("currentUser")
	public Person getCurrentUser(PortletRequest request) {
		return userService.getCurrentUser(request);
	}
	
	@ModelAttribute("config")
	public Config getConfig(PortletRequest request) {
		PortletPreferences prefs = request.getPreferences();
		return new Config(prefs);
	}

	@ModelAttribute("texts")
	public Texts getTexts(PortletRequest request) {
		PortletPreferences prefs = request.getPreferences();
		return new Texts(prefs);
	}

	/**
	 * Handle any uncaught exceptions during the request by redirecting
	 * the user to an error view where the exception is presented in a
	 * graceful way. 
	 * @param t The exception
	 * @return A ModelView object redirecting the user to the error view
	 */
	@ExceptionHandler(Throwable.class)
	public ModelAndView handleError(RuntimeException t) {
		ModelAndView modelAndView = new ModelAndView();
		logger.log(Level.SEVERE, "Uncaught exception thrown!", t);
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		modelAndView.getModelMap().addAttribute("error", t);
		modelAndView.getModelMap().addAttribute("stackTrace", sw.toString());
		modelAndView.setView("error");
		return modelAndView;
	}
	
}
