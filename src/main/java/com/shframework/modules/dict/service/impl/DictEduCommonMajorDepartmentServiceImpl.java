package com.shframework.modules.dict.service.impl;

import static com.shframework.common.util.ParamsUtil.createDictQueryParamMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.entity.DictEduCommonMajorDepartment;
import com.shframework.modules.dict.entity.DictEduCommonMajorDepartmentKey;
import com.shframework.modules.dict.mapper.DictEduCommonMajorDepartmentMapper;
import com.shframework.modules.dict.service.DictEduCommonMajorDepartmentService;

/**
 * 专业-院系关联关系维护
 * @author RanWeizheng
 *
 */
@Service
public class DictEduCommonMajorDepartmentServiceImpl implements DictEduCommonMajorDepartmentService{

	@Autowired
	DictEduCommonMajorDepartmentMapper dictEduCommonMajorDepartmentMapper;
	private static final String defaultSortField = "_decmd.inner_code";//默认排序字段
	private static final String defaultSortOrderby = "asc";

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int delete(DictEduCommonMajorDepartment majorDepartment) {
		DictEduCommonMajorDepartmentKey key = getKey(majorDepartment);
		int result = dictEduCommonMajorDepartmentMapper.deleteByPrimaryKey(key);
		cacheComponent.renew(CacheComponent.KEY_MAJORDEPARTMENT);
		cacheComponent.renew(CacheComponent.KEY_CASCADE_DEP_MAJOR);
		return result;
	}

	/**
	 * 查询条件包括 ：
	 * departmentId,
	 * 分页，
	 * orderby
	 */
	@Override
	public PageTerminal<Map<String, Object>> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		Integer departmentId = pageSupport.getParamVo().getMap().get("departmentId")!=null 
												? Integer.parseInt((String)pageSupport.getParamVo().getMap().get("departmentId"))
												: null;
		parMap.put("departmentId", departmentId);
		
		int count = dictEduCommonMajorDepartmentMapper.countByMap(parMap);
		List<Map<String, Object>> list =  dictEduCommonMajorDepartmentMapper.selectByMap(parMap);
		return new PageTerminal<Map<String, Object>>(list, pageSupport, count);
	}
	
	
	/**
	 * 查询条件包括 ：
	 * departmentId,
	 * 分页，
	 * orderby
	 */
	@Override
	public List<Map<String, Object>> findAllByDepartmentId(
			Integer departmentId) {
		Map<String, Object> parMap = new HashMap<String, Object>();
		
		parMap.put("departmentId", departmentId);
		String searchCondition = " order by _decmd.inner_code";
		parMap.put("searchCondition", searchCondition);
		
		List<Map<String, Object>> list =  dictEduCommonMajorDepartmentMapper.selectByMap(parMap);
		return list;
	}

	@Override
	public DictEduCommonMajorDepartment getDict(
			DictEduCommonMajorDepartment majorDepartment) {
		DictEduCommonMajorDepartmentKey key = getKey(majorDepartment);
		return  dictEduCommonMajorDepartmentMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insertDict(DictEduCommonMajorDepartment majorDepartment) {
		try{
			return dictEduCommonMajorDepartmentMapper.insertSelective(majorDepartment); 
		} catch(DuplicateKeyException e) {
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_MAJORDEPARTMENT);
			cacheComponent.renew(CacheComponent.KEY_CASCADE_DEP_MAJOR);
		}
	}
	
	//事务
	@Override
	@Transactional
	public int updateDict(Integer oldMajorId, DictEduCommonMajorDepartment majorDepartment) {
		try{
			int result = 0;
			if (oldMajorId != null && oldMajorId > 0){//update
				if( majorDepartment.getMajorId() == oldMajorId){//只更新
					result =  dictEduCommonMajorDepartmentMapper.updateByPrimaryKeySelective(majorDepartment);
				} else {
					result = dictEduCommonMajorDepartmentMapper.insertSelective(majorDepartment); 
					if ( result > 0){
						DictEduCommonMajorDepartmentKey key = getKey(majorDepartment);
						key.setMajorId(oldMajorId);
						dictEduCommonMajorDepartmentMapper.deleteByPrimaryKey(key);
					}
				}
			}
			return result;
		} catch(DuplicateKeyException e){
		    return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_MAJORDEPARTMENT);
			cacheComponent.renew(CacheComponent.KEY_CASCADE_DEP_MAJOR);
		}
		
	}
	
	/**
	 * 封装Key
	 * @param majorDepartment
	 * @return
	 */
	private DictEduCommonMajorDepartmentKey getKey(DictEduCommonMajorDepartment majorDepartment){
		DictEduCommonMajorDepartmentKey key = new DictEduCommonMajorDepartmentKey();
		key.setDepartmentId(majorDepartment.getDepartmentId());
		key.setMajorId(majorDepartment.getMajorId());
		return key;
	}

	@Override
	public int deleteByDepartmentId(Integer departmentId) {
		
		return dictEduCommonMajorDepartmentMapper.deleteByDepartmentId(departmentId);
	}

}
