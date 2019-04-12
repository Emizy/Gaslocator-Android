package com.example.hp.nevogas.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.nevogas.R;

public class SpecialityActivity extends AppCompatActivity {
    TextView text;
    Button user , gas ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speciality);

        user = (Button) findViewById(R.id.user_login);
        gas = (Button) findViewById(R.id.gas_login);

        user.setOnClickListener(new View.OnClickListener(){
            @Override
           public void onClick(View v){
                Intent intent = new Intent(SpecialityActivity.this, UserLoginActivity.class);
                startActivity(intent);
            }
        });

        gas.setOnClickListener(new View.OnClickListener(){
            @Override
           public void onClick(View v){
                Intent intent = new Intent(SpecialityActivity.this, AgencyLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
