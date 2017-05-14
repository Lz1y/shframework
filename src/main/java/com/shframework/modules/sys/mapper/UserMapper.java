package com.shframework.modules.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.entity.UserExample;
import com.shframework.modules.sys.entity.vo.SaltVo;

@MyBatisRepository
public interface UserMapper {

	public void saveUser(User user);

	public void updateUser(User user);

	public void deleteUser(int id);
	
	/**
	 * 获取指定用户信息， 包括所拥有的角色。
	 * @param id
	 * @return
	 */
	public User queryUserRoleById(int id);
	
	/**
	 * 根据角色code 获取所有具有指定角色的用户的id
	 * @author RanWeizheng
	 * @return
	 */
	public List<Integer> getUserIdListByRoleCode(String roleCode);
	
	/**
	 * 根据角色id 获取所有具有指定角色的用户的id
	 * @author RanWeizheng
	 * @return
	 */
	public List<Integer> getUserIdListByRoleId(Integer roleId);
	
	public User getUser(int id);
	
	@Select("select password, CONCAT('s4h8t3d6', ':', salt) as salt from sys_user where id = #{id} and status = 1 and logic_delete = 0")
	public SaltVo queryPwdAndSalt(String id);
	
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(int id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(int id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
	public List<User> queryUserRoleList(Map<String, Object> map);
    int queryUserRoleListCount(Map<String, Object> map);
    
    List<User> passwordIsNotNull();

	public User queryUser(@Param("username")String username);
	
	String getId(@Param("username")String username);

	@Update("update sys_user set password = #{2} where id = #{0} and password = #{1}")
	public int cpwd(Integer id, String old_p, String new_p);
	
	List<String> queryIdList();
	
	/**
	 * 生产库，修正数据用
	 * @return
	 * @author RanWeizheng
	 * @date 2017年3月16日 下午1:40:35
	 */
	List<User> getNoConfirmStus();
}
