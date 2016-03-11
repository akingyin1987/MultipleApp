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
import java.util.Iterator;
import java.util.List;
import java.util.Random;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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
            Random  r = new Random();
            final int page = r.nextInt(1000);
            Observable<ImageListBean>  observable = queryImageModel.onLoadData(query, "全部", page, 20, 1);
            observable.observeOn(AndroidSchedulers.mainThread())
                      .subscribeOn(Schedulers.io())
                      .map(new Func1<ImageListBean, ImageListBean>() {
                          @Override
                          public ImageListBean call(ImageListBean imageListBean) {
                              if (null != imageListBean) {
                                  Iterator<ImageListBean.ImgsEntity> iterator = imageListBean.getImgs().iterator();
                                  while (iterator.hasNext()) {
                                      ImageListBean.ImgsEntity item = iterator.next();
                                      if (TextUtils.isEmpty(item.getThumbnailUrl())) {
                                          iterator.remove();
                                      }
                                  }
                              }
                              return imageListBean;
                          }
                      })
                      .subscribe(new Action1<ImageListBean>() {
                          @Override
                          public void call(ImageListBean imageBeans) {
                              iqueryImageListView.onRefresh(imageBeans.getImgs(), true,page);
                          }
                      }, new Action1<Throwable>() {
                          @Override
                          public void call(Throwable throwable) {
                              iqueryImageListView.onRefresh(null, false,page);
                          }
                      });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoadMore(final int page, String query) {
        try {

            Observable<ImageListBean>  observable = queryImageModel.onLoadData(query, "全部", page, 20, 1);
            observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                    .map(new Func1<ImageListBean, ImageListBean>() {
                        @Override
                        public ImageListBean call(ImageListBean imageListBean) {
                            if (null != imageListBean) {
                                Iterator<ImageListBean.ImgsEntity> iterator = imageListBean.getImgs().iterator();
                                while (iterator.hasNext()) {
                                    ImageListBean.ImgsEntity item = iterator.next();
                                    if (TextUtils.isEmpty(item.getThumbnailUrl())) {
                                        iterator.remove();
                                    }
                                }
                            }
                            return imageListBean;
                        }
                    })
                .subscribe(new Action1<ImageListBean>() {
                    @Override
                    public void call(ImageListBean imageBeans) {
                        System.out.println(imageBeans.getImgs().size());
                        iqueryImageListView.onLoadMore(imageBeans.getImgs(), true, page);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iqueryImageListView.onLoadMore(null, false, page);
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
