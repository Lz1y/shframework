// 启动  
var startCycletime = function (ctx,id,endDate,resource){
	var date = new Date();
	var dt = new Date(endDate.replace(/-/,"/"));
	if(date > dt){
		bootbox.alert("结束时间在当前时间之前，请先修改结束时间，再进行启动操作。");
	}else{
		bootbox.confirm("您确认启动吗？", function(result){
			if(!result){
				return;
			}else{
				window.location.href = ctx + "/edu/prog/"+resource+"/" + id + "/start";
			}
		});
	}
};
// 特批
var approvalCycletime = function (ctx,id,resource){
	bootbox.confirm("特批操作，需要线下进行充分的沟通和领导认可。特批修改的数据，会影响到其他业务的进行，可能会导致相关工作推倒重来，您确定您要进行特批修改吗？", function(result){
		if(!result){
			return;
		}else{
			window.location.href = ctx + "/edu/prog/"+resource+"/" + id + "/approval";
		}
	});
};











