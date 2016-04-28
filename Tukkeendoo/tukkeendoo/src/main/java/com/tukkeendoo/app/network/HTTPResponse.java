package com.tukkeendoo.app.network;

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
    private Object message;
    private Object data;
    private boolean ok;

    public HTTPResponse() {
        header = new HashMap();
        error = new String("Could not connect to the server !");
        code = HttpURLConnection.HTTP_NO_CONTENT;
        message = new String("Network error !");
        data = new String("There is no data from the server !");
        ok = false;
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));

        StringBuffer sringBuffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null)
        {
            sringBuffer.append(line + "\n");
        }
        reader.close();

        this.error = sringBuffer;
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
}
