package com.tukkeendoo.app.utils.thread_manager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by fallou on 27/07/2016.
 */
public class ThreadManager {
    private Handler handler;
    private static ThreadManager instance;
    private static int NUMBER_OF_CORES;
    private BlockingQueue<Runnable> runnableQueue;
    private static int KEEP_ALIVE_TIME;
    private static TimeUnit KEEP_ALIVE_TIME_UNIT;
    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadManager(){
        NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        KEEP_ALIVE_TIME = 1;
        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        runnableQueue = new LinkedBlockingDeque<>();
        threadPoolExecutor = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                runnableQueue);
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }

}
