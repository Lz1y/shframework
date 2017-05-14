<#import "/spring.ftl" as spring />  
<#import "/macro/commonMacros.ftl" as commonMacros />
<#---资源(res)新增/修改--->
<html>
	<head>
		<title>${curMenu.title }</title>
		
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
					<form class="form-horizontal valid_form" name="dataForm" method="post" action="<#if showStyle=="edit" && res??>${contextPath}/sys/resource/<@commonMacros.scope />/${res.id}/save<#else>${contextPath}/sys/resource/<@commonMacros.scope />/0/save</#if>">
						
						<input type="hidden" name="id" value="${res.id}" />
						<input type="hidden" name="parentId" value="${res.parentId}" />
						<input type="hidden" name="resId" value="${resId}" />
						<div class="form-body">
							<#if (showStyle=="add" || showStyle=="edit") && res?? >
								<@spring.bind "res.id" />
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
									<#if showStyle=="add" || showStyle=="edit"><span style="color:red">*&nbsp;</span></#if>资源名称：</label>
								<div class="col-md-4">
									<@commonMacros.multiInput showStyle "title" res.title "required" "rangelength=2,64 " />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">	
									<#if showStyle=="add" || showStyle=="edit"><span style="color:red">*&nbsp;</span></#if>命名规则：</label>
								<div class="col-md-4">
									<@commonMacros.multiInput showStyle "rule" res.rule "required" "rangelength=2,64 " />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">资源路径：</label>
								<div class="col-md-4">
									<@commonMacros.multiInput showStyle "url" res.url "" "rangelength=0,1024 " />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">	
									<#if showStyle=="add" || showStyle=="edit"><span style="color:red">*&nbsp;</span></#if>菜单是否显示：</label>
								<div class="col-md-4">
									<@commonMacros.multiSelect staticlabel.isZeroORNot showStyle "showMenu" res.showMenu?default(1) />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">	
									<#if showStyle=="add" || showStyle=="edit"><span style="color:red">*&nbsp;</span></#if>显示次序：</label>
								<div class="col-md-4">
									<@commonMacros.multiInput showStyle "priority" res.priority "required digits" "range='0,999999'" /> 
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">	
									描述：</label>
								<div class="col-md-4">
									<@commonMacros.multiTextarea showStyle "description" res.description "" 'rangelength="0,1024"'/>
								</div>
							</div>
							
							<div class="form-actions">
								<div class="row">
									<div class="col-md-offset-3 col-md-9">
										<#if showStyle=="read">
											<a href="${contextPath}/sys/resource/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm}&<#if resId?exists>&resId=${resId}</#if>" class="btn green-seagreen">返回</a>
										<#else>
											<button type="submit" class="btn green-seagreen">保存</button>
											<a href="${contextPath}/sys/resource/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm}&<#if resId?exists>&resId=${resId}</#if>" class="btn default btn_cancel">取消</a>
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
