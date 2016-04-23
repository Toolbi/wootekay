package com.tukkeendoo.app.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.config.Tukkeendoo;
import com.tukkeendoo.app.network.HTTPRequest;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.network.Webservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity implements Webservice.WebServiceListener{
    private String LOG_TAG = SplashActivity.class.getSimpleName();
    private TextView appName;
    private ProgressBar progressBar;
    private Button testButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appName =  (TextView)findViewById(R.id.text_app_name);
        testButton = (Button)findViewById(R.id.test_button);
        progressBar = (ProgressBar) findViewById(R.id.splash_progress);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL url = null;
                Map<String, Object> parameters = new HashMap();
                parameters.put("name", "Fallou");
                try {
                    url = new URL(Tukkeendoo.TEST_URL);
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG,"", e);
                }
                HTTPRequest request = new HTTPRequest(url, HTTPRequest.POST, parameters);

                Webservice.executeRequestWithListener(request, SplashActivity.this);
            }
        });
    }

    @Override
    public void onBegin() {
        testButton.setVisibility(View.GONE);
        appName.setText("Loading please wait...");
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgress(Integer... values) {
        progressBar.setProgress(values[0]);
    }

    @Override
    public void onResponse(HTTPResponse response) {
        progressBar.setVisibility(View.GONE);
        testButton.setVisibility(View.VISIBLE);
        testButton.setText("Resend request");
        appName.setText(response.getResult().toString());
    }
}
