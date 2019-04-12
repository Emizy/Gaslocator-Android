package com.example.hp.nevogas.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.adapters.HistoryAdapter;
import com.example.hp.nevogas.adapters.StationAdapter;
import com.example.hp.nevogas.api.ApiClient;
import com.example.hp.nevogas.model.ShowOrderGas;
import com.example.hp.nevogas.model.Station;
import com.example.hp.nevogas.model.User;
import com.example.hp.nevogas.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ShowOrderGas> userhistory;
    private HistoryAdapter adapter;
    int user_id;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview3);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mProgress = new ProgressDialog(this);
        String title = "Fetching";
        mProgress.setTitle(title);
        mProgress.setMessage("Please wait while we all your history");
        mProgress.show();
        User user = SharedPrefManager.getInstance(this).getUser();
        user_id = user.getId();
        fetchhistory(user_id);
    }

    public void fetchhistory(int user_id) {

        Call<List<ShowOrderGas>> call = ApiClient.getInstance().getApi().UserHistory(
                user_id
        );

        call.enqueue(new Callback<List<ShowOrderGas>>() {
            @Override
            public void onResponse(Call<List<ShowOrderGas>> call, Response<List<ShowOrderGas>> response) {
                mProgress.dismiss();
                try{
                    userhistory = response.body();
                    adapter = new HistoryAdapter(userhistory, UserHistoryActivity.this);
                    recyclerView.setAdapter(adapter);
                }
                catch (Exception e){
                    Toast.makeText(UserHistoryActivity.this,"You are yet to make an Order",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<ShowOrderGas>> call, Throwable t) {
                mProgress.dismiss();
                Toast.makeText(UserHistoryActivity.this, "Nearest station not successfully fetched due to " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}
