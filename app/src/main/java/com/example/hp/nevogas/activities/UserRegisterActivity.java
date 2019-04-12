package com.example.hp.nevogas.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.nevogas.api.ApiClient;
import com.example.hp.nevogas.model.DefaultResponse;
import com.example.hp.nevogas.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editname, editemail, editpassword, editstate, editphone;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        editname = (EditText) findViewById(R.id.name);
        editemail = (EditText) findViewById(R.id.email);
        editpassword = (EditText) findViewById(R.id.password);
        editstate = (EditText) findViewById(R.id.state);
        editphone = (EditText) findViewById(R.id.phone);

        findViewById(R.id.user_login).setOnClickListener(this);
        findViewById(R.id.submit).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();


    }

    private void UserSignUp() {
        String name = editname.getText().toString().trim();
        String email = editemail.getText().toString().trim();
        String password = editpassword.getText().toString().trim();
        String state = editstate.getText().toString().trim();
        String phone = editphone.getText().toString().trim();

        if (name.isEmpty()) {
            editname.setError("Name is Error");
            editname.requestFocus();
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
            editphone.setError("State is required");
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

        if (networkInfo == null) {
            String message = "Kindly turn your internet ON";
            Toast.makeText(UserRegisterActivity.this, message, Toast.LENGTH_LONG).show();
        } else {
            /* User registration data is send to the api */
            mProgress = new ProgressDialog(this);
            String title = "Registering";
            mProgress.setTitle(title);
            mProgress.setMessage("Please wait while we complete your registration");
            mProgress.show();
            Call<DefaultResponse> call = ApiClient
                    .getInstance()
                    .getApi()
                    .CreateUser(
                            name, email, state,phone,password
                    );

            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if (response.code() == 200) {
                        mProgress.dismiss();
                        DefaultResponse dr = response.body();
                        Toast.makeText(UserRegisterActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                    } else if (response.code() == 404) {
                        mProgress.dismiss();
                        Toast.makeText(UserRegisterActivity.this, "User already Exist", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                }
            });



        }


    }

//    private void selectimage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, IMG_REQUEST);
//    }
//
//    private String ImageToString(){
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
//        byte[] imgbyte = byteArrayOutputStream.toByteArray();
//        return Base64.encodeToString(imgbyte,Base64.DEFAULT);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
//
//            Uri path = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                Img.setImageBitmap(bitmap);
//                Img.setVisibility(View.VISIBLE);
////                editimagetitle.setVisibility(View.VISIBLE);
//                //choosebn.setEnabled(false);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                UserSignUp();
                break;
            case R.id.user_login:
                Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                Intent intent1 = new Intent(UserRegisterActivity.this, SpecialityActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
