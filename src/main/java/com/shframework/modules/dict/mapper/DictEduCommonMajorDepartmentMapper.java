package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduCommonMajorDepartment;
import com.shframework.modules.dict.entity.DictEduCommonMajorDepartmentExample;
import com.shframework.modules.dict.entity.DictEduCommonMajorDepartmentKey;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;
@MyBatisRepository
public interface DictEduCommonMajorDepartmentMapper {
    int countByExample(DictEduCommonMajorDepartmentExample example);

    int deleteByExample(DictEduCommonMajorDepartmentExample example);

    int deleteByPrimaryKey(DictEduCommonMajorDepartmentKey key);

    int insert(DictEduCommonMajorDepartment record);

    int insertSelective(DictEduCommonMajorDepartment record);

    List<DictEduCommonMajorDepartment> selectByExample(DictEduCommonMajorDepartmentExample example);

    DictEduCommonMajorDepartment selectByPrimaryKey(DictEduCommonMajorDepartmentKey key);

    int updateByExampleSelective(@Param("record") DictEduCommonMajorDepartment record, @Param("example") DictEduCommonMajorDepartmentExample example);

    int updateByExample(@Param("record") DictEduCommonMajorDepartment record, @Param("example") DictEduCommonMajorDepartmentExample example);

    int updateByPrimaryKeySelective(DictEduCommonMajorDepartment record);

    int updateByPrimaryKey(DictEduCommonMajorDepartment record);
    
    /**
     * 查找符合条件的记录数量
     * @param parMap
     * @return
     */
    int countByMap(Map<String, Object> parMap);
    /**
     * 查找符合条件的记录， 含分页
     * @param parMap
     * @return
     */
    List<Map<String, Object>>selectByMap(Map<String, Object> parMap);
    
    /**
     * 根据departmentId 删除院系-专业关联关系
     * @param departmentId
     * @return
     */
    int deleteByDepartmentId(Integer departmentId);
    
    /**
     * 根据majorId 删除院系-专业关联关系
     * @param majorId
     * @return
     */
    int deleteByMajorId(Integer majorId);

}