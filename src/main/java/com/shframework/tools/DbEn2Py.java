/**
 * @description 
 * @author Josh Yan
 * @version 1.0
 * @datetime 2014年12月11日 下午6:05:31
 */
package com.shframework.tools;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Josh
 *
 */
public class DbEn2Py {
	private static String[] xmlExt = {"xml", "XML"};
	private static String[] sqlExt = {"sql", "SQL"};
	private static String BASE_DIR = System.getProperty("user.dir") + "\\src\\main\\resources\\generator\\";
	private static String SCRIPT_DIR = System.getProperty("user.dir") + "\\src\\main\\resources\\db\\script\\";
	private static String REGEX_TABLE = "<table[\\s]*tableName=\"([^\"]*)\"[\\s]*domainObjectName=\"([^\"]*)\">([\\s\\S]*?)</table>";
	private static String REGEX_COLUMN = "<columnOverride[\\s]*column=\"([^\"]*)\"[\\s]*property=\"([^\"]*)\"/>";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Map<String, Map<String, String>> kv = findKvFromFolder(BASE_DIR);
		rewriteScript(SCRIPT_DIR, kv);
	}

	/**
	 * 指定目录下的所有xml文件，映射表名、字段名的拼音与英文的对应关系
	 */
	private static Map<String, Map<String, String>> findKvFromFolder(String dir) throws IOException {

		Map<String, String> tables = new HashMap<String, String>();
		Map<String, String> columns = new HashMap<String, String>();
		Map<String, Map<String, String>> kv = new HashMap<String, Map<String, String>>();
		
		Collection<File> files = FileUtils.listFiles(new File(dir), xmlExt, true);
		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File file = iterator.next();
			String content = FileUtils.readFileToString(file);
			parserXml(content, tables, columns);
		}
		kv.put("tables", tables);
		kv.put("columns", columns);
		return kv;
	}

	/**
	 * 重写数据库脚本
	 */
	private static void rewriteScript(String dir, Map<String, Map<String, String>> kv) throws IOException {
		Collection<File> files = FileUtils.listFiles(new File(dir), sqlExt, true);
		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File file = iterator.next();
			if (!StringUtils.contains(file.getName(), "_py")) {
				String content = FileUtils.readFileToString(file);
				content= en2Py(content, kv);
				
				File destFile = new File(dir + File.separatorChar + StringUtils.substringBeforeLast(file.getName(), ".") + "_py" + ".sql");
				FileUtils.writeStringToFile(destFile, content);
			}
		}
	}
	
	/**
	 * 映射表名、字段名的拼音与英文的对应关系
	 */
	private static void parserXml(String content, Map<String, String> tables, Map<String, String> columns) {

		Pattern tablePattren = Pattern.compile(REGEX_TABLE);
		Matcher tableMatcher = tablePattren.matcher(content);
		Pattern columnPattren = Pattern.compile(REGEX_COLUMN);
		
		while (tableMatcher.find()) {
			String tableName = tableMatcher.group(1).trim();
			String pojoName = removeCamelCase(tableMatcher.group(2).trim());
			tables.put(tableName, pojoName);
			
			String columnBlock = tableMatcher.group(3).trim();
			Matcher columnMatcher = columnPattren.matcher(columnBlock);
			while (columnMatcher.find()) {
				String columnName = columnMatcher.group(1).trim();
				String propertyName = removeCamelCase(columnMatcher.group(2).trim());
				if (!columns.containsKey(columnName)) {
					columns.put(columnName, propertyName);
				} else {
//					System.out.println(tableName + ":" + pojoName);
//					System.out.println(columnName + ":" + propertyName);
				}
			}
		}
	}
	
	/**
	 * 英文替换成拼音
	 */
	private static String en2Py(String content, Map<String, Map<String, String>> kv) {
		for (Map.Entry<String, Map<String, String>> entry : kv.entrySet()) {
			for (Map.Entry<String, String> item : entry.getValue().entrySet()) {
				content = content.replaceAll("\\b"+item.getValue()+"\\b", item.getKey());
			}
		}
		return content;
	}

	/**
	 * 把驼峰命名的字段转换成小写字母+下划线的格式
	 */
	private static String removeCamelCase(String content) {
		String result = "";
		for (int i = 0; i < content.length(); i++) {
			char currentChar = content.charAt(i);
			if (Character.isUpperCase(currentChar)) {
				char currentCharToLowerCase = Character.toLowerCase(currentChar);
				if (i==0) {
					result = result + currentCharToLowerCase;
				} else {
					result = result + "_" + currentCharToLowerCase;
				}
			} else {
				result = result + currentChar;
			}
		}
		return result;
	}
	
}
