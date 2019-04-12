package com.example.hp.nevogas.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.adapters.AgencyAdapter;
import com.example.hp.nevogas.adapters.StationAdapter;
import com.example.hp.nevogas.api.ApiClient;
import com.example.hp.nevogas.model.ShowOrderGas;
import com.example.hp.nevogas.model.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrder extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ShowOrderGas> showOrderGases;
    private AgencyAdapter adapter;
    int agency_id;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (getIntent().getExtras() != null) {
            mProgress = new ProgressDialog(this);
            String title = "Searching";
            mProgress.setTitle(title);
            mProgress.setMessage("Please wait while we get all your orders");
            mProgress.show();
            agency_id = getIntent().getExtras().getInt("agency_id");
            fetchorder(agency_id);
        }
    }

    public void fetchorder(int agency_id) {
        Call<List<ShowOrderGas>> call = ApiClient.getInstance().getApi().ShowOrder(
                agency_id
        );

        call.enqueue(new Callback<List<ShowOrderGas>>() {
            @Override
            public void onResponse(Call<List<ShowOrderGas>> call, Response<List<ShowOrderGas>> response) {
                mProgress.dismiss();
                showOrderGases = response.body();
                showOrderGases.size();
                adapter = new AgencyAdapter(showOrderGases, ViewOrder.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ShowOrderGas>> call, Throwable t) {
                mProgress.dismiss();
                Toast.makeText(ViewOrder.this, "Order not successfully fetched due to " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
