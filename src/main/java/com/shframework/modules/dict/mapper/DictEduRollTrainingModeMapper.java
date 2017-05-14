package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollTrainingMode;
import com.shframework.modules.dict.entity.DictEduRollTrainingModeExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollTrainingModeMapper {
    int countByExample(DictEduRollTrainingModeExample example);

    int deleteByExample(DictEduRollTrainingModeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollTrainingMode record);

    int insertSelective(DictEduRollTrainingMode record);

    List<DictEduRollTrainingMode> selectByExample(DictEduRollTrainingModeExample example);

    DictEduRollTrainingMode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollTrainingMode record, @Param("example") DictEduRollTrainingModeExample example);

    int updateByExample(@Param("record") DictEduRollTrainingMode record, @Param("example") DictEduRollTrainingModeExample example);

    int updateByPrimaryKeySelective(DictEduRollTrainingMode record);

    int updateByPrimaryKey(DictEduRollTrainingMode record);
}