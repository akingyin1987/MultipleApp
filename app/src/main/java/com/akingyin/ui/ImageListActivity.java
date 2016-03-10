/*
 *
 *   Copyright (c) 2016 [akingyin@163.com]
 *
 *   Licensed under the Apache License, Version 2.0 (the "License”);
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.akingyin.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.akingyin.model.impl.QueryImageModelImpl;
import com.akingyin.pojo.ImageBean;
import com.akingyin.presenter.impl.QueryImagelistPresenterImpl;
import com.akingyin.sharelibs.widgets.PullLoadMoreRecyclerView;
import com.akingyin.ui.adapter.ImagelistAdapter;
import com.akingyin.view.IqueryImageListView;
import com.md.multipleapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by Administrator on 2016/3/10.
 */

@EActivity(R.layout.activity_baiduimagelist)
public class ImageListActivity  extends AppCompatActivity  implements IqueryImageListView{

    @ViewById
    public Toolbar toolbar;

    @ViewById
    public PullLoadMoreRecyclerView  recycler_view;

    @Bean
    public ImagelistAdapter  adapter;

    @Bean
    public QueryImagelistPresenterImpl  presenter;

    @AfterViews
    public   void    initView(){
        setSupportActionBar(toolbar);
        if(null != getSupportActionBar()){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        recycler_view.setAdapter(adapter);
        recycler_view.setPushRefreshEnable(true);
        recycler_view.setPullRefreshEnable(true);
        recycler_view.setGridLayout(2);
        recycler_view.getRecyclerView().addItemDecoration(new SpacesItemDecoration(16));
        recycler_view.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh("");
            }

            @Override
            public void onLoadMore() {

            }
        });
        presenter.onRefresh(null);
    }


    @Override
    public void onRefresh(List<ImageBean> items) {
        adapter.clear();
        adapter.addAll(items);
        recycler_view.setPullLoadMoreCompleted();
    }

    @Override
    public void onLoadMore(List<ImageBean> moreitems) {
        adapter.addAll(moreitems);
        recycler_view.setPullLoadMoreCompleted();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(View v, String message) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show();
    }
}
