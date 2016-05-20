package com.tukkeendoo.app.application;

import android.app.Application;
import android.content.res.Configuration;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;

/**
 * Created by fallou on 15/05/2016.
 */
public class Tukkeendoo extends Application {
    private static Tukkeendoo instance;
    private CookieManager cookieManager;
    private CookieStore cookieStore;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static Tukkeendoo getInstance() {
        return instance;
    }

    public Tukkeendoo(){
        super();
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieStore = cookieManager.getCookieStore();
        instance = this;
    }

    public CookieManager getCookieManager() {
        return cookieManager;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
