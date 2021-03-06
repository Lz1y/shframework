/*
 * Comet4J JavaScript Client V0.1.7
 * Copyright(c) 2011, jinghai.xiao@gamil.com.
 * http://code.google.com/p/comet4j/
 * This code is licensed under BSD license. Use it as you wish, 
 * but keep this copyright intact.
 */


var JS = {
	version : '0.1.7'
};

JS.Runtime = (function(){
	var ua = navigator.userAgent.toLowerCase(),
    check = function(r){
        return r.test(ua);
    },
     
	isOpera = check(/opera/),
	 
	isFirefox = check(/firefox/),
	 
	isChrome = check(/chrome/),
	 
	isWebKit = check(/webkit/),
	 
	isSafari = !isChrome && check(/safari/),
	 
	isSafari2 = isSafari && check(/applewebkit\/4/),
	 
	isSafari3 = isSafari && check(/version\/3/),
	 
	isSafari4 = isSafari && check(/version\/4/),
	 
	isIE = !isOpera && check(/msie/),
	 
	isIE7 = isIE && check(/msie 7/),
	 
	isIE8 = isIE && check(/msie 8/),
	 
	isIE6 = isIE && !isIE7 && !isIE8,
	 
	isGecko = !isWebKit && check(/gecko/),
	 
	isGecko2 = isGecko && check(/rv:1\.8/),
	 
	isGecko3 = isGecko && check(/rv:1\.9/),
	 
	isWindows = check(/windows|win32/),
	 
	isMac = check(/macintosh|mac os x/),
	 
	isAir = check(/adobeair/),
	 
	isLinux = check(/linux/);
	return {
		isOpera : isOpera,
		isFirefox : isFirefox,
	    isChrome : isChrome,
	    isWebKit : isWebKit,
	    isSafari : isSafari,
	    isSafari2 : isSafari2,
	    isSafari3 : isSafari3,
	    isSafari4 : isSafari4,
	    isIE : isIE,
	    isIE7 : isIE7,
	    isIE8 : isIE8,
	    isIE6 : isIE6,
	    isGecko : isGecko,
	    isGecko2 : isGecko2,
	    isGecko3 : isGecko3,
	    isWindows :isWindows,
	    isMac : isMac,
	    isAir : isAir,
	    isLinux : isLinux
	};
}());
JS.isOpera = JS.Runtime.isOpera;
JS.isFirefox = JS.Runtime.isFirefox;
JS.isChrome = JS.Runtime.isChrome;
JS.isWebKit = JS.Runtime.isWebKit;
JS.isSafari = JS.Runtime.isSafari;
JS.isSafari2 = JS.Runtime.isSafari2;
JS.isSafari3 = JS.Runtime.isSafari3;
JS.isSafari4 = JS.Runtime.isSafari4;
JS.isIE = JS.Runtime.isIE;
JS.isIE7 = JS.Runtime.isIE7;
JS.isIE8 = JS.Runtime.isIE8;
JS.isIE6 = JS.Runtime.isIE6;
JS.isGecko = JS.Runtime.isGecko;
JS.isGecko2 = JS.Runtime.isGecko2;
JS.isGecko3 = JS.Runtime.isGecko3;
JS.isWindows =JS.Runtime.isWindows;
JS.isMac = JS.Runtime.isMac;
JS.isAir = JS.Runtime.isAir;
JS.isLinux = JS.Runtime.isLinux;

