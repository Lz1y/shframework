package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduRollCandidateType;
import com.shframework.modules.dict.entity.DictEduRollCandidateTypeExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduRollCandidateTypeMapper {
    int countByExample(DictEduRollCandidateTypeExample example);

    int deleteByExample(DictEduRollCandidateTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduRollCandidateType record);

    int insertSelective(DictEduRollCandidateType record);

    List<DictEduRollCandidateType> selectByExample(DictEduRollCandidateTypeExample example);

    DictEduRollCandidateType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduRollCandidateType record, @Param("example") DictEduRollCandidateTypeExample example);

    int updateByExample(@Param("record") DictEduRollCandidateType record, @Param("example") DictEduRollCandidateTypeExample example);

    int updateByPrimaryKeySelective(DictEduRollCandidateType record);

    int updateByPrimaryKey(DictEduRollCandidateType record);
}