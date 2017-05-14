<#import "/macro/commonMacros.ftl" as commonMacros />

<html>
	<head>
		<title>${curMenu.title }</title>
		
	</head>
	<body>
		<@commonMacros.breadcrumbNavigation />
		<div class="row">
			<div class="col-md-12">
				<div class="portlet box green-seagreen">
					<div class="portlet-title">
						<div class="caption">
							${curMenu.title }
						</div>
					</div>
					<div class="portlet-body">
						
						<form name="rolePermission" method="post" action="${contextPath}/sys/role/permission/<@commonMacros.scope />/${roleid}/save">
							<#if redirectModuleId?exists>
								<input type="hidden" name="module_id" value="${redirectModuleId}" />
							</#if>
							<div class="row">
								<div class="col-md-2">
									<div class="form-group">
										<label class="control-label col-md-12">资源：<#list moduleList as module><#if module.id == moduleId>${module.title}</#if></#list></label>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-12">角色：${sysrole[roleId+""].title}</label>
									</div>
								</div>
								<!--/span-->
						    	<div class="col-md-4">
									<div class="form-group ">
										<label class="col-md-5 control-label">资源可见范围：</label>
										<div class="col-md-6">
											<@commonMacros.multiSelect staticlabel.scopetype "edit" "scope" scopeInteger/>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
							
							<input type="hidden" name="customid" value=""/>
							<input type="hidden" name="mode" value="1"/>
							<#--
							<table class="table table-striped table-bordered table-hover">
								<tbody>
									<tr>
										<td width="25%">
											<label class="control-label">资源可见范围</label>
										</td>
										<td>
											
										</td>
									</tr>
									
									<tr>
										<td>
											自定义资源ID 
										</td>
										<td>
											<input class="form-control form-filter input-sm" name="customid" type="text">
										</td>
									</tr>
									<tr>
										<td>
											检查模式
										</td>
										<td id="cludeflag">
											<input name="mode" type="radio" checked value="1" /> 包含
											<input name="mode" type="radio" value="2" /> 排除
										</td>
									</tr>
								
								</tbody>
							</table>
								-->
							<hr>
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr class="heading">
										<th>
											 <input type="checkbox"  onclick="checkAll(this)" />#
										</th>
										<#if srrpvo.plist??>
											<#list srrpvo.plist as p>
												<th>
													${p.title}<br/><input type="checkbox"  onclick="checkCol(${p.id}, this)" />
												</th>
											</#list>
										</#if>
									</tr>
								</thead>
								<tbody>
									<#assign resPermissionMap=srrpvo.resPermissionMap/>
									<#if srrpvo.rlist??>
										<#list srrpvo.rlist as r>
											<tr>
												<td>
													<input type="checkbox"  onclick="checkRow(${r.id}, this)" /><#if r.level==0><b><#elseif r.level==1><b><#elseif r.level gt 1><#list 1..r.level as times>&nbsp;&nbsp;</#list></#if>${r.title}
												</td>
												<#if srrpvo.plist??>
													<#list srrpvo.plist as p>
														<td>
															<#if  resPermissionMap[r.rule]?exists && resPermissionMap[r.rule].perList?seq_contains(p.code)>
																<#assign isChecked=false/>
																<#list srrpvo.list as main><#if (main.resourceId == r.id || main.resourceId == 1) && (main.permissionId == 1 || main.permissionId == p.id)><#assign isChecked=true/><#break></#if></#list>
																<#assign isDisabled=false/>
																<#if resPermissionMap[r.rule].readOnlyPerList?seq_contains(p.code)><#assign isDisabled=true/></#if>
																
																<input type="checkbox" name="rp" class="res_${r.id} per_${p.id}" value="${r.id}_${p.id}" 
																		<#if isChecked>checked</#if>
																		<#if isDisabled>disabled</#if>
																/>
																<#if isDisabled>
																	<#if isChecked>
																		<input type="hidden" name="rp" value="${r.id}_${p.id}" readonly/>
																	</#if>
																</#if>
															</#if>
														</td>
													</#list>
												</#if>
											</tr>
										</#list>
									</#if>
								</tbody>
								<#if srrpvo.rlist?? && srrpvo.rlist?size gt 10>
								<thead>
									<tr class="heading">
										<th></th>
										<#if srrpvo.plist??>
										<#list srrpvo.plist as p>
										<th>
											<input type="checkbox"  onclick="checkCol(${p.id}, this)" /><br/>${p.title}
										</th>
										</#list>
										</#if>
									</tr>
								</thead>
								</#if>
							</table>
							<div class="text-center">
							<button type="submit" class="btn green-seagreen" > 保存</button>
							<a class="btn default button-previous btn_cancel"  href="${contextPath}/sys/role/<@commonMacros.scope />/all/list<#if redirectModuleId?exists>?module_id=${redirectModuleId}</#if>">取消</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- END PAGE CONTENT-->
		<div id="siteMeshJavaScriptPlugins">
 			<#--<script src="${contextPath}/resources/metronic/admin/pages/scripts/table-managed.js"></script>-->
 			<#--<script type="text/javascript" src="${contextPath}/resources/metronic/global/plugins/datatables/extensions/FixedColumns/js/dataTables.fixedColumns.js"></script>-->
		</div>
		<div id="siteMeshJavaScript">
			<script>
				jQuery(document).ready(function() {       
				
					
				});
				$('#confirm-delete').on('show.bs.modal', function(e) {
				    $(this).find('.danger').attr('href', $(e.relatedTarget).data('href'));
				    $('.debug-url').html('Delete URL: <strong>' + $(this).find('.danger').attr('href') + '</strong>');
				});
				
				function checkAll(ele){
					changeCheckBox("input[name=rp]:enabled", ele.checked);
				}
				function checkCol(perId, ele){
					changeCheckBox("input[name=rp]:enabled.per_"+ perId, ele.checked);
				}
				function checkRow(resId, ele){
					changeCheckBox("input[name=rp]:enabled.res_" + resId, ele.checked);
				}
				
				function changeCheckBox(selector, isChecked){
					if(isChecked) {
						$(selector).each(function(){
							//this.setAttribute("checked", "checked");
							$(this).prop("checked", true);
							this.parentNode.setAttribute("class", "checked");
						});
					} else {
						$(selector).each(function(){
							//this.removeAttribute("checked");
							$(this).prop("checked", false);
							this.parentNode.setAttribute("class", "");
						});
					}
				}
				
			</script>
		</div>
	</body>
</html>

