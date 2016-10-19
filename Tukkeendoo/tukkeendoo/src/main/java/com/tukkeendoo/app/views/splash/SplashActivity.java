package com.tukkeendoo.app.views.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.controllers.Controller;
import com.tukkeendoo.app.models.Preferences;
import com.tukkeendoo.app.models.User;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.views.base.BaseActivity;
import com.tukkeendoo.app.views.main.MainActivity;
import com.tukkeendoo.app.views.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends BaseActivity {
    public static int LOGIN_CODE = 0x1;
    private String LOG_TAG = SplashActivity.class.getSimpleName();
    private Runnable loginRunnable = new Runnable() {
        @Override
        public void run() {
            startNextActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(loginRunnable, 3000 /* milliseconds */);

        //Intent intent = new Intent(Intent.ACTION_DIAL);
    }

    private void startNextActivity(){
        if (Controller.isUserFirstLogin()) {
            Controller.startActivityForResult(this, LoginActivity.class, LOGIN_CODE);
        }else {
            String token = Preferences.getPreference(Preferences.USER_TOKEN).getString(Preferences.USER_TOKEN, null);
            if (token != null) {
                User.loginUserByToken(this, token);
            }
        }
    }

    @Override
    public void onHTTPBegin() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_CODE && resultCode == RESULT_OK){
            boolean success = data.getBooleanExtra("success", false);
            if (success){
                Controller.startActivity(this, MainActivity.class, true);
            }
        }
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        super.onHTTPResponse(response);
        if (response.isOk()) {
            JSONObject object = (JSONObject) response.getData();
            boolean success = false;
            try {
                success = object.getBoolean("success");
            } catch (JSONException e) {
                Log.w(LOG_TAG, e.getMessage(), e);
            }
            if (success) {
                Controller.startActivity(this, MainActivity.class, true);
            }else {
                Controller.startActivityForResult(this, LoginActivity.class, LOGIN_CODE);
            }
        }else {
            Controller.startActivityForResult(this, LoginActivity.class, LOGIN_CODE);
        }
    }
}
