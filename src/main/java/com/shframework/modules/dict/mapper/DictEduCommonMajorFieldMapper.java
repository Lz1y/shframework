package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduCommonMajorField;
import com.shframework.modules.dict.entity.DictEduCommonMajorFieldExample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;
@MyBatisRepository
public interface DictEduCommonMajorFieldMapper {
    int countByExample(DictEduCommonMajorFieldExample example);

    int deleteByExample(DictEduCommonMajorFieldExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduCommonMajorField record);

    int insertSelective(DictEduCommonMajorField record);

    List<DictEduCommonMajorField> selectByExample(DictEduCommonMajorFieldExample example);

    DictEduCommonMajorField selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduCommonMajorField record, @Param("example") DictEduCommonMajorFieldExample example);

    int updateByExample(@Param("record") DictEduCommonMajorField record, @Param("example") DictEduCommonMajorFieldExample example);

    int updateByPrimaryKeySelective(DictEduCommonMajorField record);

    int updateByPrimaryKey(DictEduCommonMajorField record);
    
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
    List<DictEduCommonMajorField> selectByMap(Map<String, Object> parMap);

    /**
     * <p>针对初始化学生信息时，根据专业(专业方向)查找专业方向id的专用方法：应用电子技术(广播电视网络)：专业(专业方向)</p>
     * @param majorTitle
     * @param majorFieldTitle
     * @return
     * @author zhangjinkui
     */
	Integer selectMajorFieldIdByMajorTitleAndMajorFieldTitle(String majorTitle,
			String majorFieldTitle);

	List<HashMap<String, Object>> selectAllMajorField(String string);

	List<HashMap<String, Object>> selectByTableName(Map<String, Object> map);
}