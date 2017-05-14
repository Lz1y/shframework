package com.shframework.common.auth.realm;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.shframework.common.util.Constants;
import com.shframework.common.util.EncryptUtils;
import com.shframework.common.util.EncryptionUtil;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.sys.entity.vo.SaltVo;
import com.shframework.modules.sys.mapper.RoleMapper;
import com.shframework.modules.sys.mapper.UserMapper;

@PropertySource("classpath:shiro/shiro.properties")
public class JdbcRealm extends AuthorizingRealm {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private CredentialsMatcher credentialsMatcher;
	
	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@Autowired
	private Environment env;
	
	@Override
	public CredentialsMatcher getCredentialsMatcher() {
		return credentialsMatcher;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		this.credentialsMatcher = credentialsMatcher;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo sa = new SimpleAuthorizationInfo();

		String username = super.getAvailablePrincipal(principals).toString();
		
		String id = userMapper.getId(username);
		sa.addStringPermissions(((Map<String, List<String>>) cacheComponent.resource(CacheComponent.KEY_PERMISSIONS)).get(id));
		sa.addRoles(roleMapper.queryRoleCodeListByUserId(id));
		
		return sa;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        
        String username = upToken.getUsername();
        
        super.setCredentialsMatcher(null);
        
        if (username == null) throw new AccountException("Null usernames are not allowed by this realm.");
		
        SimpleAuthenticationInfo info = null;
        
        Session session = SecurityUtils.getSubject().getSession();
        int loginType = Integer.valueOf(session.getAttribute(Constants.SYS_LOGIN_TYPE).toString()).intValue();
        try {
            String password = null;
            String salt = null;

            if(loginType == Constants.SYS_LOGIN_TYPE_DEFAULT){
	    		String id = userMapper.getId(username);
	            SaltVo saltVo = userMapper.queryPwdAndSalt(id);
	            password = saltVo.getPassword();
	            salt = saltVo.getSalt();
            }else if(loginType == Constants.SYS_LOGIN_TYPE_SSO){
            	salt = getCombinedSalt(getSalt());
            	password = EncryptUtils.encryptSHA512(String.valueOf(upToken.getPassword()), salt, getIterations());
            }

            if (password == null) throw new UnknownAccountException("No account found for user [" + username + "]");
            
            info = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());
            
            if (salt != null) info.setCredentialsSalt(ByteSource.Util.bytes(salt));
        } catch (Exception e) {
            final String message = "There was a SQL error while authenticating user [" + username + "]";
            throw new AuthenticationException(message, e);
        }

        return info;
	}
	
	@Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals){
		super.clearCachedAuthorizationInfo(principals);
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
}