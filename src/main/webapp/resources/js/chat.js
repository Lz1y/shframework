
var statebar, toolbar, logbox, inputbox, lastTalkId, totalMemoryDom, freeMemoryDom, maxMemoryDom, usedMemoryDom, connectorCountDom, startupDom, workStyleDom, maxLogCount = 100, loginDom, a_notify_num, a_notice_num, ul_notify_content, li_notify_num, li_notice_num, ul_notice_content;
var isFocus = true, unreadCount = 0;
function windowOnFocus(){
	isFocus = true;
	unreadCount = 0;
	// document.title = dTitle;
}
function windowOnBlur(){
	isFocus = false;
}
function windowResize() {
	var offset = 2;
	var other = statebar.offsetHeight + toolbar.offsetHeight + offset;
	logbox.style.height = document.documentElement.clientHeight - other + 'px';
}
function loginEnter(event){
	if (event.keyCode == 13) {
		login();
		return false;
	}
}
function showLogin(){
	statebar.style.display = 'none';
	toolbar.style.display = 'none';
	logbox.style.display = 'none';
	loginDom.style.display = 'block';
	loginDom.style.height = document.documentElement.clientHeight + 'px';
	document.getElementById("loginName").focus();
}
function login(callback){
	var userName = document.getElementById("userName").value;
	alert('userName -> ' + userName);
	userName = userName ? userName.trim() : '' ;
	if(!userName){
		alert('非法昵称，请重新输入');
		document.getElementById("userName").fucos();
		return;
	}
	setCookie('userName',userName, 365);
	loginDom.style.display = 'none';
	statebar.style.display = 'block';
	toolbar.style.display = 'block';
	logbox.style.display = 'block';
	start();
}
function init() {
	statebar = document.getElementById("statebar");
	toolbar = document.getElementById("toolbar");
	logbox = document.getElementById("logbox");
	inputbox = document.getElementById("inputbox");
	totalMemoryDom = document.getElementById("totalMemory");
	freeMemoryDom = document.getElementById("freeMemory");
	maxMemoryDom = document.getElementById("maxMemory");
	usedMemoryDom = document.getElementById("usedMemory");
	connectorCountDom = document.getElementById("connectorCount");
	workStyleDom = document.getElementById("workStyle");
	startupDom = document.getElementById("startup");
	loginDom = document.getElementById("login");
	a_notify_num = document.getElementById("a_notify_num");
	a_notice_num = document.getElementById("a_notice_num");
	ul_notify_content = document.getElementById("ul_notify_content");
	ul_notice_content = document.getElementById("ul_notice_content");
	li_notify_num = document.getElementById("li_notify_num");
	li_notice_num = document.getElementById("li_notice_num");
	// windowResize();
	// JS.on(window,'resize',windowResize);
	JS.on(window,'focus',windowOnFocus);
	JS.on(window,'blur',windowOnBlur);
	
	// 引擎事件绑定
	JS.Engine.on({
		start : function(cId, channels, engine) {
			var style = engine.getConnector().workStyle;
			style = style === 'stream'?'长连接':'长轮询';
			workStyleDom.innerHTML = style;
		},
		stop : function(cause, url, cId, engine) {
			workStyleDom.innerHTML = '<span class="warning">已停止<a href="javascript:start();" >(重连)</a></span>';
		},
		talker : function(data, timespan, engine) {
			switch (data.type) {
			case 'rename': // 改名
				onRename(data, timespan);
				break;
			case 'talk': // 收到聊天消息
				onMessage(data, timespan);
				break;
			case 'up': // 上线
				onJoin(data, timespan);
				break;
			case 'down': // 下线
				onLeft(data, timespan);
				break;
			case 'health':
				onHealthMessage(data, timespan);
				break;
			default:
			}
		}
	});
	
	var userName = getCookie('userName') || '';
	userName = userName ? userName.trim() : '' ;
	
	start();
	notifyUpdate();
}
//开启连接
function start(){
	var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var ctx = pathName.substr(0,index+1);
	jQuery.i18n.properties({
		name:'path',
		path:ctx + '/resources/i18n/',
		mode:'both',
		callback: function() {
			JS.Engine.start($.i18n.prop('async.req.path'));
			inputbox.focus();
		} 
	}); 
}
// 用户改名通知
function onRename(data, timespan) {
	var id = data.id;
	var newName = data.newName || '';
	newName = newName.HTMLEncode();
	var oldName = data.oldName || '';
	oldName = oldName.HTMLEncode();
	var t = data.transtime;
	var str = [ '<div class="sysmessage">', t, '【', oldName, '】改名为【',
			newName, '】</div>' ];
	checkLogCount();
	logbox.innerHTML += str.join('');
	lastTalkId = null;
	moveScroll();
}
// 用户聊天通知
function onMessage(data, timespan) {
	if(!isFocus){
		unreadCount++;
		// document.title = '('+unreadCount+')'+dTitle;
	}
	var id = data.id;
	var name = data.name || '';
	name = name.HTMLEncode();
	var text = data.text || '';
	text = text.HTMLEncode();
	var t = data.transtime;
	var str;
	if (lastTalkId == id) {
		str = [ '<div class="usermessage">', '<blockquote>', text,
				'</blockquote>', '</div>' ];
	} else {
		str = [ '<div class="usermessage">', t, '<span class="user">【',
				name, '】</span><blockquote>', text, '</blockquote>', '</div>' ];
	}
	checkLogCount();
	logbox.innerHTML += str.join('');
	// notify_ul
	notifyUpdate();
	
	lastTalkId = id;
	// console.log('data -> ' + data);
	moveScroll();
}
Date.prototype.format = function(format) {
    var o = {
        "M+": this.getMonth() + 1,
        // month
        "d+": this.getDate(),
        // day
        "h+": this.getHours(),
        // hour
        "m+": this.getMinutes(),
        // minute
        "s+": this.getSeconds(),
        // second
        "q+": Math.floor((this.getMonth() + 3) / 3),
        // quarter
        "S": this.getMilliseconds()
        // millisecond
    };
    if (/(y+)/.test(format) || /(Y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

function isIE() { //ie?  
    return (!!window.ActiveXObject || "ActiveXObject" in window);
} 
function notifyUpdate() {
	var param = '';
	JS.AJAX.post('/shframework/notification/directaccess', param, function(data) {

		a_notify_num.innerHTML = '<i class="icon-bell"></i>';
		a_notice_num.innerHTML = '<i class="icon-envelope-open"></i>';
		var json;
		try {
			json = eval("(" + data.responseText + ")");
		} catch(err) {
			json = [0,0];
		}
		if (json[0] > 0) {
			var notify_num = [ '<span class="badge badge-danger">', json[0], '</span>' ];
			a_notify_num.innerHTML += notify_num.join('');
			var notify_num = [ '<h3><span class="bold">', json[0], '</span> 封未读 </h3><a href="/shframework/notification/1/list?ps=25">查看更多</a>' ];
			li_notify_num.innerHTML += notify_num.join('');
		} else {
			var notify_num = [ '<h3>没有新的通知</h3><a href="/shframework/notification/1/list?ps=25">查看所有</a>' ];
			li_notify_num.innerHTML += notify_num.join('');
			if(isIE()){
				li_notify_num.parentNode.childNodes[3].removeNode(true);
			} else {
				li_notify_num.parentNode.childNodes[3].remove();
			}
		}

		
		if (json[1] > 0) {
			var notice_num = [ '<span class="badge badge-default">', json[1], '</span>' ];
			a_notice_num.innerHTML += notice_num.join('');
			var notice_num = [ '<h3><span class="bold">', json[1], '</span> 封未读 </h3><a href="/shframework/notification/2/list?ps=25">查看更多</a>' ];
			li_notice_num.innerHTML += notice_num.join('');
		} else {
			var notice_num = [ '<h3>没有新的消息</h3><a href="/shframework/notification/2/list?ps=25">查看所有</a>' ];
			li_notice_num.innerHTML += notice_num.join('');
			if(isIE()){
				li_notice_num.parentNode.childNodes[3].removeNode(true);
			} else {
				li_notice_num.parentNode.childNodes[3].remove();
			}
		}

	});
}

function onMarkRead(element) {
	JS.AJAX.post('message/update?id='+element.title, '', function(data) {
		if (data.responseText == 1) {
			notifyUpdate();
		}
	});
}

function onMarkReadAll() {
	JS.AJAX.post('message/update/all', '', function(data) {
		if (data.responseText > 0) {
			notifyUpdate();
		}
	});
}


// 用户上线通知
function onJoin(data, timespan) {
	if(!isFocus){
		unreadCount++;
		// document.title = '('+unreadCount+')'+dTitle;
	}
	var id = data.id;
	var name = data.name || id;
	name = name.HTMLEncode();
	var t = data.transtime;
	var str = [
			'<div class="sysmessage">',
			t,
			'【',
			name,
			'】来了，欢迎体验 <a href="#" target="_new">Comet For Java</a>',
			'</div>' ];
	checkLogCount();
	logbox.innerHTML += str.join('');
	lastTalkId = null;
	moveScroll();
}
// 用户下线通知
function onLeft(data, timespan) {
	var id = data.id;
	var name = data.name || id;
	name = name.HTMLEncode();
	var t = data.transtime;
	var str = [ '<div class="sysmessage">', t, '【', name, '】离开了',
			'</div>' ];
	checkLogCount();
	logbox.innerHTML += str.join('');
	lastTalkId = null;
	moveScroll();
}
// 系统健康信息
function onHealthMessage(data, timespan) {
	var totalMemory = data.totalMemory;
	var freeMemory = data.freeMemory;
	var maxMemory = data.maxMemory;
	var usedMemory = data.usedMemory;
	var startup = data.startup;
	var connectorCount = data.connectorCount + '个';
	totalMemoryDom.innerHTML = totalMemory + 'M';
	freeMemoryDom.innerHTML = freeMemory + 'M';
	maxMemoryDom.innerHTML = maxMemory + 'M';
	usedMemoryDom.innerHTML = usedMemory + 'M';
	connectorCountDom.innerHTML = connectorCount;
	startupDom.innerHTML = startup;
}

// 检测输出长度
function checkLogCount() {
	var count = logbox.childNodes.length;
	if (count > maxLogCount) {
		var c = count - maxLogCount;
		for ( var i = 0; i < c; i++) {
			// logbox.removeChild(logbox.children[0]);
			logbox.removeChild(logbox.firstChild);
		}

	}
}
// 移动滚动条
function moveScroll() {
	logbox.scrollTop = logbox.scrollHeight;
	inputbox.focus();
}
// 回车事件
function onSendBoxEnter(event) {
	// console.log(event);
	if (event.keyCode == 13) {
		var text = inputbox.value;
		send(text);
		return false;
	}
}
// 发送聊天信息动作
function send(text) {
	if (!JS.Engine.running)
		return;
	text = text.trim();
	if (!text)
		return;
	var id = JS.Engine.getId();
	var param = "id=" + id + '&text=' + encodeURIComponent(text);
	JS.AJAX.post('talk?cmd=talk', param, function() {
		inputbox.value = '';
	});
}
// 改名动作
function rename() {
	if (!JS.Engine.running)
		return;
	var oldName = getCookie('userName') || '';
	oldName = oldName.trim();
	var userName = prompt("请输入姓名", oldName);
	userName = userName ? userName.trim() : '';
	var id = JS.Engine.getId();
	if (!id || !userName || oldName == userName)
		return;
	var param = "id=" + id + '&newName=' + encodeURIComponent(userName)
			+ '&oldName=' + encodeURIComponent(oldName);
	setCookie('userName', userName, 365);
	JS.AJAX.post('talk?cmd=rename', param);
}

// 设置Cookie
function setCookie(name, value, expireDay) {
	var exp = new Date();
	exp.setTime(exp.getTime() + expireDay * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + encodeURIComponent(value) + ";expires="
			+ exp.toGMTString();
}
// 获得Cookie
function getCookie(name) {
	var arr = document.cookie
			.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return decodeURIComponent(arr[2]);
	return null;
}
// 删除Cookie
function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}
// HTML编码
String.prototype.HTMLEncode = function() {
	var temp = document.createElement("div");
	(temp.textContent != null) ? (temp.textContent = this)
			: (temp.innerText = this);
	var output = temp.innerHTML;
	temp = null;
	return output;
};
// HTML解码
String.prototype.HTMLDecode = function() {
	var temp = document.createElement("div");
	temp.innerHTML = this;
	var output = temp.innerText || temp.textContent;
	temp = null;
	return output;
};
//String.prototype.trim = function() {
//	return this.replace(/^\s+|\s+$/g, '');
//};