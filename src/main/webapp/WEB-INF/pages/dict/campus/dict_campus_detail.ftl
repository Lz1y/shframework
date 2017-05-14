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
							    <#if campus?exists>
							    	<div class="form-group">
										<label class="col-md-3 control-label">校区编码</label>
										<div class="col-md-9"><p class="form-control-static">${campus.code?if_exists}</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">校区名称</label>
										<div class="col-md-9"><p class="form-control-static">${campus.title?if_exists}
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">校区地址</label>
										<div class="col-md-9"><p class="form-control-static">${campus.address?if_exists}</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">联系电话</label>
										<div class="col-md-9"><p class="form-control-static">${campus.tel?if_exists}
										</div>
									</div>
								
								<#else>
								抱歉，没有找到符合条件的数据！
							</#if>	
							</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="button" class="btn green-seagreen button-previous"  onclick="location.href = '${contextPath}/dict/edu/common/campus/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
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
