package com.akingyin.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.akingyin.presenter.IHomePresenter;
import com.akingyin.presenter.impl.HomePresenterImpl;
import com.akingyin.ui.fragment.ChangelogFragment;
import com.akingyin.ui.fragment.FlashlightFragment;
import com.akingyin.ui.fragment.ImplicitFragment;
import com.akingyin.ui.fragment.RetrofitFragment;
import com.akingyin.ui.fragment.RxViewFragment;
import com.akingyin.ui.fragment.UserListFragment;
import com.akingyin.ui.fragment.VoiceFragment;
import com.akingyin.view.IHomeView;

import com.md.multipleapp.AppInstallReceiver;
import com.md.multipleapp.R;

import info.hoang8f.android.segmented.SegmentedGroup;
import rx.Observable;


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
        System.out.println("mtoolbar="+(null == mToolbar));
        setSupportActionBar(mToolbar);

        if(null != getSupportActionBar()){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        SegmentedGroup segmentedGroup = (SegmentedGroup) mToolbar.findViewById(R.id.toolbar_segment);
        segmentedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.toolbar_phone){
                    showMessage(group,"one");
                }else{
                    showMessage(group,"tow");
                }
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_edit){
                    showMessage("编辑");
                }
                return false;
            }
        });
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
                    case R.id.navigation_voice:
                        switchVoiceRecord();
                        break;
                    case R.id.navigation_rxview:
                        switchRxview();
                        break;
                    case R.id.navigation_retrofit:
                        switchRetrofit();
                        break;
                    case R.id.navigation_changelogs:
                        switchChangeLog();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment, "flashlight").commit();

    }

    @Override
    public void switchVoiceRecord() {
        VoiceFragment fragment = (VoiceFragment)getSupportFragmentManager().findFragmentByTag("voice");
        if(null == fragment){
            fragment = VoiceFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment,"voice").commit();
    }

    @Override
    public void switchRetrofit() {
        RetrofitFragment   fragment = (RetrofitFragment)getSupportFragmentManager().findFragmentByTag("retrofit");
        if(null == fragment){
           fragment = com.akingyin.ui.fragment.RetrofitFragment_.builder().build();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment,"retrofit").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  true;
    }

    @Override
    public void switchRxview() {
        RxViewFragment fragment = (RxViewFragment)getSupportFragmentManager().findFragmentByTag("rxview");
        if(null == fragment){
            fragment = RxViewFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment,"rxview").commit();
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

    @Override public void switchChangeLog() {
        ChangelogFragment fragment = (ChangelogFragment)getSupportFragmentManager().findFragmentByTag("changelog");
        if(null == fragment){
            fragment = ChangelogFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment,"changelog").commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(appInstallReceiver);
    }
}
