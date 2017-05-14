package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduCommonLabel;
import com.shframework.modules.dict.entity.DictEduCommonLabelExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;
@MyBatisRepository
public interface DictEduCommonLabelMapper {
    int countByExample(DictEduCommonLabelExample example);

    int deleteByExample(DictEduCommonLabelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduCommonLabel record);

    int insertSelective(DictEduCommonLabel record);

    List<DictEduCommonLabel> selectByExample(DictEduCommonLabelExample example);

    DictEduCommonLabel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduCommonLabel record, @Param("example") DictEduCommonLabelExample example);

    int updateByExample(@Param("record") DictEduCommonLabel record, @Param("example") DictEduCommonLabelExample example);

    int updateByPrimaryKeySelective(DictEduCommonLabel record);

    int updateByPrimaryKey(DictEduCommonLabel record);
    
    /**
     * 根据查询条件map，查找符合条件的记录数量
     * @param parMap 
     * {
     * String tableName;
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
     * String tableName;
     * String searchCondition;
     * int limitStart;
     * int limitEnd;
     * }
     * @return
     * @author RanWeizheng
     */
    List<DictEduCommonLabel> selectByMap(Map<String, Object> parMap);
}