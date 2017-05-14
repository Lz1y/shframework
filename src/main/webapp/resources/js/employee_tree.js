/*只显示本层数据；树节点点击事件；全部展开、全部收起 事件。
 *依赖： common_util.js(获取contextPath)
 *教职工管理：zhangjk
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

function openOrCloseAll(obj){
	var $this = $(obj);
	if ($this.text() == "全部展开"){					
		$("#tree_1").jstree().open_all();
		$this.text("全部收起");
	}else {
		$("#tree_1").jstree().close_all();
		$this.text("全部展开");
	}
}