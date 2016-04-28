package com.tukkeendoo.app.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.network.Webservice;

/**
 * Created by fallou on 28/04/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements Webservice.WebServiceListener{
    @NonNull
    protected ProgressBar progressBar;


    @Override
    public void onHTTPBegin() {
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHTTPProgress(Integer... values) {
        progressBar.setProgress(values[0]);
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        progressBar.setVisibility(View.GONE);
    }
}
