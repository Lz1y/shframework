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
					<div class="portlet box blue-steel">
						<div class="portlet-title">
							<div class="caption">
								${curMenu.title?if_exists}
							</div>
						</div>
						<div class="portlet-body form">
							<div class="form-horizontal form-body">
							    <#if acdyearterm?exists>
							    	<div class="form-group">
										<label class="col-md-3 control-label">学年</label>
										<div class="col-md-9">
											<@commonMacros.multiSelect academicyear showStyle "academicYearId" acdyearterm.academicYearId/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">学期</label>
										<div class="col-md-9">
											<@commonMacros.multiSelect term showStyle "termId" acdyearterm.termId/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">学年学期编码</label>
										<div class="col-md-9">
											<@commonMacros.multiInput showStyle "code" acdyearterm.code/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">学期周数</label>
										<div class="col-md-9">
											<@commonMacros.multiInput showStyle "termWeeks" acdyearterm.termWeeks/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">教学周扣除数</label>
										<div class="col-md-9">
											<@commonMacros.multiInput showStyle "deductWeeks" acdyearterm.deductWeeks/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">开始日期</label>
										<div class="col-md-9">
											 <@commonMacros.multiDate showStyle "startDate" acdyearterm.startDate/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">结束日期</label>
										<div class="col-md-9">
											 <@commonMacros.multiDate showStyle "endDate" acdyearterm.endDate/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">备注</label>
										<div class="col-md-9">
											 <@commonMacros.multiInput showStyle "notes" acdyearterm.notes "" 'rangelength="0,10240"' />
										</div>
									</div>
							    
								<#else>
								抱歉，没有找到符合条件的数据！
								</#if>
							</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="button" class="btn green-seagreen button-previous"  onclick="location.href = '${contextPath}/dict/edu/common/acdyearterm/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
										</div>
									</div>
								</div>
							
							<!-- END FORM-->
							<script type="text/javascript">
							</script>
						</div>
					</div>
				</div>
			</div>
		<!-- END PAGE CONTENT-->
	</body>
</html>
