package com.shframework.modules.dict.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictEduSkdCourseMode;
import com.shframework.modules.dict.entity.DictEduSkdCourseModeExample;

@MyBatisRepository
public interface DictEduSkdCourseModeMapper {
    int countByExample(DictEduSkdCourseModeExample example);

    int deleteByExample(DictEduSkdCourseModeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduSkdCourseMode record);

    int insertSelective(DictEduSkdCourseMode record);

    List<DictEduSkdCourseMode> selectByExample(DictEduSkdCourseModeExample example);

    DictEduSkdCourseMode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduSkdCourseMode record, @Param("example") DictEduSkdCourseModeExample example);

    int updateByExample(@Param("record") DictEduSkdCourseMode record, @Param("example") DictEduSkdCourseModeExample example);

    int updateByPrimaryKeySelective(DictEduSkdCourseMode record);

    int updateByPrimaryKey(DictEduSkdCourseMode record);
    
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
    List<DictEduSkdCourseMode> selectByMap(Map<String, Object> parMap);
}