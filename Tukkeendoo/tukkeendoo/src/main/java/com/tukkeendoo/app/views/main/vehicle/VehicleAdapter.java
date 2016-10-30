package com.tukkeendoo.app.views.main.vehicle;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.tukkeendoo.app.models.Vehicle;

import java.util.List;

/**
 * Created by fallou on 23/10/2016.
 */

public class VehicleAdapter extends ArrayAdapter<Vehicle> {
    public VehicleAdapter(Context context, int resource, List<Vehicle> vehicles) {
        super(context, resource, vehicles);
    }
}
