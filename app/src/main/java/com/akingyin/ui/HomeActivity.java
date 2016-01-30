package com.akingyin.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.akingyin.presenter.IHomePresenter;
import com.akingyin.presenter.impl.HomePresenterImpl;
import com.akingyin.ui.fragment.FlashlightFragment;
import com.akingyin.ui.fragment.ImplicitFragment;
import com.akingyin.ui.fragment.UserListFragment;
import com.akingyin.view.IHomeView;

import com.jakewharton.rxbinding.view.RxView;
import com.md.multipleapp.AppInstallReceiver;
import com.md.multipleapp.R;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/1/26.
 */
public class HomeActivity  extends AppCompatActivity  implements IHomeView{

    DrawerLayout snv;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private IHomePresenter   homePresenter;
    public AppInstallReceiver appInstallReceiver;
    private static String MY_ACTION = "com.view.my_action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if(null != getSupportActionBar()){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        homePresenter = new HomePresenterImpl(this);
        homePresenter.initialize(savedInstanceState);
    }

    @Override
    public void initialize(Bundle savedInstanceState) {
        snv = (DrawerLayout)findViewById(R.id.drawer_layout);
        System.out.println("snv=="+(null == snv));
        mDrawerToggle = new ActionBarDrawerToggle(this, snv, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        snv.setDrawerListener(mDrawerToggle);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_implicitapp:
                        OpenMultipleApp();
                        break;
                    case R.id.navigation_userlist:
                        switchUserManager();
                        break;
                    case R.id.navigation_flashlight:
                        switchFlashlight();
                        break;
                }
                item.setChecked(true);
                snv.closeDrawers();
                return true;
            }
        });
        appInstallReceiver = new AppInstallReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addDataScheme("package");
        registerReceiver(appInstallReceiver, intentFilter);
        if(null == savedInstanceState){

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, ImplicitFragment.newInstance(),"implicit").commit();
        }else{

        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(View v, String message) {
        Snackbar.make(v,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void OpenMultipleApp() {
        ImplicitFragment  fragment =(ImplicitFragment) getSupportFragmentManager().findFragmentByTag("implicit");

        if(null == fragment){
            fragment = ImplicitFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment, "implicit").commit();
    }

    @Override
    public void switchUserManager() {
        UserListFragment  fragment = (UserListFragment)getSupportFragmentManager().findFragmentByTag("usermagager");
        if(null == fragment){
            fragment = UserListFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment, "usermagager").commit();
    }

    @Override
    public void switchFlashlight() {
        FlashlightFragment  fragment = (FlashlightFragment)getSupportFragmentManager().findFragmentByTag("flashlight");
        if(null == fragment){
            fragment = FlashlightFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment,"flashlight").commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        System.out.println(null == snv);
        if (snv.isDrawerOpen(Gravity.LEFT)) {
            snv.closeDrawer(Gravity.LEFT);
        } else {
            doExitApp();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return  homePresenter.onNavMenuEvent(item);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(appInstallReceiver);
    }
}
