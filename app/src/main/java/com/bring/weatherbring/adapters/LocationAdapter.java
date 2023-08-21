package com.bring.weatherbring.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bring.weatherbring.R;
import com.bring.weatherbring.model.Location;
import com.bring.weatherbring.util.SharedPreferencesManager;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private List<Location> locationList;

    public LocationAdapter(List<Location> locationList) {
        this.locationList = locationList;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        Location location = locationList.get(position);

        if(location.getCityName()!=null) {
            holder.TVcityName.setText(location.getCityName());
        }else{
            holder.TVcityName.setText(location.getLatitude()+" : "+location.getLongitude());
        }
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager sp = new SharedPreferencesManager(v.getContext());
                sp.deleteLocation(location.getLatitude()+":"+location.getLongitude());
                locationList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

            }
        });
    }
    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView TVcityName;
        ImageView ivDelete;

        public LocationViewHolder(View itemView) {
            super(itemView);
            TVcityName = itemView.findViewById(R.id.TVcityName);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }

}