JS.Syntax = {
	log : function(str){
		if(typeof console!="undefined"){
			console.log(str);
		}
	},
	nameSpace : function(){
		if(arguments.length){
			var o, d, v;
			for(var i=0,len=arguments.length; i<len; i++){
				v = arguments[i];
				if(!v){
					continue;
				}
				d = v.split(".");
				for(var j=0,len=d.length; j<len; j++){
					if(!d[j]){
						continue;
					}
					o = window[d[j]] = window[d[j]] || {};
				}
			}
		}
		return o;
	},
	apply : function(o, c, defaults){
		// no "this" reference for friendly out of scope calls
		if(defaults){
			JS.Syntax.apply(o, defaults);
		}
		if(o && c && typeof c == 'object'){
			for(var p in c){
				o[p] = c[p];
			}
		}
		return o;
	},
	override : function(origclass, overrides){
		if(overrides){
			var p = origclass.prototype;
			JS.Syntax.apply(p, overrides);
			if(JS.Runtime.isIE && overrides.hasOwnProperty('toString')){
				p.toString = overrides.toString;
			}
		}
	},
	extend : function(){
		// siple object copy
		var io = function(o){
			for(var m in o){
				this[m] = o[m];
			}
		};
		var oc = Object.prototype.constructor;

		return function(sb, sp, overrides){
			if(JS.Syntax.isObject(sp)){
				overrides = sp;
				sp = sb;
				sb = overrides.constructor != oc ? overrides.constructor : function(){sp.apply(this, arguments);};
			}
			var F = function(){},
				sbp,
				spp = sp.prototype;

			F.prototype = spp;
			sbp = sb.prototype = new F();
			sbp.constructor=sb;
			sb.superclass=spp;
			if(spp.constructor == oc){
				spp.constructor=sp;
			}
			sb.override = function(o){
				JS.Syntax.override(sb, o);
			};
			sbp.superclass = sbp.supr = (function(){
				return spp;
			});
			sbp.override = io;
			JS.Syntax.override(sb, overrides);
			sb.extend = function(o){return JS.Syntax.extend(sb, o);};
			return sb;
		};
	}(),
	callBack : function(fn,scope,arg){
		if(JS.isFunction(fn)){
			return fn.apply(scope || window,arg || []);
		}
	},
	
	isEmpty : function(v, allowBlank){
		return v === null || v === undefined || ((Ext.isArray(v) && !v.length)) || (!allowBlank ? v === '' : false);
	},

	
	isArray : function(v){
		return Object.prototype.toString.apply(v) === '[object Array]';
	},

	
	isDate : function(v){
		return Object.prototype.toString.apply(v) === '[object Date]';
	},

	
	isObject : function(v){
		return !!v && Object.prototype.toString.call(v) === '[object Object]';
	},

	
	isPrimitive : function(v){
		return Ext.isString(v) || Ext.isNumber(v) || Ext.isBoolean(v);
	},

	
	isFunction : function(v){
		return Object.prototype.toString.apply(v) === '[object Function]';
	},

	
	isNumber : function(v){
		return typeof v === 'number' && isFinite(v);
	},

	
	isString : function(v){
		return typeof v === 'string';
	},

	
	isBoolean : function(v){
		return typeof v === 'boolean';
	},

	
	isElement : function(v) {
		return !!v && v.tagName;
	},

	
	isDefined : function(v){
		return typeof v !== 'undefined';
	},
	 
	 toArray : function(){
		 return JS.isIE ?
			 function(a, i, j, res){
				 res = [];
				 for(var x = 0, len = a.length; x < len; x++) {
					 res.push(a[x]);
				 }
				 return res.slice(i || 0, j || res.length);
			 } :
			 function(a, i, j){
				 return Array.prototype.slice.call(a, i || 0, j || a.length);
			 };
	 }()
};
JS.log = JS.Syntax.log;
JS.ns = JS.Syntax.nameSpace;
JS.apply = JS.Syntax.apply;
JS.override = JS.Syntax.override;
JS.extend = JS.Syntax.extend;
JS.callBack = JS.Syntax.callBack;
JS.isEmpty = JS.Syntax.isEmpty;
JS.isArray = JS.Syntax.isArray;
JS.isDate = JS.Syntax.isDate;
JS.isObject = JS.Syntax.isObject;
JS.isPrimitive = JS.Syntax.isPrimitive;
JS.isFunction = JS.Syntax.isFunction;
JS.isNumber = JS.Syntax.isNumber;
JS.isString = JS.Syntax.isString;
JS.isBoolean = JS.Syntax.isBoolean;
JS.isElement = JS.Syntax.isElement;
JS.isDefined = JS.Syntax.isDefined;
JS.toArray = JS.Syntax.toArray;

