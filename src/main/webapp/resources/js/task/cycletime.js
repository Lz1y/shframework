

// 保存
var ctimesave = function() {
	var endDate = document.getElementsByName("endDate")[0].value;
	var date = new Date();
	var dt = new Date(endDate.replace(/-/,"/"));
	if(date > dt){
		bootbox.alert("结束时间不能小于当前时间");
	}else{
		$("#ctimeform").submit();
	}
};










