<#-- This file contains form-related macros for use in the other Freemarker template files. The generated HTML is intended for use with Twitter Bootstrap based forms. -->
<#global shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"] />

<#macro scope><#if curRc[curMenu.rule].scope??>${curRc[curMenu.rule].scope}<#else>0</#if>/<#if curRc[curMenu.rule].scope=1>${curUser.campus.id}<#elseif curRc[curMenu.rule].scope=3>${curUser.employee.departmentId}<#elseif curRc[curMenu.rule].scope=5>${curUser.id}<#else>0</#if></#macro>
<#macro menuScope curRule><#if curRc[curRule].scope??>${curRc[curRule].scope}<#else>0</#if>/<#if curRc[curRule].scope=1>${curUser.campus.id}<#elseif curRc[curRule].scope=3>${curUser.employee.departmentId}<#elseif curRc[curRule].scope=5>${curUser.id?c}<#else>0</#if></#macro>

<#macro menu resource>
	<#if resource.child??>
	    <#list resource.child as mRes>
		    <#if mRes.roles??>
		    	<#assign roles><#list mRes.roles as r><#if r??><#if r_index!=0>,</#if>${r.code}</#if></#list></#assign>
				<@shiro.hasAnyRoles name="${roles}">
					<#if mRes.status?? && mRes.showMenu==1>
						<li class="<#if curMenu??><@active curMenu.id mRes /></#if>">
							<a href="<#if mRes.url?? && mRes.url!="">${contextPath }/<#if mRes.url?index_of('/0/0/all/list')!=-1>${mRes.url?replace('/0/0/all/list', '') }/<#if curRc?? && curRc[mRes.rule]??><@menuScope mRes.rule/><#else>0/0</#if>/all/list<#else>${mRes.url}</#if><#else>#</#if>" onclick="javascript:showResRole(${mRes.id});">
								<i <#if mRes.icon??>class="fa fa-${mRes.icon}"</#if>></i>
								<span class="title">${mRes.title }<#if mRes.url??></#if></span>
								<#if curMenu??><@selected curMenu.id mRes /></#if>
							</a>
							<#if mRes.child?? && (!(mRes.url??) || mRes.url="")>
								<ul class="sub-menu">
									<@menu mRes />
								</ul>
							</#if>
						</li>
					</#if>
				</@shiro.hasAnyRoles>
			</#if>
		</#list>
	</#if>
</#macro>

<#macro active rootId menu>
	<#if menu.id?? && menu.id == rootId>active</#if>
	<#if menu.child??>
	    <#list menu.child as menu>
			<#if menu.id?? && menu.id == rootId>active</#if>
			<#if menu.child??>
				<@active rootId menu />
			</#if>
	    </#list>
	</#if>
</#macro>

<#macro selected rootId menu>
	<#if menu.id?? && menu.id == rootId><span class="selected"></span></#if>
	<#if menu.child??>
	    <#list menu.child as menu>
			<#if menu.id?? && menu.id == rootId><span class="selected"></span></#if>
			<#if menu.child??>
				<@selected rootId menu />
			</#if>
	    </#list>
	</#if>
</#macro>

<!-- 多功能树 -->
<#macro multiTree treeNode selectedId=0 hideValue=""> 
<#if treeNode.data?exists>
<li nodeid="${treeNode.data.id?if_exists}" 
<#if selectedId != 0><@mutliTreeIsOpen treeNode selectedId /></#if>
<#if selectedId == treeNode.data.id> data-jstree='{ "selected" : true }'</#if>>
<#if hideValue!=""><input id="${hideValue}" type="hidden" value="${selectedId}" /></#if>
${treeNode.data.title}
<#if treeNode.children??>
	<ul>
	    <#list treeNode.children as child>
			<@multiTree child selectedId/>
	    </#list>
	</ul>
</#if>
</li>

<#else><#--对于传入的数据实体非TreeNode类型时，如resource-->
<li nodeid="${treeNode.id?if_exists}" 
<#if selectedId != 0><@mutliTreeIsOpen treeNode selectedId /></#if>
<#if selectedId == treeNode.id> data-jstree='{ "selected" : true }'</#if>>
<#if hideValue!=""><input id="${hideValue}" type="hidden" value="${selectedId}" /></#if>
${treeNode.title}
<#if treeNode.hasChild==1>
	<ul>
	    <#list treeNode.child as child>
			<@multiTree child selectedId/>
	    </#list>
	</ul>
</#if>
</li>
</#if>

</#macro>

<!-- 树节点是否展开 -->
<#macro mutliTreeIsOpen treeNode selectedId=0>
<#if selectedId != 0>
	<#if (treeNode.data.id == selectedId && treeNode.child?if_exists)>data-jstree='{ "opened" : true }'
	<#else>
	    <#list treeNode.children as child>
			<@mutliTreeIsOpen child selectedId/>
	    </#list>
	</#if>
