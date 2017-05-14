<div class="page-header navbar navbar-fixed-top">
	<!-- BEGIN HEADER INNER -->
	<div class="page-header-inner">
		<!-- BEGIN LOGO -->
		<div class="page-logo">
			<a href="${contextPath}/home">
				<img src="${contextPath}/resources/logo.png" alt="logo" class="margin-top-10"/>
			</a>
			<div class="menu-toggler sidebar-toggler hide">
				<!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
			</div>
		</div>
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		
		<!--角色选则开始-->
		<div class="top-menu pull-left">
			<ul class="nav navbar-nav pull-right" id="curRoleS">
				<#include "/WEB-INF/pages/comn/userresrole.ftl" />			
			</ul>
		</div>
		<form id="changeRoleForm" action="${contextPath}/crole" method="post">
			<input type="hidden" id="selectRid" name="rid"/>
		</form>		
		<!--角色选则结束-->
		
		<!-- BEGIN TOP NAVIGATION MENU -->
		<div class="top-menu">
			
			<ul class="nav navbar-nav pull-right" id="notifyul">
			
				<li class="dropdown-user "><!-- 当前时间不允许点击 ，去掉 dropdown-->
					<div class="dropdown-toggle">
						<span class="username username-hide-on-mobile"  id="sys_cur_time">
						</span>
					</div>
				</li>

				<li class="dropdown dropdown-extended dropdown-notification" id="header_notification_bar">
					<a id="a_notify_num" href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					</a>
					<ul class="dropdown-menu">
						<li id="li_notify_num" class="external">
						</li>
						<li>
						</li>
					</ul>
				</li>
				
				<li class="dropdown dropdown-extended dropdown-inbox" id="header_inbox_bar"> 
					<a id="a_notice_num" href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"> 
					</a>
					<ul class="dropdown-menu">
						<li id="li_notice_num" class="external">
						</li>
						<li>
						</li>
					</ul>
				</li>
				
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<li class="dropdown dropdown-user">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<#--<img alt="" class="img-circle hide1" src="${vmRootPath}default.jpg"/>--><#--换成真实头像-->
					<img alt="" class="img-circle hide1" src="${contextPath}/resources/head.png"/>
					<span class="username username-hide-on-mobile">
						<#if curUser?? && curUser.username??>
						${curUser.username}
						</#if> 
					</span>
					<i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
						<#if curUser?? && curUser.employee.departmentId?exists && division[curUser.employee.departmentId+""]?exists>
							<li><a href="javascript:void(0);">
								部门：${division[curUser.employee.departmentId+""].title }</a>
							</li>
						</#if>
						<#-- -->
						<#if curUser?? && curUser.employee.postTypeId?exists && posttype[curUser.employee.postTypeId+""]?exists>
							<li><a href="javascript:void(0);">
								职务：${posttype[curUser.employee.postTypeId+""].title }</a>
							</li>
						</#if>
						<#if loginType?? && loginType == 1>
						<li>
							<a data-target="#cpwd" data-toggle="modal">
								<i class="fa icon-key"></i> 修改密码
							</a>
						</li>
						</#if>
						<li>
							<a data-target="#crole" data-toggle="modal">
								<i class="fa icon-users"></i> 切换角色
							</a>
						</li>
						<#if loginType?? && loginType == 1>
						<li>
							<a href="${contextPath}/logout">
							<i class="fa fa-power-off"></i> 退出 </a>
						</li>
						</#if>
					</ul>
				</li>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
		</div>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END HEADER INNER -->
</div>

<div style="display: none;">
	<div id="statebar">
		<span id="workStyle"></span>；
		<span id="connectorCount"></span>；
		<span id="usedMemory"></span>；
		<span id="freeMemory"></span>；
		<span id="totalMemory"></span>；
		<span id="maxMemory"></span>；
		<span id="startup"></span>
	</div>
	
	<div id="logbox">
	</div>
	
	<div id="toolbar" >
		<input maxlength="200" id="inputbox" class="inputbox" onkeypress="return onSendBoxEnter(event);" type="hidden" ></input>
	</div>
	
	<div id="login">
		<input type="hidden" class="inputbox" maxlength="50" id="loginName"  />
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		getCurrTime();
		setInterval("getCurrTime()",1000*60);//1000为1秒钟
	});
	
	function getCurrTime(){
		var time = new Date();
		$("span#sys_cur_time").text(formatDate(time, "yyyy年MM月dd日 星期w hh:mm"));
	}
	
	function showResRole(resId){
		var $content  = $('#curRoleS');
		$.ajax({
		    type:'POST',
		    async: false,
		    url: "${contextPath}/showrole",
		    dataType: 'html',
		    data: {'resId': resId},
		    success: function(data){
		        $content.html(data);
		    },
		    error: function() {
		        bootbox.alert("操作失败！");
		    }
		});				
	}	
	
	function changeRole(rid){
		bootbox.confirm("是否确认切换角色？", function(result){
			if(!result){ 
				<#if curMenu??>showResRole('${curMenu.id}');<#else>return;</#if>
			}else{
				$("#selectRid").val(rid); 
				$("#changeRoleForm").submit();
			}
		});		
	}
</script>