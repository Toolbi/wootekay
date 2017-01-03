package com.tukkeendoo.app.network;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.application.Tukkeendoo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fallou on 16/04/2016.
 */
public class HTTPResponse {
    private Map header;
    private Object error;
    private int code;
    private String message;
    private Object data;
    private boolean ok;

    public HTTPResponse() {
    }

    public static HTTPResponse defaultResponse(){
        HTTPResponse response = new HTTPResponse();
        response.error = Tukkeendoo.getInstance().getString(R.string.network_error);
        response.code = HttpURLConnection.HTTP_UNAVAILABLE;
        response.message = Tukkeendoo.getInstance().getString(R.string.network_error_message);
        response.ok = false;
        response.data = new Object();
        response.header = new HashMap();

        return response;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
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

    public String getMessage() {
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName() + ":{\n\t");
        stringBuilder.append("code:" + code + "\n\t");
        stringBuilder.append("data:" + data + "\n\t");
        if (error != null) {
            stringBuilder.append("message:" + message + "\n\t");
            stringBuilder.append("error:" + error + "\n\t");
        }
        stringBuilder.append("header:" + header + "\n}");

        return stringBuilder.toString();
    }

    public static class Header {
        public final static String CONTENT_TYPE = "Content-Type";
        public final static String CONTENT_LENGTH = "Content-Length";
        public final static String COOKIE = "Set-Cookie";
        public final static String CONNECTION = "Connection";
        public final static String HOST = "Host";
        public final static String ACCEPT_LANGUAGE = "Accept-Language";
        public final static String ACCEPT_ENCODING = "Accept-Encoding";
    }
}
