package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduClrClrType;
import com.shframework.modules.dict.entity.DictEduClrClrTypeExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduClrClrTypeMapper {
    int countByExample(DictEduClrClrTypeExample example);

    int deleteByExample(DictEduClrClrTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduClrClrType record);

    int insertSelective(DictEduClrClrType record);

    List<DictEduClrClrType> selectByExample(DictEduClrClrTypeExample example);

    DictEduClrClrType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduClrClrType record, @Param("example") DictEduClrClrTypeExample example);

    int updateByExample(@Param("record") DictEduClrClrType record, @Param("example") DictEduClrClrTypeExample example);

    int updateByPrimaryKeySelective(DictEduClrClrType record);

    int updateByPrimaryKey(DictEduClrClrType record);

	int countByMap(Map<String, Object> parMap);

	List<DictEduClrClrType> selectByMap(Map<String, Object> parMap);
}