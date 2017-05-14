/*--专业~院系 关系维护页面中使用--
 * @author RanWeizheng
 * 依赖： dict_management.js
 *  common_util.js(获取contextPath)
 * **/

function addMajorDepartment(departmentId){
	var url = getContextPath() + "/dict/edu/common/deptmajor/0/0/" + departmentId + "/add";
	loadContentInPage(url);
}

function editCancel(departmentId){
	var url = getContextPath() + "/dict/edu/common/deptmajor/0/0/" + departmentId + "/list";
	loadContentInPage(url);
}


/**删除*/
function deleteMajorDepartmentRecord(obj, departmentId, majorId){
	bootbox.confirm(dictDeleteWarnStr, function(result){
		if(!result){
		    return;
		}
		var $btn = $(obj);
		var url = getContextPath() + "/dict/edu/common/deptmajor/0/0/" + departmentId + "/delete";
		$btn.button("loading");
		$.ajax({
			type:'POST',
			dataType: "text",//返回字符串
			url: url,
			data:{
				departmentId : departmentId,
				majorId: majorId
			},
			success: function(msg) {
				if (msg.toUpperCase() == "OK") {
					editCancel(departmentId);
				}  
				else {
					bootbox.alert("操作失败！");	
				}
				$btn.button("reset");
			},
			error: function(){
				bootbox.alert("操作失败！");
				$btn.button("reset");
			}	
		});//ajax
		
	});//confirm
	
}

//进入修改页面
function updateMajorDepartmentRecord(departmentId, majorId){
	bootbox.confirm(dictModifyWarnStr, function(result){
		if(!result){
		    return;
		}
		var url = getContextPath() + "/dict/edu/common/deptmajor/0/0/" + departmentId + "/edit";
		
		loadContentInPage(url, {departmentId:departmentId, majorId: majorId});
	});
}

//保存
function saveMajorDepartmentAjax(obj, departmentId, url, data){
	
		var $btn = $(obj);
		
		$btn.button("loading");
		$.ajax({
			type:'POST',
			dataType: "text",//返回字符串
			url: url,
			data:data,
			success: function(msg) {
				if (msg.toUpperCase() == "OK") {
					bootbox.alert("保存成功", function(){editCancel(departmentId);});
				}  
				else if (msg.toUpperCase() == "DUPLICATEKEY"){
					bootbox.alert("已经存在相同的院系-专业关联");
				}
				else {
					bootbox.alert("操作失败！");	
				}
				$btn.button("reset");
			},
			error: function(){
				bootbox.alert("操作失败！");
				$btn.button("reset");
			}	
		});//ajax
	
}

