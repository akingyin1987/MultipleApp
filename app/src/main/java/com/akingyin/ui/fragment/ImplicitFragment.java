package com.akingyin.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.advancedrecyclerview.RecyclerviewDemoActivity;
import com.akingyin.jobmanagers.AsyncJobManager;
import com.akingyin.jobmanagers.TestJobManager;
import com.akingyin.jobmanagers.Testjob;
import com.akingyin.receiver.ReceiverConstants;
import com.akingyin.receiver.ReceiverUtil;
import com.akingyin.sharelibs.jlog.util.FileUtils;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.md.multipleapp.AppInstallReceiver;
import com.md.multipleapp.AutoInstall;
import com.md.multipleapp.R;
import com.md.multipleapp.UserEntity;

import java.util.UUID;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/1/27.
 */
public class ImplicitFragment extends Fragment{
    public AppInstallReceiver appInstallReceiver;

    public EditText editText;

    private static String MY_ACTION = "com.view.my_action";
    //private static String MY_ACTION = "com.zlcdgroup.camera";
    public TextView tv_data;

    public FloatingActionsMenu menuMultipleActions;

    public   static   ImplicitFragment   newInstance(){
        ImplicitFragment   fragment = new ImplicitFragment();
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_implicit,null);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = (EditText)view.findViewById(R.id.send_message);
        tv_data = (TextView)view.findViewById(R.id.tv_dbdata);
        menuMultipleActions = (FloatingActionsMenu)view.findViewById(R.id.multiple_actions_left);

        menuMultipleActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuMultipleActions.toggle();
            }
        });

        view.findViewById(R.id.app_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String directory = Environment.getExternalStorageDirectory().getAbsolutePath();

                AutoInstall.copyApkFromAssets(getContext(), "appdemo-debug.apk", directory + File.separator + "test.apk");
                AutoInstall.installApk(getContext(), directory + File.separator + "test.apk");
            }
        });

        view.findViewById(R.id.app_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoInstall.uninstallApk(getContext(), "com.md.appdemo");
            }
        });

        view.findViewById(R.id.app_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString().trim();

                if (AutoInstall.isAppInstalled(getContext(), "com.md.appdemo")) {

                    ReceiverUtil.sendAppReceiver(getContext(), ReceiverConstants.APP_METER_RECEIVER);
                    Intent intent = new Intent();
                    intent.setAction(MY_ACTION);
                    intent.setType("test/*");
                    intent.putExtra("data", message);
                    if(null == intent.resolveActivity(getActivity().getPackageManager())){
                        Toast.makeText(getContext(), "无法启动", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivityForResult(intent, 1);
                    return;
                }
                Toast.makeText(getContext(), "apk未安装", Toast.LENGTH_SHORT).show();

            }
        });

        view.findViewById(R.id.app_inspectdata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity = new UserEntity();

                userEntity.userName="test"+(RandomStringUtils.random(100, "utf-8"));
                userEntity.age = RandomUtils.nextInt();
                userEntity.save();
                tv_data.setText(userEntity.toString());
            }
        });
       view.findViewById(R.id.app_recyclerview).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getContext(), RecyclerviewDemoActivity.class);
               startActivity(intent);
           }
       });

        view.findViewById(R.id.app_recyclerview_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // com.akingyin.ui.RecyclerviewActivity_.intent(getContext()).start();
                com.akingyin.ui.ImageListActivity_.intent(getContext()).start();
            }
        });
        view.findViewById(R.id.app_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.zlcdgroup.camera");
                intent.setType("camera/*");

                intent.putExtra("save_name", UUID.randomUUID().toString().replace("-","")+".jpg");
                File   dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"test");
                dir.mkdirs();
                intent.putExtra("save_dir",dir.getAbsolutePath());
                startActivityForResult(intent,2);
            }
        });

        view.findViewById(R.id.app_camera2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.zlcdgroup.camera2");
                intent.setType("camera/*");

                intent.putExtra("save_name", UUID.randomUUID().toString().replace("-","")+".jpg");
                File   dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"test");
                dir.mkdirs();
                intent.putExtra("save_dir",dir.getAbsolutePath());
                startActivityForResult(intent,3);
            }
        });

        view.findViewById(R.id.app_tuya1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    File  file = new File(RESULT_FILE);
                    if(file.exists()){
                        Intent intent = new Intent();
                        intent.setAction("com.zlcdgroup.tuya");
                        intent.setType("tuya/*");

                        intent.putExtra(KEY_PIC_NAME,FileUtils.getFileName(RESULT_FILE));
                        intent.putExtra(KEY_PIC_DIRECTORYPATH,FileUtils.getFolderName(RESULT_FILE));
                        intent.putExtra(KEY_SAVE_RENAME,UUID.randomUUID().toString().replace("-","")+".jpg");

                        startActivityForResult(intent,4);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        view.findViewById(R.id.app_server).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                System.out.println("fdfdfd");
                Intent intent = new Intent();
                intent.setAction("com.zlcdgroup.testserver");
                intent.setType("server/*");
             //   intent.setPackage(getActivity().getPackageName());
                getActivity().startService(intent);

            }
        });

        view.findViewById(R.id.app_transaction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDbTransaction();
            }
        });
    }

    private TestJobManager   testJobManager;
    public    void    testDbTransaction(){
        if(null != testJobManager){
            testJobManager.cleanAllJobs();
        }
        testJobManager = new TestJobManager();
        testJobManager.setListion(new AsyncJobManager.OnJobManagerListion() {
            @Override public void onProgress(int total, int press, int error) {
                System.out.println("total="+total+":"+press+":"+error);
            }

            @Override public void onError(String message) {
                System.out.println("error="+message);
            }

            @Override public void onComplete() {
               System.out.println("onComplete");
            }
        });
        for(int i=0;i<100;i++){
            testJobManager.onAddTask(new Testjob(i));
        }
        testJobManager.execute();
    }

    // 图片名
    public static final String KEY_PIC_NAME = "picName";

    public static final String KEY_PIC_ORIGINAL = "Original_name";// 原始图片路径

    // 路径
    public static final String KEY_PIC_DIRECTORYPATH = "directoryPath";

    public static final String KEY_SAVE_RENAME = "saveReName";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestcode" + requestCode + ":" + resultCode);
        if(null != data && requestCode == 1){
            editText.setText(data.getStringExtra("data"));

        }
        if(null != data){
            if(requestCode == 2 || requestCode == 3){
                 RESULT_FILE = data.getStringExtra("result_file");
            }else if(requestCode == 4){
                String  dir = data.getStringExtra(KEY_PIC_DIRECTORYPATH);
                String  name = data.getStringExtra(KEY_SAVE_RENAME);
                RESULT_FILE = dir+File.separator+name;
                try{
                    File  file = new File(RESULT_FILE);
                    Toast.makeText(getContext(),"图片是否存在："+file.exists(),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println(data.toString());
        }
    }

    public   static    String    RESULT_FILE="";

    @Override public void onDestroyView() {
        super.onDestroyView();
        if(null != testJobManager){
            testJobManager.onDestory();
        }
    }
}
