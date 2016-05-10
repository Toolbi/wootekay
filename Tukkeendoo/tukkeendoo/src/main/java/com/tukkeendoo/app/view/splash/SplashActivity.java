package com.tukkeendoo.app.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.view.Base.BaseActivity;
import com.tukkeendoo.app.view.login.LoginActivity;

public class SplashActivity extends BaseActivity {
    private String LOG_TAG = SplashActivity.class.getSimpleName();
    private ViewGroup container;
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
        container = (ViewGroup) findViewById(R.id.container);
        container.postDelayed(loginRunnable, 4 /* milliseconds */);
    }

    private void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
    }
}
