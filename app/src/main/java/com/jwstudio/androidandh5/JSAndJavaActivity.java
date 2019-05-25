package com.jwstudio.androidandh5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class JSAndJavaActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsand_java);

        webView = findViewById(R.id.web_view);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        // 第二个参数可以简单理解为android表示AndroidAndrJsInterface的对象
        // 在js，通过它调用AndroidAndrJsInterface类下的方法
        // 名字可以自定义
        webView.addJavascriptInterface(new AndroidAndrJsInterface(), "android");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/jscalljava/index.html");
    }

    class AndroidAndrJsInterface {

        // 该注解可以解决Android 4.2以上的安全漏洞，4.2以下没有这个注解
        @JavascriptInterface
        public void showToast() {
            Toast.makeText(JSAndJavaActivity.this, "我被js调用了", Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void showToast(String info) {
            Toast.makeText(JSAndJavaActivity.this, "来自js的消息：" + info, Toast.LENGTH_LONG).show();
        }
    }
}
