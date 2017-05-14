package com.shframework.modules.dict.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shframework.common.util.AcdYearTermUtil;
import com.shframework.common.util.Constants;
import com.shframework.common.util.DbUtil;
import com.shframework.common.util.DictCascadeVo;
import com.shframework.modules.dict.entity.DictCommon;
import com.shframework.modules.dict.entity.DictCommonExample;
import com.shframework.modules.dict.entity.DictCommonPolicitalStatus;
import com.shframework.modules.dict.entity.DictCommonPolicitalStatusExample;
import com.shframework.modules.dict.entity.DictEduClrBuilding;
import com.shframework.modules.dict.entity.DictEduClrBuildingExample;
import com.shframework.modules.dict.entity.DictEduClrClrType;
import com.shframework.modules.dict.entity.DictEduClrClrTypeExample;
import com.shframework.modules.dict.entity.DictEduCommonAcdYearTerm;
import com.shframework.modules.dict.entity.DictEduCommonAcdYearTermExample;
import com.shframework.modules.dict.entity.DictEduCommonCampus;
import com.shframework.modules.dict.entity.DictEduCommonCampusExample;
import com.shframework.modules.dict.entity.DictEduCommonCollege;
import com.shframework.modules.dict.entity.DictEduCommonCollegeExample;
import com.shframework.modules.dict.entity.DictEduCommonDepartment;
import com.shframework.modules.dict.entity.DictEduCommonDepartmentExample;
import com.shframework.modules.dict.entity.DictEduCommonGroupMajor;
import com.shframework.modules.dict.entity.DictEduCommonGroupMajorExample;
import com.shframework.modules.dict.entity.DictEduCommonLabel;
import com.shframework.modules.dict.entity.DictEduCommonLabelExample;
import com.shframework.modules.dict.entity.DictEduCommonMajor;
import com.shframework.modules.dict.entity.DictEduCommonMajorDepartment;
import com.shframework.modules.dict.entity.DictEduCommonMajorDepartmentExample;
import com.shframework.modules.dict.entity.DictEduCommonMajorExample;
import com.shframework.modules.dict.entity.DictEduCommonMajorField;
import com.shframework.modules.dict.entity.DictEduCommonMajorFieldExample;
import com.shframework.modules.dict.entity.DictEduExamMode;
import com.shframework.modules.dict.entity.DictEduExamModeExample;
import com.shframework.modules.dict.entity.DictEduGradRewardsType;
import com.shframework.modules.dict.entity.DictEduGradRewardsTypeExample;
import com.shframework.modules.dict.entity.DictEduScrReason;
import com.shframework.modules.dict.entity.DictEduScrReasonExample;
import com.shframework.modules.dict.entity.DictEduSkdCourseMode;
import com.shframework.modules.dict.entity.DictEduSkdCourseModeExample;
import com.shframework.modules.dict.entity.DictEduTchPostLevel;
import com.shframework.modules.dict.entity.DictEduTchPostLevelExample;
import com.shframework.modules.dict.mapper.DictCommonMapper;
import com.shframework.modules.dict.mapper.DictCommonPolicitalStatusMapper;
import com.shframework.modules.dict.mapper.DictEduClrBuildingMapper;
import com.shframework.modules.dict.mapper.DictEduClrClrTypeMapper;
import com.shframework.modules.dict.mapper.DictEduCommonAcdYearTermMapper;
import com.shframework.modules.dict.mapper.DictEduCommonCampusMapper;
import com.shframework.modules.dict.mapper.DictEduCommonCollegeMapper;
import com.shframework.modules.dict.mapper.DictEduCommonDepartmentMapper;
import com.shframework.modules.dict.mapper.DictEduCommonGroupMajorMapper;
import com.shframework.modules.dict.mapper.DictEduCommonLabelMapper;
import com.shframework.modules.dict.mapper.DictEduCommonMajorDepartmentMapper;
import com.shframework.modules.dict.mapper.DictEduCommonMajorFieldMapper;
import com.shframework.modules.dict.mapper.DictEduCommonMajorMapper;
import com.shframework.modules.dict.mapper.DictEduExamModeMapper;
import com.shframework.modules.dict.mapper.DictEduGradRewardsTypeMapper;
import com.shframework.modules.dict.mapper.DictEduScrReasonMapper;
import com.shframework.modules.dict.mapper.DictEduSkdCourseModeMapper;
import com.shframework.modules.dict.mapper.DictEduTchPostLevelMapper;
import com.shframework.modules.dict.service.DictInitService;
import com.shframework.modules.sys.entity.Role;
import com.shframework.modules.sys.entity.RoleExample;
import com.shframework.modules.sys.entity.SysSetting;
import com.shframework.modules.sys.entity.SysSettingExample;
import com.shframework.modules.sys.mapper.RoleMapper;
import com.shframework.modules.sys.mapper.SysSettingMapper;

