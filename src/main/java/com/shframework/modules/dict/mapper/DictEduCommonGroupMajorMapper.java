package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduCommonGroupMajor;
import com.shframework.modules.dict.entity.DictEduCommonGroupMajorExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduCommonGroupMajorMapper {
    int countByExample(DictEduCommonGroupMajorExample example);

    int deleteByExample(DictEduCommonGroupMajorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduCommonGroupMajor record);

    int insertSelective(DictEduCommonGroupMajor record);

    List<DictEduCommonGroupMajor> selectByExample(DictEduCommonGroupMajorExample example);

    DictEduCommonGroupMajor selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduCommonGroupMajor record, @Param("example") DictEduCommonGroupMajorExample example);

    int updateByExample(@Param("record") DictEduCommonGroupMajor record, @Param("example") DictEduCommonGroupMajorExample example);

    int updateByPrimaryKeySelective(DictEduCommonGroupMajor record);

    int updateByPrimaryKey(DictEduCommonGroupMajor record);
    
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
    List<DictEduCommonGroupMajor> selectByMap(Map<String, Object> parMap);
}