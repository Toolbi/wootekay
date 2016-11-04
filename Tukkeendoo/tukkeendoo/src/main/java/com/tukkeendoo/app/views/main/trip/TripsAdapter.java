package com.tukkeendoo.app.views.main.trip;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.models.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fallou on 21/05/2016.
 */
public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripHolder> {

    private List<Trip> trips;
    private Context context;

    public TripsAdapter(Context context) {
        this.context = context;
        trips = new ArrayList<>();
    }

    @Override
    public TripHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trip_item, parent, false);
        TripHolder holder = new TripHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(TripHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }


    class TripHolder extends RecyclerView.ViewHolder{

        public TripHolder(View itemView) {
            super(itemView);
        }
    }
}
