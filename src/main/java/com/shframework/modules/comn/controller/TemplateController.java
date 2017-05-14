package com.shframework.modules.comn.controller;

import static com.shframework.common.util.Constants.URL_SEP;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.support.PageParam;
import com.shframework.common.util.Constants;
import com.shframework.common.util.DateUtils;
import com.shframework.common.util.ExcelUtils;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.ResourceTemplateUtils;
import com.shframework.common.util.ServletUtils;
import com.shframework.modules.comn.entity.vo.CriteriaParamVO;
import com.shframework.modules.comn.service.TemplateService;
import com.shframework.modules.sys.entity.ColumnComment;
import com.shframework.modules.sys.entity.ComnTemplate;
import com.shframework.modules.sys.entity.ComnTemplateDetail;
import com.shframework.modules.sys.service.PermissionService;

/**
 * <p>模板设置：各业务通用，只需配置（ResourceTemplateUtils类）即可实现各个业务的模板设置功能。</p>
 * <p>主要方法：1、新增Excel格式导出模板 2、新增DBF格式导出模板 3、新增Excel格式导入模板</p>
 * <p>重要方法：1、save* 2、update* </p>
 * @author zhangjk
 */
@Controller
@RequestMapping("")
public class TemplateController extends BaseComponent {

	@Autowired
	private TemplateService templateService;
	@Autowired
	private PermissionService permissionService;

	@RequestMapping(value = "/**/all/tpllist")
	public ModelAndView queryTemplateInfo(Model model,
			@RequestParam(value = "module", required = false) String module,
			@RequestParam(value = "pUrl", required = false) String pUrl,
			@PageParam PageSupport pageSupport) {
			return new ModelAndView("/comn/list", "page",templateService.queryTemplate(pageSupport, module)).addObject("purviewMap", permissionService.getTemplatePurview());
	}
	
	@RequestMapping(value = "/**/{templateId}/tpledit")
	public String editTemplate(@PathVariable("templateId") Integer templateId,
			@RequestParam(value = "module", required = false) String module,
			@PageParam PageSupport pageSupport, Model model) {
		ComnTemplate template = templateService.queryTemplate(templateId);
		model.addAttribute("template",template );
		model.addAttribute("pageSupport", pageSupport);
        
        //更新模板的方法使用AJAX方法提交，此处给必要的隐藏域赋值
        model.addAttribute("fileFormat", template.getFileFormat());
        model.addAttribute("type", template.getType());
        
		if(template.getFileFormat()==0){
			List<ComnTemplateDetail> templateDetailList = templateService.queryTemplateDetails(templateId);
			LinkedHashMap<String, String> multipleSelectMap = new LinkedHashMap<String, String>();
			for(ComnTemplateDetail templateDetail :templateDetailList){
				//key 格式：_erph,academic_year_id,收费区间
				String multipleSelectMapKey=templateDetail.getTableDbAlias()+","+templateDetail.getColDbName()+","+templateDetail.getColFileName();
				multipleSelectMap.put(multipleSelectMapKey, templateDetail.getColFileName());		
			}
			model.addAttribute("multipleSelectMap", multipleSelectMap);
			
			 LinkedHashMap<String, String> columnComment = getColumnCommentByModule(module);
			 List<LinkedHashMap<String, String>> columnCommentTarget = new ArrayList<LinkedHashMap<String, String>>();
	        //针对columnComment排除 在multipleSelectMap中存在的key
	        for(Entry<String, String> entry : multipleSelectMap.entrySet()) { 
	        	columnComment.containsKey(entry.getKey());
	        	columnComment.remove(entry.getKey());
	        } 
	        columnCommentTarget.add(columnComment);
			model.addAttribute("columnCommentTarget", columnCommentTarget);
			return "comn/multi";
		}else if(template.getFileFormat()==1){
			List<ComnTemplateDetail> templateDetailList = templateService.queryTemplateDetails(templateId);
			
		      List<ConcurrentHashMap<Object, Object>> linkedMapList = new ArrayList<ConcurrentHashMap<Object, Object>>();
		      
		        for(ComnTemplateDetail templateDetail :templateDetailList){
		        	ConcurrentHashMap<Object, Object> linkedMap = new ConcurrentHashMap<Object, Object>();
		              String multipleSelectMapKey1=templateDetail.getTableDbAlias()+","+templateDetail.getColDbName();
		              linkedMap.put(multipleSelectMapKey1, templateDetail.getColFileName());	
		              
					String multipleSelectMapKey2=templateDetail.getTableDbAlias()+","+templateDetail.getColDbName();
					linkedMap.put("detail", multipleSelectMapKey2);
					linkedMap.put("colDbName", templateDetail.getColDbName());
					linkedMap.put("colFileName", templateDetail.getColFileName());		
					
					linkedMapList.add(linkedMap);
				}
			
		        LinkedHashMap<String, String> columnComment = getCommentByModule(module);
				List<LinkedHashMap<String, String>> columnCommentTarget = new ArrayList<LinkedHashMap<String, String>>();
		        
		        for(ConcurrentHashMap<Object, Object> multipleSelectMap : linkedMapList){
		        	 //针对columnComment排除 在multipleSelectMap中存在的key (multipleSelectMapKey1)
			        for(Entry<Object, Object> entry : multipleSelectMap.entrySet()) { 
			        	if(columnComment.containsKey(entry.getKey())){
			        		multipleSelectMap.put("showName", columnComment.get(entry.getKey()));
			        	}
			        	columnComment.remove(entry.getKey());
			        }
		        }
			    columnCommentTarget.add(columnComment);
			    model.addAttribute("linkedMapList", linkedMapList);
				model.addAttribute("columnCommentTarget", columnCommentTarget);
				return "comn/dbf_modify";
		}
		return "comn/multi";
	}
	
