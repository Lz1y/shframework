package com.shframework.modules.sys.mapper;

import com.shframework.modules.sys.entity.ComnAttachment;
import com.shframework.modules.sys.entity.ComnAttachmentExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface ComnAttachmentMapper {
	
    int countByExample(ComnAttachmentExample example);

    int deleteByExample(ComnAttachmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ComnAttachment record);

    int insertSelective(ComnAttachment record);

    List<ComnAttachment> selectByExample(ComnAttachmentExample example);

    ComnAttachment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComnAttachment record, @Param("example") ComnAttachmentExample example);

    int updateByExample(@Param("record") ComnAttachment record, @Param("example") ComnAttachmentExample example);

    int updateByPrimaryKeySelective(ComnAttachment record);

    int updateByPrimaryKey(ComnAttachment record);
    
}