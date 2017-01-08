package com.demo.android.pictureindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cl150 on 2016/12/25.
 */

public class PictureIndicatorView extends LinearLayout implements  OnTabChangeListener{
    private Bitmap bitmap;
    private float initTranslation;
    private int visibleTabCount;
    private RectF rectF;
    private float mTranslationX;
    private int titleNormalColor;
    private int titleSelectedColor;
    private onTabChanged mOnTabChangeListener;
    private float topdistance;
    private int titletextsize;
    private TextPaint textPaint;

    public void setmOnTabChangeListener(onTabChanged mOnTabChangeListener) {
        this.mOnTabChangeListener = mOnTabChangeListener;
    }
    public PictureIndicatorView(Context context) {
        this(context,null);
    }

    public PictureIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PictureIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PictureIndicatorView);
        int resourceId =array.getResourceId(R.styleable.PictureIndicatorView_indicator,
                R.mipmap.ic_launcher);
        bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        initTranslation=array.getDimension(R.styleable.PictureIndicatorView_initTranslation,0);
        topdistance = array.getDimension(R.styleable.PictureIndicatorView_topdistance,dp2px(15));
        visibleTabCount=array.getInt(R.styleable.PictureIndicatorView_visibleCount,4);
        titleNormalColor = array.getColor(R.styleable.PictureIndicatorView_titlenormalcolor, Color.BLACK);
        titleSelectedColor = array.getColor(R.styleable.PictureIndicatorView_titleseletedcolor,
                Color.GREEN);
        titletextsize = array.getDimensionPixelSize(R.styleable.PictureIndicatorView_titletextsize,15);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(dp2px(titletextsize));
        array.recycle();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF();
        rectF.left=0;
        rectF.right= w/visibleTabCount-2*initTranslation;
        float height=(rectF.right-rectF.left)*bitmap.getHeight()/bitmap.getWidth();
        Rect rect=new Rect();
        textPaint.getTextBounds("2014",0,"2014".length(),rect);
        rectF.top=rect.height()/2+h/2+topdistance;
        rectF.bottom=rectF.top+height;
    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(initTranslation + mTranslationX, 0);
        canvas.drawBitmap(bitmap,null,rectF,null);
        canvas.restore();
    }
    //设置tab文本和点击事件
    public void setTitles(List<String> list) {
        if (list != null && list.size() > 0) {
            removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                TextView textView = new TextView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
                params.width = getScreenWidth() / visibleTabCount;
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(i==0?titleSelectedColor:titleNormalColor);
                textView.setTextSize(titletextsize);
                textView.setLayoutParams(params);
                textView.setText(list.get(i));
                addView(textView);
            }
            setItemClick();
        }
    }
    //  点击事件
    private void setItemClick() {
        for (int i = 0; i < getChildCount(); i++) {
            final int finalI = i;
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnTabChangeListener!=null){
                        mOnTabChangeListener.setCurrentTab(finalI);
                    }
                }
            });
        }
    }

    //改变indicator的颜色
    private void resetTextViewColor(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                if (position == i) {
                    ((TextView) view).setTextColor(titleSelectedColor);
                } else {
                    ((TextView) view).setTextColor(titleNormalColor);
                }
            }
        }
    }
    private void scroll(int position, float positionOffset) {

        mTranslationX =  (getMeasuredWidth() / visibleTabCount )*  (position + positionOffset);
        int tabWidth = getScreenWidth() / visibleTabCount;
        if (positionOffset > 0 && (position >= visibleTabCount - 2) && getChildCount() > visibleTabCount&& position < (getChildCount()-2)) {
            if (visibleTabCount != 1) {
                scrollTo((position - (visibleTabCount - 2)) * tabWidth                    + (int) (tabWidth * positionOffset), 0);
            } else {
                this.scrollTo(position * tabWidth + (int) (tabWidth * positionOffset), 0);
            }
        }
        invalidate();
    }
    //获取屏幕宽度
    private int getScreenWidth(){
        return getResources().getDisplayMetrics().widthPixels;
    }
    //dp转px
    private int dp2px(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5);
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        scroll(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        resetTextViewColor(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
    public interface onTabChanged{
        void setCurrentTab(int currentItem);
    }
}
