package com.akingyin.presenter.impl;

import android.os.Bundle;
import android.view.View;

import com.akingyin.model.impl.UserRetrofitModelImpl;

import com.akingyin.net.RetrofitUtils;
import com.akingyin.net.api.GitHubApi;
import com.akingyin.pojo.UserInfo;
import com.akingyin.presenter.IRetrofitPresenter;
import com.akingyin.view.IRetrofitView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import rx.schedulers.Schedulers;


/**
 * Created by zlcd on 2016/2/16.
 */

@EBean
public class RetrofitPresenterImpl implements IRetrofitPresenter {

    @Bean
     UserRetrofitModelImpl     userRetrofitModel;

    private GitHubApi    api;

    private IRetrofitView     retrofitView;

    public IRetrofitView getRetrofitView() {
        return retrofitView;
    }

    public void setRetrofitView(IRetrofitView retrofitView) {
        this.retrofitView = retrofitView;
    }

    @Override
    public void initialize(Bundle savedInstanceState, View view) {
        retrofitView.initialize(savedInstanceState,view);
        api = RetrofitUtils.createApi(GitHubApi.class,"https://api.github.com/");

    }

    @Override
    public void findUserInfo(String account) {
        retrofitView.showMessage("网上获取中");
        Observable<UserInfo>  observable = api.getUserInfo(account);
        observable.subscribeOn(Schedulers.newThread()).
            observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<UserInfo>() {
            @Override
            public void call(UserInfo userInfo) {
                retrofitView.hideDialog();
                retrofitView.printMessage(userInfo.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                retrofitView.hideDialog();
            }
        });
    }


}
