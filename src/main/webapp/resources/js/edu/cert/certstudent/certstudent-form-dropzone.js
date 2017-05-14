var CertStudentFormDropzone = function() {
	return {
		//main function to initiate the module
		init : function() {
			Dropzone.options.myDropzone = {
            maxFiles: 5,
            maxFilesize: 5,
            acceptedFiles: "image/.jpeg,.png,.jpg",
            dictDefaultMessage: "拖拽到此即可上传图片",
            dictInvalidFileType: "不允许上传该类型的图片",
            dictFileTooBig: "图片大小不得大于5M",
            dictResponseError: "系统异常, 请稍后再试",
            dictMaxFilesExceeded: "最多允许上传5张图片",

				init : function() {
					this.on("addedfile", function(file) {
						// Create the remove button
						var removeButton = Dropzone.createElement("<button class='btn btn-sm btn-block'>移除</button>");

						// Capture the Dropzone instance as closure.
						var _this = this;

						// Listen to the click event
						removeButton.addEventListener("click", function(e) {
							// Make sure the button click doesn't submit the form:
							e.preventDefault();
							e.stopPropagation();
							
							// Remove the file preview.
              _this.removeFile(file);
              //证书管理（图片上传）modify by zhangjinkui
              var fileResponse = JSON.parse(file.xhr.response);
              var uploadPhotoName = fileResponse.uploadPhotoName + ",";
              
              var $uploadPhotoName = $("input[name='uploadPhotoName']");
              var uploadPhotoNameReal = $uploadPhotoName.val().replace(uploadPhotoName,"");
              $uploadPhotoName.val(uploadPhotoNameReal);
							// If you want to the delete the file on the server as well,
							// you can do the AJAX request here.
						});

						// Add the button to the file preview element.
						file.previewElement.appendChild(removeButton);
					});
				}
			}
		}
	};
}();