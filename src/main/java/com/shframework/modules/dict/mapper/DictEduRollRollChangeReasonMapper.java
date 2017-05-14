package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollRollChangeReason;
import com.shframework.modules.dict.entity.DictEduRollRollChangeReasonExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollRollChangeReasonMapper {
    int countByExample(DictEduRollRollChangeReasonExample example);

    int deleteByExample(DictEduRollRollChangeReasonExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollRollChangeReason record);

    int insertSelective(DictEduRollRollChangeReason record);

    List<DictEduRollRollChangeReason> selectByExample(DictEduRollRollChangeReasonExample example);

    DictEduRollRollChangeReason selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollRollChangeReason record, @Param("example") DictEduRollRollChangeReasonExample example);

    int updateByExample(@Param("record") DictEduRollRollChangeReason record, @Param("example") DictEduRollRollChangeReasonExample example);

    int updateByPrimaryKeySelective(DictEduRollRollChangeReason record);

    int updateByPrimaryKey(DictEduRollRollChangeReason record);
}