package com.akingyin.jobmanagers;

import com.akingyin.sharelibs.taskManager.AbsTaskRunner;
import com.akingyin.sharelibs.taskManager.enums.TaskStatusEnum;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/27.
 */

public class DownloadFileTask  extends AbsTaskRunner {

    int    postion;
    String  url;
    String  dir;

    public DownloadFileTask(int postion,String url,String  dir) {
        this.postion = postion;
        this.url = url;
        this.dir = dir;
    }

    @Override
    public TaskStatusEnum onBefore() {
        return TaskStatusEnum.SUCCESS;
    }

    @Override
    public TaskStatusEnum onToDo() {
        System.out.println("postion="+postion+":"+ DateFormatUtils.format(new Date(),"HH:mm:ss SSSS")+Thread.currentThread().getId());
        OkHttpClient   okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        Call  call = null;
        Response  response = null;
        try {
            call = okHttpClient.newCall(request);
              response = call.execute();
            File   filedir = new File(dir);
            if(!filedir.isDirectory()){
                filedir.mkdirs();
            }
            is = response.body().byteStream();
            System.out.println("httpcode="+response.code());
            if(response.isSuccessful() && response.body().contentLength() >10*1024){
                fos = new FileOutputStream(dir+File.separator+ UUID.randomUUID().toString()+".jpg");

                while ((len = is.read(buf)) != -1){
                   fos.write(buf,0,len);
                }
                fos.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return  TaskStatusEnum.ERROR;
        }finally {

            if(null != fos){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if(null != call){
                call.cancel();

            }
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }


        return TaskStatusEnum.SUCCESS;
    }
}
