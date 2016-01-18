package com.md.appdemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.md.db.DaoMaster;
import com.md.db.DaoSession;

import de.greenrobot.dao.AbstractDaoMaster;

/**
 * Created by zlcd on 2016/1/11.
 */
public class MyApp extends Application {

    private   static    MyApp   instance;

    public   static  MyApp getInstance(){
        return   instance;
    }


    public DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Toast.makeText(this,"初始化子Application",Toast.LENGTH_SHORT).show();
        setupDatabase();
        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build());
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "test-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


}
