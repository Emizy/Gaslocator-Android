package com.example.hp.nevogas.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.model.Agency;
import com.example.hp.nevogas.model.User;
import com.example.hp.nevogas.storage.AgencySharedPrefManager;
import com.example.hp.nevogas.storage.SharedPrefManager;

public class AgencyDashboard extends AppCompatActivity implements View.OnClickListener {
    ImageView profile;
    TextView welcome, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_dashboard);

        welcome = (TextView) findViewById(R.id.welcome);
        status = (TextView) findViewById(R.id.agency_status);
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.profile).setOnClickListener(this);
        findViewById(R.id.contactus).setOnClickListener(this);
        findViewById(R.id.order).setOnClickListener(this);
        findViewById(R.id.history).setOnClickListener(this);
        Agency agency = SharedPrefManager.getInstance(this).getAgency();
        welcome.setText("welcome" + agency.getBusiness_name());
        status.setText("Account Status: " + agency.getAccount_status());
    }


    public void Profile() {
        Intent intent = new Intent(this, AgencyProfile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void logout() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, AgencyLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void AgencyOrder() {
        Agency agency = SharedPrefManager.getInstance(this).getAgency();
        int id;
        id = agency.getUser_id();
        Intent intent = new Intent(this, ViewOrder.class);
        intent.putExtra("agency_id", id);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                logout();
                break;
            case R.id.profile:
                Profile();
                break;
            case R.id.contactus:
                Toast.makeText(this, "AM THE CONTACT US BUTTON", Toast.LENGTH_LONG).show();
                break;
            case R.id.history:
                Toast.makeText(this, "AM THE HISTORY BUTTON", Toast.LENGTH_LONG).show();
                break;
            case R.id.order:
                AgencyOrder();
                break;
        }
    }
}
