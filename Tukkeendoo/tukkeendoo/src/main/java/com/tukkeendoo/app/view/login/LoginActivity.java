package com.tukkeendoo.app.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.config.Tukkeendoo;
import com.tukkeendoo.app.network.HTTPRequest;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.network.Webservice;
import com.tukkeendoo.app.view.Base.BaseActivity;
import com.tukkeendoo.app.view.home.HomeActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {
    private AutoCompleteTextView textUserName;
    private EditText textPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textUserName = (AutoCompleteTextView)findViewById(R.id.text_user_name);
        textPassword = (EditText) findViewById(R.id.text_password);
        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    private void login(){
        String userName = textUserName.getText().toString();
        String password = textPassword.getText().toString();
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("username", userName);
        parameters.put("password", password);
        HTTPRequest request = new HTTPRequest(Tukkeendoo.TEST_URL,HTTPRequest.POST,parameters);
        Webservice.executeRequestWithListener(request, this);
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        super.onHTTPResponse(response);
        if (response.isOk()){
            JSONObject object = (JSONObject) response.getData();
            startHomeActivity(object.toString());
        }
    }

    public void startHomeActivity(String response){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("message",response);
        startActivity(intent);
    }
}
