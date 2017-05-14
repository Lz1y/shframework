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
							    <#if majorfield?exists>
							    	<div class="form-group">
										<label class="col-md-3 control-label">专业</label>
										<div class="col-md-9"><p class="form-control-static">
											<#if major?exists>
									    	<#list major?keys as key>
									    		<#if key == majorfield.majorId?default(0)?string>
									    			<@commonMacros.showDictTitle major[key]/>
									    		</#if>
									    	</#list>
									    	</#if>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">专业方向编码</label>
										<div class="col-md-9"><p class="form-control-static">${majorfield.code?if_exists}</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">专业方向名称</label>
										<div class="col-md-9"><p class="form-control-static">${majorfield.title?if_exists}</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">负责人</label>
										<div class="col-md-9"><p class="form-control-static"><@commonMacros.multiSelect employeename "list" "principal" "${majorfield.principal?default(0)}"/></div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">显示次序</label>
										<div class="col-md-9"><p class="form-control-static"><@commonMacros.multiInput "list" "title" majorfield.priority /></div>
									</div>
									
									
									<div class="form-group">
										<label class="col-md-3 control-label">选用状态</label>
										<div class="col-md-9"><p class="form-control-static">
											 <@commonMacros.multiSelect staticlabel.status "list" "status" "${majorfield.status?default(1)}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3 control-label">执行标准</label>
										<div class="col-md-9"><p class="form-control-static">
											<@commonMacros.multiSelect staticlabel.standard "list" "standard" "${majorfield.standard?default(-1)}"/>
										</div>
									</div>
									
								<#else>
									抱歉，没有找到符合条件的数据！
								</#if>	
							</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button type="button" class="btn green-seagreen button-previous"  onclick="location.href = '${contextPath}/dict/edu/common/majorfield/<@commonMacros.scope />/all/list${pageSupport.paramVo.parm?if_exists?html}'">返回</button>
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
