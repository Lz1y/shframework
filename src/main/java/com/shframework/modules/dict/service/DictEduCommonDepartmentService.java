package com.shframework.modules.dict.service;



import java.util.List;
import java.util.Map;

import com.shframework.common.util.Node;
import com.shframework.common.util.TreeNode;
import com.shframework.modules.dict.entity.DictEduCommonDepartment;

public interface DictEduCommonDepartmentService {
	
	public int deleteById( int id);

	/**
	 * 获得院系树
	 * @return
	 */
	public TreeNode<Node> getDepartmentTree();
	
	/**
	 * 获得部门树
	 * @return
	 */
	public TreeNode<Node> getDivisionTree();

	public DictEduCommonDepartment getDict(int id);

	public  int saveDict(DictEduCommonDepartment record);
	
	/**
	 * 获得符合条件的部门列表
	 * 
	 * @param departmentId
	 * @param allFlag  是否查询本栏目下的子栏目中的资源
	 * @return
	 */
	public List<DictEduCommonDepartment> findDivisionByDepartmentId(Integer departmentId, Integer allFlag);

	void findAllDivision(List<DictEduCommonDepartment> trees);

	public int getChildrenCount(Map<String, Object> parMap);
	
	
	public DictEduCommonDepartment selectByPrimaryKey(int id);
	
	public List<Integer> getAllDepartmentParentIds(Integer departmentId);
}
