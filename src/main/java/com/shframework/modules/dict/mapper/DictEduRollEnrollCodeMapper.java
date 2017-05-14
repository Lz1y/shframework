package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollEnrollCode;
import com.shframework.modules.dict.entity.DictEduRollEnrollCodeExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollEnrollCodeMapper {
    int countByExample(DictEduRollEnrollCodeExample example);

    int deleteByExample(DictEduRollEnrollCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollEnrollCode record);

    int insertSelective(DictEduRollEnrollCode record);

    List<DictEduRollEnrollCode> selectByExample(DictEduRollEnrollCodeExample example);

    DictEduRollEnrollCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollEnrollCode record, @Param("example") DictEduRollEnrollCodeExample example);

    int updateByExample(@Param("record") DictEduRollEnrollCode record, @Param("example") DictEduRollEnrollCodeExample example);

    int updateByPrimaryKeySelective(DictEduRollEnrollCode record);

    int updateByPrimaryKey(DictEduRollEnrollCode record);
}