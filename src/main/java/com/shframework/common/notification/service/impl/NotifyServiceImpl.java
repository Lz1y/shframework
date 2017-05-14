package com.shframework.common.notification.service.impl;

import static com.shframework.common.util.PageSupport.getDefaultCriteria;
import static com.shframework.common.util.ParamsUtil.createProcResMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shframework.common.notification.service.NotifyService;
import com.shframework.common.util.Constants;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.sys.entity.SysNotification;
import com.shframework.modules.sys.entity.SysNotificationExample;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.mapper.SysNotificationMapper;
import com.shframework.modules.sys.mapper.UserMapper;

@Service
public class NotifyServiceImpl implements NotifyService {

	private static final String defaultSortField = "create_date";
	private static final String defaultSortOrderby = "desc";
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SysNotificationMapper sysNotificationMapper;
	
	@Override
	public Map<String, Integer[]> unReadCount() {
		Map<String, Integer[]> map = new HashMap<String, Integer[]>();
		for (String item : userMapper.queryIdList()) {
			Integer[] list = sysNotificationMapper.unReadCount(item);
			if(list.length == 2) map.put(item, list);
		}
		return map;
	}
	
	@Override
	public PageTerminal<SysNotification> page(PageSupport pageSupport, Integer type, User user) {
		Map<String, Object> map = createProcResMap(pageSupport, defaultSortField + " " + defaultSortOrderby);
		map.put("receiveUserId", user.getId());
		map.put("type", type);
		return new PageTerminal<SysNotification>(sysNotificationMapper.list(map), pageSupport, sysNotificationMapper.count(map));
	}
	
	@Override
	public List<SysNotification> list(Integer type, User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receiveUserId", user.getId());
		map.put("type", type);
		return sysNotificationMapper.list(map);
	}
	
	@Override
	public int updates(User user, int flag) { // flag -> 1,已读 2,删除
		int count = 0;
		if (flag == 1) {
			count = sysNotificationMapper.readAll(user.getId());
		} else if (flag == 2) {
			count = sysNotificationMapper.deleteAll(user.getId());
		}
		return count;
	}

	@Override
	public int update(List<Integer> list, User user, int flag) { // 同上
		int count = 0;
		if (flag == 1) {
			count = sysNotificationMapper.batchread(list);
		} else if (flag == 2) {
			count = sysNotificationMapper.batchdelete(list);
		}
		return count;
	}

	@Override
	public int unreadCount(Integer flag, User user) {
		return sysNotificationMapper.unreadCount(flag, user.getId());
	}
	
	@Override
	public int read(Integer id) {
		return sysNotificationMapper.read(id);
	}
	
	@Override
	public PageTerminal<SysNotification> getNotifyByType(User user, Integer type) {
		SysNotificationExample ex = new SysNotificationExample();
		ex.createCriteria()
		.andTypeEqualTo(type)
		.andStatusEqualTo(Constants.READ_NO)
		.andLogicDeleteEqualTo(Constants.BASE_LOGIC_DELETE_ZERO)
		.andReceiveUserIdEqualTo(user.getId());
		List<SysNotification> list = sysNotificationMapper.selectByExample(ex);
		int count = sysNotificationMapper.countByExample(ex);
		return new PageTerminal<SysNotification>(list, getDefaultCriteria(), count);
	}

	@Override
	public PageTerminal<SysNotification> getNotify(User user) {
		SysNotificationExample ex = new SysNotificationExample();
		ex.createCriteria()
		.andStatusEqualTo(Constants.READ_NO)
		.andLogicDeleteEqualTo(Constants.BASE_LOGIC_DELETE_ZERO)
		.andReceiveUserIdEqualTo(user.getId());
		ex.setOrderByClause("create_date desc");
		List<SysNotification> list = sysNotificationMapper.selectByExample(ex);
		int count = sysNotificationMapper.countByExample(ex);
		return new PageTerminal<SysNotification>(list, getDefaultCriteria(), count);
	}
	
}
