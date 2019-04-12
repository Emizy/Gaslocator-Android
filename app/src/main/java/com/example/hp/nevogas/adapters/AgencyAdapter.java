package com.example.hp.nevogas.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.activities.DetailedActivity;
import com.example.hp.nevogas.activities.OrderGasActivity;
import com.example.hp.nevogas.interfaces.ItemClickListener;
import com.example.hp.nevogas.model.ShowOrderGas;

import java.util.List;

/**
 * Created by HP on 3/25/2019.
 */

public class AgencyAdapter extends RecyclerView.Adapter<AgencyAdapter.MyAgencyViewHolder> {

    private List<ShowOrderGas> showOrderGases;
    private Context context;

    public AgencyAdapter(List<ShowOrderGas> showOrderGases, Context context) {
        this.showOrderGases = showOrderGases;
        this.context = context;
    }

    @Override
    public MyAgencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.agency_view, parent, false);
        return new MyAgencyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyAgencyViewHolder holder, int position) {
        holder.name.setText("Name: " + showOrderGases.get(position).getUser_name());
        holder.address.setText("Address: " + showOrderGases.get(position).getAddress());
        holder.phone.setText("Phone: " + showOrderGases.get(position).getPhone());
        holder.date.setText("Date: " + showOrderGases.get(position).getCreated_at());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {

                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("Name", showOrderGases.get(pos).getUser_name());
                intent.putExtra("Address", showOrderGases.get(pos).getAddress());
                intent.putExtra("agency_address",showOrderGases.get(pos).getAgency_address());
                intent.putExtra("Phone", showOrderGases.get(pos).getPhone());
                intent.putExtra("Quantity", showOrderGases.get(pos).getQty());
                intent.putExtra("Price", showOrderGases.get(pos).getPrice());
                intent.putExtra("Date", showOrderGases.get(pos).getCreated_at());
                intent.putExtra("latitude", showOrderGases.get(pos).getLatitude());
                intent.putExtra("longitude", showOrderGases.get(pos).getLongitude());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return showOrderGases.size();
    }

    public static class MyAgencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, address, phone, date;
        ItemClickListener itemClickListener;

        public MyAgencyViewHolder(View itemView) {

            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            phone = (TextView) itemView.findViewById(R.id.phone);
            date = (TextView) itemView.findViewById(R.id.date);
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
