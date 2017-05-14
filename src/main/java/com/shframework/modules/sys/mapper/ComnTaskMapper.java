package com.shframework.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.sys.entity.ComnTask;
import com.shframework.modules.sys.entity.ComnTaskExample;

@MyBatisRepository
public interface ComnTaskMapper {
    int countByExample(ComnTaskExample example);

    int deleteByExample(ComnTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ComnTask record);

    int insertSelective(ComnTask record);

    List<ComnTask> selectByExample(ComnTaskExample example);

    ComnTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComnTask record, @Param("example") ComnTaskExample example);

    int updateByExample(@Param("record") ComnTask record, @Param("example") ComnTaskExample example);

    int updateByPrimaryKeySelective(ComnTask record);

    int updateByPrimaryKey(ComnTask record);
    
    /**
     * 批量添加  ComnTask 数据
     * @param list
     * @return
     */
    int insertBatch(List<ComnTask> list);
}