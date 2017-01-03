package com.tukkeendoo.app.models;

import android.content.ContentValues;
import android.util.Log;

import com.google.gson.Gson;
import com.tukkeendoo.app.config.TukkeeConfig;
import com.tukkeendoo.app.database.dao.UserDAO;
import com.tukkeendoo.app.database.dao.VehicleDAO;
import com.tukkeendoo.app.network.HTTPRequest;
import com.tukkeendoo.app.network.HTTPRequestListener;
import com.tukkeendoo.app.network.HTTPResponse;
import com.tukkeendoo.app.network.Webservice;
import com.tukkeendoo.app.utils.thread_manager.Task;
import com.tukkeendoo.app.utils.thread_manager.TaskManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fallou on 23/12/2016.
 */

public class Profile extends Model {

    public static void downloadUserData(){
        HTTPRequestListener listener = new HTTPRequestListener() {
            @Override
            public void onHTTPBegin() {}

            @Override
            public void onHTTPProgress(Integer... values) {}

            @Override
            public void onHTTPResponse(HTTPResponse response) {
                try {
                    JSONObject userData = ((JSONObject) response.getData()).getJSONObject("user");
                    JSONArray userVehicles = userData.getJSONArray("user_vehicles");
                    JSONObject userProfile = userData.getJSONObject("user_profile");
                    saveUser(userProfile);
                    saveVehicles(userVehicles);
                } catch (Exception e) {
                    Log.e(Profile.class.getSimpleName(), e.getMessage(), e);
                }
            }
        };

        HTTPRequest request = new HTTPRequest(TukkeeConfig.PROFILE, HTTPRequest.GET, null);
        Webservice.executeRequestWithListener(request, listener);
    }

    private static void saveUser(final JSONObject userProfile){
        TaskManager.execute(new Task() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Gson gson = new Gson();
                User user = gson.fromJson(userProfile.toString(), User.class);
                UserDAO.save(user);
                return null;
            }
        });
    }

    private static void saveVehicles(final JSONArray jsonArray) throws JSONException {
        final Gson gson = new Gson();

        TaskManager.execute(new Task() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject vehicleJSONObject = jsonArray.getJSONObject(i);
                        Vehicle vehicle = gson.fromJson(vehicleJSONObject.toString(), Vehicle.class);
                        VehicleDAO.save(vehicle);
                    }
                } catch (Exception e) {
                    Log.e(Profile.class.getSimpleName(), e.getMessage(), e);
                }
                return null;
            }
        });

    }


    @Override
    public ContentValues getContentValues() {
        Log.e(this.getClass().getSimpleName(), "", new IllegalAccessException("Unimplemented method"));
        return null;
    }
}
