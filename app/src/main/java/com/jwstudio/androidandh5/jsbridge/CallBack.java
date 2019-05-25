package com.jwstudio.androidandh5.jsbridge;

import android.util.Log;
import android.webkit.WebView;

import org.json.JSONObject;

public class CallBack {

    private String mPort;

    private WebView mWebView;

    public CallBack(WebView webView, String mPort) {
        this.mPort = mPort;
        this.mWebView = webView;
    }

    /**
     * 通知js
     * @param jsonObject Java层处理完后返回给js层的信息
     */
    public void apply(JSONObject jsonObject) {
        if (mWebView != null) {
            mWebView.loadUrl("javascript:onAndroidFinished('" + mPort + "', " + String.valueOf(jsonObject) + ")");
        }
        Log.d("TAG", "CallBack:apply");
    }
}
