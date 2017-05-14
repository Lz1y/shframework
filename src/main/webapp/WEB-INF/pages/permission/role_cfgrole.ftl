<#import "/macro/commonMacros.ftl" as commonMacros />

<html>
	<head>
		<title>${curMenu.title }</title>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/select2/select2.css"/>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
	</head>
	<body>
		<@commonMacros.breadcrumbNavigation />
		<div class="row">
			<div class="col-md-12">
				<div class="portlet box green-seagreen">
					<div class="portlet-title">
						<div class="caption">
							${curMenu.title }
						</div>
					</div>
					
					<div class="portlet-body">
						<div class="table-toolbar">
								<div class="row">
									<div class="col-md-4 col-sm-6">
										<#if moduleList?exists>
											<a style="width:125px;" class="btn grey dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
												 <#if !module?exists>全部<#else>${module.title}</#if> <i class="fa fa-angle-down"></i>
											</a>
											<ul class="dropdown-menu" style="left:15px;top:24px;min-width:125px;">
												<li>
													<a href="${contextPath}/sys/role/<@commonMacros.scope />/${roleId}/dispatch<#if redirectModuleId?exists>?redirect_module_id=${redirectModuleId}</#if>">全部</a>
												</li>
										    	<#list moduleList as one>
										    		<#if one.showMenu==1>
													<li>
														<a href="${contextPath}/sys/role/<@commonMacros.scope />/${roleId}/dispatch?module_id=${one.id}<#if redirectModuleId?exists>&redirect_module_id=${redirectModuleId}</#if>"> ${one.title}</a>
													</li>
													</#if>
										    	</#list>
											</ul>
											</#if>
									</div>
									<div class="col-md-6 col-sm-6 font-md">
										<div class="form-group">
											<label class="col-md-8 col-sm-12 control-label">当前角色：<@commonMacros.multiSelect sysrole "list" "" roleId /> </label>
										</div>
									</div>
								</div>
							</div>
						<form name="rolePermission" method="post" action="${contextPath}/sys/role/<@commonMacros.scope />/${roleId}/dispatch">
							<input type="hidden" name="isSave" value="1" />
							<input type="hidden" name="module_id" value="${moduleId}" />
							<input type="hidden" name="redirect_module_id" value="${redirectModuleId}" />
							<table class="table table-striped table-bordered table-hover">
								
									<#list roleList as r>
										<tr>
											<td>
												<input name="cfgRoleId" type="checkbox" value="${r.id }" <#if cfgRoleIdList?seq_contains(r.id)>checked</#if>/>
												${r.title}
											</td>
										</tr>
									</#list>
								
							</table>
							<div class="text-center">
							<button type="submit" class="btn green-seagreen" > 保存</button>
							<a class="btn button-previous btn_cancel default"  href="${contextPath}/sys/role/<@commonMacros.scope />/all/list<#if redirectModuleId?exists>?module_id=${redirectModuleId}</#if>">取消</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>