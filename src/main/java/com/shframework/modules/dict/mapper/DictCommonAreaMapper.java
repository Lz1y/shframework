package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictCommonArea;
import com.shframework.modules.dict.entity.DictCommonAreaExample;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictCommonAreaMapper {
    int countByExample(DictCommonAreaExample example);

    int deleteByExample(DictCommonAreaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictCommonArea record);

    int insertSelective(DictCommonArea record);

    List<DictCommonArea> selectByExample(DictCommonAreaExample example);

    DictCommonArea selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictCommonArea record, @Param("example") DictCommonAreaExample example);

    int updateByExample(@Param("record") DictCommonArea record, @Param("example") DictCommonAreaExample example);

    int updateByPrimaryKeySelective(DictCommonArea record);

    int updateByPrimaryKey(DictCommonArea record);

	List<HashMap<String, Object>> selectProvince();
}