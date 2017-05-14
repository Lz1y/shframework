<#import "/macro/commonMacros.ftl" as commonMacros />

<html>
	<head>
		<title>${curMenu.title }</title>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/select2/select2.css"/>
		<link rel="stylesheet" type="text/css" href="${contextPath}/resources/metronic/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
	</head>
	<body>
		<@commonMacros.breadcrumbNavigation />
		<div class="row">
			<div class="col-md-12">
				<form name="listform" action="${contextPath}/sys/user/<@commonMacros.scope />/all/list" method="post"  class="form-horizontal" role="form" >
				    
				    <div class="portlet box green-seagreen">
						<div class="portlet-title">
							<div class="caption">
								搜索
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
							</div>
						</div>
						
						<div class="portlet-body">
							<div class="row ">
						    	<!--/span-->
								<div class="col-md-4">
									<div class="form-group ">
										<label class="col-md-4 control-label">用户名：</label>
										<div class="col-md-8">
											<input class="form-control" name="_su.username.1" type="text" 
												value="<#if page.pageSupport.paramVo.map?exists>${page.pageSupport.paramVo.map['_su.username']?if_exists?html}</#if>" />
										</div>
									</div>
								</div>
								<!--/span-->
								
								
								
								<!--/span-->
								<div class="col-md-8">
									<div class="form-group ">
										<div class="col-md-12">
											<button class="btn green-seagreen table-group-action-submit btn_search" type="submit" >搜索</button>
										</div>
									</div>
								</div>
								<!--/span-->
								
							</div>
						</div>
					</div>
			
				<div class="portlet box blue-steel">
					<div class="portlet-title">
						<div class="caption">
							${curMenu.title }
						</div>
					</div>
					
					<div class="portlet-body">
						<table class="table table-striped table-bordered table-hover" id="sample_1">
							<thead>
								<tr>
									<th class="table-checkbox">
										<input type="checkbox" class="group-checkable" data-set="#sample_1 .checkboxes"/>
									</th>
									<th>
										 ID
									</th>
									<th>
										 用户名
									</th>
									<th>
										 手机号码
									</th>
									<th>
										 电子邮箱
									</th>
									<th>
										 创建时间
									</th>
									<th>
										 用户状态
									</th>
									<th>
										 操作
									</th>
								</tr>
							</thead>
							<tbody>
								<#if page.list?exists>
									<#list page.list as obj>
										<tr>
											<td>
												 <input type="checkbox" class="checkboxes" value="${obj.id}"/>
											</td>
											<td>
												<@commonMacros.labelRowDetails obj.id 2/>
											</td>
											<td>
												<@commonMacros.labelRowDetails obj.username 2/>
											</td>
											<td>
												<@commonMacros.labelRowDetails obj.mobile 2/>
											</td>
											<td>
												<@commonMacros.labelRowDetails obj.email 2/>
											</td>
											<td>
												<@commonMacros.multiDate showStyle "createDate" obj.createDate />
											</td>
											<td>
												<@commonMacros.multiSelect staticlabel.status_name showStyle "status" obj.status?string />
											</td>
											<td>
												
												<a href="${contextPath}/sys/user/<@commonMacros.scope />/${obj.id}/dispatch${page.pageSupport.paramVo.parm}" class="btn btn-xs green-seagreen"> 角色分发 </a>
												<a href="javascript:void(0);"  class="btn default btn-xs green-seagreen"  data-target="#initpwd" data-toggle="modal" onclick="initPwd(${obj.id});">重置密码 </a>
												
											</td>
										</tr>
									</#list>
								</#if>
							</tbody>
						</table>
						<@commonMacros.pagination />
					</div>
				</div>
				</form>
			</div>
		</div>
		
		<div id="initpwd" class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
				<form id="pwdForm" class="valid_form form-horizontal" action="${contextPath}/sys/user/<@commonMacros.scope />/0/edit" method="post">
					<input type="hidden" name="type" value="initpwd"/>
					<input type="hidden" name="userId"/>
					<div class="modal-dialog modal-lg">
						
						<div class="portlet box green-seagreen margin-bottom-0">
							<div class="portlet-title">
								<div class="caption">重置密码</div>
								<button type="button" class="close margin-top-15" data-dismiss="modal" aria-hidden="true"></button>
							</div>
						
							<div class="portlet-body form">						
							<div class="modal-body">
							  		<div class="form-group">
										<label class="col-md-4 control-label">新密码：</label>
										<div class="col-md-6">
											<input type="password" name="pwd" class="form-control required illchar" rangelength='6,32' />
									 	</div>
								  	</div>
								  	<div class="form-group">
										<label class="col-md-4 control-label">确认新密码：</label>
										<div class="col-md-6">
											<input type="password" id="pwd_ack" name="pwd_ack" class="form-control required illchar" rangelength='6,32' equalToPwd='[name=pwd]' />
									 	</div>
								  	</div>
							</div>
							
							<div class="modal-footer">
								<button type="submit" class="btn green-seagreen">确定</button>
								<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
							</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		
		<div id="siteMeshJavaScript">
			<script>
				$('#confirm-delete').on('show.bs.modal', function(e) {
				    $(this).find('.danger').attr('href', $(e.relatedTarget).data('href'));
				    $('.debug-url').html('Delete URL: <strong>' + $(this).find('.danger').attr('href') + '</strong>');
				})
				function initPwd(id){
					var $form = $("form#pwdForm");
					var $userId = $form.find("input[name=userId]");
					$userId.val(id);
					var $pwd =  $form.find("input[name=pwd]");
					$pwd.val("1234567890");
					var $pwd_ack =  $form.find("input[name=pwd_ack]");
					$pwd_ack.val("1234567890");
					$form.valid();
				}
			</script>
		</div>
	</body>
</html>
