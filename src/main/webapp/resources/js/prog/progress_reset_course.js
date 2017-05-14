/**
 *课程已安排页，“取消课程安排” 
 * @param {Object} obj
 * @param {Object} urlSave
 */
function reset_course_do(obj,urlSave){
  var warnStr = "是否取消此课程的周次安排？";
  bootbox.confirm(warnStr, function(result){
    if(!result) return;
    $("#progressBatchForm").attr("action",urlSave);
    $("#progressBatchForm").submit();
    
  });
}