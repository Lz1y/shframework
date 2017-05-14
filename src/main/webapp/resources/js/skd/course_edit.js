	$(document).ready(function(){
		ComponentsIonSliders.init();
	});
	/**/
		
	// 周学时数
	var chpwChange = function(item) {
	
		var weeks = $("#weeks").val();
		var chpw = item.value;
		calculate_ch(weeks,chpw);
	};
	
	
	// 教学周数
	var weeksChange = function(item) {
	
		var weeks = item.value;
		var chpw = $("#chpw").val();
		calculate_ch(weeks,chpw);
	};
	
	// 学时计算方法 --公共方法
	function calculate_ch(weeks,chpw){
		
		var cm = $("#cm").val();
		
		var chSum = $("#chSum").val();
		if(cm != 2 && cm != 3){
			chSum = chpw * weeks;
		}else{
			weeks = chSum/chpw;
			weeks = Math.round(weeks/0.5)*0.5;
			$("#weeks").val(weeks);
		}
		
		var csv = chSum % 2;
		if(csv <= 1) chSum = parseInt(chSum);
		if(csv > 1) chSum = parseInt(chSum) + 1;	// 向上取整
		if(chSum % 2 != 0){
			chSum = Math.round(chSum) + 1;			// 向上取偶
		}
		
		var range_pract_pct = $("#range_pract_pct").val();
		var pch = chSum * range_pract_pct / 100;
		
		var pv = pch % 2;
		if(pv <= 1) pch = parseInt(pch);
		if(pv > 1) pch = parseInt(pch) + 1;	// 向上取整
		if(pch%2 != 0){
			pch = Math.round(pch) + 1;		// 向上取偶
		}
		var rch = chSum - pch;
		
		
	//	console.log("chsum="+chSum+"pch="+pch+"rch="+rch);
		if(cm != 3){
			$("#chSum").val(chSum);
		}
		$("#pch").val(pch);
		$("#rch").val(rch);
		
		if(cm == 2 || cm == 4){	// =周学时
			
			$("#credit").val(chpw);
		}
		else if(cm == 3) {	// =课程建议的总学时/每学分学时数
			
			var credit = $("#chSum").val()/$("#chpc").val();
			credit = Math.round(credit/0.5)*0.5;
			$("#credit").val(credit);
		}
		else if(cm == 5) {	// =周学时*教学周数/每学分学时数
			
			var credit = chpw*weeks/$("#chpc").val();
			credit = Math.round(credit/0.5)*0.5;
			$("#credit").val(credit);
			
		}else if(cm == 6) { // =教学周数
			$("#credit").val(weeks);
		}
		
	}
	
