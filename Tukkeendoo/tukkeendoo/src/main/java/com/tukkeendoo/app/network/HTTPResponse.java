package com.tukkeendoo.app.network;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by fallou on 16/04/2016.
 */
public class HTTPResponse {
    private Map header;
    private Object error;
    private int code;
    private Object message;
    private JSONObject result;
    private boolean ok;

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

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
