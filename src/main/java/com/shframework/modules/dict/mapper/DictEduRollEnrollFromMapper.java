package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollEnrollFrom;
import com.shframework.modules.dict.entity.DictEduRollEnrollFromExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollEnrollFromMapper {
    int countByExample(DictEduRollEnrollFromExample example);

    int deleteByExample(DictEduRollEnrollFromExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollEnrollFrom record);

    int insertSelective(DictEduRollEnrollFrom record);

    List<DictEduRollEnrollFrom> selectByExample(DictEduRollEnrollFromExample example);

    DictEduRollEnrollFrom selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollEnrollFrom record, @Param("example") DictEduRollEnrollFromExample example);

    int updateByExample(@Param("record") DictEduRollEnrollFrom record, @Param("example") DictEduRollEnrollFromExample example);

    int updateByPrimaryKeySelective(DictEduRollEnrollFrom record);

    int updateByPrimaryKey(DictEduRollEnrollFrom record);
}