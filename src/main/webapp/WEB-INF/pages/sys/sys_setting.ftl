<#import "/spring.ftl" as spring />  
<#import "/macro/commonMacros.ftl" as commonMacros />
<#setting classic_compatible=true>

<html>
	<head>
		<title>${curMenu.title }</title>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		<@commonMacros.breadcrumbNavigation />
		<div class="tab-pane active" id="tab_0">
			<div class="portlet box blue-steel">
				<div class="portlet-title">
					<div class="caption">
						<span class="caption">${curMenu.title }</span>
					</div>
				</div>
				<div class="portlet-body">
						<div class="table-toolbar">
							<div class="row">
									
							</div>
						</div>
					<!-- BEGIN FORM -->
					
					<form class="form-horizontal valid_form" name="clazzForm" method="post" action="${contextPath}/sys/setting/0/0/all/edit">
						<div class="portlet-body">
						    <div class="row ">
					    	<div class="col-md-4">
								<div class="form-group ">
									<label class="col-md-4 control-label">清理缓存：</label>
									<div class="col-md-8">
										<button class="btn btn-primary" type="submit">执行</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<jsplugininner>
		    <!-- BEGIN PAGE LEVEL PLUGINS -->
				<!-- form validate -->
				<script src="${contextPath}/resources/metronic/global/plugins/jquery-validation/js/jquery.validate.js" type="text/javascript"></script>
				<script src="${contextPath}/resources/metronic/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
				<script src="${contextPath}/resources/js/form_validate.js" type="text/javascript"></script>
				<!--缓存管理-->
				<script type="text/javascript" src="${contextPath}/resources/js/cache_management.js"></script>
			<!-- END PAGE LEVEL PLUGINS -->
		</jsplugininner>
		
		<jsinner>
		<script>
						
		</script>
		</jsinner>
		

	</body>
</html>
