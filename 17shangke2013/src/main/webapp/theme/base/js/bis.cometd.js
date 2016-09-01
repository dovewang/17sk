MC = {
	connected : false,
	logLevel : "warn" /*debug|info|warn|error*/
};

/**
 * 握手
 */
MC.handshake = function(mc) {
	if (mc && mc.successful) {
		$.cometd.batch(function() {
			/*私人消息*/
			$.cometd.subscribe("/pv/" + parent.Env.USER_ID, function(message) {
				parent.MC.cometdCallback("pv", message);
			});
			/*系统消息（公共）*/
			$.cometd.subscribe("/sys", function(message) {
				parent.MC.cometdCallback("sys", message);
			});
			/*系统消息（私有）*/
			$.cometd.subscribe("/sys/" + parent.Env.USER_ID, function(message) {
				parent.MC.cometdCallback("sys", message);
			});
			/*学校区域内的消息及学习圈范围内消息，学习圈不存在时GROUP_ID=0*/
			$.cometd.subscribe("/sc/" + parent.Env.SITE_ID+"/"+parent.Env.GROUP_ID, function(message) {
				parent.MC.cometdCallback("sc", message);
			});
		});
	}
}
/*发送消息*/
MC.publish = function(ch, data) {
	$.cometd.publish(ch, data);
}
/**
 *  连接消息服务器
 */
MC.connect = function(message) {
	if (MC.disconnecting) {
		MC.connected = false;
	} else {
		MC.wasConnected = MC.connected;
		MC.connected = message.successful === true;
		if (!MC.wasConnected && MC.connected) {
			MC.handshake();
		} else if (MC.wasConnected && !MC.connected) {
			/*较短时间的网络故障*/
			//alert("您的网络可能已经中断……");
		}
	}
}
/**
 * 默认自动连接消息服务
 */
$(function() {
	$.cometd.websocketEnabled = true;
	$.cometd.configure({
		url : location.protocol + "//" + location.host + "/ms",
		logLevel : MC.logLevel,
		requestHeaders : {
			key : parent.Env.USER_ID
		}
	});

	$.cometd.addListener('/meta/handshake', MC.handshake);
	$.cometd.addListener('/meta/connect', MC.connect);
	$.cometd.handshake();
	/*异常处理*/
	$.cometd.onListenerException = function(exception, subscriptionHandle, isListener, message) {
		if (isListener) {
			this.removeListener(subscriptionHandle);
		} else {
			this.unsubscribe(subscriptionHandle);
		}
	}
});
$(window).unload(function() {
	if ($.cometd.reload) {
		$.cometd.reload();
	} else {
		$.cometd.disconnect();
	}
});