</#if>
</#macro>

<!-- 级联 
areaName 级联区域名称 用于避免在同一个页面中存在多个级联时不出错。 注意，areaName中不能有“ ”这种情况
obj 保存级联数据的对象
rootId 根节点Id
staticFlag 是否静态， 如果是1, 则必须填写到最后一级（isLeaf==1），才会获得值,否则值为空
params 用来传递每一级的默认值（主要用在修改时重现之前的级联效果）
levelNum 表示层级， 这个值一般不需要赋值
-->
<#macro areaCascade obj rootId staticFlag='1' params="" areaName="cascade_area"  levelNum=0 >
<#if levelNum==0>
	<ul style="list-style:none" class="${areaName}">
</#if>
<#if obj.child??>
	<li style="float:left;">
		<select title="${obj.id }" num="${levelNum}" class="form-control form-filter input-sm selhid-true select_ac_selector select_ac_${obj.id}" onchange="${areaName}_acg(this);">
			<option value="">请选择...</option>
		    <#list obj.child as child>
				<option isLeaf="${child.isLeaf }" value="${child.id }">${child.title }</option>
		    </#list>
		</select>
	</li>
    <#list obj.child as child>
    	<@areaCascade  child rootId staticFlag params areaName levelNum+1/>
    </#list>
</#if>
<#if obj.parentId == "0">
	<input class="cascade_curid" type="hidden" value="${obj.id }" />
	<input class="cascade_rootId" type="hidden" value="${rootId }" />
	<input class="cascade_params" type="hidden" value="${params }" />
	<input class="areaCascade" type="hidden" />
	<#if staticFlag!='1'>  
		<input class="cascade_current_level" type="hidden" value="${levelNum}"/>
	</#if>
	<script>
		$(function(){//应当只针对于  class="cascade_*" 内的内容有效 
			var $cascadeArea = $("ul.${areaName}");
			
			$cascadeArea.find(":input.cascade_curid").each(function(index, item){
				$cascadeArea.find(".select_ac_" + item.value).removeClass("selhid-true");
			});
			
			var params = $cascadeArea.find(":input.cascade_params")[0].value;
			if (typeof(params) == "undefined" || params.length <= 0){
				return;					
			}
			var p = params.split(',');
			
			for(var i = 0; i < p.length; i ++){
				if ($.trim(p[i]) == ""){
					break;
				}
				var pp = $cascadeArea.find(":input.select_ac_selector").find("option[value="+p[i]+"]");
				pp.parent().val(pp.val()).removeClass("selhid-true").change();
				
				//console.log('parent->'+pp.parent());
				//console.log(pp);
			}
			//从最后一个可显示的dom节点开始，逐层确认。但不触发“change”事件，防止值错误。
			if (p.length > 1){
				${areaName}_get_showdom(p[p.length -1], $cascadeArea, ${levelNum});
			}
			
			
		});
		
		function ${areaName}_get_showdom(targetValue, $cascadeArea, topNum){
			var $pp = $cascadeArea.find(":input.select_ac_selector").find("option[value="+targetValue+"]");
			$pp.parent().val($pp.val()).removeClass("selhid-true");
			var title = $pp.parent().attr("title");
			var num = $pp.parent().attr("num");
			
			console.log("top: ${levelNum}, now :" + num);
			
			if (num != topNum) {
				console.log("go on");
				${areaName}_get_showdom(title, $cascadeArea, topNum);
			}//if
		}
		
		var ${areaName}_acg = function (element) {//onChange触发的事件
			var $cascadeArea = $("ul.${areaName}");
		
			var value = element.value;
			var title = element.title;
			var rootid = $cascadeArea.find(":input.cascade_rootId")[0].value; // 根
			var curid = $cascadeArea.find(":input.cascade_curid")[0].value; // 当前
			var isleaf = $(element).find("option:selected").attr("isLeaf");
			
			var num = $(element).attr("num");
			$cascadeArea.find(":input.select_ac_selector").each(function(index, item){
				var $item = $(item);
				var num1 = $item.attr("num");
				if(num < num1) $item.addClass("selhid-true");
			});
			
			if(rootid == title) {//如果更改的是顶级的select框
				$cascadeArea.find(":input.select_ac_selector").each(function(index, item){
					if(item.title != curid) {
						$(item).addClass("selhid-true");
					}
				});
			}
			
			$cascadeArea.find(".select_ac_" + value).removeClass("selhid-true");
			$cascadeArea.find(".select_ac_" + value).val("");
			$cascadeArea.find(":input.areaCascade").each(function(index, item){//index 貌似只能等于0 ，因为符合条件的元素就一个
				<#if staticFlag=='1'>  
				if(index == 0 && isleaf == 1) {
					item.value = value;
				}else{ 
					item.value = '';
				}
				<#else>
				var $currentLevel = $cascadeArea.find(":hidden.cascade_current_level");
				if(index == 0 && value!="") {
					item.value = value;
					$currentLevel.val($(element).attr("num") );
				}else{ 
					item.value = '';
					//如果存在上一级的节点，获取该节点当前的值,即 title 的值
					var $currentLevel = $cascadeArea.find(":hidden.cascade_current_level");
					var currentLevel = $currentLevel.val();
					if (currentLevel > ${levelNum}){
						item.value = title
						$currentLevel.val(currentLevel-1);
					}
				}
				</#if>
				
			});
			
			console.log("当前的值为： " + $cascadeArea.find(":input.areaCascade")[0].value);
			<#if staticFlag!='1'>
				console.log("当前的值为： " + $cascadeArea.find(":input.cascade_current_level")[0].value);
			</#if>  
		};
	</script>
