<#import "/macro/commonMacros.ftl" as commonMacros/>

<html>
	<head>
		<title>${curMenu.title?if_exists}</title>
		<!-- BEGIN THEME STYLES -->
		<link href="${contextPath}/resources/metronic/global/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/resources/metronic/admin/pages/css/inbox.css" rel="stylesheet" type="text/css" />
		<!-- END THEME STYLES -->
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		<@commonMacros.breadcrumbNavigation/>
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		
		<div class="row">
			<div class="col-md-3">
				<div class="portlet box blue-steel">
					<div class="portlet-title">
						<div class="caption">
							院系
						</div>
						<div class="actions">
							<button class="btn table-group-action-submit btn-default" onclick="javascript:openOrCloseAll(this);">全部收起</button>
						</div>
					</div>
					
					<div class="portlet-body">
					    <div style="padding:5px;font-size:14px; margin-bottom:5px;">
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
			
				
			<div class="col-md-9">
				<div class="portlet box blue-steel">
					<div class="portlet-title">
						<div class="caption">院系-专业关联</div>
					</div>
				
					<div class="portlet-body">
						<div class="inbox-content">
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- END PAGE CONTENT-->
		<jsplugininner>
			<!-- BEGIN PAGE LEVEL PLUGINS -->
			<script src="${contextPath}/resources/metronic/global/plugins/jstree/dist/jstree.min.js"></script>
			<script src="${contextPath}/resources/metronic/admin/pages/scripts/ui-tree.js"></script>
			
			<!--模态窗口使用的js-->
			<link href="${contextPath}/resources/metronic/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" media="screen">
			<script src="${contextPath}/resources/metronic/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
			<script src="${contextPath}/resources/metronic/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
			<link href="${contextPath}/resources/css/ajaxmodal.css" rel="stylesheet" media="screen">
			<script src="${contextPath}/resources/js/ajaxmodal.js" type="text/javascript"></script>
			
			<script src="${contextPath}/resources/js/dict_department.js" type="text/javascript"></script>
			<!-- END PAGE LEVEL PLUGINS -->
		</jsplugininner>
		
		<jsinner>
			<!-- BEGIN PAGE LEVEL SCRIPTS -->
			<script type="text/javascript">
				
				$(document).ready(function(){
		            UITree.init();
		             
		            <#if departmentTreeNodeSelectedId??>
		            setDepartmentNodeId(${departmentTreeNodeSelectedId});
		            loadContentInPage("${contextPath}/dict/edu/common/deptmajor/<@commonMacros.scope />/" + $("#departmentTreeNodeSelectedId").val() + "/list");
		            </#if>
		           
		            $('#tree_1').on('select_node.jstree', function(e, data) { 
			            var nodeid = $('#' + data.selected).attr("nodeid");
			            department_node_id = nodeid;
			           	loadContentInPage("${contextPath}/dict/edu/common/deptmajor/<@commonMacros.scope />/" + nodeid + "/list");
		            });
				});//document.ready()	
				
			</script>
		</jsinner>
		
	</body>
</html>
