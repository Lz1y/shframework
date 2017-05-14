package com.shframework.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: 日期工具类</p>
 * @author zhangjk
 * @version 1.0 
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	public static String patternyyyyMMdd = "yyyyMMdd";
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat DateFormatWithoutTime = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final int FIRST_DAY_OF_WEEK = 1;
	
	public static boolean equals(Date date1, Date date2){
		if (date1 == date2){
			return true;
		}
		if (date1==null || date2 == null){
			return false;
		}
		return date1.getTime() == date2.getTime();
	}
	
	/**
	 * 将日期类型转化为yyyy-MM-dd　HH:mm:ss格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToSimpleDate(Date date) {
		return simpleDateFormat.format(date);
	}

	/**
	 * 将日期类型转化为yyyy-MM-dd格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToString(Date date) {
		return DateFormatWithoutTime.format(date);
	}
	
	/**
	 * 将日期类型转化为指定格式字符串
	 * 
	 * @param date
	 *            日期
	 * @return 字符串
	 */
	public static String formatDateToString(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * <p>Title: formatStringToSimpleDate</p>
	 * <p>Description: yyyy-MM-dd　HH:mm:ss</p>
	 * @param date
	 * @return
	 */
	public static Date formatStringToSimpleDate(String date) {
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("e:{}", e);
		}
		return new Date();
	}
	
	public static Date formatStringToSimpleDate(String date,String pattern) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("e:{}", e);
		}
		return new Date();
	}
	

	/**
	 * 将yyyy-MM-dd格式字符串，转成Date对象
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date formatStringToDate(String date) {
		try {
			return DateFormatWithoutTime.parse(date);
		} catch (ParseException e) {
			logger.error("e:{}", e);
		}
		return new Date();
	}
	
	/**
	 * <p>倒学生信息excel数据时，多格式解析日期字符串</p>
	 * @param String 
	 * @return Date
	 * @throws ParseException 
	 */
	public static Date formatStringToDate2(String date) throws ParseException {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patternyyyyMMdd);
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("e:{}", e);
			throw e;
		}
	}

	/**
	 * 根据pattern,将String转换成Timestamp
	 * 
	 * @param pattern
	 *            yyyy-MM-dd HH:mm:ss
	 * @param dateString
	 * @return Timestamp
	 */
	public static Timestamp convertStringToTimestamp(String pattern, String dateString) {
		Timestamp newTimestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			newTimestamp = new Timestamp(sdf.parse(dateString).getTime());
		} catch (ParseException e) {
			logger.error("{}", e);
		}
		return newTimestamp;
	}


	/**
	 * 当前时间分钟的偏移
	 * 
	 * @param minute
	 * @return 偏移的时间
	 */
	public static Date getMinuteOffset(int minute) {
		return addMinutes(new Date(), minute);
	}

	/**
	 * 当前时间日期的偏移
	 * 
	 * @param offset
	 *            偏移
	 * @return 偏移的时间
	 */
	public static Date getDateOffset(int offset) {
		return addDays(new Date(), offset);
	}

	/**
	 * 获取今天，不带时间
	 * 
	 * @return 获取今天，不带时间
	 */
	public static Date getToday() {
		Date date = new Date();
		return truncate(date, Calendar.DATE);
	}

	/**
	 * 获得昨天，不带时间
	 * 
	 * @return 获取昨天，不带时间
	 */
	public static Date getYesterday() {
		Date date = new Date();

		Date ret = truncate(date, Calendar.DATE);
		ret = addDays(ret, -1);
		return ret;
	}

	/**
	 * 获得明天，不带时间
	 * 
	 * @return 获得明天，不带时间
	 */
	public static Date getTomorrow() {
		Date date = new Date();
		Date ret = truncate(date, Calendar.DATE);
		ret = addDays(ret, 1);
		return ret;
	}

	/**
	 * 获得明天，不带时间
	 * 
	 * @return 获得明天，不带时间
	 */
	public static Date getDateBySecond(long sec) {
		Date date = new Date();
		sec *= 1000;
		date.setTime(sec);
		return date;
	}
	
	/**
	 * <p>Title: getSubtractYear</p>
	 * <p>Description: 两个时间相差的年</p>
	 * @param startDate
	 * @param endDate
	 * @return 两个时间相差的年
	 */
	public static int getSubtractYear(Date startDate,Date endDate){
		if(startDate==null || endDate==null){
			return 0;
		}
		
		try {
			Calendar start = Calendar.getInstance();
			start.setTime(startDate);
			Calendar end = Calendar.getInstance();
			end.setTime(endDate);
			return start.get(Calendar.YEAR)-end.get(Calendar.YEAR);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return 0;
	}
	
	/**
	 * 两个时间的间隔秒数
	 * @param startDate 小时间
	 * @param endDate   大时间
	 * @return
	 */
	public static Long getSecondsBetweenDates(Date startDate,Date endDate){
		if(startDate == null || endDate == null ){
			return null;
		}
		return  (endDate.getTime() - startDate.getTime())/1000;
	}
	
	/**
	 * 两个时间的间隔分钟数
	 * @param startDate 小时间
	 * @param endDate   大时间
	 * @return
	 */
	public static Long getMinutesBetweenDates(Date startDate,Date endDate){
		if(startDate == null || endDate == null ){
			return null;
		}
		return  (endDate.getTime() - startDate.getTime())/(1000*60);
	}
	
	
	/**
	 * 两个时间的间隔小时数
	 * @param startDate 小时间
	 * @param endDate   大时间
	 * @return
	 */
	public static Long getHoursBetweenDates(Date startDate,Date endDate){
		if(startDate == null || endDate == null ){
			return 0L;
		}
		return  (endDate.getTime() - startDate.getTime())/(1000*60*60);
	}
	
	/**
	 * 两个时间的间隔天数
	 * @param startDate 小时间
	 * @param endDate   大时间
	 * @return
	 */
	public static Long getDayBetweenDates(Date startDate,Date endDate){
		if(startDate == null || endDate == null ){
			return 0L;
		}
		return  (endDate.getTime() - startDate.getTime())/(1000*60*60*24);
	}
	
	/**
	 * 通过小时计算天数
	 * @param hours
	 * @return
	 */
	public static Double getHoursToDay(long hours){
		double day1,day2;
		if(hours <= 0){
			return 0.0d;
		}
		day1 = hours / 24;
		day2 = hours % 24;
		if(day2>=0 && day2<6){
			day2 = 0;
		}
		else if(day2>=6 && day2<18){
			day2 = 0.5;
		}
		else if(day2>=18 && day2<24){
			day2 = 1;
		}
		return  day1 + day2;
	}
	
	
	/**
	 * 通过分钟计算小时
	 * @param hours
	 * @return
	 */
	public static Double getMinutesToHours(long minutes){
		double minutes1,minutes2;
		if(minutes <= 0){
			return 0.0d;
		}
		minutes1 = minutes / 60;
		minutes2 = minutes % 60;
		if(minutes2>=30){
			minutes2 = 1;
		}
		else{
			minutes2 = 0;
		}
		return  minutes1 + minutes2;
	}
	
	
	public static String getFmtDateStrToString(String srcDate, String fmtpattn){
		String resString="";
		if (srcDate != null) {
			try {
				Date tmpDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("ENGLISH", "CHINA")).parse(srcDate);
				SimpleDateFormat sDateFormat = new SimpleDateFormat(fmtpattn);
				resString = sDateFormat.format(tmpDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return resString;
	}

	
	/**原先 WeekUtil.java 中的内容**/
	public static int getWeekNum(Date startDate, Date endDate, int startWeek) {
		List<Map<String, Object>> weekList =  getWeekDetail(startDate, endDate, startWeek);
		
		if (weekList == null ){
			return 0;
		} else {
			return weekList.size();
		}
	}
	
	/**
	 * 根据开始时间、结束时间、周首日获得教学周信息
	 * @param startDate  开始时间
	 * @param endDate    结束时间
	 * @param startWeek  周首日：1   周一，0   周日
	 * @return List<Map> 
	 * 		   Map中key1: seq 周数，对应value类型为Integer;
	 * 		   key2: startDate 周开始时间，对应value类型为Date;
	 * 		   key3: endDate 周结束时间,对应value类型为Date
	 * @return null 开始时间大于结束时间返回null
	 * <p>modify by zhangjinkui</p>
	 * <p>
	 * while (endDate.compareTo(lastDay)>=0) { 改为：while (endDate.compareTo(lastDay)>0) {
	 * 原因：如果最后一个周次 就只有一天且是周日，周首日为周一时，会多一个周，多一天。
	 * 例如：2015-2016学年  第一学期 ,2015-09-03  ~ 2016-01-17；最后一个周次是多出来的一周这一天是 2016-01-18
	 *</p>
	 * */
	public static List<Map<String, Object>> getWeekDetail(Date startDate, Date endDate, int startWeek) {
		
		List<Map<String, Object>> eduweekList = new ArrayList<Map<String,Object>>();
		Date lastDay = getLastdayOfWeekByDate(startDate, startWeek);//取得 开始时间所在周的最后一天

		if (startDate.compareTo(endDate) > 0) {
			return null;
		}else {
			Map<String, Object> eduweekMap = new HashMap<String, Object>();
			eduweekMap.put("seq", 1);
			eduweekMap.put("startDate", StringUtils.formatDate(startDate));
			if (lastDay.compareTo(endDate) > 0) {
				eduweekMap.put("endDate", StringUtils.formatDate(endDate));
			}else {
				eduweekMap.put("endDate", StringUtils.formatDate(lastDay));
			}
			eduweekList.add(eduweekMap);
			Date firstDay = DateUtils.addDays(lastDay, 1);
			Integer i = 0;
			while (endDate.compareTo(lastDay)>0) {
				eduweekMap = new HashMap<String, Object>();
				lastDay = getLastdayOfWeekByDate(firstDay, startWeek);
				eduweekMap.put("seq", i + 2);
				eduweekMap.put("startDate", StringUtils.formatDate(firstDay));
				if (lastDay.compareTo(endDate) > 0) {
					eduweekMap.put("endDate", StringUtils.formatDate(endDate));
				}else {
					eduweekMap.put("endDate", StringUtils.formatDate(lastDay));
				}
				eduweekList.add(eduweekMap);
				firstDay = DateUtils.addDays(lastDay, 1);
				i++;
			} 
				
			return eduweekList;
		}
		
		
	}
	
    
	/**
	 * 根据周首日获得给定日期所在周的最后一天
	 * @param time 日期
	 * @param startWeek 周首日：1   周一，0   周日
	 * @return 日期
	 * */
	public static Date getLastdayOfWeekByDate(Date time, int startWeek) {
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(time); 
		int day = cal.get(Calendar.DAY_OF_WEEK);
		Date date = null;
		//周首日是周日
		if (startWeek == 0) {
			if (day == 7) {
				date = time;
			}else {
				date = DateUtils.addDays(time, 7 - day);
			}
		}else {
			if (day == 1) {
				date = time;
            } else {
                date = DateUtils.addDays(time, 8 - day);
            }
		}
		
		return date;
	}
	
    /*
     * 判断给定的日期是周几
     * @param anyDate 日期
     * @return 周几
     * 
     * */
	public static String getWeekday(Date anyDate) {
		
        SimpleDateFormat sdw = new SimpleDateFormat("E");  
        
        return sdw.format(anyDate);
	}

    /**
     * @功能:获取当前时间所在年的周数
     * @param Date      日期
     * @param startWeek 周首日
     * @return boolean
     */
    public static int getWeekOfYear(Date date, int startWeek) {
    	
        Calendar c = new GregorianCalendar();
        if (startWeek == 0) {
        	c.setFirstDayOfWeek(Calendar.SUNDAY);
		}else {
			c.setFirstDayOfWeek(Calendar.MONDAY);
		}
        c.setMinimalDaysInFirstWeek(1);//第一周最少天数
        c.setTime(date);
        
        return c.get(Calendar.WEEK_OF_YEAR);
    }
    
	public static List<String> process(Date date1, Date date2){
		List<String> tmpList = new ArrayList<String>();
    	try {
    		String tmp;
    		String dateFormat = "yyyy-MM-dd";
    		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    		tmpList.add(sdf.format(date1));
    		
    		tmp = sdf.format(date1.getTime() + 3600*24*1000);
    		while(tmp.compareTo(sdf.format(date2))<=0){        	        
    			tmpList.add(tmp);   
    			tmp = sdf.format(sdf.parse(tmp).getTime()+ 3600*24*1000);
    		}
	    } catch (ParseException e) {
			e.printStackTrace();
		}
	      return tmpList;
	}

	/**
	 * 判断 time 是否在 start 和 end 之间 （start <= time <= end）
	 * @param	
	 * @return	0:否  1:是	
	 * @throws ParseException 
	 */
	public static int isBetween(Date start, Date end,Date time){
		if (time.getTime() >= start.getTime() && time.getTime() <= end.getTime()) {
			return 1;
		} 
		return 0;
	}
	
	/**
	 * 比较2个时间的大小，注意格式
	 * @author 	wangkang
	 */
	public static int compareDate(Date dt1, Date dt2) {
		if (dt1.getTime() > dt2.getTime()) {
			return 1;
		} else if (dt1.getTime() < dt2.getTime()) {
			return -1;
		}
		return 0;
	}
	public static int compareDate(Date dt1, Date dt2,String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date date1 = sdf.parse(sdf.format(dt1));
			Date date2 = sdf.parse(sdf.format(dt2));
			
			if (date1.getTime() > date2.getTime()) {
				return 1;
			} else if (date1.getTime() < date2.getTime()) {
				return -1;
			}
			return 0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * 取得当前时间前后几个月的日期
	 */
	public static Date getLessOrGreaterDate(int offset) {
		Date dNow = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);
		calendar.add(Calendar.MONTH, offset); //设置为 ±offset 个月
		return calendar.getTime();
	}
	/**
	 * <p>取得给定日期，前后<code>offset<code>个天的日期
	 * @param originalDate the given Date "yyyy-MM-dd"
	 * @param offset
	 * @return targetDate
	 */
	public static String getOffsetDate(String originalDate,int offset) {
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(formatStringToDate(originalDate));
		calendar.add(Calendar.DATE, offset); //设置为 ±offset 个月
		return formatDateToString(calendar.getTime());
	}
	
	/**
	 * 取得当前时间前后<code>offset<code>个月的日期
	 * @param offset
	 * @return
	 */
	public static String getLessOrGreaterByNowDate(int offset) {
		Calendar  gr = new GregorianCalendar();
		gr.add(Calendar.MONTH, offset); //设置为 ±offset 个月
		return formatDateToString(gr.getTime());
	}
	
	/**
	 * 
	 * <p>calendar为空则去当前的</p>
	 * @param calendar
	 * @return
	 */
    public static String findYearMonth(Calendar calendar)
    {
        int year;
        int month;
        String date;
        if(null==calendar)calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        date = year + "" + ( month<10 ? "0" + month : month);
        return date;
    }
    
    public static String findYearMonthZh(Calendar calendar)
    {
        int year;
        int month;
        String date;
        if(null==calendar)calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        date = year + "-" + ( month<10 ? "0" + month : month);
        return date;
    }
    
	/**
	 * 获取当前年份
	 * @param year
	 * @return
	 */
	public static int getCurrYear() {
		return getCurrYear(0);
	}
	
	/**
	 * 获取当前年偏移量的年份
	 * @param offset
	 * @return
	 */
	public static int getCurrYear(int offset){
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR) + offset;
		return currentYear;
	}
	
	/**
	 * 获取当前年份前后五年年份集合
	 * @return
	 */
	public static List<Integer> getCurrYearAfterOrBeforeNYearList(int nYear){
		List<Integer> yearList = new ArrayList<Integer>();
		for(int i = nYear;i >= 0;i --){
			yearList.add(getCurrYear(-i));
		}
		for(int i = 1;i <= nYear;i ++){
			yearList.add(getCurrYear(i));
		}
		return yearList;
	}
}