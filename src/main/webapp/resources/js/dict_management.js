var dictDeleteWarnStr = "数据被删除，无法恢复。<br/>数据一旦被删除，所有使用该数据功能都会受到影响，包括历史记录。<br/>您确定要删除该数据吗？";
var dictModifyWarnStr = "数据一旦被修改，所有使用该数据功能都会受到影响，包括历史记录。<br/>您确定要继续修改该数据吗？";

/**删除*/
	function deleteDictRecord(obj, urlroot , id, param){
		bootbox.confirm(dictDeleteWarnStr, function(result){
			if(!result){
			    return;
			}
			var $btn = $(obj);
			var url = urlroot + "/" + id + "/delete" + param; 
			$btn.button("loading");
			window.location.href = url;
		});//confirm
		
	}
	
	//修改字典表记录的状态
	//status 为当前状态,要在js方法中转换成真正需要使用的
	function changeDictStatus(obj, status, urlroot, id, param){
		bootbox.confirm(dictModifyWarnStr, function(result){
			if(!result){
			    return;
			}
			var $btn = $(obj);
			var url = urlroot + "/" + id + "/edit" + param;
			status = -(status-1);
			$btn.button("loading");
			
			var $form = $("<form id='changestatusForm' action='" + url + "' method='POST'></form>");
			var $input = $("<input type='hidden'  name='status' value='" + status + "' />");
			var $input2 = $("<input type='hidden'  name='type' value='changestatus' />");
			$form.append($input).append($input2);
			$form.appendTo("body").submit();
		});//confirm
	}
	
	//进入修改页面
	function updateDictRecord(urlroot, id, param){
		bootbox.confirm(dictModifyWarnStr, function(result){
			if(!result){
			    return;
			}
			var url = urlroot + "/" + id + "/edit" +param;
			window.location.href = url;
		});
	}
	
	//进入修改页面 提示信息自定义
	function updateDictRecordMsg(urlroot, id, param, msg){
		if(msg.length>0){
			bootbox.confirm(msg, function(result){
				if(!result){
				    return;
				}
				var url = urlroot + "/" + id + "/edit" +param;
				window.location.href = url;
			});			
		}else{
			var url = urlroot + "/" + id + "/edit" +param;
			window.location.href = url;			
		}

	}
	
	//删除 提示信息自定义
	function deleteDictRecordMsg(obj, urlroot , id, param, msg){
		if(msg.length>0){
			bootbox.confirm(msg, function(result){
				if(!result){
				    return;
				}
				var $btn = $(obj);
				var url = urlroot + "/" + id + "/delete" + param; 
				$btn.button("loading");
				window.location.href = url;
			});//confirm		
		}else{
			var $btn = $(obj);
			var url = urlroot + "/" + id + "/delete" + param; 
			$btn.button("loading");
			window.location.href = url;			
		}	
	}

	//修改字典表记录的状态
	//status 为当前状态,要在js方法中转换成真正需要使用的
	function changeDictStatusMsg(obj, status, urlroot, id, param, msg){
		var $btn = $(obj);
		var url = urlroot + "/" + id + "/edit" + param;
		status = -(status-1);
		$btn.button("loading");
		
		var $form = $("<form id='changestatusForm' action='" + url + "' method='POST'></form>");
		var $input = $("<input type='hidden'  name='status' value='" + status + "' />");
		var $input2 = $("<input type='hidden'  name='type' value='changestatus' />");
		$form.append($input).append($input2);		
		if(msg.length>0){
			bootbox.confirm(msg, function(result){
				if(!result){
				    return;
				}
				$form.appendTo("body").submit();
			});//confirm		
		}else{
			$form.appendTo("body").submit();		
		}
	}