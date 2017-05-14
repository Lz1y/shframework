package com.shframework.modules.basic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shframework.common.util.Constants;
import com.shframework.modules.basic.service.HomeService;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.ResourceExample;
import com.shframework.modules.sys.mapper.ResourceMapper;
import com.shframework.modules.sys.mapper.RoleMapper;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private ResourceMapper resourceMapper;
	
	@Autowired
	private RoleMapper roleMapper;

	public Map<String, Resource> getResource() {
		Map<String, Resource> map = new HashMap<String, Resource>();
		List<Resource> list = resourceMapper.allResource();
		for (Resource item : list) map.put(item.getRule(), item);
		return map;
	}
	
	@Override
	public Resource getMenuTree() {
		Resource resource = resourceMapper.selectByPrimaryKey(Constants.RESOURCE_ROOT_ID);
		recursionMenu(resource);
		return resource;
	}

	@Override
	public List<Resource> getBreadcrumbNavigation() {
		ResourceExample ex = new ResourceExample();
		ex.createCriteria().andRuleIsNotNull().andRuleNotEqualTo("");
		
		List<Resource> list = resourceMapper.selectByExample(ex);
		for (Resource item : list) item.setBreadcrumbs(recursionBreadcrumbNavigation(new LinkedList<Resource>(), item.getParentId()));
		
		return list;
	}

	private void recursionMenu(Resource resource) {
		resource.setRoles(roleMapper.queryThisResourcesHasAnyRolesBySrrp(resource.getId()));
		if (resource.getHasChild() == 1) {
			ResourceExample resExample = new ResourceExample();
			resExample.createCriteria().andParentIdEqualTo(resource.getId()).andLogicDeleteEqualTo(Integer.parseInt(Constants.COMMON_NO));
			resExample.setOrderByClause("priority desc");
			List<Resource> list = resourceMapper.selectByExample(resExample);

			resource.setChild(list);
			
			for (Resource item : list) 
				recursionMenu(item);
		}

		if (resource.getHasChild() == 0) resource.setChild(addOtherResource(new ArrayList<Resource>(), resource));
	}

	private LinkedList<Resource> recursionBreadcrumbNavigation(LinkedList<Resource> list, Integer id) {
		Resource resource = resourceMapper.selectByPrimaryKey(id);
		if (resource != null) {
			list.addFirst(resource);
			recursionBreadcrumbNavigation(list, resource.getParentId());
		}
		return list;
	}
	
	private List<Resource> addOtherResource(List<Resource> list, Resource resource) {
		for (String item : resourceMapper.queryPmstr(resource.getId())) {
			Resource temp = new Resource();
			temp.setParentId(resource.getId());
			temp.setRule(resource.getRule() + "_" + item);
//			temp.setId(resource.getId());
			list.add(temp);
		}
		return list;
	}

}
