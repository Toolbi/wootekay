package com.tukkeendoo.app.views.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.controllers.Controller;
import com.tukkeendoo.app.models.User;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.views.base.BaseActivity;
import com.tukkeendoo.app.views.login.LoginActivity;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        buildViews();
    }

    private void buildViews(){
        //TODO build activity views here

        final Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();
            }
        });
    }

    private void disconnect() {
        User.logoutUser(this);
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        super.onHTTPResponse(response);

        Controller.startActivity(this, LoginActivity.class, true);
    }


}
