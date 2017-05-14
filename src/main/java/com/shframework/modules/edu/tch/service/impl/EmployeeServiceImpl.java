package com.shframework.modules.edu.tch.service.impl;

import static com.shframework.common.util.Constants.HASH_DEFAULT_SALT;
import static com.shframework.common.util.Constants.HASH_ITERATION_AMOUNT;
import static com.shframework.common.util.ParamsUtil.createProcResMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shframework.common.util.Constants;
import com.shframework.common.util.EncryptUtils;
import com.shframework.common.util.EncryptionUtil;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.common.util.PinYinUtil;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.edu.tch.entity.EduTchEmployee;
import com.shframework.modules.edu.tch.mapper.EduTchEmployeeMapper;
import com.shframework.modules.edu.tch.service.EmployeeService;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.entity.UserExample;
import com.shframework.modules.sys.mapper.UserMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final String defaultSortField = "_tch.modify_date";
	
	private static final String defaultSortOrderby = "desc";
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private EduTchEmployeeMapper eduTchEmployeeMapper;

	@Autowired
	private Environment env;
	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Override
	public PageTerminal<EduTchEmployee> list(Integer departmentId,PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport, defaultSortField + " " + defaultSortOrderby);
		map.put("departmentId", departmentId);
		List<EduTchEmployee> list = eduTchEmployeeMapper.queryByCondition(map);
		return new PageTerminal<EduTchEmployee>(list, pageSupport, eduTchEmployeeMapper.countByCondition(map));
	}

	@Override
	public EduTchEmployee detail(Integer userId) {
		return eduTchEmployeeMapper.detail(userId);
	}

	@Override
	@Transactional
	public void save(User user, EduTchEmployee eduTchEmployee) throws Exception {
		user.setStatus(Constants.BASE_STATUS_ONE);
		//新建“教职工”，保存密码
//		user.setSalt(getSalt());
//		user.setPassword(encodePassword(user.getPassword(), user.getSalt()));
		
		String salt = ByteSource.Util.bytes(EncryptUtils.randomSalt(16)).toBase64();
		user.setPassword(EncryptUtils.encryptSHA512(
				"1234567890", 
				HASH_DEFAULT_SALT + salt, 
				HASH_ITERATION_AMOUNT));
		user.setSalt(salt);
		
		userMapper.insertSelective(user);
		eduTchEmployee.setUserId(user.getId());
		String Hanyu = user.getUsername();
		eduTchEmployee.setPinyin(PinYinUtil.getPingYin(Hanyu));
		eduTchEmployee.setJianpin(PinYinUtil.getPinYinHeadChar(Hanyu));
		
		eduTchEmployee.setLastModifyUserId(user.getId());
		eduTchEmployee.setCreateDate(new Date());
		eduTchEmployee.setModifyDate(new Date());
		eduTchEmployee.setLogicDelete(Constants.BASE_LOGIC_DELETE_ZERO);
		
		eduTchEmployeeMapper.insertSelective(eduTchEmployee);
		
		cacheComponent.renew(CacheComponent.KEY_SYSUSER);
		@SuppressWarnings("unchecked")
		HashMap<String, List<String>> pMap = (HashMap<String, List<String>>) cacheComponent.resource(CacheComponent.KEY_PERMISSIONS);
		pMap.put(eduTchEmployee.getUserId().toString(), new ArrayList<String>());
		cacheComponent.update(CacheComponent.KEY_PERMISSIONS, pMap);
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
	@Transactional
	public void update(User user, EduTchEmployee eduTchEmployee) {
		
		user.setStatus(Constants.BASE_STATUS_ONE);
		userMapper.updateByPrimaryKeySelective(user);
		eduTchEmployee.setModifyDate(new Date());
		eduTchEmployeeMapper.updateByPrimaryKeySelective(eduTchEmployee);

		cacheComponent.renew(CacheComponent.KEY_SYSUSER);
	}

	@Override
	public int delete(Integer userId, User sessionUser) {
		
		EduTchEmployee eduTchEmployee = eduTchEmployeeMapper.selectByPrimaryKey(userId);
		eduTchEmployee.setLogicDelete(Constants.BASE_LOGIC_DELETE_ONE);
		eduTchEmployee.setLastModifyUserId(sessionUser.getId());
		eduTchEmployee.setModifyDate(new Date());
		int result = eduTchEmployeeMapper.updateByPrimaryKeySelective(eduTchEmployee);
		cacheComponent.renew(CacheComponent.KEY_SYSUSER);
		return  result;
	}

	@Override
	public PageTerminal<EduTchEmployee> listRecursionByDepartmentId(
			List<Integer> linkedListDepartmentId, PageSupport pageSupport) {
		
		
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport, defaultSortField + " " + defaultSortOrderby);
		map.put("linkedListDepartmentId", linkedListDepartmentId);
		List<EduTchEmployee> list = eduTchEmployeeMapper.listRecursionByDepartmentId(map);
		return new PageTerminal<EduTchEmployee>(list, pageSupport, eduTchEmployeeMapper.countListRecursionByDepartmentId(map));
	}

	@Override
	@Deprecated
	public PageTerminal<EduTchEmployee> listAll(PageSupport pageSupport) {
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_FIELD, defaultSortField);
		pageSupport.getParamVo().getMap().put(Constants.DEFAULT_SORT_ORDERBY, defaultSortOrderby);
		Map<String, Object> map = createProcResMap(pageSupport.getParamVo().getFlag(), pageSupport.getParamVo().getUsername(), pageSupport, defaultSortField + " " + defaultSortOrderby);
		List<EduTchEmployee> list = eduTchEmployeeMapper.queryByCondition(map);
		return new PageTerminal<EduTchEmployee>(list, pageSupport, eduTchEmployeeMapper.countByCondition(map));
	}
	
	/**
	 * 通过roleId 查找相应用户（教师）的id
	 * @param roleId
	 * @return
	 * @author RanWeizheng
	 */
	@Override
	 public List<Integer> getUserIdListByRoleId(Integer roleId){
		return userMapper.getUserIdListByRoleId(roleId);
	}

	@Override
	@Transactional
	public void importExcelTeacher(User user, EduTchEmployee eduTchEmployee,
			Map<String, Object> beanMap) {
		
		String departmentTitle = (String)beanMap.get("department_id");
		//1、部门是“本部退休”、“慧通超市”、“本部食堂”、“不在岗人员”、“南区食堂”、“东区食堂”、“长期病假” 的教职工不保存
		if(!"本部退休".equals(departmentTitle) && !"慧通超市".equals(departmentTitle) && !"本部食堂".equals(departmentTitle) && !"不在岗人员".equals(departmentTitle)
				 && !"南区食堂".equals(departmentTitle) && !"东区食堂".equals(departmentTitle) && !"餐饮中心".equals(departmentTitle) && !"长期病假".equals(departmentTitle)){
			
			UserExample example = new UserExample();
			example.createCriteria().andUserNoEqualTo(user.getUserNo());
			List<User> userList = userMapper.selectByExample(example);
			if(null != userList && userList.size()>0){
				
			}else{
				user.setStatus(Constants.BASE_STATUS_ONE);
				//新建“教职工”，保存密码
				user.setPassword("wGk4vks5sUGWr2IY4gIR6oXSgOUYskwGXVBjUsjIaebRNOfX+Ubs9brZw+uRbKTreVWRzaklSdR0L6pKmtJBCA==");
				user.setSalt("7JRJNgZnAOzPhSw2S0b3rw==");
				userMapper.insertSelective(user);
				eduTchEmployee.setUserId(user.getId());
				String Hanyu = user.getUsername();
				eduTchEmployee.setPinyin(PinYinUtil.getPingYin(Hanyu));
				eduTchEmployee.setJianpin(PinYinUtil.getPinYinHeadChar(Hanyu));
				
				// retire_flag 是否退休
				eduTchEmployee.setRetireFlag(0);
				// cert_flag 是否具备教师资格
				eduTchEmployee.setCertFlag(1);
				
				eduTchEmployee.setLastModifyUserId(user.getId());
				eduTchEmployee.setCreateDate(new Date());
				eduTchEmployee.setModifyDate(new Date());
				eduTchEmployee.setLogicDelete(Constants.BASE_LOGIC_DELETE_ZERO);
				
				eduTchEmployeeMapper.insertSelective(eduTchEmployee);
				
			}
			
		}
		
	}
	
	@Override
	@Transactional
	public void importExcelEmployee(User user, EduTchEmployee eduTchEmployee,Map<String, Object> beanMap) {
		
		UserExample example = new UserExample();
		example.createCriteria().andUserNoEqualTo(user.getUserNo());
		List<User> userList = userMapper.selectByExample(example);
		if(null != userList && userList.size()>0){
			
		}else{
			user.setStatus(Constants.BASE_STATUS_ONE);
			user.setLastModifyUserId(user.getId());
			user.setCreateDate(new Date());
			user.setModifyDate(new Date());
			user.setLogicDelete(Constants.BASE_LOGIC_DELETE_ZERO);
			//0：教师角色，1：学生角色
			user.setUserRole(Constants.BASE_LOGIC_DELETE_ZERO);
			//新建“教职工”，保存密码
			user.setPassword("wGk4vks5sUGWr2IY4gIR6oXSgOUYskwGXVBjUsjIaebRNOfX+Ubs9brZw+uRbKTreVWRzaklSdR0L6pKmtJBCA==");
			user.setSalt("7JRJNgZnAOzPhSw2S0b3rw==");
			
			userMapper.insertSelective(user);
			eduTchEmployee.setUserId(user.getId());
			String Hanyu = user.getUsername();
			eduTchEmployee.setPinyin(PinYinUtil.getPingYin(Hanyu));
			eduTchEmployee.setJianpin(PinYinUtil.getPinYinHeadChar(Hanyu));
			
			// retire_flag 是否退休
			eduTchEmployee.setRetireFlag(0);
			eduTchEmployee.setStatus(Constants.BASE_STATUS_ONE);
			eduTchEmployee.setLastModifyUserId(user.getId());
			eduTchEmployee.setCreateDate(new Date());
			eduTchEmployee.setModifyDate(new Date());
			eduTchEmployee.setLogicDelete(Constants.BASE_LOGIC_DELETE_ZERO);
			
			eduTchEmployeeMapper.insertSelective(eduTchEmployee);
			
		}
	}
}