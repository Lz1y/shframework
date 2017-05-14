package com.shframework.modules.edu.tch.service;

import java.util.List;
import java.util.Map;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.edu.tch.entity.EduTchEmployee;
import com.shframework.modules.sys.entity.User;


public interface EmployeeService {

	PageTerminal<EduTchEmployee> list(Integer departmentId,PageSupport pageSupport);

	EduTchEmployee detail(Integer userId);

	void save(User user, EduTchEmployee eduTchEmployee)throws Exception ;

	void update(User user, EduTchEmployee eduTchEmployee);

	int delete(Integer userId, User sessionUser);

	PageTerminal<EduTchEmployee> listRecursionByDepartmentId(List<Integer> linkedListDepartmentId, PageSupport pageSupport);

	PageTerminal<EduTchEmployee> listAll(PageSupport pageSupport);
	
	/**
	 * 通过roleId 查找相应用户（教师）的id
	 * @param roleId
	 * @return
	 * @author RanWeizheng
	 */
	List<Integer> getUserIdListByRoleId(Integer roleId);

	void importExcelTeacher(User user, EduTchEmployee employee,
			Map<String, Object> beanMap);

	void importExcelEmployee(User user, EduTchEmployee employee,
			Map<String, Object> beanMap);
}
