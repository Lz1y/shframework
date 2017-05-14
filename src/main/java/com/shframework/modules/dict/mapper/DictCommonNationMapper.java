package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictCommonNation;
import com.shframework.modules.dict.entity.DictCommonNationExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictCommonNationMapper {
    int countByExample(DictCommonNationExample example);

    int deleteByExample(DictCommonNationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictCommonNation record);

    int insertSelective(DictCommonNation record);

    List<DictCommonNation> selectByExample(DictCommonNationExample example);

    DictCommonNation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictCommonNation record, @Param("example") DictCommonNationExample example);

    int updateByExample(@Param("record") DictCommonNation record, @Param("example") DictCommonNationExample example);

    int updateByPrimaryKeySelective(DictCommonNation record);

    int updateByPrimaryKey(DictCommonNation record);
}