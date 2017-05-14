package com.shframework.common.util;

/**
 * 目的是获取删除的记录中存在唯一索引的列的替换值
 * @author RanWeizheng
 *
 */
public class RecordDeleteUtil {
	private static  final String SPECIAL_MARK="^";
	
	/**
	 * l=
	 * @param origStr
	 * @param primaryKey
	 * @param length 最大允许长度， 建议 至少大于 primaryKey长度 + 1 ，否则可能结果会不太尽如人意 
	 * @return
	 */
	public static String getDelValue(int primaryKey, int length, String origStr){
		int origStrLength = 0;
		if (origStr!=null){
			origStrLength = origStr.length();
		}
		int pkLength = String.valueOf(primaryKey).length();
		
		if ((origStrLength + pkLength + 1) >length){//超长
			//存在极端情况 pkLength + 1 >= length
			if (pkLength + 1 >= length){
				return StringUtils.substring(SPECIAL_MARK + primaryKey, 0, length);
			}
			origStr = StringUtils.substring(origStr, 0, length-pkLength-1);
		} 
		return origStr + SPECIAL_MARK + primaryKey;
	}
	
}
