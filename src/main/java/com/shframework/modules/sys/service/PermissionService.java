package com.shframework.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.sys.entity.Permission;
import com.shframework.modules.sys.entity.Srrp;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.entity.vo.MergerPerVo;
import com.shframework.modules.sys.entity.vo.RoleVo;
import com.shframework.modules.sys.entity.vo.SrrpVo;

public interface PermissionService {

	/**
	 * 用户列表
	 * @param pageSupport
	 * @return
	 */
	PageTerminal<User> queryUserRoleList(PageSupport pageSupport);
	
	/**
	 * 获取所有具有超级管理员角色的用户的id
	 * @author RanWeizheng
	 * @return
	 */
	List<Integer> getAdminUserIdList();
	
	/**
	 * 获取相对于该用户活动的资源的id 的列表
	 * @param sessionUser
	 * @author RanWeizheng
	 * @return
	 */
	public List<Integer> getActiveResourceIdList(User sessionUser);
	
	/**
	 * 检查指定用户是否具有超级管理员权限；
	 * @param userId
	 * @return
	 */
	boolean isAdminUser(Integer userId);

	/**
	 * 权限列表
	 * @param pageSupport
	 * @return
	 */
	PageTerminal<Permission> queryPermissionList(PageSupport pageSupport);

	/**
	 * 获取权限详情
	 * @param perId
	 * @return
	 */
	Permission queryPermissionDetail(Integer perId);
	
	/**
	 * 删除权限
	 * @param per
	 * @return
	 */
	int deletePermission(Integer perId, User operator);
	/**
	 * 保存权限
	 * @param per
	 * @return
	 */
	int savePermission(Permission per);
	
	/**
	 * 获取角色、资源、权限
	 * @param roleid
	 * @return
	 */
	SrrpVo querySrrp(int roleid);
	
	/**
	 * 获取键值为resourceId + ""， 内容为包含 该资源下的子资源list、 资源权限的并的list、 资源-权限的可授权情况的Map 
	 * @author RanWeizheng
	 * @return
	 */
	Map<String, SrrpVo> getResPerMap();
	
	/**
	 * 获取指定角色的一组指定范围（resIdList中所包含id）的 授权情况
	 * @author RanWeizheng
	 * @param roleId
	 * @param resIdList
	 * @param mergerPermissionMap 保存虚拟权限的map
	 * @return
	 */
	List<Srrp> getSrrpList(Integer roleId, List<Integer> resIdList, Map<String, MergerPerVo> mergerPermissionMap);

	/**
	 * 获取用户、角色
	 * @param id userid
	 * @return
	 */
	RoleVo queryRoleVo(int userId, Integer moduleId, User currUser);

	/**
	 * 更新用户角色  (角色分发)
	 * @param id
	 * @param roleids
	 */
	void modifyRole(int userId, int[] roleids, Integer moduleId, User currUser);

	/**
	 * 通过role获得其对应的资源模块的Id
	 * @param roleId
	 * @return
	 * @author RanWeizheng
	 */
	Integer getResIdByRoleId(Integer roleId);
	/**
	 * 更新角色权限
	 * @param roleId 角色id
	 * @param rps	  包含该角色的所有权限
	 * @param scope
	 * @param mode
	 * @param customid
	 * @param resIdList 指定资源范围（为null表示清除所有绑定的权限）
	 * @param mergerPermissionMap 保存虚拟权限的map
	 */
	void modifyPermission(int roleId, String[] rps, int scope, int mode, Integer customid, List<Integer> resIdList, Map<String, MergerPerVo> mergerPermissionMap);
	
	/**
	 * 获取模板的权限
	 * @author RanWeizheng
	 * @return
	 */
	Map<String, Object> getTemplatePurview();

}
