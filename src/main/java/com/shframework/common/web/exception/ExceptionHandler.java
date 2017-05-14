package com.shframework.common.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.filter.RequestWrapper;
import com.shframework.common.util.StringUtils;
import com.shframework.modules.sys.entity.User;

import eu.bitwalker.useragentutils.UserAgent;

public class ExceptionHandler implements HandlerExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
		
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView model = new ModelAndView("/errors/exception_error");
		model.addObject("exception", ex);
		model.addObject("httpStatusCode", "500");
		logger.error(getMsg(request, handler), ex);
		
		String[] exceptionTraces = ExceptionUtils.getStackFrames(ex);
		if (exceptionTraces != null && exceptionTraces.length > 0){
		model.addObject("rootCause" , exceptionTraces[0]);
		model.addObject("exceptionTraces" , exceptionTraces);
		}
		
		return model;
	}
	
	private String getMsg(HttpServletRequest request, Object handler){
		StringBuilder msg = new StringBuilder();
		if (request instanceof RequestWrapper) {
			msg.append("[REQUEST ID=").append(((RequestWrapper) request).getId()).append("] ");
		}
		msg.append("[URI=").append(request.getRequestURI());
		if (StringUtils.isNotBlank(request.getQueryString())) {
			msg.append('?').append(request.getQueryString()).append("] ");
		} else {
			msg.append("] ");
		}
		if (handler instanceof HandlerMethod && ((HandlerMethod) handler).getBean() instanceof BaseComponent) {
			User user = (User) request.getSession().getAttribute("curUser");
			if (user != null) {
				msg.append("[USER ID=").append(user.getId()).append("] ").append("[USER NAME=").append(user.getUsername()).append("] ");
			} else {
				msg.append("[USER ID=").append("] ").append("[USER NAME=").append("] ");
			}
		}
		msg.append("[HTTP METHOD=").append(request.getMethod()).append("] ");
		msg.append("[CONTENT TYPE=").append(request.getContentType()).append("] ");
		msg.append("[REFERER=").append(request.getHeader("referer")).append("] ");
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		msg.append("[IP=").append(ipAddress).append("] ");
		HttpSession session = request.getSession(false);
		if (session != null) {
			msg.append("[SESSION ID=").append(session.getId()).append("] ");
		}
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		msg.append("[DEVICE=").append(userAgent.getOperatingSystem().getDeviceType()).append("] ");
		msg.append("[USERAGENT=").append(userAgent).append("] ");
		msg.append("[BROWSER VERSION=").append(userAgent.getBrowserVersion()).append("] ");
		msg.append("[ENGINE=").append(userAgent.getBrowser().getRenderingEngine()).append("] ");
		
		return msg.toString();
		
	}
}
