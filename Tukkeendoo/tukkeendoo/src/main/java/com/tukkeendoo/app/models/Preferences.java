package com.tukkeendoo.app.models;

import android.content.SharedPreferences;
import android.text.TextUtils;
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

    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_TOKEN = "user_token";
    public static final String ALREADY_LOGGED_IN = "already_login_in";
    private static final String LOG_TAG = Preferences.class.getSimpleName();
    private static boolean cookiesChange;

    public static void savePreference(String preference, String key, Object value){
        SharedPreferences preferences = Tukkeendoo.getInstance().getSharedPreferences(preference, Tukkeendoo.getInstance().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, String.valueOf(value));
        editor.commit();
    }

    public static SharedPreferences getPreference(String preference){
        return Tukkeendoo.getInstance().getSharedPreferences(preference, Tukkeendoo.getInstance().MODE_PRIVATE);
    }

    public static Object retrievePreference(String preference, String key, Object defaultValue){
        SharedPreferences preferences = Tukkeendoo.getInstance().getSharedPreferences(preference, Tukkeendoo.getInstance().MODE_PRIVATE);
        return preferences.getString(key, String.valueOf(defaultValue));
    }

    public static void saveBooleanPreference(String preference, String key, Boolean value){
        SharedPreferences preferences = Tukkeendoo.getInstance().getSharedPreferences(preference, Tukkeendoo.getInstance().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean retrieveBooleanPreference(String preference,String key, Boolean defaultValue){
        SharedPreferences preferences = Tukkeendoo.getInstance().getSharedPreferences(preference, Tukkeendoo.getInstance().MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    public static void saveCookies(List<String> cookies){

        String cookiesToSave = null;

        if(cookies != null)
        {
            CookieStore cookieStore = Tukkeendoo.getInstance().getCookieStore();
            if (cookieStore.getCookies().size() > 0) {
                cookieStore.removeAll();
            }
            for (String cookieValue : cookies) {
                try {
                    cookieStore.add(null, HttpCookie.parse(cookieValue).get(0));
                } catch (Exception e) {
                    Log.w(LOG_TAG, "", e);
                }
            }
            cookiesToSave = TextUtils.join(";",cookieStore.getCookies());
            //cookiesChange = cookiesToSave.equals(retrieveCookies());

        }
        savePreference(HTTPRequest.Header.COOKIES, HTTPRequest.Header.COOKIES, cookiesToSave);
    }

    public static String retrieveCookies(){
        SharedPreferences preferences = Tukkeendoo.getInstance().getSharedPreferences(HTTPRequest.Header.COOKIES, Tukkeendoo.getInstance().MODE_PRIVATE);
        String cookies = preferences.getString(HTTPRequest.Header.COOKIES, null);
        return cookies;
    }
}
