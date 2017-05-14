package com.shframework.modules.dict.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictEduScrReason;
import com.shframework.modules.dict.entity.DictEduScrReasonExample;

@MyBatisRepository
public interface DictEduScrReasonMapper {
    int countByExample(DictEduScrReasonExample example);

    int deleteByExample(DictEduScrReasonExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduScrReason record);

    int insertSelective(DictEduScrReason record);

    List<DictEduScrReason> selectByExample(DictEduScrReasonExample example);

    DictEduScrReason selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduScrReason record, @Param("example") DictEduScrReasonExample example);

    int updateByExample(@Param("record") DictEduScrReason record, @Param("example") DictEduScrReasonExample example);

    int updateByPrimaryKeySelective(DictEduScrReason record);

    int updateByPrimaryKey(DictEduScrReason record);
    
    /**
     * 根据查询条件map，查找符合条件的记录数量
     * @param parMap 
     * {
     * String reasonType;
     * String searchCondition;
     * int limitStart;
     * int limitEnd;
     * }
     * @return
     * @author RanWeizheng
     */
    int countByMap(Map<String, Object> parMap);
    
    /**
     * 根据查询条件map，查找符合条件的记录
     * @param parMap 
     * {
     * String reasonType;
     * String searchCondition;
     * int limitStart;
     * int limitEnd;
     * }
     * @return
     * @author RanWeizheng
     */
    List<DictEduScrReason> selectByMap(Map<String, Object> parMap);
}