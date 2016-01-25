package com.md.multipleapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.advancedrecyclerview.RecyclerviewDemoActivity;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.listener.OnRapidFloatingActionListener;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/10.
 */
public class MainApp  extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    public   AppInstallReceiver   appInstallReceiver;

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfabHelper;

    public EditText   editText;

    private static String MY_ACTION = "com.view.my_action";

    public TextView    tv_data;

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


        rfaLayout = (RapidFloatingActionLayout)findViewById(R.id.rfab_group_sample_fragment_a_rfal);
        rfaButton = (RapidFloatingActionButton)findViewById(R.id.rfab_group_sample_rfab_a);


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
        initRFAB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(appInstallReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestcode" + requestCode + ":" + resultCode);
        if(null != data){
            editText.setText(data.getStringExtra("data"));

        }
    }


    private void initRFAB() {


        /*
        // 可通过代码设置属性
        rfaLayout.setFrameColor(Color.RED);
        rfaLayout.setFrameAlpha(0.4f);
        rfaBtn.setNormalColor(0xff37474f);
        rfaBtn.setPressedColor(0xff263238);
        rfaBtn.getRfabProperties().setShadowDx(ABTextUtil.dip2px(context, 3));
        rfaBtn.getRfabProperties().setShadowDy(ABTextUtil.dip2px(context, 3));
        rfaBtn.getRfabProperties().setShadowRadius(ABTextUtil.dip2px(context, 5));
        rfaBtn.getRfabProperties().setShadowColor(0xffcccccc);
        rfaBtn.getRfabProperties().setStandardSize(RFABSize.MINI);
        rfaBtn.build();
        */


        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Github: wangjiegulu")
                .setResId(R.mipmap.ico_test_d)
                .setIconNormalColor(0xff6a1b9a)
                .setIconPressedColor(0xff4a148c)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("tiantian.china.2@gmail.com")
                .setResId(R.mipmap.ico_test_c)
                .setIconNormalColor(0xff4e342e)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(Color.WHITE)
                .setLabelSizeSp(14)
                .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(this, 4)))
                .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("WangJie")
                .setResId(R.mipmap.ico_test_b)
                .setIconNormalColor(0xff056f00)
                .setIconPressedColor(0xff0d5302)
                .setLabelColor(0xff056f00)
                .setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Compose")
                .setResId(R.mipmap.ico_test_a)
                .setIconNormalColor(0xff283593)
                .setIconPressedColor(0xff1a237e)
                .setLabelColor(0xff283593)
                .setWrapper(3)
        );
        rfaContent
            .setItems(items)
            .setIconShadowRadius(ABTextUtil.dip2px(this, 5))
            .setIconShadowColor(0xff888888)
            .setIconShadowDy(ABTextUtil.dip2px(this, 5))
        ;

        rfabHelper = new RapidFloatingActionHelper(
            this,
            rfaLayout,
            rfaButton,
            rfaContent
        ).build();
    }

    @Override
    public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {
        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {
        rfabHelper.toggleContent();
    }
}
