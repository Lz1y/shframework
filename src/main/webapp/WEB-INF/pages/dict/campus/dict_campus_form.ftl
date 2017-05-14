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
							<#if campus?exists>
							<form class="form-horizontal valid_form" commandName="dict" name="dictForm" method="post"
								action="${contextPath}/dict/edu/common/campus/<@commonMacros.scope />/${campus.id?default(0)}/save${pageSupport.paramVo.parm?if_exists?html}" >
							    <input type="hidden" name="id" value="${campus.id?if_exists}"/>
											<div class="form-body">
											<@spring.bind "campus.errorMsg" />
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
											        <label class="col-md-3 control-label"><@commonMacros.requiredMark />校区编码</label>
												    <div class="col-md-4">
														<@spring.formInput  "campus.code" 'class="form-control required"  maxlength="20" placeholder="校区编码"'/>
														<@spring.showErrors "" "alert font-red" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />校区名称</label>
													<div class="col-md-4">
														<@spring.formInput  "campus.title" 'class="form-control required" maxlength="50" placeholder="校区名称"'/>
														<@spring.showErrors "" "alert font-red" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">校区地址</label>
													<div class="col-md-4">
														<@spring.formInput  "campus.address" 'class="form-control" maxlength="200" placeholder="校区地址"'/>
														<@spring.showErrors "" "alert font-red" />
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-md-3 control-label">联系电话</label>
													<div class="col-md-4">
														<@spring.formInput  "campus.tel" 'class="form-control telcheck"  maxlength="20" placeholder="联系电话"'/>
														<@spring.showErrors "" "alert font-red" />
													</div>
												</div>
												
											</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="submit" class="btn green-seagreen">保存</button>
											<button type="button" class="btn default button-previous btn_cancel" onclick="location.href = '${contextPath}/dict/edu/common/campus/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">取消</button>
										</div>
									</div>
								</div>
							</form>
							<#else>
								抱歉，没有找到符合条件的数据！<br/>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="button" class="btn green-seagreen button-previous" onclick="location.href = '${contextPath}/dict/edu/common/campus/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
										</div>
									</div>
								</div>
							</#if>
							<!-- END FORM-->
						</div>
					</div>
				</div>
			</div>
		<!-- END PAGE CONTENT-->
	</body>
</html>
