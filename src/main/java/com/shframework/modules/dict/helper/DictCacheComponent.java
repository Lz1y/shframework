package com.shframework.modules.dict.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.shframework.common.util.AcdYearTermUtil;
import com.shframework.common.util.Constants;
import com.shframework.common.util.DateUtils;
import com.shframework.common.util.DictCascadeVo;
import com.shframework.common.util.ReflectionUtils;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.entity.DictEduCommonAcdYearTerm;
import com.shframework.modules.dict.entity.DictEduCommonAcdYearTermExample;
import com.shframework.modules.dict.entity.DictEduCommonMajor;
import com.shframework.modules.dict.entity.DictEduCommonMajorField;
import com.shframework.modules.dict.entity.vo.LabelVo;
import com.shframework.modules.dict.mapper.DictEduCommonAcdYearTermMapper;
import com.shframework.modules.sys.entity.User;

/**
 * 用来获取缓存中的数据
 * @author RanWeizheng
 *
 */
@Component
public class DictCacheComponent {

	@Autowired
	private CacheComponent<?> cacheComponent;
	@Autowired
	private DictEduCommonAcdYearTermMapper dictEduCommonAcdYearTermMapper;
	
	/**
	 * 
	 * @param dataList  数据list
	 * @param fieldList   数据对应的参数
	 * @param dictMap  字典表缓存
	 * @param labelMap  label缓存
	 * @param fileFormat  导出时的文件格式 1 为dbf，
	 * @return
	 * @author RanWeizheng
	 * @date 2016年7月1日 下午5:53:12
	 */
	public <T> Object[][] getDataArray(List<T> dataList, List<String> fieldList){
		int colsSize = fieldList.size();
		int dataSize = dataList.size();
		Object[][] dataArray = new Object[dataSize][colsSize];
		
		for (int i=0; i<dataSize; i++){
			 T data = dataList.get(i);
			 for(int k=0; k<colsSize; k++ ){
				 String field = fieldList.get(k);
				 String originalValueString = null;
				 try {
					originalValueString =  BeanUtils.getProperty(data, field);
					if (StringUtils.isBlank(originalValueString) || "null".equals(originalValueString)){
						originalValueString = "";
					} else {
						originalValueString = convertData(field, originalValueString);
					}
					dataArray[i][k] = originalValueString;
				} catch (Exception e) {
					
				}//try-cache
			 }//for
		}//for
		
		return dataArray;
	}
	
	/**
	 * 全部解析，逐列解析，并放入数组。优化数据量大时的查找，不必每次获取缓存<br>
	 * 转换部分应当等价于<b>convertData(String fieldName, String originalValueString)</b>
	 * @param dataList
	 * @param cache_sys_user
	 * @return
	 * @author RanWeizheng
	 * @date 2016年8月26日 下午3:20:15
	 */
	public <T> Object[][] getDataArray(List<T> dataList, Map<String, User> cache_sys_user){
		int dataSize = dataList.size();
		int fieldSize= 0;
		List<Field> fieldList = new ArrayList<>();
		if (dataSize >0){
			T data = dataList.get(0);
			Field[] fieldArr = data.getClass().getDeclaredFields();
			for (Field field : fieldArr){
				 if ("serialVersionUID".equals(field.getName())){
					 continue;
				 }
				 fieldList.add(field);
			}
			fieldSize = fieldList.size();
		}
		Object[][] dataArray = new Object[dataSize][fieldSize];
		for(int k=0; k<fieldList.size(); k++ ){
			String fieldName = fieldList.get(k).getName();
			if (fieldName.contains("-")){
				fieldName = fieldName.substring(fieldName.indexOf("-")+1);
			}
			Object[] dataColumnArray = convertDataBatchColumn(dataList, fieldName);
			for (int j=0 ; j < dataColumnArray.length ; j++){
				dataArray[j][k] = dataColumnArray[j];
			}
		}//for
		return dataArray;
	}
	
	private final static List<String> scoreList = new ArrayList<String>();
	static {
		scoreList.add("score");
		scoreList.add("scoreFinal");
	}
		
