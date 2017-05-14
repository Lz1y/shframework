package com.shframework.common.notification.resolver;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.shframework.common.support.PageParam;

/**
 * 消息解析器
 * @author OneBoA
 *
 */
public class MessageMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(PageParam.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest httpRequest = (HttpServletRequest) webRequest.getNativeRequest();
        
		Enumeration<?> e = httpRequest.getParameterNames();
        while (e.hasMoreElements()) {
        	Object element = e.nextElement();
        		String key = element.toString();
        		String value = httpRequest.getParameter(key);
        		if (value == null || value.equals("")) {
					continue;
				}
			}
		
		return null;
	}

}
