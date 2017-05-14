/**
 * 依赖于jquery.auto_complete.js ,  common_util.js(获取contextPath)
 * 由于业务需求，要求展现 name 同时保存id，故这里需要分别填写nameSelector、idSelector
 * @param {Object} nameSelector
 * @param {Object} idSelector
 */
function autocomplete_entry_init_ajax(nameSelector, idSelector){
  var $name = $(nameSelector);
  $name.keyup(function(){
    if($name == 'undefined' || $.trim($name.val()) == ''){
      $(idSelector).val("");
    }
  });
	var data = {};
	$.ajax({
    	url: getContextPath() + '/edu/clr/classroom/0/0/classroom/directaccess',
      type: "POST",
      async:true, 
    	data: data,
    	dataType: 'json',
    	success: function(result) {
    		if (typeof(result.data) != 'undefined'){
    			var jsonObj = result.data;
        		init_autocomplete_entry(nameSelector, idSelector, jsonObj);
    		}
    	}
    });
}

/**
 * 考务选择能做考场的场地 钱渊
 * @param nameSelector
 * @param idSelector
 */		  	
function autocomplete_examroom_init_ajax(nameSelector, idSelector){
  var $name = $(nameSelector);
  $name.keyup(function(){
    if($name == 'undefined' || $.trim($name.val()) == ''){
      $(idSelector).val("");
    }
  });
	var data = {};
	$.ajax({
    	url: getContextPath() + '/edu/clr/classroom/0/0/examroom/directaccess',
      type: "POST",
      async:true, 
    	data: data,
    	dataType: 'json',
    	success: function(result) {
    		if (typeof(result.data) != 'undefined'){
    			var jsonObj = result.data;
        		init_autocomplete_entry(nameSelector, idSelector, jsonObj);
    		}
    	}
    });
}

function init_autocomplete_entry(nameSelector, idSelector, jsonObj){
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
					formatItem: function(row, i, max, item) {//格式化列表中的条目
						// 房间号-楼号-校区
						var result = row.campusName + "-" + row.code + "-" + row.roomNo;
						return  result;
					},
					formatMatch: function(row, i, max) {//对每一行数据使用此函数格式化需要查询的数据格式. 返回值是给内部搜索算法使用的. 参数值和formatItem的参数一样。
            var result = row.campusName + "-" + row.code + "-" + row.roomNo;
						return  result;
					}, 
					formatResult: function(row) {//被选中时输出的内容
						return row.campusName + "-" + row.code + "-" + row.roomNo;
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
					$(idSelector).val(data.classroomId);
				}
			});//result
			
		}