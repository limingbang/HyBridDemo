var callbacks = new Array();

/**
 * js层调用Android层方法
 * @param {Object} obj Android层的类
 * @param {Object} method 该obj中的那个方法
 * @param {Object} params 使用json的数据格式给Android传递信息
 * @param {Object} callback js层的回调方法，当Android层处理好了js层要如何处理
 */
function jsCallAndroid(obj, method, params, callback) {
	// 保存callback回调函数
	var port = callbacks.length;
	callbacks[port] = callback;
	
	// 组合出符合规则的url，并传递给Java层
	var url = 'JSBridge://' + obj + ':' + port + '/' + method + '?' + JSON.stringify(params);
	
	window.prompt(url);
}

/**
 * 当js调用完Android层时执行
 * @param {Object} port 回调函数的地址，也就是在数组中的位置
 * @param {Object} jsonObj 从Android层传过来的参数
 */
function onAndroidFinished(port, jsonObj) {
	// 从callbacks取出对应的回调函数
	var callback = callbacks[port];
	
	callback(jsonObj);
	
	// 从callbacks中删除callback
	delete callbacks[port];
}
