package com.elvis.volleydemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;

import utils.BitmapCache;

public class ImageActivity extends AppCompatActivity {
    private ImageView iv_img;
    private NetworkImageView netImgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        //initViewImgReq();

        // initViewImgLoader();
        initViewNetWorkImg();
    }


    private void initViewImgReq() {
        Toast.makeText(ImageActivity.this, "ImgReq方式", Toast.LENGTH_SHORT).show();
        iv_img = (ImageView) findViewById(R.id.iv_img);
        String url = " http://h.hiphotos.baidu.com/image/pic/item/d53f8794a4c27d1e3584e91b1fd5ad6edcc4384b.jpg";
        //ImageRequest方式
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                iv_img.setImageBitmap(bitmap);

            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ImageActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });


        MyApplication.getHttpQueues().add(request);
    }

    private void initViewImgLoader() {
        Toast.makeText(ImageActivity.this, "ImgLoader方式", Toast.LENGTH_SHORT).show();
        iv_img = (ImageView) findViewById(R.id.iv_img);
        String url = "http://image.tianjimedia.com/uploadImages/2012/090/063N2L5N2HID.jpg";
        //ImageLoader
        ImageLoader loader = new ImageLoader(MyApplication.getHttpQueues(), new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv_img, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        loader.get(url, listener);
    }

    private void initViewNetWorkImg() {
        Toast.makeText(ImageActivity.this, "NetWorkImg方式", Toast.LENGTH_SHORT).show();
        netImgView = (NetworkImageView) findViewById(R.id.netWorkImg);
        String url = "http://new.aliyiyao.com/UpFiles/Image/2011/01/13/nc_129393721364387442.jpg";
        ImageLoader loader = new ImageLoader(MyApplication.getHttpQueues(), new BitmapCache());
        netImgView.setDefaultImageResId(R.mipmap.ic_launcher);
        netImgView.setErrorImageResId(R.mipmap.ic_launcher);
        netImgView.setImageUrl(url, loader);

    }


}
