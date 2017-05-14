<#import "/spring.ftl" as spring />
<#import "/macro/commonMacros.ftl" as commonMacros />
<#setting classic_compatible=true>
<html>
	<head>
		<title>${curMenu.title?if_exists}</title>
		<link rel="stylesheet" href="${contextPath}/resources/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
		<link rel="stylesheet" href="${contextPath}/resources/zTree/css/demo.css" type="text/css" />
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		<@commonMacros.breadcrumbNavigation/>
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		
				<div class="tab-pane active" id="tab_0">
					<div class="portlet box <#if showStyle=="read">blue-steel<#else>green-seagreen</#if>">
						<div class="portlet-title">
							<div class="caption">
								${curMenu.title?if_exists}
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal valid_form " name="employeeForm" method="post" 
								action="${contextPath}/edu/tch/employee/<@commonMacros.scope />/${employee.userId?default(0)}/save${pageSupport.paramVo.parm?if_exists?html}" >
							    <input type="hidden" name="userId" value="${employee.userId?if_exists}"/>
							    <input type="hidden" name="departmentId" value="${departmentId}"/>
							    <input type="hidden" name="allFlag" value="${allFlag}"/>
										<div class="form-body">
											<#if (showStyle=="add" || showStyle=="edit") && employee?? >
												<@spring.bind "employee.errorMsg" />
												<#if spring.status.error>
													<div class="alert alert-danger">
														<button class="close" data-close="alert"></button>
														<span>  
															<#list spring.status.errorMessages as error>
															<li>${error}</li>
															</#list> 
														</span>
													</div>
												</#if>
											</#if>
											    <div class="form-group">
											        <label class="col-md-3 control-label"><@commonMacros.requiredMark />姓名：</label>
												    <div class="col-md-4">
												    		<@commonMacros.multiInput showStyle "username" employee.user.username "required" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />职工号：</label>
													<div class="col-md-4">
															<@commonMacros.multiInput showStyle "userNo" employee.user.userNo "required" />
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />性别：</label>
													<div class="col-md-4">
														<@commonMacros.multiLabel showStyle "gender" "gender" employee.gender?default(1) />
													</div>
												</div>
												<div class="form-group" style="position: relative;">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />所属部门：</label>
													<div class="col-md-4">
															<#if showStyle=="read">
																<label style="padding-left: 19px;margin-top:8px;">${(employee.department.title)!}</label>
															<#elseif showStyle=="add">
															<div class="">
																<div class="">
																	<ul class="">
																		<input id="orgSel" type="text" readonly value="<#if eduCommonDepartment??>${eduCommonDepartment.title?if_exists}</#if>" class="form-control" onclick="showMenu();" style="cursor:pointer;background-color:#fff"/>
																	</ul>
																</div>
																<div id="menuContent" class="menuContent" style="display:none;" sytle="position: absolute;" class="col-md-4" >
																	<ul id="treeDemo" class="ztree" style="margin-top:0; width:332px;"></ul>
																</div>							
															</div>
															<#else>
																<div class="">
																<div class="">
																	<ul class="">
																		<input id="orgSel" type="text" readonly value="<#if employee??>${employee.department.title?if_exists}</#if>" class="form-control" onclick="showMenu();" style="cursor:pointer;background-color:#fff"/>
																	</ul>
																</div>
																<div id="menuContent" class="menuContent" style="display:none;" sytle="position: absolute;" class="col-md-4" >
																	<ul id="treeDemo" class="ztree" style="margin-top:0; width:332px;"></ul>
																</div>							
															</div>
															</#if>
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label"><@commonMacros.requiredMark />岗位分类：</label>
													<div class="col-md-4">
														<@commonMacros.multiSelect posttype showStyle "postTypeId" employee.postTypeId "required" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">行政职务：</label>
													<div class="col-md-4">
														<@commonMacros.multiInput showStyle "postTitle" employee.postTitle  />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">职务等级：</label>
													<div class="col-md-4">
														<@commonMacros.multiSelect postlevel showStyle "postLevelId" employee.postLevelId "required" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">编制类型：</label>
													<div class="col-md-4">
														<@commonMacros.multiSelect stafftype showStyle "staffTypeId" employee.staffTypeId />
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-md-3 control-label">是否任课：</label>
													<div class="col-md-4">
														<@commonMacros.multiLabel showStyle "isZeroORNot" "teachFlag" employee.teachFlag?default(1)/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">是否退休：</label>
													<div class="col-md-4">
														<@commonMacros.multiLabel showStyle "isZeroORNot" "retireFlag" employee.retireFlag?default(0)/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">是否有教师证：</label>
													<div class="col-md-4">
														<@commonMacros.multiLabel showStyle "isZeroORNot" "certFlag" employee.certFlag?default(1)/>
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">激活：</label>
													<div class="col-md-4">
														<@commonMacros.multiLabel showStyle "isZeroORNot" "status" employee.status?default(1) />
													</div>
												</div>
											
											</div>
								
								<div class="form-actions">
								<input type="hidden" name="orgId" id="orgId" value="${orgId}"/>
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<#if showStyle=="read">
											<button type="button" class="btn blue-steel button-previous btn_cancel"  onclick="location.href = '${contextPath}/edu/tch/employee/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}&departmentId=${departmentId}&allFlag=${allFlag}'">返回</button>
											<#else>
											<button type="submit" class="btn green-seagreen">保存</button>
											<button type="button" class="btn default button-previous btn_cancel"  onclick="location.href = '${contextPath}/edu/tch/employee/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}&departmentId=${departmentId}&allFlag=${allFlag}'">取消</button>
											</#if>
										</div>
									</div>
								</div>
							</form>
							<!-- END FORM-->
							
						</div>
					</div>
				</div>
		<!-- END PAGE CONTENT-->
		<jsplugininner>
			<script type="text/javascript" src="${contextPath}/resources/zTree/js/jquery.ztree.core-3.5.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/zTree/js/jquery.ztree.excheck-3.5.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/js/employee_zTree.js"></script>
		</jsplugininner>
		
		<jsinner>
			<!-- BEGIN PAGE LEVEL SCRIPTS -->
			<script type="text/javascript">
			
			$(document).ready(function(){
				$.fn.zTree.init($("#treeDemo"), setting);
			});
			
			</script>
		</jsinner>
	</body>
</html>
