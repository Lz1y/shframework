package com.shframework.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HtmlUtils {

	public static void main(String[] args) throws Exception {
		String filepath = "C:/Users/OneBoA/Documents/Tencent Files/154854688/FileRecv/WKB20161/";
		Document c1 = Jsoup.parse(new File(filepath + "title.htm"), "gbk");
		Elements element = c1.getElementsByTag("tbody");
		Elements _a = element.get(1).getElementsByTag("a");

		Document c2 = Jsoup.parse(new File(filepath + _a.get(0).attr("href")), "gbk");
		
		Elements _a2 = c2.getElementsByTag("a");
		for (int i = 0; i < _a2.size(); i++) {
			
			Document _ta2d = Jsoup.parse(new File(filepath + _a2.get(i).attr("href")), "gbk");

			String nClzTitle = _ta2d.getElementsByTag("font").get(1).text().replaceAll("　", " ").split(" ")[1];
			Elements _ta2d2es = _ta2d.getElementsByTag("tr");
			for (int j = 0; j < _ta2d2es.size(); j++) {

				Elements _ta2d2ees = _ta2d2es.get(j).getElementsByAttributeValue("width", "160");
				
				for (int k = 0; k < _ta2d2ees.size(); k++) {

					String text = _ta2d2ees.get(k).text();
					if (StringUtils.isNotEmpty(text.replaceAll(" ", "").replaceAll("　", "").replaceAll("↓", "")) && !text.contains("星 期")) {
						String tirm = (nClzTitle + " " + text).replaceAll("　", "").replaceAll("↓", "").trim();
						System.out.println(tirm + " - ("  + j + "," + (k + 1) + "," + tirm.split(" ").length + ")");
					}
				}
			}
		}
	}
	
	public static List<String[]> parseHtml(String filepath) throws Exception {
		
		Document c1 = Jsoup.parse(new File(filepath + "title.htm"), "gbk");
		Elements _a = c1.getElementsByTag("tbody").get(1).getElementsByTag("a");
		Document c2 = Jsoup.parse(new File(filepath + _a.get(0).attr("href")), "gbk");
		
		Elements _a2 = c2.getElementsByTag("a");
		
		List<String[]> list = new ArrayList<String[]>();
		for (int i = 0; i < _a2.size(); i++) {
			Document _ta2d = Jsoup.parse(new File(filepath + _a2.get(i).attr("href")), "gbk");
			String nClzTitle = _ta2d.getElementsByTag("font").get(1).text().replaceAll("　", " ").split(" ")[1];
			Elements _ta2d2es = _ta2d.getElementsByTag("tr");
			
			for (int j = 0; j < _ta2d2es.size(); j++) {
				Elements _ta2d2ees = _ta2d2es.get(j).getElementsByAttributeValue("width", "160");
				
				for (int k = 0; k < _ta2d2ees.size(); k++) {
					String text = _ta2d2ees.get(k).text();
					if (StringUtils.isNotEmpty(text.replaceAll(" ", "").replaceAll("　", "").replaceAll("↓", "")) && !text.contains("星 期")) {
						String[] datas = ((nClzTitle + " " + text).replaceAll("　", "").replaceAll("节 ", " ").replaceAll("↓", "").trim() + " " + j + " " + k).split(" ");
						if (datas.length >= 6) list.add(datas);
						
//						String tirm = text.replaceAll("　", "").replaceAll("↓", "").trim();
//						System.out.println(tirm + " - ("  + j + "," + (k + 1) + "," + tirm.split(" ").length + ")");
					}
				}
			}
		}
		
		return list;
		
	}
	
}