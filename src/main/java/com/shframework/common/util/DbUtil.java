package com.shframework.common.util;

public class DbUtil {

	private static String SPLIT_MARK = ";";
	
	public static String getCommentTrim(String origStr){
		if (StringUtils.isNotBlank(origStr)){
			origStr = StringUtils.substringBefore(origStr, SPLIT_MARK);
		}
		return origStr;
	}
	
	public static void main(String[] args){
		System.out.println(getCommentTrim(null));
		System.out.println(getCommentTrim(""));
		System.out.println(getCommentTrim("[字典]行政区"));
		System.out.println(getCommentTrim("[字典]行政区划表; InnoDB free: 5120 kB"));
		System.out.println(getCommentTrim("[字典]行政区划表; InnoDB free: 5120 kB[字典]行政区划表; InnoDB free: 5120 kB"));
		
	}
}
