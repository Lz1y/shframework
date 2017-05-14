package com.shframework.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.shframework.modules.sys.entity.vo.MergerPerVo;

/**
 * 资源-权限 配置的读取
 * @author RanWeizheng
 *
 */
public class PurviewUtil {
			// 模板按钮的配置文件
			private static Configuration TEMPLATE_CONFIG = new Configuration("/../classes/cfg/template.purview.properties");
			// 资源可能的权限的配置文件
			private static Configuration RESOURCE_CONFIG = new Configuration("/../classes/cfg/purview.properties");
			
			public static final String TEMPLATE_FRESHMAN= "freshman";
			public static final String TEMPLATE_STUDENT= "student";
			public static final String TEMPLATE_ROLLCHANGE= "rollchange";
			public static final String TEMPLATE_PAYMENT= "payment";
			public static final String TEMPLATE_TERMENROLL= "termenroll";
			public static final String TEMPLATE_CHEATEXAMSTUDENTS= "cheatexamstudents";
			
			public static final String RESOURCE_PARENT_DEFAULT= "cfg.parentdefault";
			public static final String RESOURCE_MERGER_FLAG= "cfg.merger.flag";//是否要合并权限的标志位
			public static final String RESOURCE_MERGER_PERMISSION= "cfg.merger.permission";//要合并的权限组的名称，内容为对应配置文件中的key值
			
			//只在本资源有效的权限的匹配正则表达式
			private static String REG_LOCAL="\\[\\w+\\]";
			//不可以修改的权限的匹配正则表达式
			private static String REG_STATIC="\\<\\w+\\>";
			
			public static String[] getTemplatePurview(String key){
				String purviewStr = TEMPLATE_CONFIG.getValue(key);
				if (StringUtils.isNotBlank(purviewStr)){
					return purviewStr.split(Constants.ID_SEPARATOR);
				}else{
					return null; 
				}
			}
			
			/**
			 * 获取指定的资源可能包含的权限
			 * @param key
			 * @return
			 */
			public static String[] getResourcePurview(String key){
				String purviewStr = RESOURCE_CONFIG.getValue(key);
				if (StringUtils.isNotBlank(purviewStr)){
					return purviewStr.split(Constants.ID_SEPARATOR);
				}else{
					return null; 
				}
			}
			
			/**
			 * 获取是否要合并权限
			 * @return
			 */
			public static boolean getIsMerger(){
				String flag = RESOURCE_CONFIG.getValue(RESOURCE_MERGER_FLAG);
				if (StringUtils.isBlank(flag)){
					return false;
				}
				return Constants.COMMON_YES.equals(flag);
			}
			
			/**
			 * 获取包含合并的权限的map
			 * @return
			 */
			public static Map<String, MergerPerVo> getMergerPermissionMap(){
				String[] perArr = getResourcePurview(RESOURCE_MERGER_PERMISSION);
				if (perArr == null){
					return null;
				}
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
				int vId = -Integer.parseInt(simpleDateFormat.format(new Date()));
				Map<String, MergerPerVo> mergerMap = new HashMap<String, MergerPerVo>();
				for (String res: perArr){
					String[] perCodeArr = getResourcePurview(RESOURCE_MERGER_PERMISSION + "." + res);
					ArrayList<String> perCodeList = new ArrayList<String>();
					perCodeList.addAll(Arrays.asList(perCodeArr));
					String[] voInfo = getResourcePurview(RESOURCE_MERGER_PERMISSION + "." + res + ".info");
					if (voInfo == null || voInfo.length == 0){
						continue;
					}
					MergerPerVo vo = new MergerPerVo();
					vo.setId(vId);
					vo.setCode(res);
					vo.setTitle(voInfo[0]);
					vo.setPerCodeList(perCodeList);
					mergerMap.put(vo.getId() + "", vo);
					vId++;
				}
				return mergerMap;
			}
			
			/**
			 * 移除符合“只在本资源有效的权限”正则的值并重新组织数组
			 * @param perList
			 * @param isRemove 是否移除 “只在本级有效”的权限
			 * @return
			 */
			public static List<String> getResourcePurviewWithOutLocal(List<String> perList, boolean isRemove){
				List<String> list = new ArrayList<String>();
				if (perList != null){
					for (String per : perList){
						if (per.matches(REG_LOCAL)){
							if (!isRemove){
								list.add(StringUtils.substringBetween(per, "[", "]"));//去除 "[" "]"
							}
						} else if (per.matches(REG_STATIC)){
							if (!isRemove){
								list.add(StringUtils.substringBetween(per, "<", ">"));//去除 "[" "]"
							}
						} else {
							list.add(per);
						}//if-else
					}//for
				}//if
				return list;
			}
			
