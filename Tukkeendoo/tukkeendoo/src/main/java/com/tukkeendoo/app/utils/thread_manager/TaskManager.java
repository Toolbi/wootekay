package com.tukkeendoo.app.utils.thread_manager;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by fallou on 27/07/2016.
 */
public class TaskManager extends HandlerThread {

    public static final int TASK_PRIORITY_HIGH = 0x2;
    public static final int TASK_PRIORITY_MEDIUM = 0x1;
    public static final int TASK_PRIORITY_LOW = 0x0;

    private static final String LOG_TAG = TaskManager.class.getSimpleName();
    private static final boolean DEBUG = true;

    private Handler handler;
    private Handler updateHandler;
    private static TaskManager manager;
    private static int NUMBER_OF_CORES;
    private static int MAXIMUM_POOL_SIZE;
    private BlockingQueue<Runnable> runnableQueue;
    private static int KEEP_ALIVE_TIME;
    private static TimeUnit KEEP_ALIVE_TIME_UNIT;
    private ThreadPoolExecutor threadPoolExecutor;
    private LinkedList<Task> futureTasks;

    static {
        if (manager == null) {
            manager = new TaskManager(TaskManager.class.getSimpleName()+"Thread", Process.THREAD_PRIORITY_BACKGROUND);
            if (DEBUG)Log.v(LOG_TAG, "Initialising manager on thread " + Thread.currentThread().getName());
        }
        if (!manager.isAlive()){
            manager.start();
            if (DEBUG)Log.v(LOG_TAG,"Starting manager");
        }
    }

    private TaskManager(String name, int priority){
        super(name, priority);
        NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors() + 1;
        MAXIMUM_POOL_SIZE = NUMBER_OF_CORES * 2 + 1;
        KEEP_ALIVE_TIME = 1;
        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        runnableQueue = new LinkedBlockingDeque<>();
        futureTasks = new LinkedList<>();
        threadPoolExecutor = new ThreadPoolExecutor(NUMBER_OF_CORES,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                runnableQueue);
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                Task task = (Task) msg.obj;
                if (task != null) {
                    switch (msg.what) {
                        case Task.TASK_BEGIN:
                            task.onBegin();
                            break;
                        case Task.PUBLISH_PROGRESS:
                            task.publishProgress();
                            break;
                        case Task.TASK_COMPLETE: {
                            updateHandler.sendEmptyMessage(Task.TASK_COMPLETE);
                            task.publishResult();
                            break;
                        }
                        default:
                            super.handleMessage(msg);
                            break;
                    }
                }else {
                    super.handleMessage(msg);
                }
            }
        };
    }

    @Override
    protected void onLooperPrepared() {
        updateHandler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg) {
                if (DEBUG)Log.v(LOG_TAG,"Thread = " + Thread.currentThread().getName());
                if (msg.what == Task.TASK_COMPLETE) {
                    refreshExecutor();
                }else {
                    super.handleMessage(msg);
                }
            }
        };
    }

    public static void execute(Task task){
        synchronized (manager) {
            if (manager != null) {
                manager.addTask(task);
            }
        }
    }

    public static void cancel(Task task){
        if (manager != null){
            manager.stopTask(task);
        }
    }

    private void addTask(Task task){
        switch (task.getPriority()){
            case TASK_PRIORITY_HIGH :
                futureTasks.addFirst(task);
                break;
            case TASK_PRIORITY_MEDIUM :
                futureTasks.add(futureTasks.size()/2, task);
                break;
            case TASK_PRIORITY_LOW :
                futureTasks.addLast(task);
                break;
            default:
                futureTasks.add(task);
                break;
        }
        refreshExecutor();
    }

    private void refreshExecutor(){
        if (runnableQueue.size() < MAXIMUM_POOL_SIZE && !futureTasks.isEmpty()) {
            Task task = futureTasks.pollFirst();
            if (task != null) {
                task.setHandler(handler);
                threadPoolExecutor.execute(task);
            }
        }
        if (DEBUG) {
            Log.v(LOG_TAG, "futureTasks = " + futureTasks.size());
            Log.v(LOG_TAG, "runnableQueues = " + runnableQueue.size());
        }
    }

    public void stopTask(Task task){
        synchronized (this){
            Task [] runningTasks = new Task[runnableQueue.size()];
            runnableQueue.toArray(runningTasks);
            for (Task aTask : runningTasks){
                if (task.equals(aTask)){
                    task.cancel();
                    break;
                }
            }
        }
    }
}
