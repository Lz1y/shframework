/**
* table操作工具js
* 依赖于 jquery
* 依赖： common_util.js
* */
	//原文url： http://blog.darkthread.net/post-2011-11-23-use-left-right-key-to-movie-focus.aspx
	//存在问题： 对不规则的input上移动时，可能会有一些问题。
	//且操作上存在一些不人性化的地方		
	var baseIndex = 100;
	
	init_table_move_with_key = function(selector){
       			 $(selector).find("tr").each(function (r) {
       			 		$(this).find("td").each(function (c) {
       			 			$(this).find("input")
       			 					.attr("tabindex", r * 100 + c + baseIndex)
       			 					.addClass("cGridInput");
       			 		});
        		});
			
				$(selector).on("keydown", ".cGridInput", function (evt) {
					 var origTabIdx = parseInt($(this).attr("tabindex"));
            		 var tabIndex = origTabIdx;
            		 var key  = evt.keyCode;
            		 switch (key) {
                	case 38: //上
                    	tabIndex -= 100;
                    	break;
                	case 40: //下
                    	tabIndex += 100;
                    	break;
                	case 37: //左
                    	tabIndex--;
                    	break;
                	case 39: //右(未選取文字，且游標在最後方時才切換)
                    	tabIndex++;
                    break;
                	default:
                    	return;
            	}
            	if (origTabIdx != tabIndex) {
	                $(".cGridInput[tabindex=" + tabIndex + "]").focus();
                	return false;
	            }
            	return true;
			});
	}//init			
				
