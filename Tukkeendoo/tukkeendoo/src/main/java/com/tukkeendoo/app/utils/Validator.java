package com.tukkeendoo.app.utils;

import android.support.annotation.StringRes;
import android.text.InputType;
import android.widget.EditText;

import com.tukkeendoo.app.application.Tukkeendoo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fallou on 04/06/2016.
 */
public class Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private static Matcher matcher;

    /**
     * Validate hex with regular expression
     *
     * @param email
     *            email for validation
     * @return true valid email, false invalid email
     */
    public static boolean check(final String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean verify(String password){
        return password.length() > 5;
    }

    public static boolean validate(EditText editText, @StringRes int error){
        String text = editText.getText().toString();
        boolean success = false;
        switch (editText.getInputType()){

            case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
                success = check(text);
                break;

            case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD:
                success = verify(text);
                break;

            case InputType.TYPE_CLASS_PHONE:
                success = verify(text);
                break;

            default:
                success = !text.isEmpty();
                break;
        }

        if (!success){
            String errorString = Tukkeendoo.getInstance().getString(error);
            editText.setError(errorString);
        }
        return success;
    }
}
