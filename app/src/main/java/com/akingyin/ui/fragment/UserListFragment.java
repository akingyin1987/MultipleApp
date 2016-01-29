package com.akingyin.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.akingyin.presenter.IUserListPresenter;
import com.akingyin.presenter.impl.UserListPresenter;
import com.akingyin.ui.adapter.UserAdapter;
import com.akingyin.view.IUserListView;
import com.md.multipleapp.R;
import com.md.multipleapp.UserEntity;

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
                EditUserInfo(null);
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(View v, String message) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void addUserSucess(UserEntity userEntity) {
        adapter.add(userEntity);
    }

    public  void  EditUserInfo(final UserEntity  userEntity){
            View   view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edituser,null);

        if(null != userEntity){
            EditText user_name = (EditText) view.findViewById(R.id.user_name);
            EditText user_age = (EditText) view.findViewById(R.id.user_age);
            user_name.setText(userEntity.userName);
            user_age.setText(String.valueOf(userEntity.age));
        }
            new MaterialDialog.Builder(getContext()).title("用户编辑").positiveText("确定")
                .negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(MaterialDialog dialog, DialogAction which) {
                    if (null != dialog && null != dialog.getCustomView()) {
                        EditText user_name = (EditText) dialog.getCustomView().findViewById(R.id.user_name);
                        EditText user_age = (EditText) dialog.getCustomView().findViewById(R.id.user_age);
                        if (null == userEntity) {
                            System.out.println("name==" + user_name.getText().toString().trim());
                            presenter.addUser(user_name.getText().toString().trim(), user_age.getText().toString().trim());
                        }
                    }


                }
            }).onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(MaterialDialog dialog, DialogAction which) {

                }
            }).customView(R.layout.dialog_edituser, true).show();

    }
}
