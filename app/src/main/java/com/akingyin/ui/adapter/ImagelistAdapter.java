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

package com.akingyin.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.akingyin.pojo.ImageBean;
import com.md.multipleapp.R;
import com.squareup.picasso.Picasso;

import org.byteam.superadapter.recycler.BaseViewHolder;
import org.byteam.superadapter.recycler.SuperAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/3/10.
 */
public class ImagelistAdapter extends SuperAdapter<ImageBean> {
    public ImagelistAdapter(Context context, List<ImageBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(int viewType, BaseViewHolder holder, int position, ImageBean item) {
         holder.setText(R.id.item_text,"postion"+position);
         ImageView  imageView = holder.getView(R.id.item_image);

    }
}
