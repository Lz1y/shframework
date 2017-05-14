package com.shframework.modules.comn.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.support.PageParam;
import com.shframework.common.util.Constants;
import com.shframework.common.util.DBFUtil;
import com.shframework.common.util.ExcelUtils;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PurviewUtil;
import com.shframework.common.util.ServletUtils;
import com.shframework.modules.comn.service.TemplateService;
import com.shframework.modules.sys.entity.ComnTemplate;
import com.shframework.modules.sys.entity.ComnTemplateDetail;
import com.shframework.modules.sys.service.PermissionService;

/**
 * <p>主要方法：1、进入批量导出页面 2、导出数据并保存为 excel文件格式 3、文件下载</p>
 * <p>重要方法：1、export* 2、saveExportExcel* 3、downLoadExport</p>
 * @author zhangjinkui
 */
@Controller
@RequestMapping("")
public class ExportController extends BaseComponent {

	@Autowired
	private TemplateService templateService;
	@Autowired
	private PermissionService permissionService;

	/**
	 * <p>进入批量导出页面</p>
	 * <p>模板列表 file_format 0:excel， 1:dbf ; type 0:导入 ，1：导出；按类型精确查询。 resource_id： 资源模块</p>
	 * @param pageSupport
	 * @param model
	 * @param templateId			模板id
	 * @param cbkfieldList			选中的要导出的id集合 Integer[] 
	 * @param rowSize				导出数据成功条数
	 * @param isDownloadFile	进入页面是否直接直接下载的标志字段
	 * @param fileName 			选择的导出文件的格式
	 * @param fileFormat			选择的导出文件的格式
	 * @param initPageno			初始进入时的页数
	 */
	@RequestMapping(value = {"/**/{templateId}/export"}, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView export(@PageParam PageSupport pageSupport,
			Model model,Integer currPage,
			@PathVariable("templateId") Integer templateId,
			@RequestParam(value = "module", required = false) String module,
			@RequestParam(value = "pUrl", required = false) String pUrl,
			@RequestParam(value = "cbkfield", required = false) String[] cbkfieldList,
			@RequestParam(value = "rowSize", required = false) Integer rowSize,
			@RequestParam(value = "isDownloadFile", required = false) Integer isDownloadFile,
			@RequestParam(value = "fileName", required = false) String fileName,	
			@RequestParam(value = "file_format", required = false) String fileFormat,	
			@RequestParam(value = "init_pageno", required = false) Integer initPageno,
			@RequestParam(value = "dbfTemplateId", required = false) Integer dbfTemplateId,
			@RequestParam(value = "excelTemplateId", required = false) Integer excelTemplateId

	) {
		//以前由resourceId直接关联，改为rule
		Integer resourceId = templateService.selectResourceIdByRule(module);
		model.addAttribute("rowSize", rowSize);
		model.addAttribute("isDownloadFile", isDownloadFile);
		model.addAttribute("fileName", fileName);
		model.addAttribute("file_format", fileFormat);
		model.addAttribute("module", module);
		if(initPageno != null && initPageno == 1){
			model.addAttribute("currPage",1);
		}
		if(null != currPage){
			model.addAttribute("currPage",currPage);
			
		}
		if (null != cbkfieldList && cbkfieldList.length>0) {
			model.addAttribute("cbkfield", StringUtils.join(cbkfieldList, ","));
		}
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("resourceId", resourceId);
		queryMap.put("fileFormat", 0);
		queryMap.put("type", 1);
		List<ComnTemplate> templateList = templateService.queryTemplateListByMap(queryMap);
		model.addAttribute("templateList", templateList);
		if (templateId == null || templateId <= 0) {
			// 默认选择第一个模板
			if (templateList != null && templateList.size() > 0) {
				excelTemplateId = templateList.get(0).getId();
				model.addAttribute("excelTemplateId", excelTemplateId);
				// 模板详情
				List<ComnTemplateDetail> templateDetailList = templateService.getComnTemplateDetailListByTemplateId(excelTemplateId);
				model.addAttribute("templateDetailList", templateDetailList);
			}
		}else{
			model.addAttribute("excelTemplateId", excelTemplateId);
			// 模板详情
			List<ComnTemplateDetail> templateDetailList = templateService.getComnTemplateDetailListByTemplateId(excelTemplateId);
			model.addAttribute("templateDetailList", templateDetailList);
		}
		
		//dbf 导出模板
		queryMap.clear(); 
		queryMap.put("resourceId", resourceId);
		queryMap.put("fileFormat", 1);
		queryMap.put("type", 1);
		List<ComnTemplate> templateListDBF = templateService.queryTemplateListByMap(queryMap);

		model.addAttribute("templateListDBF", templateListDBF);
		if (templateId == null || templateId <= 0) {
			// 默认选择第一个模板
			if (templateListDBF != null && templateListDBF.size() > 0) {
				dbfTemplateId = templateListDBF.get(0).getId();
				// 模板详情
				List<ComnTemplateDetail> templateDetailListDBF = templateService.getComnTemplateDetailListByTemplateId(dbfTemplateId);
				model.addAttribute("templateDetailListDBF", templateDetailListDBF);
				model.addAttribute("dbfTemplateId", dbfTemplateId);
			}
		}else {
			model.addAttribute("templateListDBF", templateListDBF);
			model.addAttribute("dbfTemplateId", dbfTemplateId);
			// 模板详情
			List<ComnTemplateDetail> templateDetailListDBF = templateService.getComnTemplateDetailListByTemplateId(dbfTemplateId);
			model.addAttribute("templateDetailListDBF", templateDetailListDBF);
		}
		return new ModelAndView("/comn/excel/comn_export").addObject("pageSupport", pageSupport).addObject("purviewMap", permissionService.getTemplatePurview());
	}

	/**
	 * 选择不同的模板展示详情
	 */
	@RequestMapping(value = "/**/{templateId}/excel/directaccess")
	public String change(@PageParam PageSupport pageSupport,
			Model model,RedirectAttributes redirectAttributes,
			@RequestParam(value = "curMenu_pUrl", required = false) String curMenu_pUrl,
			@RequestParam(value = "module", required = false) String module,
			@PathVariable("templateId") Integer templateId,
			@RequestParam("cbkfield") String[] cbkfieldList,
			@RequestParam(value = "dbfTemplateId", required = false) Integer dbfTemplateId,
			@RequestParam(value = "excelTemplateId", required = false) Integer excelTemplateId,
			@RequestParam(value = "file_format", required = false) String fileFormat	// 选择的导出文件的格式
		) {
		Integer currPage = 2;
		redirectAttributes.addAttribute("excelTemplateId", templateId);
		redirectAttributes.addAttribute("dbfTemplateId", dbfTemplateId);
		redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
		redirectAttributes.addAttribute("cbkfield",StringUtils.join(cbkfieldList, ","));
		redirectAttributes.addAttribute("file_format",fileFormat);
		redirectAttributes.addAttribute("module",module);
		redirectAttributes.addAttribute("currPage",currPage);

		return "redirect:/"+curMenu_pUrl+"/" + 1 + "/export";
	}
	

	@RequestMapping(value = "/**/excel/export")
	public String saveExportExcel(@PageParam PageSupport pageSupport,
			RedirectAttributes redirectAttributes,
			@RequestParam(value = "curMenu_pUrl", required = false) String curMenu_pUrl,
			@RequestParam(value = "module", required = false) String module,
			@RequestParam("cbkfield") String[] cbkfieldList,
			@RequestParam(value = "dbfTemplateId", required = false) Integer dbfTemplateId,
			@RequestParam(value = "excelTemplateId", required = false) Integer excelTemplateId,
			@RequestParam(value = "file_format", required = false) String fileFormat	
			) throws Exception {
		if("dbf".equals(fileFormat)){
			String[] dbfInfo = new String[2];
			//缴费管理，联合主键 特殊
			if(PurviewUtil.TEMPLATE_PAYMENT.equals(module)){
				dbfInfo = templateService.saveExportDBF(cbkfieldList, dbfTemplateId,module,pageSupport,Constants.getProperty(Constants.DBF_ENCODING_JIAOWEI));
			}else{
				dbfInfo = templateService.saveExportDBFByIdList(cbkfieldList, dbfTemplateId,module,pageSupport,Constants.getProperty(Constants.DBF_ENCODING_JIAOWEI));
			}
			 if("null".equals(dbfInfo[0])){
		        	//此处的业务逻辑校验，放在了前台。
		        }else{
		        	Integer rowSize = Integer.valueOf(dbfInfo[0]);
					String fileName = dbfInfo[1];
					// 进入页面是否直接直接下载的标志字段
					Integer isDownloadFile = 1;		
					
	    			redirectAttributes.addAttribute("rowSize",rowSize);
	    			redirectAttributes.addAttribute("fileName",fileName);
	    			redirectAttributes.addAttribute("isDownloadFile",isDownloadFile);
	    			redirectAttributes.addAttribute("dbfTemplateId",dbfTemplateId);
		        }
		}else{
			String[] excelInfo = new String[2];
			//缴费管理，联合主键 特殊
			if(PurviewUtil.TEMPLATE_PAYMENT.equals(module)){
				excelInfo = templateService.saveExportExcel(cbkfieldList, excelTemplateId, module, pageSupport);
			}else{
				excelInfo = templateService.saveExportExcelByIdList(cbkfieldList, excelTemplateId,module,pageSupport);
			}
	        if("null".equals(excelInfo[0])){
	        	//此处的业务逻辑校验，放在了前台。
	        }else{
	        	Integer rowSize = Integer.valueOf(excelInfo[0]);
	        	String fileName = excelInfo[1];
	    		// 进入页面是否直接直接下载的标志字段
	    		Integer isDownloadFile = 1;		
    			redirectAttributes.addAttribute("rowSize",rowSize);
    			redirectAttributes.addAttribute("fileName",fileName);
    			redirectAttributes.addAttribute("isDownloadFile",isDownloadFile);
    			redirectAttributes.addAttribute("excelTemplateId",excelTemplateId);
	        }
		}
		redirectAttributes.addAttribute("currPage", 3);

		redirectAttributes.addAttribute("file_format", fileFormat);
        redirectAttributes.mergeAttributes(pageSupport.getParamVo().getParmMap());
        redirectAttributes.addAttribute("module",module);
		return "redirect:/"+curMenu_pUrl+"/" + 1 + "/export";
	}
	
	@RequestMapping(value = "/**/excel/download")
	public ResponseEntity<byte[]> downLoadExport(
		@RequestParam(value = "file_format", required = false) String fileFormat,	// 选择的导出文件的格式
		@RequestParam(value = "fileName")String fileName
	) throws Exception {
		String filePath = "";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		if("dbf".equals(fileFormat)){
			filePath = DBFUtil.getDBFPath();
			ServletUtils.setFileDownloadHeaderDBF(response, getResourse().getTitle() + "_" + fileName);
		}else{
			filePath = ExcelUtils.getExcelFileFullPath();
			ServletUtils.setFileDownloadHeader(response, getResourse().getTitle() + "_" + fileName);
		}
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath + fileName)), headers, HttpStatus.OK);
	}
	

}
