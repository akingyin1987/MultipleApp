package com.md.appdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/1/18.
 */


public class TabFragmentDemo  extends Fragment {

    public static TabFragmentDemo newInstance(String info) {
        Bundle args = new Bundle();
        TabFragmentDemo fragment = new TabFragmentDemo();
        args.putString("url", info);

        fragment.setArguments(args);
        return fragment;
    }

    TextView  textView;
    ImageView  imageView;
    View   view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(null == view){
            view = inflater.inflate(R.layout.fragment_demo, null);
            textView = (TextView)view.findViewById(R.id.fg_text);
            imageView = (ImageView)view.findViewById(R.id.fg_image);
        }

        Bundle  bundle = getArguments();
        textView.setText(bundle.getString("url", "test"));
        Picasso.with(getContext())
                .load(Uri.parse(bundle.getString("url")))
                .tag(getActivity()).fit()
                .into(imageView);
        return  view;

    }


    public   void  onReofish(String  url){
        System.out.println("url="+url);
        textView.setText(url);
        Picasso.with(getContext())
                .load(Uri.parse(url))
                .tag(getActivity()).fit()
                .into(imageView);
    }
}
