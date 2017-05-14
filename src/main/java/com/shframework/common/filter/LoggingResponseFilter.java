package com.shframework.common.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shframework.common.util.Constants;
import com.shframework.common.util.StringUtils;

import eu.bitwalker.useragentutils.UserAgent;

public class LoggingResponseFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(LoggingResponseFilter.class);
	private AtomicLong id = new AtomicLong(1);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (logger.isDebugEnabled()) {
			long requestId = id.incrementAndGet();
			request = new RequestWrapper(requestId, request);
			response = new ResponseWrapper(requestId, response);
		}
		try {
			filterChain.doFilter(request, response);
			// response.flushBuffer();
		} finally {
			if (logger.isDebugEnabled()) {
				if (!StringUtils.containsAny(request.getRequestURI(), Constants.WEB_RES_TYPE)) {
					logRequest(request);
					logResponse((ResponseWrapper) response);
				}
			}
		}
	}

	private void logRequest(HttpServletRequest request) {
		StringBuilder msg = new StringBuilder();
		if (request instanceof RequestWrapper) {
			msg.append("[REQUEST ID=").append(((RequestWrapper) request).getId()).append("] ");
		}
		msg.append("[HTTP METHOD=").append(request.getMethod()).append("] ");
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		msg.append("[IP=").append(ipAddress).append("] ");
		HttpSession session = request.getSession(false);
		if (session != null) {
			msg.append("[SESSION ID=").append(session.getId()).append("] ");
		}
		msg.append("[CONTENT TYPE=").append(request.getContentType()).append("] ");
		msg.append("[REFERER=").append(request.getHeader("referer")).append("] ");
		msg.append("[URI=").append(request.getRequestURI());
		if (StringUtils.isNotBlank(request.getQueryString())) {
			msg.append('?').append(request.getQueryString()).append("] ");
		} else {
			msg.append("] ");
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
		logger.debug(msg.toString());
	}

	private boolean isMultipart(HttpServletRequest request) {
		return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
	}

	private void logResponse(ResponseWrapper response) {
		StringBuilder msg = new StringBuilder();
		msg.append("[RESPONSE ID=").append((response.getId())).append("] ");
		msg.append("[CONTENT TYPE=").append(response.getContentType()).append("] ");
//		try {
//			msg.append("[PAYLOAD=").append(new String(response.toByteArray(), response.getCharacterEncoding())).append("] ");
//		} catch (UnsupportedEncodingException e) {
//			logger.warn("Failed to parse response payload", e);
//		}
		logger.debug(msg.toString());
	}
}
