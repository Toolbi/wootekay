package com.tukkeendoo.app.network;

import android.util.Log;

import com.tukkeendoo.app.models.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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



    public enum HeaderField {
        CONTENT_TYPE("Content-Type"),
        CONTENT_LENGTH("Content-Length"),
        COOKIE("Cookie"),
        CONNECTION("Connection");
        public String name;
        HeaderField(String name){
            this.name = name;
        }
    };

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String UTF_8 = "UTF-8";
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String LOG_TAG = HTTPRequest.class.getSimpleName();

    private URL url;
    protected HttpURLConnection connection;
    private String method;
    private int readTimeOut;
    private int connectTimeOut;
    private boolean instanceFollowRedirects;
    private boolean followRedirects;
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
        if (parameters == null) {
            Log.w(LOG_TAG, "", new IOException("HTTP parameters are null"));
            //throw new IOException("HTTP parameters are null");
            return;
        }

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
            Log.v(LOG_TAG, requestParameters.toString());
            String urlString = url.getProtocol() + "://" + url.getHost() + url.getPath() + "?" + requestParameters;
            Log.v(LOG_TAG, urlString);
            setURL(urlString);
            return;
        }
        connection.setFixedLengthStreamingMode(requestParameters.toString().getBytes(UTF_8).length);
        OutputStream out = new BufferedOutputStream(connection.getOutputStream()); // Pour optimiser la mémoire
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,UTF_8));
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

    public boolean isInstanceFollowRedirects() {
        return instanceFollowRedirects;
    }

    public void setInstanceFollowRedirects(boolean instanceFollowRedirects) {
        this.instanceFollowRedirects = instanceFollowRedirects;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
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
        //connection.setInstanceFollowRedirects(instanceFollowRedirects);
        //connection.setFollowRedirects(followRedirects);

        connection.setRequestProperty(Header.COOKIES, Preferences.retrieveCookies());

        if (method.equals(POST)) {
            connection.setDoOutput(true);
        }
        if (method.equals(GET)){
            connection.setChunkedStreamingMode(0);
            connection.setDoOutput(false);
        }
        connection.setRequestMethod(method);
        connection.setRequestProperty(Header.CONTENT_TYPE, CONTENT_TYPE);
        connection.setDoInput(true);
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

    /**
     *
     * @return responseData JSONObject
     * @throws IOException
     * @throws JSONException
     */
    public Object read() throws IOException{
        Object result;

        InputStream in = new BufferedInputStream(connection.getInputStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(in)); // Pour optimiser la mémoire

        StringBuffer sringBuffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null)
        {
            sringBuffer.append(line + "\n");
        }
        reader.close();

        Log.v(LOG_TAG, sringBuffer.toString());

        try {
            result = new JSONObject(sringBuffer.toString());
        }catch (JSONException e){
            result = sringBuffer.toString();
            Log.w(LOG_TAG, "", e);
        }

        return result;
    }

    /**
     *
     * @throws IOException
     * @throws JSONException
     */
    public void send() throws IOException, JSONException {

        if (method.equals(GET)){
            setParameters();
        }
        createConnection();

        if (method.equals(POST)) {
            setParameters();
        }

        openConection();
        setResponse();
        closeConnection();
    }

    /**
     *
     * @throws IOException
     * @throws JSONException
     */
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

    public class Header {
        public final static String CONTENT_TYPE = "Content-Type";
        public final static String CONTENT_LENGTH = "Content-Length";
        public final static String COOKIES = "Cookie";
        public final static String CONNECTION = "Connection";
        public final static String HOST = "Host";
        public final static String ACCEPT_LANGUAGE = "Accept-Language";
        public final static String ACCEPT_ENCODING = "Accept-Encoding";
    }
}
