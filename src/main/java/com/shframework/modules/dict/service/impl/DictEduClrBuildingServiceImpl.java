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
import com.shframework.modules.dict.entity.DictEduClrBuilding;
import com.shframework.modules.dict.mapper.DictEduClrBuildingMapper;
import com.shframework.modules.dict.service.DictSpecialService;

@Service("dictEduClrBuildingService")
public class DictEduClrBuildingServiceImpl implements DictSpecialService<DictEduClrBuilding>{
	@Autowired
	private DictEduClrBuildingMapper dictEduClrBuildingMapper;
	private static final String defaultSortField = "_building.code";//默认排序字段
	private static final String defaultSortOrderby = "asc";

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int deleteById(int id) {
		DictEduClrBuilding dict = dictEduClrBuildingMapper.selectByPrimaryKey(id);
         if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
            return 0;
        }
        dict.setCode(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_CODE, dict.getCode()));
        dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictEduClrBuildingMapper.updateByPrimaryKeySelective(dict);
		cacheComponent.renew(CacheComponent.KEY_BUILDING);
		cacheComponent.renew(CacheComponent.KEY_CASCADE_CAMPUS_BUILDING);
		return result;
	}

	@Override
	public PageTerminal<DictEduClrBuilding> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictEduClrBuildingMapper.countByMap(parMap);
		List<DictEduClrBuilding> majorList = null;
		if (count>0){
			majorList  =  dictEduClrBuildingMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduClrBuilding>(majorList, pageSupport, count);
	}

	@Override
	public DictEduClrBuilding getDict(int id) {
		return dictEduClrBuildingMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveDict(DictEduClrBuilding record) {
		try{
			if (record.getId()!=null && record.getId() > 0){
				return dictEduClrBuildingMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				return dictEduClrBuildingMapper.insertSelective(record);
			}
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_BUILDING);
			cacheComponent.renew(CacheComponent.KEY_CASCADE_CAMPUS_BUILDING);
		}
	}
	
}
