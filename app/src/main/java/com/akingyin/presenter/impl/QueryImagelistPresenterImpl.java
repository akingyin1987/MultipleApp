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

package com.akingyin.presenter.impl;

import android.text.TextUtils;

import com.akingyin.model.impl.QueryImageModelImpl;

import com.akingyin.pojo.ImageListBean;
import com.akingyin.presenter.IQueryImagelistPresenter;
import com.akingyin.view.IqueryImageListView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/10.
 */

@EBean
public class QueryImagelistPresenterImpl  implements IQueryImagelistPresenter {

    @Bean
    public QueryImageModelImpl   queryImageModel;

    private IqueryImageListView    iqueryImageListView;

    public IqueryImageListView getIqueryImageListView() {
        return iqueryImageListView;
    }

    public void setIqueryImageListView(IqueryImageListView iqueryImageListView) {
        this.iqueryImageListView = iqueryImageListView;
    }

    @Override
    public void onRefresh(String query) {
        try {
            String category = TextUtils.isEmpty(query)?"":URLEncoder.encode(query,"utf-8");
            String  tag  = URLEncoder.encode("全部","utf-8");
            Observable<ImageListBean>  observable = queryImageModel.onLoadData(category, tag, 1, 100, 1);
            observable.observeOn(AndroidSchedulers.mainThread())
                      .subscribeOn(Schedulers.io())
                      .subscribe(new Action1<ImageListBean>() {
                          @Override
                          public void call(ImageListBean imageBeans) {
                              iqueryImageListView.onRefresh(imageBeans.getImgs(),true);
                          }
                      }, new Action1<Throwable>() {
                          @Override
                          public void call(Throwable throwable) {
                              iqueryImageListView.onRefresh(null,false);
                          }
                      });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoadMore(int page, String query) {
        try {
            String category = TextUtils.isEmpty(query)?"":URLEncoder.encode(query,"utf-8");
            String  tag  = URLEncoder.encode("全部","utf-8");
            Observable<ImageListBean>  observable = queryImageModel.onLoadData(category, tag, page, 100, 1);
            observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ImageListBean>() {
                    @Override
                    public void call(ImageListBean imageBeans) {
                        iqueryImageListView.onLoadMore(imageBeans.getImgs(), true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iqueryImageListView.onLoadMore(null,false);
                    }
                });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
