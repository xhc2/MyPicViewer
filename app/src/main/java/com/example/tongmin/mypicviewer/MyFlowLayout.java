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
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
            return;
        }

        int childCount = getChildCount();
        //最终的高度和宽度
        int measureHeight = 0, measureWidth = 0;
        //一行中最高的一个控件的高度
        int maxRowHeight = 0;
        //当前行的宽度
        int currentRowWidth = 0;


        for (int i = 0; i < childCount; ++i) {

            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            int viewHeight = child.getMeasuredHeight();
            viewHeight += marginLayoutParams.topMargin;
            viewHeight += marginLayoutParams.bottomMargin;
            if (viewHeight > maxRowHeight) {
                //设置一行中最高的一个
                maxRowHeight = viewHeight;
            }

            int viewWidth = child.getMeasuredWidth();
            viewWidth += marginLayoutParams.leftMargin;
            viewWidth += marginLayoutParams.rightMargin;
            if (widthSize >= (viewWidth + currentRowWidth)) {
                //在这一行是可以放的下的
                currentRowWidth += viewWidth;

            } else {
                //在这里就是换行了
                if ((measureHeight + maxRowHeight) <= heightSize) {
                    //高度没有超过最高的高度
                    measureHeight += maxRowHeight;
                    maxRowHeight = 0;
                }
                if (currentRowWidth > measureWidth) {
                    //设置宽度为最宽的一行的宽度
                    measureWidth = currentRowWidth;
                }
            }
        }
        if (measureHeight == 0 && measureWidth == 0) {
            //如果两个都是0那么就是只有一行
            measureHeight += maxRowHeight;
            measureWidth = currentRowWidth;

        }
        if((measureHeight + getPaddingLeft()) < heightSize){
            measureHeight +=  getPaddingLeft();
        }
        if((measureHeight+getPaddingRight()) < heightSize){
            measureHeight +=  getPaddingRight();
        }
        if((measureWidth + getPaddingTop()) < widthSize){
            measureWidth += getPaddingTop();
        }
        if((measureWidth+getPaddingBottom()) < widthSize){
            measureWidth += getPaddingBottom();
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            measureWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            measureHeight = heightSize;
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        for(int i = 0 ;i < childCount ; ++ i){
            View child = getChildAt(i);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
        }

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
