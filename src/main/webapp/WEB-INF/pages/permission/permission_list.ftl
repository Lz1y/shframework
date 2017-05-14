<#import "/macro/commonMacros.ftl" as commonMacros />

<html>
	<head>
		<title>${curMenu.title }</title>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/select2/select2.css"/>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
		<#--x-editable-->
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/bootstrap-editable/bootstrap-editable/css/bootstrap-editable.css"/>
	</head>
	<body>
		<@commonMacros.breadcrumbNavigation />
		<div class="row">
			<div class="col-md-12">
				<div class="portlet box blue-steel">
					<div class="portlet-title">
						<div class="caption">
							${curMenu.title }
						</div>
					</div>
					<div class="portlet-body">
						<div class="table-toolbar">
								<div class="row">
									<div class="col-md-6">
										<div class="btn-group">
											<@shiro.hasAnyRoles name=" head_sys">
											<@shiro.hasPermission name="${curMenu.rule}:${curRc[curMenu.rule].scope}:0:*:add">
											<a id="sample_editable_1_new" class="btn green-seagreen"
												 href="${contextPath}/sys/permission/<@commonMacros.scope />/0/add${page.pageSupport.paramVo.parm?if_exists?html}">
											新增 
											</a>
											</@shiro.hasPermission>
											</@shiro.hasAnyRoles>
										</div>
									</div>
								</div>
							</div>
							
						<table class="table table-striped table-bordered table-hover" id="sample_1">
							<thead >
							<tr class="heading">
								<th>
									 ID
								</th>
								<th>
									权限名称
								</th>
								<th>
									 权限代码
								</th>
								<th>
									 创建时间
								</th>
								<th>
									 权限状态
								</th>
								<th>
									 排序
								</th>
								<th>
									操作
								</th>
							</tr>
							</thead>
							<tbody>
								<#if page.list?exists>
									<#list page.list as obj>
										<tr>
											<td>
												<@commonMacros.labelRowDetails obj.id 2/>
											</td>
											<td>
												<@commonMacros.labelRowDetails obj.title 2/>
											</td>
											<td>
												<@commonMacros.labelRowDetails obj.code 2/>
											</td>
											<td>
												<@commonMacros.multiDate showStyle "createDate" obj.createDate />
											</td>
											<td>
												<@commonMacros.multiSelect staticlabel.status_name showStyle "status" obj.status?string />
											</td>
											<td>
												<a href="javascript:;" name="priority" data-type="text" data-pk="${obj.id}" data-original-title="请输入排序"><@commonMacros.multiInput showStyle "priority" obj.priority /></a>
											</td>
											<td class="td_btn_width_2">
											<#if obj.locked?default(1) == 0 >
											<@shiro.hasAnyRoles name=" head_sys">
											<a class="btn btn-xs green-seagreen" href="javascript:editRecord(this, ${obj.id});">
												修改</a>
											<a class="btn btn-xs default black" href="javascript:deleteRecord(this, ${obj.id});">
												删除 </a>
											</@shiro.hasAnyRoles>
											</#if>
									    </td>
										</tr>
									</#list>
								</#if>
							</tbody>
						</table>
						<form name="listform" action="${contextPath}/sys/permission/<@commonMacros.scope />/all/list" method="post" >
						<@commonMacros.pagination  2/>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<jsplugininner>
			<#--x-editable-->
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.js"></script>
		</jsplugininner>
		
		<jsinner>
			<script type="text/javascript">
				$(document).ready(function(){
					 // X-editable 修改排序
		           //参考 form-editable.js
		            //global settings 
        			$.fn.editable.defaults.inputclass = 'form-control';
        			
		           $("a[name='priority']").editable({
            			url: '${contextPath}/sys/permission/<@commonMacros.scope />/all/save',
            			type: 'text',
            			validate: function (value) {
            				var int = /^-?[1-9]\d*$/;　　 //匹配整数
               				 if (!int.test(value)) return '请填写整数';
            			},
            			success:function(result){
            				if (result == 'succ'){
            					$("form[name=listform]").submit();
            				}
            			}
      				  });
      				  
				})
				
				var deleteWarnStr = "数据被删除，无法恢复。<br/>数据一旦被删除，所有使用该数据功能都会受到影响，包括历史记录。<br/>您确定要删除该数据吗？";
				var editWarnStr = "数据一旦被修改，所有使用该数据功能都会受到影响，包括历史记录。<br/>您确定要继续修改该数据吗";
				function deleteRecord(obj, id){
					bootbox.confirm(deleteWarnStr, function(result){
						if(!result) return;
						
						var $btn = $(obj);
						var url = "${contextPath}" + "/sys/permission/<@commonMacros.scope />/" + id + "/delete${page.pageSupport.paramVo.parm?js_string}"; 
						$btn.button("loading");
						window.location.href = url;
					});
				}
				
				function editRecord(obj, id){
					bootbox.confirm(editWarnStr, function(result){
						if(!result) return;
						
						var $btn = $(obj);
						var url = "${contextPath}" + "/sys/permission/<@commonMacros.scope />/" + id + "/edit${page.pageSupport.paramVo.parm?js_string}"; 
						$btn.button("loading");
						window.location.href = url;
					});
				}
				
			</script>
		</jsinner>
		
	</body>
</html>
