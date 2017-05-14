
package com.shframework.common.resolver;
import java.util.HashMap;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.shframework.common.support.CacheFetch;

public class CacheFetchResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CacheFetch.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, 
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
//		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		
//		Map<String, Object> dictMap = (Map<String, Object>) request.getAttribute("alldict");
			
		
		
		return new HashMap<String, Object>();
	}

	

}
