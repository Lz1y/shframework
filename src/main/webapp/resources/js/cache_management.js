//清除所有缓存
//依赖 common_util.js
function cleanAllCache(target){
    var url = getContextPath() + "/sys/setting/0/0/all/edit";
    var $btn = $(target);
    
    bootbox.confirm("是否要清空缓存？", function(result){
		if(!result){
		    return;
		}
	    $btn.button("loading");
	    $.ajax({
			type:'GET',
			dataType: "text",//返回字符串
			url: url,
			success: function(msg) {
				if (msg.toUpperCase() == "OK") {
					bootbox.alert("成功");
				}  
				else {
					bootbox.alert("失败");
				}
				$btn.button("reset");
			},
			error: function(){
				bootbox.alert("操作失败！");
				$btn.button("reset");
			}	
		});//ajax
		
    });
  
	
}