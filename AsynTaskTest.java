package com.example.elvis.mypaintest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by ELVIS on 2015/10/20.
 * AsynTaskTest
 */
public class AsynTaskTest extends AsyncTask<String,Integer,Integer>implements DialogInterface.OnCancelListener{
    private Context context;
    private  String tag = null;
    private ProgressDialog pd= null;
    private int lengh = 0;

    public AsynTaskTest(Context context, String tag, int lengh) {
        this.context = context;
        this.tag = tag;
        this.lengh = lengh;
    }

    @Override
    protected void onPreExecute() {
        //初始化的一些动作
        pd = new ProgressDialog(context);
        pd.setTitle(tag);
        pd.setMessage("In progressing");
        pd.setCancelable(true);
        pd.setOnCancelListener(this);
        pd.setIndeterminate(false);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(lengh);
        pd.show();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        pd.cancel();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //更新进度条
        Integer i = values[0];
        pd.setProgress(i+1);
    }

    @Override
    protected Integer doInBackground(String... params) {
        //非UI线程执行耗时操作
        int num = params.length;
        for (int i=0;i<num;i++){
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return num;

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Toast.makeText(context,"取消下载",Toast.LENGTH_SHORT).show();
    }
}
