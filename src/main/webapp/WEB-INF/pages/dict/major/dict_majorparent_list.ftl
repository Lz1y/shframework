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
				<form name="listform" action="${contextPath}/dict/edu/common/majorparent/<@commonMacros.scope />/all/list" method="post"  class="form-horizontal" role="form">
				    
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
										<label class="col-md-5 control-label">校定专业名称：</label>
										<div class="col-md-7">
											<input class="form-control" name="_decm.title.1" type="text"
												value="<#if page.pageSupport.paramVo.map?exists>${page.pageSupport.paramVo.map['_decm.title.1']?if_exists?html}</#if>" />
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-3">
									<div class="form-group ">
										<label class="col-md-5 control-label">选用状态：</label>
										<div class="col-md-7">
										    <@commonMacros.multiSelect staticlabel.status "search" "_decm.status" page.pageSupport.paramVo.map['_decm.status']/>
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-3">
									<div class="form-group ">
										<label class="col-md-5 control-label"> 执行标准：</label>
										<div class="col-md-7">
											<@commonMacros.multiSelect staticlabel.standard "read" "_decm.standard" "1"/>
										</div>
									</div>
								</div>
								<!--/span-->
							
							</div>
							<!--row end-->
							<div class="row">
								<!--/span-->
								<div class="col-md-4">
									<div class="form-group ">
										<label class="col-md-5 control-label">类型：</label>
										<div class="col-md-7">
											<@commonMacros.multiSelect staticlabel.majorType "search" "_decm.type" "${page.pageSupport.paramVo.map['_decm.type']?if_exists}"/>
										</div>
									</div>
								</div>
								<!--/span-->
								<!--/span-->
								<div class="col-md-6">
								</div>
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
												href="${contextPath}/dict/edu/common/majorparent/<@commonMacros.scope />/0/add${page.pageSupport.paramVo.parm?if_exists?html}">
												新增
											</a>
										</div>
									</div>
								</div>
							</div>
						
							<table class="table table-striped table-bordered table-hover util-btn-margin-bottom-5" id="datatable_ajax">
								<thead>
								<tr role="row" class="heading">
									<th >校定专业名称</th>
								    <th >所属目录</th>
								    <th >所属大类</th>
									<th class="sort_default" name="_decm.code" onclick="sortbyth(this)">校定专业编码</th>
									<th >专业名称</th>
									<th >选用状态</th>
									<th >执行标准</th>
									<th >类别</th>
									<th >操作</th>
								</tr>
								
								</thead>
								<tbody>
									<#if page.list?exists>
								    <#list page.list as one>
									<tr>
										<td>
									    	<a class="font-blue-steel" href="${contextPath}/dict/edu/common/majorparent/<@commonMacros.scope />/${one.id}/read${page.pageSupport.paramVo.parm?if_exists?html}">
									    		<u>${one.title?if_exists}</u>
									    	</a>
									    </td>
									    <td>
									    <@commonMacros.getCascadeInfoByGroupId one.groupId?if_exists />
									    <#if commonMacros.tempCategory?? && commonMacros.tempCategory?is_hash>
									    <@commonMacros.showDictTitle commonMacros.tempCategory />
									    </#if>
									    </td>
									    <td>
									        <#if groupmajor?exists>
									    	<#list groupmajor?keys as key>
									    		<#if key == one.groupId?default(0)?string>
									    			<@commonMacros.showDictTitle groupmajor[key]/>
									    		</#if>
									    	</#list>
									    	</#if>
									    </td>
									    <td>${one.code?if_exists}</td>
									    <td>
									    	<@commonMacros.multiSelect major "list" "" one.pid />
									    <td>
									    	<@commonMacros.multiSelect staticlabel.status "list" "status" "${one.status?default(1)}"/>
									   		<a class="font-blue" href="javascript:changeDictStatus(this, ${one.status?default(1)}, '${contextPath}/dict/edu/common/majorparent/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');" >
									   		<@commonMacros.showStatusMark one.status />
									    </td>
									    <td>
									    	<@commonMacros.multiSelect staticlabel.standard "list" "standard" "${one.standard?default(-1)}"/>
									    </td>
									    <td>
									    	<@commonMacros.multiSelect staticlabel.majorType "list" "majorType" "${one.type?default(1)}"/>
									    </td>
									    <td class="td_btn_width_2">
									        <#if one.locked?default(-1) == 0 >
									    	<a class="btn btn-xs green-seagreen" href="javascript:updateDictRecord('${contextPath}/dict/edu/common/majorparent/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');">
												修改</a>
											<a class="btn default btn-xs black" href="javascript:deleteDictRecord(this, '${contextPath}/dict/edu/common/majorparent/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');">
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
		
	</body>
</html>
