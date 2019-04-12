package com.example.hp.nevogas.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.model.User;
import com.example.hp.nevogas.storage.SharedPrefManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class UserDashboard extends AppCompatActivity implements View.OnClickListener {
    ImageView profile, gasstation, history, contact;
    TextView welcome, status, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        User user = SharedPrefManager.getInstance(this).getUser();
        welcome = (TextView) findViewById(R.id.welcome);
        status = (TextView) findViewById(R.id.user_status);
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.profile).setOnClickListener(this);
        findViewById(R.id.contactus).setOnClickListener(this);
        findViewById(R.id.history).setOnClickListener(this);
        welcome.setText("Welcome " + user.getName());
        status.setText("Account: " + user.getUser_status());


    }

    public void logout() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void Profile() {
        Intent intent = new Intent(this, UserProfile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

    /* code to get nearest gas stations*/
    public void getStations(View view){
        User user = SharedPrefManager.getInstance(this).getUser();
        int id;
        id = user.getId();
        Intent intent = new Intent(this,GasStationActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }


    public void  ViewHistory(){
        Intent intent = new Intent(this,UserHistoryActivity.class);
        startActivity(intent);
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
               ViewHistory();
                break;

        }
    }
}
