<#--部门列表-->
<#import "/macro/commonMacros.ftl" as commonMacros />
<html>
	<head>
		<title></title>
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		<div class="row">
			<div class="col-md-12">
				<form name="listform" action="${contextPath}/dict/edu/common/department/<@commonMacros.scope />/${departmentId?if_exists}/list" method="post" target="" >
					
						
						<div class="portlet-body">
						    
						    <div class="table-toolbar">
								<div class="row">
									<div class="col-md-6">
									    <#if departmentId??>
										<div class="btn-group">
											<a id="sample_editable_1_new" class="btn green-seagreen"
												href="javascript:addDivision('${departmentId}')" >
												新增
											</a>
										</div>
										</#if>
									</div>
								</div>
							</div>
						
							<table class="table table-striped table-bordered table-hover util-btn-margin-bottom-5" id="datatable_ajax">
								<thead>
								<tr role="row" class="heading">
									<th>序号</th>
								    <th>部门编码</th>
									<th>部门名称</th>
									<th>类型</th>
									<th>校区</th>
									<th >操作</th>
								</tr>
								
								</thead>
								<tbody>
									
								    <#list divisionList as one>
									<tr>
									    <td>${one_index + 1}</td>
									    <td>
									    	${one.code?if_exists}
									    </td>
									    <td>
									    	${one.title?if_exists}
									    </td>
									    <td>
									      <@commonMacros.multiLabel showStyle "departmentType" "type" one.type?default(1)/>
									    </td>
									    <td>
									       <@commonMacros.multiSelect campus "list" "campusName" one.campusId/>
									    </td>
									 
									    <td class="td_btn_width_3">
									    	<a class="btn btn-xs green-seagreen" href="javascript:updateDivisionRecord('${one.id}');">
												修改</a>
											<a class="btn default btn-xs black" href="javascript:deleteDivisionRecord(this, '${one.id}');">
												删除 </a>
									    </td>
									</tr>
									</#list>
									
								</tbody>
							</table>
							
						</div>
					
				</form>
				
		</div>
		<!-- END PAGE CONTENT-->
		<!-- common_util  通用工具-->
		<script src="${contextPath}/resources/js/common_util.js" type="text/javascript"></script>
		<script src="${contextPath}/resources/js/dict_management.js" type="text/javascript"></script>
		<script src="${contextPath}/resources/js/dict_division.js" type="text/javascript"></script>
		
	</body>
</html>
