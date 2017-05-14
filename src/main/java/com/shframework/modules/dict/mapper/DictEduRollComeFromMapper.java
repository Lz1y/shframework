package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollComeFrom;
import com.shframework.modules.dict.entity.DictEduRollComeFromExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollComeFromMapper {
    int countByExample(DictEduRollComeFromExample example);

    int deleteByExample(DictEduRollComeFromExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollComeFrom record);

    int insertSelective(DictEduRollComeFrom record);

    List<DictEduRollComeFrom> selectByExample(DictEduRollComeFromExample example);

    DictEduRollComeFrom selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollComeFrom record, @Param("example") DictEduRollComeFromExample example);

    int updateByExample(@Param("record") DictEduRollComeFrom record, @Param("example") DictEduRollComeFromExample example);

    int updateByPrimaryKeySelective(DictEduRollComeFrom record);

    int updateByPrimaryKey(DictEduRollComeFrom record);
}