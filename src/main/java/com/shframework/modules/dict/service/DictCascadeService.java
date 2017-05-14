package com.shframework.modules.dict.service;

import java.util.List;
import java.util.Map;

import com.shframework.common.util.DictCascadeVo;

public interface DictCascadeService {
	
	/**
	 * 获取级联信息
	 * @param type	  类型
	 * @param parentId	上一级节点的id
	 * @param option     额外的选项
	 * @return
	 */
	public  List<DictCascadeVo> getCascadeInfo(String type, Integer parentId, String option);
	
	public Map<String, List<DictCascadeVo>> getCampusBuildingCascadeInfo();
	public Map<String, List<DictCascadeVo>> getDepMajorCascadeInfo();
	public Map<String, List<DictCascadeVo>>  getCategoryGroupCascadeInfo();
	public Map<String, List<DictCascadeVo>>  getGroupMajorCascadeInfo();
	public Map<String, List<DictCascadeVo>> getMajorMajorfieldCascadeInfo();

	
	/**
	 * 校区-系部关联
	 * @RP: 	
	 * @param	
	 * @return		
	 * @create 	wangkang 2016年12月13日 上午10:59:31
	 * @modify
	 */
	public Map<String, List<DictCascadeVo>> getDepartmentsByCampusId();
	
	public List<DictCascadeVo> getRewardsCategoryCascadeInfo(int parentId);
	
	public List<DictCascadeVo> getRewardsReasonCascadeInfo(int parentId);
	
}