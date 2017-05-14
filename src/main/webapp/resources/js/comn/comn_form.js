/**
 * 用于自定义表单提交 
 * @param {Object} url
 * @param {Object} args
 */
function StandardPost (url,args) {
  var form = $("<form method='post'></form>");
  form.attr({"action":url});
  for (arg in args)
  {
      var input = $("<input type='hidden'>");
      input.attr({"name":arg});
      input.val(args[arg]);
      form.append(input);
  }
  form.appendTo(document.body);
  form.submit();
  document.body.removeChild(form[0]);
}
/**
 * @param {Object} url
 * @param {Object} args
 * @return form
 */
function sbform (url,args) {
  var form = $("<form method='post'></form>");
  form.attr({"action":url});
  for (arg in args)
  {
      var input = $("<input type='hidden'>");
      input.attr({"name":arg});
      input.val(args[arg]);
      form.append(input);
  }
  form.appendTo(document.body);
  return form;
}
    
