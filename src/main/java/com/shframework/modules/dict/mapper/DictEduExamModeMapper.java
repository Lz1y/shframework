package com.shframework.modules.dict.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictEduExamMode;
import com.shframework.modules.dict.entity.DictEduExamModeExample;

@MyBatisRepository
public interface DictEduExamModeMapper {
    int countByExample(DictEduExamModeExample example);

    int deleteByExample(DictEduExamModeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduExamMode record);

    int insertSelective(DictEduExamMode record);

    List<DictEduExamMode> selectByExample(DictEduExamModeExample example);

    DictEduExamMode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduExamMode record, @Param("example") DictEduExamModeExample example);

    int updateByExample(@Param("record") DictEduExamMode record, @Param("example") DictEduExamModeExample example);

    int updateByPrimaryKeySelective(DictEduExamMode record);

    int updateByPrimaryKey(DictEduExamMode record);
    
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
    List<DictEduExamMode> selectByMap(Map<String, Object> parMap);
}