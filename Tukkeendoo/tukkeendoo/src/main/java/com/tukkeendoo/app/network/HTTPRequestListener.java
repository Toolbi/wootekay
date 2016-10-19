package com.tukkeendoo.app.network;

/**
 * Created by fallou on 14/06/2016.
 */
public interface HTTPRequestListener {
    public void onHTTPBegin();
    public void onHTTPProgress(Integer... values);
    public void onHTTPResponse(HTTPResponse response);
}
