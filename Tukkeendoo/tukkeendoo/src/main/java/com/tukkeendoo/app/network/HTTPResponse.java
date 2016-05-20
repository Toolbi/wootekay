package com.tukkeendoo.app.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by fallou on 16/04/2016.
 */
public class HTTPResponse {
    private Map header;
    private Object error;
    private int code;
    private Object message;
    private Object data;
    private boolean ok;

    public HTTPResponse() {
    }

    public static HTTPResponse defaultResponse(){
        HTTPResponse response = new HTTPResponse();
        response.error = new String("Error: Could not connect to the server !");
        response.code = HttpURLConnection.HTTP_UNAVAILABLE;
        response.message = new String("Network error !");
        response.ok = false;
        response.data = new String("Error");

        return response;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Map getHeader() {
        return header;
    }

    public void setHeader(Map header) {
        this.header = header;
    }

    public Object getError() {
        return error;
    }

    public String readErrorStream(InputStream errorStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(errorStream)));

        StringBuffer sringBuffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null)
        {
            sringBuffer.append(line + "\n");
        }
        reader.close();

        this.error = sringBuffer.toString();
        return sringBuffer.toString();
    }

    public int getCode() {
        return code;
    }

    public Object getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public class Header {
        public final static String CONTENT_TYPE = "Content-Type";
        public final static String CONTENT_LENGTH = "Content-Length";
        public final static String COOKIE = "Set-Cookie";
        public final static String CONNECTION = "Connection";
        public final static String HOST = "Host";
        public final static String ACCEPT_LANGUAGE = "Accept-Language";
        public final static String ACCEPT_ENCODING = "Accept-Encoding";
    }
}
