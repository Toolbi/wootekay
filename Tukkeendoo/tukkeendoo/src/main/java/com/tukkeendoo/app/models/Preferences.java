package com.tukkeendoo.app.models;

import android.content.SharedPreferences;
import android.util.Log;

import com.tukkeendoo.app.application.Tukkeendoo;
import com.tukkeendoo.app.network.HTTPRequest;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.List;


/**
 * Created by fallou on 16/05/2016.
 */
public class Preferences {

    public static final String TOKEN = "token";
    private static final String LOG_TAG = Preferences.class.getSimpleName();

    public static void savePreference(String preference, String key, Object value){
        SharedPreferences preferences = Tukkeendoo.getInstance().getSharedPreferences(preference, Tukkeendoo.getInstance().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, String.valueOf(value));
        editor.commit();
    }

    public static Object retreivePreference(String key, Object defaultValue){
        SharedPreferences preferences = Tukkeendoo.getInstance().getSharedPreferences(TOKEN, Tukkeendoo.getInstance().MODE_PRIVATE);
        return preferences.getString(key, String.valueOf(defaultValue));
    }

    public static void saveCookie(List<String> cookies){

        if(cookies != null)
        {
            CookieStore cookieStore = Tukkeendoo.getInstance().getCookieStore();
            for (String cookieValue : cookies) {
                try {
                    cookieStore.add(null, HttpCookie.parse(cookieValue).get(0));
                } catch (Exception e) {
                    Log.w(LOG_TAG, "", e);
                }
            }
            savePreference(HTTPRequest.Header.COOKIE, HTTPRequest.Header.COOKIE, cookieStore.getCookies());
        }
    }

}
