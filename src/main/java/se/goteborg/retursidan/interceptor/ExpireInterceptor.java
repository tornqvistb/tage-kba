package se.goteborg.retursidan.interceptor;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.model.form.Texts;
import se.goteborg.retursidan.service.ExpireService;

/**
 * Request interceptor that is configured in the portlet Spring XML. Is run at a
 * preset interval when a render request is issued and expires any ads and requests that
 * are older than the configured expire time.
 */
public class ExpireInterceptor extends HandlerInterceptorAdapter {
	private static long lastRun = 0;
	private static final long INTERVAL = 60 * 1000; // 60 minutes
	
	@Autowired
	ExpireService expireService;
	
	/**
	 * This method is called before every render request, every 60 minutes (or more)
	 * it will call the expire methods on the ExpireService to expire ads and requests.
	 */
	@Override
	public boolean preHandleRender(RenderRequest request,
			RenderResponse response, Object handler) throws Exception {
		synchronized (this) {
			// only run once every interval
			if (lastRun < (System.currentTimeMillis() - INTERVAL) ) {
				lastRun = System.currentTimeMillis();
				Config config = new Config(request.getPreferences());
				Texts texts = new Texts(request.getPreferences());
				expireService.expireAds(config.getAdExpireTimeInt(), texts, request);
				expireService.expireRequests(config.getRequestExpireTimeInt(), texts, request);
			}
		}
		return super.preHandleRender(request, response, handler);
	}
}
