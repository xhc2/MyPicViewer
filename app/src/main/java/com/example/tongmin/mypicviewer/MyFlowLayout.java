package com.example.tongmin.mypicviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 制作一个流式布局，依次从左往右
 */
public class MyFlowLayout extends ViewGroup {
    public MyFlowLayout(Context context) {
        this(context, null);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //测试布局的尺寸
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("xhc","parent size width "+widthSize+" height "+heightSize);
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
            return;
        }

        //最后管padding
        int childCount = getChildCount();
        int measureWidth = 0, measureHeight = 0;
        //一行中最高的一个控件
        int heightMax = 0, widthMax = 0;

        for (int i = 0; i < childCount; ++i) {
            View view = getChildAt(i);

            MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
//            int widthMeasure = getChildMeasureSpec(widthMeasureSpec,0,params.width);
//            int heightMeasure = getChildMeasureSpec(heightMeasureSpec,0, params.height);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            if ((measureWidth + params.rightMargin + params.leftMargin + view.getMeasuredWidth()) <= widthSize) {
                //这一行可以放下这个控件
                measureWidth += (params.rightMargin + params.leftMargin + view.getMeasuredWidth());
                if (measureHeight < (view.getMeasuredHeight() + params.topMargin + params.bottomMargin)) {
                    Log.e("xhc", "getMeasuredHeight " + view.getMeasuredHeight() + " params.topMargin " + params.topMargin + " bottomMargin " + params.bottomMargin);
                    heightMax = (view.getMeasuredHeight() + params.topMargin + params.bottomMargin);
                }
            } else {
                //换行
                if ((measureHeight + heightMax) < heightSize) {
                    //换行了 高度增加
                    measureHeight += heightMax;
                    Log.e("xhc", " 高度增加了几次 " + measureHeight);
                }
                if (widthMax < measureWidth) {
                    //换成最宽的宽度;
                    widthMax = measureWidth;
                }
            }
        }

//        Log.e("xhc"," width "+measureWidth);

        if(widthMax != 0){
            measureWidth = widthMax;
        }


        if (measureHeight == 0) {
            measureHeight = heightMax;
        }
        if ((measureWidth + getPaddingLeft()) < widthSize) {
            measureWidth += getPaddingLeft();
        }
        if ((measureWidth + getPaddingRight()) < widthSize) {
            measureWidth += getPaddingRight();
        }
        if ((measureHeight + getPaddingTop()) < heightSize) {
            measureHeight += getPaddingTop();
        }
        if ((measureHeight + getPaddingBottom()) < heightSize) {
            measureHeight += getPaddingBottom();
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            measureWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            measureHeight = heightSize;
        }
        Log.e("xhc", "width " + measureWidth + " height " + measureHeight);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }
}
