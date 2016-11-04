package com.tukkeendoo.app.views.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.config.TukkeeConfig;
import com.tukkeendoo.app.network.HTTPRequest;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.network.Webservice;
import com.tukkeendoo.app.views.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

public class TestActivity extends BaseActivity {

    private String LOG_TAG = TestActivity.class.getSimpleName();
    private TextView appName;
    private Button testButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        appName =  (TextView)findViewById(R.id.text_app_name);
        testButton = (Button)findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> parameters = new HashMap();
                parameters.put("name", "Fallou");
                HTTPRequest request = new HTTPRequest(TukkeeConfig.TEST_URL, HTTPRequest.POST, parameters);
                Webservice.executeRequestWithListener(request, TestActivity.this);
            }
        });
    }

    @Override
    public void onHTTPBegin() {
        super.onHTTPBegin();
        testButton.setVisibility(View.GONE);
        appName.setText(R.string.loading_please_wait);
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        super.onHTTPResponse(response);
        testButton.setVisibility(View.VISIBLE);
        testButton.setText("Resend request");
        if (response.isOk())
            appName.setText(response.getData().toString());
        else
            appName.setText((String)response.getError());
    }
}
