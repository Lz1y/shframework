package com.shframework.modules.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.sys.entity.Permission;
import com.shframework.modules.sys.entity.PermissionExample;
import com.shframework.modules.sys.entity.vo.PermissionVo;

@MyBatisRepository
public interface PermissionMapper {
	
	
	@Select("call proc_authorization(#{username}, null)")
	List<String> queryRules(String username);

	@Select("call proc_authorization(#{0}, #{1})")
	List<String> queryRules1(String username, String rules);
	
    int countByExample(PermissionExample example);

    int deleteByExample(PermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

	@Select("select * from v_resource_permission where roleid = #{roleid}")
	List<PermissionVo> viewRP(Integer roleid);
	
	@Select("select * from v_datapermission_username where username = #{username}")
	List<PermissionVo> viewDU(String username);
	
	//@Delete("delete from sys_role_resource_permission where role_id = #{roleid} and resource_id in <foreach collection='resIdList' item='listItem' open='(' close=')' separator=',' >#{listItem}</foreach>")
	/**
	 * 删除指定角色的 相关权限
	 * @param roleid
	 * @param resIdList 可以为空，但不能是size==0的list
	 * @return
	 */
	int delRT(@Param("roleid")Integer roleid, @Param("resIdList")List<Integer> resIdList);
	
	@Insert("insert into `sys_role_resource_permission` (role_id, resource_id, permission_id, scope, custom_id, mode) values(#{0}, #{1}, #{2}, #{3}, #{4}, #{5})")
	int saveRT(Integer roleid, Integer resid, Integer pmsid, Integer scope, Integer cstid, int mode);

	List<Permission> queryPermissionList(Map<String, Object> map);
	int queryPermissionListCount(Map<String, Object> map);
	
	List<Permission> allPermission();
}
