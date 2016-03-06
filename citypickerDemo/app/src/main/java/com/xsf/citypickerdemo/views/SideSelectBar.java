package com.xsf.citypickerdemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.xsf.citypickerdemo.R;

/**
 * Created by hzxushangfei on 2016/2/29.
 * 创建类似微信中选择联系人时候的侧边栏
 */
public class SideSelectBar extends View {
    private static final String[] b = {"定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int chooseIndex = -1;
    private Paint barPaint;
    private boolean showBg = false;


    private OnSideSelectBarChangedListener onSideSelectBarChangedListener;
    private TextView textViewOverlay;//图层

    public SideSelectBar(Context context) {
        super(context);
        init();
    }

    public SideSelectBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SideSelectBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        barPaint = new Paint();
        barPaint.setAntiAlias(true);
        barPaint.setTextSize(getResources().getDimension(R.dimen.side_letter_bar_letter_size));
        barPaint.setColor(getResources().getColor(R.color.gray));


    }

    /**
     * 设置悬浮的textView
     *
     * @param textViewOverlay
     */
    public void setOverlay(TextView textViewOverlay) {
        this.textViewOverlay = textViewOverlay;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();
        //每个字母对应的高度
        int oneHeight = height / b.length;
        float xPos = 0;
        float yPos = 0;


        if (showBg) {
            canvas.drawColor(Color.TRANSPARENT);
            //canvas.drawColor(Color.parseColor("#40000000"));
        }

        //开始画bar上的每个字母
        for (int i = 0; i < b.length; i++) {
            if (i == chooseIndex) {
                barPaint.setColor(getResources().getColor(R.color.gray_deep));
            }
            xPos = width / 2 - barPaint.measureText(b[i]) / 2;
            yPos = oneHeight * i + oneHeight;
            canvas.drawText(b[i], xPos, yPos, barPaint);
        }

    }

    /**
     * 处理点击事件的分发函数
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldchooseIndex = chooseIndex;//暂存以前的
        OnSideSelectBarChangedListener listener = onSideSelectBarChangedListener;
        int nowTouchIndex = (int) (y / getHeight() * b.length);

        //处理响应事件
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldchooseIndex != nowTouchIndex && listener != null && nowTouchIndex > 0 && nowTouchIndex < b.length) {
                    Log.d("xsf",nowTouchIndex+"");
                    listener.OnSideSelectBarChanged(b[nowTouchIndex]);
                    chooseIndex = nowTouchIndex;
                    invalidate();
                    //弹窗显示点击字母
                    if (textViewOverlay != null) {
                        textViewOverlay.setVisibility(VISIBLE);
                        textViewOverlay.setText(b[nowTouchIndex]);

                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (oldchooseIndex != nowTouchIndex && listener != null && nowTouchIndex > 0 && nowTouchIndex < b.length) {
                    listener.OnSideSelectBarChanged(b[nowTouchIndex]);
                    chooseIndex = nowTouchIndex;
                    invalidate();
                    //弹窗显示点击字母
                    if (textViewOverlay != null) {
                        textViewOverlay.setVisibility(VISIBLE);
                        textViewOverlay.setText(b[nowTouchIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg = false;
                chooseIndex = -1;
                invalidate();
                if (textViewOverlay != null) {
                    textViewOverlay.setVisibility(GONE);
                }

                break;
        }
        //
        // return super.dispatchTouchEvent(event);
        return true;

    }

    //对外公开的监听接口
    public interface OnSideSelectBarChangedListener {
        void OnSideSelectBarChanged(String select);
    }

    /**
     * 设置回调监听
     *
     * @param onSideSelectBarChangedListener
     */
    public void setOnSideSelectBarChangedListener(OnSideSelectBarChangedListener onSideSelectBarChangedListener) {
        this.onSideSelectBarChangedListener = onSideSelectBarChangedListener;
    }

}
