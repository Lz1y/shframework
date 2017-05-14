/*
 * JQuery zTree core v3.5.18
 * http://zTree.me/
 *
 * Copyright (c) 2010 Hunter.z
 *
 * Licensed same as jquery - MIT License
 * http://www.opensource.org/licenses/mit-license.php
 *
 * Date: 2015-05-25
 * 参考zTree_v3 范例：(ZTREE DEMO - select menu)
 */

	//组织机构树
	var setting = {
		async: {
			enable: true,
			type: "post",
			url:"../directaccess",  //异步加载节点数据
//			autoParam:[""],
			dataFilter: filter
		},
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onClick,
			beforeAsync: beforeAsync,
			onAsyncSuccess: onAsyncSuccess,
			onAsyncError: onAsyncError,				
			onExpand: onExpand
		}
	};	

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
		
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		$("#orgSel").val(treeNode.name);
		$("#orgId").val(treeNode.id);
		
		hideMenu();
	}

	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getCheckedNodes(true),
		v = "";
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		var cityObj = $("#orgSel");
		cityObj.attr("value", v);
	}
	function showMenu() {
		var cityObj = $("#orgSel");
		var cityOffset = $("#orgSel").offset();
		$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		expandAll();		
		$("body").bind("mousedown", onBodyDown);
	}
	
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "orgSel" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	
	// ----------begin  全部展开  / 全部折叠---------
	var demoMsg = {
		async: "Loading asynchronous, please wait a moment and then click...",
		expandAllOver: "Expansion Completed.",
		asyncAllOver: "Asynchronously loading Completed.",
		asyncAll: "Asynchronously loading Completed, no need to reload it again.",
		expandAll: "Asynchronously loading completed, please use expandAll method."
	}
			
	var isTreeBuilt = false,
	    arrCateId,       // 数组，保存需展开的各层节点的id
	    currOpenCateId,  // 当前需要展开的节点的id
	    isOpenFolderOnly = false; 		
	
	function beforeAsync() {
		curAsyncCount++;
	}
	
	function onAsyncSuccess(event, treeId, treeNode, msg) {
		curAsyncCount--;
		if (curStatus == "expand") {
			expandNodes(treeNode.children);
		} else if (curStatus == "async") {
			asyncNodes(treeNode.children);
		}

		if (curAsyncCount <= 0) {
			if (curStatus != "init" && curStatus != "") {
				$("#demoMsg").text((curStatus == "expand") ? demoMsg.expandAllOver : demoMsg.asyncAllOver);
				asyncForAll = true;
			}
			curStatus = "";
		}
		
		// 用于展开指定节点 首次调用时处理，用户ztree加载第一个节点之后
		if (!isTreeBuilt) {
			isTreeBuilt = true;
			
			if (arrCateId) {
				currOpenCateId = arrCateId.shift();
				openNode(currOpenCateId);
			}
		}
	}

	function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
		curAsyncCount--;

		if (curAsyncCount <= 0) {
			curStatus = "";
			if (treeNode!=null) asyncForAll = true;
		}
	}

	var curStatus = "init", curAsyncCount = 0, asyncForAll = false,
	goAsync = false;
	function expandAll() {
		if (!check()) {
			return;
		}
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		if (asyncForAll) {
			$("#demoMsg").text(demoMsg.expandAll);
			zTree.expandAll(true);
		} else {
			expandNodes(zTree.getNodes());
			if (!goAsync) {
				$("#demoMsg").text(demoMsg.expandAll);
				curStatus = "";
			}
		}
	} 
	
	function expandNodes(nodes) {
		if (!nodes) return;
		curStatus = "expand";
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		for (var i=0, l=nodes.length; i<l; i++) {
			zTree.expandNode(nodes[i], true, false, false);
			if (nodes[i].isParent && nodes[i].zAsync) {
				expandNodes(nodes[i].children);
			} else {
				goAsync = true;
			}
		}
	}

	function asyncAll() {
		if (!check()) {
			return;
		}
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		if (asyncForAll) {
			$("#demoMsg").text(demoMsg.asyncAll);
		} else {
			asyncNodes(zTree.getNodes());
			if (!goAsync) {
				$("#demoMsg").text(demoMsg.asyncAll);
				curStatus = "";
			}
		}
	}
	
	function asyncNodes(nodes) {
		if (!nodes) return;
		curStatus = "async";
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		for (var i=0, l=nodes.length; i<l; i++) {
			if (nodes[i].isParent && nodes[i].zAsync) {
				asyncNodes(nodes[i].children);
			} else {
				goAsync = true;
				zTree.reAsyncChildNodes(nodes[i], "refresh", true);
			}
		}
	}

	function reset() {
		if (!check()) {
			return;
		}
		asyncForAll = false;
		goAsync = false;
		$("#demoMsg").text("");
		$.fn.zTree.init($("#treeDemo"), setting);
	}

	function check() {
		if (curAsyncCount > 0) {
			$("#demoMsg").text(demoMsg.async);
			return false;
		}
		return true;
	}
	
	function expandNode(e) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		type = e.data.type,
		nodes = zTree.getSelectedNodes();
		if (type.indexOf("All")<0 && nodes.length == 0) {
			alert("Please select one parent node at first...");
		}

		if (type == "expandAll") {
			zTree.expandAll(true);
		} else if (type == "collapseAll") {
			zTree.expandAll(false);
		} else {
			var callbackFlag = $("#callbackTrigger").attr("checked");
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.setting.view.fontCss = {};
				if (type == "expand") {
					zTree.expandNode(nodes[i], true, null, null, callbackFlag);
				} else if (type == "collapse") {
					zTree.expandNode(nodes[i], false, null, null, callbackFlag);
				} else if (type == "toggle") {
					zTree.expandNode(nodes[i], null, null, null, callbackFlag);
				} else if (type == "expandSon") {
					zTree.expandNode(nodes[i], true, true, null, callbackFlag);
				} else if (type == "collapseSon") {
					zTree.expandNode(nodes[i], false, true, null, callbackFlag);
				}
			}
		}
	}
	
	function openNode(nodeid) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var node = zTree.getNodeByParam("id", nodeid, null);
		
		if (arrCateId.length > 0) {
			zTree.expandNode(node, null, false, true, true);
		} else {
			currOpenCateId = null;
			isOpenFolderOnly = true;
			zTree.selectNode(node);
			
			treeOnClick(event, node.id, node);
		}
	}
	
	function onExpand(event, treeId, treeNode) {
		if (treeNode.id == currOpenCateId) {
			currOpenCateId = arrCateId.shift();
			
			openNode(currOpenCateId);
		}
	}
	// ----------end  全部展开  / 全部折叠---------