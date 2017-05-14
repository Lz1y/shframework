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
							    <#if major?exists>
							    <div class="form-group">
										<label class="col-md-3 control-label">专业大类</label>
										<div class="col-md-9"><p class="form-control-static">
											<#if groupmajor?exists>
									    	<#list groupmajor?keys as key>
									    		<#if key == major.groupId?default(0)?string>
									    			<@commonMacros.showDictTitle groupmajor[key]/>
									    		</#if>
									    	</#list>
									    	</#if>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">专业名称</label>
										<div class="col-md-9"><p class="form-control-static"><@commonMacros.multiSelect major "list" "" major.pid /></div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">校定专业编码</label>
										<div class="col-md-9"><p class="form-control-static">${major.code?if_exists}</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">校定专业名称</label>
										<div class="col-md-9"><p class="form-control-static">${major.title?if_exists}</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">开办时间</label>
										<div class="col-md-9"><p class="form-control-static">
											<#if major.startDate?exists>${major.startDate?string('yyyy-MM-dd')}</#if>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">编码类型</label>
										<div class="col-md-9"><p class="form-control-static">
											<@commonMacros.multiSelect staticlabel.majorType "list" "majorType" "${major.type?default(1)}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">沿革信息</label>
										<div class="col-md-9"><p class="form-control-static">${major.reformationInfo?if_exists}</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">描述</label>
										<div class="col-md-9"><p class="form-control-static">${major.description?if_exists}</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">选用状态</label>
										<div class="col-md-9"><p class="form-control-static">
											<@commonMacros.multiSelect staticlabel.status "list" "status" "${major.status?default(1)}"/>
												
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">执行标准</label>
										<div class="col-md-9"><p class="form-control-static">
											<@commonMacros.multiSelect staticlabel.standard "list" "standard" "${major.standard?default(-1)}"/>
										</div>
									</div>
								
								<#else>
								抱歉，没有找到符合条件的数据！
							</#if>	
							</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="button" class="btn green-seagreen button-previous"  onclick="location.href = '${contextPath}/dict/edu/common/majorparent/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
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
