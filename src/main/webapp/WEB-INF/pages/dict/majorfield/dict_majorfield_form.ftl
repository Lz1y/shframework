<#import "/spring.ftl" as spring />
<#import "/macro/commonMacros.ftl" as commonMacros />
<html>
	<head>
		<title>${curMenu.title?if_exists}</title>
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
							    	<div class="form-body">
								    <@spring.bind "majorfield.errorMsg" />
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
									
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />专业目录</label>
										<div class="col-md-4">
										    <@commonMacros.getCascadeInfoByMajorId majorfield.majorId />	
											<select name="categoryId" class="form-control required">
											<#list categorymajor?keys as key>
										    		<option value="${key}" <#if commonMacros.tempCategory! && key == commonMacros.tempCategory.id?string >selected </#if>>
										    			<@commonMacros.showDictTitle categorymajor[key] />
										    		</option>
										    	</#list>
											</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />专业大类</label>
										<div class="col-md-4">
											<@commonMacros.multiSelect groupmajor showStyle "groupId" "" "required" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />专业</label>
										<div class="col-md-4">
											<@commonMacros.multiSelect major showStyle "majorId" majorfield.majorId "required" />
											<@spring.bind "majorfield.majorId" />
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />专业方向编码</label>
										<div class="col-md-4">
											<@spring.formInput  "majorfield.code" 'class="form-control required"  maxlength="20" placeholder="专业方向编码"'/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />专业方向名称</label>
										<div class="col-md-4">
											<input type="hidden" name="principal" value="${majorfield.principal}"/>
											<@spring.formInput  "majorfield.title" 'class="form-control required"  maxlength="50" placeholder="专业方向名称"'/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />显示次序</label>
										<div class="col-md-4">
											<@spring.formInput  "majorfield.priority" 'class="form-control required int " range="1,99999" placeholder="显示次序"'/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />选用状态</label>
										<div class="col-md-4">
											<@commonMacros.multiLabel showStyle "status" "status" majorfield.status?default(1)/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />执行标准</label>
										<div class="col-md-5">
											<@commonMacros.multiLabel showStyle "standard" "standard" majorfield.standard?default(-1)/>
										</div>
									</div>
									
								</div>
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<#if !majorfield.locked?? ||  majorfield.locked == 0 >
											<button type="submit" class="btn green-seagreen">保存</button>
											<button type="button" class="btn default button-previous btn_cancel"  onclick="location.href = '${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">取消</button>
											<#else>
											<button type="button" class="btn green-seagreen button-previous btn_cancel"  onclick="location.href = '${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
											</#if>
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
				//定义初始变量 
				var tempCategoryId = <@commonMacros.getCascadeCategoryId /> ;
				var tempGroupId = <@commonMacros.getCascadeGroupId /> ;
				var tempMajorId = ${majorfield.majorId?default(0)};
				
				var categoryIdSelector = "select[name='categoryId']";
				var groupIdSelector = "select[name='groupId']";
				var majorIdSelector = "select[name='majorId']";
					
				//初始化专业目录--专业大类级联
				init_CascadingDropDown(categoryIdSelector, groupIdSelector, tempCategoryId, tempGroupId,  "categoryGroup");
	            //初始化专业大类--专业级联
	            init_CascadingDropDown(groupIdSelector, majorIdSelector, tempGroupId, tempMajorId ,  "groupMajor");
				$(categoryIdSelector).change();
				
           		
			});//ready	
			</script>
		</jsinner>
		
		<jsplugininner>
			<script type="text/javascript" src="${contextPath}/resources/js/jquery.cascadingdropdown.js?v=1.0.1"></script>
			<script type="text/javascript" src="${contextPath}/resources/js/jquery.cascadingdropdown_init.js"></script>
		</jsplugininner>
		
	</body>
</html>
