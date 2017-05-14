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
				<form name="listform" action="${contextPath}/dict/edu/grad/rewardsreason/<@commonMacros.scope />/all/list" method="post"  class="form-horizontal" role="form">
				    
				    <div class="portlet box green-seagreen">
						<div class="portlet-title">
							<div class="caption">搜索</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
							</div>
						</div>
						
						<div class="portlet-body">
						    <div class="row ">
						    
								<!--/span-->
								<div class="col-md-4">
									<div class="form-group ">
										<label class="col-md-5 control-label">奖惩类型：</label>
										<div class="col-md-7">
										    <@commonMacros.multiSelect rewardstype "search" "_degrr.grad_rewards_type_id" page.pageSupport.paramVo.map['_degrr.grad_rewards_type_id'] "required"/>
										</div>
									</div>
								</div>
								<!--/span-->
														    
								<!--/span-->
								<div class="col-md-4">
									<div class="form-group ">
										<label class="col-md-5 control-label">选用状态：</label>
										<div class="col-md-7">
										    <@commonMacros.multiSelect staticlabel.status "search" "_degrr.status" page.pageSupport.paramVo.map['_degrr.status']/>
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-4">
									<div class="form-group ">
										<label class="col-md-5 control-label"> 执行标准：</label>
										<div class="col-md-7">
											<@commonMacros.multiSelect staticlabel.standard "search" "_degrr.standard" "${page.pageSupport.paramVo.map['_degrr.standard']?if_exists}"/>
										</div>
									</div>
								</div>
								<!--/span-->
								
						    	<!--/span-->
						    	<div class="col-md-4">
									<div class="form-group">
										<label class="col-md-6 control-label">奖惩名称或处分原因：</label>
										<div class="col-md-6">
											<input class="form-control" name="_degrr.title.3" type="text"
												value="<#if page.pageSupport.paramVo.map?exists>${page.pageSupport.paramVo.map['_degrr.title.3']?if_exists?html}</#if>" />
										</div>
									</div>
								</div>
								<!--/span-->								
								
								<!--/span-->
								<div class="col-md-8">
									<div class="form-group ">
										<div class="col-md-12">
											<button class="btn table-group-action-submit btn_search green-seagreen" type="submit">搜索</button>
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
											<a id="sample_editable_1_new" class="btn green-seagreen" href="${contextPath}/dict/edu/grad/rewardsreason/<@commonMacros.scope />/0/add${page.pageSupport.paramVo.parm?if_exists?html}">新增</a>
										</div>
									</div>
								</div>
							</div>
						
							<table class="table table-striped table-bordered table-hover util-btn-margin-bottom-5" id="datatable_ajax">
								<thead>
									<tr role="row" class="heading">
									    <th >奖惩类型</th>
									    <th >奖惩名称或处分原因</th>
										<th >选用状态</th>
										<th >执行标准</th>
										<th >操作</th>
									</tr>
								</thead>
								<tbody>
									<#if page.list?exists>
								    <#list page.list as one>
									<tr>
									    <td><@commonMacros.multiInput showStyle "gradRewardsTypeTitle" one.gradRewardsTypeTitle /></td>
									    <td><@commonMacros.multiInput showStyle "title" one.title /></td>
									    <td>
									    	<@commonMacros.multiSelect staticlabel.status showStyle "status" "${one.status?default(1)}"/>
									   		<a class="font-blue" href="javascript:changeDictStatus(this, ${one.status?default(1)}, '${contextPath}/dict/edu/grad/rewardsreason/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');" >
									   		<@commonMacros.showStatusMark one.status />
									    </td>
									    <td>
									    	<@commonMacros.multiSelect staticlabel.standard "list" "standard" "${one.standard?default(-1)}"/>
									    </td>
									    <td class="td_btn_width_2">
									        <#if one.locked?default(-1) == 0 >
									    		<a class="btn btn-xs green-seagreen" href="javascript:updateDictRecord('${contextPath}/dict/edu/grad/rewardsreason/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');">修改</a>
												<a class="btn btn-xs default black" href="javascript:deleteDictRecord(this, '${contextPath}/dict/edu/grad/rewardsreason/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');">删除 </a>
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
