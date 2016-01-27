package com.akingyin.presenter;

import android.os.Bundle;

import com.appeaser.sublimenavigationviewlibrary.OnNavigationMenuEventListener;
import com.appeaser.sublimenavigationviewlibrary.SublimeBaseMenuItem;

/**
 * Created by Administrator on 2016/1/26.
 */
public interface IHomePresenter {

    public   void   initialize(Bundle savedInstanceState);//

    public   void   onNavMenuEvent(OnNavigationMenuEventListener.Event event, SublimeBaseMenuItem menuItem);


}
