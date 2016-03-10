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

import com.akingyin.model.impl.QueryImageModelImpl;
import com.akingyin.presenter.IQueryImagelistPresenter;
import com.akingyin.view.IqueryImageListView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by Administrator on 2016/3/10.
 */

@EBean
public class QueryImagelistPresenterImpl  implements IQueryImagelistPresenter {

    @Bean
    public QueryImageModelImpl   queryImageModel;

    private IqueryImageListView    iqueryImageListView;

    public QueryImagelistPresenterImpl(IqueryImageListView iqueryImageListView) {
        this.iqueryImageListView = iqueryImageListView;
    }

    @Override
    public void onRefresh(String query) {

    }

    @Override
    public void onLoadMore(int page, String query) {

    }
}
