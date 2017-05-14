/**
* 班级成绩录入
* 依赖： common_util.js
* 			bootbox.js
*/

	var ratioArr = new Array();
	function init_calculate(selector, tchClazzId){

		 $(selector).find("tr").each(function (r) {
		 		$(this).find("td").each(function (c) {
		 			$(this).find("input.computable").addClass("cChangeInput");
		 		});
		});
		//分数自动计算触发
		$(selector).on("blur", ".cChangeInput", function(evt){
			if (typeof(getKeyCode) != 'undefined'){
				var key = getKeyCode(evt);
				if ($.trim(key) != "" 
					&& (key == 9 || key == 13 || (key >= 37 && key<=40))){//点击方向键、回车、tab时，不计算分数
					return false;
				}
			}
			var $this = $(this);
			var $tr = $this.closest("tr");
			var $trInputs = $tr.find("input.computable");
			var noScoreCount = 0;
			for(var i=0; i<$trInputs.length; i++){
				 if (!$($trInputs[i]).valid() ){//数据非法时，不提交
					return;
				}
				 if ($.trim($($trInputs[i]).val()) == "" ){//无数据时，计数器+1
					 noScoreCount ++;
				 }
			}
			if ($this.val() == $this.attr("origscore")){
				return;
			} else {
				$this.attr("origscore", $this.val())
			}
			
			if (noScoreCount >= 1){//存在至少一项为空
				var $final = $tr.find("input.sub_info[cdType=2]")
				if ($final.length == 0){
					//changeZongping($tr.find("input.sub_info:first").attr("stucreditid"), -1, 0, 1);
					changeZongping({id:$tr.find("input.sub_info:first").attr("stucreditid"), scoreA: -1, score:-1, result:0}, 1);
					cleanZongpingReason($tr.find("input.sub_info:first").attr("stucreditid"));
				} else {
					var final_status = $.trim($final.attr("status"));
					if ( final_status== "-1" ||  final_status== "0" ||  final_status== "1"){
						//changeZongping($final.attr("stucreditid"), -1, 0, 1);
						changeZongping({id:$final.attr("stucreditid"), scoreA: -1, score:-1, result:0}, 1);
						cleanZongpingReason($final.attr("stucreditid"));
					}
				}
				return;
			}
			var url = getContextPath() + "/edu/scr/clazzcreditentry/0/0/" + tchClazzId + "/directaccess";
			prepareInfo($tr, false);
			var data = $tr.find("input").serialize();
			calculate(url, data, null);
		});
		
	}
	
	//批量计算
	function calculateBatch(tchClazzId, btn){
		var $btn = $(btn);
		var url = getContextPath() + "/edu/scr/clazzcreditentry/0/0/" + tchClazzId + "/directaccess";
		
		//准备数据
		var $form = $("form[name=scoreform]")
		if (!$form.valid()){
			return;
		}
		prepareInfo($form, false);
		var data = $form.serialize();
		$btn.button("loading");
		calculate(url, data, $btn);
	}//calculateBatch

	//后台计算结果
	function calculate(url, data, $btn){
		var $subBtn = $("a.btn_submit");
		$subBtn.button("loading");
		$.ajax({
			url : url,
			method: "POST",
			cache:false, 
    		dataType : "json",
    		data :data,
    		success : function(data,textStatus,jqXHR ) {
    			//遍历vo更新页面的显示内容
    			for(var i in data){
    				changeZongping(data[i], 1)
    				//计算完成后，要清除 input.zongpingReason 中的内容
        			cleanZongpingReason(data[i].id);
    			}
    			
    			if ($btn != null){
    				$btn.button("reset");
    			}
    			$subBtn.button("reset");
    		},
			error : function( jqXHR,  textStatus,  errorThrown) {
				if ($btn != null){
					$btn.button("reset");
				}
				$subBtn.button("reset");
    		}
		});
		
	}//calculate
	
	//学期成绩录入-手动修正总评成绩 修正 根据分数，获得成绩结果（手动调整）
	function changeExamResult(stuCreditId, score, tchClazzId, type, sctype){
		$.ajax({
			url : getContextPath() + '/edu/scr/clazzcreditentry/0/0/' + tchClazzId + '/directaccess',
			method: "POST",
			cache:false, 
			dataType : "json",
			data :{
				type: type,
				stuCreditId: stuCreditId ,
				score: score,
				sctype:sctype
			},
			success : function(data,textStatus,jqXHR ) {
				for(var i in data){
					if (type == "delayed"){
						changeZongping(data[i], 1)
					} else {
						changeZongping(data[i], 2)
					}//if-else
				}//for
			},
			error : function( jqXHR,  textStatus,  errorThrown) {
		
			}
		});
	}
	
	//根据学生总评的分数，从后台获取 成绩结果 , socre 不一定是计算值
	//type 用来区分是手动修改还是自动计算后的修改
	//1: 自动 ； 2：手动 手动时，已经在之前的js中修改过了scoreb & breason ,这里不再重复操作。
	function changeZongping(data, type){
		var id = data.id;
		var score = data.score;
		var scoreTranscript = data.scoreTranscript;
		var result = data.result;
		var $zpShow_jisuan = $("span.zongpingShow[name=zongping_jisuan_" + id + "]");
		var $zpShow_zhujiang = $("span.zongpingShow[name=zongping_zhujiang_" + id + "]");
		var $zpShow_credit = $("span.zongpingShow[name=credit_score_" + id + "]");//成绩单成绩
		var $rShow = $("span.resultShow[name=resultShow_" + id + "]");
		var $zp = $("input.zongping[name=zongping_" + id + "]");
		if (type ==1){//自动计算
			var scoreA = data.scoreA;
			$zp.attr("scorea", scoreA);
			$zp.attr("scoreb", "-1");
			$zp.attr("scorebreason", "0");
			showZpScore($zpShow_jisuan, scoreA);
			var $zpChangeBtn = $("a.btn-modify-zp-" + id);
			if (typeof($zpChangeBtn.html()) != "undefined"){
				if (score == -1){
					$zpChangeBtn.hide();
				} else {
					$zpChangeBtn.show();
				}
			}
		} else {//手动修改
			showZpScore($zpShow_zhujiang, score);
		}
		$zp.val(score);
		showZpScore($zpShow_credit, scoreTranscript);
		
		$rShow.removeClass("font-red");
		if (result == 3 || result ==2){
			$rShow.addClass("font-red");
		}
		var resultStr = "";
		switch(result)
		{
			case 2:
				resultStr = "需补考";
				break;
			case 3:
				resultStr = "需重修";
				break;
			case 6:
				resultStr = "待缓考";
				break;
			default:
				;
		}
		$rShow.text(resultStr).show();
	}
	
	function showZpScore(target, score){
		target.removeClass("font-red");
		if (score <60){
			target.addClass("font-red");
		}
		if (score >=0){
			target.text(score).show();
		} else {
			target.text("").show();
		}
	}
	
	//(批量)计算后，清空主动修改的内容
	function cleanZongpingReason(id){
		var $zp = $("input.zongping[name=zongping_" + id + "]");
		$zp.attr("scoreb", "-1");
		$zp.attr("scorebreason", "0");
		var $zpShow_zhujiang = $("span.zongpingShow[name=zongping_zhujiang_" + id + "]");
		showZpScore($zpShow_zhujiang, -1);
	}
	
	//提交前的准备
	//准备数据
	function prepareInfo($form, isSave){
		//获取页面上的分数，并给其后的隐藏域赋值
		$form.find("td>input.computable").each(function(){
				getSubInfo(this);
			});
		
		$form.find("td input.zongping").each(function(){
			if (typeof(isSave)=="undefined" || isSave){
				getZpInfo(this);
			} else {
				cleanZpInfo(this);
			}
		});
		
	}
	
	//获取 真正要提交的数据的内容
	function getSubInfo(obj){
		var $subInfo = $(obj).parent().find("input.sub_info:first");
		//<input name="credit_info" class="sub_info" value="" stucreditid="${one.id}"   cdetailId="${detail.creditDetailId}" status="${detail.status!0}"/>
		var info= "scid="+$.trim($subInfo.attr("stucreditid")) + "_cdid=" + $.trim($subInfo.attr("cdetailId")) + "_sb=" + $.trim($(obj).val()) + "_status=" +  $.trim($subInfo.attr("status"));
		$subInfo.val(info);
	}
	
	//获取 总评调整信息
	function getZpInfo(obj){
		var $this = $(obj);
		var $subInfo = $(obj).parent().find("input.sub_info:first");
		//<input name="credit_info" class="sub_info" value="" stucreditid="${one.id}"   cdetailId="${detail.creditDetailId}" status="${detail.status!0}"/>
		var info= "scid=" + $.trim($subInfo.attr("stucreditid")) + "_cdid=" + $.trim($subInfo.attr("cdetailId")) + "_sa=" + $.trim($this.attr("scorea")) + "_sb=" + $.trim($this.attr("scoreb")) + "_sbr=" + $.trim($this.attr("scorebreason"));
		$subInfo.val(info);
	}
	//清除zp项的内容 ，计算分数时，不向后台传总评成绩
	function cleanZpInfo(obj){
		var $this = $(obj);
		var $subInfo = $(obj).parent().find("input.sub_info:first");
		var info= "";
		$subInfo.val(info);
	}
	
	
	//提交期中成绩
	function submitMiddleCredit(tchClazzId, param){
		//检查数据
		var $middleData = $("form input.computable_middle") ;
//		if ($middleData.length ==0){
//			bootbox.alert("没有学生期中成绩信息，无法提交");
//			return;
//		}
		var count = 0;
		$middleData.each(function(){
			var score = $.trim($(this).val()); 
			if (score != "" && score != "-1"){
				count++;
			}
		})
		if (count != $middleData.length){
			bootbox.alert("期中成绩不完整，无法提交");
			return;
		}
		bootbox.confirm("确认提交期中成绩？", function(result){
			if (result){
				var url = getContextPath() + "/edu/scr/clazzcreditentry/0/0/" + tchClazzId + "/edit"+param;
				var $form = $("<form id='submitMiddleCreditForm' action='" + url + "' method='POST'></form>");
				var $input = $("<input type='hidden'  name='type' value='middle' />");
				$form.append($input);
				$form.appendTo("body").submit();
			}
		});
		
	}
	
	//提交总评成绩
	function submitGeneralCredit(tchClazzId, param){
		//检查数据
		var $middleData = $("form input.computable_general") ;
//		if ($middleData.length ==0){
//			bootbox.alert("没有学生成绩信息，无法提交");
//			return;
//		}
		var count = 0;
		$middleData.each(function(){
			var score = $.trim($(this).val()); 
			if (score != "" && score != "-1"){
				count++;
			}
		})
		if (count != $middleData.length){
			bootbox.alert("成绩信息不完整，无法提交");
			return;
		}
		bootbox.confirm("确认提交总评成绩？", function(result){
			if (result){
				var url = getContextPath() + "/edu/scr/clazzcreditentry/0/0/" + tchClazzId + "/edit"+param;
				var $form = $("<form id='submitGeneralCreditForm' action='" + url + "' method='POST'></form>");
				var $input = $("<input type='hidden'  name='type' value='overall' />");
				$form.append($input);
				$form.appendTo("body").submit();
			}
		});
		
    			
	}