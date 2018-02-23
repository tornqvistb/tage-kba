package se.goteborg.retursidan.portlet.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import se.goteborg.retursidan.service.InitDataService;

/**
 * Init controller, called at portlet initialization. Will load initial data into the database
 * using the InitDataService.
 *
 */
@Controller
public class InitController {
	
	@Autowired
	private InitDataService initDataService;
	
	@PostConstruct
	public void init() {
		initDataService.loadInitialData();
	}
}
