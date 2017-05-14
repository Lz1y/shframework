 /**
  * 免修与奖励学分证书维护--添加证书
  */
 function saveCert(obj){
  var $form=$("form[name=certForm]");
  if($("[name='rewardCreditFlag']:radio:checked").val() == '1'){
    if($form.valid() && isRewardCreditFlag() && refreshRewardCredit("select[name='majorId'] option:selected", "select[name='certTypeId'] option:selected")){
      $form.submit();
    }
  }else{
     if($form.valid() && refreshRewardCredit("select[name='majorId'] option:selected", "select[name='certTypeId'] option:selected")){
      $form.submit();
     }
  }
}

 /**
  * 免修与奖励学分证书维护--修改证书
  * 
  */
 function updateCert(obj){
  var $form=$("form[name=certForm]");
  if($("[name='rewardCreditFlag']:radio:checked").val() == '1'){
    if($form.valid() && isRewardCreditFlag() && refreshRewardCredit("select[name='majorId'] option[selected]", "select[name='certTypeId'] option:selected")){
      //修改“能否免修课程”的时候注意：从”不允许“改为”允许“没关系，因为没有关联上具体的免修课程。
      //从“允许”修改为“不允许”时要注意，【保存】的时候检查该证书下是否有免修课程，如果有，提醒“该证书原绑定的可以免修的课程会被删除，您确定要修改为不能免修课程吗？”，用户继续点【确定】就删除该证书下的免修课程，【取消】就不操作取消。
      var certId = $("[name='certId']").val();
      if($("[name='exmptCourseFlag']:radio:checked").val() != '1'){
        
        $.ajax({
          url: getContextPath()+'/edu/cert/certgeneral/0/0/update/directaccess',
          method: "POST",
          async:true, 
          cache:false, 
          data :{certId:certId},
          dataType: "json", 
          success: function(data,textStatus,jqXHR){
              if(data.result=="true"){
                bootbox.confirm("该证书原绑定的可以免修的课程会被删除，您确定要修改为不能免修课程吗？",function(result){
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
              bootbox.alert("服务器开小差了， 请再试试");
          }
        }); 
      }else{
            $form.submit(); 
        }

    }
  }else{
     if($form.valid() && refreshRewardCredit("select[name='majorId'] option:selected", "select[name='certTypeId'] option:selected")){
      //修改“能否免修课程”的时候注意：从”不允许“改为”允许“没关系，因为没有关联上具体的免修课程。
      //从“允许”修改为“不允许”时要注意，【保存】的时候检查该证书下是否有免修课程，如果有，提醒“该证书原绑定的可以免修的课程会被删除，您确定要修改为不能免修课程吗？”，用户继续点【确定】就删除该证书下的免修课程，【取消】就不操作取消。
      var certId = $("[name='certId']").val();
      if($("[name='exmptCourseFlag']:radio:checked").val() != '1'){
        
        $.ajax({
          url: getContextPath()+'/edu/cert/certgeneral/0/0/update/directaccess',
          method: "POST",
          async:true, 
          cache:false, 
          data :{certId:certId},
          dataType: "json", 
          success: function(data,textStatus,jqXHR){
              if(data.result=="true"){
                bootbox.confirm("该证书原绑定的可以免修的课程会被删除，您确定要修改为不能免修课程吗？",function(result){
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
              bootbox.alert("服务器开小差了， 请再试试");
          }
        }); 
      }else{
            $form.submit(); 
        }
     }
  }
}

 function showSelectedDiv(){
  if($("[name='rewardCreditFlag']:radio:checked").val() == '1'){
    $("#credit_div").css("display",""); 
  }else{
    $("#credit_div").css("display","none");
  }
}

 //是否三证
 function showSelectedDiv2(){
  if($("[name='qualCertTypeFlag']:radio:checked").val() == '1'){
    $("#qualCertType_div").css("display",""); 
  }else{
    $("#qualCertType_div").css("display","none");
  }
}

//正整数输入框。替换输入，只保留 数字
function nums(obj){ 
  obj.value = obj.value.replace(/[^\d]/g,'');
 } 
 function numsBlur(obj){
   if($.trim(obj.value) != ""){
    obj.value = Number( obj.value ) >= 0 ? Number( obj.value ) : (''); 
   } 
 }
 
/**
 *能否奖励学分：允许时，执行检查。 
 */
function isRewardCreditFlag(){
  if($("[name='rewardCreditRequiredcb']:checkbox:checked").val() == 'rewardCreditRequiredcb'){
    var rewardCreditRequired = Number($("input[name='rewardCreditRequired']").val());
    if(rewardCreditRequired < 0 || rewardCreditRequired > 100){
      bootbox.alert("奖励必修课学分不能为空，且必须是大于等于0小于等于100的整数");
      return false;
    }
  }
  if($("[name='rewardCreditOptionalcb']:checkbox:checked").val() == 'rewardCreditOptionalcb'){
    var rewardCreditOptional = Number($("input[name='rewardCreditOptional']").val());
    if(rewardCreditOptional < 0 || rewardCreditOptional > 100){
      bootbox.alert("奖励选修课学分不能为空，且必须是大于等于0小于等于100的整数");
      return false;
    }
  }
  return true;
}

/**
 * 适用专业=”各专业“ + 证件类型=”高自考“，这个规则特殊，固定为：能免修课程，不能奖励学分。 
 * 免修课程也是”随证书“，成绩是”随证书“。
 * @param {Object} majorIdSelector
 * @param {Object} certTypeIdSelector
 */
function refreshRewardCredit(majorIdSelector, certTypeIdSelector){
  var majorIdText = $.trim($(majorIdSelector).text()); 
  var certTypeIdText = $.trim($(certTypeIdSelector).text()); 
  if(majorIdText == "各专业" && certTypeIdText == "高自考"){
    $("input[name='certTypeTitle']").val("高自考");
    //能免修课程
     if($("[name='exmptCourseFlag']:radio:checked").val() != '1'){
      bootbox.alert("适用专业是各专业、证件类型是高自考时，固定为：允许免修课程，不允许奖励学分");
      return false;
    }
    //不能奖励学分
     if($("[name='rewardCreditFlag']:radio:checked").val() != '0'){
      bootbox.alert("适用专业是各专业、证件类型是高自考时，固定为：允许免修课程，不允许奖励学分");
      return false;
    }
  }
  return true;
}


/**
 *免修与奖励学分证书维护--新增免修课程 
 */
function saveAddExemptionFormDo(obj,url){
  
  $("input[name='doSave']").val("saveAddExemptionFormDo");
  var $form=$("form[name=addexemptionform]");
  $form.attr("action", url);
  if($("[name='score_radio']:radio:checked").val() == '0'){
    if(isAddExemptionForm()){
        if (!$form.valid()) {
            return;
        }
      $form.submit();
    }
  }else{
    var $checked = $("input[name='cbkfield']:checked");
    if($checked.length == 0){
        bootbox.alert("请先勾选课程！");
        return false;
    }
    $form.submit();
  }
  
}

function isAddExemptionForm(){
    var score = $.trim($("input[name='score']").val());
    if(score == "" || Number(score) < 0 || Number(score) > 100){
      bootbox.alert("认定成绩分数不能为空，且必须是大于等于0小于等于100的整数");
      return false;
    }
    var $checked = $("input[name='cbkfield']:checked");
    if($checked.length == 0){
        bootbox.alert("请先勾选课程！");
        return false;
    }
  return true;
}

/**
 * 全选、取消全选
 * @param {Object} ele
 */

function checkAll(ele){
  changeCheckBoxTemp("input[name='cbkfield']:enabled", ele.checked);
}
        
function changeCheckBoxTemp(selector, isChecked){
  if(isChecked) {
    $(selector).each(function(){
      this.setAttribute("checked", "checked");
      this.parentNode.setAttribute("class", "checked");
    });
  } else {
    $(selector).each(function(){
      this.removeAttribute("checked");
      this.parentNode.setAttribute("class", "");
    });
  }
}