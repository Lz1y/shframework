package com.shframework.modules.dict.service.impl;

import static com.shframework.common.util.ParamsUtil.createDictQueryParamMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.common.util.RecordDeleteUtil;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.entity.DictCommonPolicitalStatus;
import com.shframework.modules.dict.mapper.DictCommonPolicitalStatusMapper;
import com.shframework.modules.dict.service.DictSpecialService;

@Service("dictPolicitalStatusService")
public class DictCommonPolicitalStatusServiceImpl implements DictSpecialService<DictCommonPolicitalStatus> {
	@Autowired
	private DictCommonPolicitalStatusMapper dictPolicitalStatusMapper;//政治面貌表
	private static final String defaultSortField = "_dcps.code";//默认排序字段
	private static final String defaultSortOrderby = "asc";
	
	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int deleteById(int id) {
		DictCommonPolicitalStatus dict = dictPolicitalStatusMapper.selectByPrimaryKey(id);
         if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
            return 0;
        }
        dict.setCode(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_CODE, dict.getCode()));
        dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result =  dictPolicitalStatusMapper.updateByPrimaryKeySelective(dict);
		cacheComponent.renew(CacheComponent.KEY_POLICITALSTATUS);
		return result;
	}

	@Override
	public PageTerminal<DictCommonPolicitalStatus> findAllByPage(
			PageSupport pageSupport) {
		
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictPolicitalStatusMapper.countByMap(parMap);
		List<DictCommonPolicitalStatus> list = null;
		if (count>0){
			list  =  dictPolicitalStatusMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictCommonPolicitalStatus>(list, pageSupport, count);
	}

	@Override
	public DictCommonPolicitalStatus getDict(int id) {
		return dictPolicitalStatusMapper.selectByPrimaryKey(id);
		
	}

	@Override
	public int saveDict(DictCommonPolicitalStatus record) {
		try{	
			if (record.getId() != null && record.getId()>0){
				return dictPolicitalStatusMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setLocked(Constants.DICT_COMMON_NO);
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				if (record.getPriority() == null || record.getPriority() <1){
					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
				}
				return dictPolicitalStatusMapper.insertSelective(record);
			}//if-else
		}  catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally{
			cacheComponent.renew(CacheComponent.KEY_POLICITALSTATUS);
		}
	}
}
