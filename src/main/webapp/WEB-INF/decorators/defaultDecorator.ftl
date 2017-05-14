<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8"/>
	<title>${sysname + "教务管理系统" + " - "}<sitemesh:write property='title'/></title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta content="" name="description"/>
	<meta content="" name="author"/>
	<meta http-equiv="Cache-Control" CONTENT="max-age=0">
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<link href="${contextPath}/resources/metronic/global/plugins/select2/select2.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/resources/metronic/global/plugins/google-fonts/fonts-googleapis.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/metronic/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/metronic/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/metronic/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/metronic/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/metronic/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN THEME STYLES -->
	<link href="${contextPath}/resources/metronic/global/css/components.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/metronic/global/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/metronic/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
	<link id="style_color" href="${contextPath}/resources/metronic/admin/layout/css/themes/darkblue.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/metronic/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
	
	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css" />
	
	<script type="text/javascript" src="${contextPath}/resources/js/comet4j-0.1.7-debug.js"/>"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/chat.js?v=0.1.1"/>"></script>
 	
	<script src="${contextPath}/resources/metronic/global/plugins/jquery.min.js" type="text/javascript"></script>
	<link href="${contextPath}/resources/css/sort.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/css/liststylefix.css" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	<link rel="shortcut icon" href="${contextPath}/resources/favicon.ico"/>
	
	<link href="${contextPath}/resources/metronic/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/resources/css/ajaxmodal.css" rel="stylesheet" media="screen">
	<link href="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
	
	
	<sitemesh:write property='head'/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body class="page-header-fixed page-quick-sidebar-over-content " onload="init()">
<!-- BEGIN HEADER -->
<#include "/WEB-INF/includes/header.ftl" />
<!-- END HEADER -->
<div class="clearfix" onload="">
</div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
	<#include "/WEB-INF/includes/sidebar.ftl" />
	<!-- END SIDEBAR -->
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<sitemesh:write property='body'/>

			<div id="crole" class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
				<form id="croleForm" action="${contextPath}/crole" class="form-horizontal" method="post">
					<div class="modal-dialog modal-lg">
						<div class="portlet box green-seagreen margin-bottom-0">
							<div class="portlet-title">
								<div class="caption">切换角色</div>
								<button type="button" class="close margin-top-15" data-dismiss="modal" aria-hidden="true"></button>
							</div>
						
							<div class="portlet-body form">	
							<div class="modal-body">
								<#if curUser?? && resrole?? && resrole[curUser.id?c]??>
							    	<#list resrole[curUser.id?c] as res>
								  		<div class="form-group">
											<label class="col-md-4 control-label">${res.title}：</label>
											<div class="col-md-6">
												<select class="form-control" aria-controls="sample_1" name="rid">
													<#if curRc??>
												    	<#list res.roles as key>
												    		<option value="${key.id}" <#if curRc[res.rule]?? && key.code=curRc[res.rule].rCode>selected</#if>>
												    			${key.title}
												    		</option>
												    	</#list>
											    	</#if>
												</select>
										 	</div>
									  	</div>
							    	</#list>
						    	</#if>
							</div>
							
							<div class="modal-footer">
								<button type="submit" data-dismiss="modal" class="btn green-seagreen" onclick="crole()">确定</button>
								<button type="button" data-dismiss="modal" class="btn default">取消</button>
							</div>
							</div>
						</div>
					</div>
				</form>
			</div>

			<div id="cpwd" class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
				<form id="cpwdForm" class="valid_form form-horizontal" action="${contextPath}/cpwd" method="post">
					<div class="modal-dialog modal-lg">
						<div class="portlet box green-seagreen margin-bottom-0">
							<div class="portlet-title">
								<div class="caption">修改密码</div>
								<button type="button" class="close margin-top-15" data-dismiss="modal" aria-hidden="true"></button>
							</div>
						
							<div class="portlet-body form">	
							<div class="modal-body">
							  		<div class="form-group">
										<label class="col-md-4 control-label">当前密码：</label>
										<div class="col-md-6">
											<input type="password" id="pwd_old" name="pwd_old" class="form-control required" rangelength='6,32' />
									 	</div>
								  	</div>
							  		<div class="form-group">
										<label class="col-md-4 control-label">新密码：</label>
										<div class="col-md-6">
											<input type="password" id="pwd_new" name="pwd_new" class="form-control required" rangelength='6,32' />
									 	</div>
								  	</div>
							  		<div class="form-group">
										<label class="col-md-4 control-label">确认新密码：</label>
										<div class="col-md-6">
											<input type="password" id="pwd_ack" name="pwd_ack" class="form-control required" rangelength='6,32' equalToPwd='[name=pwd_new]' />
									 	</div>
								  	</div>
							</div>
							
							<div class="modal-footer">
								<button type="submit" class="btn green-seagreen">确定</button>
								<button type="button" data-dismiss="modal" class="btn default">取消</button>
							</div>
							</div>
						</div>
					</div>
				</form>
			</div>

		</div>
	</div>
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<#include "/WEB-INF/includes/footer.ftl" />
<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${contextPath}/resources/metronic/global/plugins/respond.min.js"></script>
<script src="${contextPath}/resources/metronic/global/plugins/excanvas.min.js"></script> 
<![endif]-->

