package com.md.appdemo;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.md.appdemo.model.UserEntity;

import com.md.appdemo.presenter.impl.DemoPresenterImpl;
import com.md.appdemo.ui.IdemoView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by Administrator on 2016/1/10.
 */

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements IdemoView{

    public   String    action;

    public   EditText   send_message;


    public TextView   tv_message;


    public   static Uri  contentUri = Uri.parse("content://org.jraf.androidcontentprovidergenerator.sample.provider/tb_user");

    @Extra
    public  String   data;

    @ViewById
    public RecyclerView   app_list;

    @Bean
    public   AAUserAdapter   adapter;

   @Bean
    DemoPresenterImpl   demoPresenter;

    @AfterViews
    public  void  initView(){
        demoPresenter.setIview(this);
        send_message = (EditText)findViewById(R.id.send_message);
        tv_message = (TextView)findViewById(R.id.tv_message);

        Button  button  = (Button)findViewById(R.id.app_btn3);
        button.setText(data);

        findViewById(R.id.app_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                String message = send_message.getText().toString().trim();
                intent1.putExtra("data", message);
                setResult(RESULT_OK, intent1);
                finish();
            }
        });

        findViewById(R.id.app_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity  userEntity = new UserEntity();
                userEntity.userName="aaaa";
                userEntity.age=222;
                demoPresenter.addUser(userEntity);
            }
        });

        findViewById(R.id.app_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_message.setText("开始查询主APP数据===>>");


            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        app_list.setLayoutManager(linearLayoutManager);
        app_list.setItemAnimator(new DefaultItemAnimator());

        app_list.setAdapter(adapter);
        demoPresenter.findAllUser();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message, View v) {

    }

    @Override
    public void addUser(UserEntity userEntity) {
          adapter.addTopItem(userEntity);
          adapter.notifyDataSetChanged();
    }

    @Override
    public void modifyUser(UserEntity userEntity) {

    }

    @Override
    public void delectUser(UserEntity userEntity) {

    }

    @Override
    public void findAllUser(List<UserEntity> userEntities) {
        adapter.cleanItems();
        adapter.addAll(userEntities);
        adapter.notifyDataSetChanged();
    }
}
