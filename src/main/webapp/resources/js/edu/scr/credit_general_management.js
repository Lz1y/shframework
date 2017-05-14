/**
* 总评成绩公式管理
* 依赖： common_util.js
* 			bootbox.js
*/
	var delFormulaTip="您正在删除一套总评成绩公式。您确认要删除该公式吗？";
	
				
				/**删除*/
				function deleteGeneral(obj, urlroot, id, param){
					bootbox.confirm(delFormulaTip, function(result){
						if(!result){
						    return;
						}
						var $btn = $(obj);
						var url =  urlroot + "/" + id + "/delete" + param; 
						$btn.button("loading");
						window.location.href = url;
					});//confirm
					
				}
				
				
				var credit_detail_index = 0; 
				function setCreditDetailIndex(index){
					credit_detail_index = index;
				}
				
				//增加一个默认的总评成绩，操作基本与appendDetail相同，但要做一些特殊的操作。
				function initDetail(){
					var $area = $("#creditDetailArea");
					var $temp = $("#creditDetailDemo").clone(true);
					//去掉“移除按钮”
					$temp.find(".remove_btn").remove();
					
					//对 $temp进行处理，对所有域增加id 形如： name_index 这种
					$temp.find("input[name=detailTitle]").attr("id", "title_"+credit_detail_index).val("总评成绩");
					$temp.find("input[name=priority]").attr("id", "priority_"+credit_detail_index).val(8);
					$temp.find("input[name=ratio]").attr("id", "ratio_"+credit_detail_index).val(100);
					$temp.find("input[name=fullCredit]").attr("id", "fullCredit_"+credit_detail_index).val(100);
					$temp.find("select[name=detailType]").attr("id", "type_"+credit_detail_index).val(3);
					//FIXME 修改时存在风险--总评被改为其他的类型，应当想办法将其固定下来
					$area.append($temp.children());
					credit_detail_index ++;
					
				}
				
				/**刷新 成绩构成 类型的 内容
				 *  背景：不同修习类型下，显示的内容不同
				**/
				function refreshCreditDetailTypeContent(studyTypeSelector, cdTypeSelector){
					var studyType = Number($(studyTypeSelector).val());
					var $cdType = $(cdTypeSelector);
					//总评项（3）总是要隐藏的， 自定义（0）不做操作
					$cdType.find("option[value=3]").hide();
					opOldValue($cdType, studyType, "store");
					if (studyType==0){//新修
						$cdType.find("option[value=1]").show();
						$cdType.find("option[value=2]").show();
						$cdType.find("option[value=6]").hide();
					} else if (studyType ==1){ //重修
						$cdType.find("option[value=1]").hide();
						$cdType.find("option[value=2]").hide();
						$cdType.find("option[value=6]").show();
					}
					opOldValue($cdType, studyType, "restore");
				}
				/**
				 * 操作旧数据
				 * @param $selector
				 * @param studyType
				 * @param operation
				 */
				function opOldValue($selector, studyType, operation){
					var storeName = "";
					var reStoreName = "";
					if (studyType == 0){//新修
						storeName = "restorevalue";//将现在的值保存到这里
						reStoreName = "storevalue";//恢复这里的值
					} else {//重修
						storeName = "storevalue";//将现在的值保存到这里
						reStoreName = "restorevalue";//恢复这里的值
					}
					$selector.each(function(){
						var $this = $(this);
						if ("store" == operation){
							var tempVal = $this.val();
							$this.attr(storeName, tempVal);
						} else if ("restore" == operation){
							var tempVal = $this.attr(reStoreName);
							if ($.trim(tempVal) != ""){
								$this.val(tempVal);
							}
						}//if-else
						
					});
				}
				
				
				var maxDetailNum = 8;
				function  appendDetail(){
					var $area = $("#creditDetailArea");
					//判断现有的条数：
					if ($area.find("div.form-body").size() >= maxDetailNum){
						bootbox.alert("成绩构成条数已经达到上限，不允许添加");
						return;
					}
					
					var $temp = $("#creditDetailDemo").clone(true);
					
					if (credit_detail_index>0){//在开头增加一个<hr>
						$temp.prepend("<hr>")
					} else { //去掉“移除按钮”
						$temp.find(".remove_btn").remove();
					}
					//对 $temp进行处理，对所有域增加id 形如： name_index 这种
					$temp.find("input[name=detailTitle]").attr("id", "title_"+credit_detail_index);
					$temp.find("input[name=priority]").attr("id", "priority_"+credit_detail_index);
					$temp.find("input[name=ratio]").attr("id", "ratio_"+credit_detail_index);
					$temp.find("input[name=fullCredit]").attr("id", "fullCredit_"+credit_detail_index);
					$temp.find("select[name=detailType]").attr("id", "type_"+credit_detail_index);
					
					$area.append($temp.html());
					credit_detail_index ++;
				}
				
				function removeDetail(obj){
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
					//依次校验 成绩构成的类型除了自定义外不得重复 以及 权重之和为100
					return checkType($form) && checkRatio($form) ;
				}
				
				var noCreditDetailTip = "没有填写成绩构成，请至少添加一项成绩构成";
				var typeNoOverallTip = "您必须设置总评成绩！";
				var typeRepeatTip = "非自定义类型的项，每种类型至多只能有一个";
				var typeTitleDiffTip ="非自定义类型的项，标题应与类型名称一致";
				var titleRepeatTip = "成绩构成名称重复";
				var zpRatioTip = "总评的权重必须为100";
				var ratioTip = "您的成绩构成权重不符合100%！";
				var priorityRepeatTip = "成绩构成的顺序号不得重复";
				
				var studyTypeNewTip="修习类型为“新修”时，不得添加重修考成绩项";
				var studyTypeRevampTip="修习类型为“重修”时，不得添加期中、期末成绩项";
				var studyTypeRevampNoRETip="修习类型为“重修”时，必须要有重修考项";
				
				var middleRequiredTip = "根据考务信息需要设置期中考试！";
				var finalRequiredTip = "根据考务信息需要设置期末考试！";
				
				var stuCreditChangeTip = "您修改了成绩构成，对之前录入的成绩带来影响，请到成绩录入页面重新保存成绩，以便进行成绩的重新计算，否则会导致总评成绩不准确";//FIXME!
				
				
				var scrCdKeys = ["期中成绩", "期末成绩",  "重修考成绩" ];//成绩构成保留关键字,自定义的项不得使用这些名字 
				var keyInCustomTip = "自定义类型的项中，不能出现“{0}”";
				
				//除了自定义外不得重复,且必须有“总评成绩”
				//总评成绩、期中、期末的类型和title必须一致。
				function checkType($form){
					var $details = $form.find("#creditDetailArea").find(".form-body");
					var titleArr = new Array(); 
					var typeArr = new Array();
					var priorityArr = new Array();
					
					var isContainKeyInCustom = false;
					var isTitleDiff=true;//非自定义类型的项，标题应与类型名称是否一致
					
					//教学班成绩构成 特殊情况判断
					var middleFlag = false; //期中提交状态
					var middleFlagValue = $(":input.middleFlag").val();
					if (typeof(middleFlagValue)!="undefinde" && middleFlagValue == "1"){
						middleFlag = true;
					}
					if ($details.size()<=1){//rwz add 2016-1-7 成绩构成至少要有一项
						bootbox.alert(noCreditDetailTip);
						return false;
					}
					$details.each(function(i, one){
						if (!isTitleDiff || isContainKeyInCustom){
							return;
						}
						var $one = $(one);
						var title = $.trim($one.find("input[name='detailTitle']").val())
						var type = $one.find(":input[name=detailType]").val();
						var priority = $one.find("input[name=priority]").val();
						
						if (typeof(type)!="undefinde"){
							var isTitleCheck = $.trim($one.find("input.isTitleCheck").val()) != "false";//title是否检查
							if(isTitleCheck
									&& type != "3"){ 
								if (type == "0" && scrCdKeys.indexOf(title) >= 0){
									isContainKeyInCustom = true;
									keyInCustomTip = keyInCustomTip.replace("\{0\}", title);
									return;
								} else if (type != "0"
									&& $.trim($one.find("select[name=detailType] option:selected").text()) != title){
								isTitleDiff = false;
								return;
								}//if
							}
							if (type != "0"){
								typeArr.push(type);
							}
						}//if
						titleArr.push(title);
						priorityArr.push(priority);
					});//each
					
					if (isContainKeyInCustom){
						bootbox.alert(keyInCustomTip);
						return false;
					}
					if (!isTitleDiff){
						bootbox.alert(typeTitleDiffTip);
						return false;
					}
					
					if (typeArr.indexOf("3") < 0){
						bootbox.alert(typeNoOverallTip);
						return false;
					}
					
					//判断标题是否重复
					if (isArrayHasRepeat(titleArr)){
						bootbox.alert(titleRepeatTip);
						return false;
					}
					
					//判断类型是否重复
					if (isArrayHasRepeat(typeArr)){
						bootbox.alert(typeRepeatTip);
						return false;
					}
					
					//判断标题是否重复
					if (isArrayHasRepeat(priorityArr)){
						bootbox.alert(priorityRepeatTip);
						return false;
					}
					
					var studyType = $(":input.studyType").val();//修习类型
					//add  by rwz 区别新修or重修下的不同：
					if (studyType == "0" && typeArr.indexOf("6") >= 0){//新修
						bootbox.alert(studyTypeNewTip);
						return false;
					} else if (studyType == "1" 
							&& ( typeArr.indexOf("1") >= 0 || typeArr.indexOf("2") >= 0)){//重修
						bootbox.alert(studyTypeRevampTip);
						return false;
					} else if (studyType == "1" 
							&&  typeArr.indexOf("6") == -1){//重修 无重修考
						bootbox.alert(studyTypeRevampNoRETip);
						return false;
					}
					
					//add by rwz 判断是否需要 期中/期末成绩
					var middleRequiredFlag = false;
					var finalRequiredFlag = false;
					if (middleRequiredFlag && typeArr.indexOf("1") == -1){
						bootbox.alert(middleRequiredTip);
						return false;
					}
					if (finalRequiredFlag && !typeArr.indexOf("2")== -1){
						bootbox.alert(finalRequiredTip);
						return false;
					} 
					
					return true;
				}
				
				//权重之和为100(总评为100 ，其他之和为100)
				function checkRatio($form){
					var $details = $form.find("#creditDetailArea").find(".form-body");
					var ratioSum = 0;
					var ratioOverall = 0;
					
					/*
					 * 允许成绩构成只有总评成绩 
					var isOnlyZp = true;//成绩构成只有总评
					*/
					
					$details.each(function(i, one){
						var ratio = Number($(one).find("input[name='ratio']").val())
						if (isTypeEqual($(one), "3")){
							ratioOverall = ratio;
						} else {
							/*
							 * 允许成绩构成只有总评成绩 
							isOnlyZp = false;
							*/
							ratioSum += ratio;
						}
					})
					
					if (ratioOverall!= 100 ){
						bootbox.alert(zpRatioTip);
						return false;
					}
					else if (ratioSum != 100){
						bootbox.alert(ratioTip);
						return false;
					}
					/*
					 * 允许成绩构成只有总评成绩 
					    else if (!isOnlyZp && ratioSum != 100){
						bootbox.alert(ratioTip);
						return false;
					}*/
					
					return true;
				}
				
				//判断是否为指定的类型
				function isTypeEqual($detail, typeVal){
					var type = $detail.find(":input[name=detailType]").val();
					return typeof(type)!="undefinde" && type==typeVal;
				}
				
				/**
				 * 组织表单内要提交的DetailInfo的内容
				 */
				function getDetailInfo($form){
					var $details = $form.find("#creditDetailArea").find(".form-body");
					var tempStr = "";
					$details.each(function(i, one){
						var $one = $(one);
						var id = $.trim($one.find("input[name='detailId']").val())//id
						var type = $.trim($one.find(":input[name=detailType]").val());//类型
						var priority = $.trim($one.find("input[name=priority]").val());//顺序
						var title = $.trim($one.find("input[name='detailTitle']").val())//名称
						var fullCredit = $.trim($one.find("input[name='fullCredit']").val())//满分
						var ratio = $.trim($one.find("input[name='ratio']").val())//权重
						
						var info= "id="+id + "_type=" + type + "_priority=" + priority + "_title=" + title + "_fullCredit=" + fullCredit + "_ratio=" + ratio;
						if (tempStr != ""){
							tempStr += "$$";
						} 
						tempStr += info;
					});//each
					var $subInfo = $form.find("input[name=detailInfo]");
					$subInfo.val(tempStr);
				}
				