package com.example.oneday.activity;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

import com.example.oneday.R;
import com.example.oneday.response.OperateDto;
import com.example.oneday.utils.AppManager;
import com.example.oneday.utils.WebAccessTools;
import com.example.oneday.view.CustomProgressDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/*
 * "公共的"Activity,所有Activity的父类
 */
public abstract class BaseActivity extends Activity implements OnClickListener {

    protected Context mContext;
    protected LayoutInflater layoutInflater;
    public static String SER_KEY = "object";
    protected CustomProgressDialog progressDialog;// 自定义进度框

    protected WebAccessTools web;// 访问网络
    protected OperateDto operateDto;//获取当前网络状态

    /*
     * 加载图片
     */
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions optionsSquare;// 方形图片

    // 自定义字体
    protected Typeface typeFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        layoutInflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        web = new WebAccessTools(mContext);

        AppManager.getAppManager().addActivity(this);//将当前activity加入到App管理类中
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/myfont.ttf");
        optionsSquare = new DisplayImageOptions.Builder()
                // .cacheInMemory(true)
                // 加载图片时会在内存中加载缓存
                // .cacheInMemory(true)
                // 是否緩存都內存中
                .showImageOnLoading(R.drawable.touming)
                        // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.touming)
                .showImageOnFail(R.drawable.touming).cacheInMemory(true)// 设置图片加载/解码过程中错误时候显示的图片
                .cacheOnDisk(true).considerExifParams(true)// 是否緩存到sd卡上
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                .displayer(new RoundedBitmapDisplayer(0)).build();


    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 启动activity
     *
     * @param cls
     */
    public void launch(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void launch(Class<?> cls, Object object) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(SER_KEY, (Serializable) object);
        startActivity(intent);

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();

    }

    protected void closeApp() {
        AppManager.getAppManager().AppExit(mContext);
    }

    public void back(View view) {
        finish();
    }

    public void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setMessage("");
        }
        progressDialog.show();
    }

    public void startProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setMessage(msg);
        }
        progressDialog.show();
    }

    public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }

    public class MyPopupDismissListener implements
            PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

}
