/**
* 毕业设计成绩构成
* 依赖： common_util.js
* 	bootbox.js
*/
var credit_detail_index = 0; 
function setCreditDetailIndex(index){
	credit_detail_index = index;
}

var maxDetailNum = 8;
function appendDetail(){
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

	$temp.find("#add_temp_title").attr("name", "details["+credit_detail_index+"].title");
	$temp.find("#add_temp_priority").attr("name", "details["+credit_detail_index+"].priority");
	$temp.find("#add_temp_ratio").attr("name", "details["+credit_detail_index+"].ratio");
	$temp.find("#add_temp_fullCredit").attr("name", "details["+credit_detail_index+"].fullCredit");	
	
	$temp.find("#add_temp_title").attr("id", "title_"+credit_detail_index);
	$temp.find("#add_temp_priority").attr("id", "priority_"+credit_detail_index);
	$temp.find("#add_temp_ratio").attr("id", "ratio_"+credit_detail_index);
	$temp.find("#add_temp_fullCredit").attr("id", "fullCredit_"+credit_detail_index);
	
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
	return checkRatio($form) && checkTitle($form);
}

var ratioTip = "您的成绩构成权重不符合100%！";

//权重之和为100(总评为100 ，其他之和为100)
function checkRatio($form){
	var $details = $form.find("#creditDetailArea").find(".form-body");
	var ratioSum = 0;
	var ratioOverall = 0;
	
	$details.each(function(i, one){
		var ratio = Number($(one).find("input[name='details["+i+"].ratio']").val());
		ratioOverall = ratio;
		ratioSum += ratio;
	});
	
	if (ratioSum != 100){
		bootbox.alert(ratioTip);
		return false;
	}
	return true;
}

function checkTitle($form){
	var $details = $form.find("#creditDetailArea").find(".form-body");
	var titleArr = new Array(); 
	$details.each(function(i, one){
		var title = $(one).find("input[name='details["+i+"].title']").val();
		titleArr.push($.trim(title));
	});
	//判断是否重复
	if (isArrayHasRepeat(titleArr)){
		bootbox.alert("名称不能重复！");
		return false;
	}
	return true;
}