package com.shframework.modules.dict.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictEduCommonCampus;
import com.shframework.modules.dict.entity.DictEduCommonCampusExample;

@MyBatisRepository
public interface DictEduCommonCampusMapper {
    int countByExample(DictEduCommonCampusExample example);

    int deleteByExample(DictEduCommonCampusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduCommonCampus record);

    int insertSelective(DictEduCommonCampus record);

    List<DictEduCommonCampus> selectByExample(DictEduCommonCampusExample example);

    DictEduCommonCampus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduCommonCampus record, @Param("example") DictEduCommonCampusExample example);

    int updateByExample(@Param("record") DictEduCommonCampus record, @Param("example") DictEduCommonCampusExample example);

    int updateByPrimaryKeySelective(DictEduCommonCampus record);

    int updateByPrimaryKey(DictEduCommonCampus record);
    
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
    List<DictEduCommonCampus> selectByMap(Map<String, Object> parMap);

	List<DictEduCommonCampus> getCampusAll();

	List<HashMap<String, Object>> selectCampus();
}