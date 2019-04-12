package com.example.hp.nevogas.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.hp.nevogas.R;
import com.example.hp.nevogas.directionshelper.FetchURL;
import com.example.hp.nevogas.directionshelper.TaskLoadedCallback;
import com.example.hp.nevogas.model.LatShow;
import com.example.hp.nevogas.model.User;
import com.example.hp.nevogas.storage.SharedPrefManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.instantapps.ActivityCompat;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    double agency_latitude, agency_longitude, user_latitude, user_longitude;
    String agency_address, user_address;
    GoogleMap map;
    MarkerOptions User_Location, Agency_Location;
    Polyline currentPolyline;
    LatShow latShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (getIntent().hasExtra("agency_latitude") && getIntent().hasExtra("agency_longitude") && getIntent().hasExtra("agency_address")) {
            agency_latitude = getIntent().getDoubleExtra("agency_latitude", 00);
            agency_longitude = getIntent().getDoubleExtra("agency_longitude", 00);
            agency_address = getIntent().getStringExtra("agency_address");
            latShow = new LatShow(agency_latitude, agency_longitude);
            User user = SharedPrefManager.getInstance(this).getUser();
            user_latitude = Double.parseDouble(user.getLatitude());
            user_longitude = Double.parseDouble(user.getLongitude());
            user_address = user.getAddress();

            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

            mapFragment.getMapAsync(this);

            User_Location = new MarkerOptions().position(new LatLng(user_latitude, user_longitude)).title(user_address);
            Agency_Location = new MarkerOptions().position(new LatLng(agency_latitude, agency_longitude)).title(agency_address);
            String url = getUrl(User_Location.getPosition(), Agency_Location.getPosition(), "driving");
            new FetchURL(MapActivity.this).execute(url, "driving");
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        User user = SharedPrefManager.getInstance(this).getUser();
        LatLng user_lat = new LatLng(Double.parseDouble(user.getLatitude()), Double.parseDouble(user.getLongitude()));
        LatLng agency_lat = new LatLng(latShow.getLatitude(), latShow.getLongitude());
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.addMarker(User_Location);
        map.addMarker(Agency_Location);
        map.moveCamera(CameraUpdateFactory.newLatLng(user_lat));
        // map.moveCamera(CameraUpdateFactory.newLatLng(agency_lat));
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }


}
