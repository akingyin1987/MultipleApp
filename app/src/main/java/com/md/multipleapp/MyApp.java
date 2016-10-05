package com.md.multipleapp;


import android.app.Application;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.akingyin.sharelibs.jlog.JLog;
import com.facebook.stetho.Stetho;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by zlcd on 2016/1/11.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("App create");
        JLog.init(this);
        JLog.d("onCreate");
        Configuration  cfg = new Configuration.Builder(this)
                       .addModelClass(UserEntity.class)
                       .create();
        ActiveAndroid.initialize(cfg,true);

        CrashReport.initCrashReport(this, "900018539", true);
        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build());
      //  CrashReport.testJavaCrash();


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }


    private String buildBundleKey() throws PackageManager.NameNotFoundException {
        PackageInfo
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
        return String.valueOf(packageInfo.versionCode) + "_" + packageInfo.versionName;
    }

    private List<String> getBundleEntryNames(ZipFile zipFile, String str, String str2) {
        List<String> arrayList = new ArrayList();
        try {
            Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                String name = ((ZipEntry) entries.nextElement()).getName();
                if (name.startsWith(str) && name.endsWith(str2)) {
                    arrayList.add(name);
                }
            }
        } catch (Throwable e) {
            Log.e("getBundleEntryNames", "Exception while get bundles in assets or lib", e);
        }
        return arrayList;
    }




}
