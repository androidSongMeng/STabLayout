package com.example.tablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/21 0021.
 */

public class CountView extends TextView {

    private Paint paint;

    private int size;

    private String color = "#FF0000";

    public CountView(Context context) {
        super(context);
    }

    public CountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        setTypeface(Typeface.DEFAULT_BOLD);
        paint.setDither(true);
        paint.setAntiAlias(true);
        setGravity(Gravity.CENTER);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(color));
        size = dip2Px(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);
        super.onDraw(canvas);
    }

    public void setBgColorSize(String color, int size) {
        this.color = color;
        this.size = dip2Px(size);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(size, size);
    }

    /*
         * converts dip to px
         */
    private int dip2Px(float dip) {
        return (int) (dip * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }
}
