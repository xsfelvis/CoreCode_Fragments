package com.example.elvis.chatlistdome;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.chat_listView);
        //添加测试数据源
        ListViewBean bean1 = new ListViewBean();
        bean1.setType(0);
        bean1.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.left));
        bean1.setText("我想吃好哒~");

        ListViewBean bean2 = new ListViewBean();
        bean2.setType(1);
        bean2.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.right));
        bean2.setText("什么好吃哒");

        ListViewBean bean3 = new ListViewBean();
        bean3.setType(0);
        bean3.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.left));
        bean3.setText("已经加你购物车了~");

        ListViewBean bean4 = new ListViewBean();
        bean4.setType(1);
        bean4.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.right));
        bean4.setText("OK，买买买");

        List<ListViewBean> data = new ArrayList<>();
        data.add(bean1);
        data.add(bean2);
        data.add(bean3);
        data.add(bean4);
        mListView.setAdapter(new ChatAdapter(this, data));


    }


}
