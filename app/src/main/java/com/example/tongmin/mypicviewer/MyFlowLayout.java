package com.example.tongmin.mypicviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        Log.e("xhc","onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
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
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            if ((measureWidth + params.rightMargin + params.leftMargin + view.getMeasuredWidth() + getPaddingRight()) <= widthSize) {
                //这一行可以放下这个控件
                measureWidth += (params.rightMargin + params.leftMargin + view.getMeasuredWidth());
                if (heightMax < (view.getMeasuredHeight() + params.topMargin + params.bottomMargin)) {
                    heightMax = (view.getMeasuredHeight() + params.topMargin + params.bottomMargin);
                }
            } else {
                //换行
                if ((measureHeight + heightMax) < heightSize) {
                    //换行了 高度增加
                    measureHeight += heightMax;
                    Log.e("xhc","高度增加"+measureHeight);
                    heightMax = 0 ;
                }
                if (widthMax < measureWidth) {
                    //换成最宽的宽度;
                    widthMax = measureWidth;
                }
                //行宽重新开始
                measureWidth = 0  ;
            }
            if(i == (childCount - 1)){
                    //这个是最后一行并且没到换行的地方需要把这一行的高度加进去
                    measureHeight += heightMax;
            }
        }


        if (widthMax != 0) {
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
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("xhc","onLayout");
        //注意父控件的padding，子空间的margin
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddintRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int currentX = paddingLeft, currentY = paddingTop;
        int heightMax = 0;
        for (int i = 0; i < childCount; ++i) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childTotalWidth = params.leftMargin + params.rightMargin + child.getMeasuredWidth();
            if ((paddintRight + currentX + childTotalWidth) <= getMeasuredWidth()) {
                //这一行可以放下
                int left = (currentX + params.leftMargin);
                int top = (currentY + params.topMargin);
                child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
                currentX += (params.leftMargin + child.getMeasuredWidth() + params.rightMargin);
                if (heightMax < (top + child.getMeasuredHeight() + params.bottomMargin)) {
                    heightMax = (top + child.getMeasuredHeight() + params.bottomMargin);
                }
            }
            else {
                currentX = paddingLeft;
                currentY += heightMax;
            }

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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("xhc","onAttachedToWindow");
    }
}
