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

package com.akingyin.model.impl;

import com.akingyin.model.IQueryImageModel;
import com.akingyin.net.RetrofitUtils;
import com.akingyin.net.api.BaiDuImagesApi;

import com.akingyin.pojo.ImageListBean;

import org.androidannotations.annotations.EBean;

import java.util.List;

import rx.Observable;


/**
 * Created by Administrator on 2016/3/10.
 */

@EBean
public class QueryImageModelImpl  implements IQueryImageModel {


    private BaiDuImagesApi    api;
    public QueryImageModelImpl() {
        api = RetrofitUtils.createApi(BaiDuImagesApi.class,RetrofitUtils.BAIDU_IMAGES_URLS);
    }

    @Override
    public Observable<ImageListBean> onLoadData(String category, String tag, int pn, int rn, int from) {
        System.out.println("tag="+tag+"--->>"+category);
        return api.queryImages(category,tag,pn,rn,from);
    }
}
