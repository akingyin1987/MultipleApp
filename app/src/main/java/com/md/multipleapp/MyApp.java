package com.md.multipleapp;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

/**
 * Created by zlcd on 2016/1/11.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("App create");
        Configuration  cfg = new Configuration.Builder(this)
                       .addModelClass(UserEntity.class)
                       .create();
        ActiveAndroid.initialize(cfg,true);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
