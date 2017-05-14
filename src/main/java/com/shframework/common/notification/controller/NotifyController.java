package com.shframework.common.notification.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.shframework.common.base.BaseComponent;
import com.shframework.common.notification.service.NotifyService;
import com.shframework.common.support.PageParam;
import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.sys.entity.SysNotification;

@Controller
@RequestMapping(value = "notification")
public class NotifyController extends BaseComponent {

	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@RequestMapping(value = "{type}/list")
	public String page(@PathVariable("type")Integer type, @PageParam PageSupport pageSupport, Model model) {
		model.addAttribute("page", notifyService.page(pageSupport, type, getSessionUser()));
		model.addAttribute("unreadCount", notifyService.unreadCount(type, getSessionUser()));
		if (type.equals(1)) return "comn/notifycation/sys/list";
		else return "comn/notifycation/user/list";
	}
	
	@RequestMapping(value = "directaccess")
	public @ResponseBody Integer[] count() {
		return (Integer[]) ((Map<String, Integer[]>)(cacheComponent.resource(CacheComponent.KEY_NOTIFYUNREAD))).get(getSessionUser().getId().toString());
	}

	@RequestMapping(value = "{type}")
	public @ResponseBody PageTerminal<SysNotification> getNotifyByType(@PathVariable("type")Integer type) {
		return notifyService.getNotifyByType(getSessionUser(), type);
	}
	
	@RequestMapping(value = "list")
	public @ResponseBody PageTerminal<SysNotification> getNotify() {
		return notifyService.getNotify(getSessionUser());
	}
	
	@RequestMapping(value = "{type}/batchdelete")
	public String nDelete(@PathVariable("type")Integer type, @RequestParam("ntype")Integer ntype, @RequestParam("cbkfield")List<Integer> list, Model model) {
		Map<String, Integer[]> map = (Map<String, Integer[]>)(cacheComponent.resource(CacheComponent.KEY_NOTIFYUNREAD));
		Integer[] num = map.get(getSessionUser().getId().toString());
		if(num.length == 2) {
			num[ntype - 1] = num[ntype - 1] - list.size();
		}
		map.put(getSessionUser().getId().toString(), num);
		cacheComponent.update(CacheComponent.KEY_NOTIFYUNREAD, map);
		model.addAttribute("resultCount", notifyService.update(list, getSessionUser(), 2));
		return "redirect:/notification/" + ntype + "/list";
	}
	
	@RequestMapping(value = "{type}/batchread")
	public String nRead(@PathVariable("type")Integer type, @RequestParam("ntype")Integer ntype, @RequestParam("cbkfield")List<Integer> list, Model model) {
		Map<String, Integer[]> map = (Map<String, Integer[]>)(cacheComponent.resource(CacheComponent.KEY_NOTIFYUNREAD));
		Integer[] num = map.get(getSessionUser().getId().toString());
		if(num.length == 2) {
			num[ntype - 1] = num[ntype - 1] - list.size();
		}
		map.put(getSessionUser().getId().toString(), num);
		cacheComponent.update(CacheComponent.KEY_NOTIFYUNREAD, map);
		model.addAttribute("resultCount", notifyService.update(list, getSessionUser(), 1));
		return "redirect:/notification/" + ntype + "/list";
	}
	
	@RequestMapping(value = "all/read")
	public String nReads(@RequestParam("ntype")Integer ntype, Model model) {
		Map<String, Integer[]> map = (Map<String, Integer[]>)(cacheComponent.resource(CacheComponent.KEY_NOTIFYUNREAD));
		Integer[] num = map.get(getSessionUser().getId().toString());
		if(num.length == 2) {
			num[ntype - 1] = 0;
		}
		map.put(getSessionUser().getId().toString(), num);
		cacheComponent.update(CacheComponent.KEY_NOTIFYUNREAD, map);
		model.addAttribute("resultCount", notifyService.updates(getSessionUser(), 1));
		return "redirect:/notification/" + ntype + "/list";
	}

	@RequestMapping(value = "all/delete")
	public String nDeletes(@RequestParam("ntype")Integer ntype, Model model) {
		Map<String, Integer[]> map = (Map<String, Integer[]>)(cacheComponent.resource(CacheComponent.KEY_NOTIFYUNREAD));
		Integer[] num = map.get(getSessionUser().getId().toString());
		if(num.length == 2) {
			num[ntype - 1] = 0;
		}
		map.put(getSessionUser().getId().toString(), num);
		cacheComponent.update(CacheComponent.KEY_NOTIFYUNREAD, map);
		model.addAttribute("resultCount", notifyService.updates(getSessionUser(), 2));
		return "redirect:/notification/" + ntype + "/list";
	}

	@RequestMapping(value = "{id}/read")
	public String read(@PathVariable("id")Integer id, @RequestParam("ntype")Integer ntype, Model model) {
		Map<String, Integer[]> map = (Map<String, Integer[]>)(cacheComponent.resource(CacheComponent.KEY_NOTIFYUNREAD));
		Integer[] num = map.get(getSessionUser().getId().toString());
		if(num.length == 2) {
			num[ntype - 1] = num[ntype - 1] - 1;
		}
		map.put(getSessionUser().getId().toString(), num);
		cacheComponent.update(CacheComponent.KEY_NOTIFYUNREAD, map);
		model.addAttribute("resultCount", notifyService.read(id));
		return "redirect:/notification/" + ntype + "/list";
	}

}
