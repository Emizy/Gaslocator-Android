package com.example.hp.nevogas.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.activities.DetailedActivity;
import com.example.hp.nevogas.activities.MapActivity;
import com.example.hp.nevogas.activities.OrderGasActivity;
import com.example.hp.nevogas.interfaces.ItemClickListener;
import com.example.hp.nevogas.model.Station;

import java.util.List;

/**
 * Created by HP on 3/19/2019.
 */

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.MyViewHolder> {

    private List<Station> stationList;
    private Context context;

    public StationAdapter(List<Station> stationList, Context context) {
        this.stationList = stationList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_layout, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.business_name.setText("Station: " + stationList.get(position).getBusiness_name());
        holder.address.setText("Address: " + stationList.get(position).getAddress());
        holder.phone.setText("Phone: " + stationList.get(position).getPhone());
        holder.id.setText(Integer.toString(stationList.get(position).getId()));
        Double dist = stationList.get(position).getDistance();
        String h = String.format("%.2f", dist);

        holder.distance.setText("Distance: " + h + "km");
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {

                Intent intent = new Intent(context, OrderGasActivity.class);
                intent.putExtra("latitude", stationList.get(pos).getLatitude());
                intent.putExtra("longitude", stationList.get(pos).getLongitude());
                intent.putExtra("business_name", stationList.get(pos).getBusiness_name());
                intent.putExtra("address", stationList.get(pos).getAddress());
                intent.putExtra("phone", stationList.get(pos).getPhone());
                intent.putExtra("state", stationList.get(pos).getState());
                intent.putExtra("id", stationList.get(pos).getId());
                intent.putExtra("distance", stationList.get(pos).getDistance());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return stationList.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView business_name, address, phone, id, distance;
        ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            business_name = (TextView) itemView.findViewById(R.id.business_name);
            address = (TextView) itemView.findViewById(R.id.address);
            phone = (TextView) itemView.findViewById(R.id.phone);
            id = (TextView) itemView.findViewById(R.id.id);
            distance = (TextView) itemView.findViewById(R.id.distance);
            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }


}

