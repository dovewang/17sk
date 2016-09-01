window.debug=true;
var Q={};
var core_toString = Object.prototype.toString, class2type = {}, type_array = ["Boolean", "Number", "String", "Function", "Array", "Date", "RegExp", "Object"];
for (var _t = 0; _t < type_array.length; _t++) {
	class2type["[object " + type_array[_t] + "]"] = type_array[_t].toLowerCase();
}

Q.type=window.type = function(obj) {
	return obj === null ? String(obj) : class2type[ core_toString.call(obj)] || "object";
};
Q.isWindow=window.isWindow = function(obj) {
	return obj && typeof obj === 'object' && 'setInterval' in obj;
};
/**
 *调试信息
 */
Q.trace=window.trace=window.debug?(window.console&&window.console.log?function(){window.console.log(arguments);}:window.alert):function() {};
/**
 *错误时显示的消息
 */
//window.onerror = function(message, url, line) {
	//window.trace("[Error]Message:" + message + "\nUrl:" + url + "\nLine:" + line);
	//return true;
//};
/*******************************************************************************
 *
 * javascript Assert
 *
 ******************************************************************************/
Q.asset=window.assert = function(expression, message) {
	if (!expression) {
		throw new Error(message);
	}
};
/**
 * 判断一个字符串或者对象是否为空
 * @param {Object} str
 * @param {Object} defaultValue 可选
 * @return  {Boolean}  or  {Object} (仅当有默认值的时候有效)
 */
Q.isEmpty=window.isEmpty = function(str) {
	var b = ( str === undefined||str === null || str.length === 0 );
	if (arguments.length == 2) {
		if (!b) {
			return str;
		} else {
			return arguments[1];
		}
	} else {
		return b;
	}
};
/**
 * 判断是否是个空串
 */
Q.isBlank=window.isBlank = function(str) {
	var b = (str === undefined ||str === null || str.trim() === "");
	if (arguments.length == 2) {
		if (!b) {
			return str;
		} else {
			return arguments[1];
		}
	} else {
		return b;
	}
};
/**
 *判断一段HTML代码是否为空，过滤掉所有的标签后才进行的判断
 */
Q.isHTMLBlank=window.isHTMLBlank = function(str) {
	if (Q.isBlank(str)) {
		return true;
	}
	/*	去除HTML tag*/
	str = str.replace(/<\/?[^>]*>/g, '');
	/*去除行尾空白*/
	str = str.replace(/[ | ]*\n/g, '\n');
	/*去除多余空行*/
	str = str.replace(/\n[\s| | ]*\r/g, '\n');
	if (isBlank(str)) {
		return true;
	}
	return false;
};
/**
 *
 */
Q.when=window.when = function(value) {
	var elem = [], defaultValue;
	for (var i = 1; i < arguments.length; i++) {
		elem.push(arguments[i]);
	}
	if (elem.length % 2 !== 0) {
		defaultValue = elem[elem.length - 1];
	}
	if (value && elem.length > 1) {
		for (i = 0; i < elem.length / 2; i++) {
			var e = elem[i * 2];
			if (o(value).equals(e)) {
				return elem[i * 2 + 1];
			}
		}
	}
	return defaultValue;
};
/**
 *  is(1,":number")=true;
 * is("123","string")=true;
 */
Q.is=window.is = function(value, type) {
	if (Q.isEmpty(value) || type ===null || type.length === 0){
		return false;
	}
	if (value == type) {
		return true;
	}
	var v = Q.type(value);
	var t = Q.type(type);
	/*检查类型是否匹配*/
	if (v == type) {
		return true;
	}
	/**/
	if (v == "string") {
		if (t == "string") {
			/*预定义正则表达式，必须已":"开头*/
			if (type.startsWith(":")) {
				return value.is(type);
			}
		} else {
			/*这里只能是正则表达式*/
			return new RegExp(type).test(value);
		}
	}
	return false;
	
};

/*window.now 需要定义为服务器的时间*/
Q.now=window.now = window.now || new Date();



/**
 *  常量包
 */

var Kiss = {
	version : "0.2",
	actions : [],
	define : function(func) {
		Kiss.actions.push(func);
		return Kiss;
	},
	initialize : function(container) {
		trace("initialize    [" + container.tagName + "#" + isEmpty(container.id, "") + "]............");
	},
	initialized : function(container) {
		trace("initialized!");
	},
	completed : function(container) {
		/*绑定事件方法*/
		$("[node-type]", container).each(function(index) {
			var self = this, 
			$this = $(self), 
			at = $this.attr("node-type").trim().mtrim().split(" "),
			parent=$this.data("container");/*记录父容器*/
			/*记录当前容器*/
			$this.data("container", {parent:parent,container:container});
			for (var n = 0; n < at.length; n++) {
				Kiss.bindEvent({
					type : at[n],
					target : self
				});
			}
		});
		/*全局方法*/
		for (var i = 0; i < Kiss.actions.length; i++) {
			var func = Kiss.actions[i];
			func(container);
		}

		trace("completed!");
	},
	start : function(container) {
		var a = new Date().getTime();
		Kiss.initialize(container);
		Kiss.initialized(container);
		Kiss.completed(container);
		trace("load jquery bind event time:" + (new Date().getTime() - a) + "ms");
	},
	bindEvent : function(event) {
		var func = Kiss.actions[event.type];
		if ($.isFunction(func)) {
			try {
				func(event.target);
			} catch(e) {
				trace(event.type + ":" + e.message);
			}
		} else {
			trace(event.type + ": no such action method!")
		}
	},
	addEventListener : function(type, func) {
		if ($.isPlainObject(type)) {
			for (var i in type) {
				Kiss.actions[i] = type[i];
			}
		} else {
			Kiss.actions[type] = func;
		}
		return Kiss;
	}
};

/*基础方法*/
$(function() {
	Kiss.addEventListener({
		/*安全相关*/
		"security" : function(obj) {
			$(obj).on("click", function(event) {
				if (!Env.isLogin) {//用户未登陆执行检查
					event.preventDefault();
					event.stopPropagation();
					if ($.showLogin)
						$.showLogin();
					$(this).die('click');
				}
			});
		},
		"loader-link" : function(obj) {
			var $this = $(obj);
			$this.on("click", function(event) {
				$($this.attr("loader-target")).load($this.attr("href"));
				$this.addClass("active");
				event.preventDefault();
			});
			if ($this.attr("autoload")) {
				$this.click();
			}
		},
		"redirect-time" : function(obj) {
			var $this = $(obj);
			window.setInterval(function() {
				var time = $this.text() * 1;
				if (time == 1) {
					location.href = url;
				} else {
					time--;
					$this.text(time)
				}
			}, 1000)
		},
		"link" : function(obj) {//ajax链接加载当前页面,可以指定加载的容器，默认为当前ajax加载的路径，不适用于跨域
			var me = $(obj), 
			    mode = me.attr("mode") || "click", 
				container=me.data("container"),/*系统默认创建的容器*/
				target = me.data("target") || (container?container.container:undefined)/*使用ajax加载时自动记录*/;
			    me.on(mode, function(event) {
				event.preventDefault();
				$(target).load(me.attr("href"),function(){

				});
			})
		},
		"history.back" : function(obj) {//返回历史
			var me = $(obj), 
			    container=me.data("container"),
				c=$(container.container), 
				h = c.data("history"),
			    url = h ? h[h.length - 2] : false;
			 me.on("click", function(event) {
				if (url) {
					c.load(url.url, url.params, url.callback);
				}
				event.preventDefault();
			});
		},
		"refresh" : function(obj) {
			var me = $(obj), 
			container=me.data("container"),
			c=$(container.container),
			h = c.data("history"),
			url = h ? h[h.length - 1] : false;
			me.on("click", function(event) {
				if (url) {
					c.load(url.url, url.params, url.callback);
				}
				event.preventDefault();
			});
		}
	});
	Kiss.start(document.body);
});
/**
 * 基于jQuery的公共基础方法
 */
;(function($) {
	$.fn.reload = function() {
		return this.each(function() {
			Kiss.start(this);
		});
	}
})(jQuery);

/* 获取flash对象 */
function getFlashMovieObject(movieName) {
	if (window.document[movieName]) {
		return window.document[movieName];
	} else if (navigator.appName.indexOf("Microsoft Internet") == -1) {
		if (document.embeds && document.embeds[movieName])
			return document.embeds[movieName];
	} else {
		return document.getElementById(movieName);
	}
};(function() {
	UA = {};
	var _ua = navigator.userAgent.toLowerCase(), match, _i = 0;
	var browsers = [/(chrome)[ \/]([\w.]+)/, /(safari)[ \/]([\w.]+)/, /(opera)(?:.*version)?[ \/]([\w.]+)/, /(msie) ([\w.]+)/, /(mozilla)(?:.*? rv:([\w.]+))?/, /(360se)/, /(360se)/];
	var platforms = [/(ip\w+).*?os ([\w_]+)/, /(android)[ \/]([\w.]+)/, /(blackberry)(?:\d*?\/|.*?version\/)([\w.]+)/, /(windows phone)( os)? ([\w.]+)/, /(symbian)(?:os\/([\w.]+))?/];
	var core = [/applewebkit\/([\d.]*)/, /presto\/([\d.]*)/, /msie\s([^;]*)/, /gecko/];
	/**
	 *
	 * 判断浏览器的类型及版本
	 *
	 */
	UA.browser = {};

	while (_i < browsers.length) {
		if (( match = browsers[_i].exec(_ua)) && match[1]) {
			UA.browser[match[1]] = true;
			UA.browser.version = match[2] || "0";
			break;
		}
		_i++;
	}
	/*
	 *判断运行的平台
	 */
	UA.platform = {};
	_i = 0;
	while (_i < platforms.length) {
		if (( match = platforms[_i].exec(_ua)) && match[1]) {
			UA.platform[match[1].replace(" p", "P")] = true;
			UA.platform.version = match[2].split("_").join(".") || "0";
			break;
		}
		_i++;
	}
})(window);

/**
 * 将字符串去空格
 */
String.prototype.trim = function(type) {
	if (type) {
		switch(type) {
			case "all":
				return this.replace(/[ ]/g, "");
			case "left":
				return this.ltrim();
			case "middle":
				return this.mtrim();
			case "right":
				return this.rtrim();
		}
	}
	/*默认只去除两边的空格*/
	return this.replace(/(^\s*)|(\s*$)|\r|\n/g, "");
};
/**
 * 去除左空格
 */
String.prototype.ltrim = function() {
	return this.replace(/^\s*/g, "");
};
/*去除中间多余的空格*/
String.prototype.mtrim = function(isAll) {
	var str = "", self = this;
	for (var i = 0; i < self.length; i++) {
		if (self.substring(i, i + 1) != " ")
			str = str + self.substring(i, i + 1);
		else {
			if (str.substring(str.length - 1, str.length) != " ") {
				str = str + " ";
			}
		}
	}
	return str;
};
/**
 * 去除右空格
 */
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)|\r|\n/g, "");
};
/**
 * 替换第一个
 */
String.prototype.replaceFirst = function(s, t) {
	var index = this.indexOf(s);
	return this.substring(0, index) + t + this.substring(index + 1);
};
/**
 *  替换最后一个
 */
String.prototype.replaceLast = function(s, t) {
	var index = this.lastIndexOf(s);
	return this.substring(0, index) + t + this.substring(index + 1);
};
/**
 * 替换字符
 *
 * @param string
 * @param replace
 */
String.prototype.replaceAll = function(string, replace) {
	return this.replace(new RegExp(string, "gm"), replace);
};
/**
 * 以某个字符串开头
 *
 * @param str
 */
String.prototype.startsWith = function(str) {
	return this.substring(0, str.length) == str;
};
/**
 * 以某个字符串结尾
 *
 * @param str
 */
String.prototype.endsWith = function(str) {
	if (str.length > this.length) {
		return false;
	}
	return this.substring(this.length - str.length) == str;
};
/**
 * 添加一个字符
 *
 * @param str
 */
String.prototype.append = function() {
	var self = this;
	for (var i = 0; i < arguments.length; i++) {
		self = self.concat(arguments[i]);
	}
	return self;
};
/**
 * 连接URL地址 例如：地址a=http://123.com?a=5&b=6 地址 b=http://234.com?c=5&r=6
 * 以A为链接的连接地址为http://123.com?a=5&b=6&c=5&r=6
 */
String.prototype.concatURL = function(url) {
	url = url.substring(url.indexOf("?") + 1);
	return this.indexOf("?") != -1 ? (this + "&" + url) : (this + "?" + url);
};
/**
 * 字符分割，和split方法不同
 *
 * <pre>
 * null, &quot;&quot;, &quot;  &quot;
 * separator = [];
 * </pre>
 *
 * @param s
 *            分割符
 * @param fn
 *            单个分割符处理，默认类型将所有的空字符串处理 function(result,data,index);
 */
String.prototype.separator = function(s, fn) {
	var result = [];
	/* 为空直接返回 */
	if (isEmpty(this)) {
		return result;
	}
	var tmp = this.split(s);
	for (var i = 0; i < tmp.length; i++) {
		if (!fn) {
			if (!isEmpty(tmp[i])) {
				result.push(tmp[i].trim());
			}
		} else {
			fn(result, tmp[i], i);
		}
	}
	return result;
};
/**
 * 当长度不满足要求是，自动在左侧填充字符
 *
 * @param ch
 *            填充字符
 * @param len
 *            整个字符串的长度，不满足长度时填充
 */
String.prototype.lpad = function(ch, len) {
	var s = this;
	while (s.length < len) {
		s = ch + s;
	}
	return s;
};
/**
 * 当长度不满足要求是，自动在右侧填充字符
 *
 * @param ch
 *            填充字符
 * @param len
 *            整个字符串的长度，不满足长度时填充
 */
String.prototype.rpad = function(ch, len) {
	var s = this;
	while (s.length < len) {
		s = s + ch;
	}
	return s;
};
/**
 * 判断是否是:
 * 例如是否是数字
 * "1".is(":number")
 * "dream@164.com".is(":email")
 */
String.prototype.is = function(regex) {
	var me=this;
	if (Q.type(regex) == "regexp") {
		return regex.test(me);
	} else {		
		if (regex.startsWith(":")) {
			var expressions = regex.substring(1).split(":");
			var result = true;
			for (var i=0; i<expressions.length;i++) {
				var expr=expressions[i],
				     diy = expr.indexOf("["),
				    index = 0,
					is = true;
				if (expr.startsWith("!")) {
					index = 1;
					is = false;
				}
				var type = expr.substring(index);
				var left = expr.indexOf("(");
				if (diy == -1 && left > -1) {
					type = expr.substring(index, left);
					var params = expr.substring(left + 1,expr.indexOf(")")).split(",");
					result = result
							&& (is ? Q.r[type](me, params) : !Q.r[type](me, params));
				} else {
					if (diy != -1) {
						type = type.substring(type.indexOf("[") + 1,type.indexOf("]"));
						result = result
								&& (is ? Q.is(me, type) : !Q.is(me, type));
					} else {
						result = result
								&& (is ? Q.r[type](me): !Q.r[type](me));
					}
				}
				/* 当验证未通过时直接返回 */
				if (!result) {
					return result;
				}
			}
			return result;
		}
		return (new RegExp(regex).test(me));
	}
};
/*字符串逆序*/
String.prototype.reverse = function() {
	return this.toCharArray().reverse().join("");
};
/*******************************************************************************
 *
 * 字符串转换函数
 *
 ******************************************************************************/
/*字符串转换为字符数组*/
String.prototype.toCharArray = function() {
	return this.split("");
}
/**
 * 转换为整数
 *
 * @param defaultValue
 *            转换失败时的默认值
 */
String.prototype.toInteger = String.prototype.toInt = function(defaultValue,radix) {
	if (this.is(":integer")) {
		return parseInt(this,radix);
	}
	return defaultValue;
};
/**
 * 转换为浮点数
 *
 * @param defaultValue
 *            转换失败时的默认值
 */
String.prototype.toFloat = function(defaultValue) {
	if (this.is(":number")) {
		return parseFloat(this);
	}
	return defaultValue;
};
/**
 * 将字符串转化为日期，目前只支持yyyy-mm-dd HH:MM:ss格式的数据转换
 *
 * @param defaultDate
 *            转换失败时的默认值
 */
String.prototype.toDate = function(defaultDate) {
	return this.toTime(defaultDate).toDate();
};
/**
 * 将字符串转化为毫秒数，目前只支持yyyy-mm-dd HH:MM:ss或yyyy/mm/dd HH:MM:ss
 *
 * @param 可以是毫秒数或字符串
 */
String.prototype.toTime = function(defaultTime) {
	var d = this.replaceAll("-", "/");
	try {
		return Date.parse(d);
	} catch (e) {
		trace(e.message);
		if ( typeof defaultTime != "string") {
			return defaultTime;
		} else {
			return Date.parse(defaultTime);
		}
	}
};
/**
 * 默认返回秒数
 *
 * @param 可以是毫秒数或字符串
 */
String.prototype.toSecondsTime = function(defaultDate) {
	return parseInt(this.toTime(defaultDate) / 1000);
};
/**
 * 将字符串转换成html源码
 *
 * @memberOf string
 * @return {Sring} 返回转换后的html代码字符串
 */
String.prototype.toHtml = function() {
	return this.replace(/&/gi, "&amp;").replace(/\\/gi, "&#92;").replace(/\'/gi, "&#39;").replace(/\"/gi, "&quot;").replace(/</gi, "&lt;").replace(/>/gi, "&gt;").replace(/ /gi, "&nbsp;").replace(/\r\n/g, "<br />").replace(/\n\r/g, "<br />").replace(/\n/g, "<br />").replace(/\r/g, "<br />");
};

/**
 *过滤掉HTML标签后返回的代码
 */
String.prototype.filterHTML = function() {
	return this.replace(/<\/?[^>]*>/g, '');
};
/*
 *  计算包含汉字的长度
 * @chinese  是否将中文1个字符按2长度2计算，默认为true
 * */
String.prototype.size = function(chinese) {
	var self = this, len = self.length, l = 0;
	if (chinese === false) {
		return len;
	}
	/*将中文字符按长度2计算*/
	for (var i = 0; i < len; i++) {
		if (self.charCodeAt(i) < 27 || self.charCodeAt(i) > 126) {
			l += 2;
		} else {
			l++;
		}
	}
	return l;
};
/**
 *过滤字符，直接调用replaceAll也可
 * @param {Object} string
 */
String.prototype.filter = function(string) {
	return this.replaceAll(string, "")
};
/*
 *  包含
 */
String.prototype.contains=String.prototype.hasText= function(string) {
	return this.indexOf(string) != -1;
};
/**
 *将字符串格式的方法转换为方法，兼容所有浏览器
 * 例如"test".toFunction()(123123); 可以直接调用function test(aaa){alert(a)}
 * 或者 "function(a){alert(a)}".toFunction()(123123);
 */
String.prototype.toFunction = function() {
	eval("var _=" + this + ";");
	return _;
};
/**数字转化为日期*/
Number.prototype.toDate = function() {
	var date = new Date();
	date.setTime(this);
	return date;
};
/***
 * @memberOf format
 * @method number
 * 格式化数字显示方式
 * @param num 要格式化的数字
 * @param pattern 格式
 * @example
 * format(12345.999,'#,##0.00')=12,34,5.99
 * format(12345.999,'0')=12345
 *  format(1234.888,'#.0');
 *  //out: 1234.8
 * number(1234.888,'000000.000000');
 *  //out: 001234.888000
 */
Number.prototype.format = function(pattern) {
	var num=this;
	var strarr = num ? num.toString().split('.') : ['0'];
	var fmtarr = pattern ? pattern.split('.') : [''];
	var retstr = '';
	// 整数部分
	var str = strarr[0];
	var fmt = fmtarr[0];
	var i = str.length - 1;
	var comma = false;
	for (var f = fmt.length - 1; f >= 0; f--) {
		switch (fmt.substr(f, 1)) {
			case '' :
				if (i >= 0)
					retstr = str.substr(i--, 1) + retstr;
				break;
			case '0' :
				if (i >= 0)
					retstr = str.substr(i--, 1) + retstr;
				else
					retstr = '0' + retstr;
				break;
			case ',' :
				comma = true;
				retstr = ',' + retstr;
				break;
		}
	}
	if (i >= 0) {
		if (comma) {
			var l = str.length;
			for (; i >= 0; i--) {
				retstr = str.substr(i, 1) + retstr;
				if (i > 0 && ((l - i) % 3) == 0)
					retstr = ',' + retstr;
			}
		} else
			retstr = str.substr(0, i + 1) + retstr;
	}

	retstr = retstr + '.';
	// 处理小数部分
	str = strarr.length > 1 ? strarr[1] : '';
	fmt = fmtarr.length > 1 ? fmtarr[1] : '';
	i = 0;
	for (var f = 0; f < fmt.length; f++) {
		switch (fmt.substr(f, 1)) {
			case '' :
				if (i < str.length)
					retstr += str.substr(i++, 1);
				break;
			case '0' :
				if (i < str.length)
					retstr += str.substr(i++, 1);
				else
					retstr += '0';
				break;
		}
	}
	return retstr.replace(/^,+/, '').replace(/\.$/, '');
};
/*
*
*/
Number.range=function(value,min,max){
	if(value<min){
		return min;
	}	
	if(value>max){
		return max;
	}
	return value;
}
Array.prototype.reduce=function(){

}
/**
 * 去除重复的值，数组可以存储任何对象，包括无序的JSON对象都可以进行比较
 *  例如：[{a:1,b:2},{b:2,a:1}].unique()=[{a:1,b:2}]
 * @return  {Array}
 */
Array.prototype.unique = function() {
	var self = this;
	if (self.length == 0) {
		return [];
	}
	var _array = [], has = false;
	_array[0] = self[0];
	for (var i = 1; i < self.length; i++) {
		has = false;
		for (var j = 0; j < _array.length; j++) {
			/*使用equals可以比较数组中的对象*/
			if (_array[j] != null && (o(_array[j]).equals(self[i]))) {
				has = true;
				break;
			}
		}
		if (!has) {
			_array.push(self[i]);
		}
	}
	return _array;
};
/*将数组转换成Map*/
Array.prototype.toMap = function(fn) {
	if(!fn){
		fn=function(v){
			return 1;
		}
	}
	var map = {};
	for (var i = 0; i < this.length; i++) {
		map[new String(this[i])] = fn(this[i]);
	}
	return map;
};
Date.prototype.toSecondsTime = function() {
	return parseInt(this.getTime() / 1000);
};
/**
 * 日期格式化
 *
 * @param format
 *            日期格式 yyyy-mm-dd HH:MM:SS
 * @return Date
 */
Date.prototype.format = function(format) {
	var mask = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g;
	var y = this.getFullYear(), m = this.getMonth(), d = this.getDate(), H = this.getHours(), M = this.getMinutes(), s = this.getSeconds();
	var flags = {
		yy : String(y).slice(2),
		yyyy : y,
		m : m + 1,
		mm : String(m + 1).lpad("0", 2),
		d : d,
		dd : String(d).lpad("0", 2),
		h : H % 12 || 12,
		hh : String(H % 12 || 12).lpad("0", 2),
		H : H,
		HH : String(H).lpad("0", 2),
		M : M,
		MM : String(M).lpad("0", 2),
		s : s,
		ss : String(s).lpad("0", 2),
		tt : H < 12 ? "am" : "pm",
		TT : H < 12 ? "AM" : "PM"
	};
	return format.replace(mask, function($0) {
		return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
	});
};
/*
* @return {Boolean}
*/
Date.prototype.before = function(date) {
	return this.compareTo(date) < 0;
};

/*
* @return {Boolean}
*/
Date.prototype.after = function(date) {
	return this.compareTo(date) > 0;
};
/*时间同步，需要时间同步器每次返回timeoffset,页面加载也需要时间(即便是同步，服务器从执行到返回仍旧需要时间，所以这个时间到客户端后基本都是不准确的)，这里我们不统计客户端的时区*/
Date.now = function(force) {
	if (force) {
		var c = new Date().getTime();
		$.getSynch("/timesynch.html", {
			time : c
		}, function(offset) {
			/*程序运行的时间误差*/
			l = c - new Date().getTime();
			try {
				/*最后计算的时间误差*/
				window.timeOffset =  parseInt(offset)-l;
			} catch(e) {

			}
			trace("timeOffset:" + window.timeOffset);
		})
	}
	return new Date().getTime() - (window.timeOffset || 0);
};/*正则表达式*/
Q.r=window.regexp = {
	username : function(str) {
		return   Q.is(str,/^[\u4e00-\u9fa5\w]*$/);
	},
	number : function(str) {
		return  Q.is(str,/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/);
	},
	integer : function(str,param) {
		return /^-?[1-9]\d*$/.test(str);
	},
	"integer+" : function(str) {
		return /^[0-9]*[1-9][0-9]*$/.test(str);
	},
	"integer-" : function(str) {
		return /^-[0-9]*[1-9][0-9]*$/.test(str);
	},
	"integer+(0)" : function(str) {
		return /^\d+$/.test(str);
	},
	"integer-(0)" : function(str) {
		return /^((-\d+)|(0+))$/.test(str);
	},
	URL : function(str) {
		return Q.is(str,/^[a-zA-z]+:\/\/([a-zA-Z0-9\-\.]+)([-\w .\/?%&=:]*)$/);
	},
	externalURL : function(str) {
		return Q.r.URL(str) && str.indexOf(document.domain) == -1;
	},
	chinese : function(str) {
		return /(^[\u4e00-\u9fa5]*$)/.test(str);
	},
	qq : function(str) {
		return /^[1-9][0-9]{4,}$/.test(str);
	},
	email : function(str) {
		return (new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(str));
	},
	phone : function(str) {
		return (new RegExp(/(^([0-9]{3,4}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0-9]{3,4}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/).test(str));
	},
	mobile : function(str) {
		return /^([0-9]{11,12})$/.test(str);
	},
	zipcode : function(str) {
		return /^[0-9]{6}$/.test(str);
	},
	money : function(str) {
		return /^\d{1,6}(\.\d{0,2})?$/.test(str);
	},
	datetime : function(str) {
		return /^\d{4}-\d{2}-\d{2}$/.test(str);
	},
	idcard : function(str) {
		return /^\d{14}(\d{1}|\d{4}|(\d{3}[xX]))$/.test(str);
	},
	image : function(str) {
		return /\.(gif|jpg|jpeg|bmp|png)$/.test(str.toLowerCase());
	},
	video : function(str, diy) {
		if (!diy)
			return /\.(avi|wmv|flv|mpeg|mov|asf|navi|3gp|rm|f4v|mp4|vob)$/.test(str.toLowerCase());
		else
			return diy.test(str.toLowerCase());
	},
	address : function(str) {
		return /(^[\u4e00-\u9fa5]*[0-9a-zA-Z]*([\u4e00-\u9fa5]|[0-9a-zA-Z])*$)/.test(str);
	},
	notHTMLBlank:function(str){
		return !window.isHTMLBlank(str);
	},
	htmlBlank:function(str){
		return window.isHTMLBlank(str);
	}
};
/*由于Object.prototype不能被扩展，
 * 这里需要使用该方法生成新的对象，
 * 可以调用相关的继承方法*/
function o(o) {
	return new _Object(o);
}
/*覆盖原始对象*/
function _Object(o) {
	this.object = o;
	/** 比较当前对象与指定对象是否相等。
	 * 覆写并扩展基类 (Object) ，方便子类 ( 如： Array) 扩展此方法。
	 * 这个方法可能用到 string, number, function 等基本数据类型的 wapper 类 (String, Number, Function) 的 equals 方法。
	 * @param obj, Object.
	 * @return Boolean.
	 */
	this.equals = function(obj) {
		var self = this.object;
		if (self === obj) {
			return true;
		}
		if (!( obj instanceof Object ) || (obj === null )) {
			return false;
		}// null is not instanceof Object.
		var i = 0;
		// object property counter.
		for (var k in self ) {
			i++;
			var o1 = self[k];
			var o2 = obj[k];
			if ((o1 != undefined) && !( o1.equals(o2))) {
				return false;
			} // inner object.
		}
		for (var k in obj ) {// compare object property counter.
			i--;
		}
		return i === 0;
	};

	this.compareTo = function(o) {
		return 0;
	}
	/*转换为原始对象*/
	this.toObject = function() {
		return this.object;
	}

	return this;
}

/** 比较当前函数对象与指定对象的值是否完全相等（包括数据类型）。
 * 函数的比较比较复杂和怪异，两个构造完全一致的函数的 valueOf() 值并不相同，这个可以理解。
 * 而使用 toString() 方法，是否也应该先将他们的无效空格和换行去掉？似乎问题变得复杂了。
 * 最大的问题是， new Function() 和 function() {} 的 toString() 方法在不同浏览器中表现不同，详情附注。
 * 出于简单性，一致性和函数的特殊性考虑，函数仅且仅在和本身比较时才相等。
 * @param number, Number.
 * @return Boolean.
 */
Function.prototype.equals = function(fun) {
	return (type(fun) === "function") && (this.valueOf() === fun.valueOf());
};

Function.prototype.compareTo = function() {
	return 0;
};
/** 比较当前字符串对象与指定对象是否相等。
 * @param string, String, Object.
 * @return Boolean.
 */
String.prototype.equals = function(string) {
	return (type(string) === "string" ) && (this.valueOf() === string.valueOf());
};

/*
 * 忽略大小写，比较2个字符串是否相等
 * */
String.prototype.equalsIgnoreCase = function(string) {
	if (type(string) === "string") {
		return this.equals(string.toLocaleLowerCase());
	}
	return false;
};
/**
 *
 */
String.prototype.compareTo = function(str) {
	if (!(type(str) === "string")) {
		return NaN;
	}
	var v1 = self.toCharArray();
	var v2 = str.toCharArray();
	var self = this, len1 = self.length, len2 = str.length;
	var n = Math.min(len1, len2);
	var v1 = self.toCharArray();
	var v2 = str.toCharArray();
	var i = 0;
	while (n-- != 0) {
		var c1 = v1[i];
		var c2 = v2[i];
		if (c1 != c2) {
			return c1 - c2;
		}
		i++;
	}
	return len1 - len2;
};
/**
 *
 */
Number.prototype.compareTo = function(number) {
	if (!(type(number) === "number")) {
		return NaN;
	}
	return this.valueOf() - number.valueOf();
};
/** 比较当前数字对象与指定对象是否完全相等（包括数据类型）。
 * @param number, Number.
 * @return Boolean.
 */
Number.prototype.equals = function(number) {
	return (type(number) === "number" ) && (this.valueOf() === number.valueOf());
};

Boolean.prototype.compareTo = function(bool) {
	if (!(type(bool) === "boolean")) {
		return NaN;
	}
	return this.valueOf() - bool.valueOf();
};
/** 比较当前布尔对象与指定对象的值是否完全相等（包括数据类型）。
 * @param  {Boolean}   bool, Boolean.
 * @return {Boolean}.
 */
Boolean.prototype.equals = function(bool) {
	return (type(bool) === "boolean" ) && (this.valueOf() === bool.valueOf());
};

/**
 *和另一个日期比较
 * @param {Date}  date
 * @param {Object} mode
 *            mode=true  比较是否是同一天，和mode="y,m,d"相同
 *            mode="y,m,d,H,M,s"   代表需要比较的字段
 * @return  ${Number}小于0代表比这个小，等于0代表2个日期相等，大于0代表比这个日期大,NaN代表无法比较（参数非日期）
 */
Date.prototype.compareTo = function(date, mode) {
	if (type(date) === "date") {
		/*转换定义，保留原始用法*/
		if (mode === true) {
			mode = "y,m,d";
		}
		if (mode) {
			var mx = mode.split(","), y = 0, m = 0, d = 0, H = 0, M = 0, s = 0;
			for (var i = 0; i < mx.length; i++) {
				switch(mx[i]) {
					case "y":
						y = this.getFullYear() - date.getFullYear();
						break;
					case "m":
						m = this.getMonth() - date.getMonth();
						break;
					case "d":
						d = this.getDate() - date.getDate();
						break;
					case "H":
						H = this.getHours() - date.getHours();
						break;
					case "M":
						M = this.getMinutes() - date.getMinutes();
						break;
					case "s":
						s = this.getSeconds() - date.getSeconds();
						break;
				}
			}
			return y != 0 ? y : (m != 0 ? m : (d != 0 ? d : (H != 0 ? H : (M != 0 ? M : s))));
		}
		return this.getTime() - date.getTime();
	}
	return NaN;
};
/**
 *比较2个日期是否相等
 * @param {Date} date
 * @param {Object} mode
 * @see #Date.prototype.compareTo
 * @return {Boolean}
 */
Date.prototype.equals = function(date, mode) {
	return this.compareTo(date, mode) === 0;
};

Array.prototype.compareTo = function(array) {
	var self = this, len1 = self.length, len2 = array.length;
	var n = Math.min(len1, len2);
	var i = 0;
	while (n-- != 0) {
		if (self[i] != array[i]) {
			/*数组中可能存在不同的对象，可能无法比较大小*/
			return self[i].compareTo(array[i]);
		}
		i++;
	}
	return len1 - len2;
};
/**
 *比较2个数组是否相等
 * @param {Object} array
 * @return {Boolean}
 */
Array.prototype.equals = function(array) {
	if (type(array) === "array" && this.length == array.length) {
		for (var i = 0; i < array.length; i++) {
			if (!(o(array[i])).equals(this[i])) {
				return false;
			}
		}
		return true;
	}
	return false;
};;(function($) {
	$.iframe = {
		getWindow : function(iframe) {
			return iframe.contentWindow;
		},
		/**
		 * iframe
		 *
		 * @param iframe
		 * @param isBody
		 *            是否获取body
		 */
		getDocument : function(iframe, isBody) {
			var doc = iframe.contentWindow ? iframe.contentWindow.document : iframe.contentDocument ? iframe.contentDocument : iframe.document;
			if (isBody) {
				return $.iframe.getBody(doc);
			} else {
				return doc;
			}
		},
		getBody : function(doc) {
			return doc.body ? doc.body : doc.documentElement;
		}
	};
})(jQuery);
;(function($) {
	/**
	 * 获取当前对象的样式，只能获取第一个元素的样式
	 */
	$.fn.getStyles = function() {
		var dom = this.get(0);
		var style;
		var styles = {};
		if (window.getComputedStyle) {
			var camelize = function(a, b) {
				return b.toUpperCase();
			};
			style = window.getComputedStyle(dom, null);
			for (var i = 0, l = style.length; i < l; i++) {
				var prop = style[i];
				var camel = prop.replace(/\-([a-z])/g, camelize);
				var val = style.getPropertyValue(prop);
				styles[camel] = val;
			};
			return styles;
		};
		if ( style = dom.currentStyle) {
			for (var prop in style) {
				styles[prop] = style[prop];
			};
			return styles;
		};
		if ( style = dom.style) {
			for (var prop in style) {
				if ( typeof style[prop] != 'function') {
					styles[prop] = style[prop];
				};
			};
			return styles;
		};
		return styles;
	}
	/**
	 * 复制源样式到目标对象上
	 */
	$.fn.copyStyles = function(source) {
		var styles = $(source).getStyles()
		this.css(styles);
	}
})(jQuery);
/**
 * 实现Map
 */
function Map() {

	this.elements = [];

	this.size = function() {
		return this.elements.length;
	}

	this.isEmpty = function() {
		return (this.elements.length == 0);
	}

	this.clear = function() {
		this.elements = [];
	}

	this.put = function(_name, _value) {
		this.remove(_name);
		this.elements.push({
			name : _name,
			value : _value
		});
	}

	this.remove = function(_name) {
		try {
			for(var i = 0; i < this.elements.length; i++) {
				if(this.elements[i].name == _name) {
					this.elements.splice(i, 1);
					return true;
				}
			}
		} catch (e) {
			return false;
		}
		return false;
	}

	this.get = function(_name) {
		try {
			for(var i = 0; i < this.elements.length; i++) {
				if(this.elements[i].name == _name) {
					return this.elements[i].value;
				}
			}
		} catch (e) {
			return null;
		}
	}

	this.element = function(_index) {
		if(_index < 0 || _index >= this.elements.length) {
			return null;
		}
		return this.elements[_index];
	}

	this.containsKey = function(_name) {
		try {
			for(var i = 0; i < this.elements.length; i++) {
				if(this.elements[i].name == _name) {
					return true;
				}
			}
		} catch (e) {
			return false;
		}
		return false;
	}

	this.keys = function() {
		var arr = [];
		for( i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].name);
		}
		return arr;
	}

	this.each = function(fn) {
		if( typeof fn != 'function') {
			return;
		}

		for(var i = 0; i < this.elements.length; i++) {
			fn(elements[i]);
		}
	}

	this.toJSON = function() {
		var s = "{";
		for(var i = 0; i < this.elements.length; i++, s += ',') {
			var k = this.elements[i].name;
			s += k + ":'" + this.elements[i].value + "'";
		}
		s += "}";
		return eval("(" + s + ")");
	}

	this.toString = function() {
		var s = "{";
		for(var i = 0; i < this.elements.length; i++, s += ',') {
			var k = this.elements[i].name;
			s += k + "=" + this.elements[i].value;
		}
		s += "}";
		return s;
	}
}!(function($){  
	/* Convenience methods in jQuery namespace.           */
	/* Use as  $.belowthefold(element, {threshold : 100, container : window}) */
	$.belowthefold = function(element, settings) {
		var fold;

		if(settings.container === undefined || settings.container === window) {
			fold = $window.height() + $window.scrollTop();
		} else {
			fold = $container.offset().top + $container.height();
		}

		return fold <= $(element).offset().top - settings.threshold;
	};

	$.rightoffold = function(element, settings) {
		var fold;

		if(settings.container === undefined || settings.container === window) {
			fold = $window.width() + $window.scrollLeft();
		} else {
			fold = $container.offset().left + $container.width();
		}

		return fold <= $(element).offset().left - settings.threshold;
	};

	$.abovethetop = function(element, settings) {
		var fold;

		if(settings.container === undefined || settings.container === window) {
			fold = $window.scrollTop();
		} else {
			fold = $container.offset().top;
		}

		return fold >= $(element).offset().top + settings.threshold + $(element).height();
	};

	$.leftofbegin = function(element, settings) {
		var fold;

		if(settings.container === undefined || settings.container === window) {
			fold = $window.scrollLeft();
		} else {
			fold = $container.offset().left;
		}

		return fold >= $(element).offset().left + settings.threshold + $(element).width();
	};

	$.inviewport = function(element, settings) {
		return !$.rightofscreen(element, settings) && !$.leftofscreen(element, settings) && !$.belowthefold(element, settings) && !$.abovethetop(element, settings);
	};
	/* Custom selectors for your convenience.   */
	/* Use as $("img:below-the-fold").something() */

	$.extend($.expr[':'], {
		"below-the-fold" : function(a) {
			return $.belowthefold(a, {
				threshold : 0,
				container : window
			});
		},
		"above-the-top" : function(a) {
			return !$.belowthefold(a, {
				threshold : 0,
				container : window
			});
		},
		"right-of-screen" : function(a) {
			return $.rightoffold(a, {
				threshold : 0,
				container : window
			});
		},
		"left-of-screen" : function(a) {
			return !$.rightoffold(a, {
				threshold : 0,
				container : window
			});
		},
		"in-viewport" : function(a) {
			return !$.inviewport(a, {
				threshold : 0,
				container : window
			});
		},
		/* Maintain BC for couple of versions. */
		"above-the-fold" : function(a) {
			return !$.belowthefold(a, {
				threshold : 0,
				container : window
			});
		},
		"right-of-fold" : function(a) {
			return $.rightoffold(a, {
				threshold : 0,
				container : window
			});
		},
		"left-of-fold" : function(a) {
			return !$.rightoffold(a, {
				threshold : 0,
				container : window
			});
		}
	});
	
	
		/**
	 * 判断是否在屏幕中
	 */
	$.expr[":"].screen = function(elem) {
		var $window = $(window)
		var viewport_top = $window.scrollTop()
		var viewport_height = $window.height()
		var viewport_bottom = viewport_top + viewport_height
		var $elem = $(elem)
		var top = $elem.offset().top
		var height = $elem.height()
		var bottom = top + height
		return (top >= viewport_top && top < viewport_bottom) || (bottom > viewport_top && bottom <= viewport_bottom) || (height > viewport_height && top <= viewport_top && bottom >= viewport_bottom)
	}
})(jQuery);  // JavaScript Document
;(function($){
	$.mouse={
		/*获取鼠标相对父元素的坐标*/
		position:function(e){
			var x, y;
			if (e.offsetX) {
				x = e.offsetX;
				y = e.offsetY;
			} else if (e.layerX) {
				x = e.layerX;
				y = e.layerY;
			}else{
				var oe=e.originalEvent;
				 if(oe.layerX){
					 x = oe.layerX;
				     y = oe.layerY;
				 }
			}
			return {
				x : x,
				y : y
			};
		}
	}
})(jQuery);!(function($) {
	// browsers like firefox2 and before and opera doesnt have the onPaste event, but the paste feature can be done with the onInput event.
	var pasteEvent = ((UA.browser.opera || (UA.browser.mozilla && parseFloat(UA.browser.version.substr(0, 3)) < 1.9)) ? 'input' : 'paste');
	// the timeout is set because we can't get the value from the input without it
	var pasteHandler = function(e) {
		e = $.event.fix(e || window.event);
		var target = e.target;
		e.type = "beforepaste"
		/*只触发绑定事件，忽略IE等支持该事件的浏览器*/
		$(target).triggerHandler(e);
		if (!e.isDefaultPrevented()) {
			e.type = "paste";
			$.event.dispatch.call(target, e);
			if (!e.isDefaultPrevented()) {
				e.type = "afterpaste"
				$(target).triggerHandler(e);
			}
		}
	};
	$.event.special.paste = {
		setup : function() {
			if (this.addEventListener)
				this.addEventListener(pasteEvent, pasteHandler, false);
			else if (this.attachEvent)
				this.attachEvent('on' + pasteEvent, pasteHandler);
		},

		teardown : function() {
			if (this.removeEventListener)
				this.removeEventListener(pasteEvent, pasteHandler, false);
			else if (this.detachEvent)
				this.detachEvent('on' + pasteEvent, pasteHandler);
		}
	};

	$.fn.paste = function(data, fn) {
		return arguments.length > 0 ? this.on(name, null, data, fn) : this.trigger(name);
	}
})(jQuery);
/* C
 *  binding an event
 *$("#my_elem").on("mousewheel", function(event, delta, deltaX, deltaY) {
 * console.log(delta, deltaX, deltaY);
 * });
 *
 * // unbinding an event
 * $("#my_elem").off("mousewheel");
 *
 */

(function($, undefined) {

	var types = ['DOMMouseScroll', 'mousewheel', 'MozMousePixelScroll'];

	if ($.event.fixHooks) {
		for (var i = types.length; i; ) {
			$.event.fixHooks[types[--i]] = $.event.mouseHooks;
		}
	}

	$.event.special.mousewheel = {
		setup : function() {
			if (this.addEventListener) {
				for (var i = types.length; i; ) {
					this.addEventListener(types[--i], handler, false);
				}
			} else {
				this.onmousewheel = handler;
			}
		},

		teardown : function() {
			if (this.removeEventListener) {
				for (var i = types.length; i; ) {
					this.removeEventListener(types[--i], handler, false);
				}
			} else {
				this.onmousewheel = null;
			}
		}
	};

	function handler(event) {
		var orgEvent = event || window.event, args = [].slice.call(arguments, 1), delta = 0, deltaX = 0, deltaY = 0;
		event = $.event.fix(orgEvent);
		event.type = "mousewheel";

		// Old school scrollwheel delta
		if (orgEvent.wheelDelta) {
			delta = orgEvent.wheelDelta / 120;
		}

		if (orgEvent.detail) {
			if (orgEvent.type == types[2]) {
				// Firefox 4+, unbind old DOMMouseScroll event
				this.removeEventListener(types[0], handler, false);
				delta = -orgEvent.detail / 42;
			} else {
				delta = -orgEvent.detail / 3;
			}
		}

		// New school multidimensional scroll (touchpads) deltas
		deltaY = delta;

		// Gecko
		if (orgEvent.axis !== undefined && orgEvent.axis === orgEvent.HORIZONTAL_AXIS) {
			deltaY = 0;
			deltaX = -1 * delta;
		}

		// Webkit
		if (orgEvent.wheelDeltaY !== undefined) {
			deltaY = orgEvent.wheelDeltaY / 120;
		}

		if (orgEvent.wheelDeltaX !== undefined) {
			deltaX = -1 * orgEvent.wheelDeltaX / 120;
		}

		// Add event and delta to the front of the arguments
		args.unshift(event, delta, deltaX, deltaY);

		return ($.event.dispatch || $.event.handle).apply(this, args);
	}


	$.fn.mousewheel = function(data, fn) {
		if (fn == null) {
			fn = data;
			data = null;
		}
		return arguments.length > 0 ? this.on("mousewheel", null, data, fn) : this.trigger("mousewheel");
	};

})(jQuery);
;(function($) {
	$.fn.rotate = function(options) {
		options = $.extend({
			mode : "css3|filter|canvas",
			zoom : .1, //缩放比率
			onPreLoad : function() {

			}, //图片加载前执行
			onLoad : function() {

			}, //图片加载后执行
			onError : function(err) {

			}//出错时执行
		}, options)
		var p = this.get(0);
		if (!options.maxWidth)
			options.maxWidth = $(this).parent().width();
		// we store the angle inside the image tag for persistence
		if (!options.whence) {
			p.angle = ((p.angle == undefined ? 0 : p.angle) + options.angle) % 360;
		} else {
			p.angle = options.angle;
		}

		if (p.angle >= 0) {
			var rotation = Math.PI * p.angle / 180;
		} else {
			var rotation = Math.PI * (360 + p.angle) / 180;
		}
		var costheta = Math.round(Math.cos(rotation) * 1000) / 1000;
		var sintheta = Math.round(Math.sin(rotation) * 1000) / 1000;

		if (document.all && !window.opera) {
			var canvas = document.createElement('img');

			canvas.src = p.src;
			canvas.height = p.height;
			canvas.width = (p.width > options.maxWidth ? options.maxWidth : p.width);

			canvas.style.filter = "progid:DXImageTransform.Microsoft.Matrix(M11=" + costheta + ",M12=" + (-sintheta) + ",M21=" + sintheta + ",M22=" + costheta + ",SizingMethod='auto expand')";
		} else {
			var canvas = document.createElement('canvas');
			if (!p.oImage) {
				canvas.oImage = new Image();
				canvas.oImage.src = p.src;
			} else {
				canvas.oImage = p.oImage;
			}
			var width = Math.abs(costheta * canvas.oImage.width) + Math.abs(sintheta * canvas.oImage.height);
			width = (width > options.maxWidth ? options.maxWidth : width);
			canvas.style.width = canvas.width = width;
			canvas.style.height = canvas.height = Math.abs(costheta * canvas.oImage.height) + Math.abs(sintheta * width);

			var context = canvas.getContext('2d');
			context.save();
			if (rotation <= Math.PI / 2) {
				context.translate(sintheta * canvas.oImage.height, 0);
			} else if (rotation <= Math.PI) {
				context.translate(canvas.width, -costheta * canvas.oImage.height);
			} else if (rotation <= 1.5 * Math.PI) {
				context.translate(-costheta * width, canvas.height);
			} else {
				context.translate(0, -sintheta * width);
			}
			context.rotate(rotation);
			context.drawImage(canvas.oImage, 0, 0, width, canvas.oImage.height);
			context.restore();
		}
		canvas.id = p.id;
		canvas.angle = p.angle;
		p.parentNode.replaceChild(canvas, p);
	}

	$.fn.rotateRight = function(angle) {
		this.rotate({
			angle : angle ? angle : 90
		});
	}

	$.fn.rotateLeft = function(angle) {
		this.rotate({
			angle : angle ? -angle : -90
		});
	}
})(jQuery);
/*!
* jquery.event.drag - v 2.2
* Copyright (c) 2010 Three Dub Media - http://threedubmedia.com
* Open Source MIT License - http://threedubmedia.com/code/license
*/
// Created: 2008-06-04
// Updated: 2012-05-21
// REQUIRES: jquery 1.7.x

!(function($) {

	// add the jquery instance method
	$.fn.drag = function(str, arg, opts) {
		// figure out the event type
		var type = typeof str == "string" ? str : "",
		// figure out the event handler...
		fn = $.isFunction(str) ? str : $.isFunction(arg) ? arg : null;
		// fix the event type
		if (type.indexOf("drag") !== 0)
			type = "drag" + type;
		// were options passed
		opts = (str == fn ? arg : opts ) || {};
		// trigger or bind event handler
		return fn ? this.bind(type, opts, fn) : this.trigger(type);
	};

	// local refs (increase compression)
	var $event = $.event, $special = $event.special,
	// configure the drag special event
	drag = $special.drag = {

		// these are the default settings
		defaults : {
			which : 1, // mouse button pressed to start drag sequence
			distance : 0, // distance dragged before dragstart
			not : ':input', // selector to suppress dragging on target elements
			handle : null, // selector to match handle target elements
			relative : false, // true to use "position", false to use "offset"
			drop : true, // false to suppress drop events, true or selector to allow
			click : false // false to suppress click events after dragend (no proxy)
		},

		// the key name for stored drag data
		datakey : "dragdata",

		// prevent bubbling for better performance
		noBubble : true,

		// count bound related events
		add : function(obj) {
			// read the interaction data
			var data = $.data(this, drag.datakey),
			// read any passed options
			opts = obj.data || {};
			// count another realted event
			data.related += 1;
			// extend data options bound with this event
			// don't iterate "opts" in case it is a node
			$.each(drag.defaults, function(key, def) {
				if (opts[key] !== undefined)
					data[key] = opts[key];
			});
		},

		// forget unbound related events
		remove : function() {
			$.data(this, drag.datakey).related -= 1;
		},

		// configure interaction, capture settings
		setup : function() {
			// check for related events
			if ($.data(this, drag.datakey))
				return;
			// initialize the drag data with copied defaults
			var data = $.extend({
				related : 0
			}, drag.defaults);
			// store the interaction data
			$.data(this, drag.datakey, data);
			// bind the mousedown event, which starts drag interactions
			$event.add(this, "touchstart mousedown", drag.init, data);
			// prevent image dragging in IE...
			if (this.attachEvent)
				this.attachEvent("ondragstart", drag.dontstart);
		},

		// destroy configured interaction
		teardown : function() {
			var data = $.data(this, drag.datakey) || {};
			// check for related events
			if (data.related)
				return;
			// remove the stored data
			$.removeData(this, drag.datakey);
			// remove the mousedown event
			$event.remove(this, "touchstart mousedown", drag.init);
			// enable text selection
			drag.textselect(true);
			// un-prevent image dragging in IE...
			if (this.detachEvent)
				this.detachEvent("ondragstart", drag.dontstart);
		},

		// initialize the interaction
		init : function(event) {
			// sorry, only one touch at a time
			if (drag.touched)
				return;
			// the drag/drop interaction data
			var dd = event.data, results;
			// check the which directive
			if (event.which != 0 && dd.which > 0 && event.which != dd.which)
				return;
			// check for suppressed selector
			if ($(event.target).is(dd.not))
				return;
			// check for handle selector
			if (dd.handle && !$(event.target).closest(dd.handle, event.currentTarget).length)
				return;

			drag.touched = event.type == 'touchstart' ? this : null;
			dd.propagates = 1;
			dd.mousedown = this;
			dd.interactions = [drag.interaction(this, dd)];
			dd.target = event.target;
			dd.pageX = event.pageX;
			dd.pageY = event.pageY;
			dd.dragging = null;
			// handle draginit event...
			results = drag.hijack(event, "draginit", dd);
			// early cancel
			if (!dd.propagates)
				return;
			// flatten the result set
			results = drag.flatten(results);
			// insert new interaction elements
			if (results && results.length) {
				dd.interactions = [];
				$.each(results, function() {
					dd.interactions.push(drag.interaction(this, dd));
				});
			}
			// remember how many interactions are propagating
			dd.propagates = dd.interactions.length;
			// locate and init the drop targets
			if (dd.drop !== false && $special.drop)
				$special.drop.handler(event, dd);
			// disable text selection
			drag.textselect(false);
			// bind additional events...
			if (drag.touched)
				$event.add(drag.touched, "touchmove touchend", drag.handler, dd);
			else
				$event.add(document, "mousemove mouseup", drag.handler, dd);
			// helps prevent text selection or scrolling
			if (!drag.touched || dd.live)
				return false;
		},

		// returns an interaction object
		interaction : function(elem, dd) {
			var offset = $( elem )[ dd.relative ? "position" : "offset" ]() || {
				top : 0,
				left : 0
			};
			return {
				drag : elem,
				callback : new drag.callback(),
				droppable : [],
				offset : offset
			};
		},

		// handle drag-releatd DOM events
		handler : function(event) {
			// read the data before hijacking anything
			var dd = event.data;
			// handle various events
			switch ( event.type ) {
				// mousemove, check distance, start dragging
				case !dd.dragging && 'touchmove':
					event.preventDefault();
				case !dd.dragging && 'mousemove':
					//  drag tolerance, x?+ y?= distance?				if ( Math.pow(  event.pageX-dd.pageX, 2 ) + Math.pow(  event.pageY-dd.pageY, 2 ) < Math.pow( dd.distance, 2 ) )
					break;
					// distance tolerance not reached
					event.target = dd.target;
					// force target from "mousedown" event (fix distance issue)
					drag.hijack(event, "dragstart", dd);
					// trigger "dragstart"
					if (dd.propagates)// "dragstart" not rejected
						dd.dragging = true;
				// activate interaction
				// mousemove, dragging
				case 'touchmove':
					event.preventDefault();
				case 'mousemove':
					if (dd.dragging) {
						// trigger "drag"
						drag.hijack(event, "drag", dd);
						if (dd.propagates) {
							// manage drop events
							if (dd.drop !== false && $special.drop)
								$special.drop.handler(event, dd);
							// "dropstart", "dropend"
							break;
							// "drag" not rejected, stop
						}
						event.type = "mouseup";
						// helps "drop" handler behave
					}
				// mouseup, stop dragging
				case 'touchend':
				case 'mouseup':
				default:
					if (drag.touched)
						$event.remove(drag.touched, "touchmove touchend", drag.handler);
					// remove touch events
					else
						$event.remove(document, "mousemove mouseup", drag.handler);
					// remove page events
					if (dd.dragging) {
						if (dd.drop !== false && $special.drop)
							$special.drop.handler(event, dd);
						// "drop"
						drag.hijack(event, "dragend", dd);
						// trigger "dragend"
					}
					drag.textselect(true);
					// enable text selection
					// if suppressing click events...
					if (dd.click === false && dd.dragging)
						$.data(dd.mousedown, "suppress.click", new Date().getTime() + 5);
					dd.dragging = drag.touched = false;
					// deactivate element
					break;
			}
		},

		// re-use event object for custom events
		hijack : function(event, type, dd, x, elem) {
			// not configured
			if (!dd)
				return;
			// remember the original event and type
			var orig = {
				event : event.originalEvent,
				type : event.type
			},
			// is the event drag related or drog related?
			mode = type.indexOf("drop") ? "drag" : "drop",
			// iteration vars
			result, i = x || 0, ia, $elems, callback, len = !isNaN(x) ? x : dd.interactions.length;
			// modify the event type
			event.type = type;
			// remove the original event
			event.originalEvent = null;
			// initialize the results
			dd.results = [];
			// handle each interacted element
			do
				if ( ia = dd.interactions[i]) {
					// validate the interaction
					if (type !== "dragend" && ia.cancelled)
						continue;
					// set the dragdrop properties on the event object
					callback = drag.properties(event, dd, ia);
					// prepare for more results
					ia.results = [];
					// handle each element
					$(elem || ia[mode] || dd.droppable).each(function(p, subject) {
						// identify drag or drop targets individually
						callback.target = subject;
						// force propagtion of the custom event
						event.isPropagationStopped = function() {
							return false;
						};
						// handle the event
						result = subject ? $event.dispatch.call(subject, event, callback) : null;
						// stop the drag interaction for this element
						if (result === false) {
							if (mode == "drag") {
								ia.cancelled = true;
								dd.propagates -= 1;
							}
							if (type == "drop") {
								ia[ mode ][p] = null;
							}
						}
						// assign any dropinit elements
						else if (type == "dropinit")
							ia.droppable.push(drag.element(result) || subject);
						// accept a returned proxy element
						if (type == "dragstart")
							ia.proxy = $( drag.element( result ) || ia.drag )[0];
						// remember this result
						ia.results.push(result);
						// forget the event result, for recycling
						delete event.result;
						// break on cancelled handler
						if (type !== "dropinit")
							return result;
					});
					// flatten the results
					dd.results[i] = drag.flatten(ia.results);
					// accept a set of valid drop targets
					if (type == "dropinit")
						ia.droppable = drag.flatten(ia.droppable);
					// locate drop targets
					if (type == "dragstart" && !ia.cancelled)
						callback.update();
				}
			while ( ++i < len )
			// restore the original event & type
			event.type = orig.type;
			event.originalEvent = orig.event;
			// return all handler results
			return drag.flatten(dd.results);
		},

		// extend the callback object with drag/drop properties...
		properties : function(event, dd, ia) {
			var obj = ia.callback;
			// elements
			obj.drag = ia.drag;
			obj.proxy = ia.proxy || ia.drag;
			// starting mouse position
			obj.startX = dd.pageX;
			obj.startY = dd.pageY;
			// current distance dragged
			obj.deltaX = event.pageX - dd.pageX;
			obj.deltaY = event.pageY - dd.pageY;
			// original element position
			obj.originalX = ia.offset.left;
			obj.originalY = ia.offset.top;
			// adjusted element position
			obj.offsetX = obj.originalX + obj.deltaX;
			obj.offsetY = obj.originalY + obj.deltaY;
			// assign the drop targets information
			obj.drop = drag.flatten((ia.drop || [] ).slice());
			obj.available = drag.flatten((ia.droppable || [] ).slice());
			return obj;
		},

		// determine is the argument is an element or jquery instance
		element : function(arg) {
			if (arg && (arg.jquery || arg.nodeType == 1 ))
				return arg;
		},

		// flatten nested jquery objects and arrays into a single dimension array
		flatten : function(arr) {
			return $.map(arr, function(member) {
				return member && member.jquery ? $.makeArray(member) : member && member.length ? drag.flatten(member) : member;
			});
		},

		// toggles text selection attributes ON (true) or OFF (false)
		textselect : function(bool) {
			$( document )[ bool ? "unbind" : "bind" ]("selectstart", drag.dontstart).css("MozUserSelect", bool ? "" : "none");
			// .attr("unselectable", bool ? "off" : "on" )
			document.unselectable = bool ? "off" : "on";
		},

		// suppress "selectstart" and "ondragstart" events
		dontstart : function() {
			return false;
		},

		// a callback instance contructor
		callback : function() {
		}
	};

	// callback methods
	drag.callback.prototype = {
		update : function() {
			if ($special.drop && this.available.length)
				$.each(this.available, function(i) {
					$special.drop.locate(this, i);
				});
		}
	};

	// patch $.event.$dispatch to allow suppressing clicks
	var $dispatch = $event.dispatch;
	$event.dispatch = function(event) {
		if ($.data(this, "suppress." + event.type) - new Date().getTime() > 0) {
			$.removeData(this, "suppress." + event.type);
			return;
		}
		return $dispatch.apply(this, arguments);
	};

	// event fix hooks for touch events...
	var touchHooks = $event.fixHooks.touchstart = $event.fixHooks.touchmove = $event.fixHooks.touchend = $event.fixHooks.touchcancel = {
		props : "clientX clientY pageX pageY screenX screenY".split(" "),
		filter : function(event, orig) {
			if (orig) {
				var touched = (orig.touches && orig.touches[0] ) || (orig.changedTouches && orig.changedTouches[0] ) || null;
				// iOS webkit: touchstart, touchmove, touchend
				if (touched)
					$.each(touchHooks.props, function(i, prop) {
						event[prop] = touched[prop];
					});
			}
			return event;
		}
	};

	// share the same special event configuration with related events...
	$special.draginit = $special.dragstart = $special.dragend = drag;

})(jQuery); /*!
* jquery.event.drop - v 2.2
* Copyright (c) 2010 Three Dub Media - http://threedubmedia.com
* Open Source MIT License - http://threedubmedia.com/code/license
*/
// Created: 2008-06-04
// Updated: 2012-05-21
// REQUIRES: jquery 1.7.x, event.drag 2.2
!(function($) {// secure $ jQuery alias

	// Events: drop, dropstart, dropend

	// add the jquery instance method
	$.fn.drop = function(str, arg, opts) {
		// figure out the event type
		var type = typeof str == "string" ? str : "",
		// figure out the event handler...
		fn = $.isFunction(str) ? str : $.isFunction(arg) ? arg : null;
		// fix the event type
		if (type.indexOf("drop") !== 0)
			type = "drop" + type;
		// were options passed
		opts = (str == fn ? arg : opts ) || {};
		// trigger or bind event handler
		return fn ? this.bind(type, opts, fn) : this.trigger(type);
	};

	// DROP MANAGEMENT UTILITY
	// returns filtered drop target elements, caches their positions
	$.drop = function(opts) {
		opts = opts || {};
		// safely set new options...
		drop.multi = opts.multi === true ? Infinity : opts.multi === false ? 1 : !isNaN(opts.multi) ? opts.multi : drop.multi;
		drop.delay = opts.delay || drop.delay;
		drop.tolerance = $.isFunction(opts.tolerance) ? opts.tolerance : opts.tolerance === null ? null : drop.tolerance;
		drop.mode = opts.mode || drop.mode || 'intersect';
	};

	// local refs (increase compression)
	var $event = $.event, $special = $event.special,
	// configure the drop special event
	drop = $.event.special.drop = {

		// these are the default settings
		multi : 1, // allow multiple drop winners per dragged element
		delay : 20, // async timeout delay
		mode : 'overlap', // drop tolerance mode

		// internal cache
		targets : [],

		// the key name for stored drop data
		datakey : "dropdata",

		// prevent bubbling for better performance
		noBubble : true,

		// count bound related events
		add : function(obj) {
			// read the interaction data
			var data = $.data(this, drop.datakey);
			// count another realted event
			data.related += 1;
		},

		// forget unbound related events
		remove : function() {
			$.data(this, drop.datakey).related -= 1;
		},

		// configure the interactions
		setup : function() {
			// check for related events
			if ($.data(this, drop.datakey))
				return;
			// initialize the drop element data
			var data = {
				related : 0,
				active : [],
				anyactive : 0,
				winner : 0,
				location : {}
			};
			// store the drop data on the element
			$.data(this, drop.datakey, data);
			// store the drop target in internal cache
			drop.targets.push(this);
		},

		// destroy the configure interaction
		teardown : function() {
			var data = $.data(this, drop.datakey) || {};
			// check for related events
			if (data.related)
				return;
			// remove the stored data
			$.removeData(this, drop.datakey);
			// reference the targeted element
			var element = this;
			// remove from the internal cache
			drop.targets = $.grep(drop.targets, function(target) {
				return (target !== element );
			});
		},

		// shared event handler
		handler : function(event, dd) {
			// local vars
			var results, $targets;
			// make sure the right data is available
			if (!dd)
				return;
			// handle various events
			switch ( event.type ) {
				// draginit, from $.event.special.drag
				case 'mousedown':
				// DROPINIT >>
				case 'touchstart':
					// DROPINIT >>
					// collect and assign the drop targets
					$targets = $(drop.targets);
					if ( typeof dd.drop == "string")
						$targets = $targets.filter(dd.drop);
					// reset drop data winner properties
					$targets.each(function() {
						var data = $.data(this, drop.datakey);
						data.active = [];
						data.anyactive = 0;
						data.winner = 0;
					});
					// set available target elements
					dd.droppable = $targets;
					// activate drop targets for the initial element being dragged
					$special.drag.hijack(event, "dropinit", dd);
					break;
				// drag, from $.event.special.drag
				case 'mousemove':
				// TOLERATE >>
				case 'touchmove':
					// TOLERATE >>
					drop.event = event;
					// store the mousemove event
					if (!drop.timer)
						// monitor drop targets
						drop.tolerate(dd);
					break;
				// dragend, from $.event.special.drag
				case 'mouseup':
				// DROP >> DROPEND >>
				case 'touchend':
					// DROP >> DROPEND >>
					drop.timer = clearTimeout(drop.timer);
					// delete timer
					if (dd.propagates) {
						$special.drag.hijack(event, "drop", dd);
						$special.drag.hijack(event, "dropend", dd);
					}
					break;

			}
		},

		// returns the location positions of an element
		locate : function(elem, index) {
			var data = $.data(elem, drop.datakey), $elem = $(elem), posi = $elem.offset() || {}, height = $elem.outerHeight(), width = $elem.outerWidth(), location = {
				elem : elem,
				width : width,
				height : height,
				top : posi.top,
				left : posi.left,
				right : posi.left + width,
				bottom : posi.top + height
			};
			// drag elements might not have dropdata
			if (data) {
				data.location = location;
				data.index = index;
				data.elem = elem;
			}
			return location;
		},

		// test the location positions of an element against another OR an X,Y coord
		contains : function(target, test) {// target { location } contains test [x,y] or { location }
			return ((test[0] || test.left ) >= target.left && (test[0] || test.right ) <= target.right && (test[1] || test.top ) >= target.top && (test[1] || test.bottom ) <= target.bottom );
		},

		// stored tolerance modes
		modes : {// fn scope: "$.event.special.drop" object
			// target with mouse wins, else target with most overlap wins
			'intersect' : function(event, proxy, target) {
				return this.contains(target, [event.pageX, event.pageY]) ? // check cursor
				1e9 : this.modes.overlap.apply(this, arguments);
				// check overlap
			},
			// target with most overlap wins
			'overlap' : function(event, proxy, target) {
				// calculate the area of overlap...
				return Math.max(0, Math.min(target.bottom, proxy.bottom) - Math.max(target.top, proxy.top)) * Math.max(0, Math.min(target.right, proxy.right) - Math.max(target.left, proxy.left));
			},
			// proxy is completely contained within target bounds
			'fit' : function(event, proxy, target) {
				return this.contains(target, proxy) ? 1 : 0;
			},
			// center of the proxy is contained within target bounds
			'middle' : function(event, proxy, target) {
				return this.contains(target, [proxy.left + proxy.width * .5, proxy.top + proxy.height * .5]) ? 1 : 0;
			}
		},

		// sort drop target cache by by winner (dsc), then index (asc)
		sort : function(a, b) {
			return (b.winner - a.winner ) || (a.index - b.index );
		},

		// async, recursive tolerance execution
		tolerate : function(dd) {
			// declare local refs
			var i, drp, drg, data, arr, len, elem,
			// interaction iteration variables
			x = 0, ia, end = dd.interactions.length,
			// determine the mouse coords
			xy = [drop.event.pageX, drop.event.pageY],
			// custom or stored tolerance fn
			tolerance = drop.tolerance || drop.modes[drop.mode];
			// go through each passed interaction...
			do
				if ( ia = dd.interactions[x]) {
					// check valid interaction
					if (!ia)
						return;
					// initialize or clear the drop data
					ia.drop = [];
					// holds the drop elements
					arr = [];
					len = ia.droppable.length;
					// determine the proxy location, if needed
					if (tolerance)
						drg = drop.locate(ia.proxy);
					// reset the loop
					i = 0;
					// loop each stored drop target
					do
						if ( elem = ia.droppable[i]) {
							data = $.data(elem, drop.datakey);
							drp = data.location;
							if (!drp)
								continue;
							// find a winner: tolerance function is defined, call it
							data.winner = tolerance ? tolerance.call(drop, drop.event, drg, drp)
							// mouse position is always the fallback
							: drop.contains(drp, xy) ? 1 : 0;
							arr.push(data);
						}
					while ( ++i < len );// loop
					// sort the drop targets
					arr.sort(drop.sort);
					// reset the loop
					i = 0;
					// loop through all of the targets again
					do
						if ( data = arr[i]) {
							// winners...
							if (data.winner && ia.drop.length < drop.multi) {
								// new winner... dropstart
								if (!data.active[x] && !data.anyactive) {
									// check to make sure that this is not prevented
									if ($special.drag.hijack( drop.event, "dropstart", dd, x, data.elem )[0] !== false) {
										data.active[x] = 1;
										data.anyactive += 1;
									}
									// if false, it is not a winner
									else
										data.winner = 0;
								}
								// if it is still a winner
								if (data.winner)
									ia.drop.push(data.elem);
							}
							// losers...
							else if (data.active[x] && data.anyactive == 1) {
								// former winner... dropend
								$special.drag.hijack(drop.event, "dropend", dd, x, data.elem);
								data.active[x] = 0;
								data.anyactive -= 1;
							}
						}
					while ( ++i < len ); // loop
				}
			while ( ++x < end )// loop
			// check if the mouse is still moving or is idle
			if (drop.last && xy[0] == drop.last.pageX && xy[1] == drop.last.pageY)
				delete drop.timer;
			// idle, don't recurse
			else// recurse
				drop.timer = setTimeout(function() {
					drop.tolerate(dd);
				}, drop.delay);
			// remember event, to compare idleness
			drop.last = drop.event;
		}
	};

	// share the same special event configuration with related events...
	$special.dropinit = $special.dropstart = $special.dropend = drop;

})(jQuery);
!(function($){
  var Waterfall, defaultOptions, __bind;

  __bind = function(fn, me) {
    return function() {
      return fn.apply(me, arguments);
    };
  };

  // Waterfall default options
  defaultOptions = {
    align: 'center',
    container: $('body'),
    offset: 2,
    autoResize: false,
    itemWidth: 0,
    flexibleWidth: 0,
    resizeDelay: 50,
    onLayoutChanged: undefined
  };

  Waterfall = (function(options) {

    function Waterfall(handler, options) {
      this.handler = handler;

      // Layout variables.
      this.columns = null;
      this.containerWidth = null;
      this.resizeTimer = null;
      this.direction = 'left';

      $.extend(true, this, defaultOptions, options);

      // Bind methods
      this.update = __bind(this.update, this);
      this.onResize = __bind(this.onResize, this);
      this.getItemWidth = __bind(this.getItemWidth, this);
      this.layout = __bind(this.layout, this);
      this.layoutFull = __bind(this.layoutFull, this);
      this.layoutColumns = __bind(this.layoutColumns, this);
      this.clear = __bind(this.clear, this);

      // Listen to resize event if requested.
      if (this.autoResize) {
        $(window).bind('resize.waterfall', this.onResize);
        this.container.bind('refreshWaterfall', this.onResize);
      };
    };

    // Method for updating the plugins options
    Waterfall.prototype.update = function(options) {
      $.extend(true, this, options);
    };

    // This timer ensures that layout is not continuously called as window is being dragged.
    Waterfall.prototype.onResize = function() {
      clearTimeout(this.resizeTimer);
      this.resizeTimer = setTimeout(this.layout, this.resizeDelay);
    };

    // Method to get the standard item width
    Waterfall.prototype.getItemWidth = function() {
      if (this.itemWidth === undefined || this.itemWidth === 0) {
        return this.handler.eq(0).outerWidth();
      }
      else if (typeof this.itemWidth == 'string' && this.itemWidth.indexOf('%') >= 0) {
        return parseFloat(this.itemWidth) / 100 * this.container.width();
      }
      return this.itemWidth;
    };

    // Method to get the flexible item width
    Waterfall.prototype.getFlexibleWidth = function() {
      var containerWidth = this.container.width(),
          flexibleWidth = this.flexibleWidth;

      if (typeof flexibleWidth == 'string' && flexibleWidth.indexOf('%') >= 0) {
        flexibleWidth = parseFloat(flexibleWidth) / 100 * containerWidth;
        flexibleWidth -= this.handler.eq(0).outerWidth() - this.handler.eq(0).innerWidth();
      }

      var columns = Math.floor(1 + containerWidth / (flexibleWidth + this.offset)),
          columnWidth = (containerWidth - (columns - 1) * this.offset) / columns;

      return Math.floor(columnWidth);
    };

    // Main layout methdd.
    Waterfall.prototype.layout = function() {
      // Do nothing if container isn't visible
      if(!this.container.is(":visible")) {
        return;
      }

      // Calculate flexible item width if option is set
      if (this.flexibleWidth) {
        this.itemWidth = this.getFlexibleWidth();
        // Stretch items to fill calculated width
        this.handler.css('width', this.itemWidth);
      }

      // Calculate basic layout parameters.
      var columnWidth = this.getItemWidth() + this.offset,
          containerWidth = this.container.width(),
          columns = Math.floor((containerWidth + this.offset) / columnWidth),
          offset = 0,
          maxHeight = 0;

      // Use less columns if there are to few items
      columns = Math.min(columns, this.handler.length);

      // Calculate the offset based on the alignment of columns to the parent container
      if (this.align == 'left' || this.align == 'right') {
        offset = Math.floor((columns / columnWidth + this.offset) / 2);
      } else {
        offset = Math.round((containerWidth - (columns * columnWidth - this.offset)) / 2);
      }

      // Get direction for positioning
      this.direction = this.align == 'right' ? 'right' : 'left';

      // If container and column count hasn't changed, we can only update the columns.
      if(this.columns != null && this.columns.length == columns) {
        maxHeight = this.layoutColumns(columnWidth, offset);
      } else {
        maxHeight = this.layoutFull(columnWidth, columns, offset);
      }

      // Set container height to height of the grid.
      this.container.css('height', maxHeight);

      if (this.onLayoutChanged !== undefined && typeof this.onLayoutChanged === 'function') {
        this.onLayoutChanged();
      }
    };

    /**
     * Perform a full layout update.
     */
    Waterfall.prototype.layoutFull = function(columnWidth, columns, offset) {
      var item, top, left, i = 0, k = 0 , j = 0,
          length = this.handler.length,
          shortest = null, shortestIndex = null,
          itemCSS = {position: 'absolute'},
          sideOffset, heights = [],
          leftAligned = this.align == 'left' ? true : false;

      this.columns = [];

      // Prepare arrays to store height of columns and items.
      while (heights.length < columns) {
        heights.push(0);
        this.columns.push([]);
      }

      // Loop over items.
      for (; i < length; i++ ) {
        item = this.handler.eq(i);

        // Find the shortest column.
        shortest = heights[0];
        shortestIndex = 0;
        for (k = 0; k < columns; k++) {
          if (heights[k] < shortest) {
            shortest = heights[k];
            shortestIndex = k;
          }
        }

        // stick to left side if alignment is left and this is the first column
        if (shortestIndex == 0 && leftAligned) {
          sideOffset = 0;
        } else {
          sideOffset = shortestIndex * columnWidth + offset;
        }

        // Position the item.
        itemCSS[this.direction] = sideOffset;
        itemCSS.top = shortest;
        item.css(itemCSS);

        // Update column height and store item in shortest column
        heights[shortestIndex] += item.outerHeight() + this.offset;
        this.columns[shortestIndex].push(item);
      }

      // Return longest column
      return Math.max.apply(Math, heights);
    };

    /**
     * This layout method only updates the vertical position of the
     * existing column assignments.
     */
    Waterfall.prototype.layoutColumns = function(columnWidth, offset) {
      var heights = [],
          i = 0, k = 0,
          column, item, itemCSS, sideOffset;

      for (; i < this.columns.length; i++) {
        heights.push(0);
        column = this.columns[i];
        sideOffset = i * columnWidth + offset;

        for (k = 0; k < column.length; k++) {
          item = column[k];
          itemCSS = {
            top: heights[i]
          };
          itemCSS[this.direction] = sideOffset;

          item.css(itemCSS);

          heights[i] += item.outerHeight() + this.offset;
        }
      }

      // Return longest column
      return Math.max.apply(Math, heights);
    };

    /**
     * Clear event listeners and time outs.
     */
    Waterfall.prototype.clear = function() {
      clearTimeout(this.resizeTimer);
      $(window).unbind('resize.waterfall', this.onResize);
      this.container.off('refreshWaterfall', this.onResize);
    };

    return Waterfall;
  })();

  $.fn.waterfall = function(options) {
    // Create a Waterfall instance if not available
    if (!this.waterfallInstance) {
      this.waterfallInstance = new Waterfall(this, options || {});
    } else {
      this.waterfallInstance.update(options || {});
    }
    // Apply layout
    this.waterfallInstance.layout();
    // Display items (if hidden) and return jQuery object to maintain chainability
    return this.show();
  };
})(jQuery);/*
CryptoJS v3.0.2
code.google.com/p/crypto-js
(c) 2009-2012 by Jeff Mott. All rights reserved.
code.google.com/p/crypto-js/wiki/License
*/
/**
 * CryptoJS core components.
 */
var CryptoJS = CryptoJS || (function (Math, undefined) {
    /**
     * CryptoJS namespace.
     */
    var C = {};

    /**
     * Library namespace.
     */
    var C_lib = C.lib = {};

    /**
     * Base object for prototypal inheritance.
     */
    var Base = C_lib.Base = (function () {
        function F() {}

        return {
            /**
             * Creates a new object that inherits from this object.
             *
             * @param {Object} overrides Properties to copy into the new object.
             *
             * @return {Object} The new object.
             *
             * @static
             *
             * @example
             *
             *     var MyType = CryptoJS.lib.Base.extend({
             *         field: 'value',
             *
             *         method: function () {
             *         }
             *     });
             */
            extend: function (overrides) {
                // Spawn
                F.prototype = this;
                var subtype = new F();

                // Augment
                if (overrides) {
                    subtype.mixIn(overrides);
                }

                // Reference supertype
                subtype.$super = this;

                return subtype;
            },

            /**
             * Extends this object and runs the init method.
             * Arguments to create() will be passed to init().
             *
             * @return {Object} The new object.
             *
             * @static
             *
             * @example
             *
             *     var instance = MyType.create();
             */
            create: function () {
                var instance = this.extend();
                instance.init.apply(instance, arguments);

                return instance;
            },

            /**
             * Initializes a newly created object.
             * Override this method to add some logic when your objects are created.
             *
             * @example
             *
             *     var MyType = CryptoJS.lib.Base.extend({
             *         init: function () {
             *             // ...
             *         }
             *     });
             */
            init: function () {
            },

            /**
             * Copies properties into this object.
             *
             * @param {Object} properties The properties to mix in.
             *
             * @example
             *
             *     MyType.mixIn({
             *         field: 'value'
             *     });
             */
            mixIn: function (properties) {
                for (var propertyName in properties) {
                    if (properties.hasOwnProperty(propertyName)) {
                        this[propertyName] = properties[propertyName];
                    }
                }

                // IE won't copy toString using the loop above
                // Other non-enumerable properties are:
                //   hasOwnProperty, isPrototypeOf, propertyIsEnumerable,
                //   toLocaleString, valueOf
                if (properties.hasOwnProperty('toString')) {
                    this.toString = properties.toString;
                }
            },

            /**
             * Creates a copy of this object.
             *
             * @return {Object} The clone.
             *
             * @example
             *
             *     var clone = instance.clone();
             */
            clone: function () {
                return this.$super.extend(this);
            }
        };
    }());

    /**
     * An array of 32-bit words.
     *
     * @property {Array} words The array of 32-bit words.
     * @property {number} sigBytes The number of significant bytes in this word array.
     */
    var WordArray = C_lib.WordArray = Base.extend({
        /**
         * Initializes a newly created word array.
         *
         * @param {Array} words (Optional) An array of 32-bit words.
         * @param {number} sigBytes (Optional) The number of significant bytes in the words.
         *
         * @example
         *
         *     var wordArray = CryptoJS.lib.WordArray.create();
         *     var wordArray = CryptoJS.lib.WordArray.create([0x00010203, 0x04050607]);
         *     var wordArray = CryptoJS.lib.WordArray.create([0x00010203, 0x04050607], 6);
         */
        init: function (words, sigBytes) {
            words = this.words = words || [];

            if (sigBytes != undefined) {
                this.sigBytes = sigBytes;
            } else {
                this.sigBytes = words.length * 4;
            }
        },

        /**
         * Converts this word array to a string.
         *
         * @param {Encoder} encoder (Optional) The encoding strategy to use. Default: CryptoJS.enc.Hex
         *
         * @return {string} The stringified word array.
         *
         * @example
         *
         *     var string = wordArray + '';
         *     var string = wordArray.toString();
         *     var string = wordArray.toString(CryptoJS.enc.Utf8);
         */
        toString: function (encoder) {
            return (encoder || Hex).stringify(this);
        },

        /**
         * Concatenates a word array to this word array.
         *
         * @param {WordArray} wordArray The word array to append.
         *
         * @return {WordArray} This word array.
         *
         * @example
         *
         *     wordArray1.concat(wordArray2);
         */
        concat: function (wordArray) {
            // Shortcuts
            var thisWords = this.words;
            var thatWords = wordArray.words;
            var thisSigBytes = this.sigBytes;
            var thatSigBytes = wordArray.sigBytes;

            // Clamp excess bits
            this.clamp();

            // Concat
            if (thisSigBytes % 4) {
                // Copy one byte at a time
                for (var i = 0; i < thatSigBytes; i++) {
                    var thatByte = (thatWords[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff;
                    thisWords[(thisSigBytes + i) >>> 2] |= thatByte << (24 - ((thisSigBytes + i) % 4) * 8);
                }
            } else if (thatWords.length > 0xffff) {
                // Copy one word at a time
                for (var i = 0; i < thatSigBytes; i += 4) {
                    thisWords[(thisSigBytes + i) >>> 2] = thatWords[i >>> 2];
                }
            } else {
                // Copy all words at once
                thisWords.push.apply(thisWords, thatWords);
            }
            this.sigBytes += thatSigBytes;

            // Chainable
            return this;
        },

        /**
         * Removes insignificant bits.
         *
         * @example
         *
         *     wordArray.clamp();
         */
        clamp: function () {
            // Shortcuts
            var words = this.words;
            var sigBytes = this.sigBytes;

            // Clamp
            words[sigBytes >>> 2] &= 0xffffffff << (32 - (sigBytes % 4) * 8);
            words.length = Math.ceil(sigBytes / 4);
        },

        /**
         * Creates a copy of this word array.
         *
         * @return {WordArray} The clone.
         *
         * @example
         *
         *     var clone = wordArray.clone();
         */
        clone: function () {
            var clone = Base.clone.call(this);
            clone.words = this.words.slice(0);

            return clone;
        },

        /**
         * Creates a word array filled with random bytes.
         *
         * @param {number} nBytes The number of random bytes to generate.
         *
         * @return {WordArray} The random word array.
         *
         * @static
         *
         * @example
         *
         *     var wordArray = CryptoJS.lib.WordArray.random(16);
         */
        random: function (nBytes) {
            var words = [];
            for (var i = 0; i < nBytes; i += 4) {
                words.push((Math.random() * 0x100000000) | 0);
            }

            return WordArray.create(words, nBytes);
        }
    });

    /**
     * Encoder namespace.
     */
    var C_enc = C.enc = {};

    /**
     * Hex encoding strategy.
     */
    var Hex = C_enc.Hex = {
        /**
         * Converts a word array to a hex string.
         *
         * @param {WordArray} wordArray The word array.
         *
         * @return {string} The hex string.
         *
         * @static
         *
         * @example
         *
         *     var hexString = CryptoJS.enc.Hex.stringify(wordArray);
         */
        stringify: function (wordArray) {
            // Shortcuts
            var words = wordArray.words;
            var sigBytes = wordArray.sigBytes;

            // Convert
            var hexChars = [];
            for (var i = 0; i < sigBytes; i++) {
                var bite = (words[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff;
                hexChars.push((bite >>> 4).toString(16));
                hexChars.push((bite & 0x0f).toString(16));
            }

            return hexChars.join('');
        },

        /**
         * Converts a hex string to a word array.
         *
         * @param {string} hexStr The hex string.
         *
         * @return {WordArray} The word array.
         *
         * @static
         *
         * @example
         *
         *     var wordArray = CryptoJS.enc.Hex.parse(hexString);
         */
        parse: function (hexStr) {
            // Shortcut
            var hexStrLength = hexStr.length;

            // Convert
            var words = [];
            for (var i = 0; i < hexStrLength; i += 2) {
                words[i >>> 3] |= parseInt(hexStr.substr(i, 2), 16) << (24 - (i % 8) * 4);
            }

            return WordArray.create(words, hexStrLength / 2);
        }
    };

    /**
     * Latin1 encoding strategy.
     */
    var Latin1 = C_enc.Latin1 = {
        /**
         * Converts a word array to a Latin1 string.
         *
         * @param {WordArray} wordArray The word array.
         *
         * @return {string} The Latin1 string.
         *
         * @static
         *
         * @example
         *
         *     var latin1String = CryptoJS.enc.Latin1.stringify(wordArray);
         */
        stringify: function (wordArray) {
            // Shortcuts
            var words = wordArray.words;
            var sigBytes = wordArray.sigBytes;

            // Convert
            var latin1Chars = [];
            for (var i = 0; i < sigBytes; i++) {
                var bite = (words[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff;
                latin1Chars.push(String.fromCharCode(bite));
            }

            return latin1Chars.join('');
        },

        /**
         * Converts a Latin1 string to a word array.
         *
         * @param {string} latin1Str The Latin1 string.
         *
         * @return {WordArray} The word array.
         *
         * @static
         *
         * @example
         *
         *     var wordArray = CryptoJS.enc.Latin1.parse(latin1String);
         */
        parse: function (latin1Str) {
            // Shortcut
            var latin1StrLength = latin1Str.length;

            // Convert
            var words = [];
            for (var i = 0; i < latin1StrLength; i++) {
                words[i >>> 2] |= (latin1Str.charCodeAt(i) & 0xff) << (24 - (i % 4) * 8);
            }

            return WordArray.create(words, latin1StrLength);
        }
    };

    /**
     * UTF-8 encoding strategy.
     */
    var Utf8 = C_enc.Utf8 = {
        /**
         * Converts a word array to a UTF-8 string.
         *
         * @param {WordArray} wordArray The word array.
         *
         * @return {string} The UTF-8 string.
         *
         * @static
         *
         * @example
         *
         *     var utf8String = CryptoJS.enc.Utf8.stringify(wordArray);
         */
        stringify: function (wordArray) {
            try {
                return decodeURIComponent(escape(Latin1.stringify(wordArray)));
            } catch (e) {
                throw new Error('Malformed UTF-8 data');
            }
        },

        /**
         * Converts a UTF-8 string to a word array.
         *
         * @param {string} utf8Str The UTF-8 string.
         *
         * @return {WordArray} The word array.
         *
         * @static
         *
         * @example
         *
         *     var wordArray = CryptoJS.enc.Utf8.parse(utf8String);
         */
        parse: function (utf8Str) {
            return Latin1.parse(unescape(encodeURIComponent(utf8Str)));
        }
    };

    /**
     * Abstract buffered block algorithm template.
     * The property blockSize must be implemented in a concrete subtype.
     *
     * @property {number} _minBufferSize The number of blocks that should be kept unprocessed in the buffer. Default: 0
     */
    var BufferedBlockAlgorithm = C_lib.BufferedBlockAlgorithm = Base.extend({
        /**
         * Resets this block algorithm's data buffer to its initial state.
         *
         * @example
         *
         *     bufferedBlockAlgorithm.reset();
         */
        reset: function () {
            // Initial values
            this._data = WordArray.create();
            this._nDataBytes = 0;
        },

        /**
         * Adds new data to this block algorithm's buffer.
         *
         * @param {WordArray|string} data The data to append. Strings are converted to a WordArray using UTF-8.
         *
         * @example
         *
         *     bufferedBlockAlgorithm._append('data');
         *     bufferedBlockAlgorithm._append(wordArray);
         */
        _append: function (data) {
            // Convert string to WordArray, else assume WordArray already
            if (typeof data == 'string') {
                data = Utf8.parse(data);
            }

            // Append
            this._data.concat(data);
            this._nDataBytes += data.sigBytes;
        },

        /**
         * Processes available data blocks.
         * This method invokes _doProcessBlock(dataWords, offset), which must be implemented by a concrete subtype.
         *
         * @param {boolean} flush Whether all blocks and partial blocks should be processed.
         *
         * @return {WordArray} The data after processing.
         *
         * @example
         *
         *     var processedData = bufferedBlockAlgorithm._process();
         *     var processedData = bufferedBlockAlgorithm._process(!!'flush');
         */
        _process: function (flush) {
            // Shortcuts
            var data = this._data;
            var dataWords = data.words;
            var dataSigBytes = data.sigBytes;
            var blockSize = this.blockSize;
            var blockSizeBytes = blockSize * 4;

            // Count blocks ready
            var nBlocksReady = dataSigBytes / blockSizeBytes;
            if (flush) {
                // Round up to include partial blocks
                nBlocksReady = Math.ceil(nBlocksReady);
            } else {
                // Round down to include only full blocks,
                // less the number of blocks that must remain in the buffer
                nBlocksReady = Math.max((nBlocksReady | 0) - this._minBufferSize, 0);
            }

            // Count words ready
            var nWordsReady = nBlocksReady * blockSize;

            // Count bytes ready
            var nBytesReady = Math.min(nWordsReady * 4, dataSigBytes);

            // Process blocks
            if (nWordsReady) {
                for (var offset = 0; offset < nWordsReady; offset += blockSize) {
                    // Perform concrete-algorithm logic
                    this._doProcessBlock(dataWords, offset);
                }

                // Remove processed words
                var processedWords = dataWords.splice(0, nWordsReady);
                data.sigBytes -= nBytesReady;
            }

            // Return processed words
            return WordArray.create(processedWords, nBytesReady);
        },

        /**
         * Creates a copy of this object.
         *
         * @return {Object} The clone.
         *
         * @example
         *
         *     var clone = bufferedBlockAlgorithm.clone();
         */
        clone: function () {
            var clone = Base.clone.call(this);
            clone._data = this._data.clone();

            return clone;
        },

        _minBufferSize: 0
    });

    /**
     * Abstract hasher template.
     *
     * @property {number} blockSize The number of 32-bit words this hasher operates on. Default: 16 (512 bits)
     */
    var Hasher = C_lib.Hasher = BufferedBlockAlgorithm.extend({
        /**
         * Configuration options.
         */
        // cfg: Base.extend(),

        /**
         * Initializes a newly created hasher.
         *
         * @param {Object} cfg (Optional) The configuration options to use for this hash computation.
         *
         * @example
         *
         *     var hasher = CryptoJS.algo.SHA256.create();
         */
        init: function (cfg) {
            // Apply config defaults
            // this.cfg = this.cfg.extend(cfg);

            // Set initial values
            this.reset();
        },

        /**
         * Resets this hasher to its initial state.
         *
         * @example
         *
         *     hasher.reset();
         */
        reset: function () {
            // Reset data buffer
            BufferedBlockAlgorithm.reset.call(this);

            // Perform concrete-hasher logic
            this._doReset();
        },

        /**
         * Updates this hasher with a message.
         *
         * @param {WordArray|string} messageUpdate The message to append.
         *
         * @return {Hasher} This hasher.
         *
         * @example
         *
         *     hasher.update('message');
         *     hasher.update(wordArray);
         */
        update: function (messageUpdate) {
            // Append
            this._append(messageUpdate);

            // Update the hash
            this._process();

            // Chainable
            return this;
        },

        /**
         * Finalizes the hash computation.
         * Note that the finalize operation is effectively a destructive, read-once operation.
         *
         * @param {WordArray|string} messageUpdate (Optional) A final message update.
         *
         * @return {WordArray} The hash.
         *
         * @example
         *
         *     var hash = hasher.finalize();
         *     var hash = hasher.finalize('message');
         *     var hash = hasher.finalize(wordArray);
         */
        finalize: function (messageUpdate) {
            // Final message update
            if (messageUpdate) {
                this._append(messageUpdate);
            }

            // Perform concrete-hasher logic
            this._doFinalize();

            return this._hash;
        },

        /**
         * Creates a copy of this object.
         *
         * @return {Object} The clone.
         *
         * @example
         *
         *     var clone = hasher.clone();
         */
        clone: function () {
            var clone = BufferedBlockAlgorithm.clone.call(this);
            clone._hash = this._hash.clone();

            return clone;
        },

        blockSize: 512/32,

        /**
         * Creates a shortcut function to a hasher's object interface.
         *
         * @param {Hasher} hasher The hasher to create a helper for.
         *
         * @return {Function} The shortcut function.
         *
         * @static
         *
         * @example
         *
         *     var SHA256 = CryptoJS.lib.Hasher._createHelper(CryptoJS.algo.SHA256);
         */
        _createHelper: function (hasher) {
            return function (message, cfg) {
                return hasher.create(cfg).finalize(message);
            };
        },

        /**
         * Creates a shortcut function to the HMAC's object interface.
         *
         * @param {Hasher} hasher The hasher to use in this HMAC helper.
         *
         * @return {Function} The shortcut function.
         *
         * @static
         *
         * @example
         *
         *     var HmacSHA256 = CryptoJS.lib.Hasher._createHmacHelper(CryptoJS.algo.SHA256);
         */
        _createHmacHelper: function (hasher) {
            return function (message, key) {
                return C_algo.HMAC.create(hasher, key).finalize(message);
            };
        }
    });

    /**
     * Algorithm namespace.
     */
    var C_algo = C.algo = {};

    return C;
}(Math));
/*
CryptoJS v3.0.2
code.google.com/p/crypto-js
(c) 2009-2012 by Jeff Mott. All rights reserved.
code.google.com/p/crypto-js/wiki/License
*/
/**
 * Cipher core components.
 */
CryptoJS.lib.Cipher || (function (undefined) {
    // Shortcuts
    var C = CryptoJS;
    var C_lib = C.lib;
    var Base = C_lib.Base;
    var WordArray = C_lib.WordArray;
    var BufferedBlockAlgorithm = C_lib.BufferedBlockAlgorithm;
    var C_enc = C.enc;
    var Utf8 = C_enc.Utf8;
    var Base64 = C_enc.Base64;
    var C_algo = C.algo;
    var EvpKDF = C_algo.EvpKDF;

    /**
     * Abstract base cipher template.
     *
     * @property {number} keySize This cipher's key size. Default: 4 (128 bits)
     * @property {number} ivSize This cipher's IV size. Default: 4 (128 bits)
     * @property {number} _ENC_XFORM_MODE A constant representing encryption mode.
     * @property {number} _DEC_XFORM_MODE A constant representing decryption mode.
     */
    var Cipher = C_lib.Cipher = BufferedBlockAlgorithm.extend({
        /**
         * Configuration options.
         *
         * @property {WordArray} iv The IV to use for this operation.
         */
        cfg: Base.extend(),

        /**
         * Creates this cipher in encryption mode.
         *
         * @param {WordArray} key The key.
         * @param {Object} cfg (Optional) The configuration options to use for this operation.
         *
         * @return {Cipher} A cipher instance.
         *
         * @static
         *
         * @example
         *
         *     var cipher = CryptoJS.algo.AES.createEncryptor(keyWordArray, { iv: ivWordArray });
         */
        createEncryptor: function (key, cfg) {
            return this.create(this._ENC_XFORM_MODE, key, cfg);
        },

        /**
         * Creates this cipher in decryption mode.
         *
         * @param {WordArray} key The key.
         * @param {Object} cfg (Optional) The configuration options to use for this operation.
         *
         * @return {Cipher} A cipher instance.
         *
         * @static
         *
         * @example
         *
         *     var cipher = CryptoJS.algo.AES.createDecryptor(keyWordArray, { iv: ivWordArray });
         */
        createDecryptor: function (key, cfg) {
            return this.create(this._DEC_XFORM_MODE, key, cfg);
        },

        /**
         * Initializes a newly created cipher.
         *
         * @param {number} xformMode Either the encryption or decryption transormation mode constant.
         * @param {WordArray} key The key.
         * @param {Object} cfg (Optional) The configuration options to use for this operation.
         *
         * @example
         *
         *     var cipher = CryptoJS.algo.AES.create(CryptoJS.algo.AES._ENC_XFORM_MODE, keyWordArray, { iv: ivWordArray });
         */
        init: function (xformMode, key, cfg) {
            // Apply config defaults
            this.cfg = this.cfg.extend(cfg);

            // Store transform mode and key
            this._xformMode = xformMode;
            this._key = key;

            // Set initial values
            this.reset();
        },

        /**
         * Resets this cipher to its initial state.
         *
         * @example
         *
         *     cipher.reset();
         */
        reset: function () {
            // Reset data buffer
            BufferedBlockAlgorithm.reset.call(this);

            // Perform concrete-cipher logic
            this._doReset();
        },

        /**
         * Adds data to be encrypted or decrypted.
         *
         * @param {WordArray|string} dataUpdate The data to encrypt or decrypt.
         *
         * @return {WordArray} The data after processing.
         *
         * @example
         *
         *     var encrypted = cipher.process('data');
         *     var encrypted = cipher.process(wordArray);
         */
        process: function (dataUpdate) {
            // Append
            this._append(dataUpdate);

            // Process available blocks
            return this._process();
        },

        /**
         * Finalizes the encryption or decryption process.
         * Note that the finalize operation is effectively a destructive, read-once operation.
         *
         * @param {WordArray|string} dataUpdate The final data to encrypt or decrypt.
         *
         * @return {WordArray} The data after final processing.
         *
         * @example
         *
         *     var encrypted = cipher.finalize();
         *     var encrypted = cipher.finalize('data');
         *     var encrypted = cipher.finalize(wordArray);
         */
        finalize: function (dataUpdate) {
            // Final data update
            if (dataUpdate) {
                this._append(dataUpdate);
            }

            // Perform concrete-cipher logic
            var finalProcessedData = this._doFinalize();

            return finalProcessedData;
        },

        keySize: 128/32,

        ivSize: 128/32,

        _ENC_XFORM_MODE: 1,

        _DEC_XFORM_MODE: 2,

        /**
         * Creates shortcut functions to a cipher's object interface.
         *
         * @param {Cipher} cipher The cipher to create a helper for.
         *
         * @return {Object} An object with encrypt and decrypt shortcut functions.
         *
         * @static
         *
         * @example
         *
         *     var AES = CryptoJS.lib.Cipher._createHelper(CryptoJS.algo.AES);
         */
        _createHelper: (function () {
            function selectCipherStrategy(key) {
                if (typeof key == 'string') {
                    return PasswordBasedCipher;
                } else {
                    return SerializableCipher;
                }
            }

            return function (cipher) {
                return {
                    encrypt: function (message, key, cfg) {
                        return selectCipherStrategy(key).encrypt(cipher, message, key, cfg);
                    },

                    decrypt: function (ciphertext, key, cfg) {
                        return selectCipherStrategy(key).decrypt(cipher, ciphertext, key, cfg);
                    }
                };
            };
        }())
    });

    /**
     * Abstract base stream cipher template.
     *
     * @property {number} blockSize The number of 32-bit words this cipher operates on. Default: 1 (32 bits)
     */
    var StreamCipher = C_lib.StreamCipher = Cipher.extend({
        _doFinalize: function () {
            // Process partial blocks
            var finalProcessedBlocks = this._process(!!'flush');

            return finalProcessedBlocks;
        },

        blockSize: 1
    });

    /**
     * Mode namespace.
     */
    var C_mode = C.mode = {};

    /**
     * Abstract base block cipher mode template.
     */
    var BlockCipherMode = C_lib.BlockCipherMode = Base.extend({
        /**
         * Creates this mode for encryption.
         *
         * @param {Cipher} cipher A block cipher instance.
         * @param {Array} iv The IV words.
         *
         * @static
         *
         * @example
         *
         *     var mode = CryptoJS.mode.CBC.createEncryptor(cipher, iv.words);
         */
        createEncryptor: function (cipher, iv) {
            return this.Encryptor.create(cipher, iv);
        },

        /**
         * Creates this mode for decryption.
         *
         * @param {Cipher} cipher A block cipher instance.
         * @param {Array} iv The IV words.
         *
         * @static
         *
         * @example
         *
         *     var mode = CryptoJS.mode.CBC.createDecryptor(cipher, iv.words);
         */
        createDecryptor: function (cipher, iv) {
            return this.Decryptor.create(cipher, iv);
        },

        /**
         * Initializes a newly created mode.
         *
         * @param {Cipher} cipher A block cipher instance.
         * @param {Array} iv The IV words.
         *
         * @example
         *
         *     var mode = CryptoJS.mode.CBC.Encryptor.create(cipher, iv.words);
         */
        init: function (cipher, iv) {
            this._cipher = cipher;
            this._iv = iv;
        }
    });

    /**
     * Cipher Block Chaining mode.
     */
    var CBC = C_mode.CBC = (function () {
        /**
         * Abstract base CBC mode.
         */
        var CBC = BlockCipherMode.extend();

        /**
         * CBC encryptor.
         */
        CBC.Encryptor = CBC.extend({
            /**
             * Processes the data block at offset.
             *
             * @param {Array} words The data words to operate on.
             * @param {number} offset The offset where the block starts.
             *
             * @example
             *
             *     mode.processBlock(data.words, offset);
             */
            processBlock: function (words, offset) {
                // Shortcuts
                var cipher = this._cipher;
                var blockSize = cipher.blockSize;

                // XOR and encrypt
                xorBlock.call(this, words, offset, blockSize);
                cipher.encryptBlock(words, offset);

                // Remember this block to use with next block
                this._prevBlock = words.slice(offset, offset + blockSize);
            }
        });

        /**
         * CBC decryptor.
         */
        CBC.Decryptor = CBC.extend({
            /**
             * Processes the data block at offset.
             *
             * @param {Array} words The data words to operate on.
             * @param {number} offset The offset where the block starts.
             *
             * @example
             *
             *     mode.processBlock(data.words, offset);
             */
            processBlock: function (words, offset) {
                // Shortcuts
                var cipher = this._cipher;
                var blockSize = cipher.blockSize;

                // Remember this block to use with next block
                var thisBlock = words.slice(offset, offset + blockSize);

                // Decrypt and XOR
                cipher.decryptBlock(words, offset);
                xorBlock.call(this, words, offset, blockSize);

                // This block becomes the previous block
                this._prevBlock = thisBlock;
            }
        });

        function xorBlock(words, offset, blockSize) {
            // Shortcut
            var iv = this._iv;

            // Choose mixing block
            if (iv) {
                var block = iv;

                // Remove IV for subsequent blocks
                this._iv = undefined;
            } else {
                var block = this._prevBlock;
            }

            // XOR block
            for (var i = 0; i < blockSize; i++) {
                words[offset + i] ^= block[i];
            }
        }

        return CBC;
    }());

    /**
     * Padding namespace.
     */
    var C_pad = C.pad = {};

    /**
     * PKCS #5/7 padding strategy.
     */
    var Pkcs7 = C_pad.Pkcs7 = {
        /**
         * Pads data using the algorithm defined in PKCS #5/7.
         *
         * @param {WordArray} data The data to pad.
         * @param {number} blockSize The multiple that the data should be padded to.
         *
         * @static
         *
         * @example
         *
         *     CryptoJS.pad.Pkcs7.pad(wordArray, 4);
         */
        pad: function (data, blockSize) {
            // Shortcut
            var blockSizeBytes = blockSize * 4;

            // Count padding bytes
            var nPaddingBytes = blockSizeBytes - data.sigBytes % blockSizeBytes;

            // Create padding word
            var paddingWord = (nPaddingBytes << 24) | (nPaddingBytes << 16) | (nPaddingBytes << 8) | nPaddingBytes;

            // Create padding
            var paddingWords = [];
            for (var i = 0; i < nPaddingBytes; i += 4) {
                paddingWords.push(paddingWord);
            }
            var padding = WordArray.create(paddingWords, nPaddingBytes);

            // Add padding
            data.concat(padding);
        },

        /**
         * Unpads data that had been padded using the algorithm defined in PKCS #5/7.
         *
         * @param {WordArray} data The data to unpad.
         *
         * @static
         *
         * @example
         *
         *     CryptoJS.pad.Pkcs7.unpad(wordArray);
         */
        unpad: function (data) {
            // Get number of padding bytes from last byte
            var nPaddingBytes = data.words[(data.sigBytes - 1) >>> 2] & 0xff;

            // Remove padding
            data.sigBytes -= nPaddingBytes;
        }
    };

    /**
     * Abstract base block cipher template.
     *
     * @property {number} blockSize The number of 32-bit words this cipher operates on. Default: 4 (128 bits)
     */
    var BlockCipher = C_lib.BlockCipher = Cipher.extend({
        /**
         * Configuration options.
         *
         * @property {Mode} mode The block mode to use. Default: CryptoJS.mode.CBC
         * @property {Padding} padding The padding strategy to use. Default: CryptoJS.pad.Pkcs7
         */
        cfg: Cipher.cfg.extend({
            mode: CBC,
            padding: Pkcs7
        }),

        reset: function () {
            // Reset cipher
            Cipher.reset.call(this);

            // Shortcuts
            var cfg = this.cfg;
            var iv = cfg.iv;
            var mode = cfg.mode;

            // Reset block mode
            if (this._xformMode == this._ENC_XFORM_MODE) {
                var modeCreator = mode.createEncryptor;
            } else /* if (this._xformMode == this._DEC_XFORM_MODE) */ {
                var modeCreator = mode.createDecryptor;

                // Keep at least one block in the buffer for unpadding
                this._minBufferSize = 1;
            }
            this._mode = modeCreator.call(mode, this, iv && iv.words);
        },

        _doProcessBlock: function (words, offset) {
            this._mode.processBlock(words, offset);
        },

        _doFinalize: function () {
            // Shortcut
            var padding = this.cfg.padding;

            // Finalize
            if (this._xformMode == this._ENC_XFORM_MODE) {
                // Pad data
                padding.pad(this._data, this.blockSize);

                // Process final blocks
                var finalProcessedBlocks = this._process(!!'flush');
            } else /* if (this._xformMode == this._DEC_XFORM_MODE) */ {
                // Process final blocks
                var finalProcessedBlocks = this._process(!!'flush');

                // Unpad data
                padding.unpad(finalProcessedBlocks);
            }

            return finalProcessedBlocks;
        },

        blockSize: 128/32
    });

    /**
     * A collection of cipher parameters.
     *
     * @property {WordArray} ciphertext The raw ciphertext.
     * @property {WordArray} key The key to this ciphertext.
     * @property {WordArray} iv The IV used in the ciphering operation.
     * @property {WordArray} salt The salt used with a key derivation function.
     * @property {Cipher} algorithm The cipher algorithm.
     * @property {Mode} mode The block mode used in the ciphering operation.
     * @property {Padding} padding The padding scheme used in the ciphering operation.
     * @property {number} blockSize The block size of the cipher.
     * @property {Format} formatter The default formatting strategy to convert this cipher params object to a string.
     */
    var CipherParams = C_lib.CipherParams = Base.extend({
        /**
         * Initializes a newly created cipher params object.
         *
         * @param {Object} cipherParams An object with any of the possible cipher parameters.
         *
         * @example
         *
         *     var cipherParams = CryptoJS.lib.CipherParams.create({
         *         ciphertext: ciphertextWordArray,
         *         key: keyWordArray,
         *         iv: ivWordArray,
         *         salt: saltWordArray,
         *         algorithm: CryptoJS.algo.AES,
         *         mode: CryptoJS.mode.CBC,
         *         padding: CryptoJS.pad.PKCS7,
         *         blockSize: 4,
         *         formatter: CryptoJS.format.OpenSSL
         *     });
         */
        init: function (cipherParams) {
            this.mixIn(cipherParams);
        },

        /**
         * Converts this cipher params object to a string.
         *
         * @param {Format} formatter (Optional) The formatting strategy to use.
         *
         * @return {string} The stringified cipher params.
         *
         * @throws Error If neither the formatter nor the default formatter is set.
         *
         * @example
         *
         *     var string = cipherParams + '';
         *     var string = cipherParams.toString();
         *     var string = cipherParams.toString(CryptoJS.format.OpenSSL);
         */
        toString: function (formatter) {
            return (formatter || this.formatter).stringify(this);
        }
    });

    /**
     * Format namespace.
     */
    var C_format = C.format = {};

    /**
     * OpenSSL formatting strategy.
     */
    var OpenSSLFormatter = C_format.OpenSSL = {
        /**
         * Converts a cipher params object to an OpenSSL-compatible string.
         *
         * @param {CipherParams} cipherParams The cipher params object.
         *
         * @return {string} The OpenSSL-compatible string.
         *
         * @static
         *
         * @example
         *
         *     var openSSLString = CryptoJS.format.OpenSSL.stringify(cipherParams);
         */
        stringify: function (cipherParams) {
            // Shortcuts
            var ciphertext = cipherParams.ciphertext;
            var salt = cipherParams.salt;

            // Format
            if (salt) {
                var wordArray = WordArray.create([0x53616c74, 0x65645f5f]).concat(salt).concat(ciphertext);
            } else {
                var wordArray = ciphertext;
            }
            var openSSLStr = wordArray.toString(Base64);

            // Limit lines to 64 characters
            openSSLStr = openSSLStr.replace(/(.{64})/g, '$1\n');

            return openSSLStr;
        },

        /**
         * Converts an OpenSSL-compatible string to a cipher params object.
         *
         * @param {string} openSSLStr The OpenSSL-compatible string.
         *
         * @return {CipherParams} The cipher params object.
         *
         * @static
         *
         * @example
         *
         *     var cipherParams = CryptoJS.format.OpenSSL.parse(openSSLString);
         */
        parse: function (openSSLStr) {
            // Parse base64
            var ciphertext = Base64.parse(openSSLStr);

            // Shortcut
            var ciphertextWords = ciphertext.words;

            // Test for salt
            if (ciphertextWords[0] == 0x53616c74 && ciphertextWords[1] == 0x65645f5f) {
                // Extract salt
                var salt = WordArray.create(ciphertextWords.slice(2, 4));

                // Remove salt from ciphertext
                ciphertextWords.splice(0, 4);
                ciphertext.sigBytes -= 16;
            }

            return CipherParams.create({ ciphertext: ciphertext, salt: salt });
        }
    };

    /**
     * A cipher wrapper that returns ciphertext as a serializable cipher params object.
     */
    var SerializableCipher = C_lib.SerializableCipher = Base.extend({
        /**
         * Configuration options.
         *
         * @property {Formatter} format The formatting strategy to convert cipher param objects to and from a string.
         *   Default: CryptoJS.format.OpenSSL
         */
        cfg: Base.extend({
            format: OpenSSLFormatter
        }),

        /**
         * Encrypts a message.
         *
         * @param {Cipher} cipher The cipher algorithm to use.
         * @param {WordArray|string} message The message to encrypt.
         * @param {WordArray} key The key.
         * @param {Object} cfg (Optional) The configuration options to use for this operation.
         *
         * @return {CipherParams} A cipher params object.
         *
         * @static
         *
         * @example
         *
         *     var ciphertextParams = CryptoJS.lib.SerializableCipher.encrypt(CryptoJS.algo.AES, message, key);
         *     var ciphertextParams = CryptoJS.lib.SerializableCipher.encrypt(CryptoJS.algo.AES, message, key, { iv: iv });
         *     var ciphertextParams = CryptoJS.lib.SerializableCipher.encrypt(CryptoJS.algo.AES, message, key, { iv: iv, format: CryptoJS.format.OpenSSL });
         */
        encrypt: function (cipher, message, key, cfg) {
            // Apply config defaults
            cfg = this.cfg.extend(cfg);

            // Encrypt
            var encryptor = cipher.createEncryptor(key, cfg);
            var ciphertext = encryptor.finalize(message);

            // Shortcut
            var cipherCfg = encryptor.cfg;

            // Create and return serializable cipher params
            return CipherParams.create({
                ciphertext: ciphertext,
                key: key,
                iv: cipherCfg.iv,
                algorithm: cipher,
                mode: cipherCfg.mode,
                padding: cipherCfg.padding,
                blockSize: cipher.blockSize,
                formatter: cfg.format
            });
        },

        /**
         * Decrypts serialized ciphertext.
         *
         * @param {Cipher} cipher The cipher algorithm to use.
         * @param {CipherParams|string} ciphertext The ciphertext to decrypt.
         * @param {WordArray} key The key.
         * @param {Object} cfg (Optional) The configuration options to use for this operation.
         *
         * @return {WordArray} The plaintext.
         *
         * @static
         *
         * @example
         *
         *     var plaintext = CryptoJS.lib.SerializableCipher.decrypt(CryptoJS.algo.AES, formattedCiphertext, key, { iv: iv, format: CryptoJS.format.OpenSSL });
         *     var plaintext = CryptoJS.lib.SerializableCipher.decrypt(CryptoJS.algo.AES, ciphertextParams, key, { iv: iv, format: CryptoJS.format.OpenSSL });
         */
        decrypt: function (cipher, ciphertext, key, cfg) {
            // Apply config defaults
            cfg = this.cfg.extend(cfg);

            // Convert string to CipherParams
            ciphertext = this._parse(ciphertext, cfg.format);

            // Decrypt
            var plaintext = cipher.createDecryptor(key, cfg).finalize(ciphertext.ciphertext);

            return plaintext;
        },

        /**
         * Converts serialized ciphertext to CipherParams,
         * else assumes CipherParams already and returns ciphertext unchanged.
         *
         * @param {CipherParams|string} ciphertext The ciphertext.
         * @param {Formatter} format The formatting strategy to use to parse serialized ciphertext.
         *
         * @return {CipherParams} The unserialized ciphertext.
         *
         * @static
         *
         * @example
         *
         *     var ciphertextParams = CryptoJS.lib.SerializableCipher._parse(ciphertextStringOrParams, format);
         */
        _parse: function (ciphertext, format) {
            if (typeof ciphertext == 'string') {
                return format.parse(ciphertext);
            } else {
                return ciphertext;
            }
        }
    });

    /**
     * Key derivation function namespace.
     */
    var C_kdf = C.kdf = {};

    /**
     * OpenSSL key derivation function.
     */
    var OpenSSLKdf = C_kdf.OpenSSL = {
        /**
         * Derives a key and IV from a password.
         *
         * @param {string} password The password to derive from.
         * @param {number} keySize The size in words of the key to generate.
         * @param {number} ivSize The size in words of the IV to generate.
         * @param {WordArray|string} salt (Optional) A 64-bit salt to use. If omitted, a salt will be generated randomly.
         *
         * @return {CipherParams} A cipher params object with the key, IV, and salt.
         *
         * @static
         *
         * @example
         *
         *     var derivedParams = CryptoJS.kdf.OpenSSL.compute('Password', 256/32, 128/32);
         *     var derivedParams = CryptoJS.kdf.OpenSSL.compute('Password', 256/32, 128/32, 'saltsalt');
         */
        compute: function (password, keySize, ivSize, salt) {
            // Generate random salt
            if (!salt) {
                salt = WordArray.random(64/8);
            }

            // Derive key and IV
            var key = EvpKDF.create({ keySize: keySize + ivSize }).compute(password, salt);

            // Separate key and IV
            var iv = WordArray.create(key.words.slice(keySize), ivSize * 4);
            key.sigBytes = keySize * 4;

            // Return params
            return CipherParams.create({ key: key, iv: iv, salt: salt });
        }
    };

    /**
     * A serializable cipher wrapper that derives the key from a password,
     * and returns ciphertext as a serializable cipher params object.
     */
    var PasswordBasedCipher = C_lib.PasswordBasedCipher = SerializableCipher.extend({
        /**
         * Configuration options.
         *
         * @property {KDF} kdf The key derivation function to use to generate a key and IV from a password.
         *   Default: CryptoJS.kdf.OpenSSL
         */
        cfg: SerializableCipher.cfg.extend({
            kdf: OpenSSLKdf
        }),

        /**
         * Encrypts a message using a password.
         *
         * @param {Cipher} cipher The cipher algorithm to use.
         * @param {WordArray|string} message The message to encrypt.
         * @param {string} password The password.
         * @param {Object} cfg (Optional) The configuration options to use for this operation.
         *
         * @return {CipherParams} A cipher params object.
         *
         * @static
         *
         * @example
         *
         *     var ciphertextParams = CryptoJS.lib.PasswordBasedCipher.encrypt(CryptoJS.algo.AES, message, 'password');
         *     var ciphertextParams = CryptoJS.lib.PasswordBasedCipher.encrypt(CryptoJS.algo.AES, message, 'password', { format: CryptoJS.format.OpenSSL });
         */
        encrypt: function (cipher, message, password, cfg) {
            // Apply config defaults
            cfg = this.cfg.extend(cfg);

            // Derive key and other params
            var derivedParams = cfg.kdf.compute(password, cipher.keySize, cipher.ivSize);

            // Add IV to config
            cfg.iv = derivedParams.iv;

            // Encrypt
            var ciphertext = SerializableCipher.encrypt.call(this, cipher, message, derivedParams.key, cfg);

            // Mix in derived params
            ciphertext.mixIn(derivedParams);

            return ciphertext;
        },

        /**
         * Decrypts serialized ciphertext using a password.
         *
         * @param {Cipher} cipher The cipher algorithm to use.
         * @param {CipherParams|string} ciphertext The ciphertext to decrypt.
         * @param {string} password The password.
         * @param {Object} cfg (Optional) The configuration options to use for this operation.
         *
         * @return {WordArray} The plaintext.
         *
         * @static
         *
         * @example
         *
         *     var plaintext = CryptoJS.lib.PasswordBasedCipher.decrypt(CryptoJS.algo.AES, formattedCiphertext, 'password', { format: CryptoJS.format.OpenSSL });
         *     var plaintext = CryptoJS.lib.PasswordBasedCipher.decrypt(CryptoJS.algo.AES, ciphertextParams, 'password', { format: CryptoJS.format.OpenSSL });
         */
        decrypt: function (cipher, ciphertext, password, cfg) {
            // Apply config defaults
            cfg = this.cfg.extend(cfg);

            // Convert string to CipherParams
            ciphertext = this._parse(ciphertext, cfg.format);

            // Derive key and other params
            var derivedParams = cfg.kdf.compute(password, cipher.keySize, cipher.ivSize, ciphertext.salt);

            // Add IV to config
            cfg.iv = derivedParams.iv;

            // Decrypt
            var plaintext = SerializableCipher.decrypt.call(this, cipher, ciphertext, derivedParams.key, cfg);

            return plaintext;
        }
    });
}());
/*
CryptoJS v3.0.2
code.google.com/p/crypto-js
(c) 2009-2012 by Jeff Mott. All rights reserved.
code.google.com/p/crypto-js/wiki/License
*/
(function (undefined) {
    // Shortcuts
    var C = CryptoJS;
    var C_lib = C.lib;
    var Base = C_lib.Base;
    var X32WordArray = C_lib.WordArray;

    /**
     * x64 namespace.
     */
    var C_x64 = C.x64 = {};

    /**
     * A 64-bit word.
     *
     * @property {number} high The high 32 bits.
     * @property {number} low The low 32 bits.
     */
    var X64Word = C_x64.Word = Base.extend({
        /**
         * Initializes a newly created 64-bit word.
         *
         * @param {number} high The high 32 bits.
         * @param {number} low The low 32 bits.
         *
         * @example
         *
         *     var x64Word = CryptoJS.x64.Word.create(0x00010203, 0x04050607);
         */
        init: function (high, low) {
            this.high = high;
            this.low = low;
        }

        /**
         * Bitwise NOTs this word.
         *
         * @return {X64Word} A new x64-Word object after negating.
         *
         * @example
         *
         *     var negated = x64Word.not();
         */
        // not: function () {
            // var high = ~this.high;
            // var low = ~this.low;

            // return X64Word.create(high, low);
        // },

        /**
         * Bitwise ANDs this word with the passed word.
         *
         * @param {X64Word} word The x64-Word to AND with this word.
         *
         * @return {X64Word} A new x64-Word object after ANDing.
         *
         * @example
         *
         *     var anded = x64Word.and(anotherX64Word);
         */
        // and: function (word) {
            // var high = this.high & word.high;
            // var low = this.low & word.low;

            // return X64Word.create(high, low);
        // },

        /**
         * Bitwise ORs this word with the passed word.
         *
         * @param {X64Word} word The x64-Word to OR with this word.
         *
         * @return {X64Word} A new x64-Word object after ORing.
         *
         * @example
         *
         *     var ored = x64Word.or(anotherX64Word);
         */
        // or: function (word) {
            // var high = this.high | word.high;
            // var low = this.low | word.low;

            // return X64Word.create(high, low);
        // },

        /**
         * Bitwise XORs this word with the passed word.
         *
         * @param {X64Word} word The x64-Word to XOR with this word.
         *
         * @return {X64Word} A new x64-Word object after XORing.
         *
         * @example
         *
         *     var xored = x64Word.xor(anotherX64Word);
         */
        // xor: function (word) {
            // var high = this.high ^ word.high;
            // var low = this.low ^ word.low;

            // return X64Word.create(high, low);
        // },

        /**
         * Shifts this word n bits to the left.
         *
         * @param {number} n The number of bits to shift.
         *
         * @return {X64Word} A new x64-Word object after shifting.
         *
         * @example
         *
         *     var shifted = x64Word.shiftL(25);
         */
        // shiftL: function (n) {
            // if (n < 32) {
                // var high = (this.high << n) | (this.low >>> (32 - n));
                // var low = this.low << n;
            // } else {
                // var high = this.low << (n - 32);
                // var low = 0;
            // }

            // return X64Word.create(high, low);
        // },

        /**
         * Shifts this word n bits to the right.
         *
         * @param {number} n The number of bits to shift.
         *
         * @return {X64Word} A new x64-Word object after shifting.
         *
         * @example
         *
         *     var shifted = x64Word.shiftR(7);
         */
        // shiftR: function (n) {
            // if (n < 32) {
                // var low = (this.low >>> n) | (this.high << (32 - n));
                // var high = this.high >>> n;
            // } else {
                // var low = this.high >>> (n - 32);
                // var high = 0;
            // }

            // return X64Word.create(high, low);
        // },

        /**
         * Rotates this word n bits to the left.
         *
         * @param {number} n The number of bits to rotate.
         *
         * @return {X64Word} A new x64-Word object after rotating.
         *
         * @example
         *
         *     var rotated = x64Word.rotL(25);
         */
        // rotL: function (n) {
            // return this.shiftL(n).or(this.shiftR(64 - n));
        // },

        /**
         * Rotates this word n bits to the right.
         *
         * @param {number} n The number of bits to rotate.
         *
         * @return {X64Word} A new x64-Word object after rotating.
         *
         * @example
         *
         *     var rotated = x64Word.rotR(7);
         */
        // rotR: function (n) {
            // return this.shiftR(n).or(this.shiftL(64 - n));
        // },

        /**
         * Adds this word with the passed word.
         *
         * @param {X64Word} word The x64-Word to add with this word.
         *
         * @return {X64Word} A new x64-Word object after adding.
         *
         * @example
         *
         *     var added = x64Word.add(anotherX64Word);
         */
        // add: function (word) {
            // var low = (this.low + word.low) | 0;
            // var carry = (low >>> 0) < (this.low >>> 0) ? 1 : 0;
            // var high = (this.high + word.high + carry) | 0;

            // return X64Word.create(high, low);
        // }
    });

    /**
     * An array of 64-bit words.
     *
     * @property {Array} words The array of CryptoJS.x64.Word objects.
     * @property {number} sigBytes The number of significant bytes in this word array.
     */
    var X64WordArray = C_x64.WordArray = Base.extend({
        /**
         * Initializes a newly created word array.
         *
         * @param {Array} words (Optional) An array of CryptoJS.x64.Word objects.
         * @param {number} sigBytes (Optional) The number of significant bytes in the words.
         *
         * @example
         *
         *     var wordArray = CryptoJS.x64.WordArray.create();
         *
         *     var wordArray = CryptoJS.x64.WordArray.create([
         *         CryptoJS.x64.Word.create(0x00010203, 0x04050607),
         *         CryptoJS.x64.Word.create(0x18191a1b, 0x1c1d1e1f)
         *     ]);
         *
         *     var wordArray = CryptoJS.x64.WordArray.create([
         *         CryptoJS.x64.Word.create(0x00010203, 0x04050607),
         *         CryptoJS.x64.Word.create(0x18191a1b, 0x1c1d1e1f)
         *     ], 10);
         */
        init: function (words, sigBytes) {
            words = this.words = words || [];

            if (sigBytes != undefined) {
                this.sigBytes = sigBytes;
            } else {
                this.sigBytes = words.length * 8;
            }
        },

        /**
         * Converts this 64-bit word array to a 32-bit word array.
         *
         * @return {CryptoJS.lib.WordArray} This word array's data as a 32-bit word array.
         *
         * @example
         *
         *     var x32WordArray = x64WordArray.toX32();
         */
        toX32: function () {
            // Shortcuts
            var x64Words = this.words;
            var x64WordsLength = x64Words.length;

            // Convert
            var x32Words = [];
            for (var i = 0; i < x64WordsLength; i++) {
                var x64Word = x64Words[i];
                x32Words.push(x64Word.high);
                x32Words.push(x64Word.low);
            }

            return X32WordArray.create(x32Words, this.sigBytes);
        },

        /**
         * Creates a copy of this word array.
         *
         * @return {X64WordArray} The clone.
         *
         * @example
         *
         *     var clone = x64WordArray.clone();
         */
        clone: function () {
            var clone = Base.clone.call(this);

            // Clone "words" array
            var words = clone.words = this.words.slice(0);

            // Clone each X64Word object
            var wordsLength = words.length;
            for (var i = 0; i < wordsLength; i++) {
                words[i] = words[i].clone();
            }

            return clone;
        }
    });
}());
/*
CryptoJS v3.0.2
code.google.com/p/crypto-js
(c) 2009-2012 by Jeff Mott. All rights reserved.
code.google.com/p/crypto-js/wiki/License
*/
(function (Math) {
    // Shortcuts
    var C = CryptoJS;
    var C_lib = C.lib;
    var WordArray = C_lib.WordArray;
    var Hasher = C_lib.Hasher;
    var C_algo = C.algo;

    // Initialization and round constants tables
    var H = [];
    var K = [];

    // Compute constants
    (function () {
        function isPrime(n) {
            var sqrtN = Math.sqrt(n);
            for (var factor = 2; factor <= sqrtN; factor++) {
                if (!(n % factor)) {
                    return false;
                }
            }

            return true;
        }

        function getFractionalBits(n) {
            return ((n - (n | 0)) * 0x100000000) | 0;
        }

        var n = 2;
        var nPrime = 0;
        while (nPrime < 64) {
            if (isPrime(n)) {
                if (nPrime < 8) {
                    H[nPrime] = getFractionalBits(Math.pow(n, 1 / 2));
                }
                K[nPrime] = getFractionalBits(Math.pow(n, 1 / 3));

                nPrime++;
            }

            n++;
        }
    }());

    // Reusable object
    var W = [];

    /**
     * SHA-256 hash algorithm.
     */
    var SHA256 = C_algo.SHA256 = Hasher.extend({
        _doReset: function () {
            this._hash = WordArray.create(H.slice(0));
        },

        _doProcessBlock: function (M, offset) {
            // Shortcut
            var H = this._hash.words;

            // Working variables
            var a = H[0];
            var b = H[1];
            var c = H[2];
            var d = H[3];
            var e = H[4];
            var f = H[5];
            var g = H[6];
            var h = H[7];

            // Computation
            for (var i = 0; i < 64; i++) {
                if (i < 16) {
                    W[i] = M[offset + i] | 0;
                } else {
                    var gamma0x = W[i - 15];
                    var gamma0  = ((gamma0x << 25) | (gamma0x >>> 7))  ^
                                  ((gamma0x << 14) | (gamma0x >>> 18)) ^
                                   (gamma0x >>> 3);

                    var gamma1x = W[i - 2];
                    var gamma1  = ((gamma1x << 15) | (gamma1x >>> 17)) ^
                                  ((gamma1x << 13) | (gamma1x >>> 19)) ^
                                   (gamma1x >>> 10);

                    W[i] = gamma0 + W[i - 7] + gamma1 + W[i - 16];
                }

                var ch  = (e & f) ^ (~e & g);
                var maj = (a & b) ^ (a & c) ^ (b & c);

                var sigma0 = ((a << 30) | (a >>> 2)) ^ ((a << 19) | (a >>> 13)) ^ ((a << 10) | (a >>> 22));
                var sigma1 = ((e << 26) | (e >>> 6)) ^ ((e << 21) | (e >>> 11)) ^ ((e << 7)  | (e >>> 25));

                var t1 = h + sigma1 + ch + K[i] + W[i];
                var t2 = sigma0 + maj;

                h = g;
                g = f;
                f = e;
                e = (d + t1) | 0;
                d = c;
                c = b;
                b = a;
                a = (t1 + t2) | 0;
            }

            // Intermediate hash value
            H[0] = (H[0] + a) | 0;
            H[1] = (H[1] + b) | 0;
            H[2] = (H[2] + c) | 0;
            H[3] = (H[3] + d) | 0;
            H[4] = (H[4] + e) | 0;
            H[5] = (H[5] + f) | 0;
            H[6] = (H[6] + g) | 0;
            H[7] = (H[7] + h) | 0;
        },

        _doFinalize: function () {
            // Shortcuts
            var data = this._data;
            var dataWords = data.words;

            var nBitsTotal = this._nDataBytes * 8;
            var nBitsLeft = data.sigBytes * 8;

            // Add padding
            dataWords[nBitsLeft >>> 5] |= 0x80 << (24 - nBitsLeft % 32);
            dataWords[(((nBitsLeft + 64) >>> 9) << 4) + 15] = nBitsTotal;
            data.sigBytes = dataWords.length * 4;

            // Hash final blocks
            this._process();
        }
    });

    /**
     * Shortcut function to the hasher's object interface.
     *
     * @param {WordArray|string} message The message to hash.
     *
     * @return {WordArray} The hash.
     *
     * @static
     *
     * @example
     *
     *     var hash = CryptoJS.SHA256('message');
     *     var hash = CryptoJS.SHA256(wordArray);
     */
    C.SHA256 = Hasher._createHelper(SHA256);

    /**
     * Shortcut function to the HMAC's object interface.
     *
     * @param {WordArray|string} message The message to hash.
     * @param {WordArray|string} key The secret key.
     *
     * @return {WordArray} The HMAC.
     *
     * @static
     *
     * @example
     *
     *     var hmac = CryptoJS.HmacSHA256(message, key);
     */
    C.HmacSHA256 = Hasher._createHmacHelper(SHA256);
}(Math));
/*
CryptoJS v3.0.2
code.google.com/p/crypto-js
(c) 2009-2012 by Jeff Mott. All rights reserved.
code.google.com/p/crypto-js/wiki/License
*/
(function () {
    // Shortcuts
    var C = CryptoJS;
    var C_lib = C.lib;
    var BlockCipher = C_lib.BlockCipher;
    var C_algo = C.algo;

    // Lookup tables
    var SBOX = [];
    var INV_SBOX = [];
    var SUB_MIX_0 = [];
    var SUB_MIX_1 = [];
    var SUB_MIX_2 = [];
    var SUB_MIX_3 = [];
    var INV_SUB_MIX_0 = [];
    var INV_SUB_MIX_1 = [];
    var INV_SUB_MIX_2 = [];
    var INV_SUB_MIX_3 = [];

    // Compute lookup tables
    (function () {
        // Compute double table
        var d = [];
        for (var i = 0; i < 256; i++) {
            if (i < 128) {
                d[i] = i << 1;
            } else {
                d[i] = (i << 1) ^ 0x11b;
            }
        }

        // Walk GF(2^8)
        var x = 0;
        var xi = 0;
        for (var i = 0; i < 256; i++) {
            // Compute sbox
            var sx = xi ^ (xi << 1) ^ (xi << 2) ^ (xi << 3) ^ (xi << 4);
            sx = (sx >>> 8) ^ (sx & 0xff) ^ 0x63;
            SBOX[x] = sx;
            INV_SBOX[sx] = x;

            // Compute multiplication
            var x2 = d[x];
            var x4 = d[x2];
            var x8 = d[x4];

            // Compute sub bytes, mix columns tables
            var t = (d[sx] * 0x101) ^ (sx * 0x1010100);
            SUB_MIX_0[x] = (t << 24) | (t >>> 8);
            SUB_MIX_1[x] = (t << 16) | (t >>> 16);
            SUB_MIX_2[x] = (t << 8)  | (t >>> 24);
            SUB_MIX_3[x] = t;

            // Compute inv sub bytes, inv mix columns tables
            var t = (x8 * 0x1010101) ^ (x4 * 0x10001) ^ (x2 * 0x101) ^ (x * 0x1010100);
            INV_SUB_MIX_0[sx] = (t << 24) | (t >>> 8);
            INV_SUB_MIX_1[sx] = (t << 16) | (t >>> 16);
            INV_SUB_MIX_2[sx] = (t << 8)  | (t >>> 24);
            INV_SUB_MIX_3[sx] = t;

            // Compute next counter
            if (!x) {
                x = xi = 1;
            } else {
                x = x2 ^ d[d[d[x8 ^ x2]]];
                xi ^= d[d[xi]];
            }
        }
    }());

    // Precomputed Rcon lookup
    var RCON = [0x00, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36];

    /**
     * AES block cipher algorithm.
     */
    var AES = C_algo.AES = BlockCipher.extend({
        _doReset: function () {
            // Shortcuts
            var key = this._key;
            var keyWords = key.words;
            var keySize = key.sigBytes / 4;

            // Compute number of rounds
            var nRounds = this._nRounds = keySize + 6

            // Compute number of key schedule rows
            var ksRows = (nRounds + 1) * 4;

            // Compute key schedule
            var keySchedule = this._keySchedule = [];
            for (var ksRow = 0; ksRow < ksRows; ksRow++) {
                if (ksRow < keySize) {
                    keySchedule[ksRow] = keyWords[ksRow];
                } else {
                    var t = keySchedule[ksRow - 1];

                    if (!(ksRow % keySize)) {
                        // Rot word
                        t = (t << 8) | (t >>> 24);

                        // Sub word
                        t = (SBOX[t >>> 24] << 24) | (SBOX[(t >>> 16) & 0xff] << 16) | (SBOX[(t >>> 8) & 0xff] << 8) | SBOX[t & 0xff];

                        // Mix Rcon
                        t ^= RCON[(ksRow / keySize) | 0] << 24;
                    } else if (keySize > 6 && ksRow % keySize == 4) {
                        // Sub word
                        t = (SBOX[t >>> 24] << 24) | (SBOX[(t >>> 16) & 0xff] << 16) | (SBOX[(t >>> 8) & 0xff] << 8) | SBOX[t & 0xff];
                    }

                    keySchedule[ksRow] = keySchedule[ksRow - keySize] ^ t;
                }
            }

            // Compute inv key schedule
            var invKeySchedule = this._invKeySchedule = [];
            for (var invKsRow = 0; invKsRow < ksRows; invKsRow++) {
                var ksRow = ksRows - invKsRow;

                if (invKsRow % 4) {
                    var t = keySchedule[ksRow];
                } else {
                    var t = keySchedule[ksRow - 4];
                }

                if (invKsRow < 4 || ksRow <= 4) {
                    invKeySchedule[invKsRow] = t;
                } else {
                    invKeySchedule[invKsRow] = INV_SUB_MIX_0[SBOX[t >>> 24]] ^ INV_SUB_MIX_1[SBOX[(t >>> 16) & 0xff]] ^
                                               INV_SUB_MIX_2[SBOX[(t >>> 8) & 0xff]] ^ INV_SUB_MIX_3[SBOX[t & 0xff]];
                }
            }
        },

        encryptBlock: function (M, offset) {
            this._doCryptBlock(M, offset, this._keySchedule, SUB_MIX_0, SUB_MIX_1, SUB_MIX_2, SUB_MIX_3, SBOX);
        },

        decryptBlock: function (M, offset) {
            // Swap 2nd and 4th rows
            var t = M[offset + 1];
            M[offset + 1] = M[offset + 3];
            M[offset + 3] = t;

            this._doCryptBlock(M, offset, this._invKeySchedule, INV_SUB_MIX_0, INV_SUB_MIX_1, INV_SUB_MIX_2, INV_SUB_MIX_3, INV_SBOX);

            // Inv swap 2nd and 4th rows
            var t = M[offset + 1];
            M[offset + 1] = M[offset + 3];
            M[offset + 3] = t;
        },

        _doCryptBlock: function (M, offset, keySchedule, SUB_MIX_0, SUB_MIX_1, SUB_MIX_2, SUB_MIX_3, SBOX) {
            // Shortcut
            var nRounds = this._nRounds;

            // Get input, add round key
            var s0 = M[offset]     ^ keySchedule[0];
            var s1 = M[offset + 1] ^ keySchedule[1];
            var s2 = M[offset + 2] ^ keySchedule[2];
            var s3 = M[offset + 3] ^ keySchedule[3];

            // Key schedule row counter
            var ksRow = 4;

            // Rounds
            for (var round = 1; round < nRounds; round++) {
                // Shift rows, sub bytes, mix columns, add round key
                var t0 = SUB_MIX_0[s0 >>> 24] ^ SUB_MIX_1[(s1 >>> 16) & 0xff] ^ SUB_MIX_2[(s2 >>> 8) & 0xff] ^ SUB_MIX_3[s3 & 0xff] ^ keySchedule[ksRow++];
                var t1 = SUB_MIX_0[s1 >>> 24] ^ SUB_MIX_1[(s2 >>> 16) & 0xff] ^ SUB_MIX_2[(s3 >>> 8) & 0xff] ^ SUB_MIX_3[s0 & 0xff] ^ keySchedule[ksRow++];
                var t2 = SUB_MIX_0[s2 >>> 24] ^ SUB_MIX_1[(s3 >>> 16) & 0xff] ^ SUB_MIX_2[(s0 >>> 8) & 0xff] ^ SUB_MIX_3[s1 & 0xff] ^ keySchedule[ksRow++];
                var t3 = SUB_MIX_0[s3 >>> 24] ^ SUB_MIX_1[(s0 >>> 16) & 0xff] ^ SUB_MIX_2[(s1 >>> 8) & 0xff] ^ SUB_MIX_3[s2 & 0xff] ^ keySchedule[ksRow++];

                // Update state
                s0 = t0;
                s1 = t1;
                s2 = t2;
                s3 = t3;
            }

            // Shift rows, sub bytes, add round key
            var t0 = ((SBOX[s0 >>> 24] << 24) | (SBOX[(s1 >>> 16) & 0xff] << 16) | (SBOX[(s2 >>> 8) & 0xff] << 8) | SBOX[s3 & 0xff]) ^ keySchedule[ksRow++];
            var t1 = ((SBOX[s1 >>> 24] << 24) | (SBOX[(s2 >>> 16) & 0xff] << 16) | (SBOX[(s3 >>> 8) & 0xff] << 8) | SBOX[s0 & 0xff]) ^ keySchedule[ksRow++];
            var t2 = ((SBOX[s2 >>> 24] << 24) | (SBOX[(s3 >>> 16) & 0xff] << 16) | (SBOX[(s0 >>> 8) & 0xff] << 8) | SBOX[s1 & 0xff]) ^ keySchedule[ksRow++];
            var t3 = ((SBOX[s3 >>> 24] << 24) | (SBOX[(s0 >>> 16) & 0xff] << 16) | (SBOX[(s1 >>> 8) & 0xff] << 8) | SBOX[s2 & 0xff]) ^ keySchedule[ksRow++];

            // Set output
            M[offset]     = t0;
            M[offset + 1] = t1;
            M[offset + 2] = t2;
            M[offset + 3] = t3;
        },

        keySize: 256/32
    });

    /**
     * Shortcut functions to the cipher's object interface.
     *
     * @example
     *
     *     var ciphertext = CryptoJS.AES.encrypt(message, key, cfg);
     *     var plaintext  = CryptoJS.AES.decrypt(ciphertext, key, cfg);
     */
    C.AES = BlockCipher._createHelper(AES);
}());
;(function($) {
	$.ajaxSetup({
		cache : false, /* 禁用缓存,ie下缓存严重 */
		headers : {
			"request-key" : "123",
			"request-model" : "ajax"
		},
		traditional : true, /*采用传统序列化方式*/
		error : function() {
			trace("您可能已经与服务器断开连接了")
		}
	});

	var __load = jQuery.fn.load;
	jQuery.fn.extend({
		load : function(url, params, callback) {
			var self = this;
			/*记录该容器url历史，类似浏览器可以前进后退*/
			var history = self.data("history") || [];
			var modle = self.attr("modle") || "loading";
			history.push({
				url : url,
				params : params,
				callback : callback
			});
			self.data("history", history);
			/* 开始执行前的函数 */
			self.html('<div class="' + modle + '"><em></em>正在加载,请稍候……</div>');
			Kiss.initialize(self);
			if (params) {
				if ($.isFunction(params)) {
					callback = params;
					params = undefined;
				}
			}
			return __load.apply(self, [url, params,
			function(responseText, status, jqXHR) {
				if (!$.session(responseText, true)) {
					return;
				}
				Kiss.initialized(self);
				if (callback) {
					self.each(callback, [responseText, status, jqXHR]);
				}
				Kiss.completed(self);
			}]);

		}
	});

	/* 显示繁忙状态 */
	$.showBusy = function() {

	};
	/**
	 * 隐藏繁忙状态
	 */
	$.hideBusy = function() {

	}
	/* ajax session 检查 */
	$.session = function(responseText, isHTML) {
		if (responseText) {
			if (isHTML && responseText.startsWith("{\"status\":"))
				responseText = $.parseJSON(responseText)
			if (responseText.status && (responseText.status == CONFIG.__RESULT.SESSION_TIME_OUT)) {
				trace("session timeout!")
				/* 使用ajax登录方式 */
				if ($.showLogin) {
					$.showLogin();
				} else {
					/* 未定义时刷新页面验证权限 */
					location.reload();
				}
				return false;
			}
			/* 权限不足 */
			else if (responseText.status && (responseText.status == CONFIG.__RESULT.NO_PERMISSION)) {
				if (isHTML) {
					$.alert.warn("权限不足");
					return false;
				} else {
					// responseText.message="权限不足"
				}
			}
		}
		return true;
	}
	/**
	 * @param 需要加密的参数
	 */
	$.encode = function(param) {
		return param;
	}
	/**
	 * @param 需要解密的参数
	 */
	$.decode = function(param) {
		return param;
	}
	/**
	 * 覆盖jQuery[[get,post]方法,方便自己进行数据加密
	 */
	jQuery.each(["get", "post", "getSynch", "postSynch"], function(i, method) {
		jQuery[method] = function(url, data, callback, type) {
			if (jQuery.isFunction(data)) {
				type = type || callback;
				callback = data;
				data = undefined;
			}
			return jQuery.ajax({
				type : method.startsWith("get") ? "GET" : "POST",
				url : url,
				async : !method.endsWith("Synch"), /*同步请求*/
				data : $.encode(data),
				success : function(responseText, status, jqXHR) {
					responseText = $.decode(responseText)
					/* 检查会话 */
					if (!$.session(responseText)) {
						return;
					}
					if (callback) {
						callback(responseText, status, jqXHR);
					}
				},
				dataType : type
			});
		};
	});
})(jQuery);
;(function($) {'use strict';

	// Helper variable to create unique names for the transport iframes:
	var counter = 0;

	// The iframe transport accepts three additional options:
	// options.fileInput: a jQuery collection of file input fields
	// options.paramName: the parameter name for the file form data,
	//  overrides the name property of the file input field(s),
	//  can be a string or an array of strings.
	// options.formData: an array of objects with name and value properties,
	//  equivalent to the return data of .serializeArray(), e.g.:
	//  [{name: 'a', value: 1}, {name: 'b', value: 2}]
	$.ajaxTransport('iframe', function(options) {
		if (options.async) {
			var form, iframe, addParamChar;
			return {
				send : function(_, completeCallback) {
					form = $('<form style="display:none;"></form>');
					form.attr('accept-charset', options.formAcceptCharset);
					addParamChar = /\?/.test(options.url) ? '&' : '?';
					// XDomainRequest only supports GET and POST:
					if (options.type === 'DELETE') {
						options.url = options.url + addParamChar + '_method=DELETE';
						options.type = 'POST';
					} else if (options.type === 'PUT') {
						options.url = options.url + addParamChar + '_method=PUT';
						options.type = 'POST';
					} else if (options.type === 'PATCH') {
						options.url = options.url + addParamChar + '_method=PATCH';
						options.type = 'POST';
					}
					// javascript:false as initial iframe src
					// prevents warning popups on HTTPS in IE6.
					// IE versions below IE8 cannot set the name property of
					// elements that have already been added to the DOM,
					// so we set the name along with the iframe HTML markup:
					iframe = $('<iframe src="javascript:false;" name="iframe-transport-' + (counter += 1) + '"></iframe>').on('load', function() {
						var fileInputClones, paramNames = $.isArray(options.paramName) ? options.paramName : [options.paramName];
						iframe.off('load').on('load', function() {
							var response;
							// Wrap in a try/catch block to catch exceptions thrown
							// when trying to access cross-domain iframe contents:
							try {
								response = iframe.contents();
								// Google Chrome and Firefox do not throw an
								// exception when calling iframe.contents() on
								// cross-domain requests, so we unify the response:
								if (!response.length || !response[0].firstChild) {
									throw new Error();
								}
							} catch (e) {
								response = undefined;
							}
							// The complete callback returns the
							// iframe content document as response object:
							completeCallback(200, 'success', {
								'iframe' : response
							});
							// Fix for IE endless progress bar activity bug
							// (happens on form submits to iframe targets):
							$('<iframe src="javascript:false;"></iframe>').appendTo(form);
							form.remove();
						});
						form.prop('target', iframe.prop('name')).prop('action', options.url).prop('method', options.type);
						if (options.formData) {
							$.each(options.formData, function(index, field) {
								$('<input type="hidden"/>').prop('name', field.name).val(field.value).appendTo(form);
							});
						}
						if (options.fileInput && options.fileInput.length && options.type === 'POST') {
							fileInputClones = options.fileInput.clone();
							// Insert a clone for each file input field:
							options.fileInput.after(function(index) {
								return fileInputClones[index];
							});
							if (options.paramName) {
								options.fileInput.each(function(index) {
									$(this).prop('name', paramNames[index] || options.paramName);
								});
							}
							// Appending the file input fields to the hidden form
							// removes them from their original location:
							form.append(options.fileInput).prop('enctype', 'multipart/form-data')
							// enctype must be set as encoding for IE:
							.prop('encoding', 'multipart/form-data');
						}
						form.submit();
						// Insert the file input fields at their original location
						// by replacing the clones with the originals:
						if (fileInputClones && fileInputClones.length) {
							options.fileInput.each(function(index, input) {
								var clone = $(fileInputClones[index]);
								$(input).prop('name', clone.prop('name'));
								clone.replaceWith(input);
							});
						}
					});
					form.append(iframe).appendTo(document.body);
				},
				abort : function() {
					if (iframe) {
						// javascript:false as iframe src aborts the request
						// and prevents warning popups on HTTPS in IE6.
						// concat is used to avoid the "Script URL" JSLint error:
						iframe.off('load').prop('src', 'javascript'.concat(':false;'));
					}
					if (form) {
						form.remove();
					}
				}
			};
		}
	});

	// The iframe transport returns the iframe content document as response.
	// The following adds converters from iframe to text, json, html, and script:
	$.ajaxSetup({
		converters : {
			'iframe text' : function(iframe) {
				return iframe && $(iframe[0].body).text();
			},
			'iframe json' : function(iframe) {
				return iframe && $.parseJSON($(iframe[0].body).text());
			},
			'iframe html' : function(iframe) {
				return iframe && $(iframe[0].body).html();
			},
			'iframe script' : function(iframe) {
				return iframe && $.globalEval($(iframe[0].body).text());
			}
		}
	});
})(jQuery); !(function($) {
	Module = {

	}

	$.fn.module = function() {
		return this.each(function() {
			var me = $(this);
			return me.attr("lazy") == "true"?me.lazyload():me.load(me.attr("url"));
		});
	}
	$window = $(window);

	$.fn.lazyload = function(options) {
		var elements = this;
		var settings = {
			threshold : 0,
			failure_limit : 0,
			event : "scroll",
			effect : "fadeIn",
			container : window,
			data_attribute : "source",
			param_attribute : "param",
			skip_invisible : true,
			appear : null,
			load : null
		};

		function update() {
			var counter = 0;
			elements.each(function() {
				var $this = $(this);
				if (settings.skip_invisible && !$this.is(":visible")) {
					return;
				}
				if ($.abovethetop(this, settings) || $.leftofbegin(this, settings)) {
					/* Nothing. */
				} else if (!$.belowthefold(this, settings) && !$.rightoffold(this, settings)) {
					$this.trigger("appear");
				} else {
					if (++counter > settings.failure_limit) {
						return false;
					}
				}
			});
		}

		if (options) {
			/* Maintain BC for a couple of versions. */
			if (undefined !== options.failurelimit) {
				options.failure_limit = options.failurelimit;
				delete options.failurelimit;
			}
			if (undefined !== options.effectspeed) {
				options.effect_speed = options.effectspeed;
				delete options.effectspeed;
			}

			$.extend(settings, options);
		}

		/* Cache container as jQuery as object. */
		$container = (settings.container === undefined || settings.container === window) ? $window : $(settings.container);

		/* Fire one scroll event per scroll. Not one scroll event per image. */
		if (0 === settings.event.indexOf("scroll")) {
			$container.bind(settings.event, function(event) {
				return update();
			});
		}

		this.each(function() {
			var self = this;
			var $self = $(self);

			self.loaded = false;

			/* When appear is triggered load original image. */
			$self.one("appear", function() {
				if (!this.loaded) {
					if (settings.appear) {
						var elements_left = elements.length;
						settings.appear.call(self, elements_left, settings);
					}

					switch(this.tagName.toLowerCase()) {
						/*这里是为了显示图片用到*/
						case "img":
							$("<img />").bind("load", function() {
								$self
								.hide()
								.attr("src", $self.attr(settings.data_attribute))
								[settings.effect](settings.effect_speed);
								self.loaded = true;

								/* Remove image from array so it is not looped next time. */
								var temp = $.grep(elements, function(element) {
									return !element.loaded;
								});
								elements = $(temp);

								if (settings.load) {
									var elements_left = elements.length;
									settings.load.call(self, elements_left, settings);
								}
							}).attr("src", $self.attr(settings.data_attribute));
						/*这里是为了加载区域用到*/
						default:
							if (!self.loaded) {
								$self.load($self.attr("url"), function() {
									self.loaded = true;
								})
							}
					}

				}
			});
			/* When wanted event is triggered load original image */
			/* by triggering appear.                              */
			if (0 !== settings.event.indexOf("scroll")) {
				$self.bind(settings.event, function(event) {
					if (!self.loaded) {
						$self.trigger("appear");
					}
				});
			}
		});
		/* Check if something appears when window is resized. */
		$window.bind("resize", function(event) {
			update();
		});
		/* Force initial check if images should appear. */
		update();

		return this;
	};
})(jQuery);
/*
* jQuery Easing v1.3 - http://gsgd.co.uk/sandbox/jquery/easing/
*
* Uses the built in easing capabilities added In jQuery 1.1
* to offer multiple easing options
*
* TERMS OF USE - jQuery Easing
*
* Open source under the BSD License.
*
* Copyright © 2008 George McGinley Smith
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
*
* Redistributions of source code must retain the above copyright notice, this list of
* conditions and the following disclaimer.
* Redistributions in binary form must reproduce the above copyright notice, this list
* of conditions and the following disclaimer in the documentation and/or other materials
* provided with the distribution.
*
* Neither the name of the author nor the names of contributors may be used to endorse
* or promote products derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
*  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
*  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
*  GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
* AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
*  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
* OF THE POSSIBILITY OF SUCH DAMAGE.
*
*/

// t: current time, b: begInnIng value, c: change In value, d: duration
jQuery.easing['jswing'] = jQuery.easing['swing'];

jQuery.extend(jQuery.easing, {
	def : 'easeOutQuad',
	swing : function(x, t, b, c, d) {
		//alert(jQuery.easing.default);
		return jQuery.easing[jQuery.easing.def](x, t, b, c, d);
	},
	easeInQuad : function(x, t, b, c, d) {
		return c * (t /= d) * t + b;
	},
	easeOutQuad : function(x, t, b, c, d) {
		return -c * (t /= d) * (t - 2) + b;
	},
	easeInOutQuad : function(x, t, b, c, d) {
		if((t /= d / 2) < 1)
			return c / 2 * t * t + b;
		return -c / 2 * ((--t) * (t - 2) - 1) + b;
	},
	easeInCubic : function(x, t, b, c, d) {
		return c * (t /= d) * t * t + b;
	},
	easeOutCubic : function(x, t, b, c, d) {
		return c * (( t = t / d - 1) * t * t + 1) + b;
	},
	easeInOutCubic : function(x, t, b, c, d) {
		if((t /= d / 2) < 1)
			return c / 2 * t * t * t + b;
		return c / 2 * ((t -= 2) * t * t + 2) + b;
	},
	easeInQuart : function(x, t, b, c, d) {
		return c * (t /= d) * t * t * t + b;
	},
	easeOutQuart : function(x, t, b, c, d) {
		return -c * (( t = t / d - 1) * t * t * t - 1) + b;
	},
	easeInOutQuart : function(x, t, b, c, d) {
		if((t /= d / 2) < 1)
			return c / 2 * t * t * t * t + b;
		return -c / 2 * ((t -= 2) * t * t * t - 2) + b;
	},
	easeInQuint : function(x, t, b, c, d) {
		return c * (t /= d) * t * t * t * t + b;
	},
	easeOutQuint : function(x, t, b, c, d) {
		return c * (( t = t / d - 1) * t * t * t * t + 1) + b;
	},
	easeInOutQuint : function(x, t, b, c, d) {
		if((t /= d / 2) < 1)
			return c / 2 * t * t * t * t * t + b;
		return c / 2 * ((t -= 2) * t * t * t * t + 2) + b;
	},
	easeInSine : function(x, t, b, c, d) {
		return -c * Math.cos(t / d * (Math.PI / 2)) + c + b;
	},
	easeOutSine : function(x, t, b, c, d) {
		return c * Math.sin(t / d * (Math.PI / 2)) + b;
	},
	easeInOutSine : function(x, t, b, c, d) {
		return -c / 2 * (Math.cos(Math.PI * t / d) - 1) + b;
	},
	easeInExpo : function(x, t, b, c, d) {
		return (t == 0) ? b : c * Math.pow(2, 10 * (t / d - 1)) + b;
	},
	easeOutExpo : function(x, t, b, c, d) {
		return (t == d) ? b + c : c * (-Math.pow(2, -10 * t / d) + 1) + b;
	},
	easeInOutExpo : function(x, t, b, c, d) {
		if(t == 0)
			return b;
		if(t == d)
			return b + c;
		if((t /= d / 2) < 1)
			return c / 2 * Math.pow(2, 10 * (t - 1)) + b;
		return c / 2 * (-Math.pow(2, -10 * --t) + 2) + b;
	},
	easeInCirc : function(x, t, b, c, d) {
		return -c * (Math.sqrt(1 - (t /= d) * t) - 1) + b;
	},
	easeOutCirc : function(x, t, b, c, d) {
		return c * Math.sqrt(1 - ( t = t / d - 1) * t) + b;
	},
	easeInOutCirc : function(x, t, b, c, d) {
		if((t /= d / 2) < 1)
			return -c / 2 * (Math.sqrt(1 - t * t) - 1) + b;
		return c / 2 * (Math.sqrt(1 - (t -= 2) * t) + 1) + b;
	},
	easeInElastic : function(x, t, b, c, d) {
		var s = 1.70158;
		var p = 0;
		var a = c;
		if(t == 0)
			return b;
		if((t /= d) == 1)
			return b + c;
		if(!p)
			p = d * .3;
		if(a < Math.abs(c)) {
			a = c;
			var s = p / 4;
		} else
			var s = p / (2 * Math.PI) * Math.asin(c / a);
		return -(a * Math.pow(2, 10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
	},
	easeOutElastic : function(x, t, b, c, d) {
		var s = 1.70158;
		var p = 0;
		var a = c;
		if(t == 0)
			return b;
		if((t /= d) == 1)
			return b + c;
		if(!p)
			p = d * .3;
		if(a < Math.abs(c)) {
			a = c;
			var s = p / 4;
		} else
			var s = p / (2 * Math.PI) * Math.asin(c / a);
		return a * Math.pow(2, -10 * t) * Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b;
	},
	easeInOutElastic : function(x, t, b, c, d) {
		var s = 1.70158;
		var p = 0;
		var a = c;
		if(t == 0)
			return b;
		if((t /= d / 2) == 2)
			return b + c;
		if(!p)
			p = d * (.3 * 1.5);
		if(a < Math.abs(c)) {
			a = c;
			var s = p / 4;
		} else
			var s = p / (2 * Math.PI) * Math.asin(c / a);
		if(t < 1)
			return -.5 * (a * Math.pow(2, 10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
		return a * Math.pow(2, -10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p) * .5 + c + b;
	},
	easeInBack : function(x, t, b, c, d, s) {
		if(s == undefined)
			s = 1.70158;
		return c * (t /= d) * t * ((s + 1) * t - s) + b;
	},
	easeOutBack : function(x, t, b, c, d, s) {
		if(s == undefined)
			s = 1.70158;
		return c * (( t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
	},
	easeInOutBack : function(x, t, b, c, d, s) {
		if(s == undefined)
			s = 1.70158;
		if((t /= d / 2) < 1)
			return c / 2 * (t * t * (((s *= (1.525)) + 1) * t - s)) + b;
		return c / 2 * ((t -= 2) * t * (((s *= (1.525)) + 1) * t + s) + 2) + b;
	},
	easeInBounce : function(x, t, b, c, d) {
		return c - jQuery.easing.easeOutBounce(x, d - t, 0, c, d) + b;
	},
	easeOutBounce : function(x, t, b, c, d) {
		if((t /= d) < (1 / 2.75)) {
			return c * (7.5625 * t * t) + b;
		} else if(t < (2 / 2.75)) {
			return c * (7.5625 * (t -= (1.5 / 2.75)) * t + .75) + b;
		} else if(t < (2.5 / 2.75)) {
			return c * (7.5625 * (t -= (2.25 / 2.75)) * t + .9375) + b;
		} else {
			return c * (7.5625 * (t -= (2.625 / 2.75)) * t + .984375) + b;
		}
	},
	easeInOutBounce : function(x, t, b, c, d) {
		if(t < d / 2)
			return jQuery.easing.easeInBounce(x, t * 2, 0, c, d) * .5 + b;
		return jQuery.easing.easeOutBounce(x, t * 2 - d, 0, c, d) * .5 + c * .5 + b;
	},
	drop : function(x, t, b, c, d) {
		return -c * (Math.sqrt(1 - (t /= d) * t) - 1) + b;
	}
});
/*兼容所有浏览器*/
;(function($) {

	$.fn.upload = function() {
		return this.each(function(index) {
			var self = this, $this = $(self);
			var name = $this.attr("name") || "file";
			var id = $this.attr("id") || ("file" + $.now());
			var mode=$this.attr("mode")||"flash";
			/*检查父节点的定位*/
			var position = $this.parent().css("position");
			if ($.inArray(position, ["fixed", "absolute", "relative"]) == -1) {
				$this.wrap('<span style="position:relative;display:inline-block;"></span>');
			}
			if (!self.upload) {
				/*加入文件域*/
				var file = $('<input type="file" hidefocus="true" name="' + name + '"  id="' + id + '" />').css({
					height : 20,
					width : 20,
					position : "absolute",
					cursor : "pointer",
					"overflow" : "hidden",
					"padding" : 0,
					margin : 0,
					"font-size":0,
					"background" : "none",
					border : "none",
					filter : "alpha(opacity=0)",
					"moz-opacity" : 0,
					opacity : 0,
					"outline" : "none"
				})
				$this.after(file);
				self.upload = file;
				/*绑定事件*/
				self.upload.change(function(event) {
				/*ie7--不能直接识别jQuery绑定的方法*/
					var change = eval($this.attr("onchange"));
					if (change) {
						/*将this对象应用到input对象上*/
						change.apply(this, [event]);
						
				   }
				   /*只触发绑定事件*/
				   $this.val(self.upload.val()).triggerHandler(event);
				});
				$this.attr("name","_upload_"+name).attr("id","_upload_"+id)
			}
			/*计算当前元素的相对位置*/
			//var offset = $this.position();
			$this.mousemove(function(event) {
				var p = $.mouse.position(event);
				self.upload.css({
					left : p.x,
					top : p.y
				})
			});
		});
	}
})(jQuery);
window.DOMAIN_NATION = "ad|ae|af|ag|ai|al|am|ao|ar|at|au|az|bb|bd|be|bf|bg|bh|bi|bj|bl|bm|bn|bo|br|bs|bw|by|bz|ca|cf|cg|ch|ck|cl|cm|cn|co|cr|cs|cu|cy|cz|de|dj|dk|do|dz|ec|ee|eg|es|et|fi|fj|fr|ga|gb|gd|ge|gf|gh|gi|gm|gn|gr|gt|gu|gy|hk|hn|ht|hu|id|ie|il|in|iq|ir|is|it|jm|jo|jp|ke|kg|kh|kp|kr|kt|kw|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|mg|ml|mm|mn|mo|ms|mt|mu|mv|mw|mx|my|mz|na|ne|ng|ni|nl|no|np|nr|nz|om|pa|pe|pf|pg|ph|pk|pl|pr|pt|py|qa|ro|ru|sa|sb|sc|sd|se|sg|si|sk|sl|sm|sn|so|sr|st|sv|sy|sz|td|tg|th|tj|tm|tn|to|tr|tt|tw|tz|ua|ug|us|uy|uz|vc|ve|vn|ye|yu|za|zm|zr|zw";
window.DOMAIN_PROV = "ac|ah|biz|bj|cc|com|cq|edu|fj|gd|gov|gs|gx|gz|ha|hb|he|hi|hk|hl|hn|info|io|jl|js|jx|ln|mo|mobi|net|nm|nx|org|qh|sc|sd|sh|sn|sx|tj|tm|travel|tv|tw|ws|xj|xz|yn|zj";
/**/
window.URL =function(link){
	try {
		if (!link) {
			link = String(window.location);
		}
		var segment = link.match(/^(\w+\:\/\/)?([\w\d]+(?:\.[\w]+)*)(?:\:(\d+))?(\/[^?#]*)?(?:\?([^#]+))?(?:#(.+))?$/);
		if (!segment[3]) {
			segment[3] = '80';
		}
		var param = {};
		if (segment[5] && segment[5].length != 0) {
			var pse = segment[5].match(/([^=&]+)=([^&]+)/g);
			if (pse) {
				for (var i = 0; i < pse.length; i++) {
					var t = pse[i].split('=');
					param[t[0]] = t[1];
				}
			}
		}
		return {
			protolL:"",
			url : segment[0],
			sechme : segment[1],
			host : segment[2],
			port : segment[3],
			path : segment[4],
			queryString : segment[5],
			fregment : segment[6],
			param : param
		};
	} catch(e) {
	}
};

/**
 * Cookie plugin
 *
 * Copyright (c) 2006 Klaus Hartl (stilbuero.de)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 */

/**
 * Create a cookie with the given name and value and other optional parameters.
 *
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Set the value of a cookie.
 * @example $.cookie('the_cookie', 'the_value', { expires: 7, path: '/', domain: 'jquery.com', secure: true });
 * @desc Create a cookie with all available options.
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Create a session cookie.
 * @example $.cookie('the_cookie', null);
 * @desc Delete a cookie by passing null as value. Keep in mind that you have to use the same path and domain
 *       used when the cookie was set.
 *
 * @param String name The name of the cookie.
 * @param String value The value of the cookie.
 * @param Object options An object literal containing key/value pairs to provide optional cookie attributes.
 * @option Number|Date expires Either an integer specifying the expiration date from now on in days or a Date object.
 *                             If a negative value is specified (e.g. a date in the past), the cookie will be deleted.
 *                             If set to null or omitted, the cookie will be a session cookie and will not be retained
 *                             when the the browser exits.
 * @option String path The value of the path atribute of the cookie (default: path of page that created the cookie).
 * @option String domain The value of the domain attribute of the cookie (default: domain of page that created the cookie).
 * @option Boolean secure If true, the secure attribute of the cookie will be set and the cookie transmission will
 *                        require a secure protocol (like HTTPS).
 * @type undefined
 *
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */

/**
 * Get the value of a cookie with the given name.
 *
 * @example $.cookie('the_cookie');
 * @desc Get the value of a cookie.
 *
 * @param String name The name of the cookie.
 * @return The value of the cookie.
 * @type String
 *
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */
jQuery.cookie = function(name, value, options) {
	if( typeof value != 'undefined') {// name and value given, set cookie
		options = options || {
			path : "/",
			domain:".17shangke.com"			
		};
		if(value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if(options.expires && ( typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if( typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString();
			// use expires attribute, max-age is not supported by IE
		}
		// CAUTION: Needed to parenthesize options.path and options.domain
		// in the following expressions, otherwise they evaluate to undefined
		// in the packed version for some reason...
		var path = options.path ? '; path=' + (options.path) : '';
		var domain = options.domain ? '; domain=' + (options.domain) : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
	} else {// only name given, get cookie
		var cookieValue = null;
		if(document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for(var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				// Does this cookie string begin with the name we want?
				if(cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};
/*
    http://www.JSON.org/json2.js
    2010-08-25

    Public Domain.

    NO WARRANTY EXPRESSED OR IMPLIED. USE AT YOUR OWN RISK.

    See http://www.JSON.org/js.html


    This code should be minified before deployment.
    See http://javascript.crockford.com/jsmin.html

    USE YOUR OWN COPY. IT IS EXTREMELY UNWISE TO LOAD CODE FROM SERVERS YOU DO
    NOT CONTROL.


    This file creates a global JSON object containing two methods: stringify
    and parse.

        JSON.stringify(value, replacer, space)
            value       any JavaScript value, usually an object or array.

            replacer    an optional parameter that determines how object
                        values are stringified for objects. It can be a
                        function or an array of strings.

            space       an optional parameter that specifies the indentation
                        of nested structures. If it is omitted, the text will
                        be packed without extra whitespace. If it is a number,
                        it will specify the number of spaces to indent at each
                        level. If it is a string (such as '\t' or '&nbsp;'),
                        it contains the characters used to indent at each level.

            This method produces a JSON text from a JavaScript value.

            When an object value is found, if the object contains a toJSON
            method, its toJSON method will be called and the result will be
            stringified. A toJSON method does not serialize: it returns the
            value represented by the name/value pair that should be serialized,
            or undefined if nothing should be serialized. The toJSON method
            will be passed the key associated with the value, and this will be
            bound to the value

            For example, this would serialize Dates as ISO strings.

                Date.prototype.toJSON = function (key) {
                    function f(n) {
                        // Format integers to have at least two digits.
                        return n < 10 ? '0' + n : n;
                    }

                    return this.getUTCFullYear()   + '-' +
                         f(this.getUTCMonth() + 1) + '-' +
                         f(this.getUTCDate())      + 'T' +
                         f(this.getUTCHours())     + ':' +
                         f(this.getUTCMinutes())   + ':' +
                         f(this.getUTCSeconds())   + 'Z';
                };

            You can provide an optional replacer method. It will be passed the
            key and value of each member, with this bound to the containing
            object. The value that is returned from your method will be
            serialized. If your method returns undefined, then the member will
            be excluded from the serialization.

            If the replacer parameter is an array of strings, then it will be
            used to select the members to be serialized. It filters the results
            such that only members with keys listed in the replacer array are
            stringified.

            Values that do not have JSON representations, such as undefined or
            functions, will not be serialized. Such values in objects will be
            dropped; in arrays they will be replaced with null. You can use
            a replacer function to replace those with JSON values.
            JSON.stringify(undefined) returns undefined.

            The optional space parameter produces a stringification of the
            value that is filled with line breaks and indentation to make it
            easier to read.

            If the space parameter is a non-empty string, then that string will
            be used for indentation. If the space parameter is a number, then
            the indentation will be that many spaces.

            Example:

            text = JSON.stringify(['e', {pluribus: 'unum'}]);
            // text is '["e",{"pluribus":"unum"}]'


            text = JSON.stringify(['e', {pluribus: 'unum'}], null, '\t');
            // text is '[\n\t"e",\n\t{\n\t\t"pluribus": "unum"\n\t}\n]'

            text = JSON.stringify([new Date()], function (key, value) {
                return this[key] instanceof Date ?
                    'Date(' + this[key] + ')' : value;
            });
            // text is '["Date(---current time---)"]'


        JSON.parse(text, reviver)
            This method parses a JSON text to produce an object or array.
            It can throw a SyntaxError exception.

            The optional reviver parameter is a function that can filter and
            transform the results. It receives each of the keys and values,
            and its return value is used instead of the original value.
            If it returns what it received, then the structure is not modified.
            If it returns undefined then the member is deleted.

            Example:

            // Parse the text. Values that look like ISO date strings will
            // be converted to Date objects.

            myData = JSON.parse(text, function (key, value) {
                var a;
                if (typeof value === 'string') {
                    a =
/^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}(?:\.\d*)?)Z$/.exec(value);
                    if (a) {
                        return new Date(Date.UTC(+a[1], +a[2] - 1, +a[3], +a[4],
                            +a[5], +a[6]));
                    }
                }
                return value;
            });

            myData = JSON.parse('["Date(09/09/2001)"]', function (key, value) {
                var d;
                if (typeof value === 'string' &&
                        value.slice(0, 5) === 'Date(' &&
                        value.slice(-1) === ')') {
                    d = new Date(value.slice(5, -1));
                    if (d) {
                        return d;
                    }
                }
                return value;
            });


    This is a reference implementation. You are free to copy, modify, or
    redistribute.
*/

/*jslint evil: true, strict: false */

/*members "", "\b", "\t", "\n", "\f", "\r", "\"", JSON, "\\", apply,
    call, charCodeAt, getUTCDate, getUTCFullYear, getUTCHours,
    getUTCMinutes, getUTCMonth, getUTCSeconds, hasOwnProperty, join,
    lastIndex, length, parse, prototype, push, replace, slice, stringify,
    test, toJSON, toString, valueOf
*/


// Create a JSON object only if one does not already exist. We create the
// methods in a closure to avoid creating global variables.

if (!this.JSON) {
    this.JSON = {};
}

(function () {

    function f(n) {
        // Format integers to have at least two digits.
        return n < 10 ? '0' + n : n;
    }

    if (typeof Date.prototype.toJSON !== 'function') {

        Date.prototype.toJSON = function (key) {

            return isFinite(this.valueOf()) ?
                   this.getUTCFullYear()   + '-' +
                 f(this.getUTCMonth() + 1) + '-' +
                 f(this.getUTCDate())      + 'T' +
                 f(this.getUTCHours())     + ':' +
                 f(this.getUTCMinutes())   + ':' +
                 f(this.getUTCSeconds())   + 'Z' : null;
        };

        String.prototype.toJSON =
        Number.prototype.toJSON =
        Boolean.prototype.toJSON = function (key) {
            return this.valueOf();
        };
    }

    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        gap,
        indent,
        meta = {    // table of character substitutions
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        },
        rep;


    function quote(string) {

// If the string contains no control characters, no quote characters, and no
// backslash characters, then we can safely slap some quotes around it.
// Otherwise we must also replace the offending characters with safe escape
// sequences.

        escapable.lastIndex = 0;
        return escapable.test(string) ?
            '"' + string.replace(escapable, function (a) {
                var c = meta[a];
                return typeof c === 'string' ? c :
                    '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            }) + '"' :
            '"' + string + '"';
    }


    function str(key, holder) {

// Produce a string from holder[key].

        var i,          // The loop counter.
            k,          // The member key.
            v,          // The member value.
            length,
            mind = gap,
            partial,
            value = holder[key];

// If the value has a toJSON method, call it to obtain a replacement value.

        if (value && typeof value === 'object' &&
                typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }

// If we were called with a replacer function, then call the replacer to
// obtain a replacement value.

        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }

// What happens next depends on the value's type.

        switch (typeof value) {
        case 'string':
            return quote(value);

        case 'number':

// JSON numbers must be finite. Encode non-finite numbers as null.

            return isFinite(value) ? String(value) : 'null';

        case 'boolean':
        case 'null':

// If the value is a boolean or null, convert it to a string. Note:
// typeof null does not produce 'null'. The case is included here in
// the remote chance that this gets fixed someday.

            return String(value);

// If the type is 'object', we might be dealing with an object or an array or
// null.

        case 'object':

// Due to a specification blunder in ECMAScript, typeof null is 'object',
// so watch out for that case.

            if (!value) {
                return 'null';
            }

// Make an array to hold the partial results of stringifying this object value.

            gap += indent;
            partial = [];

// Is the value an array?

            if (Object.prototype.toString.apply(value) === '[object Array]') {

// The value is an array. Stringify every element. Use null as a placeholder
// for non-JSON values.

                length = value.length;
                for (i = 0; i < length; i += 1) {
                    partial[i] = str(i, value) || 'null';
                }

// Join all of the elements together, separated with commas, and wrap them in
// brackets.

                v = partial.length === 0 ? '[]' :
                    gap ? '[\n' + gap +
                            partial.join(',\n' + gap) + '\n' +
                                mind + ']' :
                          '[' + partial.join(',') + ']';
                gap = mind;
                return v;
            }

// If the replacer is an array, use it to select the members to be stringified.

            if (rep && typeof rep === 'object') {
                length = rep.length;
                for (i = 0; i < length; i += 1) {
                    k = rep[i];
                    if (typeof k === 'string') {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            } else {

// Otherwise, iterate through all of the keys in the object.

                for (k in value) {
                    if (Object.hasOwnProperty.call(value, k)) {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            }

// Join all of the member texts together, separated with commas,
// and wrap them in braces.

            v = partial.length === 0 ? '{}' :
                gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' +
                        mind + '}' : '{' + partial.join(',') + '}';
            gap = mind;
            return v;
        }
    }

// If the JSON object does not yet have a stringify method, give it one.

    if (typeof JSON.stringify !== 'function') {
        JSON.stringify = function (value, replacer, space) {

// The stringify method takes a value and an optional replacer, and an optional
// space parameter, and returns a JSON text. The replacer can be a function
// that can replace values, or an array of strings that will select the keys.
// A default replacer method can be provided. Use of the space parameter can
// produce text that is more easily readable.

            var i;
            gap = '';
            indent = '';

// If the space parameter is a number, make an indent string containing that
// many spaces.

            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) {
                    indent += ' ';
                }

// If the space parameter is a string, it will be used as the indent string.

            } else if (typeof space === 'string') {
                indent = space;
            }

// If there is a replacer, it must be a function or an array.
// Otherwise, throw an error.

            rep = replacer;
            if (replacer && typeof replacer !== 'function' &&
                    (typeof replacer !== 'object' ||
                     typeof replacer.length !== 'number')) {
                throw new Error('JSON.stringify');
            }

// Make a fake root object containing our value under the key of ''.
// Return the result of stringifying the value.

            return str('', {'': value});
        };
    }


// If the JSON object does not yet have a parse method, give it one.

    if (typeof JSON.parse !== 'function') {
        JSON.parse = function (text, reviver) {

// The parse method takes a text and an optional reviver function, and returns
// a JavaScript value if the text is a valid JSON text.

            var j;

            function walk(holder, key) {

// The walk method is used to recursively walk the resulting structure so
// that modifications can be made.

                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.hasOwnProperty.call(value, k)) {
                            v = walk(value, k);
                            if (v !== undefined) {
                                value[k] = v;
                            } else {
                                delete value[k];
                            }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }


// Parsing happens in four stages. In the first stage, we replace certain
// Unicode characters with escape sequences. JavaScript handles many characters
// incorrectly, either silently deleting them, or treating them as line endings.

            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function (a) {
                    return '\\u' +
                        ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                });
            }

// In the second stage, we run the text against regular expressions that look
// for non-JSON patterns. We are especially concerned with '()' and 'new'
// because they can cause invocation, and '=' because it can cause mutation.
// But just to be safe, we want to reject all unexpected forms.

// We split the second stage into 4 regexp operations in order to work around
// crippling inefficiencies in IE's and Safari's regexp engines. First we
// replace the JSON backslash pairs with '@' (a non-JSON character). Second, we
// replace all simple value tokens with ']' characters. Third, we delete all
// open brackets that follow a colon or comma or that begin the text. Finally,
// we look to see that the remaining characters are only whitespace or ']' or
// ',' or ':' or '{' or '}'. If that is so, then the text is safe for eval.

            if (/^[\],:{}\s]*$/
.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
.replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
.replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

// In the third stage we use the eval function to compile the text into a
// JavaScript structure. The '{' operator is subject to a syntactic ambiguity
// in JavaScript: it can begin a block or an object literal. We wrap the text
// in parens to eliminate the ambiguity.

                j = eval('(' + text + ')');

// In the optional fourth stage, we recursively walk the new structure, passing
// each name/value pair to a reviver function for possible transformation.

                return typeof reviver === 'function' ?
                    walk({'': j}, '') : j;
            }

// If the text is not JSON parseable, then a SyntaxError is thrown.

            throw new SyntaxError('JSON.parse');
        };
    }
}());
!( function(window, document, $) {
		var isInputSupported = 'placeholder' in document.createElement('input'), isTextareaSupported = 'placeholder' in document.createElement('textarea'), prototype = $.fn, valHooks = $.valHooks, hooks, placeholder;
        /*检查是否支持*/
		if (isInputSupported && isTextareaSupported) {
			placeholder = prototype.placeholder = function() {
				return this;
			};
			placeholder.input = placeholder.textarea = true;
		} else {
			/*不支持placeholder时的实现*/
			placeholder = prototype.placeholder = function() {
				var $this = this;
				$this.filter(( isInputSupported ? 'textarea' : ':input') + '[placeholder]').not('.placeholder').on({
					'focus.placeholder' : clearPlaceholder,
					'blur.placeholder' : setPlaceholder,
					'init.placeholder':setPlaceholder
				}).data('placeholder-enabled', true).trigger('init.placeholder');
				return $this;
			};

			placeholder.input = isInputSupported;
			placeholder.textarea = isTextareaSupported;

			hooks = {
				'get' : function(element) {
					var $element = $(element);
					return $element.data('placeholder-enabled') && $element.hasClass('placeholder') ? '' : element.value;
				},
				'set' : function(element, value) {
					var $element = $(element);
					if (!$element.data('placeholder-enabled')) {
						return element.value = value;
					}
					if (value == '') {
						element.value = value;
						// Issue #56: Setting the placeholder causes problems if the element continues to have focus.
						if (element != document.activeElement) {
							// We can't use `triggerHandler` here because of dummy text/password inputs :(
							setPlaceholder.call(element);
						}
					} else if ($element.hasClass('placeholder')) {
						clearPlaceholder.call(element, true, value) || (element.value = value);
					} else {
						element.value = value;
					}
					// `set` can not return `undefined`; see http://jsapi.info/jquery/1.7.1/val#L2363
					return $element;
				}
			};

			isInputSupported || (valHooks.input = hooks);
			isTextareaSupported || (valHooks.textarea = hooks);

		}

		function args(elem) {
			// Return an object of element attributes
			var newAttrs = {}, rinlinejQuery = /^jQuery\d+$/;
			$.each(elem.attributes, function(i, attr) {
				if (attr.specified && !rinlinejQuery.test(attr.name)) {
					newAttrs[attr.name] = attr.value;
				}
			});
			return newAttrs;
		}

		function clearPlaceholder(event, value) {
			var input = this, $input = $(input);
			if (input.value == $input.attr('placeholder') && $input.hasClass('placeholder')) {
				if ($input.data('placeholder-password')) {
					$input = $input.hide().next().show().attr('id', $input.removeAttr('id').data('placeholder-id'));
					// If `clearPlaceholder` was called from `$.valHooks.input.set`
					if (event === true) {
						return $input[0].value = value;
					}
					$input.focus();
				} else {
					input.value = '';
					$input.removeClass('placeholder');
					input == document.activeElement && input.select();
				}
			}
		}

		function setPlaceholder() {
			var $replacement, input = this, $input = $(input), $origInput = $input, id = this.id;
			if (input.value == '') {
				if (input.type == 'password') {
					if (!$input.data('placeholder-textinput')) {
						try {
							$replacement = $input.clone().attr({
								'type' : 'text'
							});
						} catch(e) {
							$replacement = $('<input>').attr($.extend(args(this), {
								'type' : 'text'
							}));
						}
						$replacement.removeAttr('name').data({
							'placeholder-password' : true,
							'placeholder-id' : id
						}).on('focus.placeholder', clearPlaceholder);
						$input.data({
							'placeholder-textinput' : $replacement,
							'placeholder-id' : id
						}).before($replacement);
					}
					$input = $input.removeAttr('id').hide().prev().attr('id', id).show();
					// Note: `$input[0] != input` now!
				}
				$input.addClass('placeholder');
				$input[0].value = $input.attr('placeholder');
			} else {
				$input.removeClass('placeholder');
			}
		}

	}(this, document, jQuery)); (function($) {

	var delimiter = new Array();
	var tags_callbacks = new Array();
	$.fn.doAutosize = function(o) {
		var minWidth = $(this).data('minwidth'), maxWidth = $(this).data('maxwidth'), val = '', input = $(this), testSubject = $('#' + $(this).data('tester_id'));

		if (val === ( val = input.val())) {
			return;
		}

		// Enter new content into testSubject
		var escaped = val.replace(/&/g, '&amp;').replace(/\s/g, ' ').replace(/</g, '&lt;').replace(/>/g, '&gt;');
		testSubject.html(escaped);
		// Calculate new width + whether to change
		var testerWidth = testSubject.width(), newWidth = (testerWidth + o.comfortZone) >= minWidth ? testerWidth + o.comfortZone : minWidth, currentWidth = input.width(), isValidWidthChange = (newWidth < currentWidth && newWidth >= minWidth) || (newWidth > minWidth && newWidth < maxWidth);

		// Animate width
		if (isValidWidthChange) {
			input.width(newWidth);
		}

	};
	$.fn.resetAutosize = function(options) {
		// alert(JSON.stringify(options));
		var minWidth = $(this).data('minwidth') || options.minInputWidth || $(this).width(), maxWidth = $(this).data('maxwidth') || options.maxInputWidth || ($(this).closest('.input-tags').width() - options.inputPadding), val = '', input = $(this), testSubject = $('<tester/>').css({
			position : 'absolute',
			top : -9999,
			left : -9999,
			width : 'auto',
			fontSize : input.css('fontSize'),
			fontFamily : input.css('fontFamily'),
			fontWeight : input.css('fontWeight'),
			letterSpacing : input.css('letterSpacing'),
			whiteSpace : 'nowrap'
		}), testerId = $(this).attr('id') + '_autosize_tester';
		if (!$('#' + testerId).length > 0) {
			testSubject.attr('id', testerId);
			testSubject.appendTo('body');
		}

		input.data('minwidth', minWidth);
		input.data('maxwidth', maxWidth);
		input.data('tester_id', testerId);
		input.css('width', minWidth);
	};

	$.fn.addTag = function(value, options) {
		options = jQuery.extend({
			focus : false,
			callback : true
		}, options);
		this.each(function() {
			var id = $(this).attr('id');
			var tagslist = $(this).val().split(delimiter[id]);
			if (tagslist[0] == '') {
				tagslist = new Array();
			}
			value = jQuery.trim(value);
			if (options.unique) {
				var skipTag = $(this).tagExist(value);
				if (skipTag == true) {
					//Marks fake input as not_valid to let styling it
					$('#' + id + '_tag').addClass('invalid');
				}
			} else {
				var skipTag = false;
			}

			if (value != '' && skipTag != true) {
				$('<span>').addClass('tag').append($('<span>').text(value).append('&nbsp;&nbsp;'), $('<a>', {
					href : '#',
					title : '删除',
					text : '×'
				}).click(function() {
					return $('#' + id).removeTag(escape(value));
				})).insertBefore('#' + id + '_addTag');

				tagslist.push(value);

				$('#' + id + '_tag').val('');
				if (options.focus) {
					$('#' + id + '_tag').focus();
				} else {
					$('#' + id + '_tag').blur();
				}

				$.fn.tagsInput.updateTagsField(this, tagslist);

				if (options.callback && tags_callbacks[id] && tags_callbacks[id]['onAddTag']) {
					var f = tags_callbacks[id]['onAddTag'];
					f.call(this, value);
				}
				if (tags_callbacks[id] && tags_callbacks[id]['onChange']) {
					var i = tagslist.length;
					var f = tags_callbacks[id]['onChange'];
					f.call(this, $(this), tagslist[i - 1]);
				}
			}

		});

		return false;
	};

	$.fn.removeTag = function(value) {
		value = unescape(value);
		this.each(function() {
			var id = $(this).attr('id');
			var old = $(this).val().split(delimiter[id]);
			$('#' + id + '_tagsinput .tag').remove();
			str = '';
			for ( i = 0; i < old.length; i++) {
				if (old[i] != value) {
					str = str + delimiter[id] + old[i];
				}
			}
			$.fn.tagsInput.importTags(this, str);
			if (tags_callbacks[id] && tags_callbacks[id]['onRemoveTag']) {
				var f = tags_callbacks[id]['onRemoveTag'];
				f.call(this, value);
			}
		});

		return false;
	};

	$.fn.tagExist = function(val) {
		var id = $(this).attr('id');
		var tagslist = $(this).val().split(delimiter[id]);
		return (jQuery.inArray(val, tagslist) >= 0);
		//true when tag exists, false when not
	};

	// clear all existing tags and import new ones from a string
	$.fn.importTags = function(str) {
		id = $(this).attr('id');
		$('#' + id + '_tagsinput .tag').remove();
		$.fn.tagsInput.importTags(this, str);
	}

	$.fn.tagsInput = function(options) {
		var settings = jQuery.extend({
			interactive : true,
			defaultText : '添加标签，多个标签请回车输入，最多5个',
			minChars : 0,
			maxlength : 15, /*标签的最大长度*/
			size : 5, /*最多填写的标签个数*/
			width : '300px',
			autocomplete : {
				selectFirst : false
			},
			'hide' : true,
			'delimiter' : ',',
			'unique' : true,
			removeWithBackspace : true,
			placeholderColor : '#666666',
			autosize : true,
			comfortZone : 20,
			inputPadding : 6 * 2
		}, options);

		this.each(function() {
			var self = this, $this = $(self);
			var maxlength = $this.attr("maxlength") || settings.maxlength;
			var size = $this.attr("maxlength") || settings.size;
			if (settings.hide) {
				$this.hide();
			}
			var id = $this.attr('id');
			if (!id || delimiter[id]) {
				id = $this.attr('id', 'tags' + new Date().getTime()).attr('id');
			}

			var data = jQuery.extend({
				pid : id,
				real_input : '#' + id,
				holder : '#' + id + '_tagsinput',
				input_wrapper : '#' + id + '_addTag',
				fake_input : '#' + id + '_tag'
			}, settings);
			delimiter[id] = data.delimiter;
			if (settings.onAddTag || settings.onRemoveTag || settings.onChange) {
				tags_callbacks[id] = new Array();
				tags_callbacks[id]['onAddTag'] = settings.onAddTag;
				tags_callbacks[id]['onRemoveTag'] = settings.onRemoveTag;
				tags_callbacks[id]['onChange'] = settings.onChange;
			}

			var markup = '<div id="' + id + '_tagsinput" class="input-tags"><div id="' + id + '_addTag">';
			if (settings.interactive) {
				markup = markup + '<input id="' + id + '_tag" value="" data-default="' + settings.defaultText + '" maxlength="' + maxlength + '"/>';
			}
			markup = markup + '</div></div>';
			$(markup).insertAfter(this);
			$(data.holder).css('width', settings.width);
			if ($(data.real_input).val() != '') {
				$.fn.tagsInput.importTags($(data.real_input), $(data.real_input).val());
			}
			if (settings.interactive) {
				$(data.fake_input).val($(data.fake_input).attr('data-default'));
				$(data.fake_input).css('color', settings.placeholderColor);
				$(data.fake_input).resetAutosize(settings);

				$(data.holder).bind('click', data, function(event) {
					$(event.data.fake_input).focus();
				});

				$(data.fake_input).bind('focus', data, function(event) {
					if ($(event.data.fake_input).val() == $(event.data.fake_input).attr('data-default')) {
						$(event.data.fake_input).val('');
					}
					$(event.data.fake_input).css('color', '#000000');
				});

				if (settings.autocomplete_url != undefined) {
					autocomplete_options = {
						source : settings.autocomplete_url
					};
					for (attrname in settings.autocomplete) {
						autocomplete_options[attrname] = settings.autocomplete[attrname];
					}

					if (jQuery.Autocompleter !== undefined) {
						$(data.fake_input).autocomplete(settings.autocomplete_url, settings.autocomplete);
						$(data.fake_input).bind('result', data, function(event, data, formatted) {
							if (data) {
								$('#' + id).addTag(data[0] + "", {
									focus : true,
									unique : (settings.unique)
								});
							}
						});
					} else if (jQuery.ui.autocomplete !== undefined) {
						$(data.fake_input).autocomplete(autocomplete_options);
						$(data.fake_input).bind('autocompleteselect', data, function(event, ui) {
							$(event.data.real_input).addTag(ui.item.value, {
								focus : true,
								unique : (settings.unique)
							});
							return false;
						});
					}

				} else {
					// if a user tabs out of the field, create a new tag
					// this is only available if autocomplete is not used.
					$(data.fake_input).bind('blur', data, function(event) {
						var d = $(this).attr('data-default');
						var ri=$(event.data.real_input);
						var rmax=ri.val().split(settings.delimiter).length<settings.size;
						if (rmax&&$(event.data.fake_input).val() != '' && $(event.data.fake_input).val() != d) {
							if ((event.data.minChars <= $(event.data.fake_input).val().length) && (!event.data.maxChars || (event.data.maxChars >= $(event.data.fake_input).val().length)))
								$(event.data.real_input).addTag($(event.data.fake_input).val(), {
									focus : true,
									unique : (settings.unique)
								});
						} else {
							$(event.data.fake_input).val($(event.data.fake_input).attr('data-default'));
							$(event.data.fake_input).css('color', settings.placeholderColor);
						}
						return false;
					});

				}
				// if user types a comma, create a new tag
				$(data.fake_input).bind('keypress', data, function(event) {
					if (event.which == event.data.delimiter.charCodeAt(0) || event.which == 13) {
						event.preventDefault();
						var ri=$(event.data.real_input);
						var rmax=ri.val().split(settings.delimiter).length<settings.size;
						if (rmax&&(event.data.minChars <= $(event.data.fake_input).val().length) && (!event.data.maxChars || (event.data.maxChars >= $(event.data.fake_input).val().length)))
							$(event.data.real_input).addTag($(event.data.fake_input).val(), {
								focus : true,
								unique : (settings.unique)					
							});
						$(event.data.fake_input).resetAutosize(settings);
						return false;
					} else if (event.data.autosize) {
						$(event.data.fake_input).doAutosize(settings);

					}
				});
				//Delete last tag on backspace
				data.removeWithBackspace && $(data.fake_input).bind('keydown', function(event) {
					if (event.keyCode == 8 && $(this).val() == '') {
						event.preventDefault();
						var last_tag = $(this).closest('.input-tags').find('.tag:last').text();
						var id = $(this).attr('id').replace(/_tag$/, '');
						last_tag = last_tag.replace(/[\s]+x$/, '');
						$('#' + id).removeTag(escape(last_tag));
						$(this).trigger('focus');
					}
				});
				$(data.fake_input).blur();

				//Removes the not_valid class when user changes the value of the fake input
				if (data.unique) {
					$(data.fake_input).keydown(function(event) {
						if (event.keyCode == 8 || String.fromCharCode(event.which).match(/\w+|[áéíóúÁÉÍÓÚñÑ,/]+/)) {
							$(this).removeClass('invalid');
						}
					});
				}
			} // if settings.interactive
		});

		return this;

	};

	$.fn.tagsInput.updateTagsField = function(obj, tagslist) {
		var id = $(obj).attr('id');
		$(obj).val(tagslist.join(delimiter[id]));
	};

	$.fn.tagsInput.importTags = function(obj, val) {
		$(obj).val('');
		var id = $(obj).attr('id');
		var tags = val.split(delimiter[id]);
		for ( i = 0; i < tags.length; i++) {
			$(obj).addTag(tags[i], {
				focus : false,
				callback : false
			});
		}
		if (tags_callbacks[id] && tags_callbacks[id]['onChange']) {
			var f = tags_callbacks[id]['onChange'];
			f.call(obj, obj, tags[i]);
		}
	};
})(jQuery);

!(function($) {

	Combobox = function(c, o) {
		var $this = $(c);
		o = $.extend({
			source : $this.data("source"),
			result : $this.data("result"),
			itemValue : $this.data("item-value"),
			itemText : $this.data("item-text"),
			itemClick : $this.attr("item-click"),
			min : ($this.attr("min") || 0) * 1,
			max : ($this.attr("max") || 65535) * 1,
			checkbox : $this.attr("checkbox"),
		    disabled:$this.attr("disabled"),
			readonly:$this.attr("readonly"),
			mode : "box", // "box|dropdown"
			children : ".item", //列表默认子元素的列表,child上必须有value和text属性,如果不是，请覆盖item方法
			item : function(item) {
				return {
					value : item.data("value"),
					text : item.data("text")
				};
			}
		}, o);
		this.combobox = c;
		this.$this = $this;
		this.options = o;
		this.init();
		return this;
	}
	Combobox.prototype = {
		init : function() {
			var me = this;
			var $this = me.$this;
			me.defaultValue = Q.isEmpty(me.$this.data("value"), "");
			var max = me.options.max;
			if (me.options.mode = "box") {
				me.box();
			}
			/*在检测值的前后添加分号方便索引检测默认只*/
            var v_checker=","+me.defaultValue+",";
			$this.find(me.options.children).each(function() {
				if (me.options.checkbox) {
					this.checkbox = $('<label class="checkbox"></label>');
					$(this).prepend(this.checkbox);
				}

				if (v_checker.indexOf(","+$(this).data("value")+",")!=-1) {
					$(this).addClass("active");
				}
				/*最多检查*/
				$(this).click(function() {
					if(me.options.readonly||me.options.disabled){
						return;
					}
					var active = $this.find(me.options.children + ".active");
					var ss = active.length;
					if (max == 1) {
						active.removeClass("active");
						me.toggleChecked(this);
					} else if (ss < max || $(this).hasClass("active")) {
						me.toggleChecked(this);
					} else {
						$.alert.warn("对不起，您最多只能选择" + max + "项！")
					}
					/*用户自定义的单机事件*/
					if (me.options.itemClick) {
						var _fn = me.options.itemClick.toFunction();
						_fn.apply(this, [this]);
					}
				});

			});
			/*如果是单选，自动默认选第一个*/
			if (Q.isEmpty(me.defaultValue)&&max == 1) {
				$this.find(me.options.children + ":first").click();
			}
		},
		box : function() {
			var me = this;
			me.name = me.$this.data("name");
			me.id = me.$this.data("id");
			me.target = $('<input type="hidden" name="' + me.name + '" id="' + (me.id ? me.id : "cx" + $.now()) + '" value="' + me.defaultValue + '"/>')
			//自动生成默认域
			me.$this.append(me.target);

			/*是否是使用ajax方式加载的数据*/
			if (me.options.source) {
				var result = me.options.result;
				var itemValue = me.options.itemValue;
				var itemText = me.options.itemText;
				$.getSynch(me.options.source, function(data) {
					if (result) {
						data = data[result];
					}
					$(data).each(function() {
						me.$this.prepend('<span class="item" data-value="' + this[itemValue] + '" data-text="' + this[itemText] + '"> ' + this[itemText] + '</span> ');
					});
				});
			}
		},
		toggleChecked : function(item) {
			var me = this;
			var $this = me.$this;
			$(item).toggleClass('active');
			if (me.options.checkbox) {
				item.checkbox.toggleClass("checked");
			}
			/*绑定目标值*/
			var text = [];
			var value = [];
			var v = [];
			$this.find(me.options.children + ".active").each(function(index) {
				var vi = me.options.item($(this));
				v.push(vi);
				text.push(vi.text);
				value.push(vi.value);
			});
			me.target.data("value", v);
			me.target.data("text", text.join(","));
			me.target.val(value.join(","));
		}
	}

	$.fn.combobox = function(o) {
		return this.each(function() {
			if (!this.combobox)
				this.combobox = new Combobox(this, o);
			return this;
		});
	}
})(jQuery);
!(function($) {
	Stepper=function(s){
	    this.self=s;
		this.$this=$(s);
		this.init();
	}
	
	Stepper.prototype={
	    init:function(){
			var me=this;
			me.min=Number(me.$this.attr("min")||-Number.MAX_VALUE);
			me.max=Number(me.$this.attr("max")||Number.MAX_VALUE);
			var step=me.$this.attr("step")||"1",si=step.indexOf(".");
			me.step=Number(step);
			/*step需要对小数进行处理*/
			if(si!=-1){
				step=step.substr(si+1);
				me.fixed=step.length;
			}
			me.$this.wrap('<div class="stepper"></div>');	
			me.root=me.$this.parent();
			me.root.width(me.$this.outerWidth());
			me.root.append('<i class="step-up" data-type="up"></i><i class="step-down" data-type="down"></i>');
			me.root.find("i").on("click.stepper",function(){
				me[$(this).data("type")]();
			});
			/*keyboard*/
			me.$this.on("keydown.stepper",function(event) {
			if(event.which != 8 && event.which != 38 && event.which != 40 && !(event.which >= 48 && event.which <= 57) && !(event.which >= 96 && event.which <= 105)) {
				return false;
			}
			if(event.which == 38) {
				me.up();
			}
			if(event.which == 40) {
                me.down();
			}
		});
			
		},up:function(){
			var me=this;
			var v=Number(me.$this.val()||0);
			if((v+me.step)<=me.max){
				me.val(v+me.step);
			}
		},down:function(){
			var me=this;
			var v=Number(me.$this.val()||0);
			if((v-me.step)>=me.min){
				me.val(v-me.step);
			}
		},val:function(v){
			if(this.fixed){
				v=v.toFixed(this.fixed);
			}
			this.$this.val(v)
		}
	}
	
	$.fn.stepper = function() {
		
		return this.each(function(){
			if(!this.stepper){
				this.stepper=new Stepper(this);
			}
			return this;
		});
	}
})(jQuery);!(function($){
	Raty=function(r){
		this.self=r;
		this.$this=$(r);
		this.init();
	}
	
	Raty.prototype={
		init:function(){
			var me=this;
			me.number=Number(me.$this.attr("number")||5);
			me.scoretip=me.$this.data("scoretip");
			var score=me.$this.attr("score")||"1",si=score.indexOf(".");
			me.score=Number(score);
			me.index=parseInt((this.$this.val()||0)/me.score);
			/*score需要对小数进行处理,精确位和score精度一样*/
			if(si!=-1){
				score=score.substr(si+1);
				me.fixed=score.length;
			}
			
			me.$this.wrap('<div class="raty"></div>');	
			me.root=me.$this.parent();
			me.star=$('<div class="star"></div>');
			me.root.append(me.star);
			//首个无法取消，设定较小的宽度方便取消
			me.star.append('<i class="head"></i>');
			var s=[];		
			$(me.scoretip).each(function(index, element) {
				var score=this.score;
				for(var i=0;i<this.score.length;i++){
					s[this.score[i]]=this.text;
				}
			});
			
			for(var i=0;i<me.number;i++){
				me.star.append('<i title="'+Q.isEmpty(s[i+1],"")+'"></i>');
			}
			me.star.append("<span></span>")
			me.star.i=me.star.find("i");
			me.star.i.on("mouseover.raty",function(event){
				me.on($(this).index());
			}).on("click.raty",function(event){
				me.on($(this).index(),true);
			}).on("mouseout.raty",function(event){
				me.on(me.index);
			});
			
			if(me.index){
				me.on(me.index,true);
			}
			/*只读状态*/
			me.readonly=me.$this.attr("readonly");
		},on:function(index,selected){
			var me=this;
			//only read
			if(me.readonly){
				return;
			}			
			me.star.i.removeClass("active");
			me.star.find("span").text(me.star.find("i:lt("+(index+1)+")").addClass("active").last().attr("title"));
			if(selected){
				me.index=index;
				var v=index*me.score;
				if(me.fixed){
					v=v.toFixed(me.fixed);
				}
				me.$this.val(v);
			}
		}
	};
	
	$.fn.raty=function(){
		return this.each(function(){
			if(!this.raty){
				this.raty=new Raty(this);
			}
			return this;
		});
	}
})(jQuery);/*
 *  input{
 type=hidden|text|password|button|submit|reset|number|int|float|money|date|datatime|tag|color|drop|city
 mask=""
 partten=""
 *   }
 *
 */
;(function($) {
	function FormRequest() {
		this.parameters = [];
		/**
		 * 设置参数值，原来的值会被覆盖
		 * _value==null ||_value==undefined删除参数
		 */
		this.setParameter = function(_name, _value) {
			for (var i = 0; i < this.parameters.length; i++) {
				if (this.parameters[i].name == _name) {
					if (!_value || _value == undefined) {
						this.parameters.splice(i, 1);
					} else {
						this.parameters[i].value = _value;
					}
				}
			}
			return this;
		};
		this.set = this.setParameter;
		/**
		 * 添加一个新的参数值，即时原来存在也不会被覆盖
		 */
		this.addParameter = function(_name, _value) {
			this.parameters.push({
				name : _name,
				value : _value
			});
			return this;
		};
		this.add = this.addParameter;
		/*从容器中取出数据，多个值以逗号分割*/
		this.getParameter = function(_name) {
			var p = [];
			for (var i = 0; i < this.parameters.length; i++) {
				if (this.parameters[i].name == _name) {
					p.push(this.parameters[i].value);
				}
			}
			return p.toString();
		};
		this.get = this.getParameter;
		/*打印数据*/
		this.toString = function() {
			var string = "";
			for (var i = 0; i < this.parameters.length; i++) {
				string = string.append(this.parameters[i].name).append(":").append(this.getParameter(this.parameters[i].name)).append("\r\n");
			}
			return string;
		}
	};

	/**
	 * 文件上传返回结果
	 */
	function cb(e, io, callback) {
		var doc = io.contentWindow ? io.contentWindow.document : io.contentDocument ? io.contentDocument : io.document;
		var docRoot = doc.body ? doc.body : doc.documentElement;
		var reponseText;
		/*只返回json类型的数据*/
		var pre = doc.getElementsByTagName('pre')[0];
		var b = doc.getElementsByTagName('body')[0];
		if (pre) {
			reponseText = pre.textContent;
		} else if (b) {
			reponseText = b.innerHTML;
		} else {
			reponseText = docRoot ? docRoot.innerHTML : null;
		}
		try {
			callback(eval("(" + reponseText + ")"));
		} catch(e) {
		}
	}


	$.form = {
		plugins : {
			tag : function($el) {
				$el.tagsInput();
			},
			editor : function($el) {

			},
			date : function($el) {
				$el.datepicker();
			},color:function($el){
				$el.colorpicker();
			},raty:function($el){
				$el.raty();
			}
		},
		install : function($el, type) {
			if (!type) {
				return;
			}
			var func = $.form.plugins[type];
			if (func)
				func($el);
		},
		/*支持跨域表单提交*/
		submit : function(action, data, callback) {
			var form = [];
			target = target ? (" target='" + target + "'") : "";
			form.push("<div style='display:none' ><form action='" + action + "' method='post' >");
			data = data || {};
			for (var i in data) {
				form.push("<input type='hidden' name='" + i + "'  value='" + data[i] + "'/>")
			}
			form.push("</form><iframe src='about:blank'' id=''></iframe></div>");
			var $form = $(form.join(""));
			$(document.body).append($form);
			$form.submit();
		}
	}

	/*扩展方法*/
	$.fn.extend({
		/*根据表达式返回元素值，默认为JSON格式，元素的值均以name为标记为主*/
		value : function(value, clear) {
			/*设定值的时候同一名词的值一定按顺序设置*/
			if (value) {
				var index = {};
				if ($.isPlainObject(value)) {
					/*将value值转成数组*/
					for (var s in value) {
						var self = value[s];
						if (!$.isArray[self]) {
							value[s] = [self];
						}
					}
				} else if ($.isArray(value)) {
					var o = {};
					o[this[0].name] = value;
					value = o;
				} else {
					var o = {};
					o[this[0].name] = [value];
					value = o;
				}
				return this.each(function() {
					var $this = $(this), n = this.name, tag = this.tagName.toLowerCase();
					var v = value[n];
					if (n) {
						if (tag == "input") {
							var type = this.type;
							switch (type) {
								case "radio":
								case "checkbox":
									if ($.inArray($this.val(), v) != -1) {
										$this.attr("checked", "checked");
									} else if (clear) {
										$this.attr("checked", false);
									}
									break;
								default:
									index[n] = (index[n] == undefined ? -1 : index[n]) + 1;
									if (index[n] < v.length) {
										var s = v[index[n]];
										if (s != undefined)
											$(this).val(s);
										else if (clear) {
											$(this).val("");
										}
									} else if (clear) {
										$(this).val("");
									}
							}
						} else {
							index[n] = (index[n] == undefined ? -1 : index[n]) + 1;
							if (index[n] < v.length) {
								var s = v[index[n]];
								if (s != undefined)
									$(this).val(s);
								else if (clear) {
									$(this).val("");
								}
							} else if (clear) {
								$(this).val("");
							}
						}
					}
				});
			} else {
				var v = {}, oname;
				this.each(function() {
					var $this = $(this), n = this.name, tag = this.tagName.toLowerCase();
					oname = n;
					if (n) {
						if (tag == "input") {
							var type = this.type;
							switch (type) {
								case "radio":
								case "checkbox":
									if ($this.attr("checked")) {
										(v[n] = (v[n] || [])).push($this.val());
									}
									break;
								default:
									(v[n] = (v[n] || [])).push($this.val());
							}
						} else {
							//select,textarea
							(v[n] = (v[n] || [])).push($this.val());
						}
					}
				});
				/*所有的返回参数值均为数组*/
				trace("value:" + JSON.stringify(v));
				return v;
			}
		},
		/*专门提供给form获取值和值绑定或者区域性值获取*/
		values : function(value, clear) {
			return $("input,select,textarea", this).not(":submit, :reset, :image").value(value, clear);
		},
		/*使用ajax方式提交
		 * options:{url:"",
		 crossDomain:"",
		 data:{}  将form以外的值加入
		 success:function(){} 调用成功的方法
		 }
		 *  type(options)==function 代表只有一个执行成功的方法。
		 */
		postForm : function(option) {
			if (!option) {
				option = function(data) {
					$.alert.show(data);
				}
			}

			var options;
			if ($.isFunction(option)) {
				options = {
					success : option
				};
			} else {
				options = option;
			}
			var $form = $(this);
			var form = this[0];
			var request = new FormRequest();
			/*数据正在提交中,防止重复提交数据*/
			if ($form.data("posting")) {
				trace("Form data posting");
				return;
			}
			/*
			 * 提交数据之前进行验证
			 */
			if ($.form.validate($form) === false) {
				trace("Validate form failure!");
				return;
			}

			/*修改提交后的方法*/
			var callback = function(data) {
				trace("callback ");
				/*清除表单状态*/
				$form.data("change", false);
				/*清除数据提交状态*/
				$form.data("posting", false);
				/*检查返回结果*/
				if (data && data.errors) {
					for (var e in data.errors) {
						$.form.show($("#" + e), {
							valid : false,
							message : data.errors[e]
						});
					}
					return;
				}
				options.success(data);
			};
			/* 处理数据之前*/
			if (options.before && options.before(this, options) === false) {
				return;
			}

			/*HTML4 AJAX文件上传*/
			var mp = 'multipart/form-data';
			var multipart = ($form.attr('enctype') == mp || $form.attr('encoding') == mp);
			if (multipart) {
				$form.data("posting", true);
				/*加入ajax的检查*/
				$form.append('<input type="hidden" name="ajax.token" value="true" />')
				var id = $form.attr("id") + "_Frame" + $.now();
				var $io = $("#" + id);
				var io;
				if ($("#" + id).length == 0) {
					$io = $('<iframe  name="' + id + '" src="javascript:false" />');
					io = $io[0];
					$io.css({
						position : 'absolute',
						top : '-1000px',
						left : '-1000px',
						display : "none"
					});
					$io.appendTo('body');
					io.attachEvent ? io.attachEvent('onload', function(e) {
						cb(e, io, callback)
					}) : io.addEventListener('load', function(e) {
						cb(e, io, callback)
					}, false);
					$form.attr("target", id);
				}
				/*ajax包含有头信息，常规form提交却没有，当表单只有一个input元素时回车会自动提交数据，不得不用onsubmit=return false，使用后form却无法正常提交了*/
				form.submit();
				return;
			}

			var els = form.elements;
			var el, n, tag, type;
			for (var i = 0; i < els.length; i++) {
				el = els[i];
				n = el.name;
				tag = el.tagName.toLowerCase();
				if (n) {
					if (tag == "input") {
						type = (el.type || "text").toLowerCase();
						switch (type) {
							case "radio":
							case "checkbox":
								if ($(el).attr("checked")) {
									request.add(n, $(el).val());
								}
								break;
							default:
								request.add(n, $(el).val());
						}
					} else {
						request.add(n, $(el).val());
					}
				}
			}

			/* 处理数据之后 */
			if (options.after && options.after(request) === false) {
				return;
			}

			/*更新版本，提交数据前检查，无意义，将所有数据放入容器后再处理，将方法修改为*/
			if (options.handleForm && options.handleForm(request) === false) {
				return;
			}

			trace("post form:" + $form.attr("action") + ":param:\r\n" + request);
			//防止数据重复提交
			$form.data("posting", true);
			$.post($form.attr("action"), request.parameters, callback);
		},
		bindForm : function(data, options) {
			if (!options) {
				options = {};
			}
			if (options.before)
				options.before(data);
			$(this).values(data);
			if (options.after) {
				options.after();
			}
		},
		resetForm : function() {
		}
	});

	/*创建一个form，用来提交安全数据,只使用post方式提交数据,不使用文件流*/
	$.formSubmit = function(action, data, target) {
		var form = [];
		target = target ? (" target='" + target + "'") : "";
		form.push("<form action='" + action + "' method='post' style='display:none'  " + target + " >");
		data = data || {};
		for (var i in data) {
			form.push("<input type='hidden' name='" + i + "'  value='" + data[i] + "'/>")
		}
		form.push("</form>");
		var $form = $(form.join(""));
		$(document.body).append($form);
		$form.submit();
	}
})(jQuery);
/**
 * 验证的主要范围包括：
 *    长度、值范围
 * 验证的事件主要包括：
 *    focus，blur，change,post,keyup,keydown,changing
 * 消息模板：
 *    this is {message}
 *
 *  先验证
 *  1、mask参数指定为先先验证，限制用户的输入，先验证的内容不需要提示信息
 *  后验证：分为前台验证和后台验证
 * 单个验证域的编写必须做好以下几点：
 *       mask:必须有
 *       maxlength:必须有
 *
 *     readonly和disabled的数据不需要再进行验证(主要指编辑状态)
 *
 */
;(function($) {
	var Validation = {};
	Validation = $.extend({}, Q.r, {
		getMessage : function(json) {
			if ( typeof json == "string") {
				if (json.startsWith("message.")) {
					return MessageBundle.get(window.navigator.language, json.substring("message.".length));
				}
				return json;
			} else {
				return json.message;
			}
		},
		xss : function(str) {

		},
		size : function(str, json) {
			/*size:{min:1,max:50,message:"请输入50个以内的字符",template:"您已输入#size#个字符",target:"name",event:true}*/
			var min = json.min || 0;
			var max = json.max || 65535;
			if (str.size(json.chinese) >= min && str.size(json.chinese) <= max) {
				return true;
			}
			return false;
		},
		sizeEvent : function(target, json) {
			var $target = $(target);
			var max = json.max || 65535;
			var template = json.template || "您已输入#size#" + (max == 65535 ? "" : "/" + max) + "个字符";
			$target.attr("maxlength", max);
			$target.on("keydown.valid", function(event) {
				if ($target.attr("lock") === "true" && ($.inArray(event.which, [8, 37, 39, 46]) == -1)) {
					event.preventDefault();
				}
			}).on("keyup.valid", function(event) {
				var len = $target.val().size(json.chinese);
				if (len >= max) {
					$target.val($target.val().substring(0, max));
					len = max;
					/*锁定，禁止输入*/
					$target.attr("lock", "true");
				} else {
					$target.removeAttr("lock");
				}
				/*maxlength不显示字数统计*/
				if (!json.maxlength) {
					$.form.show(target, {
						message : template.replaceAll("#size#", len)
					});
				}
			}).on("paste.valid", function() {
				var len = $target.val().size(json.chinese);
				var len2 = $target.val().size();
				if (len > max) {
					$target.val($target.val().substring(0, max));
				}
			});
		},
		password_strong : function(str) {
			if (str.length <= 4)
				return 0;
			var modes = 0;
			for (var i = 0; i < str.length; i++) {
				modes |= mode(str.charCodeAt(i));
			}
			var strong = 0;
			for ( i = 0; i < 4; i++) {
				if (modes & 1)
					strong++;
				modes >>>= 1;
			}
			/*密码强度至少要大于1*/
			return strong;

			/* 密码字符检查 */
			function mode(ch) {
				if (ch >= 48 && ch <= 57)
					return 1;
				if (ch >= 65 && ch <= 90)
					return 2;
				if (ch >= 97 && ch <= 122)
					return 4;
				else
					return 8;
			}

		},
		password : function(value, json) {
			var mode = Validation.password_strong(value);
			var strong = json.strong || 0;
			return mode >= strong;
		},
		passwordEvent : function(target, json) {
			var $target = $(target);
			$target.on("keyup.valid", function() {
				/* 密码强度检查 */
				var mode = Validation.password_strong($target.val());
				var message = "";
				switch (mode) {
					case 0 :
					case 1 :
						message = "<b class='password-strong'>弱</b><b class='password-weak'>&nbsp;</b><b class='password-weak'>&nbsp;</b>";
						break;
					case 2 :
						message = "<b class='password-strong'>&nbsp;</b><b class='password-strong'>中</b><b class='password-weak'>&nbsp;</b>";
						break;
					case 3 :
						message = "<b class='password-strong'>&nbsp;</b><b class='password-strong'>&nbsp;</b><b class='password-strong'>强</b>";
						break;
				}
				$.form.show(target, {
					message : message
				});
			});
		},
		equalsTo : function(source, json) {
			// var $source=$(source),$target=$(json.target);
			//if($source.val().equals($target.val())){

			//}
		},
		number : function(value, json) {

		}
	});

	/**
	 *  单个验证器
	 */
	var Validator = function($form, v, options) {
		options = $.extend({
			errorClass : "error",
			validClass : "valid",
			errorElement : "em",
			focus : true,
			_keydown : function(e) {

			},
			_keypress : function(e) {

			},
			_keyup : function(e) {

			},
			_paste : function(e) {

			},
			_change : function(e) {
				trace("change");
				/*如果做到修改之后才验证，而且验证过的数据如果没有修改就不在验证了，第一次验证，blur为初始验证，验证完毕后改变changed的状态*/
				if ($(this).data("changed") != null || $(this).is(":hidden")) {
					$(this).data("changed", true).blur();
				}
			},
			_focus : function(e) {
				if (v[this.name]) {
					var info = v[this.name].info;
					if (info) {
						form.show(this, {
							message : info
						});
					}
				}
			},
			_blur : function(e) {
				trace("blur");
				/*第一验证或者改变*/
				if ($(this).data("changed") != false) {
					form.check(this);
				} else {
					form.show(this, $(this).data("message"));
				}
			}
		}, options);

		var form = {
			cache : {}, /*数据缓存*/
			need : {}, /*验证器缓存*/
			group : {}, /*存储需要验证的元素*/
			elements : function(cache) {
				/*一定要检查type=hidden和readonly状态的输入框，常常会有一些空间使用这种方式设置值*/
				return $form.find("input, select, textarea").not(":submit, :reset, :image,[disabled]").not(options.ignore).filter(function() {
					var el = this, $this = $(el), n = el.name, tag = el.tagName.toLowerCase(), t = "";
					if (!n) {
						return false;
					}
					if (v[n]) {
						form.need[n] = true;
					}
					/*old value cache and bind   mask/event */
					if (cache) {
						if (tag == "input") {
							var type = this.type;
							t = type;
							switch (type) {
								case "radio":
								case "checkbox":
									if ($this.attr("checked")) {
										(form.cache[n] = (form.cache[n] || [])).push($this.val());
									}
									break;
								default:
									(form.cache[n] = (form.cache[n] || [])).push($this.val());
							}
						} else {
							//select,textarea
							(form.cache[n] = (form.cache[n] || [])).push($this.val());
							t = tag;
						}
						/*将需要验证的元素放入容器*/
						if (form.need[n]) {
							form.add({
								type : t,
								element : this,
								name : n
							});
						}
					}
					return true;
				});
			},
			/*添加需要验证的元素到验证器*/
			add : function(field) {
				form.group[field.name] = form.group[field.name] || [];
				form.group[field.name].push(field);
			},
			/*根据验证元素，监听事件*/
			on : function() {
				for (var name in form.group) {
					var g = form.group[name];
					var e = g[0];
					var va = v[name];
					if (e.type == "radio" || e.type == "checkbox" || e.type == "select") {
						/*只检查选择个数*/
						var min = va[e.type].min || 0;
						var max = va[e.type].max || 65535;
						var message = va[e.type].message;
						if (e.type != "select") {
							for (var j = 0; j < g.length; j++) {
								var s = g[j].element;
								$(s).on("change.valid", function() {
									var checks = 0, ce = [];
									for (var c = 0; c < g.length; c++) {
										ce.push(g[c].element);
										if ($(g[c].element).attr("checked")) {
											checks++;
										}
									}
									if (checks >= min && checks <= max) {
										$(ce).attr("valid", "true");
										form.show($(s).parent("label:last"), {
											valid : true,
											name : s.name
										});
									} else {
										$(ce).attr("valid", "false");
										form.show($(s).parent("label:last"), {
											valid : false,
											name : s.name,
											message : message
										});
									}
								});
							}
						} else {
							var s = e.element;
							$(s).on("change.valid", function() {
								var checks = $(this).find(':selected').length;
								if (checks >= min && checks <= max) {
									$(this).attr("valid", "true");
									form.show(this, {
										valid : true
									});
								} else {
									$(this).attr("valid", "false");
									form.show(this, {
										valid : false,
										message : message
									});
								}
							});
						}
					} else {
						/*其他类型的验证*/
						for (var j = 0; j < g.length; j++) {
							var s = g[j].element;
							$(s).on("keydown.valid", va, options._keydown).on("keypress.valid", va, options._keypress).on("keyup.valid ", va, options._keyup).on("paste.valid ", va, options._paste).on("focus.valid", options._focus)
							/*在blur前调用*/.on("change.valid", va, options._change).on("blur.valid", va, options._blur);
							for (var exp in va.validators) {
								var vd = va.validators[exp];
								if ($.type(vd) == "object") {
									/*是否有事件监听*/
									if (vd.event) {
										Validation[exp+"Event"](s, vd)
									}
								}
							}

							if (va["maxlength"]) {
								$(s).attr("maxlength", va["maxlength"]);
								/*加入最大长度属性，但是对textarea不起作用,chrome是有效的，这里同样处理，不影响使用*/
								if (e.type == "textarea") {
									Validation.sizeEvent(s, {
										max : $(s).attr("maxlength"),
										maxlength : true
									});
								}
							}

							/*比较相等*/

						}
					}
				}
			},
			/*清楚所有的验证事件*/
			off : function() {
				form.elements().off(".valid");
			},
			/*重置表单*/
			reset : function() {
				$form.values(form.cache);
			},
			check : function(element) {
				var data = v[element.name];
				/*未配置验证器的元素*/
				if (!data) {
					return;
				}
				var validators = data.validators;
				/*标记值为未改变状态*/
				var value = $(element).data("changed", false).val();
				if (isEmpty(value) && !data.required) {
					return;
				}

				if (data.required && isEmpty(value)) {
					form.show(element, {
						valid : false,
						message : data.required,
						cache : true
					});
					return false;
				}

				/*定义的验证器才进行验证,否则只进行基础验证*/
				if (validators) {
					for (var vd in validators) {
						/*
						 * ajax方式验证,也可以自定义方法,返回格式必须为true或者false
						 *该方法可以定义多个，使用ajax方式调用时必须使用同步调用
						 */
						if (vd.startsWith("_")) {
							var df = validators[vd].toFunction()(value);
							if (df && df.valid === false) {
								form.show(element, {
									valid : df.valid,
									message : df.message,
									cache : true
								});
								return false;
							}
						}
						if (vd.startsWith("_")) {
							var df = validators[vd].toFunction()(value);
							if (df && df.valid === false) {
								form.show(element, {
									valid : df.valid,
									message : df.message,
									cache : true
								});
								return false;
							}
						}
						/*库中已定义的方法*/
						else if (Validation[vd] && Validation[vd](value, validators[vd]) === false) {
							form.show(element, {
								valid : false,
								message : Validation.getMessage(validators[vd]),
								cache : true
							});
							return false;
						}
					}
				}

				/*由于存在异步调用，check不返回任何值，show中的值才是调用的最后结果*/
				form.show(element, {
					valid : true,
					cache : true
				});
				return true;
			},
			/*显示信息*/
			show : function(element, message) {
				$.form.show(element, message);
			},
			/*清除所有验证状态及验证信息*/
			clean : function() {

			},
			/*未验证通过时自动聚焦的方法,将第一个元素滚动到屏幕正中央*/
			focus : function() {
				var $first = $form.find("em.warn:first");
				if ($first.length > 0) {
					$(window).scrollTop($first.offset().top - $(window).height() / 2);
				}
			},
			/*检查表单是否发生变化或者检查某个字段是否变化*/
			isChange : function(name) {
				if (name) {
					return !o(form.cache[name]).equals($form.find("[name=" + name + "]").value()[name]);
				}
				return !o(form.cache).equals($form.values());
			},
			validate : function(fn) {
				/*如果表单的值没有发生变化，将不进行检查*/
				if (form.isChange()) {
					/*再次获取element的目的是为了方便动态加入表单的元素*/
					var check = true;
					form.elements().each(function(index) {
						if (!($(this).attr("valid") == "true")) {
							check = check && (form.check(this) != false);
							if (!check) {
								return false;
							}
						}
					});
					/*检查是否有错误*/
					if (!check) {
						form.focus();
					}
					return check;
				}
				/*未发生变化时，根据表单判断是否必须验证*/
				return $form.data("change-validate") == false ? true : false;
			}
		};
		/*生成验证及缓存数据*/
		form.elements(true);
		$form.data("oldvalue", form.cache);
		form.on();
		/*表单重置*/
		$form.find(":reset").click(function() {
			form.reset();
		});

		$form.find(":submit").click(function(event) {
			if (!form.validate()) {
				event.preventDefault();
			}
		});

		this.validate = function(fn) {
			return form.validate(fn);
		}

		this.isChange = function(name) {
			return form.isChange(name);
		}
	};

	/*显示form的验证信息*/
	$.form.show = function(element, message) {
		var $field = $(element), type = "info", name = element.name || message.name;
		if (message.valid != undefined) {
			$field.attr("valid", message.valid);
			type = message.valid ? "success" : "warn";
		}
		message.message = message.message || "";
		var $v = $("#vmessage_" + name);
		if ($v.length == 0) {
			var offset = $field.position();
			$field.parent().append("<em class='help-message " + type + "'  id='vmessage_" + name + "' style='display:none'><i></i>" + message.message + "</em>");
			$v = $("#vmessage_" + name);
		} else {
			$v.removeClass("info warn success").addClass(type).html("<i></i>" + message.message);
		}
		$v.css({
			top : 0,
			right : -$v.outerWidth()
		}).show();

		/*缓存消息，减少重复验证*/
		if (message.cache) {
			$(element).data("message", message);
		}

	}
	$.form.validator = function($form, validator) {
		validator = validator || $form.data("validator");
		if (validator) {
			$form.data("form-validator", new Validator($form, validator));
		} else {
			var url = $form.data("validator-url");
			if (url)
				$.getJSON(url).done(function(data) {
					$.form.validator($form, data);
				}).fail(function(data) {
					trace(data.responseText);
				})
		}
	}
	/*只限于对form验证的使用*/
	$.form.validate = function($form, validator) {
		if (validator) {
			return $.form.validator($form, validator);
		}
		validator = $form.data("form-validator");
		if (validator)
			return validator.validate();
	}
	/*检查表单或者某个值是否发生变化*/
	$.form.isChange = function($form, name) {
		var validator = $form.data("form-validator");
		if (validator)
			return validator.isChange(name);
	}
})(jQuery);
!function ($) {

  "use strict"; // jshint ;_;


 /* DROPDOWN CLASS DEFINITION
  * ========================= */
  var toggle = '[node-type=dropdown]'
    , Dropdown = function (element) {
        var $el = $(element).on('click.dropdown.data-api', this.toggle)
        $(document).on('click.dropdown.data-api', function () {
          $el.parent().removeClass('open')
        })
      }

  Dropdown.prototype = {

    constructor: Dropdown

  , toggle: function (e) {
      var $this = $(this)
        , $parent
        , isActive

      if ($this.is('.disabled, :disabled')) return

      $parent = getParent($this)

      isActive = $parent.hasClass('open')

      clearMenus()

      if (!isActive) {
        $parent.toggleClass('open')
      }

      $this.focus()

      return false
    }

  , keydown: function (e) {
      var $this
        , $items
        , $active
        , $parent
        , isActive
        , index

      if (!/(38|40|27)/.test(e.keyCode)) return

      $this = $(this)

      e.preventDefault()
      e.stopPropagation()

      if ($this.is('.disabled, :disabled')) return

      $parent = getParent($this)

      isActive = $parent.hasClass('open')

      if (!isActive || (isActive && e.keyCode == 27)) {
        if (e.which == 27) $parent.find(toggle).focus()
        return $this.click()
      }

      $items = $('[role=menu] li:not(.divider):visible a', $parent)

      if (!$items.length) return

      index = $items.index($items.filter(':focus'))

      if (e.keyCode == 38 && index > 0) index--                                        // up
      if (e.keyCode == 40 && index < $items.length - 1) index++                        // down
      if (!~index) index = 0

      $items
        .eq(index)
        .focus()
    }

  }

  function clearMenus() {
    $(toggle).each(function () {
      getParent($(this)).removeClass('open')
    })
  }

  function getParent($this) {
    var selector = $this.attr('data-target')
      , $parent

    if (!selector) {
      selector = $this.attr('href')
      selector = selector && /#/.test(selector) && selector.replace(/.*(?=#[^\s]*$)/, '') //strip for ie7
    }

    $parent = selector && $(selector)

    if (!$parent || !$parent.length) $parent = $this.parent()

    return $parent
  }


  /* DROPDOWN PLUGIN DEFINITION
   * ========================== */
  $.fn.dropdown = function (option) {
    return this.each(function () {
      var $this = $(this)
        , data = $this.data('dropdown')
      if (!data) $this.data('dropdown', (data = new Dropdown(this)))
      if (typeof option == 'string') data[option].call($this)
    })
  }
  $.fn.dropdown.Constructor = Dropdown;
  /* APPLY TO STANDARD DROPDOWN ELEMENTS
   * =================================== */
  $(document)
    .on('click.dropdown.data-api', clearMenus)
    .on('click.dropdown.data-api', '.dropdown form', function (e) { e.stopPropagation() })
    .on('.dropdown-menu', function (e) { e.stopPropagation() })
    .on('click.dropdown.data-api'  , toggle, Dropdown.prototype.toggle)
    .on('keydown.dropdown.data-api', toggle + ', [role=menu]' , Dropdown.prototype.keydown)

}(window.jQuery);
;(function($) {
	/**
	 *计算是否是工作日，只包含周一到周五，这里不会计算节假日
	 */
	function workday(date) {
		return date.getDay() > 0 && date.getDay() < 6;
	}
    
	/*
	*  1、年月选择
	*  2、时间选择
	*  3、多日期选择
	*/
	var Datepicker = function(s, options) {

		var datepicker = this;
		var self = s, $this = $(s);
		var current, min, max, disabled;
		/*日期控件的显示模式*/
		var mode = {
			year:-1,
			yearmonth:0,
			date : 1,
			time : 2,
			datetime : 3
		}
		var now = new Date(Date.now());
		/*	默认的开始时间*/
		options=$.extend({},options,{
			min: $this.attr("min"),
			max:$this.attr("max"),
			disabled:$this.attr("data-disabled"),
			format:$this.attr("format"),
			start:$this.attr("start"),
			pickers:$this.attr("pickers")
		});
		if(options.format=="yyyy"){
			datepicker.mode=mode.year;
		}else if(options.format=="yyyy-mm"){
			datepicker.mode=mode.yearmonth;
		}else{
		  datepicker.mode = (options.format.indexOf("y") == -1 ? 0 : mode.date) + (options.format.indexOf("H") == -1 ? 0 : mode.time);
		}
		var date = evalDate(now, options.start);
		if (options.min) {
			min = evalDate(now, options.min);
		}
		if (options.max) {
			max = evalDate(now, options.max);
		}
		if (options.disabled) {
			if ($.type(options.disabled) == "string") {
				disabled = eval(options.disabled);
			} else {
				disabled = options.disabled;
			}
		}
		/*处理html默认的日期控件*/
		if (self.type == 'date') {
			self.type = "text";
			self.orginalType = "date";
		}
        $this.attr("readonly","readonly").css("cursor","pointer");
		if (!isBlank($this.val())) {
			var tmp = $this.val().replaceAll("-", "/");
			try {
				tmp = Date.parse(tmp);
				if (!isNaN(tmp))
					date = new Date(tmp);
			} catch (e) {
			}
		}
		$this.data("value", date);
		current = new Date(date.getTime());
		function dayAm(year, month) {
			return new Date(year, month + 1, 0).getDate();
		}

		function evalDate(date, exp) {
			if (!exp) {
				return date;
			}			
			var y = date.getFullYear(), m = date.getMonth(), d = date.getDate(), H = date.getHours(), M = date.getMinutes(), s = date.getSeconds();
			$(exp.split(",")).each(function(index, p) {
				var e = p.substring(0, 1);
				/*常量设定*/
				if (p.indexOf("[") != -1) {
					p = p.substring(p.indexOf("[") + 1, p.lastIndexOf("]"))
				}
				switch(e) {
					case "y":
					    /*压缩编译器修改变量名称，导致表达式无法运行，这里需要将变量直接替换*/
						y = eval(p.replaceAll("y",y));
						break;
					case "m":
						m = eval(p.replaceAll("m",m));
						break;
					case "d":
						d = eval(p.replaceAll("d",d));
						break;
					case "H":
						H = eval(p.replaceAll("H",H));
						break;
					case "M":
						M = eval(p.replaceAll("M",M));
						break;
					case "s":
						s = eval(p.replaceAll("s",s));
						break;
				}
			});
			/*生成新的对象*/
			date = new Date();
			date.setYear(y);
			date.setMonth(m);
			date.setDate(d);
			date.setHours(H);
			date.setMinutes(M);
			date.setSeconds(s);
			return date;
		}

		/**
		 *创建一个日期控件
		 */
		datepicker.create = function() {
			if (!datepicker.isCreated) {
				if(options.pickers>1){
				   // alert(111)
				}
				
				var root = $('<div   class="datepicker"/>').css("position", "absolute");
				var header = $('<div  class="datepicker-header"><div class="year-month"><span class="prev" action="prev"></span><div  class="title"><b action="year">#year#</b><b action="month">#month#</b></div><span class="next" action="next"></span></div>');
               header.day=$('<div class="day"><span>日</span><span>一</span><span>二</span><span>三</span><span>四</span><span>五</span><span>六</span></div></div>')
			   header.append(header.day);
				var body = $('<ul  class="datepicker-body"></ul>');
				/*年月处理*/
				header.find("span,b","div.year-month").each(function(index) {
					var $el = $(this), action = $el.attr("action");
					if(action=="year"){
						header.year=$el;
					}else if(action=="month"){
						header.month=$el;
					}
					$el.click(function() {
						datepicker[action]();
					})
				});
                var footer=$('<div class="datepicker-footer"></div>');
				var picker = root.append(header).append(body).append(footer);
				if (datepicker.mode & mode.time) {
					var action = $('<div class="datepicker-action"><button type="button" class="btn btn-mini">今天</button><button type="button" class="btn btn-mini btn-primary">确定</button><button type="button" class="btn btn-mini">清除</button></div>')
					var time = $('<div class="time"><input type="text"  value="00" maxlength="2" />:<input type="text" value="00" maxlength="2"/>:<input type="text" value="00" maxlength="2"/></div>');
					/*时间处理*/
					var ctime = [current.getHours(), current.getMinutes(), current.getSeconds()];
					var cmax = [24, 60, 60];
					time.find("input").each(function(index) {
						$(this).val(ctime[index]).on("keypress.datepicker", function(event) {

						}).on("keyup.datepicker", function(event) {
							if (event.which == 13) {
								datepicker.setValue($this.data("value"), true);
								$this.blur();
								return;
							}
							t = parseInt($(this).val());
							t = isNaN(t) ? 0 : t % cmax[index];
							$(this).val(t);
						});
					});
					footer.append(time);

					var afn = [
					function() {
						current = new Date(now.getTime());
						datepicker.date(current);
					},
					function() {
						datepicker.setValue($this.data("value"), true);
					},
					function() {
						datepicker.setValue(null, true);
					}];
					/*action工具栏*/
					action.find("button").each(function(index) {
						$(this).click(afn[index]);
					});

					footer.append(action);
				}
				$(document.body).append(picker).css("z-index", $this.css("z-index") * 1 + 1);
				datepicker.picker = picker;
				datepicker.header = header;
				datepicker.body = body;
				datepicker.footer = footer;
				datepicker.time = time;
				datepicker.isCreated = true;
			}
			if(datepicker.mode==mode.year||datepicker.mode==mode.yearmonth){
				datepicker.year();
				datepicker.show();
			}else{
			   datepicker.date(current);
			}
		}
         
		 /*
		 * 显示每天的日期
		 */
		datepicker.date = function(value) {
			var year = value.getFullYear(), month = value.getMonth();
			var days = dayAm(year, month), prevDays = dayAm(year, month - 1);
			var li, begin = new Date(year, month, 1).getDay();
			datepicker.body.empty().removeClass("year month");
			datepicker.header.day.show();
			datepicker.footer.show();
			datepicker.state="date";
			datepicker.min = false;
			datepicker.max = false;
			current = value;
			for (var j = !begin ? -7 : 0, a, num; j < (!begin ? 35 : 42); j++) {
				a = $("<a/>");
				if (j % 7 === 0) {
					li = $("<li/>");
					datepicker.body.append(li);
					a.addClass("sun");
				}

				if (j < begin) {
					a.addClass("off");
					num = prevDays - begin + j + 1;
					value = new Date(year, month - 1, num);
				} else if (j >= begin + days) {
					a.addClass("off");
					num = j - days - begin + 1;
					value = new Date(year, month + 1, num);
				} else {
					num = j - begin + 1;
					value = new Date(year, month, num);
					/*today*/
					if (now.equals(value, true)) {
						a.addClass("today");
					} else if ($this.data("value") && $this.data("value").equals(value, true)) {
						//selected day
						a.addClass("selected");
					}
				}
				/*最小*/
				if (min && value.compareTo(min, true) < 0) {
					datepicker.min = true;
					a.addClass("disabled");
				}
				/*最大*/
				if (max && value.compareTo(max, true) > 0) {
					a.addClass("disabled");
					datepicker.max = true;
				}
				/*禁止*/
				if (disabled && disabled(value) === false) {
					a.addClass("disabled");
				}
				a.attr("href", "#" + num).text(num).data("value", value).on("click.datepicker", function(event) {
					event.preventDefault();
					if (!$(this).hasClass("disabled")) {
						datepicker.body.find("a.selected").removeClass("selected");
						datepicker.setValue($(this).addClass("selected").data("value"), datepicker.mode == mode.date);
					}
				});
				li.append(a);
			}
			/*头部改变*/
			datepicker.header.year.html(year + "年");
			datepicker.header.month.html((month + 1) + "月");
			
			datepicker.show();
		}
		
		// show calendar
		datepicker.show=function(){
			var pos = $this.offset();

			// iPad position fix
			if (/iPad/i.test(navigator.userAgent)) {
				pos.top -= $(window).scrollTop();
			}

			datepicker.picker.css({
				top : pos.top + $this.outerHeight(),
				left : pos.left
			});
			datepicker.isOpened = true;
			datepicker.picker.show();
		}

		datepicker.prev = function() {
			if(datepicker.state=="year"){
				current.setFullYear(current.getFullYear() - 20)
				datepicker.year();
			}else if(datepicker.state=="month"){
				current.setFullYear(current.getFullYear() - 1)
				datepicker.month();
			}else{
				if (!datepicker.min) {
					current.setMonth(current.getMonth() - 1);
					datepicker.date(current);
				}
			}
		}

		datepicker.next = function(my) {
			if(datepicker.state=="year"){
				current.setFullYear(current.getFullYear() + 20)
				datepicker.year();
			}else if(datepicker.state=="month"){
				current.setFullYear(current.getFullYear() +1)
				datepicker.month();
			}else{
				if (!datepicker.max) {
					current.setMonth(current.getMonth() + 1);
					datepicker.date(current);
				}
			}
		}
		/*显示年选择*/
		datepicker.year=function(){
			var li,j, begin = current.getFullYear();
			begin=begin-begin%10;
			datepicker.header.day.hide();
			datepicker.footer.hide();
			datepicker.body.empty().removeClass("month").addClass("year");
			datepicker.state="year";
			for (j=begin; j < begin+20; j++) {
				a = $("<a/>");
				if (j%5==0) {
					li = $('<li/>'); 
					datepicker.body.append(li);
				}
				
				if(j==now.getFullYear()){
				   a.addClass("today");
				}else if($this.data("value") && ($this.data("value").getFullYear()==j)){
				   a.addClass("selected"); 
				}
				a.attr("href", "#" + j).text(j).data("value", j).on("click.datepicker", function(event) {
					event.stopPropagation();
					event.preventDefault();
					if (!$(this).hasClass("disabled")) {
						/*改变年，调整月份*/
						current.setFullYear($(this).data("value"));
						datepicker.mode == mode.year?datepicker.setValue(current, true):datepicker.month(); 
					}
				});
				
				li.append(a);
			}
			datepicker.header.year.html(begin + "年-"+(begin+19)+"年");
			datepicker.header.month.html("");
		}
		/*显示月选择*/
		datepicker.month=function(){
			var li,j, begin = current.getMonth();
			datepicker.header.day.hide();
			datepicker.footer.hide();
			datepicker.body.empty().removeClass("year").addClass("month");
			datepicker.state="month";
			for (j=0; j < 12; j++) {
				a = $("<a/>");
				if (j%4==0) {
					li = $('<li/>'); 
					datepicker.body.append(li);
				}
				if($this.data("value") &&($this.data("value").getFullYear()==current.getFullYear())&&$this.data("value").getMonth()==j){
				       a.addClass("selected"); 
				}else 
				if(current.getFullYear()==now.getFullYear()){
					if(j==now.getMonth()){
				        a.addClass("today");
					}
				} 
				
				a.attr("href", "#" + (j+1)).text((j+1)+"月").data("value", j).on("click.datepicker", function(event) {
					event.stopPropagation();
					event.preventDefault();
					if (!$(this).hasClass("disabled")) {
						    current.setMonth($(this).data("value"));
							datepicker.mode == mode.yearmonth?datepicker.setValue(current, true):datepicker.date(current); 
					}
				});
				li.append(a);
			}
			datepicker.header.year.html(current.getFullYear()+"年");
			datepicker.header.month.html((current.getMonth()+1)+"月");
		}

		datepicker.hide = function() {
			if (datepicker.isOpened) {
				datepicker.isOpened = false;
				datepicker.picker.hide();
			}
		}

		datepicker.setValue = function(v, close) {
			/*清除*/
			if (v == null) {
				$this.removeData("value");
				$this.val("");
				return;
			}
			/*计算时间容器的值，填充进来*/
			if (datepicker.time) {
				var tinput = datepicker.time.find("input");
				v.setHours(tinput.eq(0).val());
				v.setMinutes(tinput.eq(1).val());
				v.setSeconds(tinput.eq(2).val());
			}
			$this.data("value", v);
			$this.val(v.format(options.format));
			if (close) {
				$this.triggerHandler("afterpick");
				datepicker.hide();
			}
			current = new Date(v.getTime());
		}

		$this.on("focus.datepicker", function() {
			datepicker.create();
		}).on("keyup.datepicker", function(event) {
			if (event.which == 13 && datepicker.isOpened) {
				datepicker.setValue($this.data("value"), true);
				$this.blur();
			}
		});

		
	}
	
	$.fn.datepicker = function(options) {
		options = $.extend({
			/*默认选择器的个数*/
			pickers : 2,
			/*格式化*/
			format : "yyyy-mm-dd HH:MM:ss",
			/*1、默认的开始日期格式如  y+1,m,d,H,M,s :代表今年+1就是明年，也可以直接写成y+1
			 * 2、常量定义y[1986],m[12],d 代表1986年12月和今天同日的，有可能是31好，而这个正好没有31好，将会自动进位处理，就是到了下个月
			 * 3、也支持动态获取其他元素的值，设定的这个值发送变化时将会影响这个值的变化*/
			start : "y,m,d,H,M,s",
			/*最小日期*/
			min : undefined,
			/*最大日期*/
			max : undefined,
			/*禁选方法，返回true代表可以，返回false代表不可选，固有方法：workday*/
			disabled : undefined,
			/*是否开启时钟，开启时钟时，只对用户选择当前时间的最小值有影响，界面上秒钟会自动走动，*/
			clock : false,
			lang : {

			}
		}, options);
		return this.each(function() {
			var self = this;
			if (!self.datepicker){
				self.datepicker = new Datepicker(self, options);
				/*关闭时间*/
				$(document).on("click.datepicker", function(e) {
					if (e.target != self &&$(e.target).closest("div.datepicker").length == 0) {
						self.datepicker.hide();
					}
				})
			}
			return self;
		});
	}
	/*覆盖原始日期表达式*/
	$.expr[':'].date = function(el) {
		var type = el.getAttribute("type");
		return type && type == 'date' || el.getAttribute("orginalType") == "date";
	};
})(jQuery);
;(function($) {

	$.fn.screenCenter = function() {
		var top = ($(window).height() - this.height()) / 2;
		var left = ($(window).width() - this.width()) / 2;
		var scrollTop = $(document).scrollTop();
		var scrollLeft = $(document).scrollLeft();
		/*保证左上角再屏幕范围内*/
		if (top < 0)
			top = 0;
		if (left < 0)
			left = 0;
		return this.css({
			top : top + scrollTop,
			left : left + scrollLeft
		});
	}
    /*遮罩生成*/
	$.overlay = {
		size : 0,
		show : function(fn) {
			/* 生成遮罩 */
			if ($("#overlay").length == 0) {
				$(document.body).append('<div class="overlay" id="overlay" style="display:none;"></div>');
			}
			$('#overlay').fadeTo(200, 0.3, fn);
		},
		hide : function(fn) {
			if ($(".modal-overlay:visible").size()== 0)
				$('#overlay').fadeOut(200, fn);
			else
			   fn();	
		}
	}
})(jQuery);

/*
 *  弹出层样式
 * 1、效果扩展性(fade)
 *
 */
;Pop = {
	effect : {
		noeffect : [//in
		function($pop, options, done) {
			$pop.show(done);
		},
		//out
		function($pop, options, done) {
			$pop.hide(done);
		}],

		fade : [
		//in
		function($pop, options, done) {
			$pop.fadeIn(done);
		},
		//out
		function($pop, options, done) {
			$pop.fadeOut(done);
		}],
		slide : [
		function($pop, options, done) {
			$pop.slideDown(done);
		},

		function($pop, options, done) {
			$pop.slideUp(done);
		}],

		drop : [
		function($pop, options, done) {
			$pop.css({
				top : $(window).scrollTop() + 50,
				opacity : 0.5
			}).show();
			$pop.animate({
				top : '+=55',
				opacity : 1
			}, 200, 'drop', done)
		},
		function($pop, options, done) {
			$pop.animate({
				top : '-=55',
				opacity : 0.5
			}, 200, 'drop', function() {
				$pop.hide(done);
			});
		}],

		mac : [
		function($pop, options, done) {
			var $trigger = options.trigger;
			var w = $(window);
			var ow = $pop.data("ow") || $pop.outerWidth();
			var oh = $pop.data("oh") || $pop.outerHeight();
			$pop.data({
				"ow" : ow,
				"oh" : oh
			});
			var end = options.end || {
				top : (w.height() - oh) / 2 + w.scrollTop(),
				left : (w.width() - ow) / 2 + w.scrollLeft()
			}
			var start = end;
			/*触发的节点*/
			if ($trigger) {
				start = $trigger.offset();
				start.top = start.top + $trigger.outerHeight() / 2;
				start.left = start.left + $trigger.outerWidth() / 2;
			} else {
				/*无节点时*/
			}
			$pop.css({
				position : 'absolute',
				width : 0,
				height : 0,
				top : start.top,
				left : start.left
			}).show();
			$pop.animate({
				top : end.top,
				left : end.left,
				width : ow,
				height : oh
			}, options.speed, done);
			$pop.data("start", start);
		},

		function($pop, options, done) {
			var start = $pop.data("start");
			$pop.animate({
				top : start.top,
				left : start.left,
				width : 0,
				height : 0
			}, options.speed, function() {
				$pop.hide(done);
			})
		}],
		mouse : [
		function($pop, options, done) {
			var $trigger = options.trigger;
			var offset = $trigger.offset();
			/*设定和对象右对齐*/
			var start = {
				top : offset.top + $trigger.outerHeight(),
				left : offset.left + $trigger.outerWidth() - $pop.width()
			};
			var end = {
				top : offset.top - $pop.outerHeight(),
				left : offset.left + $trigger.outerWidth() - $pop.width()
			};
			$pop.css({
				top : start.top,
				left : start.left,
				opacity : 0,
				display : "block"
			});
			$pop.animate({
				top : end.top,
				left : end.left,
				opacity : 1
			}, options.speed, done);
			$pop.data("start", start);
		},
		function($pop, options, done) {
			var start = $pop.data("start");
			$pop.animate({
				top : start.top,
				left : start.left,
				opacity : 0
			}, options.speed, function() {
				$pop.css("display", "none");
				if (done) {
					done(arguments);
				}
			});
		}]

	},
	queue : [],
	settings : {
		id : "pop" + $.now(),
		container : document.body,
		layout : "center",
		template : function(options) {
			var html = [];
			if (options.title) {
				html.push("<div class='pop-header'><h3>" + options.title + "</h3>");
			} else {
				html.push("<div class='pop-header notitle'>");
			}
			html.push("<a node-type='pop-close' class='close' href='javascript:;'>×</a></div><div class='pop-body' node-type='pop-body'></div>");
			if (options.footer)
				html.push("<div class='pop-footer'>" + options.footer + "</div>")
			return html.join("");
		},
		theme : "",
		maxable : false, /*是否可以最大化*/
		minable : false, /*是否可以最小化*/
		esc : true, /*是否可以使用esc*/
		url : undefined,
		content : undefined, /*html内容或者是jquery对象，可以预先绑定事件*/
		className : "pop", /*默认样式*/
		effect : "noeffect", /*窗口特效*/
		speed : 300, /*特效显示时间*/
		trigger : null,
		destroy : false, /*是否自动销毁，例如flash调用客户端的一些组件，防止被占用，需要设置为true，自动销户节点*/
		refresh : false, //是否每次都刷新
		onShow : function($pop, options) {
		},
		afterShow : undefined,
		onClose : function($pop, options) {
		},
		afterClose : function($pop, options) {
		},
		cache : false,
		modal : true,
		iframe : false,
		resizeable : false,
		state : "normal", /*窗口状态{normal|max|min}*/
		onResize : function($pop, options) {
		},
		dragable : false,
		onDrag : function($pop, options) {
		},
		show : function($pop, options, done) {
			options.effect[0]($pop, options, done);
		},
		hide : function($pop, options, done) {
			options.effect[1]($pop, options, done);
		}
	},
	update : function($pop, options) {
		//queue.push($pop);
		var o = $pop.data("options");
		var $body = $pop;
		if (!options) {
			options = o;
		} else {
			if (options.content) {
				delete o.url;
			}
			options = $.extend($pop.data("options"), options);
		}
		/*解析定义的模板*/
		if (options.template) {
			$pop.html(options.template(options));
			$body = $pop.find("[node-type=pop-body]:first");
		}
		if (options.css) {
			$body.css(options.css);
		}
		/*iframe方式载入*/
		if (options.iframe) {
			$body.html("<a node-type='pop-close' class='close' href='javascript:;' >×</a><iframe  id='" + options.id + "_iframe' name='" + options.id + "Iframe'  src='" + options.url + "' frameborder='0' height='100%' width='100%' scrolling='no'></iframe>");
			Pop._show($pop, options);
		} else {
			if (options.url) {
				$body.load(options.url, function(data) {
					Pop._show($pop, options);
				});
			} else if (options.content) {
				/*这里是append可以保留*/
				$body.empty().append(options.content);
				Pop._show($pop, options);
			} else {
				Pop._show($pop, options);
			}
		}
		return $pop;
	},
	show : function(options) {
		if (!options.id)
			options.id = "pop" + $.now();
		options = $.extend(false, Pop.settings, options);
		var $pop = $("#" + options.id);
		if ($pop.attr("opened") == "true" && !options.refresh) {
			return $pop;
		}
		/*使用iframe方式加载，可选，用户也可以自己编辑界面，然后定义需要的iframe*/
		if (options.iframe) {
			options.theme = "iframe";
			options.template = false;
		}

		if ($pop.length == 0) {
			if (!options.container)
				options.container = document.body;
			$(options.container).append("<div id='" + options.id + "' style='display:none'></div>");
			$pop = $("#" + options.id);
			/*使用iframe方式载入，默认不使用模版*/
			$pop.addClass(options.className + " " + options.theme + " " + options.layout);
		}
		/*解析特效*/
		if (options.effect) {
			if (( typeof options.effect) == "string") {
				options.effect = Pop.effect[options.effect];
			}
		}
		$pop.data("options", options);
		//var d=$.Deferred();
		/*显示之前调用的方法*/
		if (options.onShow($pop, options) === false) {
			return $pop;
		}

		/*缓存页面*/
		if (options.cache && $pop.attr("loaded")) {
			Pop._show($pop, options);
			return $pop;
		}
		return Pop.update($pop);
	},
	_show : function($pop, options) {
		if (options.layout == "center") {
			$pop.screenCenter();
		}
		/*绑定显示方法*/
		$pop.attr("opened", "true");
		$pop.attr("loaded", "true");
		if (options.modal) {
			$.overlay.show(function() {
				options.show($pop, options, options.afterShow ? options.afterShow($pop, options) : undefined);
				$pop.addClass("modal-overlay")
			});
		} else {
			options.show($pop, options, options.afterShow ? options.afterShow($pop, options) : undefined);
		}

		/*绑定一个pop对象可以控制pop*/
		$pop.find("[window=true]").data("window", $pop);

		/*绑定关闭方法*/
		$pop.find("[node-type=pop-close]").one("click", function(event) {
			Pop.hide($pop);
			event.preventDefault();
		});

		/*在某个按钮上绑定，可以通过按钮直接调用  button.trigger("pop-close")*/
		$pop.find("[node-type=pop-close-trigger]").one("pop-close", function(event) {
			Pop.hide($pop);
			event.preventDefault();
		});

		/*绑定更新方法*/
		$pop.find("[node-type=pop-refresh]").click(function() {
			
			Pop.update($pop);
			event.preventDefault();
		});

	},
	max : function() {
	},
	/**
	 * 关闭pop层
	 */
	hide : function($pop, options) {
		if ($.type($pop) == "String") {
			$pop = $($pop);
		}
		if (!$pop.attr("opened")) {
			return;
		}
		options = $.extend($pop.data("options"), options);
		if (options.onClose($pop, options) === false) {
			return false;
		}

		options.hide($pop, options, function() {
			$pop.removeAttr("opened");
			if (options.modal) {
				$.overlay.hide(function() {
					options.afterClose($pop, options);
					/*是否自动销毁*/
					if (options.destroy) {
						$pop.remove();
					}
				});
			} else {
				options.afterClose($pop, options);
				/*是否自动销毁*/
				if (options.destroy) {
					$pop.remove();
				}
			}
		});
	}
};
/*绑定到jQuery*/
;(function($) {
	$.fn.pop = function(options) {
		if (!options)
			options = {};
		return this.each(function() {
			var $this = $(this), rel = $this.attr("rel") || $this.attr("href"), url, content, id = $this.attr("pop-id") || ("pop-" + $.now());
			$this.attr("pop-id", id);
			var extend = $this.attr("setting") ? eval("(" + $this.attr("setting") + ")") : {};
			if (!rel) {
				throw new Error("the url not defined!");
				return;
			}
			/*页面内的*/
			if (rel.startsWith("#")) {
				content = $(rel).html();
				$(rel).remove();
				extend.cache = true;
			} else {
				url = rel;
			}
			var $pop = Pop.show($.extend({}, {
				id : id + "-pop",
				content : content,
				url : url,
				template : false,
				trigger : $this
			}, options, extend));
			$this.data("pop", $pop).on("pop-close", function() {
				Pop.hide($pop);
			})
			return $pop;
		});
	}
})(jQuery);

/*
* 消息提示框，通过pop.js扩展
*/
;(function($){
$.alert = {
	settings : {
		id : "alert" + $.now(),
		theme : "pop-alert",
		effect : "drop",
		template : function(options) {
			var html = [];
			html.push("<div class='pop-header notitle'>");
			html.push("<a node-type='pop-close' class='close' href='javascript:;'>×</a></div>");
			html.push("<div class='pop-body' node-type='pop-body'></div>");
			if (options.footer) {
				html.push("<div class='pop-footer'>" + options.footer + "</div>");
			}
			return html.join("");
		}
	},
	show : function(result, fn, options) {
		/*检查参数*/
		if (fn) {
			if ($.isFunction(fn)) {
				options = options || {};
				options.afterClose = fn;
			} else {
				options = fn;
				fn = undefined;
			}
		} else {
			options = options || {};
		}
		if (!options.id)
			options.id = "alert" + $.now();

		/*使用mouse方式显现较少的内容*/
		if (options.effect == "mouse") {
			options.id = options.trigger.attr("pop-id") || options.id;
			options.trigger.attr("pop-id", options.id);
			options = $.extend({
				theme : "pop-alert small",
				effect : "mouse",
				modal : false,
				template : function(options) {
					var html = [];
					html.push("<div class='pop-body' node-type='pop-body'></div>");
					if (options.footer) {
						html.push("<div class='pop-footer'>" + options.footer + "</div>");
					}
					return html.join("");
				}
			}, options);
		}
		/*后台消息模式*/
		if (result && result.status != undefined) {
			switch(result.status) {
				case 1:
					return $.alert.success.apply(null, [result.message,options])
				case 0:
					return $.alert.warn.apply(null, [result.message, options]);
				case -1:
					return $.alert.failure.apply(null, [result.message, options]);
				case -2:
					return $.alert.error.apply(null, [result.message, options]);
				/*默认显示警告*/
				default:
					return $.alert.warn.apply(null, [result.message, options]);
			}
							
		} else if (type(result) == "string") {
			return $.alert.info(result, options);
		}
		return Pop.show($.extend(false, $.alert.settings, options));
	},
	update : function($alert, options) {
		Pop.update($alert, options)
	},
	success : function(message, options) {
		options = $.extend({
			content : "<i class='success'></i><div class='content'>" + message + "</div>",
			effect : "slide",
			afterShow : function($pop, options) {
				window.setTimeout(function() {
					Pop.hide($pop);
				}, 1000);
			}
		}, options);
		return $.alert.show(null, options);
	},
	info : function(message, options) {
		options = $.extend({
			content : "<i class='warn'></i><div class='content'>" + message + "</div>",
			effect : "drop",
			afterShow : function($pop, options) {
				window.setTimeout(function() {
					Pop.hide($pop);
				}, 1000);
			}
		}, options);
		return $.alert.show(null, options);
	},
	warn : function(message, options) {
		options = $.extend({
			content : "<i class='warn'></i><div class='content'>" + message + "</div>",
			footer : "<button node-type='pop-close' class='btn'>确定</button>",
			effect : "drop"
		}, options);
		return $.alert.show(null, options);
	},
	failure : function(message, options) {
		options = $.extend({
			content : "<i class='warn'></i><div class='content'>" + message + "</div>"
		}, options);
		return $.alert.warn(message, options);
	},
	error : function(message, options) {
		options = $.extend({
			content : "<i class='error'></i><div class='content'>" + message + "</div>"
		}, options);
		return $.alert.warn(message, options);
	},
	confirm : function(message, footer, fn, options) {
		if ($.isArray(footer)) {
			options = fn;
			fn = footer;
			footer = "<button class='btn btn-primary' node-type='pop-close'>确定</button><button class='btn' node-type='pop-close' >取消</button>";
		}
		options = $.extend({
			content : "<i class='confirm'></i><div class='content'>" + message + "</div>",
			footer : footer,
			effect : "drop",
			afterShow : function($pop, options) {
				if (fn) {
					$pop.find(".pop-footer button").each(function(index) {
						/*将方法的参数绑定到按钮上*/
						var f = fn[index];
						if (f) {
							$(this).click(function(event) {
								var me=this;
								/*改变this的指向,指向trigger,这里比较特殊，一定要等窗口关闭才能继续执行，否则后续代码将无法返回正确结果*/
								options.afterClose=function(){
								  f.apply(options.trigger ? options.trigger : me, []);
								}
							});
						}
					});
				}
			}
		}, options);
		return $.alert.show(null, options);
	}
};
})(jQuery);!(function($){
	Tooltip=function(element,type,options){
		this.init(element,type,options);
		return this;
    }
	
	Tooltip.prototype={
		Constructor:Tooltip,
		init:function(element,type,options){
			var me=this;
			me.element=element;
			me.$element=$(element);
			me.type=type;
			me.options=$.extend({
				placement:"auto",
				template:function(o){ return'<div class="'+type+'"><span class="arrow"><em>◆</em><i>◆</i></span><div class="'+type+'-inner"></div></div>'},
				trigger:"hover focus",
				content:this.$element.data(type),
				url:this.$element.data(type+"-url"),
				id:this.$element.data(type+"-id")||(type+"-"+$.now()),
				container: false,
				timeout:500
			},options,me.$element.data(type+"-options"));
			var triggers = me.options.trigger.split(' ')
            for (i = triggers.length; i--;) {
               trigger = triggers[i]
               if (trigger == 'click') {
                    me.$element.on('click.' + me.type, me.options.selector, function(){me.toggle();});
              }else if(trigger=="static"){
				me.show();
			  }else if (trigger != 'manual') {
				 me.enterLeave=true;
                 me.$element.on('hover' ? 'mouseenter' : 'focus' + '.' + me.type, me.options.selector, function(){
					  me.enter();
				 });
                 me.$element.on('hover' ? 'mouseleave' : 'blur' + '.' + me.type, me.options.selector, function(){
					 me.leave();
				 });
              }
			}
		},create:function(){
			var me=this;
			//create lock;
			me.lock=me.type+"-locked"+me.options.id;
			//cache model
			me.$tooltip=$("#"+me.options.id).length!=0?$("#"+me.options.id):$(me.options.template(me.options)).attr("id",me.options.id);
			me.$inner=me.$tooltip.find("."+me.type+"-inner");
			me.$arrow=me.$tooltip.find(".arrow");
			me.options.container ? me.$tooltip.appendTo(me.options.container) : me.$tooltip.insertAfter(me.$element);
			/*判断一下是否是URL地址，如果是URL地址自动解析使用ajax加载*/
			if(me.options.url){
				 me.$inner.load(me.options.url,function(){
					 me.isCreated=true;
			         me.show();
				 });
			}else if(me.options.show){
				me.options.show(me,function(){
					 me.isCreated=true;
			         me.show();
				});
			}else{
				me.$inner.html(me.options.content);
				 me.isCreated=true;
			     me.show();
			}
			if(me.enterLeave){
				me.$tooltip.on("mouseenter"+"."+me.type,function(){
					 window[me.lock]=true;
					clearTimeout(me.timeout);
				}).on("mouseleave"+"."+me.type,function(){
					 window[me.lock]=false;
					me.hide();
				});
			}
		},enter: function (e) {
			 var me=this;
			 /*可能存在多个元素公用一个tooltip，这里我们使用ID作为检查的条件*/
			  window[me.lock]=true;
			  clearTimeout(me.timeout);
			  me.show();
		},leave: function (e) {
			 var me=this;
			  window[me.lock]=false;
				 me.timeout=setTimeout(function(){
					 if(!window[me.lock])me.hide()
				},me.options.timeout);
		},
		show:function(){
			 if(this.isCreated){
				 this.$tooltip.show();
				 this.placement();
				 this.$tooltip.addClass("in");
			}else{
				this.create();
								
			}
		},hide:function(){
			this.$tooltip.removeClass("in").hide();
		},toggle:function(){
			this.$tooltip&&this.$tooltip.hasClass("in")?this.hide():this.show();
		},		
		placement:function(){
			var me=this,offset,replace,auto=false,placement=me.options.placement,
			 pos=me.position(),
			 height=me.$tooltip[0].offsetHeight,
			 width=me.$tooltip[0].offsetWidth,
			 actualWidth,
             actualHeight;
			 /*检查可以放置的位置*/
			 if(placement=="auto"){
				 me.$arrow.removeClass("arrow-top arrow-bottom arrow-right arrow-left");
				 placement=me.canPlace(pos);
			 }			 
			switch (placement) {
			  case 'bottom':
				 offset = {top: pos.top + pos.height+7, left: pos.left + pos.width / 2 - width / 2}
				  me.$arrow.addClass("arrow-top");
				  break;  
			  case 'top':
				  offset = {top: pos.top - height-7, left: pos.left + pos.width / 2 - width / 2};
				  me.$arrow.addClass("arrow-bottom");
				  break;
			  case 'left':
				   offset = {top: pos.top + pos.height / 2 - height / 2, left: pos.left - width-9}
				   me.$arrow.addClass("arrow-right");
				  break;
			  case 'right':
				  offset = {top: pos.top + pos.height / 2 - height / 2, left: pos.left + pos.width+5}
				  me.$arrow.addClass("arrow-left");
				  break;
				  
			}
			/*尝试定位*/
			 me.$tooltip.offset(offset);
			/*关于相对位置的判断*/
			 actualWidth = me.$tooltip[0].offsetWidth,
             actualHeight =me.$tooltip[0].offsetHeight;
			 if (placement == 'bottom' || placement == 'top') {
				 if(me.$tooltip.position().left<0){
					if (me.ratio < 1){
					  offset.left=pos.left;
					}else{
						var w=$(window).width();
						offset.left=w-pos.left-actualWidth>0?pos.left:w-actualWidth;
					}			
					me.$tooltip.offset(offset);
				 }
				 me.$arrow.css("left",pos.left+me.$element.width()/2-offset.left);	
            }else{
				me.$arrow.css("top",pos.top+me.$element.height()/2-offset.top)
			}

		},position: function () {
            var el = this.element;
           return $.extend({}, (typeof el.getBoundingClientRect == 'function') ? el.getBoundingClientRect() : {
                  width: el.offsetWidth
              , height: el.offsetHeight
       }, this.$element.offset())
       },
	   /*判断是否能够在window窗口内显示*/
	   canPlace:function(position){
		   var me=this,$w=$(window),w = $w.width(), h = $w.height(), sl = $w.scrollLeft(), st = $w.scrollTop(),
		     offset=me.$element.offset(),			 
		   	 actualWidth = me.$tooltip[0].offsetWidth,
             actualHeight =me.$tooltip[0].offsetHeight;
			 if((offset.top - st - actualHeight) >= 0 && offset.left >= 0 && (offset.left-sl >= 0)){
				 /*计算左右比例*/
				 me.ratio=position.left/(w-position.left);
				 /*计算最佳位置，即检查是否适合在下面放置*/
				 if((offset.top - st - actualHeight)-(h + st - offset.top - actualHeight)>0){
				     return "top";
				 }else{
					 return "bottom";
				 }
			 }else if((h + st - offset.top - actualHeight) >= 0 && offset.left >= 0 && (offset.left-sl >= 0)){
				 me.ratio=position.left/(w-position.left);
				 return "bottom";
			 }else if(offset.left - sl - actualWidth>=0){
				 return "left";
			 }
		   return "right";
	   }
	}

	$.fn.tooltip=function(options){
		return this.each(function(){
			if(!this.tooltip){
				this.tooltip=new Tooltip(this,"tooltip",options);
			}			
			return this;
		});
	}
	$.fn.tooltip.Constructor = Tooltip;
})(jQuery);
!function ($) {
  "use strict"; 
  var Popover = function (element, type,options) {
        this.init(element,type,options);
		return this;
  }

  Popover.prototype = $.extend({}, $.fn.tooltip.Constructor.prototype, {
    Constructor:Popover
  });

  $.fn.popover = function (options) {
      return this.each(function(){
			if(!this.popover){
				this.popover=new Popover(this,"popover",options);
			}			
			return this;
		});
  };

  $.fn.popover.Constructor = Popover;
}(window.jQuery);
/*
 *   1、简单模式,这里会自动生成head，只适用于单层级的tab，自动检测是否跨域生成iframe
 *   <div class="tabnavigator" node-type="tabnavigator">
 *        <div class="tab-view" caption="tab1" closeable="false">tab1</div>
 *        <div class="tab-view" caption="tab2" closeable="false" data-url="http://baidu.com">tab2</div>
 *        <div class="tab-view" caption="tab3" closeable="false">tab3</div>
 *    </div>
 *   2、可定义多层级的tab，tab-head，body和view的位置可以自由定义，功能更强大
 *   <div class="tab-header" node-type="tabnavigator" mode="hash|push|default">
 *         <ul><li view="cccc"><a href="">caption</a></li></ul>
 *    </div>
 *    <div class="tab-body">
 *        <div class="view" id="ccc"></div>
 *    </div>
 *
 */
!(function($) {
	var Tabnavigator = function(t, o) {
		this.self = t;
		this.$this = $(t);
        this.root=this.$this.data("root")||this.$this;
		o = $.extend({
			 mode : this.$this.attr("mode"), /*hash(锚点模式)|pjax（ajax push 只适用于html5）|default（默认模式，当刷新的时候，默认使用cookie保存页面状态，但是不适用于浏览器转发复制地址）*/
		     effect : "random"/*切换时的效果*/
			}, o);
		this.options = o;
		this.init();
	}

	Tabnavigator.prototype = {
		init : function() {
			var me = this;
			/*存储tab*/
			me.tabs = [];
			/*简单模式*/
			if (me.$this.hasClass("tabnavigator")) {
				me.header = $('<div class="tab-header"><ul></ul></div>');
				me.body = $('<div class="tab-body"></div>');
				me.$this.prepend(me.header);
				me.$this.append(me.body);
				me.body.append(me.$this.find(".tab-view"));
				me.header.tabs = me.header.find("ul");
				me.body.find(".tab-view").each(function() {
					var v=$(this);
					me.add({caption:v.attr("caption"),view:v,url:v.data("url"),mode:v.attr("mode")||me.options.mode,name:v.attr("name"),closeable:v.attr("closeable"),remote:v.attr("remote"),once:v.attr("once")});
				});
			} else {
				/*复合模式*/
				me.header=me.$this;
				me.header.tabs= me.header.find("ul");
				me.header.find("li").each(function(){
					var tab=this,
					    t=$(tab).data("parent",me.$this),
						a=t.find("a:first"),
						group=a.attr("group"),
						view=$(a.attr("view")),
						m=a.attr("mode")||me.options.mode;
                        me.add({tab:t,view:view,url:a.attr("href"),name:t.attr("name"),mode:m,closeable:a.attr("closeable"),remote:a.attr("remote"),once:a.attr("once")});
				     if(group) {
                         /*创建子选项卡*/
                          view.attr("mode",view.attr("mode")||m).data("root",me.$this).data("tab",t);
                         /*子项激活的tab会被优先mark*/
                          view.tabnavigator=new Tabnavigator(view);
                         t.on("subheader",function(){
                             var s=view.tabnavigator.header.find("li.active");
                             if(s.length==0){
                                view.tabnavigator.show(0);
                             }
                         });

						 view.tabnavigator.header.find("li").on("click",function(){
							 tab.hash=$(this).find("a:first").attr("href");
						 })
                     }
				});
			}


			me.mark(function(event){
				/*如果子项已经被mark这里就不在处理了*/
				if(me.root.data("marked")||!location.hash){
					return;
				}
				/*检查浏览器路径，仅对hash方式有效*/			
				var ac;
				me.header.find("li[mode=hash]").each(function(){
					 var $ac=$(this).find("a");
					 /*hash值中可能包含更多的参数*/
					 if(location.hash.startsWith($ac.attr("href"))){
						 ac=$ac;
						 return 0;
					 }
				});
				if(ac&&ac.length>0){
                    var li=ac.parent(),parent=li.data("parent");
					li[0].url=location.hash;
					li.click();
                    if(parent) {
                        parent.show();
                        var tab=parent.data("tab");
                        if(tab){
						   tab[0].hash=location.hash;
                           tab.addClass("active");
                        }
                  }
                    me.root.data("marked",true);
				}
		    });
			
			me.mark();
		},
		/*页面加载的时候，如果使用hash模式时自动mark*/
		mark:function(fn){
			var me=this;
			if(fn){
				me.$this.on("mark.tabnavigator",fn);
			}else{
				me.$this.trigger("mark");
			}
			return me;
		},
		/*增加一个tab
		* tab:{caption:"",closeable:"",view:}
		*/
		add : function(index, tab) {
			var me = this;
			if (tab==undefined) {
				 tab=index;
                 index=me.tabs.length;				
			}
			var view = tab.view||$('<div class="tab-view"></div>').appendTo(me.body), url = tab.url;
			url = url ? url : "javascript:;";
			
			var t = tab.tab||$('<li><a href="' + url + '">' + tab.caption + '</a></li>');
			t.on("click.tab", function(event) {
				event.preventDefault();
				event.stopPropagation();
				me.show(this);
			}).attr("mode",tab.mode);
			if(tab.closeable){
				var close=$('<span  class="close">×</span>').on("click.tab",function(event){
					 event.preventDefault();
				     event.stopPropagation();
					 me.remove($(this).parent()[0]);
				});				
				t.append(close);
			}
			/*判断加入元素的位置*/
			index==me.tabs.length?me.header.tabs.append(t):me.get(index=me.index(index)).tab.before(t);	
			me.tabs.splice(index,0,$.extend(t[0],{tab:t,view:view,url:url,mode:tab.mode,name:tab.name,remote:tab.remote,once:tab.once}));
			return me;
		},

        /**
         *
         * @param index
         * @returns {*}
         */
		remove : function(index) {
			var me = this;
			var tab = me.get(index);
			if(!tab){return me;}
            me.tabs.splice(index,1);  
			tab.tab.remove();
			view.remove();
			if(tab.tab.hasClass("active")){
				 me.show(0);
			}
			return me;  
		},
        /**
         *
         * @param index
         * @param tab
         * @returns {*}
         */
		before : function(index, tab) {
			var me = this,
			index = me.index(index);
			return me.add(index < 0 ? 0 : index - 1, tab);
		},
        /**
         *
         * @param index
         * @param tab
         * @returns {*}
         */
		after : function(index, tab) {
			var me = this,
			index = me.index(index);
			return me.add(index > me.tabs.length? me.tabs.length : index+1, tab);
		},
		/*
		* 返回缓存中存储的tab对象
		* @param: index or name
		*/
		get : function(index) {
			if(Q.type(index)=="object"){
				return index;
			}
			index=this.index(index);
			if(index!=-1)
			  return this.tabs[index];
		},
        /**
         *
         * @param index
         * @returns {number}
         */
		index:function(index){
			if(Q.type(index)=="number"){
				return 	index <0 ? -1 : index;
			}
			for(var i=0;i<this.tabs.length;i++){
			   var name=this.tabs[i].name;
			   if(name&&name==index){
				   return i;
			   }
		   }
		   return -1;
		},
        /**
         *
         * @param index
         * @returns {*}
         */
		enable : function(index) {
			var me = this;
			var tab = me.get(index);
			if(tab)
			  tab.removeClass("disabled");
			return me;
		},
        /**
         *
         * @param index
         * @returns {*}
         */
		disable : function(index) {
			var me = this;
			var tab = me.get(index);
			if(tab)
			   tab.addClass("disabled");
			return me;
		},
        /**
         *
         * @param index
         * @returns {*}
         */
		show : function(index) {
			var me = this;
			var tab = me.get(index);	
			if(!tab||tab.tab.hasClass("disabled,active")){
			    return me;
			}
			if(!tab.view.loaded||tab.once!=="true"){
				if(Q.isEmpty(tab.mode)|| tab.remote==="false"){
					tab.view.loaded=true;
					if(tab.mode=="hash"){
						me.hash(tab.hash||tab.url);
					}
				}else{
				  me.render(tab);
				}
			}else{
				if(tab.mode=="hash"){
					me.hash(tab.hash||tab.url);
				}
			}
			me.hide(me.header.find("li.active").index());
			tab.view.show();
			tab.tab.addClass("active");
			/*触发子选项卡时间*/
			tab.tab.trigger("subheader");
			return me;
		},
        /**
         *
         * @param index
         * @returns {*}
         */
		hide : function(index) {
			var me = this;
			var tab = me.get(index);
			if(!tab)return this;
			tab.view.hide();
			tab.tab.removeClass("active");
			return this;
		},
        /**
         * 渲染页面
         * @param tab
         * @param param
         */
		render : function(tab,param) {
			var me=this,url=tab.url;
			switch(tab.mode){
					case "hash":
				          url=me.hash(url);
					case "ajax":
						  tab.view.load(url,param,function(){ 						  
						        tab.view.loaded=true;
						        tab.view.find("[param]").on("click",function(event){
									tab.url="#!"+$(this).attr("href");
									me.refresh(tab);
									event.preventDefault();
								});
						  });
					     break;
					case "pajx":
					     break;
			}
			 
		},
        /**
         *
         * @param url
         * @returns {string}
         */
        hash:function(url){
			url = url.substring(url.indexOf("#!") + 2);
			location.hash = "#!"+url;
			return url;
		},
        /**
         * 刷新某个选卡
         * @param index
         * @param param
         * @returns {*}
         */
        refresh : function(index,param) {
			var me = this;
			var tab=me.get(index);
			if(!tab)return this;
			me.render(tab,param);
			return this;
		},
		resize : function() {

		},
		destroy : function() {

		},
		/*批量打包数据
		* 
		* @param {Array} indexs
		**/
		pack:function(indexs){
		     
		}
	}


	$.fn.tabnavigator = function(options) {
		return this.each(function() {
			if (!this.tabnavigator) {
				this.tabnavigator = new Tabnavigator(this, options);				            /**/
				if(!this.tabnavigator.root.data("marked")){
				    this.tabnavigator.show(0);
				}
			}
			return this;
		});
	}
})(jQuery);
/*
 * 1、常用于广告，图片，当然也可以用于页面，可以自动控制页面的任何元素
 */
;(function($) {
	/*
	 * @param s
	 * @param options
	 */
	var Slider = function(s, options) {
		var me = this, $this = $(s);
		me.options=options,
		me.main = $this.find(options.main), 
		me.size = me.main.length, me.activeIndex = options.activeIndex - 1;
		var effect=$.slider.getEffect(options.effect);
		var showEffect = effect.showEffect?effect.showEffect:$.noop();
	    var hoverEffect = effect.hoverEffect?effect.hoverEffect:$.noop();
		var d = {
			show : function(index) {
				if (index > me.size - 1) {
					index = 0;
				}
				if (index < 0) {
					index = me.size - 1;
				}
				showEffect(me, index);
				me.activeIndex = index;
				if (me.thumb) {
					me.thumb.removeClass("active");
					me.thumb.eq(me.activeIndex).addClass("active");
				}
			},
			prev : function() {
				d.show(me.activeIndex - 1);
			},
			next : function() {
				d.show(me.activeIndex + 1);
			},
			start : function() {
				if (!self.timeId)
					self.timeId = window.setInterval(function() {
						d.next();
					}, options.interval)
			},
			stop : function() {
				window.clearInterval(self.timeId);
				self.timeId = false;
			}
		};
		/*默认显示的元素*/
		d.show(me.activeIndex);
		/*自动运行*/
		d.start();
		/*鼠标离开时自动开始运行*/
		$this.mouseleave(function() {
			d.start();
		}).mouseover(function() {
			d.stop();
			hoverEffect(me);
		});

		/*thumb处理*/
		if (options.thumb) {
			me.thumb = $this.find(options.thumb);
			me.thumb.click(function() {
				d.show($(this).index());
				me.thumb.removeClass("active");
				$(this).addClass("active");
			});
		}
	}
	/*所有定义的特效,用户可以自行扩展*/
	$.slider = {
		getEffect : function(t) {
			return $.type(t) == "object" ? t : $.slider.effect[t];
		},
		effect : {
			"default" : {
				showEffect : function(me, index) {
					me.main.eq(me.activeIndex).hide().find(me.options.text).hide();
					me.main.eq(index).fadeIn(function() {
						var text = $(this).find(me.options.text);
						text.css("left", -text.width()).show();
						text.animate({
							left : 0,
							display : "block"
						}, 500);
					});
				},
				hoverEffect : function(me) {

				}
			}
		}
	};
	/*定义插件*/
	$.fn.slider = function(options) {
		options = $.extend({
			activeIndex : 1, /*默认激活的对象，从1开始*/
			interval : 5000, /*运行的间隔时间*/
			main : ".slider-main-box", /*主图列表*/
			text : undefined, /*文字区域*/
			thumb : undefined, /*缩略图区域*/
			timeline : false, /*是否有时间状态*/
			effect : "default",/*默认效果，当然也可以直接使用方法定义*/
			lazy:true
		}, options);

		return this.each(function() {
			var self = this;
			if (!self.slider)
				self.slider = new Slider(self, options);
			return self;
		});
	}
})(jQuery);
/*!
	jQuery ColorBox v1.4.4 - 2013-03-10
	(c) 2013 Jack Moore - jacklmoore.com/colorbox
	license: http://www.opensource.org/licenses/mit-license.php
*/
(function ($, document, window) {
	var
	// Default settings object.
	// See http://jacklmoore.com/colorbox for details.
	defaults = {
		transition: "elastic",
		speed: 300,
		width: false,
		initialWidth: "600",
		innerWidth: false,
		maxWidth: false,
		height: false,
		initialHeight: "450",
		innerHeight: false,
		maxHeight: false,
		scalePhotos: true,
		scrolling: true,
		inline: false,
		html: false,
		iframe: false,
		fastIframe: true,
		photo: true,
		href: false,
		title: false,
		rel: false,
		opacity: 0.9,
		preloading: true,
		className: false,
		
		// alternate image paths for high-res displays
		retinaImage: false,
		retinaUrl: false,
		retinaSuffix: '@2x.$1',

		// internationalization
		current: "第{current}/{total}张",
		previous: "上一张",
		next: "下一张",
		close: "关闭",
		xhrError: "网络繁忙，请求失败",
		imgError: "图片加载失败",

		open: false,
		returnFocus: true,
		reposition: true,
		loop: true,
		slideshow: false,
		slideshowAuto: true,
		slideshowSpeed: 2500,
		slideshowStart: "自动播放",
		slideshowStop: "停止",
		photoRegex: /\.(gif|png|jp(e|g|eg)|bmp|ico)((#|\?).*)?$/i,

		onOpen: false,
		onLoad: false,
		onComplete: false,
		onCleanup: false,
		onClosed: false,
		overlayClose: true,
		escKey: true,
		arrowKey: true,
		top: false,
		bottom: false,
		left: false,
		right: false,
		fixed: false,
		data: undefined
	},
	
	// Abstracting the HTML and event identifiers for easy rebranding
	colorbox = 'colorbox',
	prefix = 'cbox',
	boxElement = prefix + 'Element',
	
	// Events
	event_open = prefix + '_open',
	event_load = prefix + '_load',
	event_complete = prefix + '_complete',
	event_cleanup = prefix + '_cleanup',
	event_closed = prefix + '_closed',
	event_purge = prefix + '_purge',
	
	// Special Handling for IE
	isIE = !$.support.leadingWhitespace, // IE6 to IE8
	isIE6 = isIE && !window.XMLHttpRequest, // IE6
	event_ie6 = prefix + '_IE6',

	// Cached jQuery Object Variables
	$overlay,
	$box,
	$wrap,
	$content,
	$topBorder,
	$leftBorder,
	$rightBorder,
	$bottomBorder,
	$related,
	$window,
	$loaded,
	$loadingBay,
	$loadingOverlay,
	$title,
	$current,
	$slideshow,
	$next,
	$prev,
	$close,
	$groupControls,
	$events = $({}),
	
	// Variables for cached values or use across multiple functions
	settings,
	interfaceHeight,
	interfaceWidth,
	loadedHeight,
	loadedWidth,
	element,
	index,
	photo,
	open,
	active,
	closing,
	loadingTimer,
	publicMethod,
	div = "div",
	className,
	requests = 0,
	init;

	// ****************
	// HELPER FUNCTIONS
	// ****************
	
	// Convience function for creating new jQuery objects
	function $tag(tag, id, css) {
		var element = document.createElement(tag);

		if (id) {
			element.id = prefix + id;
		}

		if (css) {
			element.style.cssText = css;
		}

		return $(element);
	}
	
	// Get the window height using innerHeight when available to avoid an issue with iOS
	// http://bugs.jquery.com/ticket/6724
	function winheight() {
		return window.innerHeight ? window.innerHeight : $(window).height();
	}

	// Determine the next and previous members in a group.
	function getIndex(increment) {
		var
		max = $related.length,
		newIndex = (index + increment) % max;
		
		return (newIndex < 0) ? max + newIndex : newIndex;
	}

	// Convert '%' and 'px' values to integers
	function setSize(size, dimension) {
		return Math.round((/%/.test(size) ? ((dimension === 'x' ? $window.width() : winheight()) / 100) : 1) * parseInt(size, 10));
	}
	
	// Checks an href to see if it is a photo.
	// There is a force photo option (photo: true) for hrefs that cannot be matched by the regex.
	function isImage(settings, url) {
		return settings.photo || settings.photoRegex.test(url);
	}

	function retinaUrl(settings, url) {
		return settings.retinaUrl && window.devicePixelRatio > 1 ? url.replace(settings.photoRegex, settings.retinaSuffix) : url;
	}

	function trapFocus(e) {
		if ('contains' in $box[0] && !$box[0].contains(e.target)) {
			e.stopPropagation();
			$box.focus();
		}
	}

	// Assigns function results to their respective properties
	function makeSettings() {
		var i,
			data = $.data(element, colorbox);
		
		if (data == null) {
			settings = $.extend({}, defaults);
			if (console && console.log) {
				console.log('Error: cboxElement missing settings object');
			}
		} else {
			settings = $.extend({}, data);
		}
		
		for (i in settings) {
			if ($.isFunction(settings[i]) && i.slice(0, 2) !== 'on') { // checks to make sure the function isn't one of the callbacks, they will be handled at the appropriate time.
				settings[i] = settings[i].call(element);
			}
		}
		
		settings.rel = settings.rel || element.rel || $(element).data('rel') || 'nofollow';
		settings.href = settings.href || $(element).attr('href');
		settings.title = settings.title || element.title;
		
		if (typeof settings.href === "string") {
			settings.href = $.trim(settings.href);
		}
	}

	function trigger(event, callback) {
		// for external use
		$(document).trigger(event);

		// for internal use
		$events.trigger(event);

		if ($.isFunction(callback)) {
			callback.call(element);
		}
	}

	// Slideshow functionality
	function slideshow() {
		var
		timeOut,
		className = prefix + "Slideshow_",
		click = "click." + prefix,
		clear,
		set,
		start,
		stop;
		
		if (settings.slideshow && $related[1]) {
			clear = function () {
				clearTimeout(timeOut);
			};

			set = function () {
				if (settings.loop || $related[index + 1]) {
					timeOut = setTimeout(publicMethod.next, settings.slideshowSpeed);
				}
			};

			start = function () {
				$slideshow
					.html(settings.slideshowStop)
					.unbind(click)
					.one(click, stop);

				$events
					.bind(event_complete, set)
					.bind(event_load, clear)
					.bind(event_cleanup, stop);

				$box.removeClass(className + "off").addClass(className + "on");
			};
			
			stop = function () {
				clear();
				
				$events
					.unbind(event_complete, set)
					.unbind(event_load, clear)
					.unbind(event_cleanup, stop);
				
				$slideshow
					.html(settings.slideshowStart)
					.unbind(click)
					.one(click, function () {
						publicMethod.next();
						start();
					});

				$box.removeClass(className + "on").addClass(className + "off");
			};
			
			if (settings.slideshowAuto) {
				start();
			} else {
				stop();
			}
		} else {
			$box.removeClass(className + "off " + className + "on");
		}
	}

	function launch(target) {
		if (!closing) {
			
			element = target;
			
			makeSettings();
			
			$related = $(element);
			
			index = 0;
			
			if (settings.rel !== 'nofollow') {
				$related = $('.' + boxElement).filter(function () {
					var data = $.data(this, colorbox),
						relRelated;

					if (data) {
						relRelated =  $(this).data('rel') || data.rel || this.rel;
					}
					
					return (relRelated === settings.rel);
				});
				index = $related.index(element);
				
				// Check direct calls to ColorBox.
				if (index === -1) {
					$related = $related.add(element);
					index = $related.length - 1;
				}
			}
			
            $overlay.css({
                opacity: parseFloat(settings.opacity),
                cursor: settings.overlayClose ? "pointer" : "auto",
                visibility: 'visible'
            }).show();
            
			if (!open) {
				open = active = true; // Prevents the page-change action from queuing up if the visitor holds down the left or right keys.
				
				// Show colorbox so the sizes can be calculated in older versions of jQuery
				$box.css({visibility:'hidden', display:'block'});
				
				$loaded = $tag(div, 'LoadedContent', 'width:0; height:0; overflow:hidden').appendTo($content);

				// Cache values needed for size calculations
				interfaceHeight = $topBorder.height() + $bottomBorder.height() + $content.outerHeight(true) - $content.height();//Subtraction needed for IE6
				interfaceWidth = $leftBorder.width() + $rightBorder.width() + $content.outerWidth(true) - $content.width();
				loadedHeight = $loaded.outerHeight(true);
				loadedWidth = $loaded.outerWidth(true);
				
				
				// Opens inital empty ColorBox prior to content being loaded.
				settings.w = setSize(settings.initialWidth, 'x');
				settings.h = setSize(settings.initialHeight, 'y');
				publicMethod.position();

				if (isIE6) {
					$window.bind('resize.' + event_ie6 + ' scroll.' + event_ie6, function () {
						$overlay.css({width: $window.width(), height: winheight(), top: $window.scrollTop(), left: $window.scrollLeft()});
					}).trigger('resize.' + event_ie6);
				}
				
				slideshow();

				trigger(event_open, settings.onOpen);
				
				$groupControls.add($title).hide();
				
				$close.html(settings.close).show();

                $box.focus();
                
                // Confine focus to the modal
                // Uses event capturing that is not supported in IE8-
                if (document.addEventListener) {

                    document.addEventListener('focus', trapFocus, true);
                    
                    $events.one(event_closed, function () {
                        document.removeEventListener('focus', trapFocus, true);
                    });
                }

                // Return focus on closing
                if (settings.returnFocus) {
                    $events.one(event_closed, function () {
                        $(element).focus();
                    });
                }
			}
			
			publicMethod.load(true);
		}
	}

	// ColorBox's markup needs to be added to the DOM prior to being called
	// so that the browser will go ahead and load the CSS background images.
	function appendHTML() {
		if (!$box && document.body) {
			init = false;

			$window = $(window);
			$box = $tag(div).attr({
                id: colorbox,
                'class': isIE ? prefix + (isIE6 ? 'IE6' : 'IE') : '',
                role: 'dialog',
                tabindex: '-1'
            }).hide();
			$overlay = $tag(div, "Overlay", isIE6 ? 'position:absolute' : '').hide();
			$loadingOverlay = $tag(div, "LoadingOverlay").add($tag(div, "LoadingGraphic"));
			$wrap = $tag(div, "Wrapper");
			$content = $tag(div, "Content").append(
				$title = $tag(div, "Title"),
				$current = $tag(div, "Current"),
                $prev = $tag('button', "Previous"),
				$next = $tag('button', "Next"),
				$slideshow = $tag('button', "Slideshow"),
                $loadingOverlay,
				$close = $tag('button', "Close")
			);
			
			$wrap.append( // The 3x3 Grid that makes up ColorBox
				$tag(div).append(
					$tag(div, "TopLeft"),
					$topBorder = $tag(div, "TopCenter"),
					$tag(div, "TopRight")
				),
				$tag(div, false, 'clear:left').append(
					$leftBorder = $tag(div, "MiddleLeft"),
					$content,
					$rightBorder = $tag(div, "MiddleRight")
				),
				$tag(div, false, 'clear:left').append(
					$tag(div, "BottomLeft"),
					$bottomBorder = $tag(div, "BottomCenter"),
					$tag(div, "BottomRight")
				)
			).find('div div').css({'float': 'left'});
			
			$loadingBay = $tag(div, false, 'position:absolute; width:9999px; visibility:hidden; display:none');
			
			$groupControls = $next.add($prev).add($current).add($slideshow);

			$(document.body).append($overlay, $box.append($wrap, $loadingBay));
		}
	}

	// Add ColorBox's event bindings
	function addBindings() {
		function clickHandler(e) {
			// ignore non-left-mouse-clicks and clicks modified with ctrl / command, shift, or alt.
			// See: http://jacklmoore.com/notes/click-events/
			if (!(e.which > 1 || e.shiftKey || e.altKey || e.metaKey)) {
				e.preventDefault();
				launch(this);
			}
		}

		if ($box) {
			if (!init) {
				init = true;

				// Anonymous functions here keep the public method from being cached, thereby allowing them to be redefined on the fly.
				$next.click(function () {
					publicMethod.next();
				});
				$prev.click(function () {
					publicMethod.prev();
				});
				$close.click(function () {
					publicMethod.close();
				});
				$overlay.click(function () {
					if (settings.overlayClose) {
						publicMethod.close();
					}
				});
				
				// Key Bindings
				$(document).bind('keydown.' + prefix, function (e) {
					var key = e.keyCode;
					if (open && settings.escKey && key === 27) {
						e.preventDefault();
						publicMethod.close();
					}
                    if (open && settings.arrowKey && $related[1] && !e.altKey) {
						if (key === 37) {
							e.preventDefault();
							$prev.click();
						} else if (key === 39) {
							e.preventDefault();
							$next.click();
						}
					}
				});

				if ($.isFunction($.fn.on)) {
					// For jQuery 1.7+
					$(document).on('click.'+prefix, '.'+boxElement, clickHandler);
				} else {
					// For jQuery 1.3.x -> 1.6.x
					// This code is never reached in jQuery 1.9, so do not contact me about 'live' being removed.
					// This is not here for jQuery 1.9, it's here for legacy users.
					$('.'+boxElement).live('click.'+prefix, clickHandler);
				}
			}
			return true;
		}
		return false;
	}

	// Don't do anything if ColorBox already exists.
	if ($.colorbox) {
		return;
	}

	// Append the HTML when the DOM loads
	$(appendHTML);


	// ****************
	// PUBLIC FUNCTIONS
	// Usage format: $.fn.colorbox.close();
	// Usage from within an iframe: parent.$.fn.colorbox.close();
	// ****************
	
	publicMethod = $.fn[colorbox] = $[colorbox] = function (options, callback) {
		var $this = this;
		
		options = options || {};
		
		appendHTML();

		if (addBindings()) {
			if ($.isFunction($this)) { // assume a call to $.colorbox
				$this = $('<a/>');
				options.open = true;
			} else if (!$this[0]) { // colorbox being applied to empty collection
				return $this;
			}
			
			if (callback) {
				options.onComplete = callback;
			}
			
			$this.each(function () {
				$.data(this, colorbox, $.extend({}, $.data(this, colorbox) || defaults, options));
			}).addClass(boxElement);
			
			if (($.isFunction(options.open) && options.open.call($this)) || options.open) {
				launch($this[0]);
			}
		}
		
		return $this;
	};

	publicMethod.position = function (speed, loadedCallback) {
		var
		css,
		top = 0,
		left = 0,
		offset = $box.offset(),
		scrollTop,
		scrollLeft;
		
		$window.unbind('resize.' + prefix);

		// remove the modal so that it doesn't influence the document width/height
		$box.css({top: -9e4, left: -9e4});

		scrollTop = $window.scrollTop();
		scrollLeft = $window.scrollLeft();

		if (settings.fixed && !isIE6) {
			offset.top -= scrollTop;
			offset.left -= scrollLeft;
			$box.css({position: 'fixed'});
		} else {
			top = scrollTop;
			left = scrollLeft;
			$box.css({position: 'absolute'});
		}

		// keeps the top and left positions within the browser's viewport.
		if (settings.right !== false) {
			left += Math.max($window.width() - settings.w - loadedWidth - interfaceWidth - setSize(settings.right, 'x'), 0);
		} else if (settings.left !== false) {
			left += setSize(settings.left, 'x');
		} else {
			left += Math.round(Math.max($window.width() - settings.w - loadedWidth - interfaceWidth, 0) / 2);
		}
		
		if (settings.bottom !== false) {
			top += Math.max(winheight() - settings.h - loadedHeight - interfaceHeight - setSize(settings.bottom, 'y'), 0);
		} else if (settings.top !== false) {
			top += setSize(settings.top, 'y');
		} else {
			top += Math.round(Math.max(winheight() - settings.h - loadedHeight - interfaceHeight, 0) / 2);
		}

		$box.css({top: offset.top, left: offset.left, visibility:'visible'});

		// setting the speed to 0 to reduce the delay between same-sized content.
		speed = ($box.width() === settings.w + loadedWidth && $box.height() === settings.h + loadedHeight) ? 0 : speed || 0;
		
		// this gives the wrapper plenty of breathing room so it's floated contents can move around smoothly,
		// but it has to be shrank down around the size of div#colorbox when it's done.  If not,
		// it can invoke an obscure IE bug when using iframes.
		$wrap[0].style.width = $wrap[0].style.height = "9999px";
		
		function modalDimensions(that) {
			$topBorder[0].style.width = $bottomBorder[0].style.width = $content[0].style.width = (parseInt(that.style.width,10) - interfaceWidth)+'px';
			$content[0].style.height = $leftBorder[0].style.height = $rightBorder[0].style.height = (parseInt(that.style.height,10) - interfaceHeight)+'px';
		}

		css = {width: settings.w + loadedWidth + interfaceWidth, height: settings.h + loadedHeight + interfaceHeight, top: top, left: left};

		if(speed===0){ // temporary workaround to side-step jQuery-UI 1.8 bug (http://bugs.jquery.com/ticket/12273)
			$box.css(css);
		}
		$box.dequeue().animate(css, {
			duration: speed,
			complete: function () {
				modalDimensions(this);
				
				active = false;
				
				// shrink the wrapper down to exactly the size of colorbox to avoid a bug in IE's iframe implementation.
				$wrap[0].style.width = (settings.w + loadedWidth + interfaceWidth) + "px";
				$wrap[0].style.height = (settings.h + loadedHeight + interfaceHeight) + "px";
				
				if (settings.reposition) {
					setTimeout(function () {  // small delay before binding onresize due to an IE8 bug.
						$window.bind('resize.' + prefix, publicMethod.position);
					}, 1);
				}

				if (loadedCallback) {
					loadedCallback();
				}
			},
			step: function () {
				modalDimensions(this);
			}
		});
	};

	publicMethod.resize = function (options) {
		if (open) {
			options = options || {};
			
			if (options.width) {
				settings.w = setSize(options.width, 'x') - loadedWidth - interfaceWidth;
			}
			if (options.innerWidth) {
				settings.w = setSize(options.innerWidth, 'x');
			}
			$loaded.css({width: settings.w});
			
			if (options.height) {
				settings.h = setSize(options.height, 'y') - loadedHeight - interfaceHeight;
			}
			if (options.innerHeight) {
				settings.h = setSize(options.innerHeight, 'y');
			}
			if (!options.innerHeight && !options.height) {
				$loaded.css({height: "auto"});
				settings.h = $loaded.height();
			}
			$loaded.css({height: settings.h});
			
			publicMethod.position(settings.transition === "none" ? 0 : settings.speed);
		}
	};

	publicMethod.prep = function (object) {
		if (!open) {
			return;
		}
		
		var callback, speed = settings.transition === "none" ? 0 : settings.speed;

		$loaded.empty().remove(); // Using empty first may prevent some IE7 issues.

		$loaded = $tag(div, 'LoadedContent').append(object);
		
		function getWidth() {
			settings.w = settings.w || $loaded.width();
			settings.w = settings.mw && settings.mw < settings.w ? settings.mw : settings.w;
			return settings.w;
		}
		function getHeight() {
			settings.h = settings.h || $loaded.height();
			settings.h = settings.mh && settings.mh < settings.h ? settings.mh : settings.h;
			return settings.h;
		}
		
		$loaded.hide()
		.appendTo($loadingBay.show())// content has to be appended to the DOM for accurate size calculations.
		.css({width: getWidth(), overflow: settings.scrolling ? 'auto' : 'hidden'})
		.css({height: getHeight()})// sets the height independently from the width in case the new width influences the value of height.
		.prependTo($content);
		
		$loadingBay.hide();
		
		// floating the IMG removes the bottom line-height and fixed a problem where IE miscalculates the width of the parent element as 100% of the document width.
		
		$(photo).css({'float': 'none'});

		callback = function () {
			var total = $related.length,
				iframe,
				frameBorder = 'frameBorder',
				allowTransparency = 'allowTransparency',
				complete;
			
			if (!open) {
				return;
			}
			
			function removeFilter() {
				if (isIE) {
					$box[0].style.removeAttribute('filter');
				}
			}
			
			complete = function () {
				clearTimeout(loadingTimer);
				$loadingOverlay.hide();
				trigger(event_complete, settings.onComplete);
			};
			
			if (isIE) {
				//This fadeIn helps the bicubic resampling to kick-in.
				if (photo) {
					$loaded.fadeIn(100);
				}
			}
			
			$title.html(settings.title).add($loaded).show();
			
			if (total > 1) { // handle grouping
				if (typeof settings.current === "string") {
					$current.html(settings.current.replace('{current}', index + 1).replace('{total}', total)).show();
				}
				
				$next[(settings.loop || index < total - 1) ? "show" : "hide"]().html(settings.next);
				$prev[(settings.loop || index) ? "show" : "hide"]().html(settings.previous);
				
				if (settings.slideshow) {
					$slideshow.show();
				}
				
				// Preloads images within a rel group
				if (settings.preloading) {
					$.each([getIndex(-1), getIndex(1)], function(){
						var src,
							img,
							i = $related[this],
							data = $.data(i, colorbox);

						if (data && data.href) {
							src = data.href;
							if ($.isFunction(src)) {
								src = src.call(i);
							}
						} else {
							src = $(i).attr('href');
						}

						if (src && isImage(data, src)) {
							src = retinaUrl(data, src);
							img = new Image();
							img.src = src;
						}
					});
				}
			} else {
				$groupControls.hide();
			}
			
			if (settings.iframe) {
				iframe = $tag('iframe')[0];
				
				if (frameBorder in iframe) {
					iframe[frameBorder] = 0;
				}
				
				if (allowTransparency in iframe) {
					iframe[allowTransparency] = "true";
				}

				if (!settings.scrolling) {
					iframe.scrolling = "no";
				}
				
				$(iframe)
					.attr({
						src: settings.href,
						name: (new Date()).getTime(), // give the iframe a unique name to prevent caching
						'class': prefix + 'Iframe',
						allowFullScreen : true, // allow HTML5 video to go fullscreen
						webkitAllowFullScreen : true,
						mozallowfullscreen : true
					})
					.one('load', complete)
					.appendTo($loaded);
				
				$events.one(event_purge, function () {
					iframe.src = "//about:blank";
				});

				if (settings.fastIframe) {
					$(iframe).trigger('load');
				}
			} else {
				complete();
			}
			
			if (settings.transition === 'fade') {
				$box.fadeTo(speed, 1, removeFilter);
			} else {
				removeFilter();
			}
		};
		
		if (settings.transition === 'fade') {
			$box.fadeTo(speed, 0, function () {
				publicMethod.position(0, callback);
			});
		} else {
			publicMethod.position(speed, callback);
		}
	};

	publicMethod.load = function (launched) {
		var href, setResize, prep = publicMethod.prep, $inline, request = ++requests;
		
		active = true;
		
		photo = false;
		
		element = $related[index];
		
		if (!launched) {
			makeSettings();
		}

		if (className) {
			$box.add($overlay).removeClass(className);
		}
		if (settings.className) {
			$box.add($overlay).addClass(settings.className);
		}
		className = settings.className;
		
		trigger(event_purge);
		
		trigger(event_load, settings.onLoad);
		
		settings.h = settings.height ?
				setSize(settings.height, 'y') - loadedHeight - interfaceHeight :
				settings.innerHeight && setSize(settings.innerHeight, 'y');
		
		settings.w = settings.width ?
				setSize(settings.width, 'x') - loadedWidth - interfaceWidth :
				settings.innerWidth && setSize(settings.innerWidth, 'x');
		
		// Sets the minimum dimensions for use in image scaling
		settings.mw = settings.w;
		settings.mh = settings.h;
		
		// Re-evaluate the minimum width and height based on maxWidth and maxHeight values.
		// If the width or height exceed the maxWidth or maxHeight, use the maximum values instead.
		if (settings.maxWidth) {
			settings.mw = setSize(settings.maxWidth, 'x') - loadedWidth - interfaceWidth;
			settings.mw = settings.w && settings.w < settings.mw ? settings.w : settings.mw;
		}
		if (settings.maxHeight) {
			settings.mh = setSize(settings.maxHeight, 'y') - loadedHeight - interfaceHeight;
			settings.mh = settings.h && settings.h < settings.mh ? settings.h : settings.mh;
		}
		
		href = settings.href;
		
		loadingTimer = setTimeout(function () {
			$loadingOverlay.show();
		}, 100);
		
		if (settings.inline) {
			// Inserts an empty placeholder where inline content is being pulled from.
			// An event is bound to put inline content back when ColorBox closes or loads new content.
			$inline = $tag(div).hide().insertBefore($(href)[0]);

			$events.one(event_purge, function () {
				$inline.replaceWith($loaded.children());
			});

			prep($(href));
		} else if (settings.iframe) {
			// IFrame element won't be added to the DOM until it is ready to be displayed,
			// to avoid problems with DOM-ready JS that might be trying to run in that iframe.
			prep(" ");
		} else if (settings.html) {
			prep(settings.html);
		} else if (isImage(settings, href)) {

			href = retinaUrl(settings, href);

			$(photo = new Image())
			.addClass(prefix + 'Photo')
			.bind('error',function () {
				settings.title = false;
				prep($tag(div, 'Error').html(settings.imgError));
			})
			.one('load', function () {
				var percent;

				if (request !== requests) {
					return;
				}

				if (settings.retinaImage && window.devicePixelRatio > 1) {
					photo.height = photo.height / window.devicePixelRatio;
					photo.width = photo.width / window.devicePixelRatio;
				}

				if (settings.scalePhotos) {
					setResize = function () {
						photo.height -= photo.height * percent;
						photo.width -= photo.width * percent;
					};
					if (settings.mw && photo.width > settings.mw) {
						percent = (photo.width - settings.mw) / photo.width;
						setResize();
					}
					if (settings.mh && photo.height > settings.mh) {
						percent = (photo.height - settings.mh) / photo.height;
						setResize();
					}
				}
				
				if (settings.h) {
					photo.style.marginTop = Math.max(settings.mh - photo.height, 0) / 2 + 'px';
				}
				
				if ($related[1] && (settings.loop || $related[index + 1])) {
					photo.style.cursor = 'pointer';
					photo.onclick = function () {
						publicMethod.next();
					};
				}
				
				if (isIE) {
					photo.style.msInterpolationMode = 'bicubic';
				}
				
				setTimeout(function () { // A pause because Chrome will sometimes report a 0 by 0 size otherwise.
					prep(photo);
				}, 1);
			});
			
			setTimeout(function () { // A pause because Opera 10.6+ will sometimes not run the onload function otherwise.
				photo.src = href;
				
			}, 1);
		} else if (href) {
			$loadingBay.load(href, settings.data, function (data, status) {
				if (request === requests) {
					prep(status === 'error' ? $tag(div, 'Error').html(settings.xhrError) : $(this).contents());
				}
			});
		}
	};
		
	// Navigates to the next page/image in a set.
	publicMethod.next = function () {
		if (!active && $related[1] && (settings.loop || $related[index + 1])) {
			index = getIndex(1);
			publicMethod.load();
		}
	};
	
	publicMethod.prev = function () {
		if (!active && $related[1] && (settings.loop || index)) {
			index = getIndex(-1);
			publicMethod.load();
		}
	};

	// Note: to use this within an iframe use the following format: parent.$.fn.colorbox.close();
	publicMethod.close = function () {
		if (open && !closing) {
			
			closing = true;
			
			open = false;
			
			trigger(event_cleanup, settings.onCleanup);
			
			$window.unbind('.' + prefix + ' .' + event_ie6);
			
			$overlay.fadeTo(200, 0);
			
			$box.stop().fadeTo(300, 0, function () {
			
				$box.add($overlay).css({'opacity': 1, cursor: 'auto'}).hide();
				
				trigger(event_purge);
				
				$loaded.empty().remove(); // Using empty first may prevent some IE7 issues.
				
				setTimeout(function () {
					closing = false;
					trigger(event_closed, settings.onClosed);
				}, 1);
			});
		}
	};

	// Removes changes ColorBox made to the document, but does not remove the plugin
	// from jQuery.
	publicMethod.remove = function () {
		$([]).add($box).add($overlay).remove();
		$box = null;
		$('.' + boxElement)
			.removeData(colorbox)
			.removeClass(boxElement);

		$(document).unbind('click.'+prefix);
	};

	// A method for fetching the current element ColorBox is referencing.
	// returns a jQuery object.
	publicMethod.element = function () {
		return $(element);
	};

	publicMethod.settings = defaults;

}(jQuery, document, window));
;/*
 * 向导模块，主要是未来引导用户进行一些操作，其中引导分为2为两种情况：
 *   一种是完全指示型的，用户只能查看引导，不能进行操作，一种是操作型的，根据用户的操作执行相应的步骤（相对较复杂）
 *
 * $.guide.add({group:"test",//向导所再的组，默认存储guide.#group#作为cookie记录值
 *		  start:true,//开始之前的验证
 *        modal:true,
 *        focus:true,
 *          steps:[{
 *			modal:true,//是否显示遮罩,默认值true
 *          focus:true,//是否自动获取焦点，默认值true
 *          attach:undefined,指向的区域，如果不制定则显示在屏幕中央,唯一，只能是1个ID
 *			hightlight:undefined,//高亮显示的区域，可以是多个区域
 *			content:"",//以#号开始代表区域ID
 *			url:"",//ajax载入的模块
 *		}],end:function(){
 *          return true;
 *		}//向导结束
 *	});
 *
 */
(function($) {

	var Guider = function(options) {
		var self = this, $this = $(this);
		/*当前运行的节点*/
		trace(" create guide " + options.group + ":" + options.steps.length)
		self.start = function(restart) {
			/*设定开始的标志*/
			self.begin = true;
			/*如果页面执行操作，将继续之前的操作*/
			self.activeIndex = ($.cookie("guider." + options.group) || 1) * 1;
			if (!restart && self.activeIndex == options.steps.length) {
				return;
			}
			$.when(options.start).then(function(result) {
				if (!result || result == "false") {
					/*标记为已验证*/
					self.checked = true;
					/*开始运行向导*/
					self.show(self.activeIndex);
				}
			});
		};
		self.end = function() {
			self.hide(self.activeIndex, true);
		};
		self.show = function(index) {
			if (index > options.steps.length) {
				self.end();
				/*结束了*/
			}
			if (!self.begin) {
				self.hide(index);
			} else {
				self.run(index);
				/*设定代表不是第一个开始的*/
				self.begin = false;
			}
		};

		self.hide = function(index, end) {
			Pop.hide($("#guide-" + options.group + "-" + self.activeIndex), {
				afterClose : function() {
					var s = options.steps[self.activeIndex - 1];
					if (s.hightlight) {
						$(s.hightlight).removeClass('guider-hightlight');
					}
					if (index <= options.steps.length && !end)
						self.run(index);
					else if (end && options.end) {
						$.cookie("guider." + options.group,options.steps.length) ;
						return options.end();
					}
				}
			});
		}

		self.run = function(index) {
			var step = options.steps[index - 1];
			Pop.show($.extend({
				id : "guide-" + options.group + "-" + index,
				className : "guider",
				theme : "top",
				modal : options.modal,
				focus : options.focus,
				template : function(options) {
					var html = [];
					html.push("<i class='arrow'><em>◆</em><span>◆</span></i>");
					html.push("<div class='pop-body' node-type='pop-body'></div>");
					if (options.footer) {
						html.push("<div class='popover-footer'>" + options.footer + "</div>");
					}
					return html.join("");
				},
				trigger : $(step.attach),
				effect : [
				function($pop, options, done) {
					var offset = options.trigger.offset();
					$pop.css({
						top : offset.top + options.trigger.outerHeight() + 15,
						left : offset.left
					}).show();
				},
				function($pop, options, done) {
					$pop.hide(done);
				}],
				afterShow : function($pop, op) {
					$pop.find("[guide-type=next]").click(function() {
						self.next();
					});
					$pop.find("[guide-type=prev]").click(function() {
						self.prev();
					});
					$pop.find("[guide-type=end]").click(function() {
						self.end();
					});
					/*是否高亮显示*/
					if (step.hightlight)
						$(step.hightlight).addClass('guider-hightlight');
					/*如果标记的内容不在视野范围内，将自动滚动到该位置*/
					if (options.focus) {
						var attach = $(step.attach);
						var offset = attach.offset();
						var scrollToHeight = Math.round(Math.max(offset.top + attach.height() / 2 - $(window).height() / 2, 0));
						window.scrollTo(0, scrollToHeight);
					}
				}
			}, step));
			self.activeIndex = index;
			$.cookie("guider." + options.group, self.activeIndex);
		}

		self.prev = function() {
			self.show(self.activeIndex - 1);
		};
		self.next = function() {
			self.show(self.activeIndex + 1);
		}
	}

	$.guider = {
		_pool : {},
		add : function(options) {
			options = $.extend({
				start : false, /*标记为未验证*/
				modal : true,
				focus : true	/*如果标记的内容不在视野范围内，是否自动滚动到该位置，默认为true，step必须要设定attach才能生效*/
			}, options);
			$.guider._pool[options.group] = new Guider(options);
			return $.guider;
		},
		start : function(group) {
			$.guider._pool[group].start();
		},
		show : function(group, index) {
			$.guider._pool[group].show(index);
		},
		prev : function(group) {
			$.guider._pool[group].prev();
		},
		next : function(group) {
			$.guider._pool[group].next();
		},
		end : function(group) {
			$.guider._pool[group].end();
		}
	}
})(jQuery);
!(function($){
	var ColorPicker=function(c,o){
		var me=this;
		me.self=c;
		me.cache=[];
		me.$this=$(me.self).attr("readonly","readonly").css("cursor","pointer");
		if(c.type=="color"){
			c.type="text";
			c.orginalType = "color";
		}
		me.options=o;
		 $(c).on("focus.colorpicker",function(){
			   me.show();
		  });
	};
	
	ColorPicker.prototype={
		_td:function _td(v){
				return '<td><span style="background-color:#'+v+'" title="#'+v+'" data-color="#'+v+'"></span></td>';
		},
		init:function(){
			var me=this;
			me.root=$('<div class="colorpicker"></div>');
			me.color=$('<ul class="color"><li style="background-color:#31859b">#31859b</li><li>&nbsp;</li><li class="btn btn-primary" style="float:right" >清空颜色</li></ul>'); 
			me.color.find("li.btn").click(function(){
				me.clear();
			});
			var table=['<table class="colorpicker-table"><thead><tr><th colspan="10">主题颜色</th></tr></thead><tbody>'];			
			//base
			table.push('<tr class="color-default">');
			  $.each(me.options.baseThemeColors,function(){
				  table.push(me._td(this));
			  });
			table.push('</tr>')	;
			//subThemeColors
			table.push('<tr class="color-top">');
			for(var i=0;i<10;i++){
				table.push(me._td(me.options.subThemeColors[i]));
			}
			for(var r=1;r<4;r++){
			  table.push('</tr><tr>');
			  for(i=0;i<10;i++){ 
				 table.push(me._td(me.options.subThemeColors[r*10+i]));
			  }			
		    }
			table.push('</tr><tr class="color-bottom">');
		    for(i=40;i<50;i++){ 
			   	 table.push(me._td(me.options.subThemeColors[i]));
		    }
			
		    //standardColors
			table.push('<tr><th colspan="10">标准颜色</th></tr>');
			table.push('<tr class="color-default">');
			 for(i=0;i<10;i++){ 
			    table.push(me._td(me.options.standardColors[i]));
		    }
			table.push('</tr></tbody></table>')		
			me.table=$(table.join(""));
			me.on(me.table.find("td span"));
			me.footer=$('<div class="color-more"><a href="javascript:void(0)" data-type="more">更多颜色</a><a href="javascript:void(0)" data-type="history">历史</a></div>');
			me.footer.find("a").each(function(index, element) {
                $(this).on("click.colorpicker",function(){
					me[$(this).data("type")]();
				});
            });;
			me.root.append(me.color).append(me.table).append(me.footer);
			me.$this.after(me.root);
			me.isCreated=true;
			return me;
		},show:function(){			
			var me=this;
			if(me.isOpened){
				return me;
			}
			if(!me.isCreated){
				me.init();
			}
			var bg=me.$this.val();
			me.color.find("li:eq(0)").css("background-color",bg).text(bg);
			me.color.find("li:eq(1)").css("background-color","#F9F9F9").text("");
			var pos = me.$this.position();
			// iPad position fix
			if (/iPad/i.test(navigator.userAgent)) {
				pos.top -= $(window).scrollTop();
			}
			me.root.css({
				top : pos.top + me.$this.outerHeight(),
				left : pos.left
			});
			me.isOpened = true;
			me.root.show();
			return me;
		},hide:function(){
			/*only run when open*/
			if(this.isOpened){
				this.root.hide();
				this.isOpened=false;
			}
		},history:function(v){
			var me=this;
			/*存储值*/
			if(v!=null){
				me.cache.push(v);
				me.cache=me.cache.unique();
				if(me.cache.length>me.options.cache.max){
					me.cache.shift();
				}
			}else{
				//显示历史记录，存储在cookie中
				if(!me.htable){
					me.htable=$('<div><div class="color-divider"></div><ul class="color"></ul><div class="color-divider"></div></div>');
					me.table.after(me.htable);
					me.htable.ul=me.htable.find("ul");
				}
				me.htable.ul.empty();
				$.each(me.cache,function(){
					me.htable.ul.append('<li style="background-color:'+this+';cursor:pointer;" data-color="'+this+'">'+this+'</li>');
				});
				me.on(me.htable.ul.find("li"));
				me.table.hide();
				if(me.mtable)me.mtable.hide();
				me.htable.show();
			}
		},more:function(){
			var me=this;
			me.table.hide();
			if(me.htable)me.htable.hide();
			if(!me.mtable){
				var mtable=['<div class="color-divider"></div><div style="text-align:center">'];
				for(var i=0;i<me.options.moreColors.length;i++){
					var t=me.options.moreColors[i];
					mtable.push("<table><tbody><tr>")
					for(var j=0;j<t.length;j++){
						mtable.push(me._td(t[j]));
					}
					mtable.push("</tr></tbody></table>");
				}
				mtable.push("</div>")
				mtable.push('<div class="color-divider"></div>');
				
				mtable.push('<table><tbody><tr>');
				var t2=[];
		        for(var h=255;h>10;h-=10){
					var k=h.toString(16);
					k=k.length==1?('0'+k):k;
					mtable.push(me._td(k+k+k)); 
					h-=10;
					k=h.toString(16);
					k=k.length==1?('0'+k):k;
					t2.push(me._td(k+k+k));
		        }
				mtable.push("</tr><tr>"+t2.join(""));
                mtable.push("</tr></tbody></table>");
				me.mtable=$(mtable.join(""));
				me.table.after(me.mtable);
				me.on(me.mtable.find("td span"));
			}
			me.footer.find("a:eq(0)").data("type","less").text("简洁模式");
			me.mtable.show();
			me.root.addClass("more");
			return me;
		},less:function(){
			var me=this;
			me.mtable.hide();
			if(me.htable)me.htable.hide();
			me.table.show();
			me.footer.find("a:eq(0)").data("type","more").text("更多颜色");
			me.root.removeClass("more");
		},on:function(element){
			var me=this;
			element.on("mouseover.colorpicker",function(){
				     var bg=$(this).data("color");
				     me.color.find("li:eq(1)").css("background-color",bg).text(bg);
			      }).on("click.colorpicker",function(){
				      var bg=$(this).data("color");
				       me.$this.css("background-color",bg).val(bg);
				       me.history(bg);
				       me.hide();
			     });
		},clear:function(){
			 this.$this.css("background-color","#fff").val("");
		}
	}
	
	$.fn.colorpicker=function(options){
		options=$.extend({
			baseThemeColors:['ffffff','000000','eeece1','1f497d','4f81bd','c0504d','9bbb59','8064a2','4bacc6','f79646'],
			subThemeColors:['f2f2f2','7f7f7f','ddd9c3','c6d9f0','dbe5f1','f2dcdb','ebf1dd','e5e0ec','dbeef3','fdeada',
							'd8d8d8','595959','c4bd97','8db3e2','b8cce4','e5b9b7','d7e3bc','ccc1d9','b7dde8','fbd5b5',
							'bfbfbf','3f3f3f','938953','548dd4','95b3d7','d99694','c3d69b','b2a2c7','92cddc','fac08f',
							'a5a5a5','262626','494429','17365d','366092','953734','76923c','5f497a','31859b','e36c09',
							'7f7f7f','0c0c0c','1d1b10','0f243e','244061','632423','4f6128','3f3151','205867','974806'],
			standardColors:['c00000','ff0000','ffc000','ffff00','92d050','00b050','00b0f0','0070c0','002060','7030a0'],
			moreColors:[
				['003366','336699','3366cc','003399','000099','0000cc','000066'],
				['006666','006699','0099cc','0066cc','0033cc','0000ff','3333ff','333399'],
				['669999','009999','33cccc','00ccff','0099ff','0066ff','3366ff','3333cc','666699'],
				['339966','00cc99','00ffcc','00ffff','33ccff','3399ff','6699ff','6666ff','6600ff','6600cc'],
				['339933','00cc66','00ff99','66ffcc','66ffff','66ccff','99ccff','9999ff','9966ff','9933ff','9900ff'],
				['006600','00cc00','00ff00','66ff99','99ffcc','ccffff','ccccff','cc99ff','cc66ff','cc33ff','cc00ff','9900cc'],
				['003300','009933','33cc33','66ff66','99ff99','ccffcc','ffffff','ffccff','ff99ff','ff66ff','ff00ff','cc00cc','660066'],
				['333300','009900','66ff33','99ff66','ccff99','ffffcc','ffcccc','ff99cc','ff66cc','ff33cc','cc0099','993399'],
				['336600','669900','99ff33','ccff66','ffff99','ffcc99','ff9999','ff6699','ff3399','cc3399','990099'],
				['666633','99cc00','ccff33','ffff66','ffcc66','ff9966','ff6666','ff0066','d60094','993366'],
				['a58800','cccc00','ffff00','ffcc00','ff9933','ff6600','ff0033','cc0066','660033'],
				['996633','cc9900','ff9900','cc6600','ff3300','ff0000','cc0000','990033'],
				['663300','996600','cc3300','993300','990000','800000','993333']
			],
			cache:{
				max:15,
				mode:"default" //default|cookie
			}
		},options);
		return this.each(function(){
			var self=this;
			if(!self.colorpicker){
				self.colorpicker=new ColorPicker(self,options);
				$(document).on("click.colorpicker", function(e) {
					if (e.target != self &&$(e.target).closest("div.colorpicker").length == 0) {
						self.colorpicker.hide();
					}
				})
			}
			return this;
		});
	}
})(jQuery);/**
 *  该表格主要针对互联网开发使用（没有企业空间固定的模式），灵活度和样式定义度更高，用户可以随意修改表格的样式，响应模式，以适应不同的系统环境
 *1、主要功能表格显示数据;
 *2、排序，单排序，多排序，排序清除
 *3、数据过滤，行过滤，单元格点击过滤，绑定form过滤
 *4、分页
 *5、checkhox
 *6、行单选及多选功能
 *7、 数据分组
 
 
 
 
 * <table  action="http://" method="get|post" type="json|xml|jsonp"
 checkable="true"
 editable="false"
 max-checked="5"
 min-checked="1"
 rowclick=""
 rowdblclick=""
 cellclick="function(row,cell){}"
 celldblclick="function(row,cell){}"
 endless ="false" 滚动到末尾是否自动加载数据
 >
 *    <thead>
 <tr>
 <th sortable="true"
 dragable="false"
 resizeable="true"
 name="id"
 selectedable="false" 列是否可选
 >field</th>
 <th>field</th>
 <th>field</th>
 </tr>
 *     </thead>
 *     <tbody

 >
 *     </tbody>
 *  <footer>
 </footer>
 </table>
 *
 * 如何定义数据模板？
 *  <tbody>
 *  	  <tr class="template" onclick="alert('定义常规事件，这里和在table上定义的一样')">
 *<td>我看看我这个模版好用不[name][value][text]</td>
 *<td>无[name][value][text]</td>
 *<td><a href="?a=[name]">[name][value][text]</a></td>
 *</tr>
 *</tbody>
 *
 */
;(function() {
	Grid = function(table, options) {
		this.monitor = {
			init : {
				start : new Date().getTime()
			}
		};
		var t = $(table);
		options = $.extend({
			checkable : t.attr("checkable"), /*是否显示checkbox列*/
			resizeable : t.attr("resizeable"), /*是否允许调整列宽度*/
			editable : t.attr("editable"), /*是否可编辑*/
			numberable:t.attr("numberable"),/*是否显示序号列,这个默认显示在行首*/
			frozen:t.attr("frozen")||0,/*锁定的列宽*/
			checked : {
				min : t.attr("min-checked"), /*checkable==true时有效，最少选择的数据*/
				max : t.attr("max-checked")/*checkable==true时有效，最多选择的数据*/
			},
			action : t.attr("action"), /*url*/
			method : t.attr("method") || "POST", /*提交数据的方法，默认使用post，安全性更高，提交数据更大，如无特殊用途建议不要修改*/
			dataType : t.attr("dataType") || "json",
			load : t.attr("load")!="false", /*是否自动加载数据*/
			cacheable : t.attr("cacheable"), /*是否允许数据缓存*/
			pageable : t.attr("pageable"), /*是否有分页*/
			multiSort : t.attr("multi-sort"), /*是否允许多排序，启用该模式，排序不会自动清除*/
			multiSelected : t.attr("multi-selected"), /*是否允许多选，多选必须使用ctrl方式*/
			dragable : false,/*是否允许列拖拽*/
			stateable:false,/*是否缓存表格状态，刷新时可以自动恢复表格状态及表格关联的信息*/
			highlight:false,/*是否高亮鼠标位置的行与列*/
			page : {
				name : {
					page : "p", /*提交给服务器的页面参数名*/
					size : "size"/*提交给服务器的参数名*/
				},
				index : 1, /*当前页编号*/
				size : 50, /*默认分页大小*/
				fill:false,/*当当前页面数据不足50条时是否自动填充*/
				list : [50, 100, 200, 500, 1000], /*每页显示的数据记录数*/
				template : function(me,index,size){	
				    var o=me.options.page;
				    /*代表分页初始化*/
				    if(!index){
						index=1;
						size=o.size;
						me.pagebar=$('<div class="grid-pager"></div>');
						me.pagebar.select=$('<span class="page-size"><i>'+size+'</i></span>');
						me.pagebar.select.size=me.pagebar.select.find("i").on("click",function(){
						    if(!this.loaded){
								var div=['<div class="size-list">'];
								$.each(me.options.page.list,function(){
									div.push("<span>"+this+"</span>");
								});
								div.push("</div>");
								me.pagebar.select.list=$(div.join(""));
								me.pagebar.select.append(me.pagebar.select.list);
								me.pagebar.select.list.css({
									left:-(me.pagebar.select.list.width()-me.pagebar.select.width())/2
								}).find("span").click(function(){
									me.pagebar.select.size.text($(this).text());
									me.page(1,$(this).text()*1);
									me.pagebar.select.list.hide();
								});
								this.loaded=true;
							}
							me.pagebar.select.list.show();
						});
						me.pagebar.append(me.pagebar.select);
						me.pagebar.append('<a href="javascript:;" page="first">第一页</a><a href="javascript:;" page="prev">上一页</a><input type="number" value="'+index+'"><a href="javascript:;"  page="next">下一页</a> <a href="javascript:;"  page="last">尾页</a>')  
				   	    me.pagebar.find("a[page]").click(function(){
							var page=$(this).attr("page");
							var oldindex=o.index;
							switch(page){
								case "first":
								      o.index=1;
								     break;
								case "last":
								      o.index=o.total;
								     break;
								case "prev":
								     o.index--;
									 if(o.index==0){
										  o.index=1;
									 }
								     break;
								case "next":
								     o.index++;
									 if(o.index>o.total){
										 o.index=o.total
									 }
								    break;
							}
							/*mark and disable page*/
							if(oldindex!=o.index)
							  me.page(o.index,o.size);
						});
						me.grid.append(me.pagebar);
						o.index=index;
					}else{
						//分页刷新
						o.index=index;
						o.size=size;
						me.pagebar.find("input").val(index);
					}
				}
			},
			endless : t.attr("endless"), /*是否滚动到行末尾自动加载数据*/
			row : {
				click : t.attr("rowclick"), /*行单击事件*/
				dblclick : t.attr("rowdblclick"), /*行双击事件*/
				contextmenu : t.attr("rowcountextmenu")/*行右键*/
			},
			column : {
				click : t.attr("columnclick"), /*列单击事件*/
				dblclick : t.attr("columndblclick"), /*列双击事件*/
				contextmenu : t.attr("headcontextmenu")/*头部右键*/
			},filter:{
				open:t.attr("filterable"),/*是否启用过滤模式*/
				mode:t.attr("filter-mode")||"default",/*数据过滤模式:default:输入过滤模式，可以输入多个条件,显示的数据必须与列对应，form:与默认模式基本相同，只是用户需要自定义表单对象,输入的数据项不做限制,click仅支持单列数据过滤模式*/        
				form:t.attr("filter-form")/*如果启用form模式，必须指定form对象*/
			}
		}, options);
		this.template = table;
		this.$template = t;
		this.init(options);
		return this;
	};

	Grid.prototype = {
		init : function(o) {
			var me = this;
			me.options = o;
			me.params={};/*存储参数*/
			me.sorts = {};/*存储排序信息*/
			me.group={};/*存储分组信息*/
			
			
			me.grid=$('<div class="grid-table"></div>');   
			var _header = $('<div class="grid-header"></div>');
			var _body=$('<div class="grid-body"></div>');
			/*初始化表格*/
			me.$template.before(me.grid);
			
			/*加入表头*/
			me.grid.append(_header);
			_header.append("<table/>").find("table").append(me.$template.find("thead").clone());
			me.header=_header.find("thead");
			/*计算是否出现复合表头*/
			me.header.rowspan=me.header.find("tr").length;
			
			
			
			me.grid.append(_body);
			_body.append("<table/>").find("table").append(me.$template.find("tbody").clone());
			/*自定义滚动条实现：1、关于列锁定，当滚动条滚动时，隐藏和显示相应的列就可以了*/
			_body.css({"height":me.$template.attr("height")||"auto","overflow":"hidden"});
			me.body=_body.find("tbody");
			
			/*用户是否自定义了表格脚*/
			if(me.$template.find("tfoot").length>0){
				me.footer=$('<div class="grid-footer"></div>');
			    me.grid.append(me.footer);
			    me.footer.append("<table/>").find("table").append(me.$template.find("tfoot").clone());
			}
			/*是否使用分页*/
			if(me.options.pageable){
				me.options.page.template(me);
			}
			
			
			/*是否显示序号*/
			
			
			/*是否有checkbox*/
			me.header.tr=me.header.find("tr:first");
			if (o.checkable) {						
					var checkbox = $('<th width="12"  rowspan="'+ me.header.rowspan+'"><label class="checkbox"></label></th>');
					checkbox.find("label").click(function() {
						$(this).removeClass("half-checked").toggleClass("checked");
						var rows = me.rows();
						var listbox = rows.find("td  label.checkbox");
						if ($(this).hasClass("checked")) {
							listbox.addClass("checked");
							rows.addClass("checked");
						} else {
							listbox.removeClass("checked");
							rows.removeClass("checked");
						}
					});
					me.header.tr.prepend(checkbox);
			};
			
			if(o.numberable){
				me.header.tr.prepend('<th width="24"  rowspan="'+ me.header.rowspan+'">#</th>');
			}
			me.columns = [];
			me.header.find("th[name]").each(function(index, element) {
				var column = $(this);
				var name = column.attr("name");
				if (name) {
					var co = {
						name : name,/*字段名称*/
						hidden:column.attr("hidden"),/*是否隐藏*/
						dataType : column.attr("dataType"), /*数据类型*/
						editable : column.attr("editable"), /*是否允许编辑行*/
						resizeable : column.attr("resizeable"), /*是否允许缩放列*/
						sortable : column.attr("sortable"), /*是否允许排序*/
						dragabled : column.attr("dragabled"), /*是否允许移动*/
						dropable : column.attr("dropable"), /*是否允许放置*/
						sort : column.attr("sort")/*默认的排序*/
					}
					me.columns.push(co);
					/*允许排序*/
					if(co.sortable){
						column.click(function() {
							var oc = me.sorts[name];
							me.sort(co.name);
							/*改变排序的状态*/
							if (oc) {
								$(this).removeClass(oc);
							}
							$(this).addClass(me.sorts[name]);
						})
					}
				} else {
					me.columns.push({
						name : "_"
					});
				}
			});

			/*检查是否使用模版定义*/
			var template = me.body.find("tr.template");
			var etemplate = me.body.find("tr.edit-template");
			if (template.length != 0) {
				me.template = template.removeClass("template").clone();
				template.remove();
			}

			if (etemplate.length != 0) {
				me.etemplate = etemplate.removeClass("edit-template").clone();
				etemplate.remove();
			}

			me.monitor.init.end = new Date().getTime();
			me.monitor.init.time = me.monitor.init.end - me.monitor.init.start;
			
			/*过滤器绑定包括查询*/
			if(o.filter.open){
			   switch(o.filter.mode){
				   case "click":
				       /*单机单元格进行排序，所以需要对单元格进行监听*/
					   me.cell("click",function(){
						     //alert(111111)
					   })
					   break;
				   case "form":
				        var fid=o.filter.form;
						$("#"+fid).submit(function(event){
							event.preventDefault();
							/*添加过滤条件自动过滤数据,这里会自动嵌入对数据的验证,同时保留排序与分组*/
							var param={}							
							 $($(this).serializeArray()).each(function(){
								 param[this.name]=this.value;
							 })							
							me.filter(param);
						})
				        break
				   default:
				      
			   }
			}
			
			/*是否自动加载数据*/
			if ((o.load && o.load != "false") && o.action) {
				me.refresh();
			}
			me.$template.hide();
		},
		/*使用过滤方法选中行，返回true则选中*/
		check : function(tr, fn) {
			var me = this;
			if (tr.find("td label.checkbox").hasClass("checked")) {
				tr.addClass("checked");
			} else {
				tr.removeClass("checked");
			}
			/*检查所有的兄弟节点是否被选中*/
			var rows = me.rows();
			var crows = me.rows("checked");
			var checkbox = me.header.find("th label.checkbox");
			if (crows.length == 0) {
				checkbox.removeClass("checked half-checked")
			} else if (crows.length < rows.length) {
				checkbox.removeClass("checked").addClass("half-checked");
			} else {
				checkbox.removeClass("half-checked").addClass("checked");
			}
		},
		/*
		 * 获取checked数据
		 * @param {Function} fn 处理某一行数据function(rowvalue){}
		 */
		checked : function(fn) {
			var me = this;
			/*返回所有选中行的数据*/
			var d = [];
			me.rows("checked").each(function() {
				d.push(me.row(this, fn));
			})
			return d;
		},
		/*获取选择的行，和checked不同，这里必须是鼠标事件点击的行*/
		selected : function(fn) {
			var me = this;
			var d = [];
			me.rows("selected").each(function() {
				d.push(me.row(this, fn));
			})
			return d;
		},
		/*返回数据长度*/
		length : function(checked) {
			if (checked) {
				return this.rows("checked").length;
			}
			return this.rows().length;
		},
		/*
		 * 行选择器
		 * @param {String} type [undefined,checked,selected]
		 */
		rows : function(type) {
			return this.body.find( type ? ("tr." + type) : "tr");
		},
		/**
		 * @param {Number|Object} tr  行对象或者序号
		 * @param {Function} fn   操作某一行的方法
		 **/
		row : function(tr, fn) {
			var me = this, o = me.options;
			/*设置某一行的数据*/
			if (fn === true) {
				var r;
				/*模版方式定义数据*/
				if (me.template) {
					r = me.template.clone();				
					/*检查是否是checkbox模式，自动添加行选择*/
					if (o.checkable) {
						var checkbox = $('<td><label class="checkbox"></label></td>');
						checkbox.find("label").click(function() {
							$(this).toggleClass("checked");
							me.check(r);
						});
						r.prepend(checkbox);
					};
					r.find("td").each(function(index, element) {
						if (!o.checkable || index != 0) {
							var html = $(this).html();
							/*会导致默认的绑定事件无法响应*/
							$(this).html(html.replace(/\[(\w+)\]/gi, function(group, name) {
								return tr[name];
							})).width(me.header.tr.find("th").eq(index).width());
							
						}
					});
				} else {
					//非模版方式定义数据
					r = $("<tr></tr>")
					me.header.find("th").each(function(index) {
						var n = $(this).attr("name");
						var v = n ? tr[$(this).attr("name")] : "";
						if (o.checkable && index == 0) {
							var checkbox = $('<td width="12"><label class="checkbox"></label></td>');
							checkbox.find("label").click(function() {
								$(this).toggleClass("checked");
								me.check(r);
							});
							r.append(checkbox);
						} else {
							r.append('<td style="width:'+$(this).width()+'px">' + v + '</td>');
						}

						/*是否有单元格事件*/
					});
				}
				

				
				/*绑定行事件*/
				r.click(function(event) {
					/*selected样式*/
					if (o.row.click) {
						eval("var  _fn=" + o.row.click + ";");
						_fn.apply(this, [this]);
					}
					if (!(o.multiSelected && event.ctrlKey)) {
						me.rows("selected").removeClass("selected");
					}
					$(this).addClass("selected");

				}).dblclick(function() {
					if (o.row.dblclick) {
						eval("var  _fn=" + o.row.dblclick + ";");
						_fn.apply(this, [this]);
					}
				}).data("value", tr);
				me.body.append(r);
				
				if(o.numberable){
					r.prepend('<td width="24">'+(r.index()+1)+'</td>');
				}
				var td=r.find("td");
				/*重新设置宽度*/
				me.header.find("th").each(function(index){
					td.eq(index).width($(this).width())
				})
				
				return;
			} else if (fn === false) {
				//静态绑定数据
			}

			/*检查是否使用序号方式获取数据*/
			if ($.type(tr) == "Number") {
				tr = me.body.find("tr").eq(tr);
			}

			if (fn && $.type(tr) == "Function") {
				return fn($(tr).data("value"));
			} else {
				/*返回某一行的数据*/
				return $(tr).data("value");
			}

		},
		/*上一条记录*/
		prev : function() {
			return this.rows("selected").removeClass("selected").prev().addClass("selected");
		},

		/*下一条记录*/
		next : function() {
			return this.rows("selected").removeClass("selected").next().addClass("selected");
		},
		on:function(){
			
		},
		off:function(){
			
		},
		column : function() {

		},
		cell : function(events,fn) {
		     
		},
		/*
		* 只允许冻结列
		* @param width
		*/
		frozen:function(width){
			
		},
		/*
		 * 编辑某行数据
		 */
		edit : function(fn) {
			var me = this;
			/*将元素内容转为可编辑的状态*/
			var selected=me.rows("selected:first");
			if(me.etemplate){
			   selected.after(me.etemplate.clone());
			}else{
				selected.find("td").each(function(index) {
					var column = me.columns[index];
					if (column.dataType && column.editable) {
						
					}
				});
			}
		},
		/* 数据刷新：refresh是一个延迟对象，用法和标准的jQuery用法一样，详细请参考jQuery中的Deferred用法。以下范例仅供参考
		 * 1、刷新结束后 
		 * grid.refresh().done(fn).progress(fn).failue(fn);
		 * 2、如何刷新后在调用其他的ajax？这里需要使用pipe方式
		 * var yourfn=grid.refresh().pipe(function(data){
		 *		return // do something,this value will be return
		 *	})
		 *	yourfn.done(function(){
		 *	 
		 *	}).fail(function(){// do something})法
		 */
		refresh : function() {	
			var me = this, o = me.options;
			me.monitor.load = {
				start : new Date().getTime()
			};
			return $.ajax({
				type : o.method,
				url : o.action,
				data : me.params,
				success : function(data) {
					/*是否缓存checkbox的数据*/
					o.checkable && o.cacheable ? me.body.find("tr:not(.checked)").remove() : me.body.empty();
					/*是否使用分页*/
					if(o.pageable=="true"){
					     o.page.total=data.totalPage;	
						 o.page.count=data.totalCount;
					}
					   /*插入新的数据*/
					data=data.result;					
					$(data.result).each(function(index, element) {
						me.row(this, true);
					});					
					me.monitor.load.end = new Date().getTime();
					me.monitor.load.time = me.monitor.load.end - me.monitor.load.start;
					window.console.log(me.monitor);
					window.console.log("load " + me.length() + "rows data time:" + me.monitor.load.time + "ms");
				},
				dataType : o.dataType
			});
		},
		
		/**
		* 设置参数
		* @param param {Object} 添加的参数值
		* @param clear {Boolean} 是否清空原有参数
		*/
		param : function(param, clear) {
			if (clear)
				this.params = param;
			else
				this.params = $.extend(this.params, param);
			return this;
		},
		/*
		 * 排序或者清除排序
		 * @param {String} name字段名称
		 * @param {Boolean} clear 是否清除排序，当name和clear均为undefined时，清除所以排序
		 */
		sort : function(name,clear) {
			/*清除所有排序*/
			if(name==undefined){
			    /*需要调整可排序字段的状态*/
				me.sorts = {};
				me.param({sort:""}).refresh();
				return;
			}
			
			var me = this;
			/*清除排序*/
			if(clear){
				delete me.sorts[name];
			}else{
				var direction = (me.sorts[name] == "asc" ? "desc" : "asc");
				if (!me.options.multiSort) {
					me.sorts = {};
				}
				me.sorts[name] = direction;
			}
			var asc = [], desc = [];
			for (var i in me.sorts) {
				var d = me.sorts[i];
				if ("asc" == d) {
					asc.push(i);
				} else {
					desc.push(i);
				}
			}
			var s = [];
			if (asc.length > 0) {
				s.push("asc:" + asc.join(","));
			}
			if (desc.length > 0) {
				s.push("desc:" + desc.join(","));
			}
			me.param({
				sort : s.join(";")
			}).refresh();
		},
		/*
		*
		*/
		filter:function(param){
			var me=this;
			/*保留排序与分组信息，去除其他信息*/
			param.sort=me.params.sort;
			param.group=me.params.group;
			trace(param)
			me.param(param,true).refresh();
			return me;
		},
		group:function(){
			
		},
		/*
		* 定位到某一页，包括改变页面参数
		*/
		page : function(index,size) {
			var me = this, o = me.options;
			/*改变分页信息*/
			o.page.template(me,index,size);
			/*执行分页查询*/
			if(Q.is(index,"number")){
				if(size&&size!=o.page.size){
					o.page.size=size;
				}
				
				var p = {};
				p[o.page.name.page] = index;
				p[o.page.name.size] = o.page.size;		
				me.param(p).refresh();
			}
		}
	};
})(window);

;(function($) {
	$.fn.grid = function(options) {
		return this.each(function() {
			if (!this.grid) {
				this.grid = new Grid(this, options)
			}
			return this;
		});
	}
})(jQuery);
/*form*/
Kiss.define(function(context) {
	$("form", context).each(function() {
		$.form.validator($(this));
	});
	/*默认所有带source的图片自动延迟加载*/
	$("img[source]", context).lazyload();
	$("[placeholder]", context).each(function(index, element) {
		var me = $(this);
		setTimeout(function() {
			me.placeholder()
		}, 10);
	});

	$("input,textarea,select", context).each(function(index) {
		var el = this, $el = $(this), name = $el.attr("name"), type = $el.attr("type");
		switch(el.tagName.toLowerCase()) {
			case "input":
				$.form.install($el, type);
				break;
			case "textarea":
				break;
			case "select":
		}
	});
});
/*执行确认*/
/*pop up*/
Kiss.addEventListener({
	"pop" : function(obj) {
		$(obj).click(function(event) {
			event.preventDefault();
			$(this).pop();
		})
	},
	"pop-confirm" : function(obj) {
		var $this = $(obj), $a, ok = $this.attr("ok"), cancle = $this.attr("cancle");
		$this.click(function(event) {
			event.preventDefault();
			event.stopPropagation();
			$a = $.alert.confirm($this.attr("data-message"), [
			function() {
				if (ok) {
					eval(ok);
				}
			},
			function() {
				if (cancle) {
					eval(cancle);
				}
			}], {
				effect : "mouse",
				trigger : $this
			});
		});
	},
	"combobox" : function(obj) {
		$(obj).combobox();
	},
	"ajax-grid" : function(obj) {
		$(obj).grid();
	},
	"form-submit" : function(obj) {
		$(obj).click(function(event) {
			event.preventDefault();
			var formId = $(obj).attr("form-id"), success = $(obj).attr("success"), failure = $(obj).attr("failure");
			$("#" + formId).postForm(function(data) {
				$.alert.show(data, function() {
					if (data.status == 1) {
						if (success) {
							eval(success);
						}
					} else {
						if (failure) {
							eval(failure);
						}
					}
				})
			});
		});
	},
	"form-reset" : function(obj) {

	},
	"gallery" : function(obj) {
		$(obj).find(".gallery-item").colorbox({
			rel : "gallery-item",
			maxWidth : "90%",
			maxHeight : "95%"
		});
	},
	"tabnavigator" : function(obj) {
        $(obj).tabnavigator();
	},
	"upload-button" : function(obj) {
		$(obj).upload();
	},
	"module" : function(obj) {
		$(obj).module();
	}
});
