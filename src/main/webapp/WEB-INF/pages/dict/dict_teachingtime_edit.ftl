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
				<div class="portlet box green-seagreen">
					<div class="portlet-title">
						<div class="caption">
							教学时间
						</div>
					</div>
					<div class="portlet-body">
						
						<form name="teachingtimeForm" method="post" action="${contextPath}/dict/edu/task/teachingtime/<@commonMacros.scope />/0/save">
							<table class="table table-striped table-bordered table-hover table_align_center">
								<thead>
									<tr role="row" class="heading">
										<th>
											 编号
										</th>
										<th>
											名称
										</th>
										
										<#if weekDayVOList??>
										<#list weekDayVOList as weekDayVO >
											<th>
												<input type="checkbox"  onclick="checkCol(${weekDayVO.weekDay}, this)" />${weekDayVO.showWeekDay}
											</th>
										</#list>
									</#if> 
									</tr>
								</thead>
								<tbody>
									<#if periodList??>
										<#list periodList as period >
											<tr>
												<td>
													<input type="checkbox"  onclick="checkRow(${period.id}, this)" />${period.id}
												</td>
												<td>
													${period.title}
												</td>
												<#if weekDayVOList??>
													<#list weekDayVOList as weekDayVO >
														<td>
															<input type="checkbox" name="weekDayVO_period" class="period_${period.id} week_${weekDayVO.weekDay}" value="${weekDayVO.weekDay}_${period.id}" 
																<#list containteachingTimeList as contain><#if (contain.weekDay == weekDayVO.weekDay ) && (contain.periodId == period.id)>checked </#if></#list>
															/>
														</td>
													</#list>
												</#if>
											</tr>	
										</#list>
									</#if>
								</tbody>
							</table>
							<button type="submit" class="btn green-seagreen" > 保存 </button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- END PAGE CONTENT-->
		<jsplugininner>
 			<script src="${contextPath}/resources/metronic/admin/pages/scripts/table-managed.js"></script>
		</jsplugininner>
		<jsinner>
			<script>
				jQuery(document).ready(function() {       
					TableManaged.init();

				});
				
				function checkAll(elem){
					changeCheckBox("input[name='weekDayVO_period']:enabled", elem.checked);
				}
				
				function checkCol(weekDay, ele){
					changeCheckBox("input[name='weekDayVO_period']:enabled.week_"+ weekDay, ele.checked);
				}
				function checkRow(periodId, ele){
					changeCheckBox("input[name='weekDayVO_period']:enabled.period_" + periodId, ele.checked);
				}
				
				function changeCheckBox(selector, isChecked){
					if(isChecked) {
						$(selector).each(function(){
							$(this).prop("checked", true);
							this.parentNode.setAttribute("class", "checked");
						});
					} else {
						$(selector).each(function(){
							$(this).prop("checked", false);
							this.parentNode.setAttribute("class", "");
						});
					}
				}
				
				if(${success!} == 1){
					bootbox.alert("保存成功");
				}else if(${success!} == 0){
					bootbox.alert("保存失败");
				}
				
			</script>
		</jsinner>
		</div>
	</body>
</html>