/**
 * 将 需要加载入缓存的部分单独保存
 * @author RanWeizheng
 *
 */
@Service
public class DictInitServiceImpl implements DictInitService {

	@Autowired
	private DictCommonMapper dictMapper;
	@Autowired
	private DictEduCommonAcdYearTermMapper dictEduCommonAcdYearTermMapper;
	@Autowired
	private DictEduCommonGroupMajorMapper dictEduCommonGroupMajorMapper;
	@Autowired
	private DictEduCommonMajorMapper dictEduCommonMajorMapper;
	@Autowired
	private DictEduCommonMajorDepartmentMapper dictEduCommonMajorDepartmentMapper;
	@Autowired
	private DictCommonPolicitalStatusMapper dictCommonPolicitalStatusMapper;
	@Autowired
	private DictEduCommonLabelMapper dictEduCommonLabelMapper;
	@Autowired
	private DictEduCommonMajorFieldMapper dictEduCommonMajorFieldMapper;
	@Autowired
	private DictEduCommonCollegeMapper dictEduCommonCollegeMapper;
	@Autowired
	private DictEduCommonCampusMapper dictEduCommonCampusMapper;
	@Autowired
	private DictEduCommonDepartmentMapper dictEduCommonDepartmentMapper;
	@Autowired
	private DictEduSkdCourseModeMapper dictEduSkdCourseModeMapper;
	
	@Autowired
	private DictEduTchPostLevelMapper dictEduTchPostLevelMapper;
	@Autowired
	private DictEduClrBuildingMapper dictEduClrBuildingMapper;
	
	@Autowired
	private DictEduClrClrTypeMapper dictEduClrClrTypeMapper;
	@Autowired
	private DictEduScrReasonMapper dictEduScrReasonMapper;
	@Autowired
	private DictEduExamModeMapper dictEduExamModeMapper;
	
	@Autowired
	SysSettingMapper sysSettingMapper;
	@Autowired
	RoleMapper roleMapper;
	
	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@Autowired
	private DictEduGradRewardsTypeMapper dictEduGradRewardsTypeMapper;
	
