package com.shframework.modules.dict.service;

import java.util.List;
import java.util.Map;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictEduCommonAcdYearTerm;

public interface DictEduCommonAcdYearTermService {
	public int deleteById( int id);

	public PageTerminal<DictEduCommonAcdYearTerm> findAllByPage(PageSupport pageSupport) ;

	public DictEduCommonAcdYearTerm getDict(int id);

	public  int saveDict(DictEduCommonAcdYearTerm record);
	
	/**
	 * 根据起始学年和学制获得相应的学期list
	 * @author RanWeizheng
	 */
	public  List<Map<String, Object>> getAcdYearTerm(String startYear, Integer eduSystemId);
	
	/**
	 * 获得当前学年学期信息
	 * @author RanWeizheng
	 */
	@Deprecated//TODO 更换为使用wangkang的新方法
	public DictEduCommonAcdYearTerm getCurYearTerm();
	
	/**
	 * 获得根据key 获取 学年学期信息 id
	 * offset 只能是：
	 * -1：上一个学年学期
	 * 0： 当前学年学期
	 * 1：下一个学年学期
	 * @author RanWeizheng
	 */
	@Deprecated//TODO 更换为使用wangkang的新方法
	public Integer getYearTermId(String offset);
	
    /**
     * 根据 yearTermId 取得 DictEduCommonAcdYearTerm ，包括 yearTitle，termTitle，yearCode，termCode
     * @param
     * @return		
     * @memo			 					
     * @author 	wangkang
     * @date    2015年5月20日 下午6:57:54
     */
    public DictEduCommonAcdYearTerm selectAcdYearTermByYearTermId(Integer yearTermId);
}
