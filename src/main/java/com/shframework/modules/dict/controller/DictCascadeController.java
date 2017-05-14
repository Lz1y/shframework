package com.shframework.modules.dict.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.util.DictCascadeVo;
import com.shframework.modules.dict.service.DictCascadeService;

@Controller
@RequestMapping("dict")
public class DictCascadeController extends BaseComponent{
	
	@Autowired
	DictCascadeService dictCascadeService;
	
	private static final Logger logger = LoggerFactory.getLogger(DictCascadeController.class);
	
	/**
	 * 根据父节点ID获得子节点JSON数据
	 * @param parentId
	 */
	@RequestMapping(value="cascade/directaccess")
	public @ResponseBody Map<String, List<DictCascadeVo>> getChildrenJson(@RequestParam(value = "type", required = false) String type, 
																													@RequestParam(value = "parentId", required = false) Integer parentId,
																													@RequestParam(value = "attr", required = false) String attr) {
		Map<String, List<DictCascadeVo>> retMap = new HashMap<String, List<DictCascadeVo>>();
		List<DictCascadeVo> dataList = null;
		if(parentId != null && parentId > 0) {
			dataList = dictCascadeService.getCascadeInfo(type, parentId, attr);
		} 
		if (dataList == null ){
			dataList = new LinkedList<DictCascadeVo>();
		}
		retMap.put("data",  dataList);
		return retMap;
	}
}