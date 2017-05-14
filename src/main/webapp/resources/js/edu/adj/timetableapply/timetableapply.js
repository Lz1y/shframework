//教学运行：综合调整申请

/**
 * 教学运行：单个调整/修改申请/重修申请
 */
function saveTimetableDo(urlSave,$form){
  if(!$("#timetableform").valid()){
      return;
  }   
  var desc = document.getElementsByTagName("textarea").applyReason.value;
  if(desc == ""){
    bootbox.alert("请输入调整原因");
    return false;
  }
  
  //上课周
  var weekSeq = $("input[name='weekSeq']").val();
  //周几
  var weekDay = $("select[name='weekDay'] option:selected").val();
  //开始节次
  var periodId = Number($("select[name='periodId'] option:selected").val());   
  //教学周场地
  var classroomId = $("input[name='classroomId']").val();
  
  var employeeId = $("input[name='employeeId']").val();
  var continuousPeriod = Number($("input[name='continuousPeriod']").val());
  if((periodId+continuousPeriod)>17){
      bootbox.alert("几节连排和开始节次之和不能大于17，请重新设置!");
      return ;
  }
  var param = {
    employeeId:employeeId,
    weekDay:weekDay,
    periodId:periodId,
    weekSeq:weekSeq,
    classroomId:classroomId,
    applyReason:desc
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
          if("withoutSchoolCalendar"==returnFlag){
            var returnTip = data.tip;
            bootbox.alert("选择的上课日期不在校历范围内，请重新设置");
            return false;
          }
          if("FAIL"==returnFlag){
            bootbox.alert("操作失败");
            return false;
          }
          if("OK"==returnFlag){
              $form.submit();
              document.body.removeChild($form[0]);
          }
       },
      error : function( jqXHR,  textStatus,  errorThrown) {
            bootbox.alert("服务器异常，请稍后再试一试");
      }
    });
}

//批量修改主讲/批量修改地点
function saveBatchTimetableDo(batchEditType,urlSave,$form){
  if(!$("#batchtimetableapplyform").valid()){
      return;
  }   
  var detailIdArr = new Array();
    $("input[name='detailId']").each(function(index,item){  
    detailIdArr.push($(item).val());
   });
  var param = {};
  if(batchEditType =="classroom"){
  //教学周场地
    var classroomId = $("input[name='classroomId']").val();
    param = {
      batchEditType:batchEditType,
      classroomId:classroomId,
      detailId:detailIdArr.toString()
    };
  }else if(batchEditType =="major"){
      var employeeId = $("input[name='employeeId']").val();
      param = {
        batchEditType:batchEditType,
        employeeId:employeeId,
        detailId:detailIdArr.toString()
      };
  }else if(batchEditType =="assistant"){
      var desc = document.getElementsByTagName("textarea").applyReason.value;
      if(desc == ""){
        bootbox.alert("请输入调整原因");
        return false;
      }
      var $assistId = $("input[name='assistId']:checked");

      if($assistId.length == 0){
        bootbox.alert("请勾选一个辅助教师");
        return false;
      }
      param = {
        batchEditType:batchEditType,
        assistId:$assistId.val(),
        detailId:detailIdArr.toString(),
        applyReason:desc
      };
  }
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
            $form.submit();
            document.body.removeChild($form[0]);
          }
       },
      error : function( jqXHR,  textStatus,  errorThrown) {
            bootbox.alert("服务器异常，请稍后再试一试");
      }
    });
}

function batchSubmitDo(obj,urlSave,urlRedirect,$form){
  var $btn = $(obj);
  $btn.attr("disabled","disabled");   
  var detailIdArr = new Array();
  var $cbkfield = $("input[type='checkbox'][name='cbkfield']:checked");
  if($cbkfield.length == 0){
    $btn.removeAttr("disabled");  
    bootbox.alert("请至少勾选一个调整详情");
    return false;
  }
  
  var formData = $form.serialize();
  $.post( getContextPath()+"/edu/clr/classroomtaskapply/0/0/without/directaccess", formData , function( data ) {
    $btn.removeAttr("disabled");
    var returnFlag = data.result;
    if(returnFlag == "true"){
      bootbox.alert("批量提交申请，每条都必须输入调整原因");
      return false;
    }
    if(returnFlag == "false"){
       $.ajax({
        url : urlSave,
        method: "POST",
        async:false, 
        cache:false, 
        dataType : "json",
        data :formData,
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
              window.location.href = urlRedirect;
            }
         },
        error : function( jqXHR,  textStatus,  errorThrown) {
            $btn.removeAttr("disabled");
            bootbox.alert("服务器异常，请稍后再试一试");
        }
      });
    }
    if(returnFlag == "FAIL"){
      bootbox.alert("操作失败");
      return false;
    }
  }, "json");
  
}

