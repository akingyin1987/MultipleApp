package com.akingyin.net.api;

import com.akingyin.pojo.UserInfo;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zlcd on 2016/2/15.
 */
public interface GitHubApi {

    @GET("/users/{account}")
    Observable<UserInfo>   getUserInfo(@Path("account")String  account);

    @GET("/v2/movie/in_theaters")
    Observable<ResponseBody>  getHostMoves(@Query("city") String  account);

}
