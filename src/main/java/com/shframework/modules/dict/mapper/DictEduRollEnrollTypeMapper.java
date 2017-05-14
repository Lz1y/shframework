package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollEnrollType;
import com.shframework.modules.dict.entity.DictEduRollEnrollTypeExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollEnrollTypeMapper {
    int countByExample(DictEduRollEnrollTypeExample example);

    int deleteByExample(DictEduRollEnrollTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollEnrollType record);

    int insertSelective(DictEduRollEnrollType record);

    List<DictEduRollEnrollType> selectByExample(DictEduRollEnrollTypeExample example);

    DictEduRollEnrollType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollEnrollType record, @Param("example") DictEduRollEnrollTypeExample example);

    int updateByExample(@Param("record") DictEduRollEnrollType record, @Param("example") DictEduRollEnrollTypeExample example);

    int updateByPrimaryKeySelective(DictEduRollEnrollType record);

    int updateByPrimaryKey(DictEduRollEnrollType record);
}