package com.tukkeendoo.app.views.main.trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.views.base.BasePage;

/**
 * Created by fallou on 15/06/2016.
 */
public class TripPage extends BasePage {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.page_trip, container, false);

        return  rootView;
    }
}
