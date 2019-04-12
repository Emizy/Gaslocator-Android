package com.example.hp.nevogas.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hp.nevogas.R;


public class SplashActivity extends AppCompatActivity {
    private static int SLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent imtent = new Intent(SplashActivity.this , SpecialityActivity.class);
                startActivity(imtent);
                finish();
            }
        },SLASH_TIME_OUT);
    }
}
