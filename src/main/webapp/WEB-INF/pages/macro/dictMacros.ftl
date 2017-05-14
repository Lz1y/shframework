<#--字典表相关的marcos
    build ：RanWeizheng
    未使用，暂时作为备份
-->
<#--选用状态 对应的 操作标志
		0： 未选用 ： 对应  √   表示置为 选用
		1： 选用     ：   对应×   表示置为 未选用
-->
<#macro showStatusMark value="0">
	<#if value?default('0')?string == '1'>×<#else>√</#if>
</#macro>


<#--显示符合条件的label内容 -->
<#macro getstaticlabel labelName value>
	<#if staticlabel[labelName]?exists>
		<#list staticlabel[labelName]?keys as key>
			<#if key==value?string>
				${staticlabel[labelName][key]}
			</#if>
		</#list>
	</#if>
</#macro>

<#macro showLabelRadio labelName elementName="" value="">
	<#if staticlabel[labelName]?exists>
		<div class="radio-list">
		<#list staticlabel[labelName]?keys as key>
			<label class="radio-inline">
			<input name="${elementName}" type="radio" value="${key}" <#if key==value?string>checked="checked"</#if> />
			${staticlabel[labelName][key]}
			</label>
		</#list>
		</div>
	</#if>
</#macro>

<#macro showLabelSelect labelName elementName="" value="">
	<#if staticlabel[labelName]?exists>
		<select name="${elementName}" class="form-control  input-inline">
		    <option value="">请选择...</option>
		<#list staticlabel[labelName]?keys as key>
			<option value="${key}" <#if key==value?string>selected="selected"</#if> />
			${staticlabel[labelName][key]}
			</option>
		</#list>
		</select>
	</#if>
</#macro>

<#--多用途 ,  控制缓存中staticlabeld的使用， 
	add		新增，显示radio
	edit		修改，显示radio
	search	  搜索，显示select
	read, list  输出
-->
<#macro multiLabel showStyle labelName elementName value="">
	<#if showStyle == "add">
		<@showLabelRadio labelName elementName value />
	<#elseif showStyle == "edit">
		<@showLabelRadio labelName elementName value />
	<#elseif showStyle == "search">
		<@showLabelSelect labelName elementName value />
	<#elseif showStyle == "read">
		<label class="radio-inline" >
		<@getstaticlabel labelName value />
		</label>
	<#elseif showStyle == "list">
		<@getstaticlabel labelName value />
	</#if>

</#macro>