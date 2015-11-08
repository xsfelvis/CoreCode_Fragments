package utils;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by ELVIS on 2015/11/7.
 */
public abstract class VolleyStringReqItf {
    public Context context;
    public static Response.Listener<String> listener;
    public static Response.ErrorListener errorListener;

    //成功和失败的回调函数
    public abstract void onMySuccess(String result);

    public abstract void onMyError(VolleyError error);

    public VolleyStringReqItf(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this.context = context;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    public Response.Listener<String> loadingListener() {
        listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onMySuccess(response);
            }
        };
        return listener;
    }

    public Response.ErrorListener errorListener() {
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onMyError(volleyError);
            }
        };
        return errorListener;
    }
}
