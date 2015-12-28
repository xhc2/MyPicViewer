package com.example.tongmin.mypicviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by TongMin on 2015/12/25.
 */
public class MyTextview extends TextView {
    public MyTextview(Context context) {
        super(context);
    }

    public MyTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //全屏尺寸
        Log.e("xhc", "heightsize " + heightSize + " widthsize " + widthSize);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if(heightMode == MeasureSpec.AT_MOST){
            Log.e("xhc","heightMode AT_MOST");
        }
        if(heightMode == MeasureSpec.EXACTLY){
            Log.e("xhc","heightMode EXACTLY");
        }
        if(widthMode == MeasureSpec.AT_MOST){
            Log.e("xhc","widthMode AT_MOST");
        }
        if(widthMode == MeasureSpec.EXACTLY){
            Log.e("xhc","widthMode EXACTLY");
        }
    }
}
