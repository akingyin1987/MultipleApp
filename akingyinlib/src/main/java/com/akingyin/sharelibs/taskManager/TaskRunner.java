package com.akingyin.sharelibs.taskManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多任务执行类
 * 
 * @author peiandsky
 * 
 */
public class TaskRunner {
	/** 线程池 */
	private ExecutorService threadPool;
	private final AtomicInteger count = new AtomicInteger();
	private final AtomicInteger todo = new AtomicInteger();
	private final AtomicInteger success = new AtomicInteger();
	/**
	 * 并行任务数量
	 */
	private int paralelTaskCount = 8;

	public int getParalelTaskCount() {
		return paralelTaskCount;
	}

	public void setParalelTaskCount(int paralelTaskCount) {
		this.paralelTaskCount = paralelTaskCount;
		if (this.paralelTaskCount < 1) {
			this.paralelTaskCount = 1;
		}
	}

	public TaskRunner() {
		threadPool = Executors.newFixedThreadPool(paralelTaskCount);
	}
	public TaskRunner(int ptc) {
		threadPool = Executors.newFixedThreadPool(ptc);
	}

	public void addTask(List<SingleTask> tasks) {
		for (SingleTask singleTask : tasks) {
			addTask(singleTask);
		}
	}

	public void addTask(SingleTask task) {
		task.setTaskRunner(this);
		threadPool.execute(task);
		count.getAndIncrement();
		todo.getAndIncrement();
	}

	public int getTaskCount() {
		return count.get();
	}

	public int getTaskToDo() {
		return todo.get();
	}

	public int getTaskSuccess() {
		return success.get();
	}

	public int getTaskFail() {
		int done = getTaskCount() - getTaskToDo();
		return done - getTaskSuccess();
	}

	public void oneTaskOver(boolean rs) {
		todo.getAndDecrement();
		if (rs) {
			success.getAndIncrement();
		}
	}

	public boolean isOver() {
		if (threadPool == null) {
			return true;
		}

		if (todo.get() <= 0) {
			return true;
		}

		return false;
	}

	/**
	 * 取消正在下载的任务
	 */
	public synchronized void cancelTasks() {
		if (threadPool != null) {
			// try {
			// threadPool.shutdown();
			// threadPool.awaitTermination(10, TimeUnit.SECONDS);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }

			threadPool.shutdownNow();
			threadPool = null;
		}
	}

}
