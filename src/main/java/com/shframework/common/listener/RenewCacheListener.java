package com.shframework.common.listener;

import static com.shframework.common.util.Constants.CK_QUEUE;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shframework.modules.basic.component.CacheComponent;

/**
 * 基于请求的缓存更新
 * @author OneBoA
 *
 */
public class RenewCacheListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		// 
	}

	@Override
	@SuppressWarnings( "rawtypes" )
	public void requestInitialized(ServletRequestEvent sre) {
		// poll(set);
		synchronized (CK_QUEUE) {
			
			if(!CK_QUEUE.isEmpty()) {
				
		    	ServletContext sc = sre.getServletContext();
		    	
		    	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(sc);
		    	
				CacheComponent cacheComponent = (CacheComponent) wac.getBean("cacheComponent");
		    	
				do {
					
					String key = CK_QUEUE.poll();
					sc.setAttribute(key, cacheComponent.resource(key));
					
				} while (CK_QUEUE.size() > 0);
				
			}
			
		}
		
	}
	
}
