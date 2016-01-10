package com.md.multipleapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/1/10.
 */
public class AutoInstall {


    /**
     * 安装
     *
     * @param context
     *            接收外部传进来的context
     */
    public static void installApk(Context context,@NonNull String mUrl) {

        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(mUrl)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    public  static  void  uninstallApk(Context  context,@NonNull String  packageName){
        Uri uri = null;
        if(packageName.startsWith("package")){
            uri = Uri.parse(packageName);
        }else{
            uri = Uri.parse("package:"+packageName);
        }

        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        context.startActivity(intent);
    }

    public static boolean copyApkFromAssets(Context context, String fileName, String path) {
        boolean copyIsFinish = false;
        try {
            InputStream is = context.getAssets().open(fileName);
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            copyIsFinish = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copyIsFinish;
    }


    public   static    boolean  isAppInstalled(Context  context ,String packageName){
        PackageManager pm = context.getPackageManager();
        try {
           pm.getPackageInfo(packageName,PackageManager.GET_ACTIVITIES);
            return true;
        }catch (Exception e){
            return  false;
        }
    }
}
