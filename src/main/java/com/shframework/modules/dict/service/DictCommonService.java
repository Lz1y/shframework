package com.shframework.modules.dict.service;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictCommon;

/**
 * 通用结构表维护接口
 * @author RanWeizheng
 *
 */
public interface DictCommonService {
	
	/**
	 * 根据id ，删除数据,
	 * 此处是逻辑删除，只是将locked字段不为“1”（锁定）记录的status字段置为-1（逻辑删除）
	 * @param dictInfo
	 * @author RanWeizheng
	 */
	public int deleteById(String[] tableInfo, int id);
	
	/**
	 * 获取指定表的
	 * 此处是逻辑删除，只是将locked字段不为“1”（锁定）记录的status字段置为-1（逻辑删除）
	 * @param pageSupport
	 * @return
	 * @author RanWeizheng
	 */
	public PageTerminal<DictCommon> findAllByPage(PageSupport pageSupport, String tableName);
	
	/**
	 * 根据指定的表名和id，找到字典表记录
	 * @param tableName
	 * @param id
	 * @return
	 * @author RanWeizheng
	 */
	public DictCommon getDict(String tableName, int id);
	
	
	/**
	 * 保存字典表信息（通用）
	 * @param tableName
	 * @param dictCommon
	 * @return
	 * @author RanWeizheng
	 */
	public int saveDict(DictCommon dictCommon, String[] tableInfo);
		
}
