package com.shframework.modules.dict.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.helper.DictCacheComponent;
import com.shframework.modules.dict.service.DictHelpService;

@Service
public class DictHelpServiceImpl implements DictHelpService {

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Autowired
	private DictCacheComponent dictCacheComponent;
	
	@Override
	public int getIdbyTitle(String dictKey, String title) {
		return getIdByParameter(dictKey, title, "title");
	}
	
	@Override
	public int getIdbyCode(String dictKey, String code) {
		return getIdByParameter(dictKey, code, "code");
	}
	
	public int getIdByParameter(String dictKey, String value, String parameterName){
		int resultId = 0;
		if (StringUtils.isBlank(dictKey)  || StringUtils.isBlank(value) ){
			return resultId;
		}
		resultId = dictCacheComponent.getIdFromMap( dictKey, value, parameterName);
		return resultId;
	}
	
	/**
	 * 根据 roleCode 取得数据库中的 roleId
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年6月10日 下午3:38:33
	 */
	@Override
	public Integer getRoleIdByRoleCode(String roleCode){
		return getIdbyCode("sysrole",roleCode);
	}
	
}