JS.DomEvent = {
	
    on: function(el, name, fun, scope){
        if (el.addEventListener) {
        	//el.addEventListener(name, fun, false);
        	//TODO:若封闭成带有作用域的功能，如何删除事件，是否自己要记一下on的函数，然后在un下删除它
        	
			el.addEventListener(name, function(){
				JS.callBack(fun,scope,arguments);
			}, false);
			
		} else {
			//el.attachEvent('on' + name, fun);
			
			el.attachEvent('on' + name, function(){
				JS.callBack(fun,scope,arguments);
			});
			
		}
    },
    
    un: function(el, name, fun,scope){
        if (el.removeEventListener){
            el.removeEventListener(name, fun, false);
        } else {
            el.detachEvent('on' + name, fun);
        }
    },
    
    stop: function(e){
		e.returnValue = false;
		if (e.preventDefault) {
			e.preventDefault();
		}
		JS.DomEvent.stopPropagation(e);
    },
    
    stopPropagation: function(e){
        e.cancelBubble = true;
		if (e.stopPropagation) {
			e.stopPropagation();
		}
    }
};
JS.on = JS.DomEvent.on;
JS.un = JS.DomEvent.un;

JS.DelayedTask = function(fn, scope, args){
    var me = this,
    	id,    	
    	call = function(){
    		clearInterval(id);
	        id = null;
	        fn.apply(scope, args || []);
	    };
	    
    
    me.delay = function(delay, newFn, newScope, newArgs){
        me.cancel();
        fn = newFn || fn;
        scope = newScope || scope;
        args = newArgs || args;
        id = setInterval(call, delay);
    };

    
    me.cancel = function(){
        if(id){
            clearInterval(id);
            id = null;
        }
    };
};


JS.ns("JS.Observable");
JS.Observable = function(o){
	JS.apply(this,o || JS.toArray(arguments)[0]);
	if(this.events){
		this.addEvents(this.events);
	}
    if(this.listeners){
        this.on(this.listeners);
        delete this.listeners;
    }
};
JS.Observable.prototype = {
	
	on : function(eventName, fn, scope, o){
		if(JS.isString(eventName)){
			this.addListener(eventName, fn, scope, o);
		}else if(JS.isObject(eventName)){
			this.addListeners(eventName,scope, o);
		}
	},
	
	fireEvent : function(){
		var arg = JS.toArray(arguments),
		    eventName = arg[0].toLowerCase(),
			e = this.events[eventName];
		if(e && !JS.isBoolean(e)){
			return e.fire.apply(e,arg.slice(1));
		}
	},
	
	addEvent : function(eventName){
		if(!JS.isObject(this.events)){
			this.events = {};
		}
		if(this.events[eventName]){
			return;
		}
		if(JS.isString(eventName)){
			this.events[eventName.toLowerCase()] = true;
		}else if(eventName instanceof JS.Event){
			this.events[eventName.name.toLowerCase()] = eventName;
		}
	},
	
	addEvents : function(arr){
		if(JS.isArray(arr)){
			for(var i = 0,len = arr.length; i < len; i++){
				this.addEvent(arr[i]);
			}
		}
	},
	
	addListener : function(eventName, fn, scope, o){//o配置项尚未实现
		eventName = eventName.toLowerCase();
		this.addEvent(eventName);
		var e = this.events[eventName];
		if(e){
			if(JS.isBoolean(e)){
				e = this.events[eventName] = new JS.Event(eventName,this);
			}
			e.addListener(fn, scope , o);
		}
	},
	
	addListeners : function(obj,scope, o){
		if(JS.isObject(obj)){
			for(var p in obj){
				this.addListener(p,obj[p],scope, o);
			}
		}
	},
	
	removeListener : function(eventName, fn, scope){
		eventName = eventName.toLowerCase();
		var e = this.events[eventName];
		if(e && !JS.isBoolean(e)){
			e.removeListener(fn, scope);
		}
	},
	
	clearListeners : function(){
		var events = this.events,
			e;
		for(var p in events){
			e = events[p];
			if(!JS.isBoolean(e)){
				e.clearListeners();
			}
		}
	},
	
	clearEvents : function(){
		var events = this.events;
		this.clearListeners();
		for(var p in events){
			this.removeEvent(p);
		}
	},
	
	removeEvent : function(eventName){
		var events = this.events,
			e;
		if(events[eventName]){
			e = events[eventName];
			if(!JS.isBoolean(e)){
				e.clearListeners();
			}
			delete events[eventName];
		}
		
	},
	
	removeEvents : function(eventName){
		if(JS.isString(eventName)){
			this.removeEvent(eventName);
		}else if(JS.isArray(eventName) && eventName.length > 0){
			for(var i=0, len=eventName.length; i<len ;i++){
				this.removeEvent(eventName[i]);
			}
		}
	},
	
	hasEvent : function(eventName){
		return this.events[eventName.toLowerCase()]?true:false;
	},
	
	hasListener : function(eventName,fn,scope){
		var events = this.events,
			e = events[eventName];
		if(!JS.isBoolean(e)){
			return e.hasListener(fn,scope);
		}
		return false;
	},
	suspendEvents : function(){
		//TODO:
	},
	resumeEvents : function(){
		//TODO:
	}
};

