package com.shframework.modules.dict.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictEduClrBuilding;
import com.shframework.modules.dict.entity.DictEduClrBuildingExample;

@MyBatisRepository
public interface DictEduClrBuildingMapper {
    int countByExample(DictEduClrBuildingExample example);

    int deleteByExample(DictEduClrBuildingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduClrBuilding record);

    int insertSelective(DictEduClrBuilding record);

    List<DictEduClrBuilding> selectByExample(DictEduClrBuildingExample example);

    DictEduClrBuilding selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduClrBuilding record, @Param("example") DictEduClrBuildingExample example);

    int updateByExample(@Param("record") DictEduClrBuilding record, @Param("example") DictEduClrBuildingExample example);

    int updateByPrimaryKeySelective(DictEduClrBuilding record);

    int updateByPrimaryKey(DictEduClrBuilding record);
    /**
     * 根据查询条件map，查找符合条件的记录数量
     * @param parMap 
     * {
     * String tableName;
     * String searchCondition;
     * int limitStart;
     * int limitEnd;
     * }
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
     */
    List<DictEduClrBuilding> selectByMap(Map<String, Object> parMap);
}