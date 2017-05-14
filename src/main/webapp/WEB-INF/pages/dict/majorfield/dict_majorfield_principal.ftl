<#--专业方向的负责人设置-->
<#import "/spring.ftl" as spring />
<#import "/macro/commonMacros.ftl" as commonMacros />
<html>
	<head>
		<title>${curMenu.title?if_exists}</title>
		<#--自动补全 样式-->
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/auto_complete/css/jquery.autocomplete.css" />
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		<@commonMacros.breadcrumbNavigation/>
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		
			<div class="tab-content">
				<div class="tab-pane active" id="tab_0">
					<div class="portlet box green-seagreen">
						<div class="portlet-title">
							<div class="caption">
								${curMenu.title?if_exists}
							</div>
						</div>
						<div class="portlet-body form">
						    <#if majorfield?exists>
							<form class="form-horizontal valid_form" name="dictForm" method="post"
								action="${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />/${majorfield.id?default(0)}/save${pageSupport.paramVo.parm?if_exists?html}" >
							    <input type="hidden" name="id" value="${majorfield.id?if_exists}"/>
							    <input type="hidden" name="type" value="${type}"/>
							    
							    <div class="form-body">
									<div class="form-group">
										<label class="col-md-3 control-label">专业目录</label>
										<div class="col-md-4">
										    <@commonMacros.getCascadeInfoByMajorId majorfield.majorId />
											<@commonMacros.multiSelect categorymajor "read" "categoryId" "${commonMacros.tempCategory.id}"  />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">专业大类</label>
										<div class="col-md-4">
											<@commonMacros.multiSelect groupmajor "read" "groupId" "${commonMacros.tempGroup.id}" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">专业</label>
										<div class="col-md-4">
											<@commonMacros.multiSelect major "read" "majorId" majorfield.majorId "required" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">专业方向编码</label>
										<div class="col-md-4">
											<@commonMacros.multiInput "read" "code" majorfield.code />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">专业方向名称</label>
										<div class="col-md-4">
											<@commonMacros.multiInput "read" "title" majorfield.title />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">负责人</label>
										<div class="col-md-4">
											<#--用起来有点麻烦-->
											<#assign principalName><#if employeename[majorfield.principal?default(0)?string]??>${employeename[majorfield.principal?default(0)?string].title}</#if></#assign>
											<@commonMacros.multiInput showStyle "principalName" principalName "" 'maxlength="20" '/>
											<input type="hidden" name="principal" value="${majorfield.principal}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">显示次序</label>
										<div class="col-md-4">
											<@commonMacros.multiInput "read" "priority" majorfield.priority />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">选用状态</label>
										<div class="col-md-4">
											<@commonMacros.multiLabel "read" "status" "status" majorfield.status?default(1)/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">执行标准</label>
										<div class="col-md-4">
											<@commonMacros.multiLabel "read" "standard" "standard" majorfield.standard?default(-1)/>
										</div>
									</div>
								</div>
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="submit" class="btn green-seagreen">保存</button>
											<button type="button" class="btn default button-previous btn_cancel"  onclick="location.href = '${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">取消</button>
										</div>
									</div>
								</div>
							</form>
							<#else>
								抱歉，没有找到符合条件的数据！<br/>
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="button" class="btn green-seagreen button-previous" onclick="location.href = '${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
										</div>
									</div>
								</div>
							</#if>
							<!-- END FORM-->
						
						</div>
					</div>
				</div>
			</div>
		<!-- END PAGE CONTENT-->
		
		<jsinner>
			<script type="text/javascript">
			$("document").ready(function(){
           		 init_autocomplete_employee_ajax("input[name='principalName']", "input[name='principal']");
			});//ready	
			</script>
		</jsinner>
		
		<jsplugininner>
			<#--自动补全 js-->
			<script type="text/javascript" src="${contextPath}/resources/auto_complete/js/jquery.autocomplete.V1.2.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/js/auto_complete_init.js"></script>
		</jsplugininner>
		
	</body>
</html>
