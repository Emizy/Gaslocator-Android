package com.example.hp.nevogas.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hp.nevogas.R;

public class UserHistoryDetailActivity extends AppCompatActivity {
    TextView business_name, agency_phone, agency_address, price, quantity, order_id, gas_id, date;
    String name, address, phone, qty, userdate, gas_order_date;
    int gas_order_id, gas_gas_id,price_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_detail);

        business_name = (TextView) findViewById(R.id.business_name);
        agency_phone = (TextView) findViewById(R.id.agencyphone);
        agency_address = (TextView) findViewById(R.id.agency_address);
        price = (TextView) findViewById(R.id.price);
        quantity = (TextView) findViewById(R.id.quantity);
        order_id = (TextView) findViewById(R.id.order_id);
        gas_id = (TextView) findViewById(R.id.gas_id);
        date = (TextView) findViewById(R.id.date);

        if (getIntent().getExtras() != null) {
            name = getIntent().getStringExtra("Name");
            address = getIntent().getStringExtra("Address");
            phone = getIntent().getStringExtra("Phone");
            qty = getIntent().getStringExtra("Quantity");
            price_name = getIntent().getIntExtra("Price",-1);
            userdate = getIntent().getStringExtra("Date");
            gas_order_id = getIntent().getIntExtra("Order_Id", -1);
            gas_gas_id = getIntent().getIntExtra("Gas_Id", -1);

            business_name.setText("Station: " + name);
            agency_address.setText("Address: " + address);
            price.setText("Price: " + price_name);
            agency_phone.setText("Phone: " + phone);
            quantity.setText("Quantity: " + qty);
            order_id.setText("Order_Id: " + gas_order_id);
            gas_id.setText("Gas Id " + gas_gas_id);
            date.setText("Date: " + userdate);

        }
    }
}
