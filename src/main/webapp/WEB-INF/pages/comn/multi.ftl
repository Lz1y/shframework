<#import "/spring.ftl" as spring />  
<#import "/macro/commonMacros.ftl" as commonMacros />
<#setting classic_compatible=true>

<html>
	<head>
		<meta charset="utf-8"/>
		<title>${curMenu.title }</title>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/jquery-multi-select/css/multi-select.css"/>
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		<@commonMacros.breadcrumbNavigation />
		<div class="tab-pane active" id="tab_0">
			<div class="portlet box <#if showStyle=="tplread">blue-steel<#else>green-seagreen</#if>">
			
				<div class="portlet-title">
					<div class="caption">
						<span class="caption">${curMenu.title } </span>
					</div>
				</div>
				
				<div class="portlet-body form">
				<!-- BEGIN FORM-->
					<form class="form-horizontal valid_form" id="templateForm" name="templateForm" method="post" action="<#if showStyle="tpledit">${contextPath}/${curMenu.pUrl}/${template.id}/tplsave${pageSupport.paramVo.parm?if_exists?html}&module=${curMenu.rule}&pUrl=${curMenu.pUrl}<#else>${contextPath}/${curMenu.pUrl}/0/tplsave?module=${curMenu.rule}&pUrl=${curMenu.pUrl}</#if>" onsubmit="">
					<input type="hidden" id="file_format" name="file_format" value="${fileFormat}"/>
					<input type="hidden" name="fileFormat" value="${fileFormat}"/>
					<input type="hidden" name="type" value="${type}"/>

					<#if showStyle=="tpledit"><input type="hidden" name="id" value="${template.id}"/></#if>
					<div class="form-body">
							<div class="row">
								<div class="col-md-8">
									<div class="form-group">
										<label class="control-label col-md-2">模板类别：</label>
										<div class="col-md-10">
											<label style="padding-left: 0px;margin-top:8px;">${curMenu.title }</label>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-8">
										<div class="form-group">
											<label class="control-label col-md-2"><span style="color:red">*&nbsp;</span>模板名称：</label>
											<div class="col-md-10">
												<@commonMacros.multiInput "add" "title" template.title "required illchar" "rangelength=2,16 " />
											</div>
										</div>
									</div>
							</div>
							
							<div class="row">
								<div class="col-md-8">
									<div class="form-group last">
										<label class="control-label col-md-2">选择导出字段：</label>
										<div class="col-md-10">
											<select multiple="multiple" class="multi-select" id="my_multiple_select" name="my_multiple_select[]">
												<#if columnCommentTarget??>
													<#if (columnCommentTarget?size >0)>
														<#list columnCommentTarget as columnCommentMap>
															<#list columnCommentMap?keys as key>
													    		<option value="${key}">
														    			${columnCommentMap[key]} 
													    		</option>
													    	</#list>
												    	</#list>
										    			<#if multipleSelectMap??>
															<#list multipleSelectMap?keys as key>
													    		<option value="${key}" selected >
														    			${multipleSelectMap[key]} 
													    		</option>
													    	</#list>
										    			</#if>
												    </#if>	
												</#if>		    
											</select>
										</div>
									</div>
							</div>
							
							<div class="row">
								<div class="col-md-8">
										<div class="form-group">
											<label class="control-label col-md-2">模板描述：</label>
											<div class="col-md-10">
												<@commonMacros.multiTextarea "add" "description" template.description "illchar" 'rangelength="0,1024"' />
											</div>
										</div>
									</div>
							</div>
							
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<a class="btn green-seagreen" href="javascript:;" onclick="complete();">保存</a>
											<a href="${contextPath}/${curMenu.pUrl}/all/tpllist?module=${curMenu.rule}&pUrl=${curMenu.pUrl}" class="btn default btn_cancel">取消</a>
										</div>
									</div>
								</div>
						</div>		
					</form>
					<!-- END FORM-->
				</div>
			</div>
		</div>
		<jsplugininner>
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/admin/pages/scripts/components-dropdowns-templateSet.js"></script>
		</jsplugininner>
		<jsinner>
			<script>
				$(document).ready(function() {
           			ComponentsDropdowns.init();
           			bootbox.setDefaults({locale:"zh_CN"});
				});//ready()
				
				var complete = function() {
					if($("#my_multiple_select").val() != null && $("#my_multiple_select").val() != ""){
							 $("#templateForm").submit();
						}
					else{
							bootbox.alert("还未选择要导出的字段");
						}
				}	
				
			</script>
		</jsinner>
	</body>
</html>
