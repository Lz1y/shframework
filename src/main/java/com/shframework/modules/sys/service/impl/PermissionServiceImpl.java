package com.shframework.modules.sys.service.impl;

import static com.shframework.common.util.ParamsUtil.createProcResMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.common.util.PurviewUtil;
import com.shframework.common.util.RecordDeleteUtil;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.sys.entity.Permission;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.Role;
import com.shframework.modules.sys.entity.Srrp;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.entity.vo.MergerPerVo;
import com.shframework.modules.sys.entity.vo.ResPerVo;
import com.shframework.modules.sys.entity.vo.RoleVo;
import com.shframework.modules.sys.entity.vo.SrrpVo;
import com.shframework.modules.sys.mapper.PermissionMapper;
import com.shframework.modules.sys.mapper.ResourceMapper;
import com.shframework.modules.sys.mapper.RoleMapper;
import com.shframework.modules.sys.mapper.SrrpMapper;
import com.shframework.modules.sys.mapper.UserMapper;
import com.shframework.modules.sys.service.PermissionService;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private SrrpMapper srrpMapper;
	
	@Autowired
	private ResourceMapper resourceMapper;
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	private static final String defaultPermissionSortField = "_sp.priority";//默认排序字段
	private static final String defaultSortOrderby = "desc";

	@Override
	public PageTerminal<User> queryUserRoleList(PageSupport pageSupport) {
		Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport);
        List<User> list = userMapper.queryUserRoleList(map);
        return new PageTerminal<User>(list, pageSupport, userMapper.queryUserRoleListCount(map));
	}
	
	/**
	 * 获取所有具有超级管理员角色的用户的id
	 * @author RanWeizheng
	 * @return
	 */
	public List<Integer> getAdminUserIdList(){
//		List<Integer> cache_sys_userList = userMapper.getUserIdListByRoleCode(Constants.HEAD_SYS);
		List<Integer> adminUserList = userMapper.getUserIdListByRoleCode(Constants.SUPER_ADMIN_ROLL);
//		cache_sys_userList.removeAll(adminUserList);
//		cache_sys_userList.addAll(adminUserList);
//		return cache_sys_userList;
		return adminUserList;
	};
	
	/**
	 * 获取相对于该用户活动的资源的id 的列表
	 * @param sessionUser
	 * @author RanWeizheng
	 * @return
	 */
	public List<Integer> getActiveResourceIdList(User sessionUser){
		return resourceMapper.getActiveResourceIdList(sessionUser.getRoles());
	}
	
	/**
	 * 检查指定用户是否具有超级管理员权限；
	 * @param userId
	 * @return
	 */
	public boolean isAdminUser(Integer userId){
		List<Integer> adminList = getAdminUserIdList();
		return adminList.contains(userId);
	}

	@Override
	public PageTerminal<Permission> queryPermissionList(PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultPermissionSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		pageSupport.setPageSize(99999);
		Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport, defaultPermissionSortField + " " + defaultSortOrderby );
        List<Permission> list = permissionMapper.queryPermissionList(map);
        return new PageTerminal<Permission>(list, pageSupport, permissionMapper.queryPermissionListCount(map));
	}

	/**
	 * 获取权限详情
	 * @param perId
	 * @return
	 */
	@Override
	public Permission queryPermissionDetail(Integer perId){
		return permissionMapper.selectByPrimaryKey(perId);
	}
	
	/**
	 * 删除权限
	 * @param per
	 * @return
	 */
	@Override
	public int deletePermission(Integer perId, User operator){
		Permission per = permissionMapper.selectByPrimaryKey(perId);
		if (per==null || per.getLogicDelete() == Constants.DICT_COMMON_YES){
			return 0;
		}
		per.setCode(RecordDeleteUtil.getDelValue(perId, Constants.DB_COL_LENGTH_CODE, per.getCode()));
		per.setTitle(RecordDeleteUtil.getDelValue(perId, Constants.DB_COL_LENGTH_TITLE, per.getTitle()));
		per.setLastModifyUserId(operator.getId());
		per.setLogicDelete(Constants.DICT_COMMON_YES);
		return permissionMapper.updateByPrimaryKeySelective(per);
	}
	/**
	 * 保存权限
	 * @param record
	 * @return
	 */
	@Override
	public int savePermission(Permission record){
		if (record.getId() !=null && record.getId() >0){//update
			return permissionMapper.updateByPrimaryKeySelective(record);
		} else {//save
			if (record.getPriority() == null){
				record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
			}
			record.setLocked(Constants.DICT_COMMON_NO);
			record.setStatus(Constants.DICT_COMMON_YES);
			record.setLogicDelete(Constants.DICT_COMMON_NO);
			return permissionMapper.insertSelective(record);
		}
	}
	
	@Override
	public SrrpVo querySrrp(int roleid) {
		return new SrrpVo(srrpMapper.querySrrpByRoleId(roleid), resourceMapper.allResource(), permissionMapper.allPermission());
	}
	
	@Override
	public RoleVo queryRoleVo(int id,  Integer moduleId, User currUser) {
		List<Role> dispathalbeList = roleMapper.getDispatchableRole(currUser.getRoles(), moduleId);
		User user = userMapper.queryUserRoleById(id);
		if (user==null){
			user = userMapper.selectByPrimaryKey(id);
		}
		return new RoleVo(user, dispathalbeList);
	}
	
	@Override
	@Transactional
	public void modifyRole(int userid, int[] roleids, Integer moduleId, User currUser) {
		
		List<Integer> dispathalbeRoleIdList = roleMapper.getDispatchableRoleId(currUser.getRoles(), moduleId);
		if (userid <= 0 || dispathalbeRoleIdList==null || dispathalbeRoleIdList.size()==0){
			return;
		}
		roleMapper.delRoleDispathable(userid, dispathalbeRoleIdList);
		if (roleids != null && roleids.length > 0) {
			for (int i = 0; i < roleids.length; i++) {
				if (dispathalbeRoleIdList.contains(roleids[i]))
					roleMapper.addRole(userid, roleids[i]);
			}
		}
		cacheComponent.renew(CacheComponent.KEY_MENU);
	    cacheComponent.renew(CacheComponent.KEY_RESPERMISSION);
	    cacheComponent.renew(CacheComponent.KEY_PERMISSIONS);
	    cacheComponent.renew(CacheComponent.KEY_CURRC);
	    cacheComponent.renew(CacheComponent.KEY_RESROLE);
	}
	
	/**
	 * 通过role获得其对应的资源模块的Id
	 * @param roleId
	 * @return
	 * @author RanWeizheng
	 */
	@Override
	public Integer getResIdByRoleId(Integer roleId){
		return roleMapper.getResIdByRoleId(roleId);
	}
	
	/**
	 * 获取键值为resourceId ， 内容为包含 该资源下的子资源list、 资源权限的并的list、 资源-权限的可授权情况的Map 
	 * @return
	 * @author RanWeizheng
	 */
	@Override
	public Map<String, SrrpVo> getResPerMap(){
		Resource resTree = (Resource) cacheComponent.resource(CacheComponent.KEY_MENU);
		List<Permission> allPermissionList =  permissionMapper.allPermission();//所有权限
		
		boolean isMerger = PurviewUtil.getIsMerger();
		Map<String, MergerPerVo> MergerPermissionMap = PurviewUtil.getMergerPermissionMap();
		if (isMerger){// 判断是否要合并权限
			if (MergerPermissionMap != null){
				for (String mergerKey : MergerPermissionMap.keySet()){//内部部分可以提取出来
					boolean isContain = false;
					MergerPerVo vo = MergerPermissionMap.get(mergerKey);
					List<String> mergerCodeList = vo.getPerCodeList();
					List<Integer> mergerIdList = new LinkedList<Integer>();
					List<Permission> pList = new LinkedList<Permission>();
					for (Permission p : allPermissionList){
						if (mergerCodeList.contains(p.getCode())){
							mergerIdList.add(p.getId());
							isContain = true;
						} else {
							pList.add(p);
						}
					}//for
					vo.setPerIdList(mergerIdList);
					if (isContain){
						Permission mergerPer = dozerBeanMapper.map(vo, Permission.class);
						pList.add(mergerPer);
					}
					allPermissionList = pList;
				}
			}
		}
		
		Map<String, SrrpVo> resPerMap = new HashMap<String, SrrpVo>();
		if (resTree.getChild() != null ){
			for(Resource res : resTree.getChild()){
				if (res.getShowMenu() != null && res.getShowMenu() == Constants.DICT_COMMON_YES)
					resPerMap.put(res.getId() + "", getSrrpVoByRes(res, allPermissionList, isMerger, MergerPermissionMap));
			}
		}
		return resPerMap;
	}
	
	@Override
	public List<Srrp> getSrrpList(Integer roleId, List<Integer> resIdList, Map<String, MergerPerVo> mergerPermissionMap){
		List<Srrp> srrpList =  srrpMapper.querySrrpByRoleIdAndResIdList(roleId, resIdList);
		if (PurviewUtil.getIsMerger()){
			for (Srrp srrp : srrpList){//替换后，可能会出现内容一致的实体（原先只是权限id不同，现在被替换成同一个虚拟的id了），但是不影响前台判断，故未加额外的处理
				srrp.setPermissionId(getMergerPerId(srrp.getPermissionId(), mergerPermissionMap));
			}
		}
		return srrpList;
	}
	/**
	 * 获取某个模块下的所有子模块，所有包含的权限以及各个子模块可以分配的权限的信息
	 * @author RanWeizheng
	 * @param module 父资源
	 * @param allPermissionList 所有的权限
	 * @param boolean isMergerPer 是否要合并权限 
	 * @param Map<String, MergerPerVo> MergerPermissionMap 存放合并权限信息的map
	 * @return
	 */
	private SrrpVo getSrrpVoByRes(Resource module, List<Permission> allPermissionList, boolean isMergerPer, Map<String, MergerPerVo> mergerPermissionMap){
		SrrpVo vo = new SrrpVo();
		List<Resource> rlist = getAllChildRes(module, true, 0);//资源列表
		Map<String, ResPerVo> resPermissionMap = getSrrpByResList(rlist, module, isMergerPer, mergerPermissionMap);//具体的资源-权限对应情况
		List<Permission> plist = getResAllPermission(resPermissionMap, allPermissionList);//权限列表
		
		
		List<Integer> resIdList = new ArrayList<Integer>();
		for (Resource res: rlist){
			resIdList.add(res.getId());
		}
		vo.setResIdList(resIdList);
		vo.setRlist(rlist);
		vo.setPlist(plist);
		vo.setResPermissionMap(resPermissionMap);
		vo.setMergerPermissionMap(mergerPermissionMap);
		return vo;
	} 
	
	/**
	 * 递归获取孩子资源的list（中序）
	 * @author RanWeizheng
	 * @param res  当前资源信息
	 * @param isContainRoot 是否包含当前节点
	 * @return
	 */
	private List<Resource> getAllChildRes(Resource res, boolean isContainRoot, int level){
		List<Resource> rlist = new ArrayList<Resource>();
		res.setLevel(level);
		if (isContainRoot)
			rlist.add(res);
		if (res.getChild() != null && res.getHasChild() ==1){
			for (Resource childRes : res.getChild()){
				rlist.addAll(getAllChildRes(childRes, true, level +1));
			}
		}
		return rlist;
	}
	
	/**
	 * 获取准确的 资源-权限的可配置情况， 这里，对于需要合并权限的情形，要替换其相应的code
	 * @author RanWeizheng
	 * @param rlist  资源列表
	 * @param module 模块根资源
	 *
	 * @param boolean isMergerPer 是否要合并权限 
	 * @param Map<String, MergerPerVo> MergerPermissionMap 存放合并权限信息的map 
	 * 
	 * @return  Map<String rule, String[] permission>
	 */
	private Map<String, ResPerVo> getSrrpByResList(List<Resource> rlist, Resource module, boolean isMergerPer, Map<String, MergerPerVo> mergerPermissionMap){
		Map<String, ResPerVo> resPermissionMap = new HashMap<String, ResPerVo>();
		Map<String, ResPerVo> parentResPermissionMap = new HashMap<String, ResPerVo>();//存放父级节点可以继承的权限
		String[] defaultPer = PurviewUtil.getResourcePurview(PurviewUtil.RESOURCE_PARENT_DEFAULT);//无链接，但是存在于目录中的父节点，要有一个默认的权限
		
		ResPerVo moduleResPerVo = new ResPerVo();
		moduleResPerVo.setFullpath(module.getRule());
		String[] modulePermission =  PurviewUtil.getResourcePurview(module.getRule());
		if (modulePermission != null){
			List<String> modulePerList = new ArrayList<String>();
			modulePerList.addAll(Arrays.asList(modulePermission));
			if (!isMergerPer){
				modulePerList = replaceMergerPerCode(modulePerList, mergerPermissionMap);
			}
			moduleResPerVo.setPerList(modulePerList);
		}
		parentResPermissionMap.put(module.getId()+"", moduleResPerVo);
		
		for (Resource res: rlist){//这个list是中序遍历，故父节点总在子节点前出现，不需要担心无法获取 可继承的权限的问题
			List<String> perList = new ArrayList<String>();
			String fullpath = res.getRule();
			ResPerVo pResPerVo = parentResPermissionMap.get(res.getParentId() + "");
			if (pResPerVo !=null){
				fullpath =  pResPerVo.getFullpath() +"." +  fullpath;
				if (pResPerVo.getPerList() !=null)
					perList.addAll(pResPerVo.getPerList());//获取父节点的可继承权限
			}
			String[] permission =  PurviewUtil.getResourcePurview(fullpath);
			
			if (permission != null){
				perList.addAll(Arrays.asList(permission));
			}
			
			if (res.getHasChild() != 0){//如果存在子节点，则将可以继承的部分放入parentResPermissionMap
				ResPerVo resPerVo = new ResPerVo();
				resPerVo.setFullpath(fullpath);
				resPerVo.setPerList(PurviewUtil.getResourcePurviewWithOutLocal(perList, true));//移除了不可继承的权限
				if (!isMergerPer){
					resPerVo.setPerList(replaceMergerPerCode(resPerVo.getPerList(), mergerPermissionMap));
				}
				parentResPermissionMap.put(res.getId()+"", resPerVo);
			}
			if (StringUtils.isBlank(res.getUrl()) && res.getHasChild() != 0){
				ResPerVo resPerVo = new ResPerVo();
				resPerVo.setPerList(Arrays.asList(defaultPer));//插入一个默认的数组， 父节点，且没有url的，不允许其拥有权限其他权限
				resPerVo.setReadOnlyPerList(new ArrayList<String>());
				resPermissionMap.put(res.getRule(), resPerVo);
			} else {
				ResPerVo resPerVo = new ResPerVo();
				resPerVo.setPerList(PurviewUtil.getResourcePurviewWithOutLocal(perList, false));
				resPerVo.setReadOnlyPerList(PurviewUtil.getPermissionLocal(perList));
				if (!isMergerPer){
					resPerVo.setPerList(replaceMergerPerCode(resPerVo.getPerList(), mergerPermissionMap));
					resPerVo.setReadOnlyPerList(replaceMergerPerCode(resPerVo.getReadOnlyPerList(), mergerPermissionMap));
				}
				resPermissionMap.put(res.getRule(), resPerVo);
			}
		}
		return resPermissionMap;
	}
	
	/**
	 * 替换 合并的权限 的code 转换成实际的code
	 * 在isMerger==0（false）时被调用
	 * @return
	 */
	private List<String> replaceMergerPerCode(List<String> pCodeList, Map<String, MergerPerVo> mergerPermissionMap ){
		if (mergerPermissionMap != null){
			for (String mergerKey : mergerPermissionMap.keySet()){//内部部分可以提取出来
//				boolean isContain = false;
				MergerPerVo vo = mergerPermissionMap.get(mergerKey);
				String mergerCode = vo.getCode();
				List<String> mergerCodeList = vo.getPerCodeList();
//				List<String> list = new LinkedList<String>();
//				
//				for (String code : pCodeList){
//					if (mergerCodeList.contains(code)){
//						isContain = true;
//					} else {
//						list.add(code);
//					}
//				}//for
//				if (isContain){
//					list.add(vo.getCode());
//				}
//				pCodeList = list;
				Integer index = pCodeList.indexOf(mergerCode);
				if (index != -1){
					pCodeList.remove(index);
					pCodeList.addAll(index, mergerCodeList);
				}
				
			}//for
		}
		return pCodeList;
	}
	
	/**
	 * 替换合并的权限的id
	 * @return
	 */
	private Integer getMergerPerId(Integer perId, Map<String, MergerPerVo> mergerPermissionMap){
		if (mergerPermissionMap != null){
			for (String mergerKey : mergerPermissionMap.keySet()){//内部部分可以提取出来
				MergerPerVo vo = mergerPermissionMap.get(mergerKey);
				List<Integer> perIdList = vo.getPerIdList();
				if (perIdList.contains(perId)){
					return vo.getId();
				} else {
					return perId;
				}
			}//for
		}
		return perId;
	}
	
	/**
	 * 获取该resource下所有可能权限的并集
	 * @param rlist
	 * @return
	 * @author RanWeizheng
	 */
	private List<Permission> getResAllPermission(Map<String, ResPerVo> resPermissionMap, List<Permission> allList){
		List<Permission> pList = new ArrayList<Permission>();
		for (Permission permission : allList){//为了保证顺序
			if (permission!=null && getIsContainPermission(resPermissionMap, permission))
				pList.add(permission);
		}
		return pList;
	}
		
		/**
		 * 检查是否具有这个权限
		 * @author RanWeizheng
		 * @param resPermissionMap
		 * @param permission
		 * @return
		 */
	private boolean getIsContainPermission(Map<String, ResPerVo>resPermissionMap, Permission permission){
		boolean defaultFlag = false;
		String perCode = permission.getCode();
		//遍历map，直到找到
		for( String key : resPermissionMap.keySet()){
			ResPerVo resPerVo = resPermissionMap.get(key); 
			List<String> perList = resPerVo.getPerList();
			if (perList!=null){
				for (String per : perList){
					if (StringUtils.isNotBlank(per) && per.equals(perCode)){
						return true;
					}//if
				}//for
			}//if 
		}//for
		
		return defaultFlag;
		}
	
	@Override
	@Transactional
	public void modifyPermission(int roleId, String[] rps, int scope, int mode, Integer customid, List<Integer> resIdList, Map<String, MergerPerVo> mergerPermissionMap) {
		permissionMapper.delRT(roleId, null);//清掉角色所有绑定的权限
	//	permissionMapper.delRT(roleId, resIdList);//如果角色允许与多个资源模块绑定，使用这个。
		boolean isMerger = PurviewUtil.getIsMerger();
		if (rps != null && rps.length > 0) {
			for (int i = 0; i < rps.length; i++) {
				String[] rp = rps[i].split("_");
				if (isMerger && Integer.parseInt(rp[1]) < 0){
					MergerPerVo vo = mergerPermissionMap.get(rp[1].toString());
					if (vo != null && vo.getPerIdList() != null){
						for (int origPerId : vo.getPerIdList()){
							permissionMapper.saveRT(roleId, Integer.valueOf(rp[0]), origPerId, scope, customid, mode);
						}
					}
				} else{
					permissionMapper.saveRT(roleId, Integer.valueOf(rp[0]), Integer.valueOf(rp[1]), scope, customid, mode);
				}
			}
		}
		cacheComponent.renew(CacheComponent.KEY_MENU);
		cacheComponent.renew(CacheComponent.KEY_RESPERMISSION);
		cacheComponent.renew(CacheComponent.KEY_PERMISSIONS);
		cacheComponent.renew(CacheComponent.KEY_CURRC);
		cacheComponent.renew(CacheComponent.KEY_RESROLE);
		
		
	}
	
	@Override
	@Cacheable(value = "homeCache", key = "'templatePurview'")
	public Map<String, Object> getTemplatePurview(){
		return PurviewUtil.getTemplatePurview();
	}
	

}
