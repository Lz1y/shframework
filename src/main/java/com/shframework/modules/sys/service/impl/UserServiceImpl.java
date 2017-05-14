/**
 * @description 
 * @author Josh Yan
 * @version 1.0
 * @datetime 2014年8月29日 下午7:03:41
 */
package com.shframework.modules.sys.service.impl;

import static com.shframework.common.util.Constants.HASH_DEFAULT_SALT;
import static com.shframework.common.util.Constants.HASH_ITERATION_AMOUNT;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.shframework.common.util.Constants;
import com.shframework.common.util.EncryptUtils;
import com.shframework.common.util.EncryptionUtil;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.entity.UserExample;
import com.shframework.modules.sys.mapper.UserMapper;
import com.shframework.modules.sys.service.UserService;

/**
 * @author Josh
 *
 */
@Service("userService")
@PropertySource("classpath:shiro/shiro.properties")
public class UserServiceImpl implements UserService {

	@Autowired
	private Environment env;

	@Autowired
	private CacheComponent<?> cacheComponent;
	
	public void setEnv(Environment env) {
		this.env = env;
	}

	@Resource
	private UserMapper userMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public String getApplicationSalt() {
		return EncryptionUtil.decrypt(env.getProperty("shiro.applicationSalt"));
	}

	public String getCombinedSalt(String salt) {
		return EncryptionUtil.decrypt(env.getProperty("shiro.applicationSalt")) + ":" + salt;
	}

	public Integer getIterations() {
		return Integer.parseInt(env.getProperty("shiro.hashIterations"));
	}

	public static String getSalt() {
		return new SecureRandomNumberGenerator().nextBytes().toBase64();
	}

	public String encodePassword(String password, String salt) {
		return new Sha512Hash(password, getCombinedSalt(salt), getIterations()).toBase64();
	}

	@Override
	public void saveUser(User user) {
		user.setSalt(getSalt());
		user.setPassword(encodePassword(user.getPassword(), user.getSalt()));
		user.setCreateDate(new Date());
		userMapper.saveUser(user);
		cacheComponent.renew(CacheComponent.KEY_SYSUSER);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateByPrimaryKeySelective(user);
		cacheComponent.renew(CacheComponent.KEY_SYSUSER);
	}

	@Override
	public void deleteUser(int id) {
		userMapper.deleteUser(id);
		cacheComponent.renew(CacheComponent.KEY_SYSUSER);
	}

	@Override
	public User getUser(int id) {
		return userMapper.getUser(id);
	}
	
	@Override
	public User getUser(String username){
		return userMapper.queryUser(username); 
	}

	@Override
	public Map<String, User> getAllActiveUser() {
		Map<String, User> userMap = new HashMap<String, User>();
		UserExample example = new UserExample();
		example.createCriteria()
			.andStatusEqualTo(Integer.parseInt(Constants.COMMON_YES))
			.andLogicDeleteEqualTo(Integer.parseInt(Constants.COMMON_NO));
		List<User> uList = userMapper.selectByExample(example);
		for (User user : uList){
			user.setPassword("");
			user.setSalt("");
			userMap.put(user.getId() + "",  user);
		}
		return userMap;
	}
	
	@Override
	public int cpwd(Integer id, String old_p, String new_p) {
		User user = userMapper.selectByPrimaryKey(id);
		if (user==null){
			return 0;
		}
		return userMapper.cpwd(id, 
			EncryptUtils.encryptSHA512(old_p, HASH_DEFAULT_SALT + user.getSalt(), HASH_ITERATION_AMOUNT), 
			EncryptUtils.encryptSHA512(new_p, HASH_DEFAULT_SALT + user.getSalt(), HASH_ITERATION_AMOUNT));
	}
	
	/**
	 * 重置密码
	 * @param userId
	 * @param new_p
	 * @return
	 * @author RanWeizheng
	 * @date 2017年4月17日 下午3:12:40
	 */
	@Override
	public int initpwd(Integer userId, String new_p){
		User user = new User();
		user.setId(userId);
		String salt = ByteSource.Util.bytes(EncryptUtils.randomSalt(16)).toBase64();
		user.setPassword(EncryptUtils.encryptSHA512(
				new_p, 
				HASH_DEFAULT_SALT + salt, 
				HASH_ITERATION_AMOUNT));
		user.setSalt(salt);
		return userMapper.updateByPrimaryKeySelective(user);
	}
	

}
