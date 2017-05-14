//修正成绩
				function beforeFix(stuCreditId, obj){
					$("#fixSCId").val(stuCreditId);
					var $this = $(obj);
					var $tds = $this.closest("tr").find("td");
					$tds.each(function(i){
						switch(i){
							case 1:
								$("#fixStuName").html($(this).html());
								break;
							default:
								break;
						}//switch
					})
					
					//找到保存有分数信息的input					
					var $input = $this.closest("td").find("input.sub_info:first");
				
					$("#fixScoreB").html(getFinalScore($input));
					$("#fixReanson").val($input.attr("scorecreason"));
					var scorec = $input.attr("scorec");
					if (typeof(scorec)!="undefinde" && scorec != -1){
						$("#fixScoreC").val($this.attr("scorec"));
					} else {
						$("#fixScoreC").val("");
					}
					$("#fixScoreC").attr("range", $input.attr("range"));
					
					//$("#fixSCDetailId").val($input.attr("stucreditdetailid"));
				
				}
				
				function beforeCancel(stuCreditId, obj){
					$("#cancelSCId").val(stuCreditId);
					var $this = $(obj);
					var $tds = $this.closest("tr").find("td");
					$tds.each(function(i){
						switch(i){
							case 1:
								$("#cancelStuName").html($(this).html());
								break;
							case 4:
								$("#cancelFixReason").html($(this).html());
								break;
							default:
								break;
						}//switch
					})
					
					//找到保存有分数信息的input					
					var $input = $this.closest("td").find("input.sub_info:first");
					
					$("#cancelFixScoreB").html(getFinalScore($input));
					$("#cancelFixScoreC").html($input.attr("scorec"));
					//$("#cancelSCDetailId").val($this.attr("stucreditdetailid"));
				}
				
				function creditFix(){
					var $form = $("#fixApplyForm");
					if (!$form.valid()){
						return;
					}
					var scId = $("#fixSCId").val();
					var score = $("#fixScoreC").val();
					var reason = $("#fixReason  option:selected").text();
					var reasonId = $.trim($("#fixReason").val());
					$("#score_show_"+scId).html(score);
					$("#reason_show_"+scId).html(reason);
					
					changeBtnShowStyle(scId);
					$("#fixApplyCloseBtn").click();
					
					//找到保存有分数信息的input					
					var $input = $("#score_show_"+scId).closest("td").find("input.sub_info:first");
					$input.attr("scorec", score);
					$input.attr("scorecreason", reasonId);
				}
				
				function creditCancel(){
					var scId = $("#cancelSCId").val();
					$("#reason_show_"+scId).html("");
					changeBtnShowStyle(scId);
					
					//找到保存有分数信息的input					
					var $input = $("#score_show_"+scId).closest("td").find("input.sub_info:first");
					
					$("#score_show_"+scId).html(getFinalScore($input));
					$input.attr("scorec", "-1");
					$input.attr("scorecreason", "0");
				}
				
				function changeBtnShowStyle(stuCreditId){
					if (typeof(stuCreditId)=="undefinde" || stuCreditId<=0){
						return;
					}
					$("#fix_" + stuCreditId).toggle();
					$("#cancel_" + stuCreditId).toggle();
				}
				
				function getFinalScore($input){
					var origScore = $input.attr("scoreb");
					if (typeof(origScore)=="undefinde" || origScore<0){
						origScore = $input.attr("scorea");
						if (typeof(origScore)=="undefinde" || origScore<0){
							origScore = "N/A";
						} 
					}
					return origScore;
				}//getFinalScore
				
				
				
				//提交前的准备
				//准备数据
				function prepareInfo($form){
					//获取页面上的分数，并给其后的隐藏域赋值
					$form.find("td input.sub_info").each(function(){
						var $subInfo = $(this);
						var info = "scid=" +  $.trim($subInfo.attr("stucreditid"))  + "_cdid=" +  $.trim($subInfo.attr("cdetailId")) + "_sc=" +  $.trim($subInfo.attr("scorec")) +"_scr=" +  $.trim($subInfo.attr("scorecreason"));
						$subInfo.val(info);
					});
						
					
				}
				
			