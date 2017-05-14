<#import "/macro/commonMacros.ftl" as commonMacros />

<html>
	<head>
		<title>${curMenu.title }</title>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/select2/select2.css"/>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
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
									<div class="col-md-12">
										<@shiro.hasAnyRoles name=" head_sys">
											<@shiro.hasPermission name="${curMenu.rule}:${curRc[curMenu.rule].scope}:0:*:add">
											<a id="sample_editable_1_new" class="btn green-seagreen"
												 href="${contextPath}/sys/role/<@commonMacros.scope />/0/add${page.pageSupport.paramVo.parm?if_exists?html}<#if module?exists>&module_id=${module.id}</#if>">
											新增 </i>
											</a>
											</@shiro.hasPermission>
										</@shiro.hasAnyRoles>
										<#if moduleList?exists>
											<a style="width:125px;" class="btn grey dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
												 <#if !module?exists>全部<#else>${module.title}</#if> <i class="fa fa-angle-down"></i>
											</a>
											<ul class="dropdown-menu" style="left:90px;top:24px;min-width:125px;">
												<li>
													<a href="${contextPath}/sys/role/<@commonMacros.scope />/all/list">全部</a>
												</li>
										    	<#list moduleList as one>
										    		<#if one.showMenu==1>
													<li>
														<a href="${contextPath}/sys/role/<@commonMacros.scope />/all/list?module_id=${one.id}"> ${one.title}</a>
													</li>
													</#if>
										    	</#list>
											</ul>
											</#if>
									</div>
								</div>
							</div>
						<form name="listform" action="${contextPath}/sys/role/<@commonMacros.scope />/all/list" method="post" >
						<table class="table table-bordered" id="role_table">
							<thead>
							<tr class="heading">
								<th>所属资源</th>
								<th>
									 角色名称
								</th>
								<th>
									角色编码
								</th>
								<!--
								<th>
									 角色描述
								</th>
								-->
								<th>
									 角色状态
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
											<td><@commonMacros.labelRowDetails obj.resourceTitle 2/></td>
											<td>
												<@commonMacros.labelRowDetails obj.title 2/>
											</td>
											<td>
												<@commonMacros.labelRowDetails obj.code 2/>
											</td>
											<!--
											<td>
												<@commonMacros.labelRowDetails obj.description 2/>
											</td>
											-->
											<td>
												<@commonMacros.multiSelect staticlabel.status_name showStyle "status" obj.status?string />
											</td>
											<td>
												<a href="${contextPath}/sys/role/<@commonMacros.scope />/${obj.id}/read<#if module?exists>?module_id=${module.id}</#if>" class="btn btn-xs btn-warning help-block">查看</a>
												<#----超级管理员不允许进行授权操作，其拥有权限将被固定---->
												<@shiro.hasAnyRoles name=" head_sys">
												<#if obj.code != 'admin4dev'> 
												<a href="${contextPath}/sys/role/<@commonMacros.scope />/${obj.id}/impower<#if module?exists>?module_id=${module.id}</#if>" class="btn btn-xs blue-steel help-block">授权</a>
												</#if>
												<a href="${contextPath}/sys/role/<@commonMacros.scope />/${obj.id}/dispatch?module_id=${obj.resourceId}<#if module?exists>&redirect_module_id=${module.id}</#if>" class="btn btn-xs blue-steel help-block">再授权</a>
												<#if obj.locked?default(1) == 0 > 
												<a class="btn btn-xs green-seagreen help-block" href="javascript:editRecord(this, ${obj.id});">修改 </a>
												<a class="btn default btn-xs black help-block" href="javascript:deleteRecord(this, ${obj.id});">删除 </a>
												</#if>
												</@shiro.hasAnyRoles>
											</td>
										</tr>
									</#list>
								</#if>
							</tbody>
						</table>
						
						<@commonMacros.pagination 2 />
						</form>
					</div>
				</div>
			</div>
			
		</div>
		
		<jsinner>
			<script type="text/javascript">
				var deleteWarnStr = "数据被删除，无法恢复。<br/>数据一旦被删除，所有使用该数据功能都会受到影响，包括历史记录。<br/>您确定要删除该数据吗？";
				var editWarnStr = "数据一旦被修改，所有使用该数据功能都会受到影响。<br/>您确定要继续修改该数据吗";
				function deleteRecord(obj, id){
					bootbox.confirm(deleteWarnStr, function(result){
						if(!result) return;
						
						var $btn = $(obj);
						var url = "${contextPath}" + "/sys/role/<@commonMacros.scope />/" + id + "/delete${page.pageSupport.paramVo.parm?js_string}"; 
						$btn.button("loading");
						window.location.href = url;
					});
				}
				
				function editRecord(obj, id){
					bootbox.confirm(editWarnStr, function(result){
						if(!result) return;
						
						var $btn = $(obj);
						var url = "${contextPath}" + "/sys/role/<@commonMacros.scope />/" + id + "/edit${page.pageSupport.paramVo.parm?js_string}"; 
						$btn.button("loading");
						window.location.href = url;
					});
				}
				
				$(document).ready(function(){
					var $tempTd;
					var rowNum= 1;
					$("#role_table tbody tr").each(function(i, obj){
						var $td = $(obj).find("td:first");
						if ($tempTd == null || $tempTd.text() != $td.text()){
							$tempTd = $td;
							rowNum= 1;	
						} else {
							rowNum ++;
							$tempTd.attr("rowspan", rowNum);
							$td.hide();
						}
					})
				});
			</script>
		</jsinner>
		
	</body>
</html>
