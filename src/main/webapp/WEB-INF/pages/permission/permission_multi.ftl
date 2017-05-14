<#import "/spring.ftl" as spring />  
<#import "/macro/commonMacros.ftl" as commonMacros />

<html>
	<head>
		<title>${curMenu.title }</title>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
	</head>
	<body>
		<@commonMacros.breadcrumbNavigation />
		<div class="tab-pane active" id="tab_0">
			<div class="portlet box <#if showStyle=="read">blue-steel<#else>green-seagreen</#if>">
				<div class="portlet-title">
					<div class="caption">
						<span class="caption">${curMenu.title }</span>
					</div>
				</div>
				<div class="portlet-body form">
					<form class="form-horizontal valid_form" name="dataForm" method="post" action="<#if showStyle=="edit" && permission??>${contextPath}/sys/permission/<@commonMacros.scope />/${permission.id}/save<#else>${contextPath}/sys/permission/<@commonMacros.scope />/0/save</#if>">
						<input type="hidden" name="id" value="${permission.id}" />
						<div class="form-body">
							<#if (showStyle=="add" || showStyle=="edit") && permission?? >
								<@spring.bind "permission.id" />
								<#if spring.status.error>
									<div class="alert alert-danger">
										<button class="close" data-close="alert"></button>
										<span>  
											<#list spring.status.errorMessages as error>
											<li>${error}</li>
											</#list> 
										</span>
									</div>
								</#if>
							</#if>
							
							<div class="form-group">
								<label class="col-md-3 control-label">	
									<#if showStyle=="add" || showStyle=="edit"><span style="color:red">*&nbsp;</span></#if>权限代码：</label>
								<div class="col-md-4">
									<@commonMacros.multiInput showStyle "code" permission.code "required" "rangelength=2,16 " /> 
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">	
									<#if showStyle=="add" || showStyle=="edit"><span style="color:red">*&nbsp;</span></#if>权限名称：</label>
								<div class="col-md-4">
									<@commonMacros.multiInput showStyle "title" permission.title "required" "rangelength=2,16 " />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">	
									<#if showStyle=="add" || showStyle=="edit"><span style="color:red">*&nbsp;</span></#if>显示次序：</label>
								<div class="col-md-4">
									<@commonMacros.multiInput showStyle "priority" permission.priority "required digits"  "range='0,999999'"/> 
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">描述：</label>
								<div class="col-md-4">
									<@commonMacros.multiTextarea showStyle "description" permission.description "illchar" 'rangelength="0,1024"'/>
								</div>
							</div>
							
							<div class="form-actions">
								<div class="row">
									<div class="col-md-offset-3 col-md-9">
										<#if showStyle=="read">
											<a href="${contextPath}/sys/permission/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm}" class="btn green-seagreen">返回</a>
										<#else>
											<button type="submit" class="btn green-seagreen">保存</button>
											<a href="${contextPath}/sys/permission/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm}" class="btn default btn_cancel">取消</a>
										</#if>
									</div>
								</div>
							</div>
						
						</div>
					</form>
				</div>
			</div>
		</div>
		
		
		<jsplugininner>
		    <!-- BEGIN PAGE LEVEL PLUGINS -->
			
			
			<!-- END PAGE LEVEL PLUGINS -->
		</jsplugininner>
		
		<jsinner>
		<script>
			$(document).ready(function() {
			 	
			});
			
		</script>
		</jsinner>
		
		
		

	</body>
</html>
