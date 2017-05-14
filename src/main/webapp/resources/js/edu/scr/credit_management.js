/**
* 学分折算及绩点管理
* 依赖： common_util.js
* 			bootbox.js
*/

	var startTacticsTip="您正在启用一套学分折算及绩点分值政策，启用本套政策会将其他正在启用中的政策作废。您确认启用该政策吗？";
	var delTacticsTip="您正在删除一套学分折算及绩点分值政策。您确认要删除该政策吗？";
	
				
				/**删除*/
				function deleteCredit(obj, urlroot, id, param){
					bootbox.confirm(delTacticsTip, function(result){
						if(!result){
						    return;
						}
						var $btn = $(obj);
						var url =  urlroot + "/" + id + "/delete" + param; 
						$btn.button("loading");
						window.location.href = url;
					});//confirm
					
				}
				
				/**启动*/
				function startCredit(obj, urlroot, id, param){
					bootbox.confirm(startTacticsTip, function(result){
						if(!result){
						    return;
						}
						var $btn = $(obj);
						var url =  urlroot + "/" + id + "/edit";
						if (typeof(param)=='string' && param.length>0){
							url += param + "&type=start";
						} else {
							url += "?type=start";
						}
						$btn.button("loading");
						window.location.href = url;
					});//confirm
				}
				
				
				/**修改备注*/
				function editRemark(obj, urlroot, id, param){
						var $btn = $(obj);
						var url =  urlroot + "/" + id + "/edit"; 
						if (typeof(param)=='string' && param.length>0){
							url += param + "&type=remark";
						} else {
							url += "?type=remark";
						}
						$btn.button("loading");
						window.location.href = url;
				}
				
				//
				var credit_point_index = 0; 
				function setCreditPointIndex(index){
					credit_point_index = index;
				}
				
				function  appendPoint(){
					var $area = $("#creditPointArea");
					var $temp = $("#creditPointDemo").clone(true);
					
					if (credit_point_index>0){//在开头增加一个<hr>
						$temp.prepend("<hr>")
					} else { //去掉“移除按钮”
						$temp.find(".remove_point_btn").remove();
					}
					//对 $temp进行处理，对所有域增加id 形如： name_index 这种
					$temp.find("input[name=point]").attr("id", "point_"+credit_point_index);
					$temp.find("input[name=scoreLowerLimit]").attr("id", "scoreLowerLimit_"+credit_point_index);
					$temp.find("input[name=scoreUpperLimit]").attr("id", "scoreUpperLimit_"+credit_point_index);
					$temp.find("input[name=scoreUpperLimit]").attr("greaterThan", "[name=scoreLowerLimit]#scoreLowerLimit_"+credit_point_index);
					$temp.find("input[name=retestCategorys]").attr("id", "retestCategory_"+credit_point_index);
					
					$area.append($temp.html());
					credit_point_index ++;
				}
				
				function removePoint(obj){
					var $btn = $(obj);
					$btn.button("loading");
					var $formbody= $btn.closest(".form-body ");
					//移除上方可能存在的<hr>标签,如果没有，则移除下方的(支持删除第一个节点)
					if ($formbody.prev("hr").size()>0){
						$formbody.prev("hr").remove();
					} else {
						$formbody.next("hr").remove();
					}
					//找到最近的 .form-body ，然后移除它
					$formbody.remove();
				}
				
				
				function checkBeforeSubmit($form){
					//依次校验 学分具体设置
					return checkCategory($form) && checkScoreLimit($form) ;
				}
				
				var scoreLimitTip="百分制上限必须连续且不能重叠";
				var categoryTip = "等级制补考不及格项目，必须包含在等级制科目类别中";
				var categoryRepeatTip = "等级制科目类别重复，请检查";
				var socreLowestTip ="最后一个绩点分值百分制范围下限必须为0" 
				
				//百分制上限必须连续，不能重叠，不能不连续。
				function checkScoreLimit($form){
					var $points = $form.find("#creditPointArea").find(".form-body");
					var scoreLowerLimitArr = new Array();
					var scoreUpperLimitArr = new Array();
					$points.each(function(i, one){
						scoreLowerLimitArr.push($(one).find("input[name='scoreLowerLimit']").val().trim() );
						scoreUpperLimitArr.push($(one).find("input[name='scoreUpperLimit']").val().trim() );
					})
					
					var length = scoreLowerLimitArr.length;
					if (scoreLowerLimitArr[length-1] != 0){
						bootbox.alert(socreLowestTip);
						return false;
					}
					for (i=0; i<length-1; i++){
						if (scoreLowerLimitArr[i] != scoreUpperLimitArr[i+1]){
							bootbox.alert(scoreLimitTip);
							return false;
						}
					}
					return true;
				}
				
				//等级制补考不及格项目，必须包含在等级制科目类别中
				// 且 等级制科目类别 不能重复
				function checkCategory($form){
					var retestCategory = $.trim($form.find("#creditArea").find("input[name=retestCategory]").val());
					var categoryArr = new Array(); 
					$form.find("#creditPointArea").find("input[name=retestCategorys]").each(function(i, one){
						categoryArr.push($.trim(one.value));
					})
					//判断是否重复
					if (isArrayHasRepeat(categoryArr)){
						bootbox.alert(categoryRepeatTip);
						return false;
					}
					//判断不及格类别是否在所有类别中
					if (categoryArr.indexOf(retestCategory)<0){
						bootbox.alert(categoryTip);
						return false;
					}
					return true;
				}
				