/**
 * @description 
 * @author Josh Yan
 * @version 1.0
 * @datetime 2014年8月29日 下午6:57:49
 */
package com.shframework.modules.sys.service;

import java.util.Map;

import com.shframework.modules.sys.entity.User;

/**
 * @author Josh
 *
 */
public interface UserService {

	public void saveUser(User user);
	public void updateUser(User user);
	public void deleteUser(int id);
	public User getUser(int id);
	
	/**
	 * 获取当前系统中有效的user信息
	 * stauts == 1
	 * logic_delete ==0
	 * @return
	 */
	public Map<String, User> getAllActiveUser();
	
	User getUser(String username);
	int cpwd(Integer id, String old_p, String new_p);
	
	/**
	 * 重置密码
	 * @param userId
	 * @param new_p
	 * @return
	 * @author RanWeizheng
	 * @date 2017年4月17日 下午3:12:40
	 */
	int initpwd(Integer userId, String new_p);
	
}
