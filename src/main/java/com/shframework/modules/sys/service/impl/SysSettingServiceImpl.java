package com.shframework.modules.sys.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shframework.common.util.Constants;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.sys.entity.SysSetting;
import com.shframework.modules.sys.entity.SysSettingExample;
import com.shframework.modules.sys.entity.vo.SysCfgVo;
import com.shframework.modules.sys.mapper.SysSettingMapper;
import com.shframework.modules.sys.service.SysSettingService;

@Service
public class SysSettingServiceImpl implements SysSettingService {
	@Autowired
	SysSettingMapper sysSettingMapper;
	@Autowired
	private CacheComponent<?> cacheComponent;
	
	@SuppressWarnings("unchecked")
	@Override
	public String getContentByTitle(String title) {
		Map<String, Object> settingMap = (Map<String, Object>) cacheComponent.resource(CacheComponent.KEY_SYSSETTING);
		if (settingMap == null ){
			return null;
		}
		return settingMap.get(title)!=null ? (String) settingMap.get(title) : null;
	}
	
	@Override
	public String getContentByTitle1(String title) {
		return sysSettingMapper.getContentByTitle(title);
	}
	

	@Override
	@Transactional
	public int updateSysSetting(String title, String content) {
		SysSetting setting = new SysSetting();
		setting.setContent(content);
		SysSettingExample example = new SysSettingExample();
		example.createCriteria().andTitleEqualTo(title);
		return sysSettingMapper.updateByExampleSelective(setting, example);
	}
	
	/**
	 * 获取系统设置信息
	 * @return
	 * @author RanWeizheng
	 */
	@Override
	public SysCfgVo getSysSetting(){
		SysCfgVo vo = new SysCfgVo();
		String eduScrCreditDetailNo = sysSettingMapper.getContentByTitle(Constants.EDU_SCR_CREDIT_DETAIL_NO);
		if(null != eduScrCreditDetailNo){
			vo.setEduScrCreditDetailNo(eduScrCreditDetailNo);
		}else{
			SysSetting sysSetting = new SysSetting();
			sysSetting.setTitle(Constants.EDU_SCR_CREDIT_DETAIL_NO);
			sysSetting.setContent("JX/GC7.5-05-JL02");
			sysSetting.setDescription("学期成绩编号");
			sysSetting.setType(1);
			sysSetting.setCreateDate(new Date());
			sysSetting.setModifyDate(new Date());
			sysSetting.setPriority(8);
			sysSetting.setStatus(Constants.BASE_STATUS_ONE);
			sysSetting.setLogicDelete(Constants.BASE_LOGIC_DELETE_ZERO);
			sysSettingMapper.insertSelective(sysSetting);
			vo.setEduScrCreditDetailNo(sysSettingMapper.getContentByTitle(Constants.EDU_SCR_CREDIT_DETAIL_NO));
		}
		vo.setEduSkdScheduleNo(sysSettingMapper.getContentByTitle(Constants.EDU_SKD_SCHEDULE_NO));
		vo.setEduSkdScheduleIsNotLimitTime(sysSettingMapper.getContentByTitle(Constants.EDU_SKD_SCHEDULE_ISNOT_LIMIT_TIME));
		vo.setEduExamClazzProgressStatusLimitOpen(sysSettingMapper.getContentByTitle(Constants.EDU_EXAM_CLAZZ_PROGRESS_STATUS_LIMIT_OPEN));
		vo.setEduTaskSelNaturalclazzcodeortitle(sysSettingMapper.getContentByTitle(Constants.EDU_TASK_SEL_NATURALCLAZZCODEORTITLE));
		
		String eduGradRewardsNo = sysSettingMapper.getContentByTitle(Constants.EDU_GRAD_REWARDS_NO);
		if(null != eduGradRewardsNo){
			vo.setEduGradRewardsNo(eduGradRewardsNo);
		}else{
			SysSetting sysSetting = new SysSetting();
			sysSetting.setTitle(Constants.EDU_GRAD_REWARDS_NO);
			sysSetting.setContent("JW/GC7.5-04-JL17");
			sysSetting.setDescription("奖惩及违纪处理编号");
			sysSetting.setType(1);
			sysSetting.setCreateDate(new Date());
			sysSetting.setModifyDate(new Date());
			sysSetting.setPriority(8);
			sysSetting.setStatus(Constants.BASE_STATUS_ONE);
			sysSetting.setLogicDelete(Constants.BASE_LOGIC_DELETE_ZERO);
			sysSettingMapper.insertSelective(sysSetting);
			vo.setEduGradRewardsNo(sysSettingMapper.getContentByTitle(Constants.EDU_GRAD_REWARDS_NO));
		}
		return vo;
	}
	
