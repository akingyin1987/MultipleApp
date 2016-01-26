package com.akingyin.presenter;

import com.appeaser.sublimenavigationviewlibrary.OnNavigationMenuEventListener;
import com.appeaser.sublimenavigationviewlibrary.SublimeBaseMenuItem;

/**
 * Created by Administrator on 2016/1/26.
 */
public interface IHomePresenter {

    public   void   initialize();//

    public   void   onNavMenuEvent(OnNavigationMenuEventListener.Event event, SublimeBaseMenuItem menuItem);


}
