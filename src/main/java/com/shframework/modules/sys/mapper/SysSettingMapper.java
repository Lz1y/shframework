package com.shframework.modules.sys.mapper;

import com.shframework.modules.sys.entity.SysSetting;
import com.shframework.modules.sys.entity.SysSettingExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface SysSettingMapper {
    int countByExample(SysSettingExample example);

    int deleteByExample(SysSettingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysSetting record);

    int insertSelective(SysSetting record);

    List<SysSetting> selectByExample(SysSettingExample example);

    SysSetting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysSetting record, @Param("example") SysSettingExample example);

    int updateByExample(@Param("record") SysSetting record, @Param("example") SysSettingExample example);

    int updateByPrimaryKeySelective(SysSetting record);

    int updateByPrimaryKey(SysSetting record);
    
    /**
     * 取得配置
     * @param
     * @return		
     * @memo			 					
     * @author 	wangkang
     * @date    2015年3月27日 下午6:08:54
     */
    public String getContentByTitle(String title);

	SysSetting getSysSettingByTitle(String cskdperiodsettingsPerweekday);
}