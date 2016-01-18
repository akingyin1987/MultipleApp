package com.md.appdemo;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.md.appdemo.adapter.MyPagerAdapter;
import com.md.appdemo.presenter.DemoTabViewPagerPresenter;
import com.md.appdemo.presenter.impl.DemoTabPresenterImpl;
import com.md.appdemo.ui.ITabViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by zlcd on 2016/1/18.
 */
public class TabDemoActivity  extends AppCompatActivity  implements ITabViewPager{

    public   static    String[]  contents = new String[]{
            "http://e.hiphotos.baidu.com/image/h%3D300/sign=0d25eb3f8c1363270aedc433a18da056/11385343fbf2b2118fe9f2adcc8065380dd78e09.jpg",
            "http://g.hiphotos.baidu.com/image/h%3D360/sign=1e6b6b3250e736d147138a0eab514ffc/241f95cad1c8a7866f726fe06309c93d71cf5087.jpg",
            "http://c.hiphotos.baidu.com/image/h%3D360/sign=a05d5f5ea00f4bfb93d09852334e788f/10dfa9ec8a136327408ff2f2958fa0ec09fac794.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3068725873,153093708&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2139371473,3650994049&fm=21&gp=0.jpg"
    };
    public MyPagerAdapter   adapter;

    public DemoTabViewPagerPresenter   presenter;

    public ViewPager   viewPager;

    public TabLayout tabLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_fragment);
        Toolbar   toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(null != getSupportActionBar()){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("测试标题名字");
        ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
        ivImage.setImageResource(R.mipmap.ic_launcher);
        findViewById(R.id.tab_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int postion = 0;
                Random  random = new Random();
               postion=  random.nextInt(contents.length);
                Snackbar.make(v, "Tip"+postion, Snackbar.LENGTH_SHORT).show();
                adapter.onLoadData(postion%2, contents[postion]);
                TabLayout.Tab  tab = tabLayout.getTabAt(postion%2);
                if(null != tab){
                   tab.setText("TEXT" + postion);
                }

            }
        });

        tabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        presenter = new DemoTabPresenterImpl(this);
        presenter.init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem   menuItem  = menu.findItem(R.id.ab_search);
        SearchView    searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("newText="+newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void init() {

          adapter = new MyPagerAdapter(getSupportFragmentManager());

          adapter.addFragment(TabFragmentDemo.newInstance(contents[0]),"test1");
        adapter.addFragment(TabFragmentDemo.newInstance(contents[1]),"test2");
         viewPager.setAdapter(adapter);
          viewPager.setOffscreenPageLimit(adapter.getCount());
        tabLayout.addTab(tabLayout.newTab().setText("test1"));
        tabLayout.addTab(tabLayout.newTab().setText("test2"));

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void initImageFragemnt() {

    }

    @Override
    public void initTextFragemnt() {

    }
}
