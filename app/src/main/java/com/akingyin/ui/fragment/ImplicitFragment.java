package com.akingyin.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.advancedrecyclerview.RecyclerviewDemoActivity;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.md.multipleapp.AppInstallReceiver;
import com.md.multipleapp.AutoInstall;
import com.md.multipleapp.R;
import com.md.multipleapp.UserEntity;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/1/27.
 */
public class ImplicitFragment extends Fragment{
    public AppInstallReceiver appInstallReceiver;

    public EditText editText;

    private static String MY_ACTION = "com.view.my_action";

    public TextView tv_data;

    public FloatingActionsMenu menuMultipleActions;

    public   static   ImplicitFragment   newInstance(){
        ImplicitFragment   fragment = new ImplicitFragment();
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_implicit,null);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = (EditText)view.findViewById(R.id.send_message);
        tv_data = (TextView)view.findViewById(R.id.tv_dbdata);
        menuMultipleActions = (FloatingActionsMenu)view.findViewById(R.id.multiple_actions_left);

        menuMultipleActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuMultipleActions.toggle();
            }
        });

        view.findViewById(R.id.app_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String directory = Environment.getExternalStorageDirectory().getAbsolutePath();

                AutoInstall.copyApkFromAssets(getContext(), "appdemo-debug.apk", directory + File.separator + "test.apk");
                AutoInstall.installApk(getContext(), directory + File.separator + "test.apk");
            }
        });

        view.findViewById(R.id.app_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoInstall.uninstallApk(getContext(), "com.md.appdemo");
            }
        });

        view.findViewById(R.id.app_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString().trim();
                if (AutoInstall.isAppInstalled(getContext(), "com.md.appdemo")) {

                    Intent intent = new Intent();
                    intent.setAction(MY_ACTION);
                    intent.setType("test/");
                    intent.putExtra("data", message);
                    startActivityForResult(intent, 1);
                    return;
                }
                Toast.makeText(getContext(), "apk未安装", Toast.LENGTH_SHORT).show();

            }
        });

        view.findViewById(R.id.app_inspectdata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity = new UserEntity();
                userEntity.userName="test"+(RandomStringUtils.random(100, "utf-8"));
                userEntity.age = RandomUtils.nextInt();
                userEntity.save();
                tv_data.setText(userEntity.toString());
            }
        });
       view.findViewById(R.id.app_recyclerview).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getContext(), RecyclerviewDemoActivity.class);
               startActivity(intent);
           }
       });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestcode" + requestCode + ":" + resultCode);
        if(null != data){
            editText.setText(data.getStringExtra("data"));

        }
    }
}