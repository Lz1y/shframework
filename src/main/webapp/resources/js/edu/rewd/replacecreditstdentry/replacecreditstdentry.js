//免修与奖励学分管理：学生学分互认登记
function saveReplacecreditstdentryform1(){
  var $form = $("form[name='replacecreditstdentryform1']");
  var tempDepartmentId = $("select[name='departmentId'] option:selected").val();
  var tempClazzId = $("select[name='clazzId'] option:selected").val();
  var tempStudentId = $("select[name='studentId'] option:selected").val();
  if ((typeof(tempDepartmentId) == "undefined" || $.trim(tempDepartmentId) == "")
    || (typeof(tempClazzId) == "undefined" || $.trim(tempClazzId) == "") || (typeof(tempStudentId) == "undefined" || $.trim(tempStudentId) == "")){
        bootbox.alert("请选择要申请学分互认的学生");
        return;
    }
 $form.submit();           
}

function saveReplacecreditstdentryform2(url){
  var $form = $("form[name='replacecreditstdentryform2']");
  var $checked =  $("input[name='studentCreditId']:radio:checked");
  if($checked.length == 0){
      bootbox.alert("请选择要申请学分互认的课程");
      return;
  }
  $form.submit(); 
}

function nums(obj){ 
  obj.value = obj.value.replace(/[^\d]/g,'');
} 
function numsBlur(obj){ 
  obj.value = Number( obj.value ) > 0 ? Number( obj.value ) : (''); 
}