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
import com.shframework.modules.dict.entity.DictEduCommonLabel;
import com.shframework.modules.dict.mapper.DictEduCommonLabelMapper;
import com.shframework.modules.dict.service.DictSpecialService;


/**
 * 标签字典表维护
 * @author RanWeizheng
 *
 */
@Service("dictLabelService")
public class DictEduCommonLabelServiceImpl implements DictSpecialService<DictEduCommonLabel> {

	@Autowired
	private DictEduCommonLabelMapper dictLabelMapper;//标签表
	private static final String defaultSortField = "_decl.code";//默认排序字段
	private static final String defaultSortOrderby = "asc";

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int deleteById(int id) {
		DictEduCommonLabel dict = dictLabelMapper.selectByPrimaryKey(id);
          if (dict == null || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
               return 0;
          }
        dict.setCode(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_CODE, dict.getCode()));
        dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictLabelMapper.updateByPrimaryKeySelective(dict);
		cacheComponent.renew(CacheComponent.KEY_LABEL);
		return result;
	}

	@Override
	public PageTerminal<DictEduCommonLabel> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictLabelMapper.countByMap(parMap);
		List<DictEduCommonLabel> majorList = null;
		if (count>0){
			majorList  =  dictLabelMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduCommonLabel>(majorList, pageSupport, count);
	}

	@Override
	public DictEduCommonLabel getDict(int id) {
		return dictLabelMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveDict(DictEduCommonLabel record) {
		try{
			if (record.getId()!=null && record.getId() > 0){
				return dictLabelMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				if (record.getPriority() == null || record.getPriority() <1){
					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
				}
				return dictLabelMapper.insertSelective(record);
			}//if-else
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_LABEL);
		}
	}
	
}
