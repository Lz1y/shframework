package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollGradation;
import com.shframework.modules.dict.entity.DictEduRollGradationExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollGradationMapper {
    int countByExample(DictEduRollGradationExample example);

    int deleteByExample(DictEduRollGradationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollGradation record);

    int insertSelective(DictEduRollGradation record);

    List<DictEduRollGradation> selectByExample(DictEduRollGradationExample example);

    DictEduRollGradation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollGradation record, @Param("example") DictEduRollGradationExample example);

    int updateByExample(@Param("record") DictEduRollGradation record, @Param("example") DictEduRollGradationExample example);

    int updateByPrimaryKeySelective(DictEduRollGradation record);

    int updateByPrimaryKey(DictEduRollGradation record);
}