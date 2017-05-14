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
					<#assign moduleUrl>${contextPath}/sys/user</#assign>
					<#if isTch>
						<#assign moduleUrl>${contextPath}/edu/tch/employee</#assign>
					</#if>
					<#if departmentId?exists || allFlag?exists>
						<#assign extraParam>departmentId=${departmentId}&allFlag=${allFlag}</#assign>
					</#if>
					<#if pageSupport.paramVo.parm?html != "">
						<#assign extraParam>${pageSupport.paramVo.parm?html}&${extraParam}</#assign>
					<#elseif extraParam?exists>
						<#assign extraParam>?${extraParam}</#assign>
					</#if>
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
													<a href="${moduleUrl}/<@commonMacros.scope />/${userid}/dispatch${extraParam}">全部</a>
												</li>
										    	<#list moduleList as one>
										    		<#if one.showMenu==1 &&( !isTch?exists || (activeResIds?exists && activeResIds?seq_contains(one.id)))>
													<li>
														<a href="${moduleUrl}/<@commonMacros.scope />/${userid}/dispatch<#if extraParam?exists>${extraParam}&<#else>?</#if>module_id=${one.id}"> ${one.title}</a>
													</li>
													</#if>
										    	</#list>
											</ul>
											</#if>
									</div>
									<div class="col-md-6 col-sm-6 font-md">
										<div class="form-group">
											<label class="col-md-8 control-label">当前用户：<#if rolevo.user?exists>${rolevo.user.username}</#if></label>
										</div>
									</div>
									
								</div>
							</div>
						<form name="rolePermission" method="post" action="${moduleUrl}/<@commonMacros.scope />/${userid}/dispatch${extraParam}">
							<input type="hidden" name="isSave" value="1" />
							<input type="hidden" name="module_id" value="${module.id}" />
							<input type="hidden" name="departmentId" value="${departmentId}" />
							<input type="hidden" name="allFlag" value="${allFlag}" />
							<table class="table table-striped table-bordered table-hover">
								<#if rolevo.rlist?exists>
									<tr>
										<td>
											<input type="checkbox" value="roleid" onchange="cbkall(this)"/>
											<b>全选</b>
										</td>
									</tr>
									<#list rolevo.rlist as r>
										<tr>
											<td>
												<input name="roleid" type="checkbox" value="${r.id }" <#if rolevo.user?exists><#list rolevo.user.roles as role><#if role.id == r.id>checked</#if></#list></#if>/>
												${r.title}
											</td>
										</tr>
									</#list>
								</#if>
							</table>
							<div class="text-center">
							<button type="submit" class="btn green-seagreen" > 保存</button>
							<a class="btn default button-previous btn_cancel"  href="${moduleUrl}/<@commonMacros.scope />/all/list${extraParam}">取消</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
