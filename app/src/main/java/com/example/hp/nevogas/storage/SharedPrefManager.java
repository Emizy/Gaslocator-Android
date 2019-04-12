package com.example.hp.nevogas.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hp.nevogas.model.Agency;
import com.example.hp.nevogas.model.User;

/**
 * Created by HP on 3/15/2019.
 */

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;

    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;

    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("token", user.getToken());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("user_type", user.getType());
        editor.putString("state", user.getState());
        editor.putString("address", user.getAddress());
        editor.putString("latitude", user.getLatitude());
        editor.putString("longitude", user.getLongitude());
        editor.putString("phone", user.getPhone());
        editor.putString("user_status", user.getUser_status());
        editor.apply();
    }

    public void saveAgency(Agency agency) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", agency.getToken());
        editor.putInt("id", agency.getId());
        editor.putString("business_name", agency.getBusiness_name());
        editor.putString("email", agency.getEmail());
        editor.putString("phone", agency.getPhone());
        editor.putString("state", agency.getState());
        editor.putString("address", agency.getAddress());
        editor.putString("latitude", agency.getLatitude());
        editor.putString("longitude", agency.getLongitude());
        editor.putInt("user_id", agency.getUser_id());
        editor.putString("about_us",agency.getAbout_us());
        editor.putString("account_status", agency.getAccount_status());
        editor.apply();
    }


/* Code to check if a user is logggedin*/
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User user;
        user = new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("token", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("user_type", null),
                sharedPreferences.getString("state", null),
                sharedPreferences.getString("address", null),
                sharedPreferences.getString("latitude", null),
                sharedPreferences.getString("longitude", null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getString("user_status", null)
        );
        return user;
    }

    public Agency getAgency() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Agency agency;
        agency = new Agency(
                sharedPreferences.getString("token", null),
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("business_name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getString("state", null),
                sharedPreferences.getString("address", null),
                sharedPreferences.getString("latitude", null),
                sharedPreferences.getString("longitude", null),
                sharedPreferences.getInt("user_id", -1),
                sharedPreferences.getString("about_us",null),
                sharedPreferences.getString("account_status", null)
        );
        return agency;
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
