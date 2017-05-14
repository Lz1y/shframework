package com.shframework.modules.dict.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shframework.modules.dict.entity.DictEduTaskPeriod;
import com.shframework.modules.dict.entity.DictEduTaskTeachingTime;
import com.shframework.modules.dict.mapper.DictEduTaskPeriodMapper;
import com.shframework.modules.dict.mapper.DictEduTaskTeachingTimeMapper;
import com.shframework.modules.dict.service.TeachingTimeService;

@Service
public class TeachingTimeServiceImpl implements TeachingTimeService {

	@Autowired
	DictEduTaskPeriodMapper dictEduTaskPeriodMapper;
	
	@Autowired
	DictEduTaskTeachingTimeMapper dictEduTaskTeachingTimeMapper;
	
	@Override
	public List<DictEduTaskPeriod> queryTaskPeriodAll() {
		return dictEduTaskPeriodMapper.queryAll();
	}

	@Override
	@Transactional
	public void save(List<DictEduTaskTeachingTime> teachingTimeList) {
		dictEduTaskTeachingTimeMapper.deleteAll();
		if(null != teachingTimeList && teachingTimeList.size()>0){
			//保存“教学时间配置表”
			for(DictEduTaskTeachingTime teachingTime : teachingTimeList){
				dictEduTaskTeachingTimeMapper.insertSelective(teachingTime);
			}
		}
	}

	@Override
	public List<DictEduTaskTeachingTime> queryTeachingTimeAll() {
		return dictEduTaskTeachingTimeMapper.queryTeachingTimeAll();
	}

	@Override
	public List<DictEduTaskTeachingTime> queryTeachingTime(int dictCommonYes) {
		return dictEduTaskTeachingTimeMapper.queryTeachingTime(dictCommonYes);
	}

	@Override
	@Transactional
	public void delete() {
		dictEduTaskTeachingTimeMapper.deleteAll();
	}

}
