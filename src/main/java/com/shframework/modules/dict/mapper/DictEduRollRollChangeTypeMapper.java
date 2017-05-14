package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollRollChangeType;
import com.shframework.modules.dict.entity.DictEduRollRollChangeTypeExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollRollChangeTypeMapper {
    int countByExample(DictEduRollRollChangeTypeExample example);

    int deleteByExample(DictEduRollRollChangeTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollRollChangeType record);

    int insertSelective(DictEduRollRollChangeType record);

    List<DictEduRollRollChangeType> selectByExample(DictEduRollRollChangeTypeExample example);

    DictEduRollRollChangeType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollRollChangeType record, @Param("example") DictEduRollRollChangeTypeExample example);

    int updateByExample(@Param("record") DictEduRollRollChangeType record, @Param("example") DictEduRollRollChangeTypeExample example);

    int updateByPrimaryKeySelective(DictEduRollRollChangeType record);

    int updateByPrimaryKey(DictEduRollRollChangeType record);
}