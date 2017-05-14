<#--部门新增/修改-->

	<#import "/macro/commonMacros.ftl" as commonMacros />
					<div class="portlet box green-seagreen margin-bottom-0">
						<div class="portlet-title">
							<div class="caption">${curMenu.title?if_exists}</div>
							<button type="button" class="close margin-top-15" data-dismiss="modal" aria-hidden="true"></button>
						</div>
						
						<div class="portlet-body form">	
		
	  	<div class="modal-body">
	  	<#if dict?exists>
			<form class="form-horizontal valid_form" action="${contextPath}/dict/edu/common/department/<@commonMacros.scope />/${dict.parentId}/save" method="post" name="dictForm" id="dictForm" >
			<input type="hidden" name="parentId" value="${dict.parentId?if_exists}" />
			
	  		<div class="form-group">
				<label class="col-md-4 control-label">上级部门：</label>
				<div class="col-md-6">
			 		<@commonMacros.multiInput "read" "" parent.title />
			 	</div>
		  	</div>
		  	<div class="form-group">
				<label class="col-md-4 control-label">编码：</label>
				<div class="col-md-6">
			 		<input class="form-control required" type="text" maxlength="25" id="code" name="code" value="${dict.code?if_exists?html}" />
			 	</div>
		  	</div>
		  	<div class="form-group">
				<label class="col-md-4 control-label">名称：</label>
				<div class="col-md-6">
			 		<input class="form-control required" type="text" maxlength="25" id="title" name="title" value="${dict.title?if_exists?html}" />
			 	</div>
		  	</div>
		  	<div class="form-group">
				<label class="col-md-4 control-label required">校区：</label>
				<div class="col-md-6">
			 		<@commonMacros.multiSelect campus "edit" "campusId" dict.campusId "required" />
			 	</div>
		  	</div>
		  	<#if showStyle=="add">
		  	<div class="form-group">
				<label class="col-md-4 control-label">类别：</label>
				<div class="col-md-6">
					<div class="radio-list">
						<#if parent.type?default(0) == 0>
						<label class="radio-inline">
							<input type="radio" name="type" value="0" <#if dict.type?default(1)==0>checked</#if> /> <@commonMacros.multiSelect staticlabel.departmentType "list" ""  0 />
						</label>
						<label class="radio-inline">
							<input type="radio" name="type" value="1" <#if dict.type?default(1)==1>checked</#if>/> <@commonMacros.multiSelect staticlabel.departmentType "list" ""  1 />
						</label>
						<label class="radio-inline">
							<input type="radio" name="type" value="2" <#if dict.type?default(1)==2>checked</#if>/> <@commonMacros.multiSelect staticlabel.departmentType "list" ""  2 />
						</label>
						</#if>
						<#if parent.type?default(0) == 1>
						<label class="radio-inline">
							<input type="radio" name="type" value="1" <#if dict.type?default(1)==1>checked</#if>/> <@commonMacros.multiSelect staticlabel.departmentType "list" ""  1 />
						</label>
						<label class="radio-inline">
							<input type="radio" name="type" value="2" <#if dict.type?default(1)==2>checked</#if>/> <@commonMacros.multiSelect staticlabel.departmentType "list" ""  2 />
						</label>
						</#if>
						<#if parent.type?default(0) == 2>
						<label class="radio-inline">
							<input type="radio" name="type" value="2" checked/> <@commonMacros.multiSelect staticlabel.departmentType "list" ""  2 />
						</label>
						</#if>
					</div>
				</div>
		  	</div>
		  	</#if>
		   	</form>
		   	
		<#else>
		<div>抱歉，该节点不允许添加子节点...</div>
		</#if>
		   	
		</div>
		
	<div class="modal-footer">
		<#if dict?exists>
		<button type="button" class="btn green-seagreen" name="btnok" onclick="javascript:saveInfo(this);">保存</button>
		<button type="button" class="btn default" data-dismiss="modal" aria-hidden="true">取消</button>
		<#else>
		<button type="button" class="btn green-seagreen" data-dismiss="modal" aria-hidden="true">关闭</button>
		</#if>
	</div>
	
	<script src="${contextPath}/resources/metronic/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${contextPath}/resources/metronic/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
	<script src="${contextPath}/resources/js/form_validate.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	
		function saveInfo(obj){
			<#if dict?exists && dict.id?default(0) gt 0>
				var url = "${contextPath}/dict/edu/common/department/<@commonMacros.scope />/${dict.id}/save";
				var active = "修改";
			<#else>
				var url = "${contextPath}/dict/edu/common/department/<@commonMacros.scope />/0/save";
				var active = "添加";
			</#if>
			if (!$("#dictForm").valid()){
				return;
			}
			var $btn = $(obj);
			var data = $("#dictForm").serialize();
			$btn.button("loading");
			$.ajax({
				type:'POST',
				url: url,
				async:false, 
				cache:false, 
				data: data,
				dataType: "text", 
				success: function(msg){
					if(msg.toUpperCase() == 'OK') {
						divisionPageRefresh();
					} else if (msg.toUpperCase() == 'DUPLICATEKEY'){
						bootbox.alert("您输入的代码/名称重复，无法保存，请重新输入");
					}else {
						bootbox.alert(active + "节点失败！");
					}
					$btn.button("reset");
				},
				error: function(){
					bootbox.alert(active + "节点失败！");
					$btn.button("reset");
				}
			});
		}
</script>