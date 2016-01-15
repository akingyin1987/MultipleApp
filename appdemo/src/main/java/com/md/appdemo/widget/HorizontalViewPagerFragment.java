package com.md.appdemo.widget;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.md.appdemo.R;

import java.util.List;

/**
 * Created by zlcd on 2016/1/15.
 */
public class HorizontalViewPagerFragment  extends Fragment {


    public   HorizontalAdapter   adapter ;

    public   HorizontalListView   listView;


    public  static HorizontalViewPagerFragment  newInstance(List<HorItemData> itemDatas,boolean online) {
        HorizontalViewPagerFragment  fragment = new HorizontalViewPagerFragment();
        Bundle    bundle = new Bundle();
        bundle.putBoolean("online", online);
        HorItemDataS   itemDataS = new HorItemDataS();
        itemDataS.setItemDatas(itemDatas);
        bundle.putSerializable("data",itemDataS);
        fragment.setArguments(bundle);
        return  fragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View   view = inflater.inflate(R.layout.fragment_horlistview,null);
        listView = (HorizontalListView)view.findViewById(R.id.hlvSimpleList);

        return  view;
    }
}
