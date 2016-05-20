package com.tukkeendoo.app.view.login;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.controllers.Controller;
import com.tukkeendoo.app.models.Preferences;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.view.Base.BaseActivity;
import com.tukkeendoo.app.view.home.HomeActivity;
import com.tukkeendoo.app.view.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginActivity extends BaseActivity {
    private static final String LOG = LoginActivity.class.getSimpleName();
    private TextView textUserInformer;
    private AutoCompleteTextView textUserName;
    private EditText textPassword;
    private Button loginButton;
    private Button registerButton;
    private Button forgotPasswordButton;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews(){
        textUserInformer = (TextView) findViewById(R.id.text_user_informer);
        textUserName = (AutoCompleteTextView)findViewById(R.id.text_user_name);
        textUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    textPassword.requestFocus();
                }
                return true;
            }
        });
        textPassword = (EditText) findViewById(R.id.text_password);
        textPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    loginButton.setBackgroundColor(getResources().getColor(R.color.tukkeeDarkVert));
                }
                return false;
            }
        });
        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterActivity();
            }
        });
        forgotPasswordButton = (Button) findViewById(R.id.forgot_password_button);
        forgotPasswordButton.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }


    private void login(){
        username = textUserName.getText().toString();
        password = textPassword.getText().toString();
        Controller.loginUser(this,username,password);
    }

    private void startRegisterActivity(){
        Controller.startActivity(this,RegisterActivity.class, false);
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        super.onHTTPResponse(response);
        if (response.isOk()){
            JSONObject object = (JSONObject) response.getData();
            try {
                Preferences.savePreference("token", Preferences.TOKEN, object.getString("token"));
            } catch (JSONException e) {
                Log.w(LOG, "", e);
            }
            Preferences.savePreference("username", "user_name", username);
            Preferences.savePreference( "password", "user_passwod", password);

            Preferences.saveCookie((List<String>) response.getHeader().get(HTTPResponse.Header.COOKIE));

            startHomeActivity(object.toString());
        }
    }

    public void startHomeActivity(String response){
        Bundle extra = new Bundle();
        extra.putString("message",response);
        Controller.startActivity(this, HomeActivity.class, extra, false);
    }
}
