package com.shframework.modules.sys.mapper;

import com.shframework.modules.sys.entity.Resource;
import com.shframework.modules.sys.entity.ResourceExample;
import com.shframework.modules.sys.entity.Role;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface ResourceMapper {
    int countByExample(ResourceExample example);

    int deleteByExample(ResourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    List<Resource> selectByExample(ResourceExample example);

    Resource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByExample(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
    
    Resource selectByRule(String rule);
	List<Resource> selectByParentId(Integer parentId);
	
    List<String> queryPmstr(int id);
    
    String queryPmsTitle(String code);
    
    List<Resource> allResource();
    
    List<Resource> res_role(Integer id);
    
    @Select("select rule from sys_resource where rule <> '*'")
    List<String> findRule();
    
    /**
	 * 通过用户拥有的角色的列表，获取相对于该用户活动的资源的id 的列表
	 * @param userRoleList
	 * @return
	 * @author RanWeizheng
	 */
	public List<Integer> getActiveResourceIdList(@Param("userRoleList")List<Role> userRoleList);
	
	/**
	 * 查询符合条件的资源
	 * @param parMap
	 * @return
	 * @author RanWeizheng
	 */
	public List<Resource> queryResList(Map<String, Object> parMap);
	
	/**
	 * 获得符合条件的资源的数量
	 * @param parMap
	 * @return
	 * @author RanWeizheng
	 */
	public int queryResListCount(Map<String, Object> parMap);
	
	/**
	 * 比较库之间的数据用，不要提交！
	 * @return
	 * @author RanWeizheng
	 * @date 2016年5月17日 下午6:28:45
	 */
	public List<Resource> queryAllTempRes();
	
	List<Resource> userResRole(Map<String, Object> parMap);
}