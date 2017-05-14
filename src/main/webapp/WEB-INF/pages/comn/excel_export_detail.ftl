<#import "/spring.ftl" as spring />  
<#import "/macro/commonMacros.ftl" as commonMacros />
<#setting classic_compatible=true>

<html>
	<head>
		<meta charset="utf-8"/>
		<title>${curMenu.title }</title>
		<style type="text/css">
			.radio-inline{
			  display: inline-block;
			  padding-left: 0px;
			  margin-bottom: 0;
			  font-weight: 400;
			  vertical-align: middle;
			  cursor: pointer;
		}
	</style>
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		<@commonMacros.breadcrumbNavigation />
		<div class="tab-pane active" id="tab_0">
			<div class="portlet box <#if showStyle=="tplread">blue-steel<#else>green-seagreen</#if>">
			
				<div class="portlet-title">
					<div class="caption">
						<span class="caption">${curMenu.title } </span>
					</div>
				</div>
				
				<div class="portlet-body form">
				<!-- BEGIN FORM-->
					<form class="form-horizontal valid_form" id="templateForm" name="templateForm" method="post" action="" onsubmit="">
					<div class="form-body">
							<div class="row">
								<div class="col-md-8">
									<div class="form-group">
										<label class="control-label col-md-2">模板类别：</label>
										<div class="col-md-8">
											<label style="padding-left: 0px;margin-top:8px;">${curMenu.title }</label>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-8">
										<div class="form-group">
											<label class="control-label col-md-2"><span style="color:red">*&nbsp;</span>模板名称：</label>
											<div class="col-md-8">
												<@commonMacros.multiInput "read" "title" template.title "required illchar" "rangelength=2,16 " />
											</div>
										</div>
									</div>
							</div>
							
								<div class="row ">
									<!--/span-->
									<div class="col-md-8">
										<div class="form-group ">
											<label class="col-md-2 control-label">导出字段：</label>
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

							
							<div class="row">
								<div class="col-md-8">
										<div class="form-group">
											<label class="control-label col-md-2">模板描述：</label>
											<div class="col-md-8">
												<@commonMacros.multiTextarea "read" "description" template.description 'rangelength="0,1024"' />
											</div>
										</div>
									</div>
							</div>
							
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<a href="${contextPath}/${curMenu.pUrl}/all/tpllist?module=${curMenu.rule}&pUrl=${curMenu.pUrl}" class="btn green-seagreen">返回</a>
										</div>
									</div>
								</div>
						</div>		
					</form>
					<!-- END FORM-->
				</div>
			</div>
		</div>
		<jsplugininner>
		</jsplugininner>
		<jsinner>
			<script>
				$(document).ready(function() {
				});//ready()
				
			</script>
		</jsinner>
		
		

	</body>
</html>
