<!--字典， 考试类型， 列表页-->
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
				<form name="listform" action="${contextPath}/dict/edu/exam/${type}mode/<@commonMacros.scope />/all/list" method="post"  class="form-horizontal" role="form">
				    
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
								<div class="col-md-3">
									<div class="form-group ">
										<label class="col-md-5 control-label">考试类型：</label>
										<div class="col-md-7">
										    <@commonMacros.multiSelect dictexammodetype "search" "_deem.type" page.pageSupport.paramVo.map['_deem.type']/>
										</div>
									</div>
								</div>
								<!--/span-->						    
						    	
						    	<!--/span-->
								<div class="col-md-3">
									<div class="form-group ">
										<label class="col-md-5 control-label">考试考查：</label>
										<div class="col-md-7">
										    <@commonMacros.multiSelect staticlabel.examFlag "search" "_deem.exam_flag" page.pageSupport.paramVo.map['_deem.exam_flag']/>
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
												href="${contextPath}/dict/edu/exam/${type}mode/<@commonMacros.scope />/0/add${page.pageSupport.paramVo.parm?if_exists?html}">
												新增
											</a>
										</div>
									</div>
								</div>
							</div>
						
							<table class="table table-striped table-bordered table-hover util-btn-margin-bottom-5" id="datatable_ajax">
								<thead>
								<tr role="row" class="heading">
									<th>考试类型</th>
								    <th>考试考查（统考）</th>
								    <th>考试形式</th>
									<th>操作</th>
								</tr>
								
								</thead>
								<tbody>
									<#if page.list?exists>
								    <#list page.list as one>
									<tr>
										<td>
									    	<@commonMacros.multiSelect dictexammodetype "list" "" one.type/>											
										</td>
									    <td>
									    	<@commonMacros.multiSelect staticlabel.examFlag "list" "" one.examFlag/>
									    </td>
									    <td>
									    	<@commonMacros.multiInput showStyle ""  one.title />
									    </td>
									    <td class="td_btn_width_2">
									        <#if one.locked?default(-1) == 0 >
									    	<a class="btn btn-xs green-seagreen" href="javascript:updateDictRecord('${contextPath}/dict/edu/exam/${type}mode/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');">
												修改</a>
											<a class="btn default btn-xs black" href="javascript:deleteDictRecord(this, '${contextPath}/dict/edu/exam/${type}mode/<@commonMacros.scope />', ${one.id?if_exists}, '${page.pageSupport.paramVo.parm?if_exists?html}');">
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
