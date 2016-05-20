package com.tukkeendoo.app.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;


/**
 * Created by fallou on 10/05/2016.
 */
public class HTTPRequestTask extends AsyncTask<HTTPRequest, Integer, HTTPResponse> {

    private static final String LOG_TAG = Webservice.class.getSimpleName();
    public static final int CONNECT_TIME_OUT = 10000;
    public static final int READ_TIME_OUT = 10000;
    public static final int WRITE_TIME_OUT = 10000;
    private int maxRetry = 2;
    private HTTPRequest request;

    public HTTPRequestTask(HTTPRequest request) {
        this.request = request;
    }

    public interface HTTPRequestListener {
        public void onHTTPBegin();
        public void onHTTPProgress(Integer... values);
        public void onHTTPResponse(HTTPResponse response);
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

    public boolean isRunning() {
        Status status = getStatus();
        return status == Status.RUNNING || status == Status.FINISHED;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null){
            listener.onHTTPBegin();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (listener != null){
            listener.onHTTPProgress(values);
        }
        Log.v(LOG_TAG, "progress = " + values);
    }

    private void setRequestsTimeOuts(HTTPRequest... requests){
        for (HTTPRequest request : requests){
            request.setConnectTimeOut(CONNECT_TIME_OUT);
            request.setReadTimeOut(READ_TIME_OUT);
        }
    }

    @Override
    protected HTTPResponse doInBackground(HTTPRequest... params) {
        maxRetry--;
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
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    Log.w(LOG_TAG, "", e);
                }
                doInBackground(params);
            }
        }

        return HTTPResponse.defaultResponse();
    }

    @Override
    protected void onPostExecute(HTTPResponse response) {
        super.onPostExecute(response);
        if (listener != null){
            listener.onHTTPResponse(response);
        }
        if (onTaskStopListener != null) {
            onTaskStopListener.onTaskFinish(this);
        }
    }

    @Override
    protected void onCancelled(HTTPResponse response) {
        super.onCancelled(response);

        if (listener != null){
            listener.onHTTPResponse(response);
        }
        if (onTaskStopListener != null) {
            onTaskStopListener.onTaskCancelled(this);
        }
    }
}
