package com.shframework.modules.dict.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.shframework.common.util.Constants;
import com.shframework.common.util.Node;
import com.shframework.common.util.RecordDeleteUtil;
import com.shframework.common.util.TreeNode;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.entity.DictEduCommonDepartment;
import com.shframework.modules.dict.entity.DictEduCommonDepartmentExample;
import com.shframework.modules.dict.mapper.DictEduCommonDepartmentMapper;
import com.shframework.modules.dict.mapper.DictEduCommonMajorDepartmentMapper;
import com.shframework.modules.dict.service.DictEduCommonDepartmentService;

@Service("dictEduCommonDepartmentService")
public class DictEduCommonDepartmentServiceImpl implements DictEduCommonDepartmentService{

	@Autowired
	DictEduCommonDepartmentMapper dictEduCommonDepartmentMapper;
	@Autowired
	DictEduCommonMajorDepartmentMapper dictEduCommonMajorDepartmentMapper;
	
	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	@Transactional
	public int deleteById(int id) {
		DictEduCommonDepartmentExample example = new DictEduCommonDepartmentExample();
		example.createCriteria().andParentIdEqualTo(id)
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);//指定父Id 和 删除状态不为
		int childNum = dictEduCommonDepartmentMapper.countByExample(example);
		if (childNum > 0){
			return 0;
		}
		example.clear();
		DictEduCommonDepartment temp = dictEduCommonDepartmentMapper.selectByPrimaryKey(id);
		if (temp == null || temp.getLocked()==Constants.DICT_COMMON_YES || temp.getLogicDelete()==Constants.DICT_COMMON_YES){
			return 0;
	    }
		temp.setCode(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_CODE, temp.getCode()));
	    temp.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, temp.getTitle()));
		temp.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictEduCommonDepartmentMapper.updateByPrimaryKeySelective(temp);
		if (result > 0){
			//删除院系专业关联
			dictEduCommonMajorDepartmentMapper.deleteByDepartmentId(id);
			if (temp!= null && temp.getId()!=null)//存在父节点，则对父节点修改其 has_child属性
				updateParentStatus(temp.getParentId(), Constants.DICT_COMMON_NO);
		}
		cacheComponent.renew(CacheComponent.KEY_DEPARTMENT);
		cacheComponent.renew(CacheComponent.KEY_DIVISION);
		return result;
	}

	@Override
	public TreeNode<Node> getDepartmentTree(){
		return getTree("department");
	}
	
	@Override
	public TreeNode<Node> getDivisionTree(){
		return getTree("division");
	}
	
	/**
	 * 获取院系的数据
	 * @author RanWeizheng
	 * @param type 主要用来判断是否要获得“部门的信息” 
	 * @return
	 */
	private TreeNode<Node> getTree(String type){
		DictEduCommonDepartment department = getDict(Constants.DEPARTMENT_ROOT_ID);
		Node tmb = dozerBeanMapper.map(department, Node.class);
		TreeNode<Node> tn = new TreeNode<Node>(tmb);
		recursionTree(tn, type);
		return tn;
	}

	/**
	 * 院系树 递归查询
	 * @param tn
	 * @param type 规定获取的类型 院系(department)/全部(all)
	 */
	private void recursionTree(TreeNode<Node> tn, String type) {
		DictEduCommonDepartmentExample ex = new DictEduCommonDepartmentExample();
		if ("department".equals(type)){//只获取二级学院和院系
			ex.createCriteria()
				.andTypeNotEqualTo(Constants.DEPARTMENT_TYPE_DIVISION)
				.andParentIdEqualTo(tn.getData().getId())
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
		} else {
			ex.createCriteria()
				.andParentIdEqualTo(tn.getData().getId())
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
		}
		List<DictEduCommonDepartment> list = dictEduCommonDepartmentMapper.selectByExample(ex);
		if (list != null && list.size() > 0) {
			for (DictEduCommonDepartment item : list) {
				Node tmb = dozerBeanMapper.map(item, Node.class);
				tn.addChild(tmb);
			}//for
			for (TreeNode<Node> item : tn.getChildren()) {
				recursionTree(item, type);
			}//for
		}//if
	}
	
	@Override
	public DictEduCommonDepartment getDict(int id) {
		return dictEduCommonDepartmentMapper.selectByPrimaryKey(id);
	}

	@Override 
	@Transactional
	public int saveDict(DictEduCommonDepartment record) {
		
		try{
			if (record.getId() != null && record.getId() >0){
				return dictEduCommonDepartmentMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setHasChild(Constants.DICT_COMMON_NO);
				record.setLocked(Constants.DICT_COMMON_NO);
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				record.setStatus(Constants.DICT_COMMON_YES);
				if (record.getPriority() == null || record.getPriority() <1){
					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
				}
				updateParentStatus(record.getParentId(), Constants.DICT_COMMON_YES);
				return dictEduCommonDepartmentMapper.insertSelective(record);
			}
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_DEPARTMENT);
			cacheComponent.renew(CacheComponent.KEY_DIVISION);
		}
	}
	
	/**
	 * 更新父节点“has_child”字段状态  
	 * @param parentId
	 * @param flag  0: 删除时， 1 ： 增加时
	 */
	private void updateParentStatus(Integer parentId, Integer flag ){
		DictEduCommonDepartment dict = new DictEduCommonDepartment();
		dict.setId(parentId);
		if (flag == Constants.DICT_COMMON_YES){
			dict.setHasChild(Constants.DICT_COMMON_YES);
			dictEduCommonDepartmentMapper.updateByPrimaryKeySelective(dict);
		} 
		else if(flag == Constants.DICT_COMMON_NO){
			DictEduCommonDepartmentExample example = new DictEduCommonDepartmentExample();
			example.createCriteria().andParentIdEqualTo(parentId).andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			List<DictEduCommonDepartment> departments = dictEduCommonDepartmentMapper.selectByExample(example);
			if (departments == null  || departments.size()==0){//没有子节点了
				dict.setHasChild(Constants.DICT_COMMON_NO);
				dictEduCommonDepartmentMapper.updateByPrimaryKeySelective(dict);
			}//if
		} //if-else
	}
	
	public List<DictEduCommonDepartment> findDivisionByDepartmentId(Integer departmentId, Integer allFlag){
		DictEduCommonDepartmentExample example = new DictEduCommonDepartmentExample();
		example.createCriteria().andStatusEqualTo(Constants.DICT_COMMON_YES)
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
		if (Constants.DICT_COMMON_YES == allFlag){
			List<Integer> parentList = new ArrayList<Integer>();
			parentList.add(departmentId);
			parentList.addAll(getAllDepartmentParentIds(departmentId));
			example.getOredCriteria().get(0).andParentIdIn(parentList);
		} else {
			example.getOredCriteria().get(0).andParentIdEqualTo(departmentId);
		}
		return dictEduCommonDepartmentMapper.selectByExample(example);
	}
	
	//获得所有的子节点id，并放入List    递归，比较麻烦
	public List<Integer> getAllDepartmentParentIds(Integer departmentId){
//		DictEduCommonDepartmentExample example = new DictEduCommonDepartmentExample();
//		example.createCriteria().andStatusEqualTo(Constants.DICT_COMMON_YES)
//			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO)
//			.andParentIdEqualTo(departmentId);
//		List<DictEduCommonDepartment> dList =  dictEduCommonDepartmentMapper.selectByExample(example);

		Map<String, DictEduCommonDepartment> dMap = (Map<String, DictEduCommonDepartment>) cacheComponent.resource(CacheComponent.KEY_DIVISION);
		List<DictEduCommonDepartment> dList =  new ArrayList<>();
		for (Map.Entry<String, DictEduCommonDepartment> entry : dMap.entrySet()) {
			DictEduCommonDepartment dep = new Gson().fromJson( new Gson().toJson(entry.getValue()), DictEduCommonDepartment.class);
		     if (dep.getParentId() == departmentId.intValue()){
		    	 dList.add(dep);
		     }
		}
		
		List<Integer> list = new LinkedList<Integer>();
		for (DictEduCommonDepartment dep : dList){
			if(list.contains(dep.getId())){//防止遇到数据异常时，出现死循环
				continue;
			}
			list.add(dep.getId());
			if (Constants.DICT_COMMON_YES == dep.getHasChild()){//如果有子节点，则往下继续找
				list.addAll(getAllDepartmentParentIds(dep.getId()));
			}
		}
		return list;
	}

	/**
	 * 获取所有的部门(包含根节点)
	 * @return
	 */
	@Override
	public void findAllDivision(List<DictEduCommonDepartment> trees){
		DictEduCommonDepartmentExample example = new DictEduCommonDepartmentExample();
		example.createCriteria().
			andStatusEqualTo(Constants.DICT_COMMON_YES)
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
		List<DictEduCommonDepartment> list = dictEduCommonDepartmentMapper.selectByExample(example);
		
		if(list != null){
			int size = list.size();		
			if (list != null && size > 0) {
				for(int i = 0;i<size;i++){
					DictEduCommonDepartment department=list.get(i);
					trees.add(department);
				}
			}
		}
		
	}

	@Override
	public int getChildrenCount(Map<String, Object> parMap) {
		return dictEduCommonDepartmentMapper.getChildrenCount(parMap);
	}

	@Override
	public DictEduCommonDepartment selectByPrimaryKey(int id) {
		return dictEduCommonDepartmentMapper.selectByPrimaryKey(id);
	}
	
}
