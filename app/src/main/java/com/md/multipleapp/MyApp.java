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
import ctrip.android.bundle.framework.BundleCore;
import ctrip.android.bundle.framework.BundleException;
import ctrip.android.bundle.hotpatch.HotPatchManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
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
        SharedPreferences sharedPreferences;
        boolean isDexInstalled = true;
        final String bundleKey;
        try {
            BundleCore.getInstance().init(this);
            BundleCore.getInstance().ConfigLogger(true, 1);
            Properties properties = new Properties();
            properties.put("com.akingyin.ui.home", "com.akingyin.ui.HomeActivity"); // launch page
            sharedPreferences = getSharedPreferences("bundlecore_configs", 0);
            String lastBundleKey = sharedPreferences.getString("last_bundle_key", "");
            bundleKey = buildBundleKey();
            JLog.d("LAST="+lastBundleKey+":"+bundleKey);
            if (!TextUtils.equals(bundleKey, lastBundleKey)) {
                properties.put("ctrip.bundle.init", "true");
                isDexInstalled = false;
                HotPatchManager.getInstance().purge();
            }
            BundleCore.getInstance().startup(properties);
            if (isDexInstalled) {
                HotPatchManager.getInstance().run();
                BundleCore.getInstance().run();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JLog.d(getApplicationInfo().sourceDir);
                            ZipFile zipFile = new ZipFile(getApplicationInfo().sourceDir);
                            List bundleFiles = getBundleEntryNames(zipFile, BundleCore.LIB_PATH, ".so");
                            if (bundleFiles != null && bundleFiles.size() > 0) {
                                processLibsBundles(zipFile, bundleFiles);
                                SharedPreferences.Editor edit = getSharedPreferences("bundlecore_configs", 0).edit();
                                edit.putString("last_bundle_key", bundleKey);
                                edit.commit();
                            } else {
                                Log.e("Error Bundle", "not found bundle in apk");
                            }
                            if (zipFile != null) {
                                try {
                                    zipFile.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            BundleCore.getInstance().run();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                }).start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private void processLibsBundles(ZipFile zipFile, List<String> list) {

        for (String str : list) {
            processLibsBundle(zipFile, str);
        }
    }

    private boolean processLibsBundle(ZipFile zipFile, String str) {

        String packageNameFromEntryName = getPackageNameFromEntryName(str);

        if (BundleCore.getInstance().getBundle(packageNameFromEntryName) == null) {
            try {
                BundleCore.getInstance().installBundle(packageNameFromEntryName, zipFile.getInputStream(zipFile.getEntry(str)));
                Log.e("Succeed install", "Succeed to install bundle " + packageNameFromEntryName);
                return true;
            } catch (BundleException ex) {
                Log.e("Fail install", "Could not install bundle.", ex);
            } catch (IOException iex) {
                Log.e("Fail install", "Could not install bundle.", iex);
            }
        }
        return false;
    }

    private String getPackageNameFromEntryName(String entryName) {
        return entryName.substring(entryName.indexOf(BundleCore.LIB_PATH) + BundleCore.LIB_PATH.length(), entryName.indexOf(".so")).replace("_", ".");
    }
}
