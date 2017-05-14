/**
*  教学班 课程成绩构成维护 
* 依赖： common_util.js
* 			bootbox.js
*/

	var noFormulaSelectTip = "请选择一个公式！"
	//课程成绩构成维护 - 通用公式  选择
	function checkBeforeSubmitChoose($form, tchClazzId){
		var val=$form.find('input:radio[name="generalId"]:checked').val();
		if (val==null){
			bootbox.alert(noFormulaSelectTip);
			return false;
		}//if
		if (tchClazzId ==null || Number(tchClazzId) <= 0){
			bootbox.alert("教学班数据异常，请返回上一级页面重试");
			return false;
		}
		return true;
	}
	
	var havaCreditEntryTip = "已经有成绩录入值了，您是否还要坚持调整成绩构成？";
	var elctCompFlagTip = "该班尚未开始选课或选课未完成，请在选课完成后再进行操作";
	var noAuthTip = "您没有权限设置这个教学班的成绩构成";
	var noRealTimeSchTip =  "该班还没有实时课表，无法进行成绩构成维护";
	var noExamModeRegTip =  "该班还没有通过教务审批，无法进行成绩构成维护";
	
	/**
	 * 
	 * @param url
	 * @param param 返回页面用到的参数 以?开头
	 * @param type 类型 自定义  / 模板
	 * @param hasCredit 是否有成绩录入
	 */
	function editClazzCreditDetail(url, param, type, hasCredit, elctCompFlag){
//		if (elctCompFlag != 1){
//			bootbox.alert(elctCompFlagTip);
//			return;
//		}
		if ($.trim(param) == ""){
			url += "?optype=" + type;
		} else {
			url += param + "&optype=" + type;
		}
		
		if (hasCredit != 0){
			bootbox.confirm(havaCreditEntryTip, function(result){
				if (result){
					window.location.href = url;
				}
			});
		} else {
			window.location.href = url;
		} 
	}
	
	var middleRequireTip = "根据考务信息需要设置期中考试！";
	var finalRequireTip = "根据考务信息需要设置期末考试！";
	
	var reCalculateScoreTip = "您修改了成绩构成，对之前录入的成绩带来影响，请到成绩录入页面重新保存成绩，以便进行成绩的重新计算，否则会导致总评成绩不准确。";
	