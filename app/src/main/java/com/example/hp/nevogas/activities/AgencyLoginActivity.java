package com.example.hp.nevogas.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.hp.nevogas.storage.AgencySharedPrefManager;
import com.example.hp.nevogas.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgencyLoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editemail, editpassword;
    TextView reset, register;
    Button submit;

    private ProgressDialog mProgress;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_login);
        editemail = (EditText) findViewById(R.id.email);
        editpassword = (EditText) findViewById(R.id.password);

        reset = (TextView) findViewById(R.id.reset_password);
        register = (TextView) findViewById(R.id.agency_register);
        submit = (Button) findViewById(R.id.submit);

        reset.setOnClickListener(this);
        register.setOnClickListener(this);
        submit.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (AgencySharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, AgencyDashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void AgencyLogin() {
        String email = editemail.getText().toString().trim();
        String password = editpassword.getText().toString().trim();

        if (email.isEmpty()) {
            editemail.setError("Email field is required");
            editemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editpassword.setError("Password field is required");
            editpassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editpassword.setError("Password must be greater than 6");
            editpassword.requestFocus();
            return;
        }

        if (networkInfo == null) {
            Toast.makeText(this, "Kindly turn your internet ON", Toast.LENGTH_SHORT).show();
        } else {
            mProgress = new ProgressDialog(this);
            String title = "Validating";
            mProgress.setTitle(title);
            mProgress.setMessage("Please wait while we authenticate your informations");
            mProgress.show();

            Call<AgencyLoginResponse> call = ApiClient
                    .getInstance()
                    .getApi()
                    .LoginAgency(
                            email, password
                    );

            call.enqueue(new Callback<AgencyLoginResponse>() {
                @Override
                public void onResponse(Call<AgencyLoginResponse> call, Response<AgencyLoginResponse> response) {
                    AgencyLoginResponse agencyloginResponse = response.body();
                    if (response.code() == 200) {
                        if (agencyloginResponse.isSuccess()) {
                            mProgress.dismiss();
                            //Proceed with login
                            SharedPrefManager
                                    .getInstance(AgencyLoginActivity.this)
                                    .saveAgency(agencyloginResponse.getData());
                            Intent intent = new Intent(AgencyLoginActivity.this, AgencyDashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (!agencyloginResponse.isSuccess()) {
                            mProgress.dismiss();
                            Toast.makeText(AgencyLoginActivity.this, "Login Not Successfull", Toast.LENGTH_LONG).show();
                        }
                    } else if (response.code() == 404) {
                        mProgress.dismiss();
                        Toast.makeText(AgencyLoginActivity.this, "Login Not Successfull,Kindly check your inputted information", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<AgencyLoginResponse> call, Throwable t) {

                }
            });
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                AgencyLogin();
                break;
            case R.id.agency_register:
                Intent intent = new Intent(this, AgencyRegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.reset_password:
                break;
            case R.id.back:
                startActivity(new Intent(this, SpecialityActivity.class));
                break;
        }
    }
}
