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

package com.akingyin.sharelibs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.akingyin.sharelibs.adapter.internal.BaseSuperAdapter;
import com.akingyin.sharelibs.adapter.internal.CRUD;
import com.akingyin.sharelibs.adapter.internal.SuperViewHolder;
import java.util.List;

/**
 * <p>The core class.</p>
 * Created by Cheney on 16/3/30.
 */
public abstract class SuperAdapter<T> extends BaseSuperAdapter<T> implements CRUD<T> {

    private LayoutInflater mLayoutInflater;

    /**
     * Constructor for single itemView type.
     */
    public SuperAdapter(Context context, List<T> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mLayoutInflater = LayoutInflater.from(context);
    }


    /**
     * Constructor for multiple itemView types.
     */
    public SuperAdapter(Context context, List<T> items, IMulItemViewType<T> mulItemViewType) {
        super(context, items, mulItemViewType);
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public SuperViewHolder onCreate(View convertView, ViewGroup parent, int viewType) {
        final SuperViewHolder holder;
        if (viewType == TYPE_HEADER && hasHeaderView()) {
            return new SuperViewHolder(getHeaderView());
        } else if (viewType == TYPE_FOOTER && hasFooterView()) {
            return new SuperViewHolder(getFooterView());
        }

        if (mMulItemViewType != null) {
            holder = SuperViewHolder.get(convertView, mLayoutInflater.inflate(
                    mMulItemViewType.getLayoutId(viewType), parent, false));
        } else {
            holder = SuperViewHolder.get(convertView, mLayoutInflater.inflate(mLayoutResId, parent, false));
        }

        return holder;
    }

    @Override
    public final void add(T item) {
        mList.add(item);
        int index = mList.size() - 1;
        if (hasHeaderView())
            index++;
        notifyItemInserted(index);

    }

    @Override
    public final void insert(int index, T item) {
        mList.add(index, item);
        if (hasHeaderView())
            index++;
        notifyItemInserted(index);

    }

    @Override
    public final void addAll(List<T> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        int start = mList.size();
        mList.addAll(items);
        if (hasHeaderView())
            start++;
        notifyItemRangeInserted(start, items.size());

    }

    @Override
    public final void remove(T item) {
        if (contains(item)) {
            int index = mList.indexOf(item);
            remove(index);
        }
    }

    @Override
    public final void remove(int index) {
        mList.remove(index);
        if (hasHeaderView())
            index++;
        notifyItemRemoved(index);

    }

    @Override
    public final void set(T oldItem, T newItem) {
        set(mList.indexOf(oldItem), newItem);
    }

    @Override
    public final void set(int index, T item) {
        mList.set(index, item);
        if (hasHeaderView())
            index++;
        notifyItemChanged(index);

    }

    @Override
    public final void replaceAll(List<T> items) {
        clear();
        addAll(items);
    }

    @Override
    public final boolean contains(T item) {
        return mList.contains(item);
    }

    @Override
    public final void clear() {
        mList.clear();
        notifyDataSetChanged();

    }


    @Override
    public void addTop(T item) {
        mList.add(0,item);
    }

    @Override
    public void addAllTop(List<T> items) {
        mList.addAll(0,items);
    }


}
