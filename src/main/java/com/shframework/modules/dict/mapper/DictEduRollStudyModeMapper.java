package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollStudyMode;
import com.shframework.modules.dict.entity.DictEduRollStudyModeExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollStudyModeMapper {
    int countByExample(DictEduRollStudyModeExample example);

    int deleteByExample(DictEduRollStudyModeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollStudyMode record);

    int insertSelective(DictEduRollStudyMode record);

    List<DictEduRollStudyMode> selectByExample(DictEduRollStudyModeExample example);

    DictEduRollStudyMode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollStudyMode record, @Param("example") DictEduRollStudyModeExample example);

    int updateByExample(@Param("record") DictEduRollStudyMode record, @Param("example") DictEduRollStudyModeExample example);

    int updateByPrimaryKeySelective(DictEduRollStudyMode record);

    int updateByPrimaryKey(DictEduRollStudyMode record);
}