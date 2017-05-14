/**
 * 级联组件初始化
 * @author RanWeizheng
 * 
 * 目前仅支持指定url的 ${contextPath}/dict/cascade/directaccess
 * 
 * 依赖js： 
 * 		jquery.cascadingdropdown.js
 * 		common_util.js
 */

/*
  		triggerSelector 触发对象
  		targetSelector 目标对象
  		triggerVal 触发对象的默认值
  		targetVal  目标对象的默认值 
  		type 类型，与DictCascadeServiceImpl 中的对应：
  		{
  		  "campusBuilding" : 校区-建筑物
  		  "departmentClazz": 院系-班级
  			"departmentMajor" : 院系-专业,
  			"categoryGroup" :	专业目录(categoryMajor)id 获取与其关联的专业大类(groupMajor),
  	 		"groupMajor" : 专业大类-专业,
  			"majorField" : 专业--专业方向,
  			"clazz"		: 专业方向-班级,
  			"clazzStudent" : 行政班-学生
  			"地区" 	: {
  				"city" 	: 省级 - 市级
  				"county"： 市级-县级
    				}
  		}
  		
  		url  获取级联信息的url ，默认是getContextPath() + '/dict/cascade/directaccess';
  		由于onLoaded的问题，目前不支持其他的。
*/

function init_CascadingDropDown(triggerSelector,targetSelector, triggerVal,targetVal, type, url){
	if (typeof(url) == "undefined" || $.trim(url) == ""){
		url = getContextPath() + '/dict/cascade/directaccess';
	}
	   				$(targetSelector).CascadingDropDown(
	   								  triggerSelector,   //触发的selector
	   								  url, //取得选项的url，必须返回json
		                              {
			                              textfield: 'title',   //返回json，对应的 文字
			                              valuefiled: 'id',    //返回json，对应的id
			                              postData: function () { //取得选项url的parameter
		    									return { 
		    										type: type,
		    										parentId:$(triggerSelector).val()};
		    								},
		    							  onLoaded: function(){//triggerVal,targetVal已经变为局部变量
		    							       if(triggerVal > 0  
		    							       		&& targetVal > 0
		    							       		&& $(triggerSelector).val() == triggerVal){
		    							          $(this).val(targetVal);
												  triggerVal = 0;
												  $(this).change();
												  
		    							       }//if
		    							  }//onLoaded
		            		});
		         
		        };  
