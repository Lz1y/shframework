<#import "/spring.ftl" as spring />
<#import "/macro/commonMacros.ftl" as commonMacros />
<html>
	<head>
		<title>${curMenu.title?if_exists}</title>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
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
						    <#if major?exists>
							<form class="form-horizontal valid_form" commandName="dict" name="dictForm" method="post"
								action="${contextPath}/dict/edu/common/major/<@commonMacros.scope />/${major.id?default(0)}/save${pageSupport.paramVo.parm?if_exists?html}" >
							    <input type="hidden" name="id" value="${major.id?if_exists}"/>
								<div class="form-body">
									<@spring.bind "major.errorMsg" />
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
										<div class="col-md-4 ">
											<@commonMacros.getCascadeInfoByGroupId major.groupId />	
											<select name="categoryId" class="form-control required">
											<#list categorymajor?keys as key>
										    		<option value="${key}" <#if commonMacros.tempCategory! && key == commonMacros.tempCategory.id >selected </#if>>
										    			<@commonMacros.showDictTitle categorymajor[key] />
										    		</option>
										    	</#list>
											</select>
										</div>
									</div>
									
									<div class="form-group">
									    <label class="col-md-3 control-label"><@commonMacros.requiredMark />专业大类</label>
										<div class="col-md-4 ">
											<@commonMacros.multiSelect groupmajor showStyle "groupId" major.groupId "required" />
											<@spring.bind "major.groupId" />
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />专业代码</label>
										<div class="col-md-4">
											<@spring.formInput  "major.code" 'class="form-control required"  maxlength="20" placeholder="专业代码"'/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									
									<#if showStyle="add">
									<div class="form-group">
										<label class="col-md-3 control-label">原专业代码</label>
										<div class="col-md-4">
											<@spring.formInput  "major.formerCode" 'class="form-control "  maxlength="20" placeholder="原专业代码"'/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									<#else>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />原专业代码</label>
										<div class="col-md-4">
											<@spring.formInput  "major.formerCode" 'class="form-control required"  maxlength="20" placeholder="原专业代码"'/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
												
									</#if>
									
									
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />专业名称</label>
										<div class="col-md-4">
											<@spring.formInput  "major.title" 'class="form-control required"  maxlength="50" placeholder="专业名称"'/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">开办时间</label>
										<div class="col-md-4">
											<input class="form-control datepicker" placeholder="开办时间"   name="startDate" type="text"  value="<#if major.startDate?exists>${major.startDate?string('yyyy-MM-dd')}</#if>"/>
											<@spring.bind  "major.startDate"/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label"><@commonMacros.requiredMark />编码类型</label>
										<div class="col-md-4">
											<@commonMacros.multiLabel showStyle "majorType" "type" major.type?default(0)/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">沿革信息</label>
										<div class="col-md-4">
											<@spring.formInput  "major.reformationInfo" 'class="form-control " maxlength="10240" placeholder="沿革信息"'/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">描述</label>
										<div class="col-md-4">
											<@spring.formInput  "major.description" 'class="form-control " maxlength="10240" placeholder="描述"'/>
											<@spring.showErrors "" "alert font-red" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">选用状态</label>
										<div class="col-md-4">
											<@commonMacros.multiLabel showStyle "status" "status" major.status?default(1)/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">执行标准</label>
										<div class="col-md-5">
											<@commonMacros.multiLabel showStyle "standard" "standard" major.standard?default(-1)/>
										</div>
									</div>
									
								</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="submit" class="btn green-seagreen">保存</button>
											<button type="button" class="btn default button-previous btn_cancel"  onclick="location.href = '${contextPath}/dict/edu/common/major/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">取消</button>
										</div>
									</div>
								</div>
							</form>
							<#else>
								抱歉，没有找到符合条件的数据！<br/>
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="button" class="btn green-seagreen button-previous"  onclick="location.href = '${contextPath}/dict/edu/common/major/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
										</div>
									</div>
								</div>
							</#if>
							<!-- END FORM-->
		
						</div>
					</div>
				</div>
			</div>
			
		<jsplugininner>
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/js/jquery.cascadingdropdown.js?v=1.0.1"></script>
			<script type="text/javascript" src="${contextPath}/resources/js/jquery.cascadingdropdown_init.js"></script>
		</jsplugininner>
		
		<jsinner>
			<script type="text/javascript">
				$(document).ready(function(){
					$(".datepicker").datepicker({
						format: 'yyyy-mm-dd',
						weekStart: 1,
						autoclose: true,
						todayBtn: 'linked',
						language: 'zh-CN'
					});
									 
					//定义初始变量 
					var tempCategoryId = <@commonMacros.getCascadeCategoryId /> ;
					var tempGroupId = ${major.groupId?default(0)};
					
					var categoryIdSelector = "select[name='categoryId']";
					var groupIdSelector = "select[name='groupId']";
					
					//初始化专业目录--专业大类级联
					init_CascadingDropDown(categoryIdSelector, groupIdSelector, tempCategoryId, tempGroupId,  "categoryGroup");
					$(categoryIdSelector).change();
									 
				});//ready()
			</script>
		</jsinner>
		<!-- END PAGE CONTENT-->
	</body>
</html>
