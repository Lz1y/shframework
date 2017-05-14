/**
 * @param {Object} num
 */
function chk(num){ 
  return num?num%2?"1":"2":"0" ;
} 


// 设置“辅助教师”字的位置，默认和删除时都在第一个输入框左边
function set_position(){
  var $objArray = $("input[name='assistantName']");
  if($objArray != null && $objArray != 'undefined' && $objArray.length > 0){
  
    var $obj = $objArray.first();
    var $obj1 = $obj.parent().prev();
    $obj1.html("辅助教师：");
    $("#add_btn").html("");
  }else{
    $("#add_btn").html("辅助教师：");
  }
}



function addAssistant(obj,i,departmentId){
    var $btn = $(obj);
    
     var htmldiv = '<div class="row deleteteacher">';
     
     htmldiv += '<div class="col-md-6"><div class="form-group"><label class="control-label col-md-4"></label><div class="col-md-8">';
     
     htmldiv += '<input type="text"  name="assistantName" class="assistantName_'+i+' form-control  ac_input selector1Class'+i+' " value="" maxlength="20" autocomplete="off" placeholder="">';
     htmldiv +=  '<input type="hidden" name="assistantId" class="assistantId_'+i+' selector2Class'+i+'" >';                         
     
     htmldiv += '</div></div></div>';
     
     htmldiv += '<div class="col-md-6"><div class="form-group"><div class="col-md-8">';
     
     htmldiv += '<a class="btn default btn-xs red delete" href="javascript:;" style="margin-top:2%;" onclick="bindListener(this);"> 删除 <i class="fa fa-trash"></i></a>';
                    
     htmldiv += '</div></div></div>';
                
     htmldiv += '</div>'; 
     
    $("#addAssistantId").append(htmldiv); 
  
    init_autocomplete_employee_ajax(".assistantName_"+i, ".assistantId_"+i,departmentId,true);
    i++;
    
    set_position();
}

function bindListener(obj){
  $(obj).parents(".deleteteacher").remove();
  set_position();
}

