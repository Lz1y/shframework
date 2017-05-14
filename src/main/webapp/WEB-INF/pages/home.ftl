<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${sysname + " - "}主页</title>
	</head>
	<body>
		
		<h2>欢迎! - ${curUser.username}</h2>
		
		<a id="errorId" data-target="#error2" data-toggle="modal" style="display:none;"></a>
		<script>
			jQuery(document).ready(function() {
				
				<#if error=1>
					bootbox.alert("你没有权限操作此页面。");
				<#elseif error=2>
					bootbox.alert("你输入的密码有误。");
				</#if>
			});
		</script>
	</body>
</html>