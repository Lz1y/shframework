package com.shframework.modules.sys.entity.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.shframework.modules.sys.entity.Permission;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.Srrp;

public class SrrpVo implements Serializable {

	private static final long serialVersionUID = 5397920384514144066L;
	private List<Srrp> list;
	private List<Resource> rlist;
	private List<Permission> plist;

	private List<Integer> resIdList;
	private Map<String, ResPerVo> resPermissionMap;//rwz add 存放资源和权限的对应关系
	
	private Map<String, MergerPerVo> MergerPermissionMap;//rwz 存放合并权限信息的map
	
	public SrrpVo(){
		
	}
	public SrrpVo(List<Srrp> list, List<Resource> rlist, List<Permission> plist) {
		this.list = list;
		this.rlist = rlist;
		this.plist = plist;
	}

	public List<Srrp> getList() {
		return list;
	}

	public List<Resource> getRlist() {
		return rlist;
	}

	public List<Permission> getPlist() {
		return plist;
	}

	public void setList(List<Srrp> list) {
		this.list = list;
	}

	public void setRlist(List<Resource> rlist) {
		this.rlist = rlist;
	}

	public void setPlist(List<Permission> plist) {
		this.plist = plist;
	}
	
	public Map<String, ResPerVo> getResPermissionMap() {
		return resPermissionMap;
	}
	public void setResPermissionMap(Map<String, ResPerVo> resPermissionMap) {
		this.resPermissionMap = resPermissionMap;
	}
	public List<Integer> getResIdList() {
		return resIdList;
	}
	public void setResIdList(List<Integer> resIdList) {
		this.resIdList = resIdList;
	}
	public Map<String, MergerPerVo> getMergerPermissionMap() {
		return MergerPermissionMap;
	}
	public void setMergerPermissionMap(Map<String, MergerPerVo> mergerPermissionMap) {
		MergerPermissionMap = mergerPermissionMap;
	}
	
	
}
