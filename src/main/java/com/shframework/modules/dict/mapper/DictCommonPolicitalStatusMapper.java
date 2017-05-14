package com.shframework.modules.dict.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictCommonPolicitalStatus;
import com.shframework.modules.dict.entity.DictCommonPolicitalStatusExample;
@MyBatisRepository
public interface DictCommonPolicitalStatusMapper {
    int countByExample(DictCommonPolicitalStatusExample example);

    int deleteByExample(DictCommonPolicitalStatusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictCommonPolicitalStatus record);

    int insertSelective(DictCommonPolicitalStatus record);

    List<DictCommonPolicitalStatus> selectByExample(DictCommonPolicitalStatusExample example);

    DictCommonPolicitalStatus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictCommonPolicitalStatus record, @Param("example") DictCommonPolicitalStatusExample example);

    int updateByExample(@Param("record") DictCommonPolicitalStatus record, @Param("example") DictCommonPolicitalStatusExample example);

    int updateByPrimaryKeySelective(DictCommonPolicitalStatus record);

    int updateByPrimaryKey(DictCommonPolicitalStatus record);
    
    /**
     * 根据查询条件map，查找符合条件的记录数量
     * @param parMap 
     * {
     * String tableName;
     * String searchCondition;
     * int limitStart;
     * int limitEnd;
     * }
     * @return
     * @author RanWeizheng
     */
    int countByMap(Map<String, Object> parMap);
    
    /**
     * 根据查询条件map，查找符合条件的记录
     * @param parMap 
     * {
     * String tableName;
     * String searchCondition;
     * int limitStart;
     * int limitEnd;
     * }
     * @return
     * @author RanWeizheng
     */
    List<DictCommonPolicitalStatus> selectByMap(Map<String, Object> parMap);

	List<HashMap<String, Object>> selectPolicitalStatus();
}