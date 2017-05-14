package com.shframework.modules.comn.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.sys.entity.ColumnComment;
import com.shframework.modules.sys.entity.ComnTemplate;
import com.shframework.modules.sys.entity.ComnTemplateDetail;
import com.shframework.modules.sys.entity.User;

/**
 * <p>模板设置：各业务通用。</p>
 * <p>主要方法：1、新增Excel格式导出模板 2、新增DBF格式导出模板 3、新增Excel格式导入模板</p>
 * <p>重要方法：1、save* 2、update* </p>
 * @author zhangjk
 */
public interface TemplateService {

	public PageTerminal<ComnTemplate> queryTemplate(PageSupport pageSupport,String criteria);
	

	public List<ComnTemplateDetail> queryTemplateDetails(Integer templateId);

	/**
	 * 保存导入、导出模板，各模块通用
	 * @param template 模板主表
	 * @param resourceId	  资源标识	
	 * @param my_multiple_select 模板详情表需要字段，以数组封装
	 * @param user 当前登录用户
	 * @author zhangjk
	 */
	public void saveTemplate(ComnTemplate template, String rule,String[] my_multiple_select,User user);
	
	public void deleteTemplate(Integer templateId);

	public ComnTemplate queryTemplate(Integer templateId);

	public void updateExportTemplate(ComnTemplate template,
			String[] my_multiple_select, User sessionUser);

	public int deleteByTemplateId(Integer templateId);

	/**
     * <p>模板列表 file_format 0:excel， 1:dbf ; 
     * type 0:导入 ，1：导出；按类型精确查询。 resource_id： 资源模块</p>
     * @param map
     * @return
     */
	public List<ComnTemplate> queryTemplateListByMap(Map<String, Object> map);
	
	/**
	 * 根据模板id取得模板详情列表集合
	 * @param templateId
	 * @return
	 */
	public List<ComnTemplateDetail> getComnTemplateDetailListByTemplateId(Integer templateId);


	public byte[] downloadImportExcelFile(Integer templateId) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	public  Map<String,String> getMatchingResultByTemplateId(Integer templateId);
	
	public  Map<String,String> getColumnHeaderMapByTemplateId(Integer templateId);
	
	String fileUpload(String parent, MultipartFile file) throws IOException;


	List<ColumnComment> getComment(
			List<LinkedHashMap<String, String>> moduleList);

	public Integer selectResourceIdByRule(String module);

	/**
	 * <p>导出excel</p>
	 * @param cbkfieldList 用于组装sql语句中from之后，where之前的语句。
	 * @param templateId 模板id
	 * @param module 预留字段
	 * @param pageSupport （criteriaList 为空时，搜索条件在sql语句中就会使用）
	 * @return 返回 {"导出数据条数","文件名称)"};导出文件异常时，返回”null"
	 * modify by zhangjk,2016.03.11
	 */
	public String[] saveExportExcelByIdList(String[] cbkfieldList,
			Integer excelTemplateId, String module, PageSupport pageSupport);

	/**
	 * <p>导出dbf</p>
	 * @param idList 用于组装sql语句中from之后，where之前的语句。
	 * @param templateId 模板id
	 * @param module 预留字段
	 * @param pageSupport （criteriaList 为空时，搜索条件在sql语句中就会使用）
	 * @return 返回 {"导出数据条数","文件名称)"};导出文件异常时，返回”null"
	 * modify by zhangjk,2016.03.11
	 */
	public String[] saveExportDBFByIdList(String[] cbkfieldList,
			Integer dbfTemplateId, String module, PageSupport pageSupport,
			String property);


	/**
	 * <p>缴费管理特殊:由于是联合主键格式 "10001_1,10002_2"</p>
	 * <p>根据要导出的id和模板id,导出excel</p>
	 * @param idList 联合主键
	 * @param templateId 模板id
	 * @param module 资源rule,预留字段
	 * @param pageSupport （criteriaList 为空时，搜索条件在sql语句中就会使用）
	 * @return 返回 {"导出数据条数","文件名称)"};导出文件异常时，返回”null"
	 */
	String[] saveExportExcel(String[] idList, Integer templateId,
			String module, PageSupport pageSupport);


	public String[] saveExportDBF(String[] cbkfieldList, Integer dbfTemplateId,
			String module, PageSupport pageSupport, String property);
}
