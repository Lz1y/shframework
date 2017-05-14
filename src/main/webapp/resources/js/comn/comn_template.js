function saveExcelImportTemplateDo(module,urlSave,urlRedirect){
      var title = $("input[name='title']").val();
      if(""==$.trim(title)){
        bootbox.alert("请输入模板名称!");
        return;
      }
      var matchingResult = $("#select2_sample5").val();
      if(matchingResult == '' || matchingResult == ";"){
        bootbox.alert("请匹配字段，匹配结果不能为空!");
        return;
      }
      var desc = document.getElementsByTagName("textarea").description.value;

      var param = {
        file_format:$("#file_format").val(),
        fileFormat:$("input[name='fileFormat']").val(),
        type:$("input[name='type']").val(),
        title:title,
        description:desc,
        module:module,
        matchingResult:matchingResult
      };
     
    jQuery.ajax({
        url : urlSave,
        method: "POST",
        async:false, 
        cache:false, 
        dataType : "text",
        data :param,
        success : function(data,textStatus,jqXHR ) {
          if("true"==$.trim(data)){
            bootbox.alert("导入模板保存成功!",function(){
              window.location.href = urlRedirect;
            });
          }
          
          if("empty"==$.trim(data)){
            bootbox.alert("请先匹配字段!");
            return false;
          }
          if("conflict"==$.trim(data)){
            bootbox.alert("一个字段请勿多次匹配!");
            return false;
          }
          if("false"==$.trim(data)){
            bootbox.alert("导入模板保存失败!");
            return false;
          }
        },
        error : function( jqXHR,  textStatus,  errorThrown) {
           bootbox.alert("请求服务器失败!");
          }
          
        }); 
}