/**
 * 系定的场地录入，排课设置 保存。
 * @param {Object} classroomId
 * @param {Object} urlSave
 * @param {Object} urlRedirect
 */
function saveClassroomEntryDo(urlSave,urlRedirect){

    if(!$("#classroomEntryForm").valid()){
      return;
    }
     
    var allocIdArr = new Array();
    var classroomIdArr = new Array();
    var $allocId = $("input[name='allocId']");
    var $classroomId = $("input[name='classroomId']");
    $allocId.each(function(index,item){
      allocIdArr.push($(item).val());      
    });  
    var flag = "false";
    $classroomId.each(function(index,item){
      if($(item).val()== 'undefined' || $.trim($(item).val()) == 0 || $.trim($(item).val()) == ''){
        flag = "true";
      }
      classroomIdArr.push($(item).val());      
    });  

    if(flag == "true"){
      bootbox.alert("教学场地不能为空，请选择教学场地!");
      return false;
    }
      var param = {
          allocId:allocIdArr.toString(),
          classroomId:classroomIdArr.toString()
      };
            
      jQuery.ajax({
          url : urlSave,
          method: "POST",
          async:true, 
          cache:false, 
          dataType : "json",
          data :param,
          success : function(data,textStatus,jqXHR ) {
              var returnFlag = data.result;
              if("conflict"==returnFlag){
                bootbox.alert("您设置的排课存在教学场地时间冲突，请重新选择教学场地!");
                return false;
              }
              if("FAIL"==returnFlag){
                bootbox.alert("操作失败！");
                return false;
              }
              if("OK"==returnFlag){
                window.location.href = urlRedirect;
              }
              
           },
          error : function( jqXHR,  textStatus,  errorThrown) {
                bootbox.alert("服务器请求失败!");
          }
        });
    }