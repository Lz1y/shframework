\<#--资源管理列表-->
<#import "/macro/commonMacros.ftl" as commonMacros/>
<html>
	<head>
		<title>${curMenu.title?if_exists}</title>
		<!-- BEGIN THEME STYLES -->
		<link href="${contextPath}/resources/metronic/global/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
		<#--x-editable-->
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/bootstrap-editable/bootstrap-editable/css/bootstrap-editable.css"/>
		
		<!-- END THEME STYLES -->
		<style type="text/css">
			.dataTables_empty{
				display:none;
			}
		</style>
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		<@commonMacros.breadcrumbNavigation/>
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		
		<div class="row">
			
			<div class="col-md-4 col-sm-4 ">
				<div class="portlet box blue-steel">
					<div class="portlet-title">
						<div class="caption">
							资源
						</div>
						<div class="actions">
							<button class="btn table-group-action-submit btn-sm btn-default" onclick="javascript:openOrCloseAll(this);">全部收起</button>
						</div>
					</div>
					
					<div class="portlet-body">
						<div style="padding:5px;font-size:14px; margin-bottom:5px;">
					       <input type="checkbox" class="all_flag_check_box" value="0" <#if !allFlag?? || allFlag ==0>checked="checked"</#if> />只显示本层数据
					    </div>
						<div id="tree_1" class="tree" >
							<ul  >
							<#if resId??>
								<@commonMacros.multiTree menu resId "resId"/>
							<#else>
								<@commonMacros.multiTree menu />
							</#if>
							</ul>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-md-8 col-sm-8">
			
				<form name="listform" action="${contextPath}/sys/resource/<@commonMacros.scope />/all/list" method="post" class="form-horizontal" role="form">
					<div class="portlet box green-seagreen">
						<div class="portlet-title">
							<div class="caption inbox-title">
								搜索
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
							</div>
						</div>
						
						<div class="portlet-body">	
							
								<input type="hidden" id="resId" name="resId" value="${resId!}"/>
								<input type="hidden" id="allFlag" name="allFlag" value="${allFlag!}"/>
							<div class="row ">
								<!--/span-->
								<div class="col-md-6 col-sm-6">
									<div class="form-group ">
										<label class="col-md-4 col-sm-4 control-label">资源名称:</label>
										<div class="col-md-8 col-sm-8">
											<input type="text" class="form-control" name="_sr.title.3"
												value="<#if page.pageSupport.paramVo.map?exists>${page.pageSupport.paramVo.map['_sr.title.3']?if_exists?html}</#if>" />
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-6 col-sm-6">
									<div class="form-group ">
										<label class="col-md-4 col-sm-4 control-label">命名规则:</label>
										<div class="col-md-8 col-sm-8">
											<input type="text" class="form-control" name="_sr.rule.3"
												value="<#if page.pageSupport.paramVo.map?exists>${page.pageSupport.paramVo.map['_sr.rule.3']?if_exists?html}</#if>" />
										</div>
									</div>
								</div>
								<!--/span-->
								
							</div>	
							
							<div class="row ">
								
								<!--/span-->
								<div class="col-md-6 col-sm-6">
									<div class="form-group ">
										<label class="col-md-4 col-sm-4 control-label">资源路径:</label>
										<div class="col-md-8 col-sm-8">
											<input type="text" class="form-control" name="_sr.url.3"
												value="<#if page.pageSupport.paramVo.map?exists>${page.pageSupport.paramVo.map['_sr.url.3']?if_exists?html}</#if>" />
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
								<div class="col-md-6 col-sm-6">
									<div class="form-group ">
										<label class="col-md-4 col-sm-4 control-label">菜单是否显示:</label>
										<div class="col-md-8 col-sm-8">
											<@commonMacros.multiSelect staticlabel.isZeroORNot "search" "_sr.show_menu" page.pageSupport.paramVo.map['_sr.show_menu'] />
										</div>
									</div>
								</div>
								<!--/span-->
								
							</div>
							
							<div class="row ">
								
								<!--/span-->
								<div class="col-md-12">
									<div class="form-group ">
										<div class="col-md-12">
											<button class="btn green-seagreen table-group-action-submit btn_search" type="submit" >搜索</button>
										</div>
									</div>
								</div>
								<!--/span-->
								
							</div>
							
						</div>
						<!-- portlet-body over -->
					</div>
					<div class="portlet box blue-steel">
						<div class="portlet-title">
							<div class="caption">
								${(curMenu.title)!}
							</div>
							<div class="actions">
								<div class="btn-group">
									<a class="btn default" href="javascript:;" data-toggle="dropdown"> 选择列 <i class="fa fa-angle-down"></i></a>
									<div id="datatable_ajax_column_toggler" class="dropdown-menu hold-on-click dropdown-checkboxes pull-right">
										<label><input type="checkbox" checked data-column="0">资源名称</label>
										<label><input type="checkbox" checked data-column="1">命名规则</label>
										<label><input type="checkbox" checked data-column="2">资源路径</label>
										<label><input type="checkbox" checked data-column="3">排序</label>
										<!--
										<label><input type="checkbox" checked data-column="4">是否有子节点</label>
										<label><input type="checkbox" checked data-column="5">菜单是否显示</label>
										<label><input type="checkbox" checked data-column="6">描述</label>
										-->
									</div>
								</div>
							</div>
						</div>
						<div class="portlet-body">
							<div class="table-toolbar">
								<div class="row">
									<div class="col-md-3">
										<a class="btn green-seagreen" href="${contextPath}/sys/resource/<@commonMacros.scope />/0/add${page.pageSupport.paramVo.parm}&resId=${resId}"> 新增</a>
				               	 	</div>
				                </div>
							</div>
							
							<table class="table table-striped table-bordered table-hover" id="datatable_resource">
								<thead>
									<tr role="row" class="heading th_min_width">
										<th class="sort_default" name="_sr.title" onclick="sortbyth(this)">资源名称</th>
										<th class="sort_default" name="_sr.rule" onclick="sortbyth(this)"> 命名规则</th>
										<th >资源路径</th>
										<th class="sort_default" name="_sr.priority" onclick="sortbyth(this)">排序</th>
										<th style="display:none;">是否有子节点</th>
										<th style="display:none;">菜单是否显示</th>
										<th style="display:none;">描述</th>
										<th class=>操作</th>
									</tr>
								</thead>
								<tbody>
									<#if page.list?exists>
										<#list page.list as res>
											<tr>
												<td class="">
													<@commonMacros.labelRowDetails  res.title />
												</td>
												<td class="">
													<@commonMacros.multiInput showStyle "rule" res.rule />
												</td>
												<td class="ellipsis">
													<@commonMacros.multiInput showStyle "url" res.url />
												</td>
												<td class="">
													<a href="javascript:;" name="priority" data-type="text" data-pk="${res.id}" data-original-title="请输入排序"><@commonMacros.multiInput showStyle "priority" res.priority /></a>
												</td>
												<td style="display:none;">
													<@commonMacros.multiSelect staticlabel.isZeroORNot showStyle "hasChild" res.hasChild />
												</td>
												<td style="display:none;">
													<@commonMacros.multiSelect staticlabel.isZeroORNot showStyle "showMenu" res.showMenu />
												</td>
												<td style="display:none;">
													<@commonMacros.multiInput showStyle "description" res.description />
												</td>
												<td class="td_btn_width_2">
													<a class="btn btn-xs green-seagreen  help-block" href="javascript:;" onclick="updateRes('${res.id}');">修改</a>
													<a class="btn default btn-xs black help-block" href="javascript:deleteRes(this, '${res.id}')">删除 </a>
												</td>
											</tr>
										</#list>
									</#if>
								</tbody>
							</table>
							<@commonMacros.pagination 2 />
						</div>
					</div>
				</form>
				
				
			</div>
			
		
		</div>
		<!-- END PAGE CONTENT-->
		<jsplugininner>
			<!-- BEGIN PAGE LEVEL PLUGINS -->
			<script src="${contextPath}/resources/metronic/global/plugins/jstree/dist/jstree.min.js"></script>
			<!--<script src="${contextPath}/resources/metronic/admin/pages/scripts/ui-tree.js"></script>-->
			
			<script type="text/javascript" src="${contextPath}/resources/metronic/admin/pages/scripts/table-managed.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/admin/pages/scripts/table-advanced.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/scripts/metronic.js"></script>
			
			<#--x-editable-->
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.js"></script>
			
			<script src="${contextPath}/resources/js/list_sort.js" type="text/javascript"></script>
			<script src="${contextPath}/resources/js/employee_tree.js" type="text/javascript"></script>
			
			<!-- END PAGE LEVEL PLUGINS -->
		</jsplugininner>
		
		<jsinner>
			<!-- BEGIN PAGE LEVEL SCRIPTS -->
			<script type="text/javascript">
				
				$(document).ready(function(){
		            //UITree.init();
		             $('#tree_1').jstree({
          				 "core" : {
                			"themes" : {
                    			"responsive": false
                			}            
            			},
            			"types" : {
                			"default" : {
                    			"icon" : "fa fa-folder icon-state-warning icon-lg"
                			},
                			"file" : {
                    			"icon" : "fa fa-file icon-state-warning icon-lg"
                			}
            			},
            			"plugins": ["types"]
        			});//js-tree init
       
		            TableAdvanced.resTable();
					sortInit();
					
		            <#if resId??>
		            setDepartmentNodeId(${resId});
		            var node =  $("#tree_1").jstree().get_selected(true)[0];
		            if (typeof(node) != "undefined"){
		            	var text = node.text;
		            	if (text.indexOf(">") != -1){
		            		text = text.substring(text.indexOf(">") + 1, text.length);
		            	}
		            	$title.html("在<" + $.trim(text) + ">中 搜索");
		            	$("#tree_1").jstree().open_node(node);
		            }
		            
		            </#if>
		           
		           // X-editable 修改排序
		           //参考 form-editable.js
		            //global settings 
        			$.fn.editable.defaults.inputclass = 'form-control input_priority_width';
        			
		           $("a[name='priority']").editable({
            			url: '${contextPath}/sys/resource/<@commonMacros.scope />/all/save',
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
		           
		           //是否显示子节点的flag控制
		           $("input.all_flag_check_box").click(function(){
		           	var $this = $(this);
		           	var $target = $("form[name=listform] input[name=allFlag]");
		           	if($this.is(":checked")){
		           		$target.val($this.val());
		           	} else {
		           		$target.val("1");
		           	}
		           	$("form[name=listform]").submit();
		           })

				});//document.ready()
				
								
				$('#tree_1').on('select_node.jstree', function(e, data) { 
		            var nodeid = data.node.li_attr.nodeid;
		            setDepartmentNodeId(nodeid);
		            var text = data.node.text;
		            	if (text.indexOf(">") != -1){
		            		text = text.substring(text.indexOf(">") + 1, text.length);
		            	}
		            $title.html("在<" + $.trim(text)+">中 搜索");
		            loadDivisionInPage();
	            });
	            
	            function loadDivisionInPage(){
					var nodeid = getDepartmentNodeId();
					$("#resId").val(nodeid);
		            if (typeof(nodeid)=="undefined" || nodeid==""){
		            	return;
		            }
		            
		        	var url = "${contextPath}/sys/resource/<@commonMacros.scope />/all/list";
				 	var $form = $("<form id='changeStatusForm' action='" + url + "' method='POST'></form>");
					var $input = $("<input type='hidden'  name='resId' value='" + nodeid + "' />");
					$form.append($input);
			   		$form.appendTo("body").submit();
			}	
			
			function updateRes(id){
				bootbox.confirm("修改数据，会导致所有使用该数据的业务受到影响，包括历史记录。您确定要修改该数据吗？",function(result){
					if(result){
						window.location.href = "${contextPath}/sys/resource/<@commonMacros.scope />/"+id+"/edit${page.pageSupport.paramVo.parm?js_string}&resId=${resId}";
					}
				});
			}
			
			var dictDeleteWarnStr = "您确定要删除该数据吗?";
			function deleteRes(obj, id){
				bootbox.confirm(dictDeleteWarnStr, function(result){
					if(!result) return;
					
					var $btn = $(obj);
					var url =  "${contextPath}/sys/resource/<@commonMacros.scope />/" + id + "/delete${page.pageSupport.paramVo.parm?js_string}&resId=${resId}";
					window.location.href = url;
				});
			}
				
			</script>
		</jsinner>
		
	</body>
</html>
