package com.tukkeendoo.app.views.main.vehicle;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.models.Vehicle;

import java.util.List;

/**
 * Created by fallou on 23/10/2016.
 */

public class VehicleAdapter extends ArrayAdapter<Vehicle> {
    @LayoutRes private int resource;
    public VehicleAdapter(Context context, @LayoutRes int resource, List<Vehicle> vehicles) {
        super(context, resource, vehicles);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        TextView vehicleBrand = (TextView) convertView.findViewById(R.id.vehicle_category_name);
        TextView vehicleName = (TextView) convertView.findViewById(R.id.vehicle_name);
        TextView vehicleNumber = (TextView) convertView.findViewById(R.id.vehicle_number);

        Vehicle vehicle = getItem(position);
        vehicleBrand.setText(vehicle.getCategoryName());
        vehicleName.setText(vehicle.getTypeName());
        vehicleNumber.setText(vehicle.getNumber());

        return convertView;
    }
}
