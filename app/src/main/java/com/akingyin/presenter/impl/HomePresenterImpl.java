package com.akingyin.presenter.impl;

import android.os.Bundle;

import com.akingyin.presenter.IHomePresenter;
import com.akingyin.view.IHomeView;
import com.appeaser.sublimenavigationviewlibrary.OnNavigationMenuEventListener;
import com.appeaser.sublimenavigationviewlibrary.SublimeBaseMenuItem;

/**
 * Created by Administrator on 2016/1/26.
 */
public class HomePresenterImpl  implements IHomePresenter{

    public IHomeView   homeView;

    public HomePresenterImpl(IHomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void initialize(Bundle savedInstanceState) {
        homeView.initialize(savedInstanceState);
    }

    @Override
    public void onNavMenuEvent(OnNavigationMenuEventListener.Event event, SublimeBaseMenuItem menuItem) {

         switch (event){
             case CHECKED:

                 break;
             case CLICKED:

                 break;
             case GROUP_COLLAPSED:
                 break;
             case GROUP_EXPANDED:

                 break;
             case UNCHECKED:

                 break;
         }
    }
}
