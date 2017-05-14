package com.shframework.modules.comn.service.impl;

import static com.shframework.common.util.Constants.getProperty;
import static com.shframework.common.util.ParamsUtil.createProcResMap;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shframework.common.util.Constants;
import com.shframework.common.util.DBFUtil;
import com.shframework.common.util.DateUtils;
import com.shframework.common.util.ExcelUtils;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.common.util.ResourceTemplateUtils;
import com.shframework.modules.basic.service.LabelService;
import com.shframework.modules.comn.entity.vo.CriteriaParamVO;
import com.shframework.modules.comn.service.TemplateService;
import com.shframework.modules.dict.helper.DictCacheComponent;
import com.shframework.modules.sys.entity.ColumnComment;
import com.shframework.modules.sys.entity.ComnTemplate;
import com.shframework.modules.sys.entity.ComnTemplateDetail;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.mapper.ComnTemplateDetailMapper;
import com.shframework.modules.sys.mapper.ComnTemplateMapper;
import com.shframework.modules.sys.mapper.ResourceMapper;

/**
 * <p>模板设置：各业务通用。</p>
 * <p>主要方法：1、新增Excel格式导出模板 2、新增DBF格式导出模板 3、新增Excel格式导入模板</p>
 * <p>重要方法：1、save* 2、update* </p>
 * @author zhangjk
 */
@Service
public class TemplateServiceImpl implements TemplateService{
	private static final String defaultSortField = "_comn.id";
	private static final String defaultSortOrderby = "desc";
	
	@Autowired
	ComnTemplateDetailMapper comnTemplateDetailMapper;
	@Autowired
	ComnTemplateMapper comnTemplateMapper;
	@Autowired
	private DictCacheComponent dictCacheComponent;

	@Autowired
	LabelService labelService;
	
	@Autowired
	ResourceMapper resourceMapper;
	
