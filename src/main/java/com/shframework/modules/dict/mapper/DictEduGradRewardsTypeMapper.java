package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduGradRewardsType;
import com.shframework.modules.dict.entity.DictEduGradRewardsTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduGradRewardsTypeMapper {
    int countByExample(DictEduGradRewardsTypeExample example);

    int deleteByExample(DictEduGradRewardsTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduGradRewardsType record);

    int insertSelective(DictEduGradRewardsType record);

    List<DictEduGradRewardsType> selectByExample(DictEduGradRewardsTypeExample example);

    DictEduGradRewardsType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduGradRewardsType record, @Param("example") DictEduGradRewardsTypeExample example);

    int updateByExample(@Param("record") DictEduGradRewardsType record, @Param("example") DictEduGradRewardsTypeExample example);

    int updateByPrimaryKeySelective(DictEduGradRewardsType record);

    int updateByPrimaryKey(DictEduGradRewardsType record);
}