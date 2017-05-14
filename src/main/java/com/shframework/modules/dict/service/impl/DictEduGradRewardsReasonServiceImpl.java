package com.shframework.modules.dict.service.impl;

import static com.shframework.common.util.ParamsUtil.createDictQueryParamMap;

import java.util.LinkedHashMap;
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
import com.shframework.modules.dict.entity.DictEduGradRewardsReason;
import com.shframework.modules.dict.entity.DictEduGradRewardsReasonExample;
import com.shframework.modules.dict.mapper.DictEduGradRewardsReasonMapper;
import com.shframework.modules.dict.service.DictEduGradRewardsReasonService;

@Service
public class DictEduGradRewardsReasonServiceImpl implements DictEduGradRewardsReasonService {
	@Autowired
	private DictEduGradRewardsReasonMapper dictEduGradRewardsReasonMapper;
	private static final String defaultSortField = "_degrr.title";//默认排序字段
	private static final String defaultSortOrderby = "asc";

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int deleteById(int id) {
		DictEduGradRewardsReason dict = dictEduGradRewardsReasonMapper.selectByPrimaryKey(id);
		if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
			return 0;
		}
		dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictEduGradRewardsReasonMapper.updateByPrimaryKeySelective(dict);
		cacheComponent.renew(CacheComponent.KEY_DICT_GRAD_REWARDS_REASON);
		return result;
	}

	@Override
	public PageTerminal<DictEduGradRewardsReason> findAllByPage(
			PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictEduGradRewardsReasonMapper.countByMap(parMap);
		List<DictEduGradRewardsReason> gradRewardsList = null;
		if (count>0){
			gradRewardsList  =  dictEduGradRewardsReasonMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduGradRewardsReason>(gradRewardsList, pageSupport, count);
	}

	@Override
	public DictEduGradRewardsReason getDict(int id) {
		return dictEduGradRewardsReasonMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveDict(DictEduGradRewardsReason record) {
		try{
			if (record.getId()!=null && record.getId() > 0){
				return dictEduGradRewardsReasonMapper.updateByPrimaryKeySelective(record);
			}else {
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				if (record.getPriority() == null || record.getPriority() <1){
					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
				}
				return dictEduGradRewardsReasonMapper.insertSelective(record);
			}//if-else

		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			cacheComponent.renew(CacheComponent.KEY_DICT_GRAD_REWARDS_REASON);
		}
	}
	
	@Override
	public Map<String, DictEduGradRewardsReason> getTableMap() {
		Map<String, DictEduGradRewardsReason> map = new LinkedHashMap<String, DictEduGradRewardsReason>();
		DictEduGradRewardsReasonExample example = new DictEduGradRewardsReasonExample();
		example.createCriteria().andLogicDeleteEqualTo(Constants.BASE_LOGIC_DELETE_ZERO)
								.andStatusEqualTo(Constants.BASE_STATUS_ONE);
		List<DictEduGradRewardsReason> list = dictEduGradRewardsReasonMapper.selectByExample(example);
		for(DictEduGradRewardsReason dict:list){
			map.put(""+dict.getId(), dict);
		}
		return map;
	}
}