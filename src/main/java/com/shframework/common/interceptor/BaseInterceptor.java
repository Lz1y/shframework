package com.shframework.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shframework.common.base.BaseComponent;

/**
 * pre servlet-api
 * @author OneBoA
 *
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public synchronized boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod && ((HandlerMethod) handler).getBean() instanceof BaseComponent) 
			((BaseComponent) ((HandlerMethod) handler).getBean()).setServlet(request, response, request.getSession());
		return true;
	}
	
}
