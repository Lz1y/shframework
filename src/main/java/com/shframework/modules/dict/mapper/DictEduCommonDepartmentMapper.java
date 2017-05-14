package com.shframework.modules.dict.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.dict.entity.DictEduCommonDepartment;
import com.shframework.modules.dict.entity.DictEduCommonDepartmentExample;

@MyBatisRepository
public interface DictEduCommonDepartmentMapper {
	
    int countByExample(DictEduCommonDepartmentExample example);

    int deleteByExample(DictEduCommonDepartmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduCommonDepartment record);

    int insertSelective(DictEduCommonDepartment record);

    List<DictEduCommonDepartment> selectByExample(DictEduCommonDepartmentExample example);

    DictEduCommonDepartment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduCommonDepartment record, @Param("example") DictEduCommonDepartmentExample example);

    int updateByExample(@Param("record") DictEduCommonDepartment record, @Param("example") DictEduCommonDepartmentExample example);

    int updateByPrimaryKeySelective(DictEduCommonDepartment record);

    int updateByPrimaryKey(DictEduCommonDepartment record);
    
	int getChildrenCount(Map<String, Object> parMap);

	List<HashMap<String, Object>> selectDepartment();
    
}