JS.Event = function(name,caller){
	this.name = name.toLowerCase();
	this.caller = caller;
	this.listeners = [];
};
JS.Event.prototype = {
	
	fire : function(){
		var 
			listeners = this.listeners,
			//len = listeners.length,
			i = listeners.length-1;
		for(; i > -1; i--){//TODO:fix 倒序
			if(listeners[i].execute.apply(listeners[i],arguments) === false){
				return false;
			}
		}
		return true;
	},
	
	addListener : function(fn, scope,o){
        scope = scope || this.caller;
        if(this.hasListener(fn, scope) == -1){
            this.listeners.push(new JS.Listener(fn, scope ,o));
        }
    },
    
	removeListener : function(fn, scope){
        var index = this.hasListener(fn, scope);
		if(index!=-1){
			this.listeners.splice(index, 1);
		}
    },
    
	hasListener : function(fn, scope){
		var i = 0,
			listeners = this.listeners,
			len = listeners.length;
		for(; i<len; i++){
			if(listeners[i].equal(fn, scope)){
				return i;
			}
		}
		return -1;
	},
	 
	clearListeners : function(){
		var i = 0,
			listeners = this.listeners,
			len = listeners.length;
		for(; i<len; i++){
			listeners[i].clear();
		}
		this.listeners.splice(0);
	}

};

JS.Listener = function(fn, scope,o){
	this.handler = fn;
	this.scope = scope;
	this.o = o;//配置项，delay,buffer,once,
};
JS.Listener.prototype = {
	
	execute : function(){
		return JS.callBack(this.handler,this.scope,arguments);
	},
	
	equal : function(fn, scope){
		return this.handler === fn  ? true : false;
	},
	
	clear : function(){
		delete this.handler;
		delete this.scope ;
		delete this.o ;
	}
};
JS.ns("JS.HTTPStatus","JS.XMLHttpRequest");

JS.HTTPStatus = {
	//Informational 1xx
	'100' : 'Continue',
	'101' : 'Switching Protocols',
	//Successful 2xx
	'200' : 'OK',
	'201' : 'Created',
	'202' : 'Accepted',
	'203' : 'Non-Authoritative Information',
	'204' : 'No Content',
	'205' : 'Reset Content',
	'206' : 'Partial Content',
	//Redirection 3xx
	'300' : 'Multiple Choices',
	'301' : 'Moved Permanently',
	'302' : 'Found',
	'303' : 'See Other',
	'304' : 'Not Modified',
	'305' : 'Use Proxy',
	'306' : 'Unused',
	'307' : 'Temporary Redirect',
	//Client Error 4xx
	'400' : 'Bad Request',
	'401' : 'Unauthorized',
	'402' : 'Payment Required',
	'403' : 'Forbidden',
	'404' : 'Not Found',
	'405' : 'Method Not Allowed',
	'406' : 'Not Acceptable',
	'407' : 'Proxy Authentication Required',
	'408' : 'Request Timeout',
	'409' : 'Conflict',
	'410' : 'Gone',
	'411' : 'Length Required',
	'412' : 'Precondition Failed',
	'413' : 'Request Entity Too Large',
	'414' : 'Request-URI Too Long',
	'415' : 'Unsupported Media Type',
	'416' : 'Requested Range Not Satisfiable',
	'417' : 'Expectation Failed',
	//Server Error 5xx
	'500' : 'Internal Server Error',
	'501' : 'Not Implemented',
	'502' : 'Bad Gateway',
	'503' : 'Service Unavailable',
	'504' : 'Gateway Timeout',
	'505' : 'HTTP Version Not Supported'
};
JS.HTTPStatus.OK = 200;
JS.HTTPStatus.BADREQUEST = 400;
JS.HTTPStatus.FORBIDDEN = 403;
JS.HTTPStatus.NOTFOUND = 404;
JS.HTTPStatus.TIMEOUT = 408;
JS.HTTPStatus.SERVERERROR = 500;


