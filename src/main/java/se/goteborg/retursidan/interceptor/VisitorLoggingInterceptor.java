package se.goteborg.retursidan.interceptor;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

import se.goteborg.retursidan.service.VisitorLoggingService;

/**
 * Interceptor called for every request that logs the visit
 *
 */
public class VisitorLoggingInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	VisitorLoggingService visitorLogging;
	
	/**
	 * Log the visit using the user id, or "anonymous" if no user is logged in.
	 */
	@Override
	public boolean preHandleRender(RenderRequest request,
			RenderResponse response, Object handler) throws Exception {
		ThemeDisplay td = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		User user = td.getUser();
		String uid = (user != null) ? user.getScreenName() : "anonymous";
		visitorLogging.logVisit(uid);
		return super.preHandleRender(request, response, handler);
	}
}
