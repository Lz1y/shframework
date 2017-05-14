<#import "/macro/commonMacros.ftl" as commonMacros />
<html>
	<head>
		<meta charset="utf-8"/>
		<title>${curMenu.title }</title>
	</head>
	<body>
		<@commonMacros.breadcrumbNavigation />
		<div class="row">
			<div class="col-md-12">
				<form name="listform" action="${contextPath}/${curMenu.pUrl}/all/tpllist?module=${curMenu.rule}&pUrl=${curMenu.pUrl}" method="post" onsubmit="" class="form-horizontal" role="form">
					<div class="portlet box green-seagreen">
						<div class="portlet-title">
							<div class="caption">
								搜索
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
							</div>	
						</div>
						<div class="portlet-body">
							<div class="row">		
								<div class="col-md-4">
									<div class="form-group ">
										<label class="col-md-4 control-label">模板格式：</label>
										<div class="col-md-8">
									    	<@commonMacros.multiSelect staticlabel.templateFormat "search" "_comn.file_format" page.pageSupport.paramVo.map['_comn.file_format'] />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group ">
										<label class="col-md-4 control-label">模板类型：</label>
										<div class="col-md-8">
											<@commonMacros.multiSelect staticlabel.templateType "search" "_comn.type" page.pageSupport.paramVo.map['_comn.type'] />
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<div class="col-md-12">
											<button class="btn green-seagreen table-group-action-submit btn_search" type="submit" >搜索</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="portlet box blue-steel">
						<div class="portlet-title">
							<div class="caption">
								<span>${curMenu.title }</span>
							</div>
						</div>
						
						<div class="portlet-body">
							<div class="table-toolbar">
								<div class="row">
									<div class="col-md-12">
										<#if (purviewMap[curMenu.rule])??>
											<#list purviewMap[curMenu.rule] as purview>
												<#if purview == "export_excel">
													<a class="btn green-seagreen" href="${contextPath}/${curMenu.pUrl}/0/tpladd?file_format=0&type=1&module=${curMenu.rule}&pUrl=${curMenu.pUrl}">新增Excel格式导出模板</a>
												<#elseif purview == "export_dbf">
													<a class="btn green-seagreen" href="${contextPath}/${curMenu.pUrl}/0/tpladd?file_format=1&type=1&module=${curMenu.rule}&pUrl=${curMenu.pUrl}">新增DBF格式导出模板</a>
												<#elseif purview == "import_excel">
													<a class="btn green-seagreen" href="${contextPath}/${curMenu.pUrl}/0/tplimport?file_format=0&type=0&rootfield_option=-1&module=${curMenu.rule}&pUrl=${curMenu.pUrl}">新增Excel格式导入模板</a>
												</#if>
											</#list>
										</#if>
										
									</div>
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover" id="datatable_ajax">
									<thead>
									<tr role="row" class="heading th_min_width">
										<th style="width:15%;" class="sort_default" name="_comn.id" onclick="sortbyth(this)">模板编号</th>
										<th style="width:15%;" class="sort_default" name="_comn.title" onclick="sortbyth(this)">模板名称</th>
										<th style="width:15%;" class="sort_default" name="_comn.file_format" onclick="sortbyth(this)">模板格式</th>
										<th style="width:15%;" class="sort_default" name="_comn.type" onclick="sortbyth(this)">模板类型</th>
										<th class="td_btn_width_4">操作</th>
									</tr>
								</thead>
								<tbody>
									<#if page.list?exists>
										<#list page.list as template>
											<tr class="datarow_${template.id}">
												<td>
													${template.id}
												</td>
												<td>
													<@commonMacros.multiInput "list" "title" template.title />
												</td>
												<td>
													<@commonMacros.multiSelect staticlabel.templateFormat "list"   "fileFormat" template.fileFormat/>
												</td>
												<td>
												<@commonMacros.multiSelect staticlabel.templateType "list"  "type" template.type/>
												</td>
												<td class="td_btn_width_2">
													<a class="btn btn-xs btn-warning" href="${contextPath}/${curMenu.pUrl}/${template.id}/tplread?module=${curMenu.rule}&pUrl=${curMenu.pUrl}">查看</a>
												<#if template.type==1>
													<a class="btn btn-xs green-seagreen" href="${contextPath}/${curMenu.pUrl}/${template.id}/tpledit?module=${curMenu.rule}&pUrl=${curMenu.pUrl}">修改</a>
												</#if>
													<a class="btn btn-xs bg-blue-steel" href="${contextPath}/${curMenu.pUrl}/${template.id}/tpldownload?module=${curMenu.rule}&pUrl=${curMenu.pUrl}">下载</a>
													<a class="btn default btn-xs black" href="javascript:deleteTemplate(this, '${template.id}')">删除 </a>
												</td>
											</tr>
										</#list>
									</#if>
								</tbody>
							</table>
							<@commonMacros.pagination />
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<!-- END PAGE CONTENT-->
		<jsplugininner>
			<!-- BEGIN PAGE LEVEL PLUGINS -->
			<script src="${contextPath}/resources/js/list_sort.js" type="text/javascript"></script>
			<!-- END PAGE LEVEL PLUGINS -->
		</jsplugininner>
		
		<jsinner>	
			<script type="text/javascript">
				$(document).ready(function(){
					sortInit();
				});
				var dictDeleteWarnStr = "数据被删除，无法恢复。<br/>数据一旦被删除，所有使用该数据功能都会受到影响，包括历史记录。<br/>您确定要删除该数据吗？";
				function deleteTemplate(obj, id){
					bootbox.confirm(dictDeleteWarnStr, function(result){
						if(!result) return;
						var $btn = $(obj);
						var url = "${contextPath}/${curMenu.pUrl}/" + id + "/tpldelete${page.pageSupport.paramVo.parm?js_string}&module=${curMenu.rule}&pUrl=${curMenu.pUrl}";
						$btn.button("loading");
						window.location.href = url;
					});
				}

			</script>
		</jsinner>
		
	</body>
</html>
