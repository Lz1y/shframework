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
					<div class="portlet box green-seagreen">
						<div class="portlet-title">
							<div class="caption">
								${curMenu.title?if_exists}
							</div>
						</div>
						<div class="portlet-body form">
						    <#if college?exists>
							<form class="form-horizontal valid_form"  commandName="dict" name="dictForm" method="post"
								action="${contextPath}/dict/edu/common/college/<@commonMacros.scope />/${college.id?default(0)}/save${pageSupport.paramVo.parm?if_exists?html}">
							    <input type="hidden" name="id" value="${college.id?if_exists}"/>
											<div class="form-body">
											<@spring.bind "college.errorMsg" />
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
											        <label class="col-md-3 control-label"><@commonMacros.requiredMark />院校编码</label>
												    <div class="col-md-4">
														<@spring.formInput  "college.code" 'class="form-control required"  maxlength="20" placeholder="院校编码"'/>
														<@spring.showErrors "" "alert font-red" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />院校名称</label>
													<div class="col-md-4">
														<@spring.formInput  "college.title" 'class="form-control required"  maxlength="50" placeholder="院校名称"'/>
														<@spring.showErrors "" "alert font-red" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">代码类别</label>
													<div class="col-md-4">
														<@spring.formInput  "college.type" 'class="form-control"  maxlength="50" placeholder="代码类别"'/>
														<@spring.showErrors "" "alert font-red" />
													</div>
												</div>
												
											</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="submit" class="btn green-seagreen">保存</button>
											<button type="button" class="btn default button-previous btn_cancel"  onclick="location.href = '${contextPath}/dict/edu/common/college/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">取消</button>
										</div>
									</div>
								</div>
							</form>
							<#else>
								抱歉，没有找到符合条件的数据！<br/>
								<button type="button" class="btn green-seagreen button-previous btn_cancel" onclick="location.href = '${contextPath}/dict/edu/common/college/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'"">返回</button>
							</#if>
							<!-- END FORM-->
						</div>
					</div>
				</div>
			</div>
		<!-- END PAGE CONTENT-->
	</body>
</html>
