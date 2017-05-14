<#--部门下教职工维护页面-->
<#import "/macro/commonMacros.ftl" as commonMacros/>
<html>
	<head>
		<title>${curMenu.title?if_exists}</title>
		<!-- BEGIN THEME STYLES -->
		<link href="${contextPath}/resources/metronic/global/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/wk/table-init.css"/>
		<!-- END THEME STYLES -->
		<style type="text/css">
			.dataTables_empty{
				display:none;
			}
		</style>
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		<@commonMacros.breadcrumbNavigation />
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		
		<div class="row">
			
			<div class="col-md-3 col-sm-3">
				<div class="portlet box blue-steel">
					<div class="portlet-title">
						<div class="caption">
							院系
						</div>
						<div class="actions">
							<button class="btn btn-default table-group-action-submit btn_search" onclick="javascript:openOrCloseAll(this);">全部收起</button>
						</div>
					</div>
					
					<div class="portlet-body">
					    <div style="padding:5px;font-size:14px; margin-bottom:5px;">
						        <input type="checkbox" name="allFlag" value="${allFlag}" <#if allFlag==0>checked="checked"</#if> />只显示本层数据
					    </div>
						<div id="tree_1" class="tree" >
							<ul  >
							<#if departmentTreeNodeSelectedId??>
								<@commonMacros.multiTree treeNode departmentTreeNodeSelectedId "departmentTreeNodeSelectedId"/>
							<#else>
								<@commonMacros.multiTree treeNode/>
							</#if>
							</ul>
						</div>
					</div>
				</div>
			</div>
			
			<form name="listform" action="${contextPath}/edu/tch/employee/<@commonMacros.scope />/all/list" method="post" class="form-horizontal" role="form">
			<input type="hidden" id="departmentId" name="departmentId" value="${departmentId!}"/>
			<input type="hidden" name="allFlag" value="${allFlag}"/>
			<div class="col-md-9 col-sm-9">
			
			
					<div class="portlet box green-seagreen">
						<div class="portlet-title">
							<div class="caption inbox-title">
								
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
							</div>
						</div>
						
						<div class="portlet-body">	
							<div class="row ">
								<!--/span-->
								<div class="col-md-4 col-sm-4">
									<div class="form-group ">
										<label class="col-md-4 control-label">职工号:</label>
										<div class="col-md-8 col-sm-8">
											<input type="text" class="form-control" name="_user.user_no"
												value="<#if page.pageSupport.paramVo.map?exists>${page.pageSupport.paramVo.map['_user.user_no']?if_exists?html}</#if>" />
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-4 col-sm-4">
									<div class="form-group ">
										<label class="col-md-4 col-sm-4 control-label">姓名:</label>
										<div class="col-md-8 col-sm-8">
											<input type="text" class="form-control" name="_user.username.3"
												value="<#if page.pageSupport.paramVo.map?exists>${page.pageSupport.paramVo.map['_user.username.3']?if_exists?html}</#if>" />
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-4 col-sm-4">
									<div class="form-group ">
										<label class="col-md-4 col-sm-4 control-label">性别:</label>
										<div class="col-md-8 col-sm-8">
											<@commonMacros.multiSelect staticlabel.gender "search" "_tch.gender" "${page.pageSupport.paramVo.map['_tch.gender']?if_exists?html}" />
										</div>
									</div>
								</div>
								<!--/span-->
								
							</div>	
							
							<div class="row ">
								<!--/span-->
								<div class="col-md-4 col-sm-4">
									<div class="form-group ">
										<label class="col-md-4 col-sm-4 control-label">行政职务:</label>
										<div class="col-md-8 col-sm-8">
											<input type="text" class="form-control" name="_tch.post_title.3"
												value="<#if page.pageSupport.paramVo.map?exists>${page.pageSupport.paramVo.map['_tch.post_title.3']?if_exists?html}</#if>" />
										</div>
									</div>
								</div>
								<!--/span-->
							
								<!--/span-->
								<div class="col-md-4 col-sm-4">
									<div class="form-group ">
										<label class="col-md-4 col-sm-4 control-label">职务等级:</label>
										<div class="col-md-8 col-sm-8">
											<@commonMacros.multiSelect postlevel "search" "_tch.post_level_id" 
												"${page.pageSupport.paramVo.map['_tch.post_level_id']?if_exists?html}" />
										</div>
									</div>
								</div>
								<!--/span-->
								<!--/span-->
								<div class="col-md-4 col-sm-4">
									<div class="form-group ">
										<label class="col-md-4 col-sm-4 control-label">岗位分类:</label>
										<div class="col-md-8 col-sm-8">
											<@commonMacros.multiSelect posttype "search" "_tch.post_type_id" "${page.pageSupport.paramVo.map['_tch.post_type_id']?if_exists?html}" />
										</div>
									</div>
								</div>
								<!--/span-->
							
							</div>
							
							<div class="row ">
							
								<!--/span-->
								<div class="col-md-4 col-sm-4">
									<div class="form-group ">
										<label class="col-md-4 col-sm-4 control-label">编制类型:</label>
										<div class="col-md-8 col-sm-8">
											<@commonMacros.multiSelect stafftype "search" "_tch.staff_type_id" 
													"${page.pageSupport.paramVo.map['_tch.staff_type_id']?if_exists?html}" />
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-4 col-sm-4">
									<div class="form-group ">
										<label class="col-md-4 col-sm-4 control-label">激活:</label>
										<div class="col-md-8 col-sm-8">
											<@commonMacros.multiSelect staticlabel.isZeroORNot "search" "_tch.status" "${page.pageSupport.paramVo.map['_tch.status']?if_exists?html}" />
										</div>
									</div>
								</div>
								<!--/span-->
							
								<!--/span-->
								<div class="col-md-4 col-sm-4">
									<div class="form-group ">
										<div class="col-md-12">
											<button class="btn green-seagreen table-group-action-submit btn_search" type="submit" >搜索</button>
										</div>
									</div>
								</div>
								<!--/span-->
								
							</div>
							
						</div>
					</div>
					<div class="portlet box blue-steel">
						<div class="portlet-title">
							<div class="caption">
								${(curMenu.title)!}
							</div>
							<div class="actions">
								<div class="btn-group">
									<a class="btn btn-default" href="javascript:;" data-toggle="dropdown"> 选择列 <i class="fa fa-angle-down"></i></a>
									<div id="datatable_ajax_column_toggler" class="dropdown-menu hold-on-click dropdown-checkboxes pull-right">
										<label><input type="checkbox" checked data-column="0">职工号</label>
										<label><input type="checkbox" checked data-column="1">姓名</label>
										<label><input type="checkbox" checked data-column="2">性别</label>
										<label><input type="checkbox" checked data-column="3">部门</label>
										<label><input type="checkbox" checked data-column="4">行政职务</label>
										<label><input type="checkbox" checked data-column="5">职务等级</label>
										<label><input type="checkbox" checked data-column="6">岗位分类</label>
										<label><input type="checkbox" checked data-column="7">编制类型</label>
										<label><input type="checkbox" checked data-column="8">是否任课</label>
										<label><input type="checkbox" checked data-column="9">是否退休</label>
										<label><input type="checkbox" checked data-column="10">激活</label>
									</div>
								</div>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
								<div class="row">
									<div class="col-md-3">
										<a class="btn green-seagreen" href="${contextPath}/edu/tch/employee/<@commonMacros.scope />/0/add${page.pageSupport.paramVo.parm}&departmentId=${departmentId}&allFlag=${allFlag}">新增</a>
				               	 	</div>
				                </div>
							</div>
							
							<table class="table table-striped table-bordered table-hover table_align_center" id="datatable_ajax">
								<thead>
									<tr role="row" class="heading">
										<th>职工号</th>
										<th class="sort_default" name="_user.username" onclick="sortbyth(this)">姓名</th>
										<th>性别</th>
										<th>部门</th>
										<th>行政职务</th>
										<th>职务等级</th>
										<th>岗位分类</th>
										<th>编制类型</th>
										<th>是否任课</th>
										<th>是否退休</th>
										<th>是否有教师证</th>
										<th>激活</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<#if page.list?exists>
										<#list page.list as employee>
											<tr>
												<td class="">
													${(employee.user.userNo)!}
												</td>
												<td class="">
												<a href="${contextPath}/edu/tch/employee/<@commonMacros.scope />/${employee.userId}/read${page.pageSupport.paramVo.parm?if_exists?html}&departmentId=${departmentId}&allFlag=${allFlag}">
												<u><@commonMacros.multiInput showStyle "username" employee.user.username /></u>
												</a>
												</td>
												<td class="">
													<@commonMacros.multiSelect staticlabel.gender showStyle "gender" employee.gender />
												</td>
												<td class="">
													${(employee.department.title)!}
												</td>
												<td class="">
												<@commonMacros.multiInput showStyle "postTitle" employee.postTitle />
												</td>
												<td class="">
												<@commonMacros.multiSelect postlevel showStyle "postLevelId" employee.postLevelId />
												</td>
												<td>
												<@commonMacros.multiSelect posttype showStyle "postTypeId" employee.postTypeId />
												</td>
												<td>
												<@commonMacros.multiSelect stafftype showStyle "staffTypeId" employee.staffTypeId />
												</td>
												<td>
												<@commonMacros.multiSelect staticlabel.isZeroORNot showStyle "teachFlag" employee.teachFlag />
												</td>
												<td>
												<@commonMacros.multiSelect staticlabel.isZeroORNot showStyle "retireFlag" employee.retireFlag />
												</td>
												<td>
												<@commonMacros.multiSelect staticlabel.isZeroORNot showStyle "certFlag" employee.certFlag />
												</td>
												<td>
												<@commonMacros.multiSelect staticlabel.isZeroORNot showStyle "status" employee.status />
												</td>
												<td>
													<@shiro.hasPermission name="${curMenu.rule}:${curRc[curMenu.rule].scope}:${employee.departmentId}:*:edit">
													<a class="btn default btn-xs green-seagreen help-block" href="javascript:;" onclick="updateEmployee('${(employee.userId)!}','${departmentId!}','${allFlag!}');">修改</a>
													</@shiro.hasPermission>
													<@shiro.hasPermission name="${curMenu.rule}:${curRc[curMenu.rule].scope}:${employee.departmentId}:*:dispatch">
													<#if employee.userId!=curUser.id && !adminUserIdList?seq_contains(employee.userId)>
														<a class="btn default btn-xs green-seagreen help-block" href="${contextPath}/edu/tch/employee/<@commonMacros.scope />/${employee.userId}/dispatch${page.pageSupport.paramVo.parm?html}&departmentId=${departmentId}&allFlag=${allFlag}" >角色分发</a>
													</#if>
													</@shiro.hasPermission>
													<@shiro.hasPermission name="${curMenu.rule}:${curRc[curMenu.rule].scope}:${employee.departmentId}:*:delete">
													<a class="btn default btn-xs black help-block" href="javascript:deleteEmployee(this, '${contextPath}', '${employee.userId}','${departmentId!}','${allFlag!}')">删除 </a>
													</@shiro.hasPermission>
												</td>
											</tr>
										</#list>
									</#if>
								</tbody>
							</table>
							<@commonMacros.pagination />
						</div>
					</div>
				
				
				
			</div>
			</form>
		
		</div>
		<!-- END PAGE CONTENT-->
		<jsplugininner>
			<!-- BEGIN PAGE LEVEL PLUGINS -->
			<script src="${contextPath}/resources/metronic/global/plugins/jstree/dist/jstree.min.js"></script>
			<script src="${contextPath}/resources/metronic/admin/pages/scripts/ui-tree.js"></script>
			
			<script type="text/javascript" src="${contextPath}/resources/metronic/admin/pages/scripts/table-managed.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/admin/pages/scripts/table-advanced.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/scripts/metronic.js"></script>
			
			<script src="${contextPath}/resources/js/list_sort.js" type="text/javascript"></script>
			<script src="${contextPath}/resources/js/employee_tree.js" type="text/javascript"></script>
			<!-- END PAGE LEVEL PLUGINS -->
			<script type="text/javascript" src="${contextPath}/resources/wk/table-init.js"></script>
		</jsplugininner>
		
		<jsinner>
			<!-- BEGIN PAGE LEVEL SCRIPTS -->
			<script type="text/javascript">
				
				$(document).ready(function(){
		            UITree.init();
		            TableAdvanced.init();
					      sortInit();
					
		            <#if departmentTreeNodeSelectedId??>
		            setDepartmentNodeId(${departmentTreeNodeSelectedId});
		            var node =  $("#tree_1").jstree().get_selected(true)[0];
		            if (typeof(node) != "undefined"){
		            	$title.html($.trim(node.text));
		            }
		            </#if>
		           
		           $("#datatable_ajax").parent(".table-scrollable").removeClass("table-scrollable");
		           
		           table_init();

				});//document.ready()
				
								
				$('#tree_1').on('select_node.jstree', function(e, data) { 
		            var nodeid = data.node.li_attr.nodeid;
		            setDepartmentNodeId(nodeid);
		            $title.html($.trim(data.node.text));
		            loadDivisionInPage();
	            });
	            
	      function loadDivisionInPage(){
					   var nodeid = getDepartmentNodeId();
					   $("#departmentId").val(nodeid);
	            if (typeof(nodeid)=="undefined" || nodeid==""){
	            	return;
	            }
		          var allFlag = $("input[name=allFlag]").val();
		        	var url = "${contextPath}/edu/tch/employee/<@commonMacros.scope />/all/list";
    				 	var $form = $("<form id='changeStatusForm' action='" + url + "' method='POST'></form>");
    					var $input = $("<input type='hidden'  name='departmentId' value='" + nodeid + "' />");
    					var $input2 = $("<input type='hidden'  name='allFlag' value='" + allFlag + "' />");
    					$form.append($input);
    					$form.append($input2);
    			   	$form.appendTo("body").submit();
			}	
			
			$("input[name=allFlag]").click(function(){
	            	var allFlag = $("input[name=allFlag]").parent(".checked" );
	            	 if(allFlag.length==1){
	            	 	$("input[name=allFlag]").val(1);
	            	 }else{
	            	 	$("input[name=allFlag]").val(0);
	            	 }
	            	loadDivisionInPage();
	            });
	            
	            
			function updateEmployee(id,departmentId,allFlag){
				bootbox.confirm("修改数据，会导致所有使用该数据的业务受到影响，包括历史记录。您确定要修改该数据吗？",function(result){
					if(result){
						window.location.href = "${contextPath}/edu/tch/employee/<@commonMacros.scope />/"+id+"/edit${page.pageSupport.paramVo.parm?js_string}&departmentId="+departmentId+"&allFlag="+allFlag;
					}
				});
			}
			
			var dictDeleteWarnStr = "您确定要删除该数据吗?";
			function deleteEmployee(obj, contextPath, id,departmentId,allFlag){
				bootbox.confirm(dictDeleteWarnStr, function(result){
					if(!result) return;
					
					var $btn = $(obj);
					var url = contextPath + "/edu/tch/employee/<@commonMacros.scope />/" + id + "/delete${page.pageSupport.paramVo.parm?js_string}&departmentId="+departmentId+"&allFlag="+allFlag;
					window.location.href = url;
				});
			}
			
				
			</script>
		</jsinner>
		
	</body>
</html>
