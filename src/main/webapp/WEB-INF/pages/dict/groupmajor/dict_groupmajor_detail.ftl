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
					<div class="portlet box blue-steel">
						<div class="portlet-title">
							<div class="caption">
								${curMenu.title?if_exists}
							</div>
						</div>
						<div class="portlet-body form">
							<div class="form-horizontal form-body">
							    <#if groupmajor?exists>
							    
							    <div class="form-group">
										<label class="col-md-3 control-label">专业目录</label>
										<div class="col-md-9"><p class="form-control-static">
											<#if categorymajor?exists>
									    	<#list categorymajor?keys as key>
									    		<#if key == groupmajor.categoryId?default(0)?string>
									    			<@commonMacros.showDictTitle categorymajor[key] />
									    		</#if>
									    	</#list>
									    	</#if>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">专业大类编码</label>
										<div class="col-md-9"><p class="form-control-static">${groupmajor.code?if_exists}</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">专业大类名称</label>
										<div class="col-md-9"><p class="form-control-static">${groupmajor.title?if_exists}</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">选用状态</label>
										<div class="col-md-9"><p class="form-control-static">
											<@commonMacros.multiSelect staticlabel.status "list" "status" "${groupmajor.status?default(1)}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">执行标准</label>
										<div class="col-md-9"><p class="form-control-static">
											<@commonMacros.multiSelect staticlabel.standard "list" "standard" "${groupmajor.standard?default(-1)}"/>
										</div>
									</div>
								
								<#else>
								抱歉，没有找到符合条件的数据！
							</#if>	
							</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="button" class="btn green-seagreen button-previous" onclick="location.href = '${contextPath}/dict/edu/common/groupmajor/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
										</div>
									</div>
								</div>
							
							<!-- END FORM-->
							
						</div>
					</div>
				</div>
			</div>
		<!-- END PAGE CONTENT-->
	</body>
</html>
