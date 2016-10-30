package com.tukkeendoo.app.views.main.message;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.tukkeendoo.app.models.Vehicle;

import java.util.List;

/**
 * Created by fallou on 23/10/2016.
 */

public class MessageAdapter extends ArrayAdapter<Vehicle> {
    public MessageAdapter(Context context, int resource, List<Vehicle> vehicles) {
        super(context, resource, vehicles);
    }
}
