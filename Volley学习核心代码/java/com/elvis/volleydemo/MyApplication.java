package com.elvis.volleydemo;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ELVIS on 2015/11/6.
 *
 */
public class MyApplication extends Application {
    //定义全局的网络请求队列
    public static RequestQueue queues;
    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());
    }
    //获取请求队列
    public static RequestQueue getHttpQueues(){
        return queues;
    }
}
