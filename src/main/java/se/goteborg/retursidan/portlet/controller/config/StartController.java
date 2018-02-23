package se.goteborg.retursidan.portlet.controller.config;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.portlet.controller.BaseController;

@Controller
@RequestMapping("EDIT")
public class StartController extends BaseController {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@RenderMapping
	public String render() {
	    logger.finest("entering default render");
		return "config/start";
	}
	/*
	@RenderMapping(params="page=adminRequests")
	public String adminRequests() {
	    logger.finest("entering adminRequests");
		return "config/admin_requests";
	}
	*/


}
