package com.tukkeendoo.app.views.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tukkeendoo.app.application.Tukkeendoo;
import com.tukkeendoo.app.network.HTTPRequestListener;
import com.tukkeendoo.app.network.HTTPResponse;

/**
 * Created by fallou on 16/06/2016.
 */
public abstract class BasePage implements HTTPRequestListener {
    private View view;
    private BaseActivity activity;

    /**
     * Called by the pagerAdapter to instantiate de the view of this page
     *
     * @param inflater
     * @param container the container of this page
     * @return View
     */
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container);

    /**
     * Called by the {@link android.support.v4.view.ViewPager} to get the title of {@link android.support.design.widget.TabLayout}
     * @return
     */
    public abstract CharSequence getTitle();

    public View getView() {
        return view;
    }

    public void setView(View view) {
        if (view == null) {
            throw new NullPointerException(getClass() + "view is null");
        }
        this.view = view;
    }

    @Nullable
    protected View findViewById(@IdRes int id){
        if (view == null) {
            throw new NullPointerException(getClass() + "view is null");
        }
        return view.findViewById(id);
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public Context getContext() {
        if (view == null) {
            return Tukkeendoo.getInstance();
        }
        return activity;
    }

    public BaseActivity getActivity() {
        return activity;
    }

    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }

    public String getString(@StringRes int resId){
        return Tukkeendoo.getInstance().getString(resId);
    }

    @Override
    public void onHTTPBegin() {}

    @Override
    public void onHTTPProgress(Integer... values) {}

    @Override
    public void onHTTPResponse(HTTPResponse response) {}


}

