/*
 详细： 

$(targetID).CascadingDropDown(sourceID, actionPath, settings)
	targetID 即将自动填充选择列表的ID.
	sourceID change事件的下拉框ID（即父控件ID）.
	actionPath post数据的URL
	参数：
		promptText   下拉框第一个选项  默认: -- 请选择 --
		loadingText  当加载时.默认:加载中..
		errorText    出错时 默认: 加载失败.
		postData     post完成后的数据 如: 
						postData: function () { 
    						return { prefix: $('#txtPrefix').val(), customerID: $('#customerID').val() }; 
						} 
						将会 prefix=foo&customerID=bar 方式传参. 
						默认: 以序列化 serialize 一定要有控件的name属性 不然无法序列化
		onLoading (event) 正在加载……
		onLoaded (event) 加载完成后…… 

		textfield    对应数据库 值 字段 
		valuefiled   对应数据库 id 字段   默认：textfield: 'text',	valuefiled: 'value' 
*/

(function($) {
	$.fn.CascadingDropDown = function(source, actionPath, settings) {
		if (typeof source === 'undefined') {
			throw "no define source";
		}
		if (typeof actionPath == 'undefined') {
			throw "not define url";
		}
		var optionTag = '<option></option>';
		var config = $.extend( {}, $.fn.CascadingDropDown.defaults, settings);
		return this.each(function() {
				var $this = $(this);
				(function() {
					var methods = {
						clearItems : function() {
							$this.empty();
							if (!$this.attr("disabled")) {
								$this.attr("disabled", "disabled");
							}
						},
						reset : function() {
							methods.clearItems();
							$this.append($(optionTag).attr("value", "").text(config.promptText));
							$this.trigger('change');
						},
						initialize : function() {
							if ($this.children().size() == 0) {
								methods.reset();
							}
						},
						showLoading : function() {
							methods.clearItems();
							$this.append($(optionTag).attr("value", "").text(config.loadingText));
						},
						loaded : function() {
							$this.removeAttr("disabled");
							$this.trigger('change');
						},
						showError : function() {
							methods.clearItems();
							$this.append($(optionTag).attr("value", "").text(config.errorText));
						},
						post : function() {
							methods.showLoading();
							$.isFunction(config.onLoading) && config.onLoading.call($this);
							$.ajax({
								url : actionPath,
								type : 'POST',
								dataType : 'json',
								data : ((typeof config.postData == "function") ? config.postData(): config.postData) || $(source).serialize(),
								success : function(data) {
									methods.reset();
									$.each(data.data, function(i, k) {
											$this.append($(optionTag).attr("value", eval("k."+ config.valuefiled))
													                 .text(eval("k."+ config.textfield))
													     );
										
									});
									methods.loaded();
									$.isFunction(config.onLoaded) && config.onLoaded.call($this);
									
								},
								error : function() {
									methods.showError();
								}
							});
						}
					};

					$(source).change(function() {
						var parentSelect = $(source);
						if (parentSelect.val() != '') {
							methods.post();
						} else {
							methods.reset();
						}
					});
					methods.initialize();
				})();
			});
	}

	$.fn.CascadingDropDown.defaults = {
		promptText : '请选择...', //rwz change, orig'-- 请选择 --'
		loadingText : '加载中',
		errorText : '加载失败',
		postData : null,
		onLoading : null,
		onLoaded : null,
		textfield : 'text',
		valuefiled : 'value'
	}
})(jQuery);