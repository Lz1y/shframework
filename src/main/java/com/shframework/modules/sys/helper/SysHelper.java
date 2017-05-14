package com.shframework.modules.sys.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shframework.common.base.BaseComponent;
import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.entity.vo.ShiroPermissionVo;
import com.shframework.modules.sys.mapper.SrrpMapper;
import com.shframework.modules.sys.mapper.UserMapper;

@Component
public class SysHelper extends BaseComponent {
	
	@Autowired
	private SrrpMapper srrpMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	public boolean execute(String sql) {
		return srrpMapper.execute(sql) > 0;
	}
	
	public List<String> plist(Map<String, Object> map) {
		List<String> list = new ArrayList<String>();
		for (ShiroPermissionVo item : srrpMapper.permissions(map)) 
			list.add(this.getPermission(item, (User) map.get("user")));
		
		return list;
	}
	
	public Map<String, List<String>> permissions() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Map<String, Object> param = new HashMap<String, Object>();
		for (User user : userMapper.passwordIsNotNull()) {
			
			param.put("userId", user.getId());
			List<String> plist = new ArrayList<String>();
			for (ShiroPermissionVo item : srrpMapper.permissions(param)) {
				plist.add(this.getPermission(item, user));
			}
			map.put(user.getId().toString(), plist);
		}
		
		return map;
	}

	private String getPermission(ShiroPermissionVo spv, User user) {
		StringBuffer pms = new StringBuffer();
		if (containKeys(spv.getSpCode())) {
			
			pms.append(spv.getRule()).append(":").append(spv.getScope()).append(":");
			switch (spv.getScope()) {
			case 0: // 无
				pms.append("*");
				break;
			case 1: // 分校
				pms.append(user.getCampus().getId());
				break;
			case 2: // 总校
				pms.append("*");
				break;
			case 3: // 单系
				pms.append(user.getEmployee().getDepartmentId());
				break;
			case 4: // 总系
				pms.append("*");
				break;
			case 5: // 个人数据
				pms.append(user.getId());
				break;
			}
		
		} else {
			pms.append("*:*:*");
		}

		return pms.append(":*:" + spv.getSpCode()).toString();
	}

	public static boolean containKeys(String pCode) {
		return list.contains(pCode);
	}
	//approve 审核 add by zhangjk
	private static List<String> list = Arrays.asList("add","edit","deliver","submit", "dispatch", "delete", "back", "register","approval","approve","apply","reject","pass");// rwz add-action  'add' 'approval'  161012
	
}
