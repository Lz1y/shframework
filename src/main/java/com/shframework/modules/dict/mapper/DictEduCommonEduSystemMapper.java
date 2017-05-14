package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduCommonEduSystem;
import com.shframework.modules.dict.entity.DictEduCommonEduSystemExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;
@MyBatisRepository
public interface DictEduCommonEduSystemMapper {
    int countByExample(DictEduCommonEduSystemExample example);

    int deleteByExample(DictEduCommonEduSystemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduCommonEduSystem record);

    int insertSelective(DictEduCommonEduSystem record);

    List<DictEduCommonEduSystem> selectByExample(DictEduCommonEduSystemExample example);

    DictEduCommonEduSystem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduCommonEduSystem record, @Param("example") DictEduCommonEduSystemExample example);

    int updateByExample(@Param("record") DictEduCommonEduSystem record, @Param("example") DictEduCommonEduSystemExample example);

    int updateByPrimaryKeySelective(DictEduCommonEduSystem record);

    int updateByPrimaryKey(DictEduCommonEduSystem record);
}