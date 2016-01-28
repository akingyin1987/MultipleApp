package com.akingyin.presenter.impl;

import android.os.Bundle;
import android.view.MenuItem;

import com.akingyin.presenter.IHomePresenter;
import com.akingyin.view.IHomeView;
import com.appeaser.sublimenavigationviewlibrary.OnNavigationMenuEventListener;
import com.appeaser.sublimenavigationviewlibrary.SublimeBaseMenuItem;
import com.md.multipleapp.R;

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
    public boolean onNavMenuEvent(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_implicitapp:
                System.out.println("app");
                 homeView.OpenMultipleApp();
                return  true;
            case R.id.navigation_userlist:
                System.out.println("user");
                homeView.switchUserManager();

                return  true;

        }
        return false;
    }
}
