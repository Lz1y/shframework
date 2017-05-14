<#--原因 字典 详情/增/改-->
<#import "/spring.ftl" as spring />
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
		
			<div class="tab-content">
				<div class="tab-pane active" id="tab_0">
					<div class="portlet box <#if showStyle=="read">blue-steel<#else>green-seagreen</#if>">
						<div class="portlet-title">
							<div class="caption">
								${curMenu.title?if_exists}
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal valid_form " name="dictForm" method="post" 
								action="${contextPath}/dict/edu/${pathPrefix}/${reasonType}/<@commonMacros.scope />/${reason.id?default(0)}/save${pageSupport.paramVo.parm?if_exists?html}" >
											<div class="form-body">
											<@spring.bind "reason.id" />
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
											   
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />原因：</label>
													<div class="col-md-4">
														<#if showStyle=="read">
															<@commonMacros.multiInput showStyle "title" reason.title />
												    	<#else>
														<@spring.formInput  "reason.title" 'class="form-control required" maxlength="50" placeholder="原因"'/>
														<@spring.showErrors "" "alert font-red" />
														</#if>
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />选用状态：</label>
													<div class="col-md-4">
														<@commonMacros.multiLabel showStyle "status" "status" reason.status?default(1)/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />执行标准：</label>
													<div class="col-md-5">
														 <@commonMacros.multiLabel showStyle "standard" "standard" reason.standard?default(-1)/>
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />显示次序：</label>
													<div class="col-md-4">
														<@commonMacros.multiInput showStyle "priority" reason.priority "required digits" "range='0,999999'" /> 
													</div>
												</div>
											</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<#if showStyle=="read">
											<button type="button" class="btn green-seagreen button-previous btn_cancel"  onclick="location.href = '${contextPath}/dict/edu/${pathPrefix}/${reasonType}/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
											<#else>
											<button type="submit" class="btn green-seagreen">保存</button>
											<button type="button" class="btn default button-previous btn_cancel"  onclick="location.href = '${contextPath}/dict/edu/${pathPrefix}/${reasonType}/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">取消</button>
											</#if>
										</div>
									</div>
								</div>
							</form>
							<!-- END FORM-->
							
						</div>
					</div>
				</div>
			</div>
		<!-- END PAGE CONTENT-->
	</body>
</html>
