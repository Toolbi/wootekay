package com.tukkeendoo.app.controllers;

import android.content.Intent;
import android.os.Bundle;

import com.tukkeendoo.app.config.TukkeeConfig;
import com.tukkeendoo.app.models.Preferences;
import com.tukkeendoo.app.network.HTTPRequest;
import com.tukkeendoo.app.network.Webservice;
import com.tukkeendoo.app.views.base.BaseActivity;
import com.tukkeendoo.app.views.login.LoginActivity;

/**
 * Created by fallou on 15/05/2016.
 */
public class Controller {


    private static void startActivity(BaseActivity context, Intent intent, boolean finish){
        context.startActivity(intent);
        if (finish) {
            context.finish();
        }
    }
    public static void startActivity(BaseActivity context, Class ativityClass, boolean finish){
        Intent intent = new Intent(context, ativityClass);
        startActivity(context,intent,finish);
    }
    public static void startActivity(BaseActivity context, Class ativityClass, Bundle extras, boolean finish){
        Intent intent = new Intent(context, ativityClass);
        intent.putExtras(extras);
        startActivity(context,intent,finish);
    }

    public static void startActivityForResult(BaseActivity context, Class ativityClass, int requestCode){
        Intent intent = new Intent(context, ativityClass);
        context.startActivityForResult(intent, requestCode);
    }

//    public static boolean isUserAlreadyLogin(){
//        return Preferences.retrieveBooleanPreference(Preferences.ALREADY_LOGGED_IN, Preferences.ALREADY_LOGGED_IN, false);
//    }

//    public static void loginUser(BaseActivity context, String userName, String password){
//        if (!isUserAlreadyLogin()) {
//            User.loginUser(context, userName, password);
//        }else {
//            loadHome(context);
//        }
//    }

    public static void loadHome(BaseActivity context){
        String token = Preferences.retrievePreference(Preferences.USER_TOKEN, Preferences.USER_TOKEN, null);
        if (token != null) {
//            User.loginUserByToken(context, token);
            HTTPRequest request = new HTTPRequest(TukkeeConfig.HOME, HTTPRequest.POST, null);
            Webservice.executeRequestWithListener(request, context);
        }else {
            startActivity(context, LoginActivity.class, true);
        }

    }
}
