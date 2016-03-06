package com.xsf.citypickerdemo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author hzxushangfei
 * @time Created at 2016/3/5.
 * @email xsf_uestc_ncl@163.com
 */
public class WrapHeightGridView extends GridView {
    public WrapHeightGridView(Context context) {
        super(context);
    }

    public WrapHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapHeightGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
