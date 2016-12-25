package com.demo.android.pictureindicator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewpager);
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
        PictureIndicatorView pictureIndicatorView=
                (PictureIndicatorView) findViewById(R.id.pictureindicator);
        pictureIndicatorView.setUpWidthViewPager(viewPager,0);
        pictureIndicatorView.setTitles(Arrays.asList("2014","2015","2016","2017"));
    }
}
