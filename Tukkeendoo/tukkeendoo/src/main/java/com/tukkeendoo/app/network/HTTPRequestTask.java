package com.tukkeendoo.app.network;

import android.util.Log;

import com.tukkeendoo.app.models.Preferences;
import com.tukkeendoo.app.utils.thread_manager.Task;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;


/**
 * Created by fallou on 10/05/2016.
 */
public class HTTPRequestTask extends Task<HTTPRequest, HTTPResponse>{

    private static final String LOG_TAG = HTTPRequestTask.class.getSimpleName();
    public static final int CONNECT_TIME_OUT = 10 * 1000; // 10 s
    public static final int READ_TIME_OUT = 10 * 1000;
    public static final int WRITE_TIME_OUT = 10000;
    public static final boolean FOLLOW_REDIRECT = true;
    public static final boolean INSTANCE_FOLLOW_REDIRECT = true;

    private int maxRetry = 2;
//    private int progress;
    private HTTPRequest request;

    public HTTPRequestTask(){
        super();
    }

    public HTTPRequestTask(HTTPRequest request) {
        this.request = request;
    }

    private HTTPRequestListener listener;

    public void setListener(HTTPRequestListener listener) {
        this.listener = listener;
    }

    public interface OnTaskStopListener {
        void onTaskCancelled(HTTPRequestTask task);
        void onTaskFinish(HTTPRequestTask task);
    }

    private OnTaskStopListener onTaskStopListener;

    public void setOnTaskStopListener(OnTaskStopListener onTaskStopListener) {
        this.onTaskStopListener = onTaskStopListener;
    }

    public HTTPRequest getRequest() {
        return request;
    }


    @Override
    public void onBegin() {
        progress = 0;
        if (listener != null){
            listener.onHTTPBegin();
        }
    }

    @Override
    protected void onProgressUpdate(int progress) {
        if (listener != null){
            listener.onHTTPProgress(progress);
        }
        Log.v(LOG_TAG, "progress = " + progress);
    }

    private void setRequestsTimeOuts(HTTPRequest... requests){
        for (HTTPRequest request : requests){
            request.setConnectTimeOut(CONNECT_TIME_OUT);
            request.setReadTimeOut(READ_TIME_OUT);
//            request.setInstanceFollowRedirects(INSTANCE_FOLLOW_REDIRECT);
//            request.setFollowRedirects(FOLLOW_REDIRECT);
        }
    }

    @Override
    protected HTTPResponse doInBackground(HTTPRequest... params) {
        maxRetry--;
        publishProgress();
        setRequestsTimeOuts(params);
        HTTPRequest request = params[0];
        try {
            request.send();
            return request.getResponse();
        } catch (IOException e) {
            Log.w(LOG_TAG, "", e);
        } catch (JSONException e) {
            Log.w(LOG_TAG, "", e);
        }finally {
            if (request.getResponse() == null && maxRetry > 0){
                reTry(params);
            }else if (request.getResponse() != null && maxRetry > 0){
                if (!request.getResponse().isOk())
                    reTry(params);
            }
        }

        return HTTPResponse.defaultResponse();
    }

    private void reTry(HTTPRequest... params){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Log.w(LOG_TAG, "", e);
        }
        doInBackground(params);
    }

    @Override
    protected void onResult(HTTPResponse response) {
        if (response.getHeader().size() > 0 && response.getHeader().containsKey(HTTPResponse.Header.COOKIE)) {
            Preferences.saveCookies((List<String>) response.getHeader().get(HTTPResponse.Header.COOKIE));
        }
        if (listener != null){
            listener.onHTTPResponse(response);
        }
        if (onTaskStopListener != null) {
            onTaskStopListener.onTaskFinish(this);
        }
    }

    @Override
    protected void onCancelled(HTTPResponse response) {

//        if (listener != null){
//            listener.onHTTPResponse(response);
//        }
        if (onTaskStopListener != null) {
            onTaskStopListener.onTaskCancelled(this);
        }
    }
}
