package com.tukkeendoo.app.controllers;

import android.content.Intent;
import android.os.Bundle;

import com.tukkeendoo.app.models.User;
import com.tukkeendoo.app.view.Base.BaseActivity;

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

    public static void loginUser(BaseActivity context, String userName, String password){
        User.loginUser(context,userName,password);
    }
}
