<#import "/spring.ftl" as spring />  
<#import "/macro/commonMacros.ftl" as commonMacros />

<html>
	<head>
		<title>${curMenu.title }</title>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/bootstrap-datepicker/css/datepicker.css"/>
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		<@commonMacros.breadcrumbNavigation />
		<div class="tab-pane active" id="tab_0">
			<div class="portlet box green-seagreen">
				<div class="portlet-title">
					<div class="caption">
						<span class="caption">${curMenu.title }</span>
					</div>
				</div>
				<div class="portlet-body">
						<div class="table-toolbar">
							
							<!-- BEGIN FORM -->
							<form class="form-horizontal valid_form" name="" method="post" 
								action="${contextPath}/sys/profile/<@commonMacros.scope />/all/edit">
								
								<!--
								<div class="row ">
						    		<div class="col-md-4">
										<div class="form-group ">
											<label class="col-md-5 control-label">新学期学生注册：</label>
											<div class="col-md-7">
												<a class="btn grey" href="javascript:;" onclick="javascript:startNewTerm(this);" <#if (isDisabled??) && (isDisabled == 1)>disabled="disabled"</#if>>执行</a>
											</div>
										</div>
									</div>
								</div>
								<hr/>
						   	-->	
						    	<div class="row ">
						    		<!--/span-->
						    		<div class="col-md-6">
										<div class="form-group ">
											<label class="col-md-6 control-label">学期成绩编号：</label>
											<div class="col-md-6">
												<@commonMacros.multiInput "edit" "eduScrCreditDetailNo" vo.eduScrCreditDetailNo "required" 'rangelength="1, 64"'/>
											</div>
										</div>
									</div>
									<!--/span-->
								</div>
						    	<div class="row ">
						    		<!--/span-->
						    		<div class="col-md-6">
										<div class="form-group ">
											<label class="col-md-6 control-label">教学计划编号：</label>
											<div class="col-md-6">
												<@commonMacros.multiInput "edit" "eduSkdScheduleNo" vo.eduSkdScheduleNo "required" 'rangelength="1, 64"'/>
											</div>
										</div>
									</div>
									<!--/span-->
								</div><!--row-->
						    	<div class="row ">
						    		<!--/span-->
						    		<div class="col-md-6">
										<div class="form-group ">
											<label class="col-md-6 control-label">奖惩及违纪处理编号：</label>
											<div class="col-md-6">
												<@commonMacros.multiInput "edit" "eduGradRewardsNo" vo.eduGradRewardsNo "required" 'rangelength="1, 64"'/>
											</div>
										</div>
									</div>
									<!--/span-->
								</div><!--row-->								
								
								<div class="row ">
						    		<div class="col-md-6">
										<div class="form-group ">
											<label class="col-md-6 control-label">教学计划不受时间限制：</label>
											<div class="radio-list">
												<label class="radio-inline">
													<input type="radio" name="eduSkdScheduleIsNotLimitTime" value="1" <#if (vo.eduSkdScheduleIsNotLimitTime?? && vo.eduSkdScheduleIsNotLimitTime == 1)>checked</#if>/> 是
												</label>
												<label class="radio-inline">
													<input type="radio" name="eduSkdScheduleIsNotLimitTime" value="0" <#if (vo.eduSkdScheduleIsNotLimitTime?? && vo.eduSkdScheduleIsNotLimitTime == 0)>checked</#if>/> 否
												</label>
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="alert alert-danger">
												<strong>选择'是'，对于已分配学生的教学班，如果更换其教学计划，将无法同步改学生成绩。</strong>
										</div>
									</div>
								</div>								
								<div class="row ">
						    		<div class="col-md-6">
										<div class="form-group ">
											<label class="col-md-6 control-label">考务不受教学班处理状态限制：</label>
											<div class="radio-list">
												<label class="radio-inline">
													<input type="radio" name="eduExamClazzProgressStatusLimitOpen" value="1" <#if (vo.eduExamClazzProgressStatusLimitOpen?? && vo.eduExamClazzProgressStatusLimitOpen == 1)>checked</#if>/> 是
												</label>
												<label class="radio-inline">
													<input type="radio" name="eduExamClazzProgressStatusLimitOpen" value="0" <#if (vo.eduExamClazzProgressStatusLimitOpen?? && vo.eduExamClazzProgressStatusLimitOpen == 0)>checked</#if>/> 否
												</label>
											</div>
										</div>
									</div>
								</div>
								<div class="row ">
						    		<div class="col-md-6">
										<div class="form-group ">
											<label class="col-md-6 control-label">单一行政班的教学班名称：</label>
											<div class="radio-list">
												<label class="radio-inline">
													<input type="radio" name="eduTaskSelNaturalclazzcodeortitle" value="1" <#if (vo.eduTaskSelNaturalclazzcodeortitle?? && vo.eduTaskSelNaturalclazzcodeortitle == 1)>checked</#if>/> 行政班编号
												</label>
												<label class="radio-inline">
													<input type="radio" name="eduTaskSelNaturalclazzcodeortitle" value="2" <#if (vo.eduTaskSelNaturalclazzcodeortitle?? && vo.eduTaskSelNaturalclazzcodeortitle == 2)>checked</#if>/> 行政班名称
												</label>
											</div>
										</div>
									</div>
								</div>
								
								<hr/>								
							
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="submit" class="btn green-seagreen">保存</button>
										</div>
									</div>
								</div>
						</form>
											
					</div><!--div.table-toolebar-->
					
				</div>
			</div>
		</div>
		
		<jsplugininner>
		    <!-- BEGIN PAGE LEVEL PLUGINS -->
				<!-- form validate -->
				<script src="${contextPath}/resources/metronic/global/plugins/jquery-validation/js/jquery.validate.js" type="text/javascript"></script>
				<script src="${contextPath}/resources/metronic/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
				<script src="${contextPath}/resources/js/form_validate.js" type="text/javascript"></script>
			<!-- END PAGE LEVEL PLUGINS -->
		</jsplugininner>
		<jsinner>
		<script>
			// 开始新学期
			function startNewTerm(obj){
				var dictDeleteWarnStr = "您确定要开始新学期吗?";
					
					bootbox.confirm(dictDeleteWarnStr, function(result){
						if(!result) return;
						
						var $btn = $(obj);
						
						$btn.attr("disabled","disabled");
						
						var url = "${contextPath}/sys/profile/<@commonMacros.scope />/newterm/start"; 
						
						$.ajax({
							type:'POST',
							url: url,
							async:false, 
							cache:false, 
							data: {},
							dataType: "text", 
							success: function(msg){
								if(msg.toUpperCase() == 'OK') {
									bootbox.alert("成功。",function(){
										$btn.removeAttr("disabled");
										window.location.href="${contextPath}/sys/profile/<@commonMacros.scope />/all/list";
									});
								}
								else {
									$btn.removeAttr("disabled");
									bootbox.alert("操作失败！");
									window.location.href="${contextPath}/sys/profile/<@commonMacros.scope />/all/list";
								}
							},
							error: function(){
								$btn.removeAttr("disabled");
								bootbox.alert("server失败！");
								window.location.href="${contextPath}/sys/profile/<@commonMacros.scope />/all/list";
							}
						});
						
					});
			}		
		</script>
		</jsinner>
		

	</body>
</html>
