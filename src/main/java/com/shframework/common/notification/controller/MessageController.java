package com.shframework.common.notification.controller;

import static com.shframework.common.mq.Sender.confProducerAndSendMessage;
import static com.shframework.common.mq.Sender.notify2xml;
import static com.shframework.common.util.PageSupport.getDefaultCriteria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.util.Constants;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.sys.entity.SysNotification;
import com.shframework.modules.sys.entity.SysNotificationExample;
import com.shframework.modules.sys.mapper.SysNotificationMapper;

@Controller
public class MessageController extends BaseComponent {
	
	@Autowired
	private SysNotificationMapper sysNotificationMapper;

	@RequestMapping(value ="message/send")
	public void sendMessage(SysNotification notify) throws Exception {
		sysNotificationMapper.insertSelective(notify);
		confProducerAndSendMessage(notify2xml(notify));
	}
	
	@RequestMapping(value = "message/list")
	public @ResponseBody PageTerminal<SysNotification> queryMesssage() {
		SysNotificationExample ex = new SysNotificationExample();
		ex.createCriteria().andContentIsNotNull().andLogicDeleteEqualTo(Constants.BASE_LOGIC_DELETE_ZERO).andReceiveUserIdEqualTo(getSessionUser().getId());
		List<SysNotification> list = sysNotificationMapper.selectByExample(ex);
		int count = sysNotificationMapper.countByExample(ex);
		return new PageTerminal<SysNotification>(list, getDefaultCriteria(), count);
	}
	
	@RequestMapping(value = "message/update")
	public @ResponseBody int udtMessage(int id) {
		return sysNotificationMapper.read(id);
	}

	@RequestMapping(value = "message/update/all")
	public @ResponseBody int udtMessage() {
		return sysNotificationMapper.readAll(1);
	}
	
}
