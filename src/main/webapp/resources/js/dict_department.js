/**院系树维护用
 * @author RanWeizheng
 * 依赖： dict_management.js
 */
var department_node_id;//临时存放当前选中结点id
var $content = $('.inbox-content');
var $title = $('.inbox-title');

function setDepartmentNodeId(id){
	department_node_id = id;
}

function getDepartmentNodeId(){
	return department_node_id;
}
				function loadContentInPage(sourceUrl, data){
        			//$content.html('');
			        $.ajax({
			            type: "POST",
			            cache: false,
			            url: sourceUrl,
			            dataType: "html",
			            data: data,
			            success: function(res) 
			            {
			                $content.html(res);
			                if (Layout.fixContentHeight) {
			                    Layout.fixContentHeight();
			                }
			            },
			            error: function(xhr, ajaxOptions, thrownError){
			                
			            },
			            async: true
			        });
							    
				}
				
				function openOrCloseAll(obj){
					var $this = $(obj);
					if ($this.text() == "全部展开"){					
						$("#tree_1").jstree().open_all();
						$this.text("全部收起");
					} else {
						$("#tree_1").jstree().close_all();
						$this.text("全部展开");
					}
					
				}
				
				function divisionPageRefresh(){
					var url = getContextPath() + "/dict/edu/common/department/0/0/all/list";
					var $form = $("<form id='changestatusForm' action='" + url + "' method='POST'></form>");
					var $input = $("<input type='hidden'  name='selectedId' value='" + department_node_id + "' />");
					$form.append($input);
					$form.appendTo("body").submit();
					
				}