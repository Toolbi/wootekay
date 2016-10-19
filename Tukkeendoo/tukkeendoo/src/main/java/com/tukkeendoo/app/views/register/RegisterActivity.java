package com.tukkeendoo.app.views.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.models.User;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.utils.Validator;
import com.tukkeendoo.app.views.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

    private TextView textUserInformer;
    private EditText textFirstName;
    private EditText textLastName;
    private EditText textEmail;
    private EditText textPhone;
    private EditText textPassword;
    private Button registerButton;
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
            String firsName = textFirstName.getText().toString();
            String lastName = textLastName.getText().toString();
            String email = textEmail.getText().toString();
            String phone = textPhone.getText().toString();
            String password = textPassword.getText().toString();
            User.registerUser(this, firsName, lastName, phone, email, password);
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
        //TODO check for result here
    }
}
