package com.akingyin.sharelibs.taskManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多任务管理器 队列one by one
 * Created by Administrator on 2016/7/3.
 */
public class QueueTaskManager {

    /** 线程池 */
    private ExecutorService threadPool;
    private LinkedBlockingQueue<AbsTaskRunner> queueTasks = new LinkedBlockingQueue<>();
    private final AtomicInteger count = new AtomicInteger();//任务总数

    private final AtomicInteger successTotal = new AtomicInteger();//成功数
    private final AtomicInteger overTotal = new AtomicInteger();//已执行数
    private final AtomicInteger errorTotal = new AtomicInteger();  //错误数




}
