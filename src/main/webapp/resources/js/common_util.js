    /**
     * 通用js工具
     * @author RanWeizheng
     */
	function getContextPath(){
		var pathName = document.location.pathname;
	    var index = pathName.substr(1).indexOf("/");
	    var result = pathName.substr(0,index+1);
	    return result;
	}
	
	//导出遮罩
    //time 遮罩时间 默认1000 毫秒，可自定义
    function exportDataBlockUI(url, time){
        if(typeof(time) == 'undefined' || $.trim(time) == ''){
            time = 1000;
        }
        
        var context = '<h4><img src='+getContextPath()+'/resources/metronic/global/img/loading-spinner-blue.gif />&nbsp;下载中...</h4>';
        $.blockUI({ message: context});
        
        if(typeof(url) != 'undefined' || $.trim(url) != ''){
            window.location.href = url;
        }
        
        setTimeout(function(){
            $.unblockUI();
        }, time);   
    }
	
	/**
	 * 时间格式化
	 * @param formatStr
	 * @returns
	 * @author RanWeizheng
	 */
	formatDate= function(date, formatStr)   
	{   
	    var str = formatStr;   
	    var Week = ['日','一','二','三','四','五','六'];
	    
	    var year = date.getFullYear();
	    var month = date.getMonth() + 1;
	    var dateNum = date.getDate();
	    
	    str=str.replace(/yyyy|YYYY/,year);   
	    str=str.replace(/yy|YY/,(year % 100)>9?(year % 100).toString():'0' + (year % 100));   
	  
	    str=str.replace(/MM/,month>9?month.toString():'0' + month);   
	    str=str.replace(/M/g,month);   
	  
	    str=str.replace(/w|W/g,Week[date.getDay()]);   
	  
	    str=str.replace(/dd|DD/,dateNum>9?dateNum.toString():'0' + dateNum);   
	    str=str.replace(/d|D/g,dateNum);   
	  
	    str=str.replace(/hh|HH/,date.getHours()>9?date.getHours().toString():'0' + date.getHours());   
	    str=str.replace(/h|H/g,date.getHours());   
	    str=str.replace(/mm/,date.getMinutes()>9?date.getMinutes().toString():'0' + date.getMinutes());   
	    str=str.replace(/m/g,date.getMinutes());   
	  
	    str=str.replace(/ss|SS/,date.getSeconds()>9?date.getSeconds().toString():'0' + date.getSeconds());   
	    str=str.replace(/s|S/g,date.getSeconds());   
	  
	    return str;   
	}
	
	/**
	 * 判断数组是否存在重复元素
	 * @param ary
	 * @returns {Boolean}
	 */
	isArrayHasRepeat = function(ary){
		var nary=ary.sort();  
		  
		for(var i=0;i<ary.length;i++){  
		  
			if (nary[i]==nary[i+1]){  
			  
			    return true
			  
			}//if  
		}//for
		return false;
	}//isArrayHasRepeat
	
	//获取按键的code
	getKeyCode = function(evt){
		var key;
		if(evt.keyCode){  
			key = evt.keyCode;
		} else {
			key = evt.which;
		}
        return key;
	}//getKeyCode
