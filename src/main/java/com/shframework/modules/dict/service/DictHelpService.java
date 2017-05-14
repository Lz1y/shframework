package com.shframework.modules.dict.service;


/**
 * 处理一些缓存操作
 * @author RanWeizheng
 *
 */
public interface DictHelpService {

	/**
	 * 根据内容获取指定表中的id
	 * @param dictKey
	 * @param content
	 * @return
	 */
	public int getIdbyTitle(String dictKey, String content);
	
	/**
	 * 根据code获取指定表中的id
	 * @param dictKey
	 * @param code
	 * @return
	 */
	public int getIdbyCode(String dictKey, String code);
	
	/**
	 * 根据 指定属性的 值 获取指定表中的id
	 * @param dictKey
	 * @param value
	 * @param parameterName
	 * @return
	 */
	public int getIdByParameter(String dictKey, String value, String parameterName);
	
	/**
	 * 根据 roleCode 取得数据库中的 roleId
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年6月10日 下午3:38:33
	 */
	public Integer getRoleIdByRoleCode(String roleCode);
}
