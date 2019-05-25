package com.jwstudio.androidandh5.jsbridge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jwstudio.androidandh5.R;

import java.lang.reflect.Method;

public class JSBridgeActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsbridge);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new JSBridgeChromeClient());
        webView.loadUrl("file:///android_asset/jsbridge/index.html");

        JSBridge.register("JSBridge", Methods.class);
        Log.d("TAG", "JSBridgeActivity");
    }
}
