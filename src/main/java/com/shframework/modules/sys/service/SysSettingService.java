package com.shframework.modules.sys.service;

import java.util.List;

import com.shframework.modules.sys.entity.SysSetting;
import com.shframework.modules.sys.entity.vo.SysCfgVo;


public interface SysSettingService {

	/**
	 * 通过title属性，获得设置的内容
	 * @param title
	 * @return
	 * @author RanWeizheng
	 */
	String getContentByTitle(String title);
	
	String getContentByTitle1(String title);
	
	
	/**
	 * 更新指定title的content
	 * @param title
	 * @param content
	 * @return
	 * @author RanWeizheng
	 */
	int updateSysSetting(String title, String content);
		
	/**
	 * 获取系统设置信息
	 * @return
	 * @author RanWeizheng
	 */
	SysCfgVo getSysSetting();
	
	/**
	 * 批量更新系统设置
	 * @param vo
	 * @return
	 * @author RanWeizheng
	 */
	int updateSysSetting(SysCfgVo vo);
	
	/**
	 * 获取系统
	 * @return
	 */
	public String getScheduleNo();
	
	/**
	 * 修改课程方法的系统配置
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年7月10日 下午2:37:17
	 */
	public void updateCourseMethodSysSetting(String title, String content);
	
	/**
	 * 取得 教学计划 --> 课程计算方法内容 中的3个配置值
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年7月14日 下午5:19:06
	 */
	public List<Integer> getCourseMethodSysSetting();

	List<SysSetting> listByTitles(String... title);
}
