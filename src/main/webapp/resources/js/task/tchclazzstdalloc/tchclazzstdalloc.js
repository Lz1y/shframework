/**
 * 不可选课教学班配学生：一键分配
 */
function saveBatcheditDo(obj,urlSave,urlRedirect){

  var $checked = $("input[name='cbkfield']:checked");
  if($checked.length == 0){
      bootbox.alert("请先勾选教学班，注意只有单班、合班，才能一键分配学生！");
      return;
  }
   
  var checkedArr=new Array();  
  $checked.each(function(index,item){  
      checkedArr.push(item.value);  
  }); 
            
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
        var returnList = data.result;
        var returnFlag = "";
        var msg = "";
        $.each(returnList,function(i,value){
          if(i==0){
              if(value=="FAIL"){
                returnFlag=value;
              }
              if(value=="OK"){
                returnFlag=value;
              }
          }
          msg = value;
        });
        if("FAIL"==returnFlag){
          bootbox.alert("操作失败。");
          $btn.removeAttr("disabled");
          return false;
        }
        if("OK"==returnFlag){
          var arr= msg.split("_");
          var info = "您一键分配了" + arr[0] + "个教学班的学生，跳过" + arr[1] +"个教学班。只有单班、合班、主讲教师是当前登录人，且从未分配过的教学班才能一键分配。";
          bootbox.alert(info,function(){
            $btn.removeAttr("disabled");
            window.location.href = urlRedirect;
          });
        }
        $btn.removeAttr("disabled");
     },
    error : function( jqXHR,  textStatus,  errorThrown) {
        bootbox.alert("服务器请求失败!");
        $btn.removeAttr("disabled");
    }
  });
            
}

/**
 * 分配
 * @param {Object} obj
 * @param {Object} urlSave
 * @param {Object} urlRedirect
 */
function saveTchclazzstdallocDo(obj,urlSave,urlRedirect){
  var $checked = $("input[name='cbkfield']:checked");
   
  var checkedArr=new Array();  
  $checked.each(function(index,item){  
      checkedArr.push(item.value);  
  }); 
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
        var returnList = data.result;
        var returnFlag = "";
        $.each(returnList,function(i,value){
          if(i==0){
              if(value=="FAIL"){
                returnFlag=value;
              }
              if(value=="conflict"){
                returnFlag=value;
              }
              if(value=="OK"){
                returnFlag=value;
              }
          }
        });
        if("FAIL"==returnFlag){
          bootbox.alert("操作失败。");
          $btn.removeAttr("disabled");
          return false;
        }
        if("conflict"==returnFlag){
          bootbox.alert("您当前勾选的学生，有已经分配到其他班的学生，请刷新页面重新勾选！");
          $btn.removeAttr("disabled");
          return false;
        }
        if("OK"==returnFlag){
            var info = "分配成功。";
            bootbox.alert(info,function(){
              $btn.removeAttr("disabled");
              window.location.href = urlRedirect;
            });
        }
        $btn.removeAttr("disabled");
     },
    error : function( jqXHR,  textStatus,  errorThrown) {
        bootbox.alert("服务器请求失败!");
        $btn.removeAttr("disabled");
    }
  });
 
}

/**
 * 重修学生分配
 * @param {Object} obj
 * @param {Object} urlSave
 * @param {Object} urlRedirect
 */
function saveRevTchclazzstdallocDo(obj,urlSave,urlRedirect){
  var $checked = $("input[name='cbkfield']:checked");
   
  var checkedArr=new Array();  
  $checked.each(function(index,item){  
      checkedArr.push(item.value);  
  }); 
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
        var returnList = data.result;
        var returnFlag = "";
        $.each(returnList,function(i,value){
          if(i==0){
              if(value=="FAIL"){
                returnFlag=value;
              }
              if(value=="conflict"){
                returnFlag=value;
              }
              if(value=="OK"){
                returnFlag=value;
              }
          }
        });
        if("FAIL"==returnFlag){
          bootbox.alert("操作失败。");
          $btn.removeAttr("disabled");
          return false;
        }
        if("conflict"==returnFlag){
          bootbox.alert("您当前勾选的学生，有已经分配到其他班的学生，请刷新页面重新勾选！");
          $btn.removeAttr("disabled");
          return false;
        }
        if("OK"==returnFlag){
            var info = "分配成功。";
            bootbox.alert(info,function(){
              $btn.removeAttr("disabled");
              window.location.href = urlRedirect;
            });
        }
        $btn.removeAttr("disabled");
     },
    error : function( jqXHR,  textStatus,  errorThrown) {
        bootbox.alert("服务器请求失败!");
        $btn.removeAttr("disabled");
    }
  });
 
}


