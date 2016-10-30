package com.tukkeendoo.app.views.main.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.views.base.BasePage;

/**
 * Created by fallou on 23/10/2016.
 */

public class MessagePage extends BasePage {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.page_message, container, false);

        return  rootView;
    }

    @Override
    public CharSequence getTitle() {
        return getString(R.string.my_messages);
    }
}
