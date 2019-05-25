package com.jwstudio.androidandh5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JavaAndJSActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNoParamter;
    private Button btnYesParamter;
    private Button btnNoParamterAndReturn;

    // 加载网页或者说H5页面
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_and_js);

        btnNoParamter = findViewById(R.id.btn_no_parameter);
        btnYesParamter = findViewById(R.id.btn_yes_parmater);
        btnNoParamterAndReturn = findViewById(R.id.btn_no_parmater_return);

        btnNoParamter.setOnClickListener(this);
        btnYesParamter.setOnClickListener(this);
        btnNoParamterAndReturn.setOnClickListener(this);

        webView = new WebView(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); // 设置支持js脚本语言
        settings.setUseWideViewPort(true); // 支持双击-前提是页面要支持才显示
        settings.setBuiltInZoomControls(true); // 支持缩放按钮-前提是页面要支持才显示

        webView.setWebViewClient(new WebViewClient()); // 不跳转到默认浏览器
        webView.setWebChromeClient(new WebChromeClient()); // 支持js弹窗

        webView.addJavascriptInterface(new GetJsResult(), "Result");

        // 加载本地文件：file:///android_asset/文件具体路径
        // 网络资源，如：http://www.baidu.com
        // 此处asset后面是没有s的
        webView.loadUrl("file:///android_asset/javacalljs/index.html"); // 加载网络资源（需要网络权限），也可以时assets目录下的资源

        // 加载h5写的页面，会替换当前原生页面,在这里不需要
//        setContentView(webView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 格式：WebView.loadUrl("javascript:js方法")
            case R.id.btn_no_parameter:
                // 调用js无参函数
                webView.loadUrl("javascript:noParamter()");
                break;
            case R.id.btn_yes_parmater:
                String info = "Hello,I am come from java.";
                // 调用js有参参数
                // 传递字符串要加个单引号，数字可以不加；传递数组可以传递json格式的字符串
                webView.loadUrl("javascript:yesParamter('" + info + "')");
                break;
            case R.id.btn_no_parmater_return:
                webView.loadUrl("javascript:returnResult()");
                break;
            default:
                break;
        }
    }

    class GetJsResult {
        @JavascriptInterface
        public void getResult(String res) {
            Toast.makeText(JavaAndJSActivity.this, "js返回的结果:" + res, Toast.LENGTH_SHORT).show();
        }
    }

}
