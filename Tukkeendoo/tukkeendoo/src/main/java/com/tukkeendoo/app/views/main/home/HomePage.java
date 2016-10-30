package com.tukkeendoo.app.views.main.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.tukkeendoo.app.R;
import com.tukkeendoo.app.config.TukkeeConfig;
import com.tukkeendoo.app.network.HTTPRequest;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.views.base.BasePage;

/**
 * Created by fallou on 15/06/2016.
 */
public class HomePage extends BasePage implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String LOG_TAG = HomePage.class.getSimpleName();
    private SearchView fromSearchView;
    private SearchView toSearchView;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.page_home, container, false);

        HTTPRequest request = new HTTPRequest(TukkeeConfig.PROFILE, HTTPRequest.POST, null);
//        Webservice.executeRequestWithListener(request, this);

        return rootView;
    }

    private void initGoogleAPIClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onHTTPResponse(HTTPResponse response) {
        if (response.isOk()) {
            TextView welcome = (TextView) findViewById(R.id.text_welcome);
            welcome.setText(response.getData().toString());
        }
    }

    @Override
    public CharSequence getTitle() {
        return getString(R.string.home);
    }


    public void onStart() {
        initGoogleAPIClient();
        googleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    /**  Google API client listener methods  */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);

        Log.d(LOG_TAG, "location=" + lastLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
