
function batchSavePrintTask(urlSave){
  var $cbkfieldArray = $("input[type='checkbox'][name='cbkfield']:checked");
  if($cbkfieldArray.length == 0){
    bootbox.alert("请至少勾选一条记录");
    return false;
  }
  var detailIdArr = new Array();
  $cbkfieldArray.each(function(index,item){
    detailIdArr.push($(item).val());
   });
  var args = { printTaskId: detailIdArr };
  StandardPost(urlSave,args);         
}


