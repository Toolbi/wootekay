package com.tukkeendoo.app.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by fallou on 16/04/2016.
 */
public class Webservice extends AsyncTask<HTTPRequest, Integer, HTTPResponse>{

    private static final String LOG_TAG = Webservice.class.getSimpleName();
    public static final int CONNECT_TIME_OUT = 10000;
    public static final int READ_TIME_OUT = 10000;
    public static final int WRITE_TIME_OUT = 10000;

    public interface WebServiceListener{
        public void onBegin();
        public void onProgress(Integer... values);
        public void onResponse(HTTPResponse response);
    }

    private WebServiceListener listener;

    public void setListener(WebServiceListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null){
            listener.onBegin();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (listener != null){
            listener.onProgress(values);
        }
    }

    private void setRequestsTimeOuts(HTTPRequest... requests){
       for (HTTPRequest request : requests){
           request.setConnectTimeOut(CONNECT_TIME_OUT);
           request.setReadTimeOut(READ_TIME_OUT);
       }
    }

    @Override
    protected HTTPResponse doInBackground(HTTPRequest... params) {
        try {
            setRequestsTimeOuts(params);
            HTTPRequest request = params[0];
            request.send();
            return request.getResponse();
        } catch (IOException e) {
            Log.e(LOG_TAG, "", e);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(HTTPResponse response) {
        super.onPostExecute(response);
        if (listener != null){
            listener.onResponse(response);
        }
    }

    @Override
    protected void onCancelled(HTTPResponse response) {
        super.onCancelled(response);
        if (listener != null){
            listener.onResponse(response);
        }
    }

    public static void executeRequestWithListener(HTTPRequest request, WebServiceListener listener){
        Webservice webservice = new Webservice();
        webservice.setListener(listener);
        webservice.executeOnExecutor(THREAD_POOL_EXECUTOR,request);
    }
}
