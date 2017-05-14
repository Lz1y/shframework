package com.shframework.modules.sys.entity.vo;

import java.io.Serializable;

/**
 * 存放系统设置信息
 * @author RanWeizheng
 *
 */
public class SysCfgVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5869433085991625326L;
	private String eduSkdScheduleNo;
	
	private String eduScrCreditDetailNo;//学期成绩编号，对应着PDF中的编号
	
	private String eduSkdScheduleIsNotLimitTime;
	
	private String eduExamClazzProgressStatusLimitOpen;
	
	private String eduTaskSelNaturalclazzcodeortitle;
	
	private String eduGradRewardsNo;
	
	public String getEduTaskSelNaturalclazzcodeortitle() {
		return eduTaskSelNaturalclazzcodeortitle;
	}

	public void setEduTaskSelNaturalclazzcodeortitle(String eduTaskSelNaturalclazzcodeortitle) {
		this.eduTaskSelNaturalclazzcodeortitle = eduTaskSelNaturalclazzcodeortitle;
	}

	public String getEduSkdScheduleIsNotLimitTime() {
		return eduSkdScheduleIsNotLimitTime;
	}

	public void setEduSkdScheduleIsNotLimitTime(String eduSkdScheduleIsNotLimitTime) {
		this.eduSkdScheduleIsNotLimitTime = eduSkdScheduleIsNotLimitTime;
	}

	public String getEduSkdScheduleNo() {
		return eduSkdScheduleNo;
	}

	public void setEduSkdScheduleNo(String eduSkdScheduleNo) {
		this.eduSkdScheduleNo = eduSkdScheduleNo;
	}

	public String getEduExamClazzProgressStatusLimitOpen() {
		return eduExamClazzProgressStatusLimitOpen;
	}

	public void setEduExamClazzProgressStatusLimitOpen(
			String eduExamClazzProgressStatusLimitOpen) {
		this.eduExamClazzProgressStatusLimitOpen = eduExamClazzProgressStatusLimitOpen;
	}

	public String getEduScrCreditDetailNo() {
		return eduScrCreditDetailNo;
	}

	public void setEduScrCreditDetailNo(String eduScrCreditDetailNo) {
		this.eduScrCreditDetailNo = eduScrCreditDetailNo;
	}

	public String getEduGradRewardsNo() {
		return eduGradRewardsNo;
	}

	public void setEduGradRewardsNo(String eduGradRewardsNo) {
		this.eduGradRewardsNo = eduGradRewardsNo;
	}
}