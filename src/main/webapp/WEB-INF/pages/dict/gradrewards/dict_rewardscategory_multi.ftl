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
							<form class="form-horizontal valid_form " name="dictForm" method="post" 
								action="${contextPath}/dict/edu/grad/rewardscategory/<@commonMacros.scope />/${rewardscategory.id?default(0)}/save${pageSupport.paramVo.parm?if_exists?html}" >
								
							    <input type="hidden" name="id" value="${rewardscategory.id?if_exists}"/>
							    
								<div class="form-body">
									<@spring.bind "rewardscategory.id" />
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
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />奖惩类型</label>
										<div class="col-md-4">
											<@commonMacros.multiSelect rewardstype showStyle "gradRewardsTypeId" rewardscategory.gradRewardsTypeId "required"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />奖惩名目名称</label>
										<div class="col-md-4">
											<#if showStyle=="read">
												<@commonMacros.multiInput showStyle "title" rewardscategory.title />
									    	<#else>
											<@spring.formInput  "rewardscategory.title" 'class="form-control required" maxlength="64" placeholder="奖惩名目名称"'/>
											<@spring.showErrors "" "alert font-red" />
											</#if>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />显示次序</label>
										<div class="col-md-4">
											<#if showStyle=="read">
												<@commonMacros.multiInput showStyle "priority" rewardscategory.priority />
									    	<#else>
											<@spring.formInput "rewardscategory.priority" 'class="form-control required int" range="1,99999" placeholder="显示次序"'/>
											<@spring.showErrors "" "alert font-red" />
											</#if>
										</div>
									</div>											
									
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />选用状态</label>
										<div class="col-md-4">
											<@commonMacros.multiLabel showStyle "status" "status" rewardscategory.status?default(1)/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />执行标准</label>
										<div class="col-md-5">
											 <@commonMacros.multiLabel showStyle "standard" "standard" rewardscategory.standard?default(-1)/>
										</div>
									</div>
								</div>
							
								<div class="row">
									<div class="text-center margin-bottom-10">
										<#if showStyle=="read">
										<button type="button" class="btn button-previous btn_cancel green-seagreen"  onclick="location.href = '${contextPath}/dict/edu/grad/rewardscategory/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
										<#else>
										<button type="submit" class="btn green-seagreen">保存</button>
										<button type="button" class="btn button-previous btn_cancel default black"  onclick="location.href = '${contextPath}/dict/edu/grad/rewardscategory/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">取消</button>
										</#if>
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
