package com.md.multipleapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.advancedrecyclerview.RecyclerviewDemoActivity;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/1/10.
 */
public class MainApp  extends AppCompatActivity {

    public   AppInstallReceiver   appInstallReceiver;

    public EditText   editText;

    private static String MY_ACTION = "com.view.my_action";

    public TextView    tv_data;

    public FloatingActionsMenu  menuMultipleActions;
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
        editText = (EditText)findViewById(R.id.send_message);
        tv_data = (TextView)findViewById(R.id.tv_dbdata);
        registerReceiver(appInstallReceiver,intentFilter);
        menuMultipleActions = (FloatingActionsMenu)findViewById(R.id.multiple_actions_left);

        menuMultipleActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               menuMultipleActions.toggle();
            }
        });

        findViewById(R.id.app_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String directory = Environment.getExternalStorageDirectory().getAbsolutePath();

                AutoInstall.copyApkFromAssets(MainApp.this, "appdemo-debug.apk", directory + File.separator + "test.apk");
                AutoInstall.installApk(MainApp.this, directory + File.separator + "test.apk");
            }
        });

        findViewById(R.id.app_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 AutoInstall.uninstallApk(MainApp.this, "com.md.appdemo");
            }
        });

        findViewById(R.id.app_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  message =  editText.getText().toString().trim();
                 if(AutoInstall.isAppInstalled(MainApp.this,"com.md.appdemo")){

                     Intent   intent  =  new Intent();
                     intent.setAction(MY_ACTION);
                     intent.setType("test/");
                     intent.putExtra("data", message);
                     startActivityForResult(intent,1);
                     return;
                 }
                Toast.makeText(MainApp.this,"apk未安装",Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.app_inspectdata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity   userEntity = new UserEntity();
                userEntity.userName="test"+(RandomStringUtils.random(100,"utf-8"));
                userEntity.age = RandomUtils.nextInt();
                userEntity.save();
                tv_data.setText(userEntity.toString());
            }
        });
        findViewById(R.id.app_recyclerview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent  =  new Intent(MainApp.this, RecyclerviewDemoActivity.class);
                startActivity(intent);
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
        System.out.println("requestcode"+requestCode+":"+resultCode);
        if(null != data){
            editText.setText(data.getStringExtra("data"));

        }
    }
}
