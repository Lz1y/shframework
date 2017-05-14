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
					
					<form class="form-horizontal valid_form" name="clazzForm" method="post" action="">
						<div class="portlet-body">
						    <div class="row ">
				    		<!--/span-->
				    		<div class="col-md-6">
								<div class="form-group ">
									<label class="col-md-6 control-label">异步系统：</label>
									<div class="col-md-6">
										<a class="btn btn-primary" href="${contextPath}/sys/monitor/0/0/async/test">监测</a>
									</div>
								</div>
							</div>
							<!--/span-->
							</div>
							
							<div class="row ">
				    		<!--/span-->
				    		<div class="col-md-6">
								<div class="form-group ">
									<label class="col-md-6 control-label">前台竞争选课学生列表：</label>
									<div class="col-md-6">
										<button class="btn btn-warning" type="button" onclick="show_stms_elctstudent();">查看</button>
									</div>
								</div>
							</div>
							<!--/span-->
							</div>
						
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<jsplugininner>
		    <!-- BEGIN PAGE LEVEL PLUGINS -->
				<!-- ajaxModal Begin -->
				<link href="${contextPath}/resources/metronic/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" media="screen">
				<script src="${contextPath}/resources/metronic/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
				<script src="${contextPath}/resources/metronic/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
				<link href="${contextPath}/resources/css/ajaxmodal.css" rel="stylesheet" media="screen">
				<script src="${contextPath}/resources/js/ajaxmodal.js"　type="text/javascript"></script>
				<!-- ajaxModal End -->
			<!-- END PAGE LEVEL PLUGINS -->
		</jsplugininner>
		
		<jsinner>
		<script>
			function show_stms_elctstudent(){
				var url = "${contextPath}/sys/monitor/<@commonMacros.scope />/stms/elctstudent";
				$.ajaxModal(url);
			}	
		</script>
		</jsinner>
		

	</body>
</html>
