package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduCommonMajor;
import com.shframework.modules.dict.entity.DictEduCommonMajorExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;
@MyBatisRepository
public interface DictEduCommonMajorMapper {
    int countByExample(DictEduCommonMajorExample example);

    int deleteByExample(DictEduCommonMajorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduCommonMajor record);

    int insertSelective(DictEduCommonMajor record);

    List<DictEduCommonMajor> selectByExample(DictEduCommonMajorExample example);

    DictEduCommonMajor selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduCommonMajor record, @Param("example") DictEduCommonMajorExample example);

    int updateByExample(@Param("record") DictEduCommonMajor record, @Param("example") DictEduCommonMajorExample example);

    int updateByPrimaryKeySelective(DictEduCommonMajor record);

    int updateByPrimaryKey(DictEduCommonMajor record);
    
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
    List<DictEduCommonMajor> selectByMap(Map<String, Object> parMap);
}