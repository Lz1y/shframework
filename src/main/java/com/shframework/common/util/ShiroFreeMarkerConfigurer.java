package com.shframework.common.util;

import java.io.IOException;
import java.util.Locale;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;




import cn.dreampie.shiro.freemarker.ShiroTags;
import freemarker.template.TemplateException;

public class ShiroFreeMarkerConfigurer extends FreeMarkerConfigurer {

	// 在FreeMarker的Configuration中添加shiro的配置
	@Override
	public void afterPropertiesSet() throws IOException, TemplateException {
		super.afterPropertiesSet();
		this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
		this.getConfiguration().setClassicCompatible(true);
		this.getConfiguration().setEncoding(Locale.SIMPLIFIED_CHINESE, "UTF-8");
		this.getConfiguration().setDefaultEncoding("UTF-8");
		this.setDefaultEncoding("UTF-8");
	}
	
}
