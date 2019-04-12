package com.example.hp.nevogas.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hp.nevogas.model.Agency;

/**
 * Created by HP on 3/17/2019.
 */

public class AgencySharedPrefManager {

    private static final String SHARED_PREF_NAME = "agency_shared_pref";
    private static AgencySharedPrefManager mInstance;
    private Context mCtx;

    private AgencySharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;

    }

    public static synchronized AgencySharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new AgencySharedPrefManager(mCtx);
        }
        return mInstance;
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
        editor.putString("account_statue", agency.getAccount_status());
        editor.apply();
    }

    /* Code to check if a agency is logggedin*/
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public Agency getAgency() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Agency agency;
        agency = new Agency(
                sharedPreferences.getString("token", null),
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("bussiness_name", null),
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