</#if>
<#if levelNum==0>
		</ul>
</#if>
</#macro> 

<!-- 面包屑导航 -->
<#macro breadcrumbNavigation>
<div class="page-bar">
	<ul class="page-breadcrumb">
    	<#list breadcrumb as bread>
			<#if bread.rule == rule>
    			<#list bread.breadcrumbs as parentBreadcrumb>
					<li class="bn_li">
						<#if parentBreadcrumb.parentId == 0>
							<i class="fa fa-home"></i>
						<#else>
							<i class="fa fa-angle-right"></i>
						</#if>
						<#if parentBreadcrumb.url??>
							<a href="<#if parentBreadcrumb.url??>${contextPath}/${parentBreadcrumb.url }<#else>#</#if>">${parentBreadcrumb.title } </a>
						<#else>
							<a href="javascript:;">${parentBreadcrumb.title } </a>
						</#if>
					</li>
	    		</#list>
   				
				<li class="bn_li">
					<i class="fa fa-angle-right"></i>
					<a style="color:#888;font-weight:bold;" href="<#if bread.url??>${contextPath}/${bread.url }/${parentBreadcrumb.url }<#else>#</#if>">${bread.title } </a>
				</li>
			</#if>
   		</#list>
	</ul>
</div>
</#macro>

<!-- 分页 -->
<#macro pagination isPage=1 formName="listform">
<#assign current = page.pageSupport.curPage>

<#assign begin = current - 2>
<#assign end = current + 2>
<#if begin lt 2>
	<#assign begin = 1>
</#if>

<#if end gt page.totalPages>
	<#assign end = page.totalPages>
</#if>

<#if end lt begin>
	<#assign end = begin>
</#if>

<div class="row-flow">
	<#if isPage=1>
		<div class="pull-left">
			<select class="form-control form-filter input-sm" name="ps" onchange="sbmSelect(${formName})">
				<#if isPage==1>
					<option value="10" <#if page.pageSupport.pageSize == 10>selected</#if>>10</option>
					<option value="25" <#if page.pageSupport.pageSize == 25>selected</#if>>25</option>
					<option value="50" <#if page.pageSupport.pageSize == 50>selected</#if>>50</option>
					<option value="-9" <#if page.pageSupport.pageSize == 99999>selected</#if>>全部</option>
				<#elseif isPage==2>
					<option value="-9" selected>全部</option>
				</#if>
			</select>
		
			<br/>
			<#assign beginIndex = (page.pageSupport.curPage-1)*page.pageSupport.pageSize>
			<#assign endIndex = page.pageSupport.curPage*page.pageSupport.pageSize>
			共${page.total }条数据<#if (page.total-9>0)>(${beginIndex+1}至<#if (endIndex>page.total)>${page.total}<#else>${endIndex}</#if>)</#if>, 共${page.totalPages }页
		</div>
	
		<br/>
		<div class="dataTables_paginate paging_bootstrap_full_number pull-right" id="sample_1_paginate">
				<ul class="pagination">
					<#if (page.hasNext && current != 1) || (current == end && current != 1)>
						<li><a href="javascript:setPage(${formName}, 1, ${page.pageSupport.pageSize}, 1)">首页</a></li>
						<li><a class="active" href="javascript:setPage(${formName}, ${current - 1}, ${page.pageSupport.pageSize}, 1)">上一页</a></li>
					<#else>
						<li class="prev disabled"><a href="javascript:;">首页</a></li>
						<li class="prev disabled"><a href="javascript:;">上一页</a></li>
					</#if>
			
					<#list begin..end as i>
						<#if i == current>
		                    <li class="active">
		                		<a href="javascript:setPage(${formName}, ${i}, ${page.pageSupport.pageSize}, 1)">${i}</a>
		                    </li>
		                <#else>
		                    <li>
		                		<a href="javascript:setPage(${formName}, ${i}, ${page.pageSupport.pageSize}, 1)">${i}</a>
		                    </li>
						</#if>
					</#list>
				
					<#if page.hasNext>
						<li><a href="javascript:setPage(${formName}, ${current + 1}, ${page.pageSupport.pageSize}, 1)">下一页</a></li>
						<li><a href="javascript:setPage(${formName}, ${page.totalPages}, ${page.pageSupport.pageSize}, 1)">末页</a></li>
					<#else>
						<li class="prev disabled"><a href="javascript:;">下一页</a></li>
						<li class="prev disabled"><a href="javascript:;">末页</a></li>
					</#if>
				</ul>
		</div>
		<div class="clearfix"></div>
	</#if>
	
	<input type="hidden" name="p" value="${page.pageSupport.curPage }"/>
	<input type="hidden" name="ps" value="${page.pageSupport.pageSize }"/>
	<input type="hidden" name="t" value="${page.total }"/>
	<input type="hidden" name="page" value="0" />
			
	<input type="hidden" id="orderby" name="orderby" value="<#if page.pageSupport.paramVo.map?exists><#list page.pageSupport.paramVo.map?keys as key><#if key=='orderby'>${page.pageSupport.paramVo.map[key]?if_exists}</#if></#list></#if>" />
	<input type="hidden" id="field" name="field" value="<#if page.pageSupport.paramVo.map?exists><#list page.pageSupport.paramVo.map?keys as key><#if key=='field'>${page.pageSupport.paramVo.map[key]?if_exists}</#if></#list></#if>" />
	<input type="hidden" id="defaultSortField" value="<#list page.pageSupport.paramVo.map?keys as key><#if key=='defaultSortField'>${page.pageSupport.paramVo.map[key]?if_exists}</#if></#list>"/>
	<input type="hidden" id="defaultSortOrderby" value="<#list page.pageSupport.paramVo.map?keys as key><#if key=='defaultSortOrderby'>${page.pageSupport.paramVo.map[key]?if_exists}</#if></#list>"/>
