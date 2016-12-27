package com.demo.android.pictureindicator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements PictureIndicatorView.onTabChanged {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PictureIndicatorView pictureIndicatorView=
                (PictureIndicatorView) findViewById(R.id.pictureindicator);
        final CircleIndicator circleIndicator= (CircleIndicator) findViewById(R.id.circleindicator);
        final ArrayList<TextFragment>fragments=new ArrayList<>();
        fragments.add(TextFragment.getInstance("one"));
        fragments.add(TextFragment.getInstance("two"));
        fragments.add(TextFragment.getInstance("three"));
        fragments.add(TextFragment.getInstance("four"));
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        pictureIndicatorView.setmOnTabChangeListener(this);
        circleIndicator.setUpWithViewPager(fragments.size());
        pictureIndicatorView.setTitles(Arrays.asList("2014","2015","2016","2017"));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pictureIndicatorView.onPageScrolled(position,positionOffset,positionOffsetPixels);
                circleIndicator.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    public void setCurrentTab(int currentItem) {
        viewPager.setCurrentItem(currentItem);
    }
}
