package com.shframework.common.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;  

public class SiteMeshDomParse {

	/**
	 * 解析sitemesh3.xml文件，获得不被渲染的路径集合
	 * @param context
	 * @return
	 * @throws Exception
	 * @author Josh
	 */
    public static List<String> getExcludedPathList(ServletContext context) throws Exception{  
		
    	File file = new File(context.getRealPath(File.separator + "WEB-INF" + File.separator + Constants.SITEMESH3_PATH));
    	InputStream inputStream = new FileInputStream(file);
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder = factory.newDocumentBuilder();  
        Document document = builder.parse(inputStream);  
        Element element = document.getDocumentElement();  
          
        NodeList mappingNodes = element.getElementsByTagName("mapping"); 
        
        List<String> excludedPathList = new ArrayList<String>();
        for(int i = 0;i < mappingNodes.getLength();i ++){  
        	Element mappingElement = (Element) mappingNodes.item(i);
            String path = mappingElement.getAttribute("path");
            String exclue = mappingElement.getAttribute("exclue");
            if(StringUtils.isNotBlank(path) && StringUtils.isNotBlank(exclue) && path.split("/").length > 4){
            	excludedPathList.add(path);
            }
        }
        return excludedPathList;
    }
}