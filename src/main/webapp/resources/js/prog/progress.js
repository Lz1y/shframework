 /**
  * 教学进程：系进程管理员，拥有“上报”权限。
 * @param {Object} obj
 * @param {Object} progressId
 * @param {Object} urlSave
 * @param {Object} urlRedirect
  */
function report_progress_do(obj,progressId,urlSave,urlRedirect){
  var warnStr = "教学进程一旦上报，您将失去操作权限。您确定要上报该教学进程吗？";
  bootbox.confirm(warnStr, function(result){
    if(!result) return;
    
    var $btn = $(obj);
    $btn.attr("disabled","disabled");
    $.ajax({
      type:'POST',
      url: urlSave,
      async:true, 
      cache:false, 
      data: {},
      dataType: "text", 
      success: function(msg,textStatus,jqXHR){
          if(msg.toUpperCase() == 'OK') {
              bootbox.alert("上报成功。",function(){
              $btn.removeAttr("disabled");
              window.location.href = urlRedirect;
            });
          }else if (msg.toUpperCase() == 'ISSCHEDULED'){ // 2）左侧列表，属于本校区，本系班级，本系开的课程是否都安排了3）左侧列表，属于本校区，他系班级，本系要上的课程是否都安排了
              bootbox.alert("您还有本系开的课程或本系要上的课程尚未安排，请检查。");
              $btn.removeAttr("disabled");
              return false;
          }else if (msg.toUpperCase() == 'TIMEERR'){
              bootbox.alert("您还未启动该校区该学年学期的教学进程，或不在教学进程制定要求的时间范围，请联系校区教务。");
              $btn.removeAttr("disabled");
              return false;
          }
          else if (msg.toUpperCase() == 'FAILURE'){
              bootbox.alert("上报失败。");
              $btn.removeAttr("disabled");
              return false;
          }
          else {
            bootbox.alert("操作失败！");
            $btn.removeAttr("disabled");
          }
            $btn.removeAttr("disabled");
        },
        error: function(jqXHR,  textStatus,  errorThrown){
          bootbox.alert("服务器处理失败！");
          $btn.removeAttr("disabled");
        }
    });
    
  });
}

/**
 *校区教务即校区进程管理员，才有“下发”权限。 
 * @param {Object} obj
 * @param {Object} progressId
 * @param {Object} urlSave
 * @param {Object} urlRedirect
 */
function deliver_progress_do(obj,progressId,urlSave,urlRedirect){
  var warnStr = "教学进程一旦下发，您将失去操作权限。您确定要下发该教学进程吗？";
  bootbox.confirm(warnStr, function(result){
    if(!result) return;
    
    var $btn = $(obj);
    $btn.button("disabled","disabled");
    $.ajax({
      type:'POST',
      url: urlSave,
      async:true, 
      cache:false, 
      data: {},
      dataType: "text", 
      success: function(msg,textStatus,jqXHR){
          if(msg.toUpperCase() == 'OK') {
              bootbox.alert("下发成功。",function(){
              $btn.removeAttr("disabled");
              window.location.href = urlRedirect;
            });
          }else if (msg.toUpperCase() == 'TIMEERR'){
              bootbox.alert("您还未启动该校区该学年学期的教学进程，或不在教学进程制定要求的时间范围，请联系校区教务。");
              $btn.removeAttr("disabled");
              return false;
          }
          else if (msg.toUpperCase() == 'FAILURE'){
              bootbox.alert("下发失败。");
              $btn.removeAttr("disabled");
              return false;
          }
          else if (msg.toUpperCase() == 'NONEXISTENCE'){
              bootbox.alert("该进程的校区、学年学期下，没有班级或课程，不允许下发。确保数据正确，重新创建进程下发即可。");
              $btn.removeAttr("disabled");
              return false;
          }
          else {
            bootbox.alert("操作失败！");
            $btn.removeAttr("disabled");
          }
           $btn.removeAttr("disabled");
        },
        error: function(jqXHR,  textStatus,  errorThrown){
          bootbox.alert("服务器处理失败！");
          $btn.removeAttr("disabled");
        }
    });
    
  });
}

function add_progress_do(obj,urlSave,urlRedirect){
    var $btn = $(obj);
    $btn.attr("disabled","disabled");
    $.ajax({
      type:'POST',
      url: urlSave,
      async:true, 
      cache:false, 
      data: {},
      dataType: "text", 
      success: function(msg,textStatus,jqXHR){
          if(msg.toUpperCase() == 'OK') {
              $btn.removeAttr("disabled");
              window.location.href = urlRedirect;
          }else if(msg.toUpperCase() == 'TIMEERR'){
              bootbox.alert("您还未启动该校区该学年学期的教学进程，或不在教学进程制定要求的时间范围，请联系校区教务。");
              $btn.removeAttr("disabled");
              return false;
          }else {
              bootbox.alert("操作失败！");
              $btn.removeAttr("disabled");
          }
            $btn.removeAttr("disabled");
        },
        error: function(jqXHR,  textStatus,  errorThrown){
          bootbox.alert("服务器处理失败！");
          $btn.removeAttr("disabled");
        }
    });
    
}