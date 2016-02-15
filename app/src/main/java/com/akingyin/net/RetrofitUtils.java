package com.akingyin.net;

import android.content.Context;

import com.akingyin.okHttp.OkHttpUtils;

import okhttp3.HttpUrl;
import retrofit2.BaseUrl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zlcd on 2016/2/15.
 */
public class RetrofitUtils {

    private static Retrofit singleton;



    private  static  final  String   baseUrl="https://api.douban.com";

    public static <T> T createApi(Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitUtils.class) {
                if (singleton == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(baseUrl);//设置远程地址
                    builder.addConverterFactory(GsonConverterFactory.create());
                    builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                    builder.client(OkHttpUtils.getInstance());
                    singleton = builder.build();
                }
            }
        }
        return singleton.create(clazz);
    }

}