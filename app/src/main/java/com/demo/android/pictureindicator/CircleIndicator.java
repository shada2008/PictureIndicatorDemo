package com.demo.android.pictureindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by cl150 on 2016/12/26.
 */

public class CircleIndicator extends View implements OnTabChangeListener{

    //默认圆半径
    private int radius = 15;
    //当前指示圆半径
    private float selectedRadius;
    private int width;
    private int count;
    private Paint paint;
    private int height;
    private float current;
    private int normalColor;
    private int selectedColor;
    private float gap;

    public CircleIndicator(Context context) {
        this(context, null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
        normalColor = array.getColor(R.styleable.CircleIndicator_normalColor, Color.GRAY);
        selectedColor = array.getColor(R.styleable.CircleIndicator_selectedColor, Color.YELLOW);
        gap = array.getDimension(R.styleable.CircleIndicator_gap,0);
        array.recycle();
        //默认圆画笔
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        radius=height/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这个距离如下图所示
        float distance = (width-(count-1)*gap-count*height)/2;
        Log.d("xxxxx", "onDraw: "+count);
        //默认情况下的圆
        for (int i = 0; i < count; i++) {
            if(current==i){
                paint.setColor(selectedColor);
            }else{
                paint.setColor(normalColor);
            }
            canvas.drawCircle(distance + i*gap+(2*i-1)*radius, radius, radius, paint);
        }
    }

    //和viewpager联动
    public void setUpWithViewPager(int count) {
        //确定要画几个圆
        this.count = count;
        invalidate();
    }
    //设置红色圆移动的距离
    private void setCircleSize(int position) {
        current = position;
        invalidate();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCircleSize(position);
        current=position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
