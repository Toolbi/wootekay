package com.tukkeendoo.app.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by fallou on 16/04/2016.
 */
public class Webservice extends AsyncTask<HTTPRequest, Integer, HTTPResponse>{

    private static String LOG_TAG = WebServiceListener.class.getSimpleName();

    public interface WebServiceListener{
        public void onBegin();
        public void onProgress(Integer... values);
        public void onResponse(HTTPResponse result);
    }

    private WebServiceListener listener;

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

    @Override
    protected HTTPResponse doInBackground(HTTPRequest... params) {
        try {
            return params[0].getResponse();
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

}
