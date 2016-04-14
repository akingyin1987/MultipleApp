package com.advancedrecyclerview;

import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import com.md.multipleapp.R;

import org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zlcd on 2016/1/22.
 */
public class RecyclerviewDemoActivity  extends AppCompatActivity {

    public RecyclerView recycler_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewDragDropManager mRecyclerViewDragDropManager;
    private RecyclerView.Adapter mWrappedAdapter;
    private MyDraggableItemAdapter  myItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recycler_list_view);
        recycler_view =(RecyclerView)findViewById(R.id.recycler_view);
        mLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

        // drag & drop manager
        mRecyclerViewDragDropManager = new RecyclerViewDragDropManager();
        mRecyclerViewDragDropManager.setDraggingItemShadowDrawable(
            (NinePatchDrawable) ContextCompat.getDrawable(this, R.drawable.material_shadow_z3));
        // Start dragging after long press
        mRecyclerViewDragDropManager.setInitiateOnLongPress(true);
        mRecyclerViewDragDropManager.setInitiateOnMove(false);
        mRecyclerViewDragDropManager.setLongPressTimeout(750);

        List<String>  items = new ArrayList<>();
        for(int i=1;i<100;i++){
            items.add(RandomStringUtils.random(5,"ABCDEFGHIJKLMN") +i);
        }
        myItemAdapter = new MyDraggableItemAdapter(items);
        mWrappedAdapter = mRecyclerViewDragDropManager.createWrappedAdapter(myItemAdapter);      // wrap for dragging
        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();

        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        recycler_view.setItemAnimator(animator);

        // additional decorations
        //noinspection StatementWithEmptyBody
        if (supportsViewElevation()) {
            // Lollipop or later has native drop shadow feature. ItemShadowDecorator is not required.
        } else {
            recycler_view.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) ContextCompat.getDrawable(this, R.drawable.material_shadow_z1)));
        }

        mRecyclerViewDragDropManager.attachRecyclerView(recycler_view);

    }
    private boolean supportsViewElevation() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        System.out.println("onKeyDown"+keyCode);
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            System.out.println("onKeyDown");
            System.out.println(Arrays.toString(myItemAdapter.items.toArray()));
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecyclerViewDragDropManager != null) {
            mRecyclerViewDragDropManager.release();
            mRecyclerViewDragDropManager = null;
        }

        if (recycler_view != null) {
            recycler_view.setItemAnimator(null);
            recycler_view.setAdapter(null);
            recycler_view = null;
        }

        if (mWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(mWrappedAdapter);
            mWrappedAdapter = null;
        }

        mLayoutManager = null;
    }
}
