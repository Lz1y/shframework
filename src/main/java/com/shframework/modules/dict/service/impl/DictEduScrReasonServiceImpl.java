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
import com.shframework.modules.dict.entity.DictEduScrReason;
import com.shframework.modules.dict.mapper.DictEduScrReasonMapper;
import com.shframework.modules.dict.service.DictEduScrReasonService;

@Service
public class DictEduScrReasonServiceImpl implements DictEduScrReasonService{

	@Autowired
	DictEduScrReasonMapper dictEduScrReasonMapper;
	private static final String defaultSortField = "_desr.priority";//默认排序字段
	private static final String defaultSortOrderby = "asc";
	
	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public int deleteById(int id, Integer rType) {
		DictEduScrReason dict = dictEduScrReasonMapper.selectByPrimaryKey(id);
          if (dict == null || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
               return 0;
          }
        dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result = dictEduScrReasonMapper.updateByPrimaryKeySelective(dict);
		renewCache(rType);
		return result;
	}

	@Override
	public PageTerminal<DictEduScrReason> findAllByPage(PageSupport pageSupport, Integer rType) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		parMap.put("reasonType", rType);
		int count = dictEduScrReasonMapper.countByMap(parMap);
		List<DictEduScrReason> list = null;
		if (count>0){
			list  =  dictEduScrReasonMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduScrReason>(list, pageSupport, count);
	}

	@Override
	public DictEduScrReason getDict(int id, Integer rType) {
		return dictEduScrReasonMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveDict(DictEduScrReason record, Integer rType) {
		try{
			if (record.getId()!=null && record.getId() > 0){
				return dictEduScrReasonMapper.updateByPrimaryKeySelective(record);
			}
			else {
				record.setType(rType);
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				if (record.getPriority() == null || record.getPriority() <1){
					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
				}
				return dictEduScrReasonMapper.insertSelective(record);
			}//if-else
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally {
			renewCache(rType);
		}
	}
	
	/**
	 * 根据rType，清除缓存、
	 *
	 * 0：修改总评成绩
	 * 1：教务修正成绩
	 * 2：学生缓考
	 * 3：学生禁考
	 * 4：学生作弊
	 * 5：学生缺考
	 * 6：学生违纪
	 * 7：学生补修
	 * @param rType
	 * @author RanWeizheng
	 * @date 2016年8月2日 下午5:23:21
	 */
	private void renewCache(Integer rType){
		String key =CacheComponent.KEY_ALLSCRREASON;
		switch (rType) {
		case 0:
			key = CacheComponent.KEY_HEADEDITEXAM;
			break;
		case 1:
			key = CacheComponent.KEY_MAJOREDITEXAM;
			break;
		case 2:
			key = CacheComponent.KEY_DELAYEDEXAM;
			break;
		case 3:
			key = CacheComponent.KEY_BANEXAM;
			break;
		case 4:
			key = CacheComponent.KEY_CHEATEXAM;
			break;
		case 5:
			key = CacheComponent.KEY_ABSENTEXAM;
			break;
		case 6:
			key = CacheComponent.KEY_VIOLATIONEXAM;
			break;
		case 7:
			key = CacheComponent.KEY_REVREASON;
			break;

		default:
			break;
		}
		cacheComponent.renew(key);
	}
	

}
