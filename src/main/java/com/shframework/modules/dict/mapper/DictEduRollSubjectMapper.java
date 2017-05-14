package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollSubject;
import com.shframework.modules.dict.entity.DictEduRollSubjectExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollSubjectMapper {
    int countByExample(DictEduRollSubjectExample example);

    int deleteByExample(DictEduRollSubjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollSubject record);

    int insertSelective(DictEduRollSubject record);

    List<DictEduRollSubject> selectByExample(DictEduRollSubjectExample example);

    DictEduRollSubject selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollSubject record, @Param("example") DictEduRollSubjectExample example);

    int updateByExample(@Param("record") DictEduRollSubject record, @Param("example") DictEduRollSubjectExample example);

    int updateByPrimaryKeySelective(DictEduRollSubject record);

    int updateByPrimaryKey(DictEduRollSubject record);
}