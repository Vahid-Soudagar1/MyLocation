package com.example.mylocation.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylocation.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class LocationHistoryAdapter extends RecyclerView.Adapter<LocationHistoryAdapter.ViewHolder>{

    Context context;
    ArrayList<LocationModel> locationModels;

    public LocationHistoryAdapter(Context context, ArrayList<LocationModel> locationModels) {
        this.context = context;
        this.locationModels = locationModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_location_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationModel locationModel = locationModels.get(position);

        holder.latitude.setText("Latitude: "+locationModel.getLatitude());
        holder.longitude.setText("Longitude: "+locationModel.getLongitude());
        holder.addressLine.setText(locationModel.addressLine);
        holder.date.setText(locationModel.getDate());
        holder.time.setText(locationModel.getTime());
    }

    @Override
    public int getItemCount() {
        return locationModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView latitude, longitude, addressLine, date, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            latitude = itemView.findViewById(R.id.latitude_textview);
            longitude = itemView.findViewById(R.id.longitude_textview);
            addressLine = itemView.findViewById(R.id.address_line);
            date = itemView.findViewById(R.id.date_textview);
            time = itemView.findViewById(R.id.time_textview);

        }
    }
}
