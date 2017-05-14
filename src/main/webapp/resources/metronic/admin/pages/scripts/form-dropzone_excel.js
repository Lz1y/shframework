var FormDropzone = function() {
	return {
		//main function to initiate the module
		init : function() {
			Dropzone.options.myDropzone = {
        		maxFiles: 1,
        		maxFilesize: 10,
        		acceptedFiles: ".xls,.xlsx",
        		dictDefaultMessage: "拖拽到此即可上传文件",
        		dictInvalidFileType: "不允许上传该类型的文件",
        		dictFileTooBig: "文件大小不得大于10M",
        		dictDefaultMessage: "请上传文件",
        		dictResponseError: "系统异常, 请稍后再试",
        		dictMaxFilesExceeded: "只允许上传一个文件，请删除之前的文件",

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
						 //用此标志检查是否上传了excel文件 modify by zhangjk
							$("#fileValidate").val(-1);

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