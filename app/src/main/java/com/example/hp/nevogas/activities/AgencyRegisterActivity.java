package com.example.hp.nevogas.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.api.ApiClient;
import com.example.hp.nevogas.model.AgencyRegisterResponse;
import com.example.hp.nevogas.model.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgencyRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editbusiness, editemail, editpassword, editstate, editphone, editaboutus;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_register);
        findViewById(R.id.agency_login).setOnClickListener(this);
        findViewById(R.id.submit).setOnClickListener(this);
        editbusiness = (EditText) findViewById(R.id.business);
        editemail = (EditText) findViewById(R.id.email);
        editpassword = (EditText) findViewById(R.id.password);
        editstate = (EditText) findViewById(R.id.state);
        editphone = (EditText) findViewById(R.id.phone);
        editaboutus = (EditText) findViewById(R.id.about);


        connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();


    }

    public void AgencyRegister() {
        String business_name = editbusiness.getText().toString().trim();
        String email = editemail.getText().toString().trim();
        String password = editpassword.getText().toString().trim();
        String state = editstate.getText().toString().trim();
        String phone = editphone.getText().toString().trim();
        String about_us = editaboutus.getText().toString().trim();

        if (business_name.isEmpty()) {
            editbusiness.setError("Gas Station Name is required");
            editbusiness.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editemail.setError("Email is not valid");
            editemail.requestFocus();
            return;
        }

        if (state.isEmpty()) {
            editstate.setError("State is required");
            editstate.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            editphone.setError("Phone number is required");
            editphone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editpassword.setError("Password is required");
            editpassword.requestFocus();
            return;
        }


        if (password.length() < 6) {
            editpassword.setError("Password must be greater than 6 characters");
            editpassword.requestFocus();
            return;
        }

        if (about_us.isEmpty()) {
            editaboutus.setError("About Gas Station is required");
            editaboutus.requestFocus();
            return;
        }

        if (networkInfo == null) {
            Toast.makeText(AgencyRegisterActivity.this, "Kindly turn your internet ON", Toast.LENGTH_SHORT).show();
        } else {
            mProgress = new ProgressDialog(this);
            String title = "Registering";
            mProgress.setTitle(title);
            mProgress.setMessage("Please wait while we complete your registration");
            mProgress.show();

            Call<AgencyRegisterResponse> call = ApiClient
                    .getInstance()
                    .getApi()
                    .CreateAgency(
                            business_name, email, state, phone, password, about_us

                    );

            call.enqueue(new Callback<AgencyRegisterResponse>() {
                @Override
                public void onResponse(Call<AgencyRegisterResponse> call, Response<AgencyRegisterResponse> response) {
                    AgencyRegisterResponse dr = response.body();
                    if (response.code() == 200) {
                        mProgress.dismiss();
                        Toast.makeText(AgencyRegisterActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                    } else if (response.code() == 404) {
                        mProgress.dismiss();
                        Toast.makeText(AgencyRegisterActivity.this, "Agency already Exist", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<AgencyRegisterResponse> call, Throwable t) {

                }
            });
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                AgencyRegister();
                break;
            case R.id.agency_login:
                Intent intent = new Intent(AgencyRegisterActivity.this, AgencyLoginActivity.class);
                startActivity(intent);
                break;

        }
    }
}
