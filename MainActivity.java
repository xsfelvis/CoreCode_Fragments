package com.example.elvis.mypaintest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private Button showButton;
    private ProgressBar mProgressBar;
    private int number;
    List<String> imageUrl;
    Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new MyHandler();
        //布局控件
        image = (ImageView) findViewById(R.id.imagePic);
        showButton = (Button) findViewById(R.id.myButton);
        mProgressBar = (ProgressBar) findViewById(R.id.processbar);
        //button绑定监听器
        showButton.setOnClickListener(new showButtonListener());
        //添加下载图片的URL
        imageUrl = new ArrayList<String>();
        imageUrl.add("http://image.tianjimedia.com/uploadImages/2011/266/AIO90AV2508S.jpg");
        imageUrl.add("http://image.tianjimedia.com/uploadImages/2012/090/063N2L5N2HID.jpg");
        imageUrl.add("http://comic.sinaimg.cn/2011/0824/U5237P1157DT20110824161051.jpg");
        imageUrl.add("http://image.tianjimedia.com/uploadImages/2012/090/1429QO6389U8.jpg");
        imageUrl.add("http://new.aliyiyao.com/UpFiles/Image/2011/01/13/nc_129393721364387442.jpg");


    }


    public class showButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            MyAsyncTask myAsyncTask = new MyAsyncTask(getApplicationContext());
            myAsyncTask.execute(imageUrl.get(number % imageUrl.size()));//这样可以循环下载url图片
            number++;


        }


    }

    //String:uri,Integer进度，Bitmap 图片
    private class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        public MyAsyncTask(Context context) {
            mProgressBar.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
        }

        /*
            *onPreExecute() 该方法将在执行实际的后台操作前被UI thread调用。这个方法只是做一些准备工作，
            * 如在界面上显示一个进度条。
            *doInBackground(Params...), 将在onPreExecute 方法执行后马上执行，该方法运行在后台线程中。
            * 这里将主要负责执行那些很耗时的后台计算工作。
            *publishProgress 该方法来更新实时的任务进度。该方法是抽象方法，子类必须实现。
            *onProgressUpdate(Progress...), 在publishProgress方法被调用后，
            * UI thread将调用这个方法从而在界面上展示任务的进展情况，可以通过一个进度条进行展示。
            *onPostExecute(Result), 在doInBackground 执行完成后，onPostExecute 方法将被UI thread调用，
            * 后台的计算结果将通过该方法传递到UI thread.
          */
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            //根据输入的URL下载对应的图片
            try {
                URL mUrl = new URL(params[0]);
                URLConnection urlConnection = mUrl.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                //  Toast.makeText(getApplicationContext(),"图片下载完成",Toast.LENGTH_LONG).show();该方法是非UI线程中，
                // 如果加上这句会报错，体现了非UI线程不能更新UI界面
                //这里采用Handler机制向主线程传送消息
                Message msg = new Message();
                msg.what = 1;
                msg.obj = "传回图片";
                mHandler.sendMessage(msg);


                inputStream.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        /*
        *在doInBackground方法执行完成后调用，onPreExecute方法被UI线程调用，后台的计算结
        *果通过该方法传递到UI线程
        * */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mProgressBar.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            if (bitmap != null) {
                image.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // 任务启动
            Toast.makeText(getApplicationContext(), "任务开始......", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            //消息处理
            switch (msg.what) {
                case 1:
                    String str = (String) msg.obj;

                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }
        }
    }
}
