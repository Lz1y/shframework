package com.shframework.modules.dict.service;

import java.util.List;
import java.util.Map;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictEduCommonMajorDepartment;

public interface DictEduCommonMajorDepartmentService {

	/**
	 * 删除 指定记录
	 * @param majorDepartment  majorId, departmentId
	 * @return
	 */
	public int delete(DictEduCommonMajorDepartment majorDepartment);

	/**
	 * 查询数据
	 * @param pageSupport
	 * @return
	 */
	public PageTerminal<Map<String, Object>> findAllByPage(PageSupport pageSupport);
	
	public  List<Map<String, Object>> findAllByDepartmentId(Integer departmentId);

	/**
	 * 查找指定记录
	 * @param majorDepartment  majorId, departmentId
	 * @return
	 */
	public DictEduCommonMajorDepartment getDict(DictEduCommonMajorDepartment majorDepartment) ;

	/**
	 * 保存数据
	 * @param oldMajorId
	 * @param majorDepartment
	 * @return
	 */
	public int insertDict(DictEduCommonMajorDepartment majorDepartment);
	
	public int updateDict(Integer oldMajorId, DictEduCommonMajorDepartment majorDepartment);
	
	/**
	 * 根据departmentId删除数据，  暂时没有使用 ，只是预留出接口
	 * @param departmentId
	 * @return
	 * @author RanWeizheng
	 */
	public int deleteByDepartmentId(Integer departmentId);

}
