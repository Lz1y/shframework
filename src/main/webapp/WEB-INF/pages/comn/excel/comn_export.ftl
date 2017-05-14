<#import "/macro/commonMacros.ftl" as commonMacros />
<!DOCTYPE html>

<html>
	<head>
		<title>${(curMenu.title )!}</title>
		<meta charset="utf-8"/>
		<link type="text/css" rel="stylesheet" href="${contextPath}/resources/metronic/global/plugins/dropzone/css/dropzone.css"/>
	</head>
	
	<body class="page-header-fixed page-quick-sidebar-over-content">
		<@commonMacros.breadcrumbNavigation />
		<div class="row">
			<div class="col-md-12">
				<div class="portlet box green-seagreen" id="form_wizard_1">
					<div class="portlet-title">
						<div class="caption">
							${(curMenu.title)!}
						</div>
						<div class="tools hidden-xs">
							<a href="" class="collapse"> </a>
						</div>
					</div>
					<div class="portlet-body form">
						<form action="#" class="form-horizontal" id="submit_form" method="POST">
							<div class="form-wizard">
								<div class="form-body">
									<ul class="nav nav-pills nav-justified steps">
										<li id="tab1_li" class="<#if (currPage ??) && (currPage == 1)>active<#else>done</#if>">
											<a href="#tab1" data-toggle="tab" class="step">
												<span class="number"> 1 </span>
												<span class="desc"> <i class="fa fa-check"></i> 导出格式 </span>
											</a>
										</li>
										<li id="tab2_li" class="<#if (currPage ??) && (currPage == 2)>active<#elseif (currPage == 3)>done</#if>">
											<a href="#tab2" data-toggle="tab" class="step ">
												<span class="number"> 2 </span>
												<span class="desc"> <i class="fa fa-check"></i> 字段设定 </span>
											</a>
										</li>
										<li id="tab3_li" class="<#if (currPage ??) && (currPage == 3)>active</#if>">
											<a href="#tab3" data-toggle="tab" class="step ">
												<span class="number"> 3 </span>
												<span class="desc"> <i class="fa fa-check"></i> 完成 </span>
											</a>
										</li>
									</ul>
									<div id="bar" class="progress progress-striped" role="progressbar">
										<div class="progress-bar progress-bar-success" style="width: 33.3%;" id="progress_bar"></div>
									</div>
									<div class="tab-content">
										
										<div class="tab-pane <#if (currPage ??) && (currPage == 1)>active</#if>" id="tab1">
											<div class="row ">
												<!--/span-->
												<div class="col-md-4">
													<div class="form-group ">
														<label class="col-md-12 control-label ">请选择导出的文件格式：</label>
													</div>
												</div>
												<!--/span-->
											</div>
											<#if (purviewMap[curMenu.rule])?? >
												<#list purviewMap[curMenu.rule] as purview>
													<#if purview == "export_excel">
													<div class="row ">
														<!--/span-->
														<div class="col-md-5">
															<div class="form-group pull-right">
															<div class="col-md-12">
																<div class="radio-list">
																	<label class="radio-inline">
																		<span class="checked">
																			<input type="radio" name="file_format" value="excel" onclick="showSelectFileFormatDiv();"
																				<#if (file_format ??)><#if (file_format == "excel")>checked</#if><#else>checked</#if>/>
																		</span>Excel文件
																	</label>
																</div>
															</div>
														</div>
														</div>
														<!--/span-->
													</div>
													<#elseif purview == "export_dbf">
													<div class="row ">
														<!--/span-->
														<div class="col-md-5">
															<div class="form-group pull-right">
															<div class="col-md-12">
																<div class="radio-list">
																	<label class="radio-inline">
																		<span class="">
																			<input type="radio" name="file_format" value="dbf" onclick="showSelectFileFormatDiv();"
																				<#if (file_format ??) && (file_format == "dbf")>checked</#if>/>
																			</span>DBF文件&nbsp;&nbsp;
																	</label>
																</div>
															</div>
														</div>
													</div>
													<!--/span-->
													</div>
													</#if>
												</#list>
											</#if>
										</div>
										
										<div class="tab-pane <#if (currPage ??) && (currPage == 2)>active</#if>" id="tab2">
										
												
											<div id="tab2-excel" style="display:none">
												
											<div class="row ">
												<!--/span-->
												<div class="col-md-9">
													<div class="form-group ">
														<label class="col-md-4 control-label">选择模板：</label>
														<div class="col-md-8">
															<select class="form-control input-inline" aria-controls="sample_1" 
																name="excelTemplateId" onchange="changeTemplateDetail(this.value);">
													    		<#if templateList ??>
													    		<#list templateList as item>
														    		<option value="${(item.id)!}" <#if (excelTemplateId ??) && (item.id ??) && (excelTemplateId == item.id)>selected</#if>>
														    			${(item.title)!}
														    		</option>
													    		</#list>
													    		</#if>
													    	</select>	
														</div>
													</div>
												</div>
												<!--/span-->
												</div>
												
												<div class="row ">
												<!--/span-->
												<div class="col-md-9">
													<div class="form-group ">
														<label class="col-md-4 control-label">导出字段：</label>
														<div class="col-md-8">
															<table class="table table-striped table-bordered table-hover" id="">
															<thead>
																<tr role="row" class="heading">
																	<th>序号</th>
																	<th>导出字段</th>
																</tr>
															</thead>
															<tbody>
																<#if templateDetailList ??>
													    		<#list templateDetailList as item>
																<tr>
																	<td>
																		${(item_index+1)!}
																	</td>
																	<td>
																		${(item.colFileName)!}
																	</td>
																</tr>
																</#list>
													    		</#if>
															</tbody>
															</table>
														</div>
													</div>
												</div>
												<!--/span-->
												</div>
											</div>
										
											<div id="tab2-dbf" >
												<div class="row ">
												<!--/span-->
												<div class="col-md-9">
													<div class="form-group ">
														<label class="col-md-4 control-label">选择模板：</label>
														<div class="col-md-8">
															<select class="form-control input-inline" aria-controls="sample_1" 
																name="dbfTemplateId" onchange="changeTemplateDetail(this.value);">
													    		<#if templateListDBF ??>
													    		<#list templateListDBF as item>
														    		<option value="${(item.id)!}" <#if (dbfTemplateId ??) && (item.id ??) && (dbfTemplateId == item.id)>selected</#if>>
														    			${(item.title)!}
														    		</option>
													    		</#list>
													    		</#if>
													    	</select>	
														</div>
													</div>
												</div>
												<!--/span-->
												</div>
												
												<div class="row ">
												<!--/span-->
												<div class="col-md-9">
													<div class="form-group ">
														<label class="col-md-4 control-label">导出字段：</label>
														<div class="col-md-8">
															<table class="table table-striped table-bordered table-hover" id="">
															<thead>
																<tr role="row" class="heading">
																	<th>序号</th>
																	<th>导出字段</th>
																	<th>dbf字段名</th>
																</tr>
															</thead>
															<tbody>
																<#if templateDetailListDBF ??>
													    		<#list templateDetailListDBF as item>
																<tr>
																	<td>
																		${(item_index+1)!}
																	</td>
																	<td>
																	<#--
																		${(item.tableDbAlias)!}--${(item.colDbName)!}
																	-->	
																		${(item.colDbName)!}
																	</td>
																	<td>
																		${(item.colFileName)!}
																	</td>
																</tr>
																</#list>
													    		</#if>
															</tbody>
															</table>
														</div>
													</div>
												</div>
												<!--/span-->
												</div>
											</div>
										
											
										</div>
										
										<div class="tab-pane <#if (currPage ??) && (currPage == 3)>active</#if>" id="tab3">
											<div class="row ">
												<!--/span-->
												<div class="col-md-1">
													<div class="form-group ">
														<div class="col-md-12">
														</div>
													</div>
												</div>
												<!--/span-->
												<!--/span-->
												<div class="col-md-11">
													<div class="form-group ">
														<div class="col-md-12">
  														<#if rowSize?exists>
  															成功导出 ${(rowSize)!}  条数据
  														</#if>
														</div>
													</div>
												</div>
												<!--/span-->
											</div>
											<div class="row ">
												<!--/span-->
												<div class="col-md-1">
													<div class="form-group ">
														<div class="col-md-12">
														</div>
													</div>
												</div>
												<!--/span-->
												<!--/span-->
												<div class="col-md-11">
													<div class="form-group ">
														<div class="col-md-12">
															<font color="blue" size="3">
															<#--	
																<a href="javascript:;" id="download_file" onclick="javascript:download_file();">下载导出文件</a>
															-->	
															</font>
														</div>
													</div>
												</div>
												<!--/span-->
											</div>
											
										</div>
									</div>
								</div>
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<a href="javascript:;" class="btn green-seagreen button-previous"><i class="m-icon-swapleft m-icon-black"></i>上一步</a>
											<a href="javascript:;" class="btn green-seagreen button-next">下一步<i class="m-icon-swapright m-icon-white"></i></a>
											<a href="${contextPath}/${curMenu.pUrl}/all/list${pageSupport.paramVo.parm?if_exists?html}" 
												class="btn green-seagreen button-submit">完成<i class="m-icon-swapright m-icon-white"></i></a>
											
											<input type="hidden" id="cbkfield" name="cbkfield" value="${cbkfield!}"/>
											<!--excel 用 此处拆分，简化后台的业务处理-->
											<input type="hidden" id="excelTemplateId" name="excelTemplateId" value="${excelTemplateId!}"/>	
											<!--dbf 用 -->
											<input type="hidden" id="dbfTemplateId" name="dbfTemplateId" value="${dbfTemplateId!}"/>	
											
											<input type="hidden" id="fileName" name="fileName" value="${fileName!}"/>  
											<input type="hidden" id="currPage" name="currPage" value="${currPage!}"/>  
											<input type="hidden" id="pUrl" name="curMenu_pUrl" value="${curMenu.pUrl}"/>
											<input type="hidden" id="module" name="module" value="${module!}"/>
											
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<jsplugininner>
			<script type="text/javascript" src="${contextPath}/resources/metronic/admin/pages/scripts/form-wizard-export.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
		</jsplugininner>
		
		<jsinner>
			<script>
				jQuery(document).ready(function() {
				
					FormWizard.init();
					
					showSelectFileFormatDiv();
					
					<#if (isDownloadFile??) && (isDownloadFile == 1)>
						download_file();
					</#if>
					
					<#if (currPage??)>
						<#if (currPage == 1)>
						//	$('#form_wizard_1').find('.button-previous').hide();
						<#elseif (currPage == 2)>
						
						//	$('#form_wizard_1').find('.button-previous').show();
							
							if($("[name=file_format]:radio:checked").val() == 'dbf'){
								$('#form_wizard_1').find('.button-next').attr("class", "btn yellow button-next").text("导出").attr("href","javascript:export_dbf();");
							}else{
								$('#form_wizard_1').find('.button-next').attr("class", "btn yellow button-next").text("导出").attr("href","javascript:export_excel();");
							}
							$('#form_wizard_1').find('.button-next').append($('<i class="m-icon-swapright m-icon-white"></i>'));
						<#elseif (currPage == 3)>
							$('#form_wizard_1').find('.button-next').hide();
							$('#form_wizard_1').find('.button-submit').show();
						</#if>
					</#if>
				
				});
				
				
				// 根据选择的文件类型展示不同的div
				function showSelectFileFormatDiv(){
					if($("[name=file_format]:radio:checked").val() == 'dbf'){
						$("#tab2-dbf").css("display","");	
						$("#tab2-excel").css("display","none");
					}else{
						$("#tab2-dbf").css("display","none");
						$("#tab2-excel").css("display","");
					}
					
				}
				
				// select --> onchange 改变事件
				function changeTemplateDetail(templateId){
					var url = "${contextPath}/${curMenu.pUrl}/" +templateId+ "/excel/directaccess${pageSupport.paramVo.parm?js_string}";
					$("#submit_form").attr("action",url);
					$("#submit_form").submit();
				}
				
				// 导出excel
				function export_excel(){
					var templateId = $("#excelTemplateId").val();
					var promptMsg = "没有可选择的导出模板，请先设置模板！";
					if(templateId == 0){
						bootbox.alert(promptMsg);
                        return;
					}
					var url = "${contextPath}/${curMenu.pUrl}/excel/export${pageSupport.paramVo.parm?js_string}";
					$("#submit_form").attr("action",url);
					$("#submit_form").submit();
				}
				
				// 导出dbf/excel
				function export_dbf(){
					var dbfTemplateId = $("#dbfTemplateId").val();
					var promptMsg = "没有可选择的导出模板，请先设置模板！";
					if(dbfTemplateId == 0){
						bootbox.alert(promptMsg);
                        return;
					}
					var url = "${contextPath}/${curMenu.pUrl}/excel/export${pageSupport.paramVo.parm?js_string}";
					$("#submit_form").attr("action",url);
					$("#submit_form").submit();
				}
				
				// 下载文件
				function download_file(){
					var url ="${contextPath}/${curMenu.pUrl}/excel/download";
					$("#submit_form").attr("action",url);
					$("#submit_form").submit();
				}
			</script>
		</jsinner>
	
	</body>
</html>