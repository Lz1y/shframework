package com.shframework.common.web.tag;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * 
 * 修正sitemesh3在额外移动body中的内容时，没有正确删除多余的内容的问题
 * @author RanWeizheng
 *
 */
public class JavaScriptTagRuleBundle implements TagRuleBundle {
	
	public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
		defaultState.addRule("jsplugininner", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("jsplugininner"), false));
		defaultState.addRule("jsinner", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("jsinner"), false));
	}

	public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
		// No op.
	}
}
