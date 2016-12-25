package com.demo.android.pictureindicator;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cl150 on 2016/12/25.
 */

public class TextPager extends PagerAdapter {
    private String[]contents;
    public TextPager(String[]contents) {
        this.contents=contents;
    }

    @Override
    public int getCount() {
        return contents==null?0:contents.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        super.destroyItem(container, position, object);
    }
}
