package com.tukkeendoo.app.views.main.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.config.Tukkeendoo;
import com.tukkeendoo.app.network.HTTPRequest;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.network.Webservice;
import com.tukkeendoo.app.views.base.BasePage;

/**
 * Created by fallou on 15/06/2016.
 */
public class HomePage extends BasePage {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.page_home, container, false);

        HTTPRequest request = new HTTPRequest(Tukkeendoo.PROFILE, HTTPRequest.POST, null);
        Webservice.executeRequestWithListener(request, this);

        return rootView;
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        if (response.isOk()) {
            TextView welcome = (TextView) findViewById(R.id.text_welcome);
            welcome.setText(response.getData().toString());
        }
    }

    @Override
    public CharSequence getTitle() {
        return "Home";
    }
}
