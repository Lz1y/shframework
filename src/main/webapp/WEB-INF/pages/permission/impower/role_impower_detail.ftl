<#--角色授权详情页-->
<#import "/macro/commonMacros.ftl" as commonMacros />

<html>
	<head>
		<title>${curMenu.title }</title>
		
	</head>
	<body>
		<@commonMacros.breadcrumbNavigation />
		<div class="row">
			<div class="col-md-12">
				<div class="portlet box blue-steel">
					<div class="portlet-title">
						<div class="caption">
							${curMenu.title }
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a>
						</div>
					</div>
					
					<div class="portlet-body">
							<div class="row">
								<div class="col-md-2">
									<div class="form-group">
										<label class="control-label col-md-12">资源：<#list moduleList as module><#if module.id == moduleId>${module.title}</#if></#list></label>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-12">角色：${sysrole[roleId+""].title}</label>
									</div>
								</div>
								<!--/span-->
						    	<div class="col-md-4">
									<div class="form-group ">
										<label class="col-md-12 control-label">资源可见范围：	${(staticlabel.scopetype)[scopeInteger+""].title}</label>
										
									</div>
								</div>
								<!--/span-->
							</div><!--row end-->
							
							<hr>
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr class="heading">
										<th>
											 
										</th>
										<#if srrpvo.plist??>
											<#list srrpvo.plist as p>
												<th>
													${p.title}<br/>
												</th>
											</#list>
										</#if>
									</tr>
								</thead>
								<tbody>
									<#assign resPermissionMap=srrpvo.resPermissionMap/>
									<#if srrpvo.rlist??>
										<#list srrpvo.rlist as r>
											<tr>
												<td>
													<#if r.level==0><b><#elseif r.level==1><b><#elseif r.level gt 1><#list 1..r.level as times>&nbsp;&nbsp;</#list></#if>${r.title}
												</td>
												<#if srrpvo.plist??>
													<#list srrpvo.plist as p>
														<td>
															<#if  resPermissionMap[r.rule]?exists && resPermissionMap[r.rule].perList?seq_contains(p.code)>
																<#assign isChecked=false/>
																<#list srrpvo.list as main><#if (main.resourceId == r.id || main.resourceId == 1) && (main.permissionId == 1 || main.permissionId == p.id)><#assign isChecked=true/><#break></#if></#list>
																
																<input type="checkbox" name="rp" class="res_${r.id} per_${p.id}" value="${r.id}_${p.id}" 
																		<#if isChecked>checked</#if>
																		disabled
																/>
																
															</#if>
														</td>
													</#list>
												</#if>
											</tr>
										</#list>
									</#if>
								</tbody>
								<#if srrpvo.rlist?? && srrpvo.rlist?size gt 10>
								<thead>
									<tr class="heading">
										<th></th>
										<#if srrpvo.plist??>
										<#list srrpvo.plist as p>
										<th>
											${p.title}
										</th>
										</#list>
										</#if>
									</tr>
								</thead>
								</#if>
							</table>
							
					</div><!--portlet-body end-->
				</div>
			</div>
		</div><!-- row end-->
		
		<div class="row">
			<div class="col-md-12">
				<div class="portlet box blue-steel">
					<div class="portlet-title">
						<div class="caption">已分配角色用户信息	</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a>
						</div>
					</div>
					
					<div class="portlet-body">
								<div class="row">
									<#if userIds?? && userIds?size gte 1>
										<div class="col-md-12">
											<table class="table table-striped table-bordered table-hover">
												<thead>
													<tr role="row" class="heading  th_min_width">
														<th>序号</th><th>姓名</th><th>员工号</th><th>性别</th><th>部门</th>
													</tr>
												</thead>
												<tbody>
												<#list userIds as userid>
													<#if employeename[userid + ""] ??>
														<#assign employee = employeename[userid + ""] /> 
													<tr>
														<td>${userid_index + 1}</td>
														<td><@commonMacros.multiSelect employeename "list" "" userid /></td>
														<td><@commonMacros.multiInput "list" "" employee.userNo /></td>
														<td><@commonMacros.multiInput "list" "" employee.genderName /></td>
														<td><@commonMacros.multiInput "list" "" employee.depName /></td>
													</tr>
													
													</#if>
												</#list>
												</tbody>
											</table>
										</div>
									<#else>
										
											<div class="text-center">
												无
											</div>
									</#if>
								</div>
								
					</div><!--portlet-body end-->
				</div>
			</div>
		</div><!-- row end-->
		
		<div class="row">
			<div class="text-center">
				<a class="btn button-previous btn_cancel green-seagreen"  href="${contextPath}/sys/role/<@commonMacros.scope />/all/list<#if redirectModuleId?exists>?module_id=${redirectModuleId}</#if>">返回</a>
			</div>
		</div><!-- row end-->		
		
		<!-- END PAGE CONTENT-->
		<div id="siteMeshJavaScriptPlugins">
 			
		</div>
		<div id="siteMeshJavaScript">
			<script>
			
				$('#confirm-delete').on('show.bs.modal', function(e) {
				    $(this).find('.danger').attr('href', $(e.relatedTarget).data('href'));
				    $('.debug-url').html('Delete URL: <strong>' + $(this).find('.danger').attr('href') + '</strong>');
				});
				
			</script>
		</div>
	</body>
</html>

