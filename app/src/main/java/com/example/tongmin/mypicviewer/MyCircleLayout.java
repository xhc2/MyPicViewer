package com.example.tongmin.mypicviewer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Created by xhc on 2016/1/2.
 * 圆形菜单
 */
public class MyCircleLayout extends ViewGroup {


    //圆的半径
    private int radius;
    private int defaultSize = 250;
    //基础角度，后期的旋转都依靠这个
    private double baseAngle = 100;
    private int circleX, circleY;
    private Paint paint;

    private float dX = 0, dY = 0, mX = 0, mY = 0, uX = 0, uY = 0;
    private float lastX = 0 , lastY = 0 ;
    public MyCircleLayout(Context context) {
        this(context, null);
    }

    public MyCircleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context ctx) {
        paint = new Paint();
        paint.setColor(Color.parseColor("#000000"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureWidth, measureHeight;
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            measureWidth = widthSize;
            measureHeight = heightSize;
        } else {
            measureHeight = dip2px(getContext(), defaultSize);
            measureWidth = dip2px(getContext(), defaultSize);

            if (widthMode == MeasureSpec.EXACTLY) {
                measureWidth = widthSize;
            }
            if (heightMode == MeasureSpec.EXACTLY) {
                measureHeight = dip2px(getContext(), defaultSize);
            }
        }

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int size = Math.min(measureWidth, measureHeight);
        setRadius(size);
        setMeasuredDimension(size, size);
    }

    private void setRadius(int size) {
        radius = (size / 2) - dip2px(getContext(), 30);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getMeasuredWidth(), height = getMeasuredHeight();
        int size = Math.min(width, height);
        circleX = size / 2;
        circleY = size / 2;
        int childCount = getChildCount();
        double angle = 2 * Math.PI / childCount;

        for (int i = 0; i < childCount; ++i) {
            int childX = 0, childY = 0;
            //将子控件平均分布
            View child = getChildAt(i);
            double tempAngle = i * angle + baseAngle;
            double tempX = Math.sin(tempAngle) * radius;
            double tempY = Math.cos(tempAngle) * radius;
//            判断在哪个象限 //然后调整控件的位置
            double angleWhere = tempAngle % (2 * Math.PI);
            if (angleWhere < Math.PI / 2 && angleWhere >= 0) {
                //第一象限 x + , y -
                childX = (int) (circleX + Math.abs(tempX));
                childY = (int) (circleY - Math.abs(tempY));

            } else if (angleWhere >= Math.PI / 2 && angleWhere < Math.PI) {
                //第二象限 x + , y +
                childX = (int) (circleX + Math.abs(tempX));
                childY = (int) (circleY + Math.abs(tempY));


            } else if (angleWhere >= Math.PI && angleWhere < (Math.PI * 3 / 2)) {
                //第三象限 x - , y +
                childX = (int) (circleX - Math.abs(tempX));
                childY = (int) (circleY + Math.abs(tempY));
            } else if (angleWhere >= (Math.PI * 3 / 2) && angleWhere < (Math.PI * 2)) {
                //第四象限 x - , y -
                childX = (int) (circleX - Math.abs(tempX));
                childY = (int) (circleY - Math.abs(tempY));
            } else {
            }
            //细调控件位置，将位置指向控件中心
            childX -= child.getMeasuredWidth() / 2;
            childY -= child.getMeasuredHeight() / 2;
            child.layout(childX, childY, childX + child.getMeasuredWidth(), childY + child.getMeasuredHeight());
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(circleX, circleY, 5, paint);
        canvas.drawCircle(circleX, circleY, Math.min(getMeasuredHeight(),getMeasuredWidth()) /2, paint);

        drawPath(canvas);
    }

    private void drawPath(Canvas canvas) {

        int childCount = getChildCount();

        for (int i = 0; i < childCount; ++i) {
            View child = getChildAt(i);
            int stopX = (int) (child.getLeft() + child.getMeasuredWidth() / 2);
            int stopY = (int) (child.getTop() + child.getMeasuredHeight() / 2);
//            int stopX = child.getLeft();
//            int stopY = child.getTop();
            canvas.drawLine(circleX, circleY, stopX, stopY, paint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = event.getX();
                dY = event.getY();
                lastX = event.getX();
                lastY  = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mX = event.getX();
                mY = event.getY();
                double start = getAngle(lastX,lastY);
                double end =  getAngle(mX,mY);
                double angle = end - start;
                int quadrant = judgeQuadrant(mX, mY);
                if(quadrant == 1 || quadrant == 3){
                    baseAngle -= angle ;
                }
                else {
                    baseAngle -= angle;
                }
                //                Log.e("xhc","angle "+angle+ " pi /2 "+(Math.PI / 2 ));

                lastY = mY;
                lastX = mX;
                requestLayout();
                break;
            case MotionEvent.ACTION_UP:
                //在这里计算滑动速度
                uX = event.getX();
                uY = event.getY();

                break;

        }
        requestLayout();
        return true;
    }


    //判断在哪个象限
    private int judgeQuadrant(float x , float y ){
        x -= circleX;
        y -= circleY;
        if( y >= 0){
            return x >= 0 ? 1 : 2;
        }
        else {
            return x >= 0 ? 4 : 3;
        }
    }

    private double getAngle(float x, float y ){
        x -= circleX ;
        y -= circleY;
        if(x == 0) return 0;
        return Math.atan(y/x);
    }


    private int getLineLengthCircle(int x , int y){
        return (int)Math.sqrt(Math.pow((x - circleX),2) + Math.pow((y-circleY),2));
    }

    private int getLineLength(int x1 , int y1 , int x2 , int y2 ){

        return (int)Math.sqrt(Math.pow((x1 - x2),2) + Math.pow((y1-y2),2));
    }
}




















