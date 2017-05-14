//list 列表页排序相关的js

var sortbyth = function(ele) {
	document.getElementById("field").value = ele.getAttribute("name");
	
	if(document.getElementById("orderby").value == null || document.getElementById("orderby").value == '' || document.getElementById("orderby").value == 'asc') {
		document.getElementById("orderby").value = 'desc';
		ele.setAttribute("class", "sort_desc");
	} else {
		document.getElementById("orderby").value = 'asc';
		ele.setAttribute("class", "sort_asc");
	}

	document.getElementsByName("listform")[0].submit();
}

var cbkall = function(ele) {
		
	var cbklist = document.getElementsByName(ele.value);
	if(ele.checked) {
		for (var i = 0; i < cbklist.length; i++) {
			cbklist[i].parentNode.setAttribute("class", "checked");
			$(cbklist[i]).prop("checked", true);
		}
	} else {
		for (var i = 0; i < cbklist.length; i++) {
			cbklist[i].parentNode.removeAttribute("class");
			$(cbklist[i]).prop("checked", false);
		}
	}
	
}
	
var sortInit = function() {
	var ths = document.getElementsByTagName("th");
	
	if (document.getElementById("field") == null && document.getElementById("orderby") == null){//rwz add 避免在列表页未使用分页组件时js抛错的问题。
		return;
	}
	
	for (var i = 0; i < ths.length; i++) {
		if(document.getElementById("field").value == null || document.getElementById("field").value == '') {
			var dSortField = document.getElementById("defaultSortField").value;
			var dOrderby = document.getElementById("defaultSortOrderby").value;
			if(ths[i].getAttribute("name") == dSortField) {
				document.getElementById("field").value = dSortField;
				document.getElementById("orderby").value = dOrderby;
				
				if(dOrderby == null || dOrderby == '') dOrderby = 'asc';
				ths[i].setAttribute("class", "sort_" + dOrderby);
				return;
			}
		} else {
			if(ths[i].getAttribute("name") == document.getElementById("field").value) {
				ths[i].setAttribute("class", "sort_" + document.getElementById("orderby").value);
				return;
			}
		}
	}
		
}

function dictListOnLoad() {
	var ths = document.getElementsByTagName("th");
	for (var i = 0; i < ths.length; i++) {
		if(ths[i].getAttribute("name") == document.getElementById("field").value) {
			ths[i].setAttribute("class", "sort_" + document.getElementById("orderby").value);
			return;
		}
	}
}

function isIE() { //ie?  
    return (!!window.ActiveXObject || "ActiveXObject" in window);
} 

function isIE6() {
    return navigator.userAgent.split(";")[1].toLowerCase().indexOf("msie 6.0")=="-1"?false:true;
}

function isIE7() {
    return navigator.userAgent.split(";")[1].toLowerCase().indexOf("msie 7.0")=="-1"?false:true;
}

function isIE8() {
    return navigator.userAgent.split(";")[1].toLowerCase().indexOf("msie 8.0")=="-1"?false:true;
}

function isNN() {
   return navigator.userAgent.indexOf("Netscape")!=-1;
}

function isOpera() {
    return navigator.appName.indexOf("Opera")!=-1;
}

function isFF() {
    return navigator.userAgent.indexOf("Firefox")!=-1;
}

function isChrome() {
    return navigator.userAgent.indexOf("Chrome") > -1;
}

//RanWeizheng 
var appendMatchField = function (valueSelector, mate, text){
	var $value = $(valueSelector);
	var enabledItem = $("<li ondblclick='choiceRemove(this)' style='background-color: #f6fbf4 !important;margin-left:20px;margin-top:10px;margin-bottom:10px;' class='select2-search-choice'><div divtext=" + mate + ">" + text + "</div></li>");
	$("#s2id_select2_sample5").find(".select2-choices").append(enabledItem);
	
	if($value.val() == '') $value.val(mate);
	else $value.val($value.val() + ',' + mate);
}

