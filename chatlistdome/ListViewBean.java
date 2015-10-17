package com.example.elvis.chatlistdome;

import android.graphics.Bitmap;

/**
 * Created by ELVIS on 2015/10/17.
 * 存储聊天数据bean
 */
public class ListViewBean {
    //type 区分是左边还是右边的消息，以此对应不同布局
    private int type;
    //聊天内容
    private String text;
    //头像
    private Bitmap icon;

    public ListViewBean() {
    }

    public int getType() {
        return type;
    }


    public String getText() {
        return text;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setType(int type) {

        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
