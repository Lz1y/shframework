package com.shframework.modules.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shframework.common.util.Constants;
import com.shframework.common.util.DictCascadeVo;
import com.shframework.modules.basic.component.CacheComponent;
import com.shframework.modules.dict.entity.DictCommon;
import com.shframework.modules.dict.entity.DictEduClrBuilding;
import com.shframework.modules.dict.entity.DictEduCommonCampus;
import com.shframework.modules.dict.entity.DictEduCommonDepartment;
import com.shframework.modules.dict.entity.DictEduCommonGroupMajor;
import com.shframework.modules.dict.entity.DictEduCommonMajor;
import com.shframework.modules.dict.entity.DictEduCommonMajorDepartment;
import com.shframework.modules.dict.entity.DictEduCommonMajorField;
import com.shframework.modules.dict.entity.DictEduGradRewardsCategory;
import com.shframework.modules.dict.entity.DictEduGradRewardsReason;
import com.shframework.modules.dict.helper.DictCacheComponent;
import com.shframework.modules.dict.service.DictCascadeService;

@Service
public class DictCascadeServiceImpl implements DictCascadeService{
	@Autowired
	private CacheComponent<?> cacheComponent;
	
	private static final String CASCADE_TYPE_CAMPUSBUILDING = "campusBuilding";
	private static final String CASCADE_TYPE_DEPMAJOR = "departmentMajor";
	private static final String CASCADE_TYPE_CATEGROUP = "categoryGroup";
	private static final String CASCADE_TYPE_GROUPMAJOR = "groupMajor";
	private static final String CASCADE_TYPE_MAJORFIELD = "majorField";
	private static final String CASCADE_TYPE_CAMPUS_DEPARTMENT = "campusDepartment";
	
	private static final String CASCADE_TYPE_CITY = "city";
	private static final String CASCADE_TYPE_COUNTY = "county";
	
	private static final String OPTION_DICT = "dict";//额外的选项 ， 字典表状态, 当有这个选项时，表示是在字典表维护中使用，显示的格式为 code-title
	
	private static final String CASCADE_REWARDS_CATEGORY = "rewardsCategory";
	
	private static final String CASCADE_REWARDS_REASON = "rewardsReason";
	
	@Override
	public List<DictCascadeVo> getCascadeInfo(String type,
			Integer parentId,
			String option) {
		List<DictCascadeVo> list = null;
		
		switch (type) {
		case CASCADE_TYPE_CAMPUSBUILDING:
			list = getCascadeInfoFromCache(CacheComponent.KEY_CASCADE_CAMPUS_BUILDING, parentId, null);
			break;
		case CASCADE_TYPE_DEPMAJOR:
			list = getCascadeInfoFromCache(CacheComponent.KEY_CASCADE_DEP_MAJOR, parentId, null);
			break;
		case CASCADE_TYPE_CATEGROUP:
			list = getCascadeInfoFromCache(CacheComponent.KEY_CASCADE_CATE_GROUP, parentId, option);
			break;
		case CASCADE_TYPE_GROUPMAJOR:
			list = getCascadeInfoFromCache(CacheComponent.KEY_CASCADE_GROUP_MAJOR, parentId, option);
			break;
		case CASCADE_TYPE_MAJORFIELD:
			list = getCascadeInfoFromCache(CacheComponent.KEY_CASCADE_MAJOR_MAJORFIELD, parentId, option);
			break;
		case CASCADE_TYPE_CITY:
			list = getCityCascadeInfo(parentId);
			break;
		case CASCADE_TYPE_COUNTY:
			list = getCountyCascadeInfo(parentId);
			break;
		case CASCADE_TYPE_CAMPUS_DEPARTMENT: // 校区-系部
			list = getCascadeInfoFromCache(CacheComponent.KEY_CASCADE_CAMPUS_DEPARTMENT, parentId, option);
			break;
		case CASCADE_REWARDS_CATEGORY: // 奖惩类型-奖惩名目
			list = getRewardsCategoryCascadeInfo(parentId);
			break;
		case CASCADE_REWARDS_REASON: // 奖惩类型-奖惩原因
			list = getRewardsReasonCascadeInfo(parentId);
			break;
		default:
			break;
		}
		
		return list;
	}
	
