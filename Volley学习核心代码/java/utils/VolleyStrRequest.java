package utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.elvis.volleydemo.MyApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ELVIS on 2015/11/7.
 */
public class VolleyStrRequest {
    public static StringRequest stringRequest;
    public static Context context;

    public static void RequestStrGet(Context context, String url, String tag, VolleyStringReqItf vif) {
        //取消当前tag的volley请求防止重复请求
        MyApplication.getHttpQueues().cancelAll(tag);

        stringRequest = new StringRequest(Request.Method.GET, url, vif.loadingListener(), vif.errorListener());
        stringRequest.setTag(tag);
        MyApplication.getHttpQueues().add(stringRequest);
        //开启请求
        MyApplication.getHttpQueues().start();

    }

    public static void ReqestStrPost(Context context, String url, String tag, final HashMap<String, String> params, VolleyStringReqItf vif) {
        MyApplication.getHttpQueues().cancelAll(tag);

        stringRequest = new StringRequest(Request.Method.POST, url, vif.loadingListener(), vif.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setTag(tag);
        MyApplication.getHttpQueues().add(stringRequest);
        MyApplication.getHttpQueues().start();
    }
}
