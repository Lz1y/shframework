package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictCommonEthnicity;
import com.shframework.modules.dict.entity.DictCommonEthnicityExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictCommonEthnicityMapper {
    int countByExample(DictCommonEthnicityExample example);

    int deleteByExample(DictCommonEthnicityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictCommonEthnicity record);

    int insertSelective(DictCommonEthnicity record);

    List<DictCommonEthnicity> selectByExample(DictCommonEthnicityExample example);

    DictCommonEthnicity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictCommonEthnicity record, @Param("example") DictCommonEthnicityExample example);

    int updateByExample(@Param("record") DictCommonEthnicity record, @Param("example") DictCommonEthnicityExample example);

    int updateByPrimaryKeySelective(DictCommonEthnicity record);

    int updateByPrimaryKey(DictCommonEthnicity record);
}