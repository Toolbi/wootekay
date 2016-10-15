package com.tukkeendoo.app.utils.thread_manager;

import android.os.Handler;
import android.os.Message;
import android.os.Process;

/**
 * Created by fallou on 27/07/2016.
 */
public abstract class Task <Param, Result> implements Runnable {
    public static final int TASK_BEGIN = 0x0;
    public static final int PUBLISH_PROGRESS = 0x1;
    public static final int TASK_COMPLETE = 0x2;
    private Thread thread;
    private Param [] params;
    private Result result;
    private Handler handler;
    private int priority;
    protected int progress;

//    private TaskListener<Result> taskListener;
//
//    public void setTaskListener(TaskListener taskListener) {
//        this.taskListener = taskListener;
//    }

    public Task(){}

    public Task(Param... params){
        this.params = params;
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(TASK_BEGIN);

        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        thread = Thread.currentThread();
        result = doInBackground(params);
        if (result != null && handler != null && !thread.isInterrupted()){
            Message message = new Message();
            message.what = TASK_COMPLETE;
            message.obj = this;
            handler.sendMessage(message);
        }
    }

    public void publishProgress(){
        onProgressUpdate(progress);
    }

    public void updateProgress(){
        handler.sendEmptyMessage(PUBLISH_PROGRESS);
    }

    public abstract void onBegin();

    protected abstract Result doInBackground(Param... params);

    protected abstract void onProgressUpdate(int progress);

    protected abstract void onCancelled(Result result);

    protected abstract void onResult(Result result);

    public void publishResult(){
        onResult(result);
    }
    public void cancel(){
        if (thread != null && !thread.isInterrupted()){
            thread.interrupt();
            onCancelled(result);
        }
    }

    public boolean isRunning() {
        return thread.isAlive();
    }

    public void setParams(Param... params) {
        this.params = params;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
