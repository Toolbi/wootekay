package com.tukkeendoo.app.views.main.vehicle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.database.dao.VehicleDAO;
import com.tukkeendoo.app.models.Vehicle;
import com.tukkeendoo.app.views.base.BasePage;

import java.util.List;

/**
 * Created by fallou on 23/10/2016.
 */

public class VehiclePage extends BasePage {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.page_vehicle, container, false);

        List<Vehicle> vehicles = VehicleDAO.getVehicles();

        VehicleAdapter adapter = new VehicleAdapter(getContext(), R.layout.vehicle_item, vehicles);
        ListView vehiclesList = (ListView)rootView.findViewById(R.id.vehicles_list);
        vehiclesList.setAdapter(adapter);

        return  rootView;
    }

    @Override
    public CharSequence getTitle() {
        return getString(R.string.my_vehicles);
    }
}
