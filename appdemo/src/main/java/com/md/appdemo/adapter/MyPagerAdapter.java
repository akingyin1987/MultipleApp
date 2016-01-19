package com.md.appdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.md.appdemo.TabFragmentDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<TabFragmentDemo> mFragments = new ArrayList<>();
    private  List<String> mFragmentTitles = new ArrayList<>();


    public   void   onLoadData(int postion,String  url){
        if(0>=postion && postion<mFragments.size()){
            mFragments.get(postion).onReofish(url);
        }
    }

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(TabFragmentDemo fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
