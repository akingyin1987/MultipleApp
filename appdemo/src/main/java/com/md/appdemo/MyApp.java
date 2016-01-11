package com.md.appdemo;

import android.app.Application;
import android.widget.Toast;

/**
 * Created by zlcd on 2016/1/11.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"初始化子Application",Toast.LENGTH_SHORT).show();
    }
}
