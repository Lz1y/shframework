package com.shframework.modules.dict.service.impl;

import static com.shframework.common.util.ParamsUtil.createDictQueryParamMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.common.util.RecordDeleteUtil;
import com.shframework.common.util.StringUtils;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.entity.DictEduCommonMajor;
import com.shframework.modules.dict.entity.DictEduCommonMajorExample;
import com.shframework.modules.dict.entity.DictEduCommonMajorField;
import com.shframework.modules.dict.entity.DictEduCommonMajorFieldExample;
import com.shframework.modules.dict.mapper.DictEduCommonMajorFieldMapper;
import com.shframework.modules.dict.mapper.DictEduCommonMajorMapper;
import com.shframework.modules.dict.service.DictEduCommonMajorService;

@Service
public class DictEduCommonMajorServiceImpl implements DictEduCommonMajorService{

	@Autowired
	private DictEduCommonMajorMapper dictEduCommonMajorMapper;//专业表
	
	@Autowired
	private DictEduCommonMajorFieldMapper dictEduCommonMajorFieldMapper;//专业方向表

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	private static final String defaultSortField = "_decm.code";//默认排序字段
	private static final String defaultSortOrderby = "asc";
	
	@Override
	@Transactional
	public int deleteById(int id, boolean isSchoolFix) {
		if (!isSchoolFix && isHaveChild(id)){
			return -1;
		}
		DictEduCommonMajor dict = dictEduCommonMajorMapper.selectByPrimaryKey(id);
		if (dict == null || dict.getLocked()==Constants.DICT_COMMON_YES 
                || dict.getLogicDelete()==Constants.DICT_COMMON_YES){
               return 0;
          }
        dict.setCode(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_CODE, dict.getCode()));
        dict.setTitle(RecordDeleteUtil.getDelValue(id, Constants.DB_COL_LENGTH_TITLE, dict.getTitle()));
		dict.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
		int result =  dictEduCommonMajorMapper.updateByPrimaryKeySelective(dict);
		if (result > 0){
			dict.setId(id);
			updateDefaultMajorField(dict);
			cacheComponent.renew(CacheComponent.KEY_MAJOR);
			cacheComponent.renew(CacheComponent.KEY_CASCADE_DEP_MAJOR);
			cacheComponent.renew(CacheComponent.KEY_CASCADE_GROUP_MAJOR);
		}
		return result;
	}
	
	private boolean isHaveChild(int id){
		DictEduCommonMajorExample example = new DictEduCommonMajorExample();
		example.createCriteria()
			.andPidEqualTo(id)
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
		int count = dictEduCommonMajorMapper.countByExample(example);
		return count > 0 ;
	}

	@Override
	public PageTerminal<DictEduCommonMajor> findAllByPage(
			PageSupport pageSupport, boolean isSchoolFix) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> parMap = createDictQueryParamMap(null, pageSupport, defaultSortField + " " + defaultSortOrderby);
		parMap.put("isSchoolFix", isSchoolFix);
		int count = dictEduCommonMajorMapper.countByMap(parMap);
		List<DictEduCommonMajor> list = null;
		if (count>0){
			list  =  dictEduCommonMajorMapper.selectByMap(parMap);
		}
		return new PageTerminal<DictEduCommonMajor>(list, pageSupport, count);
	}

	@Override
	public DictEduCommonMajor getDict(int id, boolean isSchoolFix) {
		DictEduCommonMajorExample example = new DictEduCommonMajorExample();
		if (isSchoolFix){//校定
			example.createCriteria()
				.andIdEqualTo(id)
				.andPidNotEqualTo(0)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
		} else {
			example.createCriteria()
			.andIdEqualTo(id)
			.andPidEqualTo(0)
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
		}
		List<DictEduCommonMajor> list = dictEduCommonMajorMapper.selectByExample(example);
		if (list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public int saveDict(DictEduCommonMajor record) {
		int result = 0;
		try{
			if (record.getId() !=null && record.getId() > 0){//update
				result = dictEduCommonMajorMapper.updateByPrimaryKeySelective(record);
				if (result >0){
					updateDefaultMajorField(record);
					if (record.getPid() == null || record.getPid() == 0){
						updateChildMajor(record);
					}
				}
			}
			else {//add
				if (StringUtils.isBlank(record.getFormerCode())){
					record.setFormerCode(record.getCode());
				}
				record.setLocked(Constants.DICT_COMMON_NO);
				record.setLogicDelete(Constants.DICT_COMMON_NO);
				if (record.getPriority() == null || record.getPriority() <1){
					record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
				}
				result = dictEduCommonMajorMapper.insertSelective(record);
				if (result >0){
					createDefaultMajorField(record);
				}
			}//if-else
		} catch(DuplicateKeyException e){
			return  Constants.ERR_SAVE_DUPLICATEKEY;
		} finally{
			cacheComponent.renew(CacheComponent.KEY_MAJOR);
			cacheComponent.renew(CacheComponent.KEY_CASCADE_DEP_MAJOR);
			cacheComponent.renew(CacheComponent.KEY_CASCADE_GROUP_MAJOR);
		}
		return result;
	}
	
	/**
	 * 在创建专业时，同时创建默认的专业方向
	 * @author RanWeizheng
	 * @param record
	 * @return
	 */
	private int createDefaultMajorField(DictEduCommonMajor record){
		DictEduCommonMajorField majorField = new DictEduCommonMajorField();
		majorField.setMajorId(record.getId());
		majorField.setCode(record.getCode() + Constants.DICT_EDU_COMMON_MAJOR_FIELD_CODE_DEFAULT);
		majorField.setTitle(record.getTitle());
		majorField.setPriority(Constants.DICT_PRIORITY_DEFAULT);
		majorField.setStatus(record.getStatus());
		majorField.setLocked(Constants.DICT_COMMON_YES);
		majorField.setStandard(record.getStandard());
		majorField.setLogicDelete(Constants.DICT_COMMON_NO);
		majorField.setPrincipal(Constants.DEFAULT_PRINCIPAL_ID);//默认的负责人id
		int result = dictEduCommonMajorFieldMapper.insertSelective(majorField);
		cacheComponent.renew(CacheComponent.KEY_MAJORFIELD);
		cacheComponent.renew(CacheComponent.KEY_CASCADE_MAJOR_MAJORFIELD);
		return result;
	}
	
	/**
	 * 在修改专业时，同时修改默认的专业方向
	 * @param record
	 * @return
	 */
	private int updateDefaultMajorField(DictEduCommonMajor record){
		DictEduCommonMajorField majorField = new DictEduCommonMajorField();
		majorField.setMajorId(record.getId());
		if (StringUtils.isNotBlank(record.getCode())){
			String code = record.getCode() + Constants.DICT_EDU_COMMON_MAJOR_FIELD_CODE_DEFAULT;
			if (code.length() > Constants.DB_COL_LENGTH_CODE)
				code.substring(0, Constants.DB_COL_LENGTH_CODE);
			majorField.setCode(code);
		}
		majorField.setTitle(record.getTitle());
		majorField.setStatus(record.getStatus());
		majorField.setStandard(record.getStandard());
		majorField.setLogicDelete(record.getLogicDelete());
		
		DictEduCommonMajorFieldExample example = new DictEduCommonMajorFieldExample();
		example.createCriteria()
				.andMajorIdEqualTo(record.getId())
				.andCodeLike("%" + Constants.DICT_EDU_COMMON_MAJOR_FIELD_CODE_DEFAULT);		
		
		return dictEduCommonMajorFieldMapper.updateByExampleSelective(majorField, example);
	}
	
	/**
	 * 专业修正时，级联修正其对应的校定专业的信息 
	 * 目前，只有groupId需要级联修正       
	 * @return
	 * @author RanWeizheng
	 * @date 2016年5月13日 下午3:08:55
	 */
	private int updateChildMajor(DictEduCommonMajor record){
		DictEduCommonMajor childRecord = new DictEduCommonMajor();
		childRecord.setGroupId(record.getGroupId());
		DictEduCommonMajorExample example = new DictEduCommonMajorExample();
		example.createCriteria().andPidEqualTo(record.getId());
		return dictEduCommonMajorMapper.updateByExampleSelective(childRecord, example);
	}
}
