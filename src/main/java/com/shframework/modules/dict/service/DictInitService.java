package com.shframework.modules.dict.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shframework.modules.dict.entity.DictCommon;
import com.shframework.modules.dict.entity.DictEduCommonAcdYearTerm;

public interface DictInitService {

	/**
	 * 获取所有字典表的基础信息 包括 表名， 表中文说明， 对应的路径
	 * 
	 * @return
	 */
	public Map<String, String[]> getAllDictInfo();

	/**
	 * 获取所有的字典表数据，并放到map中
	 * 
	 * @return
	 */
	public Map<String, Object> getAllDictMap();

	Map<String, DictEduCommonAcdYearTerm> getYearTerm();

	/**
	 * 根据 new Date() 取得 yearTermCode
	 * 
	 * @param
	 * @return
	 * @memo
	 * @author wangkang
	 * @date 2015年6月3日 下午5:27:10
	 */
	public String getCurrYearTermCode();

	public String getPreOrNextYearTermCode(Integer index);

	/**
	 * 根据表名取得通用数据集合
	 * 
	 * @param
	 * @return
	 * @memo
	 * @author wangkang
	 * @date 2015年8月20日 下午4:44:16
	 * @deprecated by rwz 仅有一处使用，且存在效率问题，不如直接从缓存中取好
	 */
	@Deprecated
	public List<DictCommon> getCommonListByTableName(String tableName);

	/**
	 * 根据 yearTermCode 查找对象
	 * 
	 * @param
	 * @return
	 * @memo
	 * @author wangkang
	 * @date 2015年8月24日 下午6:54:31
	 */
	@Deprecated
	public DictEduCommonAcdYearTerm selectAcdYearTermByYearTermCode(String yearTermCode);

	/**
	 * 取得大于当前学期的学年学期id集合
	 * 
	 * @return
	 */
	List<Integer> getYearTermIdListOfGreaterCurrTerm();

	HashMap<String, Object> getTableMapByName(String tableName);

	Map<String, Object> getScrReason(Integer type);

	Map<String, Object> getAllSysSetting();

	Map<String, Object> getProvinceCascade();

	boolean isMuniPlace(DictCommon area);

	Map<String, Object> getAllDivision(Integer parentId);

	Map<String, Object> getSysRole(boolean isAllRole);

	Map<String, Object> getParentMajor();

	Map<String, Object> getExamMode();
	
	Map<String, Object> getExamMode(Integer examFlag);

	/**
	 * 所有学年学期的Map集合
	 * 
	 * @param
	 * @return key:yearTermCode(eg:2016-1) value:DictEduCommonAcdYearTerm
	 * @create wangkang 2016年10月27日 下午2:59:48
	 * @modify
	 */
	public Map<String, DictEduCommonAcdYearTerm> getAllYearTerm();
}