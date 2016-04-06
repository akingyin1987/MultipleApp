package com.md.appdemo;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.md.appdemo.center.AppComponent;
import com.md.appdemo.center.AppModule;


/**
 * Created by zlcd on 2016/1/11.
 */
public class MyApp extends Application {

    private   static    MyApp   instance;

    public   static  MyApp getInstance(){
        return   instance;
    }


    private AppComponent appComponent;

    public static MyApp get(Context context){
        return (MyApp)context.getApplicationContext();
    }



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Toast.makeText(this,"初始化子Application",Toast.LENGTH_SHORT).show();

        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build());
    }




}
