package com.akingyin.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akingyin.presenter.IUserListPresenter;
import com.akingyin.presenter.impl.UserListPresenter;
import com.akingyin.ui.adapter.UserAdapter;
import com.akingyin.view.IUserListView;
import com.md.multipleapp.R;

import org.byteam.superadapter.recycler.OnItemClickListener;

/**
 * Created by Administrator on 2016/1/28.
 */
public class UserListFragment  extends Fragment  implements IUserListView {

    public IUserListPresenter presenter;


    RecyclerView rvToDoList;
    FloatingActionButton add_user;
    UserAdapter adapter;

    public   static   UserListFragment   newInstance(){
        UserListFragment   fragment = new UserListFragment();
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View  view = inflater.inflate(R.layout.fragment_userlist,null);
        return  view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new UserListPresenter(this);
        presenter.initialize(savedInstanceState,view);
    }

    @Override
    public void initialize(Bundle savedInstanceState,View view) {
        add_user = (FloatingActionButton)view.findViewById(R.id.add_user);
        rvToDoList =(RecyclerView)view.findViewById(R.id.rvToDoList);
        rvToDoList.setLayoutManager(new LinearLayoutManager(getContext()));
       // rvToDoList.setItemAnimator(new DefaultItemAnimator());
        adapter = new UserAdapter(getContext(),presenter.findUsers(),R.layout.item_user);
        System.out.println("size="+adapter.getList().size());
        rvToDoList.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {

            }
        });
        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(View v, String message) {

    }
}
