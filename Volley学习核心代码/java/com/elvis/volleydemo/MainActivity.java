package com.elvis.volleydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.VolleyStrRequest;
import utils.VolleyStringReqItf;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Volley_StringGet();
        //Volley_JsonGet();
        //Volley_StringPost();
        //Volley_JsonPost();

    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueues().cancelAll("yourTag");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case 0:
                Volley_StringGet();
                break;
            case 1:
                Volley_JsonGet();
                break;
            case 2:
                Volley_StringPost();
                break;
            case 3:
                Volley_JsonPost();
                break;
            case 4:
                MyVolley_StringPost();
                break;
            case 5:
                Intent it = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(it);
                break;

            default:
                Toast.makeText(this, "方法还没定义", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, 0, "StringGet");
        menu.add(Menu.NONE, 1, 1, "JsonGet");
        menu.add(Menu.NONE, 2, 2, "StringPost");
        menu.add(Menu.NONE, 3, 3, "JsonPost");
        menu.add(Menu.NONE, 4, 4, "MyVolleyStringPost");
        menu.add(Menu.NONE, 5, 5, "ImageRequest");
        return super.onCreateOptionsMenu(menu);
    }

    private void Volley_StringGet() {
        /*这里使用StringRequets*/
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=125.71.229.221";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();

            }
        });
        /*请求对象设置Tag标签,并加入全局队列*/
        request.setTag("StringGet");
        MyApplication.getHttpQueues().add(request);

    }

    private void Volley_JsonGet() {
        /*这里使用JsonRequets*/
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=125.71.229.221";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject js) {
                Toast.makeText(MainActivity.this, js.toString(), Toast.LENGTH_LONG).show();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        /*请求对象设置Tag标签,并加入全局队列*/
        request.setTag("JsonGet");
        MyApplication.getHttpQueues().add(request);

    }

    private void Volley_StringPost() {
        /*这里使用StringRequets*/
        String url = "http://ip.taobao.com/service/getIpInfo.php?";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //这里需要设置post的参数
                Map<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("ip", "125.71.229.221");
                return hashMap;
            }
        };
        //post请求需要单独实现
        /*请求对象设置Tag标签,并加入全局队列*/
        request.setTag("StringPost");
        MyApplication.getHttpQueues().add(request);
    }

    private void Volley_JsonPost() {
        /*这里使用JsonRequets*/
        String url = "http://ip.taobao.com/service/getIpInfo.php?";

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("ip", "125.71.229.222");
        JSONObject jsonParams = new JSONObject(hashMap);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        /*请求对象设置Tag标签,并加入全局队列*/
        request.setTag("JsonPost");
        MyApplication.getHttpQueues().add(request);
    }

    private void MyVolley_StringPost() {
        String url = "http://ip.taobao.com/service/getIpInfo.php?";
        HashMap<String, String> hashmap = new HashMap<String, String>();
        hashmap.put("ip", "125.71.229.222");
        VolleyStrRequest.ReqestStrPost(this, url, "StringPost", hashmap,
                new VolleyStringReqItf(this, VolleyStringReqItf.listener, VolleyStringReqItf.errorListener) {
                    @Override
                    public void onMySuccess(String result) {
                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }


}