	/**
	 * 批量更新系统设置
	 * @param vo
	 * @return
	 * @author RanWeizheng
	 */
	@SuppressWarnings("static-access")
	@Override
	@Transactional
	public int updateSysSetting(SysCfgVo vo){
		int result = 0;
		/*List<String> paramList = ReflectionUtils.getFeildList(vo);
		for (String parameter : paramList){
			String content =  (String)ReflectionUtils.invokeGetterMethod(vo, parameter);
			result += updateSysSetting(parameter, content);// parameter 不是想要的格式
		}
		*/
		if (vo.getEduScrCreditDetailNo() != null){
			result += updateSysSetting(Constants.EDU_SCR_CREDIT_DETAIL_NO, vo.getEduScrCreditDetailNo());
		}
		if (vo.getEduSkdScheduleNo() != null){
			result += updateSysSetting(Constants.EDU_SKD_SCHEDULE_NO, vo.getEduSkdScheduleNo());
		}
		
		if (vo.getEduSkdScheduleIsNotLimitTime() != null){
			result += updateSysSetting(Constants.EDU_SKD_SCHEDULE_ISNOT_LIMIT_TIME, vo.getEduSkdScheduleIsNotLimitTime());
		}
		
		if (vo.getEduExamClazzProgressStatusLimitOpen() != null){
			result += updateSysSetting(Constants.EDU_EXAM_CLAZZ_PROGRESS_STATUS_LIMIT_OPEN, vo.getEduExamClazzProgressStatusLimitOpen());
		}
		
		if (vo.getEduTaskSelNaturalclazzcodeortitle() != null){
			result += updateSysSetting(Constants.EDU_TASK_SEL_NATURALCLAZZCODEORTITLE, vo.getEduTaskSelNaturalclazzcodeortitle());
		}
		
		if (vo.getEduGradRewardsNo() != null){
			result += updateSysSetting(Constants.EDU_GRAD_REWARDS_NO, vo.getEduGradRewardsNo());
		}
		
		cacheComponent.renew(cacheComponent.KEY_SYSSETTING);
		
		return result;
	}
	
	@Override
	@Cacheable(value="homeCache", key="'scheduleNo'")
	public String getScheduleNo() {
		return sysSettingMapper.getContentByTitle(Constants.EDU_SKD_SCHEDULE_NO);
	}
	
	/**
	 * 修改课程方法的系统配置
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年7月10日 下午2:37:17
	 */
	@Override
	public void updateCourseMethodSysSetting(String title, String content) {
		String origContent = getContentByTitle(title);
		
		if(StringUtils.isNotBlank(content) && !content.equals(origContent)) {
			updateSysSetting(title,content);
		}
		cacheComponent.renew(CacheComponent.KEY_SYSSETTING);
	}
	
	
	/**
	 * 取得 教学计划 --> 课程计算方法内容 中的3个配置值
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年7月14日 下午5:19:06
	 */
	@Override
	public List<Integer> getCourseMethodSysSetting(){
		
		String method3 = getContentByTitle(Constants.METHOD3_HOURS_PER_CREDIT);
		String method5 = getContentByTitle(Constants.METHOD5_HOURS_PER_CREDIT);
		String method6 = getContentByTitle(Constants.METHOD6_WEEKS);
		
		List<Integer> list = new ArrayList<Integer>();
		list.clear();
		list.add(Integer.valueOf(method3));
		list.add(Integer.valueOf(method5));
		list.add(Integer.valueOf(method6));
		
		return list;
	}

	@Override
	public List<SysSetting> listByTitles(String... title) {
		SysSettingExample example = new SysSettingExample();
		example.createCriteria().andTitleIn(Arrays.asList(title));
		return sysSettingMapper.selectByExample(example);
	}
}