<#import "/macro/commonMacros.ftl" as commonMacros />
<html>
	<head>
		<title>${curMenu.title?if_exists}</title>
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		<@commonMacros.breadcrumbNavigation/>
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		<div class="row">
			<div class="col-md-12">
				<form name="listform" action="${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />/all/list" method="post" class="form-horizontal" role="form">
				    
				    <div class="portlet box green-seagreen">
						<div class="portlet-title">
							<div class="caption">
								搜索
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
							</div>
						</div>
						
						<div class="portlet-body">
						    <div class="row ">
						    	<!--/span-->
						    	<div class="col-md-4">
									<div class="form-group ">
										<label class="col-md-5 control-label">专业方向名称：</label>
										<div class="col-md-7">
											<input class="form-control" name="_decmf.title.1" type="text" 
												value="<#if page.pageSupport.paramVo.map?exists>${page.pageSupport.paramVo.map['_decmf.title.1']?if_exists?html}</#if>" />
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-3">
									<div class="form-group ">
										<label class="col-md-5 control-label">选用状态：</label>
										<div class="col-md-7">
											<@commonMacros.multiSelect staticlabel.status "search" "_decmf.status" page.pageSupport.paramVo.map['_decmf.status']/>
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-3">
									<div class="form-group ">
										<label class="col-md-5 control-label"> 执行标准：</label>
										<div class="col-md-7">
											<@commonMacros.multiSelect staticlabel.standard "search" "_decmf.standard" "${page.pageSupport.paramVo.map['_decmf.standard']?if_exists}"/>
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-2">
									<div class="form-group ">
										<div class="col-md-12">
											<button class="btn green-seagreen table-group-action-submit btn_search" type="submit" >搜索</button>
										</div>
									</div>
								</div>
								<!--/span-->
								
							</div>
							<!--row end-->
						</div>
						<!--portlet-body end-->
					</div>
				
					<div class="portlet box blue-steel">
						<div class="portlet-title">
							<div class="caption">
								${curMenu.title?if_exists}
							</div>
						</div>
						<div class="portlet-body">
						    
						    <div class="table-toolbar">
								<div class="row">
									<div class="col-md-6">
										<div class="btn-group">
											<a id="sample_editable_1_new" class="btn green-seagreen"
												href="${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />/0/add${page.pageSupport.paramVo.parm?if_exists?html}">
												新增
											</a>
										</div>
									</div>
								</div>
							</div>
						
							<table class="table table-striped table-bordered table-hover util-btn-margin-bottom-5" id="datatable_ajax">
								<thead>
								<tr role="row" class="heading">
									<th>所属目录</th>
								    <th>所属大类</th>
									<th class="sort_default" name="_decmf.major_id" onclick="sortbyth(this)">专业</th>
									<th class="sort_default" name="_decmf.code" onclick="sortbyth(this)">专业方向编码</th>
									<th class="sort_default" name="_decmf.title" onclick="sortbyth(this)">专业方向名称</th>
									<th class="sort_default" name="_decmf.principal" onclick="sortbyth(this)">负责人</th>
									<th >选用</th>
									<th >执行标准</th>
									<th class="sort_default" name="_decmf.priority" onclick="sortbyth(this)">排序</th>
									<th class="td_btn_width_4">操作</th>
								</tr>
								
								</thead>
								<tbody>
									<#if page.list?exists>
								    <#list page.list as one>
									<tr>
										<td>
									    <@commonMacros.getCascadeInfoByMajorId one.majorId?if_exists />
									    <#if commonMacros.tempCategory?? && commonMacros.tempCategory?is_hash>
									    <@commonMacros.showDictTitle commonMacros.tempCategory />
									    </#if>
									    </td>
									    <td>
									    	<#if commonMacros.tempGroup?? && commonMacros.tempGroup?is_hash>
									    	<@commonMacros.showDictTitle commonMacros.tempGroup />
									    	</#if>
									    </td>
									    <td>
									    	<#if major?exists>
									    	<#list major?keys as key>
									    		<#if key == one.majorId?default(0)?string>
									    			<@commonMacros.showDictTitle major[key]/>
									    		</#if>
									    	</#list>
									    	</#if>
									    </td>
									    <td>${one.code?if_exists}</td>
									    <td>
									        <a class="font-blue-steel" href="${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />/${one.id}/read${page.pageSupport.paramVo.parm?if_exists?html}">
									    		<u>${one.title?if_exists}</u>
									    	</a>
									    </td>
									    <td><@commonMacros.multiSelect employeename "list" "employeeName" "${one.principal?default(0)}"/></td>
									    </td>
									    <td>
									    	 <@commonMacros.multiSelect staticlabel.status "list" "status" "${one.status?default(1)}"/>
									    	 <#if one.code?exists && !one.code?if_exists?ends_with("_0")>
									   		<a class="font-blue" href="javascript:changeDictStatus(this, ${one.status?default(1)}, '${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');" >
									   		<@commonMacros.showStatusMark one.status />
									   		</a>
									   		</#if>
									    </td>
									    <td>
									    	<@commonMacros.multiSelect staticlabel.standard "list" "standard" "${one.standard?default(-1)}"/>
									    </td>
									 	<td >${one.priority}</td>
									    <td class="td_btn_width_2">
									    	<#if one.locked?default(-1) == 0 >
									    	<a class="btn btn-xs green-seagreen" href="javascript:updateDictRecord('${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');">
												修改</a>
											</#if>
											<a class="btn btn-xs green-seagreen" href="javascript:changeMajorPrincipal('${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');">
												修改负责人</a>
											<#if one.locked?default(-1) == 0 >
											<a class="btn default btn-xs black" href="javascript:deleteDictRecord(this, '${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');">
												删除 </a>
											</#if>
											
									    </td>
									</tr>
									</#list>
									</#if>
								</tbody>
							</table>
							
							<@commonMacros.pagination/>
						</div>
					</div>
					
				</form>
				
		</div>
		<!-- END PAGE CONTENT-->
		
		<jsinner>
			<!-- BEGIN PAGE LEVEL SCRIPTS -->
			<script type="text/javascript">
				$(document).ready(function(){
		           
				});//document.ready()
				
				//修改专业方向负责人
				function changeMajorPrincipal(urlroot, id, param){
					bootbox.confirm(dictModifyWarnStr, function(result){
						if(!result){
				  			return;
						}
						var url = urlroot + "/" + id + "/edit" + param;
						var addParam = "type=changeprincipal";
						if ($.trim(param) != ""){
							url += "&" + addParam;
						} else {
							url += "?" + addParam;
						}
						var $form = $("<form id='changestatusForm' action='" + url + "' method='POST'></form>");
						$form.appendTo("body").submit();
					});//confirm
				}
				
			</script>
		</jsinner>
		
	</body>
</html>
