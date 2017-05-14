$(document).ready(function() {  
	//解决一个页面中存在多个form时的校验结果显示不对的问题。
	$(".valid_form").each(function(i){
		$(this).validate({
			 errorElement: 'span', //default input error message container
	         errorClass: 'font-red error-info-' + i, // default input error message class
	         meta: "validate"
		});
	})
});

$.extend($.validator.messages, {
	email: "请输入有效的电子邮箱"
});

jQuery.validator.addMethod("mobile", function(value, element) {     
    var tel = /^1(3|4|5|7|8)\d{9}$/;  
    return this.optional(element) || (tel.test(value));  
}, "请输入正确的手机号码");

jQuery.validator.addMethod("telcheck", function(value, element) {     
    var tel = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;  
    return this.optional(element) || (tel.test(value));  
}, "请输入正确的电话号码（或手机号码）");

jQuery.validator.addMethod("zint", function(value, element) {     
    var zint = /^[1-9]\d*$/;  
    return this.optional(element) || (zint.test(value));  
}, "请输入正整数");

jQuery.validator.addMethod("int", function(value, element) {     
    var int0 = /^([1-9]\d*|0)$/;  
  //  console.log("int"+"-->"+ (this.optional(element) || (int0.test(value))) );
    return this.optional(element) || (int0.test(value));  
}, "请输入非负整数");

jQuery.validator.addMethod("aint", function(value, element) {     
    var aint0 = /^-?[1-9]\d*$/;  
  //  console.log("int"+"-->"+ (this.optional(element) || (int0.test(value))) );
    return this.optional(element) || (aint0.test(value));  
}, "请输入非0整数");

jQuery.validator.addMethod("even", function(value, element) {     
    var even = /^\d*[02468]$/;  
    return this.optional(element) || (even.test(value));  
}, "请输入偶数");

//var ichar = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
//允许输入() : ,.!
jQuery.validator.addMethod("illchar", function(value, element) {     
    var ichar = /[`~@#$%^&*_+<>?"{}\/;'[\]]/im;
    return this.optional(element) || (!(ichar.test(value)));  
}, "非法字符");

jQuery.validator.addMethod("dot5", function(value, element) {     
    var ichar = /^([0-9]\d*)(?:\.[05]0?)?$/;
    return this.optional(element) || (ichar.test(value));  
}, "小数位只能是0.5");

jQuery.validator.addMethod("greaterThan", function(value, element, params) {
	if ($(params).val() != '') {    
        return isNaN(value) && isNaN($(params).val()) || (Number(value) >= Number($(params).val()));
    };
    return true; 
},'应当大于等于下限.');

jQuery.validator.addMethod("lessThan", function(value, element, params) {
	if ($(params).val() != '') {    
        return isNaN(value) && isNaN($(params).val()) || (Number(value) <= Number($(params).val()));
    };
    return true; 
},'应当小于等于上限');


jQuery.validator.addMethod("equalToSum", function(value, element, params) {
	
	var params1 = params.split("+")[0];
	var params2 = params.split("+")[1];
	
	//alert(Number(value) +"--"+ params1 +"--" + params2);
	
	if ($(params1).val() != '' && $(params2).val() != '') {    
        return isNaN(value) && isNaN($(params1).val()) && isNaN($(params2).val()) || (Number(value) == (Number($(params1).val()) + Number($(params2).val())) );
    };
    return true; 
},'应当是后两项相加之和.');

jQuery.validator.addMethod("equalToPwd", function(value, element, param) {
	var target = $( param );
	if ( this.settings.onfocusout ) {
		target.unbind( ".validate-equalTo" ).bind( "blur.validate-equalTo", function() {
			$( element ).valid();
		});
	}
	return value === target.val();
},'两次输入的密码不一致');

// 注意dbf文件字段的长度必须是英文且长度最大是10个英文字符。(需要判断长度,此校验在新增dbf导出模板时，使用)
jQuery.validator.addMethod("nameFormat", function(value, element) {     
	value = $.trim(value);
	var str = /^[a-zA-Z]+$/;
	return this.optional(element) || str.test(value); 
}, "请输入英文字符");


//正整数或一位小数 rwz
jQuery.validator.addMethod("zintOrDot1", function(value, element) {
	 var zintOrDot1 = /^([1-9]\d*|0)(\.\d)?$/;
	 return this.optional(element) || (zintOrDot1.test(value));  
	 
},'请输入正整数或一位小数');

/**
 * @author RanWeizheng
 * qq 号校验
 * 5-16位数字（自己猜的）
 */
jQuery.validator.addMethod("qq", function(value, element) {     
    var zint = /^[1-9]\d{4,15}$/;  
    return this.optional(element) || (zint.test(value));  
}, "QQ号码格式不正确");

/**
 * @author RanWeizheng
 * 微信号校验
 * http://kf.qq.com/touch/faq/1211147RVfAV121115f6JJnA.html
 * 微信帐号支持6-20个字母、数字、下划线和减号，必须以字母开头。例如：“weixin”、“qq_123”、“qq-123”。
 */
jQuery.validator.addMethod("wechat", function(value, element) {     
    var str = /^[a-zA-Z][a-zA-Z0-9_-]{5,19}$/;  
    return this.optional(element) || (str.test(value));  
}, "微信号码格式不正确");

/*学年学期
 * 形如 2016-1
 * */
jQuery.validator.addMethod("yeartermcode", function(value, element) {     
    var tel = /^[1-2]\d{3}-\d{1}$/;  
    return this.optional(element) || (tel.test(value));  
}, "请输入正确的学年学期代码（形如：2016-1）");

//请输入正确的并且不小于0的数字(至多保留两位小数)
jQuery.validator.addMethod("zintOrDot2", function(value, element) {
	 var zintOrDot1 = /^([1-9]\d*|0)(\.\d{0,2})?$/;
	 return this.optional(element) || (zintOrDot1.test(value));  
	 
},'请输入正确的并且不小于0的数字(至多保留两位小数)');

//请输入正确的并且不小于0不大于1的数字(至多保留两位小数)
jQuery.validator.addMethod("rate", function(value, element) {
	 var rate = /^[1]$|^0.\d{1,2}$/;
	 return this.optional(element) || (rate.test(value));  
	 
},'请输入正确的并且大于0并且小于或等于1的数字(至多保留两位小数)');

jQuery.validator.addMethod("idCardNo", function(value, element) { 
    return this.optional(element) || idCardNoUtil.checkIdCardNo(value); 
}, "请输入正确有效的身份证号码"); 

//请输入正确的并且不小于0的数字(小数仅允许0.5)
jQuery.validator.addMethod("zintOrDot5", function(value, element) {
	 var zintOrDot1 = /^([1-9]\d*|0)(\.[5])?$/;
	 return this.optional(element) || (zintOrDot1.test(value));  
	 
},'请输入正确的并且不小于0的数字(小数仅允许0.5)');

jQuery.validator.addMethod("intOrDot", function(value, element) {
	 var zintOrDot1 = /^(-?[1-9]\d*|0)(\.\d{0,2})?$/;
	 return this.optional(element) || (zintOrDot1.test(value));  

},'请输入数字(至多保留两位小数)');