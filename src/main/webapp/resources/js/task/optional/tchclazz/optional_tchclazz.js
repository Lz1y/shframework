/**
 * 
 * @param {Object} url
 */   
function saveTchClazzForm(url){
  
  var title_array = new Array();
  $("input[name='titles']").each(function(){
      title_array.push($.trim($(this).val()));
    });
    
    // 检查教学班名称是否重复
    if (isArrayHasRepeat(title_array)){
      bootbox.alert("教学班名称不能重复。");
      return false;
    }
    
  // 判断教学班名称是否与数据库中的数据重复
    var param = {
        titles: title_array.toString()
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
                bootbox.alert("教学班名称不能重复。");
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