</div>

<script>
	var setPage = function (fn, p, ps, isKeep) {
		document.getElementsByName("p")[0].value = p;
		document.getElementsByName("ps")[0].value = ps;
		document.getElementsByName("page")[0].value = 1;
		fn.submit();
	}
	
	var sbmSelect = function(fn) {
		document.getElementsByName("page")[0].value = 1;
		fn.submit();
	}
</script>
</#macro>

<#-- ComboBox -->
<#macro multiSelect2 dataMap showStyle elementName="" num="" flag=1>
	<#if showStyle=="search">
		<select class="form-control" aria-controls="sample_1" name="${elementName}">
		    <#if dataMap?exists>
	    		<option <#if num??>value<#if flag!=1>="-9"</#if> selected</#if>>
	    			请选择...
	    		</option>
		    	<#list dataMap?keys as key>
		    		<#if dataMap[key].visiableFlag != "0">
		    		<option value="${key}" <#if num==key>selected</#if>>
		    			${dataMap[key].title} 
		    		</option>
		    		</#if>
		    	</#list>
			</#if>
		</select>
	</#if>
</#macro>

<#-- rwz 增加校验 和 其他属性-->
<#macro multiSelect dataMap showStyle elementName="" num=""  classValid="" attributes="" titleOrCode="title" flag=1>
	<#if showStyle=="read">
	    <#if dataMap?exists>
	    	<#list dataMap?keys as key>
    			<#if num==key>
					<label class="radio-inline" >
						<#if titleOrCode=="title">
	    					${dataMap[key].title}
	    				<#else>
	    					${dataMap[key].code}
	    				</#if>
	    			</label>
    			</#if>
	    	</#list>
		</#if>
	<#elseif showStyle=="list">
	    <#if dataMap?exists>
	    	<#list dataMap?keys as key>
    			<#if num?string==key?string>
    				<#if titleOrCode=="title">
    					${dataMap[key].title}
    				<#else>
    					${dataMap[key].code}
    				</#if>
    			</#if>
	    	</#list>
		</#if>
	<#elseif showStyle=="edit" || showStyle=="approve">
		<select name="${elementName}" class="form-control ${classValid}" ${attributes}>
		    <#if dataMap?exists>
		    	<#list dataMap?keys as key>
		    		<#if dataMap[key].visiableFlag != "0">
		    		<option value="${key}" <#if num==key>selected</#if>>
		    			<#if titleOrCode=="title">
	    					${dataMap[key].title}
	    				<#else>
	    					${dataMap[key].code}
	    				</#if>
		    		</option>
		    		</#if>
		    	</#list>
			</#if>
		</select>
	<#elseif showStyle=="add" || showStyle=="batchadd">
		<select name="${elementName}" class="form-control ${classValid}" ${attributes}>
		    <#if dataMap?exists>
		    	<#list dataMap?keys as key>
		    		<#if dataMap[key].visiableFlag != "0">
		    		<option value="${key}" <#if num==key>selected</#if>>
		    			<#if titleOrCode=="title">
	    					${dataMap[key].title}
	    				<#else>
	    					${dataMap[key].code}
	    				</#if>
		    		</option>
		    		</#if>
		    	</#list>
			</#if>
		</select>
	<#elseif showStyle=="search">
		<select class="form-control ${classValid}" aria-controls="sample_1" name="${elementName}" ${attributes}>
		    <#if dataMap?exists>
	    		<option <#if num??>value<#if flag!=1>="-9"</#if> selected</#if>>
	    			请选择...
	    		</option>
		    	<#list dataMap?keys as key>
		    		<#if dataMap[key].visiableFlag != "0">
		    		<option value="${key}" <#if num==key>selected</#if>>
		    			<#if titleOrCode=="title">
	    					${dataMap[key].title}
	    				<#else>
	    					${dataMap[key].code}
	    				</#if>
		    		</option>
		    		</#if>
		    	</#list>
			</#if>
		</select>
	</#if>
