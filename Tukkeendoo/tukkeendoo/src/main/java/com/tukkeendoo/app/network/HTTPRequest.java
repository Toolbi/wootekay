package com.tukkeendoo.app.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * Created by fallou on 16/04/2016.
 */
public class HTTPRequest {
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String UTF_8 = "UTF-8";
    private static final String LOG_TAG = HTTPRequest.class.getSimpleName();

    private URL url;
    private HttpURLConnection connection;
    private String method;
    private int readTimeOut;
    private int connectTimeOut;
    private Map<String,Object> parameters;
    private HTTPResponse response;

    public HTTPRequest(URL url, String method, Map<String, Object> parameters) {
        this.url = url;
        this.method = method;
        this.parameters = parameters;
    }

    public HTTPRequest(String url, String method, Map<String, Object> parameters) {
        setURL(url);
        this.method = method;
        this.parameters = parameters;
    }

    private void setURL(String url){
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"", e);
        }
    }

    private void setParameters() throws IOException {
        if (parameters == null)
            throw new IOException("HTTP parameters are null");

        StringBuilder requestParameters = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Object> param : parameters.entrySet()){
            if (first){
                first = false;
            }else {
                requestParameters.append('&');
            }
            requestParameters.append(URLEncoder.encode(param.getKey(), UTF_8));
            requestParameters.append('=');
            requestParameters.append(URLEncoder.encode(String.valueOf(param.getValue()), UTF_8) );
        }
        if (method.equals(GET)){
            //requestParameters.replace(0,1, "?");
            Log.v(LOG_TAG, requestParameters.toString());
            String urlString = url.getProtocol() + "://" + url.getHost() + url.getPath() + "?" + requestParameters;
            Log.v(LOG_TAG, urlString);
            setURL(urlString);
            return;
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        writer.write(requestParameters.toString());
        writer.flush();
    }

    public void setParameters(Map parameters) throws IOException {
        this.parameters = parameters;
        setParameters();
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

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public HTTPResponse getResponse() {
        return response;
    }

    private HttpURLConnection createConnection() throws IOException {

        connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(readTimeOut /* milliseconds */);
        connection.setConnectTimeout(connectTimeOut /* milliseconds */);
        connection.setChunkedStreamingMode(0);
        connection.setRequestMethod(method);
        connection.setDoInput(true);

        if (method.equals(POST)) {
            connection.setDoOutput(true);
        }
        if (method.equals(GET)){
            connection.setDoOutput(false);
        }


        return connection;
    }

    private void openConection() throws IOException {
        if (connection == null)
            throw new IOException("cannot open connection because it is null");
        connection.connect();
    }

    private void closeConnection() throws IOException{
        if (connection == null)
            throw new IOException("cannot close connection because it is null");
        connection.disconnect();
    }

    public JSONObject read() throws IOException, JSONException {
        JSONObject result;

        InputStream in = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuffer sringBuffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null)
        {
            sringBuffer.append(line + "\n");
        }
        reader.close();

        Log.v(LOG_TAG, sringBuffer.toString());

        result = new JSONObject(sringBuffer.toString());

        return result;
    }

    public void send() throws IOException, JSONException {

        if (method.equals(GET)){
            setParameters();
        }
        createConnection();
        openConection();
        if (method.equals(POST)) {
            setParameters();
        }
        setResponse();
        closeConnection();
    }

    private void setResponse() throws IOException, JSONException {
        response = new HTTPResponse();
        response.setCode(connection.getResponseCode());
        response.setHeader(connection.getHeaderFields());
        response.setMessage(connection.getResponseMessage());
        if (response.getCode() == HttpURLConnection.HTTP_OK){
            response.setData(read());
            response.setOk(true);
        }else {
            response.setOk(false);
            response.readErrorStream(connection.getErrorStream());
        }
    }
}
