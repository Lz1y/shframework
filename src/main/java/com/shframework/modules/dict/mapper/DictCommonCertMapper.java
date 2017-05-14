package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictCommonCert;
import com.shframework.modules.dict.entity.DictCommonCertExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictCommonCertMapper {
    int countByExample(DictCommonCertExample example);

    int deleteByExample(DictCommonCertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictCommonCert record);

    int insertSelective(DictCommonCert record);

    List<DictCommonCert> selectByExample(DictCommonCertExample example);

    DictCommonCert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictCommonCert record, @Param("example") DictCommonCertExample example);

    int updateByExample(@Param("record") DictCommonCert record, @Param("example") DictCommonCertExample example);

    int updateByPrimaryKeySelective(DictCommonCert record);

    int updateByPrimaryKey(DictCommonCert record);
}