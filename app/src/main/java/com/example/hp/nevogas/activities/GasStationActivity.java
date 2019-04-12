package com.example.hp.nevogas.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.adapters.StationAdapter;
import com.example.hp.nevogas.api.ApiClient;
import com.example.hp.nevogas.model.Station;
import com.example.hp.nevogas.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GasStationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Station> stations;
    private StationAdapter adapter;
    int id;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_station);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (getIntent().getExtras() != null) {
            mProgress = new ProgressDialog(this);
            String title = "Searching";
            mProgress.setTitle(title);
            mProgress.setMessage("Please wait while we get the nearest gas station for you");
            mProgress.show();
            id = getIntent().getExtras().getInt("id");
            fetchstation(id);
        }

    }

    public void fetchstation(int id) {
        Call<List<Station>> call = ApiClient.getInstance().getApi().SearchGas(
                id
        );

        call.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                mProgress.dismiss();
                try{
                    stations = response.body();
                    adapter = new StationAdapter(stations, GasStationActivity.this);
                    recyclerView.setAdapter(adapter);
                }catch (Exception e){
                    Toast.makeText(GasStationActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                mProgress.dismiss();
                Toast.makeText(GasStationActivity.this, "Nearest station not successfully fetched due to "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, UserLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
