/**
 * @description 
 * @author Josh Yan
 * @version 1.0
 * @datetime 2014年9月1日 下午4:33:27
 */
package com.shframework.common.interceptor;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.filter.RequestWrapper;
import com.shframework.common.util.Constants;
import com.shframework.common.util.StringUtils;
import com.shframework.modules.sys.entity.User;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * @author Josh
 *
 */
public class LoggingResponseInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(LoggingResponseInterceptor.class);
	private AtomicLong id = new AtomicLong(1);

	// before the actual handler will be executed
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		return true;
	}

	// after the handler is executed
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//		if (logger.isInfoEnabled()) {
			long requestId = id.incrementAndGet();
			request = new RequestWrapper(requestId, request);
			if (!StringUtils.containsAny(request.getRequestURI(), Constants.WEB_RES_TYPE)) {
				logRequest(request, handler);
			}
//		}
	}
	
	private void logRequest(HttpServletRequest request, Object handler) {
		
		long startTime = (Long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;
		
		StringBuilder msg = new StringBuilder();
		if (request instanceof RequestWrapper) {
			msg.append("[REQUEST ID=").append(((RequestWrapper) request).getId()).append("] ");
		}
		msg.append("[EXECUTE TIME=").append(executeTime).append("ms] ");
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
		if (request instanceof RequestWrapper && !isMultipart(request)) {
			RequestWrapper requestWrapper = (RequestWrapper) request;
			try {
				String charEncoding = requestWrapper.getCharacterEncoding() != null ? requestWrapper.getCharacterEncoding() : "UTF-8";
				msg.append("[PAYLOAD=").append(new String(requestWrapper.toByteArray(), charEncoding)).append("] ");
			} catch (UnsupportedEncodingException e) {
				logger.warn("Failed to parse request payload", e);
			}
		}
		logger.info(msg.toString());
	}
	
	private boolean isMultipart(HttpServletRequest request) {
		return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
	}
	
}
