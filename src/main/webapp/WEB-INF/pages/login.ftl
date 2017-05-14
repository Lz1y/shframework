<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.1
Version: 3.3.0
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>${sysname + "教务管理系统" + " - "}登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${contextPath}/resources/metronic/global/plugins/google-fonts/fonts-googleapis.css" rel="stylesheet" type="text/css"/>
<link href="${contextPath}/resources/metronic/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${contextPath}/resources/metronic/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="${contextPath}/resources/metronic/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${contextPath}/resources/metronic/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="${contextPath}/resources/metronic/admin/pages/css/login.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME STYLES -->
<link href="${contextPath}/resources/metronic/global/css/components.css" rel="stylesheet" type="text/css"/>
<link href="${contextPath}/resources/metronic/global/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="${contextPath}/resources/metronic/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link id="style_color" href="${contextPath}/resources/metronic/admin/layout/css/themes/darkblue.css" rel="stylesheet" type="text/css"/>
<link href="${contextPath}/resources/metronic/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="${contextPath}/resources/favicon.ico"/>
</head>
<!-- BEGIN BODY -->
<!-- DO# Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DO# Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DO# Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DO# Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DO# Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DO# Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DO# Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DO# Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DO# Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body class="login">
<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
<div class="menu-toggler sidebar-toggler">
</div>
<!-- END SIDEBAR TOGGLER BUTTON -->
<!-- BEGIN LOGO -->
<div class="logo">
	
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
	<!-- BEGIN LOGIN FORM -->
	<form class="login-form" action="${contextPath}/authenticate" commandName="user" method="post" accept-charset='UTF-8'>
		<h3 class="form-title">
			${sysname}<br/>
			教务管理系统<br/>
		</h3>
	
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label visible-ie8 visible-ie9">用户名</label>
			<input class="form-control placeholder-no-fix" type="text" autocomplete="off" name="username" placeholder="用户名" path="username" />
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">密码</label>
			<input id="password" class="form-control placeholder-no-fix" type="password" autocomplete="off" name="password" placeholder="密码" />
		</div>
		<div class="form-actions">
			<button type="submit" class="btn btn-success uppercase center-block">登&nbsp;录&nbsp;<i class="fa fa-arrow-circle-o-right"></i></button>
		</div>
		<#--
		<div class="login-options">
			<h4>Or login with</h4>
			<ul class="social-icons">
				<li>
					<a class="social-icon-color facebook" data-original-title="facebook" href="javascript:;"></a>
				</li>
				<li>
					<a class="social-icon-color twitter" data-original-title="Twitter" href="javascript:;"></a>
				</li>
				<li>
					<a class="social-icon-color googleplus" data-original-title="Goole Plus" href="javascript:;"></a>
				</li>
				<li>
					<a class="social-icon-color linkedin" data-original-title="Linkedin" href="javascript:;"></a>
				</li>
			</ul>
		</div>
		<div class="create-account">
			<p>
				<a href="javascript:;" id="register-btn" class="uppercase">Create an account</a>
			</p>
		</div>
		-->
	</form>
	<!-- END LOGIN FORM -->
	
</div>
<!-- END LOGIN -->
<!-- BEGIN COPYRIGHT -->
<div class="copyright">
	 © 2017 ${sysname}
</div>
<!-- END COPYRIGHT -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${contextPath}/resources/metronic/global/plugins/respond.min.js"/>"></script>
<script src="${contextPath}/resources/metronic/global/plugins/excanvas.min.js"/>"></script> 
<![endif]-->
<script src="${contextPath}/resources/metronic/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${contextPath}/resources/metronic/admin/pages/scripts/login.js" type="text/javascript"></script>

<script src="${contextPath}/resources/js/jquery.md5.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
jQuery(document).ready(function() {     
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
  	Login.init();
	Demo.init(); // init demo features
});

</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>