</#macro>

<#macro bsMultiSelect dataMap showStyle elementName="" num=""  classValid="" attributes="" titleOrCode="title" flag=1>
	<#if showStyle=="search">
		<select class="form-control bs-select ${classValid}" multiple aria-controls="sample_1" name="${elementName}" ${attributes}>
		    <option value="0,1,2,3,4" <#if num?index_of('0,1,2,3,4')!=-1>selected="selected"</#if>>全选</option>  
		    <#if dataMap?exists>
		    	<#list dataMap?keys as key>
		    		<#if dataMap[key].visiableFlag != "0">
		    		<option value="${key}" <#if num?index_of(key)!=-1 && num?index_of('0,1,2,3,4')==-1>selected="selected"</#if>><#if titleOrCode=="title">${dataMap[key].title}<#else>${dataMap[key].code}</#if></option></#if>
		    	</#list>
			</#if>
		</select>
	<#elseif showStyle=="edit">
		<select class="form-control bs-select ${classValid}" multiple aria-controls="sample_1" name="${elementName}" ${attributes}>
		    <#if dataMap?exists>
		    	<#list dataMap?keys as key>
		    		<#if dataMap[key].visiableFlag != "0">
		    		<option value="${key}" <#if num?index_of(key)!=-1>selected="selected"</#if>><#if titleOrCode=="title">${dataMap[key].title}<#else>${dataMap[key].code}</#if></option></#if>
		    	</#list>
			</#if>
		</select>		
	</#if>
</#macro>

<!-- 单选按钮 - 性别 -->
<#macro multiGender showStyle elementName="" inputValue=1>
	<#if showStyle=="read">
		<label class="radio-inline">
			<#if inputValue==1>男<#else>女</#if>
		</label>
	<#elseif showStyle=="list">
		<#if inputValue==1>男<#else>女</#if>
	<#elseif showStyle=="edit">
		<div class="radio-list">
			<label class="radio-inline">
				<input type="radio" name="gender" value="1" <#if inputValue==1>checked</#if>/> 男
			</label>
			<label class="radio-inline">
				<input type="radio" name="gender" value="0" <#if inputValue==0>checked</#if>/> 女
			</label>
		</div>
	<#elseif showStyle=="add">
		<div class="radio-list">
			<label class="radio-inline">
				<input type="radio" name="gender" value="1" checked/> 男
			</label>
			<label class="radio-inline">
				<input type="radio" name="gender" value="0" /> 女
			</label>
		</div>
	</#if>
</#macro>

<!-- 列表label样式 -->
<#macro labelRowDetails value="" style=1>
	<#if style==1>
		<label class="row-details row-details-close"> ${value} </label>
	<#elseif style==2>
		<label> ${value} </label>
	</#if>
</#macro>

<#-- InputBox -->
<#macro multiInput showStyle elementName="" inputValue="" classValid="" attributes="">
	<#if showStyle=="read">
		<label class="radio-inline" >
			${inputValue?if_exists}
		</label>
	<#elseif showStyle=="list">
		${inputValue?if_exists}
	<#elseif showStyle=="edit">
		<input type="text" name="${elementName}" class="form-control ${classValid}" value="${inputValue?if_exists?html}" ${attributes}/>
	<#elseif showStyle=="add" || showStyle=="batchadd">
		<input type="text" name="${elementName}" class="form-control ${classValid}" value="${inputValue?if_exists?html}" ${attributes}/>
	</#if>
</#macro>

