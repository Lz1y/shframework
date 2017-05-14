package com.shframework.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.Validate;


/**
 *  数据格式校验工具类
 * @author zhangjk
 *
 */
public class ValidateUtil extends Validate{

	public static final String REGX_MOBILE="^0?\\d{11}$";
	public static final String REGX_PHONE="^\\(?\\d{3,4}[-\\)]?\\d{7,8}$";
	public static final String REGX_MAIL="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	public static final String REGX_ZINTORDOT1="^([1-9]\\d*|0)(\\.\\d{0,1})?$";
	public static final String REGX_ZINTORDOT2="^([1-9]\\d*|0)(\\.\\d{0,2})?$";
	public static final String REGX_ZINT="^[1-9]\\d*$";
	public static final String REGX_ZINTORZERO="^([1-9]\\d*|0)$";
	public static final String REGX_EVENINT="^\\d*[02468]$";
	public static final String REGX_DOT5="^([0-9]\\d*)(?:\\.[05]0?)?$";
	public static final String REGX_QQ="^[1-9]\\d{4,15}$";
	public static final String REGX_WEIXIN="^[a-zA-Z][a-zA-Z0-9_-]{5,19}$";
	
	public static boolean length(String str,int minLen,int maxLen){
		if(StringUtils.isBlank(str) && minLen == 0 ){
			return true;
		}
		if(StringUtils.isNotBlank(str) &&str.length() >= minLen && str.length()<= maxLen){
			str = str.trim();
			return true;
		}
		return false;
	}
	
	public static boolean lengthTrim(String str,int minLen,int maxLen){
		if(StringUtils.isNotBlank(str)){
			str = str.trim();
		}
		return length(str,minLen,maxLen);
	}
	
	/**
	 * 移动电话
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str){
		if(StringUtils.isNotBlank(str)){
			return str.matches(REGX_MOBILE);
		}
		return false;
	}
	
	/**
	 * 电话格式校验
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str){
		if(StringUtils.isNotBlank(str)){
			return str.matches(REGX_PHONE);
		}
		return false;
	}
	
	/**
	 * 邮件格式校验
	 * @param str
	 * @return
	 */
	public static boolean isMail(String str){
		if(StringUtils.isNotBlank(str)){
			return str.matches(REGX_MAIL);
		}
		return false;
	}
	
	/**
	 * 日期范围校验
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException 
	 */
	public static boolean compareDateBetween(String dateStr) throws ParseException {
		boolean b = false;
		String dateBeginStr = "1900-01-01 00:00";
		String dateEndStr = "2099-12-31 23:59";
		SimpleDateFormat f = new SimpleDateFormat("EEE MMM d H:m:s z y",Locale.ENGLISH);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dateBegin = sdf.parse(dateBeginStr);
		Date dateEnd = sdf.parse(dateEndStr);
		Date date = f.parse(dateStr);
			
		int i = date.compareTo(dateBegin);
		int j = dateEnd.compareTo(date);
			
		if(i>=0 && j>=0){
			b = true;
		}else{
			b = false;
		}			
		return b;
	}
	
	
	/**
	 * Api订单导入日期范围校验
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException 
	 */
	public static boolean compareDateBetweenApi(String dateStr) throws ParseException {
		boolean b = false;
		String dateBeginStr = "1900-01-01 00:00";
		String dateEndStr = "2099-12-31 23:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dateBegin = sdf.parse(dateBeginStr);
		Date dateEnd = sdf.parse(dateEndStr);
		Date date = sdf.parse(dateStr);
			
		int i = date.compareTo(dateBegin);
		int j = dateEnd.compareTo(date);
			
		if(i>=0 && j>=0){
			b = true;
		}else{
			b = false;
		}			
		return b;
	}
	
	/**
	 * 不小于0的数字(至多保留一位小数)
	 * @param str
	 * @return
	 */
	public static boolean isZintOrDot1(String str){
		if(StringUtils.isNotBlank(str)){
			return str.matches(REGX_ZINTORDOT1);
		}
		return false;
	}
	
	/**
	 * 不小于0的数字(至多保留两位小数)
	 * @param str
	 * @return
	 */
	public static boolean isZintOrDot2(String str){
		if(StringUtils.isNotBlank(str)){
			return str.matches(REGX_ZINTORDOT2);
		}
		return false;
	}
	
	/**
	 * 正整数
	 * @param str
	 * @return
	 */
	public static boolean isZint(String str){
		if(StringUtils.isNotBlank(str)){
			return str.matches(REGX_ZINT);
		}
		return false;
	}
	
	/**
	 * 
	 * <p>非负整数</p>
	 * @param str
	 * @return
	 */
	public static boolean isZintOrZero(String str){
		if(StringUtils.isNotBlank(str)){
			return str.matches(REGX_ZINTORZERO);
		}
		return false;
	}
	
	/**
	 * 
	 * <p>偶数</p>
	 * @param str
	 * @return
	 */
	public static boolean isEvenint(String str){
		if(StringUtils.isNotBlank(str)){
			return str.matches(REGX_EVENINT);
		}
		return false;
	}
	
	/**
	 * 
	 * <p>偶数</p>
	 * @param str
	 * @return
	 */
	public static boolean isDot5(String str){
		if(StringUtils.isNotBlank(str)){
			return str.matches(REGX_DOT5);
		}
		return false;
	}
	
	/**
	 * 
	 * <p>QQ</p>
	 * @param str
	 * @return
	 */
	public static boolean isQQ(String str){
		if(StringUtils.isNotBlank(str)){
			return str.matches(REGX_QQ);
		}
		return false;
	}
	
	/**
	 * 
	 * <p>WEIXIN</p>
	 * @param str
	 * @return
	 */
	public static boolean isWeixin(String str){
		if(StringUtils.isNotBlank(str)){
			return str.matches(REGX_WEIXIN);
		}
		return false;
	}

}
