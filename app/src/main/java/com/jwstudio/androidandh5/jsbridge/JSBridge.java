package com.jwstudio.androidandh5.jsbridge;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;

import com.jwstudio.androidandh5.jsbridge.CallBack;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理暴露给js的类和方法
 * 根据js传入的url内容找到对应的java类，并执行指定的Java方法
 *
 * 协议：JSBridge://className:callbackAddress/methodName?jsonObj
 */
public class JSBridge {

    // 存储需要暴露给js的方法
    private static Map<String, HashMap<String, Method>> exposedMethods = new HashMap<>();

    /**
     * 注册要暴露的类
     * @param exposeName JSBridge
     * @param classz 要暴露的类
     */
    public static void register(String exposeName, Class<?> classz) {
        // 将符合要求的classz类中的所有方法添加到exposedMethods中
        if (!exposedMethods.containsKey(exposeName)) {
            exposedMethods.put(exposeName, getAllMethod(classz));
        }
        Log.d("TAG", "JSBridge:register");
    }

    private static HashMap<String, Method> getAllMethod(Class injectedCls) {
        HashMap<String, Method> methodHashMap = new HashMap<>();

        // 获取该类的所有方法
        Method[] methods = injectedCls.getDeclaredMethods();

        for (Method method : methods) {
            // 剔除不符合要求的方法
            if (method.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC) || method.getName() == null) {
                continue;
            }

            // 方法的参数
            Class[] paramters = method.getParameterTypes();
            // 进一步寻找符合要求的方法
            if (paramters != null && paramters.length == 3) {
                if (paramters[0] == WebView.class && paramters[1] == JSONObject.class && paramters[2] == CallBack.class) {
                    methodHashMap.put(method.getName(), method);
                }
            }
        }

        return methodHashMap;
    }

    /**
     * 调用相应的java方法去处理js的请求
     * @param webView WebView
     * @param urlString 根据协议，js层给java传递的信息
     * @return null
     */
    public static String callJava(WebView webView, String urlString) {
        String className = "";
        String methodName = "";
        String param = "";
        String port = "";

        // 验证该urlString是否符合协议的基本要求
        if (!urlString.equals("") && urlString != null && urlString.startsWith("JSBridge")) {
            Uri uri = Uri.parse(urlString);
            className = uri.getHost();   // 要调用的类
            param = uri.getQuery();      // js层给Java层传递的信息（json格式）
            port = uri.getPort() + "";   // js层回调函数的地址
            methodName = uri.getPath().replace("/", "");  // 要调用的方法

            if (exposedMethods.containsKey(className)) {
                // 找到该类的所有符合要求的方法
                HashMap<String, Method> methodHashMap = exposedMethods.get(className);

                if (methodHashMap != null && methodHashMap.size() != 0 && methodHashMap.containsKey(methodName)) {
                    // 根据方法名找到指定的方法
                    Method method = methodHashMap.get(methodName);
                    if (method != null) {
                        try {
                            // 在这里真正处理js的请求，CallBack用于告诉js层我的活干完了，该你了
                            method.invoke(null, webView, new JSONObject(param), new CallBack(webView, port));
                            Log.d("TAG", "JSBridge:callJava");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return null;
    }

}
