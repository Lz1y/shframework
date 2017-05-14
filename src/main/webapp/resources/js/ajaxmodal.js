!function ($) {
	
	"use strict"; // jshint ;_;
	
/**
	 * ajaxModal
	 * */
	var defaultAjaxModalOption = {
			id: null,
			url: true,
			modalOverflow:false,
			fullwidth:false,
			callback: false
	};
	
	$.ajaxModal = function(url) {
		var customOptions = {
			url: url
		};
		
		var options = $.extend({}, defaultAjaxModalOption, customOptions);
		
		$('body').modalmanager('loading');
		
		var $modal = $("#ajax-modal");
		if($modal.length == 0){
			var str = '<div data-backdrop="static" tabindex="-1" class="modal fade modal-ajax" id="ajax-modal"></div>';
			$modal = $(str).appendTo('body');
		}
		if(options.fullwidth){
			$modal.addClass("page-container");
		}
		
		$.ajaxSetup({ cache: false });//避免ie浏览器对ajax请求缓存
		$.ajax( {
	        "dataType": 'html',
	        "url": options.url,
	        "success": function(html){

				if (html == "no-login") {
					location.reload();		
					return;
				} 
				
	        	$modal.html(html);
				$modal.modal(options);
				
				setTimeout(function(){
					if( options.callback && typeof(options.callback) == 'function') {
						options.callback($modal);
					}
				}, 300);
				
				$(window).trigger('resize.modal');
	        },
	        "error":function(data){
	        	alert("加载页面失败,请重试！");
	        	return;
			}
	      } );
		
		return $modal;
	};
	
}(window.jQuery);