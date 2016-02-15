package com.akingyin.net.api;

import com.akingyin.pojo.UserInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zlcd on 2016/2/15.
 */
public interface GitHubApi {

    @GET("/users/{account}")
    Observable<UserInfo>   getUserInfo(@Path("account")String  account);


}
