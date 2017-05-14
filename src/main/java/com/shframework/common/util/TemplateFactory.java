package com.shframework.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;

@SuppressWarnings("deprecation")
public class TemplateFactory {

	// 日志记录对象
	private final static Logger log = LoggerFactory.getLogger(TemplateFactory.class);
	// 模板文件路径
	private final static String templatePath = "/config/template";
	// 模板文件内容编码
	private final static String ENCODING = "utf-8";
	// 后缀
	private final static String SUFFIX = ".ftl";
	// 模板生成配置
	private static Configuration conf = new Configuration();
	// 邮件模板缓存池
	private static Map<String, Template> tempMap = new HashMap<String, Template>();
	
	static {
		// 设置模板文件路径
		conf.setClassForTemplateLoading(TemplateFactory.class, templatePath);
		conf.setDefaultEncoding(ENCODING);
	}

	/**
	 * 通过模板文件名称获取指定模板
	 * @param name 模板文件名称
	 * @return Template 模板对象
	 */
	private static Template getTemplateByName(String name, String locale) throws Exception {
		name = name + "_" + locale + SUFFIX;
		if (tempMap.containsKey(name)) {
			log.debug("the template is already exist in the map, template name : " + name);
			// 缓存中有该模板直接返回
			return tempMap.get(name);
		}
		// 缓存中没有该模板时，生成新模板并放入缓存中
		Template temp = conf.getTemplate(name);
		tempMap.put(name, temp);
		log.debug("the template is not foundin the map, template name : " + name);
		return temp;
	}

	/**
	 * 根据指定模板将内容输出到控制台
	 * @param name 模板文件名称
	 * @param map 与模板内容转换对象
	 * @throws Exception
	 */
	public static void outputToConsole(String name, String locale, Map<String, String> map) throws Exception {
		// 通过Template可以将模板文件输出到相应的流
		Template temp = getTemplateByName(name, locale);
		temp.setEncoding(ENCODING);
		temp.process(map, new PrintWriter(System.out));
	}

	/**
	 * 
	 * @param name 模板文件的名称
	 * @param map 与模板内容转换对象
	 * @return String
	 * @throws Exception
	 */
	public static String generateHtmlFromFtl(String name, String locale, Map<String, String> map) throws Exception {
		Writer out = new StringWriter(2048);
		Template temp = getTemplateByName(name, locale);
		temp.setEncoding(ENCODING);
		temp.process(map, out);
		return out.toString();
	}
}