function saveSetTaskForm(url,tchClazzId){
  
  // 判断教学班名称是否与数据库中的数据重复
    var title = $.trim($("input[name='title']").val());
    var param = {
        tchClazzId:tchClazzId,
        title:title
     };
  
   jQuery.ajax({
      url : url,
      method: "POST",
      async:true, 
      cache:false, 
      data :param,
      dataType: "json", 
      success: function(data,textStatus,jqXHR){
            if(data.success=="true"){
                bootbox.alert("教学班名称不允许重复");
                $("input[name='title']").focus();
                return false;
            }else{
                $("form[name='taskclazzform']").submit();
            }
       },
      error : function( jqXHR,  textStatus,  errorThrown) {
          bootbox.alert("服务器请求失败!");
      }
    }); 
  
}



    function saveClazzAllocDo(chpw,employeeId,urlSave,urlRedirect){
    
          if(!$("#clazzallocform").valid()){
              return;
          }

          //第一个是隐藏的，只添加节次，克隆时用。
          var hidden2Arr = $("#clazzallocform").find("div.deleteChildren");  
          if(hidden2Arr.length==0 && chpw !=0){
            bootbox.alert("请先设置上课节次！");
            return ;
          }
          //周上课节次之和
          var sumPeriod = 0;  
          
          var weekSeqStartArr = new Array();
          var weekSeqEndArr = new Array();
          var oddDualWeekArr = new Array();  
          var weekDayArr = new Array();  
          var continuousPeriodArr = new Array();
          var periodIdArr = new Array();
          
          var submitFlag = true;
          $(hidden2Arr).each(function(index,item){  
          
            //【完成】时校验，开始周必须>=1正整数，截止周必须>=1正整数，截止周>=开始周且要<=学年学期的自然周数;
            var $weekSeqStart = "input[name='weekSeqStart']";
            var $weekSeqEnd = "input[name='weekSeqEnd']";
            var weekSeqStart = Number($(item).find($weekSeqStart).val());
            var weekSeqEnd = Number($(item).find($weekSeqEnd).val());
            if(weekSeqStart>weekSeqEnd){
               bootbox.alert("截止周必须大于等于开始周，请重新输入!");
               submitFlag = false;
               return false;
            }
            //周安排
            var $oddDualWeek = "select[name='oddDualWeek'] option:selected";
            //周几
            var $weekDay = "select[name='weekDay'] option:selected";
            //开始节次
            var $periodId = "select[name='periodId'] option:selected";
            
            var oddDualWeek = Number($(item).find($oddDualWeek).val());
            var weekDay = Number($(item).find($weekDay).val());
            var periodId = Number($(item).find($periodId).val());
            //单周上
            if(oddDualWeek==1){
              if(chk(weekSeqStart)!=1 || chk(weekSeqEnd)!=1){
                bootbox.alert("周安排选择单周上时，开始周与截止周必须都输入奇数!");
                submitFlag = false;
                return ;
              }
            }
            
            //双周上
            if(oddDualWeek==2){
               if(chk(weekSeqStart)!=2 || chk(weekSeqEnd)!=2){
                bootbox.alert("周安排选择双周上时，开始周与截止周必须都输入偶数!");
                submitFlag = false;
                return ;
              }
            }
            
            //【完成】时要校验，开始节次+几节连排<17。否则提醒几节连排和开始节次冲突，请重新设置。
            var $continuousPeriod = "select[name='continuousPeriod'] option:selected";
            var continuousPeriod = Number($(item).find($continuousPeriod).val());
            if((periodId+continuousPeriod)>17){
                bootbox.alert("几节连排和开始节次冲突，请重新设置!");
                submitFlag = false;
                return ;
            }
            
            //rp图注释：每周上课节次之和 = sum（每周上课的节次） + sum（单周或双周上课的节次）/2。得出来的周上课节次之和必须与任务中的周学时数一致，必须一致
           //周上课节次之和 = sum（每周上课的节次） + sum（单周或双周上课的节次）/2，是否等于“该任务的周学时数”。
           //在后台Ajax校验

            //需要提交到后台的数据
            weekSeqStartArr.push(weekSeqStart);
            weekSeqEndArr.push(weekSeqEnd);
            oddDualWeekArr.push(oddDualWeek); 
            weekDayArr.push(weekDay);
            continuousPeriodArr.push(continuousPeriod);
            periodIdArr.push(periodId);
 
          }); 
         
         if(submitFlag){
         
          var param = {
              weekSeqStart:weekSeqStartArr.toString(),
              weekSeqEnd:weekSeqEndArr.toString(),
              oddDualWeek:oddDualWeekArr.toString(),
              weekDay:weekDayArr.toString(),
              continuousPeriod:continuousPeriodArr.toString(),
              periodId:periodIdArr.toString(),
              employeeId:employeeId
          };
                
          jQuery.ajax({
              url : urlSave,
              method: "POST",
              async:false, 
              cache:false, 
              dataType : "json",
              data :param,
              success : function(data,textStatus,jqXHR ) {
                  var returnList = data.result;
                  var returnFlag = "";
                  $.each(returnList,function(i,value){
                    if(i==0){
                        if(value=="conflict"){
                          returnFlag=value;
                        }
                        if(value=="unequal"){
                          returnFlag=value;
                        }
                        if(value=="failure"){
                          returnFlag=value;
                        }
                        if(value=="OK"){
                          returnFlag=value;
                        }
                    }
                  });
                  
                  if("conflict"==returnFlag){
                    bootbox.alert("您设置的上课节次存在主讲教师时间冲突，请重新选择!");
                    return false;
                  }
                  if("unequal"==returnFlag){
                    bootbox.alert("您设置的周上课节次之和不等于周学时数，不能完成，必须持续添加或者修改节次，直至周上课节次之和=周学时数为止！");
                    return false;
                  }
                  if("failure"==returnFlag){
                    bootbox.alert("操作失败!");
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
    }

/**
 * 1、检查当前时间和该教学班的第一节授课时间，如果当前时间已经在该教学班的第一节授课时间之后了，提示“该教学班已经开始上课，不允许编辑教学任务，如需调整，请走调课流程”。
 * 2、检查该教学班是否属于某些选课批次，如果当前时间在所属的任意一个批次的开始时间之后，如果是，只能修改主讲副讲，其他的都不能动。
 * @param {Object} url
 * @param {Object} isAllScheduleISEnd 检查全校的教学计划是否审批通过
 * @param {Object} isDeptPeriodExists 检查可选课上课时间段设置状态
 */
function editOptionalsettask(url,isAllScheduleISEnd){
   if(isAllScheduleISEnd != 0){
      var msg = "仍然有部分院系的教学计划没有审批结束，他们的教学计划可能会影响到您的工作，也许您已经完成的开课计划或教学任务的设置会受到牵连，有推翻重做的可能，您是否还要继续？";
      bootbox.confirm(msg,function(result){
          if(!result){
            return;
          }else{
            window.location.href = url;
          }
      }); 
  }else{
    window.location.href = url;
  }
}

/**
 * 如果之前已经设置了主讲和上课节次，修改时，换新主讲，可能存在新主讲的上课时间冲突的问题。检查是否存在主讲教师时间冲突。
 * 如果冲突了，提醒“主讲教师上课时间冲突，请修改主讲教师，或者修改该教学班的上课时间。”，仅提醒，不强制要求不通过。
 * @param {Object} url
 * @param {Object} tchClazzId
 */
function saveDirectAccessDo(url,tchClazzId){
  
  // 判断教学班名称是否与数据库中的数据重复
    var employeeId = $.trim($("input[name='employeeId']").val());
    var param = {
        tchClazzId:tchClazzId,
        employeeId:employeeId
     };
  
   jQuery.ajax({
      url : url,
      method: "POST",
      async:true, 
      cache:false, 
      data :param,
      dataType: "json", 
      success: function(data,textStatus,jqXHR){
            if(data.result=="true"){
              var tip = "主讲教师上课时间冲突，请修改主讲教师，或者该教学班的上课时间。";
              bootbox.confirm(tip,function(result){
                if(!result){
                  return;
                }else{
                  $("form[name='taskclazzform']").submit();
                }
              }); 
            }else{
                $("form[name='taskclazzform']").submit(); 
            }
       },
      error : function( jqXHR,  textStatus,  errorThrown) {
          bootbox.alert("服务器请求失败!");
      }
    }); 
  
}


