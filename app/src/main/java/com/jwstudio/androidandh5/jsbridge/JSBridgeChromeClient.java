package com.jwstudio.androidandh5.jsbridge;

import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.jwstudio.androidandh5.jsbridge.JSBridge;

public class JSBridgeChromeClient extends WebChromeClient {

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        // 在简单讲述是否调用result.confirm()的区别
        // 如果调用，js的回调函数才会被调用，参数的值是返回给js的
        // 如果没调用，js即使有回调函数也不会执行
        // 可以使用console.log()的方式来调式js，项目运行起来后可在run窗口查看
        result.confirm(JSBridge.callJava(view, message));
//        JSBridge.callJava(view, message);
        Log.d("TAG", "JSBridgeChromeClient");

        return true;
    }
}
