package com.shframework.modules.dict.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictEduGradRewardsReason;
import com.shframework.modules.dict.entity.DictEduGradRewardsReasonExample;

@MyBatisRepository
public interface DictEduGradRewardsReasonMapper {
    int countByExample(DictEduGradRewardsReasonExample example);

    int deleteByExample(DictEduGradRewardsReasonExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduGradRewardsReason record);

    int insertSelective(DictEduGradRewardsReason record);

    List<DictEduGradRewardsReason> selectByExample(DictEduGradRewardsReasonExample example);

    DictEduGradRewardsReason selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduGradRewardsReason record, @Param("example") DictEduGradRewardsReasonExample example);

    int updateByExample(@Param("record") DictEduGradRewardsReason record, @Param("example") DictEduGradRewardsReasonExample example);

    int updateByPrimaryKeySelective(DictEduGradRewardsReason record);

    int updateByPrimaryKey(DictEduGradRewardsReason record);
    
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
    List<DictEduGradRewardsReason> selectByMap(Map<String, Object> parMap);
}