package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollDiplomaLevel;
import com.shframework.modules.dict.entity.DictEduRollDiplomaLevelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollDiplomaLevelMapper {
    int countByExample(DictEduRollDiplomaLevelExample example);

    int deleteByExample(DictEduRollDiplomaLevelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollDiplomaLevel record);

    int insertSelective(DictEduRollDiplomaLevel record);

    List<DictEduRollDiplomaLevel> selectByExample(DictEduRollDiplomaLevelExample example);

    DictEduRollDiplomaLevel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollDiplomaLevel record, @Param("example") DictEduRollDiplomaLevelExample example);

    int updateByExample(@Param("record") DictEduRollDiplomaLevel record, @Param("example") DictEduRollDiplomaLevelExample example);

    int updateByPrimaryKeySelective(DictEduRollDiplomaLevel record);

    int updateByPrimaryKey(DictEduRollDiplomaLevel record);
}