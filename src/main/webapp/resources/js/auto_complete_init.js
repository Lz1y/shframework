//自动补全 教师/雇员 信息时的初始化  @author RanWeizheng
//依赖于jquery.auto_complete.js ,  common_util.js(获取contextPath)
//由于业务需求，要求展现 教师name 同时保存教师的id，故这里需要分别填写nameSelector、idSelector
function init_autocomplete_employee_ajax(nameSelector, idSelector, depId, isContainOthers){
	var data = {};
	if (typeof(depId)!="undefined" && depId >0){
		data.dep_id= depId;
	}
//	is_contain_others
	if (typeof(isContainOthers)!="undefined"){
		data.is_contain_others= isContainOthers;
	}
	
	var $name = $(nameSelector);//add by zhangjk(解决 不可选课教学班配学生，主讲教师选择一个教师后再删除，点击搜索，主讲教师仍显示)
	$name.keyup(function(){
		if($name == 'undefined' || $.trim($name.val()) == ''){
			$(idSelector).val("");
		}
	});
	
	$.ajax({
    	type: "POST",
    	dataType: 'json',
    	//返回 json数据
    	url: getContextPath() + '/dict/teacher/directaccess',
    	data: data,
    	success: function(result) {
    		if (typeof(result.data) != 'undefined'){
    			var jsonObj = result.data;
        		init_autocomplete_employee(nameSelector, idSelector, jsonObj);
    		}
    	}
    });
}

function init_autocomplete_employee(nameSelector, idSelector, jsonObj){
			
			var $name = $(nameSelector);
			if (typeof($name.autocomplete) != null){
				$name.unautocomplete();
			}
			$name.autocomplete(jsonObj, {
					minChars: 0,
					matchCase:false,//不区分大小写
					autoFill: false,
					matchContains:true,//是否要在字符串内部查看匹配,如ba是否与foo bar中的ba匹配.
					multiple: false,
					max: 200,
					formatItem: function(row, i, max, term) {//格式化列表中的条目
						var result ;
						if ( row.depName!=""){
							result = row.title+ "(" + row.depName + ") " + row.genderName + " " + row.userNo;
						} else {
							result = row.title +" " + row.depName + " " + row.userNo;
						}
						return  result;
					},
					formatMatch: function(row, i, max) {//对每一行数据使用此函数格式化需要查询的数据格式. 返回值是给内部搜索算法使用的. 参数值和formatItem的参数一样。也可以使用扩展的reasultSearch 属性来做，区别在于reasultSearch可自定义查询方式。
						var result ;
						if ( row.depName!=""){
							result = row.title+ "-" + row.pinyin+ "-" + row.jianpin+ "-(" + row.depName + ") " + row.genderName + " " + row.userNo;
						} else {
							result = row.title+ "-" + row.pinyin+ "-" + row.jianpin+ "- " + row.genderName + " " + row.userNo;
						}
						return  result;
					}, 
					formatResult: function(row) {//被选中时输出的内容
						return row.title;
					}
					
			});//autocomplete()
			
			$name.result(function(event, data, formatted){//当某一项被选中时/没有符合条件的结果时
				if(typeof(data)=="string"){//没有结果时--可能是由于没有找到匹配的结果，或者没有选中任何东西
					$(this).attr("placeholder",  data);// 清除 无匹配提示 
				} else if (typeof(data)=="undefined"){
					$(this).attr("placeholder",  "");//rwz 清除 无匹配提示
					$(idSelector).val("");
				} 
				else if(typeof(data) != 'undefined' || (typeof(formatted) != 'undefined' && formatted!='')){//&&优先级高于||  这里为了代码可读性还是加上了括号
					$(idSelector).val(data.userId);
				}
			});//result
			
			
		}//init_autocomplete_employee()


/**
 * 
 * @param nameSelector    教学班名显示的选择器
 * @param idSelector			暂时没有用到， 用来保存教学班id
 * @param campusId			校区id
 * @param acdYearTermId  学年学期id
 */
function init_autocomplete_tchclazz_ajax(nameSelector, idSelector, campusId, acdYearTermId, defaultValue){
	var data = {};
	if (typeof(campusId)!="undefined" && campusId >0){
		data.campus_id= campusId;
	}
	if (typeof(acdYearTermId)!="undefined" && acdYearTermId >0){
		data.acdyearterm_id= acdYearTermId;
	}
	$.ajax({
    	type: "POST",
    	dataType: 'json',
    	//返回 json数据
    	url: getContextPath() + '/dict/tchclazz/directaccess',
    	data: data,
    	success: function(result) {
    		if (typeof(result.data) != 'undefined'){
    			var jsonObj = result.data;
        		init_autocomplete_tchclazz(nameSelector, idSelector, jsonObj, defaultValue);
    		}
    	}
    });
}

function init_autocomplete_tchclazz(nameSelector, idSelector, jsonObj, defaultValue){
			
			var $name = $(nameSelector);
			if (typeof($name.autocomplete) != null){
				$name.unautocomplete();
			}
			if (jsonObj.length == 0){
				$name.attr("placeholder", "未找到匹配的结果");
			} else {
				$name.attr("placeholder", "");
			}
			
			$name.autocomplete(jsonObj, {
					minChars: 0,
					matchCase:false,//不区分大小写
					autoFill: false,
					matchContains:true,//是否要在字符串内部查看匹配,如ba是否与foo bar中的ba匹配.
					multiple: false,
					max: 200,
					formatItem: function(row, i, max, term) {//格式化列表中的条目
						return  row.title + "(" + row.courseTitle + "-" + row.courseCode + ")";
					},
					formatMatch: function(row, i, max) {//对每一行数据使用此函数格式化需要查询的数据格式. 返回值是给内部搜索算法使用的. 参数值和formatItem的参数一样。
						return  row.title + "(" + row.courseTitle + "-" + row.courseCode + ")";
					}, 
					formatResult: function(row) {//被选中时输出的内容
						return row.title;
					}
					
			});//autocomplete()

			$name.result(function(event, data, formatted){//当某一项被选中时/没有符合条件的结果时
				if(typeof(data)=="string"){//没有结果时--可能是由于没有找到匹配的结果，或者没有选中任何东西
					$(this).attr("placeholder",  data);// 清除 无匹配提示 
				} else if (typeof(data)=="undefined"){
					$(this).attr("placeholder",  "");//rwz 清除 无匹配提示
					$(idSelector).val("");
				} 
				else if(typeof(data) != 'undefined' || (typeof(formatted) != 'undefined' && formatted!='')){//&&优先级高于||  这里为了代码可读性还是加上了括号
					$(idSelector).val(data.id);
				}
			});//result
			
			
		}//init_autocomplete_tchclazz()