JS.XMLHttpRequest = JS.extend(JS.Observable,{
	
	enableCache : false,
	
	timeout : 30000,//default never time out
	 
	isAbort : false,
	
	specialXHR : '',//指定使用特殊的xhr对象
	//propoty
	_xhr : null,
	//--------request propoty--------
	 
	readyState : 0,
	//--------response propoty--------
	 
	status : 0,
	 
	statusText : '',
	
	responseText : '',
	
	responseXML : null,
	//private method
	constructor : function(){
		var self = this;
		this.addEvents([
			
			'readyStateChange',
			
			'timeout',
			
			'abort',
			
			'error',
			
			'load',
			
			'progress'
		]);
		JS.XMLHttpRequest.superclass.constructor.apply(this,arguments);
		this._xhr = this.createXmlHttpRequestObject();
		this._xhr.onreadystatechange = function(){
			self.doReadyStateChange();
		};
	},
	//private
	//超时任务
	timeoutTask : null,
	//延迟执行超时任务(timeoutTask)
	delayTimeout : function(){
		if(this.timeout){
			if(!this.timeoutTask){
				this.timeoutTask = new JS.DelayedTask(function(){
					//readyState=4已经停止，由doReadyStateChange来判断为何停止
					if(this._xhr.readyState != 4){
						this.fireEvent('timeout', this, this._xhr);
					}else{
						this.cancelTimeout();
					}
				},this);
			}
			this.timeoutTask.delay(this.timeout);
		}
	},
	//取消超时任务
	cancelTimeout : function(){
		if(this.timeoutTask){
			this.timeoutTask.cancel();
		}
	},
	createXmlHttpRequestObject : function(){
		var activeX = [
			'Msxml2.XMLHTTP.6.0', 
			'Msxml2.XMLHTTP.5.0', 
			'Msxml2.XMLHTTP.4.0', 
			'Msxml2.XMLHTTP.3.0', 
			'Msxml2.XMLHTTP', 
			'Microsoft.XMLHTTP'],
		xhr,
		specialXHR = this.specialXHR;
		if(specialXHR){//如果指定了xhr对象
			if(JS.isString(specialXHR)){
				return new ActiveXObject(specialXHR);
			}else{
				return specialXHR;
			}
		}
		try {
			xhr = new XMLHttpRequest();                
		} catch(e) {
			for (var i = 0; i < activeX.length; ++i) {	            
				try {
					xhr = new ActiveXObject(activeX[i]);                        
					break;
				} catch(e) {}
			}
		} finally {
			return xhr;
		}
	},
	//private
	doReadyStateChange : function(){
		this.delayTimeout();
		var xhr = this._xhr;
		try{
			this.readyState = xhr.readyState;
		}catch(e){
			this.readyState = 0;
		}
		try{
			this.status = xhr.status;
		}catch(e){
			this.status = 0;
		}
		try{
			this.statusText = xhr.statusText;
		}catch(e){
			this.statusText = "";
		}
		try {
			this.responseText = xhr.responseText;
		}catch(e){
			this.responseText = "";
		}
		try {
			this.responseXML = xhr.responseXML;
		}catch(e){
			this.responseXML = null;
		}
		
		this.fireEvent('readyStateChange',this.readyState, this.status, this, xhr );
		
		if(this.readyState == 3 && (this.status >= 200 && this.status < 300)){
			this.fireEvent('progress', this, xhr);
		}
		
		if(this.readyState == 4){
			this.cancelTimeout();
			var status = this.status ;
			if(status == 0 || status == ""){
				this.fireEvent('error', this, xhr);
			}else if(status >= 200 && status < 300){
				this.fireEvent('load', this, xhr);
			}else if(status >= 400 && status != 408){
				this.fireEvent('error', this, xhr);
			}else if(status == 408){
				this.fireEvent('timeout', this, xhr);
			}
			
		}
		this.onreadystatechange();
	},
	
	onreadystatechange : function(){
	},
	//--------request--------
	
	open : function(method, url, async, username, password){
		if(!url){
			return;
		}
		if(!this.enableCache){
			if(url.indexOf('?') != -1){
				url += '&ram='+Math.random();
			}else{
				url += '?ram='+Math.random();
			}
		}
		this._xhr.open(method, url, async, username, password);
	},
	
	send : function(content){
		this.delayTimeout();
		this.isAbort = false;
		this._xhr.send(content);
	},
	
	abort : function(){
		this.isAbort = true;
		this.cancelTimeout();
		this._xhr.abort();
		if(JS.isIE){//IE在abort后会清空侦听
			var self = this;
			self._xhr.onreadystatechange = function(){
				self.doReadyStateChange();
			};
		}
		this.fireEvent('abort',this,this._xhr);
	},
	
	setRequestHeader : function(header, value){
		this._xhr.setRequestHeader(header,value);
	},
	//--------request--------
	
	getResponseHeader : function(header){
		return this._xhr.getResponseHeader(header);
	},
	
	getAllResponseHeaders : function(){
		return this._xhr.getAllResponseHeaders();
	},
	
	setTimeout : function(t){
		this.timeout = t;
	}

});

