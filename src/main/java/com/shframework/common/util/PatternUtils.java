package com.shframework.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 * @author OneBoA
 *
 */
public class PatternUtils {

	/**
	 * 是否数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (Pattern.compile("[0-9]*").matcher(str).matches()) 
			return true;
		else
			return false;
	}
	
	/**
	 * 返回包含的中文数量
	 * @param str
	 * @return
	 */
	public static int cnCount(String str) {
		int count = 0;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				count = count + 1;
			}
		}
		return count;
	}
 
}
