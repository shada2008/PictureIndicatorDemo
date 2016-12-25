package com.demo.android.pictureindicator;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cl150 on 2016/12/25.
 */

public class TextFragment extends Fragment {
   public static TextFragment getInstance(String content){
       TextFragment fragment=new TextFragment();
       Bundle bundle=new Bundle();
       bundle.putString("content",content);
       fragment.setArguments(bundle);
       return fragment;
   }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.textfragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView= (TextView) view.findViewById(R.id.text);
        textView.setText(getArguments().getString("content"));
    }
}
