package com.shframework.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.entity.vo.ShiroPermissionVo;

/**
 * sys_resource表维护
 * @author RanWeizheng
 *
 */
public interface ResourceService {

	/**
	 * 删除资源
	 * @param resId 资源id
	 * @param operator 操作者
	 * @return
	 */
	int deleteRes(Integer resId, User operator);
	
	/**
	 * 保存资源
	 * @param res
	 * @return
	 */
	int saveRes(Resource res, User operator);
	
	/**
	 * 根据条件查找符合要求的资源
	 * @param pageSupport
	 * @param resId 资源id
	 * @param allFlag 是否查找子节点
	 * @return
	 */
	PageTerminal<Resource> findAllByPage(PageSupport pageSupport, Integer resId, Integer allFlag);
	
	/**
	 * 根据资源id获取资源详情
	 * @param resId
	 * @return
	 */
	Resource getResourceDetail(Integer resId);

	/**
	 * 获取第一级资源的list
	 * @return
	 */
	List<Resource> getModuleList();

	/**
	 * 切换角色权限(某资源的权限范围)
	 * @param username
	 * @param role_ids
	 * @return
	 */
	Map<String, ShiroPermissionVo> resourceScope(Map<String, Object> param);

	/**
	 * 以用户id 为key 保存其 资源-角色关系
	 * @return
	 * @author RanWeizheng
	 * @date 2016年3月3日 下午7:02:29
	 */
	Map<String, List<Resource>> res_role();
	
	List<Resource> userResRole(Integer userId, Integer resId);

	Map<String, Map<String, ShiroPermissionVo>> curRc();
}