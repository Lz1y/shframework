package com.shframework.common.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shframework.common.util.Constants;
import com.shframework.common.util.SiteMeshDomParse;
import com.shframework.modules.basic.component.CacheComponent;

public class InitCacheListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		CacheComponent<?> cacheComponent = (CacheComponent<?>) wac.getBean("cacheComponent");
		ServletContext context = event.getServletContext();

		context.setAttribute(CacheComponent.KEY_CONTEXTPATH, context.getContextPath());
		context.setAttribute(CacheComponent.KEY_SYSNAME, Constants.getProperty(Constants.SYSTEM_NAME));
		context.setAttribute(CacheComponent.KEY_VMROOTPATH, Constants.getProperty("tomcat.vm.directory.tms.static"));

		try {
			List<String> excludedPathList = SiteMeshDomParse.getExcludedPathList(context);
			if(excludedPathList != null && excludedPathList.size() > 0){
				context.setAttribute(Constants.EXCLUDED_PATH_LIST, excludedPathList);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String error = null;
		
		try {
			for (String key : CacheComponent.KEYS) {
				error = key;
				context.setAttribute(key, cacheComponent.resource(key));
			}
		} catch (Exception e) {
			System.out.println(" error key -=> " + error);
			e.printStackTrace();
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
	
}
