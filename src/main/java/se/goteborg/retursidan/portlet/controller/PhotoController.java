package se.goteborg.retursidan.portlet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.context.PortletContextAware;

/**
 * Controller responsible for serving up photos loaded from the database.
 * 
 */
@Controller
@RequestMapping("VIEW")
public class PhotoController extends PhotoBaseController implements PortletContextAware {
}
