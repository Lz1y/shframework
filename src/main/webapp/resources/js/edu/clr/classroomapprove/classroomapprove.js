/*
 * 场地使用审批
 */

function batchApproveDo(urlSave,urlRedirect){
  
  var detailIdArr = new Array();
  var $detailId = $("input[name='cbkfield']:checked");
  if($detailId.length == 0){
    bootbox.alert("请至少勾选一条记录");
    return false;
  }
  $detailId.each(function(index,item){  
    detailIdArr.push($(item).val());
   });
  var param = {
    detailId:detailIdArr.toString()
  };
                
  $.ajax({
      url : urlSave,
      method: "POST",
      async:false, 
      cache:false, 
      dataType : "json",
      data :param,
      success : function(data,textStatus,jqXHR ) {
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
            window.location.href = urlRedirect;
          }
       },
      error : function( jqXHR,  textStatus,  errorThrown) {
            bootbox.alert("服务器异常，请稍后再试一试");
      }
    });
}

function approveDo(urlSave,urlRedirect,detailId){
  
  var param = {
    detailId:detailId
  };
  $.ajax({
      url : urlSave,
      method: "POST",
      async:false, 
      cache:false, 
      dataType : "json",
      data :param,
      success : function(data,textStatus,jqXHR ) {
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
            window.location.href = urlRedirect;
          }
       },
      error : function( jqXHR,  textStatus,  errorThrown) {
            bootbox.alert("服务器异常，请稍后再试一试");
      }
    });
}

