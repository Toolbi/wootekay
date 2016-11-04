package com.tukkeendoo.app.views.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.network.HTTPRequestListener;
import com.tukkeendoo.app.network.HTTPResponse;

/**
 * Created by fallou on 28/04/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements HTTPRequestListener {

    protected AlertDialog networkTaskDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkTaskDialog = createDialog(this);
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

    private AlertDialog createDialog(Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(R.layout.dialog_network_task);
        alertDialogBuilder.setMessage(R.string.loading_please_wait);
        alertDialogBuilder.setCancelable(false);

        return alertDialogBuilder.create();
    }
}