	/**
	 * 不同业务中需要自行添加，根据不同的 fieldName 替换成缓存中对应的真实值
	 */
	private String convertData(String fieldName, String originalValueString) throws Exception {
		if (StringUtils.isBlank(fieldName)){
			return originalValueString;
		} else if (fieldName.contains("-")){
			fieldName = fieldName.substring(fieldName.indexOf("-")+1);
		}
//		excel导入时，时间格式如果不做解析，格式为："EEE MMM dd HH:mm:ss zzz yyyy"；modify by zhangjk (初始化学生信息时用此方法进行处理)
		if (fieldName.endsWith("date") 
				|| fieldName.endsWith("Date") ){//时间类型
			if (StringUtils.isNotBlank(originalValueString)){
				return originalValueString.split(" ")[0];
			} else {
				return "";
			}
		}
		if(scoreList.contains(fieldName) && originalValueString.equals("-1")) {
			return "";
		}
		if(fieldName.endsWith("Id")){ //以“Id”结尾时，表示从dictMap中获取数据
			String key = null;
			String perm = "title";
			//特殊情况
			switch (fieldName) {
				case "clazzId":
					key = Constants.TABLE_KEY_EDU_ROLL_NATURALCLAZZ;
					break;
				case "naturalClazzId"://行政班的code
					key = Constants.TABLE_KEY_EDU_ROLL_NATURALCLAZZ;
					perm = "code";
					break;
				case "provinceId":
					key = Constants.CASCADE_KEY_PROVINCE;
					break; 
				case "courseTypeId":
					key = Constants.TABLE_KEY_EDU_SKD_COURSE_TYPE;
					break; 
				case "courseId":
					key = Constants.TABLE_KEY_EDU_SKD_COURSE;
					break; 
				case "employeeId":
					key = Constants.KEY_EDU_TCH_EMPLOYEE_NAME;
					break;
				case "comeFromId":
					key = Constants.CASCADE_KEY_PROVINCE;//特殊的，生源地只可能是省级地区
					break;
				case "acdYearTermId"://学年学期
					key = Constants.DICT_TABLE_KEY_YEAR_TERM_FULLCODE;
					break;
				case "failAcdYearTermId"://学年学期
					key = Constants.DICT_TABLE_KEY_YEAR_TERM_FULLCODE;
					break;
				/*
				"departmentId".equals(fieldName) || "divisionId".equals(fieldName)|| "naturalDepartmentId".equals(fieldName) 
				|| "oldDepartmentId".equals(fieldName)|| "stuDepartmentId".equals(fieldName) || "replaceDepartmentId"
				*/
				case "departmentId":
					key = Constants.KEY_DIVISION;// 部门 ,页面上都用的department  ， 这里包括了dict_edu_common_department中的（id>1）所有数据
					break;
				case "divisionId":
					key = Constants.KEY_DIVISION;
					break;
				case "naturalDepartmentId":
					key = Constants.KEY_DIVISION;
					break;
				case "oldDepartmentId":
					key = Constants.KEY_DIVISION;
					break;
				case "stuDepartmentId":
					key = Constants.KEY_DIVISION;
					break;
				case "replaceDepartmentId":
					key = Constants.KEY_DIVISION;
					break;
				case "campusId":
					key = Constants.KEY_CAMPUS;// 校区
					break;
				case "replaceCampusId":
					key = Constants.KEY_CAMPUS;// 校区
					break;
				case "studentId":
					key = CacheComponent.KEY_SYSUSER;
					perm = "username";
					break;
				case "userId":
					key = CacheComponent.KEY_SYSUSER;
					perm = "username";
					break;
				default:
					key = fieldName.substring(0, fieldName.length()-2).toLowerCase();
			}//switch
			return getDictData(key, originalValueString, perm);
		}
		
		if ("certType".equals(fieldName)){
			return getDictData("cert", originalValueString);
		}
		
		if ("nativePlace".equals(fieldName)){//籍贯
			return getNativePlace(originalValueString);
		}
		if ("examIllegalReason".equals(fieldName) ){//成绩-原因 （各种改变考试状态的原因）
			return getDictData("allscrreason", originalValueString);
		}
	
		if ("paymentFlag".equals(fieldName) || "clazzStatus".equals(fieldName) || "status".equals(fieldName)
				|| "standard".equals(fieldName) || "majorType".equals(fieldName) || "departmentType".equals(fieldName)
				|| "presenceFlag".equals(fieldName) || "rollFlag".equals(fieldName) || "paymentFlag".equals(fieldName)
				|| "registerStatus".equals(fieldName) || "confirmFlag".equals(fieldName) || "employeeType".equals(fieldName)
				|| "scheduleStatus".equals(fieldName) || "jobType".equals(fieldName) || "gender".equals(fieldName)
				|| "status_name".equals(fieldName) || "startStatus".equals(fieldName) || (Constants.KEY_WCM).equals(fieldName)
				|| (Constants.KEY_CCM).equals(fieldName) || "forceFlag".equals(fieldName) || "optionalFlag".equals(fieldName)
				|| "coopFlag".equals(fieldName) || "examFlag".equals(fieldName) || "showUrl".equals(fieldName)
				|| "templateFormat".equals(fieldName) || "templateType".equals(fieldName) || "processStatus".equals(fieldName)
				|| "standardFileType".equals(fieldName)
				|| "courseTypeGeneral".equals(fieldName)
				|| "standardFileType".equals(fieldName) 
				|| "scrTranscriptSign".equals(fieldName) 
				|| "scrStudyResult".equals(fieldName)
				|| "confirmStatus".equals(fieldName)) {
			return getLabelData(fieldName, originalValueString);
		}
		else if ("examType".equals(fieldName) ){//成绩-考试类型
			return getLabelData("scrExamType", originalValueString);
		}
		else if ("examStatus".equals(fieldName) ){//成绩-考试状态
			return getLabelData("scrExamStatus", originalValueString);
		}
		else if ("examStatus1".equals(fieldName) ){//成绩-考试状态, 需补考缓考查询页中
			return getLabelData("scrExamStatus1", originalValueString);
		}
		else if ("studentCreditResult".equals(fieldName) ){//成绩-成绩结果
			return getLabelData("scrStudyResult", originalValueString);
		}
		else if ("creditGeneralType".equals(fieldName) ){//成绩-修习类型
			return getLabelData("scrStudyType", originalValueString);
		}
		else if ("transcriptSign".equals(fieldName) ){//成绩-成绩单标识
			return getLabelData("scrTranscriptSign", originalValueString);
		}
		else if ("tchCreditDetailStatus".equals(fieldName) ){//教学班成绩提交状态
			return getLabelData("tchCreditDetailStatus", originalValueString);
		}
		else if ("revType".equals(fieldName)){//重修类型
			return getLabelData("revtype", originalValueString);
		}
		else if (fieldName.endsWith("Flag") || fieldName.endsWith("flag") ){//是否毕业、是否新生、
			return getLabelData("isZeroORNot", originalValueString);
		}
		return originalValueString;
		
	}
	
	
	private <T> Object[] convertDataBatchColumn(List<T> dataList, String fieldName){
		int dataSize = dataList.size();
		boolean isList = false;
		String tempfieldName = null;
		if (fieldName.endsWith("List")){
			tempfieldName = fieldName.substring(0, fieldName.length()-4);
			isList = true;
		} else {
			tempfieldName = fieldName;
		}
		Map<String, Object> cacheMap = getCacheMapByFieldName(tempfieldName);
			Object[] fieldDataArray = new Object[dataSize];
			for (int i=0; i<dataSize; i++){
				 try {
					T data = dataList.get(i);
					String convertString = null;
					if (isList){
						String[] arr = BeanUtils.getArrayProperty(data, fieldName);
						convertString = "";
						if (arr == null){
							continue;
						}
						for (String str : arr) {
							convertString += getCacheValueSingle(str, tempfieldName, cacheMap) + "\r\n";
						}
					} else {
						String originalValueString =  BeanUtils.getProperty(data, fieldName);
						convertString = getCacheValueSingle(originalValueString, fieldName, cacheMap);
					}
					fieldDataArray[i] = convertString;
				} catch (Exception e) {
				}//try-cache
			}
		 return fieldDataArray;
	}
	