	private List<DictCascadeVo> getCascadeInfoFromCache(String cacheKey, int parentId, String option){
		String key = parentId + "";
		if (OPTION_DICT.equals(option)){
			key = key + "-" + OPTION_DICT;
		}
		@SuppressWarnings("unchecked")
		Map<String, List<DictCascadeVo>> map = ( Map<String, List<DictCascadeVo>>) cacheComponent.resource(cacheKey); 
		List<DictCascadeVo> list = map.get(key);
		if (list == null){
			list = new ArrayList<DictCascadeVo>();
		}
		return list;
	}
	
	
	/**
	 * 通过省级id 获取与其关联的 市级 的list
	 * @author RanWeizheng
	 * @param allDictMap
	 * @param parentId
	 * @return
	 */
	private List<DictCascadeVo> getCityCascadeInfo(int parentId){
		@SuppressWarnings("unchecked")
		HashMap<String, DictCascadeVo> provinceMap =(HashMap<String, DictCascadeVo>) cacheComponent.resource(CacheComponent.KEY_PROVINCE);
		DictCascadeVo vo = provinceMap.get(parentId + "");
		return vo.getChild();
	}
	
	/**
	 * 通过市级id 获取与其关联的 县级 的list
	 * @author RanWeizheng
	 * @param allDictMap
	 * @param parentId 
	 * @return
	 * 先根据 市级的id，找到省级节点，然后遍历找到县级内容
	 */
	private List<DictCascadeVo> getCountyCascadeInfo(int parentId){
		@SuppressWarnings("unchecked")
		HashMap<String, DictCascadeVo> provinceMap =(HashMap<String, DictCascadeVo>) cacheComponent.resource(CacheComponent.KEY_PROVINCE);
		for (Entry<String, DictCascadeVo> entry : provinceMap.entrySet()){
			DictCascadeVo province = entry.getValue();
			List<DictCascadeVo> childList = province.getChild();
			if (childList == null || childList.size() == 0){
				continue;
			}
			for (DictCascadeVo city : childList){
				if (city.getId().equals(parentId + "")){
					return city.getChild();
				}
			}
		}//while
		
		return null;
	}
	
	
	/*级联初始化 开始*/
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, List<DictCascadeVo>>  getCampusBuildingCascadeInfo(){
		Map<String, List<DictCascadeVo>> map = new HashMap<>();
		HashMap<String, DictEduCommonCampus> campusMap =(HashMap<String, DictEduCommonCampus>) cacheComponent.resource(CacheComponent.KEY_CAMPUS);
		Map<String, DictEduClrBuilding> buildingMap =(HashMap<String, DictEduClrBuilding>) cacheComponent.resource(CacheComponent.KEY_BUILDING);
		
		for (Map.Entry<String, DictEduCommonCampus> campusEntry : campusMap.entrySet()){
			int parentId = Integer.parseInt(campusEntry.getKey());
			List<DictCascadeVo> list = new LinkedList<DictCascadeVo>();
			for(Entry<String, DictEduClrBuilding> entry : buildingMap.entrySet()){
				if(entry.getValue().getCampusId() == parentId){
					DictCascadeVo vo = new DictCascadeVo();
					vo.setTitle(entry.getValue().getTitle());//vo.setTitle(entry.getValue().getCode());
					vo.setId(entry.getValue().getId() + "");
					vo.setParentId(parentId + "");
					vo.setIsLeaf(Constants.DICT_COMMON_YES);
					list.add(vo);
				}//if
			}//for
			map.put(campusEntry.getKey(), list);
		}//for
		return map;
	}
	
