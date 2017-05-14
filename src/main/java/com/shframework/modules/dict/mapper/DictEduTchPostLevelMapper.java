package com.shframework.modules.dict.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictEduTchPostLevel;
import com.shframework.modules.dict.entity.DictEduTchPostLevelExample;

@MyBatisRepository
public interface DictEduTchPostLevelMapper {
    int countByExample(DictEduTchPostLevelExample example);

    int deleteByExample(DictEduTchPostLevelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduTchPostLevel record);

    int insertSelective(DictEduTchPostLevel record);

    List<DictEduTchPostLevel> selectByExample(DictEduTchPostLevelExample example);

    DictEduTchPostLevel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduTchPostLevel record, @Param("example") DictEduTchPostLevelExample example);

    int updateByExample(@Param("record") DictEduTchPostLevel record, @Param("example") DictEduTchPostLevelExample example);

    int updateByPrimaryKeySelective(DictEduTchPostLevel record);

    int updateByPrimaryKey(DictEduTchPostLevel record);
    
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
    List<DictEduTchPostLevel> selectByMap(Map<String, Object> parMap);
}