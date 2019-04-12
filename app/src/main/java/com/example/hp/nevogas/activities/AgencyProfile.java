package com.example.hp.nevogas.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.api.ApiClient;
import com.example.hp.nevogas.model.Agency;
import com.example.hp.nevogas.model.AgencyLoginResponse;
import com.example.hp.nevogas.model.DefaultResponse;
import com.example.hp.nevogas.model.Locate;
import com.example.hp.nevogas.model.User;
import com.example.hp.nevogas.storage.AgencySharedPrefManager;
import com.example.hp.nevogas.storage.SharedPrefManager;
import com.google.android.gms.instantapps.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class AgencyProfile extends AppCompatActivity implements View.OnClickListener, LocationListener {
    Dialog dialog;
    TextView business, email, phone, state, address_1, latitude, longitude, status, about_us;
    Button submit_location;
    ProgressDialog mProgress;

    public Locate locate;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    public String user_latitude, user_longitude;
    protected LocationManager locationManager;
    protected Context context;
    protected boolean gps_enabled, network_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_profile);
        dialog = new Dialog(this);
        requestPermission();

        Agency agency = SharedPrefManager.getInstance(this).getAgency();
        business = (TextView) findViewById(R.id.business_name);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        state = (TextView) findViewById(R.id.state);
        address_1 = (TextView) findViewById(R.id.address);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        status = (TextView) findViewById(R.id.account_status);
        about_us = (TextView) findViewById(R.id.about);

        findViewById(R.id.back).setOnClickListener(this);
        business.setText(agency.getBusiness_name());
        email.setText(agency.getEmail());
        phone.setText(agency.getPhone());
        state.setText(agency.getState());
        if (agency.getAddress() == null) {
            address_1.setText("Update your address");
            latitude.setText("lat: Nill");
            longitude.setText("long: Nill");

        } else {
            address_1.setText(agency.getAddress());
            latitude.setText("lat: " + agency.getLatitude());
            longitude.setText("long: " + agency.getLongitude());
        }

        status.setText(agency.getAccount_status());
        about_us.setText(agency.getAbout_us());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
// getting GPS status
        gps_enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
// getting network status
        network_enabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gps_enabled) {
            if (android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        } else if (network_enabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }
        ;
    }
    /* start of dialog for uodating location */


    public void AddLocation(View v) {

        final TextView txt;
        Button btn;
        final EditText add_address;

        dialog.setContentView(R.layout.activity_add_locations);
        txt = (TextView) dialog.findViewById(R.id.close2);
        btn = (Button) dialog.findViewById(R.id.add_location);
        add_address = (EditText) dialog.findViewById(R.id.up_address);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address;
                address = add_address.getText().toString().trim();
                if (address.isEmpty()) {
                    add_address.setError("Address is required");
                    add_address.requestFocus();
                    return;
                }

                try {
                    if (String.valueOf(locate.getUser_latitude()) != null && String.valueOf(locate.getUser_longitude()) != null) {
                        Agency agency = SharedPrefManager.getInstance(AgencyProfile.this).getAgency();
                        int id;
                        id = agency.getUser_id();
                        CallAgencyLoc(id, address, String.valueOf(locate.getUser_latitude()), String.valueOf(locate.getUser_longitude()));
                    }
                } catch (Exception e) {
                    Toast.makeText(AgencyProfile.this, "Wait for device to detect your location", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        });

        dialog.show();

    }

    /* end of dialog for uodating location */


                            /*  end of cal the response for enqueue*/

    public void CallAgencyLoc(int id, String address, String latitude, String longitude) {
        Call<AgencyLoginResponse> call = ApiClient
                .getInstance()
                .getApi()
                .UpdateLocation(
                        id, address, latitude, longitude
                );

                            /*  cal the response for enqueue*/
        mProgress = new ProgressDialog(AgencyProfile.this);
        String title = "Updating";
        mProgress.setTitle(title);
        mProgress.setMessage("Please wait while we update your location");
        mProgress.show();
        call.enqueue(new Callback<AgencyLoginResponse>() {
            @Override
            public void onResponse(Call<AgencyLoginResponse> call, Response<AgencyLoginResponse> response) {
                AgencyLoginResponse agencyloginResponse = response.body();
                if (response.code() == 200) {
                    if (agencyloginResponse.isSuccess()) {
                        mProgress.dismiss();
                        //Proceed with login
                        SharedPrefManager
                                .getInstance(AgencyProfile.this)
                                .saveAgency(agencyloginResponse.getData());
                        Toast.makeText(AgencyProfile.this, "Location successfully Updated", Toast.LENGTH_LONG).show();
                    } else if (!agencyloginResponse.isSuccess()) {
                        mProgress.dismiss();
                        Toast.makeText(AgencyProfile.this, "Location not updated, kindly check your data", Toast.LENGTH_LONG).show();
                    }
                } else if (response.code() == 404) {
                    mProgress.dismiss();
                    Toast.makeText(AgencyProfile.this, "Location not updated successfully", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AgencyLoginResponse> call, Throwable t) {

            }
        });

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

    public void dashboard() {
        Intent intent = new Intent(this, AgencyDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void Showp(View v) {
        TextView close2;


        dialog.setContentView(R.layout.activity_add_locations);
        close2 = (TextView) dialog.findViewById(R.id.close2);

        close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    private void requestPermission() {
        android.support.v4.app.ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                dashboard();
                break;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            user_longitude = String.valueOf(location.getLongitude());
            user_latitude = String.valueOf(location.getLatitude());
            locate = new Locate(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(AgencyProfile.this, " Kindly turn On your Location or Network", Toast.LENGTH_SHORT).show();
    }
}