	private <T> String getCacheValueSingle( String originalValueString, String fieldName, Map<String, Object> cacheMap) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		String convertString = "";
		if (StringUtils.isBlank(originalValueString) || "null".equals(originalValueString)){
			convertString = "";
		} else {
			if (fieldName.endsWith("date") 
					|| fieldName.endsWith("Date") ){//时间类型
				convertString = originalValueString.split(" ")[0];
			} 
			else if ("nativePlace".equals(fieldName)){//籍贯
				convertString = getNativePlace(originalValueString);
			}
			else if(scoreList.contains(fieldName) && originalValueString.equals("-1")) {
				convertString =  "";
			}
			else {
				if (cacheMap == null){
					convertString = originalValueString;
				}  else {
					try {
						Object obj = cacheMap.get(originalValueString);
						String param = (String)cacheMap.get("parameter");
						convertString = getParamValue(obj, param);
					} catch (Exception e) {}
				}//if-else
			}
		}//if-else
		return convertString;
	}
	
	/**
	 * 通过fieldName(即缓存中的key) 找到缓存中对应的map， <br>将要获取的参数 放到map中返回 key = "parameter"
	 * @param fieldName
	 * @return
	 * @author RanWeizheng
	 * @date 2016年8月26日 下午2:19:53
	 */
	private Map<String, Object> getCacheMapByFieldName(String fieldName){
		if (StringUtils.isBlank(fieldName)){
			return null;
		} else if (fieldName.contains("-")){
			fieldName = fieldName.substring(fieldName.indexOf("-")+1);
		}
//		excel导入时，时间格式如果不做解析
		if (fieldName.endsWith("date") 
				|| fieldName.endsWith("Date") ){//时间类型
			return new HashMap<>();
		}
		
		String key = null;
		String param = "title";
		
		/*字典表部分*/
		if(fieldName.endsWith("Id")){ //以“Id”结尾时，表示从dictMap中获取数据
			//特殊情况
			switch (fieldName) {
				case "provinceId":
					key = CacheComponent.KEY_PROVINCE;
					break; 
				case "comeFromId":
					key = CacheComponent.KEY_PROVINCE;//特殊的，生源地只可能是省级地区
					break;
				case "departmentId":
					key = CacheComponent.KEY_DIVISION;// 部门 ,页面上都用的department  ， 这里包括了dict_edu_common_department中的（id>1）所有数据
					break;
				case "divisionId":
					key = CacheComponent.KEY_DIVISION;
					break;
				case "naturalDepartmentId":
					key = CacheComponent.KEY_DIVISION;
					break;
				case "oldDepartmentId":
					key = CacheComponent.KEY_DIVISION;
					break;
				case "stuDepartmentId":
					key = CacheComponent.KEY_DIVISION;
					break;
				case "replaceDepartmentId":
					key = CacheComponent.KEY_DIVISION;
					break;
				case "campusId":
					key = CacheComponent.KEY_CAMPUS;// 校区
					break;
				case "replaceCampusId":
					key = CacheComponent.KEY_CAMPUS;// 校区
					break;
				case "studentId":
					key = CacheComponent.KEY_SYSUSER;
					param = "username";
					break;
				case "userId":
					key = CacheComponent.KEY_SYSUSER;
					param = "username";
					break;
				default:
					key = fieldName.substring(0, fieldName.length()-2).toLowerCase();
			}//switch
		}
		else {
			switch (fieldName) {
			case "certType":
				key = CacheComponent.KEY_CERT;
				break;
			case "examIllegalReason"://成绩-原因 （各种改变考试状态的原因）
				key = CacheComponent.KEY_ALLSCRREASON;
				break;
			default:
				break;
			}//switch
		}//if-else
		if (key != null){
			Map<String, Object>  dictMap = getDictMap(key);
			dictMap.put("parameter", param);
			return dictMap;
		}
		
		/*标签部分*/
		switch (fieldName) {
		case "paymentFlag":
			key = "paymentFlag";
			break;
		case "clazzStatus":
			key = "clazzStatus";
			break;
		case "status":
			key = "status";
			break;
		case "standard":
			key = "standard";
			break;
		case "majorType":
			key = "majorType";
			break;
		case "departmentType":
			key = "departmentType";
			break;
		case "presenceFlag":
			key = "presenceFlag";
			break;
		case "rollFlag":
			key = "rollFlag";
			break;
		case "registerStatus":
			key = "registerStatus";
			break;
		case "confirmFlag":
			key = "confirmFlag";
			break;
		case "employeeType":
			key = "employeeType";
			break;
		case "scheduleStatus":
			key = "scheduleStatus";
			break;
		case "jobType":
			key = "jobType";
			break;
		case "gender":
			key = "gender";
			break;
		case "status_name":
			key = "status_name";
			break;
		case "startStatus":
			key = "startStatus";
			break;
		case Constants.KEY_WCM:
			key = Constants.KEY_WCM;
			break;
		case Constants.KEY_CCM:
			key = Constants.KEY_CCM;
			break;
		case "forceFlag":
			key = "forceFlag";
			break;
		case "optionalFlag":
			key = "optionalFlag";
			break;
		case "coopFlag":
			key = "coopFlag";
			break;
		case "examFlag":
			key = "examFlag";
			break;
		case "showUrl":
			key = "showUrl";
			break;
		case "templateFormat":
			key = "templateFormat";
			break;
		case "templateType":
			key = "templateType";
			break;
		case "processStatus":
			key = "processStatus";
			break;
		case "standardFileType":
			key = "standardFileType";
			break;
		case "courseTypeGeneral":
			key = "courseTypeGeneral";
			break;
		case "scrTranscriptSign":
			key = "scrTranscriptSign";
			break;
		case "scrStudyResult":
			key = "scrStudyResult";
			break;
		case "confirmStatus":
			key = "confirmStatus";
			break;
		/*case == key 的部分结束*/	
		case "examType"://成绩-考试类型
			key = "scrExamType";
			break;
		case "examStatus"://成绩-考试状态
			key = "scrExamStatus";
			break;
		case "examStatus1"://成绩-考试状态
			key = "scrExamStatus1";
			break;
		case "studentCreditResult"://成绩-成绩结果
			key = "scrStudyResult";
			break;
		case "creditGeneralType"://成绩-修习类型
			key = "scrStudyType";
			break;
		case "transcriptSign"://成绩-成绩单标识
			key = "scrTranscriptSign";
			break;
		case "tchCreditDetailStatus"://教学班成绩提交状态
			key = "tchCreditDetailStatus";
			break;
		case "revType"://重修类型
			key = "revtype";
			break;
		default:
			if (fieldName.endsWith("Flag") || fieldName.endsWith("flag") ){//是否毕业、是否新生
				key = "isZeroORNot";
			}
			break;
		}
		if (key != null){
			Map<String, Object>  labelMap = getLabelMap(key);
			labelMap.put("parameter", param);
			return labelMap;
		}
		
		return null;
	}
	
	/**
	 * 获取字典表缓存中的指定表（dictKey）的缓存信息
	 * @author RanWeizheng
	 * @param dictKey   dictMap中指定表map的key
	 * @return  如果值不存在或者出错，则返回""
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getDictMap(String dictKey){
		Map<String, Object> map = null;
		try {
			map = ((Map<String, Object>) cacheComponent.resource(dictKey));
		} catch (Exception e) {
		}
		return map!=null ? map : new HashMap<String, Object>();
	};
	
	/**
	 * 获取标签缓存中的指定表（labelKey）的缓存信息
	 * @param labelKey
	 * @param labelMap
	 * @return 如果值不存在或者出错，则返回""
	 */
	@SuppressWarnings("unchecked")
	public  Map<String, Object>  getLabelMap(String labelKey){
		Map<String, Object> map = null;
		try {
			map = (Map<String, Object>)(((Map<String, Object>) cacheComponent.resource(CacheComponent.KEY_STATICLABEL)).get(labelKey));
		} catch (Exception e) {
 
		}
		return map!=null ? map : new HashMap<String, Object>();
	};
	
	/**
	 * 获取字典表缓存中的指定表（dictKey）中指定记录（dataKey）的title值
	 * @author RanWeizheng
	 * @param dictKey   dictMap中指定表map的key
	 * @param dataKey  表map中指定记录的key值
	 * @param dictMap  字典表 Map<dictKey, Map<dataKey, Object>>    
	 * @return  如果值不存在或者出错，则返回""
	 */
	@SuppressWarnings("unchecked")
	public String getDictData(String dictKey, String dataKey){
		try {
			Object obj = ((Map<String, Object>) cacheComponent.resource(dictKey)).get(dataKey);
			if (obj!=null)
				return getParamValue(obj, "title");//获得obj中的title属性值 ，这个与系统的数据结构实现有关
		} catch (Exception e) {
 
		}
		return "";
	};
	
	/**
	 * 获取字典表缓存中的指定表（dictKey）中指定记录（dataKey）的title值
	 * @author RanWeizheng
	 * @param dictKey   dictMap中指定表map的key
	 * @param dataKey  表map中指定记录的key值
	 * @param dictMap  字典表 Map<dictKey, Map<dataKey, Object>>    
	 * @return  如果值不存在或者出错，则返回""
	 */
	@SuppressWarnings("unchecked")
	public String getDictData(String dictKey, String dataKey, String parameter){
		try {
			Object obj = ((Map<String, Object>) cacheComponent.resource(dictKey)).get(dataKey);
			return getParamValue(obj, parameter);
		} catch (Exception e) {
 
		}
		return "";
	};
	
	/**
	 * 获得obj中的指定参数的属性值
	 * @author RanWeizheng
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String getParamValue(Object obj, String parameter){
		if (obj!=null){
			String value = ""; 
			try {
				value = ReflectionUtils.invokeGetterMethod(obj, parameter).toString();
			} catch (Exception e){
				value = ((Map<String, Object>)obj).get(parameter).toString();
			}
			return value;
		}
		return "";
	}
	
	/**
	 * 获取标签缓存中的指定表（labelKey）中指定记录（dataKey）的title值
	 * @param labelKey
	 * @param dataKey
	 * @param labelMap
	 * @return 如果值不存在或者出错，则返回""
	 */
	@SuppressWarnings("unchecked")
	public String getLabelData(String labelKey, String dataKey){
		try {
			LabelVo labelVo = (LabelVo)((Map<String, Object>)(((Map<String, Object>) cacheComponent.resource(CacheComponent.KEY_STATICLABEL)).get(labelKey))).get(dataKey);
			if (labelVo != null){
				return labelVo.getTitle();
			}
		} catch (Exception e) {
 
		}
		return "";
	};
	
	/**
	 * @author RanWeizheng
	 * @param dictMap
	 * @param majorFieldId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getMajorFullName(Integer majorFieldId){
		String name = "";
		try {
			Map<String, Object> majorfieldMap = (Map<String, Object>) cacheComponent.resource("majorfield");
			Map<String, Object> majorMap = (Map<String, Object>) cacheComponent.resource("major");
			DictEduCommonMajorField dictEduCommonMajorField = new Gson().fromJson( new Gson().toJson(majorfieldMap.get(majorFieldId.toString())), DictEduCommonMajorField.class);
			if (dictEduCommonMajorField != null){
				DictEduCommonMajor dictEduCommonMajor = new Gson().fromJson( new Gson().toJson(majorMap.get(dictEduCommonMajorField.getMajorId().toString())), DictEduCommonMajor.class);
				name += dictEduCommonMajor.getTitle();
				if (StringUtils.isNotBlank(dictEduCommonMajorField.getCode()) 
						&& !dictEduCommonMajorField.getCode().endsWith(Constants.DICT_EDU_COMMON_MAJOR_FIELD_CODE_DEFAULT)){
					name = name + "(" + dictEduCommonMajorField.getTitle() + ")";
				}
			}
		} catch (Exception e) {
			
		}
		return name;
	}
	
	@SuppressWarnings("unchecked")
	public String getMajorFullName4DBF(Integer majorFieldId){
		String name = "";
		try {
			String majorIdStr =  getDictData("majorfield", majorFieldId + "" , "majorId");
			String mfCode = getDictData("majorfield", majorFieldId + "" , "code");
			String majorName = "";
			String majorFieldName = "";
			if (StringUtils.isNotBlank(majorIdStr)){
				Integer majorId = Double.valueOf(majorIdStr).intValue();
				String majorPidStr =  getDictData("major", majorId + "" , "pid");
				Integer pid = StringUtils.isNotBlank(majorPidStr) ? Double.valueOf(majorPidStr).intValue() : 0; 
				if (pid == 0){//pid 不存在，为普通专业
					majorName = getDictData("major", majorId + "");
					if (StringUtils.isNotBlank(mfCode) 
							&& !mfCode.endsWith(Constants.DICT_EDU_COMMON_MAJOR_FIELD_CODE_DEFAULT)){
						majorFieldName = getDictData("majorfield", majorFieldId + "" , "title");
					}
				} else {//pid 存在,当前为校定专业
					majorName = getDictData("major", pid + "");
					String endStr = "_" + StringUtils.substringAfterLast(mfCode, "_");
					if (StringUtils.isNotBlank(mfCode) 
							&& !endStr.equals(Constants.DICT_EDU_COMMON_MAJOR_FIELD_CODE_DEFAULT)){
						//查找可能的“真正的专业方向”
						Map<String, Object> majorfieldMap = (Map<String, Object>) cacheComponent.resource("majorfield");
						for(Map.Entry<String, Object> entry : majorfieldMap.entrySet()){
							Map<String, Object> mf =  (Map<String, Object>)entry.getValue();
							if (((String)mf.get("majorId")).equals(pid.toString())
								&& ((String)mf.get("code")).endsWith(endStr)   ){
								majorFieldName = mf.get("title") + "";
								break;
							}//if
						}//for
					}//if
				}
			}
			if (StringUtils.isNoneBlank(majorName)){
				name += majorName;
				if (StringUtils.isNoneBlank(majorFieldName)){
					name = name + "(" + majorFieldName  + ")";
				}
			}
		} catch (Exception e) {
			
		}
		return name;
	}
	
	//为dbf获取专业数据
	public String getMajorInfo4DBF(String majorId, String parameter){
		String pidStr = getDictData("major", majorId, "pid");
		int pid = StringUtils.isNotBlank(pidStr) ? Double.valueOf(pidStr).intValue() : 0; 
		if (pid > 0){//pid 存在 
			return getDictData("major", pid + "" , parameter);
		} else {
			return getDictData("major", majorId, parameter);
		}
	}
	
	/**
	 *  根据籍贯的最下一级的id 返回完整的籍贯信息； ps 籍贯信息是形如   省级 + 县级 这样的格式，跟3级结构有所区别
	 *  数据可以在dictmap.get( Constants.CASCADE_KEY_PROVINCE) 中获取，已经组织为级联形式
	 * 
	 * @param dictMap
	 * @param nativePlaceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getNativePlace(String nativePlaceId){ 
		Map<String, Object> provinceCascadeMap = (Map<String, Object>) cacheComponent.resource(Constants.CASCADE_KEY_PROVINCE);
		if (provinceCascadeMap ==null){
			return "";
		}
		
		DictCascadeVo provinceVo = null;//省级
		@SuppressWarnings("unused")
		DictCascadeVo cityVo = null; //市级
		DictCascadeVo countyVo = null;//县级
		
		try {
			//遍历 找到可能的
			for (String provinceKey : provinceCascadeMap.keySet()) {
				if (provinceKey.equals(nativePlaceId)){
					provinceVo = (DictCascadeVo) provinceCascadeMap.get(provinceKey);
					break;
				}
				DictCascadeVo tempProvince =  (DictCascadeVo) provinceCascadeMap.get(provinceKey);
				if (tempProvince == null || tempProvince.getChild()==null){
					continue;
				}
				for (DictCascadeVo city : tempProvince.getChild()){
					if (nativePlaceId.equals(city.getId())){
						provinceVo = tempProvince;
						cityVo = city;
						break;
					} else if (city.getChild()!=null){
						for (DictCascadeVo county : city.getChild()){
							if (nativePlaceId.equals(county.getId())){
								provinceVo = tempProvince;
								cityVo = city;
								countyVo = county;
								break;
							} 
						}//for
					}//if-else
				}//for
			}//for
		} catch (Exception e){
		}
		String nativePlaceStr = "";
		if(provinceVo != null){
			nativePlaceStr += provinceVo.getTitle();
			if (countyVo != null){
				nativePlaceStr += " " + countyVo.getTitle();
			}
		}
		return nativePlaceStr;
	}
	
	
	/**
	 * 不同业务中需要自行添加，根据不同的 fieldName ,将文字转换成相应的id
	 */
	public int convertDataToId(String fieldName, String originalValueString){
		if (StringUtils.isBlank(fieldName)){
			return -1;
		}
		
		if(fieldName.endsWith("Id")){ //以“Id”结尾时，表示从dictMap中获取数据
			String key = null;
			//特殊情况
			if ("clazzId".equals(fieldName)){//班级Id
				key = Constants.TABLE_KEY_EDU_ROLL_NATURALCLAZZ;
			}
			else if ("provinceId".equals(fieldName)){
				key = Constants.CASCADE_KEY_PROVINCE;
			} 
			else if ("courseTypeId".equals(fieldName)){
				key = Constants.TABLE_KEY_EDU_SKD_COURSE_TYPE;
			} 
			else if ("courseId".equals(fieldName)){
				key = Constants.TABLE_KEY_EDU_SKD_COURSE;
			} 
			else if ("employeeId".equals(fieldName)){
				key = Constants.KEY_EDU_TCH_EMPLOYEE_NAME;
			}
			else if ("departmentId".equals(fieldName) || "divisionId".equals(fieldName) || "naturalDepartmentId".equals(fieldName)){
				key = Constants.KEY_DIVISION;// 部门 ,页面上都用的department  ， 这里包括了dict_edu_common_department中的（id>1）所有数据
			}
			else if ("comeFromId".equals(fieldName)){
				key = Constants.CASCADE_KEY_PROVINCE;//特殊的，生源地只可能是省级地区
			}
			else {
				key = fieldName.substring(0, fieldName.length()-2).toLowerCase();
			}//if-else
			
			if ("policitalStatusId".equals(fieldName)){//政治面貌, 优先根据简称查找
				int resultId = getIdFromMap(key, originalValueString, "shortName");
				if (resultId >= 0){
					return resultId;
				}
			}
			return getIdFromMap(key, originalValueString);
		}
		
		if ("certType".equals(fieldName)){
			return getIdFromMap("cert", originalValueString);
		}
		
		if ("nativePlace".equals(fieldName)){//籍贯
			return getIdFromMap("area", originalValueString);
		}
	
	
		if ("paymentFlag".equals(fieldName) || "clazzStatus".equals(fieldName) || "status".equals(fieldName)
				|| "standard".equals(fieldName) || "majorType".equals(fieldName) || "departmentType".equals(fieldName)
				|| "presenceFlag".equals(fieldName) || "rollFlag".equals(fieldName) || "paymentFlag".equals(fieldName)
				|| "registerStatus".equals(fieldName) || "confirmFlag".equals(fieldName) || "employeeType".equals(fieldName)
				|| "scheduleStatus".equals(fieldName) || "jobType".equals(fieldName) || "gender".equals(fieldName)
				|| "status_name".equals(fieldName) || "startStatus".equals(fieldName) || (Constants.KEY_WCM).equals(fieldName)
				|| (Constants.KEY_CCM).equals(fieldName) || "forceFlag".equals(fieldName) || "optionalFlag".equals(fieldName)
				|| "coopFlag".equals(fieldName) || "examFlag".equals(fieldName) || "showUrl".equals(fieldName)
				|| "templateFormat".equals(fieldName) || "templateType".equals(fieldName) || "processStatus".equals(fieldName)
				|| "standardFileType".equals(fieldName)|| "confirmStatus".equals(fieldName)) {

			return getLabelId(fieldName, originalValueString);
		} else if (fieldName.endsWith("Flag") || fieldName.endsWith("flag") ){//是否毕业、是否新生
			return getLabelId("isZeroORNot", originalValueString);
		}
		
		//剩余的是未被转换的
		return -1;
		
	}
	
	/**
	 *  根据内容和参数名，找到指定的id
	 * @param allDictMap
	 * @param content
	 * @param parameterName 默认为“title”
	 * @return
	 */
	public int getIdFromMap(String dictKey, String content){
		return getIdFromMap(dictKey, content, "title");
	}
	
	/**
	 *  根据内容和参数名，找到指定的id
	 * @param allDictMap
	 * @param content
	 * @param parameterName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getIdFromMap(String dictKey, String content, String parameterName){
		Map<String, Object> dictMap  = (Map<String, Object>)cacheComponent.resource(dictKey);
		if (dictMap == null){
			return -1;
		}
		Set<String> keysSet = dictMap.keySet();
		Iterator<String> iterator = keysSet.iterator();
		
		while(iterator.hasNext()) {
			String key = (String)iterator.next();//key
			//通过反射获取 值
			Object obj = dictMap.get(key);
			if (obj != null ){
				try {
					String title = getParamValue(obj, parameterName);
					if (content.equals(title)){
						return Integer.parseInt(key);
					}
				} catch (Exception e) {
					
				}//catch
			}//if
		}//while
		
		return -1;
	}
	
	/**
	 *  根据内容和参数名，找到指定的id
	 * @param dictMap
	 * @param content
	 * @param parameterName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getLabelId(String labelKey, String content){
		if (StringUtils.isBlank(content)){
			return -1;
		}
		try {
			Map<String, Object> labels = (Map<String, Object>) ((Map<String, Object>)cacheComponent.resource(CacheComponent.KEY_STATICLABEL)).get(labelKey);
			Set<String> keysSet = labels.keySet();
			Iterator<String> iterator = keysSet.iterator();
			while(iterator.hasNext()) {
				String key = (String)iterator.next();//key
				LabelVo label = (LabelVo)labels.get(key);
				if (content.equals(label.getTitle())){
					return Integer.parseInt(key);
				}
			}
		} catch (Exception e) {
 
		}
		return -1;
	}
	
	/**
	 * 取得当前学年学期code
	 * @RP		根据redis中的 "allyearterm" 取得
	 * @param	
	 * @return	
	 * @throws ParseException 
	 * @create 	wangkang 2016年10月27日 下午2:59:48
	 * @modify
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public String getCurrYearTermCode(){
		
		String code = null;
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Map<String, DictEduCommonAcdYearTerm> allMap = (Map<String, DictEduCommonAcdYearTerm>) cacheComponent.resource(cacheComponent.KEY_AllYEARTERM);
		for(Entry<String,DictEduCommonAcdYearTerm> entry:allMap.entrySet()){
			String key = entry.getKey();
			DictEduCommonAcdYearTerm value = entry.getValue();
			Date start = value.getStartDate();
			Date end = value.getEndDate();
			if(DateUtils.isBetween(start, end, date) == 1){
				code = key;
				break;
			}
		}
		
		// 缓存没有去数据库查找：当前学期规则：取学年学期表中开始时间所有小于等于当前时间最大的一个。
		if(com.shframework.common.util.StringUtils.isBlank(code)){
			DictEduCommonAcdYearTermExample example = new DictEduCommonAcdYearTermExample();
			example.setOrderByClause("id DESC");
			example.createCriteria().andStartDateLessThanOrEqualTo(new Date())
				.andLogicDeleteEqualTo(Constants.BASE_LOGIC_DELETE_ZERO)
				.andStatusEqualTo(Constants.DICT_COMMON_YES);
			List<DictEduCommonAcdYearTerm> list = dictEduCommonAcdYearTermMapper.selectByExample1(example);
			if(list != null && list.size() > 0){
				code = list.get(0).getCode();
			}
		}
		return code;
	}
	
	/**
	 * 根据某一学年学期code取得某一学年学期对象
	 * @RP: 	
	 * @param	
	 * @return		
	 * @create 	wangkang 2016年10月27日 下午4:44:26
	 * @modify
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public DictEduCommonAcdYearTerm getYearTermByCode(String code){
		
		DictEduCommonAcdYearTerm yearTerm = null;
		Map<String, DictEduCommonAcdYearTerm> map = (Map<String, DictEduCommonAcdYearTerm>)cacheComponent.resource(cacheComponent.KEY_AllYEARTERM);
		if(!map.isEmpty() && map.get(code) != null){
			yearTerm = (DictEduCommonAcdYearTerm)map.get(code);
		}
		
		// 缓存没有去数据库查找：当前学期规则：取学年学期表中开始时间所有小于等于当前时间最大的一个。
		if(yearTerm == null){
			yearTerm = dictEduCommonAcdYearTermMapper.getYearTermByCode(getCurrYearTermCode());
		}
		return yearTerm;
	}
	
	/**
	 * 根据偏移量取学年学期对象
	 * @RP: 	
	 * @param	
	 * @return	
	 * @create 	wangkang 2016年10月27日 下午5:26:29
	 * @modify
	 */
	public DictEduCommonAcdYearTerm getYearTerm(Integer offset){
		
		String code = AcdYearTermUtil.getGraduateYearTermCode(getCurrYearTermCode(), offset);
		return getYearTermByCode(code);
	}
	
	/**
	 * 根据偏移量取当前学年学期对象
	 * @RP: 	
	 * @param	
	 * @return		
	 * @create 	wangkang 2016年10月27日 下午5:26:29
	 * @modify
	 */
	public DictEduCommonAcdYearTerm getYearTerm(){
		return getYearTerm(0);
	}
	
	/**
	 * 根据偏移量取学年学期的id
	 * @RP: 	
	 * @param	
	 * @return		
	 * @create 	wangkang 2016年10月27日 下午5:26:29
	 * @modify
	 */
	public Integer getYearTermId(Integer offset){
		
		String code = AcdYearTermUtil.getGraduateYearTermCode(getCurrYearTermCode(), offset);
		return getYearTermByCode(code) != null ? getYearTermByCode(code).getId() : null;
	}
	
	/**
	 * 根据学年学期Code及偏移量取学年学期的id
	 * @RP: 	
	 * @param	
	 * @return		
	 * @create 	madongdong 2017年01月19日 下午15:40:10
	 * @modify
	 */	
	public Integer getYearTermId(String yearTermCode, Integer offset){
		String code = AcdYearTermUtil.getGraduateYearTermCode(yearTermCode, offset);
		return getYearTermByCode(code) != null ? getYearTermByCode(code).getId() : null;
	}
	
	/**
	 * 根据偏移量取当前学年学期的id
	 * @RP: 	
	 * @param	
	 * @return		
	 * @create 	wangkang 2016年10月27日 下午5:26:29
	 * @modify
	 */
	public Integer getYearTermId(){
		return getYearTermId(0);
	}
	
	/**
	 * 根据偏移量list 获取学年学期List
	 * @param offsetList
	 * @return
	 */
	public List<DictEduCommonAcdYearTerm> getYearTermList(Integer[] offsetArray){
		List<DictEduCommonAcdYearTerm>  yearTermList = new ArrayList<DictEduCommonAcdYearTerm>();
		if(offsetArray != null && offsetArray.length > 0){
			for(Integer offset : offsetArray){
				yearTermList.add(getYearTerm(offset));
			}
		}
		
		return yearTermList;
	}
	
	/**
	 * 根据偏移量list 获取学年学期id List
	 * @param offsetList
	 * @return
	 * @create madongdong
	 */	
	public List<Integer> getYearTermIdList(Integer acdYearTermId,String yearTermCode, Integer[] offsetArray){
		List<Integer>  yearTermList = new ArrayList<Integer>();
		if(offsetArray != null && offsetArray.length > 0){
			for(Integer offset : offsetArray){
				Integer yearTermId = getYearTermId(yearTermCode, offset);
				if(yearTermId.intValue() != acdYearTermId.intValue()){
					if(!yearTermList.contains(yearTermId)){
						yearTermList.add(yearTermId);
					}
				}
			}
		}
		
		return yearTermList;
	}	
	
	/**
	 * 根据日期取得学年学期code
	 * @RP		根据redis中的 "allyearterm" 取得
	 * @param	
	 * @return	
	 * @throws ParseException 
	 * @create 	wangkang 2016年10月27日 下午2:59:48
	 * @modify
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public String getYearTermCode(String dateStr){
		
		String code = null;
		String pattern = "yyyy-MM";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Map<String, DictEduCommonAcdYearTerm> allMap = (Map<String, DictEduCommonAcdYearTerm>) cacheComponent.resource(cacheComponent.KEY_AllYEARTERM);
		for(Entry<String,DictEduCommonAcdYearTerm> entry:allMap.entrySet()){
			String key = entry.getKey();
			DictEduCommonAcdYearTerm value = entry.getValue();
			Date start = value.getStartDate();
			Date end = value.getEndDate();
			if(DateUtils.isBetween(start, end, date) == 1){
				code = key;
				break;
			}
		}
		
		// 缓存没有去数据库查找：当前学期规则：取学年学期表中开始时间所有小于等于当前时间最大的一个。
		if(com.shframework.common.util.StringUtils.isBlank(code)){
			DictEduCommonAcdYearTermExample example = new DictEduCommonAcdYearTermExample();
			example.setOrderByClause("id DESC");
			example.createCriteria().andStartDateLessThanOrEqualTo(date)
				.andLogicDeleteEqualTo(Constants.BASE_LOGIC_DELETE_ZERO)
				.andStatusEqualTo(Constants.DICT_COMMON_YES);
			List<DictEduCommonAcdYearTerm> list = dictEduCommonAcdYearTermMapper.selectByExample1(example);
			if(list != null && list.size() > 0){
				code = list.get(0).getCode();
			}
		}
		return code;
	}	
}
