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
					<form class="form-horizontal valid_form" name="dataForm" method="post" action="<#if showStyle=="edit" && role??>${contextPath}/sys/role/<@commonMacros.scope />/${role.id}/save<#else>${contextPath}/sys/role/<@commonMacros.scope />/0/save</#if>">
						<input type="hidden" name="module_id" value="${moduleId}" />
						<input type="hidden" name="id" value="${role.id}" />
						<div class="form-body">
							<#if (showStyle=="add" || showStyle=="edit") && role?? >
								<@spring.bind "role.id" />
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
							<input type="hidden" name="id" value="${resourceId!0}"/>
							<div class="form-group">
								<label class="col-md-3 control-label">	
									<#if showStyle=="add" || showStyle=="edit"><span style="color:red">*&nbsp;</span></#if>关联模块：</label>
								<div class="col-md-4">
									<#if showStyle="add" || showStyle="edit">
									<select name="resource_id" class="form-control required">
									    <#list moduleList as one>
										  <#if one.showMenu==1>
										    <option value="${one.id}" <#if (resourceId?exists && resourceId==one.id) || (!role.title?exists && moduleId=one.id)>selected</#if>> ${one.title}</option>
										  </#if>
								        </#list>
									</select>
									<#else>
										<input type="hidden" name="resource_id" value="${resourceId!0}"/>
										<#list moduleList as one>
										  <#if one.showMenu==1 && ((resourceId?exists && resourceId==one.id) || moduleId=one.id)>
										  	<@commonMacros.multiInput "read" "resourceId" one.title />
										  </#if>
								        </#list>
									</#if>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">	
									<#if showStyle=="add" || showStyle=="edit"><span style="color:red">*&nbsp;</span></#if>角色名称：</label>
								<div class="col-md-4">
									<@commonMacros.multiInput showStyle "title" role.title "required" "rangelength=2,16 " />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">	
									<#if showStyle=="add" || showStyle=="edit"><span style="color:red">*&nbsp;</span></#if>角色代码：</label>
								<div class="col-md-4">
									<@commonMacros.multiInput showStyle "code" role.code "required" "rangelength=2,16 " /> 
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">	
									<#if showStyle=="add" || showStyle=="edit"><span style="color:red">*&nbsp;</span></#if>显示次序：</label>
								<div class="col-md-4">
									<@commonMacros.multiInput showStyle "priority" role.priority "required digits" "range='0,999999'" /> 
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">	
									描述：</label>
								<div class="col-md-4">
									<@commonMacros.multiTextarea showStyle "description" role.description "" 'rangelength="0,1024"'/>
								</div>
							</div>
							
							<div class="form-actions">
								<div class="row">
									<div class="col-md-offset-3 col-md-9">
										<#if showStyle=="read">
											<a href="${contextPath}/sys/role/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm}<#if moduleId?exists>&module_id=${moduleId}</#if>" class="btn green-seagreen">返回</a>
										<#else>
											<button type="submit" class="btn green-seagreen">保存</button>
											<a href="${contextPath}/sys/role/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm}<#if moduleId?exists>&module_id=${moduleId}</#if>" class="btn default btn_cancel">取消</a>
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
