package com.tukkeendoo.app.views.login;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.controllers.Controller;
import com.tukkeendoo.app.models.Preferences;
import com.tukkeendoo.app.models.User;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.utils.TextChangeListener;
import com.tukkeendoo.app.utils.Validator;
import com.tukkeendoo.app.utils.thread_manager.Task;
import com.tukkeendoo.app.views.base.BaseActivity;
import com.tukkeendoo.app.views.register.RegisterActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.tukkeendoo.app.config.TukkeeConfig.REGISTER_CODE;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private TextView textUserInformer;
    private AutoCompleteTextView textUserEmail;
    private EditText textPassword;
    private Button loginButton;
    private Button registerButton;
    private Button forgotPasswordButton;
    private String userEmail;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews(){
        textUserInformer = (TextView) findViewById(R.id.text_user_informer);
        textUserEmail = (AutoCompleteTextView)findViewById(R.id.text_user_name);
        textUserEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    textPassword.requestFocus();
                }
                return true;
            }
        });
       textUserEmail.addTextChangedListener(new TextChangeListener() {
           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               //new FetchEmailTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
           }
       });
               textPassword = (EditText) findViewById(R.id.text_user_password);
        textPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    login();
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
        textUserInformer.setVisibility(View.GONE);
        userEmail = textUserEmail.getText().toString();
        password = textPassword.getText().toString();
        if(!Validator.check(userEmail)) {
            textUserEmail.setError(getString(R.string.invalid_email));
        }
        if (!Validator.verify(password)){
            textPassword.setError(getString(R.string.too_short_password));
        }
        if (Validator.check(userEmail) && Validator.verify(password)){
            User.loginUser(this, userEmail, password);
        }else {
            setInputError(getString(R.string.authentication_failed));
        }
    }

    private void startRegisterActivity(){
        Controller.startActivityForResult(this, RegisterActivity.class, REGISTER_CODE);
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        super.onHTTPResponse(response);
        if (response.isOk()){

            JSONObject data = (JSONObject) response.getData();
            boolean success = false;
            try {
                success = data.getBoolean("success");
                if (success) {
                    String token = data.getString("token");
                    Preferences.saveUserPreference( Preferences.USER_TOKEN, token);
                    Preferences.saveUserPreference( Preferences.USER_EMAIL, userEmail);
                    Preferences.saveUserPreference( Preferences.USER_PASSWORD, password);

                    Preferences.saveBooleanPreference(Preferences.ALREADY_LOGGED_IN, true);

                }else {
                    String error = data.getString("message");
                    setInputError(error);
                    return;
                }
            } catch (Exception e) {
                Log.w(TAG, e.getMessage(), e);
            } finally {
                setResult(success);
            }
        }else {
            setInputError((String) response.getError());
        }
    }

    private void setResult(boolean success){
        Intent resultData = new Intent();
        resultData.putExtra("success", success);
        setResult(RESULT_OK, resultData);
        finish();
    }
    private void setInputError(String error) {
        textUserInformer.setVisibility(View.VISIBLE);
        textUserInformer.setError(" ");
        textUserInformer.setText(error);
    }

    private void setInputSuccess(String message) {
        textUserInformer.setVisibility(View.VISIBLE);
        textUserInformer.setError(null);
        textUserInformer.setText(message);
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        textUserEmail.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REGISTER_CODE && resultCode == RESULT_OK) {
            boolean success = data.getBooleanExtra("success", false);
            if (success) {
                setInputSuccess(getString(R.string.register_succes));
//                String message = data.getStringExtra("message");
//                setInputSuccess(message);
            }
        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    class FetchEmailTask extends Task<Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> emailAddressCollection = new ArrayList<>();

            // Get all emails from the user's contacts and copy them to a list.
            ContentResolver cr = getContentResolver();
            Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.Contacts.Data.IS_PRIMARY + " DESC", null, null);
            while (emailCur.moveToNext()) {
                String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract
                        .CommonDataKinds.Email.DATA));
                emailAddressCollection.add(email);
            }
            emailCur.close();

            return emailAddressCollection;
        }

        @Override
        protected void onResult(List<String> emails) {
            addEmailsToAutoComplete(emails);
        }
    }
}
