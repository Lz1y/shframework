<#import "/spring.ftl" as spring />
<#import "/macro/commonMacros.ftl" as commonMacros />
<html>
	<head>
		<title></title>
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		
			<div class="tab-content">
				<div class="tab-pane active" id="tab_0">
					
						<div class="portlet-body form">
						    <#if dict?exists>
							<form class="form-horizontal valid_form" name="dictForm" method="post"
								action="${contextPath}/dict/edu/common/deptmajor/<@commonMacros.scope />/${dict.id?default(0)}/save" >
							   
							   <Input type="hidden" name="departmentId" value="${dict.departmentId?if_exists}"/> 
							   <Input type="hidden" name="oldMajorId" value="${dict.majorId?if_exists}"/>
							    <@spring.bind "dict.errorMsg" />
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
										<label class="col-md-3 control-label">院系</label>
										<div class="col-md-4">
									    	<#if department?exists>
									    	<#list department?keys as key>
									    		<#if key == dict.departmentId?default(0)?string>
									    			<label class="radio-inline">
									    			${department[key].title}
									    			</label>
									    			<#assign tempcampusId=department[key].campusId/>
									    		</#if>
									    	</#list>
									    	</#if>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">校区</label>
										<div class="col-md-4">
											<@commonMacros.multiSelect campus "read" "campusId" tempcampusId />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />专业</label>
										<div class="col-md-4">
										    
										    <@commonMacros.getCascadeInfoByMajorId dict.majorId />	
											<select name="categoryId" class="form-control required">
											<#list categorymajor?keys as key>
										    		<option value="${key}" <#if commonMacros.tempCategory! && key == commonMacros.tempCategory.id?string >selected </#if>>
										    			<@commonMacros.showDictTitle categorymajor[key] />
										    		</option>
										    	</#list>
											</select>
											<@commonMacros.multiSelect groupmajor showStyle "groupId" "" "required" />
											<@commonMacros.multiSelect major showStyle "majorId" dict.majorId "required" />
											
											<@spring.bind "dict.majorId" />
											<@spring.showErrors "" "alert font-red" />
										
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />内部编码</label>
										<div class="col-md-4">
											<@spring.formInput  "dict.innerCode" 'class="form-control required zint"  maxlength="6" placeholder="内部编码"'/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
								
								
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="button" class="btn green-seagreen" onclick="javascript:saveMajorDepartment(this);">保存</button>
											<button type="button" class="btn default button-previous btn_cancel"  onclick="javascript:editCancel(${dict.departmentId?if_exists});">取消</button>
										</div>
									</div>
								
							</form>
							<#else>
								抱歉，没有找到符合条件的数据！<br/>
								<button type="button" class="btn green-seagreen button-previous"  onclick="javascript:editCancel(${departmentId?if_exists});">返回</button>
							</#if>
							<!-- END FORM-->
						
						</div>
				</div>
			</div>
		<!-- END PAGE CONTENT-->
		
		<script src="${contextPath}/resources/metronic/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
		<script src="${contextPath}/resources/metronic/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
		<script src="${contextPath}/resources/js/form_validate.js" type="text/javascript"></script>
		<!-- common_util  通用工具-->
		<script src="${contextPath}/resources/js/common_util.js" type="text/javascript"></script>
		<script src="${contextPath}/resources/js/dict_majordepartment.js" type="text/javascript"></script>
		<script type="text/javascript" src="${contextPath}/resources/js/jquery.cascadingdropdown.js?v=1.0.1"></script>
		<script type="text/javascript" src="${contextPath}/resources/js/jquery.cascadingdropdown_init.js"></script>
		<script type="text/javascript">
				//定义初始变量 
				var tempCategoryId = <@commonMacros.getCascadeCategoryId /> ;
				var tempGroupId = <@commonMacros.getCascadeGroupId /> ;
				var tempMajorId = ${dict.majorId?default(0)};
				
				var categoryIdSelector = "select[name='categoryId']";
				var groupIdSelector = "select[name='groupId']";
				var majorIdSelector = "select[name='majorId']";
				
				//初始化专业目录--专业大类级联
                init_CascadingDropDown(categoryIdSelector, groupIdSelector, tempCategoryId, tempGroupId,  "categoryGroup");
                //初始化专业大类--专业级联
                init_CascadingDropDown(groupIdSelector, majorIdSelector, tempGroupId, tempMajorId ,  "groupMajor");	
						
				$(categoryIdSelector).change();
				
		
						function saveMajorDepartment(obj){
							var url;
							<#if  dict.majorId??>
							url = "${contextPath}/dict/edu/common/deptmajor/<@commonMacros.scope />/${departmentId?if_exists}/save";
							<#else>
							url = "${contextPath}/dict/edu/common/deptmajor/<@commonMacros.scope />/0/save";
							</#if>
							
							var $form=$("form[name=dictForm]");
							if($form.valid()){
								var departmentId = $form.find("[name=departmentId]").val();
								var majorId = $form.find("[name=majorId]").val();
								var innerCode = $form.find("[name=innerCode]").val();
								var oldMajorId = $form.find("[name=oldMajorId]").val();
								
								var data = {departmentId:departmentId, majorId:majorId, innerCode:innerCode, oldMajorId: oldMajorId};
								
								saveMajorDepartmentAjax(obj, ${departmentId!0}, url, data);
							}
						}
		</script>
		
	</body>
</html>
