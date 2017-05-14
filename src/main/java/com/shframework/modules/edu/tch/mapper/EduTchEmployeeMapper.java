package com.shframework.modules.edu.tch.mapper;

import com.shframework.modules.edu.tch.entity.EduTchEmployee;
import com.shframework.modules.edu.tch.entity.EduTchEmployeeExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface EduTchEmployeeMapper {
    int countByExample(EduTchEmployeeExample example);

    int deleteByExample(EduTchEmployeeExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(EduTchEmployee record);

    int insertSelective(EduTchEmployee record);

    List<EduTchEmployee> selectByExample(EduTchEmployeeExample example);

    EduTchEmployee selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") EduTchEmployee record, @Param("example") EduTchEmployeeExample example);

    int updateByExample(@Param("record") EduTchEmployee record, @Param("example") EduTchEmployeeExample example);

    int updateByPrimaryKeySelective(EduTchEmployee record);

    int updateByPrimaryKey(EduTchEmployee record);
    
    List<EduTchEmployee> queryByCondition(Map<String, Object> map);

	int countByCondition(Map<String, Object> map);

	EduTchEmployee detail(Integer userId);

	List<EduTchEmployee> listRecursionByDepartmentId(Map<String, Object> map);

	int countListRecursionByDepartmentId(Map<String, Object> map);
	
	@Select("SELECT _su.`username` as `任课教师`, _su.`user_no` as `工号` FROM sys_user _su INNER JOIN edu_tch_employee _ete ON _ete.user_id = _su.`id` INNER JOIN dict_edu_common_department _dep ON _dep.id = _ete.department_id INNER JOIN dict_edu_tch_post_type _type ON _type.id = _ete.post_type_id INNER JOIN dict_edu_tch_staff_type _staff ON _staff.id = _ete.staff_type_id INNER JOIN dict_edu_tch_post_level _level ON _level.id = _ete.post_level_id WHERE 1=1 AND _su.`logic_delete` = '0' AND _ete.`logic_delete` = '0' AND _type.code IN ('11', '12', '50')")
	List<Map<String, Object>> getAllTeacher();
	
	@Select("SELECT _su.`username` as `任课教师`, _su.`user_no` as `工号` FROM sys_user _su  WHERE 1=1 AND _su.`logic_delete` = '0' AND _su.user_role='0' ")
	List<Map<String, Object>> getAllTeacherLD();
}