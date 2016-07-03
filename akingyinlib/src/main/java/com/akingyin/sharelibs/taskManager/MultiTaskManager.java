package com.akingyin.sharelibs.taskManager;

import com.akingyin.sharelibs.taskManager.enums.TaskManagerStatusEnum;
import com.akingyin.sharelibs.taskManager.enums.TaskStatusEnum;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多任务管理器
 * Created by Administrator on 2016/7/3.
 */
public class MultiTaskManager implements  ITaskResultCallBack{

    /** 线程池 */
    private ExecutorService threadPool;
    private LinkedBlockingQueue<AbsTaskRunner> queueTasks = new LinkedBlockingQueue<>();
    private  AtomicInteger count = new AtomicInteger();//任务总数

    private  AtomicInteger successTotal = new AtomicInteger();//成功数
    private  AtomicInteger overTotal = new AtomicInteger();//已执行数
    private  AtomicInteger errorTotal = new AtomicInteger();  //错误数

    private  AtomicInteger  status = new AtomicInteger(0);//状态

    //获取当前任务状态
    public TaskManagerStatusEnum   getTaskManagerStatus(){
        return  TaskManagerStatusEnum.getTaskManagerStatus(status.get());
    }

    public MultiTaskManager() {
        threadPool = Executors.newFixedThreadPool(3);
    }

    private   ApiTaskCallBack  callBack;


    public ApiTaskCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(ApiTaskCallBack callBack) {
        this.callBack = callBack;
    }

    public MultiTaskManager(int nThreads) {

        threadPool = Executors.newFixedThreadPool(nThreads);
    }

    public void addTask(List<AbsTaskRunner> tasks) {
        for (AbsTaskRunner taskRunner : tasks) {
            addTask(taskRunner);
        }
    }

    public void addTask(AbsTaskRunner task) {
       task.setCallBack(this);
        try {
            queueTasks.put(task);
            count.getAndIncrement();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    public void startTask() {
        for(AbsTaskRunner  taskRunner : queueTasks){
            threadPool.execute(taskRunner);
        }
    }

    public boolean isOver() {
        if (threadPool == null) {
            return true;
        }
        return queueTasks.size() <= 0;
    }

    /**
     * 取消正在下载的任务
     */
    public synchronized void cancelTasks() {
        status.getAndSet(3);
        if (threadPool != null) {
            for(AbsTaskRunner  taskRunner : queueTasks){
                taskRunner.onCancel();
            }
            queueTasks.clear();
            try {
                threadPool.shutdownNow();
            } catch (Exception e) {
            }
            threadPool = null;
        }
    }
    @Override
    public void onCallBack(TaskStatusEnum statusEnum, String error) {
        if(status.get() == 4 || status.get() == 3){
            return;
        }
        switch (statusEnum){
            case NETERROR:
                cancelTasks();
                break;
            case SUCCESS:
                 successTotal.getAndIncrement();
                break;
            case  ERROR:
                 errorTotal.getAndIncrement();
                break;
            default:
                errorTotal.getAndIncrement();
                break;

        }
        if(null != callBack && statusEnum != TaskStatusEnum.NETERROR){
            int  errorNum = errorTotal.get();
            int  sucNum = successTotal.get();
            callBack.onCallBack(count.get(),errorNum+sucNum,errorNum);
            if(count.get() == errorNum +sucNum){
                  if(errorNum>0){
                      status.getAndSet(4);
                      callBack.onError(error,null);
                  }else{
                      callBack.onComplete();
                  }
            }
        }

    }
}
