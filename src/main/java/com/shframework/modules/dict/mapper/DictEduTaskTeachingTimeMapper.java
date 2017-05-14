package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduTaskTeachingTime;
import com.shframework.modules.dict.entity.DictEduTaskTeachingTimeExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduTaskTeachingTimeMapper {
    int countByExample(DictEduTaskTeachingTimeExample example);

    int deleteByExample(DictEduTaskTeachingTimeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduTaskTeachingTime record);

    int insertSelective(DictEduTaskTeachingTime record);

    List<DictEduTaskTeachingTime> selectByExample(DictEduTaskTeachingTimeExample example);

    DictEduTaskTeachingTime selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduTaskTeachingTime record, @Param("example") DictEduTaskTeachingTimeExample example);

    int updateByExample(@Param("record") DictEduTaskTeachingTime record, @Param("example") DictEduTaskTeachingTimeExample example);

    int updateByPrimaryKeySelective(DictEduTaskTeachingTime record);

    int updateByPrimaryKey(DictEduTaskTeachingTime record);

	void deleteAll();

	List<DictEduTaskTeachingTime> queryTeachingTimeAll();

	List<DictEduTaskTeachingTime> queryTeachingTime(int dictCommonYes);

	List<DictEduTaskTeachingTime> selectByWeekDayAndPeriodId(
			Map<String, Object> paraMap);
}