	/**
	 * <p>新增模板页面</p>
	 * @param fileFormat	模板格式：0:excel; 1:dbf
	 * @param module	资源id
	 * @param moduleName	资源名称
	 * @param type	模板类型：0:导入;	2:导出
	 * @param pageSupport
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/**/0/tpladd")
	public ModelAndView addTemplate(@RequestParam(value = "file_format", required = true) Integer fileFormat,
			@RequestParam(value = "module", required = true) String module,
			@RequestParam(value = "pUrl", required = false) String pUrl,
			@RequestParam(value = "type", required = true) Integer type,
			@PageParam PageSupport pageSupport, Model model) {
		if(fileFormat==0){
			 LinkedHashMap<String, String> columnComment = getColumnCommentByModule(module);
			 List<LinkedHashMap<String, String>> columnCommentTarget = new ArrayList<LinkedHashMap<String, String>>();
			 columnCommentTarget.add(columnComment);
	        model.addAttribute("fileFormat", fileFormat);
	        model.addAttribute("type", type);
			return new ModelAndView("comn/multi").addObject("columnCommentTarget", columnCommentTarget);
		}else if (fileFormat==1){
	        LinkedHashMap<String, String> columnComment = getCommentByModule(module);
			List<LinkedHashMap<String, String>> columnCommentTarget = new ArrayList<LinkedHashMap<String, String>>();
			
	        columnCommentTarget.add(columnComment);
	        model.addAttribute("fileFormat", fileFormat);
	        model.addAttribute("type", type);
			return new ModelAndView("comn/dbf_multi").addObject("columnCommentTarget", columnCommentTarget);
		}
		return new ModelAndView("/"+pUrl+"/all/tpllist?module="+module);
	}
	
	@RequestMapping(value = "/**/0/tplimport")
	public ModelAndView importTemplate (@PageParam PageSupport pageSupport,
			Model model,
			@RequestParam(value = "module", required = false) String module,
			@RequestParam(value = "type", required = true) Integer type,
			@RequestParam(value = "file_format", required = false) String fileFormat,
			@RequestParam(value = "rootfield_option",required = false) Integer rootfieldOption
			) throws FileNotFoundException {
		
		String excelRealPath = (String)session.getAttribute("excelPath");
		if(rootfieldOption !=-1){
			if(StringUtils.isNotEmpty(excelRealPath)){
				Map<String, String> headerMap = new HashMap<String, String>();
				headerMap = ExcelUtils.getExcelHeaderString(excelRealPath);
				model.addAttribute("headerMap", headerMap);
			}
		}
		
		LinkedHashMap<String, String> columnComment = getCommentByModule(module);
		List<LinkedHashMap<String, String>> columnCommentTarget = new ArrayList<LinkedHashMap<String,String>>();
        model.addAttribute("fileFormat", fileFormat);
        model.addAttribute("type", type);
        columnCommentTarget.add(columnComment);
        model.addAttribute("columnSize", columnComment.size());
		return new ModelAndView("comn/excel_import_template").addObject("columnCommentTarget", columnCommentTarget);
	}
	
