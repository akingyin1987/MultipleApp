package com.akingyin.model.impl;



import com.akingyin.model.IUserRetrofitModel;
import com.akingyin.net.RetrofitUtils;
import com.akingyin.net.api.GitHubApi;
import com.akingyin.pojo.UserInfo;


import org.androidannotations.annotations.EBean;



import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zlcd on 2016/2/16.
 */

@EBean
public class UserRetrofitModelImpl implements IUserRetrofitModel {

    private GitHubApi   api;

    @Override
    public boolean saveUserInfo(UserInfo userInfo) {
        return false;
    }

    public UserRetrofitModelImpl() {
        api = RetrofitUtils.createApi(GitHubApi.class);
    }

    @Override
    public UserInfo getUserInfo(String account) {
     return  null;
    }
}
