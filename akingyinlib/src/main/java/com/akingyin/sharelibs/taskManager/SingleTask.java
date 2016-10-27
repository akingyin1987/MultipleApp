package com.akingyin.sharelibs.taskManager;

/**
 * 单个任务类
 * 
 * @author peiandsky
 * 
 */
public abstract class SingleTask implements Runnable {
	/**
	 * 重试次数
	 */
	private int retryTime = 3;

	TaskRunner runner;

	public void setTaskRunner(TaskRunner runner) {
		this.runner = runner;
	}

	public int getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(int retryTime) {
		this.retryTime = retryTime;
		if (this.retryTime < 0) {
			this.retryTime = 1;
		}
	}

	public abstract boolean todo();

	private int retryCount = 1;

	@Override
	public void run() {
		todoAndTry();
	}

	private void todoAndTry() {
		boolean rs = todo();
		if ((!rs) && retryCount < retryTime) {
			retryCount++;
			todoAndTry();
			return;
		}
		runner.oneTaskOver(rs);
	}

}
