package com.tukkeendoo.app.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.config.Tukkeendoo;
import com.tukkeendoo.app.network.HTTPRequest;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.network.Webservice;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends BaseActivity {
    private String LOG_TAG = SplashActivity.class.getSimpleName();
    private TextView appName;
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
                Map<String, Object> parameters = new HashMap();
                parameters.put("name", "Fallou");
                HTTPRequest request = new HTTPRequest(Tukkeendoo.TEST_URL, HTTPRequest.GET, parameters);
                Webservice.executeRequestWithListener(request, SplashActivity.this);
            }
        });
    }

    @Override
    public void onHTTPBegin() {
        super.onHTTPBegin();
        testButton.setVisibility(View.GONE);
        appName.setText("Loading please wait...");
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        super.onHTTPResponse(response);
        testButton.setVisibility(View.VISIBLE);
        testButton.setText("Resend request");
        appName.setText(response.getData().toString());
    }
}
