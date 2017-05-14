//免修与奖励学分管理：学生免修登记
/**
 * 第一步：请选择要申请免修的学生
 */
function saveExmptstdentryform1(){
  var $form = $("form[name='exmptstdentryform1']");
  var tempDepartmentId = $("select[name='departmentId'] option:selected").val();
  var tempClazzId = $("select[name='clazzId'] option:selected").val();
  var tempStudentId = $("select[name='studentId'] option:selected").val();
  if ((typeof(tempDepartmentId) == "undefined" || $.trim(tempDepartmentId) == "")
    || (typeof(tempClazzId) == "undefined" || $.trim(tempClazzId) == "") || (typeof(tempStudentId) == "undefined" || $.trim(tempStudentId) == "")){
        bootbox.alert("请选择要申请免修的学生");
        return;
    }
 $form.submit();           
}

/**
 * 第二步：选择要申请免修的课程
 */
function saveExmptstdentryform2(){
  var $form = $("form[name='exmptstdentryformsubmit']");
  var $checked = $("input[name='studentCreditId']:checkbox:checked");

  if($checked.length == 0){
      bootbox.alert("请选择要申请免修的课程");
      return;
  }
 $form.submit();           
}

/**
 * 第三步：请选择学生获得的证书
 */
function saveExmptstdentryform3Do(url){
  var $form = $("form[name='exmptstdentryform3']");
  var $checked =  $("input[name='certStudentId']:radio:checked");
  if($checked.length == 0){
      bootbox.alert("请选择学生获得的证书");
      return;
  }
  
  var studentId = $("input[name='studentId']").val();
  var certStudentId = $("input[name='certStudentId']:radio:checked").val();
  var $courseId = $("input[name='courseId']");

  var courseIdArr = new Array();

  $courseId.each(function(index,item){
    courseIdArr.push(item.value);
  });  
  var param = {
        studentId:studentId,
        certStudentId:certStudentId,
        courseId:courseIdArr.toString()
   };
  
   $.ajax({
      url : url,
      method: "POST",
      async:true, 
      cache:false, 
      data :param,
      dataType: "json", 
      success: function(data,textStatus,jqXHR){
          if(data.result=="conflict1"){
            //1、如果免修的适用专业不是”各专业“或者该学生的本专业（当前学年学期的），提示“该学生的专业与免修证书中的适用专业不符，是否强制继续进行免修登记？“
            bootbox.confirm("该学生的专业与免修证书中的适用专业不符，是否强制继续进行免修登记？",function(result){
                if(!result){
                  return;
                }else{
                  $form.submit(); 
                }
             }); 
          }else if(data.result=="conflict2"){
             //1、如果免修的课程不是”随证书“或者前一页面选择的课程，提示”该学生申请免修的课程与免修证书中允许免修的课程不符，是否强制继续进行免修登记？“
            bootbox.confirm("该学生申请免修的课程与免修证书中允许免修的课程不符，是否强制继续进行免修登记？",function(result){
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
          bootbox.alert("服务器请求失败!");
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
  obj.value = Number( obj.value )> 0 ? Number( obj.value ) : (''); 
}