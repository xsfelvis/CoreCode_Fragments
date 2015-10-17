package com.example.elvis.chatlistdome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ELVIS on 2015/10/17.
 * 聊天数据 适配器
 */
public class ChatAdapter extends BaseAdapter {
    private List<ListViewBean> mData;
    private LayoutInflater mInflater;

    public ChatAdapter(Context context,List<ListViewBean> data) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        ListViewBean bean = mData.get(position);
        return bean.getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                //左边
                holder = new ViewHolder();
                //获取布局
                convertView = mInflater.inflate(R.layout.item_left, null);
                //获取图片和文本
                holder.icon = (ImageView) convertView.findViewById(R.id.icon_left);
                holder.text = (TextView) convertView.findViewById(R.id.text_left);
            } else {
                //右边
                holder = new ViewHolder();
                //获取布局
                convertView = mInflater.inflate(R.layout.item_right, null);
                //获取图片和文本
                holder.icon = (ImageView) convertView.findViewById(R.id.icon_right);
                holder.text = (TextView) convertView.findViewById(R.id.text_right);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageBitmap(mData.get(position).getIcon());
        holder.text.setText(mData.get(position).getText());
        return convertView;
    }

    public final class ViewHolder {
        public ImageView icon;
        public TextView text;
    }
}
