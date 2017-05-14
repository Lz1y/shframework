//免修与奖励学分管理：学生申请奖励学分登记
/**
 * 第一步：选择申请奖励学分的学生
 */
function saveRewdstdentryform1(){
  var $form = $("form[name='rewdstdentryform1']");
  var tempDepartmentId = $("select[name='departmentId'] option:selected").val();
  var tempClazzId = $("select[name='clazzId'] option:selected").val();
  var tempStudentId = $("select[name='studentId'] option:selected").val();
  if ((typeof(tempDepartmentId) == "undefined" || $.trim(tempDepartmentId) == "")
    || (typeof(tempClazzId) == "undefined" || $.trim(tempClazzId) == "") || (typeof(tempStudentId) == "undefined" || $.trim(tempStudentId) == "")){
        bootbox.alert("请选择要申请奖励学分的学生");
        return;
    }
 $form.submit();           
}

/**
 * 第二步：选择申请奖励必修课还是选修课学分
 */
function saveRewdstdentryform2(){
  var $form = $("form[name='rewdstdentryformsubmit']");
  var $doStep = $("input[name='doStep']");
  var coursePropertyId = "select[name='coursePropertyId'] option:selected";
  var coursePropertyIdText = $.trim($(coursePropertyId).text()); 
  if(coursePropertyIdText == "必修课"){
    $doStep.val(3);
  }else{
    $doStep.val(4);
  }
 $form.submit();           
}

/**
 * 第三步：请选择申请奖励学分的必修课
 */
function saveRewdstdentryform3(url){
  var $form = $("form[name='rewdstdentryform3']");
  var $checked = $("input[name='cbkfield']:checkbox:checked");

  if($checked.length == 0){
      bootbox.alert("请选择申请奖励学分的必修课");
      return;
  }
  $form.submit(); 
    
}

function saveRewdstdentryform4Do(url){
  var $form = $("form[name='rewdstdentryform4']");
  var $checked =  $("input[name='certStudentId']:radio:checked");
  if($checked.length == 0){
      bootbox.alert("请选择学生获得的证书");
      return;
  }
  var $checked =  $("input[name='certStudentId']:radio:checked");
  if($checked.length == 0){
      bootbox.alert("请选择学生获得的证书");
      return;
  }
  
  var studentId = $("input[name='studentId']").val();
  var certStudentId = $("input[name='certStudentId']:radio:checked").val();
  var $scheduleCourseId = $("input[name='cbkfield']");

  var coursePropertyIdText = $.trim($("select[name='coursePropertyIdText'] option:selected").text()); 


  var scheduleCourseIdArr = new Array();

  $scheduleCourseId.each(function(index,item){
    scheduleCourseIdArr.push(item.value);
  });  
  var param = {
        studentId:studentId,
        certStudentId:certStudentId,
        cbkfield:scheduleCourseIdArr.toString(),
        coursePropertyIdText:coursePropertyIdText
   };
  
   $.ajax({
      url : url,
      method: "POST",
      async:true, 
      cache:false, 
      data :param,
      dataType: "json", 
      success: function(data,textStatus,jqXHR){
          if(data.result=="conflict3"){
             //3、能奖励的学分>=课程学分之和。否则提示”能奖励的学分应不小于课程学分之和，请重新选择“，返回。
             bootbox.alert("能奖励学分应不小于课程学分之和，请重新选择");
             return ;
          }else if(data.result=="conflict2"){
             //2、1)之前选的课程性质是必修课时，这里选中的证书奖励必修课学分必须有值。
            //     2)之前选的课程性质是非必修课的，这里选中的证书奖励选修课学分必须有值，
           //否则，提示”奖励学分的课程性质与选中证书不符，请重新选择“
              bootbox.alert("奖励学分的课程性质与选中证书不符，请重新选择");
              return ;
          }else if(data.result=="conflict1"){
            //1、如果该证书的适用专业不是”各专业“或者该学生的本专业（当前学年学期的），提示“该学生的专业与申请奖励学分证书中的适用专业不符，是否强制继续进行奖励学分登记？“
            bootbox.confirm("该学生的专业与申请奖励学分证书中的适用专业不符，是否强制继续进行奖励学分登记？",function(result){
                if(!result){
                  return;
                }else{
                  $form.submit(); 
                }
             }); 
          }else{
              $form.submit(); 
          }
       },
      error : function( jqXHR,  textStatus,  errorThrown) {
          bootbox.alert("服务器请求异常，请稍后再试");
      }
    }); 
    
}


function showSelectedDiv(){
  if($.trim($("select[name='certTypeId'] option:selected").text()) == '高自考'){
    $("#courseTitle_div").css("display",""); 
  }else{
    $("#courseTitle_div").css("display","none");
  }
}

function nums(obj){ 
  obj.value = obj.value.replace(/[^\d]/g,'');
} 
function numsBlur(obj){ 
  obj.value = Number( obj.value ) > 0 ? Number( obj.value ) : (''); 
}