	@RequestMapping(value="/**/list/tplupload")
	@ResponseBody
	public Map<String,String> fileUpload(@RequestParam(value = "file")MultipartFile file, @RequestParam(value = "importGroupToken")String importGroupToken,  HttpSession session
			) {
		Map<String, String> headerMap = new HashMap<String, String>();
		if (!file.isEmpty()) {
			String parent = Constants.getProperty("file.upload.path.excel") + DateUtils.formatDateToString(new Date(), DateUtils.patternyyyyMMdd) + URL_SEP;
			try {
				session.setAttribute("excelPath", templateService.fileUpload(parent, file));
			} catch (IOException e) {
				e.printStackTrace();
			}
			headerMap = ExcelUtils.getExcelHeaderString(file);
		} 
		return headerMap;
	}
	
	@RequestMapping(value="/**/-1/tplimport")
	@ResponseBody
	public String importSave(@RequestParam(value = "file_format", required = true) Integer fileFormat,
			@RequestParam(value = "module", required = false) String module,
			@RequestParam(value = "matchingResult", required = true) String matchingResult,Integer type,String title,String description,
			ComnTemplate template
			){
		try{
			String returnResult ="true";
			if(StringUtils.isNotBlank(matchingResult)){
				//保存导入excel模板时，matchingResult如果有重复匹配，需要给出提示。
				String[] my_multiple_select = matchingResult.split(";");
        		Map<String, String> conflictMap = new HashMap<String, String>();
        		List<String> conflictList = new ArrayList<String>(); 
				if(null !=my_multiple_select && my_multiple_select.length>0){
					for(int i=0;i<my_multiple_select.length;i++){
						String [] arrayItem = my_multiple_select[i].split(",");
						if(arrayItem.length==3){
							String aliasName = arrayItem[0] + "_" + arrayItem[1];
							conflictMap.put(aliasName, aliasName);
							conflictList.add(aliasName);
						}
					}
				}
				
        		if(conflictMap.size() != conflictList.size()){
					return "conflict";
        		}
				
				templateService.saveTemplate(template, module, my_multiple_select, getSessionUser());
		        return returnResult;
			}else{
				return "false";
			}
		}catch(Exception e){
			return "false";
		}
			
	}

