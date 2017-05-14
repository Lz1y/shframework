var FormWizard = function () {

	return {
		//main function to initiate the module
		init : function () {
			if (!jQuery().bootstrapWizard) {
				return;
			}

			function format(state) {
				if (!state.id) return state.text; // optgroup
				return "<img class='flag' src='../../assets/global/img/flags/" + state.id.toLowerCase() + ".png'/>&nbsp;&nbsp;" + state.text;
			}

			var form = $('#submit_form');
			var error = $('.alert-danger', form);
			var success = $('.alert-success', form);

			var handleTitle = function(tab, navigation, index) {
				var total = navigation.find('li').length;
				var current = index + 1;
				// set wizard title
				$('.step-title', $('#form_wizard_1')).text('Step ' + (index + 1) + ' of ' + total);
				// set done steps
				jQuery('li', $('#form_wizard_1')).removeClass("done");
				var li_list = navigation.find('li');
				for (var i = 0; i < index; i ++) {
					jQuery(li_list[i]).addClass("done");
				}

				if (current == 1) {
					$('#form_wizard_1').find('.button-previous').hide();
					$('#form_wizard_1').find('.button-next').show();
				} else {
//					$('#form_wizard_1').find('.button-previous').show();
				}

				if (current >= total) {
					$('#form_wizard_1').find('.button-previous').hide();
					$('#form_wizard_1').find('.button-next').hide();
					$('#form_wizard_1').find('.button-submit').show();
				} else {
					$('#form_wizard_1').find('.button-next').show();
					$('#form_wizard_1').find('.button-submit').hide();
				}
				
				if (current == total - 1) {
					$('#form_wizard_1').find('.button-next').attr("class", "btn yellow button-next");
//					$('#form_wizard_1').find('.button-previous').show();
					
					if($("[name=file_format]:radio:checked").val() == 'dbf'){
						$('#form_wizard_1').find('.button-next').text("导出").attr("onclick","export_dbf();");
					}else{
						$('#form_wizard_1').find('.button-next').text("导出").attr("onclick","export_excel();");
					}
					
					$('#form_wizard_1').find('.button-next').append($('<i class="m-icon-swapright m-icon-white"></i>'));
				} else{
					$('#form_wizard_1').find('.button-next').attr("class", "btn green-seagreen button-next");
			   	 	
			   	 	$('#form_wizard_1').find('.button-next').text("下一步").attr("onclick","");
			   	 	
					$('#form_wizard_1').find('.button-next').append($('<i class="m-icon-swapright m-icon-white"></i>'));
				}
//				Metronic.scrollTo($('.page-title'));
			}

			// default form wizard
			$('#form_wizard_1').bootstrapWizard({
				'nextSelector' : '.button-next',
				'previousSelector' : '.button-previous',
				onNext : function (tab, navigation, index) {
					success.hide();
					error.hide();
					if (form.valid() == false) {
						return false;
					}
					handleTitle(tab, navigation, index);
				},
				onPrevious : function (tab, navigation, index) {
					success.hide();
					error.hide();
					handleTitle(tab, navigation, index);
				},
				
				// wangkang modify 屏蔽tab点击
		        onTabClick: function (tab, navigation, index) {
					return false;
				},
				onTabShow : function (tab, navigation, index) {
					
					var total = navigation.find('li').length;
					var current = index + 1;
					var $percent = (current / total) * 100;
					$('#form_wizard_1').find('.progress-bar').css({
						width : $percent + '%'
					});
					
				}
			});

			$('#form_wizard_1').find('.button-previous').hide();
			$('#form_wizard_1 .button-submit').click(function () {
				// alert('Finished! Hope you like it :)');
			}).hide();

		}

	};

}();