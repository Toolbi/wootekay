package com.tukkeendoo.app.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tukkeendoo.app.application.Tukkeendoo;

/**
 * Created by fallou on 23/10/2016.
 */

public class NetworkConnectivityManager {

    public static boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) Tukkeendoo.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        return isConnected;
    }
}
