package com.example.hp.nevogas.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.nevogas.R;
import com.example.hp.nevogas.api.ApiClient;
import com.example.hp.nevogas.model.LoginResponse;
import com.example.hp.nevogas.model.User;
import com.example.hp.nevogas.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editemail, editpassword;
    private ProgressDialog mProgress;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        findViewById(R.id.submit).setOnClickListener(this);
        findViewById(R.id.user_register).setOnClickListener(this);
        findViewById(R.id.reset_password).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        editemail = (EditText) findViewById(R.id.email);
        editpassword = (EditText) findViewById(R.id.password);
        connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, UserDashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void UserLogin() {
        String email = editemail.getText().toString().trim();
        String password = editpassword.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editemail.setError("Email is not valid");
            editemail.requestFocus();
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


        if (networkInfo == null) {
            Toast.makeText(UserLoginActivity.this, "Kindly turn your internet ON", Toast.LENGTH_LONG).show();
        } else {
            mProgress = new ProgressDialog(this);
            String title = "Validating";
            mProgress.setTitle(title);
            mProgress.setMessage("Please wait while we authenticate your informations");
            mProgress.show();
            Call<LoginResponse> call = ApiClient
                    .getInstance()
                    .getApi()
                    .LoginUser(
                            email, password
                    );

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();
                    if (response.code() == 200) {
                        if (loginResponse.isSuccess()) {
                            mProgress.dismiss();
                            //Proceed with login
                            SharedPrefManager
                                    .getInstance(UserLoginActivity.this)
                                    .saveUser(loginResponse.getData());
                            Intent intent = new Intent(UserLoginActivity.this, UserDashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (!loginResponse.isSuccess()) {
                            mProgress.dismiss();
                            Toast.makeText(UserLoginActivity.this, "Login Not Successfull", Toast.LENGTH_LONG).show();
                        }
                    } else if (response.code() == 404) {
                        mProgress.dismiss();
                        Toast.makeText(UserLoginActivity.this, "Login Not Successfull,Kindly check your inputted information", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                UserLogin();
                break;
            case R.id.user_register:
                startActivity(new Intent(this, UserRegisterActivity.class));
                break;
            case R.id.reset_password:
                startActivity(new Intent(this, UserResetPasswordActivity.class));
                break;
            case R.id.back:
                startActivity(new Intent(this, SpecialityActivity.class));
                break;
        }
    }
}
