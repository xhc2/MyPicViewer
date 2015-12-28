package com.example.tongmin.mypicviewer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 */
public class TestView extends View {

    private Paint paint;
    private int defaultSize = 200;
    public TestView(Context context) {
        this(context,null);
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.parseColor("#ADAFBE"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.e("xhc","heightsize "+heightSize+" widthsize "+widthSize);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int measureWidth = 0, measureHeight = 0;
        if(heightMode == MeasureSpec.AT_MOST){
            Log.e("xhc","heightMode AT_MOST");
            measureHeight = defaultSize ;

        }

        if(heightMode == MeasureSpec.EXACTLY){
            Log.e("xhc","heightMode EXACTLY");
            measureHeight = heightSize;
        }
        if(widthMode == MeasureSpec.AT_MOST){
            Log.e("xhc","widthMode AT_MOST");
            measureWidth = defaultSize;
        }
        if(widthMode == MeasureSpec.EXACTLY){
            Log.e("xhc","widthMode EXACTLY");
            measureWidth =  widthSize;
        }
        setMeasuredDimension(measureWidth,measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("xhc","left "+left +" right "+right+" top "+top+" bottom "+bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = Math.min(getHeight(),getWidth());
        int padding = Math.max(getPaddingBottom(),getPaddingTop());
        canvas.drawCircle(size/2,size/2,(size/2-padding),paint);
    }
}