<script type="text/javascript" src="${contextPath}/resources/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/jquery-i18n-properties-master/jquery.i18n.properties.js"></script>

<script src="${contextPath}/resources/metronic/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="${contextPath}/resources/metronic/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${contextPath}/resources/metronic/global/plugins/select2/select2.js" type="text/javascript" ></script>
<!-- dataTables,dataTables.fixedColumns-->
<script src="${contextPath}/resources/metronic/global/plugins/datatables/media/js/jquery.dataTables.min.js" type="text/javascript" ></script>
<script  src="${contextPath}/resources/metronic/global/plugins/datatables/extensions/FixedColumns/js/dataTables.fixedColumns.min.js" type="text/javascript"></script>

<script src="${contextPath}/resources/metronic/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript" ></script>
 <!--datepicker-->     
<script src="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript" ></script>
<script src="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js" type="text/javascript" ></script>
      
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<!-- zhangjk
<script src="${contextPath}/resources/metronic/admin/pages/scripts/ui-blockui.js"></script>
<script src="${contextPath}/resources/metronic/global/plugins/jquery.blockui.js" type="text/javascript"></script>
-->
<script src="${contextPath}/resources/metronic/admin/pages/scripts/ui-blockui2.js"></script>

<script src="${contextPath}/resources/metronic/admin/pages/scripts/ui-tree.js"/>"></script>
<script src="${contextPath}/resources/metronic/global/plugins/jstree/dist/jstree.js"/>"></script>
<script src="${contextPath}/resources/metronic/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/admin/pages/scripts/components-pickers.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/scripts/datatable.js" type="text/javascript"></script>

<!-- common_util  通用工具-->
<script src="${contextPath}/resources/js/common_util.js" type="text/javascript"></script>
<!-- Alert & Dialogs -->
<script src="${contextPath}/resources/metronic/global/plugins/bootbox/bootbox.js" type="text/javascript"></script>
<!-- dict management -->
<script src="${contextPath}/resources/js/dict_management.js" type="text/javascript"></script>
<!-- jQuery Validation Plugin -->
<script src="${contextPath}/resources/metronic/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${contextPath}/resources/js/form_validate.js" type="text/javascript"></script>

<script src="${contextPath}/resources/metronic/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="${contextPath}/resources/js/ajaxmodal.js" type="text/javascript"></script>

<script src="${contextPath}/resources/js/list_sort.js" type="text/javascript"></script>
<script src="${contextPath}/resources/js/jquery.md5.js" type="text/javascript"></script>
<sitemesh:write property='jsplugininner' />
<script>
    
	jQuery(document).ready(function() {
		// initiate layout and plugins
		Metronic.init(); // init metronic core components
		Layout.init(); // init current layout
		QuickSidebar.init(); // init quick sidebar
		Demo.init(); // init demo features
        ComponentsPickers.init();
		bootbox.setDefaults({locale:"zh_CN"});
		<#if showStyle?? && showStyle="list">
			sortInit();
		</#if>
	});
	
	
	var crole = function() {
		bootbox.confirm("是否切换角色？", function(result){
			if(!result) return;
			else $("#croleForm").submit();
		});
	}
	
</script>
<sitemesh:write property='jsinner' />
</body>
<!-- END BODY -->
</html>