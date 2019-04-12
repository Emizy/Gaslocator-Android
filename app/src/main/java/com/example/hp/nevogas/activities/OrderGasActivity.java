package com.example.hp.nevogas.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.api.ApiClient;
import com.example.hp.nevogas.model.LatShow;
import com.example.hp.nevogas.model.User;
import com.example.hp.nevogas.model.UserOrderResponse;
import com.example.hp.nevogas.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderGasActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "OrderGasActivity";
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    double agency_longitude, agency_latitude;
    String user_longitude, user_latitude;
    String business, agency_address, agency_phone, agency_state;
    int id;
    TextView name, phone, state, address, view_gas, back;
    String qty = "";
    String price = "";
    ProgressDialog mProgress;
    LatShow latShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_gas);
        spinner = (Spinner) findViewById(R.id.spin);

        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        state = (TextView) findViewById(R.id.state);
        address = (TextView) findViewById(R.id.address);
        view_gas = (TextView) findViewById(R.id.view_gas);
        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(this);
        view_gas.setOnClickListener(this);
        adapter = ArrayAdapter.createFromResource(this, R.array.price, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("1kg 350")) {
                        qty = "1kg";
                        price = "350";
                    } else if (selection.equals("2kg 700")) {
                        qty = "2kg";
                        price = "700";
                    } else if (selection.equals("3kg 1050")) {
                        qty = "3kg";
                        price = "1050";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude") && getIntent().hasExtra("business_name") && getIntent().hasExtra("id")) {
            Log.d(TAG, "getIncomingIntent: Extras found successfully");
            business = getIntent().getStringExtra("business_name");
            agency_longitude = getIntent().getDoubleExtra("longitude", 00);
            agency_latitude = getIntent().getDoubleExtra("latitude", 00);
            id = getIntent().getIntExtra("id", -1);
            agency_address = getIntent().getStringExtra("address");
            agency_phone = getIntent().getStringExtra("phone");
            agency_state = getIntent().getStringExtra("state");
            name.setText(business);
            address.setText(agency_address);
            phone.setText(agency_phone);
            state.setText(agency_state);
            latShow = new LatShow(agency_latitude, agency_longitude);

        }
    }

    public void ViewMap() {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("agency_latitude", latShow.getLatitude());
        intent.putExtra("agency_longitude", latShow.getLongitude());
        intent.putExtra("agency_address", agency_address);

        startActivity(intent);


    }


    public void OrderGas(View view) {


        User user = SharedPrefManager.getInstance(this).getUser();
        String user_id = String.valueOf(user.getId());
        String agency_id = String.valueOf(getIntent().getIntExtra("id", -1));
        SubmitOrder(qty, price, user_id, agency_id);

    }

    public void SubmitOrder(String qty, String price, String user_id, String agency_id) {
        mProgress = new ProgressDialog(this);
        String title = "Searching";
        mProgress.setTitle(title);
        mProgress.setMessage("Please wait while we get the nearest gas station for you");
        mProgress.show();
        Toast.makeText(OrderGasActivity.this, price + qty + user_id + agency_id, Toast.LENGTH_LONG).show();

        Call<UserOrderResponse> call = ApiClient.getInstance().getApi().UserOrder(
                qty, price, user_id, agency_id
        );

        call.enqueue(new Callback<UserOrderResponse>() {
            @Override
            public void onResponse(Call<UserOrderResponse> call, Response<UserOrderResponse> response) {
                mProgress.dismiss();
                UserOrderResponse userresponse = response.body();
                Toast.makeText(OrderGasActivity.this, "Order successfully Placed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<UserOrderResponse> call, Throwable t) {
                Toast.makeText(OrderGasActivity.this, "Order not-successfully Placed due to " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(OrderGasActivity.this, GasStationActivity.class);
                startActivity(intent);
                break;
            case R.id.view_gas:
                ViewMap();
                break;
        }
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
