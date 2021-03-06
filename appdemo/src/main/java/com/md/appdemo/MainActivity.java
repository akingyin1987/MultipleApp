package com.md.appdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.github.clans.fab.FloatingActionMenu;
import com.md.appdemo.jobmanager.AsyncJobManager;
import com.md.appdemo.jobmanager.TestJobManager;
import com.md.appdemo.model.UserEntity;

import com.md.appdemo.presenter.impl.DemoPresenterImpl;

import com.md.appdemo.receiver.ReceiverConstants;
import com.md.appdemo.receiver.ReceiverUtil;
import com.md.appdemo.ui.IdemoView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/1/10.
 */

@EActivity(R.layout.activity_main) public class MainActivity extends AppCompatActivity
    implements IdemoView, RecyclerViewAdapterBase.RecyclerItemClickListener,
    AsyncJobManager.OnJobManagerListion {

  public String action;

  public EditText send_message;

  @ViewById FloatingActionMenu menu_labels_right;
  public TextView tv_message;

  public static Uri contentUri =
      Uri.parse("content://org.jraf.androidcontentprovidergenerator.sample.provider/tb_user");

  @Extra public String data;

  @ViewById public RecyclerView app_list;

  @Bean public AAUserAdapter adapter;

  @Bean DemoPresenterImpl demoPresenter;

  TestJobManager jobManager;
  private int mScrollOffset = 4;

  @AfterViews public void initView() {
    jobManager = new TestJobManager(3, this);
    int age = 2000;
    Random random = new Random();
    for (int i = 0; i < 500; i++) {

      com.md.appdemo.entity.UserEntity userEntity =
          new com.md.appdemo.entity.UserEntity("test" + i, random.nextInt(age));
      jobManager.onAddTask(new TestJob(userEntity));
    }
    demoPresenter.setIview(this);
    send_message = (EditText) findViewById(R.id.send_message);
    tv_message = (TextView) findViewById(R.id.tv_message);

    Button button = (Button) findViewById(R.id.app_btn3);
    button.setText(data);

    findViewById(R.id.app_btn1).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        jobManager.onDestory();
        Intent intent1 = new Intent();
        String message = send_message.getText().toString().trim();
        intent1.putExtra("data", message);
        setResult(RESULT_OK, intent1);
        ReceiverUtil.sendAppReceiver(MainActivity.this, ReceiverConstants.APP_RECEIVER);
        finish();
      }
    });

    findViewById(R.id.app_btn2).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        jobManager.execute();
        UserEntity userEntity = new UserEntity();
        userEntity.userName = "aaaa";
        userEntity.age = 222;
        demoPresenter.addUser(userEntity);
      }
    });

    findViewById(R.id.app_btn3).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        tv_message.setText("开始查询主APP数据===>>");

        Intent intent = new Intent(MainActivity.this, TabDemoActivity.class);
        startActivity(intent);
      }
    });
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
    app_list.setLayoutManager(linearLayoutManager);
    app_list.setItemAnimator(new DefaultItemAnimator());
    adapter.setItemClickListener(this);

    app_list.setAdapter(adapter);

    app_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (Math.abs(dy) > mScrollOffset) {
          if (dy > 0) {
            menu_labels_right.hideMenu(true);
          } else {
            menu_labels_right.showMenu(true);
          }
        }
      }

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }
    });

    demoPresenter.findAllUser();
  }

  @Click public void menu_labels_right() {
    if (menu_labels_right.isOpened()) {
      menu_labels_right.toggle(true);
    }
  }

  @Override public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override public void showMessage(String message, View v) {

  }

  @Override public void addUser(UserEntity userEntity) {
    adapter.addTopItem(userEntity);
    adapter.notifyDataSetChanged();
  }

  @Override public void modifyUser(UserEntity userEntity) {
    adapter.notifyDataSetChanged();
  }

  @Override public void delectUser(UserEntity userEntity) {
    adapter.removeItem(userEntity);
    adapter.notifyDataSetChanged();
  }

  @Override public void findAllUser(List<UserEntity> userEntities) {
    adapter.cleanItems();
    adapter.addAll(userEntities);
    adapter.notifyDataSetChanged();
  }

  @Override public void onItemClick(int postion, View view) {
    final UserEntity userEntity = adapter.getItem(postion);
    new MaterialDialog.Builder(this).input("名称", userEntity.userName, true,
        new MaterialDialog.InputCallback() {
          @Override public void onInput(MaterialDialog dialog, CharSequence input) {
            userEntity.userName = input.toString();
            demoPresenter.modifyUser(userEntity);
          }
        }).theme(Theme.LIGHT).title("修改").show();
  }

  @Override public void onItemLongClick(int postion, View view) {
    final UserEntity userEntity = adapter.getItem(postion);
    new MaterialDialog.Builder(this).theme(Theme.LIGHT)
        .title("删除")
        .content("确定要删除数据吗？")
        .positiveText("确定")
        .negativeText("取消")
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override public void onClick(MaterialDialog dialog, DialogAction which) {
            dialog.dismiss();
            demoPresenter.delectUser(userEntity);
          }
        })
        .show();
  }

  @Override public void onProgress(int total, int press, int error) {

  }

  @Override public void onError(String message) {

  }

  @Override public void onComplete() {

  }
}
