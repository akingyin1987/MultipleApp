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

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.akingyin.sharelibs.adapter.IMulItemViewType;
import com.akingyin.sharelibs.adapter.OnItemClickListener;
import com.akingyin.sharelibs.adapter.OnItemLongClickListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>Base adapter.</p>
 * Created by Cheney on 16/3/30.
 */
public abstract class AABaseSuperAdapter<T,V extends  View> extends RecyclerView.Adapter<BaseViewHolder<V>>
        implements IViewBindData<T, BaseViewHolder<V>>,
        ILayoutManager, IHeaderFooter {

    protected Context mContext;
    protected List<T> mList = new LinkedList<>(); // DataSources.

    protected int mLayoutResId;
    protected IMulItemViewType<T> mMulItemViewType;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    protected RecyclerView mRecyclerView;
    protected static final int TYPE_HEADER = -0x100;
    protected static final int TYPE_FOOTER = -0x101;
    protected static final int TYPE_ITEM = 0;
    private View mHeader;
    private View mFooter;

    /**
     * Constructor for single item view type.
     *
     * @param context     Context
     * @param list        Data list.
     * @param layoutResId {@link android.support.annotation.LayoutRes}
     */
    public AABaseSuperAdapter(Context context, List<T> list, int layoutResId) {
        this.mContext = context;
        this.mList = list == null ? new ArrayList<T>() : new ArrayList<>(list);
        this.mLayoutResId = layoutResId;
        this.mMulItemViewType = null;
    }

    /**
     * Constructor for multiple item view type.
     *
     * @param context         Context
     * @param list            Data list.
     * @param mulItemViewType If null, plz override {@link #offerMultiItemViewType()}.
     */
    public AABaseSuperAdapter(Context context, List<T> list, IMulItemViewType<T> mulItemViewType) {
        this.mContext = context;
        if(null != list){
            mList.addAll(list);
        }
        this.mMulItemViewType = mulItemViewType == null ? offerMultiItemViewType() : mulItemViewType;
    }

    public AABaseSuperAdapter(){}
    public AABaseSuperAdapter(Context  context){
        this.mContext = context;
    }

    /**
     * @return Offered an {@link IMulItemViewType} by override this method.
     */
    protected IMulItemViewType<T> offerMultiItemViewType() {
        return null;
    }

    public Context getContext() {
        return mContext;
    }

    public List<T> getList() {
        return mList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */

    public int getCount() {

        return mList == null ? 0 : mList.size();
    }


    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */

    public T getItem(int position) {
        if (position<0 || position >= mList.size())
            return null;
        return mList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {

        return position;
    }

    /**
     * How many items are in the data set represented by this RecyclerView.Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getItemCount() {
        int size = mList == null ? 0 : mList.size();
        if (hasHeaderView())
            size++;
        if (hasFooterView())
            size++;
        return size;
    }




    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (isHeaderView(position)) {
            viewType = TYPE_HEADER;
        } else if (isFooterView(position)) {
            viewType = TYPE_FOOTER;
        } else {
            if (mMulItemViewType != null) {
                if (hasHeaderView()) {
                    position--;
                }
                return mMulItemViewType.getItemViewType(position, mList.get(position));
            }
            return TYPE_ITEM;
        }
        return viewType;
    }



    @Override
    public BaseViewHolder<V> onCreateViewHolder(ViewGroup parent, final int viewType) {
        final BaseViewHolder<V> holder = onCreate(null, parent, viewType);
        if (!(holder.itemView instanceof AdapterView)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, viewType, holder.getLayoutPosition());
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onItemLongClick(v, viewType, holder.getLayoutPosition());
                        return true;
                    }
                    return false;
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<V> holder, int position) {
        int viewType = getItemViewType(position);
        if (hasHeaderView())
            position--;
        if (viewType != TYPE_HEADER && viewType != TYPE_FOOTER) {
            onBind(holder, viewType, position, mList.get(position));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView != null && mRecyclerView != recyclerView)
            Log.i("BaseSuperAdapter", "Does not support multiple RecyclerViews now.");
        mRecyclerView = recyclerView;
        // Ensure a situation that add header or footer before setAdapter().
        ifGridLayoutManager();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = null;
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder<V> holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            // add header or footer to StaggeredGridLayoutManager
            if (isHeaderView(holder.getLayoutPosition()) || isFooterView(holder.getLayoutPosition())) {
                ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
            }
        }
    }

    @Override
    public boolean hasLayoutManager() {
        return mRecyclerView != null && mRecyclerView.getLayoutManager() != null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return hasLayoutManager() ? mRecyclerView.getLayoutManager() : null;
    }

    @Override
    public View getHeaderView() {
        return mHeader;
    }

    @Override
    public View getFooterView() {
        return mFooter;
    }

    @Override
    public void addHeaderView(View header) {
        if (hasHeaderView())
            throw new IllegalStateException("You have already added a header view.");
        mHeader = header;
        ifGridLayoutManager();
        notifyItemInserted(0);
    }

    @Override
    public void addFooterView(View footer) {
        if (hasFooterView())
            throw new IllegalStateException("You have already added a footer view.");
        mFooter = footer;
        ifGridLayoutManager();
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public boolean removeHeaderView() {
        if (hasHeaderView()) {
            notifyItemRemoved(0);
            mHeader = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFooterView() {
        if (hasFooterView()) {
            notifyItemRemoved(getItemCount() - 1);
            mFooter = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasHeaderView() {
        return getHeaderView() != null;
    }

    @Override
    public boolean hasFooterView() {
        return getFooterView() != null;
    }

    @Override
    public boolean isHeaderView(int position) {
        return hasHeaderView() && position == 0;
    }

    @Override
    public boolean isFooterView(int position) {
        return hasFooterView() && position == getItemCount() - 1;
    }

    private void ifGridLayoutManager() {
        final RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                        ((GridLayoutManager) layoutManager).getSpanCount() :
                        originalSpanSizeLookup.getSpanSize(position);
                }
            });
        }
    }
}
