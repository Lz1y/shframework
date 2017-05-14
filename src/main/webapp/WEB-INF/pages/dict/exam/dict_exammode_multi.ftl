<!--字典， 考试类型， 详情页-->
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
		<#if exammode?? >
			<@spring.bind "exammode.errorMsg" />
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
								action="${contextPath}/dict/edu/exam/${type}mode/<@commonMacros.scope />/${exammode.id?default(0)}/save${pageSupport.paramVo.parm?if_exists?html}" >
							    <input type="hidden" name="id" value="${exammode.id?if_exists}"/>
											<div class="form-body">
											<@spring.bind "exammode.id" />
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
											        <label class="col-md-3 control-label"><@commonMacros.requiredMark />考试类型</label>
												    <div class="col-md-4">
												    	<@commonMacros.multiSelect dictexammodetype showStyle "type" exammode.type?default(0) "required"/>
													</div>
												</div>											
											    <div class="form-group">
											        <label class="col-md-3 control-label"><@commonMacros.requiredMark />考试考查</label>
												    <div class="col-md-4">
												    	<@commonMacros.multiSelect staticlabel.examFlag showStyle "examFlag" exammode.examFlag?default(1) "required"/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />考试形式名称</label>
													<div class="col-md-4">
														<#if showStyle=="read">
															<@commonMacros.multiInput showStyle "title" exammode.title />
												    	<#else>
														<@spring.formInput  "exammode.title" 'class="form-control required" maxlength="50" placeholder="考试形式名称"'/>
														<@spring.showErrors "" "alert font-red" />
														</#if>
													</div>
												</div>												
											</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<#if showStyle=="read">
											<button type="button" class="btn green-seagreen button-previous btn_cancel"  onclick="location.href = '${contextPath}/dict/edu/exam/${type}mode/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
											<#else>
											<button type="submit" class="btn green-seagreen">保存</button>
											<button type="button" class="btn default button-previous btn_cancel"  onclick="location.href = '${contextPath}/dict/edu/exam/${type}mode/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">取消</button>
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
