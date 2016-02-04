package com.akingyin.presenter.impl;

import android.os.Bundle;
import android.view.View;

import com.akingyin.presenter.IRxbindPresenter;
import com.akingyin.view.IRxBindView;

/**
 * Created by zlcd on 2016/2/4.
 */
public class RxBindPresenterImpl implements IRxbindPresenter {

    private IRxBindView   rxBindView;

    public RxBindPresenterImpl(IRxBindView rxBindView) {
        this.rxBindView = rxBindView;
    }

    @Override
    public void initialize(Bundle savedInstanceState, View view) {
         rxBindView.initialize(savedInstanceState,view);
    }
}
