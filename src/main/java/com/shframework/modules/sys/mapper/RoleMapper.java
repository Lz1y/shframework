package com.shframework.modules.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.sys.entity.Role;
import com.shframework.modules.sys.entity.RoleExample;

@MyBatisRepository
public interface RoleMapper {
	
	/**
	 * 获取可以 分发的角色 （用于绑定角色可分发的角色时使用）
	 * @param moduleId 指定模块的id 可选，null表示无条件
	 * @return
	 * @author RanWeizheng
	 */
	public List<Role> getCfgableRole(@Param("moduleId") Integer moduleId);
	
	/**
	 * 获取可以 分发的角色 （用于绑定角色可分发的角色时使用）
	 * @param moduleId 指定模块的id 可选，null表示无条件
	 * @return
	 * @author RanWeizheng
	 */
	public List<Integer> getCfgableRoleId(@Param("moduleId") Integer moduleId);
	
	/**
	 * 根据用户拥有的角色，找到其可以<strong>授权</strong>的角色的list
	 *  @param userRoleList 当该list为空时，表示所有的角色
	 * @param moduleId 指定模块的id
	 * @return
	 * @author RanWeizheng
	 */
	public List<Role> getDispatchableRole(@Param("userRoleList")List<Role> userRoleList, @Param("moduleId") Integer moduleId);
	
	/**
	 * 根据用户拥有的角色，找到其可以<strong>授权</strong>的角色的id 的 list
	 * @param userRoleList 当该list为空时，表示所有的角色
	 * @param moduleId 指定模块的id
	 * @return
	 * @author RanWeizheng
	 */
	public List<Integer> getDispatchableRoleId(@Param("userRoleList")List<Role> userRoleList, @Param("moduleId") Integer moduleId );
	
	/**
	 * 通过roleId 删除指定角色 与用户的关联
	 * @param roleId
	 * @return
	 */
	@Delete("delete from `sys_user_role` where user_id = #{userId}")
	public int deleteUserRoleByRoleId(Integer roleId);
	

	/**
	 * 通过roleId 删除指定角色的指定角色范围内的 可配置角色
	 * @param roleId
	 * @return
	 */
	public int deleteCfgRoleByRoleId(@Param("roleId")Integer roleId, @Param("cfgRoleIdList")List<Integer> cfgRoleIdList);
	
	/**
	 * 添加角色的可配置角色
	 * @param roleId
	 * @param cfgRoleId
	 * @return
	 */
	@Insert("insert into `sys_role_cfgrole` (role_id, cfg_role_id) values (#{0}, #{1})")
	public int addCfgRole(Integer roleId, Integer cfgRoleId);
	
	@Select("SELECT resource_id from sys_role_resource  where role_id=#{0} LIMIT 1")
	public Integer getResIdByRoleId(Integer roleId);
	
	@Insert("insert into `sys_user_role` (user_id, role_id) values (#{0}, #{1})")
	public int addRole(int userid, int roleid);
	
	@Delete("delete from `sys_user_role` where user_id = #{userid}")
	public int delRole(int userid);
	
	/**
	 * 删除指定用户的指定范围内的角色
	 * @param userId
	 * @param dispatchableRoleList
	 * @return
	 * @author Ranweizheng
	 */
	public int delRoleDispathable(@Param("userId")int userId, @Param("roleIdList")List<Integer> dispatchableRoleIdList);
	
	@Insert("insert into `sys_role_resource` (role_id, resource_id) values (#{0}, #{1})")
	public int addRoleResource(int roleId, int resoureId);
	
	@Delete("delete from `sys_role_resource` where role_id = #{roleId}")
	public int delRoleResourceByRoleId(int roleId);
	
	/**
	 * 根据角色id 找到拥有该角色的所有教师的信息  ，查询时，为了保证数据准确性，要关联教师表
	 * @param roleId 角色id
	 * @return
	 * @author RanWeizheng
	 */
	public List<Integer> getUserIdsByRoleId(Integer roleId );
	
	
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	public List<Role> queryRoleList(Map<String, Object> map);

	public int queryRoleListCount(Map<String, Object> map);
	
	List<String> queryRoleCodeListByUserId(String userid);
	
	List<Role> queryThisResourcesHasAnyRolesBySrrp(int resourceId);
	
	int queryIdByCode(String code);
}
