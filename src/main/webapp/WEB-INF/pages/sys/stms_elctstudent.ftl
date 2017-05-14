<#import "/macro/commonMacros.ftl" as commonMacros />

<div class="portlet box blue-steel margin-bottom-0">
						<div class="portlet-title">
							<div class="caption">竞争选课学生列表</div>
							<button type="button" class="close margin-top-15" data-dismiss="modal" aria-hidden="true"></button>
						</div>
						
						<div class="portlet-body form">	
<div class="modal-body">
	<form class="form-horizontal valid_form"  method="post" name="editForm" id="editForm" 
		action="#">
		
		<div class="row">		
			<div class="col-md-12">
				<div class="form-group ">
					<div class="col-md-12">
						<p class="form-control-static" style="font-size:13px;">
							<#if (studentList?? && studentList?size > 0)>
							<#list studentList as student>
								<#if ((student_index + 1)%8 == 0)>
									${student}<br/><br/>
								<#else>
									<#if !student_has_next>
				    					${student}
				    				<#else>
				    					${student}、
				    				</#if>
								</#if>
							</#list>
							<#else>
								无
							</#if>
						</p>
					</div>
				</div>
			</div>
		</div>
	</form>	
</div>
	
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">确定</button>
</div>

</div>
</div>
	
