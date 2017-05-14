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
				<form name="listform" action="${contextPath}/dict/edu/common/campus/<@commonMacros.scope />/all/list" method="post" class="form-horizontal" role="form">
				    
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
										<label class="col-md-4 control-label">校区名称：</label>
										<div class="col-md-8">
											<input class="form-control" name="_decc.title.1" type="text"
												value="<#if page.pageSupport.paramVo.map?exists>${page.pageSupport.paramVo.map['_decc.title.1']?if_exists?html}</#if>" />
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-8">
									<div class="form-group ">
										<div class="col-md-12">
											<button class="btn green-seagreen table-group-action-submit btn_search" type="submit" >搜索</button>
										</div>
									</div>
								</div>
								<!--/span-->
								
							</div>
						</div>
					</div>
				
					<div class="portlet box  blue-steel">
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
												 href="${contextPath}/dict/edu/common/campus/<@commonMacros.scope />/0/add${page.pageSupport.paramVo.parm?if_exists?html}">
												新增
											</a>
										</div>
									</div>
								</div>
							</div>
						
							<table class="table table-striped table-bordered table-hover util-btn-margin-bottom-5" id="datatable_ajax">
								<thead>
								<tr role="row" class="heading">
									<th class="sort_default" name="_decc.code" onclick="sortbyth(this)">校区编码</th>
									<th class="sort_default" name="_decc.title" onclick="sortbyth(this)">校区名称</th>
									<th class="sort_default" name="_decc.address" onclick="sortbyth(this)">校区地址</th>
									<th class="sort_default" name="_decc.tel" onclick="sortbyth(this)">校区电话</th>
									<th >操作</th>
								</tr>
								</thead>
								<tbody>
									<#if page.list?exists>
								    <#list page.list as one>
									<tr>
									    <td>${one.code?if_exists}</td>
									    <td>
									    	<a class="font-blue-steel" href="${contextPath}/dict/edu/common/campus/<@commonMacros.scope />/${one.id}/read${page.pageSupport.paramVo.parm?if_exists?html}">
									    	    <u>${one.title?if_exists}<u>
									    	</a>
									    </td>
									    <td>${one.address?if_exists}</td>
									    <td>${one.tel?if_exists}</td>
									    <td class="td_btn_width_2">
									    	<#if one.locked?default(1) == 0 >
											<a class="btn default btn-xs green-seagreen" href="javascript:updateDictRecord('${contextPath}/dict/edu/common/campus/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');">
												修改</a>
											<a class="btn default btn-xs black" href="javascript:deleteDictRecord(this, '${contextPath}/dict/edu/common/campus/<@commonMacros.scope />/', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');">
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
