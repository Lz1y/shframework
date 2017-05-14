package com.shframework.modules.sys.service.impl;

import static com.shframework.common.util.ParamsUtil.createProcResMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.common.util.RecordDeleteUtil;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.sys.entity.Role;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.mapper.PermissionMapper;
import com.shframework.modules.sys.mapper.RoleMapper;
import com.shframework.modules.sys.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public PageTerminal<Role> queryRoleList(PageSupport pageSupport, Integer moduleId) {
		pageSupport.setPageSize(99999);
		Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport);
		map.put("moduleId", moduleId);
        List<Role> list = roleMapper.queryRoleList(map);
        return new PageTerminal<Role>(list, pageSupport, roleMapper.queryRoleListCount(map));
	}
	
	/**
	 * 获取角色详情
	 * @param perId
	 * @return
	 */
	@Override
	public Role queryRoleDetail(Integer roleId){
		return roleMapper.selectByPrimaryKey(roleId);
	}
	
	/**
	 * 删除角色
	 */
	@Override
	@Transactional
	public int deleteRole(Integer roleId, User operator){
		
		Role role = roleMapper.selectByPrimaryKey(roleId);
		if (role==null || role.getLocked() == Constants.DICT_COMMON_YES 
				|| role.getLogicDelete() == Constants.DICT_COMMON_YES){
			return 0;
		}//锁定或者已经被删除的，不再重复操作
		role.setCode(RecordDeleteUtil.getDelValue(roleId, Constants.DB_COL_LENGTH_CODE, role.getCode()));
		role.setTitle(RecordDeleteUtil.getDelValue(roleId, Constants.DB_COL_LENGTH_TITLE, role.getTitle()));
		role.setLastModifyUserId(operator.getId());
		role.setLogicDelete(Constants.DICT_COMMON_YES);
		int result =  roleMapper.updateByPrimaryKeySelective(role);
		if (result > 0 ){//可选操作
//			roleMapper.delRoleResourceByRoleId(roleId);//删除角色-资源关联
			permissionMapper.delRT(roleId, null);//删除角色--资源+权限的关联
			roleMapper.deleteUserRoleByRoleId(roleId);//删除角色-用户关联
		}
		cacheComponent.renew(CacheComponent.KEY_SYSROLE);
		cacheComponent.renew(CacheComponent.KEY_SKDSYSROLE);
		cacheComponent.renew(CacheComponent.KEY_RESROLE);
		return result;
	}
	/**
	 * 保存角色
	 * @param record
	 * @return
	 */
	@Override
	@Transactional
	public int saveRole(Role record, Integer resoureId){
		
		int result = 0;
		if (record.getId() !=null && record.getId() >0){//update
			result = roleMapper.updateByPrimaryKeySelective(record);
			roleMapper.delRoleResourceByRoleId(record.getId());//删除角色-资源关联
			roleMapper.addRoleResource(record.getId(), resoureId);
			
		} else {//save
			if (record.getPriority() == null){
				record.setPriority(Constants.DICT_PRIORITY_DEFAULT);
			}
			record.setLocked(Constants.DICT_COMMON_NO);
			record.setStatus(Constants.DICT_COMMON_YES);
			record.setLogicDelete(Constants.DICT_COMMON_NO);
			result = roleMapper.insertSelective(record);
			if (result > 0){
				roleMapper.addRoleResource(record.getId(), resoureId);
			}
		}
		cacheComponent.renew(CacheComponent.KEY_SYSROLE);
		cacheComponent.renew(CacheComponent.KEY_SKDSYSROLE);
		return result;
	}
	
	/**
	 * 获得所有可以配置的角色
	 * @param moduleId	模块id 可以为空
	 * @return
	 */
	@Override
	public List<Role> getDispatchableRoleList(Integer moduleId){
		return roleMapper.getCfgableRole(moduleId);
	}
	
	/**
	 * 获得指定用户已经配置的角色id的List
	 * @param roleId
	 * @param moduleId
	 * @return
	 */
	@Override
	public List<Integer> getCfgRoleIdList(Integer roleId, Integer moduleId){
		List<Role> roleList = new ArrayList<Role>();
		Role r = new Role();
		r.setId(roleId);
		roleList.add(r);
		return roleMapper.getDispatchableRoleId(roleList, moduleId);
	}

	
	/**
	 * 更新角色的可授权角色
	 * @param roleId  配置的角色
	 * @param cfgRoleIds 可配置的角色
	 * @param moduleId	所属模块
	 */
	@Override
	@Transactional
	public void modifyCfgRole(int roleId, int[] cfgRoleIds, Integer moduleId){
		//先删除所有角色
		List<Integer> cfgableRoleIdList = roleMapper.getCfgableRoleId(moduleId);//获得指定模块下，所有可以分配的角色
		if (roleId <=0 || cfgableRoleIdList==null || cfgableRoleIdList.size()==0){
			return;
		}
		roleMapper.deleteCfgRoleByRoleId(roleId, cfgableRoleIdList);
		if (cfgRoleIds != null && cfgRoleIds.length > 0) {
			for (int i = 0; i < cfgRoleIds.length; i++) {
				if (cfgableRoleIdList.contains(cfgRoleIds[i]))
					roleMapper.addCfgRole(roleId, cfgRoleIds[i]);
			}
		}
	}

	@Override
	public List<Integer> getUsersByRoleId(Integer roleId) {
		return roleMapper.getUserIdsByRoleId(roleId);
	}
}