	/**
	 * <p>保存模板：Excel、DBF格式导出模板</p>
	 * @param fileFormat	模板格式：0:excel; 1:dbf
	 * @param type			模板类型：0:导入;	1:导出
	 * @param module		资源id
	 * @param my_multiple_select	保存excel时，前台传过来的（模板详情表需要字段，以数组封装）
	 * @param dbfFileName	保存dbf时，前台传过来的（dbf字段名，以数组封装）
	 * @param template		模板主表信息（使用ComnTemplate 类型接收）
	 * @param info	保存dbf时，前台封装好的数据格式：例如："_ernc,campus_id;_ersr,department_id ..."
	 * @param result
	 * @param redirectAttributes
	 * @return
	 * @author zhangjk
	 */
	@RequestMapping(value = "/**/0/tplsave", method = RequestMethod.POST)
	public String saveTemplate(@RequestParam(value = "file_format", required = true) Integer fileFormat,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "module", required = false) String module,
			@RequestParam(value = "pUrl", required = false) String pUrl,
			@RequestParam(value = "my_multiple_select[]", required = false)String[] my_multiple_select,
			@RequestParam(value = "dbfFileName", required = false)String[] dbfFileName,ComnTemplate template,String info,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			try {
				if(fileFormat==0){
					String[] multiple_select = new String[1];
					if(null != my_multiple_select && !my_multiple_select[0].contains(",")){
						multiple_select [0] = my_multiple_select[0] + "," + my_multiple_select[1] + "," + my_multiple_select[2];
						templateService.saveTemplate(template,module,multiple_select,getSessionUser());
					}else{
						templateService.saveTemplate(template,module,my_multiple_select,getSessionUser());
					}
				}else if (fileFormat==1){
					String[] infoArr = info.split(";");
					int infoArrSize = infoArr.length;
					String[] my_multiple_select2 = new String[infoArrSize];
					for(int i=0; i<infoArrSize; i++){
						String dbfName = dbfFileName[i];
						String[] templateInfo = infoArr[i].split(",");
						my_multiple_select2[i]=templateInfo[0] +","+templateInfo[1] +","+dbfName;
					}
					templateService.saveTemplate(template,module,my_multiple_select2,getSessionUser());
				}
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("template", template);
				redirectAttributes.addAttribute("type", type);
				redirectAttributes.addAttribute("file_format", fileFormat);
				redirectAttributes.addAttribute("module", module);
				return "redirect:/"+pUrl+"/0/tpladd";
			}
		}
		redirectAttributes.addAttribute("module", module);
		return "redirect:/"+pUrl+"/all/tpllist";
	}
	
	/**
	 * 
	 * @param templateId	模板主表主键
	 * @param template	模板主表信息（使用ComnTemplate 类型接收）
	 * @param fileFormat	模板格式：0:excel; 1:dbf
	 * @param module	资源id
	 * @param my_multiple_select	保存excel时，前台传过来的（模板详情表需要字段，以数组封装）
	 * @param dbfFileName	保存dbf时，前台传过来的（dbf字段名，以数组封装）
	 * @param info	保存dbf时，前台封装好的数据格式：例如："_ernc,campus_id;_ersr,department_id ..."
	 * @param result
	 * @param redirectAttributes
	 * @param pageSupport
	 * @return
	 */
	@RequestMapping(value = "/**/{templateId}/tplsave", method = RequestMethod.POST)
	public String editTemplate(@PathVariable("templateId") Integer templateId, ComnTemplate template,
			@RequestParam(value = "file_format", required = true) Integer fileFormat,
			@RequestParam(value = "module", required = false) String module,
			@RequestParam(value = "pUrl", required = false) String pUrl,
			@RequestParam(value = "my_multiple_select[]",required = false)String[] my_multiple_select,
			@RequestParam(value = "dbfFileName", required = false)String[] dbfFileName,
			String info,
			BindingResult result, RedirectAttributes redirectAttributes, @PageParam PageSupport pageSupport) {
			if (!result.hasErrors()) {
				try {
					if(fileFormat==0){
						String[] multiple_select = new String[1];
						if(null != my_multiple_select && !my_multiple_select[0].contains(",")){
							multiple_select [0] = my_multiple_select[0] + "," + my_multiple_select[1] + "," + my_multiple_select[2];
							templateService.updateExportTemplate(template,multiple_select,getSessionUser());
						}else{
							templateService.updateExportTemplate(template,my_multiple_select,getSessionUser());
						}
					}else if (fileFormat==1){
						String[] infoArr = info.split(";");
						int infoArrSize = infoArr.length;
						String[] my_multiple_select2 = new String[infoArrSize];
						for(int i=0; i<infoArrSize; i++){
							String dbfName = dbfFileName[i];
							String[] templateInfo = infoArr[i].split(",");
							my_multiple_select2[i]=templateInfo[0] +","+templateInfo[1] +","+dbfName;
						}
						templateService.updateExportTemplate(template,my_multiple_select2,getSessionUser());
					}
				} catch (Exception e) {
					e.printStackTrace();
					redirectAttributes.addFlashAttribute("template", template);
					redirectAttributes.addAttribute("file_format", fileFormat);
					return "redirect:/"+pUrl+"/"+templateId + "/tpledit";
				}
			}
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		redirectAttributes.addAttribute("module",module);
		return "redirect:/"+pUrl+"/all/tpllist";
	}

	@RequestMapping(value = "/**/{templateId}/tpldelete")
	public String deleteTemplate(@PathVariable("templateId") Integer templateId,
			@RequestParam(value = "module", required = false) String module,
			@RequestParam(value = "pUrl", required = false) String pUrl,
			RedirectAttributes redirectAttributes, @PageParam PageSupport pageSupport) {
		templateService.deleteTemplate(templateId);
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		redirectAttributes.addAttribute("module", module);
		return "redirect:/"+pUrl+"/all/tpllist";
	}
	
	@RequestMapping(value = "/**/{templateId}/tplread")
	public String readTemplate(@PathVariable("templateId") Integer templateId,
			@RequestParam(value = "module", required = false) String module,
			RedirectAttributes redirectAttributes, @PageParam PageSupport pageSupport,Model model) {
		ComnTemplate template = templateService.queryTemplate(templateId);
		List<ComnTemplateDetail> templateDetailList =  templateService.queryTemplateDetails(templateId);
		model.addAttribute("template", template);
		model.addAttribute("templateDetailList", templateDetailList);
		//excel、导入
		if(template.getFileFormat()==0 && template.getType()==0){
			return "comn/import_detail";
		}else if(template.getFileFormat()==0 && template.getType()==1){//excel、导出
			return "comn/excel_export_detail";
		}
		//dbf、导出 详情页
		return "comn/dbf_export_detail";
	}
	
	@RequestMapping(value = "/**/{templateId}/tpldownload")
	public  ResponseEntity<byte[]> download(@PathVariable("templateId") Integer templateId,
			@RequestParam(value = "module", required = false) String module,
			@PageParam PageSupport pageSupport)  throws Exception{
        /*
         *  fileFormat	模板格式：0:excel; 1:dbf
         *  type			模板类型：0:导入;	1:导出
         */
        ComnTemplate templateFlag = templateService.queryTemplate(templateId);
        String templateTitle = templateFlag.getTitle();
        Integer fileFormat = templateFlag.getFileFormat();
        Integer type = templateFlag.getType();
        String excelFileName = "";
        if(fileFormat == 0 && type == 0){
        	excelFileName = templateTitle + "导入excel模板";
        }else if(fileFormat == 0 && type == 1){
        	excelFileName = templateTitle + "导出excel模板";
        }else{
        	excelFileName = templateTitle + "导出dbf模板";
        }
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		ServletUtils.setFileDownloadHeader(response,excelFileName);
		byte[] bytes = templateService.downloadImportExcelFile(templateId);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
	
	/**
	 * <p>模板是excel类型时，根据各自业务的模块名，自己判断、并封装查询参数，取得所需数据。</p>
	 * <p>所有配置均在 ResourceTemplateUtils 类中</p>
	 * @param module 根据各自业务的模块名，取得所需数据。
	 * @return
	 */
	public LinkedHashMap<String, String> getColumnCommentByModule (String module){
			List<LinkedHashMap<String,String>> moduleList = new ArrayList<LinkedHashMap<String,String>>();
			List<CriteriaParamVO>  returnListVO  = ResourceTemplateUtils.getCriteriaParamVOByKey(module);
			if(null != returnListVO && returnListVO.size()>0){
				CriteriaParamVO criteriaParamVO = returnListVO.get(0);
				Integer[] multiTable = criteriaParamVO.getMultiTable();
				if(multiTable ==null || multiTable.length==0){
					LinkedHashMap<String,String> linkedMap = new LinkedHashMap<String, String>();
					linkedMap.put("alias",criteriaParamVO.getTableAlias());
					linkedMap.put("tableName",criteriaParamVO.getTableName());
					linkedMap.put("dbName", Constants.getDbName());
					moduleList.add(linkedMap);
				}else{
					for(int i=0; i<multiTable.length; i++){
						CriteriaParamVO criteriaParam = returnListVO.get(multiTable[i]);
						LinkedHashMap<String,String> linkedMap = new LinkedHashMap<String, String>();
						linkedMap.put("alias",criteriaParam.getTableAlias());
						linkedMap.put("tableName",criteriaParam.getTableName());
						linkedMap.put("dbName", Constants.getDbName());
						moduleList.add(linkedMap);
					}
				}
			}
			
			LinkedHashMap<String, String> columnComment = new LinkedHashMap<String, String>();
			List<ColumnComment> columnCommentList = templateService.getComment(moduleList);
		    List<String> commentList = ResourceTemplateUtils.getColumnComment();
			for(ColumnComment comment : columnCommentList){
				if(!commentList.contains(comment.getColumnComment())){
					String tableComment = comment.getTableComment();
					//[系统]用户表; InnoDB free: 4096 kB; (`last_modify_user_id`) REFER `tms/sys_user`(`id`
					//表名，描述元数据描述 见上。截取出符合要求的数据：(用户表)
					String tableComment2 = "";
					if(StringUtils.isNotBlank(tableComment) && (tableComment.indexOf(";")!= -1) && (tableComment.lastIndexOf("]") != -1)){
						tableComment2 = "("+tableComment.substring(tableComment.lastIndexOf("]")+1, tableComment.indexOf(";"))+")"+comment.getColumnComment();
						columnComment.put(comment.getColumnName()+ ","+tableComment2, tableComment2);
					} else if(StringUtils.isNotBlank(tableComment) &&  (tableComment.lastIndexOf("]") != -1)){
						tableComment2 = "("+tableComment.substring(tableComment.lastIndexOf("]")+1)+")"+comment.getColumnComment();
						columnComment.put(comment.getColumnName()+ ","+tableComment2, tableComment2);
					} 
				}
			}
	        return columnComment;
	}
	
	public LinkedHashMap<String,String> getCommentByModule (String module){
		List<LinkedHashMap<String,String>> moduleList = new ArrayList<LinkedHashMap<String,String>>();
		//要取几个表的数据，就new 几个linkedMap对象。
		List<CriteriaParamVO>  returnListVO  = ResourceTemplateUtils.getCriteriaParamVOByKey(module);
		if(null != returnListVO && returnListVO.size()>0){
			CriteriaParamVO criteriaParamVO = returnListVO.get(0);
			Integer[] multiTable = criteriaParamVO.getMultiTable();
			if(multiTable ==null || multiTable.length==0){
				LinkedHashMap<String,String> linkedMap = new LinkedHashMap<String, String>();
				linkedMap.put("alias",criteriaParamVO.getTableAlias());
				linkedMap.put("tableName",criteriaParamVO.getTableName());
				linkedMap.put("dbName", Constants.getDbName());
				moduleList.add(linkedMap);
			}else{
				for(int i=0; i<multiTable.length; i++){
					CriteriaParamVO criteriaParam = returnListVO.get(multiTable[i]);
					LinkedHashMap<String,String> linkedMap = new LinkedHashMap<String, String>();
					linkedMap.put("alias",criteriaParam.getTableAlias());
					linkedMap.put("tableName",criteriaParam.getTableName());
					linkedMap.put("dbName", Constants.getDbName());
					moduleList.add(linkedMap);
				}
			}
		}
		
		LinkedHashMap<String, String> columnComment = new LinkedHashMap<String, String>();
		List<ColumnComment> columnCommentList = templateService.getComment(moduleList);
	    List<String> commentList = ResourceTemplateUtils.getColumnComment();
		for(ColumnComment comment : columnCommentList){
			if(!commentList.contains(comment.getColumnComment())){
				String tableComment = comment.getTableComment();
				String tableComment2 = "";
				if(StringUtils.isNotBlank(tableComment) && (tableComment.indexOf(";")!= -1) && (tableComment.lastIndexOf("]") != -1)){
					tableComment2 = "("+tableComment.substring(tableComment.lastIndexOf("]")+1, tableComment.indexOf(";"))+")"+comment.getColumnComment();
					// 注意：与 getColumnCommentByModule方法中此处代码 是不同的
					columnComment.put(comment.getColumnName(), tableComment2);
				} else if(StringUtils.isNotBlank(tableComment) &&  (tableComment.lastIndexOf("]") != -1)){
					tableComment2 = "("+tableComment.substring(tableComment.lastIndexOf("]")+1)+")"+comment.getColumnComment();
					// 注意：与 getColumnCommentByModule方法中此处代码 是不同的
					columnComment.put(comment.getColumnName(), tableComment2);
				} 
			}
		}
        return columnComment;
	}
	
}
