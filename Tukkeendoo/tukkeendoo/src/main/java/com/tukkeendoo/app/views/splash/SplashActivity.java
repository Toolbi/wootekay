package com.tukkeendoo.app.views.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.controllers.Controller;
import com.tukkeendoo.app.models.Preferences;
import com.tukkeendoo.app.models.User;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.network.NetworkConnectivityManager;
import com.tukkeendoo.app.views.base.BaseActivity;
import com.tukkeendoo.app.views.login.LoginActivity;
import com.tukkeendoo.app.views.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static com.tukkeendoo.app.config.TukkeeConfig.LOGIN_CODE;

public class SplashActivity extends BaseActivity {
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
        String token = Preferences.getUserPreference(Preferences.USER_TOKEN, null);
        if (token != null) {
            if (NetworkConnectivityManager.isConnected()) {
                User.loginUserByToken(this, token);
            }else {
                Toast toast = Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG);
                toast.show();
                Controller.startActivity(this, MainActivity.class, true);
            }
        } else  {
            Controller.startActivityForResult(this, LoginActivity.class, LOGIN_CODE);
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
            }else {
                startNextActivity();
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
            } catch (Exception e) {
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
