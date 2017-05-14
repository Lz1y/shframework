<html>

<head>
<title>Exception</title>
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="${contextPath}/resources/metronic/admin/pages/css/error.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-500-full-page">
<div class="row">
	<div class="col-md-12 page-500">
		<div class=" number">
			 ${httpStatusCode}
		</div>
		<div class=" details">
			<p>
				<a href="${contextPath}/home">
				返回首页 </a>
			</p>
		</div>
	</div>
	
	<div class="col-md-12">
		<h3><b>${rootCause}</b></h3>
	</div>
	
	<div class="col-md-12">
		<#list exceptionTraces as trace>
			<#if trace_index != 0>
			<#if trace?index_of("com.shtd") gte 0  || trace?index_of("com/shtd") gte 0 || trace?starts_with("###")><b></#if>
			${trace}
			<#if trace?index_of("com.shtd") gte 0  || trace?index_of("com/shtd") gte 0 || trace?starts_with("###")></b></#if>
			<br>
			</#if>
		
		</#list>
	</div>
</div>
</body>
<!-- END BODY -->
</html>