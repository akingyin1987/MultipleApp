package com.md.appdemo.tuwen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.md.appdemo.R;

/**
 * Created by Administrator on 2016/1/15.
 */
public class TuwenInfoFragment extends Fragment {

    public  View  view;
    public ViewPager  viewPager;

    public static TuwenInfoFragment newInstance(String uri,ViewPagerDatas  datas) {
        Bundle args = new Bundle();
        TuwenInfoFragment fragment = new TuwenInfoFragment();
        args.putString("url", uri);
        args.putSerializable("datas", datas);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.fragemnt_tuweninfo,null);
          viewPager = (ViewPager)view.findViewById(R.id.view_pager);
           loadData(getArguments());
          return view;
    }

    public   void    loadData(Bundle  bundle){
        
    }
}
