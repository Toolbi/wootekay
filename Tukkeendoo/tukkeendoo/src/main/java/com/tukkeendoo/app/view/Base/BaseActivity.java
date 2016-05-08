package com.tukkeendoo.app.view.Base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.network.Webservice;

/**
 * Created by fallou on 28/04/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements Webservice.WebServiceListener{

    protected Dialog networkTaskDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkTaskDialog = createDialog(this, R.string.app_name);
    }

    @Override
    public void onHTTPBegin() {
        networkTaskDialog.show();
    }

    @Override
    public void onHTTPProgress(Integer... values) {
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        networkTaskDialog.dismiss();
    }

    private Dialog createDialog(Context context, @StringRes int titleId){
        Dialog dialog = new Dialog(context, R.style.AppTheme);
        dialog.setContentView(R.layout.dialog_network_task);
        dialog.setTitle(titleId);
        return dialog;
    }
}