	@Override
	public PageTerminal<ComnTemplate> queryTemplate(
			PageSupport pageSupport,String module) {
		Resource resource = resourceMapper.selectByRule(module);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport, defaultSortField + " " + defaultSortOrderby);
		map.put("resourceId", resource.getId());
		List<ComnTemplate> list = comnTemplateMapper.queryTemplate(map);
		return new PageTerminal<ComnTemplate>(list, pageSupport, comnTemplateMapper.queryTemplateCount(map));
	}
	

	@Override
	public List<ComnTemplateDetail> queryTemplateDetails(Integer templateId) {
		
		List<ComnTemplateDetail> TemplateDetails = comnTemplateDetailMapper.getComnTemplateDetailListByTemplateId(templateId);
		return TemplateDetails;
	}

	/**
	 * 保存导入、导出模板，各模块通用
	 * @param template 模板主表
	 * @param resourceId	  资源标识	
	 * @param my_multiple_select 模板详情表需要字段，以数组封装
	 * @param user 当前登录用户
	 * @author zhangjk
	 */
	@Override
	@Transactional
	public void saveTemplate(ComnTemplate template, String rule,String[] my_multiple_select,User user) {
		Resource resource = resourceMapper.selectByRule(rule);
//		my_multiple_select 数组元素 如： _erph,academic_year_id,收费区间
		List<CriteriaParamVO>  returnListVO  = ResourceTemplateUtils.getCriteriaParamVOByKey(rule);
	    Gson gson = new Gson();
	    String gsonStr = gson.toJson(returnListVO);
		template.setCriteria(gsonStr);
		template.setResourceId(resource.getId());
		template.setLastModifyUserId(user.getId());
		template.setCreateDate(new Date());
		template.setModifyDate(new Date());
		comnTemplateMapper.insertSelective(template);
		
		ComnTemplateDetail templateDetail = new ComnTemplateDetail();
		
		templateDetail.setTemplateId(template.getId());
		if(null !=my_multiple_select && my_multiple_select.length>0){
			for(int i=0;i<my_multiple_select.length;i++){
				String [] arrayItem = my_multiple_select[i].split(",");
				if(arrayItem.length==3){
					templateDetail.setTableDbAlias(arrayItem[0]);
					templateDetail.setColDbName(arrayItem[1]);
					templateDetail.setColFileName(arrayItem[2]);
					templateDetail.setPriority(i);
					comnTemplateDetailMapper.insertSelective(templateDetail);
				}
			}
		}
	}

	@Override
	public void deleteTemplate(Integer templateId) {
		comnTemplateMapper.deleteByPrimaryKey(templateId);
	}

	@Override
	public ComnTemplate queryTemplate(Integer templateId) {
		return comnTemplateMapper.selectByPrimaryKey(templateId);
	}

	@Override
	@Transactional
	public void updateExportTemplate(ComnTemplate template,
			String[] my_multiple_select, User user) {
		template.setLastModifyUserId(user.getId());
		template.setModifyDate(new Date());
		comnTemplateMapper.updateByPrimaryKeySelective(template);
		
		deleteByTemplateId(template.getId());
		ComnTemplateDetail templateDetail = new ComnTemplateDetail();
		
		templateDetail.setTemplateId(template.getId());
		if(null !=my_multiple_select && my_multiple_select.length>0){
			for(int i=0;i<my_multiple_select.length;i++){
				String [] arrayItem = my_multiple_select[i].split(",");
				templateDetail.setTableDbAlias(arrayItem[0]);
				templateDetail.setColDbName(arrayItem[1]);
				templateDetail.setColFileName(arrayItem[2]);
				templateDetail.setPriority(i);
				comnTemplateDetailMapper.insertSelective(templateDetail);
			}
		}
	}

	@Override
	public int deleteByTemplateId(Integer templateId) {
		return comnTemplateDetailMapper.deleteByTemplateId(templateId);
	}

	@Override
	public List<ColumnComment> getComment(
			List<LinkedHashMap<String, String>> moduleList) {
		return comnTemplateDetailMapper.getComment(moduleList);
	}
	
	public List<ComnTemplateDetail> getComnTemplateDetailListByTemplateId(Integer templateId) {
		return comnTemplateDetailMapper.getComnTemplateDetailListByTemplateId(templateId);
	}
	

	/**
     * file_format 0:excel， 1:dbf ; 
     * type 0:导入 ，1：导出 ;
     * @param map
     * @return
     */
	@Override
	public List<ComnTemplate> queryTemplateListByMap(Map<String, Object> map) {
		return comnTemplateMapper.queryTemplateListByMap(map);
	}

	private static String changeFirstLetter(String source) {
	    String[] sourcearr =  source.split("_");
		
		for (int i=1 ; i < sourcearr.length;i++){
			char schar = Character.toUpperCase(sourcearr[i].charAt(0));
			String news = String.valueOf(schar).concat(sourcearr[i].substring(1));
			sourcearr[i] = news;
		}
		String target = StringUtils.join(sourcearr);
		return target;
	}


	@Override
	public byte[] downloadImportExcelFile(Integer templateId) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<ComnTemplateDetail>detailList = comnTemplateDetailMapper.getComnTemplateDetailListByTemplateId(templateId);
		String[] fieldNames = new String[detailList.size()];
		for(int i = 0;i<fieldNames.length;i++){
			fieldNames[i] = detailList.get(i).getColFileName();
		}
		return ExcelUtils.writeExcelHeader(fieldNames);
	}


	@Override
	public Map<String, String> getMatchingResultByTemplateId(Integer templateId) {
		//modify by zhangjk,废除 MapInterceptor 拦截器 
//		matchingResult 匹配结果 例如：{收费区间:academic_year_id,...}
//		Map<String,String> matchingResult = new LinkedHashMap<String, String>();
//		MapParam mapParam = new MapParam("col_file_name", "col_db_name");
//		mapParam.put("templateId", templateId);
//		matchingResult = comnTemplateDetailMapper.getMap(mapParam);
//		return matchingResult;
		Map<String,String> matchingResult = new LinkedHashMap<String, String>();

		List<ComnTemplateDetail> templateDetailList = comnTemplateDetailMapper.getComnTemplateDetailListByTemplateId(templateId);
		for(ComnTemplateDetail detail : templateDetailList){
			matchingResult.put(detail.getColFileName(), detail.getColDbName());
		}
		return matchingResult;
	}


	@Override
	public Map<String, String> getColumnHeaderMapByTemplateId(Integer templateId) {
		Map<String,String> matchingResult = new LinkedHashMap<String, String>();

		List<ComnTemplateDetail> templateDetailList = comnTemplateDetailMapper.getComnTemplateDetailListByTemplateId(templateId);
		for(ComnTemplateDetail detail : templateDetailList){
			matchingResult.put(detail.getColDbName(), detail.getColFileName());
		}
		return matchingResult;
	}
	
	public String fileUpload(String parent, MultipartFile file) throws IOException {
	String originalFilename = file.getOriginalFilename();
		
		String format = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		
		String child = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "." + format;

		
    	FileUtils.copyInputStreamToFile(file.getInputStream(), new File(getProperty("file.root.savepath") + getProperty("file.tmp.path.input") + parent, child));
		String realPath = getProperty("file.root.savepath")+getProperty("file.tmp.path.input")+ parent + child;
		return realPath;
	}

	@Override
	public Integer selectResourceIdByRule(String module) {
		Resource resource = resourceMapper.selectByRule(module);
		return resource.getId();
	}

	/**
	 * <p>导出excel（除“缴费管理”导出功能外，都可调用此方法）</p>
	 * @param idList 主键集合
	 * @param templateId 模板id
	 * @param module 资源rule
	 * @param pageSupport （criteriaList 为空时，搜索条件在sql语句中就会使用）
	 * @return 返回 {"导出数据条数","文件名称)"};导出文件异常时，返回”null"
	 */
	@Override
	@Transactional
	public String[] saveExportExcelByIdList(String[] idList,
			Integer templateId, String module, PageSupport pageSupport) {
		
		List<ComnTemplateDetail> templateDetailList = comnTemplateDetailMapper.getComnTemplateDetailListByTemplateId(templateId);
		ComnTemplate template = comnTemplateMapper.selectByPrimaryKey(templateId);
		
		Gson gson = new Gson();
		if(null != template && StringUtils.isNotEmpty(template.getCriteria())){
				// json转为带泛型的list 
				List<CriteriaParamVO> criteriaList = gson.fromJson(template.getCriteria(),
						new TypeToken<List<CriteriaParamVO>>() { 
				}.getType()); 
			int colsSize = templateDetailList.size();
			String filePath = ExcelUtils.getExcelFileFullPath();
			String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String fileFullPathName = filePath + fileName;
			String[] colFileNames = new String[colsSize]; //  文件表头字段数组
			List<String> propertyColDbNames = new ArrayList<String>();
			// 根据不同的模板详情取得SQL语句的结果字段部分
			String columnSql = "";
			for (int i = 0; i < colsSize; i++) {
				ComnTemplateDetail detail = templateDetailList.get(i);
				String propertyColDbName =  detail.getTableDbAlias() + "-" + changeFirstLetter(detail.getColDbName());
				columnSql += detail.getTableDbAlias() + "." + detail.getColDbName() + " AS " + "`" + propertyColDbName + "`" + ",";
				String colFileName = detail.getColFileName();
				String colFileName2 = colFileName.substring(colFileName.indexOf(")")+1);
				colFileNames[i] = colFileName2;
				propertyColDbNames.add(propertyColDbName);
			}
			columnSql = columnSql.substring(0, columnSql.length() - 1);
			
			Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport);

			if (StringUtils.isNotBlank(columnSql)) {
				map.put("columnSql", columnSql);
				// 根据不同的模板详情取得SQL语句的from部分
				StringBuffer fromSql = new StringBuffer();
				for(int index=0;index< criteriaList.size();index++){
					CriteriaParamVO vo = criteriaList.get(index);
					if(index==0){
						fromSql.append(vo.getTableName()).append(" AS ").append(vo.getTableAlias());
					}else{
						fromSql.append(" ").append(vo.getRelationMethod()).append(" ").append(vo.getTableName()).append(" AS ").append(vo.getTableAlias()).append(" ON ").append(vo.getRelation());
					}
				}
				map.put("fromSql", fromSql);
				
				String[] relationPrimaryKey = criteriaList.get(0).getRelationPrimaryKey();
				map.put("whereSql",relationPrimaryKey);
				String searchCondition = (String) map.get("searchCondition");
				if(relationPrimaryKey.length==1 && null != idList && idList.length >0){
					if(null != searchCondition && StringUtils.isNotBlank(searchCondition)){
						if(searchCondition.indexOf("order by") != -1)
							searchCondition = searchCondition.substring(0, searchCondition.indexOf("order by"));
					}
					map.put("idList",idList );
				}
				map.put("searchCondition", searchCondition+" ");
				String defaultWhereSql = criteriaList.get(0).getDefaultWhereSql();
				if(StringUtils.isNoneBlank(defaultWhereSql)){
					map.put("defaultWhereSql", defaultWhereSql);
				}
				
				List<Map<String, Object>> dataList = comnTemplateDetailMapper.saveExportExcelByIdList(map);
				// 获取缓存字典集合
		    	
		    	Object[][] data = dictCacheComponent.getDataArray(dataList, propertyColDbNames);
				boolean flag = ExcelUtils.writeFile(fileFullPathName,colFileNames, data); // official
				if(flag){
					String dataTotalNumber = String.valueOf(data.length);
			        String[] strArray = {dataTotalNumber,fileName};
			        return strArray;
				}
			}
		}
		return new String[]{"null"};
	}

	/**
	 * <p>导出dbf</p>
	 * @param idList 用于组装sql语句中from之后，where之前的语句。
	 * @param templateId 模板id
	 * @param module 预留字段
	 * @param pageSupport （criteriaList 为空时，搜索条件在sql语句中就会使用）
	 * @return 返回 {"导出数据条数","文件名称)"};导出文件异常时，返回”null"
	 */
	@Override
	public String[] saveExportDBFByIdList(String[] idList,
			Integer templateId, String module, PageSupport pageSupport,
			String fileCoding) {
		List<ComnTemplateDetail> templateDetailList = comnTemplateDetailMapper.getComnTemplateDetailListByTemplateId(templateId);
		ComnTemplate template = comnTemplateMapper.selectByPrimaryKey(templateId);
		
		Gson gson = new Gson();
		if(null != template && StringUtils.isNotEmpty(template.getCriteria())){
				// json转为带泛型的list 
				List<CriteriaParamVO> criteriaList = gson.fromJson(template.getCriteria(),
						new TypeToken<List<CriteriaParamVO>>() { 
				}.getType()); 
			int colsSize = templateDetailList.size();
			String filePath = DBFUtil.getDBFPath();
			String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String fileFullPathName = filePath + fileName;
			String[] colFileNames = new String[colsSize]; //  文件表头字段数组
			List<String> propertyColDbNames = new ArrayList<String>();
			int[] fieldLengthArray = new int[colsSize]; // dbf 文件表头字段的长度

			// 根据不同的模板详情取得SQL语句的结果字段部分
			String columnSql = "";
			for (int i = 0; i < colsSize; i++) {
				ComnTemplateDetail detail = templateDetailList.get(i);
				String propertyColDbName =  detail.getTableDbAlias() + "-" + changeFirstLetter(detail.getColDbName());
				columnSql += detail.getTableDbAlias() + "." + detail.getColDbName() + " AS " + "`" + propertyColDbName + "`" + ",";
				String colFileName = detail.getColFileName();
				String colFileName2 = colFileName.substring(colFileName.indexOf(")")+1);
				colFileNames[i] = colFileName2;
				propertyColDbNames.add(propertyColDbName);
				fieldLengthArray[i] = Constants.DEFAULT_FILED_LENGTH ;	// 字段默认长度

			}
			columnSql = addDefaultCol(columnSql);
			columnSql = columnSql.substring(0, columnSql.length() - 1);
	
			Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport);
			if (StringUtils.isNotBlank(columnSql)) {
				map.put("columnSql", columnSql);
				// 根据不同的模板详情取得SQL语句的from部分
				StringBuffer fromSql = new StringBuffer();
				for(int index=0;index< criteriaList.size();index++){
					CriteriaParamVO vo = criteriaList.get(index);
					if(index==0){
						fromSql.append(vo.getTableName()).append(" AS ").append(vo.getTableAlias());
					}else{
						fromSql.append(" ").append(vo.getRelationMethod()).append(" ").append(vo.getTableName()).append(" AS ").append(vo.getTableAlias()).append(" ON ").append(vo.getRelation());
					}
				}
				map.put("fromSql", fromSql);
				
				String[] relationPrimaryKey = criteriaList.get(0).getRelationPrimaryKey();
				map.put("whereSql",relationPrimaryKey);
				String searchCondition = (String) map.get("searchCondition");
				if(relationPrimaryKey.length==1 && null != idList && idList.length >0){
					if(null != searchCondition && StringUtils.isNotBlank(searchCondition)){
						if(searchCondition.indexOf("order by") != -1)
							searchCondition = searchCondition.substring(0, searchCondition.indexOf("order by"));
					}
					map.put("idList",idList );
				}
				map.put("searchCondition", searchCondition+" ");
				
				String defaultWhereSql = criteriaList.get(0).getDefaultWhereSql();
				if(StringUtils.isNoneBlank(defaultWhereSql)){
					map.put("defaultWhereSql", defaultWhereSql);
				}
				List<Map<String, Object>> dataList = comnTemplateDetailMapper.saveExportExcelByIdList(map);
				// 获取缓存字典集合
		    	//一些需要特殊处理的 如时间格式，专业方向全称
		    	List<String> dateKeyList = new ArrayList<String>();
		    	List<String> majorKeyList = new ArrayList<String>();
		    	String majorFullNameKey = null;
		    	
		    	String majorFullCol = Constants.getConfigValue(Constants.DBF_JIAOWEI_MAJORFULLNAME, "ZYMC");
		    	List<String> dateColList = new ArrayList<String>();
		    	dateColList.addAll(Arrays.asList(Constants.getConfigValue(Constants.DBF_JIAOWEI_DATE, "CSRQ,BYRQ,YJBYRQ,RXRQ").split(Constants.ID_SEPARATOR))) ;
		    	String dateFormat = Constants.getConfigValue(Constants.DBF_JIAOWEI_DATE_FORMAT, "yyyyMMdd");
		    	
		    	for (int i=0 ; i < colFileNames.length; i++){
		    		String exportTitle = colFileNames[i];
		    		if (majorFullCol.equals(exportTitle)){//专业名称，要求是专业 （专业方向）
		    			majorFullNameKey = propertyColDbNames.get(i);
		    			propertyColDbNames.remove(i);
	    				propertyColDbNames.add(i, majorFullNameKey + "Fix");
		    		} else if (dateColList.contains(exportTitle)){
		    			dateKeyList.add( propertyColDbNames.get(i));
		    		} else {
		    			String key = propertyColDbNames.get(i);
		    			if ("_decm-code".equals(key) || "_decm-title".equals(key)|| "_decmf-code".equals(key)|| "_decmf-title".equals(key)){
		    				majorKeyList.add(key);
		    			} else if ("_decmf-majorId".equals(key)){
		    				propertyColDbNames.remove(i);
		    				propertyColDbNames.add(i, "_decm-title");
		    				majorKeyList.add("_decm-title");
		    			}
		    		}
		    	}//for colfileNames
		    	
		    	for (Map<String, Object> dataMap : dataList){
		    		if (StringUtils.isNotBlank(majorFullNameKey)){
			    		Integer majorFieldId = dataMap.get(majorFullNameKey)!=null ? (Integer)dataMap.get(majorFullNameKey) : 0; 
			    		if (majorFieldId > 0){
			    			dataMap.put(majorFullNameKey + "Fix", dictCacheComponent.getMajorFullName4DBF(majorFieldId));
			    		}
		    		}
		    		for (String dateKey : dateKeyList){
		    			Date date = dataMap.get(dateKey)!=null ? (Date)dataMap.get(dateKey) : null;
		    			if (date!=null)
		    				dataMap.put(dateKey, DateUtils.formatDateToString(date, dateFormat));
		    		}
		    		//特殊处理，已知majorId, 获取实际的[ _decm.code, _decm.title]
		    		Date compareDate =  DateUtils.formatStringToDate("2016-08-01");
		    		for (String majorKey : majorKeyList){
		    			String majorId = dataMap.get("majorId")!= null ?  dataMap.get("majorId").toString() : null;
		    			if (majorId == null){
		    				continue;
		    			}
		    			if ("_decm-title".equals(majorKey)){
		    				dataMap.put(majorKey, dictCacheComponent.getMajorInfo4DBF(majorId, "title"));
		    			}else if ("_decm-code".equals(majorKey)){
		    				Date enrollDate = dataMap.get("enrollDate")!=null ? (Date)dataMap.get("enrollDate") : null;
		    				if (enrollDate == null || DateUtils.compareDate(enrollDate, compareDate) > 0){//20160801后启用新的code
		    					dataMap.put(majorKey, dictCacheComponent.getMajorInfo4DBF(majorId, "code"));
		    				} else {
		    					dataMap.put(majorKey, dictCacheComponent.getMajorInfo4DBF(majorId, "formerCode"));
		    				}
		    			}//if-else
		    		}//for 
		    		
		    	}//for dataList
		    	
		    	Object[][] data = dictCacheComponent.getDataArray(dataList, propertyColDbNames);
				boolean flag = DBFUtil.writeDBF(fileFullPathName, colFileNames, fieldLengthArray, data, fileCoding); // 正式环境使用 UTF-8 编码
				if(flag){
					String dataTotalNumber = String.valueOf(data.length);
			        String[] strArray = {dataTotalNumber,fileName};
			        return strArray;
				}
			}
		}
		return new String[]{"null"};
	}
	
	/**
	 * <p>缴费管理特殊:由于是联合主键格式 "10001_1,10002_2"</p>
	 * <p>根据要导出的id和模板id,导出excel</p>
	 * @param idList 联合主键
	 * @param templateId 模板id
	 * @param module 资源rule,预留字段
	 * @param pageSupport （criteriaList 为空时，搜索条件在sql语句中就会使用）
	 * @return 返回 {"导出数据条数","文件名称)"};导出文件异常时，返回”null"
	 */
	@Override
	@Transactional
	public String[] saveExportExcel(String[] idList, Integer templateId, String module, PageSupport pageSupport) {
		
				List<ComnTemplateDetail> templateDetailList = comnTemplateDetailMapper.getComnTemplateDetailListByTemplateId(templateId);
				ComnTemplate template = comnTemplateMapper.selectByPrimaryKey(templateId);
				
				Gson gson = new Gson();
				if(null != template && StringUtils.isNotEmpty(template.getCriteria())){
					// json转为带泛型的list 
					List<CriteriaParamVO> criteriaList = gson.fromJson(template.getCriteria(),
							new TypeToken<List<CriteriaParamVO>>() { 
					}.getType()); 
					int colsSize = templateDetailList.size();
					String filePath = ExcelUtils.getExcelFileFullPath();
					String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					String fileFullPathName = filePath + fileName;
					String[] colFileNames = new String[colsSize]; //  文件表头字段数组
					List<String> propertyColDbNames = new ArrayList<String>(); 
					// 根据不同的模板详情取得SQL语句的结果字段部分
					String columnSql = "";
					for (int i = 0; i < colsSize; i++) {
						ComnTemplateDetail detail = templateDetailList.get(i);
						String propertyColDbName =  detail.getTableDbAlias() + "-" + changeFirstLetter(detail.getColDbName());
						columnSql += detail.getTableDbAlias() + "." + detail.getColDbName() + " AS " + "`" + propertyColDbName + "`" + ",";
						String colFileName = detail.getColFileName();
						String colFileName2 = colFileName.substring(colFileName.indexOf(")")+1);
						colFileNames[i] = colFileName2;
						propertyColDbNames.add(propertyColDbName);
					}
					columnSql = columnSql.substring(0, columnSql.length() - 1);

					Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport);
					if (StringUtils.isNotBlank(columnSql)) {
						map.put("columnSql", columnSql);
						// 根据不同的模板详情取得SQL语句的from部分
						StringBuffer fromSql = new StringBuffer();
						for(int index=0;index< criteriaList.size();index++){
							CriteriaParamVO vo = criteriaList.get(index);
							if(index==0){
								fromSql.append(vo.getTableName()).append(" AS ").append(vo.getTableAlias());
							}else{
								fromSql.append(" ").append(vo.getRelationMethod()).append(" ").append(vo.getTableName()).append(" AS ").append(vo.getTableAlias()).append(" ON ").append(vo.getRelation());
							}
						}
						map.put("fromSql", fromSql);
						
						String[] relationPrimaryKey = criteriaList.get(0).getRelationPrimaryKey();
						/*
						  _erph.student_id = #{studentId,jdbcType=INTEGER}
				  			and _erph.academic_year_id = #{academicYearId,jdbcType=INTEGER}
						 */
//						map.put("whereSql",relationPrimaryKey);
						if(relationPrimaryKey.length==2){
							int itemLength = idList.length;
						    List<LinkedHashMap<String,String>> idListReal = new ArrayList<>();
						    
							if(null != idList && itemLength >0){
							  for (int i = 0; i < itemLength; i++) {
							      //联合主键：格式 "10001_1,10002_2"
								  String[] matchingItemIds = idList[i].split("_");
								  int index =matchingItemIds.length;
						  		  for(int j= 0; j<index; j++){
				            		LinkedHashMap<String,String> idHashMap = new LinkedHashMap<>();
				            		idHashMap.put("studentId", matchingItemIds[0]);
				            		idHashMap.put("academicYearId", matchingItemIds[1]);
				            		idListReal.add(idHashMap);
				            	  }  
							  }
							  map.put("idListReal", idListReal);
							}
						}
						String defaultWhereSql = criteriaList.get(0).getDefaultWhereSql();
						if(StringUtils.isNoneBlank(defaultWhereSql)){
							map.put("defaultWhereSql", defaultWhereSql);
						}
						List<ConcurrentHashMap<String, Object>> dataList = comnTemplateDetailMapper.exportExcelPaymentData(map);
						// 获取缓存字典集合
				    	for(ConcurrentHashMap<String,Object> data : dataList){
				    		if(data.containsKey("_erph-fee")){
				    			double fee = Double.parseDouble(String.valueOf(data.get("_erph-fee"))) / 100;
				    			data.put("_erph-fee", fee);
				    		}
				    		if(data.containsKey("_erph-arrearage")){
				    			double arrearage = Double.parseDouble(String.valueOf(data.get("_erph-arrearage"))) / 100;
				    			data.put("_erph-arrearage", arrearage);
				    		}
				    	}
				    	Object[][] data = dictCacheComponent.getDataArray(dataList, propertyColDbNames);
						boolean flag = ExcelUtils.writeFile(fileFullPathName,colFileNames, data); // official

						if(flag){
							String dataTotalNumber = String.valueOf(data.length);
					        String[] strArray = {dataTotalNumber,fileName};
					        return strArray;
						}
					}
				}
			return new String[]{"null"};
	}


	@Override
	public String[] saveExportDBF(String[] idList, Integer templateId,
			String module, PageSupport pageSupport, String fileCoding) {
		List<ComnTemplateDetail> templateDetailList = comnTemplateDetailMapper.getComnTemplateDetailListByTemplateId(templateId);
		ComnTemplate template = comnTemplateMapper.selectByPrimaryKey(templateId);
		
		Gson gson = new Gson();
		if(null != template && StringUtils.isNotEmpty(template.getCriteria())){
				// json转为带泛型的list 
				List<CriteriaParamVO> criteriaList = gson.fromJson(template.getCriteria(),
						new TypeToken<List<CriteriaParamVO>>() { 
				}.getType()); 
			int colsSize = templateDetailList.size();
			String filePath = DBFUtil.getDBFPath();
			String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String fileFullPathName = filePath + fileName;
			String[] colFileNames = new String[colsSize]; //  文件表头字段数组
			List<String> propertyColDbNames = new ArrayList<String>();
			int[] fieldLengthArray = new int[colsSize]; // dbf 文件表头字段的长度

			// 根据不同的模板详情取得SQL语句的结果字段部分
			String columnSql = "";
			for (int i = 0; i < colsSize; i++) {
				ComnTemplateDetail detail = templateDetailList.get(i);
				String propertyColDbName =  detail.getTableDbAlias() + "-" + changeFirstLetter(detail.getColDbName());
				columnSql += detail.getTableDbAlias() + "." + detail.getColDbName() + " AS " + "`" + propertyColDbName + "`" + ",";
				String colFileName = detail.getColFileName();
				String colFileName2 = colFileName.substring(colFileName.indexOf(")")+1);
				colFileNames[i] = colFileName2;
				propertyColDbNames.add(propertyColDbName);
				fieldLengthArray[i] = Constants.DEFAULT_FILED_LENGTH ;	// 字段默认长度

			}
			columnSql = columnSql.substring(0, columnSql.length() - 1);
	
			Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport);
			if (StringUtils.isNotBlank(columnSql)) {
				map.put("columnSql", columnSql);
				// 根据不同的模板详情取得SQL语句的from部分
				StringBuffer fromSql = new StringBuffer();
				for(int index=0;index< criteriaList.size();index++){
					CriteriaParamVO vo = criteriaList.get(index);
					if(index==0){
						fromSql.append(vo.getTableName()).append(" AS ").append(vo.getTableAlias());
					}else{
						fromSql.append(" ").append(vo.getRelationMethod()).append(" ").append(vo.getTableName()).append(" AS ").append(vo.getTableAlias()).append(" ON ").append(vo.getRelation());
					}
				}
				map.put("fromSql", fromSql);
				
				String[] relationPrimaryKey = criteriaList.get(0).getRelationPrimaryKey();
				/*
				  _erph.student_id = #{studentId,jdbcType=INTEGER}
		  			and _erph.academic_year_id = #{academicYearId,jdbcType=INTEGER}
				 */
//				map.put("whereSql",relationPrimaryKey);
				if(relationPrimaryKey.length==2){
					int itemLength = idList.length;
				    List<LinkedHashMap<String,String>> idListReal = new ArrayList<>();
				    
					if(null != idList && itemLength >0){
					  for (int i = 0; i < itemLength; i++) {
					      //联合主键：格式 "10001_1,10002_2"
						  String[] matchingItemIds = idList[i].split("_");
						  int index =matchingItemIds.length;
				  		  for(int j= 0; j<index; j++){
		            		LinkedHashMap<String,String> idHashMap = new LinkedHashMap<>();
		            		idHashMap.put("studentId", matchingItemIds[0]);
		            		idHashMap.put("academicYearId", matchingItemIds[1]);
		            		idListReal.add(idHashMap);
		            	  }  
					  }
					  map.put("idListReal", idListReal);
					}
				}
				String defaultWhereSql = criteriaList.get(0).getDefaultWhereSql();
				if(StringUtils.isNoneBlank(defaultWhereSql)){
					map.put("defaultWhereSql", defaultWhereSql);
				}
				List<ConcurrentHashMap<String, Object>> dataList = comnTemplateDetailMapper.exportExcelPaymentData(map);
		    	for(ConcurrentHashMap<String,Object> data : dataList){
		    		if(data.containsKey("_erph-fee")){
		    			double fee = Double.parseDouble(String.valueOf(data.get("_erph-fee"))) / 100;
		    			data.put("_erph-fee", fee);
		    		}
		    		if(data.containsKey("_erph-arrearage")){
		    			double arrearage = Double.parseDouble(String.valueOf(data.get("_erph-arrearage"))) / 100;
		    			data.put("_erph-arrearage", arrearage);
		    		}
		    	}
				// 获取缓存字典集合
		    	Object[][] data = dictCacheComponent.getDataArray(dataList, propertyColDbNames);
				boolean flag = DBFUtil.writeDBF(fileFullPathName, colFileNames, fieldLengthArray, data, fileCoding); // test
				if(flag){
					String dataTotalNumber = String.valueOf(data.length);
			        String[] strArray = {dataTotalNumber,fileName};
			        return strArray;
				}
			}
		}
		return new String[]{"null"};
	}
	
	/**
	 * 向dbf导出sql中增加默认的列,期中涉及的表默认模板中存在
	 * @param columnSql
	 * @author RanWeizheng
	 * @date 2016年7月1日 下午6:30:01
	 */
	private String addDefaultCol(String columnSql){
		columnSql += " _ersr.enroll_date as enrollDate,";
		columnSql += " _decmf.major_id as majorId,";
		return columnSql;
	}
	
}