	@Override
	public Map<String, List<DictCascadeVo>> getDepMajorCascadeInfo(){
		Map<String, List<DictCascadeVo>> map = new HashMap<>();
		@SuppressWarnings("unchecked")
		Map<String, Map<String, DictEduCommonMajorDepartment>> allMajorDepMap = (HashMap<String, Map<String, DictEduCommonMajorDepartment>>) cacheComponent.resource(CacheComponent.KEY_MAJORDEPARTMENT);
		@SuppressWarnings("unchecked")
		Map<String, DictEduCommonMajor> majorMap = (HashMap<String, DictEduCommonMajor>) cacheComponent.resource(CacheComponent.KEY_MAJOR);
		for (Map.Entry<String,  Map<String, DictEduCommonMajorDepartment>> majorDepEntry : allMajorDepMap.entrySet()){
			Map<String, DictEduCommonMajorDepartment> majorDepMap = majorDepEntry.getValue();
			List<DictCascadeVo> list = new LinkedList<DictCascadeVo>();
			if (majorDepMap != null) {
				for (Map.Entry<String , DictEduCommonMajorDepartment> entry :  majorDepMap.entrySet()){
					DictEduCommonMajorDepartment majorDep = entry.getValue();
					if (majorMap.get(""+majorDep.getMajorId())!=null ){
						DictEduCommonMajor major = majorMap.get(""+majorDep.getMajorId());
						DictCascadeVo majorVo = new DictCascadeVo();
						majorVo.setId(major.getId() + "");
						majorVo.setTitle(major.getTitle());
						majorVo.setParentId(majorDepEntry.getKey());
						majorVo.setIsLeaf(Constants.DICT_COMMON_YES);
						list.add(majorVo);
					}
				}//for
			}//if
			map.put(majorDepEntry.getKey(), list);
		}//for
		return map;
	}
	
	@Override
	public Map<String, List<DictCascadeVo>>  getCategoryGroupCascadeInfo(){
		Map<String, List<DictCascadeVo>>  map = new HashMap<>();
		@SuppressWarnings("unchecked")
		HashMap<String, DictCommon> categoryMap =(HashMap<String, DictCommon>) cacheComponent.resource(CacheComponent.KEY_CATEGORYMAJOR);
		@SuppressWarnings("unchecked")
		HashMap<String, DictEduCommonGroupMajor> groupMap =(HashMap<String, DictEduCommonGroupMajor>) cacheComponent.resource(CacheComponent.KEY_GROUPMAJOR);
		
		for (Map.Entry<String, DictCommon> categoryEntry : categoryMap.entrySet()){
			List<DictCascadeVo> list = new LinkedList<DictCascadeVo>();
			List<DictCascadeVo> dictList = new LinkedList<DictCascadeVo>();//字典表模块使用时；
			int parentId = Integer.parseInt(categoryEntry.getKey());
			for (Map.Entry<String, DictEduCommonGroupMajor> entry : groupMap.entrySet()){
				DictEduCommonGroupMajor group = entry.getValue();
				if (group.getCategoryId() == parentId){
					DictCascadeVo gVo = new DictCascadeVo();
					gVo.setId(group.getId() + "");
					gVo.setTitle(group.getTitle());
					gVo.setParentId(parentId + "");
					gVo.setIsLeaf(Constants.DICT_COMMON_YES);
					list.add(gVo);
					
					DictCascadeVo gDictVo = new DictCascadeVo();
					gDictVo.setId(gVo.getId());
					gDictVo.setTitle(group.getCode() + "-" + group.getTitle() );
					gDictVo.setParentId(gVo.getParentId());
					gDictVo.setIsLeaf(gVo.getIsLeaf());
					dictList.add(gDictVo);
				}
			}
			map.put(categoryEntry.getKey(), list);
			map.put(categoryEntry.getKey() + "-" + OPTION_DICT, dictList);
		}
		return map;
	}
	
