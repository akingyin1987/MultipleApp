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

package com.akingyin.view;

import android.view.View;


import com.akingyin.pojo.ImageListBean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/10.
 */
public interface IqueryImageListView {

    public    void    onRefresh(List<ImageListBean.ImgsEntity>  items,boolean   success,int page);


    public    void    onLoadMore(List<ImageListBean.ImgsEntity> moreitems,boolean   success,int page);

    public   void   showMessage(String  message);

    public   void   showMessage(View v,String message);

}
