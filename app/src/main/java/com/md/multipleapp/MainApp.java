package com.md.multipleapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2016/1/10.
 */
public class MainApp  extends AppCompatActivity {

    public   AppInstallReceiver   appInstallReceiver;

    private static String MY_ACTION = "com.view.my_action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appInstallReceiver = new AppInstallReceiver();
        IntentFilter   intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addDataScheme("package");

        registerReceiver(appInstallReceiver,intentFilter);

        findViewById(R.id.app_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  String  directory = Environment.getExternalStorageDirectory().getAbsolutePath();

                  AutoInstall.copyApkFromAssets(MainApp.this,"appdemo-debug.apk",directory+ File.separator+"test.apk");
                  AutoInstall.installApk(MainApp.this,directory+ File.separator+"test.apk");
            }
        });

        findViewById(R.id.app_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 AutoInstall.uninstallApk(MainApp.this,"com.md.appdemo");
            }
        });

        findViewById(R.id.app_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(AutoInstall.isAppInstalled(MainApp.this,"com.md.appdemo")){
                     System.out.println("222222222222222");
                     Intent   intent  =  new Intent();
                     intent.setAction(MY_ACTION);
                     intent.setType("test/");
                     intent.putExtra("data", "test");
                     startActivityForResult(intent,1);
                     return;
                 }
                Toast.makeText(MainApp.this,"apk未安半",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(appInstallReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestcode"+requestCode);
        if(null != data){
            System.out.println(data.getStringExtra("data"));
        }
    }
}
