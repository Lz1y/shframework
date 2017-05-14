package com.shframework.modules.dict.service;

import java.util.List;

import com.shframework.modules.dict.entity.DictEduTaskPeriod;
import com.shframework.modules.dict.entity.DictEduTaskTeachingTime;

public interface TeachingTimeService {

	List<DictEduTaskPeriod> queryTaskPeriodAll();

	void save(List<DictEduTaskTeachingTime> teachingTimeList);

	List<DictEduTaskTeachingTime> queryTeachingTimeAll();

	List<DictEduTaskTeachingTime> queryTeachingTime(int dictCommonYes);

	void delete();

}
