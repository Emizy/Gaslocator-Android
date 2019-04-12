package com.example.hp.nevogas.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.directionshelper.FetchURL;
import com.example.hp.nevogas.directionshelper.TaskLoadedCallback;
import com.example.hp.nevogas.model.Agency;
import com.example.hp.nevogas.model.LatShow;
import com.example.hp.nevogas.storage.SharedPrefManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class GasMapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback  {
    GoogleMap map;
    MarkerOptions User_Location, Agency_Location;
    Polyline currentPolyline;
    LatShow latShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_map);

        if (getIntent().hasExtra("user_latitude") && getIntent().hasExtra("user_longitude") && getIntent().hasExtra("user_address")) {
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.gasmap);

            mapFragment.getMapAsync(this);
            Agency agency = SharedPrefManager.getInstance(this).getAgency();

            double user_latitude, user_longitude, agency_latitude, agency_longitude;
            String agency_address, user_address;
            user_latitude = getIntent().getDoubleExtra("user_latitude", 00);
            user_longitude = getIntent().getDoubleExtra("user_longitude", 00);
            agency_latitude = Double.parseDouble(agency.getLatitude());
            agency_longitude = Double.parseDouble(agency.getLongitude());
            agency_address = agency.getAddress();
            user_address = getIntent().getExtras().getString("user_address");
            latShow = new LatShow(user_latitude,user_longitude);

            User_Location = new MarkerOptions().position(new LatLng(user_latitude, user_longitude)).title(user_address);
            Agency_Location = new MarkerOptions().position(new LatLng(agency_latitude, agency_longitude)).title(agency_address);

            String url = getUrl(User_Location.getPosition(), Agency_Location.getPosition(), "driving");
            new FetchURL(GasMapActivity.this).execute(url, "driving");

        } else {
            Toast.makeText(GasMapActivity.this, "Ooops Necessary information is missing", Toast.LENGTH_LONG).show();
        }


    }

//

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Agency agency = SharedPrefManager.getInstance(this).getAgency();
        map = googleMap;
        LatLng user_lat = new LatLng(latShow.getLatitude(),latShow.getLongitude());
        LatLng agency_lat = new LatLng(Double.parseDouble(agency.getLatitude()),Double.parseDouble(agency.getLongitude()));
        map.addMarker(User_Location);
        map.addMarker(Agency_Location);
        map.moveCamera(CameraUpdateFactory.newLatLng(user_lat));
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
