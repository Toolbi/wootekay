package com.tukkeendoo.app.network;

import org.json.JSONObject;

/**
 * Created by fallou on 16/04/2016.
 */
public class HTTPResponse {
    private String header;
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
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
