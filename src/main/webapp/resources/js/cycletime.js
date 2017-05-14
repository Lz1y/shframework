
//1:修改edit 2:启动start 3:特批approval 4:新增add
var startCycletime = function(ctx, id, sDate) {
	var date = new Date();
	var dt = new Date(sDate.replace(/-/,"/"));
	if(date > dt) 
		bootbox.alert("教学计划制定的结束时间在当前时间之前，请先修改结束时间，再进行启动操作。");
	else 
		bootbox.confirm("您正在启动教学计划的制定工作。您确认启动吗？", function(result){
			if(!result) return;
			window.location.href = ctx + "/edu/skd/cycletime/" + id + "/start?pType=2";
		});
}

var approvalCycletime = function(ctx, id) {
	bootbox.confirm("特批操作，需要线下进行充分的沟通和领导认可。特批修改的数据，会影响到其他业务的进行，可能会导致相关工作推倒重来，您确定您要进行特批修改吗？", function(result){
		if(!result) return;
		window.location.href = ctx + "/edu/skd/cycletime/" + id + "/approval";
	});
}

var ctimesave = function() {
	var endDate = document.getElementsByName("endDate")[0].value;
	var date = new Date();
	var dt = new Date(endDate.replace(/-/,"/"));
	if(date > dt) bootbox.alert("结束时间不能小于当前时间");
	else $("#ctimeform").submit();
}