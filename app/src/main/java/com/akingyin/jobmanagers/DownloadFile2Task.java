package com.akingyin.jobmanagers;

import com.akingyin.sharelibs.taskManager.AbsTaskRunner;
import com.akingyin.sharelibs.taskManager.enums.TaskStatusEnum;

import org.apache.commons.lang.time.DateFormatUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import okhttp3.Call;


/**
 * Created by Administrator on 2016/10/27.
 */

public class DownloadFile2Task extends AbsTaskRunner{


    int    postion;
    String  url;
    String  dir;
    private HttpURLConnection urlcon;
    public DownloadFile2Task(int postion,String url,String  dir) {
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

        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        Call call = null;
        try {
           URL request = new URL(url);
            urlcon = (HttpURLConnection) request.openConnection();
            is = urlcon.getInputStream();
            if(urlcon.getResponseCode() == 200){

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
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != urlcon){
                urlcon.disconnect();
            }



        }


        return TaskStatusEnum.SUCCESS;
    }
}
