//证书管理：学生获得证书登记、学生获得证书审核

function saveCertstudentNext(){
  var $form = $("form[name='certstudentform']");
  var uploadPhotoName = $("input[name='uploadPhotoName']").val();
  if($form.valid()){
    var certScore = Number($("input[name='certScore']").val());
     if($.trim($("input[name='certScore']").val())!=''){
      if(certScore < 0 || certScore > 100){
        alert("证书成绩如果不为空，请输入大于等于0小于等于100的数值");
        return false;
      }
    }
    //图片上传，改为非必填。
    // if($.trim(uploadPhotoName) == ""){
        // bootbox.alert("请上传要申请备案的证书照片");
        // return;
    // }
   $form.submit();           
  }
}

function saveCertStudentEdit(){
  var $form = $("form[name='certstudentform']");
  var uploadPhotoName = $("input[name='uploadPhotoName']").val();
  var uploadPhotoNum = 0;
  if($.trim(uploadPhotoName) != ""){
    //111,222, -> .split(",").length == 3
    uploadPhotoNum = uploadPhotoName.split(",").length-1;
  }
  var certPhotoNumTotal = $("input[name='certPhotoId']").length;
  var certPhotoNum = $("input[name='certPhotoId']:checked").length;
  var limitNum = certPhotoNumTotal - certPhotoNum  + uploadPhotoNum;
  if(limitNum <= 5){
  }else{
    bootbox.alert("允许登记的证书照片应小于5张");
    return false;
  }
  
  if($form.valid()){
    var certScore = Number($("input[name='certScore']").val());
    if($.trim($("input[name='certScore']").val())!=''){
      if(certScore < 0 || certScore > 100){
        alert("证书成绩如果不为空，请输入大于等于0小于等于100的数值");
        return false;
      }
    }
   $form.submit();           
  }
}

function changeSelect(id){
  if(id == ""){
    
  }else{
      var url = getContextPath() + "/edu/cert/certstudentapply/0/0/change/directaccess";
      jQuery.ajax({
        url : url,
        method: "POST",
        async:true, 
        cache:false, 
          dataType : "json",
          data :{studentId:id},
          success : function(data,textStatus,jqXHR ) {
            $("#studentNo").text(data.studentNo);
          },
        error : function( jqXHR,  textStatus,  errorThrown) {
        
          }
      }); 
  }
}


function showSelectedDiv(){
  if($.trim($("select[name='certTypeId'] option:selected").text()) == '高自考'){
    $("#courseTitle_div").css("display",""); 
  }else{
    $("#courseTitle_div").css("display","none");
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