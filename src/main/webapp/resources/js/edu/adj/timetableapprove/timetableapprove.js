function batchEmpTimetableApprove(obj,urlSave,urlRedirect,checkedArr){
  var $btn = $(obj);
  $btn.attr("disabled","disabled");    
  var param = {
    cbkfield : checkedArr.toString()
  };
  jQuery.ajax({
    url : urlSave,
    method: "POST",
    async:true, 
    cache:false, 
    dataType : "json",
    data :param,
    success : function(data,textStatus,jqXHR ) {
        $btn.removeAttr("disabled");
        var returnFlag = data.result;
        if("teacherConflict"==returnFlag){
          var returnTip = data.tip;
          bootbox.alert(returnTip);
          return false;
        }
        if("classroomConflict"==returnFlag){
          var returnTip = data.tip;
          bootbox.alert(returnTip);
          return false;
        }
        if("naturalClazzConflict"==returnFlag){
          var returnTip = data.tip;
          bootbox.alert(returnTip);
          return false;
        }
        if("FAIL"==returnFlag){
          bootbox.alert("操作失败");
          return false;
        }
        if("OK"==returnFlag){
            $btn.removeAttr("disabled");
            window.location.href = urlRedirect;
        }
     },
    error : function( jqXHR,  textStatus,  errorThrown) {
        bootbox.alert("服务器请求异常，请再试一试");
        $btn.removeAttr("disabled");
    }
  });
}