<#-- Textarea -->
<#macro multiTextarea showStyle elementName="" inputValue="" classValid="" attributes="" rows="5">
	<#if showStyle=="read">
		<label class="radio-inline">
			${inputValue?if_exists}
		</label>
	<#elseif showStyle=="list">
		${inputValue?if_exists}
	<#elseif showStyle=="edit">
		<textarea class="form-control ${classValid}" name="${elementName}" rows="${rows}" ${attributes}>${inputValue?if_exists?html}</textarea>
	<#elseif showStyle=="add">
		<textarea class="form-control ${classValid}" name="${elementName}" rows="${rows}" ${attributes}>${inputValue?if_exists?html}</textarea>
	</#if>
</#macro>

<#-- Date -->
<#macro multiDate showStyle elementName dateValue placeholder="" attributes="">
	<#if showStyle=="read">
		<#if dateValue??>
			<label class="radio-inline"> ${dateValue?date} </label>
		</#if>
	<#elseif showStyle=="list">
		<#if dateValue??>
			<label style="font-size:12px;"> ${dateValue?date} </label>
		</#if>
	<#elseif showStyle=="edit">
		<input type="text" name="${elementName}" class="form-control datepicker required" placeholder="${placeholder}" value="<#if dateValue??>${dateValue?date}</#if>" ${attributes}/>
	<#elseif showStyle=="add" || showStyle="batchadd">
		<input type="text" name="${elementName}" class="form-control datepicker required" placeholder="${placeholder}" value="<#if dateValue??>${dateValue?date}</#if>" ${attributes}/>
	<#elseif showStyle=="search">
		<input type="text" name="${elementName}" class="form-control datepicker required" placeholder="${placeholder}" value="<#if dateValue??>${dateValue?date}</#if>" ${attributes}/>
	</#if>
</#macro>

<#-- DateTime -->
<#macro multiDateTime showStyle elementName dateValue placeholder="">
	<#if showStyle=="read">
		<#if dateValue??>
			<label class="radio-inline"> ${dateValue?string('yyyy-MM-dd HH:mm:ss')} </label>
		</#if>
	<#elseif showStyle=="list">
		<#if dateValue??>
			<label style="font-size:12px;"> ${dateValue?string('yyyy-MM-dd HH:mm:ss')} </label>
		</#if>
	<#elseif showStyle=="edit" || showStyle=="add" || showStyle=="search" || showStyle=="batchadd">
		<div class="input-group date form_meridian_datetime">
			<input type="text" name="${elementName}" size="16" class="form-control required" placeholder="${placeholder}" value="<#if dateValue??>${dateValue?string('yyyy-MM-dd HH:mm:ss')}</#if>">
			<span class="input-group-btn" style="vertical-align:top">
				<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
			</span>
		</div>
	</#if>
</#macro>

<#macro multiDateTimeNoSec showStyle elementName dateValue placeholder="" attributes="">
	<#if showStyle=="read">
		<#if dateValue??>
			<label class="radio-inline"> ${dateValue?string('yyyy-MM-dd HH:mm')} </label>
		</#if>
	<#elseif showStyle=="list">
		<#if dateValue??>
			<label style="font-size:12px;"> ${dateValue?string('yyyy-MM-dd HH:mm')} </label>
		</#if>
	<#elseif showStyle=="edit" || showStyle=="add" || showStyle=="search" || showStyle=="batchadd">
		<div class="input-group date form_meridian_datetime_nosec">
			<input type="text" name="${elementName}" size="16" class="form-control required" placeholder="${placeholder}" value="<#if dateValue??>${dateValue?string('yyyy-MM-dd HH:mm')}</#if>" ${attributes}>
			<span class="input-group-btn" style="vertical-align:top">
				<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
			</span>
		</div>
	</#if>
</#macro>



<#--展示字典信息 rwz Modify by wangkang-->
<#macro showDictTitle dictRecord separator="-" isYearTerm="false">
<#if dictRecord?? && dictRecord?is_hash && dictRecord.title?exists>
	<#if separator == "">
	<#if isYearTerm == "false">
		(${dictRecord.code?if_exists})
	</#if>${dictRecord.title?if_exists}
	<#else>${dictRecord.code?if_exists}${separator}${dictRecord.title?if_exists}
	</#if>
<#elseif dictRecord?? && dictRecord?is_hash>
	${dictRecord.code?if_exists}
</#if>
</#macro>


<#--级联部分 by RanWeizheng
分别将三部分的级联信息装载到：
tempMajor,
tempGroup,
tempCategory这三个变量中，前端可以直接调用
-->
<#--根据groupId获取级联信息 -->
<#macro getCascadeInfoByGroupId groupId>
	<#assign tempGroup = null/>
	<#assign tempCategory = null/>
	
	<#if groupId?exists && groupId!="">
		<#if groupmajor?exists>
		    <#list groupmajor?keys as key>
		        <#if key==groupId?default(0)?string>
		            <#assign tempGroup = groupmajor[key]>
		            <#break/>
		        </#if>
	        </#list>
	    </#if>
	</#if>
    <#if tempGroup?is_hash && tempGroup.categoryId?exists>
	    <#if categorymajor?exists>
	        <#list categorymajor?keys as key>
	            <#if key==tempGroup.categoryId?default(0)?string>
	                <#assign tempCategory=categorymajor[key]>
	                <#break/>
	            </#if>
	        </#list>
	    </#if>
	</#if>
