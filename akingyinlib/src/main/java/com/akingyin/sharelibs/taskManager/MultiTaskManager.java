package com.akingyin.sharelibs.taskManager;

import com.akingyin.sharelibs.jlog.JLog;
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
    private  AtomicInteger count = new AtomicInteger(0);//任务总数

    private  AtomicInteger successTotal = new AtomicInteger(0);//成功数
    private  AtomicInteger overTotal = new AtomicInteger(0);//已执行数
    private  AtomicInteger errorTotal = new AtomicInteger(0);  //错误数

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

    public void addTasks(List<AbsTaskRunner> tasks) {
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



    public void executeTask() {
        for(AbsTaskRunner  taskRunner : queueTasks){
            threadPool.execute(taskRunner);
        }
    }

    public   int    getTotal(){
        return  count.get();
    }

    public  int    getSuccessTotal(){
        return  successTotal.get();
    }

    public  int   getErrorTotal(){
        return  errorTotal.get();
    }

    public   int   getOverTotal(){
        return  overTotal.get();
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
        JLog.d("cancelTasks---");
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
        JLog.d("onCallBack="+statusEnum.getName()+":"+error);
        if(status.get() == 4 || status.get() == 3 || status.get() ==5){
            return;
        }
        switch (statusEnum){
            case NETERROR:
                JLog.d("new-error");
                cancelTasks();
                break;
            case SUCCESS:
                 overTotal.getAndIncrement();
                 successTotal.getAndIncrement();
                break;
            case  ERROR:
                 overTotal.getAndIncrement();
                 errorTotal.getAndIncrement();
                break;
            default:
                overTotal.getAndIncrement();
                errorTotal.getAndIncrement();
                break;

        }
        if(null != callBack ){
            if(statusEnum != TaskStatusEnum.NETERROR){
                int  errorNum = errorTotal.get();
                int  sucNum = successTotal.get();
                callBack.onCallBack(count.get(),errorNum+sucNum,errorNum);
                if(count.get() == errorNum +sucNum){
                    status.getAndSet(4);
                    if(errorNum>0){
                        callBack.onError(error,TaskManagerStatusEnum.COMPLETE);
                    }else{
                        callBack.onComplete();
                    }
                }
            }else{
                JLog.d("网络错误");
                callBack.onError(error,TaskManagerStatusEnum.NETError);
            }
        }
    }
}
