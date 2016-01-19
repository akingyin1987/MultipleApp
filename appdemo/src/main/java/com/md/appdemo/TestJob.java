package com.md.appdemo;

import com.md.appdemo.entity.UserEntity;
import com.md.appdemo.jobmanager.AsyncJob;
import com.md.appdemo.jobmanager.JobstatusEnum;

/**
 * Created by Administrator on 2016/1/19.
 */
public class TestJob  extends AsyncJob<UserEntity> {


    public TestJob(UserEntity userEntity, OnJobListion jobListion) {
        super(userEntity, jobListion);
    }

    public TestJob(UserEntity userEntity) {
        super(userEntity);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void doOnBackground() {
        try{
            System.out.println(getT().toString());
            Thread.sleep(getT().getAge());
            if(getT().getAge()%2==0){
                setJobstatusEnum(JobstatusEnum.Success);
            }else{
                setJobstatusEnum(JobstatusEnum.Error);
            }
            if(null != getJobListion()){
                getJobListion().onResult(getJobstatusEnum());
            }
        }catch (Exception e){

        }

    }
}