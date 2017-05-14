<#import "/macro/commonMacros.ftl" as commonMacros />
<html>
	<head>
		<meta charset="utf-8"/>
		<title>消息通知</title>
		<link href="${contextPath}/resources/metronic/admin/pages/css/todo.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="portlet light">
			<div class="portlet-title">
				<div class="caption">
					<span class="caption-subject font-red-sunglo bold uppercase">通知 &nbsp;&nbsp;<span style="margin-down:20px;font-size:12px;color:black;"><span style='color:red'>${unreadCount}</span>封未读</span></span>
				</div>
			</div>
			<div class="portlet-body">
				<form name="listform" class="form-horizontal valid_form" method="post" action="${contextPath}/notification/${type}/list">
					<ul class="feeds">
						<#if page.list?exists>
							<#list page.list as data>
								<li>
									<div class="col1 pull-left" style="width: 90%;">
										<div class="cont">
											<div class="cont-col1">
												<div class="label label-sm label-<#if data.status=0>danger<#else>default</#if>">
													<i class="fa fa-bell-o"></i>
												</div>
											</div>
											<div class="cont-col2">
												<div class="desc" style="<#if data.status=0>font-weight:bold;</#if>cursor:pointer;">
													<span <#if data.status=0>onclick="read(${data.id})"</#if>>
														${data.content}
													</span>
												</div>
											</div>
										</div>
									</div>
									<div class="col2 pull-right" style="width: 10%;">
										<div class="date">
											<span>${data.createDate?string('yyyy-MM-dd HH:mm:ss')}</span>
										</div>
									</div>
								</li>
							</#list>
						</#if>
					</ul>
					<p>&nbsp;</p>
					<@commonMacros.pagination />
				</form>
			</div>
		</div>
		<script>
			var read = function(id) {
				var $form = $("form[name=listform]");
				$form.attr("action", '${contextPath}/notification/'+id+'/read?ntype=${type}');
				$form.submit();
			}
		</script>
	</body>
</html>