JS.ns("JS.AJAX");

JS.AJAX = (function(){
	var xhr = new JS.XMLHttpRequest();
	return {
		dataFormatError : '服务器返回的数据格式有误',
		urlError : '未指定url',
		
		post : function(url,param,callback,scope,asyn){
			if(typeof url!=='string'){
				throw new Error(this.urlError);
			}
			//默认为异步请求
			var asynchronous = true;
			if(asyn===false){
				asynchronous = false;
			}
			xhr.onreadystatechange = function(){
				if(xhr.readyState==4 && asynchronous){
					JS.callBack(callback,scope,[xhr]);
				}
			};
			xhr.open('POST', url, asynchronous);
			xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF8");
			xhr.send(param || null);
			if(!asynchronous){
				JS.callBack(callback,scope,[xhr]);
			}
			
		},
		
		get : function(url,param,callback,scope,asyn){
			if(typeof url!=='string'){
				throw new Error(this.urlError);
			}
			//默认为异步请求
			var asynchronous = true;
			if(asyn===false){
				asynchronous = false;
			}
			xhr.onreadystatechange = function(){
				if(xhr.readyState==4 && asynchronous){
					JS.callBack(callback,scope,[xhr]);
				}
			};
			xhr.open('GET', url, asynchronous);
			xhr.setRequestHeader("Content-Type","html/text;charset=UTF8");
			xhr.send(param || null);
			if(!asynchronous){
				JS.callBack(callback,scope,[xhr]);
			}
			
		},
		
		getText : function(url,jsonData,callback,scope,asyn){
			this.get(url,jsonData,function(xhr){
				if(scope){
					callback.call(scope,xhr.responseText);
				}else{
					callback(xhr.responseText);
				}
			},this,asyn);
		},
		
		getJson : function(url,jsonData,callback,scope,asyn){
			this.get(url,jsonData,function(xhr){
				var json = null;
				try{
					json = eval("("+xhr.responseText+")");
				}catch(e){
					throw new Error(this.dataFormatError);
				}
				JS.callBack(callback,scope,[json]);
			},this,asyn);
		}
	};
})();

