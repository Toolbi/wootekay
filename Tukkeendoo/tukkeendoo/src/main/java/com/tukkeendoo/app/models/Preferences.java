package com.tukkeendoo.app.models;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.tukkeendoo.app.application.Tukkeendoo;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.tukkeendoo.app.network.HTTPRequest.Header.COOKIES;


/**
 * Created by fallou on 16/05/2016.
 */
public class Preferences {

    private static final String TAG = Preferences.class.getSimpleName();
    private static final String DEFAULT_PREFERENCES = "default_preferences";
    private static final String USER_PREFERENCES = "user_preferences";

    public static final String USER_FIRST_NAME = "user_first_name";
    public static final String USER_LAST_NAME = "user_last_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_TOKEN = "user_token";
    public static final String ALREADY_LOGGED_IN = "already_login_in";

//    private static boolean cookiesChange;

    public static void savePreference(String preference, String key, String value){
        if (preference == null || key == null || value == null){
            Log.w(TAG,"Can't save a null preference", new NullPointerException("preference or key or value is null"));
            return;
        }
        SharedPreferences preferences = getPreference(preference);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static SharedPreferences getPreference(String preference){
        return Tukkeendoo.getInstance().getSharedPreferences(preference, MODE_PRIVATE);
    }

    public static String retrievePreference(String preference, String key, String defaultValue){
        if (preference == null || key == null){
            Log.w(TAG,"Can't retrieve a null preference", new NullPointerException("preference or keyl is null"));
            return defaultValue;
        }
        SharedPreferences preferences = getPreference(preference);
        return preferences.getString(key, defaultValue);
    }

    public static void saveBooleanPreference(String key, Boolean value){
        SharedPreferences preferences = getPreference(DEFAULT_PREFERENCES);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean retrieveBooleanPreference(String key, Boolean defaultValue){
        SharedPreferences preferences = getPreference(DEFAULT_PREFERENCES);
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
                    Log.w(TAG, "", e);
                }
            }
            cookiesToSave = TextUtils.join(";",cookieStore.getCookies());
            //cookiesChange = cookiesToSave.equals(retrieveCookies());
        }
        savePreference(DEFAULT_PREFERENCES, COOKIES, cookiesToSave);
    }

    public static String retrieveCookies(){
        String cookies = retrievePreference(DEFAULT_PREFERENCES, COOKIES, null);
        return cookies;
    }


    public static void saveUserPreference(String key, String value){
        savePreference(USER_PREFERENCES, key, value);
    }

    public static String getUserPreference(String key, String defaultValue){
        return retrievePreference(USER_PREFERENCES, key, defaultValue);
    }
}
