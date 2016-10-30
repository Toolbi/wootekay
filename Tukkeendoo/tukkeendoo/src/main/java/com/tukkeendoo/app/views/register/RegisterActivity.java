package com.tukkeendoo.app.views.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.models.Preferences;
import com.tukkeendoo.app.models.User;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.utils.Validator;
import com.tukkeendoo.app.views.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private TextView textUserInformer;
    private EditText textFirstName;
    private EditText textLastName;
    private EditText textEmail;
    private EditText textPhone;
    private EditText textPassword;
    private Button registerButton;

    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        iniViews();
    }


    private  void iniViews(){
        textUserInformer = (TextView) findViewById(R.id.text_user_informer);
        textFirstName = (EditText) findViewById(R.id.text_user_first_name);
        textLastName = (EditText) findViewById(R.id.text_user_last_name);
        textEmail = (EditText) findViewById(R.id.text_user_email);
        textPhone = (EditText) findViewById(R.id.text_user_phone_number);
        textPassword = (EditText) findViewById(R.id.text_user_password);
        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register(){

        resetViews();

        boolean success = Validator.validate(textFirstName, R.string.this_field_is_required) &&
                Validator.validate(textLastName, R.string.this_field_is_required) &&
                Validator.validate(textEmail, R.string.invalid_email) &&
                Validator.validate(textPhone, R.string.this_field_is_required) &&
                Validator.validate(textPassword, R.string.too_short_password);

        if (success){
            userFirstName = textFirstName.getText().toString();
            userLastName = textLastName.getText().toString();
            userEmail = textEmail.getText().toString();
            userPhone = textPhone.getText().toString();
            String password = textPassword.getText().toString();
            User.registerUser(this, userFirstName, userLastName, userPhone, userEmail, password);
        }else {
            setInputError(getString(R.string.register_failed));
        }
    }

    private void setInputError(String error) {
        textUserInformer.setVisibility(View.VISIBLE);
        textUserInformer.setError(" ");
        textUserInformer.setText(error);
    }

    private void resetViews(){
        textUserInformer.setVisibility(View.GONE);
        textFirstName.setError(null);
        textLastName.setError(null);
        textEmail.setError(null);
        textPhone.setError(null);
        textPassword.setError(null);
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        super.onHTTPResponse(response);
        //TODO check for result here and check register.php
        if (response.isOk()){

            JSONObject data = (JSONObject) response.getData();
            try {
                boolean success = data.getBoolean("success");
                if (success) {
                    Preferences.saveBooleanPreference(Preferences.ALREADY_LOGGED_IN, true);

                    Preferences.saveUserPreference(Preferences.USER_EMAIL, userEmail);

                    Intent resultData = new Intent();
                    resultData.putExtra("success", success);
                    setResult(RESULT_OK, resultData);
                    finish();

                }else {
                    String error = data.getString("message");
                    setInputError(error);
                    return;
                }
            } catch (JSONException e) {
                Log.w(TAG, e.getMessage(), e);
            }
        }else {
            setInputError((String) response.getError());
        }
    }
}