			/**
			 * 获取 符合“不可以修改的权限”正则的值并重新组织数组
			 * @param perList
			 * @return
			 */
			public static List<String> getPermissionLocal(List<String> perList){
				List<String> list = new ArrayList<String>();
				if (perList != null){
					for (String per : perList){
						if (per.matches(REG_STATIC)){
							list.add(StringUtils.substringBetween(per, "<", ">"));//去除 "<" ">"
						}
					}
				}//if
				return list;
			}
			
			/**
			 * 获取模板的权限
			 * @return
			 */
			public static Map<String, Object> getTemplatePurview(){
				Map<String, Object> purviewMap = new HashMap<String, Object>();
				purviewMap.put(TEMPLATE_FRESHMAN, getTemplatePurview(TEMPLATE_FRESHMAN));
				purviewMap.put(TEMPLATE_STUDENT, getTemplatePurview(TEMPLATE_STUDENT));
				purviewMap.put(TEMPLATE_ROLLCHANGE, getTemplatePurview(TEMPLATE_ROLLCHANGE));
				purviewMap.put(TEMPLATE_PAYMENT, getTemplatePurview(TEMPLATE_PAYMENT));
				purviewMap.put(TEMPLATE_TERMENROLL, getTemplatePurview(TEMPLATE_TERMENROLL));
				purviewMap.put(TEMPLATE_CHEATEXAMSTUDENTS, getTemplatePurview(TEMPLATE_CHEATEXAMSTUDENTS));
				
				return purviewMap;
			}
			
			/**
			 * 场地信息管理 （所在楼层）
			 * @return
			 */
			public static Map<String, Object> getClassroomPurview(){
				Map<String, Object> purviewMap = new HashMap<String, Object>();
				purviewMap.put("floor", getTemplatePurview("floor"));
				return purviewMap;
			}
			
			/**
			 * #排课节次设置（每周天数）
			 * @return
			 */
			public static Map<String, Object> getPerweekdayMap(){
				Map<String, Object> purviewMap = new HashMap<String, Object>();
				purviewMap.put("perweekday", getTemplatePurview("perweekday"));
				return purviewMap;
			}
			
			/**
			 * #排课节次设置（上午节数）
			 * @return
			 */
			public static Map<String, Object> getAmperiodMap(){
				Map<String, Object> purviewMap = new HashMap<String, Object>();
				purviewMap.put("amperiod", getTemplatePurview("amperiod"));
				return purviewMap;
			}
			
			/**
			 * #排课节次设置（下午节数）
			 * @return
			 */
			public static Map<String, Object> getPmperiodMap(){
				Map<String, Object> purviewMap = new HashMap<String, Object>();
				purviewMap.put("pmperiod", getTemplatePurview("pmperiod"));
				return purviewMap;
			}
			
			/**
			 * #排课节次设置（晚上节数）
			 * @return
			 */
			public static Map<String, Object> getNightperiodMap(){
				Map<String, Object> purviewMap = new HashMap<String, Object>();
				purviewMap.put("nightperiod", getTemplatePurview("nightperiod"));
				return purviewMap;
			}
			
			/**
			 * #排课节次设置（上午节次(共（1-6）6个节次）
			 * @return
			 */
			public static Map<String, Object> getAmperiodRealMap(){
				Map<String, Object> purviewMap = new HashMap<String, Object>();
				purviewMap.put("amperiodreal", getTemplatePurview("amperiodreal"));
				return purviewMap;
			}
			
			/**
			 * #排课节次设置（下午节次 共（7-12）6个节次）
			 * @return
			 */
			public static Map<String, Object> getPmperiodRealMap(){
				Map<String, Object> purviewMap = new HashMap<String, Object>();
				purviewMap.put("pmperiodreal", getTemplatePurview("pmperiodreal"));
				return purviewMap;
			}
			
			/**
			 * #排课节次设置（晚上节次 共（13-16）4个节次）
			 * @return
			 */
			public static Map<String, Object> getNightperiodRealMap(){
				Map<String, Object> purviewMap = new HashMap<String, Object>();
				purviewMap.put("nightperiodreal", getTemplatePurview("nightperiodreal"));
				return purviewMap;
			}
			
			public static void main(String[] args){
				System.out.println(getIsMerger());
				System.out.println(getMergerPermissionMap());
			}
	
}