	public Map<String, List<DictCascadeVo>>  getGroupMajorCascadeInfo(){
		Map<String, List<DictCascadeVo>>  map = new HashMap<>();
		@SuppressWarnings("unchecked")
		HashMap<String, DictEduCommonGroupMajor> groupMap =(HashMap<String, DictEduCommonGroupMajor>) cacheComponent.resource(CacheComponent.KEY_GROUPMAJOR);
		@SuppressWarnings("unchecked")
		HashMap<String, DictEduCommonMajor> majorMap = (HashMap<String, DictEduCommonMajor>) cacheComponent.resource(CacheComponent.KEY_MAJOR);
		
		for (Map.Entry<String, DictEduCommonGroupMajor> groupEntry : groupMap.entrySet()){
			List<DictCascadeVo> list = new LinkedList<DictCascadeVo>();
			List<DictCascadeVo> dictList = new LinkedList<DictCascadeVo>();//字典表模块使用时；
			int parentId = Integer.parseInt(groupEntry.getKey());
			for (Map.Entry<String, DictEduCommonMajor> entry : majorMap.entrySet()){
				DictEduCommonMajor major = entry.getValue();
				if (major.getGroupId() ==  parentId){
					DictCascadeVo mVo = new DictCascadeVo();
					mVo.setId(major.getId() + "");
					mVo.setTitle(major.getTitle());
					mVo.setParentId(parentId + "");
					mVo.setIsLeaf(Constants.DICT_COMMON_YES);
					list.add(mVo);
					
					DictCascadeVo mDictVo = new DictCascadeVo();
					mDictVo.setId(mVo.getId());
					mDictVo.setTitle(major.getCode() + "-" + major.getTitle() );
					mDictVo.setParentId(mVo.getParentId());
					mDictVo.setIsLeaf(mVo.getIsLeaf());
					dictList.add(mDictVo);
				}
			}
			map.put(groupEntry.getKey(), list);
			map.put(groupEntry.getKey() + "-" + OPTION_DICT, dictList);
		}
		
		return map;
	}
	
	public Map<String, List<DictCascadeVo>> getMajorMajorfieldCascadeInfo(){
		Map<String, List<DictCascadeVo>>  map = new HashMap<>();
		@SuppressWarnings("unchecked")
		HashMap<String, DictEduCommonMajor> majorMap = (HashMap<String, DictEduCommonMajor>) cacheComponent.resource(CacheComponent.KEY_MAJOR);
		@SuppressWarnings("unchecked")
		HashMap<String, DictEduCommonMajorField> majorfieldMap =(HashMap<String, DictEduCommonMajorField>) cacheComponent.resource(CacheComponent.KEY_MAJORFIELD);
		
		for (Map.Entry<String, DictEduCommonMajor> majorEntry : majorMap.entrySet()){
			List<DictCascadeVo> list = new LinkedList<DictCascadeVo>();
			List<DictCascadeVo> dictList = new LinkedList<DictCascadeVo>();//字典表模块使用时；
			int parentId = Integer.parseInt(majorEntry.getKey());
			for (Map.Entry<String, DictEduCommonMajorField> entry : majorfieldMap.entrySet()){
				DictEduCommonMajorField majorField = entry.getValue();
				if (majorField.getMajorId() == parentId){
					DictCascadeVo fVo = new DictCascadeVo();
					fVo.setId(majorField.getId() + "");
					fVo.setTitle(majorField.getTitle());
					fVo.setParentId(parentId + "");
					fVo.setIsLeaf(Constants.DICT_COMMON_YES);
					list.add(fVo);
					
					DictCascadeVo fDictVo = new DictCascadeVo();
					fDictVo.setId(fVo.getId());
					fDictVo.setTitle(majorField.getCode() + "-" + majorField.getTitle() );
					fDictVo.setParentId(fVo.getParentId());
					fDictVo.setIsLeaf(fVo.getIsLeaf());
					dictList.add(fDictVo);
				}
			}//for
			map.put(majorEntry.getKey(), list);
			map.put(majorEntry.getKey() + "-" + OPTION_DICT, dictList);
		}
		
		return map;
	}
	