//RanWeizheng 清除匹配内容
var cleanMatchField = function(valueSelector){
	var $value = $(valueSelector);
	$value.val("");
	$("#s2id_select2_sample5").find(".select2-choices").children().remove();
}

var matchField = function() {
	var rootfield = $("#rootfield").val();
	var dbfield = $("#dbfield").val();
	
	if(rootfield != null && dbfield != null) {
		$("#s2id_select2_sample5").find(".select2-search-field").remove();
		
		var obj1 = document.getElementById('rootfield');
		var textRootField = obj1.options[obj1.selectedIndex].text;//获取文本
		
		var obj2 = document.getElementById('dbfield');
		var textDbField = obj2.options[obj2.selectedIndex].text;//获取文本
		
		var mate = rootfield + '-' + dbfield;
		var text = textRootField + ' - ' + textDbField;
		
		appendMatchField("#select2_sample5", mate, text);
	}
}


//导入excel模板设置所用的方法 modify by zhangjk
var matchField2 = function() {
	var value = $("#select2_sample5").val();
	
	var rootfield = $("#rootfield").val();
	var dbfield = $("#dbfield").val();
	
	if(rootfield != null && dbfield != null) {
		$("#s2id_select2_sample5").find(".select2-search-field").remove();
		
		var obj1 = document.getElementById('rootfield');
		var textRootField = obj1.options[obj1.selectedIndex].text;//获取文本
		
		var obj2 = document.getElementById('dbfield');
		var textDbField = obj2.options[obj2.selectedIndex].text;//获取文本
		
		var mate = dbfield + ',' +rootfield;
		var text = textRootField + ' - ' + textDbField;
		var enabledItem = $("<li ondblclick='choiceRemove(this)' style='background-color: #f6fbf4 !important;margin-left:20px;margin-top:10px;margin-bottom:10px;' class='select2-search-choice'><div divtext=" + mate + ">" + text + "</div></li>");
		$("#s2id_select2_sample5").find(".select2-choices").append(enabledItem);
		
		if(value == '') $("#select2_sample5").val(mate);
		//格式如下："_erph,academic_year_id,收费区间;_ersr,student_no,学号 ..."
		else $("#select2_sample5").val(value + ';' + mate);
	}
}

var choiceRemove = function(element) {
	var tag = element.getElementsByTagName("div");
	var text = tag[0].getAttribute("divtext");
	var value = $("#select2_sample5").val().replace(text, '');
	
	if(value.indexOf(',') == 0) value = value.substring(1, value.length);
	else if (value.indexOf(';') == 0) value = value.substring(1, value.length);
	else if(value.lastIndexOf(',') == value.length) value = value.substring(0, value.length - 1);
	else if(value.indexOf(',,') != -1) value = value.replace(',,', ',');
	
	$("#select2_sample5").val(value);
	
	if(isIE()){
		element.removeNode(true);
	} else {
		element.remove();
	}
}

var omitr = function (element) {
	if(isIE()){
		element.parentNode.parentNode.removeNode(true);
	} else {
		element.parentNode.parentNode.remove();
	}
}

var removeRow = function() {
	$("#datatable_ajax_wrapper").find("div[class=row]").each(function(index, item){
		if(isIE()) item.removeNode(true);
		else item.remove();
	});
}


/*
	批量取得选中的id的通用方法
	用法：
	var json = return_selected_ids("checkbox_name");
	var count = json["count"];
	var ids = json["ids"];
	
	author:wangkang
*/ 
function return_selected_ids(checkbox_name){
	var count = 0;
	var idArray = new Array();
	$("[type='checkbox'][name='"+checkbox_name+"']").each(function(){
		if($(this).parent().hasClass("checked")){
			count ++;
			idArray.push($(this).val());
		}
	});
	var json = {"count":count,"ids":idArray.toString()};
	return json;
}

