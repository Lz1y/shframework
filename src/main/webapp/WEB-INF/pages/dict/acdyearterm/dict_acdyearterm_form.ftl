<#import "/spring.ftl" as spring />
<#import "/macro/commonMacros.ftl" as commonMacros />
<html>
	<head>
		<title>${curMenu.title?if_exists}</title>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
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
						    <#if acdyearterm?exists>
							<form class="form-horizontal valid_form" name="dictForm" method="post"
								action="${contextPath}/dict/edu/common/acdyearterm/<@commonMacros.scope />/${acdyearterm.id?default(0)}/save${pageSupport.paramVo.parm?if_exists?html}" >
							    <input type="hidden" name="id" value="${acdyearterm.id?if_exists}"/>
							    <div class="form-body">
							    <@spring.bind "acdyearterm.errorMsg" />
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
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />学年</label>
										<div class="col-md-4">
											<@commonMacros.multiSelect academicyear showStyle "academicYearId" acdyearterm.academicYearId/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />学期</label>
										<div class="col-md-4">
											<@commonMacros.multiSelect term showStyle "termId" acdyearterm.termId/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />学年学期代码</label>
										<div class="col-md-4">
											<#if showStyle?? && showStyle == "add">
											<@spring.formInput  "acdyearterm.code" 'class="form-control required yeartermcode" maxlength="20" placeholder="学年学期代码"'/>
											<@spring.showErrors "" "alert font-red" />
											<#else>
											<input class="form-control" type="text" name="code" value="${acdyearterm.code?if_exists?html}" readonly />
											
											</#if>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />教学周扣除数</label>
										<div class="col-md-4">
											<@spring.formInput  "acdyearterm.deductWeeks" 'class="form-control required" max="52" placeholder="教学周扣除数"'/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									
									<div class="input-daterange data-picker">
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />开始日期</label>
										<div class="col-md-4">
											<input class="form-control required" placeholder="开始时间"  name="startDate" id="startDate" type="text" value="<#if acdyearterm.startDate?exists>${acdyearterm.startDate?string('yyyy-MM-dd')}</#if>" style="text-align: left"/>
											<@spring.bind  "acdyearterm.startDate"/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />结束日期</label>
										<div class="col-md-4">
											<input class="form-control required" placeholder="结束时间"  name="endDate" id="endDate" type="text" value="<#if acdyearterm.endDate?exists>${acdyearterm.endDate?string('yyyy-MM-dd')}</#if>"  style="text-align: left"/>
											<@spring.bind  "acdyearterm.endDate"/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">备注</label>
										<div class="col-md-4">
											<input type="text" class="form-control " maxlength="10240" placeholder="备注"  name="notes" value="${acdyearterm.notes?if_exists?html}"/>
										</div>
									</div>
									
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="submit" class="btn green-seagreen">保存</button>
											<button type="button" class="btn default button-previous btn_cancel"  onclick="location.href = '${contextPath}/dict/edu/common/acdyearterm/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">取消</button>
										</div>
									</div>
								</div>
								
							</div>
							</form>
							<#else>
								抱歉，没有找到符合条件的数据！<br/>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="button" class="btn green-seagreen button-previous"  onclick="location.href = '${contextPath}/dict/edu/common/acdyearterm/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
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
			
		<jsplugininner>
		    <!-- BEGIN PAGE LEVEL PLUGINS -->
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"></script>
			<!-- END PAGE LEVEL PLUGINS -->
		</jsplugininner>
		
		<jsinner>
			<script>
							$(document).ready(function(){
								
								$(".data-picker").datepicker({
									  format: 'yyyy-mm-dd',
									  weekStart: 1,
									  autoclose: true,
									  todayBtn: 'linked',
									  language: 'zh-CN'
									 });
							 
							});
							
			</script>
		</jsinner>
		
	</body>
</html>
