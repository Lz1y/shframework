package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollLocal;
import com.shframework.modules.dict.entity.DictEduRollLocalExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollLocalMapper {
    int countByExample(DictEduRollLocalExample example);

    int deleteByExample(DictEduRollLocalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollLocal record);

    int insertSelective(DictEduRollLocal record);

    List<DictEduRollLocal> selectByExample(DictEduRollLocalExample example);

    DictEduRollLocal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollLocal record, @Param("example") DictEduRollLocalExample example);

    int updateByExample(@Param("record") DictEduRollLocal record, @Param("example") DictEduRollLocalExample example);

    int updateByPrimaryKeySelective(DictEduRollLocal record);

    int updateByPrimaryKey(DictEduRollLocal record);
}