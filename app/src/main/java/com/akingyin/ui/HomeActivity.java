package com.akingyin.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.akingyin.view.IHomeView;
import com.appeaser.sublimenavigationviewlibrary.OnNavigationMenuEventListener;
import com.appeaser.sublimenavigationviewlibrary.SublimeBaseMenuItem;
import com.appeaser.sublimenavigationviewlibrary.SublimeNavigationView;
import com.md.multipleapp.R;

/**
 * Created by Administrator on 2016/1/26.
 */
public class HomeActivity  extends AppCompatActivity  implements IHomeView{

    SublimeNavigationView snv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(null != getSupportActionBar()){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void initialize() {
        snv = (SublimeNavigationView)findViewById(R.id.navigation_view);
        snv.setNavigationMenuEventListener(new OnNavigationMenuEventListener() {
            @Override
            public boolean onNavigationMenuEvent(Event event, SublimeBaseMenuItem menuItem) {

                return true;
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(View v, String message) {
        Snackbar.make(v,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void OpenMultipleApp() {

    }
}