JS.ns("JS.Connector");
JS.Connector = JS.extend(JS.Observable,{
    
	version : JS.version,
	SYSCHANNEL:'c4j', //协议常量
	 
	LLOOPSTYLE : 'lpool',//协议常量
	 
	STREAMSTYLE : 'stream',//协议常量
	CMDTAG : 'cmd',
	
	retryCount : 3,
	
	retryDelay : 1000,
	currRetry : 0,
	
	url : '',
	
	param : '',
	
	revivalDelay : 100,
     
	cId : '',
	 
	channels : [],
	 
	workStyle : '',
	emptyUrlError : 'URL为空',
	runningError : '连接正在运行',
	dataFormatError : '数据格式有误',
	 
	running : false,
	_xhr : null,
	lastReceiveMessage : '',
	constructor:function(){
		JS.Connector.superclass.constructor.apply(this,arguments);
		this.addEvents([
			
			'beforeConnect',
			
			'connect',
			
			'beforeStop',
			
			'stop',
			
			'message',
			
			'revival'
		]);
		if(JS.isIE7){
			this._xhr = new JS.XMLHttpRequest({
				specialXHR : 'Msxml2.XMLHTTP.6.0'
			});
		}else{
			this._xhr = new JS.XMLHttpRequest();
		}
		//this._xhr.addListener('readyStateChange',this.onReadyStateChange,this);
		
		this._xhr.addListener('progress',this.doOnProgress,this);
		this._xhr.addListener('load',this.doOnLoad,this);
		this._xhr.addListener('error',this.doOnError,this);
		this._xhr.addListener('timeout',this.doOnTimeout,this);
		
		this.addListener('beforeStop',this.doDrop,this);
		JS.on(window,'beforeunload',this.doDrop,this);

	},
	//private
	doDrop : function(url,cId,conn,xhr){
		if(!this.running || !this.cId){
			return;
		}
		try {
			var xhr = new JS.XMLHttpRequest();
			var param = this.perfectParam(this.param);
			var url = this.url + '?'+this.CMDTAG+'=drop&cid=' + this.cId + param;
			xhr.open('GET', url, false);
			xhr.send(null);
			xhr = null;
		}catch(e){};
	},
	//private distributed 派发服务器消息
	dispatchServerEvent : function(msg){
		this.fireEvent('message', msg.channel, msg.data, msg.time, this);
	},
	//private 长连接信息转换
	translateStreamData : function(responseText){
		var str = responseText;
		if(this.lastReceiveMessage && str){//剥离出接收到的数据
			str = str.split(this.lastReceiveMessage);
			str = str.length ? str[str.length-1] : "";
		}
		this.lastReceiveMessage = responseText;
		return str;
	},
	//private 消息解码
	decodeMessage : function(msg){
		var json = null;
		if(JS.isString(msg) && msg!=""){
			//解析数据格式
			if(msg.charAt(0)=="<" ){
				msg = msg.substring(1,msg.length);
			}
			if(msg.charAt(msg.length-1)==">"){
				msg = msg.substring(0,msg.length-1);
			}
			msg = decodeURIComponent(msg);
			try{
				json = eval("("+msg+")");
			}catch(e){
				this.stop('JSON转换异常');
			}			
		}
		return json;
	},
	//private
	doOnProgress : function(xhr){
		if(this.workStyle === this.STREAMSTYLE){				
			var str = this.translateStreamData(xhr.responseText);
			var msglist = str.split(">");
			if(msglist.length > 0){
				for(var i=0, len=msglist.length; i<len; i++){
					if(!msglist[i] && i!=0){
						return;
					}
					var json = this.decodeMessage(msglist[i]);
					if(json){
						this.currRetry = 0;
						this.dispatchServerEvent(json);
						if(json.channel == this.SYSCHANNEL){
							this.revivalConnect();
						}
					}else{//非正常情况，状态为3,200并且还没有收到任何数据
						this.currRetry++;
						if(this.currRetry > this.retryCount){
							this.stop('服务器异常');
						}else{
							this.retryRevivalConnect();
						}
					}
				}
			}
		}
	},
	//private
	doOnLoad : function(xhr){
		if(this.workStyle === this.LLOOPSTYLE){
			var json = this.decodeMessage(xhr.responseText);
			if(json){
				this.currRetry = 0;
				this.dispatchServerEvent(json);
				this.revivalConnect();
			}else{//非正常情况，状态为4,200并且还没有收到任何数据
				this.currRetry++;
				if(this.currRetry > this.retryCount){
					this.stop('服务器异常');
				}else{
					this.retryRevivalConnect();
				}
			}
		}
	},
	//private
	doOnError : function(xhr){
		this.currRetry++;
		if(this.currRetry > this.retryCount){
			this.stop('服务器异常');
		}else{
			this.retryRevivalConnect();
		}
		
	},
	//private
	doOnTimeout : function(xhr){
		this.currRetry++;
		if(this.currRetry > this.retryCount){
			this.stop('请求超时');
		}else{
			this.retryRevivalConnect();
		}
	},
	//private
	startConnect : function(url){
		if(this.running){
			var connXhr = new JS.XMLHttpRequest();
			connXhr.addListener('error',function(xhr){
				this.stop("连接时发生错误");
			},this);
			connXhr.addListener('timeout',function(xhr){
				this.stop("连接超时");
			},this);
			connXhr.addListener('load',function(xhr){
				var msg = this.decodeMessage(xhr.responseText);
				// console.log('msg -> ' + JSON.stringify(msg));
				if(!msg){
					this.stop('连接失败');
					return;
				}

				var data = msg.data;
				// JS.AJAX.post('setting/' + data.cId + '/cid', '', function(data) { });

				this.cId = data.cId;
				this.channels = data.channels;
				this.workStyle = data.ws;
				this._xhr.setTimeout(data.timeout + 1000);
				this.fireEvent('connect', data.cId, data.channels, data.ws, data.timeout, this);
				this.revivalConnect();
			},this);
			connXhr.open('GET', url, true);
			connXhr.send(null);
			
		}
	},

	//private 重试复活连接
	retryRevivalConnect : function(){
		var self = this;
		if(this.running){
			setTimeout(function(){
				self.revivalConnect();
			},this.retryDelay);
		}
	},
	
	//private
	revivalConnect : function(){
		var self = this;
		if(this.running){
			setTimeout(revival,this.revivalDelay);
		}
		function revival(){
			var xhr = self._xhr;
			var param = self.perfectParam(self.param);
			var url = self.url + '?'+self.CMDTAG+'=revival&cid=' + self.cId + param;
			xhr.open('GET', url, true);
			xhr.send(null);
			self.fireEvent('revival',self.url, self.cId, self);
		}
		
	},
	
	start : function(url,param){
		if(this.running){
			return;
		}
		
		this.url = url || this.url;
		if(!this.url){
			throw new Error(this.emptyUrlError);
		}
		
		if(this.fireEvent('beforeConnect', this.url, this) === false){
			return;
		}
		
		this.param = param || this.param;
		param = this.perfectParam(this.param);
		var url = this.url+'?'+this.CMDTAG+'=conn&cv='+this.version+param;
		
		this.running = true;
		this.currRetry = 0;
		var self = this;
		setTimeout(function(){
			self.startConnect(url);
		},1000);
	},
	// 完善参数
	perfectParam : function(param){
		if(param && JS.isString(param)){
			if(param.charAt(0) != '&'){
				param = '&'+param;
			}
		}
		return param;
	},
	
	stop : function(cause){
		if(!this.running){
			return;
		}
		if(this.fireEvent('beforeStop',cause,this.cId, this.url,  this) === false){
			return;
		}
		this.running = false;
		var cId = this.cId;
		this.cId = '';
		this.channels = [];
		this.workStyle = '';
		try{
			this._xhr.abort();
		}catch(e){};
		this.fireEvent('stop',cause, cId, this.url, this);
	},
	
	getId : function(){
		return this.cId;
	}
});