	/**
	 * 校区-系部关联
	 * @RP: 	
	 * @param	
	 * @return		
	 * @create 	wangkang 2016年12月13日 上午10:59:31
	 * @modify
	 */
	public Map<String, List<DictCascadeVo>> getDepartmentsByCampusId(){
		Map<String, List<DictCascadeVo>>  map = new HashMap<>();
		@SuppressWarnings("unchecked")
		HashMap<String, DictEduCommonCampus> campusMap = (HashMap<String, DictEduCommonCampus>) cacheComponent.resource(CacheComponent.KEY_CAMPUS);
		@SuppressWarnings("unchecked")
		HashMap<String, DictEduCommonDepartment> departmentMap =(HashMap<String, DictEduCommonDepartment>) cacheComponent.resource(CacheComponent.KEY_DEPARTMENT);
		
		for (Map.Entry<String, DictEduCommonCampus> campusEntry : campusMap.entrySet()){
			List<DictCascadeVo> list = new LinkedList<DictCascadeVo>();
			List<DictCascadeVo> dictList = new LinkedList<DictCascadeVo>();
			int parentId = Integer.parseInt(campusEntry.getKey());
			for (Map.Entry<String, DictEduCommonDepartment> entry : departmentMap.entrySet()){
				DictEduCommonDepartment dept = entry.getValue();
				if (dept.getCampusId().intValue() == parentId){
					DictCascadeVo fVo = new DictCascadeVo();
					fVo.setId(dept.getId() + "");
					fVo.setTitle(dept.getTitle());
					fVo.setParentId(parentId + "");
					fVo.setIsLeaf(Constants.DICT_COMMON_YES);
					list.add(fVo);
					
					DictCascadeVo fDictVo = new DictCascadeVo();
					fDictVo.setId(fVo.getId());
					fDictVo.setTitle(dept.getCode() + "-" + dept.getTitle() );
					fDictVo.setParentId(fVo.getParentId());
					fDictVo.setIsLeaf(fVo.getIsLeaf());
					dictList.add(fDictVo);
				}
			}//for
			map.put(campusEntry.getKey(), list);
			map.put(campusEntry.getKey() + "-" + OPTION_DICT, dictList);
		}
		
		return map;
	}
	
	@Override
	public List<DictCascadeVo> getRewardsCategoryCascadeInfo(int parentId){
		List<DictCascadeVo> list = new LinkedList<DictCascadeVo>();
		@SuppressWarnings("unchecked")
		HashMap<String, DictEduGradRewardsCategory> rewardsCategoryMap =(HashMap<String, DictEduGradRewardsCategory>)  cacheComponent.resource(CacheComponent.KEY_DICT_GRAD_REWARDS_CATEGORY);
		for (Map.Entry<String, DictEduGradRewardsCategory> entry : rewardsCategoryMap.entrySet()){
			DictEduGradRewardsCategory dictEduGradRewardsCategory = entry.getValue();
			if (dictEduGradRewardsCategory.getGradRewardsTypeId().intValue() == parentId){
				DictCascadeVo vo = new DictCascadeVo();
				vo.setId(dictEduGradRewardsCategory.getId() + "");
				vo.setTitle(dictEduGradRewardsCategory.getTitle());
				vo.setParentId(parentId + "");
				list.add(vo);
			}
		}//for
		return list;
	}
	
	@Override
	public List<DictCascadeVo> getRewardsReasonCascadeInfo(int parentId){
		List<DictCascadeVo> list = new LinkedList<DictCascadeVo>();
		@SuppressWarnings("unchecked")
		HashMap<String, DictEduGradRewardsReason> rewardsCategoryMap =(HashMap<String, DictEduGradRewardsReason>)  cacheComponent.resource(CacheComponent.KEY_DICT_GRAD_REWARDS_REASON);
		for (Map.Entry<String, DictEduGradRewardsReason> entry : rewardsCategoryMap.entrySet()){
			DictEduGradRewardsReason dictEduGradRewardsReason = entry.getValue();
			if (dictEduGradRewardsReason.getGradRewardsTypeId().intValue() == parentId){
				DictCascadeVo vo = new DictCascadeVo();
				vo.setId(dictEduGradRewardsReason.getId() + "");
				vo.setTitle(dictEduGradRewardsReason.getTitle());
				vo.setParentId(parentId + "");
				list.add(vo);
			}
		}//for
		
		return list;
	}
}