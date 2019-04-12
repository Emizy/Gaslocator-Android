package com.example.hp.nevogas.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.model.Agency;
import com.example.hp.nevogas.model.LatShow;
import com.example.hp.nevogas.storage.SharedPrefManager;

public class DetailedActivity extends AppCompatActivity {
    String name, address, phone, qty, userdate;
    int price;
    TextView username, userphone, gas_qty, useraddress, gasprice, date;
    double user_latitude, user_longitude;
    String user_address;
    LatShow latShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        username = (TextView) findViewById(R.id.username);
        userphone = (TextView) findViewById(R.id.userphone);
        useraddress = (TextView) findViewById(R.id.useraddress);
        gas_qty = (TextView) findViewById(R.id.quantity);
        gasprice = (TextView) findViewById(R.id.price);
        date = (TextView) findViewById(R.id.date);
        if (getIntent().hasExtra("Name") && getIntent().hasExtra("Date") && getIntent().hasExtra("Address") && getIntent().hasExtra("Phone") && getIntent().hasExtra("Quantity")
                && getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")) {

            name = getIntent().getExtras().getString("Name");
            address = getIntent().getExtras().getString("Address");
            phone = getIntent().getExtras().getString("Phone");
            qty = getIntent().getExtras().getString("Quantity");
            price = getIntent().getExtras().getInt("Price");
            userdate = getIntent().getExtras().getString("Date");
            username.setText("Name: " + name);
            useraddress.setText("Address: " + address);
            userphone.setText("Phone: " + phone);
            gas_qty.setText("Quantity: " + qty);
            gasprice.setText("Price: " + String.valueOf(price));
            date.setText("Date: " + userdate);

            user_latitude = getIntent().getDoubleExtra("latitude", 00);
            user_longitude = getIntent().getDoubleExtra("longitude", 00);
            user_address = getIntent().getExtras().getString("Address");

            latShow = new LatShow(user_latitude, user_longitude);


        } else {
            Toast.makeText(this, "Missing parameter", Toast.LENGTH_LONG).show();
        }
    }

    public void ViewOnMap(View view) {
        Intent intent = new Intent(this, GasMapActivity.class);
        intent.putExtra("user_latitude", latShow.getLatitude());
        intent.putExtra("user_longitude", latShow.getLongitude());
        intent.putExtra("user_address", user_address);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, AgencyLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