JS.ns("JS.Engine");
JS.Engine = (function(){
	var Engine = JS.extend(JS.Observable,{
		lStore : [],//用于存放没启动状态下用户增加的侦听
		 
		running : false,
		 
		connector : null,
		constructor:function(){
			this.addEvents([
				   
				'start',
				
				'stop'
			]);
			Engine.superclass.constructor.apply(this,arguments);
			this.connector = new JS.Connector();
			this.initEvent();
		},
		
		//重载addListener函数
		addListener : function(eventName, fn, scope, o){
			if(this.running){
				Engine.superclass.addListener.apply(this,arguments);
			}else{
				this.lStore.push({
					eventName : eventName,
					fn : fn,
					scope : scope,
					o : o
				});
			}
		},
		//private 
		initEvent : function(){
			var self = this;
			this.connector.on({
				connect : function(cId, aml, conn){
					//self.running = true;
					self.addEvents(aml);
					for(var i=0,len=self.lStore.length; i<len; i++){
						var e = self.lStore[i];
						self.addListener(e.eventName,e.fn,e.scope);
					}
					self.fireEvent('start', cId, aml, self);
				},
				stop : function(cause, cId, url, conn){
					self.running = false;
					self.fireEvent('stop',cause, cId,url, self);
					self.clearListeners();
				},
				message : function(amk, data, time){
					self.fireEvent(amk, data, time, self);
				}
			});
		},
		
		start : function(url,param){
			this.running = true;
			
			for(var i=0,len=this.lStore.length; i<len; i++){
				var e = this.lStore[i];
				this.addListener(e.eventName,e.fn,e.scope);
			}
			this.connector.start(url,param);
		},
		
		stop : function(cause){
			if(!this.running){
				return;
			}
			this.connector.stop(cause);
		},
		
		getConnector : function(){
			return this.connector;
		},
		
		getId : function(){
			return this.connector.cId;
		}
		
	});
	return new Engine();
}());
