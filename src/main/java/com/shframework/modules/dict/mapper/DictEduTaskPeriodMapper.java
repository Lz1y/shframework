package com.shframework.modules.dict.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictEduTaskPeriod;
import com.shframework.modules.dict.entity.DictEduTaskPeriodExample;

@MyBatisRepository
public interface DictEduTaskPeriodMapper {
    int countByExample(DictEduTaskPeriodExample example);

    int deleteByExample(DictEduTaskPeriodExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduTaskPeriod record);

    int insertSelective(DictEduTaskPeriod record);

    List<DictEduTaskPeriod> selectByExample(DictEduTaskPeriodExample example);

    DictEduTaskPeriod selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduTaskPeriod record, @Param("example") DictEduTaskPeriodExample example);

    int updateByExample(@Param("record") DictEduTaskPeriod record, @Param("example") DictEduTaskPeriodExample example);

    int updateByPrimaryKeySelective(DictEduTaskPeriod record);

    int updateByPrimaryKey(DictEduTaskPeriod record);
    
    int countByMap(Map<String, Object> parMap);
    
    List<DictEduTaskPeriod> selectByMap(Map<String, Object> parMap);

    List<DictEduTaskPeriod> queryAll();

    
}