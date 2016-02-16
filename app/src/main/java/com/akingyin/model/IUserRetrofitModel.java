package com.akingyin.model;

import com.akingyin.pojo.UserInfo;

/**
 * Created by zlcd on 2016/2/16.
 */
public interface IUserRetrofitModel {

    public UserInfo   getUserInfo(String account);

    public boolean    saveUserInfo(UserInfo  userInfo);
}
