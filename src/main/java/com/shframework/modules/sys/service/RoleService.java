package com.shframework.modules.sys.service;

import java.util.List;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.sys.entity.Role;
import com.shframework.modules.sys.entity.User;

public interface RoleService {

	/**
	 * 角色列表
	 * @param pageSupport
	 * @param moduleId 模块id (父节点的id为1 的资源)
	 * @return
	 */
	PageTerminal<Role> queryRoleList(PageSupport pageSupport, Integer moduleId);
	
	/**
	 * 获取角色详情
	 * @param roleId
	 * @return
	 */
	Role queryRoleDetail(Integer roleId);
	
	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	int deleteRole(Integer roleId, User operator);
	/**
	 * 保存角色
	 * @param roleId
	 * @return
	 */
	int saveRole(Role role, Integer resourceId);
	
	
	/**
	 * 获得所有可以配置的角色
	 * @param moduleId	模块id 可以为空，为空时其不作为查询条件出现在sql中。
	 * @return
	 */
	List<Role> getDispatchableRoleList(Integer moduleId);
	
	/**
	 * 获得指定用户已经配置的角色id的List
	 * @param roleId
	 * @param moduleId
	 * @return
	 */
	List<Integer> getCfgRoleIdList(Integer roleId, Integer moduleId);
	/**
	 * 更新角色的可授权角色
	 * @param roleId
	 * @param cfgRoleIds
	 * @param moduleId
	 */
	void modifyCfgRole(int roleId, int[] cfgRoleIds, Integer moduleId);
	
	/**
	 * 通过角色id，获取拥有该角色的所有教师信息
	 * @param roleId
	 * @return
	 * @author RanWeizheng
	 * @date 2016年11月4日 下午3:12:27
	 */
	List<Integer> getUsersByRoleId(Integer roleId);
}
