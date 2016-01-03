package com.netease.epay.finance.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by hzxushangfei on 2015/12/29.
 */
public class HorizontalScrollViewWithListener extends HorizontalScrollView {

    public HorizontalScrollViewWithListener(Context context,
                                            AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public HorizontalScrollViewWithListener(Context context,
                                            AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScrollViewWithListener(Context context) {
        super(context);
    }


    public interface ScrollViewListener {

        void onScrollChanged(ScrollType scrollType);

    }

    private Handler mHandler = new Handler();
    private ScrollViewListener scrollViewListener;

    /**
     * 滚动状态   IDLE 滚动停止  TOUCH_SCROLL 手指拖动滚动   FLING滚动 FLINGLEFT向左滚动 FLINGRIGHT向右滚动
     *
     * @author hzxushangfei
     * @Time 2015-12-29
     */
    public enum ScrollType {
        IDLE, TOUCH_SCROLL, FLING, FLINGLEFT, FLINGRIGHT
    }

    ;

    /**
     * 记录当前滚动的距离
     */
    private int currentX = -9999999;
    /**
     * 当前滚动状态
     */
    private ScrollType scrollType = ScrollType.IDLE;
    /**
     * 滚动监听间隔
     */
    private int scrollDealy = 50;
    /**
     * 滚动监听runnable
     */
    private Runnable scrollRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (getScrollX() == currentX) {
                //滚动停止  取消监听线程
                Log.d("", "停止滚动");
                scrollType = ScrollType.IDLE;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollChanged(scrollType);
                }
                mHandler.removeCallbacks(this);
                return;
            } else if (getScrollX() < currentX) {
                Log.d("", "FlingLeft。。。。。");
                scrollType = ScrollType.FLINGLEFT;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollChanged(scrollType);
                }
            } else if (getScrollX() > currentX) {
                Log.d("", "FlingRight。。。。。");
                scrollType = ScrollType.FLINGRIGHT;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollChanged(scrollType);
                }
            }

            /*
            如果只是需要不区分左右的监听可以使用此分支
            *else{
                //手指离开屏幕    view还在滚动的时候
                Log.d("", "Fling。。。。。");
                scrollType = ScrollType.FLING;
                if(scrollViewListener!=null){
                    scrollViewListener.onScrollChanged(scrollType);
                }
            }*/
            currentX = getScrollX();
            mHandler.postDelayed(this, scrollDealy);
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                this.scrollType = ScrollType.TOUCH_SCROLL;
                scrollViewListener.onScrollChanged(scrollType);
                //手指在上面移动的时候   取消滚动监听线程
                mHandler.removeCallbacks(scrollRunnable);
                break;
            case MotionEvent.ACTION_UP:
                //手指移动的时候
                mHandler.post(scrollRunnable);
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 设置滚动监听
     */
    public void setOnScrollStateChangedListener(ScrollViewListener listener) {
        this.scrollViewListener = listener;
    }

}
