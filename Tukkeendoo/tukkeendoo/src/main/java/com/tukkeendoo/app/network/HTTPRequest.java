package com.tukkeendoo.app.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by fallou on 16/04/2016.
 */
public class HTTPRequest {
    public static final String POST = "POST";
    public static final String GET = "GET";
    private static final String LOG_TAG = HTTPRequest.class.getSimpleName();

    private URL url;
    private HttpURLConnection connection;
    private String method;
    private int readTimeOut;
    private int connectTimeOut;
    private HashMap<String,Object> parameters;
    private HTTPResponse response;

    public HTTPRequest(URL url, String method, HashMap<String, Object> parameters) {
        this.url = url;
        this.method = method;
        this.parameters = parameters;
    }

    private void setParameters() throws MalformedURLException {
        StringBuilder urlString = new StringBuilder();
        urlString.append(url.toString());
        urlString.append('?');

        Set<String> keys = parameters.keySet();

        boolean first = true;

        for (String key : keys){
            Object param = parameters.get(key);
            if (first){
                urlString.append("?");
                first = false;
            }else {
                urlString.append("&");
            }
            urlString.append(key + "=" + param );
        }
        url = new URL(urlString.toString());
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public HashMap<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, Object> parameters) {
        this.parameters = parameters;
        try {
            setParameters();
        } catch (MalformedURLException e) {
            Log.w(LOG_TAG, "",e);
        }
    }


    private HttpURLConnection createConnection() throws IOException {

        connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(readTimeOut /* milliseconds */);
        connection.setConnectTimeout(connectTimeOut /* milliseconds */);
        connection.setRequestMethod(method);

        if ( method.equals(HTTPRequest.POST)){
            connection.setDoInput(true);
        }
        if (method == HTTPRequest.GET){
            connection.setDoOutput(true);
        }


        return connection;
    }

    private void openConection() throws IOException {
        createConnection();
        connection.connect();
    }

    public void closeConnection(){
        if (connection != null)
            connection.disconnect();
    }

    public JSONObject read() throws IOException, JSONException {
        JSONObject result;

        InputStream in = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuffer sringBuffer = new StringBuffer();
        String readedLine;
        while ((readedLine = reader.readLine()) != null)
        {
            sringBuffer.append(readedLine + "\n");
        }
        reader.close();

        result = new JSONObject(sringBuffer.toString());

        return result;
    }

    public boolean write(Object object){

        return false;
    }

    public HTTPResponse getResponse() throws IOException, JSONException {
        response = new HTTPResponse();
        response.setCode(connection.getResponseCode());
        response.setHeader(connection.getHeaderField(response.getCode()));
        response.setError(connection.getErrorStream());
        response.setMessage(connection.getResponseMessage());
        if (response.getCode() == HttpURLConnection.HTTP_OK){
            response.setResult(read());
            response.setOk(true);
        }else {
            response.setOk(false);
        }
        return response;
    }
}
