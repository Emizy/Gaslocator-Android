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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.api.ApiClient;
import com.example.hp.nevogas.model.Locate;
import com.example.hp.nevogas.model.LoginResponse;
import com.example.hp.nevogas.model.User;
import com.example.hp.nevogas.storage.SharedPrefManager;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserProfile extends AppCompatActivity implements View.OnClickListener, LocationListener {
    Dialog dialog;
    TextView name, email, account_status, phone, state, address, latitude, longitude;
    ProgressDialog mProgress;
    public String user_latitude, user_longitude;
    public Locate locate;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    protected LocationManager locationManager;
    protected Context context;
    protected boolean gps_enabled, network_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        dialog = new Dialog(this);
        findViewById(R.id.back).setOnClickListener(this);

        User user = SharedPrefManager.getInstance(this).getUser();
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        state = (TextView) findViewById(R.id.state);
        phone = (TextView) findViewById(R.id.phone);
        account_status = (TextView) findViewById(R.id.status);
        address = (TextView) findViewById(R.id.address);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);


        name.setText(user.getName());
        email.setText(user.getEmail());
        state.setText(user.getState());
        phone.setText(user.getPhone());
        if (user.getAddress() == null) {
            address.setText("Update Your Location");
            latitude.setText("lat:  Nill");
            longitude.setText("long:  Nill");
        } else {
            address.setText(user.getAddress());
            latitude.setText("lat: " + user.getLatitude());
            longitude.setText("long: " + user.getLongitude());
        }
        account_status.setText(user.getUser_status());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
// getting GPS status
        gps_enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
// getting network status
        network_enabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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


    public void SaveLocation(View v) {
        TextView txt;
        Button btn;
        dialog.setContentView(R.layout.activity_user_add_location);
        btn = (Button) dialog.findViewById(R.id.save_location);
        txt = (TextView) dialog.findViewById(R.id.closeD);

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
                EditText street;
                street = (EditText) dialog.findViewById(R.id.user_address);
                address = street.getText().toString().trim();
                if (address.isEmpty()) {
                    street.setError("Field required");
                    street.requestFocus();
                    return;
                }
                try {
                    if (String.valueOf(locate.getUser_latitude()) != null && String.valueOf(locate.getUser_longitude()) != null) {
                        User user = SharedPrefManager.getInstance(UserProfile.this).getUser();
                        int id = user.getId();
                        callLocApi(id, address, String.valueOf(locate.getUser_latitude()), String.valueOf(locate.getUser_longitude()));
                    }
                } catch (Exception e) {
                    Toast.makeText(UserProfile.this, "Wait for device to detect your location", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        });


        dialog.show();
    }

    /* code to request for permission */


    public void EditProfile(View v) {
        TextView txt;
        Button btn;

        dialog.setContentView(R.layout.activity_user_edit_profile);
        txt = (TextView) dialog.findViewById(R.id.close);
        btn = (Button) dialog.findViewById(R.id.submit_user_profile);

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

//    public void AddLocations(View v) {
//        final TextView txt, lat1, adrss;
//        Button btn;
//        final EditText add_address;
//
//
//        dialog.setContentView(R.layout.activity_user_add_location);
//        txt = (TextView) dialog.findViewById(R.id.close3);
//        adrss = (TextView) dialog.findViewById(R.id.addresdds);
//        lat1 = (TextView) dialog.findViewById(R.id.latlong);
//        btn = (Button) dialog.findViewById(R.id.add_locations);
//        add_address = (EditText) dialog.findViewById(R.id.user_address);
//        txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        final String address;
//        address = add_address.getText().toString().trim();
//        if (address.isEmpty()) {
//            add_address.setError("Address is required");
//            add_address.requestFocus();
//            return;
//        }
//
////
////        btn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                final String address;
////                address = add_address.getText().toString().trim();
////                if (address.isEmpty()) {
////                    add_address.setError("Address is required");
////                    add_address.requestFocus();
////                    return;
////                }
////
////                if (ActivityCompat.checkSelfPermission(UserProfile.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////
////                    return;
////                }
////                client.getLastLocation().addOnSuccessListener(UserProfile.this, new OnSuccessListener<Location>() {
////                    @Override
////                    public void onSuccess(Location location) {
////                        if (location != null) {
////                            String add, lat, longi;
////                            adrss.setText(address);
////                            lat = String.valueOf(location.getLatitude());
////                            longi = String.valueOf(location.getLongitude());
////                            lat1.setText(lat + " " + longi);
////                        }
////                    }
////                });
////
////            }
////        });
//
//    }


//    private void getLocation(final String address) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//        client.getLastLocation().addOnSuccessListener(UserProfile.this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    String latitude, longitude;
//                    latitude = String.valueOf(location.getLatitude());
//                    longitude = String.valueOf(location.getLongitude());
//                    Toast.makeText(UserProfile.this, "working", Toast.LENGTH_LONG).show();
//                    User user = SharedPrefManager.getInstance(UserProfile.this).getUser();
//                    mProgress = new ProgressDialog(UserProfile.this);
//                    String title = "Updating";
//                    mProgress.setTitle(title);
//                    mProgress.setMessage("Please wait while we update your location");
//                    mProgress.show();
//                    int id;
//                    id = user.getId();
////                    callLocApi(id, address, latitude, longitude);
//                }
//            }
//        });
//
//    }

    private void callLocApi(int id, String address, String latitude, String longitude) {

        Call<LoginResponse> call = ApiClient
                .getInstance()
                .getApi()
                .UpdateUser(
                        id, address, latitude, longitude
                );


                                    /*  cal the response for enqueue*/

        mProgress = new ProgressDialog(UserProfile.this);
        String title = "Updating";
        mProgress.setTitle(title);
        mProgress.setMessage("Please wait while we update your location");
        mProgress.show();

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (response.code() == 200) {
                    if (loginResponse.isSuccess()) {
                        mProgress.dismiss();
                        //Proceed with login
                        SharedPrefManager
                                .getInstance(UserProfile.this)
                                .saveUser(loginResponse.getData());
                        Toast.makeText(UserProfile.this, "Location successfully Updated", Toast.LENGTH_LONG).show();
                    } else if (!loginResponse.isSuccess()) {
                        mProgress.dismiss();
                        Toast.makeText(UserProfile.this, "Location not updated, kindly check your data", Toast.LENGTH_LONG).show();
                    }
                } else if (response.code() == 404) {
                    mProgress.dismiss();
                    Toast.makeText(UserProfile.this, "Location not updated successfully", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
                            /*  end of cal the response for enqueue*/


    }


    public void dashboard() {
        Intent intent = new Intent(this, UserDashboard.class);
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
        Toast.makeText(UserProfile.this, " Kindly turn On your Location or Network", Toast.LENGTH_SHORT).show();
    }
}
