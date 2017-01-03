package com.tukkeendoo.app.views.base;

import android.support.v4.app.Fragment;

import com.tukkeendoo.app.network.HTTPRequestListener;
import com.tukkeendoo.app.network.HTTPResponse;

/**
 * Created by fallou on 14/06/2016.
 */
public abstract class BaseFragment extends Fragment implements HTTPRequestListener{
    @Override
    public void onHTTPBegin() {

    }

    @Override
    public void onHTTPProgress(Integer... values) {

    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {

    }
}
