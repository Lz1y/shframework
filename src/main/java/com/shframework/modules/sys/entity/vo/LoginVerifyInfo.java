package com.shframework.modules.sys.entity.vo;

import java.io.Serializable;

public class LoginVerifyInfo implements Serializable{

	private static final long serialVersionUID = -7213334272068959494L;

	private String verify;//表示MD5加密信息（32位加密）
	
	private String userName;//表示用户名（职工号）
	
	private String strSysDatetime;//表示由学校数据中心自动生成的时间戳
	
	private String jsName;//表示用户的角色（学生student 教师teacher 部门department）
	
	private String openType;//表示系统的集成方式，1表示在框架中打开，2表示在新窗口打开
	
	private String url;//表示认证通过后直接指向当前的地址，可以为空
	
	private String gnmkdm;//表示认证通过时指向到当前功能模块代码的对应页面（需要进行权限验证）（可以为空）
	
	private String strKey;

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStrSysDatetime() {
		return strSysDatetime;
	}

	public void setStrSysDatetime(String strSysDatetime) {
		this.strSysDatetime = strSysDatetime;
	}

	public String getJsName() {
		return jsName;
	}

	public void setJsName(String jsName) {
		this.jsName = jsName;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGnmkdm() {
		return gnmkdm;
	}

	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}

	public String getStrKey() {
		return strKey;
	}

	public void setStrKey(String strKey) {
		this.strKey = strKey;
	}
}