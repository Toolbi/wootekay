package com.tukkeendoo.app.views.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tukkeendoo.app.network.HTTPRequestListener;
import com.tukkeendoo.app.network.HTTPResponse;

/**
 * Created by fallou on 16/06/2016.
 */
public abstract class BasePage implements HTTPRequestListener {
    private View view;
    private Context context;

    /**
     * Called by the pagerAdapter to instantiate de the view of this page
     *
     * @param inflater
     * @param container the container of this page
     * @return View
     */
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container);

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Nullable
    protected View findViewById(@IdRes int id){
        if (view == null)
            throw new NullPointerException(getClass() + ".view is null");
        return view.findViewById(id);
    }

    @Override
    public void onHTTPBegin() {}

    @Override
    public void onHTTPProgress(Integer... values) {}

    @Override
    public void onHTTPResponse(HTTPResponse response) {}

    public abstract CharSequence getTitle();
}

