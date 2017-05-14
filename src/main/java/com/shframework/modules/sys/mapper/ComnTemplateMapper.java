package com.shframework.modules.sys.mapper;

import com.shframework.modules.sys.entity.ComnTemplate;
import com.shframework.modules.sys.entity.ComnTemplateExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;
@MyBatisRepository
public interface ComnTemplateMapper {
    int countByExample(ComnTemplateExample example);

    int deleteByExample(ComnTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ComnTemplate record);

    int insertSelective(ComnTemplate record);

    List<ComnTemplate> selectByExample(ComnTemplateExample example);

    ComnTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComnTemplate record, @Param("example") ComnTemplateExample example);

    int updateByExample(@Param("record") ComnTemplate record, @Param("example") ComnTemplateExample example);

    int updateByPrimaryKeySelective(ComnTemplate record);

    int updateByPrimaryKey(ComnTemplate record);
    
    
    /**
     * file_format 0:excel， 1:dbf ; type 0:导入 ，1：导出；按类型精确查询 
     * @param map
     * @return
     * @author zhangjk
     */
    public List<ComnTemplate> queryTemplateListByMap(Map<String, Object> map);
    

	List<ComnTemplate> queryTemplate(Map<String, Object> map);
	

	int queryTemplateCount(Map<String, Object> map);
}