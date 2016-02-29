package com.akingyin.ui;

import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.akingyin.sharelibs.widgets.PullLoadMoreRecyclerView;
import com.akingyin.ui.adapter.UserRecyclerviewAdapter;
import com.md.multipleapp.R;
import com.md.multipleapp.UserEntity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.byteam.superadapter.recycler.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/29.
 */


@EActivity(R.layout.activity_recyclerviewmore)
public class RecyclerviewActivity extends AppCompatActivity{

    @ViewById
    public Toolbar   toolbar;

    @ViewById
    public FloatingActionButton  fab;

    @ViewById
    public CollapsingToolbarLayout  collapsing_toolbar;

    @ViewById
    public PullLoadMoreRecyclerView  recycler_view;

    public UserRecyclerviewAdapter  adapter;


    @AfterViews
    public   void    initView(){
        setSupportActionBar(toolbar);
        if(null != getSupportActionBar()){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        collapsing_toolbar.setTitle("测试");
        List<UserEntity>   items = new ArrayList<>();
         for(int  i=0;i<10;i++){
             UserEntity  userEntity = new UserEntity();
             userEntity.userName="test"+i;
             items.add(userEntity);
         }
        adapter = new UserRecyclerviewAdapter(this, items,R.layout.item_user);
        recycler_view.setAdapter(adapter);
        recycler_view.setPushRefreshEnable(true);
        recycler_view.setPullRefreshEnable(true);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {

            }
        });
        recycler_view.setLinearLayout();

        recycler_view.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
               adapter.clear();
                List<UserEntity>   items = new ArrayList<>();
                for(int  i=0;i<10;i++){
                    UserEntity  userEntity = new UserEntity();
                    userEntity.userName="test"+i;
                    items.add(userEntity);
                }
                adapter.addAll(items);
                recycler_view.setPullLoadMoreCompleted();
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<UserEntity> items = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            UserEntity userEntity = new UserEntity();
                            userEntity.userName = "test" + i;
                            items.add(userEntity);
                        }
                        adapter.addAll(items);
                        recycler_view.setPullLoadMoreCompleted();
                    }
                }, 2000);

            }
        });

    }
}
