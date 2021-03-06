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

			$("#country_list").select2({
				placeholder : "Select",
				allowClear : true,
				formatResult : format,
				formatSelection : format,
				escapeMarkup : function (m) {
					return m;
				}
			});

			var form = $('#submit_form');
			var error = $('.alert-danger', form);
			var success = $('.alert-success', form);

			form.validate({//FIXME！
				doNotHideMessage : true, //this option enables to show the error/success messages on tab switch.
				errorElement : 'span', //default input error message container
				errorClass : 'help-block help-block-error', // default input error message class
				focusInvalid : false, // do not focus the last invalid input
				rules : {
					//account
					filepath : {
						file:true
					},
					
				},

				messages : { // custom messages for radio buttons and checkboxes
					
				},

				errorPlacement : function (error, element) { // render error placement for each input type
					if (element.attr("name") == "gender") { // for uniform radio buttons, insert the after the given container
						error.insertAfter("#form_gender_error");
					} else {
						error.insertAfter(element); // for other inputs, just perform default behavior
					}
				},

				invalidHandler : function (event, validator) { //display error alert on form submit   
					success.hide();
					error.show();
					Metronic.scrollTo(error, -200);
				},

				highlight : function (element) { // hightlight error inputs
					$(element).closest('.form-group').removeClass('has-success').addClass('has-error'); // set error class to the control group
				},

				unhighlight : function (element) { // revert the change done by hightlight
					$(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
				},

				success : function (label) {
					if (label.attr("for") == "gender" || label.attr("for") == "payment[]") { // for checkboxes and radio buttons, no need to show OK icon
						label.closest('.form-group').removeClass('has-error').addClass('has-success');
						label.remove(); // remove error label here
					} else { 
						// display success icon for other inputs
						// mark the current input as valid and display OK icon
						label.addClass('valid').closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
					}
				},

				submitHandler : function (form) {
					success.show();
					error.hide();
					//add here some ajax code to submit your form or just call form.submit() if you want to submit the form without ajax
				}

			});

			var displayConfirm = function() {
				$('#tab4 .form-control-static', form).each(function(){
					var input = $('[name="' + $(this).attr("data-display") + '"]', form);
					if (input.is(":radio")) {
						input = $('[name="' + $(this).attr("data-display") + '"]:checked', form);
					}
					if (input.is(":text") || input.is("textarea")) {
						$(this).html(input.val());
					} else if (input.is("select")) {
						$(this).html(input.find('option:selected').text());
					} else if (input.is(":radio") && input.is(":checked")) {
						$(this).html(input.attr("data-title"));
					} else if ($(this).attr("data-display") == 'payment[]') {
						var payment = [];
						$('[name="payment[]"]:checked', form).each(function(){ 
							payment.push($(this).attr('data-title'));
						});
						$(this).html(payment.join("<br>"));
					}
				});
			}

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
				} else {
					$('#form_wizard_1').find('.button-previous').show();
				}

				if (current >= total) {
					//最后一步的时候隐藏上一步按钮
					$('#form_wizard_1').find('.button-previous').hide();
					$('#form_wizard_1').find('.button-next').hide();
					$('#form_wizard_1').find('.button-submit').show();
				} else {
					$('#form_wizard_1').find('.button-next').show();
					$('#form_wizard_1').find('.button-submit').hide();
				}
				
				if (current == total - 1) {
					$('#form_wizard_1').find('.button-next').attr("class", "btn yellow button-next");
					$('#form_wizard_1').find('.button-next').text("导入");
					$('#form_wizard_1').find('.button-next').append($('<i class="m-icon-swapright m-icon-white"></i>'));
				} else {
			   	 	$('#form_wizard_1').find('.button-next').attr("class", "btn green-seagreen button-next");
					$('#form_wizard_1').find('.button-next').text("下一步");
					$('#form_wizard_1').find('.button-next').append($('<i class="m-icon-swapright m-icon-white"></i>'));
				}
				Metronic.scrollTo($('.page-title'));
			}

			// default form wizard
			$('#form_wizard_1').bootstrapWizard({
				'nextSelector' : '.button-next',
				'previousSelector' : '.button-previous',
				onTabClick : function (tab, navigation, index, clickedIndex) {
					return false;
					
				},
				onNext : function (tab, navigation, index) {
					if($("#fileValidate").val() != 1) {
						bootbox.alert('请先上传文件!');
						return false;
					}
					if (index==2){
						var $rootfieldOption = $("#rootfield option");
						var $dbffieldOption = $("#dbfield option");
						cleanMatchField();
						
						$rootfieldOption.each(function(){
							var rootfieldValue = $.trim($(this).val());
							var rootfieldText = $.trim($(this).text());
							//右侧遍历 ，再找到可匹配的值时， 调用 appendMatchField("#select2_sample5", mate, text);
							$dbffieldOption.each(function(){
								var dbffieldValue = $.trim($(this).val());
								var dbffieldText = $.trim($(this).text());
								if(rootfieldText == dbffieldText){
									var mate = rootfieldValue + '-' + dbffieldValue;
									var text = rootfieldText + ' - ' + dbffieldText;
									appendMatchField("#select2_sample5", mate, text);
									return false;
								}
								
							})
						})
						
					}
					if(index==4){
						  //console.log("前台传输 字段匹配结果、判定缴费依据设定 等给后台");
						  var contextPath=  $("#contextPath").val();
						  var scope = $("#scope").val();
						  var srcurl = contextPath+"/edu/roll/freshman/"+scope+"/all/import";//真正导入时的请求
						  
						  var templateId= $("#templateId").val();
						  var fileFormat= $("input[name='file_format']:checked").val();
						  
						   var param = {
								   templateId:templateId,
								   fileFormat:fileFormat,
								   isCover:$("input[name='isCover']:checked").val(),
								   matchingResult:$("#select2_sample5").val()//matchingResult
						        };
						   
						   //ajax 
						  jQuery.ajax({
								url : srcurl,
								method: "POST",
								async:false, 
								cache:false, 
					    		dataType : "json",
					    		data :param,
					    		success : function(data,textStatus,jqXHR ) {
					    			var htmlText = "";
					    			if (data.sucmsg!=null){
					    				htmlText =  "<strong>" + data.sucmsg + "</strong>";
					    			} else if (data.tip!=null){
					    				var temp = data.tip;
					    				var reg = /^\[.*\]$/;
					    				if (reg.test(temp))
					    				{
					    					temp = temp.substring(1, temp.length-1);
					    					$.each(temp.split(","), function(i, value){
					    						htmlText +=  "<strong>" + value + "</strong><br>";
					    					});
					    				}
					    			} else if (data.msg!=null){
					    				$.each(data.msg, function(i, value){
					    					htmlText +=  "<strong>" + value + "</strong><br>";
					    				});
					    			}
					    		   
					    			$( "#returnResult" ).html( htmlText );
					    		},
								error : function( jqXHR,  textStatus,  errorThrown) {
									 $( "#returnResult" ).html( "<strong>" + jqXHR.responseText + "</strong>" );
					    		}
					    	});	
					}
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
			}).hide();

			//apply validation on select2 dropdown value change, this only needed for chosen dropdown integration.
			$('#country_list', form).change(function () {
				form.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
			});
		}

	};

}();