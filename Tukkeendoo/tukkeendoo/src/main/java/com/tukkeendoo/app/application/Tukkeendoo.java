package com.tukkeendoo.app.application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.network.NetworkConnectivityManager;

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
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!NetworkConnectivityManager.isConnected()){
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        };

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(receiver, filter);

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
