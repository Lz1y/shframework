package com.shframework.modules.basic.service;

import java.util.List;

import com.shframework.modules.sys.entity.Resource;

public interface HomeService {

	/**
	 * 菜单树
	 * @return
	 */
	Resource getMenuTree();

	/**
	 * 面包屑
	 * @param id
	 * @return
	 */
	List<Resource> getBreadcrumbNavigation();

//	Map<String, Resource> getResource();
	
}
