/*
 * 以教学时间为基础。排课节次是教学时间的一个子集。
 */

function count () {
  var amperiod = $("select[name='amperiod'] option:selected").val();
  var pmperiod = $("select[name='pmperiod'] option:selected").val();
  var nightperiod = $("select[name='nightperiod'] option:selected").val();
  var amperiodValue = parseInt(amperiod);
  var pmperiodValue = parseInt(pmperiod);
  var nightperiodValue = parseInt(nightperiod);
  var perdayperiod = amperiodValue + pmperiodValue + nightperiodValue;
  $("#perdayperiod").html(perdayperiod);
}

/*
  //教学时间不可用的节次，排课的时候也不可用。每周第一天 默认“周一”。
  //校验冲突：比如上午节次选了6，但是在教学时间中，有某一天（每周天数）的上午节次没有勾选满6节，那么就无法满足排课节次是教学时间的子集的要求了
  //这时候，保存提示“排课节次是教学时间的子集，请重新设置。”。  
 */
function saveFormDo(urlSave,urlRedirect){
  var perweekday = $("select[name='perweekday'] option:selected").val();
  var amperiod = $("select[name='amperiod'] option:selected").val();
  var pmperiod = $("select[name='pmperiod'] option:selected").val();
  var nightperiod = $("select[name='nightperiod'] option:selected").val();
  if(!$("#cskdperiodsettingsForm").valid()){
          return;
  }
     
  var param = {
      perweekday:perweekday,
      amperiod:amperiod,
      pmperiod:pmperiod,
      nightperiod:nightperiod
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
                if(value=="conflict"){
                  returnFlag=value;
                }
                if(value=="FAIL"){
                  returnFlag=value;
                }
                if(value=="OK"){
                  returnFlag=value;
                }
            }
            msg = value;
          });
          
          if("conflict"==returnFlag){
            bootbox.alert(msg+"不满足条件,排课节次是教学时间的子集，请重新设置。");
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
