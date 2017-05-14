package com.shframework.common.resolver;

import static com.shframework.common.util.StringUtils.replaceValueSpecialCharacter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.shframework.common.base.vo.ParamVo;
import com.shframework.common.support.DictParam;
import com.shframework.common.support.PageParam;
import com.shframework.common.util.Constants;
import com.shframework.common.util.DateUtils;
import com.shframework.common.util.PageSupport;

import static com.shframework.common.util.Constants.getProperty;

public class PageSupportResolver implements HandlerMethodArgumentResolver {
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return (parameter.hasParameterAnnotation(PageParam.class) || parameter.hasParameterAnnotation(DictParam.class));
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		this.supportsParameter(parameter);
		
		HttpServletRequest httpRequest = (HttpServletRequest) webRequest.getNativeRequest();

		String curPage = "1";
		String pageSize = StringUtils.equals(getProperty("page.pagesize"), "-9")?"99999":getProperty("page.pagesize");

		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer parm = new StringBuffer("?p_=1");
		Map<String, Object> parmMap = new HashMap<String, Object>();
		StringBuffer searchCondition = new StringBuffer();

		StringBuffer pRule = (StringBuffer) httpRequest.getSession().getAttribute("pRule");
		
		StringBuffer searchSort = new StringBuffer();
		Enumeration<?> e = httpRequest.getParameterNames();
		while (e.hasMoreElements()) {
			Object element = e.nextElement();
			String key = element.toString();
			if (StringUtils.indexOf(key, '?') != -1) continue;
			
			String value = replaceValueSpecialCharacter(httpRequest.getParameter(key));
			if (StringUtils.isNotEmpty(value)) {
				if ((StringUtils.indexOf(pRule, Constants.SYS_PERMISSION_LIST) != -1 || StringUtils.indexOf(key, "p_") == 0) 
						&& !StringUtils.equals(key, "p_")) {
					boolean flag = StringUtils.equals(value, "99999");
					if (StringUtils.indexOf(key, "p_") != 0) {
						parm.append("&p_" + key + "=" + (flag?0:value));
						parmMap.put("p_" + key, (flag?0:value));
					} else {
						parm.append("&" + key + "=" + (flag?0:value));
						parmMap.put(key, (flag?0:value));
					}
				}
				
				if (StringUtils.indexOf(key, "p_") == 0) key = StringUtils.replaceOnce(key, "p_", "");
				String keys[] = StringUtils.split(key, '.');
				if (keys.length == 1) {
					switch (key) {
					case "p":
						curPage = value;
						break;
					case "ps":
						pageSize = StringUtils.equals(value, "-9") ? "99999" : value;
						break;
					case "page":
						break;
					case "field":
						String orderby = httpRequest.getParameter("orderby");
						String p_orderby = httpRequest.getParameter("p_orderby");
						searchSort.append(" order by ").append(value).append(" ");
						if (StringUtils.isNotEmpty(orderby) || StringUtils.isNotEmpty(p_orderby)) searchSort.append(StringUtils.isEmpty(orderby)?p_orderby:orderby);
						else searchSort.append("desc");
						break;
					}
				} else if (keys.length == 2) {
					searchCondition.append(" and " + key + " = '" + value + "'");
				} else if (keys.length == 3) {
					String fKey = ((StringUtils.indexOf(keys[0], "p_") == 0)?StringUtils.replaceOnce(keys[0], "p_", ""):keys[0])+"."+keys[1];
					
					Integer fType = Integer.parseInt(keys[2]);
					switch (fType) {
						case Constants.SEARCH_FIELD_HANDLE_TYPE_ONE:
							searchCondition.append(" and " + fKey + " like '" + value + "%'");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_TWO:
							searchCondition.append(" and " + fKey + " like '%" + value + "'");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_THREE:
							searchCondition.append(" and " + fKey + " like '%" + value + "%'");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_FOUR:
							searchCondition.append(" and " + fKey + " >= '" + value + "'");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_FIVE:
							searchCondition.append(" and " + fKey + " > '" + value + "'");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_SIX:
							searchCondition.append(" and " + fKey + " <= '" + value + "'");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_SEVEN:
							searchCondition.append(" and " + fKey + " < '" + value + "'");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_EIGHT:// just INT
							if (StringUtils.equals(value, "0")) searchCondition.append(" and " + fKey + " = 0 ");
							else searchCondition.append(" and " + fKey + " <> 0 ");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_NINE:
							if (StringUtils.equals(value, "0")) searchCondition.append(" and " + fKey + " is null ");
							else searchCondition.append(" and " + fKey + " is not null ");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_TEN:
							searchCondition.append(" and " + fKey + " <> '" + value + "'");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_ELEVEN:
							searchCondition.append(" and " + fKey + " >= '" + value + "'");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_TWELVE:
							searchCondition.append(" and " + fKey + " <= '" + DateUtils.getOffsetDate(value, 1) + "'");
							break;
						case Constants.SEARCH_FIELD_HANDLE_TYPE_IN:
							searchCondition.append(" and " + fKey + " in (" + value + ")");
							break;
					}
					map.put(fKey, value);
				}
				map.put(key, value);
			} 
		}
		
		if (StringUtils.isNotEmpty(searchSort)) searchCondition.append(searchSort);
		if ((StringUtils.equals(httpRequest.getParameter("page"), "0")) || (Integer.parseInt(curPage) != 1 
		&& (Integer.parseInt(curPage) - 1) * Integer.parseInt(pageSize) >= Integer.parseInt(httpRequest.getParameter("t")!=null?
				httpRequest.getParameter("t"):httpRequest.getParameter("p_t")))) curPage = "1";
		
		return PageSupport.createCriteria(Integer.parseInt(curPage), Integer.parseInt(pageSize), new ParamVo(0, searchCondition.toString(), map, parm, parmMap));
	}
	
}
