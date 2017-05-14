package com.shframework.modules.dict.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictEduGradRewardsCategory;
import com.shframework.modules.dict.entity.DictEduGradRewardsCategoryExample;

@MyBatisRepository
public interface DictEduGradRewardsCategoryMapper {
    int countByExample(DictEduGradRewardsCategoryExample example);

    int deleteByExample(DictEduGradRewardsCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduGradRewardsCategory record);

    int insertSelective(DictEduGradRewardsCategory record);

    List<DictEduGradRewardsCategory> selectByExample(DictEduGradRewardsCategoryExample example);

    DictEduGradRewardsCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduGradRewardsCategory record, @Param("example") DictEduGradRewardsCategoryExample example);

    int updateByExample(@Param("record") DictEduGradRewardsCategory record, @Param("example") DictEduGradRewardsCategoryExample example);

    int updateByPrimaryKeySelective(DictEduGradRewardsCategory record);

    int updateByPrimaryKey(DictEduGradRewardsCategory record);
    
    /**
     * 根据查询条件map，查找符合条件的记录数量
     * @param parMap 
     * {
     *   String searchCondition;
     * }
     * @return
     * @author Josh
     */
    int countByMap(Map<String, Object> parMap);
    
    /**
     * 根据查询条件map，查找符合条件的记录
     * @param parMap 
     * {
     *   String searchCondition;
     *   String defaultSortField
     *   int limitStart;
     *   int limitEnd;
     * }
     * @return
     * @author Josh
     */
    List<DictEduGradRewardsCategory> selectByMap(Map<String, Object> parMap);
}