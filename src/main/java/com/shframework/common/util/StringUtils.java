package com.shframework.common.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static boolean isChinese(char c) {
	      return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
	}
	
	public static boolean containsAny(String inputString, String[] items) {
		for (int i = 0; i < items.length; i++) {
			if (inputString.contains(items[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static StringBuffer createStringBufferSingeleton() {
		return new StringBuffer();
	}
	
	public static void clearStringBuffer(StringBuffer sb) {
		if (sb != null && sb.length() > 0) {
			sb.delete(0, sb.length());
		}
	}
	
	/**
	 * 除去特殊字符
	 * @param value
	 */
	public static String replaceValueSpecialCharacter(String value) {
		if (value.indexOf("'") != -1 || value.indexOf("\"") != -1 || value.indexOf("%") != -1) {
			value = value.replace("'", "");
			value = value.replace("\"", "");
			value = value.replace("%", "");
		}
		return value;
	}
	
	//格式化日期
	public static String formatDate(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(date);
	}
	
	public static Date toDate(String s) {
		if (s == null || s.equals(""))
			return null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 编码转换(中文乱码)
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年8月4日 下午2:36:57
	 */
	public static String stringEncoding(String s){
		try {
			s = new String(s.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * html字符串处理
	 * @param text
	 * @return
	 * modify by :jiangnan
	 */
	public static String textToHtml(String text) {
		/* 增加flag钩子，目的是保留首位空格的转义。
		 * 主要是防止单词+&nbsp;在HTML中当成一个单词处理
		 * */
		boolean flag = true;
		
		String htmlStr = "";
		String encode = "";
		if (StringUtils.isEmpty(text)) {
			return htmlStr;
		}
		
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if(flag && ch != 32) {
				flag= false;
			}
			switch (ch) {
			case '<':
				encode = "&lt;";
				break;
			case '>':
				encode = "&gt;";
				break;
			case '\"':
				encode = "&quot;";
				break;
			case '&':
				encode = "&amp;";
				break;
			case 10:			//对\n处理 同\r
			case 13:
				encode = "<br>";
				break;
			
			case 32:
				if(flag) {
					encode = "&nbsp;";
				}else{
					encode = "" + ch;
				}
				break;
			default:
				encode = "" + ch;
				break;
			}
			htmlStr += encode;
		}
		return htmlStr;
	}
	
	/**
	 * html展示：回车转化为br
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年9月8日 下午3:54:42
	 */
	public static String enterToBr(String str){
		return textToHtml(str.replace("\n", ""));
	}
	
	/**
	 * html展示：br转化为回车
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年9月8日 下午3:54:42
	 */
	public static String brToEnter(String str){
		return str.replace("<br>", "\r\n");
	}
	
	/**
	 * 把回车换行符 转换为"<br>"
	 * @param text
	 * @return
	 */
	public static String textToBr(String text) {
		/* 增加flag钩子，目的是保留首位空格的转义。
		 * 主要是防止单词+&nbsp;在HTML中当成一个单词处理
		 * */
		boolean flag = true;
		
		String htmlStr = "";
		String encode = "";
		if (StringUtils.isEmpty(text)) {
			return htmlStr;
		}
		
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if(flag && ch != 32) {
				flag= false;
			}
			switch (ch) {
			case 10:			//对\n处理 同\r
			case 13:
				encode = "<br>";
				break;
			
			case 32:
				if(flag) {
					encode = "&nbsp;";
				}else{
					encode = "" + ch;
				}
				break;
			default:
				encode = "" + ch;
				break;
			}
			htmlStr += encode;
		}
		return htmlStr;
	}
	
	/**
	 * 分割字符串，并转换成List
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年8月4日 下午2:37:16
	 */
	public static List<Integer> strToList(String items,String separator){
		
		List<Integer> list = new ArrayList<Integer>();
		list.clear();
		
		if(StringUtils.isNotBlank(items)){
			if(items.indexOf(separator) > 0){
				List<String> tmpList = Arrays.asList(items.split(separator));
				if(tmpList != null && tmpList.size() > 0){
					for(String t:tmpList){
						list.add(Integer.valueOf(t));
					}
				}
			}else{
				list.add(Integer.valueOf(items));
			}
		}
		return list;
	}
	
	public static List<String> strToListForStr(String items,String separator){
		
		List<String> list = new ArrayList<String>();
		list.clear();
		
		if(StringUtils.isNotBlank(items)){
			if(items.indexOf(separator) > 0){
				List<String> tmpList = Arrays.asList(items.split(separator));
				if(tmpList != null && tmpList.size() > 0){
					for(String t:tmpList){
						list.add(t);
					}
				}
			}else{
				list.add(items);
			}
		}
		return list;
	}
	
	/**
	 * 将List转换成字符串,用分隔符连接
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年8月4日 下午2:37:16
	 */
	public static String listToStr(List<String> list,String separator){
		
		StringBuffer str = new StringBuffer();
		if(list != null && list.size() > 0){
			for(String s:list){
				if(StringUtils.isNotBlank(s)){
					str.append(s).append(separator);
				}
			}
		}
		
		if(StringUtils.isBlank(str.toString())){
			return null;
		}
		return str.toString().substring(0, str.toString().length() - separator.length());
	}
	
	public static String listToStrForInt(List<Integer> list,String separator){
		
		StringBuffer str = new StringBuffer();
		if(list != null && list.size() > 0){
			for(Integer s:list){
				if(s != null){
					str.append(s).append(separator);
				}
			}
		}
		
		if(StringUtils.isBlank(str.toString())){
			return null;
		}
		return str.toString().substring(0, str.toString().length() - separator.length());
	}
	
	/**
	 * 删除list中的空数据
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年8月6日 下午6:28:44
	 */
	public static List<Integer> deleteListNullData(List<Integer> list){
		
		List<Integer> newList = new ArrayList<Integer>();
		newList.clear();
		
		int size = list.size();
		if(list != null && size > 0){
			for(int i=0;i<size;i++){
				if(list.get(i) != null){
					newList.add(list.get(i));
				}
			}
		}
		return newList;
	}
	
	/**
	 * 删除list中的重复数据
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年8月6日 下午6:28:44
	 */
	public static List<Integer> deleteListRepeatData(List<Integer> list){
		
		int size = list.size();
		if(list != null && size > 0){
			for(int i=0;i<size-1;i++){
				Integer value1 = list.get(i);
				for(int j=(i+1);j<size;j++){
					
					Integer value2 = list.get(j);
					if(value1 != null && value2 != null 
							&& value1.intValue() == value2.intValue()){
						list.remove(j);
						--j;
						--size;
					}
				}
			}
		}
		return list; 
	}
	
	public static List<String> deleteListRepeatDataStr(List<String> list){
		
		int size = list.size();
		if(list != null && size > 0){
			for(int i=0;i<size-1;i++){
				String value1 = list.get(i);
				for(int j=(i+1);j<size;j++){
					
					String value2 = list.get(j);
					if(value1.equals(value2)){
						list.remove(j);
						--j;
						--size;
					}
				}
			}
		}
		return list; 
	}
	
	
	public static List<Integer> strListToIntList(String[] items){
		List<Integer> list = new ArrayList<Integer>();
		list.clear();
		if(null != items && items.length >0){
			for(int i=0;i<items.length;i++){
				list.add(Integer.valueOf(items[i]));
			}
		}
		return list;
	}
	
	/**
	 * 取得字符串集合的第index个元素，eg:["1_a_b","2_c_d"...] --> [1,2...]
	 */
	public static List<Integer> getFirstItemByList(List<String> list,String separator,int index){
		
		List<Integer> newList = new ArrayList<Integer>();
		newList.clear();
		if(list != null && list.size() > 0){
			for(String s:list){
				if(StringUtils.isNotBlank(s) && s.indexOf(separator) > 0){
					String[] sArray = s.split(separator);
					newList.add(Integer.valueOf(sArray[index]));
				}
			}
		}
		return newList;
	}
	
	/**
	 * 查找并返回List中的重复数据 ["1_1","1_2","1_1","1_2","1_3"] --> ["1_1","1_2"]
	 */
	public static List<String> getListRepeatStrData(List<String> list){
		
		List<String> newList = new ArrayList<String>();
		newList.clear();
		List<String> newList2 = new ArrayList<String>();
		newList2.clear();
		
		int size = list.size();
		if(list != null && size > 0){
			for(int i=0;i<size;i++){
				String value1 = list.get(i).trim();
				if(newList.indexOf(value1) == -1){
					newList.add(value1);
				}else{
					newList2.add(value1);
				}
			}
		}
		return deleteListRepeatDataStr(newList2); 
	}
	
	/**
	 * 比较2个List的值是否相同
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2016年1月27日 下午3:44:53
	 */
	public static boolean isListSame(List<Integer> list1,List<Integer> list2){
		
		boolean isSame = true;
		
		if(list1 == null || list2 == null || list1.size() != list2.size()){
			isSame = false;
		}else{
			Collections.sort(list1);
			Collections.sort(list2);
			int size = list1.size();
			for(int i=0;i<size;i++){
				Integer item1 = list1.get(i);
				Integer item2 = list2.get(i);
				if(item1.intValue() != item2){
					isSame = false;
					break;
				}
			}
		}
		return isSame; 
	}
	
	/**
	 * 使用指定的list 替换List中的指定值
	 * @param origList
	 * @param replaceList
	 * @param replaceKey
	 * @author RanWeizheng
	 * @date 2016年2月18日  作为通用方法迁移至此
	 */
	public static <T> void replaceListVaule(List<T> origList, List<T> replaceList, T replaceKey){
		if (origList.indexOf(replaceKey) <= 0){
			return;
		}
		for (T one : replaceList){
			origList.add(origList.indexOf(replaceKey), one);
		}
		origList.remove(replaceKey);
	}
	
	/**
	 * 删除字符串中重复的数据后返回字符串
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2016年4月6日 上午11:45:06
	 */
	public static String deleteStrRepeatData(String str,String separator){
		List<String> list = deleteListRepeatDataStr(strToListForStr(str,","));
		String s = listToStr(list,separator);
		return s;
	}
	
	public static String deleteSpace(String str){
		return str.replace(" ", "");
	}
	
	/**
	 * 删除List中的指定值元素，改变size
	 * @RP: 	
	 * @param	
	 * @return		
	 * @create 	wangkang 2016年5月30日 下午6:53:44
	 * @modify
	 */
	public static List<String> deleteListValue(List<String> list,String value){
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				if(list.get(i).equals(value)){
					list.remove(list.get(i));
					i--;
				}
			}
		}
		return list;
	}
	public static List<Integer> deleteListValue(List<Integer> list,Integer value){
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				if(list.get(i).intValue() == value.intValue()){
					list.remove(list.get(i));
					i--;
				}
			}
		}
		return list;
	}
	
	/**
	 * <p>list1 中是否包含 list2 中的元素，如果 isContain=1 包含，返回List<冲突的值>。</p>
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static Map<String,Object> isContain(List<String> list1,List<String> list2){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		String isContain = "false";
		List<String> returnList = new ArrayList<String>();
		if(list1 == null || list1.size() == 0 && list2 != null && list2.size() > 0){
			returnList.addAll(list2);
			returnMap.put("isContain", isContain);
			returnMap.put("returnList", returnList);
			return returnMap;
		}
		if(list1 != null && list1.size() > 0 && list2 != null && list2.size() > 0){
			for(String s:list2){
				if(list1.contains(s)){
					isContain = "true";
					returnList.add(s);
				}
			}
		}
		if(returnList != null && returnList.size() > 0){
			returnMap.put("isContain", isContain);
			returnMap.put("returnList", returnList);
			return returnMap;
		}else{
			returnMap.put("isContain", isContain);
			returnMap.put("returnList", null);
			return returnMap;
		}
	}

	/**
	 * PageSupport 只用来解析原本是Integer的值
	 * @param object
	 * @return
	 */
	public static Integer parseObject(Object object) {
		if (null != object)
			return Integer.parseInt(String.valueOf(object));
		return null;
	}
	/**
	 * <p>生成印刷单 使用
	 * eg:YSD-16110010
	 * @param src
	 * @param padding
	 * @param length
	 * @return
	 * @author zhangjk
	 */
	public static String rightAlign(String src, char padding, int length){
		int len = 0;
		try{
			src = filter(src);
			if ((len = src.getBytes().length) > length){
				return src.substring(len - length);
			}
			else if (len == length) return src;
			len = length - len;
			return (loop(padding, len) + src);
		}
		catch (Exception e){
			System.out.println("length exceed constraint, truncated: " + src + "(" + len + "): " + length);
			return src;
		}
	}
	
	public static String filter(String s){
		return s == null ? "" : s;
	}
	public static String loop(char ch, int number){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < number; i++, sb.append(ch));
		return sb.toString();
	}
	
	/**
	 * 剔除list中重复元素，并按照原有list顺序返回剔重后结果
	 * @author Norris
	 */
	public static <T> List<T> removeListDuplicateWithOrder(List<T> list) {
		return new ArrayList<T>(new LinkedHashSet<T>(list));
	}
	
	/**
	 * 验证excel表格的某列是否为空(包括一些空字符串)
	 * @param row
	 * @return
	 */
	public static boolean isBlankRow(Row row){
        if(row == null) return true;
        boolean result = true;
        for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
        	Cell cell = row.getCell(i);
            String value = "";
            if(cell != null){
                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = String.valueOf((int) cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    value = String.valueOf(cell.getCellFormula());
                    break;
                //case Cell.CELL_TYPE_BLANK:
                //    break;
                default:
                    break;
                }
                 
                if(!value.trim().equals("")){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }	
	
	public static Integer getMinIndex(List<Integer> list){
		Integer index = null;
		if(list != null && list.size() > 0){
			Integer min = Collections.min(list);
			int size = list.size();
			for (int i=0;i<size;i++) {
				if(min.intValue() == list.get(i)){
					index = i;
				}
			}
		}
		return index;
	}
}