</#macro>
<#--根据majorId获取级联信息 -->
<#macro getCascadeInfoByMajorId majorId>
	<#assign tempMajor = null/>
	
	<#if majorId?exists && majorId!="">
		<#if major?exists>
		    <#list major?keys as key>
		        <#if key==majorId?default(0)?string>
		        	<#assign tempMajor = major[key]>
		            <#break/>
		        </#if>
	        </#list>
	    </#if>
	</#if>
	
	<#if tempMajor?is_hash>
        <@getCascadeInfoByGroupId tempMajor.groupId />
    <#else>
    	<#assign tempGroup = null/>
		<#assign tempCategory = null/>
    </#if>
</#macro>

<#--根据majorFieldId获取级联信息 -->
<#macro getCascadeInfoByMajorFieldId majorFieldId>
	<#assign tempMajorField = null/>
	<#if majorFieldId?exists && majorFieldId!="">
		<#if majorfield?exists>
		    <#list majorfield?keys as key>
		        <#if key==majorFieldId?default(0)?string>
		            <#assign tempMajorField = majorfield[key]>
		            <#break/>
		        </#if>
	        </#list>
	    </#if>
	</#if>
	<#if tempMajorField?is_hash>
        <@getCascadeInfoByMajorId tempMajorField.majorId />
    <#else>
    	<#assign tempMajor = null/>
    	<#assign tempGroup = null/>
		<#assign tempCategory = null/>
    </#if>
</#macro>

<#macro getCascadeCategoryId >
	<#if tempCategory?? && tempCategory?is_hash>
	${tempCategory.id?default(0)}
	<#else>0
	</#if>
</#macro>
<#macro getCascadeGroupId >
	<#if tempGroup?? && tempGroup?is_hash>
	${tempGroup.id?default(0)}
	<#else>0
	</#if>
</#macro>
<#macro getCascadeMajorId >
	<#if tempMajor?? && tempMajor?is_hash>
	${tempMajor.id?default(0)}
	<#else>0
	</#if>
</#macro>

<#--通过地区级联的最下一级获得上面的两级的值-->
<#macro getCityCascadeByCountyId countyId >
	<#if countyId?exists && countyId != "">
		<#assign tempProvinceId = countyId />
		<#list provincecascade?keys as key>
		    <#if provincecascade[key].id?string == countyId ><#break/></#if>
		    <#if provincecascade[key].child?exists >
		   		<#list provincecascade[key].child as city>
		   			<#if city.id?string == countyId>
		   				<#assign tempProvinceId = provincecascade[key].id />
		   				<#assign tempCityId = countyId />
		   				<#break/>
		   			</#if>
		   			<#if city.child?exists>
		   				<#list city.child as county>
		   					<#if county.id?exists && county.id?string == countyId>
		   						<#assign tempProvinceId = provincecascade[key].id />
		   						<#assign tempCityId = city.id />
		   						<#break/>
		   					</#if>
		   				</#list>
		   			</#if>
		   		</#list>
		   </#if> 
		</#list>
	</#if>
</#macro>


<#macro getMajorStr tempMajor tempMajorField>
	${tempMajor.title}
	<#if tempMajorField.code?exists && !tempMajorField.code?ends_with("_0")>
		(${tempMajorField.title})
	</#if>
</#macro>
<#--通过majorFieldId 得到 专业名称（专业方向名称）这样的显示-->
<#macro getMajorInfoByMajorFieldId majorFieldId showStyle2="list">
	<#assign tempMajor = ""/>
	<#assign tempMajorField = ""/>
	<#if majorFieldId?? && majorFieldId!="" && majorfield[majorFieldId?string]?exists>
		<#assign tempMajorField = majorfield[majorFieldId?string]/>
		<#if major[tempMajorField.majorId?string]?exists>
			<#assign tempMajor = major[tempMajorField.majorId?string]/>
			<#if  showStyle2 == "list">
				<@getMajorStr tempMajor tempMajorField />
			<#elseif showStyle2="read">
				<label class="radio-inline" ><@getMajorStr tempMajor tempMajorField /></label>
			</#if>
		<#else>
			<@multiSelect majorfield showStyle2 "majorFieldId" majorFieldId />
		</#if>
	</#if>
</#macro>

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
				${staticlabel[labelName][key].title}
			</#if>
		</#list>
	</#if>
</#macro>

