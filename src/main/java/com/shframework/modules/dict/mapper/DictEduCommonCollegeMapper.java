package com.shframework.modules.dict.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictEduCommonCollege;
import com.shframework.modules.dict.entity.DictEduCommonCollegeExample;

@MyBatisRepository
public interface DictEduCommonCollegeMapper {
    int countByExample(DictEduCommonCollegeExample example);

    int deleteByExample(DictEduCommonCollegeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduCommonCollege record);

    int insertSelective(DictEduCommonCollege record);

    List<DictEduCommonCollege> selectByExample(DictEduCommonCollegeExample example);

    DictEduCommonCollege selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduCommonCollege record, @Param("example") DictEduCommonCollegeExample example);

    int updateByExample(@Param("record") DictEduCommonCollege record, @Param("example") DictEduCommonCollegeExample example);

    int updateByPrimaryKeySelective(DictEduCommonCollege record);

    int updateByPrimaryKey(DictEduCommonCollege record);
    
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
    List<DictEduCommonCollege> selectByMap(Map<String, Object> parMap);
}