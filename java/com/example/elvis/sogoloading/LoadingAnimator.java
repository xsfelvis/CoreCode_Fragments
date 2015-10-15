package com.example.elvis.sogoloading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by ELVIS on 2015/10/12.
 */
public class LoadingAnimator extends View {
    private final String TAG = "XSFtext";
    //画笔
    private Paint mPaint;
    /*
    * */
    //颜色
    private int color = Color.parseColor("#0000FF");
    //半径
    private int radius = 10;
    //屏幕密度
    private float density;
    //矩形块
    private RectF rectf;
    //起点，终点，当前端点
    private int startY, startX, endY, currentY;

    public LoadingAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取当前的设备的像素密度
        density = getResources().getDisplayMetrics().density;
        //设置画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setStyle(Paint.Style.FILL);//实心画笔

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取视图的中心点
        startX = getWidth() / 2;
        endY = getHeight() / 2;
        startY = endY * 5 / 6;
        //画笔设置颜色
        mPaint.setColor(color);
        if (currentY == 0) {
            playAnimation();
        } else {
            //绘制圆形
            drawMyCircle(canvas);
            drawShader(canvas);
            //绘制阴影

        }

    }

    /*
    * 小球加速下落动画
    * */
    private void playAnimation() {
        //我们只需在Y轴产生动画即可
        ValueAnimator valueAmimator = ValueAnimator.ofInt(startY, endY);
        //设置动画监听器
        valueAmimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentY = (int) animation.getAnimatedValue();
                //刷新
                invalidate();
            }
        });
        //动画效果加速器
        valueAmimator.setInterpolator(new AccelerateInterpolator(1.2f));
        valueAmimator.setRepeatCount(-1);
        valueAmimator.setRepeatMode(2);
        valueAmimator.setDuration(500);
        valueAmimator.start();


    }

    /*
    * 绘制圆形，在快要到达底部时绘制压扁的效果
    * */
    private void drawMyCircle(Canvas canvas) {
        if (endY - currentY > 10) {
            //此时离底部较远直接绘制实心圆
            canvas.drawCircle(startX, currentY, radius * density, mPaint);

        } else {
            //到底部需要显示阴影效果，即开始绘制椭圆
            rectf = new RectF(startX - radius * density - 3, currentY - radius * density + 5, startX + radius * density + 3, currentY + radius * density);
            canvas.drawOval(rectf, mPaint);


        }
    }

    /*
    *绘制底部阴影，由椭圆支持，根据高度比来计算底部阴影大小
    * */
    private void drawShader(Canvas canvas) {
        //计算Y轴整体高度差
        int totalY = endY - startY;
        //计算当前y轴距离差
        int nowY = currentY - startY;
        //计算比值
        float ratio = (float) (nowY * 1.0 / totalY);
        if (ratio <= 0.3) {
            return;
        }
        int ovalRadius = (int) (radius * ratio * density);
        //设置倒影的颜色
        mPaint.setColor(Color.parseColor("#3F3B2D"));
        rectf = new RectF(startX - ovalRadius, endY + 10, startX + ovalRadius, endY + 15);
        canvas.drawOval(rectf, mPaint);

    }
    /*
    * 设置颜色
    * */
    public void setColor(int color){
        this.color = color;
    }


}