<#macro showLabelRadio labelName elementName="" value="" attributes="">
	<#if staticlabel[labelName]?exists>
		<div class="radio-list">
		<#list staticlabel[labelName]?keys as key>
			<label class="radio-inline">
			<input name="${elementName}" type="radio" value="${key}" <#if key==value?string>checked="checked"</#if>  ${attributes}/>
			${staticlabel[labelName][key].title}
			</label>
		</#list>
		</div>
	</#if>
</#macro>
<#-- class去掉 input-inline,页面显示更美观-->
<#macro showLabelSelect labelName elementName="" value="">
	<#if staticlabel[labelName]?exists>
		<select name="${elementName}" class="form-control">
		    <option value="">请选择...</option>
		<#list staticlabel[labelName]?keys as key>
			<option value="${key}" <#if key==value?string>selected="selected"</#if> />
			${staticlabel[labelName][key].title}
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

<#macro requiredMark>
<span style="color:red">*&nbsp;</span>
</#macro>

<#--显示统计部分的数字 Ranweizheng-->
<#macro showStatNum statNum>
      <#if statNum?string == "0">-<#else>${statNum}</#if>
</#macro>

<#-- 根据教学班id 显示分配详情 
2-3周 每周上 周三 2 ,3 ,4 ,5 ,6 节,院本部-benbu001-2015 ;
4-6周 每周上 周三 10 ,11 ,12 ,13 节,系定;
-->
<#macro showalloc tchClazzId showStyle="list" showclassroom="1">
	<#if showStyle == "read">
		<label class="radio-inline" >
	</#if>
	<#if (allocvo??) && (allocvo[tchClazzId?string]?? && allocvo[tchClazzId?string]?size > 0)>
		<#assign allocList = allocvo[tchClazzId?string]/>
	<#else>
		<#assign allocList=[]/>
	</#if>
	
	<#if (tchClazzId?? && tchClazzId > 0) && (allocList?? && allocList?size > 0)>
	<#list allocList as alloc>
	
		<#if alloc_index == 0><#assign minWeekSeq=alloc.weekSeq?sort?first/></#if>
	
		<#assign weekSeqStr = alloc.weekSeq?sort?first + "-" + alloc.weekSeq?sort?last/>
		<#assign oddDualWeekStr = staticlabel.oddDualWeek[alloc.oddDualWeek?string].title/>
		<#assign weekDayStr = staticlabel.weekDay[alloc.weekDay?string].title/>
		<#assign periodStr>
			<#list alloc.periodId..(alloc.periodId+alloc.continuousPeriod-1) as i>
				<#if i == alloc.periodId>
					${i}
				<#else>
					${","+ i}
				</#if>
			</#list>
		</#assign>
		<#if alloc.classroomId?? && (alloc.classroomId > 0)>
			<#assign classroomStr>
				<@showClassroom alloc.classroomId />
			</#assign>
		<#else>
			<#if sysrole[alloc.pendingRoleId?string].code == "dept_cskd_cskd">
		  		<#assign classroomStr = "（系定）">
		  	<#else>
		  		<#assign classroomStr = null>
		  	</#if>
		</#if>
		
		<#if showclassroom == "1">
			<#if (classroomStr?? && classroomStr != null)>
				${weekSeqStr + "周 " + oddDualWeekStr + " " + weekDayStr + " " + periodStr + "节," + classroomStr +";<br/>"}
			<#else>
				${weekSeqStr + "周 " + oddDualWeekStr + " " + weekDayStr + " " + periodStr + "节;<br/>"}
			</#if>
		<#else>
			${weekSeqStr + "周 " + oddDualWeekStr + " " + weekDayStr + " " + periodStr + "节;<br/>"}
		</#if>
		<input type="hidden" name="minWeekSeq" value="${minWeekSeq}"/>
    </#list>
	</#if>
	<#if showStyle == "read">
		</label>
	</#if>
</#macro>


<#macro showClassroom classroomId><#if classroomId??>${classroom[classroomId?string].campusName}-${classroom[classroomId?string].code}-${classroom[classroomId?string].roomNo}</#if></#macro>

<#--显示所有 考场包括激活不激活 qy-->
<#macro showAllClassroom classroomId>
  <#if classroomId??>${classroomAll[classroomId?string].campusName}-${classroomAll[classroomId?string].code}-${classroomAll[classroomId?string].roomNo}</#if>
</#macro>

<#macro singleCheckbox elementName="" value="" title="" criter="" attributes="">	
	<div class="radio-list">
		<label class="radio-inline">
		<span class="">
		<input name="${elementName}" type="checkbox" value="${value}" ${attributes} <#if criter == value>checked="checked"</#if>></span>
		${title}
		</label>	
	</div>
</#macro>

<#-- 毕业资格审核页面使用 不需要审核的项显示横线 -->
<#macro line>
	— —
</#macro>