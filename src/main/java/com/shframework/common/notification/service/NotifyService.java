package com.shframework.common.notification.service;

import java.util.List;
import java.util.Map;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.sys.entity.SysNotification;
import com.shframework.modules.sys.entity.User;

public interface NotifyService {

	PageTerminal<SysNotification> page(PageSupport pageSupport, Integer type, User user);

	int updates(User user, int flag);

	int update(List<Integer> list, User user, int flag);

	int unreadCount(Integer type, User sessionUser);

	int read(Integer id);

	PageTerminal<SysNotification> getNotifyByType(User user, Integer type);

	PageTerminal<SysNotification> getNotify(User user);

	List<SysNotification> list(Integer type, User user);

	Map<String, Integer[]> unReadCount();

}
