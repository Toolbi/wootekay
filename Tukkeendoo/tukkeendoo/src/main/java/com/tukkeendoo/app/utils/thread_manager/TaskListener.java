package com.tukkeendoo.app.utils.thread_manager;

/**
 * Created by fallou on 30/07/2016.
 */
public interface TaskListener <Result>{
    void onBegin();
    void onResult(Result result);
}
