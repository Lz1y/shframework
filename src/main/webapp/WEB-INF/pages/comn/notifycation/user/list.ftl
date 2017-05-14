<#import "/macro/commonMacros.ftl" as commonMacros />
<html>
	<head>
		<meta charset="utf-8"/>
		<title>通知</title>
		<link href="${contextPath}/resources/metronic/admin/pages/css/todo.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="portlet light">
			<div class="portlet-title">
				<div class="caption">
					<span class="caption-subject font-green bold uppercase">消息 &nbsp;&nbsp;<span style="margin-down:20px;font-size:12px;color:black;"><span style='color:red'>${unreadCount}</span>封未读</span></span>
				</div>
			</div>
			<div class="portlet-body form">
				<form name="listform" class="form-horizontal valid_form" method="post" action="${contextPath}/notification/${type}/list">
					<div class="form-body">
	                    
						<#if page.list?exists>
							<#list page.list as data>
			                    <div class="col-md-12" style="margin-top:5px;background-color: #fafafa;">
									<div class="scroller" style="max-height: 800px;" data-always-visible="0" data-rail-visible="0" data-handle-color="#dae3e7">
										<div class="todo-tasklist">
											<div class="pull-left date" style="margin-left:10px;margin-right:10px;margin-top:6px;font-size:12px;">
												<input type="checkbox" name="cbkfield" value="${data.id}" class="pull-left">
												<#if data.status=0>
													<strong>${sysuser[data.sendUserId?string].username}</strong>
												<#else>
													${sysuser[data.sendUserId?string].username}
												</#if>
												<p style="margin-left:28px;margin-top:12px;">
													${data.createDate?string('yyyy-MM-dd HH:mm:ss')}													
												</p>
											</div>
											<div class="" <#if data.status=0>onclick="read(${data.id})"</#if> style="cursor:pointer;margin-left:200px;">
												<div class="todo-tasklist-item-title">
													<span <#if data.status=0>style="font-weight:bold;"</#if>>${data.title}</span><#if data.status=0></#if>
												</div>
												<div class="todo-tasklist-item-text">
													${data.content}
												</div>
											</div>	
										</div>	
									</div>
								</div>
							</#list>
						</#if>
	                    <div class="col-md-12" style="margin-left:13px;margin-top:10px;">
	                    	<a href="javascript:;" onclick="checked()">全选</a> | <a href="javascript:;" onclick="invert()">反选</a>
							<input type='button' value='标记为已读' onclick='nRead()' />
							<input type='button' value='删除选中的记录' onclick='nDelete()' />
						</div>
						<p>&nbsp;</p>
						<@commonMacros.pagination />
					</div>
				</form>
			</div>
		</div>

		<script>
			var $form = $("form[name=listform]");
			
			function nDelete(){
				if($("[name=cbkfield][type=checkbox]:checked").length==0){
					bootbox.alert("未选中任何记录");
					return;
				} else {
					$form.attr("action", '${contextPath}/notification/2/batchdelete?ntype=${type}');
					$form.submit();
				}
			}
			
			function nRead(){
				if($("[name=cbkfield][type=checkbox]:checked").length==0){
					bootbox.alert("未选中任何记录");
					return;
				} else {
					$form.attr("action", '${contextPath}/notification/1/batchread?ntype=${type}');
					$form.submit();
				}
			}
			
			function checked() {
				var cbklist = document.getElementsByName('cbkfield');
				for (var i = 0; i < cbklist.length; i++) {
					cbklist[i].setAttribute("checked", "checked");
					cbklist[i].parentNode.setAttribute("class", "checked");
				}
			}
			
			function invert() {
				var cbklist = document.getElementsByName('cbkfield');
				for (var i = 0; i < cbklist.length; i++) {
					cbklist[i].setAttribute("checked", "");
					cbklist[i].parentNode.setAttribute("class", "");
				}
			}
			
			var read = function(id) {
				var $form = $("form[name=listform]");
				$form.attr("action", '${contextPath}/notification/'+id+'/read?ntype=${type}');
				$form.submit();
			}
		</script>
	</body>
</html>