	@Override
	public Map<String, String[]> getAllDictInfo() {
		List<Map<String, String>> dictInfoList = dictMapper.getAllDictInfo(Constants.DB_NAME);
		Map<String, String[]> dictInfoMap = new HashMap<String, String[]>();
		for (Map<String, String> dictInfo : dictInfoList){
			String key = "";
			String tableName =	dictInfo.get("TABLE_NAME");
			
			String isContainCode = "NO";
			Map<String,Object> tableNameMap = new HashMap<>();
			tableNameMap.put("tableName", tableName);
			List<Map<String, String>> dictList = dictMapper.getDictInfoByTableName(tableNameMap);
			if(dictList.size() != 0 && null != dictList && null != dictList.get(0)){
				if("NO".equals(dictList.get(0).get("Null"))){
					isContainCode = "YES";
				}
			}
			
			String path = "";
			if (StringUtils.isBlank(tableName)){
				continue;
			}
			String tableNameStr = tableName.replace("_", "").toLowerCase();//全部变为小写，方便对比
			if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_COMMON)){
				path = Constants.DICT_PATH_COMMON;
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_COMMON, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_COMMON)){
				path = Constants.DICT_PATH_EDU_COMMON;
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_COMMON, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_ROLL)){
				path = Constants.DICT_PATH_EDU_ROLL;
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_ROLL, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_SKD)){
				path = Constants.DICT_PATH_EDU_SKD;
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_SKD, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_TCH)){//师资
				path = Constants.DICT_PATH_EDU_TCH;
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_TCH, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_CLR)){//教学资源
				path = Constants.DICT_PATH_EDU_CLR;
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_CLR, "");
			}else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_CERT)){//证书管理
				path = Constants.DICT_PATH_EDU_CERT;
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_CERT, "");
			}else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_EXAM)){//考务管理
				path = Constants.DICT_PATH_EDU_EXAM;
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_EXAM, "");
			}else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_GRAD)){//毕业资格审核
				path = Constants.DICT_PATH_EDU_GRAD;
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_GRAD, "");
			}
			else {
				continue;
			}
			String[] dict = new String[Constants.DICT_INFO_LENGTH];
			dict[Constants.DICT_INFO_TABLENAME]=tableName;
			dict[Constants.DICT_INFO_TABLEDESC]=getTableDesc(dictInfo.get("TABLE_COMMENT"));
			dict[Constants.DICT_INFO_PATHPREFIX]=path;
			dict[Constants.DICT_INFO_ISCONTAINCODE]=isContainCode;
			dict[Constants.DICT_INFO_KEY]=key;
			dictInfoMap.put(key, dict);
		}//for

		return dictInfoMap;
	}
	
	/**
	 * 根据原始的表描述信息，截取到需要的部分
	 * @param origStr
	 * @return
	 * InnoDB 下，会额外的包含InnoDB free***格式的信息。
		详细信息见：<a href="http://bugs.mysql.com/bug.php?id=11379">http://bugs.mysql.com/bug.php?id=11379</a>
	 */
	private String getTableDesc(String origStr){
		String str = DbUtil.getCommentTrim(origStr);
		if (StringUtils.isNoneBlank(str)){//默认描述文字格式为： [字典]***表，只取***部分
			return str.substring(4, str.indexOf("表"));
		}
		return str;
	}
	
	/**
	 * <P>目前只有导入数据，解析入库时才用到此方法。整体上是废弃状态，框架、业务都不再用了。目前也是没人在使用。
	 */
	@Deprecated
	@Override
	public Map<String, Object> getAllDictMap() {
		List<Map<String, String>> dictInfoList = dictMapper.getAllDictInfo(Constants.DB_NAME);
		Map<String, Object> dictMap = new HashMap<String, Object>();
		
		for (Map<String, String> dictInfo : dictInfoList){
			String key = "";
			String tableName =	dictInfo.get("TABLE_NAME");
			
			if (StringUtils.isBlank(tableName)){
				continue;
			}
			String tableNameStr = tableName.replace("_", "").toLowerCase();//全部变为小写，方便对比
			if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_COMMON)){
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_COMMON, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_COMMON)){
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_COMMON, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_ROLL)){
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_ROLL, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_SKD)){
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_SKD, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_TCH)){
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_TCH, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_CLR)){//教学资源
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_CLR, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_CERT)){//证书管理
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_CERT, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_EXAM)){//考务管理
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_EXAM, "");
			}
			else if (tableNameStr.contains(Constants.DICT_NAME_PREFIX_EDU_GRAD)){//毕业资格审核
				key = tableNameStr.replace(Constants.DICT_NAME_PREFIX_EDU_GRAD, "");
			}
			else {
				continue;//不在以上范围的，不做处理
			}
			
			dictMap.put(key, getTableMapByName(tableName));
		}//for
		
		//所有pid为0的专业的集合
		dictMap.put("parentmajor", getParentMajor());
		
		//原因字典
		dictMap.put("headeditexam", getScrReason(Constants.SCR_REASON_TYPE_HEAD_EDIT));//修改总评成绩的原因
		dictMap.put("majoreditexam", getScrReason(Constants.SCR_REASON_TYPE_MAJOR_EDIT));//教务修正成绩
		dictMap.put("delayedexam", getScrReason(Constants.SCR_REASON_TYPE_STU_DEFER));//学生缓考
		dictMap.put("banexam", getScrReason(Constants.SCR_REASON_TYPE_STU_BAN));//学生禁考
		dictMap.put("cheatexam", getScrReason(Constants.SCR_REASON_TYPE_STU_CHEAT));//学生作弊
		dictMap.put("absentexam", getScrReason(Constants.SCR_REASON_TYPE_STU_ABSENT));//学生缺考
		dictMap.put("violationexam", getScrReason(Constants.SCR_REASON_TYPE_STU_BREAKRULE));//学生违纪
		dictMap.put("revreason", getScrReason(Constants.SCR_REASON_TYPE_REV_REASON)); //补修原因
		dictMap.put("allscrreason", getScrReason(null)); //获取所有原因
		
		//考试类型
		dictMap.put("exammode", getExamMode());
		
		//额外加载地区三级级联
		dictMap.put(Constants.CASCADE_KEY_PROVINCE, getProvinceCascade());
		
		dictMap.put(Constants.KEY_SYS_SETTING, getAllSysSetting());
		
		// 上学期，当前学期，下学期
		dictMap.put(Constants.KEY_YEAR_TERM, getYearTerm());	
		
		dictMap.put(Constants.KEY_DIVISION, getAllDivision(null));// 部门 ,页面上都用的department  ， 这里包括了dict_edu_common_department中的（id>1）所有数据
		dictMap.put(Constants.KEY_SEC_DIVISION, getAllDivision(Constants.DEPARTMENT_ROOT_ID));//只取第一级部门
		// 取得所有的系统角色
		dictMap.put(Constants.KEY_SYS_ROLE, getSysRole(true));
		dictMap.put(Constants.KEY_SKD_SYS_ROLE, getSysRole(false));
		
		return dictMap;
	}
	
	/**
	 * 根据表名加载相应表的未删除且状态为“选用”的记录
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getTableMapByName(String tableName){
		HashMap<String, Object> tableMap = new LinkedHashMap<String, Object>();
		if  (Constants.DICT_TABLE_NAME_EDU_COMMON_ACD_YEAR_TERM.equals(tableName)){
			List<DictEduCommonAcdYearTerm> list = dictEduCommonAcdYearTermMapper.getYearTermTitle();// 学年+学期名称
			for (DictEduCommonAcdYearTerm dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if (Constants.DICT_TABLE_NAME_EDU_COMMON_MAJOR_DEPARTMENT.equals(tableName)){
			DictEduCommonMajorDepartmentExample example = new DictEduCommonMajorDepartmentExample();
			List<DictEduCommonMajorDepartment> list = dictEduCommonMajorDepartmentMapper.selectByExample(example);
			for (DictEduCommonMajorDepartment dict : list){
				Map<String, Object> tempMap = null;
				if (tableMap.get(dict.getDepartmentId() + "") == null){
					tempMap = new HashMap<>();
					tempMap.put(dict.getMajorId()  + "", dict);
				} else {
					tempMap = (Map<String, Object>)tableMap.get(dict.getDepartmentId() + "");
					tempMap.put(dict.getMajorId()  + "", dict);
				}
				tableMap.put(Integer.toString(dict.getDepartmentId()), tempMap);
			}
			return tableMap;
		}
		else if  (Constants.DICT_TABLE_NAME_COMMON_POLICITAL_STATUS.equals(tableName)){
			DictCommonPolicitalStatusExample example = new DictCommonPolicitalStatusExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
			List<DictCommonPolicitalStatus> list = dictCommonPolicitalStatusMapper.selectByExample(example);
			for (DictCommonPolicitalStatus dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_COMMON_LABEL.equals(tableName)){
			DictEduCommonLabelExample example = new DictEduCommonLabelExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
			List<DictEduCommonLabel> list = dictEduCommonLabelMapper.selectByExample(example);
			for (DictEduCommonLabel dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_COMMON_GROUP_MAJOR.equals(tableName)){
			DictEduCommonGroupMajorExample example = new DictEduCommonGroupMajorExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
			List<DictEduCommonGroupMajor> list = dictEduCommonGroupMajorMapper.selectByExample(example);
			for (DictEduCommonGroupMajor dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_COMMON_MAJOR.equals(tableName)){
			DictEduCommonMajorExample example = new DictEduCommonMajorExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
			List<DictEduCommonMajor> list = dictEduCommonMajorMapper.selectByExample(example);
			for (DictEduCommonMajor dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_COMMON_MAJOR_FIELD.equals(tableName)){
			DictEduCommonMajorFieldExample example = new DictEduCommonMajorFieldExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
			List<DictEduCommonMajorField> list = dictEduCommonMajorFieldMapper.selectByExample(example);
			for (DictEduCommonMajorField dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_COMMON_COLLEGE.equals(tableName)){
			DictEduCommonCollegeExample example = new DictEduCommonCollegeExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
			List<DictEduCommonCollege> list = dictEduCommonCollegeMapper.selectByExample(example);
			for (DictEduCommonCollege dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_COMMON_CAMPUS.equals(tableName)){
			DictEduCommonCampusExample example = new DictEduCommonCampusExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
			List<DictEduCommonCampus> list = dictEduCommonCampusMapper.selectByExample(example);
			for (DictEduCommonCampus dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_COMMON_DEPARTMENT.equals(tableName)){
			DictEduCommonDepartmentExample example = new DictEduCommonDepartmentExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO)
				.andTypeEqualTo(Constants.DICT_COMMON_YES)//类型： 院系
				.andIdGreaterThan(Constants.DEPARTMENT_ROOT_ID);//去除跟节点
			example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
			List<DictEduCommonDepartment> list = dictEduCommonDepartmentMapper.selectByExample(example);
			for (DictEduCommonDepartment dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_SKD_COURSE_MODE.equals(tableName)){//课程类型
			DictEduSkdCourseModeExample example = new DictEduSkdCourseModeExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
			List<DictEduSkdCourseMode> list = dictEduSkdCourseModeMapper.selectByExample(example);
			for (DictEduSkdCourseMode dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_TCH_POST_LEVEL.equals(tableName)){//行政职务等级
			DictEduTchPostLevelExample example = new DictEduTchPostLevelExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause("id");
			List<DictEduTchPostLevel> list = dictEduTchPostLevelMapper.selectByExample(example);
			for (DictEduTchPostLevel dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_CLR_BUILDING.equals(tableName)){//建筑信息表
			DictEduClrBuildingExample example = new DictEduClrBuildingExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause("id");
			List<DictEduClrBuilding> list = dictEduClrBuildingMapper.selectByExample(example);
			for (DictEduClrBuilding dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_CLR_CLR_TYPE.equals(tableName)){//场地类型表
			DictEduClrClrTypeExample example = new DictEduClrClrTypeExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause("id");
			List<DictEduClrClrType> list = dictEduClrClrTypeMapper.selectByExample(example);
			for (DictEduClrClrType dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else if  (Constants.DICT_TABLE_NAME_EDU_GRAD_REWARDS_TYPE.equals(tableName)){//毕业资格审核
			DictEduGradRewardsTypeExample example = new DictEduGradRewardsTypeExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause("id");
			List<DictEduGradRewardsType> list = dictEduGradRewardsTypeMapper.selectByExample(example);
			for (DictEduGradRewardsType dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}
		else {
			DictCommonExample example = new DictCommonExample();
			example.createCriteria().
				andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
			List<DictCommon> list = dictMapper.selectByExample(tableName, example);
			for (DictCommon dict : list){
				tableMap.put(Integer.toString(dict.getId()), dict);
			}
		}//if-else
		
		return tableMap;
	}
	
	/**
	 * 获取所有父id为0的专业, 其他条件与正常的缓存一致
	 * @return
	 * @author RanWeizheng
	 * @date 2016年5月13日 上午10:41:51
	 */
	@Override
	public Map<String, Object> getParentMajor(){
		Map<String, Object> tableMap = new LinkedHashMap<String, Object>();
		DictEduCommonMajorExample example = new DictEduCommonMajorExample();
		example.createCriteria()
			.andPidEqualTo(Integer.parseInt(Constants.COMMON_ZERO))
			.andStatusEqualTo(Constants.DICT_COMMON_YES)
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			
		example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
		List<DictEduCommonMajor> list = dictEduCommonMajorMapper.selectByExample(example);
		for (DictEduCommonMajor dict : list){
			tableMap.put(Integer.toString(dict.getId()), dict);
		}
		return tableMap;
	}
	
	/**
	 * 根据类型获取 成绩/考务 更改的原因
	 * @return
	 */
	@Override
	public Map<String, Object> getScrReason(Integer type){
		Map<String, Object> tableMap = new LinkedHashMap<String, Object>();
		DictEduScrReasonExample example = new DictEduScrReasonExample();
		if (type != null){
			example.createCriteria()
				.andStatusEqualTo(Constants.DICT_COMMON_YES)
				.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO)
				.andTypeEqualTo(type);
		} else {
			example.createCriteria()
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
		}
		List<DictEduScrReason> list = dictEduScrReasonMapper.selectByExample(example);
		for (DictEduScrReason reason : list){
			tableMap.put(Integer.toString(reason.getId()), reason);
		}
		return tableMap;
	}

	@Override
	public Map<String, Object> getExamMode(){
		Map<String, Object> tableMap = new LinkedHashMap<String, Object>();
		DictEduExamModeExample example = new DictEduExamModeExample();
		example.createCriteria().andStatusEqualTo(Constants.DICT_COMMON_YES)
								.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
		example.setOrderByClause("priority desc");
		List<DictEduExamMode> list = dictEduExamModeMapper.selectByExample(example);
		for (DictEduExamMode dict : list){
			tableMap.put(Integer.toString(dict.getId()), dict);
		}
		return tableMap;
	}
	
	@Override
	public Map<String, Object> getExamMode(Integer examFlag){
		Map<String, Object> tableMap = new LinkedHashMap<String, Object>();
		DictEduExamModeExample example = new DictEduExamModeExample();
		example.createCriteria().andStatusEqualTo(Constants.DICT_COMMON_YES)
								.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO)
								.andExamFlagEqualTo(examFlag);
		example.setOrderByClause("priority desc");
		List<DictEduExamMode> list = dictEduExamModeMapper.selectByExample(example);
		for (DictEduExamMode dict : list){
			tableMap.put(Integer.toString(dict.getId()), dict);
		}
		return tableMap;
	}
	
	/**
	 * 获取所有的系统参数
	 * @return
	 */
	@Override
	public Map<String, Object> getAllSysSetting(){
		Map<String, Object> tableMap = new LinkedHashMap<String, Object>();
		SysSettingExample example = new SysSettingExample();
		example.createCriteria()
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO)
			.andStatusNotEqualTo(Constants.DICT_COMMON_NO);
		List<SysSetting> list = sysSettingMapper.selectByExample(example);
		for (SysSetting setting : list){//注意，这里比较特殊，要用title作为key，把content直接作为map的value
			tableMap.put(setting.getTitle(), setting.getContent());
		}
		return tableMap;
		
	}
	
	/**
	 * 获取省级的级联信息
	 * @return
	 */
	@Override
	public  Map<String, Object> getProvinceCascade(){
		String topLevelCondition = "0000";
		String secLevelCondition = "00";
		Map<String, Object> tableMap = new LinkedHashMap<String, Object>();
		
		List<DictCommon> provinceList = new LinkedList<DictCommon>();
		Map<String, List<DictCommon>>  subMap = new HashMap<String, List<DictCommon>>();
		Map<String, List<DictCommon>>  countyMap = new HashMap<String, List<DictCommon>>();
		
		DictCommonExample example = new DictCommonExample();
		example.createCriteria().
			andStatusEqualTo(Constants.DICT_COMMON_YES)
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
		example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
		List<DictCommon> areaList = dictMapper.selectByExample(Constants.DICT_TABLE_NAME_COMMON_AREA, example);
		
		for (DictCommon area : areaList){
			if (area.getCode().endsWith(topLevelCondition)){
				provinceList.add(area);
			} else if (isMuniPlace(area)){
				continue;
			}else if ( area.getCode().endsWith(secLevelCondition) ){
				String secHeadStr = area.getCode().substring(0, 2);
				List<DictCommon> secList = subMap.get(secHeadStr);
				if (secList == null ){
					secList = new LinkedList<DictCommon>();
				}
				secList.add(area);
				subMap.put(secHeadStr, secList);
			} else {
				String thirdHeadStr = area.getCode().substring(0, 4);
				List<DictCommon> thirdList = countyMap.get(thirdHeadStr);
				if (thirdList == null ){
					thirdList = new LinkedList<DictCommon>();
				}
				thirdList.add(area);
				countyMap.put(thirdHeadStr, thirdList);
			}//if-else
		}//for
		
		for (DictCommon province : provinceList){
			DictCascadeVo pVo = dozerBeanMapper.map(province, DictCascadeVo.class);
			List<DictCommon> secList = subMap.get(province.getCode().substring(0, 2));
			if (secList==null || secList.size() ==0){
				pVo.setIsLeaf(Constants.DICT_COMMON_YES);
			} else {
				List<DictCascadeVo> childList = new LinkedList<DictCascadeVo>();
				for(DictCommon subArea : secList){
					DictCascadeVo childVo = dozerBeanMapper.map(subArea, DictCascadeVo.class);
					List<DictCommon> thirdList = countyMap.get(subArea.getCode().substring(0, 4));
					if (thirdList==null || thirdList.size() ==0){
						childVo.setIsLeaf(Constants.DICT_COMMON_YES);
					} else {
						List<DictCascadeVo> countyList = new LinkedList<DictCascadeVo>();
						for(DictCommon countyArea : thirdList){
							DictCascadeVo countyVo = dozerBeanMapper.map(countyArea, DictCascadeVo.class);
							countyList.add(countyVo);
						}//for
						childVo.setChild(countyList);
					}
					childList.add(childVo);
				}//for
				pVo.setChild(childList);
			}
			tableMap.put(Integer.toString(province.getId()), pVo);
		}
		return tableMap;
	}
	
	//判断是否是直辖市下属的地区
	@Override
	public boolean isMuniPlace(DictCommon area){
		if (area !=null ){
			String  topCode = area.getCode().substring(0, 2);
			if ("11".equals(topCode) 
					|| "12".equals(topCode)
					|| "31".equals(topCode)
					|| "50".equals(topCode)){//31 50 71 81 82
				return true;
			}
		}
		return false;
	}
	
	
	// 根据 new Date() 取得 yearTermCode
	@Override
	public String getCurrYearTermCode(){
		
		// 当前学期规则：取学年学期表中开始时间所有小于等于当前时间最大的一个。
		String defaultCurrYearTerm = "";
		
		DictEduCommonAcdYearTermExample example = new DictEduCommonAcdYearTermExample();
		example.setOrderByClause("id DESC");
		example.createCriteria().andStartDateLessThanOrEqualTo(new Date())
			.andLogicDeleteEqualTo(Constants.BASE_LOGIC_DELETE_ZERO)
			.andStatusEqualTo(Constants.DICT_COMMON_YES);
		List<DictEduCommonAcdYearTerm> list = dictEduCommonAcdYearTermMapper.selectByExample1(example);
		if(list != null && list.size() > 0){
			defaultCurrYearTerm = list.get(0).getCode();
		}
		
		return defaultCurrYearTerm;
	}
	
	// 根据当前学期的 code 取得上一学期或下一学期的code
	public String getPreOrNextYearTermCode(Integer index){
		
		String currYearTermCode = getCurrYearTermCode();	// 当前学期
		return AcdYearTermUtil.getGraduateYearTermCode(currYearTermCode, index);
		
	}
	
	// 上学期，当前学期，下学期
	@Override
	public Map<String, DictEduCommonAcdYearTerm> getYearTerm(){
		Map<String, DictEduCommonAcdYearTerm> tableMap = new LinkedHashMap<String, DictEduCommonAcdYearTerm>();
		
		String currYearTermCode = getPreOrNextYearTermCode(0);
		String preYearTermCode = getPreOrNextYearTermCode(-1);
		String nextYearTermCode = getPreOrNextYearTermCode(1);
		
		DictEduCommonAcdYearTerm currYearTerm = dictEduCommonAcdYearTermMapper.selectAcdYearTermByYearTermCode(currYearTermCode).get(0);
		DictEduCommonAcdYearTerm preYearTerm = dictEduCommonAcdYearTermMapper.selectAcdYearTermByYearTermCode(preYearTermCode).get(0);
		DictEduCommonAcdYearTerm nextYearTerm = dictEduCommonAcdYearTermMapper.selectAcdYearTermByYearTermCode(nextYearTermCode).get(0);
		
		tableMap.put("-1", preYearTerm);
		tableMap.put("0", currYearTerm);
		tableMap.put("1", nextYearTerm);

		return tableMap;
	}
	
	@Override
	public Map<String, Object> getAllDivision(Integer parentId){
		Map<String, Object> tableMap = new LinkedHashMap<String, Object>();
		DictEduCommonDepartmentExample example = new DictEduCommonDepartmentExample();
		if (parentId == null){
			example.createCriteria()
			.andStatusEqualTo(Constants.DICT_COMMON_YES)
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			//由于信息中心给的数据部分教职工挂在了（北京信息职业技术学院）下，所以注释掉了下句代码。modify by zhangjinkui
//			.andIdGreaterThan(Constants.DEPARTMENT_ROOT_ID);//去除跟节点
		} else {
			example.createCriteria()
			.andStatusEqualTo(Constants.DICT_COMMON_YES)
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO)
			.andParentIdEqualTo(parentId);
		}
		example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
		List<DictEduCommonDepartment> list = dictEduCommonDepartmentMapper.selectByExample(example);
		initDivisionChild(list);
		for (DictEduCommonDepartment dict : list){
			tableMap.put(Integer.toString(dict.getId()), dict);
		}
		return tableMap;
	}
	
	/**
	 * 获取所有节点的子节点，TODO 待优化
	 * @param depList
	 * @author RanWeizheng
	 * @date 2016年6月24日 下午4:15:30
	 */
	private void initDivisionChild(List<DictEduCommonDepartment> depList){
		for (DictEduCommonDepartment dep : depList) {
			dep.setChildIdList(getDivisionChild(depList, dep.getId()));	
		}
	}
	private List<Integer> getDivisionChild(List<DictEduCommonDepartment> depList, Integer parentId){
		List<DictEduCommonDepartment> dList =  new ArrayList<>();
		for (DictEduCommonDepartment dep : depList) {
			 if (dep.getParentId() == parentId.intValue()){
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
				list.addAll(getDivisionChild(depList, dep.getId()));
			}
		}
		return list;
	}
	
	
	/**
	 * 取得所有的系统角色
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年6月10日 下午2:26:09
	 */
	@Override
	public Map<String, Object> getSysRole(boolean isAllRole){
		Map<String, Object> tableMap = new LinkedHashMap<String, Object>();
		RoleExample example = new RoleExample();
		example.createCriteria()
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO)
			.andStatusNotEqualTo(Constants.DICT_COMMON_NO);
		List<Role> list = roleMapper.selectByExample(example);
		
		List<String> roleCodeList = new ArrayList<String>();
		roleCodeList.add(Constants.HEAD_SCH);
		roleCodeList.add(Constants.HEAD_DEPT);
		roleCodeList.add(Constants.HEAD_MAJOR);
		roleCodeList.add(Constants.HEAD_TECH);
		
		for (Role role : list){
			if(isAllRole || roleCodeList.contains(role.getCode())){
				tableMap.put(Integer.toString(role.getId()), role);
			}
		}
		return tableMap;
	}
	
	/**
	 * 根据表名取得通用数据集合
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年8月20日 下午4:44:16
	 */
	@Override
	public List<DictCommon> getCommonListByTableName(String tableName){
		
		DictCommonExample example = new DictCommonExample();
		example.createCriteria().
			andStatusEqualTo(Constants.DICT_COMMON_YES)
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
		example.setOrderByClause(Constants.DICT_LIST_ORDERBY_DEFAULT);
		List<DictCommon> list = dictMapper.selectByExample(tableName, example);
		
		return list;
	}
	
	/** 
	 * 根据 yearTermCode 查找对象
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年8月24日 下午6:54:31
	 */
	@Override
	public DictEduCommonAcdYearTerm selectAcdYearTermByYearTermCode(String yearTermCode){
		return dictEduCommonAcdYearTermMapper.selectAcdYearTermByYearTermCode(yearTermCode).get(0);
	}
	
	/**
	 * 取得大于当前学期的学年学期id集合
	 * @return
	 */
	@Override
	public List<Integer> getYearTermIdListOfGreaterCurrTerm(){
		String currYearTermCode = getCurrYearTermCode();
		return dictEduCommonAcdYearTermMapper.getYearTermIdListOfGreaterCurrTerm(currYearTermCode);
	}
	
	/**
	 * 所有学年学期的Map集合
	 * @param	
	 * @return	key:yearTermCode(eg:2016-1) value:DictEduCommonAcdYearTerm	
	 * @create 	wangkang 2016年10月27日 下午2:59:48
	 * @modify
	 */
	@Override
	public Map<String, DictEduCommonAcdYearTerm> getAllYearTerm(){
		Map<String, DictEduCommonAcdYearTerm> tableMap = new LinkedHashMap<String, DictEduCommonAcdYearTerm>();
		List<DictEduCommonAcdYearTerm> list = dictEduCommonAcdYearTermMapper.getYearTermTitle();// 学年+学期名称
		for (DictEduCommonAcdYearTerm dict : list){
			tableMap.put(dict.getCode(), dict);
		}
		return tableMap;
	}
}