package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollTrainingLevel;
import com.shframework.modules.dict.entity.DictEduRollTrainingLevelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollTrainingLevelMapper {
    int countByExample(DictEduRollTrainingLevelExample example);

    int deleteByExample(DictEduRollTrainingLevelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollTrainingLevel record);

    int insertSelective(DictEduRollTrainingLevel record);

    List<DictEduRollTrainingLevel> selectByExample(DictEduRollTrainingLevelExample example);

    DictEduRollTrainingLevel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollTrainingLevel record, @Param("example") DictEduRollTrainingLevelExample example);

    int updateByExample(@Param("record") DictEduRollTrainingLevel record, @Param("example") DictEduRollTrainingLevelExample example);

    int updateByPrimaryKeySelective(DictEduRollTrainingLevel record);

    int updateByPrimaryKey(DictEduRollTrainingLevel record);
}