/**
 * 可选课的教学任务提交，批量提交 
 * @param {Object} obj
 * @param {Object} urlSave
 * @param {Object} urlRedirect
 */
function saveBatcheditDo(obj,urlSave,urlRedirect){

  var $checked = $("input[name='cbkfield']:checked");
  if($checked.length == 0){
      bootbox.alert("请先勾选教学任务");
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
          var info = "您批量提交了" + arr[0] + "条教学任务，跳过" + arr[1] +"条教学任务。";
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