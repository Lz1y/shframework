var CourseFormDropzone = function() {
	return {
		//main function to initiate the module
		init : function() {
			Dropzone.options.myDropzone = {
        		maxFiles: 1,
        		maxFilesize: 20,
        		acceptedFiles: "application/pdf,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.txt",
        		dictDefaultMessage: "到拽到此即可上传文件",
        		dictInvalidFileType: "暂不支持该格式",
        		dictFileTooBig: "文件大小不得大于20M",
        		dictDefaultMessage: "还没有上传文件",
        		dictResponseError: "系统异常, 请稍后再试",
        		dictMaxFilesExceeded: "先删除之前的文件",

				init : function() {
					this.on("addedfile", function(file) {
						// Create the remove button
						var removeButton = Dropzone.createElement("<button class='btn btn-sm btn-block'>Remove file</button>");

						// Capture the Dropzone instance as closure.
						var _this = this;

						// Listen to the click event
						removeButton.addEventListener("click", function(e) {
							// Make sure the button click doesn't submit the form:
							e.preventDefault();
							e.stopPropagation();
							
							// Remove the file preview.
							_this.removeFile(file);
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