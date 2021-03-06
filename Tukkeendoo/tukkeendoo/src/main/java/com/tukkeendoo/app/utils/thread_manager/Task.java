package com.tukkeendoo.app.utils.thread_manager;

import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;

/**
 * Created by fallou on 27/07/2016.
 */
public abstract class Task <Param, Result> implements Runnable {
    private static final boolean DEBUG = true;
    public static final int TASK_BEGIN = 0x0;
    public static final int PUBLISH_PROGRESS = 0x1;
    public static final int TASK_COMPLETE = 0x2;
//    private static final String LOG_TAG = Task.class.getSimpleName();
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
        if (DEBUG) Log.v(getClass().getSimpleName(), "Starting task on thread " + thread.getName());
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

    public void onBegin(){};

    protected abstract Result doInBackground(Param... params);

    protected  void onProgressUpdate(int progress){};

    protected  void onCancelled(Result result){};

    protected  void onResult(Result result){};

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
        return thread != null && thread.isAlive();
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
