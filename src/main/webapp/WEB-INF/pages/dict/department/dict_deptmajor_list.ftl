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
				<form name="listform" action="${contextPath}/dict/edu/common/deptmajor/<@commonMacros.scope />/${departmentId?if_exists}/read" method="post" target="" >
					
						
						<div class="portlet-body">
						    
						    <div class="table-toolbar">
								<div class="row">
									<div class="col-md-6">
									    <#if curdepartment?? && curdepartment.type?default(0) == 1>
										<div class="btn-group">
											<a id="sample_editable_1_new" class="btn green-seagreen"
												href="javascript:addMajorDepartment('${curdepartment.id?if_exists}')" >
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
									<th>内部编码</th>
								    <th>专业编码</th>
									<th>专业名称</th>
									<th>院系</th>
									<th>校区</th>
									<th >操作</th>
								</tr>
								
								</thead>
								<tbody>
									<#if majorList?exists>
								    <#list majorList as one>
									<tr>
									    <td>${one.innerCode?if_exists}</td>
									    <td>
									    	${one.majorCode?if_exists}
									    </td>
									    <td>
									    	<@commonMacros.multiSelect major "list" "majorName" one.majorId/>
									    </td>
									    <td>
									       <@commonMacros.multiSelect department "list" "departmentName" one.departmentId/>
									    </td>
									    <td>
									       <@commonMacros.multiSelect campus "list" "campusName" one.campusId/>
									    </td>
									 
									    <td class="td_btn_width_3">
									    	<a class="btn btn-xs green-seagreen" href="javascript:updateMajorDepartmentRecord('${one.departmentId?if_exists}', '${one.majorId?if_exists}');">
												修改</a>
											<a class="btn default btn-xs black" href="javascript:deleteMajorDepartmentRecord(this, '${one.departmentId?if_exists}', '${one.majorId?if_exists}');">
												删除 </a>
									    </td>
									</tr>
									</#list>
									</#if>
								</tbody>
							</table>
							
						</div>
					
				</form>
				
		</div>
		<!-- END PAGE CONTENT-->
		<!-- common_util  通用工具-->
		<script src="${contextPath}/resources/js/common_util.js" type="text/javascript"></script>
		<script src="${contextPath}/resources/js/dict_management.js" type="text/javascript"></script>
		<script src="${contextPath}/resources/js/dict_majordepartment.js" type="text/javascript"></script>
		
	</body>
</html>
