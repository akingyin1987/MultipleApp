package com.akingyin.jobmanagers;

import com.akingyin.sharelibs.taskManager.SingleTask;


import org.apache.commons.lang.time.DateFormatUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/27.
 */

public class DownloadSingleFileTask extends SingleTask {

    int    postion;
    String  url;
    String  dir;


    public DownloadSingleFileTask(int postion,String url,String  dir) {
        this.postion = postion;
        this.url = url;
        this.dir = dir;
    }


    @Override
    public boolean todo() {
        System.out.println("postion="+postion+":"+ DateFormatUtils.format(new Date(),"HH:mm:ss SSSS")+Thread.currentThread().getId());
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            File filedir = new File(dir);
            if(!filedir.isDirectory()){
                filedir.mkdirs();
            }
            System.out.println("httpcode="+response.code());
            if(response.isSuccessful() && response.body().contentLength() >10*1024){
                fos = new FileOutputStream(dir+File.separator+ UUID.randomUUID().toString()+".jpg");
                is = response.body().byteStream();
                while ((len = is.read(buf)) != -1){
                    fos.write(buf,0,len);
                }
                fos.flush();
            }
            response.body().close();

        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        }finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != fos){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.gc();
        }


        return true;
    }
}
