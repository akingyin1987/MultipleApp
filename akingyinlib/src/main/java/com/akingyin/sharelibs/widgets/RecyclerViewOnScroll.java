package com.akingyin.sharelibs.widgets;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by WuXiaolong on 2015/7/7.
 */
public class RecyclerViewOnScroll extends RecyclerView.OnScrollListener implements View.OnTouchListener {
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    public RecyclerViewOnScroll(PullLoadMoreRecyclerView pullLoadMoreRecyclerView) {
        this.mPullLoadMoreRecyclerView = pullLoadMoreRecyclerView;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastCompletelyVisibleItem = 0;
        int firstVisibleItem = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();

        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
            System.out.println("sp="+gridLayoutManager.getSpanCount());
            //Position to find the final item of the current LayoutManager
            lastCompletelyVisibleItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            firstVisibleItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
            lastCompletelyVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            firstVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
            // since may lead to the final item has more than one StaggeredGridLayoutManager the particularity of the so here that is an array
            // this array into an array of position and then take the maximum value that is the last show the position value
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
            lastCompletelyVisibleItem = findMax(lastPositions);
            int[] firstPositions = staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(lastPositions);
            firstVisibleItem = findMin(firstPositions);
        }
        System.out.println("firstVisibleItem="+firstVisibleItem+":"+lastCompletelyVisibleItem);
        if (firstVisibleItem == 0) {
            if (mPullLoadMoreRecyclerView.getPullRefreshEnable())
                mPullLoadMoreRecyclerView.setSwipeRefreshEnable(true);
        } else {
               mPullLoadMoreRecyclerView.setSwipeRefreshEnable(false);
        }

        System.out.println(lastCompletelyVisibleItem+":"+totalItemCount+":"+mPullLoadMoreRecyclerView.getPullRefreshEnable());
        System.out.println(mPullLoadMoreRecyclerView.isRefresh()+":"+mPullLoadMoreRecyclerView.isLoadMore());
        if (mPullLoadMoreRecyclerView.getPushRefreshEnable() &&
                !mPullLoadMoreRecyclerView.isRefresh()
                && mPullLoadMoreRecyclerView.isHasMore()
                && (lastCompletelyVisibleItem == totalItemCount - 1 ||(lastCompletelyVisibleItem==totalItemCount-1) )
                && !mPullLoadMoreRecyclerView.isLoadMore()
                && (dx > 0 || dy > 0)) {
            mPullLoadMoreRecyclerView.loadMore();
        }else{
            mPullLoadMoreRecyclerView.onCancelLoadMore();
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    int    lasty=0;
    public   boolean   isdown = false;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                isdown = lasty-event.getY()<0;
                lasty = (int)event.getY();
                break;
        }
        return false;
    }

    //To find the maximum value in the array

    private int findMax(int[] lastPositions) {

        int max = lastPositions[0];
        for (int value : lastPositions) {
            //       int max    = Math.max(lastPositions,value);
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private  int  findMin(int[] lastPositions){
        int min = lastPositions[0];
        for (int value : lastPositions) {
            //       int max    = Math.max(lastPositions,value);
            if (value < min) {
                min = value;
            }
        }
        return min;
    }
}
