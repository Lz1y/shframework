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
import com.shframework.modules.dict.entity.DictCommon;
import com.shframework.modules.dict.mapper.DictCommonMapper;
import com.shframework.modules.dict.service.DictCommonService;

/**
 * 字典表维护
 * 通用结构表维护接口
 * @author RanWeizheng
 *
 */
@Service("dictCommonService")
 // @PropertySource("classpath:shiro/shiro.properties")
public class DictCommonServiceImpl implements DictCommonService {
	@Autowired
	private DictCommonMapper dictMapper;
	private static final String defaultSortField = "_dc.priority";//默认排序字段
	private static final String defaultSortOrderby = "desc";

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	/*********************删除数据**********************************/
	
	/**
	 * 根据id串 ，删除数据
	 * 此处是逻辑删除，只是将locked字段不为“1”（锁定）记录的status字段置为-1（逻辑删除）
	 * @author RanWeizheng
	 */
	@Override
	public int deleteById(String[] tableInfo, int id) {
		String tableName = tableInfo[Constants.DICT_INFO_TABLENAME];
		DictCommon dict = dictMapper.selectByPrimaryKey(tableName, id);
		 if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES 
	                || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
			 return 0;
	     }
		 if ("YES".equals(tableInfo[Constants.DICT_INFO_ISCONTAINCODE])){
			 dict.setCode(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_CODE, dict.getCode()));
		 }
	     dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
	     dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除

	    int result = dictMapper.updateByPrimaryKeySelective(tableName, dict);
	    renewCache(tableInfo[Constants.DICT_INFO_KEY]);
		return result;
		
	}
		
	/*********************查找数据**********************************/
	
	/**
	 * 获取指定表的数据
	 * @param pageSupport
	 * @param dictInfo
	 * @return
	 * @author RanWeizheng
	 */
	public PageTerminal<DictCommon> findAllByPage(PageSupport pageSupport, String tableName){
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(tableName, pageSupport, defaultSortField + " " + defaultSortOrderby);
		int count = dictMapper.countByMap(parMap);
		List<DictCommon> list =  dictMapper.selectByMap(parMap);
		return new PageTerminal<DictCommon>(list, pageSupport, count);
	}
	
	/**************通过主键获取 记录数据 ，用于查看详情 / 修改前的准备************/
	
	/**
	 * 根据指定的表名和id，找到字典表记录, 只要没有被逻辑删除的
	 * @param tableName
	 * @param id
	 * @return
	 * @author RanWeizheng
	 */
	@Override
	public DictCommon getDict(String tableName, int id) {
		return dictMapper.selectByPrimaryKey(tableName, id);
	}
	
	
	/***************保存字典表记录   增 or 改******************/
	
	/**
	 * 保存字典表数据
	 * return : -1 异常抛出，其他情况下返回字典表记录的id or 操作影响的记录条数
	 * @author RanWeizheng
	 */
	@Override
	public int saveDict(DictCommon dictCommon, String[] tableInfo){
		String tableName = tableInfo[Constants.DICT_INFO_TABLENAME];
		int result =  saveDictCommon(tableName, dictCommon);
		renewCache(tableInfo[Constants.DICT_INFO_KEY]);
		return result;
	}
	
	/**
	 * 保存通用字典表记录
	 * @param tableName
	 * @param parMap
	 * @return
	 * @author RanWeizheng
	 */
	private int saveDictCommon(String tableName, DictCommon dictCommon){
		try {
			if (dictCommon.getId()!=null && dictCommon.getId() >0){
				return updateDictCommon(tableName, dictCommon);
			}
			else {
				dictCommon.setLocked(Constants.DICT_COMMON_NO);
				return addDictCommon(tableName, dictCommon);
			}//if-else
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		}
	}
	
	/**
	 * 增加字典表信息（通用）
	 * @param tableName
	 * @param dictCommon
	 * @return
	 * @author RanWeizheng
	 */
	private int addDictCommon(String tableName, DictCommon dictCommon) {
		if (dictCommon.getPriority() == null || dictCommon.getPriority() <1){
			dictCommon.setPriority(Constants.DICT_PRIORITY_DEFAULT);
		}
		dictCommon.setLogicDelete(Constants.DICT_COMMON_NO);
		return dictMapper.insertSelective(tableName, dictCommon);
	}

	/**
	 * 更新字典表信息(通用)
	 * @param tableName
	 * @param dictCommon
	 * @return
	 * @author RanWeizheng
	 */
	private int updateDictCommon(String tableName, DictCommon dictCommon) {
		return dictMapper.updateByPrimaryKeySelective(tableName, dictCommon);
	}
	
	/**
	 * 这里，默认key 与cacheComponent中对应的key值一致，特别的
	 * @param tableKey
	 * @author RanWeizheng
	 * @date 2016年8月2日 下午6:01:26
	 */
	private void renewCache(String tableKey){
		cacheComponent.renew(tableKey);
	}
}