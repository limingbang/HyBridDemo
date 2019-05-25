package com.jwstudio.androidandh5.jsbridge;

import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.jwstudio.androidandh5.jsbridge.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 封装js层要调用的方法
 */
public class Methods {

    public static void showToast(WebView view, JSONObject param, CallBack callBack) {
        // 解析得到key=msg的值
        String message = param.optString("msg");

        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

        if (callBack != null) {
            try {
                JSONObject result = new JSONObject();
                result.put("key", "value");
                result.put("key1", "value1");
                callBack.apply(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
