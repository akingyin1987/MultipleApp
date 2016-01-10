package com.md.appdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2016/1/10.
 */
public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent   intent  = getIntent();
        String  data  = intent.getStringExtra("data");
        Button  button  = (Button)findViewById(R.id.app_btn3);
        button.setText(data);

        findViewById(R.id.app_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent  intent1  =  new Intent();
                intent1.putExtra("data","收到了的");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        findViewById(R.id.app_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.app_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
