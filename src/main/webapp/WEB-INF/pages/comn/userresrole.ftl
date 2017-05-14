<#if userresrole?? && userresrole?size gt 0>
	<li class="dropdown-user ">
		<div class="dropdown-toggle">
			<strong class="username username-hide-on-mobile" style="letter-spacing:1px;padding-left:20px">您当前在&nbsp;<span class="font-yellow-gold" style="letter-spacing:1px"><#if curM??>${curM.title?html}</#if></span>&nbsp;中角色为</strong>
		</div>
	</li>
	
	<li class="dropdown dropdown-user">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true" aria-expanded="false">
			<span class="username username-hide-on-mobile font-yellow-gold">
				<#list userresrole as res>
					<#if curRc??><#list res.roles as key><#if curRc[res.rule]?? && key.code=curRc[res.rule].rCode>${key.title}</#if></#list></#if>
				</#list>
			</span>
			<#list userresrole as res><#if res.roles?size gt 1><i class="fa fa-angle-down"></i></#if></#list>
		</a>
		<#list userresrole as res>
		<#if res.roles?size gt 1>
		<ul class="dropdown-menu">
			<#if curRc??>
		    	<#list res.roles as key>
					<#if curRc[res.rule]?? && key.code!=curRc[res.rule].rCode>
						<li><a href="javascript:changeRole('${key.id}');">${key.title}</a></li>		    		
					</#if>			
				</#list>
			</#if>
		</ul>
		</#if>
		</#list>
	</li>
</#if>