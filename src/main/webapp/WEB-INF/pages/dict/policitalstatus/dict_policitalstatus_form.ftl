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
						    <#if policitalstatus?exists>
							<form class="form-horizontal valid_form " name="dictForm" method="post" 
								action="${contextPath}/dict/common/policitalstatus/<@commonMacros.scope />/${policitalstatus.id?default(0)}/save${pageSupport.paramVo.parm?if_exists?html}" >
							    <input type="hidden" name="id" value="${policitalstatus.id?if_exists}"/>
											<div class="form-body">
											<@spring.bind "policitalstatus.errorMsg" />
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
											        <label class="col-md-3 control-label"><@commonMacros.requiredMark />政治面貌编码</label>
												    <div class="col-md-4">
														<@spring.formInput  "policitalstatus.code" 'class="form-control required"  maxlength="50"  placeholder="政治面貌编码"' />
														<@spring.showErrors "" "alert font-red" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />政治面貌名称</label>
													<div class="col-md-4">
														<@spring.formInput  "policitalstatus.title" 'class="form-control required" maxlength="50" placeholder="政治面貌名称"'/>
														<@spring.showErrors "" "alert font-red" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">政治面貌简称</label>
													<div class="col-md-4">
														<@spring.formInput  "policitalstatus.shortName" 'class="form-control" maxlength="50" placeholder="政治面貌简称"'/>
														<@spring.showErrors "" "alert font-red" />
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />选用状态</label>
													<div class="col-md-4">
														<@commonMacros.multiLabel showStyle "status" "status" policitalstatus.status?default(1)/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />执行标准</label>
													<div class="col-md-5">
														 <@commonMacros.multiLabel showStyle "standard" "standard" policitalstatus.standard?default(-1)/>
													</div>
												</div>
											</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="submit" class="btn green-seagreen">保存</button>
											<button type="button" class="btn default button-previous btn_cancel"  onclick="location.href = '${contextPath}/dict/common/policitalstatus/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">取消</button>
										</div>
									</div>
								</div>
							</form>
							<#else>
								抱歉，没有找到符合条件的数据！<br/>
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="button" class="btn green-seagreen button-previous"  onclick="location.href = '${contextPath}/dict/common/policitalstatus/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
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
