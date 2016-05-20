package com.tukkeendoo.app.view.splash;

import android.os.Bundle;
import android.os.Handler;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.controllers.Controller;
import com.tukkeendoo.app.view.Base.BaseActivity;
import com.tukkeendoo.app.view.login.LoginActivity;

public class SplashActivity extends BaseActivity {
    private String LOG_TAG = SplashActivity.class.getSimpleName();
    private Runnable loginRunnable = new Runnable() {
        @Override
        public void run() {
            startLoginActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(loginRunnable, 3000 /* milliseconds */);

        //Intent intent = new Intent(Intent.ACTION_DIAL);
    }

    private void startLoginActivity(){
        Controller.startActivity(this, LoginActivity.class, true);
    }

    @Override
    public void onBackPressed() {
    }
}
