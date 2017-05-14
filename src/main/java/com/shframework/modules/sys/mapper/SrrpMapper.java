package com.shframework.modules.sys.mapper;

import com.shframework.modules.sys.entity.Srrp;
import com.shframework.modules.sys.entity.SrrpExample;
import com.shframework.modules.sys.entity.SrrpKey;
import com.shframework.modules.sys.entity.vo.ShiroPermissionVo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface SrrpMapper {
	
    int countByExample(SrrpExample example);

    int deleteByExample(SrrpExample example);

    int deleteByPrimaryKey(SrrpKey key);

    int insert(Srrp record);

    int insertSelective(Srrp record);

    List<Srrp> selectByExample(SrrpExample example);

    Srrp selectByPrimaryKey(SrrpKey key);

    int updateByExampleSelective(@Param("record") Srrp record, @Param("example") SrrpExample example);

    int updateByExample(@Param("record") Srrp record, @Param("example") SrrpExample example);

    int updateByPrimaryKeySelective(Srrp record);

    int updateByPrimaryKey(Srrp record);
    
    List<Srrp> querySrrpByRoleId(int roleid);
    /**
     * 根据角色id以及资源list 获取相关的权限
     * @author RanWeizheng
     * @param roleid
     * @param resIdList
     * @return
     */
    List<Srrp> querySrrpByRoleIdAndResIdList(@Param("roleid") int roleid, @Param("resIdList") List<Integer> resIdList);
    
    List<ShiroPermissionVo> getResourceScopeByUsername(String username);
    
    List<ShiroPermissionVo> getPermissionsByUsername(String username);
    
    List<ShiroPermissionVo> permissions(Map<String, Object> map);
    
    List<String> getRulesByUsername(String username);
    
    Integer getScopeByRoleAndResource(Map<String, Object> map);
    
    Integer execute(String sql);
}