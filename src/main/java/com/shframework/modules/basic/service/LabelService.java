package com.shframework.modules.basic.service;

import java.util.Map;

import com.shframework.modules.dict.entity.vo.LabelVo;

public interface LabelService {

	/**
	 * 获取所有的静态标签
	 * @return
	 */
	Map<String, Object> getAllStaticLabel();
	
	/**
	 * 进程自定义符号，页面显示用。
	 * @return
	 */
	public Map<String, LabelVo> progCustomSymbol();
}
