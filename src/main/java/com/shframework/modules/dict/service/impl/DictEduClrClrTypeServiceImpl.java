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
import com.shframework.modules.dict.entity.DictEduClrClrType;
import com.shframework.modules.dict.mapper.DictEduClrClrTypeMapper;
import com.shframework.modules.dict.service.DictSpecialService;

@Service("dictEduClrClrTypeService")
public class DictEduClrClrTypeServiceImpl implements DictSpecialService<DictEduClrClrType>{
	@Autowired
	private DictEduClrClrTypeMapper dictEduClrClrTypeMapper;
	private static final String defaultSortField = "_clrtype.id";//默认排序字段
	private static final String defaultSortOrderby = "asc";

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int deleteById(int id) {
		DictEduClrClrType dict = dictEduClrClrTypeMapper.selectByPrimaryKey(id);
         if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
            return 0;
        }
        dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictEduClrClrTypeMapper.updateByPrimaryKeySelective(dict);
		cacheComponent.renew(CacheComponent.KEY_CLRTYPE);
		return result;
	}

	@Override
	public PageTerminal<DictEduClrClrType> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictEduClrClrTypeMapper.countByMap(parMap);
		List<DictEduClrClrType> majorList = null;
		if (count>0){
			majorList  =  dictEduClrClrTypeMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduClrClrType>(majorList, pageSupport, count);
	}

	@Override
	public DictEduClrClrType getDict(int id) {
		return dictEduClrClrTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveDict(DictEduClrClrType record) {
		try{
			if (record.getId()!=null && record.getId() > 0){
				return dictEduClrClrTypeMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				return dictEduClrClrTypeMapper.insertSelective(record);
			}
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_CLRTYPE);
		}
	}
}
