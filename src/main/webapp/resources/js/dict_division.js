/*--部门字典表页面中使用--
 * @author RanWeizheng
 * 依赖： dict_department.js
 * common_util.js
 * 
 * **/

function addDivision(departmentId){
	var url = getContextPath() + "/dict/edu/common/department/0/0/0/add?parentId=" + departmentId;
//	loadContentInPage(url);
	$.ajaxModal(url);
}

/**删除*/
function deleteDivisionRecord(obj, departmentId){
	bootbox.confirm(dictDeleteWarnStr, function(result){
		if(!result){
		    return;
		}
		var $btn = $(obj);
		var url = getContextPath() + "/dict/edu/common/department/0/0/" + departmentId + "/delete";
		$btn.button("loading");
		$.ajax({
			type:'POST',
			dataType: "text",//返回字符串
			url: url,
			data:{
				departmentId : departmentId,
			},
			success: function(msg) {
				if (msg.toUpperCase() == "OK") {
					divisionPageRefresh();
				}  
				else {
					bootbox.alert("操作失败,节点以删除或存在子节点！");	
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
function updateDivisionRecord(departmentId, majorId){
	bootbox.confirm(dictModifyWarnStr, function(result){
		if(!result){
		    return;
		}
		var url = getContextPath() + "/dict/edu/common/department/0/0/" + departmentId + "/edit";
		$.ajaxModal(url);
	});
}
