package com.shframework.common.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.shframework.common.util.Constants;
import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.User;

@Component
//rRule:scope:scopeId:id:pCode
//@RequestMapping(value = "{rule}/{scope}/{scopeId}/{id}/{pCode}")
public class BaseComponent {
	
	public static Map<Integer, String> sessions = new HashMap<Integer, String>();
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	@Autowired
	private SessionDAO sessionDAO;

	private JedisPool jedisPool;
	@Autowired
	private JedisPoolConfig jedisPoolConfig;
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;
	
	public Jedis getJedis() {
		if (jedisPool == null) jedisPool = new JedisPool(jedisPoolConfig, jedisConnectionFactory.getHostName(), jedisConnectionFactory.getPort(), 5000);
		return jedisPool.getResource();
	}
	
	public void returnJedis(Jedis jedis) {
		if (jedis != null) jedisPool.returnResource(jedis);
	}

	
	protected Collection<Session> list() {
		return sessionDAO.getActiveSessions();
	}
	
	protected Serializable create(Session session){
		return sessionDAO.create(session);
	}
	
	protected void forceLogout(Integer userId) {
		String sessionId = sessions.get(userId);
		Session session = null;
		if(sessionId != null) try {session = sessionDAO.readSession(sessionId);} catch (UnknownSessionException e) {sessions.remove(userId);}
		if(session != null) session.setAttribute(Constants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
	}
	
	protected void forceLogout(List<Integer> userIds) {
		for (Integer userId : userIds) forceLogout(userId);
	}

	public synchronized void setServlet(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		this.request = request;
		this.response = response;
		this.session = session;
	}

	protected User getSessionUser() {
		return (User) session.getAttribute("curUser");
	}
	
	protected Resource getResourse() {
		return (Resource) request.getAttribute(Constants.COMMON_DEFINED_CURMENU);
	}

	public void writeCookie(String cKey, String cValue) {
		Cookie cookie = new Cookie(cKey, new String(Base64.encodeBase64(cValue.getBytes())));
	    cookie.setPath("/");
	    cookie.setMaxAge(Constants.COOKIE_BASE_LIFECYCLE);
	    response.addCookie(cookie);
	}
	
	public void writeCookie(String cKey, String cValue, int maxAge) {
		Cookie cookie = new Cookie(cKey, new String(Base64.encodeBase64(cValue.getBytes())));
	    cookie.setPath("/");
	    if(maxAge > 0) cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}

	public void removeCookie(String cKey) {
		Cookie cookie = new Cookie(cKey, null);
	    cookie.setPath("/");
	    cookie.setMaxAge(-1);
	    response.addCookie(cookie);
	}
	
	public String readBase64Cookie(String cName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) 
			for (int i = 0; i < cookies.length; i++) 
				if (StringUtils.equals(cName, cookies[i].getName())) 
					return new String(Base64.decodeBase64(cookies[i].getValue().getBytes()));
		
		return null;
	}

	public String readCookie(String cName){
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) 
			for (int i = 0; i < cookies.length; i++) 
				if (StringUtils.equals(cName, cookies[i].getName())) 
					return cookies[i].getValue();
		
		return null;
	}

	public void del(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(key);
		} finally {
			returnJedis(jedis);
		}
	}
	
	public void flushDB() {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.flushDB();
		} finally {
			returnJedis(jedis);
		}
	}

}
