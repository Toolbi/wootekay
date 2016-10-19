package com.tukkeendoo.app.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by fallou on 12/06/2016.
 */
public abstract class TextChangeListener implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
