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

package com.akingyin.sharelibs.adapter.internal;

import android.view.View;
import android.view.ViewGroup;

/**
 * <p>Create and bind data to item view.</p>
 * Created by Cheney on 16/3/31.
 */
interface IViewBindData<T, VH> {

    /**
     * @param convertView Support by {@link AABaseSuperAdapter#(int, View, ViewGroup)}.
     * @param parent      Target container(ListView, GridView, RecyclerView,Spinner, etc.).
     * @param viewType    Choose the layout resource according to view type.
     * @return Created view holder.
     */
    VH onCreate(View convertView, ViewGroup parent, int viewType);

    /**
     * Method for binding data to view.
     *
     * @param holder   ViewHolder
     * @param viewType {@link AABaseSuperAdapter#getItemViewType(int)}
     * @param position position
     * @param item     data
     */
    void onBind(VH holder, int viewType, int position, T item);
}
