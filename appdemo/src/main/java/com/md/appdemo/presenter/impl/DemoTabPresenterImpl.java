package com.md.appdemo.presenter.impl;

import com.md.appdemo.presenter.DemoTabViewPagerPresenter;
import com.md.appdemo.ui.ITabViewPager;


/**
 * Created by Administrator on 2016/1/18.
 */
public class DemoTabPresenterImpl  implements DemoTabViewPagerPresenter {

    private ITabViewPager idemoView;

    public DemoTabPresenterImpl(ITabViewPager idemoView) {
        this.idemoView = idemoView;
    }

    @Override
    public void init() {
         idemoView.init();
    }

    @Override
    public void initImageFragment() {
         idemoView.initImageFragemnt();
    }

    @Override
    public void initTextFragment() {
         idemoView.initTextFragemnt();
    }
}
