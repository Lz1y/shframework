package com.shframework.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数封装工具类(可自行添加)
 * @author OneBoA
 *
 */
public final class ParamsUtil {

	/**
	 * @param flag
	 * @param username
	 * @param pageSupport
	 * @return
	 */
    public static Map<String, Object> createProcResMap(int flag, String username, PageSupport pageSupport) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", username);
		map.put("limitStart", pageSupport.getLimitStart());
		map.put("limitEnd", pageSupport.getLimitEnd());
		map.put("flag", flag);
		map.put("searchCondition", pageSupport.getParamVo().getSearchCondition());
        return map;
    }
    
    public static Map<String, Object> createProcResMap(PageSupport pageSupport) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limitStart", pageSupport.getLimitStart());
		map.put("limitEnd", pageSupport.getLimitEnd());
		map.put("searchCondition", pageSupport.getParamVo().getSearchCondition());
        return map;
    }

    public static Map<String, Object> createProcResMap(PageSupport pageSupport, String defaultSortField) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limitStart", pageSupport.getLimitStart());
		map.put("limitEnd", pageSupport.getLimitEnd());
		map.put("searchCondition", pageSupport.getParamVo().getSearchCondition());
		map.put("defaultSortField", defaultSortField);
        return map;
    }

	/**
	 * @param flag
	 * @param username
	 * @param pageSupport
	 * @param defaultSortField 默认排序字段(人工指定)
	 * @return
	 */
    public static Map<String, Object> createProcResMap(int flag, String username, PageSupport pageSupport, String defaultSortField) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", username);
		map.put("limitStart", pageSupport.getLimitStart());
		map.put("limitEnd", pageSupport.getLimitEnd());
		map.put("flag", flag);
		map.put("defaultSortField", defaultSortField);
		String searchCondition = pageSupport.getParamVo().getSearchCondition().trim();
		if (StringUtils.isNotEmpty(searchCondition)) {
			map.put("searchCondition", pageSupport.getParamVo().getSearchCondition());
		}
        return map;
    }
    
    public static String linkSearchCondition() {
    	
    	return "";
    }
    
    /**
     * 字典表查询参数封装
     * @param tableName
     * @param pageSupport
     * @return
     * @author RanWeizheng
     */
    public static Map<String, Object> createDictQueryParamMap(String tableName, PageSupport pageSupport, String defaultSortField){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("tableName", tableName);
		map.put("limitStart", pageSupport.getLimitStart());
		map.put("limitEnd", pageSupport.getLimitEnd());
		String searchConditionStr = pageSupport.getParamVo().getSearchCondition();
		if (StringUtils.isBlank(searchConditionStr) ){
			searchConditionStr = " order by " + defaultSortField;
		}else if (searchConditionStr.indexOf("order by") == -1){
			searchConditionStr += " order by " + defaultSortField;
		}
		map.put("searchCondition", searchConditionStr);
		map.put("defaultSortField", defaultSortField);
        return map;
    }
    
    
}
