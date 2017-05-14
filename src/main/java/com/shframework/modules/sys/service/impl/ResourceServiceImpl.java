package com.shframework.modules.sys.service.impl;

import static com.shframework.common.util.ParamsUtil.createProcResMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.common.util.RecordDeleteUtil;
import com.shframework.common.util.StringUtils;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.ResourceExample;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.entity.vo.ShiroPermissionVo;
import com.shframework.modules.sys.mapper.ResourceMapper;
import com.shframework.modules.sys.mapper.SrrpMapper;
import com.shframework.modules.sys.mapper.UserMapper;
import com.shframework.modules.sys.service.ResourceService;

/**
 * sys_resource表维护
 * @author RanWeizheng
 *
 */
@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	SrrpMapper srrpMapper;
	
	@Autowired
	ResourceMapper resourceMapper;

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	private CacheComponent<?> cacheComponent;
	
	private static final String defaultSortField = "_sr.priority";//默认排序字段
	private static final String defaultSortOrderby = "desc";
	
	@Override
	public Map<String, List<Resource>> res_role() {
		Map<String, List<Resource>> map = new HashMap<String, List<Resource>>();
		for (User user : userMapper.passwordIsNotNull()) {
			map.put(user.getId().toString(), resourceMapper.res_role(user.getId()));
		}
		
		return map;
	}
	
	@Override
	public List<Resource> userResRole(Integer userId, Integer resId) {
		Map<String, Object> parMap = Maps.newHashMap();
		Resource menu = (Resource) cacheComponent.resource(CacheComponent.KEY_MENU);
		List<Integer> parentList = new ArrayList<Integer>();
		getParentIdList(menu, resId, parentList);
		parMap.put("resIdList", parentList);
		parMap.put("userId", userId);
		return resourceMapper.userResRole(parMap);
	}
	
	@Override
	public Map<String, ShiroPermissionVo> resourceScope(Map<String, Object> param) {
		Map<String, ShiroPermissionVo> map = new HashMap<String, ShiroPermissionVo>();
		for (ShiroPermissionVo sp : srrpMapper.permissions(param)) map.put(sp.getRule(), sp);
		return map;
	}
	
	@Override
	public Map<String, Map<String, ShiroPermissionVo>> curRc() {
		Map<String, Map<String, ShiroPermissionVo>> map = new HashMap<String, Map<String,ShiroPermissionVo>>();
		Map<String, Object> param = new HashMap<String, Object>();
		for (User user : userMapper.passwordIsNotNull()) {
			param.put("userId", user.getId());
			map.put(user.getId().toString(), resourceScope(param));
		}
		return map;
	}
	
	@Override
	public int deleteRes(Integer resId, User operator) {
		ResourceExample example = new ResourceExample();
		example.createCriteria().andParentIdEqualTo(resId)
			.andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);//指定父Id 和 删除状态不为
		int childNum = resourceMapper.countByExample(example);
		if (childNum > 0){//存在子节点
			return 0;
		}
		
		Resource res = resourceMapper.selectByPrimaryKey(resId);
        if (res == null || res.getLogicDelete()==Constants.DICT_COMMON_YES){
             return 0;
        }
      res.setTitle(RecordDeleteUtil.getDelValue(resId, Constants.DB_COL_LENGTH_TITLE, res.getTitle()));
      res.setRule(RecordDeleteUtil.getDelValue(resId, Constants.DB_COL_LENGTH_TITLE, res.getRule()));//rule的长度限制与title一致
	  res.setLogicDelete(Constants.DICT_COMMON_YES);//逻辑删除
	  res.setLastModifyUserId(operator.getId());
	  
	  int result =  resourceMapper.updateByPrimaryKeySelective(res);
	  if (result > 0){
		  updateParentStatus(resId, Constants.DICT_COMMON_NO);
		  cacheComponent.renew(CacheComponent.KEY_MENU);
		  cacheComponent.renew(CacheComponent.KEY_BREADCRUMB);
		  cacheComponent.renew(CacheComponent.KEY_RESPERMISSION);
		  cacheComponent.renew(CacheComponent.KEY_PERMISSIONS);
		  cacheComponent.renew(CacheComponent.KEY_CURRC);
		  cacheComponent.renew(CacheComponent.KEY_RESROLE);
	  }
	  return result;
	}

	@Override
	@Transactional
	public int saveRes(Resource record, User operator) {
		record.setLastModifyUserId(operator.getId());
		if (record.getUrl()!=null){
			record.setUrl(record.getUrl().trim());
		}
	    try {
	    	if (record.getId() !=null && record.getId() >0){//update
	    		return resourceMapper.updateByPrimaryKeySelective(record);
	    	} else {//save
	    		if (record.getPriority() == null){
	    			record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
	    		}
	    		if (StringUtils.isBlank(record.getUrl())){
	    			record.setUrl(null);
	    		}
	    		record.setHasChild(Constants.DICT_COMMON_NO);
	    		record.setLocked(Constants.DICT_COMMON_NO);
	    		record.setStatus(Constants.DICT_COMMON_YES);
	    		record.setLogicDelete(Constants.DICT_COMMON_NO);
	    		updateParentStatus(record.getParentId(), Constants.DICT_COMMON_YES);
	    		return resourceMapper.insertSelective(record);
	    	}
			
		} catch (Exception e) {
			return 0;
			// TODO: handle exception
		} finally {
			cacheComponent.renew(CacheComponent.KEY_MENU);
		    cacheComponent.renew(CacheComponent.KEY_BREADCRUMB);
		    cacheComponent.renew(CacheComponent.KEY_RESPERMISSION);
		    cacheComponent.renew(CacheComponent.KEY_PERMISSIONS);
		    cacheComponent.renew(CacheComponent.KEY_CURRC);
		    cacheComponent.renew(CacheComponent.KEY_RESROLE);
		}
	     
		
	}

	@Override
	public PageTerminal<Resource> findAllByPage(PageSupport pageSupport, Integer resId, Integer allFlag) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		pageSupport.setPageSize(99999);
		Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport, defaultSortField + " " + defaultSortOrderby );
		if (allFlag != null && allFlag == Integer.parseInt(Constants.COMMON_YES)){//查询子节点
			Resource menu = (Resource) cacheComponent.resource(CacheComponent.KEY_MENU);
			List<Integer> parentList = new ArrayList<Integer>();
			parentList.add(resId);
			parentList.addAll(getAllResParentIds(menu, resId));
			map.put("resIdList", parentList);
		} else {
			map.put("resId", resId);
		}
        List<Resource> list = resourceMapper.queryResList(map);
        return new PageTerminal<Resource>(list, pageSupport, resourceMapper.queryResListCount(map));
	}
	
	/**
	 * 获得所有指定节点下的 “父节点”（即存在子节点的节点）的id
	 * @menu 目录数，其中包含了树形结构中所有的节点
	 * @param resId
	 * @return
	 */
	private List<Integer> getAllResParentIds(Resource menu, Integer resId){
		List<Resource> rList =  menu.getChild();
		List<Integer> list = new LinkedList<Integer>();
		for (Resource res : rList){
			if (res.getParentId() == resId.intValue()){
				list.add(res.getId());
				if (Constants.DICT_COMMON_YES == res.getHasChild()){//如果有子节点，则往下继续找
					list.addAll(getAllResParentIds(res, res.getId()));
				}
			} else if (Constants.DICT_COMMON_YES == res.getHasChild()){
				list.addAll(getAllResParentIds(res, resId));
			}
		}
		return list;
	}
	
	/**
	 * 获得所有指定节点下的 “父节点”（即存在子节点的节点）的id
	 * @menu 目录数，其中包含了树形结构中所有的节点
	 * @param resId
	 * @return
	 */
	private void getParentIdList(Resource menu, Integer resId, List<Integer> parentList){
		List<Resource> rList =  menu.getChild();
		for (Resource res : rList){
			if(res.getId().intValue() == resId.intValue()){
				parentList.add(res.getId());
				if(res.getParentId().intValue() != Constants.RESOURCE_ROOT_ID){
					parentList.add(res.getParentId());
					getParentIdList((Resource) cacheComponent.resource(CacheComponent.KEY_MENU), res.getParentId(), parentList);
				}
			}else{
				if(res.getHasChild().intValue() == Constants.DICT_COMMON_YES){
					getParentIdList(res, resId, parentList);
				}
			}
		}
	}

	@Override
	public Resource getResourceDetail(Integer resId) {
		return resourceMapper.selectByPrimaryKey(resId);
	}
	
	/**
	 * 更新父节点“has_child”字段状态  
	 * @param parentId
	 * @param flag  0: 删除时， 1 ： 增加时
	 */
	private void updateParentStatus(Integer parentId, Integer flag ){
		Resource res = new Resource();
		res.setId(parentId);
		if (flag == Constants.DICT_COMMON_YES){
			res.setHasChild(Constants.DICT_COMMON_YES);
			resourceMapper.updateByPrimaryKeySelective(res);
		} 
		else if(flag == Constants.DICT_COMMON_NO){
			ResourceExample example = new ResourceExample();
			example.createCriteria().andParentIdEqualTo(parentId).andLogicDeleteEqualTo(Constants.DICT_COMMON_NO);
			List<Resource> childList = resourceMapper.selectByExample(example);
			if (childList == null  || childList.size()==0){//没有子节点了
				res.setHasChild(Constants.DICT_COMMON_NO);
				resourceMapper.updateByPrimaryKeySelective(res);
			}//if
		} //if-else
	}

	@Override
	public List<Resource> getModuleList() {
		Resource menu = (Resource) cacheComponent.resource(CacheComponent.KEY_MENU);
		List<Resource> rList = new ArrayList<Resource>();
		if (menu != null && menu.getChild()!=null){
			for (Resource r : menu.getChild()){
				if (r.getShowMenu() == Integer.parseInt(Constants.COMMON_YES)){
					rList.add(r);
				}
			}
		}
		return rList;
	}

}
