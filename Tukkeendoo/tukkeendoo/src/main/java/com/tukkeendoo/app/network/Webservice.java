package com.tukkeendoo.app.network;

import android.os.AsyncTask;

import com.tukkeendoo.app.utils.thread_manager.TaskManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executor;

/**
 * Created by fallou on 16/04/2016.
 */
public class Webservice implements HTTPRequestTask.OnTaskStopListener{

    private static Webservice instance;
    private LinkedList<HTTPRequestTask> tasks;
    private LinkedList<HTTPRequest> requests;
    private Executor threadPoolExecutor;
    private Executor serialExecutor;
    private static final int MAX_THREAD_IN_PARALLEL = 10;

    private Webservice(){
        tasks = new LinkedList<>();
        requests = new LinkedList<>();
        threadPoolExecutor = AsyncTask.THREAD_POOL_EXECUTOR;
        serialExecutor = AsyncTask.SERIAL_EXECUTOR;
    }

    public static Webservice getInstance() {
        if (instance == null){
            instance = new Webservice();
        }
        return instance;
    }

    private void addTask(HTTPRequestTask task){
        this.tasks.add(task);
    }

    public static void executeRequestWithListener(HTTPRequest request, HTTPRequestListener listener){
        HTTPRequestTask task = new HTTPRequestTask(request);
        task.setListener(listener);
        task.setOnTaskStopListener(getInstance());
        getInstance().addTask(task);
        getInstance().executeTasks();
    }

    private void executeTasks(){
        Iterator<HTTPRequestTask> iterator = tasks.iterator();
        while (iterator.hasNext()){
            HTTPRequestTask task = iterator.next();
            if (!task.isRunning() && !requests.contains(task.getRequest())){

                requests.add(task.getRequest());

                TaskManager.execute(task);

//                if (tasks.size() < MAX_THREAD_IN_PARALLEL) {
//                    task.executeOnExecutor(threadPoolExecutor, task.getRequest());
//                }else {
//                    task.executeOnExecutor(serialExecutor, task.getRequest());
//                }

            }else {
                iterator.remove();
            }
        }
    }

    @Override
    public void onTaskCancelled(HTTPRequestTask task) {
        this.tasks.remove(task);
        requests.remove(task.getRequest());
    }

    @Override
    public void onTaskFinish(HTTPRequestTask task) {
        this.tasks.remove(task);
        requests.remove(task.getRequest());
    }
}
