<#import "/macro/commonMacros.ftl" as commonMacros />
<!DOCTYPE html>

<html>
	<head>
		<title>${curMenu.title }</title>
		<meta charset="utf-8"/>
		<link type="text/css" rel="stylesheet" href="${contextPath}/resources/metronic/global/plugins/dropzone/css/dropzone.css"/>
		<input id="contextPath" type="hidden" value="${contextPath}" />
		<style type="text/css">
			ul{
				list-style-type:none;
			}
		
			.select2-results {
			  margin: -11px 0;
			}
		</style>
	</head>
	
	<body class="page-header-fixed page-quick-sidebar-over-content">
		<@commonMacros.breadcrumbNavigation />
		<div class="row">
			<div class="col-md-12">
				<div class="portlet box green-seagreen" id="form_wizard_1">
					<div class="portlet-title">
						<div class="caption">
							${curMenu.title }
						</div>
						<div class="tools hidden-xs">
							<a href="" class="collapse"> </a>
						</div>
					</div>
					<div class="portlet-body form">
						<form action="#" class="form-horizontal valid_form" id="submit_form" method="POST">
						<input type="hidden" id="file_format" name="file_format" value="${fileFormat}"/>
						<input type="hidden" name="fileFormat" value="${fileFormat}"/>
						<input type="hidden" name="type" value="${type}"/>
							<div class="form-wizard">
								<div class="form-body">
									<ul class="nav nav-pills nav-justified steps">
										<li class="active">
											<a href="#tab1" data-toggle="tab" class="step active">
												<span class="number"> 1 </span>
												<span class="desc"> <i class="fa fa-check"></i> 上传Excel文件 </span>
											</a>
										</li>
										<li>
											<a href="#tab2" data-toggle="tab" class="step">
												<span class="number"> 2 </span>
												<span class="desc"> <i class="fa fa-check"></i> 匹配字段 </span>
											</a>
										</li>
									</ul>
									<div id="bar" class="progress progress-striped" role="progressbar">
										<div class="progress-bar progress-bar-success" style="width: 50%;"></div>
									</div>
									<div class="tab-content">
										
										
										<div class="tab-pane active" id="tab1">
											<div class="form-group">
												<p class="col-md-3">
													<span class="label label-danger"> NOTE: </span>
													&nbsp; 上传Excel文件： <span class="file"> * </span>
												</p>
											</div>
											<table action="${contextPath}/${curMenu.pUrl}/list/tplupload" name="filepath" class="dropzone" style="width:100%; height:10px;" id="my-dropzone"></table>
										</div>
										
										
										
										<div class="tab-pane" id="tab2">
										
											<div class="form-body">
							<div class="row">
								<div class="col-md-8">
									<div class="form-group">
										<label class="control-label col-md-2">模板类别：</label>
										<div class="col-md-10">
											<label style="padding-left: 0px;margin-top:8px;">${curMenu.title }</label>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-8">
										<div class="form-group">
											<label class="control-label col-md-2"><span style="color:red">*&nbsp;</span>模板名称：</label>
											<div class="col-md-10">
												<@commonMacros.multiInput "edit" "title" template.title "required illchar" "rangelength=2,16 " />
											</div>
										</div>
									</div>
							</div>
											<div class="form-group">
												<div class="col-md-2"></div>
												<div class="col-md-3">
													<label class="control-label col-md-6">表头字段： </label>
													<select size="<#if (columnSize >0)>${columnSize!}<#else>10</#if>" multiple id="rootfield" class="form-control">
														<#if headerMap?exists>
															<#list headerMap?keys as key>
													    		<option value="${key}">
														    			${headerMap[key]} 
													    		</option>
													    	</#list>
													    </#if>
													</select>
												</div>
												<div class="col-md-3">
													<label class="control-label col-md-6">入库字段：</label>
													<select size="<#if (columnSize >0)>${columnSize!}<#else>10</#if>" multiple id="dbfield" class="form-control">
														<#if (columnCommentTarget?size >0)>
															<#list columnCommentTarget as columnCommentMap>
																	<#list columnCommentMap?keys as key>
															    		<option value="${key}">
																    			${columnCommentMap[key]} 
															    		</option>
															    	</#list>
													    	</#list>
													    </#if>	
													</select>
												</div>
												<div>
													</br></br></br>
													<a href="javascript:matchField2()" class="red btn">&nbsp;匹配&nbsp;<i class="m-icon-swapdown m-icon-white"></i></a>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-md-4">匹配结果：</label>
												<div class="col-md-4">
													<input type="hidden" readonly id="select2_sample5" class="form-control" />
												</div>
                        <span style="color:red">友情提示：双击可取消匹配结果</span>
											</div>
											
											<div class="row">
												<div class="col-md-8">
														<div class="form-group">
															<label class="control-label col-md-2">模板描述：</label>
															<div class="col-md-10">
																<@commonMacros.multiTextarea "edit" "description" template.description "illchar" 'rangelength="0,1024"' />
															</div>
														</div>
													</div>
											</div>
							
										</div>
									</div>
								</div>
								<div class="form-actions">
								<input id="fileValidate" type="hidden" value="${fileValidate!}"/>
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<a href="javascript:;" class="btn green-seagreen button-previous"><i class="m-icon-swapleft m-icon-black"></i>上一步</a>
											<a href="javascript:;" class="btn green-seagreen button-next">下一步<i class="m-icon-swapright m-icon-white"></i></a>
											<a class="btn green-seagreen button-submit" href="javascript:;" onclick="saveExcelImportTemplate();">保存<i class="m-icon-swapright m-icon-white"></i></a>
											<a href="${contextPath}/${curMenu.pUrl}/all/tpllist?module=${curMenu.rule}&pUrl=${curMenu.pUrl}" class="btn default btn_cancel">取消</a>
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
			<script type="text/javascript" src="${contextPath}/resources/js/list_sort.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/ajaxFileUploader/ajaxfileupload.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/plugins/dropzone/dropzone_payment.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/admin/pages/scripts/form-wizard_template.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/admin/pages/scripts/components-dropdowns_payment.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/admin/layout/scripts/demo.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/admin/pages/scripts/form-dropzone_payment.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/js/comn/comn_template.js"></script>
		</jsplugininner>
		
		<jsinner>
			<script>
				jQuery(document).ready(function() {
					FormWizard.init();
					ComponentsDropdowns.init();
					FormDropzone.init();
					
					//隐藏匹配结果区域 初始化后的小块。
					$("#s2id_autogen1").attr('type','hidden');
					
					
				});
				
				
				var currentTime = new Date();
				var actstr = document.getElementById("my-dropzone").getAttribute("action");
				var importGroupToken = currentTime.getFullYear() + '-' + currentTime.getMonth() + '-' + currentTime.getDate() + '-' + currentTime.getHours() + '-' + currentTime.getMinutes() + '-' + currentTime.getSeconds();
				document.getElementById("my-dropzone").setAttribute("action", actstr + "?importGroupToken=" + importGroupToken);
				
				var ajaxFileUpload = function() {
					$.ajaxFileUpload({
						url : actstr,
						secureuri : false,
						fileElementId : 'file',
						dataType : 'json',
						success: function(data, status) {
						},
						error: function (data, status, e) {
						}
					});
				}
				
					function saveExcelImportTemplate(){
  					var urlSave = "${contextPath}/${curMenu.pUrl}/-1/tplimport";
  					var urlRedirect = "${contextPath}/${curMenu.pUrl}/all/tpllist?module=${curMenu.rule}&pUrl=${curMenu.pUrl}";
  					saveExcelImportTemplateDo('${curMenu.rule}',urlSave,urlRedirect);
				}
				
			</script>
		</jsinner>
	
	</body>
</html>